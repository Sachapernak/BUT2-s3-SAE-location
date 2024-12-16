package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.ConfigurationConnexion;

public class GestionConfigurationConnexion implements ActionListener{

	private ConfigurationConnexion fen_connexion;
	
	public GestionConfigurationConnexion(ConfigurationConnexion c) {
		this.fen_connexion = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
			JButton btnActif = (JButton) e.getSource();
	        String btnLibelle = btnActif.getText();
			
			switch (btnLibelle) {
				case "Annuler" :
					this.fen_connexion.dispose();
					break;
				case "Connecter" : 
					System.out.println("connexion");
					this.fen_connexion.dispose();
					
					break;
			}
			
	}

}
