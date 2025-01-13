package modele.dao;

import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
