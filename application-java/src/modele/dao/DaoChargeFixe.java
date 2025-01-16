package modele.dao;

import java.io.IOException;



import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.ChargeFixe;
import modele.dao.requetes.RequeteCreateChargeFixe;
import modele.dao.requetes.RequeteDeleteChargeFixe;
import modele.dao.requetes.RequeteSelectCfByIdDocComptable;
import modele.dao.requetes.RequeteSelectChargeFixe;
import modele.dao.requetes.RequeteSelectChargeFixeById;
import modele.dao.requetes.RequeteUpdateChargeFixe;

public class DaoChargeFixe extends DaoModele<ChargeFixe>{

	@Override
	public void create(ChargeFixe donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateChargeFixe(), donnees);
		
	}

	@Override
	public void update(ChargeFixe donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateChargeFixe(), donnees);
		
	}

	@Override
	public void delete(ChargeFixe donnees) throws SQLException, IOException {
		 miseAJour(new RequeteDeleteChargeFixe(), donnees);
		
	}

	@Override
	public ChargeFixe findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectChargeFixeById(), id);
	}

	@Override
	public List<ChargeFixe> findAll() throws SQLException, IOException {
		return find(new RequeteSelectChargeFixe());
	}
	
	public ChargeFixe findByIdDocumentComptable(String...id) throws SQLException, IOException {
		return findById(new RequeteSelectCfByIdDocComptable(), id);
	}

    @Override
    protected ChargeFixe createInstance(ResultSet curseur) throws SQLException, IOException {
        
    	String id = curseur.getString("id_charge_cf");
    	
    	String dateCharge = curseur.getDate("date_de_charge").toString();
        String type = curseur.getString("type");
        BigDecimal montant = curseur.getBigDecimal("montant");
        
        String numeroDocument = curseur.getString("numero_document");
        String dateDocument = curseur.getDate("date_document").toString();

        return new ChargeFixe(id ,dateCharge, type, montant, numeroDocument, dateDocument);
    }

}
