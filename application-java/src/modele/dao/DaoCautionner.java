package modele.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.Cautionner;
import modele.Bail;
import modele.dao.requetes.Requete;
import modele.dao.requetes.RequeteSelectCautionByIdBail;

public class DaoCautionner {

    /**
     * Crée une instance de l'objet Cautionner à partir d'un ResultSet.
     * 
     * Cette méthode extrait les informations d'une caution à partir d'un `ResultSet` et retourne une instance de `Cautionner`
     * associée au cautionnaire spécifié. Elle gère la conversion des dates et la récupération des informations du bail lié.
     * 
     * @param curseur Le `ResultSet` contenant les données de la caution.
     * @param cautionnaire Le cautionnaire associé.
     * @return L'instance de `Cautionner` correspondant aux données extraites du `ResultSet`.
     * @throws SQLException Si une erreur survient lors de l'accès aux données du `ResultSet`.
     * @throws IllegalArgumentException Si les données de la caution sont invalides.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    protected Cautionner createInstance(ResultSet curseur, Bail bail) throws SQLException, IllegalArgumentException, IOException {
        String idCautionnaire = curseur.getString("id_caution");
        String fichierCaution = curseur.getString("fichier_caution");
        BigDecimal montant = curseur.getBigDecimal("montant");        
        Cautionner caution = new Cautionner(montant,fichierCaution ,bail, new DaoCautionnaire().findById(idCautionnaire));


        return caution;
    }

   
    /**
     * Récupère la liste des cautions associées à un bail.
     * 
     * Cette méthode exécute une requête SQL pour récupérer toutes les cautions associées à un bail donné. 
     * Elle renvoie une liste de cautions sous forme d'objets `Cautionner` associés à ce bail.
     * 
     * @param donnees Le bail pour lequel les cautions sont recherchées.
     * @return Une liste de cautions associées au bail.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    protected List<Cautionner> getCautions(Bail donnees) throws SQLException, IOException {
        if (donnees == null) {
            return new ArrayList<Cautionner>();
        }

        Requete<Cautionner> req = new RequeteSelectCautionByIdBail();

        String id = String.valueOf(donnees.getIdBail());

        List<Cautionner> res = new ArrayList<>();

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
