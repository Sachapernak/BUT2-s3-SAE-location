package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.FactureBien;

public class RequeteSelectContracterByIdLogement extends Requete<FactureBien> {

	@Override
	public String requete() {
		return "select c.identifiant_locataire, c.id_bail, c.date_de_sortie, c.date_d_entree, c.part_de_loyer "
				+ "from sae_contracter c, sae_bail b "
				+ "where c.id_bail = b.id_bail "
				+ "and b.identifiant_logement = ?";
	}

	
	@Override
	public void parametres(PreparedStatement prSt, String...id) throws SQLException {
		prSt.setString(1, id[0]);
	}
	
}
