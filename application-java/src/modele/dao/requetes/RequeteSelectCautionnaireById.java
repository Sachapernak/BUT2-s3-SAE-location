package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionnaire;

public class RequeteSelectCautionnaireById extends Requete<Cautionnaire>{
	
	@Override
	public String requete() {
		return "select * from SAE_Cautionnaire where id_caution = ? ";
	}
	
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1,id[0]);
	}
	
	public void parametres(PreparedStatement prSt, Cautionnaire donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdCautionnaire());
	}
	
}
