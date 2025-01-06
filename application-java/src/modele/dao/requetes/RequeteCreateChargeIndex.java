package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


import modele.ChargeIndex;

public class RequeteCreateChargeIndex extends Requete<ChargeIndex> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_CHARGE_INDEX("
				+ "id_charge_index, "
				+ "Date_de_releve, "
				+ "Type, "
				+ "Valeur_compteur, "
				+ "Cout_variable_unitaire, "
				+ "Cout_fixe, "
				+ "numero_document, "
				+ "date_document, "
				+ "Date_releve_precedent, "
				+ "id_charge_index_preced) "
				+ ""
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, ChargeIndex donnee) throws SQLException {
		prSt.setString(1, donnee.getId());
		prSt.setDate(2, Date.valueOf(donnee.getDateDeReleve()));
		prSt.setString(3, donnee.getType());
		prSt.setBigDecimal(4, donnee.getValeurCompteur());
		prSt.setBigDecimal(5, donnee.getCoutVariable());
		prSt.setBigDecimal(6, donnee.getCoutFixe());
		
		prSt.setString(7, donnee.getNumDoc());
		prSt.setDate(8, Date.valueOf(donnee.getDateDoc()));
		
		if (donnee.getDateRelevePrecedent() == null || donnee.getDateRelevePrecedent().isEmpty()) {
			prSt.setNull(9, Types.DATE);
			prSt.setNull(10, Types.VARCHAR);
		} else {
			prSt.setDate(9,Date.valueOf(donnee.getDateRelevePrecedent()));
			// L'ID d'une charge précedente doit etre le meme que la charge n+1
			prSt.setString(10, donnee.getId());
		}
	}

}
