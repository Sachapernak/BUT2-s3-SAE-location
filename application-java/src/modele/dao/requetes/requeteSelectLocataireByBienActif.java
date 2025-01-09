package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;

public class requeteSelectLocataireByBienActif extends Requete<Locataire> {

	@Override
	public String requete() {

		return 	"""
				select distinct l.* from sae_locataire l, sae_contracter ct, sae_bail b
				where b.identifiant_logement = ?
				and ct.ID_BAIL = b.ID_Bail
				and l.identifiant_locataire = ct.identifiant_locataire
				""";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		prSt.setString(1, id[0]);
	}

}
