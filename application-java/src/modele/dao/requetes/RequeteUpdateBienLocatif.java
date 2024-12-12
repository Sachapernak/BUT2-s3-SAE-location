package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLocatif;

public class RequeteUpdateBienLocatif extends Requete<BienLocatif> {

	@Override
	public String requete() {
	    return "UPDATE SAE_BIEN_LOCATIF " +
	           "SET LOYER_DE_BASE = ?, " +
	           "    IDENTIFIANT_FISCAL = ?, " +
	           "    COMPLEMENT_D_ADRESSE = ?, " +
	           "    SURFACE = ?, " +
	           "    NB_PIECE = ?, " +
	           "    TYPE_DE_BIEN = ?, " +
	           "    IDENTIFIANT_BATIMENT = ? " +
	           "WHERE IDENTIFIANT_LOGEMENT = ?";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, BienLocatif donnee) throws SQLException {
	    prSt.setBigDecimal(1, donnee.getLoyerBase()); // Correspond à LOYER_DE_BASE
	    prSt.setString(2, donnee.getIdFiscal()); // Correspond à IDENTIFIANT_FISCAL
	    prSt.setString(3, donnee.getComplementAdresse()); // Correspond à COMPLEMENT_D_ADRESSE
	    prSt.setDouble(4, donnee.getSurface()); // Correspond à SURFACE
	    prSt.setInt(5, donnee.getNbPiece()); // Correspond à NB_PIECE
	    prSt.setString(6, donnee.getType().getValeur()); // Correspond à TYPE_DE_BIEN
	    prSt.setString(7, donnee.getBat().getIdBat()); // Correspond à IDENTIFIANT_BATIMENT
	    prSt.setString(8, donnee.getIdentifiantLogement()); // Correspond à la condition WHERE (IDENTIFIANT_LOGEMENT)
	}

}
