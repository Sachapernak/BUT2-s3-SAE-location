package modele.dao.requetes;

import modele.Entreprise;

public class RequeteSelectEntreprise extends Requete<Entreprise> {

	@Override
	public String requete() {
		return "select * from sae_entreprise";
	}

}
