package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import modele.dao.DaoBail;
import vue.AfficherBaux;

public class GestionAfficherBaux {
	
	private AfficherBaux fen;

	public GestionAfficherBaux(AfficherBaux fen) {
		this.fen = fen;
	}
	
	public void chargerDonneesTable() {
		
		try {
			List<String[]> lignes = new DaoBail().findAll().stream()
					.map(e -> new String[] {e.getIdBail(), e.getDateDeDebut(), 
						e.getDateDeFin(), e.getBien().getIdentifiantLogement()})
					.toList();
			
			fen.remplirTable(lignes);
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
	}
	

}
