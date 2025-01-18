package modele.dao.requetes;


import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Bail;


public class RequeteFindNbBailActif extends Requete<Bail> {

	@Override
	public String requete() {
		return "{? = call pkg_Bail.nombre_bail_actif(?)}";
	}
	
	public void parametres(CallableStatement prSt, String... id) throws SQLException {
		
		prSt.registerOutParameter(1, java.sql.Types.INTEGER);
		prSt.setString(2, id[0]);
	}


	
	

}
