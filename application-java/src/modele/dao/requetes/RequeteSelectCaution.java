package modele.dao.requetes;

import modele.Cautionner;

public class RequeteSelectCaution extends Requete<Cautionner>{

	@Override
	public String requete() {
		return "select * from sae_cautionner ";
	}
	
}
