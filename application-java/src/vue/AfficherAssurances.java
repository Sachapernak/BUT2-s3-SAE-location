package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherAssurances;

import javax.swing.JButton;

public class AfficherAssurances extends JInternalFrame  {

	private static final long serialVersionUID = 1L;
	private JTable tableAssurances;
	private JSpinner spinnerAnnee;
	private JComboBox<String> comboBoxTypeContrat;

	private GestionAfficherAssurances gestionFen;
	

	/**
	 * Create the frame.
	 */
	public AfficherAssurances() {
		this.gestionFen = new GestionAfficherAssurances(this);
		
		setBounds(100, 100, 450, 300);
		
		JPanel panelTitre = new JPanel();
		getContentPane().add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Assurances\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelTitre.add(lblNewLabel);
		
		JPanel panelAssurances = new JPanel();
		getContentPane().add(panelAssurances, BorderLayout.CENTER);
		panelAssurances.setLayout(null);
		
		JLabel lblAnneeAssurance = new JLabel("Annee :");
		lblAnneeAssurance.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnneeAssurance.setBounds(37, 26, 45, 13);
		panelAssurances.add(lblAnneeAssurance);
		
		spinnerAnnee = new JSpinner();
		spinnerAnnee.setModel(new SpinnerNumberModel(Integer.valueOf(2025), Integer.valueOf(1970), null, Integer.valueOf(1)));
		spinnerAnnee.setBounds(91, 23, 79, 20);
		panelAssurances.add(spinnerAnnee);
		spinnerAnnee.addChangeListener(this.gestionFen);
		
		JLabel lblTypeContrat = new JLabel("Type de contrat :");
		lblTypeContrat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTypeContrat.setBounds(180, 26, 104, 13);
		panelAssurances.add(lblTypeContrat);
		
		comboBoxTypeContrat = new JComboBox<>();
		comboBoxTypeContrat.setBounds(294, 22, 104, 21);
		panelAssurances.add(comboBoxTypeContrat);
		gestionFen.remplirComboBoxAssurances();
		comboBoxTypeContrat.addActionListener(this.gestionFen);
		
		JScrollPane scrollPaneTableAssurance = new JScrollPane();
		scrollPaneTableAssurance.setBounds(10, 53, 418, 144);
		panelAssurances.add(scrollPaneTableAssurance);
		
		tableAssurances = new JTable();
		tableAssurances.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"Num\u00E9ro de contrat", "Ann\u00E9e", "Type"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPaneTableAssurance.setViewportView(tableAssurances);
		this.gestionFen.initialiserTable();
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionFen);
		btnRetour.setBounds(294, 207, 85, 24);
		panelAssurances.add(btnRetour);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(this.gestionFen);
		btnAjouter.setBounds(180, 207, 85, 24);
		panelAssurances.add(btnAjouter);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(this.gestionFen);
		btnSupprimer.setBounds(56, 207, 94, 24);
		panelAssurances.add(btnSupprimer);
	}
	
	
    /**
     * Remplit la combo des types de contrat d'assurance.
     * @param locataires la liste de type de contrat d'assurance
     */
    public void setComboBoxTypeContrat(List<String> typeContrats) {
    	comboBoxTypeContrat.removeAllItems();
        for(String type : typeContrats) {
        	comboBoxTypeContrat.addItem(type);
        }
    }
    
    public void setTableAssurances(DefaultTableModel tableModel) {
        tableAssurances.setModel(tableModel);
    }
    
    
    /**
     * @return la valeur sélectionnée dans la combo 'type contrat'
     */
    public String getTypeContratCombo() {
        return String.valueOf(comboBoxTypeContrat.getSelectedItem());
    }
    
	public int getSpinnerAnnee() {
		return (int) spinnerAnnee.getValue();
	}

	public JComboBox<String> getComboBoxTypeContrat() {
		return comboBoxTypeContrat;
	}
	
	public int getSelectedRow() {
        return tableAssurances.getSelectedRow();
    }
	
	public Object getValueAt(int ligne, int colonne){
		return this.tableAssurances.getValueAt(ligne, colonne);
	}
	

}
