package testmodeledao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Cautionnaire;
import modele.ConnexionBD;
import modele.Adresse;
import modele.dao.DaoAdresse;
import modele.dao.DaoCautionnaire;

public class TestDaoCautionnaire {

    private DaoCautionnaire daoC;
    private Cautionnaire cautionnaireRecup;
    private Adresse adresseTest;

    @Before
    public void setUp() throws SQLException, IOException {
        this.daoC = new DaoCautionnaire();  
        adresseTest = new Adresse("TESTADDR", "10 rue du Test", 12345, "TestVille");
        DaoAdresse daoA = new DaoAdresse();
        
        deleteTestData();
        
        daoA.create(adresseTest);
 
    }
    
    private void ajouterCautionnaire(Cautionnaire cautionnaire) throws SQLException, IOException {
        daoC.create(cautionnaire);
    }

    private void supprimerCautionnaire(Cautionnaire cautionnaire) throws SQLException, IOException {
        daoC.delete(cautionnaire);
    }
    
    public void deleteTestData() throws SQLException, IOException {
        String deleteCautionnaire = "DELETE FROM SAE_cautionnaire WHERE lower(id_caution) LIKE '%test%'";
        String deleteAddresses = "DELETE FROM SAE_ADRESSE WHERE lower(ID_SAE_ADRESSE) LIKE '%test%'";

        Connection cn = ConnexionBD.getInstance().getConnexion();

        try (Statement stmt = cn.createStatement()) {
        	// Commencer une transaction
        	cn.setAutoCommit(false);

            // Exécuter les suppressions
            stmt.executeUpdate(deleteCautionnaire);
            stmt.executeUpdate(deleteAddresses);
            
            // Valider les modifications
            cn.commit();
        }
        

        System.out.println("Les données contenant 'test' ont été supprimées avec succès.");

    }
    
    @Test
    public void testDaoCautionnaireCreate() throws SQLException, IOException {
        Cautionnaire createCautionnaire = new Cautionnaire(9,"NomTest","PrenomTest", "DescriptionTest");
        createCautionnaire.setAdresse(adresseTest);
        
        ajouterCautionnaire(createCautionnaire);
        cautionnaireRecup = daoC.findById("9");

        assertNotNull("Cautionnaire non trouvé après insertion.", cautionnaireRecup);
        assertEquals("L'ID du cautionnaire est incorrect.", 9, cautionnaireRecup.getIdCautionnaire());
        assertEquals("Le nom du cautionnaire est incorrect.", "NomTest", cautionnaireRecup.getNomOuOrganisme());
        assertEquals("La description du cautionnaire est incorrecte.", "DescriptionTest", cautionnaireRecup.getDescription());
        supprimerCautionnaire(createCautionnaire);
    }
    
    @Test
    public void testDaoCautionnaireModify() throws SQLException, IOException {
        Cautionnaire majCautionnaire = new Cautionnaire(9, "NomTest", "PrenomTest", "DescriptionTest");
        majCautionnaire.setAdresse(adresseTest);
        ajouterCautionnaire(majCautionnaire);

        cautionnaireRecup = daoC.findById("9");
        assertNotNull("Cautionnaire non trouvé après insertion.", cautionnaireRecup);
        assertEquals("Le nom du cautionnaire est incorrect.", "NomTest", cautionnaireRecup.getNomOuOrganisme());

        majCautionnaire.setNomOuOrganisme("NomModifie");
        daoC.update(majCautionnaire);

        cautionnaireRecup = daoC.findById("9");
        assertEquals("Le nom modifié est incorrect.", "NomModifie", cautionnaireRecup.getNomOuOrganisme());
        supprimerCautionnaire(majCautionnaire);
    }
    
    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        Cautionnaire testCautionnaire = new Cautionnaire(9, "NomTest", "PrenomTest", "DescriptionTest");
        testCautionnaire.setAdresse(adresseTest);
        ajouterCautionnaire(testCautionnaire);

        cautionnaireRecup = daoC.findById("9");
        assertNotNull("Cautionnaire non trouvé après insertion.", cautionnaireRecup);

        supprimerCautionnaire(testCautionnaire);
        cautionnaireRecup = daoC.findById("9");
        assertNull("Le cautionnaire n'a pas été correctement supprimé.", cautionnaireRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerCautionnaire(testCautionnaire);
    }
    
    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Cautionnaire findAllCautionnaire = new Cautionnaire(9, "NomTest", "PrenomTest", "DescriptionTest");
        findAllCautionnaire.setAdresse(adresseTest);
        ajouterCautionnaire(findAllCautionnaire);

        List<Cautionnaire> list = daoC.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(cautionnaire -> 9 == cautionnaire.getIdCautionnaire());

        assertTrue("Le cautionnaire inséré n'est pas présent dans la liste récupérée.", contientId);

        supprimerCautionnaire(findAllCautionnaire);
    }

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        Cautionnaire cautionnaire = daoC.findById("100");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", cautionnaire);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException, IllegalArgumentException {
        Cautionnaire fakeCautionnaire = new Cautionnaire(8888, "FauxNom", "FauxPrenom", "DescriptionFausse");
        fakeCautionnaire.setAdresse(adresseTest);
        daoC.delete(fakeCautionnaire);
        Cautionnaire cautionnaire = daoC.findById("8888");
        assertNull("La suppression d'un cautionnaire inexistant ne devrait pas lever une erreur.", cautionnaire);
    }


}
