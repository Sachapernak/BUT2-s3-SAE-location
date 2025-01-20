package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionSelectionBailQuittance;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;

public class SelectionBailQuittance extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private String idLoc;
	private JTextField textFieldDate;
	private JLabel lblIdLoc;
	private JLabel lblDateChoisie;
	private JTable tableBail;
	private JButton btnValider;
	private JButton btnVoir;
	private JButton btnQuitter;
	
	private GestionSelectionBailQuittance gest;


	
	/**
	 * Create the dialog.
	 */
	public SelectionBailQuittance(String idLoc) {
		

		
		gest = new GestionSelectionBailQuittance(this);
		
		this.idLoc = idLoc;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gblContentPanel = new GridBagLayout();
		gblContentPanel.columnWidths = new int[]{0, 0, 80, 0, 0, 0, 0, 0};
		gblContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gblContentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gblContentPanel);
		
		JLabel lblTitre = new JLabel("Quittances : choix du bail");
		GridBagConstraints gbcLblTitre = new GridBagConstraints();
		gbcLblTitre.gridwidth = 5;
		gbcLblTitre.insets = new Insets(0, 0, 5, 5);
		gbcLblTitre.gridx = 1;
		gbcLblTitre.gridy = 0;
		contentPanel.add(lblTitre, gbcLblTitre);
		
		JLabel lblDateQuittance = new JLabel("Date de quittance :");
		GridBagConstraints gbcLblDateQuittance = new GridBagConstraints();
		gbcLblDateQuittance.insets = new Insets(0, 0, 5, 5);
		gbcLblDateQuittance.anchor = GridBagConstraints.EAST;
		gbcLblDateQuittance.gridx = 1;
		gbcLblDateQuittance.gridy = 1;
		contentPanel.add(lblDateQuittance, gbcLblDateQuittance);
		
		textFieldDate = new JTextField();
		GridBagConstraints gbcTextFieldDate = new GridBagConstraints();
		gbcTextFieldDate.gridwidth = 3;
		gbcTextFieldDate.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldDate.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDate.gridx = 2;
		gbcTextFieldDate.gridy = 1;
		contentPanel.add(textFieldDate, gbcTextFieldDate);
		textFieldDate.setColumns(10);
		
		btnValider = new JButton("Valider");
		GridBagConstraints gbcBtnValider = new GridBagConstraints();
		gbcBtnValider.insets = new Insets(0, 0, 5, 5);
		gbcBtnValider.gridx = 5;
		gbcBtnValider.gridy = 1;
		contentPanel.add(btnValider, gbcBtnValider);
		
		JLabel lblLocTitre = new JLabel("Locataire :");
		GridBagConstraints gbcLblLocTitre = new GridBagConstraints();
		gbcLblLocTitre.insets = new Insets(0, 0, 5, 5);
		gbcLblLocTitre.gridx = 1;
		gbcLblLocTitre.gridy = 2;
		contentPanel.add(lblLocTitre, gbcLblLocTitre);
		
		lblIdLoc = new JLabel(idLoc);
		GridBagConstraints gbcLblIdLoc = new GridBagConstraints();
		gbcLblIdLoc.anchor = GridBagConstraints.WEST;
		gbcLblIdLoc.insets = new Insets(0, 0, 5, 5);
		gbcLblIdLoc.gridx = 2;
		gbcLblIdLoc.gridy = 2;
		contentPanel.add(lblIdLoc, gbcLblIdLoc);
		
		JLabel lblNewLabel = new JLabel("Date :");
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.anchor = GridBagConstraints.EAST;
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel.gridx = 3;
		gbcLblNewLabel.gridy = 2;
		contentPanel.add(lblNewLabel, gbcLblNewLabel);
		
		lblDateChoisie = new JLabel("Veuillez entrer une date");
		GridBagConstraints gbcLblDateChoisie = new GridBagConstraints();
		gbcLblDateChoisie.gridwidth = 2;
		gbcLblDateChoisie.insets = new Insets(0, 0, 5, 5);
		gbcLblDateChoisie.gridx = 4;
		gbcLblDateChoisie.gridy = 2;
		contentPanel.add(lblDateChoisie, gbcLblDateChoisie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 100));
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 5;
		gbcScrollPane.insets = new Insets(0, 0, 0, 5);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 1;
		gbcScrollPane.gridy = 3;
		contentPanel.add(scrollPane, gbcScrollPane);
		
		tableBail = new JTable();
		tableBail.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Bail", "Date debut", "Date fin"
			}
		));
		scrollPane.setViewportView(tableBail);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnVoir = new JButton("Voir");
		buttonPane.add(btnVoir);
		getRootPane().setDefaultButton(btnVoir);

		btnQuitter = new JButton("Quitter");
		buttonPane.add(btnQuitter);
		btnQuitter.addActionListener(e-> dispose());
		
		gest.gestionBoutonSuivant(btnVoir);
		gest.gestionBoutonValiderDate(btnValider);
		
		gest.chargerDonnees();
		
	}
	
	public void setDate(String date) {
		lblDateChoisie.setText(date);
		textFieldDate.setText(date);
	}
	
	public String getDate() {
		return lblDateChoisie.getText();
	}
	
	public String getTextFieldDate() {
		return textFieldDate.getText();
	}
	
	public String getLoc() {
		return idLoc;
	}
	
	
	
	public String getSelectedIdBail() {
		int rowNum = tableBail.getSelectedRow();
		
		if (rowNum < 0) {
			return "";
		}
		
		return String.valueOf(tableBail.getModel().getValueAt(rowNum, 0));
	}
	
    /**
     * Affiche une boîte de dialogue d'erreur.
     * 
     * @param message contenu de l'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
	public void chargerTable(List<String[]> lignes) {

	    DefaultTableModel model = (DefaultTableModel) tableBail.getModel();
	    
	    model.setRowCount(0);
	    
	    for (String[] rowData : lignes) {
	        if (rowData != null && rowData.length >= 3) {
	            model.addRow(new Object[] { rowData[0], rowData[1], rowData[2] });
	        }
	    }
	}

}
