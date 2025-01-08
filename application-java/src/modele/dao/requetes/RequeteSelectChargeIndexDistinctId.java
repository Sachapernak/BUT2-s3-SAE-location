package modele.dao.requetes;

import modele.ChargeIndex;

public class RequeteSelectChargeIndexDistinctId extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "select distinct id_charge_index from sae_charge_index";
	}

}
