package modele.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T>{
	public void create(T donnees)throws SQLException, IOException;
	public void update(T donnees)throws SQLException, IOException;
	public void delete(T donnees)throws SQLException, IOException;
	public T findById(String... id)throws SQLException, IOException;
	public List<T> findAll()throws SQLException, IOException;
}

