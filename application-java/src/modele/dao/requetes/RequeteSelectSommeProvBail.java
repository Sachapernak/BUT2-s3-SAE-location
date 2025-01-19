package modele.dao.requetes;

import java.sql.CallableStatement;
import java.sql.SQLException;


import modele.Bail;

public class RequeteSelectSommeProvBail extends Requete<Bail>{

	@Override
	public String requete() {
	    return "{call pkg_regularisation_charge.calculer_somme_provision_bail(?, ?, ?, ?, ?)}";
	}

	public void parametres(CallableStatement prSt, String... id) throws SQLException {
		// Paramètre 1 : p_id_bail
	    prSt.setString(1, id[0]);

	    // Paramètre 2 : p_date_debut
	    if (id[1] != null && !id[1].isEmpty()) {
	        prSt.setDate(2, java.sql.Date.valueOf(id[1]));
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }

	    // Paramètre 3 : p_date_fin
	    if (id[2] != null && !id[2].isEmpty()) {
	        prSt.setDate(3, java.sql.Date.valueOf(id[2])); 
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    
	    // Paramètres 4 et 5 : paramètres OUT
	    prSt.registerOutParameter(4, java.sql.Types.DECIMAL);
	    prSt.registerOutParameter(5, java.sql.Types.VARCHAR);
	}

}
