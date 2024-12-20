package testmodeledao;

import static org.junit.Assert.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ChargeFixe;
import modele.ConnexionBD;
import modele.DocumentComptable;
import modele.TypeDoc;
import modele.dao.DaoChargeFixe;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoEntreprise;

public class TestDaoChargeFixe {

    private DaoChargeFixe daoChargeFixe;
    private DaoDocumentComptable daoDocumentComptable;
    private DocumentComptable docTest;

    @Before
    public void setUp() throws SQLException, IOException {
        this.daoChargeFixe = new DaoChargeFixe();
        this.daoDocumentComptable = new DaoDocumentComptable();

        assertNotNull("La connexion BD ne devrait pas être null", ConnexionBD.getInstance().getConnexion());

        String numeroDocTest = "DOC_CFIXE_TEST001";
        String dateDocTest = "2025-01-01"; 
        TypeDoc typeDocTest = TypeDoc.FACTURE; 

        docTest = new DocumentComptable(
            numeroDocTest,
            dateDocTest,
            typeDocTest,
            new BigDecimal("100.00"),
            "fichier_test_cf.pdf"
        );
        
        docTest.setEntreprise(new DaoEntreprise().findById("ENTR001"));
        
        daoDocumentComptable.create(docTest);
    }

    @After
    public void tearDown() throws SQLException, IOException {
        // Nettoyage des données de test
        deleteTestData();

        if (docTest != null) {
            daoDocumentComptable.delete(docTest);
        }
    }

    @Test
    public void testCreateAndFindById() throws SQLException, IOException {
        ChargeFixe cf = new ChargeFixe(
            "TEST_CF001", 
            "2025-12-31", 
            "Loyer", 
            new BigDecimal("800.00"), 
            docTest.getNumeroDoc(), 
            docTest.getDateDoc()
        );

        daoChargeFixe.create(cf);

        ChargeFixe cfRecup = daoChargeFixe.findById("TEST_CF001");
        assertNotNull("La charge fixe TEST_CF001 devrait être créée et récupérée.", cfRecup);
        assertEquals("Le type de charge est incorrect.", "Loyer", cfRecup.getType());
        assertEquals("Le montant est incorrect.", 0, new BigDecimal("800.00").compareTo(cfRecup.getMontant()));
    }

    @Test
    public void testUpdate() throws SQLException, IOException {
        ChargeFixe cf = new ChargeFixe(
            "TEST_CF002", 
            "2025-12-30", 
            "Assurance", 
            new BigDecimal("300.00"), 
            docTest.getNumeroDoc(), 
            docTest.getDateDoc()
        );
        daoChargeFixe.create(cf);

        // Mise à jour du montant
        cf.setMontant(new BigDecimal("350.00"));
        daoChargeFixe.update(cf);

        ChargeFixe cfRecup = daoChargeFixe.findById("TEST_CF002");
        assertNotNull("La charge fixe TEST_CF002 devrait exister après update.", cfRecup);
        assertEquals("Le montant mis à jour est incorrect.", 0, new BigDecimal("350.00").compareTo(cfRecup.getMontant()));
    }

    @Test
    public void testDelete() throws SQLException, IOException {
        ChargeFixe cf = new ChargeFixe(
            "TEST_CF003", 
            "2025-12-29", 
            "Internet", 
            new BigDecimal("50.00"), 
            docTest.getNumeroDoc(), 
            docTest.getDateDoc()
        );
        daoChargeFixe.create(cf);

        daoChargeFixe.delete(cf);

        ChargeFixe cfRecup = daoChargeFixe.findById("TEST_CF003");
        assertNull("La charge fixe TEST_CF003 devrait être supprimée.", cfRecup);
    }

    @Test
    public void testFindAll() throws SQLException, IOException {
        // Insérer une charge fixe de test
        ChargeFixe cf = new ChargeFixe(
            "TEST_CF004", 
            "2025-12-28", 
            "Entretien", 
            new BigDecimal("90.00"), 
            docTest.getNumeroDoc(), 
            docTest.getDateDoc()
        );
        daoChargeFixe.create(cf);

        List<ChargeFixe> liste = daoChargeFixe.findAll();
        assertNotNull("La liste des ChargeFixe ne devrait pas être null.", liste);

        boolean contientCF004 = liste.stream().anyMatch(c -> "TEST_CF004".equals(c.getId()));
        assertTrue("La charge fixe TEST_CF004 devrait être présente dans la liste.", contientCF004);
    }

    @Test
    public void testFindByIdDocumentComptable() throws SQLException, IOException {
        // Création d'une nouvelle charge pour le même document
        ChargeFixe cf = new ChargeFixe(
            "TEST_CF005", 
            "2025-12-27", 
            "Energie", 
            new BigDecimal("120.00"), 
            docTest.getNumeroDoc(), 
            docTest.getDateDoc()
        );
        daoChargeFixe.create(cf);

        ChargeFixe cfRecup = daoChargeFixe.findByIdDocumentComptable(docTest.getNumeroDoc(), docTest.getDateDoc());
        assertNotNull("Une ChargeFixe liée à " + docTest.getNumeroDoc() + " devrait exister.", cfRecup);
        assertEquals("L'ID de la ChargeFixe trouvée est incorrecte.", "TEST_CF005", cfRecup.getId());
    }

    /**
     * Méthode utilitaire pour supprimer les données de test créées dans la base de données.
     */
    public void deleteTestData() throws SQLException, IOException {
        String deleteCF = "DELETE FROM sae_charge_cf WHERE id_charge_cf LIKE 'TEST_CF%'";
        Connection cn = ConnexionBD.getInstance().getConnexion();

        try (Statement stmt = cn.createStatement()) {
            cn.setAutoCommit(false);
            stmt.executeUpdate(deleteCF);
            cn.commit();
        } catch (SQLException e) {
            cn.rollback();
            throw e;
        }
    }

}
