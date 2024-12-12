package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;

public class RequeteSelectBatimentById extends Requete<Batiment> {

	@Override
	public String requete() {
		return "select * from sae_batiment "
				+ "where identifiant_batiment = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
