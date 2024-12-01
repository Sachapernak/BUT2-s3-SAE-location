package modele.dao.requetes;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.List;

import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;

public class RequeteUpdateLocataire extends Requete<Locataire> {

    @Override
    public String requete() {
        return "UPDATE SAE_Locataire SET " +
               "Nom_locataire = ?, Prenom_locataire = ?, email_locataire = ?, " +
               "telephone_locataire = ?, date_naissance = ?, Lieu_de_naissance = ?, " +
               "Acte_de_caution = ?, Id_SAE_Adresse = ? " +
               "WHERE identifiant_locataire = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
        prSt.setString(1, donnee.getNom());
        prSt.setString(2, donnee.getPrenom());
        prSt.setString(3, donnee.getEmail());
        prSt.setString(4, donnee.getTelephone());
        prSt.setDate(5,   Date.valueOf(donnee.getDateNaissance())); 
        prSt.setString(6, donnee.getLieuDeNaissance());
        prSt.setString(7, donnee.getActeDeCaution());
        
        // Récupération de l'ID de l'adresse (si l'adresse est associée)
        if (donnee.getAdresse() != null) {
            prSt.setString(8, donnee.getAdresse().getIdAdresse());
        } else {
            prSt.setNull(8, java.sql.Types.VARCHAR); // Null si pas d'adresse
        }

        prSt.setString(9, donnee.getIdLocataire());
        
   
        
        

    }
}
