package modele.dao.requetes;

import java.sql.CallableStatement;
import java.sql.SQLException;


import modele.Bail;

public class RequeteSelectSommeProvBaiLoc extends Requete<Bail>{

	@Override
	public String requete() {
	    return "{call pkg_solde_compte.calculer_somme_provision(?, ?, ?, ?, ?, ?)}";
	}

	public void parametres(CallableStatement prSt, String... id) throws SQLException {
	    // Paramètre 1 : p_id_bail
	    prSt.setString(1, id[0]);
	    // Paramètre 2 : p_id_loc
	    prSt.setString(2, id[1]);

	    // Paramètre 3 : p_date_debut
	    if (id[2] != null && !id[2].isEmpty()) {
	        prSt.setDate(3, java.sql.Date.valueOf(id[2]));
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }

	    // Paramètre 4 : p_date_fin
	    if (id[3] != null && !id[3].isEmpty()) {
	        prSt.setDate(4, java.sql.Date.valueOf(id[3])); 
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
	    
	    // Paramètres 5 et 6 : paramètres OUT
	    prSt.registerOutParameter(5, java.sql.Types.DECIMAL);
	    prSt.registerOutParameter(6, java.sql.Types.VARCHAR);
	}

}
