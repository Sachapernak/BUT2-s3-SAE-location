package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;

public class RequeteCreateEntreprise extends Requete<Entreprise> {

	@Override
	public String requete() {
		return "INSERT INTO sae_entreprise("
				+ "siret, secteur_d_activite,"
				+ "nom, id_sae_adresse) "
				+ "VALUES(?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise donnee) throws SQLException {
		prSt.setString(1, donnee.getSiret());
		prSt.setString(2, donnee.getSecteur());
		prSt.setString(3, donnee.getNom());
		prSt.setString(4, donnee.getAdresse().getIdAdresse());
	}
}
