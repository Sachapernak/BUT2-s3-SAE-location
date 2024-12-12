package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Diagnostiques;

public class DaoDiagnostique extends DaoModele<Diagnostiques> {

	@Override
	public void create(Diagnostiques donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Diagnostiques donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Diagnostiques donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Diagnostiques findById(String... id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Diagnostiques> findAll() throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Diagnostiques createInstance(ResultSet curseur) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Diagnostiques> findByIdLogement(String identifiantLogement) {
		// TODO Auto-generated method stub
		return null;
	}

}
