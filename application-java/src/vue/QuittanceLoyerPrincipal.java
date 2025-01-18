package vue;


import javax.swing.*;


import javax.swing.table.DefaultTableModel;

import controleur.GestionQuittanceLoyerLocataire;
import controleur.GestionTableQuittance;

import java.awt.*;
import java.util.Calendar;

public class QuittanceLoyerPrincipal extends JInternalFrame {

    private JTable tableLocataires;
    private JTable tableBiensActuels;
    private JTable tableBiensAnciens;
    private JTextField searchField;
    private GestionTableQuittance gestionTableQuittance;
    private GestionQuittanceLoyerLocataire gestionQuittanceLoyerLocataire;
    private Boolean estActuel =true;
    private JPanel cardPanel;
    private JPanel mainPanel;
	private JTextField numeroDocField;
	private JTextField dateDocField;
	private JTextField partChargesField;
	private JTable chargeTable;
	private JSpinner yearSpinner1;
	private JSpinner monthSpinner1;
	private JSpinner daySpinner1;
	private JSpinner yearSpinner2;
	private JSpinner monthSpinner2;
	private JSpinner daySpinner2;
	private JTable DocComptLoyerTable;

	

    public JTable getDocComptLoyerTable() {
		return DocComptLoyerTable;
	}


	public GestionTableQuittance getGestionTableQuittance() {
		return gestionTableQuittance;
	}

	

	public JSpinner getDaySpinner1() {
		return daySpinner1;
	}


	public JSpinner getMonthSpinner2() {
		return monthSpinner2;
	}


	public JSpinner getYearSpinner2() {
		return yearSpinner2;
	}


	public JSpinner getDaySpinner2() {
		return daySpinner2;
	}


	public JSpinner getYearSpinner() {
		return yearSpinner1;
	}


	public JSpinner getMonthSpinner() {
		return monthSpinner1;
	}
	

	public JTable getChargeTable() {
		return chargeTable;
	}


	public JPanel getmainPanel(){
    	return mainPanel;
    }
    
    
    public JPanel getCardPanel() {
    	return cardPanel;
    }
    
    
 
    public Boolean getEstActuel() {
		return estActuel;
	}

	public void setEstActuel(Boolean estActuel) {
		this.estActuel = estActuel;
	}
	
		/**
	 * @return the tableLocataires
	 */
	public JTable getTableLocataires() {
		return tableLocataires;
	}
	
	/**
	 * @return the tableBiensActuels
	 */
	public JTable getTableBiensActuels() {
		return tableBiensActuels;
	}
	
	/**
	 * @return the tableBiensAnciens
	 */
	public JTable getTableBiensAnciens() {
		return tableBiensAnciens;
	}
	
	/**
	 * @return the searchField
	 */
	public JTextField getSearchField() {
		return searchField;
	}
	
	
	public String getNumeroDoc() {
	    return numeroDocField.getText();
	}
	
	public String getDateDoc() {
	    return dateDocField.getText();
	}
	
	
	
	public String getPartCharges() {
	    return partChargesField.getText();
	}

public QuittanceLoyerPrincipal() {
	super("Gestion des Locataires", true, true, true, true);
	setBounds(25, 25, 670, 490);
	
	String[] locataireColumns = {"ID", "Nom", "Prénom", "Email"};
    DefaultTableModel locataireTableModel = new DefaultTableModel(locataireColumns, 0);
    tableLocataires = new JTable(locataireTableModel);
    
    String[] bienColumns = {"Identifiant","Date d'entr\u00E9e", "Type ", "Batiment", "Adresse compl\u00E8te", "Loyer", "Parts de loyer", "Derni\u00E8re r\u00E9gularisation"};
    DefaultTableModel biensActuelsModel = new DefaultTableModel(bienColumns, 0);
    tableBiensActuels = new JTable(biensActuelsModel);
    
    DefaultTableModel biensAnciensModel = new DefaultTableModel(bienColumns, 0);
    tableBiensAnciens = new JTable(biensAnciensModel);
    
    String[] chargeIndexColumns = {"ID", "Date de Changement", "Montant"};
    DefaultTableModel chargeIndexTableModel = new DefaultTableModel(chargeIndexColumns, 0);
    chargeTable = new JTable(chargeIndexTableModel);
    
    String[] docComptLoyerColumns = {"NumDoc","DateDoc","Montant"};
    DefaultTableModel docComptLoyerTableModel = new DefaultTableModel(docComptLoyerColumns, 0);
    DocComptLoyerTable = new JTable(docComptLoyerTableModel);
	gestionTableQuittance = new GestionTableQuittance(this);
	gestionQuittanceLoyerLocataire = new GestionQuittanceLoyerLocataire(this);
	mainPanel = new JPanel(new CardLayout());
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(mainPanel, BorderLayout.CENTER);
	
	 // Page principale
    GridBagLayout gbl_homePanel = new GridBagLayout();
    gbl_homePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    gbl_homePanel.rowHeights = new int[]{0, 0, 34, 0, 0, 0};
    JPanel homePanel = new JPanel(gbl_homePanel);
    mainPanel.add(homePanel, "Home");
    
	// Champ de recherche
    searchField = new JTextField();
    JButton searchButton = new JButton("Rechercher");

    GridBagConstraints gbcSearchField = new GridBagConstraints();
    gbcSearchField.gridx = 0;
    gbcSearchField.gridy = 0;
    gbcSearchField.weightx = 0.8;
    gbcSearchField.insets = new Insets(5, 5, 5, 5);
    gbcSearchField.fill = GridBagConstraints.HORIZONTAL;
    homePanel.add(searchField, gbcSearchField);

    GridBagConstraints gbcSearchButton = new GridBagConstraints();
    gbcSearchButton.gridx = 1;
    gbcSearchButton.gridy = 0;
    gbcSearchButton.weightx = 0.2;
    gbcSearchButton.insets = new Insets(5, 5, 5, 5);
    gbcSearchButton.fill = GridBagConstraints.HORIZONTAL;
    homePanel.add(searchButton, gbcSearchButton);

    // Label pour le tableau des locataires
    JLabel locataireLabel = new JLabel("Liste des Locataires :");
    GridBagConstraints gbcLocataireLabel = new GridBagConstraints();
    gbcLocataireLabel.gridx = 0;
    gbcLocataireLabel.gridy = 1;
    gbcLocataireLabel.gridwidth = 2;
    gbcLocataireLabel.insets = new Insets(5, 5, 5, 5);
    gbcLocataireLabel.anchor = GridBagConstraints.WEST;
    homePanel.add(locataireLabel, gbcLocataireLabel);

    // Tableau des locataires
    gestionTableQuittance.remplirTableLocataires();
    tableLocataires.getSelectionModel().addListSelectionListener(this.gestionTableQuittance);
    JScrollPane locataireScrollPane = new JScrollPane(tableLocataires);

    GridBagConstraints gbcLocataireScrollPane = new GridBagConstraints();
    gbcLocataireScrollPane.gridx = 0;
    gbcLocataireScrollPane.gridy = 2;
    gbcLocataireScrollPane.gridwidth = 2;
    gbcLocataireScrollPane.weightx = 1;
    gbcLocataireScrollPane.weighty = 0.5;
    gbcLocataireScrollPane.insets = new Insets(5, 5, 5, 5);
    gbcLocataireScrollPane.fill = GridBagConstraints.BOTH;
    homePanel.add(locataireScrollPane, gbcLocataireScrollPane);

    // CardLayout pour les tableaux des biens
    cardPanel = new JPanel(new CardLayout());

    // Tableau des biens actuels
    JScrollPane biensActuelsScrollPane = new JScrollPane(tableBiensActuels);
    cardPanel.add(biensActuelsScrollPane, "Biens Actuels");

    // Tableau des biens anciens
    JScrollPane biensAnciensScrollPane = new JScrollPane(tableBiensAnciens);
    cardPanel.add(biensAnciensScrollPane, "Biens Anciens");

    GridBagConstraints gbcCardPanel = new GridBagConstraints();
    gbcCardPanel.gridx = 0;
    gbcCardPanel.gridy = 3;
    gbcCardPanel.gridwidth = 2;
    gbcCardPanel.weightx = 1;
    gbcCardPanel.weighty = 0.5;
    gbcCardPanel.insets = new Insets(5, 5, 5, 5);
    gbcCardPanel.fill = GridBagConstraints.BOTH;
    homePanel.add(cardPanel, gbcCardPanel);

    // Boutons radio pour sélectionner le tableau
    JRadioButton biensActuelsButton = new JRadioButton("Biens Actuels");
    JRadioButton biensAnciensButton = new JRadioButton("Biens Anciens");
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(biensActuelsButton);
    buttonGroup.add(biensAnciensButton);

    // Ajouter les boutons radio
    JPanel radioPanel = new JPanel();
    radioPanel.add(biensActuelsButton);
    radioPanel.add(biensAnciensButton);

    GridBagConstraints gbcRadioPanel = new GridBagConstraints();
    gbcRadioPanel.gridx = 0;
    gbcRadioPanel.gridy = 4;
    gbcRadioPanel.gridwidth = 2;
    gbcRadioPanel.insets = new Insets(5, 5, 5, 5);
    gbcRadioPanel.fill = GridBagConstraints.HORIZONTAL;
    homePanel.add(radioPanel, gbcRadioPanel);

    biensActuelsButton.addActionListener(gestionQuittanceLoyerLocataire);

    biensAnciensButton.addActionListener(gestionQuittanceLoyerLocataire);

    // Boutons Retour et Voir Quittance de Loyer
    JButton retourButton = new JButton("Retour");
    JButton voirQuittanceButton = new JButton("Suivant");
    retourButton.addActionListener(gestionQuittanceLoyerLocataire);
    voirQuittanceButton.addActionListener(gestionQuittanceLoyerLocataire);
    GridBagConstraints gbcRetourButton = new GridBagConstraints();
    gbcRetourButton.gridx = 0;
    gbcRetourButton.gridy = 5;
    gbcRetourButton.weightx = 0.5;
    gbcRetourButton.insets = new Insets(5, 5, 5, 5);
    gbcRetourButton.fill = GridBagConstraints.HORIZONTAL;
    homePanel.add(retourButton, gbcRetourButton);

    GridBagConstraints gbcVoirQuittanceButton = new GridBagConstraints();
    gbcVoirQuittanceButton.gridx = 1;
    gbcVoirQuittanceButton.gridy = 5;
    gbcVoirQuittanceButton.weightx = 0.5;
    gbcVoirQuittanceButton.insets = new Insets(5, 5, 5, 5);
    gbcVoirQuittanceButton.fill = GridBagConstraints.HORIZONTAL;
    homePanel.add(voirQuittanceButton, gbcVoirQuittanceButton);


    // Initialiser les boutons radio
    biensActuelsButton.setSelected(true);
    CardLayout cl = (CardLayout) (cardPanel.getLayout());
    cl.show(cardPanel, "Biens Actuels");
    
    // Page de quittance
    GridBagLayout gbl_quittancePanel = new GridBagLayout();
    gbl_quittancePanel.columnWidths = new int[]{41, 0};
    gbl_quittancePanel.rowHeights = new int[]{0, 50, 0, 0, 131, 9, 120, 0, 0};
    gbl_quittancePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    gbl_quittancePanel.columnWeights = new double[]{0.0, 1.0};
    JPanel quittancePanel = new JPanel(gbl_quittancePanel);
    mainPanel.add(quittancePanel, "Quittance");
    

    // Champ pour le numéro de document
    JLabel numeroDocLabel = new JLabel("Numéro de Document:");
    numeroDocField = new JTextField(20);

    GridBagConstraints gbcNumeroDocLabel = new GridBagConstraints();
    gbcNumeroDocLabel.gridx = 0;
    gbcNumeroDocLabel.gridy = 0;
    gbcNumeroDocLabel.insets = new Insets(5, 5, 5, 5);
    gbcNumeroDocLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(numeroDocLabel, gbcNumeroDocLabel);

    GridBagConstraints gbcNumeroDocField = new GridBagConstraints();
    gbcNumeroDocField.gridx = 1;
    gbcNumeroDocField.gridy = 0;
    gbcNumeroDocField.weightx = 1;
    gbcNumeroDocField.insets = new Insets(5, 5, 5, 0);
    gbcNumeroDocField.fill = GridBagConstraints.HORIZONTAL;
    quittancePanel.add(numeroDocField, gbcNumeroDocField);
    gbcNumeroDocLabel.gridx = 0;
    gbcNumeroDocLabel.gridy = 1;
    gbcNumeroDocLabel.insets = new Insets(5, 5, 5, 5);
    gbcNumeroDocLabel.anchor = GridBagConstraints.WEST;
    gbcNumeroDocField.gridx = 1;
    gbcNumeroDocField.gridy = 1;
    gbcNumeroDocField.weightx = 1;
    gbcNumeroDocField.insets = new Insets(5, 5, 5, 5);
    gbcNumeroDocField.fill = GridBagConstraints.HORIZONTAL;

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    // JSpinner pour les années
    SpinnerNumberModel yearModel1 = new SpinnerNumberModel(currentYear, 1900, 2100, 1);
    // JSpinner pour les mois
    SpinnerNumberModel monthModel1 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
 // JSpinner pour les années
    SpinnerNumberModel dayModel1 = new SpinnerNumberModel(currentDay, 1, 31, 1);
    // JSpinner pour les mois
    SpinnerNumberModel monthModel2 = new SpinnerNumberModel(currentMonth, 1, 12, 1);
 // JSpinner pour les années
    SpinnerNumberModel yearModel2 = new SpinnerNumberModel(currentYear, 1900, 2100, 1);
    // JSpinner pour les mois
    SpinnerNumberModel dayModel2 = new SpinnerNumberModel(currentDay+1, 1, 31, 1);
    
    // Champ pour la date du document
    JLabel dateDocLabel = new JLabel("Date du Document entre:");
    
       
       
       GridBagConstraints gbcDateDocLabel = new GridBagConstraints();
       gbcDateDocLabel.gridx = 0;
       gbcDateDocLabel.gridy = 1;
       gbcDateDocLabel.insets = new Insets(5, 5, 5, 5);
       gbcDateDocLabel.anchor = GridBagConstraints.WEST;
       quittancePanel.add(dateDocLabel, gbcDateDocLabel);
       yearSpinner1 = new JSpinner(yearModel1);
       yearSpinner1.addChangeListener(gestionTableQuittance);
       monthSpinner1 = new JSpinner(monthModel1);
       monthSpinner1.addChangeListener(gestionTableQuittance);
       daySpinner1 = new JSpinner(dayModel1);
       daySpinner1.addChangeListener(gestionTableQuittance);
       yearSpinner2 = new JSpinner(yearModel2);
       yearSpinner2.addChangeListener(gestionTableQuittance);
       monthSpinner2 = new JSpinner(monthModel2);
       monthSpinner2.addChangeListener(gestionTableQuittance);
       daySpinner2 = new JSpinner(dayModel2);
       daySpinner2.addChangeListener(gestionTableQuittance);
    
    
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        datePanel.add(new JLabel("Jour:"));
        datePanel.add(daySpinner1);
        datePanel.add(new JLabel("Mois:"));
        datePanel.add(monthSpinner1);
        datePanel.add(new JLabel("Année:"));
        datePanel.add(yearSpinner1);
        
        
        
        GridBagConstraints gbcDatePanel = new GridBagConstraints();
        gbcDatePanel.gridx = 1;
        gbcDatePanel.gridy = 1;
        gbcDatePanel.weightx = 1;
        gbcDatePanel.insets = new Insets(5, 5, 5, 0);
        gbcDatePanel.fill = GridBagConstraints.HORIZONTAL;
        quittancePanel.add(datePanel, gbcDatePanel);
    
    JLabel lblEt = new JLabel(" et :");
    GridBagConstraints gbc_lblEt = new GridBagConstraints();
    gbc_lblEt.anchor = GridBagConstraints.WEST;
    gbc_lblEt.insets = new Insets(0, 0, 5, 5);
    gbc_lblEt.gridx = 0;
    gbc_lblEt.gridy = 2;
    quittancePanel.add(lblEt, gbc_lblEt);
    
    JPanel date2panel = new JPanel();
    GridBagConstraints gbc_panel = new GridBagConstraints();
    gbc_panel.anchor = GridBagConstraints.WEST;
    gbc_panel.insets = new Insets(0, 0, 5, 0);
    gbc_panel.gridx = 1;
    gbc_panel.gridy = 2;
    quittancePanel.add(date2panel, gbc_panel);
    
    JLabel label = new JLabel(" Jour:");
    date2panel.add(label);
    
    date2panel.add(daySpinner2);
    
    JLabel label_1 = new JLabel("Mois:");
    date2panel.add(label_1);
    
    date2panel.add(monthSpinner2);
    
    JLabel label_2 = new JLabel("Année:");
    date2panel.add(label_2);
    
    date2panel.add(yearSpinner2);
    
    
    
    
    // Tableau des charges indexées
    JLabel chargeLabel = new JLabel("Charges :");
    GridBagConstraints gbcChargeLabel = new GridBagConstraints();
    gbcChargeLabel.gridx = 0;
    gbcChargeLabel.gridy = 3;
    gbcChargeLabel.gridwidth = 2;
    gbcChargeLabel.insets = new Insets(5, 5, 5, 0);
    gbcChargeLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(chargeLabel, gbcChargeLabel);
    
    
    
    chargeTable.getSelectionModel().addListSelectionListener(this.gestionTableQuittance);
    JScrollPane chargeScrollPane = new JScrollPane(chargeTable);
    
    GridBagConstraints gbcChargeTable = new GridBagConstraints();
    gbcChargeTable.gridx = 0;
    gbcChargeTable.gridy = 4;
    gbcChargeTable.gridwidth = 2;
    gbcChargeTable.weightx = 1;
    gbcChargeTable.weighty = 0.3;
    gbcChargeTable.insets = new Insets(5, 5, 5, 0);
    gbcChargeTable.fill = GridBagConstraints.BOTH;
    quittancePanel.add(chargeScrollPane, gbcChargeTable);
    
    JLabel DocumentComptableLoyer = new JLabel("Document Comptable Loyer :");
    DocumentComptableLoyer.setHorizontalAlignment(SwingConstants.CENTER);
    GridBagConstraints gbc_DocumentComptableLoyer = new GridBagConstraints();
    gbc_DocumentComptableLoyer.anchor = GridBagConstraints.NORTHWEST;
    gbc_DocumentComptableLoyer.insets = new Insets(0, 0, 5, 5);
    gbc_DocumentComptableLoyer.gridx = 0;
    gbc_DocumentComptableLoyer.gridy = 5;
    quittancePanel.add(DocumentComptableLoyer, gbc_DocumentComptableLoyer);
    
    
    
    
    JScrollPane DocComptLoyerScrollPane = new JScrollPane(DocComptLoyerTable);
    GridBagConstraints gbc_DocComptLoyerScrollPane = new GridBagConstraints();
    gbc_DocComptLoyerScrollPane.gridx = 0;
    gbc_DocComptLoyerScrollPane.gridy = 6;
    gbc_DocComptLoyerScrollPane.gridwidth = 2;
    gbc_DocComptLoyerScrollPane.weightx = 1;
    gbc_DocComptLoyerScrollPane.weighty = 0.5;
    gbc_DocComptLoyerScrollPane.insets = new Insets(5, 5, 5, 0);
    gbc_DocComptLoyerScrollPane.fill = GridBagConstraints.BOTH;
    quittancePanel.add(DocComptLoyerScrollPane, gbc_DocComptLoyerScrollPane);
    
    // Boutons
    JButton retourButton2 = new JButton("Precedent");
    retourButton2.addActionListener(gestionQuittanceLoyerLocataire);
    
    GridBagConstraints gbcPrecButton = new GridBagConstraints();
    gbcPrecButton.gridx = 0;
    gbcPrecButton.gridy = 7;
    gbcPrecButton.insets = new Insets(10, 5, 5, 5);
    gbcPrecButton.anchor = GridBagConstraints.CENTER;
    quittancePanel.add(retourButton2, gbcPrecButton);
    
    JButton saveButton = new JButton("Voir Quittance de Loyer");
    saveButton.addActionListener(gestionQuittanceLoyerLocataire);
    GridBagConstraints gbcSaveButton = new GridBagConstraints();
    gbcSaveButton.gridx = 1;
    gbcSaveButton.gridy = 7;
    gbcSaveButton.insets = new Insets(10, 5, 5, 0);
    gbcSaveButton.anchor = GridBagConstraints.CENTER;
    quittancePanel.add(saveButton, gbcSaveButton);
}


}
