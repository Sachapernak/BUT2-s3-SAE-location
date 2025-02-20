package testmodele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ConnexionBD;
import modele.FichierConfig;
import oracle.jdbc.datasource.OracleDataSource;

public class TestConnexionBD {

    

    @Before
    public void initialize() throws IOException {

    	FichierConfig.changerChemin(null);
    	FichierConfig config= FichierConfig.getInstance();
       
    	// Le fichier config.properties DOIT avoir des informations valide
    	// pour pouvoir effectuer ces test !
    	assert(config.lire("DB_PASSWORD") != null) : "Mot de passe manquant, verifier src/config.properties";
    	assert(config.lire("DB_USER") != null) : "Nom d'utilisateur manquant, verifier src/config.properties";
    	assert(config.lire("DB_LINK") != null) : "URL manquante, verifier src/config.properties";
    }

    @After
    public void teardown() throws Exception {
     ConnexionBD connexion;
    	FichierConfig.changerChemin(null);
        connexion = ConnexionBD.getInstance(); 
        connexion.fermerConnexion();
        // Utilise la réflexion pour réinitialiser l'instance du singleton après chaque test
        Field instance = ConnexionBD.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }
    
	@Test
	public void testGetInstanceNonNul() throws SQLException, IOException {
		assertTrue(ConnexionBD.getInstance() != null);
	}
	
	@Test
	public void testGetInstanceIsSingleton() throws SQLException, IOException {
		assertTrue(ConnexionBD.getInstance() instanceof ConnexionBD);
		
	}
	
	@Test
	public void testGetInstanceMemeObjet() throws SQLException, IOException {
		ConnexionBD e1 = ConnexionBD.getInstance();
		ConnexionBD e2 = ConnexionBD.getInstance();

		assertEquals(e1,e2);
	}
	
	@Test(expected = IOException.class)
	public void testGetInstanceMauvaiseConfigFichierException() throws SQLException, IOException {
    	FichierConfig.changerChemin("src/testmodeledao/test1.properties");
		ConnexionBD.getInstance();
		
	}
	
	@Test
	public void testGetConnexionPremiereFois() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		coBD.getConnexion();
	}
	
	@Test
	public void testGetConnexionDeuxiemeFois() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		Connection cn = coBD.getConnexion();
		Connection cn2 = coBD.getConnexion();
		assertEquals(cn,cn2);
	}
	
	@Test
	public void getBDPremiereFois() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		coBD.getBD();
		
	}
	
	@Test
	public void getBDDeuxiemeFois() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		OracleDataSource bd = coBD.getBD();
		OracleDataSource bd2 = coBD.getBD();
		assertEquals(bd, bd2);
		
	}
	
	@Test
	public void testFermerConnexionNull() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		coBD.fermerConnexion();
		
	}
	
	@Test
	public void testFermerConnexionFermee() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		Connection cn = coBD.getConnexion();
		coBD.fermerConnexion();
		assertTrue(cn.isClosed());
		coBD.fermerConnexion();
		assertTrue(cn.isClosed());
	}
	
	@Test
	public void testFermerConnexion() throws SQLException, IOException {
		ConnexionBD coBD = ConnexionBD.getInstance();
		Connection cn = coBD.getConnexion();
		coBD.fermerConnexion();
		assertTrue(cn.isClosed());
	}
   
   
    
}
