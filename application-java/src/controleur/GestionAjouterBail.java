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
	    	JButton btnActif = (JButton) e.getSource();
	        String btnLibelle = btnActif.getText();
			
			switch (btnLibelle) {
				case "Annuler" :
					this.fenAjouterBail.dispose();
					break;
				case "Continuer" : 
					if (!gererErreurs()) {
						ouvrirFenAjoutCautionnaire();
					}
					break;
				case "Vider" : 
					this.fenAjouterBail.textFieldIdBail.setText("");
					this.fenAjouterBail.textFieldDateDebut.setText("");
					this.fenAjouterBail.textFieldDateFin.setText("");		
					break;
			}

		
	}
	
	private boolean gererErreurs() {
		boolean erreurTrouvee = false;
		
		if (!verifChamps.verifierDates(this.fenAjouterBail.getChampsDate())) {
			this.fenAjouterBail.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
		} else if (this.fenAjouterBail.getRdbtnBailExistant().isSelected()) {
			
			if (this.fenAjouterBail.getTextFieldDateArrivee().isEmpty()) {
				this.fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
				erreurTrouvee = true;
			}
			if(!totalPartEgalAUn()) {
				this.fenAjouterBail.afficherMessageErreur("Le total des parts n'est pas égal à 1");
				erreurTrouvee = true;
			}
		}else if(this.fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
			if (!this.verifChamps.champsRemplis(this.fenAjouterBail.getChampsObligatoiresNouveauBail())){
				this.fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
				erreurTrouvee = true;
			} 
		}
		return erreurTrouvee;
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
	
	public boolean totalPartEgalAUn() {
		JTable tableParts = this.fenAjouterBail.getTablePartsLoyer();
		int indexLastLigne = tableParts.getRowCount() - 1; 
		float totalParts = (float) tableParts.getValueAt(indexLastLigne,1);
		
		return totalParts == 1F;
	}
	

}
