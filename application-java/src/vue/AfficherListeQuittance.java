package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionAfficherQuittances;

public class AfficherListeQuittance extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldRecherche;
	private JTable table;
	private JButton btnAjouter;
	private JButton btnQuitter;
	private JButton btnRechercher;
	
	private GestionAfficherQuittances gest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AfficherListeQuittance dialog = new AfficherListeQuittance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AfficherListeQuittance() {
		
		this.gest = new GestionAfficherQuittances(this);
		
		setBounds(100, 100, 481, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblRecherche = new JLabel("Recherche :");
		GridBagConstraints gbc_lblRecherche = new GridBagConstraints();
		gbc_lblRecherche.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecherche.anchor = GridBagConstraints.EAST;
		gbc_lblRecherche.gridx = 0;
		gbc_lblRecherche.gridy = 0;
		contentPanel.add(lblRecherche, gbc_lblRecherche);
	
		textFieldRecherche = new JTextField();
		GridBagConstraints gbc_textFieldRecherche = new GridBagConstraints();
		gbc_textFieldRecherche.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRecherche.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRecherche.gridx = 1;
		gbc_textFieldRecherche.gridy = 0;
		contentPanel.add(textFieldRecherche, gbc_textFieldRecherche);
		textFieldRecherche.setColumns(10);
	
		btnRechercher = new JButton("Rechercher");
		GridBagConstraints gbc_btnRechercher = new GridBagConstraints();
		gbc_btnRechercher.insets = new Insets(0, 0, 5, 0);
		gbc_btnRechercher.gridx = 2;
		gbc_btnRechercher.gridy = 0;
		contentPanel.add(btnRechercher, gbc_btnRechercher);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 300));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Numero doc", "IDLoc", "Bien Locatif"
			}
		));
		scrollPane.setViewportView(table);
	
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnAjouter = new JButton("Ajouter");
		btnAjouter.setActionCommand("");
		buttonPane.add(btnAjouter);
		getRootPane().setDefaultButton(btnAjouter);
	
	
		btnQuitter = new JButton("Quitter");
		btnQuitter.setActionCommand("Cancel");
		buttonPane.add(btnQuitter);
		
		
		btnQuitter.addActionListener(e -> dispose());
		gest.gestionBtnAjouter(btnAjouter);
		
			
		
	}
	
	public String getTextRecherche() {
		return String.valueOf(this.textFieldRecherche);
	}
	
	public String[] getSelectedLigne() {
	    int row = table.getSelectedRow();
	    if (row < 0) {
	        return new String[0];
	    }

	    int colCount = table.getColumnCount();
	    String[] ligneComplete = new String[colCount]; 
	    TableModel model = table.getModel();

	    for (int i = 0; i < colCount; i++) {
	        Object value = model.getValueAt(row, i);
	        ligneComplete[i] = value != null ? value.toString() : null;
	    }

	    return ligneComplete;
	}
	
	
    /**
     * Met à jour la table des charges avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table
     */
    public void chargerTable(List<Object[]> liste) {
        String[] nomsColonnes = {"Date", "Numero doc", "IDLoc", "Bien Locatif"};

        DefaultTableModel model = new DefaultTableModel(nomsColonnes, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] ligne : liste) {
            model.addRow(ligne);
        }

        table.setModel(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.revalidate();
        table.repaint();
    }

	
	

}
