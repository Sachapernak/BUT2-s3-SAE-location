package modele.dao;

import java.util.List;

import modele.Creneau;
import modele.Groupe;


public class DaoGroupe implements Dao<Groupe> {

    @Override
    public void create(Groupe c) {
        DaoTest.insertGroupe(c);
    }

    @Override
    public void update(Groupe c) {
        DaoTest.updateGoupe(c);        
    }

    @Override
    public void delete(Groupe c) {
        DaoTest.deleteGroupe(c);    
    }

    @Override
    public Groupe findById(String... id) {
    	System.out.println(id);
        if (DaoTest.selectGroupe(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectGroupe(id).get(0);
    }

    @Override
    public List<Groupe> findAll() {
        return DaoTest.selectGroupe();
    }
}
