package vue;


import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import controleur.GestionQuittanceLoyerLocataire;
import controleur.GestionTableQuittance;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class QuittanceLoyerPrincipal extends JInternalFrame {

    private JTable tableLocataires;
    private JTable tableBiensActuels;
    private JTable tableBiensAnciens;
    private JTable loyerTable;
    private JTextField searchField;
    private GestionTableQuittance gestionTableQuittance;
    private GestionQuittanceLoyerLocataire gestionQuittanceLoyerLocataire;
    private Boolean estActuel =true;
    private JPanel cardPanel;
    private JPanel mainPanel;
	private JTextField numeroDocField;
	private JTextField dateDocField;
	private JTextField partChargesField;
	private JTable chargeIndexTable;
	private JTable chargeCFTable;
	private JSpinner yearSpinner;
	private JSpinner monthSpinner;
	private JSpinner daySpinner;

	public JTable getLoyerTable() {
		return loyerTable;
	}

    public JSpinner getYearSpinner() {
		return yearSpinner;
	}


	public JSpinner getMonthSpinner() {
		return monthSpinner;
	}


	public JSpinner getDaySpinner() {
		return daySpinner;
	}


	public JTable getChargeIndexTable() {
		return chargeIndexTable;
	}


	public JTable getChargeCFTable() {
		return chargeCFTable;
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
    
    String[] loyerColumns = {"Identifiant Logement", "Date de Changement", "Montant Loyer"};
    DefaultTableModel loyerTableModel = new DefaultTableModel(loyerColumns, 0);
    loyerTable = new JTable(loyerTableModel);
    
    String[] chargeIndexColumns = {"ID", "Date de Relevé", "Type", "Valeur Compteur", "Coût Variable", "Coût Fixe"};
    DefaultTableModel chargeIndexTableModel = new DefaultTableModel(chargeIndexColumns, 0);
    chargeIndexTable = new JTable(chargeIndexTableModel);
    
    String[] chargeCFColumns = {"ID", "Date de Charge", "Type", "Montant"};
    DefaultTableModel chargeCFTableModel = new DefaultTableModel(chargeCFColumns, 0);
    chargeCFTable = new JTable(chargeCFTableModel);
    
	gestionTableQuittance = new GestionTableQuittance(this);
	gestionQuittanceLoyerLocataire = new GestionQuittanceLoyerLocataire(this);
	mainPanel = new JPanel(new CardLayout());
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(mainPanel, BorderLayout.CENTER);
	
	 // Page principale
    JPanel homePanel = new JPanel(new GridBagLayout());
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
    JPanel quittancePanel = new JPanel(new GridBagLayout());
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
    gbcNumeroDocField.insets = new Insets(5, 5, 5, 5);
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
    
    // Champ pour la date du document
    JLabel dateDocLabel = new JLabel("Date du Document:");

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // Les mois commencent à 0
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

    // JSpinner pour les années
    SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 1900, 2100, 1);
    yearSpinner = new JSpinner(yearModel);
    yearSpinner.addChangeListener(gestionTableQuittance);
    // JSpinner pour les mois
    SpinnerNumberModel monthModel = new SpinnerNumberModel(currentMonth, 1, 12, 1);
    monthSpinner = new JSpinner(monthModel);
    monthSpinner.addChangeListener(gestionTableQuittance);
    // JSpinner pour les jours
    SpinnerNumberModel dayModel = new SpinnerNumberModel(currentDay, 1, 31, 1);
    daySpinner = new JSpinner(dayModel);
    daySpinner.addChangeListener(gestionTableQuittance);
    
    JLabel partChargesLabel = new JLabel("Part des Charges:");
    GridBagConstraints gbcpartChargesLabel = new GridBagConstraints();
    gbcpartChargesLabel.anchor = GridBagConstraints.WEST;
    gbcpartChargesLabel.insets = new Insets(5, 5, 5, 5);
    gbcpartChargesLabel.gridx = 0;
    gbcpartChargesLabel.gridy = 1;
    quittancePanel.add(partChargesLabel, gbcpartChargesLabel);
    partChargesField = new JTextField(20);
    
        GridBagConstraints gbcpartChargesField = new GridBagConstraints();
        gbcpartChargesField.fill = GridBagConstraints.HORIZONTAL;
        gbcpartChargesField.insets = new Insets(5, 5, 5, 5);
        gbcpartChargesField.gridx = 1;
        gbcpartChargesField.gridy = 1;
        quittancePanel.add(partChargesField, gbcpartChargesField);
    
    GridBagConstraints gbcDateDocLabel = new GridBagConstraints();
    gbcDateDocLabel.gridx = 0;
    gbcDateDocLabel.gridy = 2;
    gbcDateDocLabel.insets = new Insets(5, 5, 5, 5);
    gbcDateDocLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(dateDocLabel, gbcDateDocLabel);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    datePanel.add(new JLabel("Jour:"));
    datePanel.add(daySpinner);
    datePanel.add(new JLabel("Mois:"));
    datePanel.add(monthSpinner);
    datePanel.add(new JLabel("Année:"));
    datePanel.add(yearSpinner);

    GridBagConstraints gbcDatePanel = new GridBagConstraints();
    gbcDatePanel.gridx = 1;
    gbcDatePanel.gridy = 2;
    gbcDatePanel.weightx = 1;
    gbcDatePanel.insets = new Insets(5, 5, 5, 5);
    gbcDatePanel.fill = GridBagConstraints.HORIZONTAL;
    quittancePanel.add(datePanel, gbcDatePanel);

    // Tableau des loyers
    JLabel loyerLabel = new JLabel("Loyers:");
    GridBagConstraints gbcLoyerLabel = new GridBagConstraints();
    gbcLoyerLabel.gridx = 0;
    gbcLoyerLabel.gridy = 3;
    gbcLoyerLabel.gridwidth = 2;
    gbcLoyerLabel.insets = new Insets(5, 5, 5, 5);
    gbcLoyerLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(loyerLabel, gbcLoyerLabel);

    
    this.gestionTableQuittance.remplirTableLoyer(null);
    JScrollPane loyerScrollPane = new JScrollPane(loyerTable);

    GridBagConstraints gbcLoyerTable = new GridBagConstraints();
    gbcLoyerTable.gridx = 0;
    gbcLoyerTable.gridy = 4;
    gbcLoyerTable.gridwidth = 2;
    gbcLoyerTable.weightx = 1;
    gbcLoyerTable.weighty = 0.3;
    gbcLoyerTable.insets = new Insets(5, 5, 5, 5);
    gbcLoyerTable.fill = GridBagConstraints.BOTH;
    quittancePanel.add(loyerScrollPane, gbcLoyerTable);

    // Tableau des charges indexées
    JLabel chargeIndexLabel = new JLabel("Charges Indexées:");
    GridBagConstraints gbcChargeIndexLabel = new GridBagConstraints();
    gbcChargeIndexLabel.gridx = 0;
    gbcChargeIndexLabel.gridy = 5;
    gbcChargeIndexLabel.gridwidth = 2;
    gbcChargeIndexLabel.insets = new Insets(5, 5, 5, 5);
    gbcChargeIndexLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(chargeIndexLabel, gbcChargeIndexLabel);

    
    this.gestionTableQuittance.remplirTableChargeIndex(null);
    JScrollPane chargeIndexScrollPane = new JScrollPane(chargeIndexTable);

    GridBagConstraints gbcChargeIndexTable = new GridBagConstraints();
    gbcChargeIndexTable.gridx = 0;
    gbcChargeIndexTable.gridy = 6;
    gbcChargeIndexTable.gridwidth = 2;
    gbcChargeIndexTable.weightx = 1;
    gbcChargeIndexTable.weighty = 0.3;
    gbcChargeIndexTable.insets = new Insets(5, 5, 5, 5);
    gbcChargeIndexTable.fill = GridBagConstraints.BOTH;
    quittancePanel.add(chargeIndexScrollPane, gbcChargeIndexTable);

    // Tableau des charges fixes
    JLabel chargeCFLabel = new JLabel("Charges Fixes:");
    GridBagConstraints gbcChargeCFLabel = new GridBagConstraints();
    gbcChargeCFLabel.gridx = 0;
    gbcChargeCFLabel.gridy = 7;
    gbcChargeCFLabel.gridwidth = 2;
    gbcChargeCFLabel.insets = new Insets(5, 5, 5, 5);
    gbcChargeCFLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(chargeCFLabel, gbcChargeCFLabel);

    
    this.gestionTableQuittance.remplirTableChargeFixe(null);
    JScrollPane chargeCFScrollPane = new JScrollPane(chargeCFTable);

    GridBagConstraints gbcChargeCFTable = new GridBagConstraints();
    gbcChargeCFTable.gridx = 0;
    gbcChargeCFTable.gridy = 8;
    gbcChargeCFTable.gridwidth = 2;
    gbcChargeCFTable.weightx = 1;
    gbcChargeCFTable.weighty = 0.3;
    gbcChargeCFTable.insets = new Insets(5, 5, 5, 5);
    gbcChargeCFTable.fill = GridBagConstraints.BOTH;
    quittancePanel.add(chargeCFScrollPane, gbcChargeCFTable);
    gbcRetourButton.gridx = 0;
    gbcRetourButton.gridy = 9;
    gbcRetourButton.insets = new Insets(10, 5, 5, 5);
    gbcRetourButton.anchor = GridBagConstraints.CENTER;

    JButton saveButton = new JButton("Voir Quittance de Loyer");
    saveButton.addActionListener(gestionQuittanceLoyerLocataire);
    
    // Boutons
    JButton retourButton2 = new JButton("Precedent");
    retourButton2.addActionListener(gestionQuittanceLoyerLocataire);
    GridBagConstraints gbcRetourButton2 = new GridBagConstraints();
    gbcRetourButton2.insets = new Insets(0, 0, 0, 5);
    gbcRetourButton2.gridx = 0;
    gbcRetourButton2.gridy = 9;
    quittancePanel.add(retourButton2, gbcRetourButton2);
    
    GridBagConstraints gbcSaveButton = new GridBagConstraints();
    gbcSaveButton.gridx = 1;
    gbcSaveButton.gridy = 9;
    gbcSaveButton.insets = new Insets(10, 5, 0, 5);
    gbcSaveButton.anchor = GridBagConstraints.CENTER;
    quittancePanel.add(saveButton, gbcSaveButton);
}


}
