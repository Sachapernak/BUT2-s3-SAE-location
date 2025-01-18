package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;

import modele.dao.DaoLocataire;
import vue.SelectionBailQuittance;
import vue.SelectionQuittance;

public class GestionSelectionQuittance {
	
	private SelectionQuittance fen;
	
	public GestionSelectionQuittance(SelectionQuittance fen) {
		this.fen = fen;
	}
	
	public void chargerDonnees() {
		
		List<String[]> locs;
		
		try {
		
			if (fen.getNomRecherche().isEmpty()) {
				locs = new DaoLocataire().findAll().stream()
						.map(e -> {
							String[] res = {e.getIdLocataire(), e.getNom(), e.getPrenom()};
							return res;})
						.toList();
			} else {
				locs = new DaoLocataire().findByNomOuPrenom(fen.getNomRecherche()).stream()
						.map(e -> {
							String[] res = {e.getIdLocataire(), e.getNom(), e.getPrenom()};
							return res;})
						.toList();
				
			}
			
			fen.chargerTable(locs);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void gestionBoutonRecherche(JButton btn) {
		btn.addActionListener(e -> {
			chargerDonnees();
		});
	}
	
	public void gestionBoutonSuivant(JButton btn) {
		btn.addActionListener(e -> {
			String id = fen.getSelectedIdLoc();
			if (id.isEmpty()) {
				fen.afficherMessageErreur("Veuillez selectionner une ligne");
			} else {
				SelectionBailQuittance fenQBai = new SelectionBailQuittance(id);
				fenQBai.setVisible(true);
			}
			
		});
	}

}
