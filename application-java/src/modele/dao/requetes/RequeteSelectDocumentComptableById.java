package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptableById extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		return "Select * from sae_document_comptable "
				+ "where numero_document = ? "
				+ "and date_document = ?";

	}
	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
	}

}
