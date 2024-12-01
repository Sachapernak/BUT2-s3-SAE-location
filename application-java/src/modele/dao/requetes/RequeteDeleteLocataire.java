package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;

public class RequeteDeleteLocataire extends Requete<Locataire> {

	@Override
	public String requete() {
		return "{call pkg_locataire.supprimer_locataire(?)}";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
	}

}
