package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Loyer;

public class RequeteCreateLoyer extends Requete<Loyer> {

    @Override
    public String requete() {
        return "INSERT INTO SAE_LOYER (IDENTIFIANT_LOGEMENT, DATE_DE_CHANGEMENT, MONTANT_LOYER) "
        		+ "VALUES (?, ?, ?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, Loyer donnee) throws SQLException {
        prSt.setString(1, donnee.getIdBien());
        prSt.setDate(2, java.sql.Date.valueOf(donnee.getDateDeChangement()));
        prSt.setBigDecimal(3, donnee.getMontantLoyer());
    }
}
