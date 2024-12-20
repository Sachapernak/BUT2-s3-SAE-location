package testmodeledao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ChargeIndex;
import modele.ConnexionBD;
import modele.DocumentComptable;
import modele.TypeDoc; // Assurez-vous que ce type est disponible
import modele.dao.DaoChargeIndex;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoEntreprise;

public class TestDaoChargeIndex {

    private DaoChargeIndex daoChargeIndex;
    private DaoDocumentComptable daoDocumentComptable;
    private DocumentComptable docTest; // Document comptable dédié aux tests

    @Before
    public void setUp() throws SQLException, IOException {
        this.daoChargeIndex = new DaoChargeIndex();
        this.daoDocumentComptable = new DaoDocumentComptable();

        // Vérification de la connexion
        assertNotNull("La connexion BD ne devrait pas être null", ConnexionBD.getInstance().getConnexion());

        // Création d'un document comptable spécifiquement pour les tests
        // On choisit un numero_document et une date_document non utilisés pour éviter les conflits.
        String numeroDocTest = "DOC_TEST001";
        String dateDocTest = "2025-01-01"; 
        TypeDoc typeDocTest = TypeDoc.FACTURE; // Utilisez un typeDoc existant dans votre enum

        docTest = new DocumentComptable(
            numeroDocTest,
            dateDocTest,
            typeDocTest,
            new BigDecimal("100.00"),
            "fichier_test.pdf"
        );
        
        docTest.setEntreprise(new DaoEntreprise().findById("ENTR001"));
        
        // On insère ce document dans la BD via son DAO
        daoDocumentComptable.create(docTest);
    }

    @After
    public void tearDown() throws SQLException, IOException {
        // Suppression des données de test créées
        deleteTestData();

        // Suppression du document comptable de test
        if (docTest != null) {
            daoDocumentComptable.delete(docTest);
        }
    }

    @Test
    public void testCreateAndFindById() throws SQLException, IOException {
        // On crée un nouveau ChargeIndex lié au docTest créé dans setUp()
        ChargeIndex ci = new ChargeIndex(
            "TEST_CI001",
            "2025-12-31",  // Date de releve
            "Eau",
            new BigDecimal("200.000"),
            new BigDecimal("1.50000"),
            new BigDecimal("60.00"),
            docTest.getNumeroDoc(),
            docTest.getDateDoc()
        );

        daoChargeIndex.create(ci);

        // On le récupère par son ID
        ChargeIndex ciRecup = daoChargeIndex.findById("TEST_CI001", "2025-12-31");
        assertNotNull("Le ChargeIndex TEST_CI001 devrait être créé et récupéré.", ciRecup);
        assertEquals("Le type de charge est incorrect.", "Eau", ciRecup.getType());
        assertEquals("La valeur compteur est incorrecte.", 0, new BigDecimal("200.000").compareTo(ciRecup.getValeurCompteur()));
    }

    @Test
    public void testUpdate() throws SQLException, IOException {
        ChargeIndex ci = new ChargeIndex(
            "TEST_CI002",
            "2025-12-30",
            "Eau",
            new BigDecimal("150.000"),
            new BigDecimal("1.30000"),
            new BigDecimal("50.00"),
            docTest.getNumeroDoc(),
            docTest.getDateDoc()
        );
        daoChargeIndex.create(ci);

        // On le met à jour
        ci.setValeurCompteur(new BigDecimal("155.000"));
        ci.setCoutVariable(new BigDecimal("1.40000"));
        daoChargeIndex.update(ci);

        // On récupère et vérifie
        ChargeIndex ciRecup = daoChargeIndex.findById("TEST_CI002", "2025-12-30");
        assertNotNull("Le ChargeIndex TEST_CI002 devrait exister après update.", ciRecup);
        assertEquals("La valeur compteur mise à jour est incorrecte.", 0, new BigDecimal("155.000").compareTo(ciRecup.getValeurCompteur()));
        assertEquals("Le coût variable mis à jour est incorrect.", 0, new BigDecimal("1.40000").compareTo(ciRecup.getCoutVariable()));
    }

    @Test
    public void testDelete() throws SQLException, IOException {
        ChargeIndex ci = new ChargeIndex(
            "TEST_CI003",
            "2025-12-29",
            "Electricite",
            new BigDecimal("300.000"),
            new BigDecimal("2.00000"),
            new BigDecimal("70.00"),
            docTest.getNumeroDoc(),
            docTest.getDateDoc()
        );
        daoChargeIndex.create(ci);

        // On le supprime
        daoChargeIndex.delete(ci);

        // On vérifie qu'il n'existe plus
        ChargeIndex ciRecup = daoChargeIndex.findById("TEST_CI003", "2025-12-29");
        assertNull("Le ChargeIndex TEST_CI003 devrait être supprimé.", ciRecup);
    }

    @Test
    public void testFindAll() throws SQLException, IOException {
        // Selon le script initial, CI001 devrait exister
        List<ChargeIndex> liste = daoChargeIndex.findAll();
        assertNotNull("La liste des ChargeIndex ne devrait pas être null.", liste);

        boolean contientCI001 = liste.stream().anyMatch(ci -> "CI001".equals(ci.getId()));
        assertTrue("Le ChargeIndex CI001 devrait être présent dans la liste.", contientCI001);
    }

    @Test
    public void testFindByIdDocumentComptable() throws SQLException, IOException {
        // Selon les données initiales, CI001 est lié à DOC002 (2023-08-10)
        ChargeIndex ciRecup = daoChargeIndex.findByIdDocumentComptable("DOC002", "2023-08-10");
        assertNotNull("Un ChargeIndex lié à DOC002 devrait exister.", ciRecup);
        assertEquals("L'ID du ChargeIndex trouvé est incorrecte.", "CI001", ciRecup.getId());
    }

    @Test
    public void testFindAllSameId() throws SQLException, IOException {
        ChargeIndex ci1 = new ChargeIndex(
            "TEST_CI004",
            "2025-12-28",
            "Gaz",
            new BigDecimal("500.000"),
            new BigDecimal("2.50000"),
            new BigDecimal("80.00"),
            docTest.getNumeroDoc(),
            docTest.getDateDoc()
        );
        daoChargeIndex.create(ci1);


        
        String numeroDocTest = "DOC_TEST003";
        String dateDocTest = "2025-01-01"; 
        TypeDoc typeDocTest = TypeDoc.FACTURE;

        DocumentComptable docTest2 = new DocumentComptable(
            numeroDocTest,
            dateDocTest,
            typeDocTest,
            new BigDecimal("100.00"),
            "fichier_test.pdf"
        );
        
        ChargeIndex ci2 = new ChargeIndex(
                "TEST_CI005",
                "2025-12-27",
                "Gaz",
                new BigDecimal("520.000"),
                new BigDecimal("2.50000"),
                new BigDecimal("80.00"),
                docTest2.getNumeroDoc(),
                docTest2.getDateDoc()
            );
        
        docTest2.setEntreprise(new DaoEntreprise().findById("ENTR001"));
        daoDocumentComptable.create(docTest2);
        
        daoChargeIndex.create(ci2);

        List<ChargeIndex> listeSame = daoChargeIndex.findAllSameId("TEST_CI004");
        daoDocumentComptable.delete(docTest2);
        assertNotNull(listeSame);
        assertTrue("On devrait trouver au moins un ChargeIndex pour TEST_CI004", listeSame.size() >= 1);
    }
    
    @Test
    public void testCreateAvecChargePreced() throws SQLException, IOException {
    	

    	// Creation de la charge 1
        ChargeIndex ci1 = new ChargeIndex(
                "TEST_CI006",
                "2025-12-28",
                "Gaz",
                new BigDecimal("500.000"),
                new BigDecimal("2.50000"),
                new BigDecimal("80.00"),
                docTest.getNumeroDoc(),
                docTest.getDateDoc()
            );
        

        // Creation du doc2
        String numeroDocTest = "DOC_TEST003";
        String dateDocTest = "2025-01-01"; 
        TypeDoc typeDocTest = TypeDoc.FACTURE;

        DocumentComptable docTest2 = new DocumentComptable(
                numeroDocTest,
                dateDocTest,
                typeDocTest,
                new BigDecimal("100.00"),
                "fichier_test.pdf"
            );
        
        docTest2.setEntreprise(new DaoEntreprise().findById("ENTR001"));
            
        
        // Creation de la charge 2
        ChargeIndex ci2 = new ChargeIndex(
                "TEST_CI006",
                "2026-01-20",
                "Gaz",
                new BigDecimal("520.000"),
                new BigDecimal("2.50000"),
                new BigDecimal("80.00"),
                docTest2.getNumeroDoc(),
                docTest2.getDateDoc()
            );
        
        ci2.setDateRelevePrecedent(ci1.getDateDeReleve());
        
        
        // Insertion du doc 2
        daoDocumentComptable.create(docTest2);
        
        // Insertion des charges
        daoChargeIndex.create(ci1);
        daoChargeIndex.create(ci2);
        
        // Verification
        ChargeIndex recup = daoChargeIndex.findById(ci2.getId(), ci2.getDateDeReleve());
        
        assertEquals(ci1.getDateDeReleve() ,recup.getDateRelevePrecedent());
        assertEquals(ci1.getId(), recup.getId());
        
        daoDocumentComptable.delete(docTest2);
    	
    }
    
    @Test (expected = SQLException.class)
    public void testExceptionDateIntrouvable() throws SQLException, IOException {
    	// Creation de la charge
        ChargeIndex ci1 = new ChargeIndex(
                "TEST_CI006",
                "2025-12-28",
                "Gaz",
                new BigDecimal("500.000"),
                new BigDecimal("2.50000"),
                new BigDecimal("80.00"),
                docTest.getNumeroDoc(),
                docTest.getDateDoc()
            );
        
        // date précedente non-existante
        ci1.setDateRelevePrecedent("2000-01-01");
        
        // Insertion des charges
        daoChargeIndex.create(ci1);
        

    }

    /**
     * Méthode utilitaire pour supprimer les données de test créées dans la base de données.
     */
    public void deleteTestData() throws SQLException, IOException {
        String deleteCI = "DELETE FROM sae_charge_index WHERE id_charge_index LIKE 'TEST_CI%'";
        Connection cn = ConnexionBD.getInstance().getConnexion();

        try (Statement stmt = cn.createStatement()) {
            cn.setAutoCommit(false);
            stmt.executeUpdate(deleteCI);
            cn.commit();
        } catch (SQLException e) {
            cn.rollback();
            throw e;
        }
    }
    
 
}
