package vue;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.Cursor;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionAfficherCharge;

/**
 * Fenêtre principale pour afficher les charges associées à des biens locatifs.
 * <p>
 * Cette classe met en place l'interface utilisateur, configure les écouteurs d'événements,
 * et délègue les opérations de chargement et de gestion des données à {@link GestionAfficherCharge}.
 * </p>
 */
public class AfficherCharges extends JFrame {

    private static final long serialVersionUID = 1L;

    // Composants de l'interface utilisateur
    private JComboBox<String> comboBatiment;
    private JComboBox<String> comboLogement;
    private JComboBox<String> comboTypeCharge;
    private JTable tableCharges;
    private JButton btnQuitter;
    private JButton btnAjouterCharge;
    private JButton btnSupprCharge;
    private JButton btnRecharger;
    private JTextField txtRecherche;
    
    private String bat;
    private String log;

    // Référence au contrôleur
    private transient GestionAfficherCharge gest;

    /**
     * Point d'entrée de l'application.
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AfficherCharges::new);
    }

    /**
     * Constructeur par défaut.
     */
    public AfficherCharges() {
        this(null, null);
    }

    /**
     * Constructeur principal de la fenêtre avec possibilité de préselection de bâtiment et logement.
     * 
     * @param bat l'identifiant du bâtiment à pré-sélectionner
     * @param log l'identifiant du logement à pré-sélectionner
     */
    public AfficherCharges(String bat, String log) {
        this.bat = bat;
        this.log = log;
        this.gest = new GestionAfficherCharge(this);
        
        // Configuration de la fenêtre
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(25, 25, 670, 490);
        setContentPane(initMainPanel());
        setSize(730, 572);
        setLocationRelativeTo(null);
        setVisible(true);

        // Configuration du tri et des filtres pour la table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel)tableCharges.getModel());
        tableCharges.setRowSorter(sorter);
        gest.gestionEcouteChampRecherche(sorter, txtRecherche);
        gest.gestionEcouteFiltreType(sorter, comboTypeCharge, txtRecherche);

        // Initialisation des combos et écouteurs
        comboLogement.setEnabled(false);
        gest.chargerComboBoxBatiment();
        gest.gestionActionComboBat(comboBatiment);
        gest.gestionActionComboLog(comboLogement);

        // Configuration des actions des boutons
        btnQuitter.addActionListener(e -> quitter());
        btnAjouterCharge.addActionListener(e -> gest.ajouterNouvelleCharge());
        btnRecharger.addActionListener(e -> gest.recharger());
        gest.gestionBoutonSupprimer(btnSupprCharge, tableCharges);
        gest.initDoubleClickListener(tableCharges);
    }

    /**
     * Initialise et configure le panneau principal de la fenêtre.
     * @return le JPanel principal configuré
     */
    private JPanel initMainPanel() {
        // Configuration du layout principal
        GridBagLayout gblMainPanel = new GridBagLayout();
        gblMainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        gblMainPanel.columnWidths = new int[]{100, 99, 0, 100, 0, 100, 0};
        JPanel mainPanel = new JPanel(gblMainPanel);

        // ----- Ligne 0 : Sélection bâtiment, logement, type de document -----
        addLabel(mainPanel, "Bâtiment :", 0, 0, GridBagConstraints.LINE_END);
        comboBatiment = new JComboBox<>(new String[]{"Chargement..."});
        addComponent(mainPanel, comboBatiment, 1, 0, GridBagConstraints.LINE_START);

        addLabel(mainPanel, "Logement :", 2, 0, GridBagConstraints.LINE_END);
        comboLogement = new JComboBox<>(new String[]{});
        addComponent(mainPanel, comboLogement, 3, 0, GridBagConstraints.LINE_START);

        addLabel(mainPanel, "Type de document :", 4, 0, GridBagConstraints.EAST);
        String[] typeOptions = {"Tous", "Charges", "Devis", "Quittance"};
        comboTypeCharge = new JComboBox<>(typeOptions);
        GridBagConstraints gbc6 = createGbc(5, 0);
        gbc6.anchor = GridBagConstraints.WEST;
        mainPanel.add(comboTypeCharge, gbc6);

        // ----- Ligne 1 : Barre de recherche -----
        addLabel(mainPanel, "Recherche :", 0, 1, GridBagConstraints.LINE_END);
        txtRecherche = new JTextField(15);
        GridBagConstraints gbc8 = createGbc(1, 1);
        gbc8.gridwidth = 5;
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        gbc8.weightx = 1.0;
        mainPanel.add(txtRecherche, gbc8);

        // ----- Ligne 2 : Table dans un JScrollPane -----
        String[] columnNames = {"Numéro", "Type", "Montant au prorata", "Date"};
        Object[][] data = {{0, "Chargement...", 0, "N/A"}};
        DefaultTableModel initialModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCharges = new JTable(initialModel);
        JScrollPane scrollPane = new JScrollPane(tableCharges,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints gbc9 = createGbc(0, 2);
        gbc9.gridwidth = 7;
        gbc9.fill = GridBagConstraints.BOTH;
        gbc9.weightx = 1.0;
        gbc9.weighty = 1.0;
        gbc9.insets = new Insets(5, 5, 5, 0);
        mainPanel.add(scrollPane, gbc9);

        // ----- Ligne 3 : Boutons supplémentaires -----
        btnSupprCharge = new JButton("Supprimer charge");
        GridBagConstraints gbcBtnSupprCharge = createGbc(0, 3);
        mainPanel.add(btnSupprCharge, gbcBtnSupprCharge);

        btnRecharger = new JButton("Recharger");
        GridBagConstraints gbcBtnRecharger = createGbc(3, 3);
        mainPanel.add(btnRecharger, gbcBtnRecharger);

        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbc10 = createGbc(5, 3);
        gbc10.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnQuitter, gbc10);

        btnAjouterCharge = new JButton("Ajouter Charge");
        GridBagConstraints gbc11 = createGbc(4, 3);
        gbc11.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnAjouterCharge, gbc11);

        return mainPanel;
    }

    /**
     * Ajoute un JLabel au panneau avec les contraintes spécifiées.
     */
    private void addLabel(JPanel panel, String text, int gridx, int gridy, int anchor) {
        JLabel label = new JLabel(text);
        GridBagConstraints gbc = createGbc(gridx, gridy);
        gbc.anchor = anchor;
        panel.add(label, gbc);
    }

    /**
     * Ajoute un composant au panneau avec des contraintes simples.
     */
    private void addComponent(JPanel panel, JComboBox<String> combo, int gridx, int gridy, int anchor) {
        GridBagConstraints gbc = createGbc(gridx, gridy);
        gbc.anchor = anchor;
        panel.add(combo, gbc);
    }

    /**
     * Crée une instance de GridBagConstraints avec des paramètres communs.
     */
    private GridBagConstraints createGbc(int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

    /**
     * Ferme la fenêtre.
     */
    public void quitter() {
        dispose();
    }

    /**
     * Filtre la table des charges selon le texte recherché.
     * 
     * @param sorter le trieur de table associé
     */
    public void filtrerTable(TableRowSorter<DefaultTableModel> sorter) {
        String text = txtRecherche.getText();
        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    // Méthodes d'accès aux composants (getters et setters)

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

    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * 
     * @param message le message à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message,
                "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Met à jour la table des charges avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table
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

    // Méthodes pour gérer le curseur d'attente depuis le contrôleur
    public void setCursorAttente(boolean enAttente) {
        setCursor(enAttente ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) 
                            : Cursor.getDefaultCursor());
    }
    
    public void chargementFini(){
        if (this.bat != null && this.log != null) {
        	setItemInCombo(this.comboBatiment, bat);
        	setItemInCombo(this.comboLogement, log);

        }
    }
    
    private void setItemInCombo(JComboBox<String> combo, String val) {
		String item;
		for (int i = 0; i < combo.getItemCount(); i++) {
			item = String.valueOf(combo.getItemAt(i));
			if (item.equalsIgnoreCase(val)) {
				combo.setSelectedIndex(i);
				break;
			}
		}
	}
}
