package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe abstraite Requete utilisée pour représenter des requêtes SQL dans le DAO.
 * 
 * Cette classe générique définit la structure de base pour exécuter des requêtes SQL paramétrées.
 * Elle est destinée à être étendue par des classes spécifiques implémentant des requêtes
 * adaptées à des entités particulières.
 *
 * @param <T> le type d'entité sur lequel la requête opère.
 */
public abstract class Requete<T> {
    
    /**
     * Retourne la requête SQL sous forme de chaîne.
     * 
     * Cette méthode doit être implémentée par les classes dérivées pour fournir 
     * la requête SQL à exécuter.
     * 
     * @return une chaîne représentant la requête SQL.
     */
    public abstract String requete();
    
    /**
     * Définit les paramètres de la requête à l'aide d'identifiants.
     * 
     * Cette méthode peut être utilisée pour des requêtes basées sur des identifiants 
     * (par exemple, des clés primaires).
     * 
     * @param prSt le PreparedStatement sur lequel définir les paramètres.
     * @param id un ou plusieurs identifiants à utiliser dans la requête.
     * @throws SQLException si une erreur survient lors de la configuration des paramètres.
     */
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {}
    
    /**
     * Définit les paramètres de la requête à l'aide d'un objet de type T.
     * 
     * Cette méthode peut être utilisée pour des requêtes où les données de l'objet 
     * doivent être insérées ou mises à jour.
     * 
     * @param prSt le PreparedStatement sur lequel définir les paramètres.
     * @param donnee l'objet contenant les informations nécessaires pour la requête.
     * @throws SQLException si une erreur survient lors de la configuration des paramètres.
     */
    public void parametres(PreparedStatement prSt, T donnee) throws SQLException {}
}

