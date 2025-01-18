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
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import java.awt.Component;

import controleur.GestionAjouterCharge;

/**
 * Fenêtre de dialogue permettant d'ajouter ou de modifier une charge.
 * <p>
 * Cette classe se compose de divers champs pour renseigner les détails de la charge :
 * <ul>
 *   <li>Numéro et date du document</li>
 *   <li>Type de document</li>
 *   <li>Entreprise, bâtiment, biens locatifs</li>
 *   <li>Options de coût (unitaire, abonnement), index</li>
 *   <li>Possibilité de parcourir un fichier (facture, devis, etc.)</li>
 * </ul>
 * <p>
 * Les interactions sont gérées par un contrôleur {@link GestionAjouterCharge}.
 */
public class AjouterCharge extends JDialog {

    private static final long serialVersionUID = 1L;

    // --- Panneau principal du contenu
    private final JPanel contentPanel = new JPanel();

    // --- Labels
    private JLabel labelTitre;
    private JLabel labelNumeroDoc;
    private JLabel labelDateDocument;
    private JLabel labelType;
    private JLabel labelLienFichier;
    private JLabel labelEntreprise;
    private JLabel labelBat;
    private JLabel labelBienLocatif;
    private JLabel labelRecupLoc;
    private JLabel labelIdChargeIndex;
    private JLabel labelOu;
    private JLabel labelCout;
    private JLabel labelValIndex;
    private JLabel labelLocataire;
    private JLabel labelAssu;
    private JLabel labelNomTypeCharge;

    // --- Champs de saisie et composants
    private JTextField textFieldNumDoc;
    private JTextField textFieldDateDoc;
    private JTextField textFieldIdCharge;
    private JTextField textFieldValeurAncienIndex;
    private JTextField textFieldTypeNomCharge;

    private JTable tableLogements;
    private JComboBox<String> comboBoxType;
    private JComboBox<String> comboBoxEntreprise;
    private JComboBox<String> comboBoxBat;
    private JComboBox<String> comboBoxChoixCharge;
    private JComboBox<String> comboBoxLocataire;
    private JComboBox<String> comboBoxAssu;

    private JScrollPane scrollPane;
    private JCheckBox checkBoxRecupLoc;

    private JSpinner spinnerCoutVarUnit;
    private JSpinner spinnerCoutVarAbon;
    private JSpinner spinnerNouveauIndex;

    // --- Champs pour la gestion de fichier
    private JTextField textFieldLienFichier;
    private JButton buttonParcourir;
    private JFileChooser fileChooser;

    // --- Boutons du bas de la fenêtre
    private JButton okButton;
    private JButton annulerButton;

    // --- Contrôleur
    private GestionAjouterCharge gestion;

    /**
     * Méthode main de test (lancement autonome).
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        try {
            AjouterCharge dialog = new AjouterCharge();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur principal de la boîte de dialogue "AjouterCharge".
     * Initialise l'interface et configure les listeners vers le contrôleur.
     */
    public AjouterCharge() {
        // Permet d'afficher dans la barre des tâches
        super(null, ModalityType.TOOLKIT_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.gestion = new GestionAjouterCharge(this);

        // Configuration de la fenêtre
        setBounds(100, 100, 548, 620);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{180, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0};
        gblContentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);

        // Titre
        labelTitre = new JLabel("Ajout de charge");
        labelTitre.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbcLabelTitre = new GridBagConstraints();
        gbcLabelTitre.insets = new Insets(0, 0, 5, 0);
        gbcLabelTitre.gridx = 0;
        gbcLabelTitre.gridy = 0;
        contentPanel.add(labelTitre, gbcLabelTitre);

        // Panneau gauche
        JPanel panelLeft = new JPanel();
        GridBagConstraints gbcPanelLeft = new GridBagConstraints();
        gbcPanelLeft.fill = GridBagConstraints.BOTH;
        gbcPanelLeft.gridx = 0;
        gbcPanelLeft.gridy = 1;
        contentPanel.add(panelLeft, gbcPanelLeft);

        GridBagLayout gblPanelLeft = new GridBagLayout();
        gblPanelLeft.columnWidths = new int[]{155, 160, 0, 160, 0, 0};
        gblPanelLeft.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 100, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblPanelLeft.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblPanelLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelLeft.setLayout(gblPanelLeft);

        // ----------------------------------------------------------------
        // 1. Numéro de document
        // ----------------------------------------------------------------
        labelNumeroDoc = new JLabel("Numéro de document :");
        labelNumeroDoc.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelNumeroDoc = new GridBagConstraints();
        gbcLabelNumeroDoc.anchor = GridBagConstraints.EAST;
        gbcLabelNumeroDoc.insets = new Insets(0, 0, 5, 5);
        gbcLabelNumeroDoc.gridx = 0;
        gbcLabelNumeroDoc.gridy = 0;
        panelLeft.add(labelNumeroDoc, gbcLabelNumeroDoc);

        textFieldNumDoc = new JTextField();
        GridBagConstraints gbcTextFieldNumDoc = new GridBagConstraints();
        gbcTextFieldNumDoc.gridwidth = 3;
        gbcTextFieldNumDoc.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldNumDoc.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldNumDoc.gridx = 1;
        gbcTextFieldNumDoc.gridy = 0;
        panelLeft.add(textFieldNumDoc, gbcTextFieldNumDoc);
        textFieldNumDoc.setColumns(10);

        // ----------------------------------------------------------------
        // 2. Date du document
        // ----------------------------------------------------------------
        labelDateDocument = new JLabel("Date du document :");
        labelDateDocument.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelDateDocument = new GridBagConstraints();
        gbcLabelDateDocument.anchor = GridBagConstraints.EAST;
        gbcLabelDateDocument.insets = new Insets(0, 0, 5, 5);
        gbcLabelDateDocument.gridx = 0;
        gbcLabelDateDocument.gridy = 1;
        panelLeft.add(labelDateDocument, gbcLabelDateDocument);

        textFieldDateDoc = new JTextField();
        GridBagConstraints gbcTextFieldDateDoc = new GridBagConstraints();
        gbcTextFieldDateDoc.gridwidth = 3;
        gbcTextFieldDateDoc.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldDateDoc.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldDateDoc.gridx = 1;
        gbcTextFieldDateDoc.gridy = 1;
        panelLeft.add(textFieldDateDoc, gbcTextFieldDateDoc);
        textFieldDateDoc.setColumns(10);

        // ----------------------------------------------------------------
        // 3. Type de document
        // ----------------------------------------------------------------
        labelType = new JLabel("Type :");
        labelType.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelType = new GridBagConstraints();
        gbcLabelType.anchor = GridBagConstraints.EAST;
        gbcLabelType.insets = new Insets(0, 0, 5, 5);
        gbcLabelType.gridx = 0;
        gbcLabelType.gridy = 2;
        panelLeft.add(labelType, gbcLabelType);

        comboBoxType = new JComboBox<>();
        GridBagConstraints gbcComboBoxType = new GridBagConstraints();
        gbcComboBoxType.gridwidth = 3;
        gbcComboBoxType.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxType.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxType.gridx = 1;
        gbcComboBoxType.gridy = 2;
        panelLeft.add(comboBoxType, gbcComboBoxType);

        // ----------------------------------------------------------------
        // 4. Lien vers le fichier + bouton "Parcourir..."
        // ----------------------------------------------------------------
        labelLienFichier = new JLabel("Lien vers le fichier :");
        labelLienFichier.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelLienFichier = new GridBagConstraints();
        gbcLabelLienFichier.fill = GridBagConstraints.VERTICAL;
        gbcLabelLienFichier.anchor = GridBagConstraints.EAST;
        gbcLabelLienFichier.insets = new Insets(0, 0, 5, 5);
        gbcLabelLienFichier.gridx = 0;
        gbcLabelLienFichier.gridy = 3;
        panelLeft.add(labelLienFichier, gbcLabelLienFichier);

        // Panneau contenant TextField + bouton "Parcourir"
        JPanel panelFileChooser = new JPanel(new BorderLayout());
        GridBagConstraints gbcPanelFileChooser = new GridBagConstraints();
        gbcPanelFileChooser.gridwidth = 3;
        gbcPanelFileChooser.insets = new Insets(0, 0, 5, 5);
        gbcPanelFileChooser.fill = GridBagConstraints.BOTH;
        gbcPanelFileChooser.gridx = 1;
        gbcPanelFileChooser.gridy = 3;
        panelLeft.add(panelFileChooser, gbcPanelFileChooser);

        textFieldLienFichier = new JTextField();
        panelFileChooser.add(textFieldLienFichier, BorderLayout.CENTER);

        buttonParcourir = new JButton("Parcourir...");
        panelFileChooser.add(buttonParcourir, BorderLayout.EAST);

        // Instanciation du JFileChooser
        fileChooser = new JFileChooser();
        // (Configurer le JFileChooser si nécessaire)

        // Ajout d'un espace vertical
        Component verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbcVerticalStrut = new GridBagConstraints();
        gbcVerticalStrut.insets = new Insets(0, 0, 5, 5);
        gbcVerticalStrut.gridx = 0;
        gbcVerticalStrut.gridy = 4;
        panelLeft.add(verticalStrut, gbcVerticalStrut);

        // ----------------------------------------------------------------
        // 5. Entreprise
        // ----------------------------------------------------------------
        labelEntreprise = new JLabel("Entreprise :");
        labelEntreprise.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelEntreprise = new GridBagConstraints();
        gbcLabelEntreprise.anchor = GridBagConstraints.EAST;
        gbcLabelEntreprise.insets = new Insets(0, 0, 5, 5);
        gbcLabelEntreprise.gridx = 0;
        gbcLabelEntreprise.gridy = 5;
        panelLeft.add(labelEntreprise, gbcLabelEntreprise);

        comboBoxEntreprise = new JComboBox<>();
        GridBagConstraints gbcComboBoxEntreprise = new GridBagConstraints();
        gbcComboBoxEntreprise.gridwidth = 3;
        gbcComboBoxEntreprise.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxEntreprise.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxEntreprise.gridx = 1;
        gbcComboBoxEntreprise.gridy = 5;
        panelLeft.add(comboBoxEntreprise, gbcComboBoxEntreprise);

        // ----------------------------------------------------------------
        // 6. Bâtiment
        // ----------------------------------------------------------------
        labelBat = new JLabel("Bâtiment :");
        labelBat.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelBat = new GridBagConstraints();
        gbcLabelBat.anchor = GridBagConstraints.EAST;
        gbcLabelBat.insets = new Insets(0, 0, 5, 5);
        gbcLabelBat.gridx = 0;
        gbcLabelBat.gridy = 6;
        panelLeft.add(labelBat, gbcLabelBat);

        comboBoxBat = new JComboBox<>();
        GridBagConstraints gbcComboBoxBat = new GridBagConstraints();
        gbcComboBoxBat.gridwidth = 3;
        gbcComboBoxBat.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxBat.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxBat.gridx = 1;
        gbcComboBoxBat.gridy = 6;
        panelLeft.add(comboBoxBat, gbcComboBoxBat);

        // ----------------------------------------------------------------
        // 7. Biens locatifs (Tableau)
        // ----------------------------------------------------------------
        labelBienLocatif = new JLabel("Biens locatifs concernés :");
        labelBienLocatif.setHorizontalAlignment(SwingConstants.TRAILING);
        labelBienLocatif.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints gbcLabelBienLocatif = new GridBagConstraints();
        gbcLabelBienLocatif.anchor = GridBagConstraints.EAST;
        gbcLabelBienLocatif.insets = new Insets(0, 0, 5, 5);
        gbcLabelBienLocatif.gridx = 0;
        gbcLabelBienLocatif.gridy = 7;
        panelLeft.add(labelBienLocatif, gbcLabelBienLocatif);

        JPanel panelInterne = new JPanel();
        panelInterne.setMaximumSize(new Dimension(32767, 120));
        panelInterne.setPreferredSize(new Dimension(10, 80));
        GridBagConstraints gbcPanelInterne = new GridBagConstraints();
        gbcPanelInterne.gridwidth = 3;
        gbcPanelInterne.insets = new Insets(0, 0, 5, 5);
        gbcPanelInterne.fill = GridBagConstraints.BOTH;
        gbcPanelInterne.gridx = 1;
        gbcPanelInterne.gridy = 7;
        panelLeft.add(panelInterne, gbcPanelInterne);
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

        // ----------------------------------------------------------------
        // 8. Case : récupérable locataire ?
        // ----------------------------------------------------------------
        labelRecupLoc = new JLabel("Récupérable locataire :");
        GridBagConstraints gbcLabelRecupLoc = new GridBagConstraints();
        gbcLabelRecupLoc.anchor = GridBagConstraints.EAST;
        gbcLabelRecupLoc.insets = new Insets(0, 0, 5, 5);
        gbcLabelRecupLoc.gridx = 0;
        gbcLabelRecupLoc.gridy = 8;
        panelLeft.add(labelRecupLoc, gbcLabelRecupLoc);

        checkBoxRecupLoc = new JCheckBox("");
        GridBagConstraints gbcCheckBoxRecupLoc = new GridBagConstraints();
        gbcCheckBoxRecupLoc.anchor = GridBagConstraints.WEST;
        gbcCheckBoxRecupLoc.insets = new Insets(0, 0, 5, 5);
        gbcCheckBoxRecupLoc.gridx = 1;
        gbcCheckBoxRecupLoc.gridy = 8;
        panelLeft.add(checkBoxRecupLoc, gbcCheckBoxRecupLoc);

        Component verticalStrut1 = Box.createVerticalStrut(20);
        GridBagConstraints gbcVerticalStrut1 = new GridBagConstraints();
        gbcVerticalStrut1.insets = new Insets(0, 0, 5, 5);
        gbcVerticalStrut1.gridx = 0;
        gbcVerticalStrut1.gridy = 9;
        panelLeft.add(verticalStrut1, gbcVerticalStrut1);

        // ----------------------------------------------------------------
        // 9. ID charge + combo
        // ----------------------------------------------------------------
        labelIdChargeIndex = new JLabel("ID charge :");
        labelIdChargeIndex.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelIdChargeIndex = new GridBagConstraints();
        gbcLabelIdChargeIndex.anchor = GridBagConstraints.EAST;
        gbcLabelIdChargeIndex.insets = new Insets(0, 0, 5, 5);
        gbcLabelIdChargeIndex.gridx = 0;
        gbcLabelIdChargeIndex.gridy = 10;
        panelLeft.add(labelIdChargeIndex, gbcLabelIdChargeIndex);

        textFieldIdCharge = new JTextField();
        GridBagConstraints gbcTextFieldIDCharge = new GridBagConstraints();
        gbcTextFieldIDCharge.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldIDCharge.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldIDCharge.gridx = 1;
        gbcTextFieldIDCharge.gridy = 10;
        panelLeft.add(textFieldIdCharge, gbcTextFieldIDCharge);
        textFieldIdCharge.setColumns(10);

        labelOu = new JLabel("Ou");
        GridBagConstraints gbcLabelOu = new GridBagConstraints();
        gbcLabelOu.anchor = GridBagConstraints.EAST;
        gbcLabelOu.insets = new Insets(0, 0, 5, 5);
        gbcLabelOu.gridx = 2;
        gbcLabelOu.gridy = 10;
        panelLeft.add(labelOu, gbcLabelOu);

        comboBoxChoixCharge = new JComboBox<>();
        GridBagConstraints gbcComboBoxChoixCharge = new GridBagConstraints();
        gbcComboBoxChoixCharge.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxChoixCharge.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxChoixCharge.gridx = 3;
        gbcComboBoxChoixCharge.gridy = 10;
        panelLeft.add(comboBoxChoixCharge, gbcComboBoxChoixCharge);

        // ----------------------------------------------------------------
        // 10. Nom / Type charge
        // ----------------------------------------------------------------
        labelNomTypeCharge = new JLabel("Nom / Type :");
        GridBagConstraints gbcLabelNomTypeCharge = new GridBagConstraints();
        gbcLabelNomTypeCharge.anchor = GridBagConstraints.EAST;
        gbcLabelNomTypeCharge.insets = new Insets(0, 0, 5, 5);
        gbcLabelNomTypeCharge.gridx = 0;
        gbcLabelNomTypeCharge.gridy = 11;
        panelLeft.add(labelNomTypeCharge, gbcLabelNomTypeCharge);

        textFieldTypeNomCharge = new JTextField();
        GridBagConstraints gbcTextFieldTypeNomCharge = new GridBagConstraints();
        gbcTextFieldTypeNomCharge.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldTypeNomCharge.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldTypeNomCharge.gridx = 1;
        gbcTextFieldTypeNomCharge.gridy = 11;
        panelLeft.add(textFieldTypeNomCharge, gbcTextFieldTypeNomCharge);
        textFieldTypeNomCharge.setColumns(10);

        // ----------------------------------------------------------------
        // 11. Coût unitaire | abonnement
        // ----------------------------------------------------------------
        labelCout = new JLabel("Coût unitaire | abonnement :");
        labelCout.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelCout = new GridBagConstraints();
        gbcLabelCout.anchor = GridBagConstraints.EAST;
        gbcLabelCout.insets = new Insets(0, 0, 5, 5);
        gbcLabelCout.gridx = 0;
        gbcLabelCout.gridy = 12;
        panelLeft.add(labelCout, gbcLabelCout);

        spinnerCoutVarUnit = new JSpinner();
        spinnerCoutVarUnit.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbcSpinnerCoutVarUnit = new GridBagConstraints();
        gbcSpinnerCoutVarUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcSpinnerCoutVarUnit.insets = new Insets(0, 0, 5, 5);
        gbcSpinnerCoutVarUnit.gridx = 1;
        gbcSpinnerCoutVarUnit.gridy = 12;
        panelLeft.add(spinnerCoutVarUnit, gbcSpinnerCoutVarUnit);

        spinnerCoutVarAbon = new JSpinner();
        spinnerCoutVarAbon.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(10)));
        GridBagConstraints gbcSpinnerCoutVarAbon = new GridBagConstraints();
        gbcSpinnerCoutVarAbon.fill = GridBagConstraints.HORIZONTAL;
        gbcSpinnerCoutVarAbon.insets = new Insets(0, 0, 5, 5);
        gbcSpinnerCoutVarAbon.gridx = 3;
        gbcSpinnerCoutVarAbon.gridy = 12;
        panelLeft.add(spinnerCoutVarAbon, gbcSpinnerCoutVarAbon);

        // ----------------------------------------------------------------
        // 12. Nouvel/Ancien index
        // ----------------------------------------------------------------
        labelValIndex = new JLabel("Nouveau | Ancien Index :");
        GridBagConstraints gbcLabelValIndex = new GridBagConstraints();
        gbcLabelValIndex.anchor = GridBagConstraints.EAST;
        gbcLabelValIndex.insets = new Insets(0, 0, 5, 5);
        gbcLabelValIndex.gridx = 0;
        gbcLabelValIndex.gridy = 13;
        panelLeft.add(labelValIndex, gbcLabelValIndex);

        spinnerNouveauIndex = new JSpinner();
        spinnerNouveauIndex.setModel(new SpinnerNumberModel(Double.valueOf(0), null, null, Double.valueOf(5)));
        GridBagConstraints gbcSpinnerNouveauIndex = new GridBagConstraints();
        gbcSpinnerNouveauIndex.fill = GridBagConstraints.HORIZONTAL;
        gbcSpinnerNouveauIndex.insets = new Insets(0, 0, 5, 5);
        gbcSpinnerNouveauIndex.gridx = 1;
        gbcSpinnerNouveauIndex.gridy = 13;
        panelLeft.add(spinnerNouveauIndex, gbcSpinnerNouveauIndex);

        textFieldValeurAncienIndex = new JTextField();
        textFieldValeurAncienIndex.setEditable(false);
        GridBagConstraints gbcTextFieldValeurAncienIndex = new GridBagConstraints();
        gbcTextFieldValeurAncienIndex.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldValeurAncienIndex.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldValeurAncienIndex.gridx = 3;
        gbcTextFieldValeurAncienIndex.gridy = 13;
        panelLeft.add(textFieldValeurAncienIndex, gbcTextFieldValeurAncienIndex);
        textFieldValeurAncienIndex.setColumns(10);

        // ----------------------------------------------------------------
        // 13. Quittance pour un locataire
        // ----------------------------------------------------------------
        labelLocataire = new JLabel("Quittance pour :");
        GridBagConstraints gbcLabelLocataire = new GridBagConstraints();
        gbcLabelLocataire.anchor = GridBagConstraints.EAST;
        gbcLabelLocataire.insets = new Insets(0, 0, 5, 5);
        gbcLabelLocataire.gridx = 0;
        gbcLabelLocataire.gridy = 15;
        panelLeft.add(labelLocataire, gbcLabelLocataire);

        comboBoxLocataire = new JComboBox<>();
        GridBagConstraints gbcComboBoxLocataire = new GridBagConstraints();
        gbcComboBoxLocataire.gridwidth = 3;
        gbcComboBoxLocataire.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxLocataire.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxLocataire.gridx = 1;
        gbcComboBoxLocataire.gridy = 15;
        panelLeft.add(comboBoxLocataire, gbcComboBoxLocataire);

        // ----------------------------------------------------------------
        // 14. Assurance
        // ----------------------------------------------------------------
        labelAssu = new JLabel("Assurance :");
        labelAssu.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLabelAssu = new GridBagConstraints();
        gbcLabelAssu.anchor = GridBagConstraints.EAST;
        gbcLabelAssu.insets = new Insets(0, 0, 0, 5);
        gbcLabelAssu.gridx = 0;
        gbcLabelAssu.gridy = 16;
        panelLeft.add(labelAssu, gbcLabelAssu);

        comboBoxAssu = new JComboBox<>();
        GridBagConstraints gbcComboBoxAssu = new GridBagConstraints();
        gbcComboBoxAssu.gridwidth = 3;
        gbcComboBoxAssu.insets = new Insets(0, 0, 0, 5);
        gbcComboBoxAssu.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxAssu.gridx = 1;
        gbcComboBoxAssu.gridy = 16;
        panelLeft.add(comboBoxAssu, gbcComboBoxAssu);

        // ----------------------------------------------------------------
        // Panneau bas : boutons "OK" et "Annuler"
        // ----------------------------------------------------------------
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        annulerButton = new JButton("Annuler");
        annulerButton.setActionCommand("Cancel");
        buttonPane.add(annulerButton);

        // Déclenche l'ajout automatique de ligne dans la table
        addRowListenerToTable();

        // -------------------------------------------------
        // Initialisation des listeners et des combos
        // -------------------------------------------------
        // 1) Action sur la combo "Type"
        comboBoxType.addActionListener(e -> gestion.gestionComboType());
        // 2) Action sur la combo "ID Charge"
        comboBoxChoixCharge.addActionListener(e -> gestion.gestionComboIDCharge());
        // 3) Bouton OK
        okButton.addActionListener(e -> gestion.gestionBoutonOk());
        // 4) Bouton Annuler
        annulerButton.addActionListener(e -> gestion.gestionBoutonAnnuler());

        // Charge les combos en asynchrone
        gestion.chargerComboBox();
        // Gère la sélection IDCharge
        gestion.gestionComboID(comboBoxChoixCharge);
        // Action du bouton "Parcourir..."
        gestion.gestionBoutonParcourir(buttonParcourir, fileChooser);
    }

    /**
     * Ajoute un listener pour détecter si la dernière ligne du tableau est remplie.
     * Le cas échéant, on ajoute automatiquement une nouvelle ligne vide.
     */
    public void addRowListenerToTable() {
        tableLogements.getModel().addTableModelListener(e -> {
            int rowCount = tableLogements.getRowCount();
            // Vérifie si la dernière ligne est remplie
            boolean lastRowFilled = true;
            for(int col = 0; col < tableLogements.getColumnCount(); col++) {
                Object value = tableLogements.getValueAt(rowCount - 1, col);
                if(value == null || value.toString().trim().isEmpty()) {
                    lastRowFilled = false;
                    break;
                }
            }
            // Si la dernière ligne est remplie, on ajoute une nouvelle ligne vide
            if(lastRowFilled) {
                DefaultTableModel model = (DefaultTableModel) tableLogements.getModel();
                model.addRow(new Object[]{null, null});
            }
        });
    }

    // ------------------------------------------------------------------------
    // Getters pour récupérer les valeurs des champs
    // ------------------------------------------------------------------------

    /**
     * @return la valeur saisie dans le champ "Numéro de document"
     */
    public String getTextNumDoc() {
        return textFieldNumDoc.getText();
    }

    /**
     * @return la valeur saisie dans le champ "Date du document"
     */
    public String getTextDateDoc() {
        return textFieldDateDoc.getText();
    }

    /**
     * Retourne la liste des biens locatifs et la part des charges pour chacun.
     * @return une liste de listes {idBien, partCharge}
     */
    public List<List<Object>> getListeLogement() {
        int rowCount = tableLogements.getModel().getRowCount();
        List<List<Object>> result = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Object valLog = tableLogements.getValueAt(i, 0);
            Object valPart = tableLogements.getValueAt(i, 1);
            if(valLog != null && valPart != null) {
                String logement = (String) valLog;
                BigDecimal part = BigDecimal.valueOf((Float) valPart);
                result.add(Arrays.asList(logement, part));
            }
        }
        return result;
    }

    /**
     * @return la valeur saisie dans le champ "ID charge"
     */
    public String getTextIDCharge() {
        return textFieldIdCharge.getText();
    }

    /**
     * @return la valeur sélectionnée dans la combo "ID Charge"
     */
    public String getIdChargeCombo() {
        return String.valueOf(comboBoxChoixCharge.getSelectedItem());
    }

    /**
     * Modifie la valeur de l'ancien index dans le champ de texte dédié.
     * @param ancienne la nouvelle valeur (String)
     */
    public void setAncienneValeur(String ancienne) {
        textFieldValeurAncienIndex.setText(ancienne);
    }

    /**
     * @return le type de document (FACTURE, DEVIS, QUITTANCE, etc.)
     */
    public String getTypeDoc() {
        return String.valueOf(comboBoxType.getSelectedItem());
    }

    /**
     * @return le chemin (String) vers le fichier
     */
    public String getLienFichier() {
        return textFieldLienFichier.getText();
    }

    /**
     * @return l'identifiant de l'entreprise sélectionnée
     */
    public String getIDEntreprise() {
        return String.valueOf(comboBoxEntreprise.getSelectedItem());
    }

    /**
     * @return l'identifiant du bâtiment sélectionné
     */
    public String getIDBat() {
        return String.valueOf(comboBoxBat.getSelectedItem());
    }

    /**
     * @return true si la case "Récupérable locataire" est cochée, false sinon
     */
    public boolean estRecupLoc() {
        return checkBoxRecupLoc.isSelected();
    }

    /**
     * @return la valeur unitaire (float) convertie en BigDecimal
     */
    public BigDecimal getCoutVarUnit() {
        return new BigDecimal(String.valueOf (spinnerCoutVarUnit.getValue()));
    }

    /**
     * @return la valeur d'abonnement (float) convertie en BigDecimal
     */
    public BigDecimal getCoutVarAbon() {
        return new BigDecimal(String.valueOf(spinnerCoutVarAbon.getValue()));
    }

    /**
     * @return le nouvel index (double) converti en BigDecimal
     */
    public BigDecimal getValIndex() {
        return new BigDecimal( String.valueOf(spinnerNouveauIndex.getValue()));
    }

    /**
     * @return l'ancien index (String) converti en float et ensuite BigDecimal
     */
    public BigDecimal getAncienIndex() {
        String textValue = textFieldValeurAncienIndex.getText();
        if (textValue.isEmpty()) {
            textValue = "0.0";
        }
        return BigDecimal.valueOf(Float.valueOf(textValue));
    }

    /**
     * @return l'identifiant du locataire sélectionné
     */
    public String getIDLocataire() {
        return String.valueOf(comboBoxLocataire.getSelectedItem());
    }

    /**
     * @return l'identifiant de l'assurance sélectionnée
     */
    public String getIDAssu() {
        return String.valueOf(comboBoxAssu.getSelectedItem());
    }

    /**
     * @return la valeur saisie dans le champ "Nom / Type de charge"
     */
    public String getNomTypeCharge() {
        return textFieldTypeNomCharge.getText();
    }

    // ------------------------------------------------------------------------
    // Setters pour remplir les combos, spinner, etc.
    // ------------------------------------------------------------------------

    /**
     * Remplit la combo des types de document.
     * @param types la liste des types (strings)
     */
    public void setComboBoxTypes(List<String> types) {
        comboBoxType.removeAllItems();
        for(String type : types) {
            comboBoxType.addItem(type);
        }
    }

    /**
     * Remplit la combo des entreprises.
     * @param entreprises la liste d'entreprises (strings)
     */
    public void setComboBoxEntreprise(List<String> entreprises) {
        comboBoxEntreprise.removeAllItems();
        for(String entreprise : entreprises) {
            comboBoxEntreprise.addItem(entreprise);
        }
    }

    /**
     * Remplit la combo des bâtiments.
     * @param batiments la liste d'IDs de bâtiments
     */
    public void setComboBoxBat(List<String> batiments) {
        comboBoxBat.removeAllItems();
        for(String batiment : batiments) {
            comboBoxBat.addItem(batiment);
        }
    }

    /**
     * Remplit la combo des ID de charge.
     * @param charges la liste d'IDs de charge
     */
    public void setComboIDCharge(List<String> charges) {
        comboBoxChoixCharge.removeAllItems();
        for(String ch : charges) {
            comboBoxChoixCharge.addItem(ch);
        }
    }

    /**
     * Remplit la combo des locataires.
     * @param locataires la liste d'IDs locataires
     */
    public void setComboBoxLocataire(List<String> locataires) {
        comboBoxLocataire.removeAllItems();
        for(String locataire : locataires) {
            comboBoxLocataire.addItem(locataire);
        }
    }

    /**
     * Remplit la combo des assurances.
     * @param assurances liste de noms d'assurances
     */
    public void setComboBoxAssu(List<String> assurances) {
        comboBoxAssu.removeAllItems();
        for(String assu : assurances) {
            comboBoxAssu.addItem(assu);
        }
    }

    /**
     * Modifie la valeur du champ "Ancien Index".
     * @param val la nouvelle valeur
     */
    public void setTextAncienIndex(String val) {
        textFieldValeurAncienIndex.setText(val);
    }

    /**
     * Efface la valeur du champ "ID Charge".
     */
    public void clearTextIdCharge() {
        textFieldIdCharge.setText("");
    }

    /**
     * Modifie la valeur du spinner du coût unitaire.
     * @param val la nouvelle valeur
     */
    public void setValSpinnerUnit(BigDecimal val) {
        spinnerCoutVarUnit.setValue(val);
    }

    /**
     * Modifie la valeur du spinner du coût abonnement.
     * @param val la nouvelle valeur
     */
    public void setValSpinnerAbonn(BigDecimal val) {
        spinnerCoutVarAbon.setValue(val);
    }

    /**
     * Modifie la valeur du champ "Nom / Type de charge".
     * @param type le texte à afficher
     */
    public void setNomTypeCharge(String type) {
        textFieldTypeNomCharge.setText(type);
    }

    // ------------------------------------------------------------------------
    // Méthodes pour modifier l'affichage de certains champs selon le type choisi
    // ------------------------------------------------------------------------

    /**
     * Affiche ou masque les champs relatifs à l'entreprise.
     * @param visible true pour afficher, false pour masquer
     */
    public void setEntrepriseVisible(boolean visible) {
        labelEntreprise.setVisible(visible);
        comboBoxEntreprise.setVisible(visible);
    }

    /**
     * Affiche ou masque les champs relatifs à l'assurance.
     * @param visible true pour afficher, false pour masquer
     */
    public void setAssuranceVisible(boolean visible) {
        labelAssu.setVisible(visible);
        comboBoxAssu.setVisible(visible);
    }

    /**
     * Affiche ou masque les champs pour sélectionner un locataire.
     * @param visible true pour afficher, false pour masquer
     */
    public void setLocataireVisible(boolean visible) {
        labelLocataire.setVisible(visible);
        comboBoxLocataire.setVisible(visible);
    }

    /**
     * Affiche ou masque le spinner pour l'abonnement.
     * @param visible true pour afficher, false pour masquer
     */
    public void setCoutAbonnementVisible(boolean visible) {
        spinnerCoutVarAbon.setVisible(visible);
    }

    /**
     * Affiche ou masque les champs relatifs à l'index (nouveau / ancien).
     * @param visible true pour afficher, false pour masquer
     */
    public void setIndexVisible(boolean visible) {
        labelValIndex.setVisible(visible);
        spinnerNouveauIndex.setVisible(visible);
        textFieldValeurAncienIndex.setVisible(visible);
    }

    /**
     * Affiche ou masque le bloc permettant de saisir un ID charge ou d'en choisir un.
     * @param visible true pour afficher, false pour masquer
     */
    public void setIdChargeTotalVisible(boolean visible) {
        labelIdChargeIndex.setVisible(visible);
        labelOu.setVisible(visible);
        textFieldIdCharge.setVisible(visible);
        comboBoxChoixCharge.setVisible(visible);
        labelNomTypeCharge.setVisible(visible);
        textFieldTypeNomCharge.setVisible(visible);
    }

    /**
     * Affiche uniquement la saisie d'ID charge et nom/type de charge, en masquant la combo IDCharge.
     * @param visible true pour afficher, false pour masquer
     */
    public void setIdChargeNomVisible(boolean visible) {
        comboBoxChoixCharge.setVisible(false);
        labelOu.setVisible(false);
        textFieldIdCharge.setVisible(visible);
        labelIdChargeIndex.setVisible(visible);
        labelNomTypeCharge.setVisible(visible);
        textFieldTypeNomCharge.setVisible(visible);
    }

    /**
     * Permet de modifier le label de coût unitaire/abonnement.
     * @param text le texte à afficher
     */
    public void setTextLabelCout(String text) {
        labelCout.setText(text);
    }

    /**
     * Fixe la valeur du champ "Lien vers le fichier".
     * @param lien le chemin du fichier
     */
    public void setLienFichier(String lien) {
        textFieldLienFichier.setText(lien);
    }

    // ------------------------------------------------------------------------
    // Méthodes utilitaires
    // ------------------------------------------------------------------------

    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * @param message le message d'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
            this,
            "Erreur : \n" + message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
