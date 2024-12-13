package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteSelectFactureBienByIdBien extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "select * from sae_facture_du_bien "
				+ "where identifiant_logement = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
