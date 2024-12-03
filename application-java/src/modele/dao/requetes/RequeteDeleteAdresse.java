package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Adresse;

public class RequeteDeleteAdresse extends Requete<Adresse> {

	@Override
	public String requete() {
		return "Delete from SAE_Adresse "
				+ "where id_sae_adresse = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Adresse donnee) throws SQLException {
		prSt.setString(1, donnee.getIdAdresse());
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}