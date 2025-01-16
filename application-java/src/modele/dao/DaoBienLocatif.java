package modele.dao;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Batiment;
import modele.BienLocatif;
import modele.ConnexionBD;
import modele.dao.requetes.RequeteSelectBienLocatif;
import modele.dao.requetes.RequeteSelectBienLocatifById;
import modele.dao.requetes.RequeteSelectBienLocatifByIdBat;
import modele.dao.requetes.RequeteCountNbLogementsBatiment;
import modele.dao.requetes.RequeteCreateBienLocatif;
import modele.dao.requetes.RequeteDeleteBienLocatif;
import modele.dao.requetes.RequeteUpdateBienLocatif;
import modele.dao.requetes.RequeteSelectBienLocatifByIdDocComptQuittance;
import modele.TypeDeBien;

public class DaoBienLocatif extends DaoModele<BienLocatif> {

	@Override
	public void create(BienLocatif donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateBienLocatif(), donnees);
		
	}

	@Override
	public void update(BienLocatif donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateBienLocatif(), donnees);
	}

	@Override
	public void delete(BienLocatif donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteBienLocatif(), donnees);
		
	}

	@Override
	public BienLocatif findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectBienLocatifById(), id);
	}
	
	
	public BienLocatif findByIdDocQuit(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectBienLocatifByIdDocComptQuittance(), id);
	}
	@Override
	public List<BienLocatif> findAll() throws SQLException, IOException {
		return find(new RequeteSelectBienLocatif());
	}
	
	public List<BienLocatif> findByIdBat(String batiment) throws SQLException, IOException {
		return find(new RequeteSelectBienLocatifByIdBat(), batiment);
	}
	
	public int countBiens(String... id) throws SQLException, IOException{
        Connection cn = ConnexionBD.getInstance().getConnexion();
        RequeteCountNbLogementsBatiment req = new RequeteCountNbLogementsBatiment();
        PreparedStatement prSt = cn.prepareStatement(req.requete());
        req.parametres(prSt, id);
        ResultSet rs = prSt.executeQuery();
        int res = 0;
        if (rs.next()) {
             res = rs.getInt(2);
        }

        rs.close();
        prSt.close();

        return res;
    }

	@Override
	protected BienLocatif createInstance(ResultSet curseur) throws SQLException, IOException {
		
		String idLogement = curseur.getString("IDENTIFIANT_LOGEMENT");
		TypeDeBien type = "garage".equalsIgnoreCase(curseur.getString("TYPE_DE_BIEN")) ? TypeDeBien.GARAGE : TypeDeBien.LOGEMENT;
		int surface = curseur.getInt("SURFACE");
		int nbPiece = curseur.getInt("NB_PIECE");
		BigDecimal loyerBase = curseur.getBigDecimal("LOYER_DE_BASE");
		
		String idBat = curseur.getString("IDENTIFIANT_BATIMENT");
		Batiment bat = new DaoBatiment().findById(idBat);
		
		return new BienLocatif(idLogement, type , surface, nbPiece, loyerBase, bat);
	}
	
}
