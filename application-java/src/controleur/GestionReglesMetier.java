package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.ReglesMetier;

public class GestionReglesMetier implements ActionListener {

	private ReglesMetier fen_regles_metier;
	
	public GestionReglesMetier(ReglesMetier rm)  {
		this.fen_regles_metier = rm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" :
				this.fen_regles_metier.dispose();
				break;
		}
	}
	
}
