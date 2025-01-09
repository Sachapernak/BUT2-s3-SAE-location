package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.AfficherCharges;

public class GestionAfficherCharges implements ActionListener{

	private AfficherCharges fen_afficher_charges;
	
	public GestionAfficherCharges(AfficherCharges ac) {
		this.fen_afficher_charges = ac;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Régulariser" :

				break;
				
			case "Retour" :
				this.fen_afficher_charges.dispose();
				break;
			default:
				break;
		}
		
	}
	


}
