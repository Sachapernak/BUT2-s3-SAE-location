package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.Loyer;

public class RequeteSelectLoyerActuelById extends Requete<Loyer> {

	@Override
	public String requete() {
	    return """
	        SELECT sl.identifiant_logement, sl.date_de_changement, (sl.montant_loyer*sc.part_de_loyer) montant_loyer 
	        FROM SAE_BAIL sb, SAE_CONTRACTER sc, 
	             SAE_BIEN_LOCATIF sbl, SAE_LOCATAIRE sloc
	        WHERE sbl.identifiant_logement = sb.identifiant_logement
	         
	          AND sb.id_bail = sc.id_bail
	          AND sc.identifiant_locataire = sloc.identifiant_locataire
	          AND TO_CHAR(sl.date_de_changement, 'YYYY') = ?
	          AND sloc.identifiant_locataire = ?
	          AND sbl.identifiant_logement = ?
	        ORDER BY sl.date_de_changement
	    """;
	}


    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
        prSt.setString(2, id[1]);
        
    }
}