package modele.dao.requetes;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ProvisionCharge;

public class RequeteSelectProvisionChargeById extends Requete<ProvisionCharge> {

	@Override
	public String requete() {
		return "select * from SAE_PROVISION_CHARGE "
				+ "where ID_Bail = ? "
				+ "AND to_char(DATE_CHANGEMENT, 'DD-MM-YYYY') = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}
}
