package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;
import modele.BienLocatif;


public class RequeteCountNbLogementsBatiment extends Requete<Batiment>{

	@Override
	public String requete() {
		return "select identifiant_batiment, count(*) from sae_bien_locatif where identifiant_batiment = ? group by identifiant_batiment";
	}
	

	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
	}

}
