package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.Contracter;
import modele.Locataire;
import modele.dao.requetes.ProcedureCreateLocataire;
import modele.dao.requetes.RequeteDeleteLocataire;
import modele.dao.requetes.RequeteSelectLocataire;
import modele.dao.requetes.RequeteSelectLocataireById;
import modele.dao.requetes.RequeteUpdateLocataire;
import modele.dao.requetes.requeteSelectLocataireByBienActif;

/**
 * Classe DaoLocataire qui gère l'accès aux données relatives aux locataires dans la base de données.
 * 
 * Cette classe contient des méthodes pour créer, mettre à jour, supprimer et récupérer des locataires. Elle gère également 
 * l'association des locataires à leurs contrats et à leur adresse dans la base de données.
 */
public class DaoLocataire extends DaoModele<Locataire> {

    /**
     * Crée un nouveau locataire dans la base de données.
     * 
     * Utilise une procédure stockée pour insérer les informations du locataire dans la base de données.
     * 
     * @param donnees Le locataire à créer dans la base de données.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    @Override
    public void create(Locataire donnees) throws SQLException, IOException {
        appelProcedure(new ProcedureCreateLocataire(), donnees);
    }

    /**
     * Met à jour les informations d'un locataire dans la base de données.
     * 
     * @param donnees Le locataire avec les informations mises à jour.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    @Override
    public void update(Locataire donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateLocataire(), donnees);
    }

    /**
     * Supprime un locataire de la base de données.
     * 
     * Cette méthode supprime un locataire en fonction de son identifiant. Si des documents comptables existent, 
     * une erreur Oracle -20002 peut être levée.
     * 
     * @param donnees Le locataire à supprimer (objet contenant les informations nécessaires).
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    @Override
    public void delete(Locataire donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteLocataire(), donnees);
    }

    /**
     * Recherche un locataire par son identifiant.
     * 
     * Cette méthode récupère un locataire à partir de la base de données en fonction de son identifiant. 
     * Elle récupère également les contrats associés au locataire.
     * 
     * @param id Identifiant du locataire.
     * @return Le locataire correspondant à l'identifiant.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    @Override
    public Locataire findById(String... id) throws SQLException, IOException {
        Locataire locataire = findById(new RequeteSelectLocataireById(), id);
        
        List<Contracter> contrats = new DaoContracter().getContrats(locataire);
        
        for (Contracter contrat : contrats) {
            locataire.getContrats().add(contrat);
        }
        
        return locataire;
    }
    
    public List<Locataire> findByIdBien(String...id) throws SQLException, IOException {
    	return find(new requeteSelectLocataireByBienActif(), id);
    }

    /**
     * Recherche tous les locataires dans la base de données.
     * 
     * Cette méthode récupère tous les locataires enregistrés dans la base de données et leurs contrats associés.
     * 
     * @return Une liste de tous les locataires.
     * @throws SQLException Si une erreur survient lors de l'exécution de la requête SQL.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    @Override
    public List<Locataire> findAll() throws SQLException, IOException {
        List<Locataire> locataires = find(new RequeteSelectLocataire());
        
        for (Locataire locataire : locataires) {
            List<Contracter> contrats = new DaoContracter().getContrats(locataire);
            
            for (Contracter contrat : contrats) {
                locataire.getContrats().add(contrat);
            }
        }
        
        return locataires;
    }

    /**
     * Crée un objet Locataire à partir des données récupérées dans un ResultSet.
     * 
     * Cette méthode extrait les informations relatives à un locataire depuis un ResultSet et retourne une instance 
     * de l'objet Locataire avec ces informations. Elle inclut également l'adresse et d'autres détails du locataire.
     * 
     * @param curseur Le ResultSet contenant les données du locataire.
     * @return L'objet Locataire créé à partir des données du ResultSet.
     * @throws SQLException Si une erreur survient lors de l'extraction des données du ResultSet.
     * @throws IOException Si une erreur survient lors de la lecture des données.
     */
    @Override
    protected Locataire createInstance(ResultSet curseur) throws SQLException, IOException {
        String id = curseur.getString("IDENTIFIANT_LOCATAIRE");
        String nom = curseur.getString("NOM_LOCATAIRE");
        String prenom = curseur.getString("PRENOM_LOCATAIRE");
        String email = curseur.getString("EMAIL_LOCATAIRE");
        String telephone = curseur.getString("TELEPHONE_LOCATAIRE");
        String dateNaissance = curseur.getDate("DATE_NAISSANCE").toString();
        String lieuNaissance = curseur.getString("LIEU_DE_NAISSANCE");
        
        String idAdresse = curseur.getString("ID_SAE_ADRESSE");
        
        Adresse adresse = null;
        
        if (idAdresse != null) {
            adresse = new DaoAdresse().findById(idAdresse);
        }
        
        Locataire locataire = new Locataire(id, nom, prenom, dateNaissance);
        
        if (telephone != null) {
            locataire.setTelephone(telephone);
        }
        
        if (lieuNaissance != null) {
            locataire.setLieuDeNaissance(lieuNaissance);
        }
        
        if (email != null) {
            locataire.setEmail(email);
        }
        
        if (adresse != null) {
            locataire.setAdresse(adresse);
        }
        
        return locataire;
    }
}
