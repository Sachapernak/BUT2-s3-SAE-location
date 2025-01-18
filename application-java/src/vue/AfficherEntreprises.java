package vue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

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
	private JComboBox<String> comboBoxSecteursActivite;


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
		
		comboBoxSecteursActivite = new JComboBox<>();
		comboBoxSecteursActivite.setBounds(218, 26, 113, 21);
		pCentre.add(comboBoxSecteursActivite);
		this.gestionFen.remplirComboBox();
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
		this.gestionFen.initialiserTable();
		
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
	
	// -------------------------------------------------------------------------
    // Methodes d'aide pour le contrôleur
    // -------------------------------------------------------------------------
	
	public void setComboBoxSecteursActivite(List<String> typeContrats) {
    	comboBoxSecteursActivite.removeAllItems();
        for(String type : typeContrats) {
        	comboBoxSecteursActivite.addItem(type);
        }
    }
    
    public void setTableEntreprises(DefaultTableModel tableModel) {
        tableEntreprises.setModel(tableModel);
    }
    
    
    /**
     * @return la valeur sélectionnée dans la combo 'secteur d'activité'
     */
    public String getTypeSecteurActivitetCombo() {
        return String.valueOf(comboBoxSecteursActivite.getSelectedItem());
    }
    
	public int getSelectedRow() {
        return tableEntreprises.getSelectedRow();
    }
	
	public Object getValueAt(int ligne, int colonne){
		return this.tableEntreprises.getValueAt(ligne, colonne);
	}
}