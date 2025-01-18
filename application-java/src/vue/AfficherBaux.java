package vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherBaux;

public class AfficherBaux extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable tableBaux;
    private JButton btnQuitter;
    private GestionAfficherBaux gest;


    /**
     * Create the frame.
     */
    public AfficherBaux() {
        gest = new GestionAfficherBaux(this);

        setBounds(100, 100, 450, 300);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblTitre = new JLabel("Baux :");
        GridBagConstraints gbcLblTitre = new GridBagConstraints();
        gbcLblTitre.insets = new Insets(0, 0, 5, 0);
        gbcLblTitre.gridx = 0;
        gbcLblTitre.gridy = 0;
        getContentPane().add(lblTitre, gbcLblTitre);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(100, 200));
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.insets = new Insets(0, 0, 5, 0);
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 1;
        getContentPane().add(scrollPane, gbcScrollPane);

        tableBaux = new JTable();
        tableBaux.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Identifiant", "Début", "Fin", "Bien"
            }
        ) {
 
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
                String.class, String.class, String.class, String.class
            };
            boolean[] columnEditables = new boolean[] {
                false, false, false, false
            };
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(tableBaux);

        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.anchor = GridBagConstraints.EAST;
        gbcBtnQuitter.gridx = 0;
        gbcBtnQuitter.gridy = 2;
        getContentPane().add(btnQuitter, gbcBtnQuitter);

        btnQuitter.addActionListener(e -> dispose());
        gest.chargerDonneesTable();
    }
    
    /**
     * Remplit la JTable tableBaux avec les données fournies.
     *
     * @param data liste de tableaux de chaînes, chaque tableau représentant une ligne de données pour la table
     */
    public void remplirTable(List<String[]> data) {
        // Obtient le modèle de la table
        DefaultTableModel model = (DefaultTableModel) tableBaux.getModel();
        
        model.setRowCount(0);

        for (String[] rowData : data) {
            model.addRow(rowData);
        }
    }
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * 
     * @param message le message à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message,
                "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
