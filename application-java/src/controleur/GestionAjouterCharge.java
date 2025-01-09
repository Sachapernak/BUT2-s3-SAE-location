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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import modele.Batiment;
import modele.ChargeFixe;
import modele.ChargeIndex;
import modele.ConnexionBD;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.TypeDoc;
import modele.dao.DaoAssurance;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoChargeFixe;
import modele.dao.DaoChargeIndex;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoEntreprise;
import modele.dao.DaoFactureBien;
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
                res.add(0,"");
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
        fen.getTextIDCharge();
        
        // Faire un traitement si nécessaire
        // (Par exemple, charger l'ancien index, etc.)
        // ...
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
            	if (id == null || id.isEmpty()) {
            		ci = null;
            	} else {
            		 ci = new DaoChargeIndex().findAllSameId(id).stream().findFirst().orElse(null);
            	}
            	fen.setSpinnerCoutVarAbon(ci == null ? BigDecimal.valueOf(0.0f) : ci.getCoutFixe());
            	fen.setSpinnerCoutVarUnit(ci == null ? BigDecimal.valueOf(0.0f) : ci.getCoutVariable());
            	fen.setTextAncienIndex(ci == null ? "" : String.valueOf(ci.getValeurCompteur()));
            	fen.setNomTypeCharge(ci == null ? "" : String.valueOf(ci.getType()));
            	fen.clearTextIdCharge();
            	return null;
            }
            
            @Override
            protected void done() {
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerIDCharge.execute();
        
    }
    
    public void gestionBoutonParcourir(JButton buttonParcourir, JFileChooser fileChooser) {
        buttonParcourir.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(fen);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Met à jour le champ texte du fichier sélectionné via la vue
                fen.setLienFichier(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    
    /**
     * Action quand on clique sur OK.
     */
    public void gestionBoutonOk() {
    	
    	ConnexionBD bd = null;
        
        try {
        	
            bd = ConnexionBD.getInstance();
            bd.setAutoCommit(false);
            
            // 1) Récupérer toutes les infos de la vue
            String numDoc = fen.getTextNumDoc();
            String dateDoc = fen.getTextDateDoc();
            String typeDoc = fen.getTypeDoc();
            String fichierDoc = fen.getLienFichier();
            boolean recupLoc = fen.estRecupLoc();
            
            String idEntreprise = fen.getIDEntreprise();
            String idBat = fen.getIDBat();
            String idLoc = fen.getIDLocataire();
            String idAssu = fen.getIDAssu();
            
            String idCharge = fen.getTextIDCharge();
            String idChargeCombo = fen.getIDChargeCombo();
            
            String typeCharge = fen.getNomTypeCharge();
            
            BigDecimal coutUnit = fen.getCoutVarUnit();
            BigDecimal coutAbon = fen.getCoutVarAbon();
            BigDecimal nouveaIndex = fen.getValIndex();
            BigDecimal ancienIndex = fen.getAncienIndex();
            List<List<Object>> listeLogements = fen.getListeLogement();
            
            // 2) Logique métier, ex: vérifications, insertion en DB, etc.
            String selected = fen.getTypeDoc();

            DocumentComptable nouveauDoc;
            DaoDocumentComptable daoDC = new DaoDocumentComptable();
            DaoFactureBien daoFB = new DaoFactureBien();
            
            DaoBienLocatif daoB = new DaoBienLocatif();
           
            String bien;
            
            BigDecimal tolerance = new BigDecimal("0.01");
            
            BigDecimal part;
            
            BigDecimal difference;
            
            BigDecimal somme;

            switch (selected) {
                case "QUITTANCE":
                	                	
                	nouveauDoc = new DocumentComptable(numDoc, dateDoc, TypeDoc.valueOf(typeDoc),
                			coutUnit, fichierDoc);
                	
                	nouveauDoc.setRecuperableLoc(recupLoc);
                	
                	nouveauDoc.setBatiment(new DaoBatiment().findById(idBat));
                	
                	nouveauDoc.setLocataire(new DaoLocataire().findById(idLoc));
                	
                	daoDC.create(nouveauDoc);
                
                
            		bien = String.valueOf(listeLogements.get(0).get(0));
            		part = (BigDecimal)listeLogements.get(0).get(1);
            		daoFB.create(new FactureBien(daoB.findById(bien), nouveauDoc, part.floatValue()));

                	
                    break;
                    
                case "FACTURE":

                case "FACTURE_CF":
                	
                	nouveauDoc = new DocumentComptable(numDoc, dateDoc, TypeDoc.valueOf(typeDoc),
                			coutUnit, fichierDoc);
                	
                	nouveauDoc.setRecuperableLoc(recupLoc);
                	
                	nouveauDoc.setBatiment(new DaoBatiment().findById(idBat));

                	nouveauDoc.setEntreprise(new DaoEntreprise().findById(idEntreprise.split(" ")[1]));
                	
                	if (!idAssu.equalsIgnoreCase("aucune")) {
                		nouveauDoc.setAssurance(new DaoAssurance().findById(idAssu.split(" ")[0], idAssu.split(" ")[1]));
                	}
                	
                	daoDC.create(nouveauDoc);
                	somme = new BigDecimal(0);
                	for (List<Object> bienPart : listeLogements) {
                		
                		bien = String.valueOf(bienPart.get(0));
                		part = (BigDecimal) bienPart.get(1);
                		
                		somme = somme.add(part);
                		daoFB.create(new FactureBien(daoB.findById(bien), nouveauDoc, part.floatValue()));
                	}

                	
                	difference = somme.subtract(BigDecimal.ONE).abs();

                	if (difference.compareTo(tolerance) > 0) {
                	    throw new IllegalArgumentException("La somme des parts de charges doit être égale à 1. Valeur : " + somme);
                	}
                	
                	ChargeFixe cf = new ChargeFixe(idCharge, dateDoc,typeCharge, coutUnit, numDoc, dateDoc);
                	new DaoChargeFixe().create(cf);
            		

                    break;

                case "FACTURE_CV":
                	
                	if(nouveaIndex.compareTo(ancienIndex) < 0) {
                		throw new IllegalArgumentException("L'ancien index ne peut etre inférieur au nouevau");
                	}
                	
                	BigDecimal coutVar = coutUnit.multiply(nouveaIndex.subtract(ancienIndex)).add(coutAbon);
                	
                	
                	
                	nouveauDoc = new DocumentComptable(numDoc, dateDoc, TypeDoc.valueOf(typeDoc),
                			coutVar, fichierDoc);
                	
                	nouveauDoc.setRecuperableLoc(recupLoc);
                	
                	nouveauDoc.setBatiment(new DaoBatiment().findById(idBat));

                	nouveauDoc.setEntreprise(new DaoEntreprise().findById(idEntreprise.split(" ")[1]));
                	
                	if (!idAssu.equalsIgnoreCase("aucune")) {
                		nouveauDoc.setAssurance(new DaoAssurance().findById(idAssu.split(" ")[0], idAssu.split(" ")[1]));
                	}
                	
                	daoDC.create(nouveauDoc);
                	somme = new BigDecimal(0);
                	for (List<Object> bienPart : listeLogements) {
                		
                		bien = String.valueOf(bienPart.get(0));
                		part = (BigDecimal) bienPart.get(1);
                		
                		somme = somme.add(part);
                		daoFB.create(new FactureBien(daoB.findById(bien), nouveauDoc, part.floatValue()));
                	}

                	
                	difference = somme.subtract(BigDecimal.ONE).abs();

                	if (difference.compareTo(tolerance) > 0) {
                	    throw new IllegalArgumentException("La somme des parts de charges doit être égale à 1. Valeur : " + somme);
                	}
                	
                	String idcv = idCharge.isEmpty() ? idChargeCombo : idCharge;

                	
                	ChargeIndex cv = new ChargeIndex(idcv, dateDoc, typeCharge, nouveaIndex, 
                			coutUnit, coutAbon, numDoc, dateDoc);
                	
                	if (!idChargeCombo.isEmpty()) {
                		cv.setDateRelevePrecedent(new DaoChargeIndex().findAllSameId(idcv).get(0).getDateDeReleve());
                	}
                	new DaoChargeIndex().create(cv);

                    break;
                    
                case "DEVIS":

                	nouveauDoc = new DocumentComptable(numDoc, dateDoc, TypeDoc.valueOf(typeDoc),
                			coutUnit, fichierDoc);
                	
                	nouveauDoc.setRecuperableLoc(recupLoc);
                	
                	nouveauDoc.setBatiment(new DaoBatiment().findById(idBat));

                	nouveauDoc.setEntreprise(new DaoEntreprise().findById(idEntreprise.split(" ")[1]));
                	
                	daoDC.create(nouveauDoc);
                	somme = new BigDecimal(0);
                	for (List<Object> bienPart : listeLogements) {
                		
                		bien = String.valueOf(bienPart.get(0));
                		part = (BigDecimal) bienPart.get(1);
                		
                		somme = somme.add(part);
                		daoFB.create(new FactureBien(daoB.findById(bien), nouveauDoc, part.floatValue()));
                	}

                	
                	difference = somme.subtract(BigDecimal.ONE).abs();

                	if (difference.compareTo(tolerance) > 0) {
                	    throw new IllegalArgumentException("La somme des parts de charges doit être égale à 1. Valeur : " + somme);
                	}

                    break;
                    
                default:
                	fen.afficherMessageErreur("Type non valide");
                    break;
            }
            bd.valider();
            // 3) Si tout va bien, on peut fermer la fenêtre :
            fen.dispose();
            
        } catch (Exception e) {
        	if (bd != null) {
        		bd.anuler();
        	}
        	e.printStackTrace();
            fen.afficherMessageErreur(e.getMessage());
            
        } finally {
        	if (bd != null) {
        		bd.setAutoCommit(false);
        	}
        	
        }
    }


}
