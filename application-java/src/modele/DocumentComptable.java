package modele;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;

import modele.dao.DaoAssurance;
import modele.dao.DaoBatiment;
import modele.dao.DaoChargeFixe;
import modele.dao.DaoChargeIndex;
import modele.dao.DaoEntreprise;
import modele.dao.DaoLocataire;

public class DocumentComptable {

    private String numeroDoc;
    private String dateDoc;
    private TypeDoc typeDoc;

    private BigDecimal montant;
    private BigDecimal montantDevis;
    private boolean recuperableLoc = false;
    private String fichierDoc;

    // --- Relations directes ---
    // Au lieu de charger directement les objets associés, on stocke leurs identifiants.
    private String idLocataire;
    private String idBatiment;
    private String idEntreprise;
    private String numeroContrat;    // pour Assurance
    private String anneeContrat;     // pour Assurance

    // Pour ChargeFixe et ChargeIndex, vous utilisez (numDoc, dateDoc) comme identifiant
    // déjà présents dans cette classe.

    // --- Objets associés (lazy) ---
    private Locataire locataire;
    private Batiment batiment;
    private Entreprise entreprise;
    private Assurance assurance;
    private ChargeFixe chargeFixe;
    private ChargeIndex chargeIndex;

    // --- Indicateurs de chargement (lazy loading) ---
    private boolean locLoaded = false;
    private boolean batLoaded = false;
    private boolean entLoaded = false;
    private boolean asrLoaded = false;
    private boolean cfLoaded  = false;
    private boolean ciLoaded  = false;

    /**
     * Constructeur minimal.
     */
    public DocumentComptable(String numDoc, String dateDoc, TypeDoc type,
                             BigDecimal montant, String fichierDoc) {
        this.numeroDoc  = numDoc;
        this.dateDoc    = dateDoc;
        this.typeDoc    = type;
        this.montant    = montant;
        this.fichierDoc = fichierDoc;
    }

    /**
     * Remet à zéro les indicateurs de chargement et les objets associés
     * afin de forcer un nouveau lazy-load lors des prochains appels getXxx().
     */
    public void rechargerDonnees() {
        this.locataire = null;
        this.batiment  = null;
        this.entreprise = null;
        this.assurance  = null;
        this.chargeFixe = null;
        this.chargeIndex = null;

        this.locLoaded = false;
        this.batLoaded = false;
        this.entLoaded = false;
        this.asrLoaded = false;
        this.cfLoaded  = false;
        this.ciLoaded  = false;
    }

    // --- Getters et Setters de base ---

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public String getDateDoc() {
        return dateDoc;
    }

    public TypeDoc getTypeDoc() {
        return typeDoc;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        // Vérification d’un montant négatif
        if (montant.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant ne peut être négatif !");
        }
        this.montant = montant;
    }

    public BigDecimal getMontantDevis() {
        return montantDevis;
    }

    public void setMontantDevis(BigDecimal montantDevis) {
        if (montantDevis.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant du devis ne peut être négatif !");
        }
        this.montantDevis = montantDevis;
    }

    public boolean isRecuperableLoc() {
        return recuperableLoc;
    }

    public void setRecuperableLoc(boolean recuperableLoc) {
        this.recuperableLoc = recuperableLoc;
    }

    public String getFichierDoc() {
        return fichierDoc;
    }

    public void setFichierDoc(String fic) {
        this.fichierDoc = fic;
    }

    // --- Getters/Setters des identifiants pour lazy loading ---

    public String getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(String idLocataire) {
        this.idLocataire = idLocataire;
    }

    public String getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(String idBatiment) {
        this.idBatiment = idBatiment;
    }

    public String getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(String idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public String getAnneeContrat() {
        return anneeContrat;
    }

    public void setAnneeContrat(String anneeContrat) {
        this.anneeContrat = anneeContrat;
    }


    // --- Lazy Loading : getters qui chargent les objets associés si nécessaire ---

    public Locataire getLocataire() throws SQLException, IOException {
        if (!locLoaded && idLocataire != null && !idLocataire.isEmpty()) {
            this.locataire = new DaoLocataire().findById(idLocataire);
            locLoaded = true;
        }
        return locataire;
    }

    public Batiment getBatiment() throws SQLException, IOException {
        if (!batLoaded && idBatiment != null && !idBatiment.isEmpty()) {
            this.batiment = new DaoBatiment().findById(idBatiment);
            batLoaded = true;
        }
        return batiment;
    }

    public Entreprise getEntreprise() throws SQLException, IOException {
        if (!entLoaded && idEntreprise != null && !idEntreprise.isEmpty()) {
            this.entreprise = new DaoEntreprise().findById(idEntreprise);
            entLoaded = true;
        }
        return entreprise;
    }

    public Assurance getAssurance() throws SQLException, IOException {
        if (!asrLoaded && numeroContrat != null && !numeroContrat.isEmpty()) {
            this.assurance = new DaoAssurance().findById(numeroContrat, anneeContrat);
            asrLoaded = true;
        }
        return assurance;
    }

    public ChargeFixe getChargeFixe() throws SQLException, IOException {
        if (!cfLoaded) {
            // Pour ChargeFixe, la clé est (numeroDoc, dateDoc) d’après votre DAO
            this.chargeFixe = new DaoChargeFixe().findByIdDocumentComptable(numeroDoc, dateDoc);
            cfLoaded = true;
        }
        return chargeFixe;
    }

    public ChargeIndex getChargeIndex() throws SQLException, IOException {
        if (!ciLoaded) {
            this.chargeIndex = new DaoChargeIndex().findByIdDocumentComptable(numeroDoc, dateDoc);
            ciLoaded = true;
        }
        return chargeIndex;
    }

    // --- Setters des objets (si vous souhaitez affecter directement) ---

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
        this.locLoaded = true;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
        this.batLoaded = true;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
        this.entLoaded = true;
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
        this.asrLoaded = true;
    }

    public void setChargeFixe(ChargeFixe chargeFixe) {
        this.chargeFixe = chargeFixe;
        this.cfLoaded = true;
    }

    public void setChargeIndex(ChargeIndex chargeIndex) {
        this.chargeIndex = chargeIndex;
        this.ciLoaded = true;
    }

    // --- equals / hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentComptable)) return false;
        DocumentComptable that = (DocumentComptable) o;
        return numeroDoc.equals(that.numeroDoc) && dateDoc.equals(that.dateDoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroDoc, dateDoc);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DocumentComptable {")
          .append("numeroDoc='").append(numeroDoc).append('\'')
          .append(", dateDoc='").append(dateDoc).append('\'')
          .append(", typeDoc=").append(typeDoc)
          .append(", montant=").append(montant)
          .append(", montantDevis=").append(montantDevis)
          .append(", recuperableLoc=").append(recuperableLoc)
          .append(", fichierDoc='").append(fichierDoc).append('\'');

        return sb.toString();
    }

}
