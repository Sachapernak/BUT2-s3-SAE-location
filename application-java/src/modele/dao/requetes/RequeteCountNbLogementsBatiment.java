package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;


public class RequeteCountNbLogementsBatiment extends Requete<Batiment>{

	@Override
	public String requete() {
		return "SELECT id_sae_adresse, COUNT(*) FROM sae_batiment WHERE identifiant_batiment = ? and id_sae_adresse = ? GROUP BY id_sae_adresse";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
		prSt.setString(2, donnee.getAdresse().getIdAdresse());
	}

}
