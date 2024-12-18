package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;

public class RequeteUpdateEntreprise extends Requete<Entreprise> {

	@Override
	public String requete() {
		return "UPDATE sae_entreprise SET "
				+ "secteur_d_activite = ?, "
				+ "nom = ?,"
				+ "id_sae_adresse = ? "
				+ "where siret = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise donnee) throws SQLException {
		prSt.setString(1, donnee.getSecteur());
		prSt.setString(2, donnee.getNom());
		prSt.setString(3, donnee.getAdresse().getIdAdresse());
		prSt.setString(4, donnee.getSiret());
	}
}
