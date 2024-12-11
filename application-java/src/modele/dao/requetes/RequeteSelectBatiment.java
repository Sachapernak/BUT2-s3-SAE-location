package modele.dao.requetes;

import modele.Batiment;

public class RequeteSelectBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "Select * from sae_batiment";
	}

}
