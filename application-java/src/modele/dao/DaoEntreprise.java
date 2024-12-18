package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.Entreprise;
import modele.dao.requetes.RequeteCreateEntreprise;
import modele.dao.requetes.RequeteDeleteEntreprise;
import modele.dao.requetes.RequeteSelectEntreprise;
import modele.dao.requetes.RequeteSelectEntrepriseById;
import modele.dao.requetes.RequeteUpdateEntreprise;

public class DaoEntreprise extends DaoModele<Entreprise> {

	@Override
	public void create(Entreprise donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateEntreprise(), donnees);
		
	}

	@Override
	public void update(Entreprise donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateEntreprise(), donnees);
		
	}

	@Override
	public void delete(Entreprise donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteEntreprise(), donnees);
		
	}

	@Override
	public Entreprise findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectEntrepriseById(), id);
	}

	@Override
	public List<Entreprise> findAll() throws SQLException, IOException {
		return find(new RequeteSelectEntreprise());
	}

	@Override
	protected Entreprise createInstance(ResultSet curseur) throws SQLException, IOException {

		String siret = curseur.getString("siret");
		String nom = curseur.getString("nom");
		String secteur = curseur.getString("secteur_d_activite");
		
		String idAdresse = curseur.getString("id_sae_adresse");
		Adresse adresse = new DaoAdresse().findById(idAdresse);
		
		return new Entreprise(siret, nom, secteur, adresse);
	}

}
