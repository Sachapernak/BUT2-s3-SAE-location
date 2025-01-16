package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteCreateFactureBien extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_FACTURE_DU_BIEN "
				+ "(IDENTIFIANT_LOGEMENT, NUMERO_DOCUMENT, "
				+ "DATE_DOCUMENT, PART_DES_CHARGES) "
				+ "VALUES (?, ?, to_date(?,'DD-MM-YYYY'), ?) ";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, FactureBien donnee) throws SQLException {
		prSt.setString(1, donnee.getBien().getIdentifiantLogement());
		prSt.setString(2, donnee.getDocument().getNumeroDoc());
		prSt.setString(3, donnee.getDocument().getDateDoc());
		prSt.setFloat(4, donnee.getPartDesCharges());
	}
	
}
