package modele.dao.requetes;

import modele.Assurance;

public class RequeteSelectAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "select * from SAE_Assurance";
	}
}