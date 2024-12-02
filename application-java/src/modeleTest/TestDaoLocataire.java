package modeleTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.Adresse;
import modele.Bail;
import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoAdresse;
import modele.dao.DaoLocataire;

public class TestDaoLocataire {

    private DaoLocataire daoLocataire;
    private Adresse adresseTest;

    @Before
    public void setUp() throws SQLException, IOException {

    	daoLocataire = new DaoLocataire();        
        adresseTest = new Adresse("TESTADDR", "10 rue du Test", 12345, "TestVille");
        DaoAdresse daoA = new DaoAdresse();
        
        deleteTestData();
        
        daoA.create(adresseTest);
        
    }
    
    
    // Methodes support pour les test
    private Locataire creerLocataireTest(String id) {
    	
        Locataire locataire = new Locataire(id, "NomTest", "PrenomTest", "2000-01-01");
        
        locataire.setEmail("test@test.com");
        locataire.setTelephone("0600000000");
        locataire.setAdresse(adresseTest);
        locataire.setLieuDeNaissance("TestVille");
        locataire.setActeDeCaution("CautionTest");
        
        return locataire;
    }

    private void ajouterLocataire(Locataire locataire) throws SQLException, IOException {
        daoLocataire.create(locataire);
    }

    private void supprimerLocataire(Locataire locataire) throws SQLException, IOException {
        daoLocataire.delete(locataire);
    }
    
    public void deleteTestData() throws SQLException, IOException {
        String deleteContracts = "DELETE FROM SAE_contracter WHERE lower(identifiant_locataire) LIKE '%test%'";
        String deleteLocataires = "DELETE FROM SAE_LOCATAIRE WHERE lower(identifiant_locataire) LIKE '%test%'";
        String deleteAddresses = "DELETE FROM SAE_ADRESSE WHERE lower(ID_SAE_ADRESSE) LIKE '%test%'";

        Connection cn = ConnexionBD.getInstance().getConnexion();

        	Statement stmt = cn.createStatement();
        
            // Commencer une transaction
            cn.setAutoCommit(false);

            // Exécuter les suppressions
            stmt.executeUpdate(deleteContracts);
            stmt.executeUpdate(deleteLocataires);
            stmt.executeUpdate(deleteAddresses);

            // Valider les modifications
            cn.commit();
            System.out.println("Les données contenant 'test' ont été supprimées avec succès.");

    }

    @Test
    public void testDaoLocataireCreate() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST01");

        ajouterLocataire(locataire);
        Locataire locataireRecup = daoLocataire.findById("TEST01");

        assertNotNull("Le locataire inséré n'a pas été trouvé.", locataireRecup);
        assertEquals("L'ID du locataire est incorrect.", "TEST01", locataireRecup.getIdLocataire());
        assertEquals("L'adresse email est incorrecte.", "test@test.com", locataireRecup.getEmail());
        assertEquals("Le téléphone est incorrect.", "0600000000", locataireRecup.getTelephone());
        assertNotNull("L'adresse associée au locataire est manquante.", locataireRecup.getAdresse());
        assertEquals("L'adresse associée est incorrecte.", "TESTADDR", locataireRecup.getAdresse().getIdAdresse());

        supprimerLocataire(locataire);
    }

    @Test
    public void testDaoLocataireModify() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST02");
        ajouterLocataire(locataire);

        locataire.setEmail("nouvelemail@test.com");
        locataire.setTelephone("0700000000");
        daoLocataire.update(locataire);

        Locataire locataireRecup = daoLocataire.findById("TEST02");
        assertNotNull("Le locataire mis à jour n'a pas été trouvé.", locataireRecup);
        assertEquals("L'email mis à jour est incorrect.", "nouvelemail@test.com", locataireRecup.getEmail());
        assertEquals("Le téléphone mis à jour est incorrect.", "0700000000", locataireRecup.getTelephone());

        supprimerLocataire(locataire);
    }

    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST03");
        ajouterLocataire(locataire);

        Locataire locataireRecup = daoLocataire.findById("TEST03");
        assertNotNull("Le locataire inséré n'a pas été trouvé.", locataireRecup);

        supprimerLocataire(locataire);
        locataireRecup = daoLocataire.findById("TEST03");
        assertNull("Le locataire n'a pas été correctement supprimé.", locataireRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerLocataire(locataire);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST04");
        Locataire locataire2 = creerLocataireTest("TEST05");
        
        ajouterLocataire(locataire);
        ajouterLocataire(locataire2);

        List<Locataire> list = daoLocataire.findAll();
        System.out.println(list);
        
        Boolean contientId1 = false;
        Boolean contientId2 = false;
        for (Locataire loc : list) {
        	System.out.println(loc.getIdLocataire());
        	
        	if (loc.getIdLocataire().equals("TEST04")) {
        		contientId1 = true;
        	}
        	if (loc.getIdLocataire().equals("TEST05")) {
        		contientId2 = true;
        	}
        		
        }
        
        supprimerLocataire(locataire2);
        supprimerLocataire(locataire);
        assertTrue("Le locataire inséré n'est pas présent dans la liste récupérée.", contientId1);
        assertTrue("Le locataire inséré n'est pas présent dans la liste récupérée.", contientId2);

        
    }
    

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        Locataire locataire = daoLocataire.findById("INVALID_ID");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", locataire);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException {
        Locataire fakeLocataire = creerLocataireTest("FAKE");
        daoLocataire.delete(fakeLocataire);

        Locataire locataire = daoLocataire.findById("FAKE");
        assertNull("La suppression d'un locataire inexistant ne devrait pas lever une erreur.", locataire);
    }
    
    @Test
    public void testFindByIdContrat() throws SQLException, IOException {
    	Locataire locataire = creerLocataireTest("TEST06");
    	// Bail dans les jeu de test
    	Bail bail = new Bail("BAI01", "2001-01-01");
    	Bail bail2 = new Bail("BAI02", "2001-01-01");
    	
    	Contracter ctr1 = new Contracter(locataire, bail, "2021-10-10", 1f);
    	
    	Contracter ctr2 = new Contracter(locataire, bail2, "2020-10-10", 1f);
    	
    	locataire.getContrats().add(ctr1);
    	locataire.getContrats().add(ctr2);
    	
    	
    	ajouterLocataire(locataire);
    	
    	
    	Locataire recup = daoLocataire.findById("TEST06");
    	
    	boolean foundBAI01 = false;
    	boolean foundBAI02 = false;

    	for (Contracter contrat : recup.getContrats()) {
    	    if ("BAI01".equals(contrat.getBail().getIdBail())) {
    	        foundBAI01 = true;
    	    }
    	    if ("BAI02".equals(contrat.getBail().getIdBail())) {
    	        foundBAI02 = true;
    	    }
    	    if (foundBAI01 && foundBAI02) {
    	        break; // Arrête la boucle si les deux IDs sont trouvés
    	    }
    	}

    	assertTrue("Le bail 'BAI01' est introuvable.", foundBAI01);
    	assertTrue("Le bail 'BAI02' est introuvable.", foundBAI02);
    	
    }
    
    @Test
    public void testUpdateLocataireWithContrats() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST07");

        // Ajout initial de contrats
        Bail bail1 = new Bail("BAI01", "2001-01-01");
        Bail bail2 = new Bail("BAI02", "2001-01-01");
        Contracter contrat1 = new Contracter(locataire, bail1, "2021-10-10", 1f);
        Contracter contrat2 = new Contracter(locataire, bail2, "2022-10-10", 1f);

        locataire.getContrats().add(contrat1);
        locataire.getContrats().add(contrat2);

        ajouterLocataire(locataire);

        // Mise à jour : modification d'un contrat et ajout d'un nouveau
        Bail bail3 = new Bail("BAI03", "2001-01-01", "2023-11-10");
        Contracter contrat3 = new Contracter(locataire, bail3, "2023-10-10", 1f);
        contrat3.ajouterDateSortie("2023-11-10");
        locataire.getContrats().clear(); // Remplace les contrats
        locataire.getContrats().add(contrat1); // Garder le premier
        locataire.getContrats().add(contrat3); // Ajouter un nouveau

        daoLocataire.update(locataire);

        Locataire locataireRecup = daoLocataire.findById("TEST07");
        assertNotNull(locataireRecup);

        // Vérification des contrats après mise à jour
        assertEquals("Nombre de contrats incorrect après mise à jour.", 2, locataireRecup.getContrats().size());
        assertTrue("Le contrat 'BAI01' devrait être présent.", 
            locataireRecup.getContrats().stream().anyMatch(c -> "BAI01".equals(c.getBail().getIdBail())));
        assertTrue("Le contrat 'BAI03' devrait être présent.", 
            locataireRecup.getContrats().stream().anyMatch(c -> "BAI03".equals(c.getBail().getIdBail())));

        supprimerLocataire(locataire);
    }
    
    @Test
    public void testSuppressionLocataireAvecDocumentsComptables() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST08");
        ajouterLocataire(locataire);

        // Simuler un document comptable lié au locataire
        Connection cn = ConnexionBD.getInstance().getConnexion();
        String insertDocComptable = "INSERT INTO sae_document_comptable (numero_document, date_document, " +
                                    "type_de_document, identifiant_locataire) VALUES ('TESTDOC01', SYSDATE, 'quittance', 'TEST08')";
        cn.createStatement().executeUpdate(insertDocComptable);
        cn.commit();

        // Pas de Expected pour pouvoir supprimer les fichier comptable fictif
        SQLException exception = assertThrows(SQLException.class, () -> {
            supprimerLocataire(locataire);
        });

        assertTrue("L'erreur de suppression n'a pas été levée ou n'est pas correcte.",
                   exception.getMessage().contains("Des paiements ou documents comptables sont liés à ce locataire"));
        
        // Nettoyage
        String deleteDocument = "DELETE FROM sae_document_comptable WHERE numero_document = 'TESTDOC01'";
        try (Statement stmt = cn.createStatement()) {
            stmt.executeUpdate(deleteDocument);
        }
        supprimerLocataire(locataire);
    }

    @Test 
    public void testAjoutLocataireAvecContratsInexistants() throws SQLException, IOException {
        Locataire locataire = creerLocataireTest("TEST09");

        // Associer un contrat avec un bail inexistant
        Bail bailInexistant = new Bail("BAI_INVALIDE", "2000-01-01");
        Contracter contrat = new Contracter(locataire, bailInexistant, "2021-10-10", 1f);
        locataire.getContrats().add(contrat);

        SQLException exception = assertThrows(SQLException.class, () -> {
            ajouterLocataire(locataire);
        });
        assertTrue("L'erreur attendue n'est pas retournée.", 
            exception.getMessage().contains("ORA-"));

        supprimerLocataire(locataire);
    }



}
