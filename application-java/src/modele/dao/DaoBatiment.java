package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.Batiment;
import modele.dao.requetes.RequeteCreateBatiment;
import modele.dao.requetes.RequeteDeleteBatiment;
import modele.dao.requetes.RequeteSelectBatiment;
import modele.dao.requetes.RequeteSelectBatimentById;

public class DaoBatiment extends DaoModele<Batiment> {

	@Override
	public void create(Batiment donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateBatiment(), donnees);
		
	}

	@Override
	public void update(Batiment donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub 
		// Pas besoins ? 
		
	}

	@Override
	public void delete(Batiment donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteBatiment(), donnees);
		
	}

	@Override
	public Batiment findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectBatimentById(), id);
	}

	@Override
	public List<Batiment> findAll() throws SQLException, IOException {
		return find(new RequeteSelectBatiment());
	}

	@Override
	protected Batiment createInstance(ResultSet curseur) throws SQLException, IOException {
		String id = curseur.getString("IDENTIFIANT_BATIMENT");
		
		String idAdresse = curseur.getString("ID_SAE_ADRESSE");
		
		Adresse adresse = new DaoAdresse().findById(idAdresse);
		
		return new Batiment(id, adresse);
	}

}
