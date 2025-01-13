package modele.dao;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Loyer;
import modele.dao.requetes.RequeteCreateLoyer;
import modele.dao.requetes.RequeteUpdateLoyer;
import modele.dao.requetes.RequeteDeleteLoyer;
import modele.dao.requetes.RequeteSelectLoyerByIdLocBienDocComptable;
import modele.dao.requetes.RequeteSelectLoyer;
import modele.dao.requetes.RequeteSelectLoyerById;
import modele.dao.requetes.RequeteSelectLoyerByIdLogement;

public class DaoLoyer extends DaoModele<Loyer> {

    @Override
    public void create(Loyer donnees) throws SQLException, IOException {
        miseAJour(new RequeteCreateLoyer(), donnees);
    }

    @Override
    public void update(Loyer donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateLoyer(), donnees);
    }

    @Override
    public void delete(Loyer donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteLoyer(), donnees);
    }

    @Override
    public Loyer findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectLoyerById(), id);
    }

    @Override
    public List<Loyer> findAll() throws SQLException, IOException {
        return find(new RequeteSelectLoyer());
    }
    
    public List<Loyer> findByIdLocBienDocComptable(String... id) throws SQLException, IOException{
		return find(new RequeteSelectLoyerByIdLocBienDocComptable(), id);
	}

    public List<Loyer> findByIdLogement(String id) throws SQLException, IOException {
        return find(new RequeteSelectLoyerByIdLogement(), id);
    }

    @Override
    protected Loyer createInstance(ResultSet curseur) throws SQLException, IOException {
        String idBien = curseur.getString("IDENTIFIANT_LOGEMENT");
        String date = curseur.getDate("DATE_DE_CHANGEMENT").toString();
        BigDecimal montant = curseur.getBigDecimal("MONTANT_LOYER");

        return new Loyer(idBien, date, montant);
    }
}