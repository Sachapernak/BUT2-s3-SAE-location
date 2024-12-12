package testmodele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Loyer;
import modele.dao.DaoLoyer;

public class TestDaoLoyer {

	static final String NOM_BIEN_LOC1 = "LOG001";
	static final String NOM_BIEN_LOC2 = "LOG002";
	static final String NOM_BIEN_LOC3 = "LOG003";
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
        Loyer loyer = new Loyer(NOM_BIEN_LOC1, "2024-01-01", new BigDecimal("800.50"));
        ajouterLoyer(loyer);

        loyerRecup = daoLoyer.findById(NOM_BIEN_LOC1, "2024-01-01");

        assertNotNull("Le loyer n'a pas été trouvé après insertion.", loyerRecup);
        assertEquals("L'ID du bien est incorrect.", NOM_BIEN_LOC1, loyerRecup.getIdBien());
        assertEquals("La date de changement est incorrecte.", "2024-01-01", loyerRecup.getDateDeChangement());
        assertEquals("Le montant du loyer est incorrect.", 0, new BigDecimal("800.50").compareTo(loyerRecup.getMontantLoyer()));

        supprimerLoyer(loyer);
    }

    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        Loyer loyer = daoLoyer.findById("INVALID_ID", "2024-01-01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", loyer);
    }

    @Test
    public void testDaoLoyerDelete() throws SQLException, IOException {
        Loyer loyer = new Loyer(NOM_BIEN_LOC2, "2024-02-01", new BigDecimal("900.75"));
        ajouterLoyer(loyer);

        supprimerLoyer(loyer);
        loyerRecup = daoLoyer.findById(NOM_BIEN_LOC2, "2024-02-01");
        assertNull("Le loyer n'a pas été correctement supprimé.", loyerRecup);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Loyer loyer = new Loyer(NOM_BIEN_LOC3, "2024-03-01", new BigDecimal("950.00"));
        ajouterLoyer(loyer);

        List<Loyer> loyers = daoLoyer.findAll();

        boolean contientLoyer = loyers.stream()
                                      .anyMatch(l -> NOM_BIEN_LOC3.equals(l.getIdBien()) && "2024-03-01".equals(l.getDateDeChangement()));

        assertTrue("Le loyer inséré n'est pas présent dans la liste récupérée.", contientLoyer);

        supprimerLoyer(loyer);
    }

    @Test
    public void testFindByIdLogement() throws SQLException, IOException {
        Loyer loyer = new Loyer(NOM_BIEN_LOC1, "2024-04-01", new BigDecimal("1000.00"));
        ajouterLoyer(loyer);

        List<Loyer> loyers = daoLoyer.findByIdLogement(NOM_BIEN_LOC1);

        assertNotNull("La méthode findByIdLogement n'a pas retourné de résultat.", loyers);
        assertTrue("Le logement recherché n'a pas été trouvé.", loyers.stream().anyMatch(l -> NOM_BIEN_LOC1.equals(l.getIdBien())));

        supprimerLoyer(loyer);
    }
    
    @Test 
    public void testDaoLoyerUpdate() throws SQLException, IOException {
    	Loyer loyer = new Loyer(NOM_BIEN_LOC1, "2024-02-02", new BigDecimal("11000.00"));
    	ajouterLoyer(loyer);
    	
    	loyerRecup = daoLoyer.findById(NOM_BIEN_LOC1, "2024-02-02");
    	
    	loyerRecup.setMontantLoyer(new BigDecimal("1000.00"));
    	
    	daoLoyer.update(loyerRecup);
    	
    	loyerRecup = daoLoyer.findById(NOM_BIEN_LOC1, "2024-02-02");
    	
    	assertEquals("Le montant du loyer est incorrect.", 0, new BigDecimal("1000.00").compareTo(loyerRecup.getMontantLoyer()));
    	
    	supprimerLoyer(loyer);
    	
    	
    }
}
