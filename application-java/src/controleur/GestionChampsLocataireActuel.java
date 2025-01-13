package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Bail;
import modele.Contracter;
import modele.Locataire;
import modele.Loyer;
import modele.Regularisation;
import modele.dao.DaoContracter;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;

/**
 * Contrôleur gérant l'affichage des champs relatifs au locataire sélectionné
 * dans la vue {@link AfficherLocatairesActuels}. <br/>
 * Implémente {@link ListSelectionListener} pour réagir aux changements
 * de sélection dans la table des locataires.
 */
public class GestionChampsLocataireActuel implements ListSelectionListener {

    /** La vue associée permettant d'afficher les locataires. */
    private final AfficherLocatairesActuels fenAfficherLocataires;
    /** DAO pour l'accès aux données de locataires. */
    private final DaoLocataire daoLocataire;

    /**
     * Constructeur principal.
     *
     * @param afl la vue affichant les locataires actuels
     */
    public GestionChampsLocataireActuel(AfficherLocatairesActuels afl) {
        this.fenAfficherLocataires = afl;
        this.daoLocataire = new DaoLocataire();
    }

    /**
     * Méthode appelée lorsqu'une nouvelle ligne est sélectionnée
     * dans la table des locataires actuels.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return; // Ignore les événements intermédiaires
        }

        JTable tableLoc = fenAfficherLocataires.getTableLocatairesActuels();
        JTable tableBiens = fenAfficherLocataires.getTableBiensLoues();

        int index = tableLoc.getSelectedRow();
        if (index == -1) {
            // Aucune ligne sélectionnée, on vide les champs
            viderChamps();
            tableBiens.clearSelection();
            UtilitaireTable.viderTable(tableBiens);
            return;
        }

        // On vide les champs avant de les remplir
        viderChamps();
        chargerLocataireAsync(tableLoc, index);
    }

    /**
     * Charge le locataire en tâche de fond, puis met à jour l'UI et la table des biens.
     *
     * @param tableLoc la table contenant la liste des locataires
     * @param selectedRow l'indice de la ligne sélectionnée
     */
    private void chargerLocataireAsync(JTable tableLoc, int selectedRow) {
        fenAfficherLocataires.setCursorAttente(true);

        new SwingWorker<Locataire, Void>() {
            @Override
            protected Locataire doInBackground() throws Exception {
                return lireLigneTable(tableLoc, selectedRow);
            }

            @Override
            protected void done() {
                try {
                    Locataire loc = get();
                    if (loc != null) {
                        mettreAJourChamps(loc);
                        remplirTableLocationAsync(fenAfficherLocataires.getTableBiensLoues(), loc);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    fenAfficherLocataires.afficherMessageErreur("Chargement interrompu.");
                } catch (ExecutionException ex) {
                    fenAfficherLocataires.afficherMessageErreur("Erreur : " + ex.getCause().getMessage());
                } finally {
                    fenAfficherLocataires.setCursorAttente(false);
                }
            }
        }.execute();
    }

    /**
     * Met à jour les champs de la vue avec les informations du locataire sélectionné.
     *
     * @param locSelect le locataire à afficher
     */
    private void mettreAJourChamps(Locataire locSelect) {
        // Récupération des champs
        JTextField dateNaissance = fenAfficherLocataires.getTextFieldDateDeNaissance();
        JTextField adresse = fenAfficherLocataires.getTextFieldAdressePerso();
        JTextField tel = fenAfficherLocataires.getTextFieldTel();
        JTextField mail = fenAfficherLocataires.getTextFieldMail();

        // Affectation des valeurs
        dateNaissance.setText(locSelect.getDateNaissance());
        Adresse adr = locSelect.getAdresse();
        if (adr != null) {
            adresse.setText(adr.getAdressePostale());
        }
        tel.setText(locSelect.getTelephone());
        mail.setText(locSelect.getEmail());
    }

    /**
     * Remplit la table des biens loués (contrats) du locataire, en tâche de fond.
     *
     * @param tableLocations la table des biens loués
     * @param locSelect le locataire dont on veut charger les contrats
     */
    private void remplirTableLocationAsync(JTable tableLocations, Locataire locSelect) {
        fenAfficherLocataires.setCursorAttente(true);

        new SwingWorker<List<Contracter>, Void>() {
            @Override
            protected List<Contracter> doInBackground() throws Exception {
                // Hypothèse : getContrats() peut déclencher une requête en BD.
                return locSelect.getContrats();
            }

            @Override
            protected void done() {
                try {
                    List<Contracter> contrats = get();
                    UtilitaireTable.viderTable(tableLocations);
                    DefaultTableModel model = (DefaultTableModel) tableLocations.getModel();

                    for (Contracter contrat : contrats) {
                        String dateEntree = contrat.getDateEntree();
                        String partLoyer = String.valueOf(contrat.getPartLoyer());
                        Bail bail = contrat.getBail();
                        String idBail = bail.getIdBail();

                        // Dernière régularisation
                        String dateDerniereRegularisation = extraireDerniereRegularisation(bail);
                        // Loyer actuel
                        String loyerActuel = extraireLoyerActuel(bail);

                        String batiment = bail.getBien().getBat().getIdBat();
                        String complementAdr = bail.getBien().getComplementAdresse();
                        String type = bail.getBien().getType().getValeur();

                        model.addRow(new Object[]{
                            idBail,
                            dateEntree,
                            type,
                            batiment,
                            complementAdr,
                            loyerActuel,
                            partLoyer,
                            dateDerniereRegularisation
                        });
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    fenAfficherLocataires.afficherMessageErreur("Chargement interrompu.");
                } catch (ExecutionException ex) {
                    fenAfficherLocataires.afficherMessageErreur("Erreur : " + ex.getCause().getMessage());
                } catch (SQLException | IOException e) {
                	 fenAfficherLocataires.afficherMessageErreur("Erreur : " + e.getCause().getMessage());
					e.printStackTrace();
				}  finally {
                    fenAfficherLocataires.setCursorAttente(false);
                }
            }
        }.execute();
    }

    /**
     * Extrait la date de la dernière régularisation d'un bail.
     *
     * @param bail le bail
     * @return la date de la dernière régularisation, ou chaîne vide si aucune
     */
    private String extraireDerniereRegularisation(Bail bail) throws SQLException, IOException {
        List<Regularisation> regularisations = bail.getRegularisation();
        if (regularisations.isEmpty()) {
            return "";
        }
        return regularisations.get(regularisations.size() - 1).getDateRegu();
    }

    /**
     * Extrait le loyer actuel d'un bail.
     *
     * @param bail le bail
     * @return le montant du loyer en String, ou chaîne vide si aucun
     */
    private String extraireLoyerActuel(Bail bail) throws SQLException, IOException {
        List<Loyer> loyers = bail.getBien().getLoyers();
        if (loyers.isEmpty()) {
            return "";
        }
        return String.valueOf(loyers.get(loyers.size() - 1).getMontantLoyer());
    }

    /**
     * Vide les champs de texte relatifs au locataire dans la vue.
     */
    private void viderChamps() {
        fenAfficherLocataires.getTextFieldDateDeNaissance().setText("");
        fenAfficherLocataires.getTextFieldAdressePerso().setText("");
        fenAfficherLocataires.getTextFieldTel().setText("");
        fenAfficherLocataires.getTextFieldMail().setText("");
        fenAfficherLocataires.getTextFieldProvisionPourCharge().setText("");
        fenAfficherLocataires.getTextFieldCaution().setText("");
    }

    /**
     * Remplit la table des locataires actuels via un appel DAO en SwingWorker.
     * Seuls ceux qui n'ont pas de date de sortie sont affichés.
     */
    public void remplirTableLocatairesActuels() {
        JTable tableLocataires = fenAfficherLocataires.getTableLocatairesActuels();
        UtilitaireTable.viderTable(tableLocataires);

        fenAfficherLocataires.setCursorAttente(true);

        new SwingWorker<List<Locataire>, Void>() {
            @Override
            protected List<Locataire> doInBackground() throws Exception {
                return daoLocataire.findAll();
            }

            @Override
            protected void done() {
                try {
                    List<Locataire> locataires = get();
                    DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
                    model.setRowCount(0);

                    DaoContracter daoContracter = new DaoContracter();

                    for (Locataire loc : locataires) {
                        // On affiche uniquement les locataires dont un contrat n'a pas de date de sortie
                        boolean estActif = daoContracter.getContrats(loc).stream()
                                .anyMatch(e -> e.getDateSortie() == null);

                        if (estActif) {
                            model.addRow(new String[]{
                                loc.getIdLocataire(),
                                loc.getNom(),
                                loc.getPrenom()
                            });
                        }
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    fenAfficherLocataires.afficherMessageErreur("Chargement interrompu.");
                } catch (ExecutionException ex) {
                    fenAfficherLocataires.afficherMessageErreur("Erreur : " + ex.getCause().getMessage());
                } catch (SQLException | IOException e) {
                	fenAfficherLocataires.afficherMessageErreur("Erreur : " + e.getCause().getMessage());
					e.printStackTrace();
				}  finally {
                    fenAfficherLocataires.setCursorAttente(false);
                }
            }
        }.execute();
    }

    /**
     * Lit la ligne sélectionnée dans la table et renvoie le locataire correspondant.
     *
     * @param tableLocatairesActuels la table où figure le locataire
     * @param selectedRow l'indice de la ligne sélectionnée
     * @return le {@link Locataire} correspondant, ou null si erreur
     */
    private Locataire lireLigneTable(JTable tableLocatairesActuels, int selectedRow) {
        String idLoc = (String) tableLocatairesActuels.getValueAt(selectedRow, 0);
        try {
            return daoLocataire.findById(idLoc);
        } catch (SQLException | IOException ex) {
            fenAfficherLocataires.afficherMessageErreur(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
