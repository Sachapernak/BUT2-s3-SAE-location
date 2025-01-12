package modele.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.ConnexionBD;
import modele.Loyer;
import modele.dao.requetes.RequeteCreateLoyer;
import modele.dao.requetes.RequeteUpdateLoyer;
import modele.dao.requetes.RequeteDeleteLoyer;
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

    public List<Loyer> findByIdLogement(String id) throws SQLException, IOException {
        return find(new RequeteSelectLoyerByIdLogement(), id);
    }
    
    public boolean estLoyerAugmentable(String idLog) throws SQLException, IOException {
        Connection cn = ConnexionBD.getInstance().getConnexion();
        String call = "{? = call pkg_icc.loyer_augmentable(?)}";
        
        try (CallableStatement prCl = cn.prepareCall(call)) {
            prCl.setString(2, idLog);
            prCl.registerOutParameter(1, java.sql.Types.INTEGER);
            
            prCl.execute();
            
            // Retourne true si loyer augmentable (retour non nul), false sinon
            return prCl.getInt(1) != 0;
        }
    }

    public BigDecimal getMaxLoyer(String idLog) throws SQLException, IOException {
        Connection cn = ConnexionBD.getInstance().getConnexion();
        String call = "{call pkg_icc.calculer_nouveau_loyer(?,?)}";
        
        try (CallableStatement prCl = cn.prepareCall(call)) {
            prCl.setString(1, idLog);
            prCl.registerOutParameter(2, java.sql.Types.DECIMAL);
            
            prCl.execute();
            
            return prCl.getBigDecimal(2);
        }
    }
    
    public BigDecimal getDernierLoyer(String idLog) throws SQLException, IOException {
        Connection cn = ConnexionBD.getInstance().getConnexion();
        String call = "{call pkg_icc.dernier_loyer(?,?)}";
        
        try (CallableStatement prCl = cn.prepareCall(call)) {
            prCl.setString(1, idLog);
            prCl.registerOutParameter(2, java.sql.Types.DECIMAL);
            
            prCl.execute();
            
            return prCl.getBigDecimal(2);
        }
    }


    @Override
    protected Loyer createInstance(ResultSet curseur) throws SQLException, IOException {
        String idBien = curseur.getString("IDENTIFIANT_LOGEMENT");
        String date = curseur.getDate("DATE_DE_CHANGEMENT").toString();
        BigDecimal montant = curseur.getBigDecimal("MONTANT_LOYER");

        return new Loyer(idBien, date, montant);
    }
}