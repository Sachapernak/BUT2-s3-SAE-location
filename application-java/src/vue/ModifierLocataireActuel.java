package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controleur.GestionModifierLocataireActuel;

public class ModifierLocataireActuel extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;
	private JTextField textFieldEmail;
	private JTextField textFieldDateNaissance;
	private JTextField textFieldLieuNaissance;
	private JTextField textFieldVille;
	private JTextField textFieldCodePostal;
	private JTextField textComplement;
	private JTextField textFieldAdr;
	private JTextField textFieldTel;
	
	private GestionModifierLocataireActuel gestionClic;

	

	public JTextField getTextFieldNom() {
		return textFieldNom;
	}


	public void setTextFieldNom(JTextField textFieldNom) {
		this.textFieldNom = textFieldNom;
	}


	public JTextField getTextFieldPrenom() {
		return textFieldPrenom;
	}


	public void setTextFieldPrenom(JTextField textFieldPrenom) {
		this.textFieldPrenom = textFieldPrenom;
	}


	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}


	public void setTextFieldEmail(JTextField textFieldEmail) {
		this.textFieldEmail = textFieldEmail;
	}


	public JTextField getTextFieldDateNaissance() {
		return textFieldDateNaissance;
	}


	public void setTextFieldDateNaissance(JTextField textFieldDateNaissance) {
		this.textFieldDateNaissance = textFieldDateNaissance;
	}


	public JTextField getTextFieldLieuNaissance() {
		return textFieldLieuNaissance;
	}


	public void setTextFieldLieuNaissance(JTextField textFieldLieuNaissance) {
		this.textFieldLieuNaissance = textFieldLieuNaissance;
	}


	public JTextField getTextFieldVille() {
		return textFieldVille;
	}


	public void setTextFieldVille(JTextField textFieldVille) {
		this.textFieldVille = textFieldVille;
	}


	public JTextField getTextFieldCodePostal() {
		return textFieldCodePostal;
	}


	public void setTextFieldCodePostal(JTextField textFieldCodePostal) {
		this.textFieldCodePostal = textFieldCodePostal;
	}


	public JTextField getTextFieldComplement() {
		return textComplement;
	}


	public void setTextComplement(JTextField textComplement) {
		this.textComplement = textComplement;
	}


	public JTextField getTextFieldAdr() {
		return textFieldAdr;
	}


	public void setTextFieldAdr(JTextField textFieldAdr) {
		this.textFieldAdr = textFieldAdr;
	}


	public JTextField getTextFieldTel() {
		return textFieldTel;
	}


	public void setTextFieldTel(JTextField textFieldTel) {
		this.textFieldTel = textFieldTel;
	}
	

	/**
	 * Create the frame.
	 */
	public ModifierLocataireActuel(AfficherLocatairesActuels al) {
		
		this.gestionClic = new GestionModifierLocataireActuel(this);
		
		setBounds(0, 0, 620, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel p_nord = new JPanel();
		contentPane.add(p_nord, BorderLayout.NORTH);
		
		JLabel lblTitreAjoutLoc = new JLabel("Modifier le locataire");
		lblTitreAjoutLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
		p_nord.add(lblTitreAjoutLoc);
		
		JPanel p_centre = new JPanel();
		contentPane.add(p_centre, BorderLayout.CENTER);
		p_centre.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel p_gauche = new JPanel();
		p_centre.add(p_gauche, BorderLayout.WEST);
		p_gauche.setLayout(new GridLayout(8, 2, 0, 0));
		
		JPanel panel = new JPanel();
		p_gauche.add(panel);
		
		JPanel panel_1 = new JPanel();
		p_gauche.add(panel_1);
		
		JPanel p_lblNom = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) p_lblNom.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblNom);
		
		JLabel lblNom = new JLabel("Nom* : ");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		p_lblNom.add(lblNom);
		
		JPanel p_txtNom = new JPanel();
		p_gauche.add(p_txtNom);
		
		textFieldNom = new JTextField();
		p_txtNom.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		JPanel p_lblPrenom = new JPanel();
		FlowLayout flowLayout = (FlowLayout) p_lblPrenom.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblPrenom);
		
		JLabel lblPrenom = new JLabel("Prénom* :");
		lblPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		p_lblPrenom.add(lblPrenom);
		
		JPanel p_txtPrenom = new JPanel();
		p_gauche.add(p_txtPrenom);
		
		textFieldPrenom = new JTextField();
		p_txtPrenom.add(textFieldPrenom);
		textFieldPrenom.setColumns(10);
		
		JPanel p_lblTel = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) p_lblTel.getLayout();
		flowLayout_11.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblTel);
		
		JLabel lblTel = new JLabel("Téléphone :");
		lblTel.setHorizontalAlignment(SwingConstants.CENTER);
		p_lblTel.add(lblTel);
		
		JPanel p_txtTel = new JPanel();
		p_gauche.add(p_txtTel);
		
		textFieldTel = new JTextField();
		p_txtTel.add(textFieldTel);
		textFieldTel.setColumns(10);
		
		JPanel p_lblEmail = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) p_lblEmail.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblEmail);
		
		JLabel lblEmail = new JLabel("E-mail :");
		p_lblEmail.add(lblEmail);
		
		JPanel p_txtEmail = new JPanel();
		p_gauche.add(p_txtEmail);
		
		textFieldEmail = new JTextField();
		p_txtEmail.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JPanel p_lblDateNaissance = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) p_lblDateNaissance.getLayout();
		flowLayout_4.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblDateNaissance);
		
		JLabel lblDateNaissance = new JLabel("Date de naissance* :");
		p_lblDateNaissance.add(lblDateNaissance);
		
		JPanel p_txtDateNaissance = new JPanel();
		p_gauche.add(p_txtDateNaissance);
		
		textFieldDateNaissance = new JTextField();
		p_txtDateNaissance.add(textFieldDateNaissance);
		textFieldDateNaissance.setColumns(10);
		
		JPanel p_lblLieuNaissance = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) p_lblLieuNaissance.getLayout();
		flowLayout_5.setAlignment(FlowLayout.RIGHT);
		p_gauche.add(p_lblLieuNaissance);
		
		JLabel lblLieuNaissance = new JLabel("Lieu de naissance* :");
		p_lblLieuNaissance.add(lblLieuNaissance);
		
		JPanel p_txtLieuNaissance = new JPanel();
		p_gauche.add(p_txtLieuNaissance);
		
		textFieldLieuNaissance = new JTextField();
		p_txtLieuNaissance.add(textFieldLieuNaissance);
		textFieldLieuNaissance.setColumns(10);
				
		JPanel p_droite = new JPanel();
		p_centre.add(p_droite, BorderLayout.EAST);
		p_droite.setLayout(new BorderLayout(0, 0));
		
		JPanel p_adresse = new JPanel();
		p_adresse.setBorder(new TitledBorder(null, "Adresse : ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		p_droite.add(p_adresse, BorderLayout.CENTER);
		p_adresse.setLayout(new GridLayout(6, 2, 0, 0));
		
		JPanel panelVideHautGauche = new JPanel();
		p_adresse.add(panelVideHautGauche);
		
		JPanel panelVideHautDroite = new JPanel();
		p_adresse.add(panelVideHautDroite);
		
		JPanel p_lblAdr = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) p_lblAdr.getLayout();
		flowLayout_6.setAlignment(FlowLayout.RIGHT);
		p_adresse.add(p_lblAdr);
		
		JLabel lblAdr = new JLabel("Adresse : ");
		p_lblAdr.add(lblAdr);
		
		JPanel p_txtAdr = new JPanel();
		p_adresse.add(p_txtAdr);
		
		textFieldAdr = new JTextField();
		p_txtAdr.add(textFieldAdr);
		textFieldAdr.setColumns(10);
		
		JPanel p_lblComplement = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) p_lblComplement.getLayout();
		flowLayout_7.setAlignment(FlowLayout.RIGHT);
		p_adresse.add(p_lblComplement);
		
		JLabel lblComplement = new JLabel("Complément :");
		lblComplement.setHorizontalAlignment(SwingConstants.CENTER);
		p_lblComplement.add(lblComplement);
		
		JPanel p_txtComplement = new JPanel();
		p_adresse.add(p_txtComplement);
		
		textComplement = new JTextField();
		p_txtComplement.add(textComplement);
		textComplement.setColumns(10);
		
		JPanel p_lblCodePostal = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) p_lblCodePostal.getLayout();
		flowLayout_9.setAlignment(FlowLayout.RIGHT);
		p_adresse.add(p_lblCodePostal);
		
		JLabel lblCodePostal = new JLabel("Code postal : ");
		p_lblCodePostal.add(lblCodePostal);
		
		JPanel p_txtCodePostal = new JPanel();
		p_adresse.add(p_txtCodePostal);
		
		textFieldCodePostal = new JTextField();
		p_txtCodePostal.add(textFieldCodePostal);
		textFieldCodePostal.setColumns(10);
		
		JPanel p_lblVille = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) p_lblVille.getLayout();
		flowLayout_8.setAlignment(FlowLayout.RIGHT);
		p_adresse.add(p_lblVille);
		
		JLabel lblVille = new JLabel("Ville :");
		p_lblVille.add(lblVille);
		
		JPanel p_txtVille = new JPanel();
		p_adresse.add(p_txtVille);
		
		textFieldVille = new JTextField();
		p_txtVille.add(textFieldVille);
		textFieldVille.setColumns(10);
		
		JPanel panelVideBas = new JPanel();
		p_droite.add(panelVideBas, BorderLayout.SOUTH);
		panelVideBas.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panelVideBas_1 = new JPanel();
		panelVideBas.add(panelVideBas_1);
		
		JPanel panelVideHaut = new JPanel();
		p_droite.add(panelVideHaut, BorderLayout.NORTH);
		panelVideHaut.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panelVideHaut_1 = new JPanel();
		panelVideHaut.add(panelVideHaut_1);
				
		
		JPanel p_sud = new JPanel();
		contentPane.add(p_sud, BorderLayout.SOUTH);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(this.gestionClic);
		p_sud.add(btnValider);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		p_sud.add(btnAnnuler);
		
	}

}
