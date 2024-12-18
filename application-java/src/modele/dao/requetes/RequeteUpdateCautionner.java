package modele.dao.requetes;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Cautionner;

public class RequeteUpdateCautionner extends Requete<Cautionner>{

	@Override
	public String requete() {
		return "UPDATE SAE_Cautionner "
				+ "SET MONTANT = ? , FICHIER_CAUTION = ? "
				+ "WHERE ID_CAUTION = ? AND ID_BAIL = ? ";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setBigDecimal(1, new BigDecimal(id[0]));
		prSt.setString(2, id[1]);
		prSt.setInt(3, Integer.valueOf(id[2]));
		prSt.setString(4, id[3]);
	}
	
	@Override
	public void parametres(PreparedStatement prSt, Cautionner donnee) throws SQLException {
        prSt.setBigDecimal(1, donnee.getMontant());
		prSt.setString(2, donnee.getFichierCaution());
		prSt.setInt(3, donnee.getLastCautionnaire().getIdCautionnaire());
		prSt.setString(4, donnee.getBail().getIdBail());
	
	}
}
