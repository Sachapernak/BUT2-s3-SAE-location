package modele.dao.requetes;

import java.sql.CallableStatement;
import java.sql.SQLException;


public abstract class Procedure<T> {
	
	public abstract String procedure();
	
	public void parametres(CallableStatement prSt, String... id) throws SQLException {};
	
	public void parametres(CallableStatement prSt, T donnee) throws SQLException {};
}
