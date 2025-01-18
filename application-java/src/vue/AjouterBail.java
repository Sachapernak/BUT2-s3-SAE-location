package vue;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAjouterBail;
import controleur.GestionTablesAjouterBail;

/**
 * Vue permettant l'ajout/rattachement d'un bail pour un nouveau locataire.
 * <p>
 * Utilise un {@link CardLayout} pour afficher soit la section
 * "Baux existants", soit "Nouveau Bail".
 */
public class AjouterBail extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Composants principaux de la vue
    private JPanel contentPane;
    private JPanel panelAssocierBail;
    private JTable tableBauxActuels;
    private JTable tablePartsLoyer;
    private JTable tableTotal;
    private JTextField textFieldIdBail;
    private JTextField textFieldDateDebut;
    private JTextField textFieldDateFin;
    private JTextField textFieldDateArrivee;
    private JComboBox<String> comboBoxBiensLoc;
    private JLabel lblMessageErreur;

    // Layout principal pour basculer entre "bauxExistants" et "nouveauBail"
    private CardLayout cardLayout;

    // Groupement de boutons radio
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rdbtnNouveauBail;
    private JRadioButton rdbtnBailExistant;

    // Contrôleurs
    private GestionAjouterBail gestionFen;
    private GestionTablesAjouterBail gestionTableAjoutBail;

    // Vue précédente
    private AjouterLocataire fenPrecedente;
    private JTextField textFieldCharge;

    /**
     * Constructeur principal de la fenêtre "AjouterBail".
     *
     * @param al  la vue {@link AjouterLocataire} associée
     * @param afl la vue {@link AfficherLocatairesActuels} pour lister les locataires
     */
    public AjouterBail(AjouterLocataire al, AfficherLocatairesActuels afl) {
        // Instanciation des contrôleurs
        this.gestionFen = new GestionAjouterBail(this, al, afl);
        this.gestionTableAjoutBail = new GestionTablesAjouterBail(this, al);
        this.fenPrecedente = al;

        // Configuration de la fenêtre
        setBounds(0, 0, 670, 470);
        setClosable(true); // Permet de fermer la fenêtre interne
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Titre
        JLabel lblTitreAjoutLoc = new JLabel("Ajouter un nouveau locataire 2/3");
        lblTitreAjoutLoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitreAjoutLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitreAjoutLoc.setBounds(0, 10, 658, 30);
        contentPane.add(lblTitreAjoutLoc);

        JLabel lblSousTitre = new JLabel("Associer le locataire à un bail");
        lblSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSousTitre.setBounds(10, 40, 648, 20);
        contentPane.add(lblSousTitre);

        // Panel principal permettant de basculer entre "bauxExistants" et "nouveauBail"
        panelAssocierBail = new JPanel();
        panelAssocierBail.setBounds(20, 102, 616, 259);
        contentPane.add(panelAssocierBail);

        cardLayout = new CardLayout();
        panelAssocierBail.setLayout(cardLayout);

        // --------------------------------------------------------------------
        // 1. Panel "Baux existants"
        // --------------------------------------------------------------------
        JPanel panelBauxExistants = new JPanel();
        panelBauxExistants.setBorder(new TitledBorder(null, "Baux existants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBauxExistants.setLayout(null);
        panelAssocierBail.add(panelBauxExistants, "bauxExistants");

        JScrollPane scrollPaneBauxActuels = new JScrollPane();
        scrollPaneBauxActuels.setBounds(10, 46, 579, 74);
        panelBauxExistants.add(scrollPaneBauxActuels);

        tableBauxActuels = new JTable();
        tableBauxActuels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBauxActuels.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null},
        	},
        	new String[] {
        		"Identifiant bail", "Compl\u00E9ment", "Adresse", "Date de d\u00E9but", "Date de fin"
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false, false, false, false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        scrollPaneBauxActuels.setViewportView(tableBauxActuels);

        // Remplir la table des baux existants via le contrôleur "gestionTableAjoutBail"
        gestionTableAjoutBail.remplirTableBauxExistant(tableBauxActuels);
        tableBauxActuels.getSelectionModel().addListSelectionListener(gestionTableAjoutBail);

        // Tableau des parts de loyer
        JScrollPane scrollPanePartsLoyer = new JScrollPane();
        scrollPanePartsLoyer.setBounds(10, 162, 250, 74);
        panelBauxExistants.add(scrollPanePartsLoyer);

        tablePartsLoyer = new JTable();
        tablePartsLoyer.setToolTipText("Veuillez mettre à jour les parts de loyer");
        tablePartsLoyer.addKeyListener(gestionTableAjoutBail);
        tablePartsLoyer.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null},
        	},
        	new String[] {
        		"Locataire", "Part de loyer en %"
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        tablePartsLoyer.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePartsLoyer.getColumnModel().getColumn(1).setPreferredWidth(150);
        scrollPanePartsLoyer.setViewportView(tablePartsLoyer);

        // Table "total" en en-tête de ligne
        tableTotal = new JTable();
        tableTotal.setModel(new DefaultTableModel(
        	new Object[][] {
        		{""},
        	},
        	new String[] {
        		""
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        tableTotal.getColumnModel().getColumn(0).setResizable(false);
        scrollPanePartsLoyer.setRowHeaderView(tableTotal);
        tableTotal.setRowSelectionAllowed(false);
        tableTotal.setPreferredScrollableViewportSize(new Dimension(40, 0));
        tableTotal.setEnabled(false);

        JLabel lblDateArrivee = new JLabel("Date d'arrivée* :");
        lblDateArrivee.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDateArrivee.setBounds(280, 160, 119, 20);
        panelBauxExistants.add(lblDateArrivee);

        textFieldDateArrivee = new JTextField();
        textFieldDateArrivee.setBounds(415, 162, 100, 20);
        panelBauxExistants.add(textFieldDateArrivee);

        JLabel lblListeLocataire = new JLabel("Liste des locataires associés au bail");
        lblListeLocataire.setBounds(10, 139, 323, 13);
        panelBauxExistants.add(lblListeLocataire);

        JLabel lblListeBaux = new JLabel("Liste des baux");
        lblListeBaux.setBounds(10, 23, 162, 13);
        panelBauxExistants.add(lblListeBaux);

        lblMessageErreur = new JLabel(" ");
        lblMessageErreur.setForeground(Color.RED);
        lblMessageErreur.setHorizontalAlignment(SwingConstants.LEFT);
        lblMessageErreur.setBounds(9, 240, 390, 13);
        panelBauxExistants.add(lblMessageErreur);

        // --------------------------------------------------------------------
        // 2. Panel "Nouveau Bail"
        // --------------------------------------------------------------------
        JPanel panelNouveauBail = new JPanel();
        panelNouveauBail.setLayout(null);
        panelAssocierBail.add(panelNouveauBail, "nouveauBail");

        // Sous-panel pour les champs du nouveau bail
        JPanel panelNouveauBailChamps = new JPanel();
        panelNouveauBailChamps.setBorder(new TitledBorder(null, "Nouveau Bail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelNouveauBailChamps.setBounds(181, 85, 256, 148);
        panelNouveauBail.add(panelNouveauBailChamps);
        panelNouveauBailChamps.setLayout(null);

        // ID Bail
        JLabel lblIdBail = new JLabel("ID Bail* :");
        lblIdBail.setBounds(10, 27, 58, 13);
        panelNouveauBailChamps.add(lblIdBail);

        textFieldIdBail = new JTextField();
        textFieldIdBail.setBounds(135, 24, 100, 20);
        textFieldIdBail.setColumns(10);
        panelNouveauBailChamps.add(textFieldIdBail);

        // Date de début
        JLabel lblDateDebut = new JLabel("Date de début* :");
        lblDateDebut.setBounds(10, 53, 100, 20);
        panelNouveauBailChamps.add(lblDateDebut);

        textFieldDateDebut = new JTextField();
        textFieldDateDebut.setBounds(135, 54, 100, 20);
        textFieldDateDebut.setColumns(10);
        panelNouveauBailChamps.add(textFieldDateDebut);

        // Initialisation de la date de début via le contrôleur
        gestionFen.initialiserDateDebut(textFieldDateDebut);

        // Date de fin
        JLabel lblDateFin = new JLabel("Date de fin :");
        lblDateFin.setBounds(10, 83, 100, 20);
        panelNouveauBailChamps.add(lblDateFin);

        textFieldDateFin = new JTextField();
        textFieldDateFin.setBounds(135, 84, 100, 20);
        textFieldDateFin.setColumns(10);
        panelNouveauBailChamps.add(textFieldDateFin);

        // Bouton "Vider"
        JButton btnVider = new JButton("Vider");
        btnVider.setBounds(161, 114, 74, 20);
        panelNouveauBailChamps.add(btnVider);
        btnVider.addActionListener(gestionFen);

        // ComboBox des biens locatifs
        comboBoxBiensLoc = new JComboBox<>();
        comboBoxBiensLoc.setBounds(266, 10, 123, 21);
        panelNouveauBail.add(comboBoxBiensLoc);

        JLabel lblBiensLoc = new JLabel("Bien locatif : ");
        lblBiensLoc.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBiensLoc.setLabelFor(comboBoxBiensLoc);
        lblBiensLoc.setBounds(136, 14, 117, 13);
        panelNouveauBail.add(lblBiensLoc);

        // Le contrôleur remplit la comboBox
        gestionFen.remplirJComboBoxBatiment(comboBoxBiensLoc);
        
        JLabel lblCharge = new JLabel("Montant charges* :");
        lblCharge.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCharge.setBounds(150, 44, 100, 13);
        panelNouveauBail.add(lblCharge);
        
        textFieldCharge = new JTextField();
        textFieldCharge.setColumns(10);
        textFieldCharge.setBounds(266, 41, 100, 19);
        panelNouveauBail.add(textFieldCharge);
        
        JLabel lblEuros = new JLabel("€");
        lblEuros.setBounds(376, 44, 45, 13);
        panelNouveauBail.add(lblEuros);

        // --------------------------------------------------------------------
        // Panel pour les boutons en bas (Continuer, Annuler)
        // --------------------------------------------------------------------
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(0, 371, 658, 37);
        contentPane.add(panelBoutons);

        JButton btnContinuer = new JButton("Continuer");
        panelBoutons.add(btnContinuer);

        JButton btnAnnuler = new JButton("Annuler");
        panelBoutons.add(btnAnnuler);

        // Panel pour les boutons radio
        JPanel panelRadioBtn = new JPanel();
        panelRadioBtn.setBounds(10, 62, 648, 30);
        contentPane.add(panelRadioBtn);

        rdbtnNouveauBail = new JRadioButton("Créer un nouveau bail");
        buttonGroup.add(rdbtnNouveauBail);
        panelRadioBtn.add(rdbtnNouveauBail);

        rdbtnBailExistant = new JRadioButton("Rattacher à un bail existant");
        buttonGroup.add(rdbtnBailExistant);
        panelRadioBtn.add(rdbtnBailExistant);

        // Par défaut, le bouton "Nouveau Bail" est sélectionné
        rdbtnNouveauBail.setSelected(true);
        cardLayout.show(panelAssocierBail, "nouveauBail");

        // Écouteurs sur les boutons radio et boutons "Continuer"/"Annuler"
        rdbtnBailExistant.addActionListener(gestionFen);
        rdbtnNouveauBail.addActionListener(gestionFen);
        btnAnnuler.addActionListener(gestionFen);
        btnContinuer.addActionListener(gestionFen);
    }

    /**
     * Retourne la vue précédente, {@link AjouterLocataire}.
     */
    public AjouterLocataire getFenPrecedente() {
        return fenPrecedente;
    }

    /**
     * Affiche une boîte de dialogue avec un message d'erreur.
     *
     * @param message le texte à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Getters/Setters uniques pour les champs (ÉVITER LES DOUBLONS)
    // -------------------------------------------------------------------------

    public String getTextIdBail() {
        return textFieldIdBail.getText();
    }

    public void setTextIdBail(String text) {
        textFieldIdBail.setText(text);
    }

    public String getTextDateDebut() {
        return textFieldDateDebut.getText();
    }

    public void setTextDateDebut(String text) {
        textFieldDateDebut.setText(text);
    }

    public String getTextDateFin() {
        return textFieldDateFin.getText();
    }

    public void setTextDateFin(String text) {
        textFieldDateFin.setText(text);
    }
    
    public String getTextCharges() {
        return textFieldCharge.getText();
    }

    public void setTextCharges(String text) {
        textFieldCharge.setText(text);
    }

    public String getTextDateArrivee() {
        return textFieldDateArrivee.getText();
    }

    public JTable getTableBauxActuels() {
        return tableBauxActuels;
    }

    public void setTableBauxActuels(JTable tableBauxActuels) {
        this.tableBauxActuels = tableBauxActuels;
    }

    public JTable getTablePartsLoyer() {
        return tablePartsLoyer;
    }

    public void setTablePartsLoyer(JTable tablePartsLoyer) {
        this.tablePartsLoyer = tablePartsLoyer;
    }

    public JTable getTableTotal() {
        return tableTotal;
    }

    public JComboBox<String> getComboBoxBiensLoc() {
        return comboBoxBiensLoc;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getPanelAssocierBail() {
        return panelAssocierBail;
    }

    public JRadioButton getRdbtnNouveauBail() {
        return rdbtnNouveauBail;
    }

    public JRadioButton getRdbtnBailExistant() {
        return rdbtnBailExistant;
    }

    public JLabel getLblMessageErreur() {
        return lblMessageErreur;
    }

    // -------------------------------------------------------------------------
    // Méthodes d'aide pour le contrôleur
    // -------------------------------------------------------------------------

    /**
     * Retourne la liste des champs obligatoires pour un nouveau bail.
     * @return liste de chaînes représentant les valeurs des champs obligatoires
     */
    public List<String> getChampsObligatoiresNouveauBail() {
        List<String> res = new ArrayList<>();
        res.add(getTextCharges());
        res.add(getTextIdBail());
        res.add(getTextDateDebut());
        return res;
    }

    /**
     * Retourne la liste des champs de date (arrivée, début, fin).
     * @return liste des valeurs de ces champs
     */
    public List<String> getChampsDate() {
        List<String> res = new ArrayList<>();
        res.add(getTextDateArrivee());
        res.add(getTextDateDebut());
        res.add(getTextDateFin());
        return res;
    }
}
