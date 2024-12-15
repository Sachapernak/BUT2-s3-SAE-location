package testmodeledao;

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

import modele.Regularisation;
import modele.dao.DaoRegularisation;

public class TestDaoRegularisation {
    
    private DaoRegularisation daoR;
    Regularisation reguRecup;

    @Before
    public void setUp() {
        this.daoR = new DaoRegularisation();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterRegularisation(Regularisation regu) throws SQLException, IOException {
    	daoR.create(regu);
    }

    private void supprimerRegularisation(Regularisation regu) throws SQLException, IOException {
    	daoR.delete(regu);
    }
    
    @Test
    public void testDaoRegularisationCreate() throws SQLException, IOException {
        Regularisation regularisation = new Regularisation("BAI04","2024-01-01", new BigDecimal("100.00"));
    	ajouterRegularisation(regularisation);
    	
    	reguRecup = daoR.findById("BAI04","2024-01-01");
        
        assertNotNull("La regularisation n'a pas été ajouté après insertion ", reguRecup);
        assertEquals("L'ID de la Regularisation est incorrect.", "BAI04", reguRecup.getIdBail());
        assertEquals("La date de Regularisation est incorrecte.", "2024-01-01",reguRecup.getDateRegu());
        assertEquals("Le montant doit être de 100.", 0, new BigDecimal("100.00").compareTo(reguRecup.getMontantRegu()));

        supprimerRegularisation(reguRecup);
    }
    
    @Test
    public void testDaoRegularisationDelete() throws SQLException, IOException {
    	Regularisation regularisation = new Regularisation("BAI01", "2024-08-02",new BigDecimal("100"));
    	ajouterRegularisation(regularisation);
    	
        supprimerRegularisation(regularisation);
        reguRecup = daoR.findById("BAI01", "2024-08-02");
        assertNull("La regularisation n'a pas été supprimé.", reguRecup);
    }
    
    @Test
    public void testDaoRegularisationModify() throws SQLException, IOException {
    	Regularisation regularisation = new Regularisation("BAI02", "2024-08-02", new BigDecimal("100"));
        ajouterRegularisation(regularisation);
        
        reguRecup = daoR.findById("BAI02","2024-08-02");
        
        reguRecup.setMontant(new BigDecimal("2300"));
        
        daoR.update(reguRecup);

        reguRecup = daoR.findById("BAI02", "2024-08-02");
        
        assertEquals("Le montant modifié est incorrecte. ", 0, new BigDecimal("2300.00").compareTo(reguRecup.getMontantRegu()));
        
        supprimerRegularisation(reguRecup);
    }
    
    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Regularisation findAllRegu = new Regularisation("BAI03", "2024-10-02", new BigDecimal("1020"));
        ajouterRegularisation(findAllRegu);
        List<Regularisation> list = daoR.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(regu -> "BAI03".equals(regu.getIdBail()) && "2024-10-02".equals(regu.getDateRegu()));

        assertTrue("La régularisation insérée n'est pas présente dans la liste récupérée.", contientId);

        supprimerRegularisation(findAllRegu);
    }
    
    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        Regularisation regu = daoR.findById("INVALID_ID", "2024-01-01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", regu);
    }
    
    @Test
    public void testFindByIdBail() throws SQLException, IOException {
        Regularisation regu = new Regularisation("BAI01", "2024-04-01",new BigDecimal("100"));
        ajouterRegularisation(regu);

        List<Regularisation> regus = daoR.findByIdBail("BAI01");

        assertNotNull("La méthode findByIdBail n'a pas retourné de résultat.", regus);
        assertTrue("La regularisation recherchée n'a pas été trouvé.", regus.stream().anyMatch(l -> "BAI01".equals(l.getIdBail())));

        supprimerRegularisation(regu);
    }
}