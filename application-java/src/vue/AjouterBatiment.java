package vue;


import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controleur.GestionAjouterBatiment;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class AjouterBatiment extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldIdentifiant;
	private JTextField textFieldIdAdr;
	private JTextField textFieldAdresse;
	private JTextField textFieldComplement;
	private JTextField textFieldVille;
	private JTextField textFieldCodePostal;
	
	private GestionAjouterBatiment gestionClic;
	
	

	
	public String getStringTextFieldIdentifiant() {
		return textFieldIdentifiant.getText();
	}




	public String getStringTextFieldIdAdr() {
		return textFieldIdAdr.getText();
	}




	public String getStringTextFieldAdresse() {
		return textFieldAdresse.getText();
	}




	public String getStringTextFieldComplement() {
		return textFieldComplement.getText();
	}




	public String getStringTextFieldVille() {
		return textFieldVille.getText();
	}




	public String getStringTextFieldCodePostal() {
		return textFieldCodePostal.getText();
	}

	public List<String> getChampsObligatoires(){
		List<String> res = new ArrayList<>();
		res.add(getStringTextFieldIdentifiant());
		res.add(getStringTextFieldIdAdr());
		res.add(getStringTextFieldAdresse());
		res.add(getStringTextFieldVille());
		res.add(getStringTextFieldCodePostal());
		return res;
	}



	public AjouterBatiment() {
		
		this.gestionClic = new GestionAjouterBatiment(this);
		setBounds(100, 100, 366, 390);
		
		JPanel panelNorth = new JPanel();
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Ajouter un bâtiment");
		panelNorth.add(lblTitre);
		
		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(null);
		
		JLabel lblIdentifiant = new JLabel("Identifiant* : ");
		lblIdentifiant.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentifiant.setBounds(34, 30, 96, 13);
		panelCentre.add(lblIdentifiant);
		
		textFieldIdentifiant = new JTextField();
		textFieldIdentifiant.setBounds(152, 27, 96, 19);
		panelCentre.add(textFieldIdentifiant);
		textFieldIdentifiant.setColumns(10);
		
		JPanel panelAdresse = new JPanel();
		panelAdresse.setBorder(new TitledBorder(null, "Adresse ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdresse.setBounds(44, 58, 259, 191);
		panelCentre.add(panelAdresse);
		panelAdresse.setLayout(null);
		
		JLabel lblIdAdr = new JLabel("Identifiant* : ");
		lblIdAdr.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdAdr.setBounds(28, 28, 90, 13);
		panelAdresse.add(lblIdAdr);
		
		textFieldIdAdr = new JTextField();
		textFieldIdAdr.setBounds(128, 25, 96, 19);
		panelAdresse.add(textFieldIdAdr);
		textFieldIdAdr.setColumns(10);
		
		JLabel lblAdresse = new JLabel("Adresse* : ");
		lblAdresse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresse.setBounds(34, 60, 84, 13);
		panelAdresse.add(lblAdresse);
		
		textFieldAdresse = new JTextField();
		textFieldAdresse.setBounds(128, 57, 96, 19);
		panelAdresse.add(textFieldAdresse);
		textFieldAdresse.setColumns(10);
		
		JLabel lblComplément = new JLabel("Complément : ");
		lblComplément.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplément.setBounds(22, 88, 96, 13);
		panelAdresse.add(lblComplément);
		
		textFieldComplement = new JTextField();
		textFieldComplement.setBounds(128, 85, 96, 19);
		panelAdresse.add(textFieldComplement);
		textFieldComplement.setColumns(10);
		
		JLabel lblVille = new JLabel("Ville : ");
		lblVille.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVille.setBounds(21, 150, 97, 13);
		panelAdresse.add(lblVille);
		
		textFieldVille = new JTextField();
		textFieldVille.setColumns(10);
		textFieldVille.setBounds(128, 147, 96, 19);
		panelAdresse.add(textFieldVille);
		
		textFieldCodePostal = new JTextField();
		textFieldCodePostal.setColumns(10);
		textFieldCodePostal.setBounds(128, 118, 96, 19);
		panelAdresse.add(textFieldCodePostal);
		
		JLabel lblCodePostal = new JLabel("Code postal : ");
		lblCodePostal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodePostal.setBounds(15, 121, 103, 13);
		panelAdresse.add(lblCodePostal);
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(0, 270, 354, 29);
		panelCentre.add(panelBtn);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(this.gestionClic);
		panelBtn.add(btnAjouter);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		panelBtn.add(btnAnnuler);
		

	}




	public void afficherMessageErreur(String message) {
		JOptionPane.showMessageDialog(this,message, "Erreur",JOptionPane.ERROR_MESSAGE);
	}




	public void afficherMessageSucces(String message) {
		JOptionPane.showMessageDialog(this,message, "Batiment ajouté",JOptionPane.INFORMATION_MESSAGE);
		
	}
}
