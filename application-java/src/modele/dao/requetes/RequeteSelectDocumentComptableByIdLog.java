package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptableByIdLog extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		return "select distinct d.* from sae_document_comptable d, sae_facture_du_bien f "
				+ "where d.numero_document = f.numero_document "
				+ "and identifiant_logement = ? "
				+ "and Type_de_document!= 'loyer'";


	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
