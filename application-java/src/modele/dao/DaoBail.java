package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modele.Bail;
import modele.dao.requetes.RequeteCreateBail;
import modele.dao.requetes.RequeteDeleteBail;
import modele.dao.requetes.RequeteSelectBail;
import modele.dao.requetes.RequeteSelectBailById;
import modele.dao.requetes.RequeteUpdateBail;

/**
 * Classe DaoBail qui implémente les opérations CRUD pour l'entité Bail.
 * 
 * Cette classe permet de gérer les opérations de création, mise à jour, suppression, et recherche des baux dans la base de données.
 * Elle étend la classe générique DaoModele et utilise des requêtes SQL spécifiques pour interagir avec la base de données.
 */
public class DaoBail extends DaoModele<Bail> implements Dao<Bail> {

    /**
     * Crée un nouveau bail dans la base de données.
     * 
     * Cette méthode insère un nouveau bail dans la base de données en utilisant une requête de type `RequeteCreateBail`.
     * 
     * @param donnees Le bail à insérer dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void create(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteCreateBail(), donnees);
    }

    /**
     * Met à jour un bail existant dans la base de données.
     * 
     * Cette méthode met à jour les informations d'un bail dans la base de données en utilisant une requête de type `RequeteUpdateBail`.
     * 
     * @param donnees Le bail à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void update(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateBail(), donnees);
    }

    /**
     * Supprime un bail de la base de données.
     * 
     * Cette méthode supprime un bail de la base de données en utilisant une requête de type `RequeteDeleteBail`.
     * 
     * @param donnees Le bail à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void delete(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteBail(), donnees);
    }

    /**
     * Recherche un bail par son identifiant dans la base de données.
     * 
     * Cette méthode retourne le bail correspondant à l'identifiant passé en paramètre en utilisant une requête de type `RequeteSelectBailById`.
     * 
     * @param id L'identifiant du bail à rechercher.
     * @return Le bail correspondant à l'identifiant fourni.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public Bail findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectBailById(), id);
    }

    /**
     * Recherche tous les baux dans la base de données.
     * 
     * Cette méthode retourne une liste de tous les baux présents dans la base de données en utilisant la requête `RequeteSelectBail`.
     * 
     * @return La liste des baux trouvés dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public List<Bail> findAll() throws SQLException, IOException {
        return find(new RequeteSelectBail());
    }

    /**
     * Crée une instance d'un bail à partir des données récupérées d'un ResultSet.
     * 
     * Cette méthode extrait les informations d'un bail à partir d'un `ResultSet` et retourne une instance de l'objet `Bail` correspondant.
     * Elle gère la conversion des dates en objets `LocalDate` et formate les dates pour correspondre au format attendu par la classe `Bail`.
     * 
     * @param curseur Le `ResultSet` contenant les données d'un bail.
     * @return L'instance de Bail correspondant aux données extraites.
     * @throws SQLException Si une erreur survient lors de l'accès aux données du `ResultSet`.
     */
    @Override
    protected Bail createInstance(ResultSet curseur) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir la chaîne en LocalDateTime
        LocalDateTime localDateTimeDebut = LocalDateTime.parse(curseur.getString("DATE_DE_DEBUT"), formatter);

        // Extraire uniquement la date si nécessaire
        LocalDate localDateDebut = localDateTimeDebut.toLocalDate();
        
        String idBail = curseur.getString("ID_BAIL");

        // Formatter la date de début
        String dateDeDebut = localDateDebut.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));

        Bail bail = new Bail(idBail, dateDeDebut);
        String dateDeFin = curseur.getString("DATE_DE_FIN");
        if (dateDeFin != null) {
            LocalDateTime localDateTimeFin = LocalDateTime.parse(dateDeFin, formatter);
            LocalDate localDateFin = localDateTimeFin.toLocalDate();
            bail.setDateDeFin(localDateFin.format(DateTimeFormatter.ofPattern("yyyy-dd-MM")));
        }
        return bail;
    }
}
