package vue;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAjouterBail;
import java.awt.CardLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AjouterBail extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelAssocierBail;
    private JTable tableBauxActuels;
    private JTable tablePartsLoyer;
    public JTextField textFieldIdBail;
    public JTextField textFieldDateDebut;
    public JTextField textFieldDateFin;
    private JTextField textFieldDateArrivee;
    
    private CardLayout cardLayout;

    private GestionAjouterBail gestionFen;
    
    private AjouterLocataire fenPrecedente;
    private JTable tableTotal;
    private final ButtonGroup buttonGroup = new ButtonGroup();
  	private JRadioButton rdbtnNouveauBail;
    private JRadioButton rdbtnBailExistant;
    private JComboBox<String> comboBoxBiensLoc;
    
    public CardLayout getCardLayout() {
    	return this.cardLayout;
    }
    
    public JPanel getPanelAssocierBail() {
    	return this.panelAssocierBail;
    }
    
    public JRadioButton getRdbtnNouveauBail() {
		return rdbtnNouveauBail;
	}

	public JRadioButton getRdbtnBailExistant() {
		return rdbtnBailExistant;
	}
	
	public JTextField getTextFieldIdBail() {
		return textFieldIdBail;
	}

	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
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
	
	public JTextField getTextFieldDateArrivee() {
		return textFieldDateArrivee;
	}
	
	
	public JComboBox<String> getComboBoxBiensLoc() {
		return comboBoxBiensLoc;
	}

	/**
     * Create the frame.
     */
    public AjouterBail(AjouterLocataire al, AfficherLocatairesActuels afl) {
    	
    	this.gestionFen = new GestionAjouterBail(this,al, afl);
    	this.fenPrecedente = al;
    	
    	
        setBounds(0, 0, 670, 470);
        //721, 489
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

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
        
        this.panelAssocierBail = new JPanel();
        panelAssocierBail.setBounds(20, 102, 616, 249);
        contentPane.add(panelAssocierBail);
        cardLayout = new CardLayout();
        panelAssocierBail.setLayout(cardLayout);

        // Panel Encadré Baux existants
        JPanel panelBauxExistants = new JPanel();
        panelBauxExistants.setBorder(new TitledBorder(null, "Baux existants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBauxExistants.setBounds(10, 70, 348, 346);
        panelAssocierBail.add(panelBauxExistants, "bauxExistants");
        panelBauxExistants.setLayout(null);

        // Tableau des baux actuels
        JScrollPane scrollPaneBauxActuels = new JScrollPane();
        scrollPaneBauxActuels.setBounds(10, 46, 579, 74);
        panelBauxExistants.add(scrollPaneBauxActuels);

        tableBauxActuels = new JTable();
        tableBauxActuels.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null},
        		{null, null, null, null},
        		{null, null, null, null},
        		{null, null, null, null},
        		{null, null, null, null},
        		{null, null, null, null},
        	},
        	new String[] {
        		"Compl\u00E9ment", "Adresse", "Date de d\u00E9but", "Date de fin"
        	}
        ));
        this.gestionFen.remplirTableBauxExistant(tableBauxActuels);
        scrollPaneBauxActuels.setViewportView(tableBauxActuels);

        // Tableau des parts de loyer
        JScrollPane scrollPanePartsLoyer = new JScrollPane();
        scrollPanePartsLoyer.setBounds(10, 162, 250, 74);
        panelBauxExistants.add(scrollPanePartsLoyer);

        tablePartsLoyer = new JTable();
        tablePartsLoyer.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        	},
        	new String[] {
        		"Locataire", "Part de loyer en %"
        	}
        ));
        tablePartsLoyer.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePartsLoyer.getColumnModel().getColumn(1).setPreferredWidth(150);
        scrollPanePartsLoyer.setViewportView(tablePartsLoyer);
        
        tableTotal = new JTable();
        tableTotal.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null},
        		{null},
        		{"Total"},
        	},
        	new String[] {
        		""
        	}
        ));
        scrollPanePartsLoyer.setRowHeaderView(tableTotal);
        tableTotal.getColumnModel().getColumn(0).setResizable(false);
		tableTotal.setRowSelectionAllowed(false);
		tableTotal.setPreferredScrollableViewportSize(new Dimension(40, 0));
		tableTotal.setEnabled(false);
        
        
        JLabel lblDateArrivee = new JLabel("Date d'arrivée :");
        lblDateArrivee.setBounds(316, 162, 119, 20);
        panelBauxExistants.add(lblDateArrivee);
                
        textFieldDateArrivee = new JTextField();
        textFieldDateArrivee.setBounds(415, 162, 100, 20);
        panelBauxExistants.add(textFieldDateArrivee);
        textFieldDateArrivee.setColumns(10);
        
        JLabel lblListeLocataire = new JLabel("Liste des locataires associés au bail");
        lblListeLocataire.setBounds(10, 139, 323, 13);
        panelBauxExistants.add(lblListeLocataire);
        
        JLabel lblListeBaux = new JLabel("Liste des baux");
        lblListeBaux.setBounds(10, 23, 162, 13);
        panelBauxExistants.add(lblListeBaux);

        // Panel Encadré Nouveau Bail
        JPanel panelNouveauBail = new JPanel();
        panelNouveauBail.setBounds(378, 70, 250, 222);
        panelAssocierBail.add(panelNouveauBail, "nouveauBail");
        panelNouveauBail.setLayout(null);
        
        JPanel panelNouveauBail_champs = new JPanel();
        panelNouveauBail_champs.setBorder(new TitledBorder(null, "Nouveau Bail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelNouveauBail_champs.setBounds(181, 49, 256, 200);
        panelNouveauBail.add(panelNouveauBail_champs);
        panelNouveauBail_champs.setLayout(null);
        
        // Champs de saisie pour Nouveau Bail
        JLabel lblIdBail = new JLabel("ID Bail :");
        lblIdBail.setBounds(10, 25, 58, 13);
        panelNouveauBail_champs.add(lblIdBail);
                
        textFieldIdBail = new JTextField();
        textFieldIdBail.setBounds(135, 22, 100, 20);
        panelNouveauBail_champs.add(textFieldIdBail);
        textFieldIdBail.setColumns(10);
                       
        JLabel lblDateDebut = new JLabel("Date de début :");
        lblDateDebut.setBounds(10, 65, 100, 20);
        panelNouveauBail_champs.add(lblDateDebut);
                               
        textFieldDateDebut = new JTextField();
        textFieldDateDebut.setBounds(135, 66, 100, 20);
        panelNouveauBail_champs.add(textFieldDateDebut);
        textFieldDateDebut.setColumns(10);
        this.gestionFen.initialiserDateDebut(textFieldDateDebut);
                                        
        JLabel lblDateFin = new JLabel("Date de fin :");
        lblDateFin.setBounds(10, 111, 100, 20);
        panelNouveauBail_champs.add(lblDateFin);
                                                
        textFieldDateFin = new JTextField();
        textFieldDateFin.setBounds(135, 112, 100, 20);
        panelNouveauBail_champs.add(textFieldDateFin);
        textFieldDateFin.setColumns(10);
                                                       
        JButton btnVider = new JButton("Vider");
        btnVider.setBounds(161, 158, 74, 20);
        panelNouveauBail_champs.add(btnVider);
                                                       
        comboBoxBiensLoc = new JComboBox<String>();
        comboBoxBiensLoc.setBounds(302, 10, 123, 21);
        this.gestionFen.remplirJComboBoxBatiment(comboBoxBiensLoc);
        panelNouveauBail.add(comboBoxBiensLoc);
                                            
        JLabel lblBiensLoc = new JLabel("Bien locatif : ");
        lblBiensLoc.setLabelFor(comboBoxBiensLoc);
        lblBiensLoc.setBounds(192, 14, 117, 13);
        panelNouveauBail.add(lblBiensLoc);
        btnVider.addActionListener(this.gestionFen);
        
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(0, 371, 658, 37);
        contentPane.add(panelBoutons);
        
        // Boutons en bas au centre
        JButton btnContinuer = new JButton("Continuer");
        panelBoutons.add(btnContinuer);
              
        JButton btnAnnuler = new JButton("Annuler");
        panelBoutons.add(btnAnnuler);
        
        JPanel panelRadioBtn = new JPanel();
        panelRadioBtn.setBounds(10, 62, 648, 30);
        contentPane.add(panelRadioBtn);
        
        rdbtnNouveauBail = new JRadioButton("Créer un nouveau bail");
        buttonGroup.add(rdbtnNouveauBail);
        panelRadioBtn.add(rdbtnNouveauBail);
        
        rdbtnBailExistant = new JRadioButton("Rattacher à un bail existant");
        buttonGroup.add(rdbtnBailExistant);
        panelRadioBtn.add(rdbtnBailExistant);
        
        rdbtnNouveauBail.setSelected(true);
        cardLayout.show(panelAssocierBail, "nouveauBail");
        
        rdbtnBailExistant.addActionListener(this.gestionFen);

        rdbtnNouveauBail.addActionListener(this.gestionFen);
        
        btnAnnuler.addActionListener(this.gestionFen);
        btnContinuer.addActionListener(this.gestionFen);
    }

	public AjouterLocataire getFenPrecedente() {
		return fenPrecedente;
	}
}
