package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ICC;

public class RequeteCreateICC extends Requete<ICC> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_ICC (IDENTIFIANT_LOGEMENT, ANNEE_ICC,"
				+ " TRIMESTRE_ICC, INDICE) "
				+ "VALUES(?, ?, ?, ?);";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, ICC donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getAnnee());
		prSt.setString(3, donnee.getTrimestre());
		prSt.setInt(4, donnee.getIndiceICC());

		
	}

}