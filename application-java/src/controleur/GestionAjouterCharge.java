package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

/**
 * Gère les actions et chargements de données pour l'écran d'ajout de charges (AjouterCharge).
 */
public class GestionAjouterCharge {
    
    private static final String CHARGEMENTTEXT = "Chargement...";
	/**
     * Vue associée au contrôleur.
     */
    private final AjouterCharge view;

    /**
     * Constructeur.
     *
     * @param view Fenêtre (vue) à contrôler.
     */
    public GestionAjouterCharge(AjouterCharge view) {
        this.view = view;
    }
    
    /**
     * Chargement initial des données dans les comboBox (appelé depuis le constructeur de la vue).
     */
    public void chargerComboBox() {
        try {
            loadTypeCombo();
            loadCompanyCombo();
            loadBuildingCombo();
            loadTenantCombo();
            loadAssuranceCombo();
            loadChargeIndexCombo();
        } catch (Exception e) {
            view.afficherMessageErreur(e.getMessage());
        }
    }
    
    // ---------------------------------------------
    //  CHARGEMENT ASYNCHRONE DES COMBOS
    // ---------------------------------------------

    /**
     * Charge la liste des types de documents dans la comboBox de la vue.
     */
    private void loadTypeCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboBoxTypes(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> typeWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Ici, si besoin, on chargerait en base la liste des types
                return null;
            }
            
            @Override
            protected void done() {
            	
            	List<String> nomEnum = Stream.of(TypeDoc.values())
            	        .map(Enum::name)
            	        .filter(name -> !"loyer".equalsIgnoreCase(name) 
            	        		&& !"quittance".equalsIgnoreCase(name)) // Ignorer "loyer" ou "quittance"
            	        .collect(Collectors.toList());
            	
            	nomEnum.remove("LOYER");
            	
                view.setComboBoxTypes(nomEnum);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        typeWorker.execute();
    }
    
    /**
     * Charge la liste des entreprises dans la comboBox de la vue.
     */
    private void loadCompanyCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboBoxEntreprise(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> companyWorker = new SwingWorker<Void, Void>() {
            private List<String> companyNames;

            @Override
            protected Void doInBackground() throws Exception {
                companyNames = new DaoEntreprise().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getSiret())
                    .toList();
                return null;
            }
            
            @Override
            protected void done() {
                view.setComboBoxEntreprise(companyNames);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        companyWorker.execute();
    }
    
    /**
     * Charge la liste des identifiants de bâtiments dans la comboBox de la vue.
     */
    private void loadBuildingCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboBoxBat(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> buildingWorker = new SwingWorker<Void, Void>() {
            private List<String> buildingIds;
            
            @Override
            protected Void doInBackground() throws Exception {
                buildingIds = new DaoBatiment().findAll().stream()
                    .map(Batiment::getIdBat)
                    .toList();
                return null;
            }
            
            @Override
            protected void done() {
                view.setComboBoxBat(buildingIds);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        buildingWorker.execute();
    }
    
    /**
     * Charge la liste des locataires dans la comboBox de la vue.
     */
    private void loadTenantCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboBoxLocataire(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> tenantWorker = new SwingWorker<Void, Void>() {
            private List<String> tenantNames;
            
            @Override
            protected Void doInBackground() throws Exception {
                tenantNames = new DaoLocataire().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getIdLocataire())
                    .toList();
                return null;
            }
            
            @Override
            protected void done() {
                view.setComboBoxLocataire(tenantNames);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        tenantWorker.execute();
    }
    
    /**
     * Charge la liste des contrats d'assurance dans la comboBox de la vue.
     */
    private void loadAssuranceCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboBoxAssu(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> assuranceWorker = new SwingWorker<Void, Void>() {
            private List<String> assuranceContracts;
            
            @Override
            protected Void doInBackground() throws Exception {
                assuranceContracts = new DaoAssurance().findAll().stream()
                    .map(a -> a.getNumeroContrat() + " " + a.getAnneeContrat())
                    .collect(Collectors.toList());
                assuranceContracts.add(0, "Aucune");
                return null;
            }
            
            @Override
            protected void done() {
                view.setComboBoxAssu(assuranceContracts);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        assuranceWorker.execute();
    }
    
    /**
     * Charge les ID de charge (distinct) pour les charges à index dans la comboBox de la vue.
     */
    private void loadChargeIndexCombo() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        view.setComboIDCharge(Arrays.asList(CHARGEMENTTEXT));
        
        SwingWorker<Void, Void> chargeIndexWorker = new SwingWorker<Void, Void>() {
            private List<String> distinctChargeIndexIds;
            
            @Override
            protected Void doInBackground() throws SQLException, IOException {
                distinctChargeIndexIds = new DaoChargeIndex().findAllDistinctId();
                distinctChargeIndexIds.add(0, "");
                return null;
            }
            
            @Override
            protected void done() {
                view.setComboIDCharge(distinctChargeIndexIds);
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        chargeIndexWorker.execute();
    }
    
    // ---------------------------------------------
    //  GESTION DES ACTIONS UTILISATEUR
    // ---------------------------------------------

    /**
     * Gère les changements du comboBox de type de document (typeDoc).
     * Masque ou affiche certaines zones de la vue selon le type.
     */
    public void gestionComboType() {
        String selectedType = view.getTypeDoc();
        
        // On remet tout "visible" par défaut
        view.setEntrepriseVisible(true);
        view.setAssuranceVisible(true);
        view.setLocataireVisible(true);
        view.setCoutAbonnementVisible(true);
        view.setIndexVisible(true);
        view.setIdChargeTotalVisible(true);
        refreshIndex(null);
        
        // On masque / affiche les champs selon le type
        switch (selectedType) {
            case "QUITTANCE":
                view.setEntrepriseVisible(false);
                view.setAssuranceVisible(false);
                view.setLocataireVisible(true);
                view.setCoutAbonnementVisible(false);
                view.setIndexVisible(false);
                view.setIdChargeTotalVisible(false);
                view.setTextLabelCout("Montant quittance :");
                break;
                
            case "FACTURE","FACTURE_CF":
                view.setLocataireVisible(false);
                view.setCoutAbonnementVisible(false);
                view.setIndexVisible(false);
                view.setIdChargeNomVisible(true);
                view.setTextLabelCout("Montant :");
                break;

            case "FACTURE_CV":
                view.setLocataireVisible(false);
                view.setCoutAbonnementVisible(true);
                view.setTextLabelCout("Cout unitaire | abonnement :");
                refreshIndex(view.getIdChargeCombo());
                break;
                
            case "DEVIS":
                view.setLocataireVisible(false);
                view.setAssuranceVisible(false);
                view.setCoutAbonnementVisible(false);
                view.setIndexVisible(false);
                view.setIdChargeNomVisible(false);
                view.setTextLabelCout("Montant devis :");
                break;
                
            default:
                break;
        }
    }
    
    /**
     * Gère le changement de sélection dans la comboBox de choix de l'ID de charge.
     * Peut servir à récupérer l'ancien index.
     */
    public void gestionComboIDCharge() {
        // Récupération de la valeur, puis traitement si nécessaire
        view.getTextIDCharge();
    }
    
    /**
     * Gère l'événement quand on clique sur le bouton "Annuler".
     * Ferme simplement la fenêtre d'ajout de charge.
     */
    public void gestionBoutonAnnuler() {
        view.dispose();
    }
    
    /**
     * Ajoute un écouteur pour surveiller le changement d'état de la comboBox d'ID de charge.
     *
     * @param combo ComboBox concerné
     */
    public void gestionComboID(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
                if (evt.getStateChange() == ItemEvent.SELECTED && combo.isVisible()) {
                    refreshIndex(String.valueOf(combo.getSelectedItem()));
                }
        });
    }
    
    /**
     * Met à jour les valeurs d'index et les coûts (unitaire, abonnement)
     * dès qu'un ID de charge est sélectionné.
     *
     * @param chargeIndexId ID de la charge à mettre à jour
     */
    private void refreshIndex(String chargeIndexId) {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        SwingWorker<Void, Void> refreshIndexWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                ChargeIndex chargeIndex;
                if (chargeIndexId == null || chargeIndexId.isEmpty()) {
                    chargeIndex = null;
                } else {
                    chargeIndex = new DaoChargeIndex()
                            .findAllSameId(chargeIndexId)
                            .stream()
                            .findFirst()
                            .orElse(null);
                }

                view.setValSpinnerAbonn(chargeIndex == null ? BigDecimal.valueOf(0.0) : chargeIndex.getCoutFixe());
                view.setValSpinnerUnit(chargeIndex == null ? BigDecimal.valueOf(0.0) : chargeIndex.getCoutVariable());
                view.setTextAncienIndex(chargeIndex == null ? "" : String.valueOf(chargeIndex.getValeurCompteur()));
                view.setNomTypeCharge(chargeIndex == null ? "" : String.valueOf(chargeIndex.getType()));
                view.clearTextIdCharge();
                return null;
            }
            
            @Override
            protected void done() {
                view.setCursor(Cursor.getDefaultCursor());
            }
        };
        refreshIndexWorker.execute();
    }
    
    /**
     * Gère l'événement quand on clique sur le bouton "Parcourir".
     * Ouvre un JFileChooser pour sélectionner un fichier et met à jour la vue.
     *
     * @param browseButton Bouton "Parcourir"
     * @param fileChooser Composant JFileChooser
     */
    public void gestionBoutonParcourir(JButton browseButton, JFileChooser fileChooser) {
        browseButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(view);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                view.setLienFichier(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }
    /**
     * Gère le clic sur le bouton "OK".
     * Vérifie les champs obligatoires, effectue les contrôles métiers, enregistre les données en base,
     * puis ferme la fenêtre si tout est correct.
     */
    public void gestionBoutonOk() {
        ConnexionBD bd = null;
        try {
            validateMandatoryFields();
            bd = ConnexionBD.getInstance();
            bd.setAutoCommit(false);

            final String typeDoc = view.getTypeDoc();
            switch (typeDoc) {
                case "FACTURE", "FACTURE_CF": processFacture(); break;
                case "FACTURE_CV": processFactureCV(); break;
                case "DEVIS": processDevis(); break;
                default:
                    view.afficherMessageErreur("Type de document non valide.");
                    return;
            }

            bd.valider();
            view.dispose();
        } catch (Exception e) {
            if (bd != null) { bd.anuler(); }
            e.printStackTrace();
            view.afficherMessageErreur(e.getMessage());
        } finally {
            if (bd != null) { bd.setAutoCommit(false); }
        }
    }


    /**
     * Traite le cas où le type de document est "FACTURE" ou "FACTURE_CF".
     * @throws IOException 
     * @throws SQLException en cas d'erreur de bd
     */
    private void processFacture() throws SQLException, IOException {
        DaoDocumentComptable daoDoc = new DaoDocumentComptable();
        DaoFactureBien daoFacture = new DaoFactureBien();
        DaoBienLocatif daoBien = new DaoBienLocatif();

        String idEntreprise = getSiretEntreprise(view.getIDEntreprise());
        String idBat = view.getIDBat();
        String idAssurance = view.getIDAssu();
        

        DocumentComptable doc = new DocumentComptable(
                view.getTextNumDoc(), view.getTextDateDoc(), 
                TypeDoc.valueOf(view.getTypeDoc()),
                view.getCoutVarUnit(), view.getLienFichier()
        );
        doc.setRecuperableLoc(view.estRecupLoc());
        doc.setBatiment(new DaoBatiment().findById(idBat));
        

        doc.setEntreprise(new DaoEntreprise().findById(idEntreprise));
        
        if (!idAssurance.equalsIgnoreCase("aucune")) {
            String[] parts = idAssurance.split(" ");
            doc.setAssurance(new DaoAssurance().findById(parts[0], parts[1]));
        }
        

        
        daoDoc.create(doc);

        processParts(daoFacture, daoBien, doc);
        new DaoChargeFixe().create(new ChargeFixe(
                view.getTextIDCharge(), view.getTextDateDoc(), view.getNomTypeCharge(),
                view.getCoutVarUnit(), view.getTextNumDoc(), view.getTextDateDoc()
        ));
    }

    /**
     * Traite le cas où le type de document est "FACTURE_CV".
     * @throws IOException 
     * @throws SQLException en cas d'erreur de bd
     */
    private void processFactureCV() throws SQLException, IOException {
        BigDecimal nouvelIndex = view.getValIndex();
        BigDecimal ancienIndex = view.getAncienIndex();
        if (nouvelIndex.compareTo(ancienIndex) < 0) {
            throw new IllegalArgumentException("L'ancien index ne peut être supérieur au nouveau.");
        }

        DaoDocumentComptable daoDoc = new DaoDocumentComptable();
        DaoFactureBien daoFacture = new DaoFactureBien();
        DaoBienLocatif daoBien = new DaoBienLocatif();

        BigDecimal coutVariable = view.getCoutVarUnit()
                .multiply(nouvelIndex.subtract(ancienIndex))
                .add(view.getCoutVarAbon());

        String idEntreprise = getSiretEntreprise(view.getIDEntreprise());
        System.out.println(idEntreprise);
        
        String idBat = view.getIDBat();
        String idAssurance = view.getIDAssu();

        DocumentComptable doc = new DocumentComptable(
                view.getTextNumDoc(), view.getTextDateDoc(), 
                TypeDoc.valueOf(view.getTypeDoc()),
                coutVariable, view.getLienFichier()
        );
        doc.setRecuperableLoc(view.estRecupLoc());
        doc.setBatiment(new DaoBatiment().findById(idBat));
        
        doc.setEntreprise(new DaoEntreprise().findById(idEntreprise));
        
        if (!idAssurance.equalsIgnoreCase("aucune")) {
            String[] parts = idAssurance.split(" ");
            doc.setAssurance(new DaoAssurance().findById(parts[0], parts[1]));
        }
        
        
        //daoDoc.create(doc);

        processParts(daoFacture, daoBien, doc);

        String idChargeVar = view.getTextIDCharge().isEmpty() ? view.getIdChargeCombo() : view.getTextIDCharge();
        ChargeIndex chargeIndex = new ChargeIndex(
                idChargeVar, view.getTextDateDoc(), view.getNomTypeCharge(),
                nouvelIndex, view.getCoutVarUnit(), view.getCoutVarAbon(),
                view.getTextNumDoc(), view.getTextDateDoc()
        );
        if (!view.getIdChargeCombo().isEmpty()) {
            chargeIndex.setDateRelevePrecedent(
                    new DaoChargeIndex().findAllSameId(idChargeVar).get(0).getDateDeReleve()
            );
        }
        
        new DaoChargeIndex().create(chargeIndex);
    }

    /**
     * Traite le cas où le type de document est "DEVIS".
     * @throws IOException
     * @throws SQLException en cas d'erreur de bd
     */
    private void processDevis() throws SQLException, IOException {
        DaoDocumentComptable daoDoc = new DaoDocumentComptable();
        DaoFactureBien daoFacture = new DaoFactureBien();
        DaoBienLocatif daoBien = new DaoBienLocatif();

        String idEntreprise = view.getIDEntreprise().split(" ")[1];
        String idBat = view.getIDBat();
        
        DocumentComptable doc = new DocumentComptable(
                view.getTextNumDoc(), view.getTextDateDoc(), 
                TypeDoc.valueOf(view.getTypeDoc()),
                view.getCoutVarUnit(), view.getLienFichier()
        );
        doc.setRecuperableLoc(view.estRecupLoc());
        doc.setBatiment(new DaoBatiment().findById(idBat));
        doc.setEntreprise(new DaoEntreprise().findById(idEntreprise));
        daoDoc.create(doc);

        processParts(daoFacture, daoBien, doc);
    }

    /**
     * Traite les parties communes à plusieurs types de documents.
     * Crée les factures associées à chaque logement, vérifie que la somme des parts est égale à 1 avec une tolérance.
     *
     * @param daoFacture DAO pour gérer les factures
     * @param daoBien DAO pour récupérer un bien locatif
     * @param doc Document comptable déjà créé
     * @throws SQLException en cas d'erreur base de données
     * @throws IOException 
     */
    private void processParts(DaoFactureBien daoFacture, DaoBienLocatif daoBien, DocumentComptable doc) throws SQLException, IOException {
        BigDecimal sumParts = BigDecimal.ZERO;
        final BigDecimal tolerance = new BigDecimal("0.01");
        
        List<List<Object>> logements = view.getListeLogement();

        for (List<Object> bienPart : logements) {
            String bienId = String.valueOf(bienPart.get(0));
            BigDecimal part = (BigDecimal) bienPart.get(1);
            sumParts = sumParts.add(part);
            daoFacture.create(new FactureBien(daoBien.findById(bienId), doc, part.floatValue()));
        }
        BigDecimal difference = sumParts.subtract(BigDecimal.ONE).abs();
        if (difference.compareTo(tolerance) > 0) {
            throw new IllegalArgumentException(
                "La somme des parts de charges doit être égale à 1. Valeur : " + sumParts
            );
        }
    }

    // ---------------------------------------------
    //  MÉTHODES PRIVÉES
    // ---------------------------------------------

    /**
     * Valide que les champs obligatoires de la vue soient renseignés.
     * Lance une IllegalArgumentException en cas de problème.
     */
    private void validateMandatoryFields() {
        if (view.getTextNumDoc() == null || view.getTextNumDoc().isEmpty()) {
            throw new IllegalArgumentException("Le numéro du document est obligatoire.");
        }
        if (view.getTextDateDoc() == null || view.getTextDateDoc().isEmpty()) {
            throw new IllegalArgumentException("La date du document est obligatoire.");
        }
        if (view.getTypeDoc() == null || view.getTypeDoc().isEmpty()) {
            throw new IllegalArgumentException("Le type du document est obligatoire.");
        }
        if (view.getIDBat() == null || view.getIDBat().isEmpty()) {
            throw new IllegalArgumentException("Le bâtiment est obligatoire.");
        }
        // Les autres champs comme l'entreprise ou l'assurance peuvent être optionnels
        // suivant le type de document choisi, donc on ne les force pas ici.
    }
    
    
    /**
     * Extrait le dernier mot d'une chaîne de caractères (qui devrait correspondre au Siret) et le renvoie.
     * 
     * @param input La chaîne de caractères contenant les informations de l'entreprise.
     * @return Le dernier mot de la chaîne (siret), ou une chaîne vide si l'entrée est nulle ou vide.
     */
    private static String getSiretEntreprise(String input) {
        // Vérifier si l'entrée est nulle ou vide
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        
        // Découper la chaîne par les espaces
        String[] parts = input.split("\\s+"); // Utilisation de "\\s+" pour gérer plusieurs espaces

        return parts[parts.length - 1];
    }
    

}
