package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Cautionner;

public class RequeteSelectCautionByIdBail extends Requete<Cautionner> {

	@Override
	public String requete() {
		return "select * from sae_caution "
				+ "where id_bail = ?";
	}


	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
	
}