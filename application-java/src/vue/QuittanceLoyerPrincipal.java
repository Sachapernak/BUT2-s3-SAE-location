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
	private JTable chargeTable;
	private JSpinner yearSpinner;
	private JSpinner monthSpinner;

	public JTable getLoyerTable() {
		return loyerTable;
	}

    public JSpinner getYearSpinner() {
		return yearSpinner;
	}


	public JSpinner getMonthSpinner() {
		return monthSpinner;
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
    
    String[] loyerColumns = {"Identifiant Logement", "Date de Changement", "Montant Loyer"};
    DefaultTableModel loyerTableModel = new DefaultTableModel(loyerColumns, 0);
    loyerTable = new JTable(loyerTableModel);
    
    String[] chargeIndexColumns = {"ID", "Date de Changement", "Montant"};
    DefaultTableModel chargeIndexTableModel = new DefaultTableModel(chargeIndexColumns, 0);
    chargeTable = new JTable(chargeIndexTableModel);

    
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

    // JSpinner pour les années
    SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 1900, 2100, 1);
    yearSpinner = new JSpinner(yearModel);
    yearSpinner.addChangeListener(gestionTableQuittance);
    // JSpinner pour les mois
    SpinnerNumberModel monthModel = new SpinnerNumberModel(currentMonth, 1, 12, 1);
    monthSpinner = new JSpinner(monthModel);
    monthSpinner.addChangeListener(gestionTableQuittance);
 
    
    
    GridBagConstraints gbcDateDocLabel = new GridBagConstraints();
    gbcDateDocLabel.gridx = 0;
    gbcDateDocLabel.gridy = 2;
    gbcDateDocLabel.insets = new Insets(5, 5, 5, 5);
    gbcDateDocLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(dateDocLabel, gbcDateDocLabel);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
    JLabel chargeLabel = new JLabel("Charges :");
    GridBagConstraints gbcChargeLabel = new GridBagConstraints();
    gbcChargeLabel.gridx = 0;
    gbcChargeLabel.gridy = 5;
    gbcChargeLabel.gridwidth = 2;
    gbcChargeLabel.insets = new Insets(5, 5, 5, 5);
    gbcChargeLabel.anchor = GridBagConstraints.WEST;
    quittancePanel.add(chargeLabel, gbcChargeLabel);

    
    this.gestionTableQuittance.remplirTableCharge(null);
    JScrollPane chargeScrollPane = new JScrollPane(chargeTable);

    GridBagConstraints gbcChargeTable = new GridBagConstraints();
    gbcChargeTable.gridx = 0;
    gbcChargeTable.gridy = 6;
    gbcChargeTable.gridwidth = 2;
    gbcChargeTable.weightx = 1;
    gbcChargeTable.weighty = 0.3;
    gbcChargeTable.insets = new Insets(5, 5, 5, 5);
    gbcChargeTable.fill = GridBagConstraints.BOTH;
    quittancePanel.add(chargeScrollPane, gbcChargeTable);

 
    JButton saveButton = new JButton("Voir Quittance de Loyer");
    saveButton.addActionListener(gestionQuittanceLoyerLocataire);
    
    // Boutons
    JButton retourButton2 = new JButton("Precedent");
    retourButton2.addActionListener(gestionQuittanceLoyerLocataire);
    GridBagConstraints gbcRetourButton2 = new GridBagConstraints();
    gbcRetourButton2.insets = new Insets(0, 0, 0, 5);
    gbcRetourButton2.gridx = 0;
    gbcRetourButton2.gridy = 7;
    gbcRetourButton2.anchor = GridBagConstraints.CENTER;
    quittancePanel.add(retourButton2, gbcRetourButton2);
    
    GridBagConstraints gbcSaveButton = new GridBagConstraints();
    gbcSaveButton.gridx = 1;
    gbcSaveButton.gridy = 7;
    gbcSaveButton.insets = new Insets(10, 5, 0, 5);
    gbcSaveButton.anchor = GridBagConstraints.CENTER;
    quittancePanel.add(saveButton, gbcSaveButton);
}


}
