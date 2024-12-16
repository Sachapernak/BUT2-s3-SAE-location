package vue;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionFenetrePrincipale;
import controleur.GestionMenu;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;


public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GestionFenetrePrincipale gestionClic;
	private GestionMenu gestionMenu;
	private JTable tableBiensLoc;
	private JTable tableBatiment;

	public JTable getTableBiensLoc() {
		return this.tableBiensLoc;
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JMenu archives = new JMenu("Archives");
		archives.setForeground(new Color(255, 255, 255));
		archives.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(archives);
		
		JMenuItem mntmArchiverDocs = new JMenuItem("Consulter les documents");
		archives.add(mntmArchiverDocs);
		
		JMenuItem mntmArchiverDocumentComptable = new JMenuItem("Archiver les documents comptables");
		archives.add(mntmArchiverDocumentComptable);
		mntmArchiverDocs.addActionListener(this.gestionMenu);
		
		JMenu reglesMetier = new JMenu("Règles métier");
		reglesMetier.setForeground(new Color(255, 255, 255));
		menuBar.add(reglesMetier);
		
		JMenuItem mntmReglesMetier = new JMenuItem("Afficher les règles métier");
		reglesMetier.add(mntmReglesMetier);
		mntmReglesMetier.addActionListener(this.gestionMenu);
		
		JMenu declarationFiscale = new JMenu("Déclaration fiscale");
		declarationFiscale.setForeground(new Color(255, 255, 255));
		declarationFiscale.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(declarationFiscale);
		declarationFiscale.addActionListener(this.gestionMenu);
		
		menuBar.add(Box.createHorizontalGlue());
		JMenu configurationConnexion = new JMenu(" ");
		ImageIcon originalIcon = new ImageIcon("C:\\Users\\d_eri\\Documents\\Cours\\BUT_2A\\SAE_3.01\\SAE\\images\\iconeEngrenage.png");
		Image ImageRedim = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon IconeRedim = new ImageIcon(ImageRedim);
		configurationConnexion.setIcon(IconeRedim);
		menuBar.add(configurationConnexion);
		
		JMenuItem itemConfigurationConnexion = new JMenuItem("Configurer la connexion");
		itemConfigurationConnexion.addActionListener(this.gestionMenu);
		configurationConnexion.add(itemConfigurationConnexion);
		
		JPanel panel_biensLocatifs = new JPanel();
		panel_biensLocatifs.setBounds(25, 264, 679, 266);
		contentPane.add(panel_biensLocatifs);
		panel_biensLocatifs.setLayout(null);
		
		JLabel lblBiensLoc = new JLabel("Liste des logements :");
		lblBiensLoc.setForeground(new Color(0, 0, 0));
		lblBiensLoc.setBounds(0, 10, 700, 13);
		panel_biensLocatifs.add(lblBiensLoc);
		
		JScrollPane scrollPaneBiensLoc = new JScrollPane();
		scrollPaneBiensLoc.setBounds(10, 39, 586, 154);
		panel_biensLocatifs.add(scrollPaneBiensLoc);
		
		tableBiensLoc = new JTable();
		tableBiensLoc.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
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
		btnAugmenterLoyer.setBounds(10, 213, 165, 21);
		panel_biensLocatifs.add(btnAugmenterLoyer);
		
		JButton btnAfficherLesCharges = new JButton("Afficher les charges");
		btnAfficherLesCharges.addActionListener(this.gestionClic);
		btnAfficherLesCharges.setBounds(201, 213, 165, 21);
		panel_biensLocatifs.add(btnAfficherLesCharges);
		
		
		JLabel lblTitre = new JLabel("Bienvenue");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setBounds(0, 54, 746, 13);
		contentPane.add(lblTitre);
		
		JPanel panel_bien = new JPanel();
		panel_bien.setBounds(25, 92, 679, 151);
		contentPane.add(panel_bien);
		panel_bien.setLayout(null);
		
		JLabel lblBatiment = new JLabel("Liste des biens :");
		lblBatiment.setBounds(0, 10, 127, 13);
		panel_bien.add(lblBatiment);
		
		JScrollPane scrollPaneBatiment = new JScrollPane();
		scrollPaneBatiment.setBounds(10, 33, 444, 88);
		panel_bien.add(scrollPaneBatiment);
		
		tableBatiment = new JTable();
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
				"Identifiant", "Adresse", "Nb de logements"
			}
		));
		this.gestionClic.remplirBatiments(tableBatiment);
		scrollPaneBatiment.setViewportView(tableBatiment);
		
		JButton btnAjoutBien = new JButton("Ajouter un bien");
		btnAjoutBien.setBounds(488, 36, 165, 21);
		panel_bien.add(btnAjoutBien);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(25, 253, 679, 2);
		contentPane.add(separator);
	}
}
