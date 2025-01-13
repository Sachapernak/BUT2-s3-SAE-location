package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.ProvisionCharge;

public class RequeteSelectPPCByID extends Requete<ProvisionCharge> {

	@Override
	public String requete() {
	    return """
	        SELECT spc.id_bail, spc.date_changement, (spc.PROVISION_POUR_CHARGE * sc.part_de_loyer) AS PROVISION_POUR_CHARGE 
	        FROM SAE_PROVISION_CHARGE spc, SAE_BAIL sb, SAE_CONTRACTER sc, 
	             SAE_BIEN_LOCATIF sbl, SAE_LOCATAIRE sloc
	        WHERE sbl.identifiant_logement = sb.identifiant_logement
	          AND sb.id_bail = spc.id_bail
	          AND sb.id_bail = sc.id_bail
	          AND sc.identifiant_locataire = sloc.identifiant_locataire
	          AND TO_CHAR(spc.DATE_CHANGEMENT, 'MM-YYYY') = ?
	          AND sloc.identifiant_locataire = ?
	          AND sbl.identifiant_logement = ?
	    """;
	}

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
        prSt.setString(2, id[1]);
        prSt.setString(3, id[2]);
        
    }
}