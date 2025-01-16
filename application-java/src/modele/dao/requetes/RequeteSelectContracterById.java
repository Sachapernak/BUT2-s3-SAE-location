package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Contracter;

public class RequeteSelectContracterById extends Requete<Contracter> {

	@Override
	public String requete() {
	    return """
	        select * from sae_contracter sc
	        where sc.identifiant_locataire = ? 
	        and sc.id_bail = ? 
	    """;
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

}
