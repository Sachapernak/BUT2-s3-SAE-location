package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Loyer;

public class RequeteSelectLoyerByIdLocBienDocComptable extends Requete<Loyer> {

	@Override
	public String requete() {
		return "SELECT "
		           + "    sl.* "
		           + "FROM "
		           + "    SAE_LOYER sl, "
		           + "    SAE_BIEN_LOCATIF sbl, "
		           + "    SAE_FACTURE_DU_BIEN sfdb, "
		           + "    SAE_DOCUMENT_COMPTABLE sdc "
		           + "WHERE "
		           + "    sdc.NUMERO_DOCUMENT = sfdb.NUMERO_DOCUMENT "
		           + "    AND sdc.DATE_DOCUMENT = sfdb.DATE_DOCUMENT "
		           + "    AND sbl.IDENTIFIANT_LOGEMENT = sfdb.IDENTIFIANT_LOGEMENT "
		           + "    AND sl.IDENTIFIANT_LOGEMENT = sbl.IDENTIFIANT_LOGEMENT "
		           + "    AND sfdb.IDENTIFIANT_LOGEMENT = ? "
		           + "    AND sdc.TYPE_DE_DOCUMENT LIKE '%charge%' "
		           + "    AND sdc.IDENTIFIANT_LOCATAIRE = ? "
		           + "ORDER BY "
		           + "    sl.DATE_DE_CHANGEMENT DESC";
				
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

}
