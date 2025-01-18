package modele.dao.requetes;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ProvisionCharge;

public class RequeteSelectProvisionChargeById extends Requete<ProvisionCharge> {

	@Override
	public String requete() {
		return "select * from SAE_PROVISION_CHARGE "
				+ "where ID_Bail = ? "
				+ "AND DATE_CHANGEMENT = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
	}
}
