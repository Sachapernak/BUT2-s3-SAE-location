package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import modele.DocumentComptable;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import vue.AfficherCharges;
import vue.AjouterCharge;
import vue.DetailChargeDialog;

public class GestionAfficherCharge {
    
    private final AfficherCharges frame;
    private boolean firstLoad = true;
    
    public GestionAfficherCharge(AfficherCharges frame) {
        this.frame = frame;
    }
    
    /**
     * Charge la liste des bâtiments dans la ComboBox de la vue, avec affichage d'un curseur d'attente.
     */
    public void chargerComboBoxBatiment() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoBatiment().findAll().stream()
                                              .map(e -> e.getIdBat())
                                              .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> noms = get();
                    frame.setListBatiment(noms);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue.");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        frame.afficherMessageErreur(cause.getMessage());
                    } else {
                        frame.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
                    }
                } finally {
                    frame.setCursor(Cursor.getDefaultCursor());
                    chargerComboBoxLogement();
                }
            }
        }.execute();
    }
    
    /**
     * Charge la liste des logements pour le bâtiment sélectionné dans la ComboBox.
     */
    public void chargerComboBoxLogement() {
        String idBat = frame.getSelectBatiment();
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoBienLocatif().findByIdBat(idBat).stream()
                                                   .map(e -> e.getIdentifiantLogement())
                                                   .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> noms = get();
                    frame.setListLogement(noms);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue.");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        frame.afficherMessageErreur(cause.getMessage());
                    } else {
                        frame.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
                    }
                } finally {
                    frame.setCursor(Cursor.getDefaultCursor());
                    chargerTable();
                }
            }
        }.execute();
    }

    /**
     * Gère l'action de changement de sélection sur la ComboBox des bâtiments.
     * Utilise une lambda pour simplifier l'écouteur d'événements.
     */
    public void gestionActionComboBat(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                chargerComboBoxLogement();
                chargerTable();
            }
        });
    }
    
    /**
     * Gère l'action de changement de sélection sur la ComboBox des logements.
     */
    public void gestionActionComboLog(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                chargerTable();
            }
        });
    }
    
    /**
     * Ajoute un DocumentListener au champ de recherche pour filtrer la table en temps réel.
     */
    public void gestionEcouteChampRecherche(TableRowSorter<DefaultTableModel> sorter, JTextField txtRecherche) {
        txtRecherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                frame.filtrerTable(sorter);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                frame.filtrerTable(sorter);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                frame.filtrerTable(sorter);
            }
        });
    }
    
    /**
     * Charge les données des documents comptables dans la table de la vue.
     */
    public void chargerTable() {
        try {
            DaoDocumentComptable dao = new DaoDocumentComptable();
            String selLog = frame.getSelectLogement();
            List<DocumentComptable> docs = dao.findByIdLogement(selLog);
            
            List<Object[]> rows = docs.stream()
                .map(doc -> {
                    try {
                        return new Object[] {
                            doc.getNumeroDoc(),
                            doc.getTypeDoc(),
                            dao.findMontantProrata(doc, selLog),
                            doc.getDateDoc()
                        };
                    } catch (SQLException | IOException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
            
            frame.chargerTable(rows);
            if (firstLoad) {
                frame.chargementFini();
                firstLoad = false;
            }
        } catch (SQLException | IOException e) {
            frame.afficherMessageErreur("Erreur : " + e.getMessage());
        }
    }
    
    /**
     * Configure un ActionListener pour filtrer la table en fonction du type de charge sélectionné.
     */
    public void gestionEcouteFiltreType(TableRowSorter<DefaultTableModel> sorter, JComboBox<String> comboTypeCharge, JTextField txtRecherche) {
        comboTypeCharge.addActionListener(e -> appliquerFiltres(sorter, comboTypeCharge, txtRecherche));
    }

    /**
     * Applique les filtres de recherche et de type de charge à la table.
     */
    private void appliquerFiltres(TableRowSorter<DefaultTableModel> sorter, JComboBox<String> comboTypeCharge, JTextField txtRecherche) {
        List<RowFilter<Object,Object>> filters = new ArrayList<>();

        String searchText = txtRecherche.getText();
        if (!searchText.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + searchText));
        }

        String selectedType = (String) comboTypeCharge.getSelectedItem();
        if (selectedType != null && !selectedType.equals("Tous")) {
            switch (selectedType) {
                case "Charges":
                    filters.add(RowFilter.regexFilter("^FACTURE.*", 1));
                    break;
                case "Devis":
                    filters.add(RowFilter.regexFilter("^DEVIS.*", 1));
                    break;
                case "Quittance":
                    filters.add(RowFilter.regexFilter("^QUITTANCE.*", 1));
                    break;
                default:
                    break;
            }
        }

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }

    // -------------------------------------------------------------------------
    //  NOUVELLES FONCTIONNALITÉS
    // -------------------------------------------------------------------------

    /**
     * Ouvre la boîte de dialogue pour ajouter une nouvelle charge.
     */
    public void ajouterNouvelleCharge() {
        AjouterCharge dialog = new AjouterCharge();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    /**
     * Affiche les détails d'une charge sélectionnée via son numéro de document et sa date.
     */
    public void afficherChargeDetail(String numeroDoc, String dateDoc) {
        try {
            DocumentComptable doc = new DaoDocumentComptable().findById(numeroDoc, dateDoc);
            if (doc != null) {
                new DetailChargeDialog(frame, doc, frame.getSelectLogement());
            } else {
                frame.afficherMessageErreur("Document introuvable : " + numeroDoc);
            }
        } catch (SQLException | IOException e) {
            frame.afficherMessageErreur("Erreur lors du chargement : " + e.getMessage());
        }
    }
    
    /**
     * Initialise un double-clic sur une ligne de la table pour afficher les détails de la charge.
     */
    public void initDoubleClickListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int rowIndex = table.getSelectedRow();
                    int modelIndex = table.convertRowIndexToModel(rowIndex);
                    Object numeroDoc = table.getModel().getValueAt(modelIndex, 0);
                    Object dateDoc = table.getModel().getValueAt(modelIndex, 3);
                    afficherChargeDetail((String) numeroDoc, (String) dateDoc);
                }
            }
        });
    }

    /**
     * Recharge la table des charges.
     */
    public void recharger() {
        chargerTable();
    }

    /**
     * Configure le bouton de suppression de charge avec un ActionListener utilisant une lambda.
     */
    public void gestionBoutonSupprimer(JButton btnSupprCharge, JTable table) {
        btnSupprCharge.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow == -1) {
                frame.afficherMessageErreur("Aucune ligne sélectionnée pour suppression.");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(
                frame,
                "Êtes-vous sûr de vouloir supprimer cette charge ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
            );
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            
            int modelRow = table.convertRowIndexToModel(viewRow);
            String numDoc = (String) table.getModel().getValueAt(modelRow, 0);
            String dateDoc = (String) table.getModel().getValueAt(modelRow, 3);
            try {
                DaoDocumentComptable dc = new DaoDocumentComptable();
                DocumentComptable doc = dc.findById(numDoc, dateDoc);
                if(doc != null) {
                    dc.delete(doc);
                    recharger();
                } else {
                    frame.afficherMessageErreur("Document introuvable pour la suppression.");
                }
            } catch (SQLException | IOException ex) {
                frame.afficherMessageErreur("Erreur lors de la suppression : " + ex.getMessage());
            }
        });
    }
}
