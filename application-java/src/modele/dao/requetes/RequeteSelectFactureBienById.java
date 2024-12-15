package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteSelectFactureBienById extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "select * from sae_facture_du_bien "
				+ "where identifiant_logement = ? "
				+ "and numero_document = ? "
				+ "and date_document = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setDate(3, Date.valueOf(id[2]));
	}

}
