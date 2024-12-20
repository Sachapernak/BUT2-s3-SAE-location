package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeIndex;

public class RequeteSelectChargeIndexById extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "select * from sae_charge_index "
				+ "where id_charge_index = ? "
				+ "and Date_de_releve = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
	}

}
