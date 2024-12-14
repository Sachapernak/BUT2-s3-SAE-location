package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeFixe;

public class RequeteSelectChargeFixeById extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "select * from sae_charge_cf where "
				+ "id_charge_fixe = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
