package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteUpdateFactureBien extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "UPDATE SAE_FACTURE_DU_BIEN "
				+ "SET PART_DES_CHARGES = ? "
				+ "WHERE IDENTIFIANT_LOGEMENT = ? "
				+ "AND NUMERO_DOCUMENT = ? "
				+ "AND DATE_DOCUMENT = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, FactureBien donnee) throws SQLException {
		prSt.setFloat(1, donnee.getPartDesCharges());
		prSt.setString(2, donnee.getBien().getIdentifiantLogement());
		prSt.setString(3, donnee.getDocument().getNumeroDoc());
		prSt.setDate(4, Date.valueOf(donnee.getDocument().getDateDoc()));
	}

}
