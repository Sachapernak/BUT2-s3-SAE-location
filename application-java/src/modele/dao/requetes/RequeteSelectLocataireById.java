package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;

public class RequeteSelectLocataireById extends Requete<Locataire> {

	@Override
	public String requete() {
		return "select * from SAE_Locataire "
				+ "where identifiant_locataire = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
