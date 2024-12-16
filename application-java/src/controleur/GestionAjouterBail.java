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
import javax.swing.JTextField;

import modele.Batiment;
import modele.dao.DaoBatiment;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

public class GestionAjouterBail implements ActionListener{
	
	private AjouterBail fen_ajouter_bail;
	private AfficherLocatairesActuels fen_afficher_locataires;
	private AjouterLocataire fen_ajouter_locataire;
	private DaoBatiment daoBatiment;
	
	public GestionAjouterBail(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
		this.fen_ajouter_bail = ab;
		this.fen_ajouter_locataire = al;
		this.fen_afficher_locataires = afl;
		this.daoBatiment = new DaoBatiment();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (btn instanceof JRadioButton) {
	    	JRadioButton rdBtnActif = (JRadioButton) e.getSource();
	        String rdBtnLibelle = rdBtnActif.getText();
			CardLayout cd = this.fen_ajouter_bail.getCardLayout();
			JPanel p = this.fen_ajouter_bail.getPanelAssocierBail();
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
					this.fen_ajouter_bail.dispose();
					break;
				case "Continuer" : 
					AjouterCautionnaire ac = new AjouterCautionnaire(this.fen_ajouter_bail, this.fen_ajouter_locataire, this.fen_afficher_locataires) ;
					JLayeredPane layeredPaneAjoutCautionnaire = this.fen_afficher_locataires.getLayeredPane();
					layeredPaneAjoutCautionnaire.add(ac, JLayeredPane.PALETTE_LAYER);
					ac.setVisible(true);
					break;
				case "Vider" : 
					this.fen_ajouter_bail.textFieldIdBail.setText("");
					this.fen_ajouter_bail.textFieldDateDebut.setText("");
					this.fen_ajouter_bail.textFieldDateFin.setText("");		
					break;
			}
		}
		

	}
	
	public void initialiserDateDebut(JTextField textFieldDateDebut) {
        LocalDate dateActelle = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        String formattedDate = dateActelle.format(formatter);
        textFieldDateDebut.setText(formattedDate);
    }
	
	public void remplirJComboBoxBatiment(JComboBox<String> comboBoxBatiment) {
		try {
			List<Batiment> batiments = daoBatiment.findAll();
			for (Batiment batiment : batiments) {
	            comboBoxBatiment.addItem(batiment.getIdBat());
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
