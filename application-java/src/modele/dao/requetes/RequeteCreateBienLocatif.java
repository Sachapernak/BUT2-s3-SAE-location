package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLocatif;

public class RequeteCreateBienLocatif extends Requete<BienLocatif> {

    @Override
    public String requete() {
        return "INSERT INTO SAE_Bien_locatif (identifiant_logement, loyer_de_base, " +
               "identifiant_fiscal, complement_d_adresse, surface, nb_piece, type_de_bien, " +
               "identifiant_batiment) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLocatif donnee) throws SQLException {
        prSt.setString(1, donnee.getIdentifiantLogement()); // Identifiant du logement
        prSt.setBigDecimal(2, donnee.getLoyerBase());            // Loyer de base
        prSt.setString(3, donnee.getIdFiscal());            // Identifiant fiscal
        prSt.setString(4, donnee.getComplementAdresse());   // Complément d'adresse
        prSt.setInt(5, donnee.getSurface());                // Surface
        prSt.setInt(6, donnee.getNbPiece());                // Nombre de pièces
        prSt.setString(7, donnee.getType().getValeur());    // Type de bien (enum valeur)
        prSt.setString(8, donnee.getBat().getIdBat());      // Identifiant du bâtiment
    }
}