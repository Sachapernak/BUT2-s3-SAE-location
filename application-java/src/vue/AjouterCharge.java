package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import java.awt.Component;

import controleur.GestionAjouterCharge;

public class AjouterCharge extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    
    // --- Champs d'instance pour les labels :
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
    
    // --- Champs d'instance pour les composants de saisie :
    private JTextField textFieldNumDoc;
    private JTextField textFieldDateDoc;
    private JTable tableLogements;
    private JTextField textFieldIDCharge;
    private JTextField textFieldValeurAncienIndex;
    private JComboBox<String> comboBoxType;
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
    
    // --- Remplacement de textAreaLien par un champ texte + bouton + fileChooser
    private JTextField textFieldLienFichier;   // Montre le chemin du fichier sélectionné
    private JButton buttonParcourir;           // Bouton pour ouvrir le JFileChooser
    private JFileChooser fileChooser;          // Sélecteur de fichier
    
    // Boutons bas de fenêtre
    private JButton okButton;
    private JButton annulerButton; // renommé depuis 'cancelButton'
    
    private GestionAjouterCharge gest;
    private JLabel lblNomTypeCharge;
    private JTextField textFieldTypeNomCharge;

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
    public AjouterCharge()  {
        
        // Permet d'afficher dans la barre des tâches
        super(null, ModalityType.TOOLKIT_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
        gbl_panelLeft.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 100, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panelLeft.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panelLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelLeft.setLayout(gbl_panelLeft);
        
        // ---------------------
        // Ligne : Numéro de document
        // ---------------------
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
        
        // ---------------------
        // Ligne : Date du document
        // ---------------------
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
        
        // ---------------------
        // Ligne : Type de document
        // ---------------------
        lblType = new JLabel("Type :");
        lblType.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.anchor = GridBagConstraints.EAST;
        gbc_lblType.insets = new Insets(0, 0, 5, 5);
        gbc_lblType.gridx = 0;
        gbc_lblType.gridy = 2;
        panelLeft.add(lblType, gbc_lblType);
        
        comboBoxType = new JComboBox<>();
        GridBagConstraints gbc_comboBoxType = new GridBagConstraints();
        gbc_comboBoxType.gridwidth = 3;
        gbc_comboBoxType.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxType.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxType.gridx = 1;
        gbc_comboBoxType.gridy = 2;
        panelLeft.add(comboBoxType, gbc_comboBoxType);
        
        // ---------------------
        // Ligne : Lien vers le fichier => on ajoute un panel pour le TextField + bouton "Parcourir..."
        // ---------------------
        lblLienFichier = new JLabel("Lien vers le fichier :");
        lblLienFichier.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblLienFichier = new GridBagConstraints();
        gbc_lblLienFichier.fill = GridBagConstraints.VERTICAL;
        gbc_lblLienFichier.anchor = GridBagConstraints.EAST;
        gbc_lblLienFichier.insets = new Insets(0, 0, 5, 5);
        gbc_lblLienFichier.gridx = 0;
        gbc_lblLienFichier.gridy = 3;
        panelLeft.add(lblLienFichier, gbc_lblLienFichier);
        
        // Panel contenant le TextField + bouton
        JPanel panelFileChooser = new JPanel(new BorderLayout());
        GridBagConstraints gbc_panelFileChooser = new GridBagConstraints();
        gbc_panelFileChooser.gridwidth = 3;
        gbc_panelFileChooser.insets = new Insets(0, 0, 5, 5);
        gbc_panelFileChooser.fill = GridBagConstraints.BOTH;
        gbc_panelFileChooser.gridx = 1;
        gbc_panelFileChooser.gridy = 3;
        panelLeft.add(panelFileChooser, gbc_panelFileChooser);
        
        textFieldLienFichier = new JTextField();
        panelFileChooser.add(textFieldLienFichier, BorderLayout.CENTER);
        
        buttonParcourir = new JButton("Parcourir...");
        panelFileChooser.add(buttonParcourir, BorderLayout.EAST);
        
        // Instanciation du JFileChooser
        fileChooser = new JFileChooser();
        // Configurez le JFileChooser si besoin (filtres, répertoires par défaut, etc.)
        

        
        // ---------------------
        // Strut vertical
        // ---------------------
        Component verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 0;
        gbc_verticalStrut.gridy = 4;
        panelLeft.add(verticalStrut, gbc_verticalStrut);
        
        // ---------------------
        // Ligne : Entreprise
        // ---------------------
        lblEntreprise = new JLabel("Entreprise :");
        lblEntreprise.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblEntreprise = new GridBagConstraints();
        gbc_lblEntreprise.anchor = GridBagConstraints.EAST;
        gbc_lblEntreprise.insets = new Insets(0, 0, 5, 5);
        gbc_lblEntreprise.gridx = 0;
        gbc_lblEntreprise.gridy = 5;
        panelLeft.add(lblEntreprise, gbc_lblEntreprise);
        
        comboBoxEntreprise = new JComboBox<>();
        GridBagConstraints gbc_comboBoxEntreprise = new GridBagConstraints();
        gbc_comboBoxEntreprise.gridwidth = 3;
        gbc_comboBoxEntreprise.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxEntreprise.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxEntreprise.gridx = 1;
        gbc_comboBoxEntreprise.gridy = 5;
        panelLeft.add(comboBoxEntreprise, gbc_comboBoxEntreprise);
        
        // ---------------------
        // Ligne : Bâtiment
        // ---------------------
        lblBat = new JLabel("Batiment :");
        lblBat.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblBat = new GridBagConstraints();
        gbc_lblBat.anchor = GridBagConstraints.EAST;
        gbc_lblBat.insets = new Insets(0, 0, 5, 5);
        gbc_lblBat.gridx = 0;
        gbc_lblBat.gridy = 6;
        panelLeft.add(lblBat, gbc_lblBat);
        
        comboBoxBat = new JComboBox<>();
        GridBagConstraints gbc_comboBoxBat = new GridBagConstraints();
        gbc_comboBoxBat.gridwidth = 3;
        gbc_comboBoxBat.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxBat.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxBat.gridx = 1;
        gbc_comboBoxBat.gridy = 6;
        panelLeft.add(comboBoxBat, gbc_comboBoxBat);
        
        // ---------------------
        // Ligne : Bien locatifs (tableau)
        // ---------------------
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

            @SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
                String.class, Float.class
            };
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });
        scrollPane.setViewportView(tableLogements);
        
        // ---------------------
        // Ligne : Récupérable locataire ?
        // ---------------------
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
        
        // ---------------------
        // Ligne : ID charge + combo
        // ---------------------
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
        
        comboBoxChoixCharge = new JComboBox<>();
        GridBagConstraints gbc_comboBoxChoixCharge = new GridBagConstraints();
        gbc_comboBoxChoixCharge.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxChoixCharge.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxChoixCharge.gridx = 3;
        gbc_comboBoxChoixCharge.gridy = 10;
        panelLeft.add(comboBoxChoixCharge, gbc_comboBoxChoixCharge);
        
        lblNomTypeCharge = new JLabel("Nom / Type :");
        GridBagConstraints gbc_lblNomTypeCharge = new GridBagConstraints();
        gbc_lblNomTypeCharge.anchor = GridBagConstraints.EAST;
        gbc_lblNomTypeCharge.insets = new Insets(0, 0, 5, 5);
        gbc_lblNomTypeCharge.gridx = 0;
        gbc_lblNomTypeCharge.gridy = 11;
        panelLeft.add(lblNomTypeCharge, gbc_lblNomTypeCharge);
        
        textFieldTypeNomCharge = new JTextField();
        GridBagConstraints gbc_textFieldTypeNomCharge = new GridBagConstraints();
        gbc_textFieldTypeNomCharge.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldTypeNomCharge.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldTypeNomCharge.gridx = 1;
        gbc_textFieldTypeNomCharge.gridy = 11;
        panelLeft.add(textFieldTypeNomCharge, gbc_textFieldTypeNomCharge);
        textFieldTypeNomCharge.setColumns(10);
        
        // ---------------------
        // Ligne : Coût unitaire | abonnement
        // ---------------------
        lblCout = new JLabel("Cout unitaire | abonnement :");
        lblCout.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblCout = new GridBagConstraints();
        gbc_lblCout.anchor = GridBagConstraints.EAST;
        gbc_lblCout.insets = new Insets(0, 0, 5, 5);
        gbc_lblCout.gridx = 0;
        gbc_lblCout.gridy = 12;
        panelLeft.add(lblCout, gbc_lblCout);
        
        spinnerCoutVarUnit = new JSpinner();
        spinnerCoutVarUnit.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbc_spinnerCoutVarUnit = new GridBagConstraints();
        gbc_spinnerCoutVarUnit.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerCoutVarUnit.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerCoutVarUnit.gridx = 1;
        gbc_spinnerCoutVarUnit.gridy = 12;
        panelLeft.add(spinnerCoutVarUnit, gbc_spinnerCoutVarUnit);
        
        spinnerCoutVarAbon = new JSpinner();
        spinnerCoutVarAbon.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbc_spinnerCoutVarAbon = new GridBagConstraints();
        gbc_spinnerCoutVarAbon.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerCoutVarAbon.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerCoutVarAbon.gridx = 3;
        gbc_spinnerCoutVarAbon.gridy = 12;
        panelLeft.add(spinnerCoutVarAbon, gbc_spinnerCoutVarAbon);
        
        // ---------------------
        // Ligne : Nouvel/Ancien Index
        // ---------------------
        lblValIndex = new JLabel("Nouveau | Ancien Index :");
        GridBagConstraints gbc_lblValIndex = new GridBagConstraints();
        gbc_lblValIndex.anchor = GridBagConstraints.EAST;
        gbc_lblValIndex.insets = new Insets(0, 0, 5, 5);
        gbc_lblValIndex.gridx = 0;
        gbc_lblValIndex.gridy = 13;
        panelLeft.add(lblValIndex, gbc_lblValIndex);
        
        spinnerNouveauIndex = new JSpinner();
        spinnerNouveauIndex.setModel(new SpinnerNumberModel(Double.valueOf(0), null, null, Double.valueOf(5)));
        GridBagConstraints gbc_spinnerNouveauIndex = new GridBagConstraints();
        gbc_spinnerNouveauIndex.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerNouveauIndex.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerNouveauIndex.gridx = 1;
        gbc_spinnerNouveauIndex.gridy = 13;
        panelLeft.add(spinnerNouveauIndex, gbc_spinnerNouveauIndex);
        
        textFieldValeurAncienIndex = new JTextField();
        textFieldValeurAncienIndex.setEditable(false);
        GridBagConstraints gbc_textFieldValeurAncienIndex = new GridBagConstraints();
        gbc_textFieldValeurAncienIndex.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValeurAncienIndex.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldValeurAncienIndex.gridx = 3;
        gbc_textFieldValeurAncienIndex.gridy = 13;
        panelLeft.add(textFieldValeurAncienIndex, gbc_textFieldValeurAncienIndex);
        textFieldValeurAncienIndex.setColumns(10);
        
        // ---------------------
        // Ligne : Quittance pour (Locataire)
        // ---------------------
        lblLocataire = new JLabel("Quittance pour :");
        GridBagConstraints gbc_lblLocataire = new GridBagConstraints();
        gbc_lblLocataire.anchor = GridBagConstraints.EAST;
        gbc_lblLocataire.insets = new Insets(0, 0, 5, 5);
        gbc_lblLocataire.gridx = 0;
        gbc_lblLocataire.gridy = 15;
        panelLeft.add(lblLocataire, gbc_lblLocataire);
        
        comboBoxLocataire = new JComboBox<>();
        GridBagConstraints gbc_comboBoxLocataire = new GridBagConstraints();
        gbc_comboBoxLocataire.gridwidth = 3;
        gbc_comboBoxLocataire.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxLocataire.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxLocataire.gridx = 1;
        gbc_comboBoxLocataire.gridy = 15;
        panelLeft.add(comboBoxLocataire, gbc_comboBoxLocataire);
        
        // ---------------------
        // Ligne : Assurance
        // ---------------------
        lblAssu = new JLabel("Assurance :");
        lblAssu.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblAssu = new GridBagConstraints();
        gbc_lblAssu.anchor = GridBagConstraints.EAST;
        gbc_lblAssu.insets = new Insets(0, 0, 0, 5);
        gbc_lblAssu.gridx = 0;
        gbc_lblAssu.gridy = 16;
        panelLeft.add(lblAssu, gbc_lblAssu);
        
        comboBoxAssu = new JComboBox<>();
        GridBagConstraints gbc_comboBoxAssu = new GridBagConstraints();
        gbc_comboBoxAssu.gridwidth = 3;
        gbc_comboBoxAssu.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxAssu.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxAssu.gridx = 1;
        gbc_comboBoxAssu.gridy = 16;
        panelLeft.add(comboBoxAssu, gbc_comboBoxAssu);
    
        // --------------------------------------------------------------------
        // Panel bas : boutons OK et Annuler
        // --------------------------------------------------------------------
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
        
        // -------------------------------------------------
        //  AJOUT DES ACTION LISTENERS (appels au contrôleur)
        // -------------------------------------------------
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
        
        // Gère la sélection IDCharge
        gest.gestionComboID(comboBoxChoixCharge);
        
        // Action du bouton "Parcourir..."
        gest.gestionBoutonParcourir(buttonParcourir, fileChooser);

    }

	public void gestionBoutonParcourir(JButton buttonParcourir, JFileChooser fileChooser) {
		buttonParcourir.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(AjouterCharge.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Obtenir le fichier sélectionné
                textFieldLienFichier.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
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
        
        // On boucle de 0 à rc - 1
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
    
    /**
     * Si on veut la valeur de la combo
     */
    public String getIDChargeCombo() {
        return String.valueOf(this.comboBoxChoixCharge.getSelectedItem());
    }
    
    public void setAncienneValeur(String ancienne) {
        this.textFieldValeurAncienIndex.setText(ancienne);
    }
    
    public String getTypeDoc() {
        return String.valueOf(this.comboBoxType.getSelectedItem());
    }
    
    /**
     * Récupère le chemin du fichier choisi via le JFileChooser
     */
    public String getLienFichier() {
        return this.textFieldLienFichier.getText();
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
    
    public BigDecimal getAncienIndex() {
        return BigDecimal.valueOf(Float.valueOf(this.textFieldValeurAncienIndex.getText().isEmpty() ? 
        										"0.0" : this.textFieldValeurAncienIndex.getText()));
    }
    
    public String getIDLocataire() {
        return String.valueOf(this.comboBoxLocataire.getSelectedItem());
    }
    
    public String getIDAssu() {
        return String.valueOf(this.comboBoxAssu.getSelectedItem());
    }
    
    public String getNomTypeCharge() {
    	return this.textFieldTypeNomCharge.getText();
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
    
    public void setLienFichier(String lien) {
        textFieldLienFichier.setText(lien);
    }
    
    public void setTextAncienIndex(String val) {
        textFieldValeurAncienIndex.setText(val);
    }
    
    public void clearTextIdCharge() {
    	this.textFieldIDCharge.setText("");
    }
    
    
    public void setValSpinnerUnit(Float val) {
        spinnerCoutVarUnit.setValue(val);
    }
    
    public void setValSpinnerAbonn(Float val) {
        spinnerCoutVarAbon.setValue(val);
    }
    
    public void setNomTypeCharge(String type) {
    	this.textFieldTypeNomCharge.setText(type);
    }
    
    // -----------------------------------------------------------------------
    //  Méthodes pour masquer / afficher certains champs selon le type
    // -----------------------------------------------------------------------
    
    public void setEntrepriseVisible(boolean visible) {
        lblEntreprise.setVisible(visible);
        comboBoxEntreprise.setVisible(visible);
    }
    
    public void setAssuranceVisible(boolean visible) {
        lblAssu.setVisible(visible);
        comboBoxAssu.setVisible(visible);
    }
    
    public void setLocataireVisible(boolean visible) {
        lblLocataire.setVisible(visible);
        comboBoxLocataire.setVisible(visible);
    }
    
    public void setCoutAbonnementVisible(boolean visible) {
        spinnerCoutVarAbon.setVisible(visible);
    }
    
    public void setIndexVisible(boolean visible) {
        lblValIndex.setVisible(visible);
        spinnerNouveauIndex.setVisible(visible);
        textFieldValeurAncienIndex.setVisible(visible);
    }
    
    public void setIdChargeTotalVisible(boolean visible) {
        lblIDChargeIndex.setVisible(visible);
        lblOu.setVisible(visible);
        textFieldIDCharge.setVisible(visible);
        comboBoxChoixCharge.setVisible(visible);
        lblNomTypeCharge.setVisible(visible);
        textFieldTypeNomCharge.setVisible(visible);
    }
    
    public void setIdChargeNomVisible(boolean visible) {
        comboBoxChoixCharge.setVisible(false);
        lblOu.setVisible(false);
        textFieldIDCharge.setVisible(visible);
        lblIDChargeIndex.setVisible(visible);
        lblNomTypeCharge.setVisible(visible);
        textFieldTypeNomCharge.setVisible(visible);
    }
    
    // Pour afficher un message d'erreur
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
