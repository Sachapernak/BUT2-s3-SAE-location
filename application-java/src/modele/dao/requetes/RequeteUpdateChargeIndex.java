package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ChargeIndex;

public class RequeteUpdateChargeIndex extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "Update sae_charge_index SET "
				+ "type = ?, "
				+ "valeur_compteur = ?,"
				+ "cout_variable_unitaire = ?, "
				+ "cout_fixe = ? "
				+ "where id_charge_index = ? "
				+ "and Date_de_releve = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeIndex donnee) throws SQLException {
		prSt.setString(1, donnee.getType());
		prSt.setBigDecimal(2, donnee.getValeurCompteur());
		prSt.setBigDecimal(3, donnee.getCoutVariable());
		prSt.setBigDecimal(4, donnee.getCoutFixe());
		
		prSt.setString(5, donnee.getId());
		prSt.setDate(6, Date.valueOf(donnee.getDateDeReleve()));
	}

}
