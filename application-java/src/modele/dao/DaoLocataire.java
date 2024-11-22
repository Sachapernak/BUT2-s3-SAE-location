package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.dao.requetes.RequeteSelectLocataireById;
import modele.dao.requetes.RequeteSelectLocataire;
import modele.Adresse;
import modele.Locataire;

public class DaoLocataire extends DaoModele<Locataire> {

	@Override
	public void create(Locataire donnees) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Locataire donnees) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Locataire donnees) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Locataire findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectLocataireById(), id );
	}

	@Override
	public List<Locataire> findAll() throws SQLException, IOException {
		return find(new RequeteSelectLocataire());
	}

	@Override
	protected Locataire createInstance(ResultSet curseur) throws SQLException, IOException {
		String id = curseur.getString("IDENTIFIANT_LOCATAIRE");
		String nom = curseur.getString("NOM_LOCATAIRE");
		String prenom = curseur.getString("PRENOM_LOCATAIRE");
		String email = curseur.getString("EMAIL_LOCATAIRE");
		String telephone = curseur.getString("TELEPHONE_LOCATAIRE");
		String dateNaissance = curseur.getDate("DATE_NAISSANCE").toString();
		String lieuNaissance = curseur.getString("LIEU_DE_NAISSANCE");
		String acteCaution = curseur.getString("ACTE_DE_CAUTION");
		
		String idAdresse = curseur.getString("ID_SAE_ADRESSE");
		Adresse adresse = new DaoAdresse().findById(idAdresse);
		
		Locataire locataire = new Locataire(id, nom, prenom, dateNaissance);
		
		if (telephone != "") {
			locataire.setTelephone(telephone);
		}
		
		if (lieuNaissance != "") {
			locataire.setLieuDeNaissance(lieuNaissance);
		}
		
		if (acteCaution != "") {
			locataire.setActeDeCaution(acteCaution);
		}
		
		if (email != "") {
			locataire.setEmail(email);
		}
		
		return locataire;
		
		
	}

	
	

}
