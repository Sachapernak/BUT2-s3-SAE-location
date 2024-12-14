package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteDeleteFactureBien extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "Delete from sae_facture_du_bien "
				+ "where identifiant_logement = ? "
				+ "and numero_document = ? "
				+ "and date_document = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, FactureBien donnee) throws SQLException {
		
		prSt.setString(1, donnee.getBien().getIdentifiantLogement());
		prSt.setString(2, donnee.getDocument().getNumeroDoc());
		prSt.setDate(3, Date.valueOf(donnee.getDocument().getDateDoc()));
	}

}
