package modele.dao.requetes;

import modele.Document;

public class RequeteSelectDocument extends Requete<Document> {

	@Override
	public String requete() {
		return "select * from SAE_DOCUMENT";
	}

}
