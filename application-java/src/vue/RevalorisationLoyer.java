package vue;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RevalorisationLoyer extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblAncienLoyer;
	private JTextField textFieldLoyerActuel;
	private JTextField textFieldLoyerMax;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RevalorisationLoyer frame = new RevalorisationLoyer();
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
	public RevalorisationLoyer() {
		setBounds(100, 100, 450, 230);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 100, 160, 50, 80, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRevaloriserLoyers = new JLabel("Revaloriser un loyer");
		GridBagConstraints gbc_lblRevaloriserLoyers = new GridBagConstraints();
		gbc_lblRevaloriserLoyers.gridwidth = 5;
		gbc_lblRevaloriserLoyers.insets = new Insets(0, 0, 5, 0);
		gbc_lblRevaloriserLoyers.gridx = 0;
		gbc_lblRevaloriserLoyers.gridy = 0;
		getContentPane().add(lblRevaloriserLoyers, gbc_lblRevaloriserLoyers);
		
		JLabel lblBienLoc = new JLabel("Bien locatif :");
		GridBagConstraints gbc_lblBienLoc = new GridBagConstraints();
		gbc_lblBienLoc.insets = new Insets(0, 0, 5, 5);
		gbc_lblBienLoc.anchor = GridBagConstraints.EAST;
		gbc_lblBienLoc.gridx = 1;
		gbc_lblBienLoc.gridy = 1;
		getContentPane().add(lblBienLoc, gbc_lblBienLoc);
		
		JComboBox comboBoxBienLoc = new JComboBox();
		GridBagConstraints gbc_comboBoxBienLoc = new GridBagConstraints();
		gbc_comboBoxBienLoc.gridwidth = 2;
		gbc_comboBoxBienLoc.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxBienLoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBienLoc.gridx = 2;
		gbc_comboBoxBienLoc.gridy = 1;
		getContentPane().add(comboBoxBienLoc, gbc_comboBoxBienLoc);
		
		lblAncienLoyer = new JLabel("Ancien loyer :");
		GridBagConstraints gbc_lblAncienLoyer = new GridBagConstraints();
		gbc_lblAncienLoyer.anchor = GridBagConstraints.EAST;
		gbc_lblAncienLoyer.insets = new Insets(0, 0, 5, 5);
		gbc_lblAncienLoyer.gridx = 1;
		gbc_lblAncienLoyer.gridy = 2;
		getContentPane().add(lblAncienLoyer, gbc_lblAncienLoyer);
		
		textFieldLoyerActuel = new JTextField();
		textFieldLoyerActuel.setEditable(false);
		GridBagConstraints gbc_textFieldLoyerActuel = new GridBagConstraints();
		gbc_textFieldLoyerActuel.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLoyerActuel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLoyerActuel.gridx = 2;
		gbc_textFieldLoyerActuel.gridy = 2;
		getContentPane().add(textFieldLoyerActuel, gbc_textFieldLoyerActuel);
		textFieldLoyerActuel.setColumns(10);
		
		JLabel lblEuro = new JLabel("€");
		GridBagConstraints gbc_lblEuro = new GridBagConstraints();
		gbc_lblEuro.anchor = GridBagConstraints.WEST;
		gbc_lblEuro.insets = new Insets(0, 0, 5, 5);
		gbc_lblEuro.gridx = 3;
		gbc_lblEuro.gridy = 2;
		getContentPane().add(lblEuro, gbc_lblEuro);
		
		JLabel lblMax = new JLabel("Loyer maximum :");
		GridBagConstraints gbc_lblMax = new GridBagConstraints();
		gbc_lblMax.anchor = GridBagConstraints.EAST;
		gbc_lblMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMax.gridx = 1;
		gbc_lblMax.gridy = 3;
		getContentPane().add(lblMax, gbc_lblMax);
		
		textFieldLoyerMax = new JTextField();
		textFieldLoyerMax.setEditable(false);
		GridBagConstraints gbc_textFieldLoyerMax = new GridBagConstraints();
		gbc_textFieldLoyerMax.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLoyerMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLoyerMax.gridx = 2;
		gbc_textFieldLoyerMax.gridy = 3;
		getContentPane().add(textFieldLoyerMax, gbc_textFieldLoyerMax);
		textFieldLoyerMax.setColumns(10);
		
		JLabel lblEuro2 = new JLabel("€");
		GridBagConstraints gbc_lblEuro2 = new GridBagConstraints();
		gbc_lblEuro2.anchor = GridBagConstraints.WEST;
		gbc_lblEuro2.insets = new Insets(0, 0, 5, 5);
		gbc_lblEuro2.gridx = 3;
		gbc_lblEuro2.gridy = 3;
		getContentPane().add(lblEuro2, gbc_lblEuro2);
		
		JLabel lblNouvLoyer = new JLabel("Nouveau Loyer :");
		GridBagConstraints gbc_lblNouvLoyer = new GridBagConstraints();
		gbc_lblNouvLoyer.insets = new Insets(0, 0, 5, 5);
		gbc_lblNouvLoyer.gridx = 1;
		gbc_lblNouvLoyer.gridy = 5;
		getContentPane().add(lblNouvLoyer, gbc_lblNouvLoyer);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 5;
		getContentPane().add(spinner, gbc_spinner);
		
		JButton btnICC = new JButton("Voir ICC");
		GridBagConstraints gbc_btnICC = new GridBagConstraints();
		gbc_btnICC.insets = new Insets(0, 0, 0, 5);
		gbc_btnICC.gridx = 1;
		gbc_btnICC.gridy = 7;
		getContentPane().add(btnICC, gbc_btnICC);
		
		JButton btnRevaloriser = new JButton("Revaloriser");
		GridBagConstraints gbc_btnRevaloriser = new GridBagConstraints();
		gbc_btnRevaloriser.anchor = GridBagConstraints.EAST;
		gbc_btnRevaloriser.gridwidth = 2;
		gbc_btnRevaloriser.insets = new Insets(0, 0, 0, 5);
		gbc_btnRevaloriser.gridx = 2;
		gbc_btnRevaloriser.gridy = 7;
		getContentPane().add(btnRevaloriser, gbc_btnRevaloriser);
		
		JButton btnQuitter = new JButton("Quitter");
		GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
		gbc_btnQuitter.gridx = 4;
		gbc_btnQuitter.gridy = 7;
		getContentPane().add(btnQuitter, gbc_btnQuitter);

	}

}
