package controleur;

import java.awt.Cursor;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.SwingWorker;

import modele.Adresse;
import modele.Bail;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import modele.dao.DaoLoyer;
import rapport.RapportRegularisation;
import vue.FenetrePrincipale;
import vue.RevalorisationCharge;
import vue.VoirRegularisationCharges;

/**
 * Contrôleur pour la gestion de l'affichage du solde de tous les comptes d'un locataire.
 * 
 * Cette classe utilise des SwingWorker pour charger les données en arrière-plan afin de ne pas bloquer
 * l'interface utilisateur lors des opérations de lecture en base de données.
 */
public class GestionVoirRegularisationCharges {
    
    private static final String ERREUR_INATTENDUE = "Erreur inattendue : ";
    private static final String OP_INTERROMPUE = "Opération interrompue.";

    /** Stocke le total des charges pour calculer une suggestion de provision mensuelle. */
    private BigDecimal total;

    /** Locataire dont on affiche les détails. */
    private Locataire loc;
    
    /** Rapport de régularisation (utilisé pour la génération de documents). */
    private RapportRegularisation rap;
    
    /** Vue associée pour l'affichage des soldes et des informations du locataire. */
    private VoirRegularisationCharges fen;


    /**
     * Constructeur du contrôleur.
     * 
     * @param voirRegulariserCharges la vue associée à ce contrôleur
     */
    public GestionVoirRegularisationCharges(VoirRegularisationCharges voirRegulariserCharges) {
        this.fen = voirRegulariserCharges;
        this.rap = new RapportRegularisation();
    }


    /**
     * Charge les informations d'un locataire à partir de la base de données en utilisant un SwingWorker.
     * Le curseur est mis en sablier pendant le chargement.
     */
    public void loadLocataire() {
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
                    loc = get();  // Récupère le locataire chargé en background
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE + " ligne 81");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 86");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() + " ligne 88");
                    }
                } finally {
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
        
        rap.setNom(loc.getNom());   // Le locataire n'a pas d'adresse, mais on garde son nom
        rap.setPrenom(loc.getPrenom());

        if (loc.getAdresse() != null) {
            // Cas : Le locataire a une adresse renseignée
            fen.setAdresse(loc.getAdresse().toString());
            

            rap.setAdresse(loc.getAdresse().getAdressePostale());
            rap.setComplement(loc.getAdresse().getComplementAdresse());
            rap.setCodePostal(String.valueOf(loc.getAdresse().getCodePostal()));
            rap.setVille(loc.getAdresse().getVille());
        } else {
            // Cas : Le locataire n'a pas d'adresse -> Récupérer celle du bail
            try {
                Bail bai = new DaoBail().findById(fen.getIdBail());
                if (bai != null && bai.getBien() != null && bai.getBien().getBat() != null) {
                    fen.setAdresse(bai.getBien().getBat().getAdresse().toString());
                    
                    Adresse adresse = bai.getBien().getBat().getAdresse();

                    rap.setAdresse(adresse.getAdressePostale());
                    rap.setComplement(adresse.getComplementAdresse());
                    rap.setCodePostal(String.valueOf(adresse.getCodePostal()));
                    rap.setVille(adresse.getVille());
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                fen.afficherMessageErreur("Impossible de charger l'adresse du bail : " + e.getMessage());
            }
        }
    }

    /**
     * Définit les dates de début et de fin pour la période d'affichage de la régularisation 
     * et les stocke dans le rapport.
     */
    public void setDates() {
        fen.setDateDebut(fen.getDateDebut());
        rap.setDateDebut(fen.getDateDebut());
       
        fen.setDateFin(fen.getDateFin());
        rap.setDateFin(fen.getDateFin());
       
        rap.setDateCourante(LocalDate.now().toString());
    }

    /**
     * Charge les charges associées au bail spécifié pour la période donnée.
     * Utilise un SwingWorker pour effectuer l'opération en arrière-plan.
     */
    public void loadCharges() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String[]>, Void>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                // Récupération de la liste des charges depuis la base de données
                return new DaoBail().findAllChargesBail(
                    fen.getIdBail(),
                    fen.getDateDebut(),
                    fen.getDateFin()
                );
            }

            @Override
            protected void done() {
                try {
                    List<String[]> lignes = get();
                    fen.chargerTableCharges(lignes);
                    rap.setCharges(lignes);  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE + " ligne 168");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 172");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() + " ligne 174");
                    }
                } finally {
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
                String[] res = new DaoBail().findAllDeducBail(
                    fen.getIdBail(),
                    fen.getDateDebut(),
                    fen.getDateFin()
                );

                // Préparation de la ligne pour l'affichage
                List<String[]> lignes = new ArrayList<>();
                // res[0] = total provisions
                // res[1] = détail calcul
                String[] provisions = {"Provisions pour charge", res[1], res[0]};
                lignes.add(provisions);
                
                return lignes;
            }

            @Override
            protected void done() {
                try {
                    List<String[]> lignes = get();
                    fen.chargerTableDeduc(lignes);
                    
                    // Mémoriser le détail de calcul et le total
                    rap.setCalcProv(lignes.get(0)[1]);
                    rap.setTotalProv(lignes.get(0)[2]);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 224");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() + " ligne 226");
                    }
                } finally {
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
                return new DaoBail().findTotalChargeDeducBail(
                    fen.getIdBail(),
                    fen.getDateDebut(),
                    fen.getDateFin()
                );
            }

            @Override
            protected void done() {
                try {
                    BigDecimal[] sousTot = get();
                    fen.setSousTotCharge(sousTot[0].toString());
                    fen.setSousTotDeduc(sousTot[1].toString());
                    fen.setTotal(sousTot[2].toString());
                    
                    rap.setTotalCharge(sousTot[0].toString());
                    rap.setTotalDeduc(sousTot[1].toString());
                    rap.setTotal(sousTot[2].toString());
                    
                    total = sousTot[0];  // total charges pour suggestion de provision

                    // Charger le loyer (pour l'affichage dans le rapport) une fois qu'on a tout
                    loadLoyerIntoRapport();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 274");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() + " ligne 276");
                    }
                } finally {
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }

    /**
     * Ajoute un Listener sur le bouton "Générer" pour ouvrir la page de revalorisation des charges
     * en lui passant les données nécessaires au rapport (nouvelle provision conseillée).
     */
    public void gestionBtnGenerer(JButton btnGenerer) {
        btnGenerer.addActionListener(e -> {
            String nomFichier = loc.getNom() + "-REGULARISATIONCHARGES-" + LocalDate.now().toString();
            String idBail = fen.getIdBail();

            // Suggérer la nouvelle provision mensuelle = totalCharges / 12
            BigDecimal sugCharge = total.divide(new BigDecimal("12"), 1, RoundingMode.HALF_UP);
            
            ouvrirRevalorisationCharges(idBail, sugCharge, rap, nomFichier);
        });
    }

    /**
     * Charge le loyer du bien dans le rapport.
     * On va chercher le bail, puis le dernier loyer connu via DaoLoyer.
     */
    public void loadLoyerIntoRapport() {
        try {
            Bail bai = new DaoBail().findById(fen.getIdBail());
            BigDecimal loyer = new DaoLoyer().getDernierLoyer(bai.getBien().getIdentifiantLogement());
            rap.setLoyer(loyer.toString());
        } catch (SQLException | IOException e) {
            fen.afficherMessageErreur(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ouvre la page RevalorisationCharge, en lui passant un bail, 
     * la valeur préconisée pour les provisions pour charges, et le rapport. 
     * @param idBail     Identifiant du bail à pré-sélectionner
     * @param newVal     Montant préconisé pour la provision mensuelle
     * @param rap        RapportRegularisation qui contient les infos à afficher
     * @param nomFichier Nom du fichier de rapport à générer
     */
    public void ouvrirRevalorisationCharges(String idBail, BigDecimal newVal, RapportRegularisation rap, String nomFichier) {
        FenetrePrincipale fp = fen.getFenPrincipale();
        JLayeredPane fenLayerPane = fp.getLayeredPane();
        rap.setNomFichier(nomFichier);

        RevalorisationCharge reval = new RevalorisationCharge(idBail, newVal, rap);
        fenLayerPane.add(reval, JLayeredPane.PALETTE_LAYER);
        reval.setVisible(true);

        // Fermer la fenêtre actuelle
        fen.dispose();
    }
}
