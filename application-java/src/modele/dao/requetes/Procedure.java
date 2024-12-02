package modele.dao.requetes;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Classe abstraite Procedure utilisée pour représenter une procédure stockée dans la base de données.
 * 
 * Cette classe générique définit la structure de base pour exécuter des procédures stockées. Elle est destinée
 * à être étendue par des classes spécifiques qui implémentent des procédures pour des entités particulières.
 * 
 * @param <T> Le type d'entité manipulée par la procédure stockée.
 */
public abstract class Procedure<T> {

    /**
     * Retourne la chaîne SQL correspondant à l'appel de la procédure stockée.
     * 
     * Cette méthode doit être implémentée dans les sous-classes pour fournir la syntaxe exacte de l'appel
     * de la procédure stockée (par exemple, `{ call procedure_name(?, ?) }`).
     * 
     * @return Une chaîne de caractères représentant l'appel de la procédure stockée.
     */
    public abstract String procedure();

    /**
     * Ajoute les paramètres nécessaires à un CallableStatement à partir d'identifiants.
     * 
     * Cette méthode est destinée à être surchargée dans les sous-classes si la procédure nécessite des identifiants
     * spécifiques en tant que paramètres.
     * 
     * @param prSt Le CallableStatement utilisé pour exécuter la procédure stockée.
     * @param id Les identifiants à passer à la procédure.
     * @throws SQLException Si une erreur survient lors de la définition des paramètres.
     */
    public void parametres(CallableStatement prSt, String... id) throws SQLException {
        // Méthode optionnelle, à surcharger si nécessaire dans les sous-classes.
    }

    /**
     * Ajoute les paramètres nécessaires à un CallableStatement à partir d'un objet de type T.
     * 
     * Cette méthode est destinée à être surchargée dans les sous-classes si la procédure nécessite un objet complet
     * pour définir ses paramètres.
     * 
     * @param prSt Le CallableStatement utilisé pour exécuter la procédure stockée.
     * @param donnee L'objet de type T contenant les données nécessaires pour exécuter la procédure.
     * @throws SQLException Si une erreur survient lors de la définition des paramètres.
     */
    public void parametres(CallableStatement prSt, T donnee) throws SQLException {
        // Méthode optionnelle, à surcharger si nécessaire dans les sous-classes.
    }
}

