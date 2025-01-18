package modele.dao.requetes;

import modele.DocumentComptable;

public class RequeteSelectDocumentComptable extends Requete<DocumentComptable> {

	@Override
	public String requete() {
		return "select * from sae_document_comptable where type_de_document != 'loyer'";
	}

}
