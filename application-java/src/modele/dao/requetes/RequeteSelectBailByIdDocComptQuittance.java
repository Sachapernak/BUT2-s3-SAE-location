package modele.dao.requetes;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;


public class RequeteSelectBailByIdDocComptQuittance extends Requete<Bail> {


	@Override
	public String requete() {
	    return """
	        SELECT sb.* 
	        FROM sae_bien_locatif sbl, sae_locataire sl, sae_contracter sc, sae_bail sb, 
	             sae_facture_du_bien sfdb, sae_document_comptable sdc
	        WHERE sbl.identifiant_logement = sb.identifiant_logement
	          AND sb.id_bail = sc.id_bail
	          AND sc.identifiant_locataire = sl.identifiant_locataire
	          AND sbl.identifiant_logement = sfdb.identifiant_logement
	          AND sfdb.numero_document = sdc.numero_document
	          AND sfdb.date_document = sdc.date_document
	          AND sdc.identifiant_locataire = sl.identifiant_locataire
	          AND TO_CHAR(sdc.date_document, 'MM-YYYY') = TO_CHAR(TO_DATE(?, 'DD-MM-YYYY'), 'MM-YYYY')
	          AND sdc.numero_document = ?
	    """;
	}

	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

}
