package modele.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.dao.requetes.Procedure;
import modele.dao.requetes.Requete;

/**
 * Classe abstraite DaoModele qui fournit des opérations de base pour la gestion des objets dans la base de données.
 * 
 * Cette classe générique définit des méthodes pour exécuter des requêtes SQL (SELECT, UPDATE) et des procédures stockées
 * afin de manipuler les données dans la base. Elle est destinée à être étendue par des classes spécifiques qui 
 * définissent des entités particulières (comme Locataire, Contracter, etc.).
 * 
 * @param <T> Type générique représentant l'entité à manipuler (par exemple, Locataire, Contracter).
 */
public abstract class DaoModele<T> implements Dao<T> {

    /**
     * Crée une instance de l'entité T à partir des données extraites d'un ResultSet.
     * 
     * Cette méthode est abstraite et doit être implémentée dans les classes filles pour créer une instance
     * d'une entité spécifique à partir des données récupérées d'une requête SQL.
     * 
     * @param curseur Le ResultSet contenant les données extraites de la base de données.
     * @return L'entité T créée à partir des données du ResultSet.
     * @throws SQLException Si une erreur survient lors de l'extraction des données du ResultSet.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    protected abstract T createInstance(ResultSet curseur) throws SQLException, IOException;

    /**
     * Exécute une requête SELECT et retourne une liste d'objets T.
     * 
     * Cette méthode est utilisée pour récupérer plusieurs entités à partir de la base de données. Elle utilise
     * un PreparedStatement pour exécuter la requête SQL et appelle createInstance pour créer chaque entité.
     * 
     * @param prSt Le PreparedStatement pour exécuter la requête SQL.
     * @return Une liste d'objets T extraits de la base de données.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    public List<T> select(PreparedStatement prSt) throws SQLException, IOException {
        List<T> results = new ArrayList<>();

        ResultSet rs = prSt.executeQuery();

        while(rs.next()) {
            results.add(createInstance(rs));
        }

        rs.close();
        prSt.close();

        return results;
    }

    /**
     * Appelle une procédure stockée dans la base de données.
     * 
     * Cette méthode exécute une procédure stockée en utilisant un CallableStatement. Les paramètres nécessaires 
     * sont ajoutés via la méthode parametres de la procédure, puis la procédure est exécutée.
     * 
     * @param pro La procédure stockée à exécuter.
     * @param donnee Les données à passer à la procédure.
     * @return Le nombre de lignes affectées par la procédure.
     * @throws SQLException Si une erreur survient lors de l'exécution de la procédure SQL.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    public int appelProcedure(Procedure<T> pro, T donnee) throws SQLException, IOException {
        Connection cn = ConnexionBD.getInstance().getConnexion();
        CallableStatement clSt = cn.prepareCall(pro.procedure());
        pro.parametres(clSt, donnee);

        return clSt.executeUpdate();
    }

    /**
     * Effectue une mise à jour dans la base de données à l'aide d'une requête UPDATE.
     * 
     * Cette méthode utilise un PreparedStatement pour exécuter la requête SQL de mise à jour et passe les paramètres 
     * nécessaires via la méthode parametres de la requête.
     * 
     * @param req La requête SQL à exécuter pour la mise à jour.
     * @param donnee Les données à passer à la requête.
     * @return Le nombre de lignes affectées par la mise à jour.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    public int miseAJour(Requete<T> req, T donnee) throws SQLException, IOException {
        Connection cn = ConnexionBD.getInstance().getConnexion();
        PreparedStatement prSt = cn.prepareStatement(req.requete());
        req.parametres(prSt, donnee);

        return prSt.executeUpdate();
    }

    /**
     * Effectue une recherche dans la base de données à l'aide d'une requête SELECT.
     * 
     * Cette méthode utilise un PreparedStatement pour exécuter la requête SQL de sélection et retourne une liste 
     * des entités T extraites.
     * 
     * @param req La requête SQL à exécuter.
     * @param id L'identifiant ou les paramètres à passer à la requête.
     * @return Une liste d'objets T extraits de la base de données.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    public List<T> find(Requete<T> req, String...id) throws SQLException, IOException {
        List<T> res;

        Connection cn = ConnexionBD.getInstance().getConnexion();
        PreparedStatement prSt = cn.prepareStatement(req.requete());

        req.parametres(prSt, id);

        res = select(prSt);

        prSt.close();

        return res;
    }

    /**
     * Recherche une entité par son identifiant dans la base de données.
     * 
     * Cette méthode recherche une entité spécifique à l'aide de la méthode find et retourne la première correspondance.
     * Si aucune entité n'est trouvée, elle retourne null.
     * 
     * @param req La requête SQL à exécuter.
     * @param id L'identifiant à rechercher dans la base de données.
     * @return L'entité T correspondant à l'identifiant, ou null si aucune entité n'est trouvée.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    public T findById(Requete<T> req, String...id) throws SQLException, IOException {
        List<T> res = find(req, id);

        if (res.isEmpty()) {
            return null;
        } else {
            return res.get(0);
        }
    }
}
