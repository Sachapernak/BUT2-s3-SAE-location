package vue;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionFenetrePrincipale;
import controleur.GestionMenu;
import controleur.GestionTablesFenetrePrincipale;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

public class FenetrePrincipale extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GestionFenetrePrincipale gestionClic;
	private GestionTablesFenetrePrincipale gestionTable;
	private GestionMenu gestionMenu;
	private JTable tableBiensLoc;
	private JTable tableBatiment;
	private JLabel lblEtatConnexion;

	public JTable getTableBiensLoc() {
		return this.tableBiensLoc;
	}
	
	public JTable getTableBatiment() {
		return this.tableBatiment;
	}
	
	public void setEtatConnexion(Color couleur) {
		this.lblEtatConnexion.setForeground(couleur);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetrePrincipale frame = new FenetrePrincipale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetrePrincipale() {
		
		this.gestionClic = new GestionFenetrePrincipale(this);
		this.gestionMenu = new GestionMenu(this);
		this.gestionTable = new GestionTablesFenetrePrincipale(this);
		
		this.gestionClic.afficherPageConnexion();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(70, 130, 180));
		menuBar.setBounds(0, 0, 746, 22);
		contentPane.add(menuBar);
		
		JMenu fichier = new JMenu("Fichier");
		fichier.setForeground(new Color(255, 255, 255));
		menuBar.add(fichier);
		
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.setForeground(new Color(0, 0, 0));
		fichier.add(quitter);
		quitter.addActionListener(this.gestionMenu);
				
		JMenu locataires = new JMenu("Locataires");
		locataires.setForeground(new Color(255, 255, 255));
		menuBar.add(locataires);
		
		JMenuItem mntmAnciensLocataires = new JMenuItem("Liste des anciens locataires");
		mntmAnciensLocataires.addActionListener(this.gestionMenu);
		
		JMenuItem mntmLocatairesActuels = new JMenuItem("Liste des locataires actuels");
		mntmLocatairesActuels.addActionListener(this.gestionMenu);
		locataires.add(mntmLocatairesActuels);
		locataires.add(mntmAnciensLocataires);

		JMenuItem mntnQuittances = new JMenuItem("Quittances de loyer");
		mntnQuittances.addActionListener(this.gestionMenu);
		locataires.add(mntnQuittances);
		
		JMenuItem mntnSoldeToutCompte = new JMenuItem("Solde de tout comptes");
		mntnSoldeToutCompte.addActionListener(this.gestionMenu);
		locataires.add(mntnSoldeToutCompte);

		JMenuItem mntnBaux = new JMenuItem("Liste des baux");
		locataires.add(mntnBaux);
		mntnBaux.addActionListener(this.gestionMenu);
		
		JMenu menuloyersCharges = new JMenu("Charge et loyers");
		menuloyersCharges.setForeground(new Color(255, 255, 255));
		menuloyersCharges.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menuloyersCharges);
		
		JMenuItem mntmConsulterCharges = new JMenuItem("Consulter les charges");
		mntmConsulterCharges.addActionListener(this.gestionMenu);
		menuloyersCharges.add(mntmConsulterCharges);
		
		JMenuItem mntmConsulterLoyers = new JMenuItem("Consulter les loyers");
		mntmConsulterLoyers.addActionListener(this.gestionMenu);
		menuloyersCharges.add(mntmConsulterLoyers);
		
		JMenuItem mntmChargerLoyers = new JMenuItem("Charger les loyers");
		mntmChargerLoyers.addActionListener(this.gestionMenu);
		
		JMenuItem mntmAugmenterLoyers = new JMenuItem("Augmenter les loyers");
		menuloyersCharges.add(mntmAugmenterLoyers);
		mntmAugmenterLoyers.addActionListener(this.gestionMenu);
		menuloyersCharges.add(mntmChargerLoyers);
		
		JMenuItem mntmICC = new JMenuItem("Consulter ICC");
		menuloyersCharges.add(mntmICC);
		
		JMenuItem mntmRegularisationCharges = new JMenuItem("Régulariser les charges");
		mntmRegularisationCharges.addActionListener(this.gestionMenu);
		menuloyersCharges.add(mntmRegularisationCharges);
		mntmICC.addActionListener(this.gestionMenu);
		
		JMenu declarationFiscale = new JMenu("Déclaration fiscale");
		declarationFiscale.setForeground(new Color(255, 255, 255));
		declarationFiscale.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(declarationFiscale);
		declarationFiscale.addActionListener(this.gestionMenu);
		
		JMenu mnAdministratif = new JMenu("Administratif");
		mnAdministratif.setForeground(new Color(255, 255, 255));
		menuBar.add(mnAdministratif);
		
		JMenuItem mntmAfficherEntreprises = new JMenuItem("Afficher les entreprises");
		mnAdministratif.add(mntmAfficherEntreprises);
		
		JMenuItem mntmAssurances = new JMenuItem("Afficher les assurances");
		mntmAssurances.addActionListener(this.gestionMenu);
		mnAdministratif.add(mntmAssurances);
		mntmAfficherEntreprises.addActionListener(this.gestionMenu);
		
		menuBar.add(Box.createHorizontalGlue());
		JMenu configurationConnexion = new JMenu(" ");
		ImageIcon originalIcon = new ImageIcon("images/iconeEngrenage.png");
		Image imageRedim = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconeRedim = new ImageIcon(imageRedim);
		configurationConnexion.setIcon(iconeRedim);
		menuBar.add(configurationConnexion);
		
		JMenuItem itemConfigurationConnexion = new JMenuItem("Configurer la connexion");
		itemConfigurationConnexion.addActionListener(this.gestionMenu);
		configurationConnexion.add(itemConfigurationConnexion);
		
		JPanel panelBiensLocatifs = new JPanel();
		panelBiensLocatifs.setBounds(25, 264, 679, 266);
		contentPane.add(panelBiensLocatifs);
		panelBiensLocatifs.setLayout(null);
		
		JLabel lblBiensLoc = new JLabel("Liste des biens locatifs :");
		lblBiensLoc.setForeground(new Color(0, 0, 0));
		lblBiensLoc.setBounds(0, 10, 700, 13);
		panelBiensLocatifs.add(lblBiensLoc);
		
		JScrollPane scrollPaneBiensLoc = new JScrollPane();
		scrollPaneBiensLoc.setBounds(0, 39, 596, 154);
		panelBiensLocatifs.add(scrollPaneBiensLoc);
		
		tableBiensLoc = new JTable();
		tableBiensLoc.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null}
			},
			new String[] {
				"Identifiant", "Nb de pi\u00E8ces", "Surface", "Type", "Compl\u00E9ment d'adresse", "Loyer de base", "Loyer actuel"
			}
		));
		tableBiensLoc.getColumnModel().getColumn(2).setPreferredWidth(65);
		tableBiensLoc.getColumnModel().getColumn(2).setMaxWidth(65);
		scrollPaneBiensLoc.setViewportView(tableBiensLoc);
		
		JButton btnAugmenterLoyer = new JButton("Augmenter le loyer");
		btnAugmenterLoyer.addActionListener(this.gestionClic);
		btnAugmenterLoyer.setBounds(0, 213, 190, 21);
		panelBiensLocatifs.add(btnAugmenterLoyer);
		
		JButton btnAfficherLesCharges = new JButton("Consulter charges");
		btnAfficherLesCharges.addActionListener(this.gestionClic);
		btnAfficherLesCharges.setBounds(406, 213, 190, 21);
		panelBiensLocatifs.add(btnAfficherLesCharges);
		
		JButton btnModifierCharges = new JButton("Modifier charges");
		btnModifierCharges.addActionListener(this.gestionClic);
		btnModifierCharges.setBounds(206, 212, 190, 23);
		panelBiensLocatifs.add(btnModifierCharges);
		
		
		JLabel lblTitre = new JLabel("Bienvenue");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setBounds(0, 54, 746, 13);
		contentPane.add(lblTitre);
		
		JPanel panelBien = new JPanel();
		panelBien.setBounds(25, 92, 679, 151);
		contentPane.add(panelBien);
		panelBien.setLayout(null);
		
		JLabel lblBatiment = new JLabel("Liste des batiments :");
		lblBatiment.setBounds(0, 10, 147, 13);
		panelBien.add(lblBatiment);
		
		JScrollPane scrollPaneBatiment = new JScrollPane();
		scrollPaneBatiment.setBounds(0, 33, 454, 88);
		panelBien.add(scrollPaneBatiment);
		
		tableBatiment = new JTable();
		tableBatiment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableBatiment.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Identifiant", "Adresse", "Nb de biens locatifs"
			}
		));
		TableColumnModel columnModel = tableBatiment.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); 
		columnModel.getColumn(1).setPreferredWidth(250); 
		columnModel.getColumn(2).setPreferredWidth(120); 
		tableBatiment.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		this.gestionTable.remplirBatiments(tableBatiment);
		tableBatiment.getSelectionModel().addListSelectionListener(this.gestionTable);
		scrollPaneBatiment.setViewportView(tableBatiment);
		
		JButton btnAjoutBatiment = new JButton("Ajouter un batiment");
		btnAjoutBatiment.addActionListener(this.gestionClic);
		btnAjoutBatiment.setBounds(488, 36, 178, 21);
		panelBien.add(btnAjoutBatiment);
		
		JButton btnCharger = new JButton("Charger");
		btnCharger.addActionListener(this.gestionClic);
		btnCharger.setBounds(488, 100, 178, 21);
		panelBien.add(btnCharger);
		
		JButton btnAjouterBienLoc = new JButton("Ajouter un bien locatif");
		btnAjouterBienLoc.setBounds(488, 68, 178, 23);
		panelBien.add(btnAjouterBienLoc);
		btnAjouterBienLoc.addActionListener(this.gestionClic);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(25, 253, 679, 2);
		contentPane.add(separator);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("images/logo-immeuble.png"));
		lblLogo.setBounds(265, 42, 55, 47);
		contentPane.add(lblLogo);
	}
	
	/**
	 * Renvoie l'identifiant du logement de la ligne sélectionnée dans la table des biens locatifs.
	 *
	 * @return L'identifiant du logement sélectionné, ou null si aucune ligne n'est sélectionnée.
	 */
	public String getValeurIdTableLogement() {
		return getIdTable(tableBiensLoc);
	}
	
	/**
	 * Renvoie l'identifiant du batiment de la ligne sélectionnée dans la table des biens locatifs.
	 *
	 * @return L'identifiant du batiment sélectionné, ou null si aucune ligne n'est sélectionnée.
	 */
	public String getValeurIdTableBat() {
		
		return getIdTable(tableBatiment);
	}
	
	// Utilitaire pour eviter la duplication de code
	private String getIdTable(JTable table) {
	    int selectedRow = table.getSelectedRow(); // Récupère l'index de la ligne sélectionnée
	    if (selectedRow != -1) { // Vérifie qu'une ligne est bien sélectionnée
	        return table.getValueAt(selectedRow, 0).toString(); // Récupère la valeur de la première colonne
	    }
	    return null;
	}
	
    /**
     * Affiche une boîte de dialogue d'erreur.
     * 
     * @param message contenu de l'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

}