package modele.dao;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.dao.requetes.RequeteSelectAdresse;
import modele.dao.requetes.RequeteSelectAdresseById;

import modele.Adresse;

public class DaoAdresse extends DaoModele<Adresse> {

	@Override
	public void create(Adresse donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Adresse donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Adresse donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
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
		
		if (complementAdresse != "") {
			adresse.setComplementAdresse(complementAdresse);
		}
		
		return adresse;
		
	}

}
