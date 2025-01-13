package modele.dao.requetes;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptableAllLoyers extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		
		return "select * from sae_document_comptable "
				+ "where TYPE_DE_DOCUMENT = 'loyer'";
	}

}
