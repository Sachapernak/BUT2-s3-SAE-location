package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.dao.requetes.RequeteSelectLocataireById;
import modele.dao.requetes.RequeteSelectLocataire;
import modele.dao.requetes.RequeteDeleteLocataire;
import modele.dao.requetes.RequeteUpdateLocataire;
import modele.Adresse;
import modele.Locataire;

public class DaoLocataire extends DaoModele<Locataire> {

	@Override
	public void create(Locataire donnees) throws SQLException, IOException {
		
		
	}

	@Override
	public void update(Locataire donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateLocataire(), donnees);
		
	}

	/**
	 * Supprime un locataire de la base de données.
	 * 
	 * @param donnees le locataire à supprimer (objet contenant les informations nécessaires).
	 * @throws SQLException si une erreur survient lors de l'exécution de la requête SQL,
	 *                     Nottament : l'erreur Oracle -20002 en cas de documents comptables existants.
	 *                     
	 * @throws IOException si il y a erreur dans la lecture du fichier config.
	 */
	@Override
	public void delete(Locataire donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteLocataire(), donnees);
		
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
		
		Adresse adresse = null;
		
		if (idAdresse != null) {
			adresse = new DaoAdresse().findById(idAdresse);
		}
		
		
		Locataire locataire = new Locataire(id, nom, prenom, dateNaissance);
		
		if (telephone != null) {
			locataire.setTelephone(telephone);
		}
		
		if (lieuNaissance != null) {
			locataire.setLieuDeNaissance(lieuNaissance);
		}
		
		if (acteCaution != null) {
			locataire.setActeDeCaution(acteCaution);
		}
		
		if (email != null) {
			locataire.setEmail(email);
		}
		
		if (adresse != null) {
			locataire.setAdresse(adresse);
		}
		
		return locataire;
		
		
	}

	
	

}
