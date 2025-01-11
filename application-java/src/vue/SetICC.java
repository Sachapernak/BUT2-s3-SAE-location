package vue;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import controleur.GestionICC;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SetICC extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldAnnee;
	private JTextField textFieldIndice;
	private JButton btnSupprimer;
	private JButton btnConfirmer;
	private JButton btnAnnuler;
	
	private GestionICC gest;
	private JScrollPane scrollPane;
	private JList<String> listICC;
	private JComboBox<String> comboBoxTrimestre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					SetICC frame = new SetICC();
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
	public SetICC() {
		
		gest = new GestionICC(this);
		
		setBounds(100, 100, 480, 280);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{160, 90, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTitre = new JLabel("Ajouter / Supprimer");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 1;
		gbc_lblTitre.gridy = 0;
		getContentPane().add(lblTitre, gbc_lblTitre);
		
		JLabel lblAnneeICC = new JLabel("Année de l'ICC :");
		GridBagConstraints gbc_lblAnneeICC = new GridBagConstraints();
		gbc_lblAnneeICC.anchor = GridBagConstraints.EAST;
		gbc_lblAnneeICC.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnneeICC.gridx = 0;
		gbc_lblAnneeICC.gridy = 1;
		getContentPane().add(lblAnneeICC, gbc_lblAnneeICC);
		
		textFieldAnnee = new JTextField();
		GridBagConstraints gbc_textFieldAnnee = new GridBagConstraints();
		gbc_textFieldAnnee.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAnnee.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAnnee.gridx = 1;
		gbc_textFieldAnnee.gridy = 1;
		getContentPane().add(textFieldAnnee, gbc_textFieldAnnee);
		textFieldAnnee.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		listICC = new JList<String>();
		scrollPane.setViewportView(listICC);
		
		JLabel lblTrimestre = new JLabel("Trimestre de l'ICC :");
		GridBagConstraints gbc_lblTrimestre = new GridBagConstraints();
		gbc_lblTrimestre.anchor = GridBagConstraints.EAST;
		gbc_lblTrimestre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrimestre.gridx = 0;
		gbc_lblTrimestre.gridy = 2;
		getContentPane().add(lblTrimestre, gbc_lblTrimestre);
		
		comboBoxTrimestre = new JComboBox<String>();
		comboBoxTrimestre.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		GridBagConstraints gbc_textFieldTrim = new GridBagConstraints();
		gbc_textFieldTrim.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTrim.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTrim.gridx = 1;
		gbc_textFieldTrim.gridy = 2;
		getContentPane().add(comboBoxTrimestre, gbc_textFieldTrim);
		
		JLabel lblIndice = new JLabel("Indice :");
		GridBagConstraints gbc_lblIndice = new GridBagConstraints();
		gbc_lblIndice.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndice.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblIndice.gridx = 0;
		gbc_lblIndice.gridy = 3;
		getContentPane().add(lblIndice, gbc_lblIndice);
		
		textFieldIndice = new JTextField();
		GridBagConstraints gbc_textFieldIndice = new GridBagConstraints();
		gbc_textFieldIndice.anchor = GridBagConstraints.NORTH;
		gbc_textFieldIndice.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIndice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIndice.gridx = 1;
		gbc_textFieldIndice.gridy = 3;
		getContentPane().add(textFieldIndice, gbc_textFieldIndice);
		textFieldIndice.setColumns(10);
		
		btnSupprimer = new JButton("Supprimer");

		GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
		gbc_btnSupprimer.insets = new Insets(0, 0, 0, 5);
		gbc_btnSupprimer.gridx = 0;
		gbc_btnSupprimer.gridy = 5;
		getContentPane().add(btnSupprimer, gbc_btnSupprimer);
		
		btnConfirmer = new JButton("Ajouter");

		
		GridBagConstraints gbc_btnConfirmer = new GridBagConstraints();
		gbc_btnConfirmer.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirmer.gridx = 1;
		gbc_btnConfirmer.gridy = 5;
		getContentPane().add(btnConfirmer, gbc_btnConfirmer);

		
		btnAnnuler = new JButton("Quitter");
		
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.gridx = 4;
		gbc_btnAnnuler.gridy = 5;
		getContentPane().add(btnAnnuler, gbc_btnAnnuler);
		
		gest.gestionAnnuler(btnAnnuler);
		gest.gestionConfirmer(btnConfirmer);
		gest.gestionSupprimer(btnSupprimer);
		gest.chargerDonnee();

	}

	public void setTableICC(List<String> iccs) {
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		
		for (String icc : iccs) {
			listModel.addElement(icc);
		}
		

		listICC.setModel(listModel);
	}
	
	public String getAnnee() {
		return textFieldAnnee.getText();
	}
	
	public String getTrimestre() {
		return String.valueOf(comboBoxTrimestre.getSelectedItem());
	}
	
	public String getIndice() {
		return textFieldIndice.getText();
	}
	
	public String getSelectedLine() {
		return listICC.getSelectedValue();
	}
	
    // Pour afficher un message d'erreur
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }

}
