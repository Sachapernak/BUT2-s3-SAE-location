package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AjouterBail;
import vue.AjouterLocataire;
import vue.AfficherLocatairesActuels;

public class GestionAjouterLocataire implements ActionListener{
	
	private AjouterLocataire fenAjouterLocataire;
	private AfficherLocatairesActuels fenAfficherLocataires;
	
	private VerificationFormatChamps formatCorrect;
	
	public GestionAjouterLocataire(AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		
		this.formatCorrect = new VerificationFormatChamps();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fenAjouterLocataire.dispose();
				break;
			case "Continuer" : 
				if (!champsRemplis(this.fenAjouterLocataire.getChampsObligatoires())) {
					this.fenAjouterLocataire.afficherMessageErreur("Il est nécessaire de remplir les champs obligatoires");
				} 
				
				String dateNaissance = this.fenAjouterLocataire.getTextFieldDateNaissance();
				String tel = this.fenAjouterLocataire.getTextFieldTel();
				
				if (!this.formatCorrect.validerDate(dateNaissance)) {
					this.fenAjouterLocataire.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
				} else {
					DaoLocataire daoLocataire = new DaoLocataire();
					try {
						Locataire loc = daoLocataire.findById(this.fenAjouterLocataire.getTextFieldIdLocataire());
						if (loc == null) {
							AjouterBail ab = new AjouterBail(this.fenAjouterLocataire, this.fenAfficherLocataires) ;
							JLayeredPane layeredPaneAjoutBail = this.fenAfficherLocataires.getLayeredPane();
							layeredPaneAjoutBail.add(ab, JLayeredPane.PALETTE_LAYER);
							ab.setVisible(true);
						}else {
							this.fenAjouterLocataire.afficherMessageErreur("Le locataire existe déjà");
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
	
	public boolean champsRemplis(List<String> champs) {
		boolean champsNonVides = true;
		for (String champ : champs) {
			System.out.println(champ);
			if (champ.equals("")) {
				champsNonVides = false;
			}
		}
		return champsNonVides;
	}

	
}
