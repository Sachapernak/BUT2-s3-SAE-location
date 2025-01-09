package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteSelectFactureBienByIdDoc extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "select * from sae_facture_du_bien "
				+ "where numero_document = ? "
				+ "and date_document = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
	}

}
