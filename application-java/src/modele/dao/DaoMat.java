package modele.dao;

import java.util.List;

import modele.Mat;

public class DaoMat implements Dao<Mat>  {


    @Override
    public void create(Mat c) {
        DaoTest.insertMat(c);
    }

    @Override
    public void update(Mat c) {
        DaoTest.updateMat(c);        
    }

    @Override
    public void delete(Mat c) {
        DaoTest.deleteMat(c);    
    }

    @Override
    public Mat findById(String... id) {
        if (DaoTest.selectMat(id).isEmpty()) {
            return null;
        }
        return DaoTest.selectMat(id).get(0);
    }

    @Override
    public List<Mat> findAll() {
        return DaoTest.selectMat();
    }
	

}
