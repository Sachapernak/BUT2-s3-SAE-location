package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;

public class RequeteSelectLocataireByNomPrenom extends Requete<Locataire> {

    @Override
    public String requete() {
        return "SELECT * FROM sae_locataire " +
               "WHERE (lower(nom_locataire) LIKE lower(?) OR lower(prenom_locataire) LIKE lower(?) )";
    }
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        String searchParam = "%" + id[0] + "%";
		prSt.setString(1, searchParam);
		prSt.setString(2, searchParam);
	}
}
