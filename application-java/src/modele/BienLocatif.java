package modele;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import modele.dao.DaoICC;
import modele.dao.DaoLoyer;

public class BienLocatif {
	
	// TODO : finir cette classe

    String identifiantLogement;
    String idFiscal;
    String complementAdresse;
    TypeDeBien type;
    int surface;
    int nbPiece;
    float partChargeBat;
    float loyerBase;
    Batiment bat;

    // Listes des entités associées
    private List<ICC> iccList = new ArrayList<>();
    private List<Loyer> loyers = new ArrayList<>();
    private List<ComprendreChargeFixe> comprendreChargesFixes = new ArrayList<>();
    private List<ComprendreChargeVariable> comprendreChargesVariables = new ArrayList<>();
    private List<DocumentComptable> docsComptables = new ArrayList<>();
    private List<Diagnostiques> diagnostiques = new ArrayList<>();

    public BienLocatif(String identifiantLogement, TypeDeBien type, int surface, int nbPiece, float partChargeBat,
            float loyerBase, Batiment bat) {

        this.identifiantLogement = identifiantLogement;
        this.type = type;
        this.surface = surface;
        this.nbPiece = nbPiece;
        this.partChargeBat = partChargeBat;
        this.loyerBase = loyerBase;
        this.bat = bat;
    }

    // Getters & Setters
    public String getIdentifiantLogement() {
        return identifiantLogement;
    }

    public String getIdFiscal() {
        return idFiscal;
    }

    public void setIdFiscal(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    public String getComplementAdresse() {
        return complementAdresse;
    }

    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    public TypeDeBien getType() {
        return type;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNbPiece() {
        return nbPiece;
    }

    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
    }

    public float getPartChargeBat() {
        return partChargeBat;
    }

    public void setPartChargeBat(float partChargeBat) {
        this.partChargeBat = partChargeBat;
    }

    public float getLoyerBase() {
        return loyerBase;
    }

    public void setLoyerBase(float loyerBase) {
        this.loyerBase = loyerBase;
    }

    public Batiment getBat() {
        return bat;
    }

    // généricité
    private <T> List<T> loadData(DataLoader<T> loader) {
        try {
            return loader.load();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Permet de throw les exceptions dans la truc générique
    @FunctionalInterface
    interface DataLoader<T> {
        List<T> load() throws SQLException, IOException;
    }

    // Getters avec Lazy-loading pour les listes : 
    
    public List<ICC> getIccList() {
        if (iccList.isEmpty()) {
            iccList = loadData(() -> new DaoICC().findByIdLogement(this.identifiantLogement));
        }
        return iccList;
    }

    public List<Loyer> getLoyers() {
        if (loyers.isEmpty()) {
            loyers = loadData(() -> new DaoLoyer().findByIdLogement(this.identifiantLogement));
        }
        return loyers;
    }


    public List<ComprendreChargeFixe> getChargesFixes() {
        return comprendreChargesFixes;
    }

    public List<ComprendreChargeVariable> getChargesVariables() {
        return comprendreChargesVariables;
    }

    public List<DocumentComptable> getDocsComptables() {
        return docsComptables;
    }

    public List<Diagnostiques> getDiagnostiques() {
        return diagnostiques;
    }

    // Méthodes de chargement depuis la BD (à implémenter via DAO)
    // Exemples :
    /*
    public void loadLoyersFromDB() {
        // DaoLoyer dao = new DaoLoyer();
        // this.loyers = dao.findByLogementId(this.identifiantLogement);
    }

    public void loadChargesFixesFromDB() {
        // DaoChargeFixe dao = new DaoChargeFixe();
        // this.chargesFixes = dao.findByLogementId(this.identifiantLogement);
    }

    // etc.
    */
}
