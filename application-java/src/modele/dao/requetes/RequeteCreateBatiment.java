package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;

public class RequeteCreateBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "INSERT INTO sae_batiment(identifiant_batiment, "
				+ "id_sae_adresse) "
				+ "VALUES(?, ?)";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
		prSt.setString(2, donnee.getAdresse().getIdAdresse());
	}

}
