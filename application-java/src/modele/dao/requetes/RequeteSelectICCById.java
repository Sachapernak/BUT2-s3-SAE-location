package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ICC;

public class RequeteSelectICCById extends Requete<ICC> {
	
	@Override
	public String requete() {
		return "SELECT * FROM SAE_ICC WHERE "
				+ "IDENTIFIANT_LOGEMENT = ? AND "
				+ "ANNEE_ICC = ? AND TRIMESTRE_ICC = ?";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, ICC donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getAnnee());
		prSt.setString(3, donnee.getTrimestre());
		
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1,id[0]);
		prSt.setString(2,id[1]);
		prSt.setString(3,id[2]);
	}
	
	


}