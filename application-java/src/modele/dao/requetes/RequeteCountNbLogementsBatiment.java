package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.BienLocatif;


public class RequeteCountNbLogementsBatiment extends Requete<BienLocatif>{

	@Override
	public String requete() {
		return "select identifiant_batiment, count(*) from sae_bien_locatif where identifiant_batiment = ? group by identifiant_batiment";
	}
	

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
