package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.SwingWorker;
import modele.Batiment;
import modele.ChargeIndex;
import modele.dao.DaoAssurance;
import modele.dao.DaoBatiment;
import modele.dao.DaoChargeIndex;
import modele.dao.DaoEntreprise;
import modele.dao.DaoLocataire;
import vue.AjouterCharge;

public class GestionAjouterCharge {
    
    private AjouterCharge fen;
    
    public GestionAjouterCharge(AjouterCharge fen) {
        this.fen = fen;
    }
    
    /**
     * Chargement initial des données dans les comboBox (appelé depuis le constructeur de la vue).
     */
    public void chargerComboBox() {
        try {
            chargerComboType();
            chargerComboEntreprise();
            chargerComboBat();
            chargerComboLoc();
            chargerComboAssurance();
            chargerComboIDCharge();
        } catch (Exception e) {
            fen.afficherMessageErreur(e.getMessage());
        }
    }
    
    // ---------------------------------------------
    //  CHARGEMENT DES COMBOS (asynchrone)
    // ---------------------------------------------
    private void chargerComboType() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxTypes(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerType = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Si besoin : on peut charger en base la liste des types...
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxTypes(Arrays.asList(
                    "FACTURE", 
                    "FACTURE_CV", 
                    "FACTURE_CF", 
                    "DEVIS", 
                    "QUITTANCE",
                    "DEBUG" // TODO : REMOVE
                ));
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerType.execute();
    }
    
    private void chargerComboEntreprise() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxEntreprise(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerEntr = new SwingWorker<Void, Void>() {
            private List<String> nomsEntr;
            @Override
            protected Void doInBackground() throws Exception {
                nomsEntr = new DaoEntreprise().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getSiret())
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxEntreprise(nomsEntr);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerEntr.execute();
    }
    
    private void chargerComboBat() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxBat(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerBat = new SwingWorker<Void, Void>() {
            private List<String> bats;
            @Override
            protected Void doInBackground() throws Exception {
                bats = new DaoBatiment().findAll().stream()
                    .map(Batiment::getIdBat)
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxBat(bats);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerBat.execute();
    }
    
    private void chargerComboLoc() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxLocataire(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerLoc = new SwingWorker<Void, Void>() {
            private List<String> locs;
            @Override
            protected Void doInBackground() throws Exception {
                locs = new DaoLocataire().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getIdLocataire())
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxLocataire(locs);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerLoc.execute();
    }
    
    private void chargerComboAssurance() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxAssu(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerAssu = new SwingWorker<Void, Void>() {
            private List<String> assus;
            @Override
            protected Void doInBackground() throws Exception {
            	
                assus = new DaoAssurance().findAll().stream()
                    .map(a -> a.getNumeroContrat() + " " + a.getAnneeContrat())
                    .collect(Collectors.toList());
                assus.add(0, "Aucune");
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxAssu(assus);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerAssu.execute();
    }
    
    private void chargerComboIDCharge() throws SQLException, IOException {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboIDCharge(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerIDCharge = new SwingWorker<Void, Void>() {
            private List<String> res;
            @Override
            protected Void doInBackground() throws Exception {
                res = new DaoChargeIndex().findAllDistinctId();
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboIDCharge(res);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerIDCharge.execute();
    }
    
    // ---------------------------------------------
    //  GESTION DES ACTIONS UTILISATEUR
    // ---------------------------------------------
    
    /**
     * Action lors du changement de type de document (comboBoxType).
     */
    public void gestionComboType() {
        String selected = fen.getTypeDoc();
        
        // On remet tout "visible" par défaut
        fen.setEntrepriseVisible(true);
        fen.setAssuranceVisible(true);
        fen.setLocataireVisible(true);
        fen.setCoutAbonnementVisible(true);
        fen.setIndexVisible(true);
        fen.setIdChargeTotalVisible(true);
        majIndex(null);
        
        // Puis on masque / grise selon le type
        switch (selected) {
            case "QUITTANCE":
                // On veut : "cacher l'entreprise, l'assurance"
                fen.setEntrepriseVisible(false);
                fen.setAssuranceVisible(false);
                // On veut "afficher la quittance" => donc Locataire visible
                fen.setLocataireVisible(true);
                // On peut masquer l'abonnement si non pertinent, par exemple:
                fen.setCoutAbonnementVisible(false);
                // Idem pour l'index si c'est inutile pour une quittance
                fen.setIndexVisible(false);
                // On peut masquer le choix IDCharge si ce n’est pas pertinent pour quittance
                fen.setIdChargeTotalVisible(false);
                
                fen.setTextLblCout("Montant quittance :");
                break;
                
            case "FACTURE":
            	// meme que CF
            case "FACTURE_CF":
                fen.setLocataireVisible(false);
                fen.setCoutAbonnementVisible(false);
                fen.setIndexVisible(false);
                fen.setIdChargeNomVisible(true);
                fen.setTextLblCout("Montant :");
                
                break;

            case "FACTURE_CV":
            	
            	fen.setLocataireVisible(false);
                fen.setCoutAbonnementVisible(true);
                fen.setLocataireVisible(false);
                fen.setTextLblCout("Cout unitaire | abonnement :");
                majIndex(fen.getIDChargeCombo());
                break;
                
            case "DEVIS":
                fen.setLocataireVisible(false);
                fen.setAssuranceVisible(false);
                fen.setCoutAbonnementVisible(false);
                fen.setIndexVisible(false);
                fen.setIdChargeNomVisible(false);
                fen.setTextLblCout("Montant devis :");
                break;
                
            default:
                break;
        }
    }
    
    /**
     * Action lors du changement de combo ID charge (comboBoxChoixCharge).
     */
    public void gestionComboIDCharge() {
        String selectedID = fen.getTextIDCharge(); 
        // OU, si on veut récupérer la valeur depuis la combo :
        // String selectedID = (String) fen.getComboBoxChoixCharge().getSelectedItem();
        
        // Faire un traitement si nécessaire
        // (Par exemple, charger l'ancien index, etc.)
        // ...
    }
    
    /**
     * Action quand on clique sur OK.
     */
    public void gestionBoutonOk() {
        try {
            // 1) Récupérer toutes les infos de la vue
            String numDoc = fen.getTextNumDoc();
            String dateDoc = fen.getTextDateDoc();
            String typeDoc = fen.getTypeDoc();
            String idEntreprise = fen.getIDEntreprise();
            boolean recupLoc = fen.estRecupLoc();
            BigDecimal coutUnit = fen.getCoutVarUnit();
            BigDecimal coutAbon = fen.getCoutVarAbon();
            BigDecimal valIndex = fen.getValIndex();
            List<List<Object>> listeLogements = fen.getListeLogement();
            
            // 2) Logique métier, ex: vérifications, insertion en DB, etc.
            String selected = fen.getTypeDoc();

            switch (selected) {
                case "QUITTANCE":

                    break;
                    
                case "FACTURE":
                	// meme que CF
                case "FACTURE_CF":


                    break;

                case "FACTURE_CV":

                    break;
                    
                case "DEVIS":

                    break;
                    
                default:
                	fen.afficherMessageErreur("Type non valide");
                    break;
            }
            // 3) Si tout va bien, on peut fermer la fenêtre :
            fen.dispose();
            
        } catch (Exception e) {
            fen.afficherMessageErreur("Erreur dans le bouton OK : " + e.getMessage());
        }
    }
    
    /**
     * Action quand on clique sur le bouton Annuler.
     */
    public void gestionBoutonAnnuler() {
        // Par exemple, on ferme la fenêtre
        fen.dispose();
    }
    
    public void gestionComboID(JComboBox<String> combo) {
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED && combo.isVisible()) {
                    majIndex(String.valueOf(combo.getSelectedItem()));
                }
            }
        });
    }
    
    private void majIndex(String id) {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  
        
        SwingWorker<Void, Void> workerIDCharge = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	ChargeIndex ci;
            	if (id == null) {
            		ci = null;
            	} else {
            		 ci = new DaoChargeIndex().findAllSameId(id).stream().findFirst().orElse(null);
            	}
            	fen.setSpinnerCoutVarAbon(ci == null ? BigDecimal.valueOf(0.0f) : ci.getCoutFixe());
            	fen.setSpinnerCoutVarUnit(ci == null ? BigDecimal.valueOf(0.0f) : ci.getCoutVariable());
            	fen.setTextAncienIndex(ci == null ? "" : String.valueOf(ci.getValeurCompteur()));
            	
            	return null;
            }
            
            @Override
            protected void done() {
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerIDCharge.execute();
        
    }
}
