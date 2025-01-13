package modele.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Assurance;
import modele.dao.requetes.RequeteCreateAssurance;
import modele.dao.requetes.RequeteUpdateAssurance;
import modele.dao.requetes.RequeteDeleteAssurance;
import modele.dao.requetes.RequeteSelectAssurance;
import modele.dao.requetes.RequeteSelectAssuranceById;

public class DaoAssurance extends DaoModele<Assurance>{

	@Override
	public void create(Assurance donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateAssurance(), donnees);
		
	}

	@Override
	public void update(Assurance donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateAssurance(), donnees);
		
	}

	@Override
	public void delete(Assurance donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteAssurance(), donnees);
		
	}

	@Override
	public Assurance findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectAssuranceById(), id);
	}

	@Override
	public List<Assurance> findAll() throws SQLException, IOException {
		return find(new RequeteSelectAssurance());
	}

	@Override
	protected Assurance createInstance(ResultSet curseur) throws SQLException, IOException {
		String numeroContrat = curseur.getString("numero_de_contrat");
        int anneeContrat = curseur.getInt("annee_du_contrat");
        String typeContrat = curseur.getString("type_de_contrat");
		
        return new Assurance(numeroContrat, anneeContrat, typeContrat);
	}

}
