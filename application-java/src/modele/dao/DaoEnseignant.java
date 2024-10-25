package modele.dao;

import java.util.List;

import modele.Enseignant;

public class DaoEnseignant implements Dao<Enseignant> {

    @Override
    public void create(Enseignant c) {
        DaoTest.insertEnseignant(c);
    }

    @Override
    public void update(Enseignant c) {
        DaoTest.updateEnseignant(c);        
    }

    @Override
    public void delete(Enseignant c) {
        DaoTest.deleteEnseignant(c);    
    }

    @Override
    public Enseignant findById(String... id) {
        if (DaoTest.selectEnseignant(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectEnseignant(id).get(0);
    }

    @Override
    public List<Enseignant> findAll() {
        return DaoTest.selectEnseignant();
    }

	

}
