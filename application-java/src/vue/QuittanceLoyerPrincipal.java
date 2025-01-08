package vue;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.GestionQuittanceLoyerLocataire;
import controleur.GestionTableQuittance;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuittanceLoyerPrincipal extends JInternalFrame {

    private JTable tableLocataires;
    private JTable tableBiensActuels;
    private JTable tableBiensAnciens;
    private JTextField searchField;
    private GestionTableQuittance gestionTableQuittance;
    private GestionQuittanceLoyerLocataire gestionQuittanceLoyerLocataire;
    private Boolean estActuel =true;
    private JPanel cardPanel;
    
    
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
    
	gestionTableQuittance = new GestionTableQuittance(this);
	gestionQuittanceLoyerLocataire = new GestionQuittanceLoyerLocataire(this);
	getContentPane().setLayout(new GridBagLayout());
	// Champ de recherche
    searchField = new JTextField();
    JButton searchButton = new JButton("Rechercher");

    GridBagConstraints gbcSearchField = new GridBagConstraints();
    gbcSearchField.gridx = 0;
    gbcSearchField.gridy = 0;
    gbcSearchField.weightx = 0.8;
    gbcSearchField.insets = new Insets(5, 5, 5, 5);
    gbcSearchField.fill = GridBagConstraints.HORIZONTAL;
    add(searchField, gbcSearchField);

    GridBagConstraints gbcSearchButton = new GridBagConstraints();
    gbcSearchButton.gridx = 1;
    gbcSearchButton.gridy = 0;
    gbcSearchButton.weightx = 0.2;
    gbcSearchButton.insets = new Insets(5, 5, 5, 5);
    gbcSearchButton.fill = GridBagConstraints.HORIZONTAL;
    add(searchButton, gbcSearchButton);

    // Label pour le tableau des locataires
    JLabel locataireLabel = new JLabel("Liste des Locataires :");
    GridBagConstraints gbcLocataireLabel = new GridBagConstraints();
    gbcLocataireLabel.gridx = 0;
    gbcLocataireLabel.gridy = 1;
    gbcLocataireLabel.gridwidth = 2;
    gbcLocataireLabel.insets = new Insets(5, 5, 5, 5);
    gbcLocataireLabel.anchor = GridBagConstraints.WEST;
    add(locataireLabel, gbcLocataireLabel);

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
    add(locataireScrollPane, gbcLocataireScrollPane);

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
    add(cardPanel, gbcCardPanel);

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
    add(radioPanel, gbcRadioPanel);

    biensActuelsButton.addActionListener(gestionQuittanceLoyerLocataire);

    biensAnciensButton.addActionListener(gestionQuittanceLoyerLocataire);

    // Boutons Retour et Voir Quittance de Loyer
    JButton retourButton = new JButton("Retour");
    JButton voirQuittanceButton = new JButton("Voir Quittance de Loyer");
    retourButton.addActionListener(gestionQuittanceLoyerLocataire);
    voirQuittanceButton.addActionListener(gestionQuittanceLoyerLocataire);
    GridBagConstraints gbcRetourButton = new GridBagConstraints();
    gbcRetourButton.gridx = 0;
    gbcRetourButton.gridy = 5;
    gbcRetourButton.weightx = 0.5;
    gbcRetourButton.insets = new Insets(5, 5, 5, 5);
    gbcRetourButton.fill = GridBagConstraints.HORIZONTAL;
    add(retourButton, gbcRetourButton);

    GridBagConstraints gbcVoirQuittanceButton = new GridBagConstraints();
    gbcVoirQuittanceButton.gridx = 1;
    gbcVoirQuittanceButton.gridy = 5;
    gbcVoirQuittanceButton.weightx = 0.5;
    gbcVoirQuittanceButton.insets = new Insets(5, 5, 5, 5);
    gbcVoirQuittanceButton.fill = GridBagConstraints.HORIZONTAL;
    add(voirQuittanceButton, gbcVoirQuittanceButton);


    // Initialiser les boutons radio
    biensActuelsButton.setSelected(true);
    CardLayout cl = (CardLayout) (cardPanel.getLayout());
    cl.show(cardPanel, "Biens Actuels");
}

    // TODO: Charger les données dans les tableaux
}
