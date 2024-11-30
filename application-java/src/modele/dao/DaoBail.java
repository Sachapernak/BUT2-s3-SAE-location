package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Bail;

import modele.dao.requetes.RequeteSelectBail;
import modele.dao.requetes.RequeteSelectBailById;


public class DaoBail extends DaoModele<Bail> implements Dao<Bail> {

	@Override
	public void create(Bail donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Bail donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Bail donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
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
		String idBail = curseur.getString("ID_BAIL");
		String dateDeDebut = curseur.getString("DATE_DE_DEBUT");
		String dateDeFin = curseur.getString("DATE_DE_FIN");
		
		Bail bail = new Bail(idBail, dateDeDebut, dateDeFin );
		
		if (dateDeFin == "") {
			bail.setDateDeFin(dateDeFin);
		}
		
		return bail;
		
	}

}
