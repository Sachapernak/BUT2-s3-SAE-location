package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controleur.GestionDetailLoyers;
import controleur.GestionDetailProv;

/**
 * Fenêtre (JDialog) affichant les loyers associés à un logement donné.
 * <p>
 * Permet de visualiser :
 * <ul>
 *   <li>Le loyer de base du logement</li>
 *   <li>L'historique des changements de loyer</li>
 * </ul>
 * Offre la possibilité de supprimer un loyer sélectionné et de quitter la fenêtre.
 */
public class DetailProvParBail extends JDialog {

    private static final long serialVersionUID = 1L;

    /** Panneau principal accueillant les composants de la fenêtre. */
    private final JPanel contentPanel = new JPanel();
    /** Tableau listant les loyers (date de changement, montant). */
    private JTable tableProv;
    /** Bouton pour supprimer le loyer sélectionné dans la table. */
    private JButton btnSupprimer;
    /** Label affichant le titre (Logement : <idLog>) en haut de la fenêtre. */
    private JLabel lblTitre;
    /** Bouton pour quitter la fenêtre. */
    private JButton btnQuitter;

    /** Identifiant du logement pour lequel on affiche les loyers. */
    private String idBail;
    /** Contrôleur gérant la logique (suppression, chargement des loyers, etc.). */
    private GestionDetailProv gest;

    /**
     * Point d'entrée de test. Lance la fenêtre pour un logement factice "LOG001".
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        try {
            DetailProvParBail dialog = new DetailProvParBail("LOG001");
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur principal de la fenêtre. Initialise l'interface et
     * configure le contrôleur, puis charge les données.
     *
     * @param idLogement l'identifiant du logement dont on veut afficher les loyers
     */
    public DetailProvParBail(String idLogement) {
        
        // Stocke l'identifiant du logement et crée le contrôleur
        this.idBail = idLogement;
        this.gest = new GestionDetailProv(this);
        
        // Configuration de la fenêtre
        setBounds(100, 100, 481, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Layout principal (GridBagLayout) pour la disposition des composants
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{120, 0, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);

        // Label titre (Logement : XXX)
        lblTitre = new JLabel("Bail : chargement");
        GridBagConstraints gbcLblTitre = new GridBagConstraints();
        gbcLblTitre.gridwidth = 2;
        gbcLblTitre.insets = new Insets(0, 0, 5, 0);
        gbcLblTitre.gridx = 0;
        gbcLblTitre.gridy = 0;
        contentPanel.add(lblTitre, gbcLblTitre);

        // Zone de défilement (ScrollPane) pour afficher la table des loyers
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(100, 200));
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.insets = new Insets(0, 0, 5, 0);
        gbcScrollPane.gridwidth = 2;
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 1;
        contentPanel.add(scrollPane, gbcScrollPane);
        
        // Table pour l'historique des loyers (date, montant)
        tableProv = new JTable();
        tableProv.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Date de changement", "Montant"}
        ));
        scrollPane.setViewportView(tableProv);
    
        // Bouton "Supprimer"
        btnSupprimer = new JButton("Supprimer");
        GridBagConstraints gbcBtnSupprimer = new GridBagConstraints();
        gbcBtnSupprimer.insets = new Insets(0, 0, 0, 5);
        gbcBtnSupprimer.gridx = 0;
        gbcBtnSupprimer.gridy = 2;
        contentPanel.add(btnSupprimer, gbcBtnSupprimer);

        // Bouton "Quitter"
        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.anchor = GridBagConstraints.EAST;
        gbcBtnQuitter.gridx = 1;
        gbcBtnQuitter.gridy = 2;
        contentPanel.add(btnQuitter, gbcBtnQuitter);
        btnQuitter.setActionCommand("Cancel");
    
        // Chargement initial de la fenêtre + gestion des boutons via le contrôleur
        gest.chargerFen();
        gest.gestionBtnQuitter(btnQuitter);
        gest.gestionBtnSupprimer(btnSupprimer);
    }

    /**
     * Retourne l'identifiant du logement pour lequel s'affichent les loyers.
     *
     * @return l'ID du logement (ex: "LOG001")
     */
    public String getIdBail() {
        return this.idBail;
    }
    
    
    /**
     * Met à jour le label titre pour indiquer l'identifiant du logement.
     *
     * @param idLog l'ID du logement (ex: "LOG001")
     */
    public void setTitreBail(String idBail) {
        lblTitre.setText("Logement : " + idBail);
    }
    
    /**
     * Affiche dans la table (tableLoyers) une liste d'objets 
     * (date, montant) représentant l'historique des loyers.
     *
     * @param donnees liste d'objets (date, montant) pour chaque ligne
     */
    public void chargerTableCharges(List<Object[]> donnees) {
        String[] columns = {"Date de changement", "Montant"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Ajoute chaque ligne au modèle
        for (Object[] row : donnees) {
            model.addRow(row);
        }
        tableProv.setModel(model);

        // Mise en place d'un tri sur la colonne "Date de changement" (index 0), 
        // par ordre décroissant (le plus récent en premier)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableProv.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, javax.swing.SortOrder.DESCENDING)));
        sorter.sort();

        tableProv.revalidate();
        tableProv.repaint();
    }
    
    /**
     * Retourne la date sélectionnée dans la table (colonne 0) ou une chaîne vide 
     * si aucune ligne n'est sélectionnée.
     *
     * @return la date de changement sélectionnée, ou ""
     */
    public String getSelectedDate() {
        int selectedRow = tableProv.getSelectedRow();
        if (selectedRow != -1) {
            // Convertit l'index de la vue en index du modèle
            int modelRow = tableProv.convertRowIndexToModel(selectedRow);
            return String.valueOf(tableProv.getModel().getValueAt(modelRow, 0));
        } else {
            return "";
        }
    }
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     *
     * @param message le message à afficher
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
     * Change le curseur de la fenêtre pour indiquer un traitement en cours.
     *
     * @param wait true pour curseur d'attente, false pour le curseur par défaut
     */
    public void setWaitCursor(boolean wait) {
        if (wait) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
