package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;

public class RequeteSelectAllCIBail extends Requete<Bail> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_cv_par_bail " +
	             "WHERE idBai = ? " +
	             "  AND dateDoc >= NVL(?, TO_DATE('1900-01-01', 'YYYY-MM-DD')) " +
	             "  AND dateDoc <= NVL(?, SYSDATE)";
	}
	

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	   
		prSt.setString(1, id[0]);

	    if (!id[1].isEmpty()) {
	        prSt.setDate(2, java.sql.Date.valueOf(id[1]));
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }

	    if (!id[3].isEmpty()) {
	        prSt.setDate(3, java.sql.Date.valueOf(id[2])); 
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	}
	
	

}
