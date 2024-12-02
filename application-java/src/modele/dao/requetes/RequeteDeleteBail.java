package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;

public class RequeteDeleteBail extends Requete<Bail>{
	
	@Override
	public String requete() {
		return "Delete from SAE_Bail where ID_BAIL = ? ";
				
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Bail donnee) throws SQLException {
	
		prSt.setString(1, donnee.getIdBail());
		
	}
}
