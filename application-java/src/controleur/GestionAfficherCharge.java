package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import modele.DocumentComptable;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import vue.AfficherCharges;


public class GestionAfficherCharge {
	
	private AfficherCharges frame;
	
	public GestionAfficherCharge(AfficherCharges frame) {
		this.frame = frame;
	}
	
	public void chargerComboBoxBatiment() {
	    // Afficher un curseur d'attente
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    
	    new SwingWorker<List<String>, Void>() {
	        @Override
	        protected List<String> doInBackground() throws Exception {
	            
	        	return new DaoBatiment().findAll().stream()
									              .map(e -> e.getIdBat())
									              .collect(Collectors.toList());
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
	                // Réinitialiser le curseur
	                frame.setCursor(Cursor.getDefaultCursor());
	                chargerComboBoxLogement();
	            }
	        }
	    }.execute();
	    
	    
	}
	
	public void chargerComboBoxLogement() {
		String idBat = frame.getSelectBatiment();
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    
	    new SwingWorker<List<String>, Void>() {
	        @Override
	        protected List<String> doInBackground() throws Exception {
	        	return new DaoBienLocatif().findByIdBat(idBat).stream()
										        			.map(e -> e.getIdentifiantLogement())
										        			.collect(Collectors.toList());
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
	                // Réinitialiser le curseur
	                frame.setCursor(Cursor.getDefaultCursor());
	                chargerTable();
	            }
	        }
	    }.execute();
	}

	public void gestionActionComboBat(JComboBox<String> combo) {
	    combo.addItemListener(new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent evt) {
	            if (evt.getStateChange() == ItemEvent.SELECTED) {
	                chargerComboBoxLogement();
	                chargerTable();
	            }
	        }
	    });
	}
	
	public void gestionActionComboLog(JComboBox<String> combo) {
	    combo.addItemListener(new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent evt) {
	            if (evt.getStateChange() == ItemEvent.SELECTED) {
	                chargerTable();
	            }
	        }
	    });
	}
	
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
	
	public void chargerTable() {
	    try {
	        // Récupération des documents comptables
	    	
	    	DaoDocumentComptable dao = new DaoDocumentComptable();
	    	String selLog = frame.getSelectLogement();
	    	
	        List<DocumentComptable> docs = dao.findByIdLogement(selLog);
	        
	        // Transformation des documents en lignes pour la table
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
	            .collect(Collectors.toList());
	        
	        // Chargement des données dans la table
	        frame.chargerTable(rows);
	        
	    } catch (SQLException | IOException e) {
	        frame.afficherMessageErreur("Erreur : " + e.getMessage());
	    }
	}
	
    public void gestionEcouteFiltreType(TableRowSorter<DefaultTableModel> sorter, JComboBox<String> comboTypeCharge, JTextField txtRecherche) {
        comboTypeCharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appliquerFiltres(sorter, comboTypeCharge, txtRecherche);
            }
        });
    }

    // Méthode pour appliquer les deux filtres (recherche et type)
    private void appliquerFiltres(TableRowSorter<DefaultTableModel> sorter, JComboBox<String> comboTypeCharge, JTextField txtRecherche) {
        List<RowFilter<Object,Object>> filters = new ArrayList<>();

        // Filtre de recherche
        String searchText = txtRecherche.getText();
        if (searchText.trim().length() > 0) {
            filters.add(RowFilter.regexFilter("(?i)" + searchText));
        }

        // Filtre de type
        String selectedType = (String) comboTypeCharge.getSelectedItem();
        if (selectedType != null && !selectedType.equals("Tous")) {
            switch (selectedType) {
                case "Charges":
                    // Filtrer les types commençant par "facture"
                    filters.add(RowFilter.regexFilter("^facture.*", 1)); // Supposant que "Type" est la colonne d'indice 1
                    break;
                case "Devis":
                    // Filtrer les types commençant par "devis"
                    filters.add(RowFilter.regexFilter("^devis.*", 1));
                    break;
                case "Quittance":
                    // Filtrer les types exactement égaux à "quittance"
                    filters.add(RowFilter.regexFilter("^quittance$", 1));
                    break;
                default:
                    break;
            }
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }

}
