package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.DocumentComptable;

public class RequeteDeleteDocumentComptable extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		return "{ call pkg_document_comptable.supprimer_document_comptable(?, ?) }";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, DocumentComptable donnee) throws SQLException {
		prSt.setString(1, donnee.getNumeroDoc());
		prSt.setDate(2, Date.valueOf(donnee.getDateDoc()));
	}

}
