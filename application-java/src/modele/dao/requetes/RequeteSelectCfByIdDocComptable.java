package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeFixe;

public class RequeteSelectCfByIdDocComptable extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "Select * from sae_charge_cf "
				+ "where numero_document = ? "
				+ "and Date_document = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
	}

}
