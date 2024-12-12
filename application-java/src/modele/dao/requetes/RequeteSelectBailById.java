package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;

public class RequeteSelectBailById extends Requete<Bail>{

	@Override
	public String requete() {
		return "select * from SAE_Bail where id_bail = ? ";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1,id[0]);
	}
	@Override
	
	public void parametres(PreparedStatement prSt, Bail donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBail());
	}
	
	

}
