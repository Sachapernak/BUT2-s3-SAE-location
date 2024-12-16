package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.dao.requetes.RequeteCreateAdresse;
import modele.dao.requetes.RequeteDeleteAdresse;
import modele.dao.requetes.RequeteSelectAdresse;
import modele.dao.requetes.RequeteSelectAdresseById;
import modele.dao.requetes.RequeteUpdateAdresse;

/**
 * Classe DaoAdresse qui implémente les opérations CRUD pour l'entité Adresse.
 * 
 * Cette classe fournit les méthodes spécifiques pour gérer les adresses dans une base de données.
 * Elle étend la classe générique DaoModele et utilise des requêtes pré-définies pour interagir avec la base de données.
 */
public class DaoAdresse extends DaoModele<Adresse> {

    /**
     * Crée une nouvelle adresse dans la base de données.
     * 
     * Cette méthode insère les informations d'une adresse dans la base de données en utilisant une requête
     * de type `RequeteCreateAdresse`.
     * 
     * @param donnees L'adresse à insérer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void create(Adresse donnees) throws SQLException, IOException {
        miseAJour(new RequeteCreateAdresse(), donnees);
    }

    /**
     * Met à jour une adresse existante dans la base de données.
     * 
     * Cette méthode met à jour les informations d'une adresse dans la base de données en utilisant une requête
     * de type `RequeteUpdateAdresse`.
     * 
     * @param donnees L'adresse à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void update(Adresse donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateAdresse(), donnees);
    }

    /**
     * Supprime une adresse de la base de données.
     * 
     * Cette méthode supprime une adresse de la base de données en utilisant une requête de type
     * `RequeteDeleteAdresse`.
     * 
     * @param donnees L'adresse à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void delete(Adresse donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteAdresse(), donnees);
    }

    /**
     * Recherche une adresse par son identifiant dans la base de données.
     * 
     * Cette méthode retourne l'adresse correspondant à l'identifiant passé en paramètre en utilisant la requête
     * `RequeteSelectAdresseById`.
     * 
     * @param id L'identifiant de l'adresse à rechercher.
     * @return L'adresse correspondant à l'identifiant fourni.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public Adresse findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectAdresseById(), id);
    }

    /**
     * Recherche toutes les adresses dans la base de données.
     * 
     * Cette méthode retourne une liste de toutes les adresses présentes dans la base de données en utilisant la requête
     * `RequeteSelectAdresse`.
     * 
     * @return La liste des adresses trouvées dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public List<Adresse> findAll() throws SQLException, IOException {
        return find(new RequeteSelectAdresse());
    }

    /**
     * Crée une instance d'une adresse à partir des données récupérées d'un ResultSet.
     * 
     * Cette méthode extrait les informations d'une adresse à partir d'un `ResultSet` et retourne une instance de l'objet
     * `Adresse` correspondant.
     * 
     * @param curseur Le `ResultSet` contenant les données d'une adresse.
     * @return L'instance d'Adresse correspondant aux données extraites.
     * @throws SQLException Si une erreur survient lors de l'accès aux données du `ResultSet`.
     */
    @Override
    protected Adresse createInstance(ResultSet curseur) throws SQLException {
    	String idAdresse = curseur.getString("ID_SAE_ADRESSE");
        String adresseLigne = curseur.getString("ADRESSE");
        int codePostal = curseur.getInt("CODE_POSTAL");
        String ville = curseur.getString("VILLE");
        String complementAdresse = curseur.getString("COMPLEMENT_ADRESSE");

        Adresse adresse = new Adresse(idAdresse, adresseLigne, codePostal, ville);
        
        if (complementAdresse != null) {
            adresse.setComplementAdresse(complementAdresse);
        }
        
        return adresse;
    }
}
