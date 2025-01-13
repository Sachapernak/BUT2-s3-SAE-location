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
		GridBagConstraints gbcLblTitre = new GridBagConstraints();
		gbcLblTitre.insets = new Insets(0, 0, 5, 5);
		gbcLblTitre.gridx = 1;
		gbcLblTitre.gridy = 0;
		getContentPane().add(lblTitre, gbcLblTitre);
		
		JLabel lblAnneeICC = new JLabel("Année de l'ICC :");
		GridBagConstraints gbcLblAnneeICC = new GridBagConstraints();
		gbcLblAnneeICC.anchor = GridBagConstraints.EAST;
		gbcLblAnneeICC.insets = new Insets(0, 0, 5, 5);
		gbcLblAnneeICC.gridx = 0;
		gbcLblAnneeICC.gridy = 1;
		getContentPane().add(lblAnneeICC, gbcLblAnneeICC);
		
		textFieldAnnee = new JTextField();
		GridBagConstraints gbcTextFieldAnnee = new GridBagConstraints();
		gbcTextFieldAnnee.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldAnnee.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldAnnee.gridx = 1;
		gbcTextFieldAnnee.gridy = 1;
		getContentPane().add(textFieldAnnee, gbcTextFieldAnnee);
		textFieldAnnee.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 2;
		gbcScrollPane.gridheight = 3;
		gbcScrollPane.insets = new Insets(0, 0, 5, 0);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 3;
		gbcScrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbcScrollPane);
		
		listICC = new JList<>();
		scrollPane.setViewportView(listICC);
		
		JLabel lblTrimestre = new JLabel("Trimestre de l'ICC :");
		GridBagConstraints gbcLblTrimestre = new GridBagConstraints();
		gbcLblTrimestre.anchor = GridBagConstraints.EAST;
		gbcLblTrimestre.insets = new Insets(0, 0, 5, 5);
		gbcLblTrimestre.gridx = 0;
		gbcLblTrimestre.gridy = 2;
		getContentPane().add(lblTrimestre, gbcLblTrimestre);
		
		comboBoxTrimestre = new JComboBox<>();
		comboBoxTrimestre.setModel(new DefaultComboBoxModel<>(new String[] {"1", "2", "3", "4"}));
		GridBagConstraints gbcTextFieldTrim = new GridBagConstraints();
		gbcTextFieldTrim.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldTrim.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldTrim.gridx = 1;
		gbcTextFieldTrim.gridy = 2;
		getContentPane().add(comboBoxTrimestre, gbcTextFieldTrim);
		
		JLabel lblIndice = new JLabel("Indice :");
		GridBagConstraints gbcLblIndice = new GridBagConstraints();
		gbcLblIndice.insets = new Insets(0, 0, 5, 5);
		gbcLblIndice.anchor = GridBagConstraints.NORTHEAST;
		gbcLblIndice.gridx = 0;
		gbcLblIndice.gridy = 3;
		getContentPane().add(lblIndice, gbcLblIndice);
		
		textFieldIndice = new JTextField();
		GridBagConstraints gbcTextFieldIndice = new GridBagConstraints();
		gbcTextFieldIndice.anchor = GridBagConstraints.NORTH;
		gbcTextFieldIndice.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldIndice.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldIndice.gridx = 1;
		gbcTextFieldIndice.gridy = 3;
		getContentPane().add(textFieldIndice, gbcTextFieldIndice);
		textFieldIndice.setColumns(10);
		
		btnSupprimer = new JButton("Supprimer");

		GridBagConstraints gbcBtnSupprimer = new GridBagConstraints();
		gbcBtnSupprimer.insets = new Insets(0, 0, 0, 5);
		gbcBtnSupprimer.gridx = 0;
		gbcBtnSupprimer.gridy = 5;
		getContentPane().add(btnSupprimer, gbcBtnSupprimer);
		
		btnConfirmer = new JButton("Ajouter");

		
		GridBagConstraints gbcBtnConfirmer = new GridBagConstraints();
		gbcBtnConfirmer.insets = new Insets(0, 0, 0, 5);
		gbcBtnConfirmer.gridx = 1;
		gbcBtnConfirmer.gridy = 5;
		getContentPane().add(btnConfirmer, gbcBtnConfirmer);

		
		btnAnnuler = new JButton("Quitter");
		
		GridBagConstraints gbcBtnAnnuler = new GridBagConstraints();
		gbcBtnAnnuler.gridx = 4;
		gbcBtnAnnuler.gridy = 5;
		getContentPane().add(btnAnnuler, gbcBtnAnnuler);
		
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
