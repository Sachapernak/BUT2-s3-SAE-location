package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionnaire;

public class RequeteDeleteCautionnaire extends Requete<Cautionnaire>{
	@Override
	public String requete() {
		return "Delete from SAE_Cautionnaire where ID_CAUTION = ? ";		
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionnaire donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdCautionnaire());
		
	}
}
