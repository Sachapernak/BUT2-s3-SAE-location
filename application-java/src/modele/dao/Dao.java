package modele.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface générique représentant un Data Access Object (DAO).
 * 
 * Cette interface définit les méthodes standard pour l'accès aux données dans une base de données.
 * Elle permet de créer, mettre à jour, supprimer, et rechercher des entités de type T.
 * 
 * @param <T> Le type d'entité géré par le DAO (par exemple, une entité modèle comme `Adresse` ou `Bail`).
 */
public interface Dao<T> {

    /**
     * Crée une nouvelle entrée dans la base de données.
     * 
     * Cette méthode prend une entité de type T et insère les données dans la base.
     * 
     * @param donnees L'entité à insérer dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public void create(T donnees) throws SQLException, IOException;

    /**
     * Met à jour une entrée existante dans la base de données.
     * 
     * Cette méthode met à jour les données d'une entité dans la base en fonction des informations passées.
     * 
     * @param donnees L'entité à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public void update(T donnees) throws SQLException, IOException;

    /**
     * Supprime une entrée de la base de données.
     * 
     * Cette méthode supprime une entité de la base de données.
     * 
     * @param donnees L'entité à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public void delete(T donnees) throws SQLException, IOException;

    /**
     * Recherche une entité par son identifiant dans la base de données.
     * 
     * Cette méthode retourne une entité de type T correspondant à l'identifiant spécifié.
     * 
     * @param id L'identifiant de l'entité à rechercher. Peut être un ou plusieurs paramètres en fonction de la clé primaire.
     * @return L'entité correspondante à l'identifiant, ou null si elle n'existe pas.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public T findById(String... id) throws SQLException, IOException;

    /**
     * Recherche toutes les entités de type T dans la base de données.
     * 
     * Cette méthode retourne une liste de toutes les entités présentes dans la base.
     * 
     * @return Une liste contenant toutes les entités de type T.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public List<T> findAll() throws SQLException, IOException;
}

