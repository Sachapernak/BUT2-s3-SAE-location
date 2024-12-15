package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeFixe;

public class RequeteDeleteChargeFixe extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "Delete from sae_charge_cf "
				+ "where id_charge_cf = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeFixe donnee) throws SQLException {
		prSt.setString(1, donnee.getId());
	}

}
