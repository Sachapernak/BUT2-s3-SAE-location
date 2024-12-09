package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Loyer;

public class RequeteUpdateLoyer extends Requete<Loyer> {

    @Override
    public String requete() {
        return "UPDATE SAE_LOYER SET MONTANT_LOYER = ? WHERE IDENTIFIANT_LOGEMENT = ? AND DATE_DE_CHANGEMENT = ?;";
    }

    @Override
    public void parametres(PreparedStatement prSt, Loyer donnee) throws SQLException {
        prSt.setDouble(1, donnee.getMontantLoyer());
        prSt.setString(2, donnee.getIdBien());
        prSt.setDate(3, java.sql.Date.valueOf(donnee.getDateDeChangement()));
    }
}