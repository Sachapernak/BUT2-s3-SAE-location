package vue;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionAfficherLoyers;

/**
 * Fenêtre interne permettant l'affichage des loyers par locataire.
 * <p>
 *  - Sélection d'un bâtiment -> charge la liste des biens
 *  - Sélection d'un bien -> charge la liste des locataires
 *  - Sélection d'un locataire -> charge la liste de ses loyers
 * </p>
 */
public class AfficherLoyers extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Composants de l'interface
    private JComboBox<String> comboBatiment;
    private JComboBox<String> comboBien;
    private JComboBox<String> comboLocataire;
    private JTextField textFieldNbLoyers;
    private JTable tableLoyers;

    private JButton btnSupprimer;
    private JButton btnAjouter;
    private JButton btnQuitter;

    // Contrôleur pour gérer les actions de la vue
    private GestionAfficherLoyers gestion;

    /**
     * Constructeur principal de la fenêtre.
     */
    public AfficherLoyers() {
        super("Gestion des Loyers", true, true, true, true);
        initUI();
        this.gestion = new GestionAfficherLoyers(this);
        ajouterListeners();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialise les composants graphiques et organise la disposition (layout).
     */
    private void initUI() {
        setBounds(100, 100, 600, 400);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{30, 90, 90, 30, 70, 80, 30};
        gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
        getContentPane().setLayout(gridBagLayout);

        // Titre
        JLabel lblTitre = new JLabel("Loyers par locataire :");
        GridBagConstraints gbcLblTitre = new GridBagConstraints();
        gbcLblTitre.gridwidth = 7;
        gbcLblTitre.insets = new Insets(0, 0, 5, 0);
        gbcLblTitre.gridx = 0;
        gbcLblTitre.gridy = 0;
        getContentPane().add(lblTitre, gbcLblTitre);

        // Bâtiment
        JLabel lblBatiment = new JLabel("Bâtiment :");
        GridBagConstraints gbcLblBatiment = new GridBagConstraints();
        gbcLblBatiment.anchor = GridBagConstraints.EAST;
        gbcLblBatiment.insets = new Insets(0, 0, 5, 5);
        gbcLblBatiment.gridx = 1;
        gbcLblBatiment.gridy = 1;
        getContentPane().add(lblBatiment, gbcLblBatiment);

        comboBatiment = new JComboBox<>();
        comboBatiment.setModel(new DefaultComboBoxModel<>(new String[] {"Chargement..."}));
        GridBagConstraints gbcComboBatiment = new GridBagConstraints();
        gbcComboBatiment.insets = new Insets(0, 0, 5, 5);
        gbcComboBatiment.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBatiment.gridx = 2;
        gbcComboBatiment.gridy = 1;
        getContentPane().add(comboBatiment, gbcComboBatiment);

        // Bien (Logement)
        JLabel lblBienLocatif = new JLabel("Bien :");
        GridBagConstraints gbcLblBienLocatif = new GridBagConstraints();
        gbcLblBienLocatif.insets = new Insets(0, 0, 5, 5);
        gbcLblBienLocatif.anchor = GridBagConstraints.EAST;
        gbcLblBienLocatif.gridx = 4;
        gbcLblBienLocatif.gridy = 1;
        getContentPane().add(lblBienLocatif, gbcLblBienLocatif);

        comboBien = new JComboBox<>();
        comboBien.setModel(new DefaultComboBoxModel<>(new String[] {}));
        GridBagConstraints gbcComboBien = new GridBagConstraints();
        gbcComboBien.insets = new Insets(0, 0, 5, 5);
        gbcComboBien.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBien.gridx = 5;
        gbcComboBien.gridy = 1;
        getContentPane().add(comboBien, gbcComboBien);

        // Info loyers payés
        JLabel lblLoyers = new JLabel("Loyers payés :");
        GridBagConstraints gbcLblLoyers = new GridBagConstraints();
        gbcLblLoyers.anchor = GridBagConstraints.EAST;
        gbcLblLoyers.insets = new Insets(0, 0, 5, 5);
        gbcLblLoyers.gridx = 1;
        gbcLblLoyers.gridy = 2;
        getContentPane().add(lblLoyers, gbcLblLoyers);

        textFieldNbLoyers = new JTextField("12 / 12");
        GridBagConstraints gbcTextFieldNbLoyers = new GridBagConstraints();
        gbcTextFieldNbLoyers.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldNbLoyers.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldNbLoyers.gridx = 2;
        gbcTextFieldNbLoyers.gridy = 2;
        getContentPane().add(textFieldNbLoyers, gbcTextFieldNbLoyers);
        textFieldNbLoyers.setColumns(10);

        // Locataire
        JLabel lblLocataire = new JLabel("Locataire :");
        GridBagConstraints gbcLblLocataire = new GridBagConstraints();
        gbcLblLocataire.anchor = GridBagConstraints.EAST;
        gbcLblLocataire.insets = new Insets(0, 0, 5, 5);
        gbcLblLocataire.gridx = 4;
        gbcLblLocataire.gridy = 2;
        getContentPane().add(lblLocataire, gbcLblLocataire);

        comboLocataire = new JComboBox<>();
        comboLocataire.setModel(new DefaultComboBoxModel<>(new String[] {}));
        GridBagConstraints gbcComboLocataire = new GridBagConstraints();
        gbcComboLocataire.insets = new Insets(0, 0, 5, 5);
        gbcComboLocataire.fill = GridBagConstraints.HORIZONTAL;
        gbcComboLocataire.gridx = 5;
        gbcComboLocataire.gridy = 2;
        getContentPane().add(comboLocataire, gbcComboLocataire);

        // Table des loyers
        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.gridwidth = 5;
        gbcScrollPane.insets = new Insets(0, 0, 5, 5);
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 1;
        gbcScrollPane.gridy = 3;
        getContentPane().add(scrollPane, gbcScrollPane);

        tableLoyers = new JTable();
        // Modèle initial
        tableLoyers.setModel(new DefaultTableModel(
            new Object[][] { {"", "", ""} },
            new String[] { "ID Bien", "Date", "Montant" }
        ));
        scrollPane.setViewportView(tableLoyers);

        // Bouton Supprimer
        btnSupprimer = new JButton("Supprimer");
        GridBagConstraints gbcBtnSupprimer = new GridBagConstraints();
        gbcBtnSupprimer.insets = new Insets(0, 0, 5, 5);
        gbcBtnSupprimer.gridx = 1;
        gbcBtnSupprimer.gridy = 4;
        getContentPane().add(btnSupprimer, gbcBtnSupprimer);

        // Bouton Ajouter
        btnAjouter = new JButton("Ajouter");
        GridBagConstraints gbcBtnAjouter = new GridBagConstraints();
        gbcBtnAjouter.insets = new Insets(0, 0, 5, 5);
        gbcBtnAjouter.gridx = 4;
        gbcBtnAjouter.gridy = 4;
        getContentPane().add(btnAjouter, gbcBtnAjouter);

        // Bouton Quitter (Annuler)
        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.insets = new Insets(0, 0, 5, 5);
        gbcBtnQuitter.gridx = 5;
        gbcBtnQuitter.gridy = 4;
        getContentPane().add(btnQuitter, gbcBtnQuitter);
    }

    /**
     * Ajoute tous les écouteurs (listeners) nécessaires aux composants.
     */
    private void ajouterListeners() {
        // Changement de bâtiment -> charge la liste des biens
        comboBatiment.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                gestion.chargerComboBien();
            }
        });

        // Changement de bien -> charge la liste des locataires
        comboBien.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                gestion.chargerComboLocataire();
            }
        });

        // Changement de locataire -> charge la table des loyers
        comboLocataire.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                gestion.chargerTableLoyers();
            }
        });

        // Bouton Ajouter (ouvre un panneau pour ajouter un loyer) -> TODO
        btnAjouter.addActionListener(e -> gestion.ajouterLoyer());

        // Bouton Supprimer (supprime un loyer sélectionné)
        btnSupprimer.addActionListener(e -> gestion.supprimerLoyer());

        // Bouton Quitter (annule / ferme la fenêtre)
        btnQuitter.addActionListener(e -> dispose());
    }

    /**
     * Met à jour la table avec la liste fournie, triée du plus récent au plus ancien.
     * 
     * @param data Données à afficher
     */
    public void majTableLoyers(List<Object[]> data) {
        String[] columns = { "ID Bien", "Date", "Montant" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }
        tableLoyers.setModel(model);

        // Tri du plus récent au plus ancien sur la colonne "Date" (index = 1)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableLoyers.setRowSorter(sorter);

        // On précise que la colonne date doit être triée "desc"
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, javax.swing.SortOrder.DESCENDING)));
        sorter.sort();

        tableLoyers.revalidate();
        tableLoyers.repaint();
    }

    /**
     * Permet de définir la liste de bâtiments.
     * 
     * @param listBats liste des identifiants de bâtiments
     */
    public void setListBatiment(List<String> listBats) {
        comboBatiment.setModel(new DefaultComboBoxModel<>(listBats.toArray(new String[0])));
    }

    /**
     * Permet de définir la liste de biens (logements).
     * 
     * @param listBiens liste des identifiants de biens
     */
    public void setListBien(List<String> listBiens) {
        comboBien.setModel(new DefaultComboBoxModel<>(listBiens.toArray(new String[0])));
    }

    /**
     * Permet de définir la liste de locataires.
     * 
     * @param listLocataires liste des locataires
     */
    public void setListLocataire(List<String> listLocataires) {
        comboLocataire.setModel(new DefaultComboBoxModel<>(listLocataires.toArray(new String[0])));
    }

    /**
     * Modifie le champ indiquant le nombre de loyers payés (par exemple "12 / 12").
     * 
     * @param info texte à afficher
     */
    public void setNbLoyers(String info) {
        // //TODO : Calculer réellement le ratio payés / total
        textFieldNbLoyers.setText(info);
    }

    /**
     * Renvoie l'identifiant du bâtiment actuellement sélectionné.
     * 
     * @return id du bâtiment
     */
    public String getSelectedBatiment() {
        return (String) comboBatiment.getSelectedItem();
    }

    /**
     * Renvoie l'identifiant du bien actuellement sélectionné.
     * 
     * @return id du bien
     */
    public String getSelectedBien() {
        return (String) comboBien.getSelectedItem();
    }

    /**
     * Renvoie l'identifiant du locataire actuellement sélectionné.
     * 
     * @return id du locataire
     */
    public String getSelectedLocataire() {
        return (String) comboLocataire.getSelectedItem();
    }

    /**
     * Retourne l'index de la ligne sélectionnée dans la table.
     * 
     * @return index de ligne, ou -1 si aucune
     */
    public int getSelectedRow() {
        return tableLoyers.getSelectedRow();
    }

    /**
     * Retourne la valeur "ID Bien" de la ligne sélectionnée, si existante.
     * 
     * @return l'ID du bien (Object), ou null si rien.
     */
    public Object getValueAtSelectedRow() {
        int row = tableLoyers.getSelectedRow();
        if (row == -1) return null;
        int modelIndex = tableLoyers.convertRowIndexToModel(row);
        return tableLoyers.getModel().getValueAt(modelIndex, 0);
    }

    /**
     * Affiche une boîte de dialogue d'erreur.
     * 
     * @param message contenu de l'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Modifie le curseur de la fenêtre (sablier ou normal).
     * 
     * @param wait true pour curseur sablier, false pour normal
     */
    public void setWaitCursor(boolean wait) {
        if (wait) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Méthode pour tester directement l'IHM.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AfficherLoyers frame = new AfficherLoyers();
            frame.setVisible(true);
        });
    }
}
