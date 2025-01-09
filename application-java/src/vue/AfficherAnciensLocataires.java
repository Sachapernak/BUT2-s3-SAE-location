package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherAnciensLocataires;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;



public class AfficherAnciensLocataires extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	private JTable tableLocataires;
	
	private GestionAfficherAnciensLocataires gestionClic;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public AfficherAnciensLocataires() {
		
		this.gestionClic = new GestionAfficherAnciensLocataires(this);
		
		setBounds(25, 25, 670, 490);
		getContentPane().setLayout(null);
		
		JLabel lblBatiment = new JLabel("Batiment :");
		lblBatiment.setBounds(10, 68, 66, 13);
		getContentPane().add(lblBatiment);
		
		JComboBox comboBox = new JComboBox();
		lblBatiment.setLabelFor(comboBox);
		comboBox.setBounds(86, 64, 136, 21);
		getContentPane().add(comboBox);
		
		JLabel lblAdresseComplete = new JLabel("adresse complète.........");
		lblAdresseComplete.setBounds(380, 68, 145, 13);
		getContentPane().add(lblAdresseComplete);
		
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
		
		tableLocataires = new JTable();
		tableLocataires.setModel(new DefaultTableModel(
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
		scrollPaneLocataires.setViewportView(tableLocataires);
		
		JPanel p_boutons = new JPanel();
		p_boutons.setBounds(20, 380, 89, 31);
		getContentPane().add(p_boutons);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(this.gestionClic);
		p_boutons.add(btnModifier);
		
		JPanel panel_retour = new JPanel();
		panel_retour.setBounds(559, 380, 89, 31);
		getContentPane().add(panel_retour);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionClic);
		panel_retour.add(btnRetour);
		
		JLabel lblTitre = new JLabel("Anciens locataires");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(0, 10, 658, 44);
		getContentPane().add(lblTitre);

	}

}
