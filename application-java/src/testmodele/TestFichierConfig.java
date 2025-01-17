package testmodele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.FichierConfig;

public class TestFichierConfig {

	    private FichierConfig config;
	    private static final String CHEMIN_TEST_KEY_CONFIG = "src/testmodele/test-key.config"; // Nouveau chemin pour la clé de test
	    
	    @Before 
	    public void initialize() throws IOException {
	    	FichierConfig.setCheminKeyConfig(CHEMIN_TEST_KEY_CONFIG);
	        config = FichierConfig.getInstance();   
	    }

	    @After
	    public void teardown() {
	        // Restaurer les valeurs par défaut après les tests
	        FichierConfig.changerChemin(null);
	        FichierConfig.setCheminKeyConfig(null);
	        config = FichierConfig.getInstance();
	        
	    }

	    @Test
	    public void testGetInstance() {
	        config = FichierConfig.getInstance();
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
	        FichierConfig.changerChemin("src/testmodele/test1.properties");
	        assertEquals("src/testmodele/test1.properties", FichierConfig.getChemin());
	        FichierConfig.changerChemin(null);
	        assertNotEquals("src/testmodele/test1.properties", FichierConfig.getChemin());
	    }

	    @Test
	    public void testProperties() throws IOException {
	        FichierConfig.changerChemin("src/testmodele/test1.properties");
	        assertEquals("Ok1", config.lire("TEST"));
	        assertEquals("Ok2", config.lire("TEST2"));
	    }

	    @Test(expected = IOException.class)
	    public void testException() throws IOException {
	        FichierConfig.changerChemin("src/testmodele/fichierInexistant.properties");
	        config.lire("erreur");
	    }

	    // Test pour la génération et la sauvegarde de la clé secrète
	    @Test
	    public void testGenererEtSauvegarderCle() throws IOException {
	    	FichierConfig.changerChemin("src/testmodele/test1.properties");
	    	FichierConfig.setCheminKeyConfig(CHEMIN_TEST_KEY_CONFIG);
	        // Sauvegarder la clé si elle n'existe pas
	        File keyFile = new File(CHEMIN_TEST_KEY_CONFIG);
	        if (keyFile.exists()) {
	            keyFile.delete(); // Supprimer l'existant pour éviter les conflits
	        }

	        config.genererEtSauvegarderCle();
	        assertTrue(keyFile.exists()); // Vérifier que le fichier de clé existe
	    }

	    // Test pour le chiffrement et déchiffrement d'un mot de passe
	    @Test
	    public void testChiffrementEtDechiffrementMdp() throws Exception {
	    	FichierConfig.changerChemin("src/testmodele/test1.properties");
	    	FichierConfig.setCheminKeyConfig(CHEMIN_TEST_KEY_CONFIG);
	        String mdp = "motDePasseTest123!";
	        config.enregistrerMdp(mdp);

	        String mdpDechiffre = config.recupererMdp();
	        assertEquals(mdp, mdpDechiffre); // Vérifier que le mot de passe déchiffré est le même que celui d'origine
	    }

	    // Test pour vérifier le comportement lorsque la clé est manquante
	    @Test(expected = IOException.class)
	    public void testCleManquante() throws IOException {
	    	FichierConfig.changerChemin("src/testmodele/test1.properties");
	        // Supprimer le fichier de clé si il existe
	        File keyFile = new File(CHEMIN_TEST_KEY_CONFIG);
	        if (keyFile.exists()) {
	            keyFile.delete();
	        }

	        // Essayer de lire un mot de passe sans clé disponible
	        config.recupererMdp();
	    }
	}

