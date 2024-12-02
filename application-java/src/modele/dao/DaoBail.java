package modele.dao;

import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modele.Bail;

import modele.dao.requetes.RequeteSelectBail;
import modele.dao.requetes.RequeteSelectBailById;
import modele.dao.requetes.RequeteCreateBail;
import modele.dao.requetes.RequeteUpdateBail;
import modele.dao.requetes.RequeteDeleteBail;


public class DaoBail extends DaoModele<Bail> implements Dao<Bail> {

	@Override
	public void create(Bail donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateBail(), donnees);
	
	}

	@Override
	public void update(Bail donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateBail(), donnees);
	}

	@Override
	public void delete(Bail donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		miseAJour(new RequeteDeleteBail(), donnees);
	}

	@Override
	public Bail findById(String... id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return findById(new RequeteSelectBailById(), id);
	}

	@Override
	public List<Bail> findAll() throws SQLException, IOException {
		// TODO Auto-generated method stub
		return find(new RequeteSelectBail());
	}

	@Override
	protected Bail createInstance(ResultSet curseur) throws SQLException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    // Convertir la chaîne en LocalDateTime
	    LocalDateTime localDateTimeDebut = LocalDateTime.parse(curseur.getString("DATE_DE_DEBUT"), formatter);
	    
	    // Extraire uniquement la date si nécessaire
	    LocalDate localDateDebut = localDateTimeDebut.toLocalDate();
	    
	    
		String idBail = curseur.getString("ID_BAIL");
		
		// Formatter la date si nécessaire
		String dateDeDebut = localDateDebut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		
		Bail bail = new Bail(idBail, dateDeDebut );
		String dateDeFin = curseur.getString("DATE_DE_FIN");
		if (dateDeFin != null) {
			LocalDateTime localDateTimeFin = LocalDateTime.parse(dateDeFin, formatter);
			LocalDate localDateFin = localDateTimeFin.toLocalDate();
			bail.setDateDeFin(localDateFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		return bail;
		
	}

}
