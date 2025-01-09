package modele.dao.requetes;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionner;

public class RequeteCreateCautionner extends Requete<Cautionner>{

	@Override
	public String requete() {
		return "INSERT INTO SAE_CAUTIONNER(ID_CAUTION, ID_BAIL, MONTANT, FICHIER_CAUTION) "
				+ "VALUES (? , ? , ? , ?)";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.valueOf(id[4]));
		prSt.setString(2, id[3]);
		prSt.setBigDecimal(3, new BigDecimal(id[0]));
		prSt.setString(4, id[1]);	
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionner donnee) throws SQLException {
        prSt.setInt(1, donnee.getLastCautionnaire().getIdCautionnaire());
		prSt.setString(2, donnee.getBail().getIdBail());
		prSt.setBigDecimal(3, donnee.getMontant());
		prSt.setString(4, donnee.getFichierCaution());
	
	}

}
