package modele.dao;

import java.util.List;

public interface Dao<T>{
	public void create(T donnees);
	public void update(T donnees);
	public void delete(T donnees);
	public T findById(String... id);
	public List<T> findAll();
}
