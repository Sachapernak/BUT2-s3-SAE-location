package modele.dao;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.dao.requetes.RequeteSelectAdresse;
import modele.dao.requetes.RequeteSelectAdresseById;
import modele.dao.requetes.RequeteDeleteAdresse; 
import modele.dao.requetes.RequeteUpdateAdresse;
import modele.dao.requetes.RequeteCreateAdresse; 

import modele.Adresse;

public class DaoAdresse extends DaoModele<Adresse> {

	@Override
	public void create(Adresse donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateAdresse(), donnees);
		
	}

	@Override
	public void update(Adresse donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateAdresse(), donnees);
		
	}

	@Override
	public void delete(Adresse donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteAdresse(), donnees);
		
	}

	@Override
	public Adresse findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectAdresseById(), id);
	}

	@Override
	public List<Adresse> findAll() throws SQLException, IOException {
		return find(new RequeteSelectAdresse());
	}

	@Override
	protected Adresse createInstance(ResultSet curseur) throws SQLException {
		String idAdresse = curseur.getString("ID_SAE_ADRESSE");
		String adresseLigne = curseur.getString("ADRESSE");
		int codePostal = curseur.getInt("CODE_POSTAL");
		String ville = curseur.getString("VILLE");
		String complementAdresse = curseur.getString("COMPLEMENT_ADRESSE");
		
		Adresse adresse = new Adresse(idAdresse, adresseLigne, codePostal, ville );
		
		if (complementAdresse != null) {
			adresse.setComplementAdresse(complementAdresse);
		}
		
		return adresse;
		
	}

}
