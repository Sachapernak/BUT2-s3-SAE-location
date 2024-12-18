package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionner;

public class RequeteDeleteCautionner extends Requete<Cautionner>{
	@Override
	public String requete() {
		return "Delete from SAE_Cautionner where ID_CAUTION = ? and ID_BAIL = ?";		
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionner donnee) throws SQLException {
		prSt.setInt(1, donnee.getLastCautionnaire().getIdCautionnaire());
		prSt.setString(2, donnee.getBail().getIdBail());
	}
}
