package modele.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.dao.requetes.Requete;

public abstract class DaoModele<T> implements Dao<T> {


	protected abstract T createInstance(ResultSet curseur) throws SQLException, IOException;

	public List<T> select(PreparedStatement prSt) throws SQLException, IOException {
		List<T> results = new ArrayList<T>();
		
		ResultSet rs = prSt.executeQuery();
		
		while(rs.next()) {
			results.add(createInstance(rs));
		}
		
		rs.close();
		prSt.close();
		
		return results;

	}
	
	public int miseAJour(Requete<T> req, T donnee) throws SQLException, IOException {
		Connection cn = ConnexionBD.getInstance().getConnexion();
		PreparedStatement prSt = cn.prepareStatement(req.requete());
		req.parametres(prSt, donnee);
		
		return prSt.executeUpdate();	
	}
	
	public List<T> find(Requete<T> req, String...id) throws SQLException, IOException{
		List<T> res = new ArrayList<>();
		
		Connection cn = ConnexionBD.getInstance().getConnexion();
		PreparedStatement prSt = cn.prepareStatement(req.requete());
		
		req.parametres(prSt, id);
		
		res = select(prSt);
		
		prSt.close();
		
		return res;
	}
	
	public T findById(Requete<T> req, String...id) throws SQLException, IOException {

		List<T> res = find(req, id);
		
		if (res.isEmpty()) {
			return null;
		} else {
			return res.get(0);
		}
	}



}