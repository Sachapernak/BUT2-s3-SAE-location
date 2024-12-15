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

import modele.ProvisionCharge;
import modele.dao.DaoProvisionCharge;

public class TestDaoProvisionCharge {
    
    private DaoProvisionCharge daoPC;
    ProvisionCharge proChaRecup;

    @Before
    public void setUp() {
        this.daoPC = new DaoProvisionCharge();
    }

    // Méthodes utilitaires pour éviter la duplication de code
    private void ajouterProvisionCharge(ProvisionCharge proCha) throws SQLException, IOException {
    	daoPC.create(proCha);
    }

    private void supprimerProvisionCharge(ProvisionCharge proCha) throws SQLException, IOException {
    	daoPC.delete(proCha);
    }
    
    @Test
    public void testDaoProvisionChargeCreate() throws SQLException, IOException {
        ProvisionCharge provisioncharge = new ProvisionCharge("BAI04","2024-01-01", new BigDecimal("100.00"));
        ajouterProvisionCharge(provisioncharge);
    	
    	proChaRecup = daoPC.findById("BAI04","2024-01-01");
        
        assertNotNull("La provision pour charge n'a pas été ajouté après insertion ", proChaRecup);
        assertEquals("L'ID de la provision pour charge est incorrect.", "BAI04", proChaRecup.getIdBail());
        assertEquals("La date de changement est incorrecte.", "2024-01-01",proChaRecup.getDateChangement());
        assertEquals("Le montant de la provision pour charge doit être de 100.", 0, new BigDecimal("100.00").compareTo(proChaRecup.getProvisionPourCharge()));

        supprimerProvisionCharge(proChaRecup);
    }
    
    @Test
    public void testDaoProvisionChargeDelete() throws SQLException, IOException {
    	ProvisionCharge provisioncharge = new ProvisionCharge("BAI01", "2024-08-02",new BigDecimal("100"));
    	ajouterProvisionCharge(provisioncharge);
    	
    	supprimerProvisionCharge(provisioncharge);
    	proChaRecup = daoPC.findById("BAI01", "2024-08-02");
        assertNull("La provision pour charge n'a pas été supprimé.", proChaRecup);
    }
    
    @Test
    public void testDaoProvisionChargeModify() throws SQLException, IOException {
    	ProvisionCharge provisioncharge = new ProvisionCharge("BAI02", "2024-08-02", new BigDecimal("100"));
    	ajouterProvisionCharge(provisioncharge);
        
    	proChaRecup = daoPC.findById("BAI02","2024-08-02");
        
    	proChaRecup.setProvisionPourCharge(new BigDecimal("2300"));
        
        daoPC.update(proChaRecup);

        proChaRecup = daoPC.findById("BAI02", "2024-08-02");
        
        assertEquals("Le montant de la provision pour charge modifié est incorrecte. ", 0, new BigDecimal("2300.00").compareTo(proChaRecup.getProvisionPourCharge()));
        
        supprimerProvisionCharge(proChaRecup);
    }
    
    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        ProvisionCharge findAllProCha = new ProvisionCharge("BAI03", "2024-10-02", new BigDecimal("1020"));
        ajouterProvisionCharge(findAllProCha);
        List<ProvisionCharge> list = daoPC.findAll();

        boolean contientId = list.stream()
                                 .anyMatch(regu -> "BAI03".equals(regu.getIdBail()) && "2024-10-02".equals(regu.getDateChangement()));

        assertTrue("La provision pour charge insérée n'est pas présente dans la liste récupérée.", contientId);

        supprimerProvisionCharge(findAllProCha);
    }
    
    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        ProvisionCharge proCha = daoPC.findById("INVALID_ID", "2024-01-01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", proCha);
    }
    
    @Test
    public void testFindByIdBail() throws SQLException, IOException {
        ProvisionCharge proCha = new ProvisionCharge("BAI01", "2024-04-01",new BigDecimal("100"));
        ajouterProvisionCharge(proCha);

        List<ProvisionCharge> proChas = daoPC.findByIdBail("BAI01");

        assertNotNull("La méthode findByIdBail n'a pas retourné de résultat.", proChas);
        assertTrue("La provision pour charge recherchée n'a pas été trouvé.", proChas.stream().anyMatch(l -> "BAI01".equals(l.getIdBail())));

        supprimerProvisionCharge(proCha);
    }
}