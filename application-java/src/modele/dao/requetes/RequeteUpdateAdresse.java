package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Adresse;

public class RequeteUpdateAdresse extends Requete<Adresse> {

    @Override
    public String requete() {
        return "UPDATE SAE_Adresse SET adresse = ?, Code_postal = ?, ville = ?, Complement_adresse = ? " +
               "WHERE Id_SAE_Adresse = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Adresse donnee) throws SQLException {
        prSt.setString(1, donnee.getAdressePostale());
        prSt.setInt(2, donnee.getCodePostal());
        prSt.setString(3, donnee.getVille());
        prSt.setString(4, donnee.getComplementAdresse());
        prSt.setString(5, donnee.getIdAdresse());
    }
}
