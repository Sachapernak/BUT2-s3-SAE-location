package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptableByIdLog extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		return "select d.* from sae_document_comptable d, sae_facture_du_bien f "
				+ "where d.numero_document = f.numero_document "
				+ "and identifiant_logement = ? ";
				//+ "and d.type_de_document in ('facture_cf', 'facture_cv', 'facture')";

	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
