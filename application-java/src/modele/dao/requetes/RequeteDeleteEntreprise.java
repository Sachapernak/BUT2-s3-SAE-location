package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;

public class RequeteDeleteEntreprise extends Requete<Entreprise> {

	@Override
	public String requete() {
		return "Delete from sae_entreprise "
				+ "where siret = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Entreprise donnee) throws SQLException {
		prSt.setString(1, donnee.getSiret());
	}

}
