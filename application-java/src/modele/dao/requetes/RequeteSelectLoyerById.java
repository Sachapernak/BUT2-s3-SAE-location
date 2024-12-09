package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Loyer;

public class RequeteSelectLoyerById extends Requete<Loyer> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_LOYER WHERE IDENTIFIANT_LOGEMENT = ? AND DATE_DE_CHANGEMENT = ?;";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
        prSt.setDate(2, java.sql.Date.valueOf(id[1]));
    }
}