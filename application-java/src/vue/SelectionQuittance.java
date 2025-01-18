package vue;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionSelectionQuittance;

public class SelectionQuittance extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNomRecherche;
	private JTable tableLoc;
	private JButton btnSuivant;
	private JButton btnQuitter;

	private GestionSelectionQuittance gest;

	/**
	 * Create the frame.
	 */
	public SelectionQuittance() {
		
		this.gest = new GestionSelectionQuittance(this);
		
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTitre = new JLabel("Quittance de loyer");
		GridBagConstraints gbcLblTitre = new GridBagConstraints();
		gbcLblTitre.insets = new Insets(0, 0, 5, 5);
		gbcLblTitre.gridx = 2;
		gbcLblTitre.gridy = 0;
		getContentPane().add(lblTitre, gbcLblTitre);
		
		JLabel lblRecherche = new JLabel("Nom :");
		GridBagConstraints gbcLblRecherche = new GridBagConstraints();
		gbcLblRecherche.insets = new Insets(0, 0, 5, 5);
		gbcLblRecherche.anchor = GridBagConstraints.EAST;
		gbcLblRecherche.gridx = 1;
		gbcLblRecherche.gridy = 1;
		getContentPane().add(lblRecherche, gbcLblRecherche);
		
		textFieldNomRecherche = new JTextField();
		GridBagConstraints gbcTextFieldNomRecherche = new GridBagConstraints();
		gbcTextFieldNomRecherche.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldNomRecherche.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldNomRecherche.gridx = 2;
		gbcTextFieldNomRecherche.gridy = 1;
		getContentPane().add(textFieldNomRecherche, gbcTextFieldNomRecherche);
		textFieldNomRecherche.setColumns(10);
		
		JButton btnRecherche = new JButton("Rechercher");
		GridBagConstraints gbcBtnRecherche = new GridBagConstraints();
		gbcBtnRecherche.insets = new Insets(0, 0, 5, 5);
		gbcBtnRecherche.gridx = 3;
		gbcBtnRecherche.gridy = 1;
		getContentPane().add(btnRecherche, gbcBtnRecherche);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		GridBagConstraints gbcVerticalStrut = new GridBagConstraints();
		gbcVerticalStrut.insets = new Insets(0, 0, 5, 5);
		gbcVerticalStrut.gridx = 2;
		gbcVerticalStrut.gridy = 2;
		getContentPane().add(verticalStrut, gbcVerticalStrut);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 3;
		gbcScrollPane.insets = new Insets(0, 0, 5, 5);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 1;
		gbcScrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbcScrollPane);
		
		tableLoc = new JTable();
		tableLoc.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Locataire", "Nom", "Pr\u00E9nom"
			}
		) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tableLoc);
		
		btnSuivant = new JButton("Continuer");
		GridBagConstraints gbcBtnSuivant = new GridBagConstraints();
		gbcBtnSuivant.anchor = GridBagConstraints.EAST;
		gbcBtnSuivant.insets = new Insets(0, 0, 0, 5);
		gbcBtnSuivant.gridx = 2;
		gbcBtnSuivant.gridy = 4;
		getContentPane().add(btnSuivant, gbcBtnSuivant);
		
		btnQuitter = new JButton("Quitter");
		GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.anchor = GridBagConstraints.EAST;
		gbcBtnQuitter.insets = new Insets(0, 0, 0, 5);
		gbcBtnQuitter.gridx = 3;
		gbcBtnQuitter.gridy = 4;
		getContentPane().add(btnQuitter, gbcBtnQuitter);
		
		btnQuitter.addActionListener(e -> dispose());
		
		gest.gestionBoutonRecherche(btnRecherche);
		gest.gestionBoutonSuivant(btnSuivant);
		
		gest.chargerDonnees();
	}
	

	
	public String getNomRecherche() {
		return this.textFieldNomRecherche.getText();
	}
	
	public void chargerTable(List<String[]> lignes) {

	    DefaultTableModel model = (DefaultTableModel) tableLoc.getModel();
	    
	    model.setRowCount(0);
	    
	    for (String[] rowData : lignes) {
	        if (rowData != null && rowData.length >= 3) {
	            model.addRow(new Object[] { rowData[0], rowData[1], rowData[2] });
	        }
	    }
	}
	
	public String getSelectedIdLoc() {
		int rowNum = tableLoc.getSelectedRow();
		
		if (rowNum < 0) {
			return "";
		}
		
		return String.valueOf(tableLoc.getModel().getValueAt(rowNum, 0));
	}
	
    /**
     * Affiche une boîte de dialogue d'erreur.
     * 
     * @param message contenu de l'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
	
}
