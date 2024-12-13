package modele.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.BienLocatif;

import modele.DocumentComptable;
import modele.FactureBien;
import modele.dao.requetes.RequeteCreateFactureBien;
import modele.dao.requetes.RequeteDeleteFactureBien;
import modele.dao.requetes.RequeteSelectFactureBienById;
import modele.dao.requetes.RequeteSelectFactureBienByIdBien;
import modele.dao.requetes.RequeteUpdateFactureBien;

public class DaoFactureBien extends DaoModele<FactureBien> {

	@Override
	public void create(FactureBien donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateFactureBien(), donnees);
		
	}

	@Override
	public void update(FactureBien donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateFactureBien(), donnees);
		
	}

	@Override
	public void delete(FactureBien donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteFactureBien(), donnees);
		
	}

	@Override
	public FactureBien findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectFactureBienById(), id);
	}
	

	public List<FactureBien> findByIdLogement(String... id) throws SQLException, IOException {
		return find(new RequeteSelectFactureBienByIdBien(), id);
	}
	
	
	@Override
    public List<FactureBien> select(PreparedStatement prSt) throws SQLException, IOException {
        
		List<FactureBien> results = new ArrayList<>();

        ResultSet rs = prSt.executeQuery();
        
        if (rs.next()) {
        	String idLogement = rs.getString("identifiant_logement");
        	
        	BienLocatif bien = new DaoBienLocatif().findById(idLogement);
        	results.add(createInstance(rs, bien));
        	
            while(rs.next()) {
                results.add(createInstance(rs, bien));
            }
        }

        rs.close();
        prSt.close();

        return results;
    }
	

	@Override
	public List<FactureBien> findAll() throws SQLException, IOException {
		throw new UnsupportedOperationException("Cette opération ne devrait pas etre utilisée");
	}

	
	
	protected FactureBien createInstance(ResultSet curseur, BienLocatif bien) throws SQLException, IOException {
		
		
		String numDoc = curseur.getString("numero_document");
		String dateDoc = curseur.getDate("date_document").toString();
		
		DocumentComptable docu = new DaoDocumentComptable().findById(numDoc, dateDoc);
		
		float partDesCharges = curseur.getFloat("part_des_charges");
		
		return new FactureBien(bien, docu, partDesCharges);
	}

	@Override
	public FactureBien createInstance(ResultSet curseur) throws SQLException, IOException {
		throw new UnsupportedOperationException("Veuillez utiliser createInstance(ResultSet,BienLocatif");
	}


}
