package testmodeledao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.DocumentComptable;
import modele.TypeDoc;
import modele.dao.DaoDocumentComptable;

public class TestDaoDocumentComptable {

    private DaoDocumentComptable dao;
    private DocumentComptable testDoc;

    @Before
    public void setUp() throws Exception {
        dao = new DaoDocumentComptable();

        // Insertion d'un document comptable pour les tests
        testDoc = new DocumentComptable(
                "TEST_DOC001", // numeroDoc
                "2023-12-01",  // dateDoc
                TypeDoc.QUITTANCE, 
                new BigDecimal("100.00"), 
                "test_facture.pdf"
        );
        testDoc.setRecuperableLoc(true);
       
        testDoc.setEntreprise(null);
        dao.delete(testDoc);
        
        dao.create(testDoc);
    }

    @After
    public void tearDown() throws Exception {
        // Suppression des données insérées pour les tests
        try {
            dao.delete(testDoc);
        } catch (SQLException | IOException ignored) {
            // Les données peuvent déjà avoir été supprimées, ignorer les exceptions
        }
    }

    @Test
    public void testCreate() throws SQLException, IOException {
        DocumentComptable newDoc = new DocumentComptable(
                "TEST_DOC002", // numeroDoc
                "2023-12-15",  // dateDoc
                TypeDoc.QUITTANCE, 
                new BigDecimal("150.00"), 
                "test_quittance.pdf"
        );
        newDoc.setRecuperableLoc(false);

        dao.create(newDoc);

        DocumentComptable retrievedDoc = dao.findById("TEST_DOC002", "2023-12-15");

        assertNotNull("Le document inséré n'a pas été trouvé.", retrievedDoc);
        assertEquals("Le numéro du document est incorrect.", "TEST_DOC002", retrievedDoc.getNumeroDoc());
        assertEquals("Le montant est incorrect.", 0, newDoc.getMontant().compareTo(retrievedDoc.getMontant()));

        // Nettoyage
        dao.delete(newDoc);
    }

    @Test
    public void testUpdate() throws SQLException, IOException {
        testDoc.setMontant(new BigDecimal("200.00"));
        testDoc.setFichierDoc("updated_test_facture.pdf");
        dao.update(testDoc);

        DocumentComptable updatedDoc = dao.findById("TEST_DOC001", "2023-12-01");

        assertNotNull("Le document mis à jour n'a pas été trouvé.", updatedDoc);
        assertEquals("Le montant mis à jour est incorrect.", 0, new BigDecimal("200.00").compareTo(updatedDoc.getMontant()));
        assertEquals("Le fichier mis à jour est incorrect.", "updated_test_facture.pdf", updatedDoc.getFichierDoc());
    }

    @Test
    public void testDelete() throws SQLException, IOException {
        DocumentComptable toDelete = new DocumentComptable(
                "TEST_DOC003", // numeroDoc
                "2023-12-20",  // dateDoc
                TypeDoc.QUITTANCE, 
                new BigDecimal("300.00"), 
                "test_devis.pdf"
        );
        dao.create(toDelete);

        dao.delete(toDelete);

        DocumentComptable deletedDoc = dao.findById("TEST_DOC003", "2023-12-20");

        assertNull("Le document supprimé est toujours récupérable.", deletedDoc);
    }

    @Test
    public void testFindById() throws SQLException, IOException {
        DocumentComptable retrievedDoc = dao.findById("TEST_DOC001", "2023-12-01");

        assertNotNull("Le document recherché n'a pas été trouvé.", retrievedDoc);
        assertEquals("Le type du document est incorrect.", TypeDoc.QUITTANCE, retrievedDoc.getTypeDoc());
        assertEquals("Le fichier associé est incorrect.", "test_facture.pdf", retrievedDoc.getFichierDoc());
    }

    @Test
    public void testFindAll() throws SQLException, IOException {
        List<DocumentComptable> allDocs = dao.findAll();

        assertNotNull("La liste des documents est null.", allDocs);
        assertTrue("La liste des documents est vide.", allDocs.size() > 0);

        boolean foundTestDoc = allDocs.stream()
                .anyMatch(doc -> "TEST_DOC001".equals(doc.getNumeroDoc()) && "2023-12-01".equals(doc.getDateDoc()));

        assertTrue("Le document de test n'a pas été trouvé dans la liste.", foundTestDoc);
    }
}
