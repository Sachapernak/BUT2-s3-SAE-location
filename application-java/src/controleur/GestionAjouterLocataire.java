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
	
	private VerificationChamps verifChamps;
	
	public GestionAjouterLocataire(AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		
		this.verifChamps = new VerificationChamps();
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
				if (!verifChamps.champsRemplis(this.fenAjouterLocataire.getChampsObligatoires())) {
					this.fenAjouterLocataire.afficherMessageErreur("Il est nécessaire de remplir les champs obligatoires");
					break;
				} 
				
				String dateNaissance = this.fenAjouterLocataire.getTextFieldDateNaissance();
				
				if (!this.verifChamps.validerDate(dateNaissance)) {
					this.fenAjouterLocataire.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
					break;
				} 
				
				if(this.verifChamps.auMoinsUnChampRempli(this.fenAjouterLocataire.getChampsObligatoiresAdresse())) {
					String codePostal = this.fenAjouterLocataire.getTextFieldCodePostal();
					if (!codePostal.isEmpty()&& !this.verifChamps.validerCodePostal(codePostal)){
						this.fenAjouterLocataire.afficherMessageErreur("Le code postal doit etre composé de 5 entiers");
						break;
					}
					if (!this.verifChamps.champsRemplis(this.fenAjouterLocataire.getChampsObligatoiresAdresse())){
						this.fenAjouterLocataire.afficherMessageErreur("Si vous voulez saisir une adresse, \n"
								+ "vous devez impérativement saisir l'ensembles des champs obligatoires \n"
								+ "à l'exception du complément");
						break;
					}
					
				}
								
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
	}
	
	

	
}
