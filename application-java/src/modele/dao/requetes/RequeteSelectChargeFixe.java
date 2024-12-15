package modele.dao.requetes;

import modele.ChargeFixe;

public class RequeteSelectChargeFixe extends Requete<ChargeFixe> {

	@Override
	public String requete() {
		return "Select * from sae_charge_cf";
	}

}
