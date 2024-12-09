package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ICC;

public class RequeteSelectICCByIdLogement extends Requete<ICC> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ICC WHERE "
				+ "IDENTIFIANT_LOGEMENT = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1,id[0]);
	}
	
	


}