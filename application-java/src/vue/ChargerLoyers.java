package vue;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controleur.GestionChargerLoyer;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Fenêtre (JDialog) permettant de charger des loyers à partir d'un fichier CSV.
 * L'utilisateur peut parcourir le disque pour sélectionner un fichier,
 * puis visualiser et éventuellement manipuler (ajouter/supprimer) les lignes
 * dans un tableau avant de confirmer le chargement.
 */
public class ChargerLoyers extends JDialog {
    
    private static final long serialVersionUID = 1L;

    /** Panneau principal contenant les éléments de la fenêtre. */
    private final JPanel contentPanelPrincipal = new JPanel();

    /** Champ texte affichant le chemin du fichier CSV. */
    private JTextField textFieldLienFichier;
    /** Tableau affichant les loyers chargés. */
    private JTable tableLoyer;

    /** Label indiquant le nombre de loyers chargés. */
    private JLabel lblNbLoyers;
    /** Label pour désigner le champ "Fichier". */
    private JLabel lblFichier;

    /** Bouton pour parcourir le disque (ouvrir un JFileChooser). */
    private JButton btnParcourirFichier;
    /** Bouton pour déclencher le chargement effectif des loyers. */
    private JButton btnCharger;
    /** Bouton pour annuler et fermer la fenêtre. */
    private JButton btnAnnuler;

    /** Contrôleur gérant la logique de chargement de fichiers CSV. */
    private final GestionChargerLoyer gestionnaireCharger;

    /** Bouton pour ajouter une ligne vide dans le tableau. */
    private JButton btnAjouter;
    /** Bouton pour supprimer une ligne sélectionnée du tableau. */
    private JButton btnSupprimer;

    /** Zone de défilement contenant le tableau des loyers. */
    private JScrollPane scrollPaneLoyers;

    /**
     * Point d'entrée pour tester la boîte de dialogue en autonome.
     * 
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        try {
            ChargerLoyers dialog = new ChargerLoyers();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur principal de la fenêtre ChargerLoyers.
     * Initialise le contrôleur, les composants de l'interface
     * et configure leurs positions/layout.
     */
    public ChargerLoyers() {
        // Instanciation du contrôleur
        this.gestionnaireCharger = new GestionChargerLoyer(this);

        // Configuration de la fenêtre
        setBounds(100, 100, 530, 381);
        getContentPane().setLayout(new BorderLayout());
        contentPanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanelPrincipal, BorderLayout.CENTER);

        // Layout principal (GridBagLayout)
        GridBagLayout gblContentPanelPrincipal = new GridBagLayout();
        gblContentPanelPrincipal.columnWidths = new int[]{0, 0, 0, 0};
        gblContentPanelPrincipal.rowHeights = new int[]{0, 0, 0, 100, 0, 0};
        gblContentPanelPrincipal.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblContentPanelPrincipal.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPanelPrincipal.setLayout(gblContentPanelPrincipal);

        // Label principal en haut
        JLabel lblCharger = new JLabel("Charger Loyers");
        GridBagConstraints gbcLblCharger = new GridBagConstraints();
        gbcLblCharger.gridwidth = 3;
        gbcLblCharger.insets = new Insets(0, 0, 5, 0);
        gbcLblCharger.gridx = 0;
        gbcLblCharger.gridy = 0;
        contentPanelPrincipal.add(lblCharger, gbcLblCharger);

        // Label "Fichier"
        lblFichier = new JLabel("Fichier : ");
        GridBagConstraints gbcLblFichier = new GridBagConstraints();
        gbcLblFichier.insets = new Insets(0, 0, 5, 5);
        gbcLblFichier.anchor = GridBagConstraints.EAST;
        gbcLblFichier.gridx = 0;
        gbcLblFichier.gridy = 1;
        contentPanelPrincipal.add(lblFichier, gbcLblFichier);

        // Champ texte pour le lien du fichier CSV
        textFieldLienFichier = new JTextField();
        GridBagConstraints gbcTextFieldLienFichier = new GridBagConstraints();
        gbcTextFieldLienFichier.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldLienFichier.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldLienFichier.gridx = 1;
        gbcTextFieldLienFichier.gridy = 1;
        contentPanelPrincipal.add(textFieldLienFichier, gbcTextFieldLienFichier);
        textFieldLienFichier.setColumns(10);

        // Bouton "Parcourir"
        btnParcourirFichier = new JButton("Parcourir");
        GridBagConstraints gbcBtnParcourirFichier = new GridBagConstraints();
        gbcBtnParcourirFichier.insets = new Insets(0, 0, 5, 0);
        gbcBtnParcourirFichier.gridx = 2;
        gbcBtnParcourirFichier.gridy = 1;
        contentPanelPrincipal.add(btnParcourirFichier, gbcBtnParcourirFichier);

        // Label indiquant le nombre de loyers
        lblNbLoyers = new JLabel("0");
        GridBagConstraints gbcLblNbLoyers = new GridBagConstraints();
        gbcLblNbLoyers.anchor = GridBagConstraints.EAST;
        gbcLblNbLoyers.insets = new Insets(0, 0, 5, 5);
        gbcLblNbLoyers.gridx = 0;
        gbcLblNbLoyers.gridy = 2;
        contentPanelPrincipal.add(lblNbLoyers, gbcLblNbLoyers);

        // Label "loyer(s) à ajouter :"
        JLabel lblLoyersAAjouter = new JLabel("loyer(s) à ajouter : ");
        GridBagConstraints gbcLblLoyersAAjouter = new GridBagConstraints();
        gbcLblLoyersAAjouter.anchor = GridBagConstraints.WEST;
        gbcLblLoyersAAjouter.insets = new Insets(0, 0, 5, 5);
        gbcLblLoyersAAjouter.gridx = 1;
        gbcLblLoyersAAjouter.gridy = 2;
        contentPanelPrincipal.add(lblLoyersAAjouter, gbcLblLoyersAAjouter);

        // Zone de défilement pour la table des loyers
        scrollPaneLoyers = new JScrollPane();
        scrollPaneLoyers.setPreferredSize(new Dimension(100, 200));
        GridBagConstraints gbcScrollPaneLoyers = new GridBagConstraints();
        gbcScrollPaneLoyers.insets = new Insets(0, 0, 5, 0);
        gbcScrollPaneLoyers.gridwidth = 3;
        gbcScrollPaneLoyers.fill = GridBagConstraints.BOTH;
        gbcScrollPaneLoyers.gridx = 0;
        gbcScrollPaneLoyers.gridy = 3;
        contentPanelPrincipal.add(scrollPaneLoyers, gbcScrollPaneLoyers);

        // Table affichant les loyers (5 colonnes)
        tableLoyer = new JTable();
        tableLoyer.setModel(new DefaultTableModel(
            null,
            new String[] {
                "ID Logement", "ID Locataire", "Date", "Montant total", "NumDoc"
            }
        ));
        scrollPaneLoyers.setViewportView(tableLoyer);

        // Panel bas (flow layout) pour les boutons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnSupprimer = new JButton("Supprimer ligne");
        buttonPane.add(btnSupprimer);

        btnAjouter = new JButton("Ajouter ligne");
        buttonPane.add(btnAjouter);

        btnCharger = new JButton("Charger");
        btnCharger.setActionCommand("OK");
        buttonPane.add(btnCharger);
        getRootPane().setDefaultButton(btnCharger);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setActionCommand("Cancel");
        buttonPane.add(btnAnnuler);

        // Configuration des listeners via le contrôleur
        gestionnaireCharger.gestionParcourirFichier(btnParcourirFichier);
        gestionnaireCharger.gestionCharger(btnCharger);
        gestionnaireCharger.gestionAnnuler(btnAnnuler);
        gestionnaireCharger.gestionBtnAjouter(btnAjouter);
        gestionnaireCharger.gestionBtnSupprimer(btnSupprimer);
    }

    /**
     * Assigne un texte au champ indiquant le chemin du fichier CSV.
     *
     * @param text le chemin du fichier à afficher
     */
    public void setTextLienFichier(String text) {
        textFieldLienFichier.setText(text);
    }

    /**
     * Met à jour la table (JTable) avec une liste de loyers (sous forme de liste de listes).
     * Puis met à jour le label du nombre de loyers.
     *
     * @param data liste des données à afficher, chaque élément représente une ligne
     */
    public void afficherDonneesLoyer(List<List<String>> data) {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        model.setRowCount(0); // Nettoie les lignes existantes

        for (List<String> row : data) {
            model.addRow(row.toArray());
        }

        setTxtNbLoyers(String.valueOf(data.size()));
    }

    /**
     * Met à jour le label indiquant le nombre de loyers chargés.
     *
     * @param valeur la nouvelle valeur du label
     */
    public void setTxtNbLoyers(String valeur) {
        lblNbLoyers.setText(valeur);
    }

    /**
     * Ajoute une valeur entière au nombre de loyers existant dans le label.
     *
     * @param valeur la valeur à ajouter
     */
    public void ajouterNbLoyers(int valeur) {
        lblNbLoyers.setText(String.valueOf(Integer.valueOf(lblNbLoyers.getText()) + valeur));
    }

    /**
     * @return la chaîne correspondant au chemin du fichier CSV saisi
     */
    public String getLienFichier() {
        return textFieldLienFichier.getText();
    }

    /**
     * Récupère les données de la JTable et les retourne sous forme de liste de listes de chaînes.
     * Les lignes incomplètes ou vides sont ignorées.
     *
     * @return liste des lignes valides du tableau
     */
    public List<List<String>> getListsTable() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        int rowCount = model.getRowCount();
        List<List<String>> validRows = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            List<String> rowData = getLigneIfValid(rowIndex);
            if (!rowData.isEmpty()) {
                validRows.add(rowData);
            }
        }
        return validRows;
    }

    /**
     * Récupère la ligne à l'indice 'rowIndex' si elle est complète
     * (aucune cellule vide). Retourne une liste vide si la ligne est incomplète.
     *
     * @param rowIndex indice de la ligne à examiner
     * @return la liste des valeurs si la ligne est complète, sinon liste vide
     */
    public List<String> getLigneIfValid(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        int colCount = model.getColumnCount();
        List<String> rowData = new ArrayList<>(colCount);
        boolean skipRow = false;

        for (int colIndex = 0; colIndex < colCount; colIndex++) {
            Object cellValue = model.getValueAt(rowIndex, colIndex);
            if (cellValue == null || cellValue.toString().trim().isEmpty()) {
                skipRow = true;  
                break;
            }
            rowData.add(cellValue.toString().trim());
        }

        return skipRow ? Collections.emptyList() : rowData;
    }

    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     *
     * @param message le texte de l'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
            this,
            "Erreur : \n" + message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * @return l'indice de la ligne sélectionnée dans la JTable (ou -1 si aucune sélection).
     */
    public int getSelectLineIndex() {
        return tableLoyer.getSelectedRow();
    }

    /**
     * Supprime la ligne spécifiée. Si l'indice est invalide (-1),
     * supprime la dernière ligne existante si possible.
     *
     * @param ligne l'indice de la ligne à supprimer
     */
    public void supprimerLigne(int ligne) {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();

        if (ligne >= 0) {
            model.removeRow(ligne);
        } else if (nbLigneTable() > 0) {
            model.removeRow(nbLigneTable() - 1);
        }
    }

    /**
     * Ajoute une nouvelle ligne vide (cellules null).
     */
    public void ajouterLigneVide() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        model.addRow(new String[] {null, null, null, null});
    }

    /**
     * @return le nombre total de lignes dans le tableau
     */
    public int nbLigneTable() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        return model.getRowCount();
    }

    /**
     * Fait défiler la barre de défilement vers le bas de la table.
     */
    public void scrollToBottom() {
        JScrollBar verticalBar = scrollPaneLoyers.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    /**
     * Réinitialise la fenêtre : supprime toutes les lignes du tableau,
     * vide le champ de texte pour le lien et remet le compteur de loyers à 0.
     */
    public void fenClear() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        model.setRowCount(0); 
        setTextLienFichier("");
        setTxtNbLoyers("0");
    }
}
