package vue;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionAfficherCharge;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class AfficherCharges extends JFrame {

    private static final long serialVersionUID = 1L;

    // Déclaration des composants de l'interface
    private JComboBox<String> comboBatiment;
    private JComboBox<String> comboLogement;
    private JComboBox<String> comboTypeCharge;
    private JTable tableCharges;
    private JButton btnQuitter;
    private JButton btnAjouterCharge;
    private JTextField txtRecherche;
    private GestionAfficherCharge gest;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AfficherCharges::new);
    }

    public AfficherCharges() {
        super("Gestion des Charges");
        this.gest = new GestionAfficherCharge(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuration du layout principal
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        gbl_mainPanel.columnWidths = new int[]{90, 99, 0, 100, 0, 110, 0};
        JPanel mainPanel = new JPanel(gbl_mainPanel);

        // ----- Ligne 0 : Sélection de batiment, logement, type de document -----
        JLabel lblBatiment = new JLabel("Bâtiment :");
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0; gbc1.gridy = 0;
        gbc1.insets = new Insets(5, 5, 5, 5);
        gbc1.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(lblBatiment, gbc1);

        comboBatiment = new JComboBox<>(new String[]{"Chargement..."});
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1; gbc2.gridy = 0;
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(comboBatiment, gbc2);

        JLabel lblLogement = new JLabel("Logement :");
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 2; gbc3.gridy = 0;
        gbc3.insets = new Insets(5, 5, 5, 5);
        gbc3.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(lblLogement, gbc3);

        comboLogement = new JComboBox<>(new String[]{});
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 3; gbc4.gridy = 0;
        gbc4.insets = new Insets(5, 5, 5, 5);
        gbc4.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(comboLogement, gbc4);

        JLabel lblCharge = new JLabel("Type de document :");
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 4; gbc5.gridy = 0;
        gbc5.insets = new Insets(5, 5, 5, 5);
        gbc5.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblCharge, gbc5);

        String[] typeOptions = {"Tous", "Charges", "Devis", "Quittance"};
        comboTypeCharge = new JComboBox<>(typeOptions);
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.anchor = GridBagConstraints.WEST;
        gbc6.gridx = 5; gbc6.gridy = 0;
        gbc6.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(comboTypeCharge, gbc6);

        // ----- Ligne 1 : Barre de recherche -----
        JLabel lblRecherche = new JLabel("Recherche :");
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.gridx = 0; gbc7.gridy = 1;
        gbc7.insets = new Insets(5, 5, 5, 5);
        gbc7.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(lblRecherche, gbc7);

        txtRecherche = new JTextField(15);
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.gridx = 1; gbc8.gridy = 1;
        gbc8.gridwidth = 5;
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        gbc8.weightx = 1.0;
        gbc8.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(txtRecherche, gbc8);

        // ----- Ligne 2 : Table dans un JScrollPane -----
        String[] columnNames = {"Numéro", "Type", "Montant au prorata", "Date"};
        Object[][] data = {{0, "Chargement...", 0, "N/A"}};

        // Création du modèle non éditable pour la table
        DefaultTableModel initialModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCharges = new JTable(initialModel);

        // ScrollPane avec barres de défilement automatiques
        JScrollPane scrollPane = new JScrollPane(tableCharges,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.gridx = 0; gbc9.gridy = 2;
        gbc9.gridwidth = 7;
        gbc9.fill = GridBagConstraints.BOTH;
        gbc9.weightx = 1.0; gbc9.weighty = 1.0;
        gbc9.insets = new Insets(5, 5, 5, 0);
        mainPanel.add(scrollPane, gbc9);

        // ----- Ligne 3 : Boutons Quitter et Ajouter Charge -----
        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.gridx = 5; gbc10.gridy = 3;
        gbc10.fill = GridBagConstraints.NONE;
        gbc10.weightx = 0; gbc10.weighty = 0;
        gbc10.anchor = GridBagConstraints.EAST;
        gbc10.insets = new Insets(5, 5, 10, 5);
        mainPanel.add(btnQuitter, gbc10);

        btnAjouterCharge = new JButton("Ajouter Charge");
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.gridx = 4; gbc11.gridy = 3;
        gbc11.fill = GridBagConstraints.NONE;
        gbc11.weightx = 0; gbc11.weighty = 0;
        gbc11.anchor = GridBagConstraints.EAST;
        gbc11.insets = new Insets(5, 5, 10, 5);
        mainPanel.add(btnAjouterCharge, gbc11);

        // Configuration finale de la fenêtre
        setContentPane(mainPanel);
        setSize(630, 572);
        setLocationRelativeTo(null);
        setVisible(true);

        // Mise en place du tri, des filtres et des écouteurs
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(initialModel);
        tableCharges.setRowSorter(sorter);
        gest.gestionEcouteChampRecherche(sorter, txtRecherche);
        gest.gestionEcouteFiltreType(sorter, comboTypeCharge, txtRecherche);

        comboLogement.setEnabled(false);
        gest.chargerComboBoxBatiment();
        gest.gestionActionComboBat(comboBatiment);
        gest.gestionActionComboLog(comboLogement);

        // Actions des boutons
        btnQuitter.addActionListener(e -> quitter());
        btnAjouterCharge.addActionListener(e -> gest.ajouterNouvelleCharge());

        // Gestion du double-clic sur une ligne de la table
        tableCharges.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Si double-clic et une ligne est sélectionnée
                if (e.getClickCount() == 2 && tableCharges.getSelectedRow() != -1) {
                    int rowIndex = tableCharges.getSelectedRow();
                    int modelIndex = tableCharges.convertRowIndexToModel(rowIndex);
                    Object numeroDoc = tableCharges.getModel().getValueAt(modelIndex, 0);
                    Object dateDoc = tableCharges.getModel().getValueAt(modelIndex, 3);
                    gest.afficherChargeDetail((String) numeroDoc, (String) dateDoc);
                }
            }
        });
    }

    /** 
     * Filtre la table en fonction du texte de recherche.
     * Utilise une regex insensible à la casse pour filtrer toutes les colonnes.
     */
    public void filtrerTable(TableRowSorter<DefaultTableModel> sorter) {
        String text = txtRecherche.getText();
        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    // Accesseurs et méthodes de gestion des combos et de la table

    public void setListBatiment(List<String> batiments) {
        comboBatiment.setModel(new DefaultComboBoxModel<>(batiments.toArray(new String[0])));
    }
    public String getSelectBatiment() {
        return (String) comboBatiment.getSelectedItem();
    }
    
    public void setListLogement(List<String> logements) {
        comboLogement.setModel(new DefaultComboBoxModel<>(logements.toArray(new String[0])));
        comboLogement.setEnabled(true);
    }
    public String getSelectLogement() {
        return (String) comboLogement.getSelectedItem();
    }
    
    public String getSelectCharge() {
        return (String) comboTypeCharge.getSelectedItem();
    }
    
    public String getValRecherche() {
        return txtRecherche.getText();
    }
    
    public void quitter() {
        dispose();
    }

    /**
     * Charge les données dans la table et met en place les filtres.
     * @param liste Liste d'objets représentant les lignes à afficher.
     */
    public void chargerTable(List<Object[]> liste) {
        String[] nomsColonnes = {"Numéro", "Type", "Montant au prorata", "Date"};

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

        tableCharges.setModel(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableCharges.setRowSorter(sorter);

        gest.gestionEcouteChampRecherche(sorter, txtRecherche);
        gest.gestionEcouteFiltreType(sorter, comboTypeCharge, txtRecherche);

        tableCharges.revalidate();
        tableCharges.repaint();
    }

    public Object getValueAt(int ligne, int col) {
        return tableCharges.getValueAt(ligne, col);
    }
    
    public void deleteLigneAt(int ligne) {
        DefaultTableModel model = (DefaultTableModel) tableCharges.getModel();
        model.removeRow(ligne);
    }
    
    public JTable getTableCharges() {
        return tableCharges;
    }
    
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
