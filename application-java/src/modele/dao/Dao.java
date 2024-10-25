package modele.dao;

import java.util.List;

public interface Dao<T> {
    public void create(T obj);

    public void update(T obj);

    public void delete(T obj);

    public T findById(String... id);

    public List<T> findAll();
}
