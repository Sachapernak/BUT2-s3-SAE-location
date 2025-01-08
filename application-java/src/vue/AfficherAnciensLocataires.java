package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherAnciensLocataires;
import controleur.GestionChampsMontantAfficherLocataire;
import modele.dao.DaoBienLocatif;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;



public class AfficherAnciensLocataires extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox comboBoxBatiment;
	private JComboBox comboBoxBienLocatif;
	private JTable tableAnciensLocataires;
	
	private GestionAfficherAnciensLocataires gestionTab;
	
	
	public JComboBox getComboBoxBatiment() {
		return comboBoxBatiment;
	}

	public void setComboBoxBatiment(JComboBox comboBoxBatiment) {
		this.comboBoxBatiment = comboBoxBatiment;
	}
	
	public JComboBox getComboBoxBienLocatif() {
		return comboBoxBienLocatif;
	}

	public void setComboBoxBienLocatif(JComboBox comboBoxBienLocatif) {
		this.comboBoxBienLocatif = comboBoxBienLocatif;
	}
	
	public JTable getTableAnciensLocataires() {
		return tableAnciensLocataires;
	}

	public void setTableAnciensLocataires(JTable tableAnciensLocataires) {
		this.tableAnciensLocataires = tableAnciensLocataires;
	}
	

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public AfficherAnciensLocataires() {
		
		this.gestionTab = new GestionAfficherAnciensLocataires(this);
		
		setBounds(25, 25, 670, 490);
		getContentPane().setLayout(null);
		
		JLabel lblBatiment = new JLabel("Batiment :");
		lblBatiment.setBounds(65, 68, 66, 13);
		getContentPane().add(lblBatiment);
		
		comboBoxBatiment = new JComboBox();

		lblBatiment.setLabelFor(comboBoxBatiment);
		comboBoxBatiment.setBounds(141, 64, 136, 21);
		comboBoxBatiment.setModel(new DefaultComboBoxModel(new String[] {"Chargement..."}));
		this.gestionTab.remplirComboBoxBatiment();
		this.gestionTab.gestionActionComboBatiment();
		getContentPane().add(comboBoxBatiment);
		
		JLabel lblBienLocatif = new JLabel("Bien locatif :");
		lblBienLocatif.setBounds(368, 68, 86, 13);
		getContentPane().add(lblBienLocatif);
		
		comboBoxBienLocatif = new JComboBox();
		lblBienLocatif.setLabelFor(comboBoxBienLocatif);
		comboBoxBienLocatif.setBounds(450, 64, 136, 21);
		comboBoxBienLocatif.setModel(new DefaultComboBoxModel(new String[] {"Chargement..."}));
		gestionTab.remplirComboBoxBienLocatif(String.valueOf(comboBoxBatiment.getSelectedItem()));
		this.gestionTab.gestionActionComboLogement();
		getContentPane().add(comboBoxBienLocatif);
		
		JPanel panel_locataires = new JPanel();
		panel_locataires.setBorder(new TitledBorder(null, "Les locataires", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_locataires.setBounds(10, 103, 638, 242);
		getContentPane().add(panel_locataires);
		panel_locataires.setLayout(new BorderLayout(0, 0));
		
		JPanel p_locataires_north = new JPanel();
		panel_locataires.add(p_locataires_north, BorderLayout.NORTH);
		
		JPanel p_locataires_centre = new JPanel();
		panel_locataires.add(p_locataires_centre, BorderLayout.CENTER);
		p_locataires_centre.setLayout(null);
		
		JScrollPane scrollPaneLocataires = new JScrollPane();
		scrollPaneLocataires.setBounds(21, 10, 581, 170);
		p_locataires_centre.add(scrollPaneLocataires);
		
		tableAnciensLocataires = new JTable();
		tableAnciensLocataires.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Identifiant", "Nom", "Pr\u00E9nom", "Date d'entr\u00E9e", "Date de sortie"
			}
		));
		scrollPaneLocataires.setViewportView(tableAnciensLocataires);
		this.gestionTab.remplirTableAnciensLocataires(String.valueOf(comboBoxBienLocatif.getSelectedItem()));
		
		JPanel p_boutons = new JPanel();
		p_boutons.setBounds(20, 380, 89, 31);
		getContentPane().add(p_boutons);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(this.gestionTab);
		p_boutons.add(btnModifier);
		
		JPanel panel_retour = new JPanel();
		panel_retour.setBounds(559, 380, 89, 31);
		getContentPane().add(panel_retour);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionTab);
		panel_retour.add(btnRetour);
		
		JLabel lblTitre = new JLabel("Anciens locataires");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(0, 10, 658, 44);
		getContentPane().add(lblTitre);

	}

}
