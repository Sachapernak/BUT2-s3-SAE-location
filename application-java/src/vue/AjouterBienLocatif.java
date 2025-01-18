package vue;


import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.GestionAjouterBienLocatif;

import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class AjouterBienLocatif extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldIdBien;
	private JTextField textFieldLoyerBase;
	private JTextField textFieldIdFiscal;
	private JTextField textFieldComplementAdr;
	private JTextField textFieldSurface;
	private JTextField textFieldNbPieces;
	private JComboBox<String> comboBoxBatiment;
	private JComboBox<String> comboBoxTypeBien;
	
	private GestionAjouterBienLocatif gestionFen;
	
	public String getTextIdBien() {
		return this.textFieldIdBien.getText();
	}
	
	public String getTextLoyerBase() {
		return this.textFieldLoyerBase.getText();
	}
	
	public String getTextIdFiscal() {
		return this.textFieldIdFiscal.getText();
	}
	
	public String getTextComplementAdr() {
		return this.textFieldComplementAdr.getText();
	}
	
	public String getTextSurface() {
		return this.textFieldSurface.getText();
	}
	
	public String getTextNbPieces() {
		return this.textFieldNbPieces.getText();
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public AjouterBienLocatif() {
		this.gestionFen = new GestionAjouterBienLocatif(this);
		setBounds(100, 100, 531, 389);
		
		JPanel panelTitre = new JPanel();
		getContentPane().add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Ajouter un bien locatif");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitre.add(lblNewLabel);
		
		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gblPanelCentre = new GridBagLayout();
		gblPanelCentre.columnWidths = new int[]{71, 0, 124, 0, 0, 0, 0, 0};
		gblPanelCentre.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblPanelCentre.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblPanelCentre.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentre.setLayout(gblPanelCentre);
		
		JLabel lblIdBatiment = new JLabel("Batiment : ");
		GridBagConstraints gbcLblIdBatiment = new GridBagConstraints();
		gbcLblIdBatiment.insets = new Insets(0, 0, 5, 5);
		gbcLblIdBatiment.anchor = GridBagConstraints.EAST;
		gbcLblIdBatiment.gridx = 1;
		gbcLblIdBatiment.gridy = 1;
		panelCentre.add(lblIdBatiment, gbcLblIdBatiment);
		
		comboBoxBatiment = new JComboBox<>();
		GridBagConstraints gbcComboBoxBatiment = new GridBagConstraints();
		gbcComboBoxBatiment.insets = new Insets(0, 0, 5, 5);
		gbcComboBoxBatiment.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxBatiment.gridx = 2;
		gbcComboBoxBatiment.gridy = 1;
		panelCentre.add(comboBoxBatiment, gbcComboBoxBatiment);
		this.gestionFen.remplirComboBoxIdBat();
		
		JLabel lblTypeLogement = new JLabel("Type :");
		GridBagConstraints gbcLblTypeLogement = new GridBagConstraints();
		gbcLblTypeLogement.anchor = GridBagConstraints.EAST;
		gbcLblTypeLogement.insets = new Insets(0, 0, 5, 5);
		gbcLblTypeLogement.gridx = 1;
		gbcLblTypeLogement.gridy = 3;
		panelCentre.add(lblTypeLogement, gbcLblTypeLogement);
		
		comboBoxTypeBien = new JComboBox<>();
		GridBagConstraints gbcComboBoxTypeBien = new GridBagConstraints();
		gbcComboBoxTypeBien.insets = new Insets(0, 0, 5, 5);
		gbcComboBoxTypeBien.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxTypeBien.gridx = 2;
		gbcComboBoxTypeBien.gridy = 3;
		panelCentre.add(comboBoxTypeBien, gbcComboBoxTypeBien);
		this.gestionFen.remplirComboBoxTypeBienLoc();
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut = new GridBagConstraints();
		gbcHorizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut.gridx = 2;
		gbcHorizontalStrut.gridy = 4;
		panelCentre.add(horizontalStrut, gbcHorizontalStrut);
		
		JLabel lblIdBien = new JLabel("Identifiant logement* :");
		lblIdBien.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbcLblIdBien = new GridBagConstraints();
		gbcLblIdBien.anchor = GridBagConstraints.EAST;
		gbcLblIdBien.insets = new Insets(0, 0, 5, 5);
		gbcLblIdBien.gridx = 1;
		gbcLblIdBien.gridy = 5;
		panelCentre.add(lblIdBien, gbcLblIdBien);
		
		textFieldIdBien = new JTextField();
		GridBagConstraints gbcTextFieldIdBien = new GridBagConstraints();
		gbcTextFieldIdBien.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldIdBien.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldIdBien.gridx = 2;
		gbcTextFieldIdBien.gridy = 5;
		panelCentre.add(textFieldIdBien, gbcTextFieldIdBien);
		textFieldIdBien.setColumns(10);
		
		Component horizontalStrut1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut1 = new GridBagConstraints();
		gbcHorizontalStrut1.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut1.gridx = 2;
		gbcHorizontalStrut1.gridy = 6;
		panelCentre.add(horizontalStrut1, gbcHorizontalStrut1);
		
		JLabel lblLoyerBase = new JLabel("Loyer de base* : ");
		lblLoyerBase.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbcLblLoyerBase = new GridBagConstraints();
		gbcLblLoyerBase.anchor = GridBagConstraints.EAST;
		gbcLblLoyerBase.insets = new Insets(0, 0, 5, 5);
		gbcLblLoyerBase.gridx = 1;
		gbcLblLoyerBase.gridy = 7;
		panelCentre.add(lblLoyerBase, gbcLblLoyerBase);
		
		textFieldLoyerBase = new JTextField();
		GridBagConstraints gbcTextFieldLoyerBase = new GridBagConstraints();
		gbcTextFieldLoyerBase.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldLoyerBase.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldLoyerBase.gridx = 2;
		gbcTextFieldLoyerBase.gridy = 7;
		panelCentre.add(textFieldLoyerBase, gbcTextFieldLoyerBase);
		textFieldLoyerBase.setColumns(10);
		
		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut2 = new GridBagConstraints();
		gbcHorizontalStrut2.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut2.gridx = 2;
		gbcHorizontalStrut2.gridy = 8;
		panelCentre.add(horizontalStrut2, gbcHorizontalStrut2);
		
		JLabel lblIdFiscal = new JLabel("Identifiant fiscal :");
		GridBagConstraints gbcLblIdFiscal = new GridBagConstraints();
		gbcLblIdFiscal.anchor = GridBagConstraints.EAST;
		gbcLblIdFiscal.insets = new Insets(0, 0, 5, 5);
		gbcLblIdFiscal.gridx = 1;
		gbcLblIdFiscal.gridy = 9;
		panelCentre.add(lblIdFiscal, gbcLblIdFiscal);
		
		textFieldIdFiscal = new JTextField();
		GridBagConstraints gbcTextFieldIdFiscal = new GridBagConstraints();
		gbcTextFieldIdFiscal.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldIdFiscal.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldIdFiscal.gridx = 2;
		gbcTextFieldIdFiscal.gridy = 9;
		panelCentre.add(textFieldIdFiscal, gbcTextFieldIdFiscal);
		textFieldIdFiscal.setColumns(10);
		
		Component horizontalStrut3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut3 = new GridBagConstraints();
		gbcHorizontalStrut3.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut3.gridx = 2;
		gbcHorizontalStrut3.gridy = 10;
		panelCentre.add(horizontalStrut3, gbcHorizontalStrut3);
		
		JLabel lblComplementAdr = new JLabel("Complement d'adresse :");
		GridBagConstraints gbcLblComplementAdr = new GridBagConstraints();
		gbcLblComplementAdr.anchor = GridBagConstraints.EAST;
		gbcLblComplementAdr.insets = new Insets(0, 0, 5, 5);
		gbcLblComplementAdr.gridx = 1;
		gbcLblComplementAdr.gridy = 11;
		panelCentre.add(lblComplementAdr, gbcLblComplementAdr);
		
		textFieldComplementAdr = new JTextField();
		GridBagConstraints gbcTextFieldComplementAdr = new GridBagConstraints();
		gbcTextFieldComplementAdr.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldComplementAdr.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldComplementAdr.gridx = 2;
		gbcTextFieldComplementAdr.gridy = 11;
		panelCentre.add(textFieldComplementAdr, gbcTextFieldComplementAdr);
		textFieldComplementAdr.setColumns(10);
		
		Component horizontalStrut4 = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut4 = new GridBagConstraints();
		gbcHorizontalStrut4.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut4.gridx = 2;
		gbcHorizontalStrut4.gridy = 12;
		panelCentre.add(horizontalStrut4, gbcHorizontalStrut4);
		
		JLabel lblSurface = new JLabel("Surface* :");
		GridBagConstraints gbcLblSurface = new GridBagConstraints();
		gbcLblSurface.anchor = GridBagConstraints.EAST;
		gbcLblSurface.insets = new Insets(0, 0, 5, 5);
		gbcLblSurface.gridx = 1;
		gbcLblSurface.gridy = 13;
		panelCentre.add(lblSurface, gbcLblSurface);
		
		textFieldSurface = new JTextField();
		GridBagConstraints gbcTextFieldSurface = new GridBagConstraints();
		gbcTextFieldSurface.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldSurface.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldSurface.gridx = 2;
		gbcTextFieldSurface.gridy = 13;
		panelCentre.add(textFieldSurface, gbcTextFieldSurface);
		textFieldSurface.setColumns(10);
		
		Component horizontalStrut5 = Box.createHorizontalStrut(20);
		GridBagConstraints gbcHorizontalStrut5 = new GridBagConstraints();
		gbcHorizontalStrut5.insets = new Insets(0, 0, 5, 5);
		gbcHorizontalStrut5.gridx = 2;
		gbcHorizontalStrut5.gridy = 14;
		panelCentre.add(horizontalStrut5, gbcHorizontalStrut5);
		
		JLabel lblNbPieces = new JLabel("Nombre de pièces* :");
		GridBagConstraints gbcLblNbPieces = new GridBagConstraints();
		gbcLblNbPieces.anchor = GridBagConstraints.EAST;
		gbcLblNbPieces.insets = new Insets(0, 0, 5, 5);
		gbcLblNbPieces.gridx = 1;
		gbcLblNbPieces.gridy = 15;
		panelCentre.add(lblNbPieces, gbcLblNbPieces);
		
		textFieldNbPieces = new JTextField();
		GridBagConstraints gbcTextFieldNbPieces = new GridBagConstraints();
		gbcTextFieldNbPieces.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldNbPieces.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldNbPieces.gridx = 2;
		gbcTextFieldNbPieces.gridy = 15;
		panelCentre.add(textFieldNbPieces, gbcTextFieldNbPieces);
		textFieldNbPieces.setColumns(10);
		
		JButton btnAjouter = new JButton("Ajouter");
		GridBagConstraints gbcBtnAjouter = new GridBagConstraints();
		gbcBtnAjouter.insets = new Insets(0, 0, 0, 5);
		gbcBtnAjouter.gridx = 4;
		gbcBtnAjouter.gridy = 16;
		panelCentre.add(btnAjouter, gbcBtnAjouter);
		btnAjouter.addActionListener(this.gestionFen);
		
		JButton btnAnnuler = new JButton("Annuler");
		GridBagConstraints gbcBtnAnnuler = new GridBagConstraints();
		gbcBtnAnnuler.insets = new Insets(0, 0, 0, 5);
		gbcBtnAnnuler.gridx = 5;
		gbcBtnAnnuler.gridy = 16;
		panelCentre.add(btnAnnuler, gbcBtnAnnuler);
		btnAnnuler.addActionListener(this.gestionFen);

	}
	
	// -------------------------------------------------------------------------
    // Méthodes d'aide pour le contrôleur
    // -------------------------------------------------------------------------

    /**
     * Retourne la liste des champs obligatoires pour un bien locatif.
     * @return liste de chaînes représentant les valeurs des champs obligatoires
     */
    public List<String> getChampsObligatoiresBienLocatif() {
        List<String> res = new ArrayList<>();
        res.add(getTextIdBien());
        res.add(getTextLoyerBase());
        res.add(getTextSurface());
        res.add(getTextNbPieces());
        return res;
    }
    
    /**
     * fonction permettant d'afficher un message sur la fenetre
     */

	public void afficherMessage(String message, String titre, int typeMessage) {
	    JOptionPane.showMessageDialog(this, message, titre, typeMessage);
	}
	
	/**
     * Remplit la combo des batiments.
     * @param locataires la liste d'IDs des batiments
     */
    public void setComboBoxBatiment(List<String> idBatiments) {
    	comboBoxBatiment.removeAllItems();
        for(String type : idBatiments) {
        	comboBoxBatiment.addItem(type);
        }
    }
    
    /**
     * @return la valeur sélectionnée dans la combo 'batiment'
     */
    public String getIdBatimentCombo() {
        return String.valueOf(comboBoxBatiment.getSelectedItem());
    }
    
	/**
     * Remplit la combo des typeDeBien.
     * @param locataires la liste des types de bien
     */
    public void setComboBoxTypeBien(List<String> typesBiens) {
    	comboBoxTypeBien.removeAllItems();
        for(String type : typesBiens) {
        	comboBoxTypeBien.addItem(type);
        }
    }
    
    /**
     * @return la valeur sélectionnée dans la combo 'type'
     */
    public String getTypeBienCombo() {
        return String.valueOf(comboBoxTypeBien.getSelectedItem());
    }

}
