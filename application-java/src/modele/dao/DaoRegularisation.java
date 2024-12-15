package modele.dao;

import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modele.Regularisation;
import modele.dao.requetes.RequeteCreateRegularisation;
import modele.dao.requetes.RequeteDeleteRegularisation;
import modele.dao.requetes.RequeteSelectRegularisationByIdBail;
import modele.dao.requetes.RequeteSelectRegularisation;
import modele.dao.requetes.RequeteSelectRegularisationById;
import modele.dao.requetes.RequeteUpdateRegularisation;


public class DaoRegularisation extends DaoModele<Regularisation> implements Dao<Regularisation> {
    
	@Override
    public void create(Regularisation donnees) throws SQLException, IOException{
		miseAJour(new RequeteCreateRegularisation(), donnees);
    }
    
    @Override
    public void update(Regularisation donnees) throws SQLException, IOException {
    	miseAJour(new RequeteUpdateRegularisation(), donnees);
    }
    
    @Override
    public void delete(Regularisation donnees)throws SQLException, IOException {
    	miseAJour(new RequeteDeleteRegularisation(), donnees);
    }
   
    @Override
    public Regularisation findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectRegularisationById(), id);
    }
    
    public List<Regularisation> findByIdBail(String... id) throws SQLException, IOException {
        return find(new RequeteSelectRegularisationByIdBail(), id);
    }
    
    @Override
    public List<Regularisation> findAll() throws SQLException, IOException {
        return find(new RequeteSelectRegularisation());
    }
    
    @Override
    protected Regularisation createInstance(ResultSet curseur) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir la chaîne en LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(curseur.getString("DATE_REGU"), formatter);

        // Extraire uniquement la date si nécessaire
        LocalDate localDate = localDateTime.toLocalDate();
        
        String idBail = curseur.getString("ID_BAIL");
        
        // Formatter la date de début
        String dateRegu = localDate.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        
        BigDecimal montant = curseur.getBigDecimal("MONTANT");
        
        Regularisation regularisation = new Regularisation(idBail, dateRegu, montant);

        return regularisation;
    }
}
