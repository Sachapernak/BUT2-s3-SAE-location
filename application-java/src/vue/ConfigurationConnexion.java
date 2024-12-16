package vue;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.GestionConfigurationConnexion;
import javax.swing.SwingConstants;

//import controleur.GestionConnexion;

public class ConfigurationConnexion extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField loginText;
	private JPasswordField mdpField;
	private JTextField lienText;
	private GestionConfigurationConnexion gestionClic;

	/**
	 * Create the frame.
	 */
	public ConfigurationConnexion() {
		this.gestionClic = new GestionConfigurationConnexion(this);
		
		setBounds(100, 100, 428, 245);
		getContentPane().setLayout(null);
		
		JLabel login = new JLabel("Login :");
		login.setHorizontalAlignment(SwingConstants.RIGHT);
		login.setBounds(81, 40, 85, 13);
		getContentPane().add(login);
		
		loginText = new JTextField();
		login.setLabelFor(loginText);
		loginText.setBounds(178, 37, 147, 19);
		getContentPane().add(loginText);
		loginText.setColumns(10);
		
		JLabel mdp = new JLabel("Mot de passe :");
		mdp.setHorizontalAlignment(SwingConstants.RIGHT);
		mdp.setBounds(31, 77, 137, 13);
		getContentPane().add(mdp);
		
		mdpField = new JPasswordField();
		mdp.setLabelFor(mdpField);
		mdpField.setBounds(178, 74, 147, 19);
		getContentPane().add(mdpField);
		
		JLabel lienConfig = new JLabel("Lien pour la configuration :");
		lienConfig.setHorizontalAlignment(SwingConstants.RIGHT);
		lienConfig.setBounds(10, 111, 158, 13);
		getContentPane().add(lienConfig);
		
		lienText = new JTextField();
		lienConfig.setLabelFor(lienText);
		lienText.setColumns(10);
		lienText.setBounds(178, 108, 147, 19);
		getContentPane().add(lienText);
		
		JButton btnConnecter = new JButton("Connecter");
		btnConnecter.addActionListener(this.gestionClic);
		btnConnecter.setBounds(69, 157, 114, 21);
		getContentPane().add(btnConnecter);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		btnAnnuler.setBounds(216, 157, 85, 21);
		getContentPane().add(btnAnnuler);
		
	}


	public String getValeurChLogin() {
		return this.loginText.getText();
	}


	public String getValeurPasswordField() {
		return String.valueOf(mdpField.getPassword());
	}
	
	public String getValeurLien() {
		return this.lienText.getText();
	}
}
