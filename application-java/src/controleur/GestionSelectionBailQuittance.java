package controleur;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;

import modele.Adresse;
import modele.Bail;
import modele.BienLocatif;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.Locataire;
import modele.TypeDoc;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoFactureBien;
import modele.dao.DaoLocataire;
import rapport.RapportQuittance;
import vue.SelectionBailQuittance;

/**
 * Contrôleur pour la sélection d'un bail afin de générer une quittance.
 * Gère les interactions entre la vue {@link SelectionBailQuittance} et les opérations métier associées.
 */
public class GestionSelectionBailQuittance {
	
	private SelectionBailQuittance fen;
	private Locataire loc;
	private RapportQuittance rapport;

	/**
	 * Constructeur qui initialise le contrôleur avec la vue correspondante et un nouveau rapport.
	 * @param fen la fenêtre de sélection de bail pour la quittance.
	 */
	public GestionSelectionBailQuittance(SelectionBailQuittance fen) {
		this.rapport = new RapportQuittance();
		this.fen = fen;
	}
	
	/**
	 * Configure le bouton "Suivant" pour générer une quittance lorsque l'utilisateur clique dessus.
	 * @param btn le bouton "Suivant" à configurer.
	 */
	public void gestionBoutonSuivant(JButton btn) {
	    btn.addActionListener(clic -> {
	        try {
	            DaoBail daoBail = new DaoBail(); 
	            DaoBienLocatif daoBien = new DaoBienLocatif();
	            DaoDocumentComptable daoDoc = new DaoDocumentComptable();

	            // Récupération des informations nécessaires pour générer la quittance
	            
	            
	            
	            Bail bai = daoBail.findById(fen.getSelectedIdBail());
	            BienLocatif bien = bai.getBien();
	            Adresse adresse = bien.getBat().getAdresse();

	            String nomLoc = loc.getNom();
	            String prenomLoc = loc.getPrenom();
	            String adresseBien = adresse.getAdressePostale();
	            String complement = (adresse.getComplementAdresse() == null ? "" : adresse.getComplementAdresse()) + 
	                                (bien.getComplementAdresse() == null ? "" : " " + bien.getComplementAdresse());
	            String codePostal = String.valueOf(adresse.getCodePostal());
	            String ville = adresse.getVille();

	            // Calcul des montants financiers liés au bail et au locataire
	            Map<String, String> montants = calculerMontants(daoDoc, daoBien, daoBail, bien);

	            // Assignation des informations au rapport
	            rapport.setNom(nomLoc);
	            rapport.setPrenom(prenomLoc);
	            rapport.setAdresse(adresseBien);
	            rapport.setComplement(complement);
	            rapport.setCodePostal(codePostal);
	            rapport.setVille(ville);
	            rapport.setDateCourante(LocalDate.now().toString());
	            rapport.setDatePaiement(fen.getDate());
	            rapport.setTotalPaiement(montants.get("montantTotal"));
	            rapport.setMois(formatMoisAnnee(fen.getDate()));
	            rapport.setTotalLoyer(montants.get("loyerDuMoisString"));
	            rapport.setTotalCharges(montants.get("chargeDuMoisString"));
	            rapport.setExcedent(montants.get("restText"));

                // Génération du fichier de la quittance
                String cheminFichier = rapport.genererSoldeToutCompte(nomLoc + "-QUITTANCE-" + fen.getDate());

                // Création ou ouverture du fichier généré
                File fichier = new File(cheminFichier);
                
                DocumentComptable doc = new DocumentComptable(
                        "QUIT" + nomLoc + fen.getDate(), 
                        fen.getDate(), 
                        TypeDoc.QUITTANCE, 
                        new BigDecimal(montants.get("montantTotal")), 
                        cheminFichier
                );
                doc.setLocataire(loc);
                
                // Enregistrement du document comptable s'il n'existe pas déjà
                if (daoDoc.findById(doc.getNumeroDoc(), doc.getDateDoc()) == null) {
                    new DaoDocumentComptable().create(doc);
                    new DaoFactureBien().create(new FactureBien(bien, doc, 1));
                }
                
                // Ouvrir la quittance générée ou afficher une erreur si le fichier n'existe pas
                if (fichier.exists()) {
                    Desktop.getDesktop().open(fichier);
                    fen.dispose();
                } else {
                    fen.afficherMessageErreur("Le fichier n'a pas été trouvé : " + cheminFichier);
                }

	        } catch (SQLException | IOException ex) {
	            fen.afficherMessageErreur(ex.getMessage());
	            ex.printStackTrace();
	        }
	    });
	}

	/**
	 * Calcule divers montants financiers associés à la quittance pour un bail donné.
	 * @param daoDoc accès aux documents comptables.
	 * @param daoBien accès aux informations sur les biens.
	 * @param daoBail accès aux informations sur les baux.
	 * @param bien le bien locatif concerné.
	 * @return une carte (Map) contenant les valeurs calculées sous forme de chaînes.
	 * @throws SQLException en cas d'erreur d'accès aux données.
	 * @throws IOException en cas d'erreur d'E/S.
	 */
	private Map<String, String> calculerMontants(
	        DaoDocumentComptable daoDoc, 
	        DaoBienLocatif daoBien, 
	        DaoBail daoBail,
	        BienLocatif bien) throws SQLException, IOException {

        String idLocataire = loc.getIdLocataire(); 
        String date = fen.getDate();
		String idBail = fen.getSelectedIdBail();
		
	    BigDecimal mult = daoBail.findPartDesCharges(idBail, idLocataire);
	    BigDecimal montantTotal = daoDoc.findTotalLoyerMois(idLocataire, idBail, date);
	    BigDecimal loyerDuMois = daoBien.findLoyerDuMois(bien.getIdentifiantLogement(), date)
	                                      .multiply(mult);
	    BigDecimal chargeDuMois = daoBail.findChargeDuMois(idBail, date).multiply(mult);

	    BigDecimal rest = montantTotal.subtract(loyerDuMois);
	    String loyerDuMoisString;

	    if (rest.compareTo(BigDecimal.ZERO) > 0) {
	        loyerDuMoisString = loyerDuMois.toString();
	    } else {
	        loyerDuMoisString = loyerDuMois.subtract(montantTotal).toString();
	        rest = BigDecimal.ZERO;
	    }

	    String chargeDuMoisString = "0";
	    String restText = "0";

	    if (rest.compareTo(BigDecimal.ZERO) > 0) {
	        BigDecimal restApresLoyer = rest;
	        BigDecimal differenceCharge = restApresLoyer.subtract(chargeDuMois);

	        if (differenceCharge.compareTo(BigDecimal.ZERO) > 0) {
	            chargeDuMoisString = chargeDuMois.toString();
	        } else {
	            chargeDuMoisString = chargeDuMois.subtract(restApresLoyer).toString();
	        }

	        rest = restApresLoyer.subtract(chargeDuMois);
	        restText = (rest.compareTo(BigDecimal.ZERO) > 0) ? rest.toString() : "0";
	    }

	    Map<String, String> result = new HashMap<>();
	    result.put("loyerDuMoisString", loyerDuMoisString);
	    result.put("chargeDuMoisString", chargeDuMoisString);
	    result.put("restText", restText);
	    result.put("montantTotal", montantTotal.toString());

	    return result;
	}

	/**
	 * Formate une date pour afficher le mois et l'année en français.
	 * @param dateStr la date au format ISO (yyyy-MM-dd).
	 * @return la chaîne formatée avec le mois et l'année.
	 */
	private String formatMoisAnnee(String dateStr) {
	    LocalDate date = LocalDate.parse(dateStr); 
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
	    return date.format(formatter);
	}
	
	/**
	 * Configure le bouton "Valider" pour vérifier la date entrée et actualiser les données en conséquence.
	 * @param btn le bouton "Valider" à configurer.
	 */
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
	
	/**
	 * Initialise la date à aujourd'hui et charge les données correspondantes.
	 */
	public void chargerDonnees() {
		fen.setDate(LocalDate.now().toString());
        chargerDonneesTable();
	}
	
	/**
	 * Charge les données des contrats actifs du locataire pour la date sélectionnée et les affiche dans la table.
	 */
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
