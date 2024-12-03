package modeleTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Bail;
import modele.dao.DaoBail;

public class TestDaoBail {
    
    private DaoBail daoB;
    Bail bailRecup;

    @Before
    public void setUp() {
        this.daoB = new DaoBail();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterBail(Bail bail) throws SQLException, IOException {
        daoB.create(bail);
    }

    private void supprimerBail(Bail bail) throws SQLException, IOException {
        daoB.delete(bail);
    }

    @Test
    public void testDaoBailCreate() throws SQLException, IOException {
        Bail createBail = new Bail("TEST01", "2024-01-01");
        
        ajouterBail(createBail);
        bailRecup = daoB.findById("TEST01");

        assertNotNull("Bail non trouvé après insertion.", bailRecup);
        assertEquals("L'ID du bail est incorrect.", "TEST01", bailRecup.getIdBail());
        assertEquals("La date de debut du bail est incorrecte.", "2024-01-01", bailRecup.getDateDeDebut());
        assertNull("La date de fin du bail devrait être null.", bailRecup.getDateDeFin());

        supprimerBail(createBail);
    }

    @Test
    public void testDaoBailModify() throws SQLException, IOException {
        Bail majBail = new Bail("TEST02", "2024-01-01", "2025-01-01");
        ajouterBail(majBail);

        bailRecup = daoB.findById("TEST02");
        assertNotNull("Bail non trouvé après insertion.", bailRecup);
        assertEquals("La date de debut du bail est incorrecte.", "2024-01-01", bailRecup.getDateDeDebut());

        // Modification de l'adresse
        majBail.setDateDeDebut("2024-10-01");
        daoB.update(majBail);

        bailRecup = daoB.findById("TEST02");
        assertEquals("La date de debut modifiée est incorrecte.", "2024-10-01", bailRecup.getDateDeDebut());

        supprimerBail(majBail);
    }

    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        Bail testBail = new Bail("TEST03", "2024-01-03", "2025-03-02");
        ajouterBail(testBail);

        bailRecup = daoB.findById("TEST03");
        assertNotNull("Bail non trouvé après insertion.", bailRecup);

        supprimerBail(testBail);
        bailRecup = daoB.findById("TEST03");
        assertNull("Le bail n'a pas été correctement supprimé.", bailRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerBail(testBail);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Bail findAllBail = new Bail("TEST04", "2024-21-05", "2025-01-03");
        ajouterBail(findAllBail);

        List<Bail> list = daoB.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(bail -> "TEST04".equals(bail.getIdBail()));

        assertTrue("Le bail inséré n'est pas présent dans la liste récupérée.", contientId);

        supprimerBail(findAllBail);
    }

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        Bail bail = daoB.findById("INVALID_ID");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", bail);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException, IllegalArgumentException {
        Bail fakeBail = new Bail("FAKE", "2024-01-01", "2025-01-01");
        daoB.delete(fakeBail);
        Bail bail = daoB.findById("FAKE");
        assertNull("La suppression d'un bail inexistant ne devrait pas lever une erreur.", bail);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDateInvalideCreate() throws SQLException, IOException {
    	new Bail("TEST02", "2025-01-01", "2024-01-01");
 
    	
    	
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDateInvalideSet() throws SQLException, IOException {
    	Bail majBail = new Bail("TEST02", "2025-01-01");
    	majBail.setDateDeFin("2024-01-01");
    	
    	
    }
}