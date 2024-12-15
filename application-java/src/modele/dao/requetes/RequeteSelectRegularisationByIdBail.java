package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Regularisation;

public class RequeteSelectRegularisationByIdBail extends Requete<Regularisation> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_REGULARISATION WHERE ID_BAIL = ? ";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	
        prSt.setString(1, id[0]);
    	
       
       
    }
}