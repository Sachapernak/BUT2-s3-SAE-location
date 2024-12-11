package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ICC;

public class RequeteUpdateICC extends Requete<ICC> {

    @Override
    public String requete() {
        return "UPDATE SAE_ICC SET INDICE = ? "
               + "ANNEE_ICC = ? AND TRIMESTRE_ICC = ?;";
    }

    @Override
    public void parametres(PreparedStatement prSt, ICC donnee) throws SQLException {
    	prSt.setInt(1, donnee.getIndiceICC());
		prSt.setString(2, donnee.getAnnee());
		prSt.setString(3, donnee.getTrimestre());
		
    }
}
