package modele.dao.requetes;

import modele.Bail;

public class RequeteSelectBail extends Requete<Bail> {

	@Override
	public String requete() {
		// TODO Auto-generated method stub
		return "select * from bail";
	}

}
