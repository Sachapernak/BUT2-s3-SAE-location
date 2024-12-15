package modele;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import modele.dao.DaoDocument;
import modele.dao.DaoProvisionCharge;
import modele.dao.DaoRegularisation;


/**
 * La classe Bail représente un contrat de location avec un identifiant unique, une date de début et une date de fin.
 * Elle permet de valider la relation temporelle entre ces dates et de manipuler les informations du bail.
 */
public class Bail {

    /**
     * Identifiant unique du bail.
     */
    private String idBail;

    /**
     * Date de début du bail au format "yyyy-dd-MM".
     */
    private String dateDeDebut;

    /**
     * Date de fin du bail au format "yyyy-dd-MM".
     */
    private String dateDeFin;
    
    private BienLocatif bien;
    /**
    * Indicateur de chargement des regularisations.
    */
    private boolean reguLoaded;
    /**
     * Indicateur de chargement des provisions pour charge.
     */
    private boolean proviLoaded;
    
    /**
     * Indicateur de chargement des documents.
     */
    
    private boolean docLoaded;
    
    /**
     * Liste des regularisations associées au bail.
     */
    
    private List<Regularisation> regularisations = new ArrayList<>(); 
    /**
     * Liste des provisions poru charge associées au bail.
     */
    private List<ProvisionCharge> provisioncharges = new ArrayList<>(); 
    /**
     * Liste des documents associés au bail.
     */
    private List<Document> documents = new ArrayList<>(); 
    
    /**
     * Constructeur de la classe Bail.
     *
     * @param idBail      Identifiant unique du bail.
     * @param dateDeDebut Date de début du bail au format "yyyy-dd-MM".
     * @param bien 		  Le bien dans lequel le bail se trouve.
     */
    public Bail(String idBail, String dateDeDebut, BienLocatif bien) throws IllegalArgumentException{
       
    	// Vérifier que la date de début est strictement antérieure à la date de fin
        if (idBail == null || dateDeDebut == null || bien == null) {
            throw new IllegalArgumentException("idBail, dateDeDebut et bien ne doivent pas etre null");
        }
    	this.idBail = idBail;
        this.dateDeDebut = dateDeDebut;
        this.bien = bien;
        
    }

    /**
     * Constructeur de la classe Bail avec validation des dates.
     *
     * Ce constructeur initialise un objet Bail avec un identifiant unique, une date de début et une date de fin.
     * Il valide que la date de début est strictement antérieure à la date de fin, et que les dates respectent le format "yyyy-dd-MM".
     *
     * @param idBail      Identifiant unique du bail.
     * @param dateDeDebut Date de début du bail au format "yyyy-dd-MM".
     * @param dateDeFin   Date de fin du bail au format "yyyy-dd-MM".
     * @throws IllegalArgumentException Si la date de début n'est pas strictement antérieure à la date de fin
     *                                  ou si le format des dates est incorrect.
     */
    public Bail(String idBail, String dateDeDebut, String dateDeFin, BienLocatif bien) throws IllegalArgumentException {
    	if (idBail == null || dateDeDebut == null || bien == null) {
            throw new IllegalArgumentException("idBail, dateDeDebut et bien ne doivent pas etre null");
        }
    	
    	// Définir le format attendu pour les dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir les chaînes en objets LocalDate
        LocalDate date1 = LocalDate.parse(dateDeDebut, formatter);
        LocalDate date2 = LocalDate.parse(dateDeFin, formatter);

        // Vérifier que la date de début est strictement antérieure à la date de fin
        if (date1.isAfter(date2) || date1.isEqual(date2)) {
            throw new IllegalArgumentException("La date de début doit être avant celle de la fin.");
        }

        // Initialiser les attributs de l'objet
        this.idBail = idBail;
        this.dateDeDebut = dateDeDebut;
        this.dateDeFin = dateDeFin;
        this.bien = bien;
        
    }

    
    
    @Override
	public String toString() {
		return "Bail [idBail=" + idBail + ", dateDeDebut=" + dateDeDebut + ", dateDeFin=" + dateDeFin + ", bien=" + bien+ "]";
	}

	public BienLocatif getBien() {
    	return this.bien;
    }

    /**
     * Retourne l'identifiant unique du bail.
     *
     * @return L'identifiant du bail.
     */
    public String getIdBail() {
        return idBail;
    }

    /**
     * Retourne la date de début du bail.
     *
     * @return La date de début du bail.
     */
    public String getDateDeDebut() {
        return dateDeDebut;
    }

    /**
     * Modifie la date de début du bail.
     *
     * @param date_de_debut La nouvelle date de début du bail.
     */
    public void setDateDeDebut(String dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    /**
     * Retourne la date de fin du bail.
     *
     * @return La date de fin du bail.
     */
    public String getDateDeFin() {
        return dateDeFin;
    }

    /**
     * Modifie la date de fin du bail.
     * Cette méthode assure que la nouvelle date de fin est postérieure à la date de début.
     *
     * @param dateDeFin La nouvelle date de fin, au format "yyyy-dd-MM".
     * @throws IllegalArgumentException Si la date de fin est antérieure ou égale à la date de début, ou si le format est invalide.
     */
    public void setDateDeFin(String dateDeFin) throws IllegalArgumentException {
        // Définir le format attendu pour les dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir les dates en objets LocalDate
        LocalDate date1 = LocalDate.parse(this.dateDeDebut, formatter); // Date de début actuelle
        LocalDate date2 = LocalDate.parse(dateDeFin, formatter);       // Nouvelle date de fin

        // Vérifier que la nouvelle date de fin est postérieure à la date de début
        if (date1.isAfter(date2) || date1.isEqual(date2)) {
            throw new IllegalArgumentException("La date de début doit être avant celle de la fin");
        }

        this.dateDeFin = dateDeFin;
    }

    

    
    /**
     * Calcule et retourne le code de hachage basé sur l'identifiant du bail.
     *
     * @return Le code de hachage du bail.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idBail);
    }

    /**
     * Compare deux objets Bail pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les baux ont le même identifiant, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bail)) {
            return false;
        }
        Bail other = (Bail) obj;
        return Objects.equals(idBail, other.idBail);
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

    // Getters avec Lazy-loading pour les maps

    /**
     * Récupère la map des régularisations associés au bien locatif.
     * <p>
     * Cette méthode utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * </p>
     * 
     * @return une map non modifiable des loyers
     * @throws SQLException  si une erreur SQL survient lors du chargement des loyers
     * @throws IOException   si une erreur d'entrée/sortie survient lors du chargement des loyers
     */
    public List<Regularisation> getRegularisation() throws SQLException, IOException {
        if (regularisations.isEmpty() && !reguLoaded) {
            regularisations = loadData(() -> new DaoRegularisation().findByIdBail(this.idBail));
            reguLoaded = true;
        }
        return Collections.unmodifiableList(regularisations);
    }
    
    
    
    /**
     * Ajoute une régularisation au bail.
     * <p>
     * Cette méthode vérifie que la régularisation correspond bien au bail avant de l'ajouter.
     * Elle crée également la régularisation dans la base de données via le DAO approprié.
     * </p>
     * 
     * @param regularisation la regularisation à ajouter
     * @throws IllegalArgumentException si la regularisation ne correspond pas au bail
     * @throws SQLException             si une erreur SQL survient lors de la création du regularisation
     * @throws IOException              si une erreur d'entrée/sortie survient lors de la création du regularisation
     */
    public void addRegularisation(Regularisation regu) throws SQLException, IOException {
        if (!regu.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("La regularisation doit correspondre au bail !");
        }

        new DaoRegularisation().create(regu);
        this.regularisations.add(regu);
    }
    
    
    
    /**
     * Supprime une regularisation du bail.
     * <p>
     * Cette méthode vérifie que la regularisation correspond bien au bail avant de le supprimer.
     * Elle supprime également la regularisation dans la base de données via le DAO approprié.
     * </p>
     * 
     * @param regularisation la regularisation à supprimer
     * @throws IllegalArgumentException si la regularisation ne correspond pas au bail
     * @throws SQLException             si une erreur SQL survient lors de la suppression de la regularisation
     * @throws IOException              si une erreur d'entrée/sortie survient lors de la suppression de la regularisation
     */
    public void removeRegularisation(Regularisation regu) throws SQLException, IOException {
        if (!regu.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("La regularisation doit correspondre au bien !");
        }

        new DaoRegularisation().delete(regu);
        this.regularisations.remove(regu);
    }
    
    
    
    /**
     * Ajoute une provision pour charge au bail.
     * <p>
     * Cette méthode s'assure que la provision pour charge correspond au bail en comparant les identifiants.
     * En cas de correspondance, elle ajoute la provision à la liste et enregistre l'entrée via le DAO.
     * </p>
     *
     * @param proCha La provision pour charge à ajouter.
     * @throws IllegalArgumentException Si l'identifiant de la provision pour charge ne correspond pas au bail.
     * @throws SQLException             Si une erreur SQL survient lors de l'ajout à la base de données.
     * @throws IOException              Si une erreur d'entrée/sortie se produit lors de l'ajout.
     */
    public void addProvisionCharge(ProvisionCharge proCha) throws SQLException, IOException {
        if (!proCha.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("La Provision pour charge doit correspondre au bail !");
        }

        new DaoProvisionCharge().create(proCha);
        this.provisioncharges.add(proCha);
    }

    
    
    
    /**
     * Supprime une provision pour charge du bail.
     * <p>
     * Cette méthode vérifie que la provision pour charge appartient bien au bail avant de la supprimer.
     * Elle effectue ensuite la suppression dans la base de données via le DAO.
     * </p>
     *
     * @param proCha La provision pour charge à supprimer.
     * @throws IllegalArgumentException Si l'identifiant de la provision pour charge ne correspond pas au bail.
     * @throws SQLException             Si une erreur SQL survient lors de la suppression de la base de données.
     * @throws IOException              Si une erreur d'entrée/sortie se produit lors de la suppression.
     */
    public void removeProvisionCharge(ProvisionCharge proCha) throws SQLException, IOException {
        if (!proCha.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("La provision pour charge doit correspondre au bail !");
        }

        new DaoProvisionCharge().delete(proCha);
        this.provisioncharges.remove(proCha);
    }

    
    /**
     * Récupère la liste des provisions pour charges associées au bail.
     * <p>
     * Utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * Les données sont chargées depuis la base de données uniquement si elles n'ont pas encore été chargées.
     * </p>
     *
     * @return Une liste non modifiable des provisions pour charges.
     * @throws SQLException Si une erreur SQL survient lors du chargement des provisions.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors du chargement des provisions.
     */
    public List<ProvisionCharge> getProvisionCharge() throws SQLException, IOException {
        if (provisioncharges.isEmpty() && !proviLoaded) {
            provisioncharges = loadData(() -> new DaoProvisionCharge().findByIdBail(this.idBail));
            proviLoaded = true;
        }
        return Collections.unmodifiableList(provisioncharges);
    }

    
    
    /**
     * Ajoute un document au bail.
     * <p>
     * Cette méthode vérifie que le document à ajouter est bien associé au bail en comparant les identifiants.
     * En cas de correspondance, le document est ajouté à la liste des documents et inséré dans la base de données
     * via le DAO approprié.
     * </p>
     *
     * @param docu Le document à ajouter.
     * @throws IllegalArgumentException Si l'identifiant du document ne correspond pas à celui du bail.
     * @throws SQLException             Si une erreur SQL survient lors de l'insertion du document dans la base de données.
     * @throws IOException              Si une erreur d'entrée/sortie se produit lors de l'insertion du document.
     */
    public void addDocument(Document docu) throws SQLException, IOException {
        if (!docu.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("Le document doit correspondre au bail !");
        }

        new DaoDocument().create(docu);
        this.documents.add(docu);
    }

    
    
    
    /**
     * Supprime un document du bail.
     * <p>
     * Cette méthode vérifie que le document à supprimer est bien associé au bail en comparant les identifiants.
     * En cas de correspondance, le document est supprimé de la liste des documents et retiré de la base de données
     * via le DAO approprié.
     * </p>
     *
     * @param docu Le document à supprimer.
     * @throws IllegalArgumentException Si l'identifiant du document ne correspond pas à celui du bail.
     * @throws SQLException             Si une erreur SQL survient lors de la suppression du document dans la base de données.
     * @throws IOException              Si une erreur d'entrée/sortie se produit lors de la suppression du document.
     */
    public void removeDocument(Document docu) throws SQLException, IOException {
        if (!docu.getIdBail().equals(this.idBail)) {
            throw new IllegalArgumentException("Le document doit correspondre au bail !");
        }

        new DaoDocument().delete(docu);
        this.documents.remove(docu);
    }

    
    /**
     * Récupère la liste des documents associés au bail.
     * <p>
     * Cette méthode utilise le chargement paresseux (lazy-loading) pour optimiser les performances.
     * Les documents sont chargés depuis la base de données uniquement si cela n'a pas déjà été fait.
     * La liste retournée est non modifiable pour préserver l'intégrité des données.
     * </p>
     *
     * @return Une liste non modifiable des documents associés au bail.
     * @throws SQLException Si une erreur SQL survient lors du chargement des documents depuis la base de données.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors du chargement des documents.
     */
    public List<Document> getDocument() throws SQLException, IOException {
        if (documents.isEmpty() && !docLoaded) {
            documents = loadData(() -> new DaoDocument().findByIdBail(this.idBail));
            docLoaded = true;
        }
        return Collections.unmodifiableList(documents);
    }

    
    
    
    /**
     * Recharge les données associées au bail.
     * <p>
     * Réinitialise les indicateurs de chargement et réinitialise les listes des entités associées.
     * </p>
     */
    public void rechargerDonnees() {
        reguLoaded = false;
        proviLoaded = false;
        docLoaded = false;
        
        regularisations = new ArrayList<>();
        provisioncharges = new ArrayList<>();
        documents = new ArrayList<>();
    }
}

