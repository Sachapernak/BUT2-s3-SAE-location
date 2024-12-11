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

import modele.Loyer;
import modele.dao.DaoLoyer;

public class TestDaoLoyer {

    private DaoLoyer daoLoyer;
    Loyer loyerRecup;

    @Before
    public void setUp() {
        this.daoLoyer = new DaoLoyer();
    }

    private void ajouterLoyer(Loyer loyer) throws SQLException, IOException {
        daoLoyer.create(loyer);
    }

    private void supprimerLoyer(Loyer loyer) throws SQLException, IOException {
        daoLoyer.delete(loyer);
    }

    @Test
    public void testDaoLoyerCreate() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOGBIENTEST01", "2024-01-01", 800.50);
        ajouterLoyer(loyer);

        loyerRecup = daoLoyer.findById("LOGBIENTEST01", "2024-01-01");

        assertNotNull("Le loyer n'a pas été trouvé après insertion.", loyerRecup);
        assertEquals("L'ID du bien est incorrect.", "LOGBIENTEST01", loyerRecup.getIdBien());
        assertEquals("La date de changement est incorrecte.", "2024-01-01", loyerRecup.getDateDeChangement());
        assertEquals("Le montant du loyer est incorrect.", 800.50, loyerRecup.getMontantLoyer(), 0.01);

        supprimerLoyer(loyer);
    }

    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        Loyer loyer = daoLoyer.findById("INVALID_ID", "2024-01-01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", loyer);
    }

    @Test
    public void testDaoLoyerDelete() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOGBIENTEST02", "2024-02-01", 900.75);
        ajouterLoyer(loyer);

        supprimerLoyer(loyer);
        loyerRecup = daoLoyer.findById("LOGBIENTEST02", "2024-02-01");
        assertNull("Le loyer n'a pas été correctement supprimé.", loyerRecup);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOGBIENTEST03", "2024-03-01", 950.00);
        ajouterLoyer(loyer);

        List<Loyer> loyers = daoLoyer.findAll();

        boolean contientLoyer = loyers.stream()
                                      .anyMatch(l -> "LOGBIENTEST03".equals(l.getIdBien()) && "2024-03-01".equals(l.getDateDeChangement()));

        assertTrue("Le loyer inséré n'est pas présent dans la liste récupérée.", contientLoyer);

        supprimerLoyer(loyer);
    }

    @Test
    public void testFindByIdLogement() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOGBIENTEST04", "2024-04-01", 1000.00);
        ajouterLoyer(loyer);

        List<Loyer> loyers = daoLoyer.findByIdLogement("LOGBIENTEST04");

        assertNotNull("La méthode findByIdLogement n'a pas retourné de résultat.", loyers);
        assertTrue("Le logement recherché n'a pas été trouvé.", loyers.stream().anyMatch(l -> "LOGBIENTEST04".equals(l.getIdBien())));

        supprimerLoyer(loyer);
    }
}
