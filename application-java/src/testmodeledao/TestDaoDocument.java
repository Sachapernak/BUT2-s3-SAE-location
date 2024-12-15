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

import modele.Document;
import modele.dao.DaoDocument;

public class TestDaoDocument {
    
    private DaoDocument daoDocu;
    Document documentRecup;

    @Before
    public void setUp() {
        this.daoDocu = new DaoDocument();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterDocument(Document Docu) throws SQLException, IOException {
    	daoDocu.create(Docu);
    }

    private void supprimerDocument(Document Docu) throws SQLException, IOException {
    	daoDocu.delete(Docu);
    }
    
    @Test
    public void testDaoDocumentCreate() throws SQLException, IOException {
        Document document = new Document("BAI04","2024-01-01", "docu", "url");
        ajouterDocument(document);
    	
    	documentRecup = daoDocu.findById("BAI04","2024-01-01");
        
        assertNotNull("Le document n'a pas été ajouté après insertion ", documentRecup);
        assertEquals("L'ID du document est incorrect.", "BAI04", documentRecup.getIdBail());
        assertEquals("La date du document est incorrecte.", "2024-01-01",documentRecup.getDateDocument());
        assertEquals("Le type de document est incorrecte.", "docu", documentRecup.getTypeDocument());
        assertEquals("L'url du document est incorrecte.", "url", documentRecup.getUrlDocument());

        supprimerDocument(documentRecup);
    }
    
    @Test
    public void testDaoDocumentDelete() throws SQLException, IOException {
    	Document document = new Document("BAI01", "2024-08-02", "docu", "url");
    	ajouterDocument(document);
    	
    	supprimerDocument(document);
    	documentRecup = daoDocu.findById("BAI01", "2024-08-02");
        assertNull("Le document n'a pas été supprimé.", documentRecup);
    }
    
    @Test
    public void testDaoDocumentModify() throws SQLException, IOException {
    	Document document = new Document("BAI02", "2024-08-02",  "docu", "url");
    	ajouterDocument(document);
        
    	documentRecup = daoDocu.findById("BAI02","2024-08-02");
        
    	documentRecup.setTypeDocument("docus");
        
        daoDocu.update(documentRecup);

        documentRecup = daoDocu.findById("BAI02", "2024-08-02");
        
        assertEquals("Le type de document modifié est incorrecte. ", "docus", documentRecup.getTypeDocument());
        
        supprimerDocument(documentRecup);
    }
    
    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Document findAllDocu = new Document("BAI03", "2024-10-02", "docu", "url");
        ajouterDocument(findAllDocu);
        List<Document> list = daoDocu.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(docu -> "BAI03".equals(docu.getIdBail()) && "2024-10-02".equals(docu.getDateDocument()));

        assertTrue("Le document inséré n'est pas présent dans la liste récupérée.", contientId);

        supprimerDocument(findAllDocu);
    }
    
    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        Document document = daoDocu.findById("INVALID_ID", "2024-01-01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", document);
    }
    
    @Test
    public void testFindByIdBail() throws SQLException, IOException {
        Document document = new Document("BAI01", "2024-04-01", "docu", "url");
        ajouterDocument(document);

        List<Document> documents = daoDocu.findByIdBail("BAI01");

        assertNotNull("La méthode findByIdBail n'a pas retourné de résultat.", documents);
        assertTrue("Le document recherché n'a pas été trouvé.", documents.stream().anyMatch(l -> "BAI01".equals(l.getIdBail())));

        supprimerDocument(document);
    }
}