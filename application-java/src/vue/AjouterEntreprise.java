package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import controleur.GestionAjouterEntreprise;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;


public class AjouterEntreprise extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldIdAdr;
	private JTextField textFieldAdr;
	private JTextField textFieldComplement;
	private JTextField textFieldVille;
	private JTextField textFieldCodePostal;
	private JTextField textFieldSiret;
	private JTextField textFieldSecteurActivite;
	
	private GestionAjouterEntreprise gestionClic;
	private JTextField textFieldNom;
	

	public String getStringTextFieldSiret() {
		return textFieldSiret.getText();
	}
	
	public String getStringTextFieldSecteurActivite() {
		return textFieldSecteurActivite.getText();
	}
	
	public String getStringTextFieldNom() {
		return textFieldNom.getText();
	}
	
	public String getStringTextFieldIdAdr() {
		return textFieldIdAdr.getText();
	}

	public String getStringTextFieldAdresse() {
		return textFieldAdr.getText();
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
		res.add(getStringTextFieldSiret());
		res.add(getStringTextFieldSecteurActivite());
		res.add(getStringTextFieldIdAdr());
		res.add(getStringTextFieldAdresse());
		res.add(getStringTextFieldVille());
		res.add(getStringTextFieldCodePostal());
		return res;
	}

	public AjouterEntreprise() {
		setBounds(100, 100, 509, 330);
		this.gestionClic = new GestionAjouterEntreprise(this);
		
		JPanel panelTitre = new JPanel();
		getContentPane().add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblAjouterEntreprise = new JLabel("Ajouter une entreprise ");
		lblAjouterEntreprise.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelTitre.add(lblAjouterEntreprise);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panelAdresse = new JPanel();
		panelAdresse.setLayout(null);
		panelAdresse.setBorder(new TitledBorder(null, "Adresse ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAdresse.setBounds(213, 34, 259, 191);
		panel.add(panelAdresse);
		
		JLabel lblIdAdr = new JLabel("Identifiant* : ");
		lblIdAdr.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdAdr.setBounds(28, 28, 90, 13);
		panelAdresse.add(lblIdAdr);
		
		textFieldIdAdr = new JTextField();
		textFieldIdAdr.setColumns(10);
		textFieldIdAdr.setBounds(128, 25, 96, 19);
		panelAdresse.add(textFieldIdAdr);
		
		JLabel lblAdresse = new JLabel("Adresse* : ");
		lblAdresse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresse.setBounds(34, 60, 84, 13);
		panelAdresse.add(lblAdresse);
		
		textFieldAdr = new JTextField();
		textFieldAdr.setColumns(10);
		textFieldAdr.setBounds(128, 57, 96, 19);
		panelAdresse.add(textFieldAdr);
		
		JLabel lblComplément = new JLabel("Complément : ");
		lblComplément.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplément.setBounds(22, 88, 96, 13);
		panelAdresse.add(lblComplément);
		
		textFieldComplement = new JTextField();
		textFieldComplement.setColumns(10);
		textFieldComplement.setBounds(128, 85, 96, 19);
		panelAdresse.add(textFieldComplement);
		
		JLabel lblVille = new JLabel("Ville* : ");
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
		
		JLabel lblCodePostal = new JLabel("Code postal* : ");
		lblCodePostal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodePostal.setBounds(15, 121, 103, 13);
		panelAdresse.add(lblCodePostal);
		
		JPanel panelInfosEntreprise = new JPanel();
		panelInfosEntreprise.setBounds(24, 38, 170, 197);
		panel.add(panelInfosEntreprise);
		panelInfosEntreprise.setLayout(null);
		
		JLabel lblSiret = new JLabel("N° de SIRET* :");
		lblSiret.setBounds(10, 10, 98, 13);
		panelInfosEntreprise.add(lblSiret);
		
		textFieldSiret = new JTextField();
		textFieldSiret.setBounds(10, 33, 96, 19);
		panelInfosEntreprise.add(textFieldSiret);
		textFieldSiret.setColumns(10);
		
		JLabel lblSecteurActivité = new JLabel("Secteur d'activité* :");
		lblSecteurActivité.setBounds(10, 74, 118, 13);
		panelInfosEntreprise.add(lblSecteurActivité);
		
		textFieldSecteurActivite = new JTextField();
		textFieldSecteurActivite.setBounds(10, 97, 96, 19);
		panelInfosEntreprise.add(textFieldSecteurActivite);
		textFieldSecteurActivite.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setBounds(10, 141, 45, 13);
		panelInfosEntreprise.add(lblNom);
		
		textFieldNom = new JTextField();
		textFieldNom.setBounds(12, 164, 96, 19);
		panelInfosEntreprise.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(this.gestionClic);
		panel_1.add(btnAjouter);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		panel_1.add(btnAnnuler);

	}

	public void afficherMessage(String message, String titre, int typeMessage) {
	    JOptionPane.showMessageDialog(this, message, titre, typeMessage);
	}

}