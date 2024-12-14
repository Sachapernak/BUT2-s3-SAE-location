package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeFixe;

public class RequeteCreateChargeFixe extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "INSERT INTO sae_charge_cf("
				+ "id_charge_cf, "
				+ "Date_de_charge, "
				+ "Type, "
				+ "montant,"
				+ "numero_document, "
				+ "date_document)"
				+ "VALUES(?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeFixe donnee) throws SQLException {
		prSt.setString(1, donnee.getId());
		prSt.setDate(2, Date.valueOf(donnee.getDateDeCharge()));
		prSt.setString(3, donnee.getType());
		prSt.setBigDecimal(4, donnee.getMontant());
		prSt.setString(5, donnee.getNumDoc());
		prSt.setDate(6, Date.valueOf(donnee.getDateDoc()));
	}

}
