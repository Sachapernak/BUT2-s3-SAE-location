package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeIndex;

public class RequeteSelectChargeIndexSameId extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "Select * from sae_charge_index "
				+ "where id_charge_index = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
