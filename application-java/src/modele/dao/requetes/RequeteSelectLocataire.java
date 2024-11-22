package modele.dao.requetes;

import modele.Locataire;

public class RequeteSelectLocataire extends Requete<Locataire> {

	@Override
	public String requete() {
		return "select * from Locataire";
	}

}
