package testmodeledao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Adresse;
import modele.dao.DaoAdresse;

public class TestDaoAdresse {
    
    private DaoAdresse daoA;
    Adresse adresseRecup;

    @Before
    public void setUp() {
        this.daoA = new DaoAdresse();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterAdresse(Adresse adresse) throws SQLException, IOException {
        daoA.create(adresse);
    }

    private void supprimerAdresse(Adresse adresse) throws SQLException, IOException {
        daoA.delete(adresse);
    }

    @Test
    public void testDaoAdresseCreate() throws SQLException, IOException {
        Adresse createAdresse = new Adresse("TEST01", "10 rue du test", 1, "testVille");
        
        ajouterAdresse(createAdresse);
        adresseRecup = daoA.findById("TEST01");

        assertNotNull("Adresse non trouvée après insertion.", adresseRecup);
        assertEquals("L'ID de l'adresse est incorrect.", "TEST01", adresseRecup.getIdAdresse());
        assertEquals("L'adresse est incorrecte.", "10 rue du test", adresseRecup.getAdressePostale());
        assertEquals("Le code postal est incorrect.", 1, adresseRecup.getCodePostal());
        assertEquals("La ville est incorrecte.", "testVille", adresseRecup.getVille());
        assertNull("Le complément d'adresse devrait être null.", adresseRecup.getComplementAdresse());

        supprimerAdresse(createAdresse);
    }

    @Test
    public void testDaoAdresseModify() throws SQLException, IOException {
        Adresse majAdresse = new Adresse("TEST02", "20 rue du test", 2, "testVille2", "TestComplement2");
        ajouterAdresse(majAdresse);

        adresseRecup = daoA.findById("TEST02");
        assertNotNull("Adresse non trouvée après insertion.", adresseRecup);
        assertEquals("L'adresse initiale est incorrecte.", "20 rue du test", adresseRecup.getAdressePostale());

        // Modification de l'adresse
        majAdresse.setAdresse("20 avenue du test");
        daoA.update(majAdresse);

        adresseRecup = daoA.findById("TEST02");
        assertEquals("L'adresse modifiée est incorrecte.", "20 avenue du test", adresseRecup.getAdressePostale());

        supprimerAdresse(majAdresse);
    }

    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        Adresse testAdresse = new Adresse("TEST03", "30 rue du test", 3, "testVille3", "TestComplement3");
        ajouterAdresse(testAdresse);

        adresseRecup = daoA.findById("TEST03");
        assertNotNull("Adresse non trouvée après insertion.", adresseRecup);

        supprimerAdresse(testAdresse);
        adresseRecup = daoA.findById("TEST03");
        assertNull("L'adresse n'a pas été correctement supprimée.", adresseRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerAdresse(testAdresse);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Adresse findAllAdresse = new Adresse("TEST04", "40 rue du test", 4, "testVille4", "TestComplement4");
        ajouterAdresse(findAllAdresse);

        List<Adresse> list = daoA.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(adresse -> "TEST04".equals(adresse.getIdAdresse()));

        assertTrue("L'adresse insérée n'est pas présente dans la liste récupérée.", contientId);

        supprimerAdresse(findAllAdresse);
    }

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        Adresse adresse = daoA.findById("INVALID_ID");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", adresse);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException {
        Adresse fakeAdresse = new Adresse("FAKE", "Non Existent", 0, "Nowhere");
        daoA.delete(fakeAdresse);
        Adresse adresse = daoA.findById("FAKE");
        assertNull("La suppression d'une adresse inexistante ne devrait pas lever une erreur.", adresse);
    }
}
