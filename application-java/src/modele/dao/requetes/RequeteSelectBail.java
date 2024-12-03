package modele.dao.requetes;

import modele.Bail;

public class RequeteSelectBail extends Requete<Bail> {

	@Override
	public String requete() {
		return "select * from sae_bail";
	}

}
