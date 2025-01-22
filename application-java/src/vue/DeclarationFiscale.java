package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionDeclarationFiscale;
import javax.swing.border.EtchedBorder;


public class DeclarationFiscale extends JInternalFrame {

	private static final long serialVersionUID = 1L;


	private JTable tableBatiments;
	private final JComboBox<String> comboBoxAnnee;
	private JTextField textFieldTotal;
	
	private GestionDeclarationFiscale gestionDecla;
	

	/**
	 * Create the frame.
	 */
	
	@SuppressWarnings("rawtypes")
	public DeclarationFiscale() {
		
		this.gestionDecla = new GestionDeclarationFiscale(this);
		
		setBounds(25, 25, 670, 434);
		getContentPane().setLayout(null);
		
		JLabel lblTitre = new JLabel("Déclaration Fiscale");
		lblTitre.setForeground(new Color(70, 130, 180));
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(0, 10, 658, 44);
		getContentPane().add(lblTitre);
		
		
		// Label et ComboBox pour le bâtiment
        JLabel lblBatiment = new JLabel("Annee :");
        lblBatiment.setBounds(26, 81, 66, 13);
        getContentPane().add(lblBatiment);

        comboBoxAnnee = new JComboBox<>();
        lblBatiment.setLabelFor(comboBoxAnnee);
        comboBoxAnnee.setBounds(88, 77, 136, 21);
        comboBoxAnnee.setModel(new DefaultComboBoxModel<>(new String[] {"Chargement..."}));
        getContentPane().add(comboBoxAnnee);
        this.gestionDecla.remplirComboBoxAnnee();
		
	
		
		JPanel panelBatiment = new JPanel();
		panelBatiment.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "R\u00E9capitulatif par b\u00E2timent", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBatiment.setBounds(26, 116, 580, 155);
		getContentPane().add(panelBatiment);
		panelBatiment.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPanelBatiment = new JScrollPane();
		panelBatiment.add(scrollPanelBatiment, BorderLayout.CENTER);

		tableBatiments = new JTable();
		tableBatiments.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},

			new String[] {
				"Batiment", "Loyers encaissés", "Frais et charges", "Benefice(+) ou deficit(-)"
			}
		));
		scrollPanelBatiment.setViewportView(tableBatiments);
		
		
		JPanel panelBas = new JPanel();
        panelBas.setBounds(26, 293, 580, 39);
        panelBas.setLayout(null);
        getContentPane().add(panelBas);
        
        JLabel lblTotal = new JLabel("Total Bénéfices :");
        lblTotal.setBounds(-24, 13, 127, 13);
        panelBas.add(lblTotal);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
                textFieldTotal = new JTextField();
                textFieldTotal.setBounds(113, 10, 96, 19);
                panelBas.add(textFieldTotal);
		
        this.gestionDecla.remplirTableBatiments();
		gestionDecla.gestionActionComboBoxAnnee(this.comboBoxAnnee);
		
		JPanel panel = new JPanel();
		panel.setBounds(339, 362, 267, 31);
		getContentPane().add(panel);
		
		JButton btnGenerer = new JButton("Générer");
		btnGenerer.addActionListener(this.gestionDecla);
		panel.add(btnGenerer);
		

		JButton btnRetour = new JButton("Retour");
		panel.add(btnRetour);
		btnRetour.addActionListener(this.gestionDecla);
		
	}
	
	// -------------------------------------------------------------------------
    // Méthodes d'aide pour le contrôleur
    // -------------------------------------------------------------------------

	
	public String getTextTotal() {
		return textFieldTotal.getText();
	}
	
	public void setTextTotal(String text) {
		this.textFieldTotal.setText(text);
	}
	
	public JTable getTableBatiments() {
		return tableBatiments;
	}

	public void setTableBatiment(JTable tableBatiment) {
		this.tableBatiments = tableBatiment;
	}
	
	
	/**
     * Remplit la combo des types de contrat d'assurance.
     * @param locataires la liste de type de contrat d'assurance
     */
    public void setComboBoxAnnee(List<String> annees) {
    	comboBoxAnnee.removeAllItems();
        for(String annee : annees) {
        	comboBoxAnnee.addItem(annee);
        }
    }
	
    /**
     * Cette méthode récupère l'indice de la ligne sélectionnée dans la table des bâtiments.
     * 
     * @return L'indice de la ligne sélectionnée dans la table. Si aucune ligne n'est sélectionnée, elle retourne -1.
     */
	public int getSelectedRow() {
        return tableBatiments.getSelectedRow();
    }
	
	public int getNbLignesTable() {
		return tableBatiments.getRowCount();
	}
	
	public int getColumnCount() {
		return tableBatiments.getColumnCount();
	}
	
	/**
	 * Cette méthode récupère la valeur située à la position spécifiée (ligne, colonne) dans la table des bâtiments.
	 * 
	 * @param ligne   L'indice de la ligne dans la table.
	 * @param colonne L'indice de la colonne dans la table.
	 * @return La valeur située à la position spécifiée dans la table.
	 */
	public Object getValueAt(int ligne, int colonne){
		return this.tableBatiments.getValueAt(ligne, colonne);
	}
	
	/**
	 * Cette méthode récupère l'élément actuellement sélectionné dans le JComboBox des années.
	 * 
	 * @return L'élément sélectionné dans le JComboBox des années. Cela peut être une chaîne représentant l'année.
	 */
	public Object getSelectedItemComboAnnee() {
		return comboBoxAnnee.getSelectedItem();
	}
	
	
	/**
     * fonction permettant d'afficher les messages d'erreurs
     */
	public void afficherMessageErreur(String message) {
	    JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	// Dans la classe FenDeclarationFiscale
	public void setWaitCursor() {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setDefaultCursor() {
	    this.setCursor(Cursor.getDefaultCursor());
	}
	
	public String getAnnee() {
		return String.valueOf(comboBoxAnnee.getSelectedItem());
	}

}
