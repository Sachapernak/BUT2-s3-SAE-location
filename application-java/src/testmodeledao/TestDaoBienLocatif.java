package testmodeledao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.Adresse;
import modele.Batiment;
import modele.BienLocatif;
import modele.ConnexionBD;
import modele.TypeDeBien;
import modele.dao.DaoAdresse;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;

public class TestDaoBienLocatif {

    private DaoBienLocatif daoBien;
    private DaoBatiment daoBatiment;
    private BienLocatif bienRecup;
    private Adresse adresse;

    @Before
    public void setUp() throws SQLException, IOException {
        this.daoBien = new DaoBienLocatif();
        this.daoBatiment = new DaoBatiment();
        adresse = new DaoAdresse().findById("ADDR001");
    }

    @After
    public void tearDown() throws SQLException, IOException {
    	deleteTestData();
    }

    @Test
    public void testDaoBienLocatifCreateAndDelete() throws SQLException, IOException {
        Batiment batiment = new Batiment("BAT_TEST", adresse);
        daoBatiment.create(batiment);

        BienLocatif bienCreate = new BienLocatif("TEST001", TypeDeBien.LOGEMENT, 40, 2, new BigDecimal("1000.00"), batiment);
        daoBien.create(bienCreate);

        bienRecup = daoBien.findById("TEST001");
        assertNotNull("Le bien locatif TEST001 devrait être créé et récupéré.", bienRecup);
        assertEquals("Le type de bien est incorrect.", TypeDeBien.LOGEMENT, bienRecup.getType());
        assertEquals("La surface est incorrecte.", 40, bienRecup.getSurface());

        daoBien.delete(bienCreate);
        bienRecup = daoBien.findById("TEST001");
        assertNull("Le bien locatif TEST001 devrait être supprimé.", bienRecup);
    }

    @Test
    public void testDaoBienLocatifUpdate() throws SQLException, IOException {
        Batiment batiment = new Batiment("BAT_TEST2", adresse);
        daoBatiment.create(batiment);

        BienLocatif bienUpdate = new BienLocatif("TEST002", TypeDeBien.GARAGE, 25, 0, new BigDecimal("500.00"), batiment);
        daoBien.create(bienUpdate);

        bienRecup = daoBien.findById("TEST002");
        assertNotNull("Le bien locatif TEST002 devrait être créé et récupéré.", bienRecup);

        // Mise à jour
        bienUpdate = new BienLocatif("TEST002", TypeDeBien.LOGEMENT, 30, 1, new BigDecimal("750.00"), batiment);
        daoBien.update(bienUpdate);

        bienRecup = daoBien.findById("TEST002");
        assertEquals("Le type de bien mis à jour est incorrect.", TypeDeBien.LOGEMENT, bienRecup.getType());
        assertEquals("La surface mise à jour est incorrecte.", 30, bienRecup.getSurface());
        assertEquals("Le loyer mis à jour est incorrect.", 0 ,new BigDecimal("750.00").compareTo(bienRecup.getLoyerBase()));

        daoBien.delete(bienUpdate);
    }

    @Test
    public void testDaoBienLocatifFindAllWithAdditionalData() throws SQLException, IOException {
        Batiment batiment = new Batiment("BAT_TEST3", adresse);
        daoBatiment.create(batiment);

        BienLocatif bienFindAll1 = new BienLocatif("TEST003", TypeDeBien.LOGEMENT, 50, 3, new BigDecimal("1500.00"), batiment);
        BienLocatif bienFindAll2 = new BienLocatif("TEST004", TypeDeBien.GARAGE, 20, 0, new BigDecimal("800.00"), batiment);
        daoBien.create(bienFindAll1);
        daoBien.create(bienFindAll2);

        List<BienLocatif> biens = daoBien.findAll();
        assertNotNull("La liste des biens locatifs ne devrait pas être null.", biens);

        boolean contientTest003 = biens.stream()
                                       .anyMatch(bien -> "TEST003".equals(bien.getIdentifiantLogement()));
        boolean contientTest004 = biens.stream()
                                       .anyMatch(bien -> "TEST004".equals(bien.getIdentifiantLogement()));

        assertTrue("Le bien locatif TEST003 devrait être présent.", contientTest003);
        assertTrue("Le bien locatif TEST004 devrait être présent.", contientTest004);

        daoBien.delete(bienFindAll1);
        daoBien.delete(bienFindAll2);
    }

    @Test
    public void testDaoBienLocatifFindByIdInvalid() throws SQLException, IOException {
        BienLocatif bien = daoBien.findById("INVALID_ID");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", bien);
    }

    @Test
    public void testDaoBienLocatifDeleteRaisesException() throws SQLException, IOException {
        BienLocatif bienLinked = daoBien.findById("LOG001");
        assertNotNull("Le bien locatif LOG001 devrait exister.", bienLinked);

        try {
            daoBien.delete(bienLinked);
            fail("Une exception devrait être levée si le bien est lié à des baux ou des documents comptables.");
        } catch (SQLException e) {
            assertTrue("Le message d'erreur devrait mentionner les baux liés.",
                e.getMessage().contains("possède des baux liés"));
        }
    }
    
    public void deleteTestData() throws SQLException, IOException {
        String deleteBien = "delete from sae_bien_locatif where identifiant_logement like '%TEST%'";
        String deleteBat = "delete from sae_batiment where identifiant_batiment like '%TEST%'";
       

        Connection cn = ConnexionBD.getInstance().getConnexion();

        try (Statement stmt = cn.createStatement()) {
        	// Commencer une transaction
        	cn.setAutoCommit(false);

            // Exécuter les suppressions
            stmt.executeUpdate(deleteBien);
            stmt.executeUpdate(deleteBat);
            
            // Valider les modifications
            cn.commit();
        }
    }
}

