package modele;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class ConnexionBD {
	
	private static ConnexionBD instance;
	private static Connection cn;
	private static OracleDataSource bd;
	private static FichierConfig conf;
	
	private ConnexionBD() throws SQLException, IOException {
		conf = FichierConfig.getInstance();
		initierBD();
	}
	
	private void initierBD() throws SQLException, IOException {
		bd = new OracleDataSource();
		
		bd.setUser(conf.lire("DB_USER"));
		bd.setPassword(conf.lire("DB_PASSWORD"));
		bd.setURL(conf.lire("DB_LINK"));
		
	}
	
	// synchronized potentiellement pas utile
    public static synchronized ConnexionBD getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new ConnexionBD();
        }
        return instance;
    }
    
    public Connection getConnexion() throws SQLException {
    	if (cn == null || cn.isClosed()) {
    		cn = bd.getConnection();
    	}
    	return cn;
    }

    public OracleDataSource getBD() {
    	return bd;
    }
    
    public void fermerConnexion() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            cn.close();
        }
    }
    
    public void updateBDLink() throws SQLException, IOException {
    	fermerConnexion();
    	initierBD();
    }
}
