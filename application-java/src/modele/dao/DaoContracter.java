package modele.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import modele.dao.requetes.Requete;
import modele.dao.requetes.RequeteSelectContratByIdLocataire;

/**
 * Classe DaoContracter qui gère l'accès aux données relatives aux contrats (contracter) dans la base de données.
 * 
 * Cette classe contient des méthodes pour récupérer les contrats associés à un locataire spécifique.
 * Elle utilise des requêtes SQL spécifiques pour interagir avec la base de données et obtenir des informations sur les contrats.
 */
public class DaoContracter {

    /**
     * Crée une instance de l'objet Contracter à partir d'un ResultSet.
     * 
     * Cette méthode extrait les informations d'un contrat à partir d'un `ResultSet` et retourne une instance de `Contracter`
     * associée au locataire spécifié. Elle gère la conversion des dates et la récupération des informations du bail lié.
     * 
     * @param curseur Le `ResultSet` contenant les données du contrat.
     * @param locataire Le locataire associé au contrat.
     * @return L'instance de `Contracter` correspondant aux données extraites du `ResultSet`.
     * @throws SQLException Si une erreur survient lors de l'accès aux données du `ResultSet`.
     * @throws IllegalArgumentException Si les données du contrat sont invalides.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    protected Contracter createInstance(ResultSet curseur, Locataire locataire) throws SQLException, IllegalArgumentException, IOException {
        String idBail = curseur.getString("id_bail");
        String dateEntre = curseur.getDate("Date_d_entree").toString();
        String dateSortie;
        dateSortie = curseur.getDate("Date_de_sortie") == null ?  null : curseur.getDate("Date_de_sortie").toString();
        float partLoyer = curseur.getFloat("PART_DE_LOYER");
        
        Contracter contrat = new Contracter(locataire,
                                            new DaoBail().findById(idBail),
                                            dateEntre,
                                            partLoyer);
        
        if (dateSortie != null) {
            contrat.ajouterDateSortie(dateSortie);
        }
        
        return contrat;
    }

    /**
     * Récupère la liste des contrats associés à un locataire.
     * 
     * Cette méthode exécute une requête SQL pour récupérer tous les contrats associés à un locataire donné. Elle renvoie
     * une liste de contrats sous forme d'objets `Contracter` associés à ce locataire.
     * 
     * @param donnees Le locataire pour lequel les contrats sont recherchés.
     * @return Une liste de contrats associés au locataire.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    protected List<Contracter> getContrats(Locataire donnees) throws SQLException, IOException {
        if (donnees == null) {
            return new ArrayList<Contracter>();
        }
        
        Requete<Contracter> req = new RequeteSelectContratByIdLocataire();
        
        String id = donnees.getIdLocataire();
        
        List<Contracter> res = new ArrayList<>();
        
        Connection cn = ConnexionBD.getInstance().getConnexion();
        
        PreparedStatement prSt = cn.prepareStatement(req.requete());
        
        req.parametres(prSt, id);
        
        ResultSet rs = prSt.executeQuery();
        
        while (rs.next()) {
            res.add(createInstance(rs, donnees));
        }
        
        rs.close();
        prSt.close();
        return res;
    }
}
