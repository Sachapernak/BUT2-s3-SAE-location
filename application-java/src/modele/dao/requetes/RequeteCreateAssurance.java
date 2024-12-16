package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;

public class RequeteCreateAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_Assurance (numero_de_Contrat, annee_du_Contrat, type_de_Contrat) "
				+ "VALUES (?, ?, ?)";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt,Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getNumeroContrat());
		prSt.setInt(2, donnee.getAnneeContrat());
		prSt.setString(3, donnee.getTypeContrat());
		
	}

}