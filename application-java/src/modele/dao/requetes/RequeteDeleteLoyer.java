package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Loyer;

public class RequeteDeleteLoyer extends Requete<Loyer> {

    @Override
    public String requete() {
        return "DELETE FROM SAE_LOYER WHERE IDENTIFIANT_LOGEMENT = ? AND DATE_DE_CHANGEMENT = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Loyer donnee) throws SQLException {
        prSt.setString(1, donnee.getIdBien());
        prSt.setDate(2, java.sql.Date.valueOf(donnee.getDateDeChangement()));
    }
}