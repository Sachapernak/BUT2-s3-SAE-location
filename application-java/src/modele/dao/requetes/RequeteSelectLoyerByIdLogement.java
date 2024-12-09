package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Loyer;

public class RequeteSelectLoyerByIdLogement extends Requete<Loyer> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_LOYER WHERE IDENTIFIANT_LOGEMENT = ?;";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}