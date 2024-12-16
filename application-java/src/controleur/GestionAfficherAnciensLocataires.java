package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.AfficherAnciensLocataires;

public class GestionAfficherAnciensLocataires implements ActionListener {
	
	private AfficherAnciensLocataires fen_afficher_anciens_locataires;
	
	public GestionAfficherAnciensLocataires(AfficherAnciensLocataires aal)  {
		this.fen_afficher_anciens_locataires = aal;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" :
				this.fen_afficher_anciens_locataires.dispose();
				break;
			case "Modifier" :
				System.out.println("modification");
				break;
		}
	}
	
}
