package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.ModifierLocataireActuel;

public class GestionModifierLocataireActuel implements ActionListener{
	
	private ModifierLocataireActuel fen_modifier_locataire;
	
	public GestionModifierLocataireActuel(ModifierLocataireActuel mla) {
		this.fen_modifier_locataire = mla;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fen_modifier_locataire.dispose();
				break;
			case "Valider" : 
				JOptionPane.showMessageDialog(this.fen_modifier_locataire,"Modification effectuée","Modifier le locataire", JOptionPane.INFORMATION_MESSAGE);
				break;
		}
	}
	
}
