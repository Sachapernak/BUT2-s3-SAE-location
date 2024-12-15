package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.DocumentComptable;

public class RequeteUpdateDocumentComptable extends Requete<DocumentComptable> {

    @Override
    public String requete() {
        return "UPDATE SAE_document_comptable "
        		+ "SET montant = ?, "
        		+ "fichier_document = ?, "
        		+ "montant_devis = ?, "
        		+ "recuperable_locataire = ? "
        		+ "WHERE numero_document = ? "
        		+ "AND date_document = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, DocumentComptable donnee) throws SQLException {
        prSt.setBigDecimal(1, donnee.getMontant());
        prSt.setString(2, donnee.getFichierDoc());
        prSt.setBigDecimal(3, donnee.getMontantDevis());
        prSt.setBoolean(4, donnee.isRecuperableLoc());
        prSt.setString(5, donnee.getNumeroDoc());
        prSt.setDate(6, Date.valueOf(donnee.getDateDoc()));
    }
}
