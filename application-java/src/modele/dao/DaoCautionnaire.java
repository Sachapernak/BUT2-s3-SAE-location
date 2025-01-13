package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.Cautionnaire;
import modele.dao.requetes.RequeteCreateCautionnaire;
import modele.dao.requetes.RequeteUpdateCautionnaire;
import modele.dao.requetes.RequeteDeleteCautionnaire;
import modele.dao.requetes.RequeteSelectCautionnaireById;
import modele.dao.requetes.RequeteSelectCautionnaire;

public class DaoCautionnaire extends DaoModele<Cautionnaire> implements Dao<Cautionnaire>{

	@Override
	public void create(Cautionnaire donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateCautionnaire(), donnees);
		
	}

	@Override
	public void update(Cautionnaire donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateCautionnaire(), donnees);
		
	}

	@Override
	public void delete(Cautionnaire donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteCautionnaire(), donnees);
		
	}

	@Override
	public Cautionnaire findById(String... id) throws SQLException, IOException {
		 return findById(new RequeteSelectCautionnaireById(), id);
	}

	@Override
	public List<Cautionnaire> findAll() throws SQLException, IOException {
		 return find(new RequeteSelectCautionnaire());
	}

	@Override
	protected Cautionnaire createInstance(ResultSet curseur) throws SQLException, IOException {
		 int idCautionnaire = curseur.getInt("ID_CAUTION");
	     String nomOuOrganisme = curseur.getString("NOM_OU_ORGANISME");
	     String prenom = curseur.getString("PRENOM");
	     String description = curseur.getString("DESCRIPTION_DU_CAUTIONNAIRE");
	     String idAdresse = curseur.getString("ID_SAE_ADRESSE");
	     
	     Adresse adresse = null;
	        
	     if (idAdresse != null) {
	        adresse = new DaoAdresse().findById(idAdresse);
	     }
	     
	     Cautionnaire cautionnaire = new Cautionnaire(idCautionnaire, nomOuOrganisme, prenom, description);   
	     cautionnaire.setAdresse(adresse);
	     
	     return cautionnaire;
	}

}
