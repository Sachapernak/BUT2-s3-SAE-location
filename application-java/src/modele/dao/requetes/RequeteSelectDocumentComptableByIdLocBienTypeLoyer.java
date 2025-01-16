package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.DocumentComptable;


public class RequeteSelectDocumentComptableByIdLocBienTypeLoyer extends Requete<DocumentComptable> {


@Override
public String requete() {
    return """
        SELECT sdc.* 
        FROM SAE_DOCUMENT_COMPTABLE sdc, SAE_FACTURE_DU_BIEN sfb
        WHERE sdc.identifiant_locataire = ?
          AND sdc.numero_document = sfb.numero_document
          AND sdc.date_document = sfb.date_document
          AND sfb.identifiant_logement = ?
          AND sdc.type_de_document = 'loyer'
          AND to_char(sdc.date_document, 'MM-YYYY') = ?
    """;
}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
	}

}

