package modele.dao.requetes;

import modele.Regularisation;

public class RequeteSelectRegularisation extends Requete<Regularisation> {

	@Override
	public String requete() {
		return "select * from SAE_REGULARISATION";
	}

}
