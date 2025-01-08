package modele.dao.requetes;

import modele.ICC;

public class RequeteSelectICC extends Requete<ICC> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ICC "
				+ "order by annee_icc desc, trimestre_icc desc";
	}

}