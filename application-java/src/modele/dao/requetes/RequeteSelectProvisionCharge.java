package modele.dao.requetes;

import modele.ProvisionCharge;

public class RequeteSelectProvisionCharge extends Requete<ProvisionCharge> {

	@Override
	public String requete() {
		return "select * from SAE_PROVISION_CHARGE";
	}

}
