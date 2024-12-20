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
import vue.AjouterBail;
import vue.AjouterLocataire;
import vue.AfficherLocatairesActuels;

public class GestionAjouterLocataire implements ActionListener{
	
	private AjouterLocataire fen_ajouter_locataire;
	private AfficherLocatairesActuels fen_afficher_locataires;
	
	public GestionAjouterLocataire(AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fen_ajouter_locataire = al;
		this.fen_afficher_locataires = afl;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fen_ajouter_locataire.dispose();
				break;
			case "Continuer" : 
				if (!champsObligatoiresRemplis()) {
					 JOptionPane.showMessageDialog(this.fen_ajouter_locataire,"Il est nécessaire de remplir les champs obligatoires","Formulaire incomplet", JOptionPane.ERROR_MESSAGE);
				} else {
					DaoLocataire daoLocataire = new DaoLocataire();
					try {
						Locataire loc = daoLocataire.findById(this.fen_ajouter_locataire.getTextFieldId().getText());
						if (loc == null) {
							AjouterBail ab = new AjouterBail(this.fen_ajouter_locataire, this.fen_afficher_locataires) ;
							JLayeredPane layeredPaneAjoutBail = this.fen_afficher_locataires.getLayeredPane();
							layeredPaneAjoutBail.add(ab, JLayeredPane.PALETTE_LAYER);
							ab.setVisible(true);
						}else {
						       JOptionPane.showMessageDialog(this.fen_ajouter_locataire,"Le locataire existe déjà","Locataire existant", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
						
				break;
		}
	}
	
	public boolean champsObligatoiresRemplis() {
	    return (!this.fen_ajouter_locataire.getTextFieldId().getText().isEmpty()
	            && !this.fen_ajouter_locataire.getTextFieldNom().getText().isEmpty()
	            && !this.fen_ajouter_locataire.getTextFieldPrenom().getText().isEmpty()
	            && !this.fen_ajouter_locataire.getTextFieldDateNaissance().getText().isEmpty()
	            && !this.fen_ajouter_locataire.getTextFieldLieuNaissance().getText().isEmpty());
	}

	
}
