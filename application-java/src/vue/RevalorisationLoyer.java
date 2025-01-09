package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import controleur.GestionRevalorisationLoyer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class RevalorisationLoyer extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private GestionRevalorisationLoyer gestionClic;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RevalorisationLoyer() {
		
		this.gestionClic = new GestionRevalorisationLoyer(this);
		
		setBounds(7, 7, 490, 500);
		getContentPane().setLayout(null);
		
		JPanel panel_boutons = new JPanel();
		panel_boutons.setBounds(0, 387, 479, 31);
		getContentPane().add(panel_boutons);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionClic);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(this.gestionClic);
		panel_boutons.add(btnValider);
		panel_boutons.add(btnRetour);
		
		JPanel panel_choixBien = new JPanel();
		panel_choixBien.setBounds(44, 62, 385, 119);
		getContentPane().add(panel_choixBien);
		panel_choixBien.setLayout(null);
		
		JLabel lblBienLocatif = new JLabel("Bien locatif :");
		lblBienLocatif.setBounds(23, 25, 96, 13);
		lblBienLocatif.setHorizontalAlignment(SwingConstants.LEFT);
		panel_choixBien.add(lblBienLocatif);
		
		JComboBox comboBoxBiensLoc = new JComboBox();
		comboBoxBiensLoc.setBounds(183, 22, 168, 21);
		comboBoxBiensLoc.setModel(new DefaultComboBoxModel(new String[] {"appartement 1 ", "garage 1", "immeuble 1"}));
		panel_choixBien.add(comboBoxBiensLoc);
		
		JLabel lblTrimestre = new JLabel("Trimestre de l'indice ICC :");
		lblTrimestre.setHorizontalAlignment(SwingConstants.LEFT);
		lblTrimestre.setBounds(23, 55, 147, 13);
		panel_choixBien.add(lblTrimestre);
		
		JComboBox comboBoxTrimestre = new JComboBox();
		comboBoxTrimestre.setModel(new DefaultComboBoxModel(new String[] {"Premier trimestre", "Deuxième trimestre", "Troisième trimestre", "Quatrième trimestre"}));
		comboBoxTrimestre.setBounds(183, 51, 168, 21);
		panel_choixBien.add(comboBoxTrimestre);
		
		JLabel lblSaisiLoyer = new JLabel("Loyer :");
		lblSaisiLoyer.setHorizontalAlignment(SwingConstants.LEFT);
		lblSaisiLoyer.setBounds(23, 85, 49, 13);
		panel_choixBien.add(lblSaisiLoyer);
		
		textField = new JTextField();
		textField.setBounds(183, 82, 96, 19);
		panel_choixBien.add(textField);
		textField.setColumns(10);
		
		JPanel panel_loyerRevalorise = new JPanel();
		panel_loyerRevalorise.setBackground(new Color(207, 228, 243));
		panel_loyerRevalorise.setBorder(new TitledBorder(null, "R\u00E9sultat", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_loyerRevalorise.setBounds(44, 210, 385, 153);
		getContentPane().add(panel_loyerRevalorise);
		panel_loyerRevalorise.setLayout(null);
		
		JLabel lblNouveuLoyer = new JLabel("Montant du loyer après revalorisation");
		lblNouveuLoyer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNouveuLoyer.setBounds(10, 22, 222, 13);
		panel_loyerRevalorise.add(lblNouveuLoyer);
		
		JLabel lblMontant = new JLabel("100");
		lblMontant.setBounds(10, 45, 34, 15);
		lblMontant.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_loyerRevalorise.add(lblMontant);
		
		JLabel lblEuros = new JLabel("€");
		lblEuros.setBounds(44, 45, 8, 15);
		lblEuros.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_loyerRevalorise.add(lblEuros);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 70, 365, 2);
		panel_loyerRevalorise.add(separator);
		
		JPanel panel_details = new JPanel();
		panel_details.setBackground(new Color(207, 228, 243));
		panel_details.setBounds(10, 80, 365, 63);
		panel_loyerRevalorise.add(panel_details);
		panel_details.setLayout(null);
		
		JLabel lblDetails = new JLabel("Détails du calcul :");
		lblDetails.setBounds(0, 0, 90, 15);
		lblDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_details.add(lblDetails);
		
		JLabel lblTitre = new JLabel("Revalorisation du loyer");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setBounds(0, 10, 479, 38);
		getContentPane().add(lblTitre);

	}
}
