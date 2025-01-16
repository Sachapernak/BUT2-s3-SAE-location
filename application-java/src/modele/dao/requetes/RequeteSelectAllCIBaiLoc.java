package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Bail;

public class RequeteSelectAllCIBaiLoc extends Requete<Bail> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_cv_par_loc " +
	             "WHERE idBai = ? " +
	             "  AND idLoc = ? " +
	             "  AND dateDoc >= NVL(?, TO_DATE('1900-01-01', 'YYYY-MM-DD')) " +
	             "  AND dateDoc <= NVL(?, SYSDATE)";
	}
	

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	   
		prSt.setString(1, id[0]);
	    prSt.setString(2, id[1]);

	    if (!id[2].isEmpty()) {
	        prSt.setDate(3, java.sql.Date.valueOf(id[2]));
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }

	    if (!id[3].isEmpty()) {
	        prSt.setDate(4, java.sql.Date.valueOf(id[3])); 
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
	}
	
	

}
