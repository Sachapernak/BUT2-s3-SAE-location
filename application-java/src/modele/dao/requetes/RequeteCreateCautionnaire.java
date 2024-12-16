package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Cautionnaire;

public class RequeteCreateCautionnaire extends Requete<Cautionnaire>{

	@Override
	public String requete() {
		return "INSERT INTO SAE_CAUTIONNAIRE(ID_CAUTION, NOM_OU_ORGANISME, PRENOM, DESCRIPTION_DU_CAUTIONNAIRE, ID_SAE_ADRESSE) "
				+ "VALUES (? , ? , ? , ? , ?)";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.valueOf(id[0]));
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
	
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionnaire donnee) throws SQLException {
        prSt.setInt(1, donnee.getIdCautionnaire());
		prSt.setString(2, donnee.getNomOuOrganisme());
		prSt.setString(3, donnee.getPrenom());
		prSt.setString(4, donnee.getDescription());
		prSt.setString(5, donnee.getAdresse().getIdAdresse());
	
	}

}
