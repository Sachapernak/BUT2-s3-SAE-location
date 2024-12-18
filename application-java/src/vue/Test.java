package vue;


import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherLocataire;
import controleur.GestionChampsLocataireActuel;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.JSeparator;



public class Test extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private GestionAfficherLocataire gestionClic;
	private GestionChampsLocataireActuel gestionChampsLoc;
	
	private JTextField textFieldDateDeNaissance;
	private JTextField textFieldAdressePerso;
	private JTextField textFieldTel;
	private JTable tableBiensLoues;
	private JTextField textFieldProvisionPourCharge;
	private JTextField textFieldMontantPaye;
	private JTextField textFieldMontantRestantDu;
	private JTextField textFieldMail;
	private JTable tableLocataires;
	

	public JTable getTableLocataires() {
		return tableLocataires;
	}

	public void setTableLocataires(JTable tableLocataires) {
		this.tableLocataires = tableLocataires;
	}

	public JTextField getTextFieldDateDeNaissance() {
		return textFieldDateDeNaissance;
	}

	public void setTextFieldDateDeNaissance(JTextField textFieldDateDeNaissance) {
		this.textFieldDateDeNaissance = textFieldDateDeNaissance;
	}

	public JTextField getTextFieldAdressePerso() {
		return textFieldAdressePerso;
	}

	public void setTextFieldAdressePerso(JTextField textFieldAdressePerso) {
		this.textFieldAdressePerso = textFieldAdressePerso;
	}

	public JTextField getTextFieldTel() {
		return textFieldTel;
	}
	
	public void setTextFieldTel(JTextField textFieldTel) {
		this.textFieldTel = textFieldTel;
	}

	public JTextField getTextFieldMail() {
		return textFieldMail;
	}

	public void setTextFieldMail(JTextField textFieldMail) {
		this.textFieldMail = textFieldMail;
	}
	

	public JTable getTableBiensLoues() {
		return tableBiensLoues;
	}

	public void setTableBiensLoues(JTable tableBiensLoues) {
		this.tableBiensLoues = tableBiensLoues;
	}

	public JTextField getTextFieldProvisionPourCharge() {
		return textFieldProvisionPourCharge;
	}

	public void setTextFieldProvisionPourCharge(JTextField textFieldProvisionPourCharge) {
		this.textFieldProvisionPourCharge = textFieldProvisionPourCharge;
	}

	public JTextField getTextFieldMontantPaye() {
		return textFieldMontantPaye;
	}

	public void setTextFieldMontantPaye(JTextField textFieldMontantPaye) {
		this.textFieldMontantPaye = textFieldMontantPaye;
	}

	public JTextField getTextFieldMontantRestantDu() {
		return textFieldMontantRestantDu;
	}

	public void setTextFieldMontantRestantDu(JTextField textFieldMontantRestantDu) {
		this.textFieldMontantRestantDu = textFieldMontantRestantDu;
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		
		//this.gestionClic = new GestionAfficherLocataire(this);
		//this.gestionChampsLoc = new GestionChampsLocataireActuel(this);
		
		setBounds(25, 25, 670, 490);
		getContentPane().setLayout(null);
		
		JPanel panel_locataires = new JPanel();
		panel_locataires.setBorder(new TitledBorder(null, "Les locataires", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_locataires.setBounds(10, 67, 302, 155);
		getContentPane().add(panel_locataires);
		panel_locataires.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneLocatairesActuels = new JScrollPane();
		panel_locataires.add(scrollPaneLocatairesActuels, BorderLayout.CENTER);
		
		tableLocataires = new JTable();
		tableLocataires.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Identifiant", "Nom", "Pr\u00E9nom"
			}
		));
		scrollPaneLocatairesActuels.setViewportView(tableLocataires);
		//this.gestionClic.remplirListeLoc();
		
		JPanel p_boutons = new JPanel();
		p_boutons.setBounds(8, 226, 304, 31);
		getContentPane().add(p_boutons);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(this.gestionClic);
		p_boutons.add(btnModifier);
		
		JButton btnResilierBail = new JButton("Résilier le bail");
		btnResilierBail.addActionListener(this.gestionClic);
		p_boutons.add(btnResilierBail);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(this.gestionClic);
		p_boutons.add(btnAjouter);
		
		JPanel panel_retour = new JPanel();
		panel_retour.setBounds(559, 420, 89, 31);
		getContentPane().add(panel_retour);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionClic);
		panel_retour.add(btnRetour);
		
		JLabel lblTitre = new JLabel("Les locataires actuels");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(0, 10, 658, 39);
		getContentPane().add(lblTitre);
		
		JPanel panel_informations_personnelles = new JPanel();
		panel_informations_personnelles.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Informations Personnelles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_informations_personnelles.setBounds(328, 67, 320, 155);
		getContentPane().add(panel_informations_personnelles);
		panel_informations_personnelles.setLayout(null);
		
		JLabel lblAdressePerso = new JLabel("Adresse personnelle :");
		lblAdressePerso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdressePerso.setBounds(-14, 61, 146, 13);
		panel_informations_personnelles.add(lblAdressePerso);
		
		JLabel lblNumTel = new JLabel("Téléphone :");
		lblNumTel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumTel.setBounds(-27, 90, 159, 13);
		panel_informations_personnelles.add(lblNumTel);
		
		JLabel lblDateDeNaissance = new JLabel("Date de naissance :");
		lblDateDeNaissance.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateDeNaissance.setBounds(-4, 29, 136, 13);
		panel_informations_personnelles.add(lblDateDeNaissance);
		
		textFieldDateDeNaissance = new JTextField();
		lblDateDeNaissance.setLabelFor(textFieldDateDeNaissance);
		textFieldDateDeNaissance.setBounds(145, 26, 96, 19);
		panel_informations_personnelles.add(textFieldDateDeNaissance);
		textFieldDateDeNaissance.setColumns(10);
		
		textFieldAdressePerso = new JTextField();
		lblAdressePerso.setLabelFor(textFieldAdressePerso);
		textFieldAdressePerso.setBounds(145, 58, 159, 19);
		panel_informations_personnelles.add(textFieldAdressePerso);
		textFieldAdressePerso.setColumns(10);
		
		textFieldTel = new JTextField();
		textFieldTel.setBounds(145, 87, 96, 19);
		panel_informations_personnelles.add(textFieldTel);
		textFieldTel.setColumns(10);
		
		textFieldMail = new JTextField();
		textFieldMail.setBounds(145, 116, 159, 19);
		panel_informations_personnelles.add(textFieldMail);
		textFieldMail.setColumns(10);
		
		JLabel lblMail = new JLabel("E-mail : ");
		lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMail.setBounds(87, 119, 45, 13);
		panel_informations_personnelles.add(lblMail);
		
		JPanel panel_informationsBatiments = new JPanel();
		panel_informationsBatiments.setBorder(new TitledBorder(null, "Location", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_informationsBatiments.setBounds(10, 267, 638, 73);
		getContentPane().add(panel_informationsBatiments);
		panel_informationsBatiments.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneBiensLoues = new JScrollPane();
		panel_informationsBatiments.add(scrollPaneBiensLoues, BorderLayout.CENTER);
		
		tableBiensLoues = new JTable();
		tableBiensLoues.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Date d'entr\u00E9e", "Type ", "Batiment", "Adresse compl\u00E8te", "Loyer", "Parts de loyer", "Derni\u00E8re r\u00E9gularisation", "Caution"
			}
		));
		scrollPaneBiensLoues.setViewportView(tableBiensLoues);
		
		JPanel panel_infosPaiement = new JPanel();
		panel_infosPaiement.setBounds(8, 350, 304, 91);
		getContentPane().add(panel_infosPaiement);
		panel_infosPaiement.setLayout(null);
		
		JLabel lblProvisionPourCharge = new JLabel("Provisions pour charges : ");
		lblProvisionPourCharge.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProvisionPourCharge.setBounds(-11, 8, 178, 13);
		panel_infosPaiement.add(lblProvisionPourCharge);
		
		textFieldProvisionPourCharge = new JTextField();
		textFieldProvisionPourCharge.setBounds(181, 5, 96, 19);
		panel_infosPaiement.add(textFieldProvisionPourCharge);
		textFieldProvisionPourCharge.setColumns(10);
		
		JLabel lblMontantPaye = new JLabel("Montant payé :");
		lblMontantPaye.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontantPaye.setBounds(0, 37, 167, 13);
		panel_infosPaiement.add(lblMontantPaye);
		
		textFieldMontantPaye = new JTextField();
		textFieldMontantPaye.setBounds(181, 34, 96, 19);
		panel_infosPaiement.add(textFieldMontantPaye);
		textFieldMontantPaye.setColumns(10);
		
		JLabel lblMontantRestantDu = new JLabel("Montant restant dû :");
		lblMontantRestantDu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontantRestantDu.setBounds(0, 66, 167, 13);
		panel_infosPaiement.add(lblMontantRestantDu);
		
		textFieldMontantRestantDu = new JTextField();
		textFieldMontantRestantDu.setBounds(181, 63, 96, 19);
		panel_infosPaiement.add(textFieldMontantRestantDu);
		textFieldMontantRestantDu.setColumns(10);
	}
}
