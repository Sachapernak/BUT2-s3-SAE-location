package controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;

import modele.Adresse;
import modele.Bail;
import modele.BienLocatif;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoLocataire;
import rapport.RapportQuittance;
import vue.SelectionBailQuittance;

public class GestionSelectionBailQuittance {
	
	private SelectionBailQuittance fen;
	private Locataire loc;
	private Bail bai;
	private RapportQuittance rapport;

	
	public GestionSelectionBailQuittance(SelectionBailQuittance fen) {
		
		this.rapport = new RapportQuittance();
		this.fen = fen;
	}
	
	public void gestionBoutonSuivant(JButton btn) {
		
		
		btn.addActionListener(clic -> {
			try {
				DaoBail daoBail = new DaoBail(); 
				DaoBienLocatif daoBien = new DaoBienLocatif();
				DaoDocumentComptable daoDoc = new DaoDocumentComptable();
				
				Bail bai = daoBail.findById(fen.getSelectedIdBail());
				
				BienLocatif bien = bai.getBien();
				Adresse adresse = bien.getBat().getAdresse();
				
				String nomLoc = loc.getNom();
				String prenomLoc = loc.getPrenom();
				String AdresseBien = adresse.getAdressePostale();
				String Complement = adresse.getComplementAdresse() + " " + bien.getComplementAdresse();
				String codeP = String.valueOf(adresse.getCodePostal());
				String ville = adresse.getVille();
			
				BigDecimal mult = daoBail.findPartDesCharges(fen.getSelectedIdBail(), loc.getIdLocataire());
				
				BigDecimal montantTotal = daoDoc.findTotalLoyerMois(loc.getIdLocataire(), fen.getSelectedIdBail(), 
																						fen.getDate());
				BigDecimal loyerDuMois = daoBien.findLoyerDuMois(bien.getIdentifiantLogement(),fen.getDate())
																				.multiply(mult);
				
				BigDecimal chargeDuMois = daoBail.findChargeDuMois(fen.getSelectedIdBail(), fen.getDate())
																			.multiply(mult);
				
				// Calcul initial du reste après déduction du loyer
				BigDecimal rest = montantTotal.subtract(loyerDuMois);

				// Traitement pour le loyer
				String loyerDuMoisString;
				

				if (rest.compareTo(BigDecimal.ZERO) > 0) {
				    // Si montantTotal - loyer > 0, on prend le loyer complet
				    loyerDuMoisString = loyerDuMois.toString();
				
				} else {
					
				    // Sinon, on calcule la différence négative (loyer - montantTotal)
				    loyerDuMoisString = loyerDuMois.subtract(montantTotal).toString();
				    
				    // Dans ce cas, le montantTotal est insuffisant pour couvrir le loyer,
				    // le reste reste négatif ou nul, pas besoin de continuer plus loin.
				    rest = BigDecimal.ZERO;
				}

				// Traitement pour la charge si le reste est positif
				String chargeDuMoisString = "0";
				String restText = "0";

				if (rest.compareTo(BigDecimal.ZERO) > 0) {
				    // Si après le loyer il reste un montant positif
				    BigDecimal restApresLoyer = rest; 
				    BigDecimal differenceCharge = restApresLoyer.subtract(chargeDuMois);
				    
				    if (differenceCharge.compareTo(BigDecimal.ZERO) > 0) {
				        // Si rest - charge > 0, on prend la charge complète
				        chargeDuMoisString = chargeDuMois.toString();
				        
				    } else {
				        // Sinon, on calcule charge - rest
				        chargeDuMoisString = chargeDuMois.subtract(restApresLoyer).toString();
				    }
				    
				    // Mise à jour du reste après déduction de la charge
				    rest = restApresLoyer.subtract(chargeDuMois);
				    
				    if (rest.compareTo(BigDecimal.ZERO) > 0) {
				        restText = rest.toString();
				    } else {
				        restText = "0";
				    }
				} else {
				    // Si pas de reste après le loyer, aucune charge n'est déduite
				    chargeDuMoisString = "0";
				    restText = "0";
				}
				
	            rapport.setNom(nomLoc);
	            rapport.setPrenom(prenomLoc);
	            rapport.setAdresse(AdresseBien);
	            rapport.setComplement(Complement);  // Exemple : aucun complément
	            rapport.setCodePostal(codeP);
	            rapport.setVille(ville);
	            rapport.setDateCourante(LocalDate.now().toString());
	            rapport.setDatePaiement(fen.getDate());    // Date du paiement
	            rapport.setTotalPaiement(montantTotal.toString());      // Exemple de total payé
	            rapport.setMois(fen.getDate());
	            rapport.setTotalLoyer(loyerDuMoisString);         // Exemple de loyer
	            rapport.setTotalCharges(chargeDuMoisString);        // Exemple de charges
	            rapport.setExcedent(restText);   
				
				
				
			} catch (SQLException | IOException ex) {
				fen.afficherMessageErreur(ex.getMessage());
				ex.printStackTrace();
			}

		});
	}
	
	public void gestionBoutonValiderDate(JButton btn) {
	    btn.addActionListener(e -> {
	        try {
	            String dateEntree = fen.getTextFieldDate(); 
	            
	            // Vérifier si la date est au format yyyy-MM-dd
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate.parse(dateEntree, formatter);
	        } catch (DateTimeParseException ex) { 
	            ex.printStackTrace(); 
	            fen.afficherMessageErreur("La date doit être au format yyyy-MM-dd"); 
	            return; 
	        }
	        
	        fen.setDate(fen.getTextFieldDate());
	        chargerDonneesTable();

	    });
	}
	
	public void chargerDonnees() {
		fen.setDate(LocalDate.now().toString());
        chargerDonneesTable();

	}
	
	public void chargerDonneesTable() {
		try {
			 this.loc = new DaoLocataire().findById(fen.getLoc());
			
			List<String[]> contrats = loc.getContrats().stream()
				    .filter(ctr -> {
				        Bail bai = ctr.getBail();
				        String fenDate = fen.getDate();

				        return bai.getDateDeDebut().compareTo(fenDate) < 0
				            && (bai.getDateDeFin() == null || bai.getDateDeFin().isEmpty() || bai.getDateDeFin().compareTo(fenDate) > 0)
				            && ctr.getDateEntree().compareTo(fenDate) < 0
				            && (ctr.getDateSortie() == null || ctr.getDateSortie().isEmpty() || ctr.getDateSortie().compareTo(fenDate) > 0);
				    })
				    .map(ctr -> {
						Bail bai = ctr.getBail();
				        return new String[] {
				        		bai.getIdBail(),
				        		bai.getDateDeDebut(),
				        		bai.getDateDeFin() == null ? "" : bai.getDateDeFin(),
				        };
				    })
				    .toList();
			
			fen.chargerTable(contrats);			
			
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
			
		}
		
		
	}


}
