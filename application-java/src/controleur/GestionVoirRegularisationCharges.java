package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.SwingWorker;

import java.math.RoundingMode;

import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import rapport.RapportRegularisation;
import vue.FenetrePrincipale;
import vue.VoirRegularisationCharges;
import vue.RevalorisationCharge;


/**
 * Contrôleur pour la gestion de l'affichage du solde de tous les comptes d'un locataire.
 * 
 * Cette classe utilise des SwingWorker pour charger les données en arrière-plan afin de ne pas bloquer
 * l'interface utilisateur lors des opérations de lecture en base de données.
 */
public class GestionVoirRegularisationCharges {
	
	private BigDecimal total;


    private static final String ERREUR_INATTENDUE = "Erreur inattendue : ";
	private static final String OP_INTERROMPUE = "Opération interrompue.";
	/** Le locataire dont on affiche les détails. */
    private Locataire loc;
    /** La vue associée pour l'affichage des soldes et des informations du locataire. */
    private VoirRegularisationCharges fen;
    
    private RapportRegularisation rap;
    
    	
    

    /**
     * Constructeur du contrôleur.
     * 
     * @param regulariserCharges la vue associée à ce contrôleur
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
                    fen.afficherMessageErreur(OP_INTERROMPUE + " ligne 81");
                } catch (ExecutionException e) {
                	
                	e.printStackTrace();
                    // Gérer les exceptions éventuelles survenues pendant l'exécution.
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() +" ligne 86");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() +" ligne 88");
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
        
        rap.setNom(loc.getNom());
        rap.setPrenom(loc.getPrenom());
        
        rap.setAdresse(loc.getAdresse().getAdressePostale());
        rap.setComplement(loc.getAdresse().getComplementAdresse());
        rap.setCodePostal(String.valueOf(loc.getAdresse().getCodePostal()));
        rap.setVille(loc.getAdresse().getVille());
    }

    /**
     * Définit les dates de début et de fin pour la période d'affichage de la régularisation.
     * et "Aujourd'hui" pour la date de fin).
     */
    public void setDates() {
        fen.setDateDebut(fen.getDateDebut());
        rap.setDateDebut(fen.getDateDebut());
       
        fen.setDateFin(fen.getDateFin());
        rap.setDateFin(fen.getDateFin());
       
        rap.setDateCourante(LocalDate.now().toString());
    }

    /**
     * Charge les charges associées au bail spécifiés pour la période donnée.
     * Utilise un SwingWorker pour effectuer l'opération en arrière-plan.
     */
    public void loadCharges() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String[]>, Void>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                // Récupération de la liste des charges depuis la base de données.
            	
            	System.out.println(fen.getDateFin().toString());
            	
                return new DaoBail().findAllChargesBail(
                        fen.getIdBail(),
                        fen.getDateDebut(),
                        fen.getDateFin());
            }

            @Override
            protected void done() {
                try {
                	
                    // Récupération et traitement des résultats une fois l'opération terminée.
                    List<String[]> lignes = get();
                    fen.chargerTableCharges(lignes);
                    
                    rap.setCharges(lignes);
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE +" ligne 168");
                } catch (ExecutionException e) {
                	
                	e.printStackTrace();
                	
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage()+" ligne 172");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage()  +" ligne 174");
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
                String[] res = new DaoBail().findAllDeducBail(
                        fen.getIdBail(),
                        fen.getDateDebut(),
                        fen.getDateFin());

                // Préparation des lignes pour l'affichage.
                List<String[]> lignes = new ArrayList<>();
                String[] provisions = {"Provisions pour charge", res[1], res[0]};
                lignes.add(provisions);
                
                return lignes;
            }

            @Override
            protected void done() {
                try {
                	
                    // Récupération des lignes formatées et mise à jour de la table des déductions.
                    List<String[]> lignes = get();
                    fen.chargerTableDeduc(lignes);
                    
                    rap.setCalcProv(lignes.get(0)[1]);
                    rap.setTotalProv(lignes.get(0)[2]);
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                	
                	e.printStackTrace();
                	
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 224"); //PB ICI 
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() +" ligne 226");
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
                return new DaoBail().findTotalChargeDeducBail(
                        fen.getIdBail(),
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
                    
                    rap.setTotalCharge(sousTot[0].toString());
                    rap.setTotalDeduc(sousTot[1].toString());
                    rap.setTotal(sousTot[2].toString());
                    
                    total = sousTot[0];
                    
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur(OP_INTERROMPUE);
                } catch (ExecutionException e) {
                	
                	e.printStackTrace();
                	
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage() + " ligne 274");
                    } else {
                        fen.afficherMessageErreur(ERREUR_INATTENDUE + cause.getMessage() +" ligne 276");
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
           
                    // Générer le fichier
                    String nomFichier = loc.getNom() + "-REGULARISATIONCHARGES-" + LocalDate.now().toString();
             
                    String idBail = fen.getIdBail();
                    // La suggestion de charge @Erine
                    BigDecimal sugCharge = total.divide(new BigDecimal("12"), 1, RoundingMode.HALF_UP);
                   
                    
                    // AU LIEU DE GENERE : ouvrir la fenetre
                   ouvrirRevalorisationCharges(idBail, sugCharge, rap, nomFichier);
                   

            }
        });
    }
    
    public void ouvrirRevalorisationCharges(String idBail, BigDecimal newVal, RapportRegularisation rap, String nomFichier) {
    	FenetrePrincipale fp = fen.getFenPrincipale();
    	JLayeredPane fenLayerPane = fp.getLayeredPane();
    	rap.setNomFichier(nomFichier);
    	RevalorisationCharge reval = new RevalorisationCharge(idBail, newVal, rap);
		fenLayerPane.add(reval, JLayeredPane.PALETTE_LAYER);
		reval.setVisible(true);

        
        fen.dispose();
    }

}
