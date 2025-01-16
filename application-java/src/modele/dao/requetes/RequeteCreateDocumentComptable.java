package modele.dao.requetes;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modele.DocumentComptable;

public class RequeteCreateDocumentComptable extends Requete<DocumentComptable> {

    @Override
    public String requete() {
        return "INSERT INTO SAE_document_comptable (numero_document, date_document, "
        		+ "type_de_document, montant, fichier_document, recuperable_locataire, "
        		+ "identifiant_locataire, identifiant_batiment, siret, numero_de_contrat, "
        		+ "annee_du_contrat) " 
        		+ "VALUES (?, to_date(?,'DD-MM-YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, DocumentComptable document) throws SQLException {
        prSt.setString(1, document.getNumeroDoc());
        prSt.setString(2, document.getDateDoc());
        String type = document.getTypeDoc().name().toLowerCase();
        prSt.setString(3, type); // Utilisation de l'enum TypeDoc
        prSt.setBigDecimal(4, document.getMontant());
        prSt.setString(5, document.getFichierDoc());
        prSt.setBoolean(6, document.isRecuperableLoc());
        
        try {
        // Gestion des associations (null si absent)
        if (document.getLocataire() != null) {
            prSt.setString(7, document.getLocataire().getIdLocataire());
        } else {
            prSt.setNull(7, java.sql.Types.VARCHAR);
        }

        if (document.getBatiment() != null) {
            prSt.setString(8, document.getBatiment().getIdBat());
        } else {
            prSt.setNull(8, java.sql.Types.VARCHAR);
        }

        if (!type.equals("quittance") && document.getEntreprise() != null) {
            prSt.setString(9, document.getEntreprise().getSiret());
        } else {
            prSt.setNull(9, java.sql.Types.VARCHAR);
        }

        if (document.getAssurance() != null) {
            prSt.setString(10, document.getAssurance().getNumeroContrat());
            prSt.setInt(11, document.getAssurance().getAnneeContrat());
        } else {
            prSt.setNull(10, java.sql.Types.VARCHAR);
            prSt.setNull(11, java.sql.Types.INTEGER);
        }
        
        } catch (IOException | SQLException e) {
        	e.printStackTrace();
        }
    }
}
