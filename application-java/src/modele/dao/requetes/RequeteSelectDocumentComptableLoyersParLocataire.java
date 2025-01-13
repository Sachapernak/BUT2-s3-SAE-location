package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptableLoyersParLocataire extends Requete<DocumentComptable> {


	
	@Override
	public String requete() {
		
		return "select * from sae_document_comptable "
				+ "where IDENTIFIANT_LOCATAIRE = ? "
				+ "and TYPE_DE_DOCUMENT = 'loyer'";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
