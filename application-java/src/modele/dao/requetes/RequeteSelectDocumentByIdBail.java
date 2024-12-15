package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Document;

public class RequeteSelectDocumentByIdBail extends Requete<Document> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_DOCUMENT WHERE ID_BAIL = ? ";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	
        prSt.setString(1, id[0]);
    	
       
       
    }
}