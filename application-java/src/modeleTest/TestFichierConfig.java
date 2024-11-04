package modeleTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.FichierConfig;

public class TestFichierConfig {

	FichierConfig config;	
	
    @Before 
    public void initialize() {
	       config= FichierConfig.getInstance();
	       FichierConfig.changerChemin(null);
	 }
    
    @After
    public void teardown() {
    	FichierConfig.changerChemin(null);
    }
    
    @Test
    public void testGetInstance() {
    	config= FichierConfig.getInstance();
    	assertTrue(config instanceof FichierConfig);
    }
	
    @Test
    public void testGetChemin() {
    	assertEquals("src/config.properties", FichierConfig.getChemin());
    }
    
	@Test
	public void testChangerCheminVide() {
		
		FichierConfig.changerChemin("truc");
		assertNotEquals("src/config.properties", FichierConfig.getChemin());
		FichierConfig.changerChemin(null);
		assertEquals("src/config.properties", FichierConfig.getChemin());
		
		
	}
	
	@Test
	public void testChangerChemin() {
		
		FichierConfig.changerChemin("src/modeleTest/test1.properties");
		assertEquals("src/modeleTest/test1.properties", FichierConfig.getChemin());
		FichierConfig.changerChemin(null);
		assertNotEquals("src/modeleTest/test1.properties", FichierConfig.getChemin());
		
	}
	
	@Test
	public void testProperties() {
		FichierConfig.changerChemin("src/modeleTest/test1.properties");
		assertEquals("Ok1", config.lire("TEST"));
		assertEquals("Ok2", config.lire("TEST2"));
	}
	
	@Test
	public void testException(){
		FichierConfig.changerChemin("src/modeleTest/fichierInexistant.properties");
		assertEquals(null,config.lire("test"));
		

	}

}



