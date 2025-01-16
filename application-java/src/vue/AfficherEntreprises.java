package vue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherEntreprises;

import javax.swing.JButton;

public class AfficherEntreprises extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableEntreprises;
	
	private GestionAfficherEntreprises gestionFen;

	
	public JTable getTableEntreprises() {
		return tableEntreprises;
	}


	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public AfficherEntreprises() {
		this.gestionFen = new GestionAfficherEntreprises(this);
		setBounds(100, 100, 514, 365);
		
		JLabel lblNewLabel = new JLabel("Les entreprises  ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel pCentre = new JPanel();
		getContentPane().add(pCentre, BorderLayout.CENTER);
		pCentre.setLayout(null);
		
		JComboBox<String> comboBoxSecteursActivite = new JComboBox<String>();
		comboBoxSecteursActivite.setBounds(218, 26, 113, 21);
		pCentre.add(comboBoxSecteursActivite);
		this.gestionFen.remplirComboBox(comboBoxSecteursActivite);
		comboBoxSecteursActivite.addActionListener(this.gestionFen);
		
		JLabel lblSecteurActivite = new JLabel("Secteur d'activité :");
		lblSecteurActivite.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecteurActivite.setBounds(78, 30, 130, 13);
		pCentre.add(lblSecteurActivite);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 78, 452, 121);
		pCentre.add(scrollPane);
		
		tableEntreprises = new JTable();
		tableEntreprises.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Siret", "Nom", "Adresse"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tableEntreprises);
		this.gestionFen.remplirTable(tableEntreprises, (String) comboBoxSecteursActivite.getSelectedItem());
		
		JPanel pBtns = new JPanel();
		pBtns.setBounds(0, 267, 502, 39);
		pCentre.add(pBtns);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(this.gestionFen);
		pBtns.add(btnSupprimer);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(this.gestionFen);
		pBtns.add(btnAjouter);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionFen);
		pBtns.add(btnQuitter);

	}
}