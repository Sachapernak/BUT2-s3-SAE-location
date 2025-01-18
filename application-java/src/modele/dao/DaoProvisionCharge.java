package modele.dao;


import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.ProvisionCharge;
import modele.dao.requetes.RequeteCreateProvisionCharge;
import modele.dao.requetes.RequeteDeleteProvisionCharge;
import modele.dao.requetes.RequeteSelectProvisionChargeByIdBail;
import modele.dao.requetes.RequeteSelectProvisionCharge;
import modele.dao.requetes.RequeteSelectProvisionChargeById;
import modele.dao.requetes.RequeteUpdateProvisionCharge;



public class DaoProvisionCharge extends DaoModele<ProvisionCharge> implements Dao<ProvisionCharge> {
    
	@Override
    public void create(ProvisionCharge donnees) throws SQLException, IOException{
		miseAJour(new RequeteCreateProvisionCharge(), donnees);
    }
    
    @Override
    public void update(ProvisionCharge donnees) throws SQLException, IOException {
    	miseAJour(new RequeteUpdateProvisionCharge(), donnees);
    }
    
    @Override
    public void delete(ProvisionCharge donnees)throws SQLException, IOException {
    	miseAJour(new RequeteDeleteProvisionCharge(), donnees);
    }
   
    @Override
    public ProvisionCharge findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectProvisionChargeById(), id);
    }
    
    public List<ProvisionCharge> findByIdBail(String... id) throws SQLException, IOException {
        return find(new RequeteSelectProvisionChargeByIdBail(), id);
    }
    
    public List<ProvisionCharge> findByIdPPC(String... id) throws SQLException, IOException {
    	
    	String plsqlProcedure = "{ call Quittance.GetPPCDocCompt(?, ?, ?, ?,?) }";
		CallableStatement stmt = ConnexionBD.getInstance().getConnexion().prepareCall(plsqlProcedure);
		 
        // Définir les paramètres d'entrée
        stmt.setString(1, id[0]);
        stmt.setString(2, id[1]);
        stmt.setString(3, id[2]);
        stmt.setString(4, id[3]);
        // Définir le paramètre de sortie (SYS_REFCURSOR)
        stmt.registerOutParameter(5, Types.REF_CURSOR);

        // Exécuter la procédure
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(5);
        if (rs != null) {
        	List<ProvisionCharge> list = new ArrayList<>();
        	while(rs.next()) {
            	list.add(createInstance(rs));
        	}
    		return list;
        }
        return null;
    }
    
public List<ProvisionCharge> findByIdLocLog(String... id) throws SQLException, IOException {
    	
    	String plsqlProcedure = "{ call Quittance.GetPPCLocBien(?, ?, ?) }";
		CallableStatement stmt = ConnexionBD.getInstance().getConnexion().prepareCall(plsqlProcedure);
		 
        // Définir les paramètres d'entrée
        stmt.setString(1, id[0]);
        stmt.setString(2, id[1]);
        // Définir le paramètre de sortie (SYS_REFCURSOR)
        stmt.registerOutParameter(3, Types.REF_CURSOR);

        // Exécuter la procédure
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(3);
        List<ProvisionCharge> list = new ArrayList<>();
        while(rs.next()) {
        	list.add(createInstance(rs));
        }
		return list;
    }
    
    @Override
    public List<ProvisionCharge> findAll() throws SQLException, IOException {
        return find(new RequeteSelectProvisionCharge());
    }
    
    @Override
    protected ProvisionCharge createInstance(ResultSet curseur) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir la chaîne en LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(curseur.getString("DATE_CHANGEMENT"), formatter);

        // Extraire uniquement la date si nécessaire
        LocalDate localDate = localDateTime.toLocalDate();
        
        String idBail = curseur.getString("ID_BAIL");
        
        // Formatter la date de début
        String dateChangement = localDate.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        
        BigDecimal provisionpourcharge = curseur.getBigDecimal("PROVISION_POUR_CHARGE");
        
        return new ProvisionCharge(idBail, dateChangement, provisionpourcharge);
    }
}
