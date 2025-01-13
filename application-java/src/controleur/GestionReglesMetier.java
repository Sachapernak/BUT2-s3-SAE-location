package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.ReglesMetier;

public class GestionReglesMetier implements ActionListener {

	private ReglesMetier fen;
	
	public GestionReglesMetier(ReglesMetier rm)  {
		this.fen = rm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		if( "Retour".equals(btnLibelle)) {
				this.fen.dispose();
		}
	}
	
}
