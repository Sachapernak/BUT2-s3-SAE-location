package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;

public class RequeteSelectCautionParLocBai extends Requete<Bail> {

	@Override
	public String requete() {
		return "Select * from sae_caution_bail_par_person "+
				"where idBail = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
