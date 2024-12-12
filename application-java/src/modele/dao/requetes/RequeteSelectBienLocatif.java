package modele.dao.requetes;

import modele.BienLocatif;

public class RequeteSelectBienLocatif extends Requete<BienLocatif> {

	@Override
	public String requete() {
		return "Select * from sae_bien_locatif";
	}

}
