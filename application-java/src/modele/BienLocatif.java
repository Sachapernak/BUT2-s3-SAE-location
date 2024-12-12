package modele;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import modele.dao.DaoFactureBien;
import modele.dao.DaoDiagnostique;
import modele.dao.DaoLoyer;

/**
 * Représente un bien locatif avec ses caractéristiques et ses entités associées telles que les loyers,
 * les documents comptables et les diagnostics.
 * <p>
 * Cette classe gère également le chargement paresseux (lazy-loading) des entités associées via des DAO.
 * </p>
 * 
 */
public class BienLocatif {

    /**
     * Identifiant unique du logement.
     */
    private final String identifiantLogement;

    /**
     * Type de bien locatif.
     */
    private final TypeDeBien type;

    /**
     * Bâtiment auquel le bien locatif appartient.
     */
    private final Batiment bat;

    /**
     * Identifiant fiscal du bien locatif.
     */
    private String idFiscal;

    /**
     * Complément d'adresse du bien locatif.
     */
    private String complementAdresse;

    /**
     * Surface du bien locatif en mètres carrés.
     */
    private int surface;

    /**
     * Nombre de pièces du bien locatif.
     */
    private int nbPiece;

    /**
     * Loyer de base du bien locatif.
     */
    private BigDecimal loyerBase;

    // Listes des entités associées avec indicateurs de chargement

    /**
     * Liste des loyers associés au bien locatif.
     */
    private List<Loyer> loyers = new ArrayList<>();

    /**
     * Indicateur de chargement des loyers.
     */
    private boolean loyersLoaded = false;

    /**
     * Liste des documents comptables associés au bien locatif.
     */
    private List<FactureBien> docsComptables = new ArrayList<>();

    /**
     * Indicateur de chargement des documents comptables.
     */
    private boolean docsLoaded = false;

    /**
     * Liste des diagnostics associés au bien locatif.
     */
    private List<Diagnostiques> diagnostiques = new ArrayList<>();

    /**
     * Indicateur de chargement des diagnostics.
     */
    private boolean diagLoaded = false;

    /**
     * Constructeur principal de la classe {@code BienLocatif}.
     * <p>
     * Initialise un nouveau bien locatif avec les paramètres fournis après avoir validé les entrées.
     * </p>
     * 
     * @param identifiantLogement identifiant unique du logement
     * @param type                type de bien locatif
     * @param surface             surface du bien locatif en mètres carrés (doit être positive)
     * @param nbPiece             nombre de pièces du bien locatif
     * @param loyerBase           loyer de base du bien locatif (ne peut pas être négatif)
     * @param bat                 bâtiment auquel appartient le bien locatif
     * @throws IllegalArgumentException si la surface est non positive, si le loyer de base est négatif,
     *                                  ou si les champs obligatoires sont null
     */
    public BienLocatif(String identifiantLogement, TypeDeBien type, int surface, int nbPiece,
                      BigDecimal loyerBase, Batiment bat) {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit être positive.");
        }
        if (loyerBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le loyer de base ne peut pas être négatif.");
        }
        if (identifiantLogement == null || type == null || bat == null) {
            throw new IllegalArgumentException("Les champs obligatoires ne peuvent pas être null.");
        }

        this.identifiantLogement = identifiantLogement;
        this.type = type;
        this.surface = surface;
        this.nbPiece = nbPiece;
        this.loyerBase = loyerBase;
        this.bat = bat;
    }

    // Getters & Setters

    /**
     * Récupère l'identifiant unique du logement.
     * 
     * @return l'identifiant du logement
     */
    public String getIdentifiantLogement() {
        return identifiantLogement;
    }

    /**
     * Récupère l'identifiant fiscal du bien locatif.
     * 
     * @return l'identifiant fiscal
     */
    public String getIdFiscal() {
        return idFiscal;
    }

    /**
     * Définit l'identifiant fiscal du bien locatif.
     * 
     * @param idFiscal l'identifiant fiscal à définir
     */
    public void setIdFiscal(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    /**
     * Récupère le complément d'adresse du bien locatif.
     * 
     * @return le complément d'adresse
     */
    public String getComplementAdresse() {
        return complementAdresse;
    }

    /**
     * Définit le complément d'adresse du bien locatif.
     * 
     * @param complementAdresse le complément d'adresse à définir
     */
    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    /**
     * Récupère le type de bien locatif.
     * 
     * @return le type de bien locatif
     */
    public TypeDeBien getType() {
        return type;
    }

    /**
     * Récupère la surface du bien locatif.
     * 
     * @return la surface en mètres carrés
     */
    public int getSurface() {
        return surface;
    }

    /**
     * Définit la surface du bien locatif.
     * <p>
     * La surface doit être positive.
     * </p>
     * 
     * @param surface la surface à définir
     * @throws IllegalArgumentException si la surface est non positive
     */
    public void setSurface(int surface) {
        if (surface <= 0) {
            throw new IllegalArgumentException("La surface doit être positive.");
        }
        this.surface = surface;
    }

    /**
     * Récupère le nombre de pièces du bien locatif.
     * 
     * @return le nombre de pièces
     */
    public int getNbPiece() {
        return nbPiece;
    }

    /**
     * Définit le nombre de pièces du bien locatif.
     * 
     * @param nbPiece le nombre de pièces à définir
     */
    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
    }

    /**
     * Récupère le loyer de base du bien locatif.
     * 
     * @return le loyer de base
     */
    public BigDecimal getLoyerBase() {
        return loyerBase;
    }

    /**
     * Définit le loyer de base du bien locatif.
     * <p>
     * Le loyer de base ne peut pas être négatif.
     * </p>
     * 
     * @param loyerBase le loyer de base à définir
     * @throws IllegalArgumentException si le loyer de base est négatif
     */
    public void setLoyerBase(BigDecimal loyerBase) {
        if (loyerBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le loyer de base ne peut pas être négatif.");
        }
        this.loyerBase = loyerBase;
    }

    /**
     * Récupère le bâtiment auquel appartient le bien locatif.
     * 
     * @return le bâtiment
     */
    public Batiment getBat() {
        return bat;
    }

    // Généricité

    /**
     * Charge les données en utilisant le chargeur fourni.
     * <p>
     * Cette méthode générique permet de charger différentes entités associées au bien locatif.
     * </p>
     * 
     * @param <T>    le type des données à charger
     * @param loader le chargeur de données fonctionnel
     * @return la liste des données chargées
     * @throws SQLException si une erreur SQL survient lors du chargement des données
     * @throws IOException  si une erreur d'entrée/sortie survient lors du chargement des données
     */
    private <T> List<T> loadData(DataLoader<T> loader) throws SQLException, IOException {
        return loader.load();
    }

    /**
     * Interface fonctionnelle pour le chargement des données.
     * <p>
     * Utilisée pour passer des méthodes de chargement de données en tant que paramètres.
     * </p>
     * 
     * @param <T> le type des données à charger
     */
    @FunctionalInterface
    interface DataLoader<T> {
        /**
         * Charge et retourne une liste de données.
         * 
         * @return la liste des données chargées
         * @throws SQLException si une erreur SQL survient lors du chargement des données
         * @throws IOException  si une erreur d'entrée/sortie survient lors du chargement des données
         */
        List<T> load() throws SQLException, IOException;
    }

    // Getters avec Lazy-loading pour les listes

    /**
     * Récupère la liste des loyers associés au bien locatif.
     * <p>
     * Cette méthode utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * </p>
     * 
     * @return une liste non modifiable des loyers
     * @throws SQLException  si une erreur SQL survient lors du chargement des loyers
     * @throws IOException   si une erreur d'entrée/sortie survient lors du chargement des loyers
     */
    public List<Loyer> getLoyers() throws SQLException, IOException {
        if (loyers.isEmpty() && !loyersLoaded) {
            loyers = loadData(() -> new DaoLoyer().findByIdLogement(this.identifiantLogement));
            loyersLoaded = true;
        }
        return Collections.unmodifiableList(loyers);
    }

    /**
     * Récupère la liste des documents comptables associés au bien locatif.
     * <p>
     * Cette méthode utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * </p>
     * 
     * @return une liste non modifiable des documents comptables
     * @throws SQLException  si une erreur SQL survient lors du chargement des documents comptables
     * @throws IOException   si une erreur d'entrée/sortie survient lors du chargement des documents comptables
     */
    public List<FactureBien> getDocsComptables() throws SQLException, IOException {
        if (docsComptables.isEmpty() && !docsLoaded) {
            docsComptables = loadData(() -> new DaoFactureBien().findByIdLogement(this.identifiantLogement));
            docsLoaded = true;
        }

        return Collections.unmodifiableList(docsComptables);
    }

    /**
     * Récupère la liste des diagnostics associés au bien locatif.
     * <p>
     * Cette méthode utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * </p>
     * 
     * @return une liste non modifiable des diagnostics
     * @throws SQLException  si une erreur SQL survient lors du chargement des diagnostics
     * @throws IOException   si une erreur d'entrée/sortie survient lors du chargement des diagnostics
     */
    public List<Diagnostiques> getDiagnostiques() throws SQLException, IOException {
        if (diagnostiques.isEmpty() && !diagLoaded) {
            diagnostiques = loadData(() -> new DaoDiagnostique().findByIdLogement(this.identifiantLogement));
            diagLoaded = true;
        }

        return Collections.unmodifiableList(diagnostiques);
    }

    /**
     * Ajoute un loyer au bien locatif.
     * <p>
     * Cette méthode vérifie que le loyer correspond bien au bien locatif avant de l'ajouter.
     * Elle crée également le loyer dans la base de données via le DAO approprié.
     * </p>
     * 
     * @param loyer le loyer à ajouter
     * @throws IllegalArgumentException si le loyer ne correspond pas au bien locatif
     * @throws SQLException             si une erreur SQL survient lors de la création du loyer
     * @throws IOException              si une erreur d'entrée/sortie survient lors de la création du loyer
     */
    public void addLoyer(Loyer loyer) throws SQLException, IOException {
        if (!loyer.getIdBien().equals(this.identifiantLogement)) {
            throw new IllegalArgumentException("Le loyer doit correspondre au bien !");
        }

        new DaoLoyer().create(loyer);
        this.loyers.add(loyer);
    }

    /**
     * Supprime un loyer du bien locatif.
     * <p>
     * Cette méthode vérifie que le loyer correspond bien au bien locatif avant de le supprimer.
     * Elle supprime également le loyer dans la base de données via le DAO approprié.
     * </p>
     * 
     * @param loyer le loyer à supprimer
     * @throws IllegalArgumentException si le loyer ne correspond pas au bien locatif
     * @throws SQLException             si une erreur SQL survient lors de la suppression du loyer
     * @throws IOException              si une erreur d'entrée/sortie survient lors de la suppression du loyer
     */
    public void removeLoyer(Loyer loyer) throws SQLException, IOException {
        if (!loyer.getIdBien().equals(this.identifiantLogement)) {
            throw new IllegalArgumentException("Le loyer doit correspondre au bien !");
        }

        new DaoLoyer().delete(loyer);
        this.loyers.remove(loyer);
    }

    /**
     * Recharge les données associées au bien locatif.
     * <p>
     * Réinitialise les indicateurs de chargement et réinitialise les listes des entités associées.
     * </p>
     */
    public void rechargerDonnees() {
        loyersLoaded = false;
        docsLoaded = false;
        diagLoaded = false;
        
        diagnostiques = new ArrayList<>();
        loyers = new ArrayList<>();
        docsComptables = new ArrayList<>();
    }

    /**
     * Compare cet objet à un autre objet pour vérifier l'égalité.
     * <p>
     * Deux objets {@code BienLocatif} sont considérés égaux s'ils ont le même identifiant de logement.
     * </p>
     * 
     * @param o l'objet à comparer
     * @return {@code true} si les objets sont égaux, {@code false} sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BienLocatif)) return false;
        BienLocatif that = (BienLocatif) o;
        return identifiantLogement.equals(that.identifiantLogement);
    }

    /**
     * Calcule le code de hachage pour cet objet.
     * 
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(identifiantLogement);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de ce bien locatif.
     * 
     * @return une chaîne de caractères décrivant le bien locatif
     */
    @Override
    public String toString() {
        return "BienLocatif{" +
                "identifiantLogement='" + identifiantLogement + '\'' +
                ", type=" + type +
                ", surface=" + surface +
                ", nbPiece=" + nbPiece +
                ", loyerBase=" + loyerBase +
                ", bat=" + bat +
                '}';
    }

}
