package modele.dao;

import java.util.List;

import modele.Formation;

public class DaoFormation implements Dao<Formation>  {


    @Override
    public void create(Formation c) {
        DaoTest.insertFormation(c);
    }

    @Override
    public void update(Formation c) {
        DaoTest.updateFormation(c);        
    }

    @Override
    public void delete(Formation c) {
        DaoTest.deleteFormation(c);    
    }

    @Override
    public Formation findById(String... id) {
        if (DaoTest.selectFormation(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectFormation(id).get(0);
    }

    @Override
    public List<Formation> findAll() {
        return DaoTest.selectFormation();
    }
}
