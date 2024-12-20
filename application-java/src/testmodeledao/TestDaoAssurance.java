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

import modele.Assurance;
import modele.dao.DaoAssurance;

public class TestDaoAssurance {
    
    private DaoAssurance daoA;
    Assurance assuranceRecup;

    @Before
    public void setUp() {
        this.daoA = new DaoAssurance();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterAssurance(Assurance assurance) throws SQLException, IOException {
        daoA.create(assurance);
    }

    private void supprimerAssurance(Assurance assurance) throws SQLException, IOException {
        daoA.delete(assurance);
    }

    @Test
    public void testDaoAssuranceCreate() throws SQLException, IOException {
        Assurance createAssurance = new Assurance("61970238", 2022, "Protection Juridique");
        
        ajouterAssurance(createAssurance);
        assuranceRecup = daoA.findById("61970238", "2022");

        assertNotNull("Adresse non trouvée après insertion.", assuranceRecup);
        assertEquals("L'ID de l'adresse est incorrect.", "61970238", assuranceRecup.getNumeroContrat());
        assertEquals("L'adresse est incorrecte.", 2022, assuranceRecup.getAnneeContrat());
        assertEquals("Le code postal est incorrect.", "Protection Juridique", assuranceRecup.getTypeContrat());


        supprimerAssurance(createAssurance);
    }

    @Test
    public void testDaoAssuranceModify() throws SQLException, IOException {
        Assurance majAssurance = new Assurance("61970163", 2018, "Prime");
        ajouterAssurance(majAssurance);

        assuranceRecup = daoA.findById("61970163", "2018");
        assertNotNull("Adresse non trouvée après insertion.", assuranceRecup);
        assertEquals("L'adresse initiale est incorrecte.", "Prime", assuranceRecup.getTypeContrat());

        // Modification de TypeContrat
        majAssurance.setTypeContrat("Protection Juridique");
        daoA.update(majAssurance);

        assuranceRecup = daoA.findById("61970163", "2018");
        assertEquals("L'adresse modifiée est incorrecte.", "Protection Juridique", assuranceRecup.getTypeContrat());

        supprimerAssurance(majAssurance);
    }

    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        Assurance testAssurance = new Assurance("61970146", 2019, "Prime");
        ajouterAssurance(testAssurance);

        assuranceRecup = daoA.findById("61970146", "2019");
        assertNotNull("Adresse non trouvée après insertion.", assuranceRecup);

        supprimerAssurance(testAssurance);
        assuranceRecup = daoA.findById("61970146", "2019");
        assertNull("L'adresse n'a pas été correctement supprimée.", assuranceRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerAssurance(testAssurance);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Assurance findAllAssurance = new Assurance("61970175", 2005, "Protection Juridique");
        ajouterAssurance(findAllAssurance);

        List<Assurance> list = daoA.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(adresse -> "61970175".equals(adresse.getNumeroContrat()) && 2005 == adresse.getAnneeContrat());

        assertTrue("L'adresse insérée n'est pas présente dans la liste récupérée.", contientId);

        supprimerAssurance(findAllAssurance);
    }

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        Assurance assurance = daoA.findById("INVALID_ID", "000000");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", assurance);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException {
        Assurance fakeAssurance = new Assurance("FAKE", 0, "No");
        daoA.delete(fakeAssurance);
        Assurance assurance = daoA.findById("FAKE", "0");
        assertNull("La suppression d'une adresse inexistante ne devrait pas lever une erreur.", assurance);
    }
}
