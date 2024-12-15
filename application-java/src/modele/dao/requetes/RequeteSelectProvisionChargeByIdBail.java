package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ProvisionCharge;

public class RequeteSelectProvisionChargeByIdBail extends Requete<ProvisionCharge> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_PROVISION_CHARGE WHERE ID_BAIL = ? ";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	
        prSt.setString(1, id[0]);
    	
       
       
    }
}