package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLocatif;

public class RequeteSelectBienLocatifByIdBat extends Requete<BienLocatif> {

	@Override
	public String requete() {
		return "select * from sae_bien_locatif "
				+ "where identifiant_batiment = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
