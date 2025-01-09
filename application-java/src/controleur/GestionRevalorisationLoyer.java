package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.RevalorisationLoyer;

public class GestionRevalorisationLoyer implements ActionListener {

	private RevalorisationLoyer fen_revalorisation;
	
	public GestionRevalorisationLoyer(RevalorisationLoyer rl)  {
		this.fen_revalorisation = rl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" :
				this.fen_revalorisation.dispose();
				break;
			case "Valider" :
				this.fen_revalorisation.dispose();
				break;
		}
	}
	
}
