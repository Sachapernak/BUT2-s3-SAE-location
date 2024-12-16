package modele.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Adresse;
import modele.Batiment;
import modele.ConnexionBD;
import modele.dao.requetes.RequeteCountNbLogementsBatiment;
import modele.dao.requetes.RequeteCreateBatiment;
import modele.dao.requetes.RequeteDeleteBatiment;
import modele.dao.requetes.RequeteSelectBatiment;
import modele.dao.requetes.RequeteSelectBatimentById;

public class DaoBatiment extends DaoModele<Batiment> {

	@Override
	public void create(Batiment donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateBatiment(), donnees);
	}



	@Override
	public void delete(Batiment donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteBatiment(), donnees);
		
	}

	@Override
	public Batiment findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectBatimentById(), id);
	}

	@Override
	public List<Batiment> findAll() throws SQLException, IOException {
		return find(new RequeteSelectBatiment());
	}
	
	public int countBiens(Batiment donnees) throws SQLException, IOException{
		Connection cn = ConnexionBD.getInstance().getConnexion();
		RequeteCountNbLogementsBatiment req = new RequeteCountNbLogementsBatiment();
        PreparedStatement prSt = cn.prepareStatement(req.requete());
        req.parametres(prSt, donnees);
        ResultSet rs = prSt.executeQuery();
        int res = 0;
        if (rs.next()) {
        	 res = rs.getInt(2); // Récupérer la valeur comme chaîne
        }

        rs.close();
        prSt.close();

        return res;
	}

	@Override
	protected Batiment createInstance(ResultSet curseur) throws SQLException, IOException {
		String id = curseur.getString("IDENTIFIANT_BATIMENT");
		
		String idAdresse = curseur.getString("ID_SAE_ADRESSE");
		
		Adresse adresse = new DaoAdresse().findById(idAdresse);
		
		return new Batiment(id, adresse);
	}


	@Override
	public void update(Batiment donnees) throws SQLException, IOException {
		throw new UnsupportedOperationException("Le batiment ne peut etre modifié : "
				+ "Il n'y aucun attribut a modifier (hormis l'adresse, qui ne devrait "
				+ "pas changer)");
		
		
	}

}
