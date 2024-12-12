package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLocatif;

public class RequeteDeleteBienLocatif extends Requete<BienLocatif> {

	@Override
	public String requete() {
		return "{call pkg_bien_locatif.supprimer_bien_locatif(?)}";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, BienLocatif donnee) throws SQLException {
		prSt.setString(1, donnee.getIdentifiantLogement());
	}

}
