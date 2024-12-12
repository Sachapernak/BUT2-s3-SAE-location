package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.ICC;

import modele.dao.requetes.RequeteCreateICC;
import modele.dao.requetes.RequeteDeleteICC;
import modele.dao.requetes.RequeteSelectICC;
import modele.dao.requetes.RequeteSelectICCById;
import modele.dao.requetes.RequeteUpdateICC;

public class DaoICC extends DaoModele<ICC> {

    /**
     * Crée une nouvelle ICC dans la base de données.
     * 
     * Cette méthode insère les informations d'une ICC dans la base de données en utilisant une requête
     * de type `RequeteCreateICC`.
     * 
     * @param donnees L'ICC à insérer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void create(ICC donnees) throws SQLException, IOException {
        miseAJour(new RequeteCreateICC(), donnees);
    }

    /**
     * Met à jour une ICC existante dans la base de données.
     * 
     * Cette méthode met à jour les informations d'une ICC dans la base de données en utilisant une requête
     * de type `RequeteUpdateICC`.
     * 
     * @param donnees L'ICC à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void update(ICC donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateICC(), donnees);
    }

    /**
     * Supprime une ICC de la base de données.
     * 
     * Cette méthode supprime une ICC de la base de données en utilisant une requête de type
     * `RequeteDeleteICC`.
     * 
     * @param donnees L'ICC à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void delete(ICC donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteICC(), donnees);
    }

    /**
     * Recherche une ICC par son identifiant dans la base de données.
     * 
     * Cette méthode retourne l'ICC correspondant à l'identifiant passé en paramètre en utilisant la requête
     * `RequeteSelectICCById`.
     * 
     * @param id Les identifiants de l'ICC à rechercher.
     * @return L'ICC correspondant à l'identifiant fourni.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public ICC findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectICCById(), id);
    }

    /**
     * Recherche toutes les ICCs dans la base de données.
     * 
     * Cette méthode retourne une liste de toutes les ICCs présentes dans la base de données en utilisant la requête
     * `RequeteSelectICC`.
     * 
     * @return La liste des ICCs trouvées dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public List<ICC> findAll() throws SQLException, IOException {
        return find(new RequeteSelectICC());
    }

	@Override
	protected ICC createInstance(ResultSet curseur) throws SQLException, IOException {
		
		String annee = curseur.getString("ANNEE_ICC");
		String trimestre = curseur.getString("TRIMESTRE_ICC");
		int indice = curseur.getInt("INDICE");
		
		return new ICC(annee, trimestre, indice);
	}

}
