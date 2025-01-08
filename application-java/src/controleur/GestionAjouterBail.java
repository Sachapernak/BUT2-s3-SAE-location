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
	
	private VerificationFormatChamps formatCorrect;
	
	public GestionAjouterBail(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fenAjouterBail = ab;
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		this.daoBien = new DaoBienLocatif();
		
		this.formatCorrect = new VerificationFormatChamps();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (btn instanceof JRadioButton) {
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
			}
			
		} else if (btn instanceof JButton) {
	    	JButton btnActif = (JButton) e.getSource();
	        String btnLibelle = btnActif.getText();
			
			switch (btnLibelle) {
				case "Annuler" :
					this.fenAjouterBail.dispose();
					break;
				case "Continuer" : 
					
					if (!verifierDates()) {
						this.fenAjouterBail.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
					} else if (this.fenAjouterBail.getRdbtnBailExistant().isSelected()) {
						
						if (this.fenAjouterBail.getTextFieldDateArrivee().equals("")) {
							this.fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
							break;
						}
						if(!totalPartEgalAUn()) {
							this.fenAjouterBail.afficherMessageErreur("Le total des parts n'est pas égal à 1");
						}else {
							ouvrirFenAjoutCautionnaire();
						}
					}else if(this.fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
						if (!champsRemplis(this.fenAjouterBail.getChampsObligatoiresNouveauBail())){
							this.fenAjouterBail.afficherMessageErreur("Les champs obligatoires (dotés d'une étoile) doivent être renseignés");
						} else {
							ouvrirFenAjoutCautionnaire();
						}
					}
				
					break;
				case "Vider" : 
					this.fenAjouterBail.textFieldIdBail.setText("");
					this.fenAjouterBail.textFieldDateDebut.setText("");
					this.fenAjouterBail.textFieldDateFin.setText("");		
					break;
			}
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean verifierDates() {
		List<String> champsDate = this.fenAjouterBail.getChampsDate();
		for (String champ : champsDate) {
			if (!champ.equals("")&&!this.formatCorrect.validerDate(champ)) {
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
