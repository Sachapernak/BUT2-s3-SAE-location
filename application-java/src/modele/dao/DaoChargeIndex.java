package modele.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import modele.ChargeIndex;
import modele.dao.requetes.RequeteSelectCiByIdDocComptable;

public class DaoChargeIndex extends DaoModele<ChargeIndex>{

	@Override
	public void create(ChargeIndex donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ChargeIndex donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ChargeIndex donnees) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChargeIndex findById(String... id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChargeIndex> findAll() throws SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ChargeIndex createInstance(ResultSet curseur) throws SQLException, IOException {
		
		String id = curseur.getString("id_charge_index");
		String dateDeReleve = curseur.getDate("date_de_releve").toString(); 
		String type = curseur.getString("type");
		
		BigDecimal valeur = curseur.getBigDecimal("valeur");
		BigDecimal coutVariable = curseur.getBigDecimal("cout_variable");
		BigDecimal coutFixe = curseur.getBigDecimal("cout_fixe");
		
        String numDoc = curseur.getString("numero_document") ;
        String dateDoc = curseur.getDate("date_document").toString();
		
		ChargeIndex nouveau = new ChargeIndex(id, dateDeReleve, type, valeur, coutVariable, 
				coutFixe, numDoc, dateDoc);
		
		String dateRelevePreced = curseur.getDate("date_releve_precedent").toString();
		
		if (!(dateRelevePreced == null || dateRelevePreced.isEmpty())) {
			nouveau.setDateRelevePrecedent(dateRelevePreced);
		}
		return nouveau;
	}

	public ChargeIndex findByIdDocumentComptable(String...id) throws SQLException, IOException {
		return findById(new RequeteSelectCiByIdDocComptable(), id);
	}

}
