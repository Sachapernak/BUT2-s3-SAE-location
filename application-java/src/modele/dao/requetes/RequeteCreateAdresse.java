package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Adresse;

public class RequeteCreateAdresse extends Requete<Adresse> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, "
				+ " code_postal, ville, complement_adresse) "
				+ "VALUES (?, ?, ?, ?, ?)";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Adresse donnee) throws SQLException {
		prSt.setString(1, donnee.getIdAdresse());
		prSt.setString(2, donnee.getAdresse());
		prSt.setInt(3, donnee.getCodePostal());
		prSt.setString(4, donnee.getVille());
		prSt.setString(5, donnee.getComplementAdresse());
		
	}

}
