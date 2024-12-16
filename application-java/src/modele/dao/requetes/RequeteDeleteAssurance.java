package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;

public class RequeteDeleteAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "Delete from SAE_Assurance "
				+ "where numeroContrat = ?, anneeContrat = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt,Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getTypeContrat());
		prSt.setString(2, donnee.getNumeroContrat());
		prSt.setInt(3, donnee.getAnneeContrat());
		
		
	}

}