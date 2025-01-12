package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.WindowConstants;

import java.awt.Insets;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionDetailLoyers;

public class DetailLoyersParLogement extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldLoyer;
	private JTable tableLoyers;
	private JButton btnSupprimer;
	private JLabel lblTitre;
	private JButton btnQuitter;
	private String idLog;
	private GestionDetailLoyers gest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetailLoyersParLogement dialog = new DetailLoyersParLogement("LOG001");
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetailLoyersParLogement(String idLogement) {
		
		this.idLog = idLogement;
		gest = new GestionDetailLoyers(this);
		
		setBounds(100, 100, 481, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{120, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			lblTitre = new JLabel("Logement : Chargement...");
			GridBagConstraints gbc_lblTitre = new GridBagConstraints();
			gbc_lblTitre.gridwidth = 2;
			gbc_lblTitre.insets = new Insets(0, 0, 5, 0);
			gbc_lblTitre.gridx = 0;
			gbc_lblTitre.gridy = 0;
			contentPanel.add(lblTitre, gbc_lblTitre);
		}
		{
			JLabel lblLoyerDeBase = new JLabel("Loyer de base :");
			GridBagConstraints gbc_lblLoyerDeBase = new GridBagConstraints();
			gbc_lblLoyerDeBase.insets = new Insets(0, 0, 5, 5);
			gbc_lblLoyerDeBase.anchor = GridBagConstraints.EAST;
			gbc_lblLoyerDeBase.gridx = 0;
			gbc_lblLoyerDeBase.gridy = 1;
			contentPanel.add(lblLoyerDeBase, gbc_lblLoyerDeBase);
		}
		{
			textFieldLoyer = new JTextField();
			textFieldLoyer.setEditable(false);
			GridBagConstraints gbc_textFieldLoyer = new GridBagConstraints();
			gbc_textFieldLoyer.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldLoyer.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldLoyer.gridx = 1;
			gbc_textFieldLoyer.gridy = 1;
			contentPanel.add(textFieldLoyer, gbc_textFieldLoyer);
			textFieldLoyer.setColumns(10);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(100, 200));
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				tableLoyers = new JTable();
				tableLoyers.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Date de changement", "Montant"
					}
				));
				scrollPane.setViewportView(tableLoyers);
			}
		}
		{
			btnSupprimer = new JButton("Supprimer");
			GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
			gbc_btnSupprimer.insets = new Insets(0, 0, 0, 5);
			gbc_btnSupprimer.gridx = 0;
			gbc_btnSupprimer.gridy = 3;
			contentPanel.add(btnSupprimer, gbc_btnSupprimer);
		}
		{
			btnQuitter = new JButton("Quitter");
			GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
			gbc_btnQuitter.anchor = GridBagConstraints.EAST;
			gbc_btnQuitter.gridx = 1;
			gbc_btnQuitter.gridy = 3;
			contentPanel.add(btnQuitter, gbc_btnQuitter);
			btnQuitter.setActionCommand("Cancel");
		}
		
		gest.chargerFen();
		gest.gestionBtnQuitter(btnQuitter);
		gest.gestionBtnSupprimer(btnSupprimer);
	}
	
	public String getIdLog() {
		return this.idLog;
	}
	
	public void setLoyerDeBase(String base) {
		textFieldLoyer.setText(base);
	}
	
	public void setTitreLogement(String idLog) {
		lblTitre.setText("Logement : " + idLog);
	}
	
	public void chargerTableLoyers(List<Object[]> donnees) {
		
        String[] columns = { "Date de changement", "Montant" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] row : donnees) {
            model.addRow(row);
        }
        tableLoyers.setModel(model);

        // Tri du plus récent au plus ancien sur la colonne "Date" (index = 0)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableLoyers.setRowSorter(sorter);

        // On précise que la colonne date doit être triée "desc"
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, javax.swing.SortOrder.DESCENDING)));
        sorter.sort();

        tableLoyers.revalidate();
        tableLoyers.repaint();
        


	}
	
	public String getSelectedDate() {
	    int selectedRow = tableLoyers.getSelectedRow();
	    if (selectedRow != -1) {
	        // Convertir l'index de la vue en index du modèle
	        int modelRow = tableLoyers.convertRowIndexToModel(selectedRow);
	        return String.valueOf(tableLoyers.getModel().getValueAt(modelRow, 0));
	    } else {
	        return "";
	    }
	}
	
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    public void setWaitCursor(boolean wait) {
        if (wait) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    
}
