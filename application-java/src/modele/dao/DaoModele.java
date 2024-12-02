package modele.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ConnexionBD;
import modele.dao.requetes.Procedure;
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
	
	public int appelProcedure(Procedure<T> pro, T donnee) throws SQLException, IOException {
		Connection cn = ConnexionBD.getInstance().getConnexion();
		CallableStatement clSt = cn.prepareCall(pro.procedure());
		pro.parametres(clSt, donnee);

		int res = clSt.executeUpdate();
		
		return res;
	}
	
	public int miseAJour(Requete<T> req, T donnee) throws SQLException, IOException {
		Connection cn = ConnexionBD.getInstance().getConnexion();
		PreparedStatement prSt = cn.prepareStatement(req.requete());
		req.parametres(prSt, donnee);

		int res = prSt.executeUpdate();
		
		return res;
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