package modele.dao;

import java.util.List;
import src.controleur.*;

import modele.Creneau;


public class DaoCreneau implements Dao<Creneau> {

    @Override
    public void create(Creneau c) {
        DaoTest.insertCreneau(c);
    }

    @Override
    public void update(Creneau c) {
        DaoTest.updateCreneau(c);        
    }

    @Override
    public void delete(Creneau c) {
        DaoTest.deleteCreneau(c);    
    }

    @Override
    public Creneau findById(String... id) {
    	System.out.println(id);
        if (DaoTest.selectCreneau(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectCreneau(id).get(0);
    }

    @Override
    public List<Creneau> findAll() {
        return DaoTest.selectCreneau();
    }

	

    

}