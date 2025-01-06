package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;

public class RequeteUpdateAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "UPDATE SAE_Assurance SET type_de_Contrat = ? "
				+ "WHERE numero_de_Contrat = ? and annee_du_Contrat = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt,Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getTypeContrat());
		prSt.setString(2, donnee.getNumeroContrat());
		prSt.setInt(3, donnee.getAnneeContrat());
		
		
	}

}