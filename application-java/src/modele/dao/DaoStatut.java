package modele.dao;

import java.util.List;

import modele.Statut;

public class DaoStatut implements Dao<Statut>  {

    @Override
    public void create(Statut c) {
        DaoTest.insertStatut(c);
    }

    @Override
    public void update(Statut c) {
        DaoTest.updateStatut(c);        
    }

    @Override
    public void delete(Statut c) {
        DaoTest.deleteStatut(c);    
    }

    @Override
    public Statut findById(String... id) {
        if (DaoTest.selectStatut(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectStatut(id).get(0);
    }

    @Override
    public List<Statut> findAll() {
        return DaoTest.selectStatut();
    }

}
