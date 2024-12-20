package modele.dao.requetes;

import modele.ChargeIndex;

public class RequeteSelectChargeIndex extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "select * from sae_charge_index";
	}

}
