package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;

public class RequeteDeleteBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "Delete from sae_batiment where identifiant_batiment = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
	}

}
