package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeIndex;

public class RequeteDeleteChargeIndex extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "Delete from sae_charge_index "
				+ "where id_charge_index = ?"
				+ "and Date_de_releve = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeIndex donnee) throws SQLException {
		prSt.setString(1, donnee.getId());
		prSt.setDate(2, Date.valueOf( donnee.getDateDeReleve()));
	}

}
