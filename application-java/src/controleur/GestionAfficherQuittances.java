package controleur;

import javax.swing.JButton;

import vue.AfficherListeQuittance;

public class GestionAfficherQuittances {
	
	private AfficherListeQuittance fen;

	
	public GestionAfficherQuittances(AfficherListeQuittance fen) {
		this.fen = fen;
	}


	public void gestionBtnAjouter(JButton btnAjouter) {
		btnAjouter.addActionListener(e -> {
			System.out.println("BTN CLIC");
			
			//TODO : ajouter la logique metier
			}
		);
		
	}
	
	public void chargerDonnes() {
		// Logique metier
	}
}
