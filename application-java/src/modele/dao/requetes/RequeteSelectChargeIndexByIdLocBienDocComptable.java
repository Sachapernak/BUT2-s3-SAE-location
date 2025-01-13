package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeIndex;

public class RequeteSelectChargeIndexByIdLocBienDocComptable extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "SELECT "
		           + "    sci.* "
		           + "FROM "
		           + "    SAE_DOCUMENT_COMPTABLE sc, "
		           + "    SAE_CHARGE_INDEX sci, "
		           + "    SAE_FACTURE_DU_BIEN sfdb "
		           + "WHERE "
		           + "    sc.NUMERO_DOCUMENT = sci.NUMERO_DOCUMENT "
		           + "    AND sc.NUMERO_DOCUMENT = sfdb.NUMERO_DOCUMENT "
		           + "    AND sc.DATE_DOCUMENT = sfdb.DATE_DOCUMENT "
		           + "    AND TO_CHAR(sc.DATE_DOCUMENT, 'MM-YYYY') = ? "
		           + "    AND sfdb.IDENTIFIANT_LOGEMENT = ? "
		           + "    AND sc.TYPE_DE_DOCUMENT LIKE '%charge%' "
		           + "    AND sc.IDENTIFIANT_LOCATAIRE = ?";
				
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[1]);
		prSt.setString(2, id[0]);
		prSt.setString(3, id[2]);
	}

}
