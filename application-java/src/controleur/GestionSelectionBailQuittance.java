package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;

import modele.Bail;
import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.SelectionBailQuittance;

public class GestionSelectionBailQuittance {
	
	private SelectionBailQuittance fen;
	
	public GestionSelectionBailQuittance(SelectionBailQuittance fen) {
		this.fen = fen;
	}
	
	public void gestionBoutonSuivant(JButton btn) {
		btn.addActionListener(e -> {

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
			Locataire loc = new DaoLocataire().findById(fen.getLoc());
			
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
