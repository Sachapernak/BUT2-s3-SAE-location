package controleur;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import modele.BienLocatif;
import modele.dao.DaoBienLocatif;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

public class GestionAjouterBail implements ActionListener{
	
	private AjouterBail fenAjouterBail;
	private AfficherLocatairesActuels fenAfficherLocataires;
	private AjouterLocataire fenAjouterLocataire;
	private DaoBienLocatif daoBien;
	
	private VerificationChamps verifChamps;
	
	public GestionAjouterBail(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fenAjouterBail = ab;
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		this.daoBien = new DaoBienLocatif();
		
		this.verifChamps = new VerificationChamps();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (btn instanceof JRadioButton) {
	    	actionPerformedRadio(e);
			
		} else if (btn instanceof JButton) {
	    	actionPerformedJbutton(e);
		}
	}

	// TODO : A tester
	private void actionPerformedJbutton(ActionEvent e) {
	    JButton sourceButton = (JButton) e.getSource();
	    String buttonLabel = sourceButton.getText();

	    switch (buttonLabel) {
	        case "Annuler":
	            handleAnnuler();
	            break;
	        case "Continuer":
	            handleContinuer();
	            break;
	        case "Vider":
	            viderTextFields();
	            break;
	        default:
	            // Aucun traitement pour les autres boutons
	            break;
	    }
	}

	/**
	 * Gère l'action sur le bouton "Annuler" en fermant la fenêtre d'ajout de bail.
	 */
	private void handleAnnuler() {
	    fenAjouterBail.dispose();
	}

	/**
	 * Gère l'action sur le bouton "Continuer".
	 * Valide les dates, puis délègue le traitement selon le type de bail sélectionné.
	 */
	private void handleContinuer() {
	    // Vérification du format des dates
	    if (!verifierDates()) {
	        fenAjouterBail.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
	        return;
	    }

	    // Traitement selon le type de bail sélectionné
	    if (fenAjouterBail.getRdbtnBailExistant().isSelected()) {
	        handleBailExistant();
	    } else if (fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
	        handleNouveauBail();
	    }
	}

	/**
	 * Gère le cas d'un bail existant lors du clic sur "Continuer".
	 * Vérifie les champs spécifiques et le total des parts avant d'ouvrir la fenêtre d'ajout de cautionnaire.
	 */
	private void handleBailExistant() {
	    // Vérification que la date d'arrivée est renseignée
	    if (fenAjouterBail.getStringTextFieldDateArrivee().equals("")) {
	        fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
	        return;
	    }

	    // Vérification que le total des parts est égal à 1
	    if (!totalPartEgalAUn()) {
	        fenAjouterBail.afficherMessageErreur("Le total des parts n'est pas égal à 1");
	        return;
	    }

	    // Ouverture de la fenêtre d'ajout de cautionnaire si toutes les vérifications sont passées
	    ouvrirFenAjoutCautionnaire();
	}

	/**
	 * Gère le cas d'un nouveau bail lors du clic sur "Continuer".
	 * Vérifie que tous les champs obligatoires pour un nouveau bail sont remplis avant d'ouvrir la fenêtre d'ajout de cautionnaire.
	 */
	private void handleNouveauBail() {
	    if (!verifChamps.champsRemplis(fenAjouterBail.getChampsObligatoiresNouveauBail())) {
	        fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
	        return;
	    }

	    ouvrirFenAjoutCautionnaire();
	}



	private void viderTextFields() {
		// TODO : Changer en un fen.setTextIdBail etc
		this.fenAjouterBail.getTextFieldIdBail().setText("");
		this.fenAjouterBail.getTextFieldDateDebut().setText("");
		this.fenAjouterBail.getTextFieldDateFin().setText("");
	}


	private void actionPerformedRadio(ActionEvent e) {
		JRadioButton rdBtnActif = (JRadioButton) e.getSource();
		String rdBtnLibelle = rdBtnActif.getText();
		CardLayout cd = this.fenAjouterBail.getCardLayout();
		JPanel p = this.fenAjouterBail.getPanelAssocierBail();
		switch (rdBtnLibelle) {
			case "Créer un nouveau bail" :
				cd.show(p, "nouveauBail");
				break;
			case "Rattacher à un bail existant" : 
				cd.show(p, "bauxExistants");
				break;
			default:
				break;
		}
	}

	private void ouvrirFenAjoutCautionnaire() {
		AjouterCautionnaire ac = new AjouterCautionnaire(this.fenAjouterBail, this.fenAjouterLocataire, this.fenAfficherLocataires) ;
		JLayeredPane layeredPaneAjoutCautionnaire = this.fenAfficherLocataires.getLayeredPane();
		layeredPaneAjoutCautionnaire.add(ac, JLayeredPane.PALETTE_LAYER);
		ac.setVisible(true);
	}
	
	public void initialiserDateDebut(JTextField textFieldDateDebut) {
        LocalDate dateActelle = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateActelle.format(formatter);
        textFieldDateDebut.setText(formattedDate);
    }
	
	public void remplirJComboBoxBatiment(JComboBox<String> comboBoxBiens) {
		try {
			List<BienLocatif> biens = daoBien.findAll();
			for (BienLocatif bien : biens) {
	            comboBoxBiens.addItem(bien.getIdentifiantLogement());
	        }
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
	}
	
	public boolean verifierDates() {
		List<String> champsDate = this.fenAjouterBail.getChampsDate();
		for (String champ : champsDate) {
			if (!champ.equals("")&&!this.verifChamps.validerDate(champ)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean totalPartEgalAUn() {
		JTable tableParts = this.fenAjouterBail.getTablePartsLoyer();
		int indexLastLigne = tableParts.getRowCount() - 1; 
		float totalParts = (float) tableParts.getValueAt(indexLastLigne,1);
		
		return totalParts == 1F;
	}

}
