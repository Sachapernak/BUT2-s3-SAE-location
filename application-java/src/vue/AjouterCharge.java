package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAjouterCharge;

import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AjouterCharge extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    
    // --- On transforme ces labels en champs d'instance pour pouvoir y accéder dans les setters d'affichage :
    private JLabel lblTitre;
    private JLabel lblNumeroDoc;
    private JLabel lblDateDocument;
    private JLabel lblType;
    private JLabel lblLienFichier;
    private JLabel lblEntreprise;
    private JLabel lblBat;
    private JLabel lblBienLocatif;
    private JLabel lblRecupLoc;
    private JLabel lblIDChargeIndex;
    private JLabel lblOu;
    private JLabel lblCout;
    private JLabel lblValIndex;
    private JLabel lblLocataire;
    private JLabel lblAssu;
    
    private JTextField textFieldNumDoc;
    private JTextField textFieldDateDoc;
    private JTable tableLogements;
    private JTextField textFieldIDCharge;
    private JTextField textFieldValeurAncienIndex;
    private JComboBox<String> comboBoxType;
    private JTextArea textAreaLien;
    private JComboBox<String> comboBoxEntreprise;
    private JComboBox<String> comboBoxBat;
    private JScrollPane scrollPane;
    private JCheckBox chckbxRecupLoc;
    private JComboBox<String> comboBoxChoixCharge;
    private JSpinner spinnerCoutVarUnit;
    private JSpinner spinnerCoutVarAbon;
    private JSpinner spinnerNouveauIndex;
    private JComboBox<String> comboBoxLocataire;
    private JComboBox<String> comboBoxAssu;
    
    // Boutons
    private JButton okButton;
    private JButton annulerButton; // renommé depuis 'cancelButton'
    
    private GestionAjouterCharge gest;

    /**
     * Launch the application (test).
     */
    public static void main(String[] args) {
        try {
            AjouterCharge dialog = new AjouterCharge();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AjouterCharge() {
        
        this.gest = new GestionAjouterCharge(this);
        
        setBounds(100, 100, 548, 620);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{180, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        
        lblTitre = new JLabel("Ajout de charge");
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblTitre = new GridBagConstraints();
        gbc_lblTitre.insets = new Insets(0, 0, 5, 0);
        gbc_lblTitre.gridx = 0;
        gbc_lblTitre.gridy = 0;
        contentPanel.add(lblTitre, gbc_lblTitre);
        
        JPanel panelLeft = new JPanel();
        GridBagConstraints gbc_panelLeft = new GridBagConstraints();
        gbc_panelLeft.fill = GridBagConstraints.BOTH;
        gbc_panelLeft.gridx = 0;
        gbc_panelLeft.gridy = 1;
        contentPanel.add(panelLeft, gbc_panelLeft);
        GridBagLayout gbl_panelLeft = new GridBagLayout();
        gbl_panelLeft.columnWidths = new int[]{155, 160, 0, 160, 0, 0};
        gbl_panelLeft.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 100, 16, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panelLeft.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panelLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelLeft.setLayout(gbl_panelLeft);
        
        lblNumeroDoc = new JLabel("Numero de document :");
        lblNumeroDoc.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblNumeroDoc = new GridBagConstraints();
        gbc_lblNumeroDoc.anchor = GridBagConstraints.EAST;
        gbc_lblNumeroDoc.insets = new Insets(0, 0, 5, 5);
        gbc_lblNumeroDoc.gridx = 0;
        gbc_lblNumeroDoc.gridy = 0;
        panelLeft.add(lblNumeroDoc, gbc_lblNumeroDoc);
        
        textFieldNumDoc = new JTextField();
        GridBagConstraints gbc_textFieldNumDoc = new GridBagConstraints();
        gbc_textFieldNumDoc.gridwidth = 3;
        gbc_textFieldNumDoc.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNumDoc.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNumDoc.gridx = 1;
        gbc_textFieldNumDoc.gridy = 0;
        panelLeft.add(textFieldNumDoc, gbc_textFieldNumDoc);
        textFieldNumDoc.setColumns(10);
        
        lblDateDocument = new JLabel("Date du document :");
        lblDateDocument.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblDateDocument = new GridBagConstraints();
        gbc_lblDateDocument.anchor = GridBagConstraints.EAST;
        gbc_lblDateDocument.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateDocument.gridx = 0;
        gbc_lblDateDocument.gridy = 1;
        panelLeft.add(lblDateDocument, gbc_lblDateDocument);
        
        textFieldDateDoc = new JTextField();
        GridBagConstraints gbc_textFieldDateDoc = new GridBagConstraints();
        gbc_textFieldDateDoc.gridwidth = 3;
        gbc_textFieldDateDoc.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldDateDoc.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldDateDoc.gridx = 1;
        gbc_textFieldDateDoc.gridy = 1;
        panelLeft.add(textFieldDateDoc, gbc_textFieldDateDoc);
        textFieldDateDoc.setColumns(10);
        
        lblType = new JLabel("Type :");
        lblType.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.anchor = GridBagConstraints.EAST;
        gbc_lblType.insets = new Insets(0, 0, 5, 5);
        gbc_lblType.gridx = 0;
        gbc_lblType.gridy = 2;
        panelLeft.add(lblType, gbc_lblType);
        
        comboBoxType = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxType = new GridBagConstraints();
        gbc_comboBoxType.gridwidth = 3;
        gbc_comboBoxType.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxType.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxType.gridx = 1;
        gbc_comboBoxType.gridy = 2;
        panelLeft.add(comboBoxType, gbc_comboBoxType);
        
        lblLienFichier = new JLabel("Lien vers le fichier :");
        lblLienFichier.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblLienFichier = new GridBagConstraints();
        gbc_lblLienFichier.fill = GridBagConstraints.VERTICAL;
        gbc_lblLienFichier.anchor = GridBagConstraints.EAST;
        gbc_lblLienFichier.insets = new Insets(0, 0, 5, 5);
        gbc_lblLienFichier.gridx = 0;
        gbc_lblLienFichier.gridy = 3;
        panelLeft.add(lblLienFichier, gbc_lblLienFichier);
        
        textAreaLien = new JTextArea();
        textAreaLien.setLineWrap(true);
        textAreaLien.setWrapStyleWord(true);
        textAreaLien.setRows(2);
        GridBagConstraints gbc_textAreaLien = new GridBagConstraints();
        gbc_textAreaLien.gridwidth = 3;
        gbc_textAreaLien.insets = new Insets(0, 0, 5, 5);
        gbc_textAreaLien.fill = GridBagConstraints.BOTH;
        gbc_textAreaLien.gridx = 1;
        gbc_textAreaLien.gridy = 3;
        panelLeft.add(textAreaLien, gbc_textAreaLien);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 0;
        gbc_verticalStrut.gridy = 4;
        panelLeft.add(verticalStrut, gbc_verticalStrut);
        
        lblEntreprise = new JLabel("Entreprise :");
        lblEntreprise.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblEntreprise = new GridBagConstraints();
        gbc_lblEntreprise.anchor = GridBagConstraints.EAST;
        gbc_lblEntreprise.insets = new Insets(0, 0, 5, 5);
        gbc_lblEntreprise.gridx = 0;
        gbc_lblEntreprise.gridy = 5;
        panelLeft.add(lblEntreprise, gbc_lblEntreprise);
        
        comboBoxEntreprise = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxEntreprise = new GridBagConstraints();
        gbc_comboBoxEntreprise.gridwidth = 3;
        gbc_comboBoxEntreprise.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxEntreprise.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxEntreprise.gridx = 1;
        gbc_comboBoxEntreprise.gridy = 5;
        panelLeft.add(comboBoxEntreprise, gbc_comboBoxEntreprise);
        
        lblBat = new JLabel("Batiment :");
        lblBat.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblBat = new GridBagConstraints();
        gbc_lblBat.anchor = GridBagConstraints.EAST;
        gbc_lblBat.insets = new Insets(0, 0, 5, 5);
        gbc_lblBat.gridx = 0;
        gbc_lblBat.gridy = 6;
        panelLeft.add(lblBat, gbc_lblBat);
        
        comboBoxBat = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxBat = new GridBagConstraints();
        gbc_comboBoxBat.gridwidth = 3;
        gbc_comboBoxBat.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxBat.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxBat.gridx = 1;
        gbc_comboBoxBat.gridy = 6;
        panelLeft.add(comboBoxBat, gbc_comboBoxBat);
        
        lblBienLocatif = new JLabel("Bien locatifs concernés :");
        lblBienLocatif.setHorizontalAlignment(SwingConstants.TRAILING);
        lblBienLocatif.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbc_lblBienLocatif = new GridBagConstraints();
        gbc_lblBienLocatif.anchor = GridBagConstraints.EAST;
        gbc_lblBienLocatif.insets = new Insets(0, 0, 5, 5);
        gbc_lblBienLocatif.gridx = 0;
        gbc_lblBienLocatif.gridy = 7;
        panelLeft.add(lblBienLocatif, gbc_lblBienLocatif);
        
        JPanel panelInterne = new JPanel();
        panelInterne.setMaximumSize(new Dimension(32767, 120));
        panelInterne.setPreferredSize(new Dimension(10, 80));
        GridBagConstraints gbc_panelInterne = new GridBagConstraints();
        gbc_panelInterne.gridwidth = 3;
        gbc_panelInterne.insets = new Insets(0, 0, 5, 5);
        gbc_panelInterne.fill = GridBagConstraints.BOTH;
        gbc_panelInterne.gridx = 1;
        gbc_panelInterne.gridy = 7;
        panelLeft.add(panelInterne, gbc_panelInterne);
        panelInterne.setLayout(new BorderLayout(0, 0));
        
        scrollPane = new JScrollPane();
        panelInterne.add(scrollPane, BorderLayout.CENTER);
        
        tableLogements = new JTable();
        tableLogements.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null},
            },
            new String[] {
                "ID du bien", "Part des charges"
            }
        ) {
            private static final long serialVersionUID = 1L;
            Class[] columnTypes = new Class[] {
                String.class, Float.class
            };
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });
        scrollPane.setViewportView(tableLogements);
        
        lblRecupLoc = new JLabel("Récuperable locataire :");
        GridBagConstraints gbc_lblRecupLoc = new GridBagConstraints();
        gbc_lblRecupLoc.anchor = GridBagConstraints.EAST;
        gbc_lblRecupLoc.insets = new Insets(0, 0, 5, 5);
        gbc_lblRecupLoc.gridx = 0;
        gbc_lblRecupLoc.gridy = 8;
        panelLeft.add(lblRecupLoc, gbc_lblRecupLoc);
        
        chckbxRecupLoc = new JCheckBox("");
        GridBagConstraints gbc_chckbxRecupLoc = new GridBagConstraints();
        gbc_chckbxRecupLoc.anchor = GridBagConstraints.WEST;
        gbc_chckbxRecupLoc.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxRecupLoc.gridx = 1;
        gbc_chckbxRecupLoc.gridy = 8;
        panelLeft.add(chckbxRecupLoc, gbc_chckbxRecupLoc);
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
        gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_1.gridx = 0;
        gbc_verticalStrut_1.gridy = 9;
        panelLeft.add(verticalStrut_1, gbc_verticalStrut_1);
        
        lblIDChargeIndex = new JLabel("ID charge :");
        lblIDChargeIndex.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblIDChargeIndex = new GridBagConstraints();
        gbc_lblIDChargeIndex.anchor = GridBagConstraints.EAST;
        gbc_lblIDChargeIndex.insets = new Insets(0, 0, 5, 5);
        gbc_lblIDChargeIndex.gridx = 0;
        gbc_lblIDChargeIndex.gridy = 10;
        panelLeft.add(lblIDChargeIndex, gbc_lblIDChargeIndex);
        
        textFieldIDCharge = new JTextField();
        GridBagConstraints gbc_textFieldIDCharge = new GridBagConstraints();
        gbc_textFieldIDCharge.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldIDCharge.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldIDCharge.gridx = 1;
        gbc_textFieldIDCharge.gridy = 10;
        panelLeft.add(textFieldIDCharge, gbc_textFieldIDCharge);
        textFieldIDCharge.setColumns(10);
        
        lblOu = new JLabel("Ou");
        GridBagConstraints gbc_lblOu = new GridBagConstraints();
        gbc_lblOu.anchor = GridBagConstraints.EAST;
        gbc_lblOu.insets = new Insets(0, 0, 5, 5);
        gbc_lblOu.gridx = 2;
        gbc_lblOu.gridy = 10;
        panelLeft.add(lblOu, gbc_lblOu);
        
        comboBoxChoixCharge = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxChoixCharge = new GridBagConstraints();
        gbc_comboBoxChoixCharge.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxChoixCharge.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxChoixCharge.gridx = 3;
        gbc_comboBoxChoixCharge.gridy = 10;
        panelLeft.add(comboBoxChoixCharge, gbc_comboBoxChoixCharge);
        
        lblCout = new JLabel("Cout unitaire | abonnement :");
        lblCout.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblCout = new GridBagConstraints();
        gbc_lblCout.anchor = GridBagConstraints.EAST;
        gbc_lblCout.insets = new Insets(0, 0, 5, 5);
        gbc_lblCout.gridx = 0;
        gbc_lblCout.gridy = 11;
        panelLeft.add(lblCout, gbc_lblCout);
        
        spinnerCoutVarUnit = new JSpinner();
        spinnerCoutVarUnit.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbc_spinnerCoutVarUnit = new GridBagConstraints();
        gbc_spinnerCoutVarUnit.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerCoutVarUnit.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerCoutVarUnit.gridx = 1;
        gbc_spinnerCoutVarUnit.gridy = 11;
        panelLeft.add(spinnerCoutVarUnit, gbc_spinnerCoutVarUnit);
        
        spinnerCoutVarAbon = new JSpinner();
        spinnerCoutVarAbon.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbc_spinnerCoutVarAbon = new GridBagConstraints();
        gbc_spinnerCoutVarAbon.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerCoutVarAbon.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerCoutVarAbon.gridx = 3;
        gbc_spinnerCoutVarAbon.gridy = 11;
        panelLeft.add(spinnerCoutVarAbon, gbc_spinnerCoutVarAbon);
        
        lblValIndex = new JLabel("Nouveau | Ancien Index :");
        GridBagConstraints gbc_lblValIndex = new GridBagConstraints();
        gbc_lblValIndex.anchor = GridBagConstraints.EAST;
        gbc_lblValIndex.insets = new Insets(0, 0, 5, 5);
        gbc_lblValIndex.gridx = 0;
        gbc_lblValIndex.gridy = 12;
        panelLeft.add(lblValIndex, gbc_lblValIndex);
        
        spinnerNouveauIndex = new JSpinner();
        spinnerNouveauIndex.setModel(new SpinnerNumberModel(Double.valueOf(0), null, null, Double.valueOf(5)));
        GridBagConstraints gbc_spinnerNouveauIndex = new GridBagConstraints();
        gbc_spinnerNouveauIndex.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerNouveauIndex.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerNouveauIndex.gridx = 1;
        gbc_spinnerNouveauIndex.gridy = 12;
        panelLeft.add(spinnerNouveauIndex, gbc_spinnerNouveauIndex);
        
        textFieldValeurAncienIndex = new JTextField();
        textFieldValeurAncienIndex.setEditable(false);
        GridBagConstraints gbc_textFieldValeurAncienIndex = new GridBagConstraints();
        gbc_textFieldValeurAncienIndex.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValeurAncienIndex.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldValeurAncienIndex.gridx = 3;
        gbc_textFieldValeurAncienIndex.gridy = 12;
        panelLeft.add(textFieldValeurAncienIndex, gbc_textFieldValeurAncienIndex);
        textFieldValeurAncienIndex.setColumns(10);
        
        lblLocataire = new JLabel("Quittance pour :");
        GridBagConstraints gbc_lblLocataire = new GridBagConstraints();
        gbc_lblLocataire.anchor = GridBagConstraints.EAST;
        gbc_lblLocataire.insets = new Insets(0, 0, 5, 5);
        gbc_lblLocataire.gridx = 0;
        gbc_lblLocataire.gridy = 14;
        panelLeft.add(lblLocataire, gbc_lblLocataire);
        
        comboBoxLocataire = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxLocataire = new GridBagConstraints();
        gbc_comboBoxLocataire.gridwidth = 3;
        gbc_comboBoxLocataire.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxLocataire.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxLocataire.gridx = 1;
        gbc_comboBoxLocataire.gridy = 14;
        panelLeft.add(comboBoxLocataire, gbc_comboBoxLocataire);
        
        lblAssu = new JLabel("Assurance :");
        lblAssu.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblAssu = new GridBagConstraints();
        gbc_lblAssu.anchor = GridBagConstraints.EAST;
        gbc_lblAssu.insets = new Insets(0, 0, 0, 5);
        gbc_lblAssu.gridx = 0;
        gbc_lblAssu.gridy = 15;
        panelLeft.add(lblAssu, gbc_lblAssu);
        
        comboBoxAssu = new JComboBox<String>();
        GridBagConstraints gbc_comboBoxAssu = new GridBagConstraints();
        gbc_comboBoxAssu.gridwidth = 3;
        gbc_comboBoxAssu.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxAssu.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxAssu.gridx = 1;
        gbc_comboBoxAssu.gridy = 15;
        panelLeft.add(comboBoxAssu, gbc_comboBoxAssu);
    
        // -- Panel bas : boutons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
    
        annulerButton = new JButton("Annuler");  // renommé
        annulerButton.setActionCommand("Cancel");
        buttonPane.add(annulerButton);
        
        // Ajout du listener pour l'ajout automatique de ligne dans la table
        addRowListenerToTable();
        
        // ---------------------------------------
        //  AJOUT DES ACTION LISTENERS
        // ---------------------------------------
        // 1) combo type
        comboBoxType.addActionListener(e -> {
            gest.gestionComboType();
        });
        
        // 2) combo id charge
        comboBoxChoixCharge.addActionListener(e -> {
            gest.gestionComboIDCharge();
        });
        
        // 3) bouton OK
        okButton.addActionListener(e -> {
            gest.gestionBoutonOk();
        });
        
        // 4) bouton Annuler
        annulerButton.addActionListener(e -> {
            gest.gestionBoutonAnnuler();
        });
        
        // Charge les combos en asynchrone
        gest.chargerComboBox();
    }
    
    // ---------------- GETTERS -----------------
    public String getTextNumDoc() {
        return this.textFieldNumDoc.getText();
    }
    
    public String getTextDateDoc() {
        return this.textFieldDateDoc.getText();
    }
    
    public List<List<Object>> getListeLogement(){
        int rc = this.tableLogements.getModel().getRowCount();
        String logement;
        BigDecimal part;
        
        List<List<Object>> result = new ArrayList<>();
        
        // Correction : on boucle sur i de 0 à rc-1
        for (int i = 0; i < rc; i++) {
            Object valLog = this.tableLogements.getValueAt(i, 0);
            Object valPart = this.tableLogements.getValueAt(i, 1);
            if(valLog != null && valPart != null) {
                logement = (String) valLog;
                part = BigDecimal.valueOf((Float) valPart);
                result.add(Arrays.asList(logement, part));
            }
        }
        
        return result;
    }
    
    public String getTextIDCharge() {
        return this.textFieldIDCharge.getText();
    }
    
    public void setAncienneValeur(String ancienne) {
        this.textFieldValeurAncienIndex.setText(ancienne);
    }
    
    public String getTypeDoc() {
        return String.valueOf(this.comboBoxType.getSelectedItem());
    }
    
    public String getLienFichier() {
        return this.textAreaLien.getText();
    }
    
    public String getIDEntreprise() {
        return String.valueOf(this.comboBoxEntreprise.getSelectedItem());
    }
    
    public String getIDBat() {
        return String.valueOf(this.comboBoxBat.getSelectedItem());
    }
    
    public boolean estRecupLoc() {
        return this.chckbxRecupLoc.isSelected();
    }
    
    public String typeDeCharge() {
        return String.valueOf(this.comboBoxType.getSelectedItem());
    }
    
    public BigDecimal getCoutVarUnit() {
        return BigDecimal.valueOf((float) this.spinnerCoutVarUnit.getValue());
    }
    
    public BigDecimal getCoutVarAbon() {
        return BigDecimal.valueOf((float) this.spinnerCoutVarAbon.getValue()); 
    }
    
    public BigDecimal getValIndex() {
        return BigDecimal.valueOf((double) this.spinnerNouveauIndex.getValue());
    }
    
    public String getIDLocataire() {
        return String.valueOf(this.comboBoxLocataire.getSelectedItem());
    }
    
    public String getIDAssu() {
        return String.valueOf(this.comboBoxAssu.getSelectedItem());
    }
    
    // ------------- Setters combos -------------
    // 1. Types de la combo box
    public void setComboBoxTypes(List<String> types) {
        comboBoxType.removeAllItems();
        for(String type : types) {
            comboBoxType.addItem(type);
        }
    }
    
    // 2. Combo entreprise
    public void setComboBoxEntreprise(List<String> entreprises) {
        comboBoxEntreprise.removeAllItems();
        for(String entreprise : entreprises) {
            comboBoxEntreprise.addItem(entreprise);
        }
    }
    
    // 3. Combo Batiment
    public void setComboBoxBat(List<String> batiments) {
        comboBoxBat.removeAllItems();
        for(String batiment : batiments) {
            comboBoxBat.addItem(batiment);
        }
    }
    
    // 4. Combo IDCharge
    public void setComboIDCharge(List<String> charges) {
        comboBoxChoixCharge.removeAllItems();
        for(String ch : charges) {
            comboBoxChoixCharge.addItem(ch);
        }
    }
    
    // 5. Ajout d'une ligne dans la JTable quand la dernière n'est plus vide
    public void addRowListenerToTable() {
        tableLogements.getModel().addTableModelListener(e -> {
            int rowCount = tableLogements.getRowCount();
            // Vérifier si la dernière ligne n'est pas vide
            boolean lastRowFilled = true;
            for(int col = 0; col < tableLogements.getColumnCount(); col++) {
                Object value = tableLogements.getValueAt(rowCount - 1, col);
                if(value == null || value.toString().trim().isEmpty()) {
                    lastRowFilled = false;
                    break;
                }
            }
            // Si la dernière ligne est remplie, ajouter une nouvelle ligne vide
            if(lastRowFilled) {
                DefaultTableModel model = (DefaultTableModel) tableLogements.getModel();
                model.addRow(new Object[]{null, null});
            }
        });
    }
    
    // 6. Valeurs des spinners
    public void setSpinnerNouveauIndex(BigDecimal value) {
        spinnerNouveauIndex.setValue(value.doubleValue());
    }

    public void setSpinnerCoutVarUnit(BigDecimal value) {
        spinnerCoutVarUnit.setValue(value.floatValue());
    }

    public void setSpinnerCoutVarAbon(BigDecimal value) {
        spinnerCoutVarAbon.setValue(value.floatValue());
    }

    // 7. Combo locataire
    public void setComboBoxLocataire(List<String> locataires) {
        comboBoxLocataire.removeAllItems();
        for(String locataire : locataires) {
            comboBoxLocataire.addItem(locataire);
        }
    }

    // 8. Assurance
    public void setComboBoxAssu(List<String> assurances) {
        comboBoxAssu.removeAllItems();
        for(String assu : assurances) {
            comboBoxAssu.addItem(assu);
        }
    }
    

    
    // 10. Setter textes 
    
    public void setTextLblCout(String text) {
    	lblCout.setText(text);
    }
    
    // -----------------------------------------------------------------------
    //  AJOUT DE METHODES POUR MASQUER / AFFICHER LES CHAMPS SELON LE TYPE
    // -----------------------------------------------------------------------
    
    /** Masque ou affiche la zone "Entreprise". */
    public void setEntrepriseVisible(boolean visible) {
        lblEntreprise.setVisible(visible);
        comboBoxEntreprise.setVisible(visible);
    }
    
    /** Masque ou affiche la zone "Assurance". */
    public void setAssuranceVisible(boolean visible) {
        lblAssu.setVisible(visible);
        comboBoxAssu.setVisible(visible);
    }
    
    /** Masque ou affiche la zone "Locataire" (Quittance pour). */
    public void setLocataireVisible(boolean visible) {
        lblLocataire.setVisible(visible);
        comboBoxLocataire.setVisible(visible);
    }
    
    /** Masque ou affiche le cout abonnement. */
    public void setCoutAbonnementVisible(boolean visible) {
            spinnerCoutVarAbon.setVisible(visible);
            spinnerCoutVarAbon.setVisible(visible);
    }
    
    /** Masque / affiche la zone Nouvel/Ancien index. */
    public void setIndexVisible(boolean visible) {
        lblValIndex.setVisible(visible);
        spinnerNouveauIndex.setVisible(visible);
        textFieldValeurAncienIndex.setVisible(visible);
    }
    
    /** Masque / affiche la partie "ID charge" + combo d'id charge. */
    public void setIdChargeTotalVisible(boolean visible) {
        lblIDChargeIndex.setVisible(visible);
        lblOu.setVisible(visible);
        textFieldIDCharge.setVisible(visible);
        comboBoxChoixCharge.setVisible(visible);
    }
    
    public void setIdChargeNomVisible(boolean visible) {
        comboBoxChoixCharge.setVisible(false);
        lblOu.setVisible(false);
        textFieldIDCharge.setVisible(visible);
        lblIDChargeIndex.setVisible(visible);
    }
    
    // Utilitaire :
    
    //Pour afficher un message d'erreur
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
}
