package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ICC;

public class RequeteDeleteICC extends Requete<ICC> {

	@Override
	public String requete() {
		return "DELETE FROM SAE_ICC WHERE "
				+ "IDENTIFIANT_LOGEMENT = ? AND "
				+ "ANNEE_ICC = ? AND TRIMESTRE_ICC = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, ICC donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getAnnee());
		prSt.setString(3, donnee.getTrimestre());
		
	}

}