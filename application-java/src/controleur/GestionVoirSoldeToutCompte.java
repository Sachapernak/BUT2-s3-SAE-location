package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import vue.VoirSoldeToutCompte;

/**
 * Contrôleur pour la gestion de l'affichage du solde de tous les comptes d'un locataire.
 * 
 * Cette classe utilise des SwingWorker pour charger les données en arrière-plan afin de ne pas bloquer
 * l'interface utilisateur lors des opérations de lecture en base de données.
 */
public class GestionVoirSoldeToutCompte {

    private static final String ERREUR_INATTENDUE = "Erreur inattendue : ";
	private static final String OP_INTERROMPUE = "Opération interrompue.";
	/** Le locataire dont on affiche les détails. */
    private Locataire loc;
    /** La vue associée pour l'affichage des soldes et des informations du locataire. */
    private VoirSoldeToutCompte fen;

    /**
     * Constructeur du contrôleur.
     * 
     * @param fen la vue associée à ce contrôleur
     */
    public GestionVoirSoldeToutCompte(VoirSoldeToutCompte fen) {
        this.fen = fen;
    }

    /**
     * Charge les informations d'un locataire à partir de la base de données en utilisant un SwingWorker.
     * Le curseur est mis en sablier pendant le chargement.
     */
    public void loadLocataire() {
        // Change le curseur pour indiquer une opération en cours.
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<Locataire, Void>() {
            @Override
            protected Locataire doInBackground() throws Exception {
                // Recherche le locataire par son identifiant.
                return new DaoLocataire().findById(fen.getIdLoc());
            }

            @Override
            protected void done() {
                try {
                    // Récupère le résultat de l'opération en arrière-plan.
                    loc = get();
                } catch (InterruptedException e) {
                    // Réinterrompre le thread si l'opération a été interrompue.
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    // Gérer les exceptions éventuelles survenues pendant l'exécution.
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage());
                    }
                } finally {
                    // Réinitialise le curseur et met à jour l'affichage avec les informations du locataire.
                    fen.setCursor(Cursor.getDefaultCursor());
                    setInfoLoc();
                }
            }
        }.execute();
    }

    /**
     * Met à jour la vue avec les informations du locataire.
     * Si le locataire n'est pas encore chargé, déclenche son chargement.
     */
    public void setInfoLoc() {
        // Si les informations du locataire ne sont pas disponibles, les charger d'abord.
        if (this.loc == null) {
            loadLocataire();
            return;
        }

        // Met à jour les champs de la vue avec les données du locataire.
        fen.setNomLoc(loc.getNom());
        fen.setPrenom(loc.getPrenom());
        fen.setAdresse(loc.getAdresse().toString());
    }

    /**
     * Définit les dates de début et de fin pour la période d'affichage du solde.
     * Si une date n'est pas spécifiée, on utilise des valeurs par défaut ("Début" pour la date de début
     * et "Aujourd'hui" pour la date de fin).
     */
    public void setDates() {
        if (fen.getDateDebut() == null || fen.getDateDebut().isEmpty()) {
            fen.setDateDebut("Début");
        } else {
            fen.setDateDebut(fen.getDateDebut());
        }

        if (fen.getDateFin() == null || fen.getDateFin().isEmpty()) {
            fen.setDateFin("Aujourd'hui");
        } else {
            fen.setDateFin(fen.getDateFin());
        }
    }

    /**
     * Charge les charges associées au locataire et au bail spécifiés pour la période donnée.
     * Utilise un SwingWorker pour effectuer l'opération en arrière-plan.
     */
    public void loadCharges() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String[]>, Void>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                // Récupération de la liste des charges depuis la base de données.
                return new DaoBail().findAllChargesBaiLoc(
                        fen.getIdBail(),
                        fen.getIdLoc(),
                        fen.getDateDebut(),
                        fen.getDateFin());
            }

            @Override
            protected void done() {
                try {
                    // Récupération et traitement des résultats une fois l'opération terminée.
                    List<String[]> lignes = get();
                    fen.chargerTableCharges(lignes);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage());
                    }
                } finally {
                    // Réinitialisation du curseur après chargement.
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }

    /**
     * Charge les déductions associées au locataire et au bail pour la période spécifiée.
     * Utilise un SwingWorker pour effectuer l'opération en arrière-plan.
     */
    public void loadDeduc() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String[]>, Void>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                // Récupération des données de déduction depuis la base de données.
                String[] res = new DaoBail().findAllDeducBaiLoc(
                        fen.getIdBail(),
                        fen.getIdLoc(),
                        fen.getDateDebut(),
                        fen.getDateFin());

                // Préparation des lignes pour l'affichage.
                List<String[]> lignes = new ArrayList<>();
                String[] provisions = {"Provisions pour charge", res[1], res[0]};
                String[] caution = {"Caution de l'appartement", "", res[2]};
                lignes.add(provisions);
                lignes.add(caution);
                return lignes;
            }

            @Override
            protected void done() {
                try {
                    // Récupération des lignes formatées et mise à jour de la table des déductions.
                    List<String[]> lignes = get();
                    fen.chargerTableDeduc(lignes);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage());
                    }
                } finally {
                    // Réinitialisation du curseur après chargement.
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }
    

    /**
     * Charge les sous-totaux des charges et des déductions pour la période définie.
     * Utilise un SwingWorker pour effectuer l'opération en arrière-plan.
     */
    public void loadSousTotaux() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<BigDecimal[], Void>() {
            @Override
            protected BigDecimal[] doInBackground() throws Exception {
                // Calcul des totaux à partir de la base de données.
                return new DaoBail().findTotalChargeDeduc(
                        fen.getIdBail(),
                        fen.getIdLoc(),
                        fen.getDateDebut(),
                        fen.getDateFin());
            }

            @Override
            protected void done() {
                try {
                    // Mise à jour des champs de la vue avec les sous-totaux calculés.
                    BigDecimal[] sousTot = get();
                    fen.setSousTotCharge(sousTot[0].toString());
                    fen.setSousTotDeduc(sousTot[1].toString());
                    fen.setTotal(sousTot[2].toString());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage());
                    }
                } finally {
                    // Réinitialisation du curseur après le calcul.
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }
    
    
	public void gestionBtnGenerer(JButton btnGenerer) {
		btnGenerer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//TODO
        	}
        });
	}
}
