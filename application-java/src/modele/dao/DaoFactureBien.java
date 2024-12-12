package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.FactureBien;

public class DaoFactureBien extends DaoModele<FactureBien> {

	@Override
	public void create(FactureBien donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FactureBien donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(FactureBien donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FactureBien findById(String... id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactureBien> findAll() throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FactureBien createInstance(ResultSet curseur) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<FactureBien> findByIdLogement(String identifiantLogement) {
		// TODO Auto-generated method stub
		return null;
	}

}
