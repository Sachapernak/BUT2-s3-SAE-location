package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Cautionner;

public class RequeteSelectCautionById extends Requete<Cautionner> {

	@Override
	public String requete() {
		return "select * from sae_cautionner "
				+ "where id_caution = ? and id_bail = ?";
	}


	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setInt(1, Integer.parseInt(id[0]));
		prSt.setString(2, id[1]);
	}
	
}