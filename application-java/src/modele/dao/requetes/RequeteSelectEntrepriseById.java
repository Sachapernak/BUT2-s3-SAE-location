package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;

public class RequeteSelectEntrepriseById extends Requete<Entreprise> {

	@Override
	public String requete() {
		return "Select * from sae_entreprise "
				+ "where siret = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
