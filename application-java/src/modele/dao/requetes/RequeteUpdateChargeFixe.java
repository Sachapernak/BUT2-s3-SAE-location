package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeFixe;

public class RequeteUpdateChargeFixe extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "UPDATE SAE_CHARGE_CF "
				+ "SET date_de_charge = ?, "
				+ "Type = ?, "
				+ "montant = ?"
				+ "WHERE id_charge_cf = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeFixe donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateDeCharge()));
		prSt.setString(2, donnee.getType());
		prSt.setBigDecimal(3, donnee.getMontant());
		prSt.setString(4, donnee.getId());
	}
}
