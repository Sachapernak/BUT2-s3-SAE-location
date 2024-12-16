package modele.dao.requetes;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionnaire;

public class RequeteUpdateCautionnaire extends Requete<Cautionnaire>{

	@Override
	public String requete() {
		return "UPDATE SAE_Cautionnaire "
				+ "SET NOM_OU_ORGANISME = ? , PRENOM = ? , "
				+ "DESCRIPTION_DU_CAUTIONNAIRE = ? , ID_SAE_ADRESSE = ? "
				+ "WHERE ID_CAUTION = ? ";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionnaire donnee) throws SQLException {
        prSt.setString(1, donnee.getNomOuOrganisme());
        prSt.setString(2, donnee.getPrenom());
        prSt.setString(3, donnee.getDescription());
        prSt.setString(4, donnee.getAdresse().getIdAdresse());
        prSt.setInt(5, donnee.getIdCautionnaire());
		
	}
}
