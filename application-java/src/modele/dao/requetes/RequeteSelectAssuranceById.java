package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;

public class RequeteSelectAssuranceById extends Requete<Assurance> {

	@Override
	public String requete() {
		return "select * from SAE_Assurance "
				+ "where numero_de_Contrat = ?"
				+ "and annee_du_Contrat = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt,Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getNumeroContrat());
		prSt.setInt(2, donnee.getAnneeContrat());
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}
}