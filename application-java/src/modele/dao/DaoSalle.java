package modele.dao;

import java.util.List;

import modele.Salle;

public class DaoSalle implements Dao<Salle>  {

    @Override
    public void create(Salle c) {
        DaoTest.insertSalle(c);
    }

    @Override
    public void update(Salle c) {
        DaoTest.updateSalle(c);        
    }

    @Override
    public void delete(Salle c) {
        DaoTest.deleteSalle(c);    
    }

    @Override
    public Salle findById(String... id) {
        if (DaoTest.selectSalle(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectSalle(id).get(0);
    }

    @Override
    public List<Salle> findAll() {
        return DaoTest.selectSalle();
    }
}
