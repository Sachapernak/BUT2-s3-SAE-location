package controleur;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import modele.DocumentComptable;
import modele.Locataire;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoFactureBien;
import modele.dao.DaoLocataire;
import vue.AfficherLoyers;
import vue.ChargerLoyers;

/**
 * Contrôleur gérant les actions liées à l'affichage des loyers.
 */
public class GestionAfficherLoyers {

    private AfficherLoyers frame;

    /**
     * Constructeur principal
     * 
     * @param frame la vue gérée par ce contrôleur
     */
    public GestionAfficherLoyers(AfficherLoyers frame) {
        this.frame = frame;
        chargerComboBatiment();
    }

    /**
     * Charge la liste des bâtiments dans la comboBox via un SwingWorker.
     */
    private void chargerComboBatiment() {
        frame.setWaitCursor(true);

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoBatiment().findAll()
                        .stream().map(b -> b.getIdBat())
                        .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> result = get();
                    frame.setListBatiment(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue.");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    frame.afficherMessageErreur("Erreur lors du chargement des bâtiments : " + cause.getMessage());
                } finally {
                    frame.setWaitCursor(false);
                    chargerComboBien();
                }
            }
        }.execute();
        
        
    }

    /**
     * Charge la liste des biens (logements) pour le bâtiment sélectionné.
     */
    public void chargerComboBien() {
        String selectedBat = frame.getSelectedBatiment();
        if (selectedBat == null) return;

        frame.setWaitCursor(true);

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoBienLocatif().findByIdBat(selectedBat)
                        .stream().map(b -> b.getIdentifiantLogement())
                        .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> result = get();
                    frame.setListBien(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue (chargement biens).");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    frame.afficherMessageErreur("Erreur chargement biens : " + cause.getMessage());
                } finally {
                    frame.setWaitCursor(false);
                    chargerComboLocataire();
                }
            }
        }.execute();
        
        
    }

    /**
     * Charge la liste des locataires pour le bien (logement) sélectionné.
     */
    public void chargerComboLocataire() {
        String selectedBien = frame.getSelectedBien();
        if (selectedBien == null) return;

        frame.setWaitCursor(true);

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
            	// List des locataires

                return new DaoLocataire().findByIdBien(selectedBien).stream()
                										.map(Locataire::getIdLocataire)
                										.toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> result = get();
                    frame.setListLocataire(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue (chargement locataires).");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    frame.afficherMessageErreur("Erreur chargement locataires : " + cause.getMessage());
                } finally {
                    frame.setWaitCursor(false);
                    chargerTableLoyers();
                }
            }
        }.execute();
        
        
    }

    /**
     * Charge la table des loyers pour le locataire sélectionné.
     * Met également à jour le champ "Nb loyers".
     */
    public void chargerTableLoyers() {
        String selectedLoc = frame.getSelectedLocataire();
        if (selectedLoc == null) return;

        frame.setWaitCursor(true);

        new SwingWorker<List<DocumentComptable>, Void>() {
            @Override
            protected List<DocumentComptable> doInBackground() throws Exception {
                // Récupérer tous les loyers du locataire via la DAO
                // (Méthode demandée : DaoDocumentComptable.findLoyersByIdLocataire(String idLocataire))
                return new DaoDocumentComptable().findLoyersByIdLocataire(selectedLoc);
            }

            @Override
            protected void done() {
                try {
                    // Résultat : liste des loyers
                    List<DocumentComptable> loyers = get();
                    
                    // Transformation de la liste en structure exploitable par la table
                    List<Object[]> data = loyers.stream().map(l -> {
                        
                    	// (ID Bien, Date, Montant)
                    	
                    	String idBien = "Introuvable";
						try {
							idBien = new DaoFactureBien().findByIdDoc(l.getNumeroDoc(), l.getDateDoc())
																.get(0).getBien().getIdentifiantLogement();
						} catch (SQLException | IOException e) {
							frame.afficherMessageErreur(e.getMessage());
						}
                    	
                        return new Object[] {l.getNumeroDoc(), idBien, l.getDateDoc(), l.getMontant().toString() };
                    }).toList();

                    frame.majTableLoyers(data);

                    //TODO : Amelioration priorité faible : calculer le ratio de loyers payers
                    String ratio = loyers.size() + "";
                    frame.setNbLoyers(ratio);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue (chargement loyers).");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    frame.afficherMessageErreur("Erreur chargement loyers : " + cause.getMessage());
                } finally {
                    frame.setWaitCursor(false);
                }
            }
        }.execute();
    }

    /**
     * Action pour le bouton "Ajouter".
     * 
     * Ouvre la page de chargement des loyers
     */
    public void ajouterLoyer() {
		ChargerLoyers chLoyer = new ChargerLoyers();
		chLoyer.setVisible(true);
    }

    /**
     * Action pour le bouton "Supprimer".
     * Supprime le loyer actuellement sélectionné dans la table.
     */
    public void supprimerLoyer() {
        int row = frame.getSelectedRow();
        if (row == -1) {
            frame.afficherMessageErreur("Aucun loyer sélectionné à supprimer.");
            return;
        }
        
        // Verification de la présence des données
        Object idBien = frame.getNumDocAtSelectedRow();
        if (idBien == null) {
            frame.afficherMessageErreur("Ligne de loyer invalide.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(frame,
            "Voulez-vous vraiment supprimer ce loyer ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {

            return;
        }

        // Suppression en base de données du loyer        
        frame.setWaitCursor(true);

        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {   
            	
            	DaoDocumentComptable dao = new DaoDocumentComptable();
            	DocumentComptable doc = dao.findById(String.valueOf(frame.getNumDocAtSelectedRow()), String.valueOf(frame.getDateAtSelectedRow()));
            	
            	dao.delete(doc);
                return true; 
            }

            @Override
            protected void done() {
                try {
                    Boolean success = get();
                    if (Boolean.TRUE.equals(success)) {
                        // Réactualiser l'affichage
                        chargerTableLoyers();
                    } else {
                        frame.afficherMessageErreur("Impossible de supprimer le loyer.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    frame.afficherMessageErreur("Opération interrompue (suppression loyer).");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    frame.afficherMessageErreur("Erreur suppression loyer : " + cause.getMessage());
                } finally {
                    frame.setWaitCursor(false);
                }
            }
        }.execute();
    }
}
