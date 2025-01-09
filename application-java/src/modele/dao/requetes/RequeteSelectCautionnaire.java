package modele.dao.requetes;

import modele.Cautionnaire;

public class RequeteSelectCautionnaire extends Requete<Cautionnaire>{

	@Override
	public String requete() {
		return "select * from sae_cautionnaire";
	}
	
}
