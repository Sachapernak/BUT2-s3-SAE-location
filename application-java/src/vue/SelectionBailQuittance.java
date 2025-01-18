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
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 80, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblTitre = new JLabel("Quittances : choix du bail");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.gridwidth = 5;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 1;
		gbc_lblTitre.gridy = 0;
		contentPanel.add(lblTitre, gbc_lblTitre);
		
		JLabel lblDateQuittance = new JLabel("Date de quittance :");
		GridBagConstraints gbc_lblDateQuittance = new GridBagConstraints();
		gbc_lblDateQuittance.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateQuittance.anchor = GridBagConstraints.EAST;
		gbc_lblDateQuittance.gridx = 1;
		gbc_lblDateQuittance.gridy = 1;
		contentPanel.add(lblDateQuittance, gbc_lblDateQuittance);
		
		textFieldDate = new JTextField();
		GridBagConstraints gbc_textFieldDate = new GridBagConstraints();
		gbc_textFieldDate.gridwidth = 3;
		gbc_textFieldDate.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDate.gridx = 2;
		gbc_textFieldDate.gridy = 1;
		contentPanel.add(textFieldDate, gbc_textFieldDate);
		textFieldDate.setColumns(10);
		
		btnValider = new JButton("Valider");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.insets = new Insets(0, 0, 5, 5);
		gbc_btnValider.gridx = 5;
		gbc_btnValider.gridy = 1;
		contentPanel.add(btnValider, gbc_btnValider);
		
		JLabel lblLocTitre = new JLabel("Locataire :");
		GridBagConstraints gbc_lblLocTitre = new GridBagConstraints();
		gbc_lblLocTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocTitre.gridx = 1;
		gbc_lblLocTitre.gridy = 2;
		contentPanel.add(lblLocTitre, gbc_lblLocTitre);
		
		lblIdLoc = new JLabel(idLoc);
		GridBagConstraints gbc_lblIdLoc = new GridBagConstraints();
		gbc_lblIdLoc.anchor = GridBagConstraints.WEST;
		gbc_lblIdLoc.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdLoc.gridx = 2;
		gbc_lblIdLoc.gridy = 2;
		contentPanel.add(lblIdLoc, gbc_lblIdLoc);
		
		JLabel lblNewLabel = new JLabel("Date :");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 2;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		lblDateChoisie = new JLabel("Veuillez entrer une date");
		GridBagConstraints gbc_lblDateChoisie = new GridBagConstraints();
		gbc_lblDateChoisie.gridwidth = 2;
		gbc_lblDateChoisie.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateChoisie.gridx = 4;
		gbc_lblDateChoisie.gridy = 2;
		contentPanel.add(lblDateChoisie, gbc_lblDateChoisie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 100));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
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
