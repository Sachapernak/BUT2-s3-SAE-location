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

import modele.Adresse;
import modele.Batiment;
import modele.BienLocatif;
import modele.TypeDeBien;
import modele.dao.DaoAdresse;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;

public class TestDaoBatiment {

    private DaoBatiment daoBat;
    private DaoAdresse daoAdresse;
    private DaoBienLocatif daoBien;
    Batiment batimentRecup;

    @Before
    public void setUp() {
        this.daoBat = new DaoBatiment();
        this.daoAdresse = new DaoAdresse();
        this.daoBien = new DaoBienLocatif();
    }

    private void ajouterBatiment(Batiment batiment) throws SQLException, IOException {
        daoBat.create(batiment);
    }
    

    private void supprimerBatiment(Batiment batiment) throws SQLException, IOException {
        daoBat.delete(batiment);
    }
    
    private void supprimerBien(BienLocatif bien) throws SQLException, IOException {
        daoBien.delete(bien);
    }
    
    

    @Test
    public void testDaoBatimentCreate() throws SQLException, IOException {
        Adresse adresse = new Adresse("ADDRTEST01", "1 rue du test", 12345, "TestVille");
        daoAdresse.create(adresse);

        Batiment batiment = new Batiment("BATTEST01", adresse);
        ajouterBatiment(batiment);

        batimentRecup = daoBat.findById("BATTEST01");

        assertNotNull("Le bâtiment n'a pas été trouvé après insertion.", batimentRecup);
        assertEquals("L'ID du bâtiment est incorrect.", "BATTEST01", batimentRecup.getIdBat());
        assertEquals("L'adresse du bâtiment est incorrecte.", "ADDRTEST01", batimentRecup.getAdresse().getIdAdresse());

        supprimerBatiment(batiment);
        daoAdresse.delete(adresse);
    }

    @Test
    public void testDaoFindByIdInvalid() throws SQLException, IOException {
        Batiment batiment = daoBat.findById("INVALID_ID");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", batiment);
    }

    @Test
    public void testDaoBatimentDelete() throws SQLException, IOException {
        Adresse adresse = new Adresse("ADDRTEST02", "2 rue du test", 67890, "TestVille2");
        daoAdresse.create(adresse);

        Batiment batiment = new Batiment("BATTEST02", adresse);
        ajouterBatiment(batiment);

        supprimerBatiment(batiment);
        batimentRecup = daoBat.findById("BATTEST02");
        assertNull("Le bâtiment n'a pas été correctement supprimé.", batimentRecup);

        daoAdresse.delete(adresse);
    }

    @Test
    public void testDaoFindAll() throws SQLException, IOException {
        Adresse adresse = new Adresse("ADDRTEST03", "3 rue du test", 54321, "TestVille3");
        daoAdresse.create(adresse);

        Batiment batiment = new Batiment("BATTEST03", adresse);
        ajouterBatiment(batiment);

        List<Batiment> batiments = daoBat.findAll();

        boolean contientBatiment = batiments.stream()
                                           .anyMatch(b -> "BATTEST03".equals(b.getIdBat()));

        assertTrue("Le bâtiment inséré n'est pas présent dans la liste récupérée.", contientBatiment);

        supprimerBatiment(batiment);
        daoAdresse.delete(adresse);
    }
    
    @Test 
    public void testDaoCountNbLogement() throws SQLException, IOException {
    	 Adresse adresse = new Adresse("ADDRTEST04", "4 rue du test", 65432, "TestVille4");
         daoAdresse.create(adresse);

         Batiment batiment = new Batiment("BATTEST04", adresse);
         ajouterBatiment(batiment);
         
         BienLocatif bien1 = new BienLocatif("BIENTEST01",TypeDeBien.LOGEMENT, 20, 2, new BigDecimal(150),batiment);
         BienLocatif bien2 = new BienLocatif("BIENTEST01",TypeDeBien.LOGEMENT, 20, 2, new BigDecimal(150),batiment);
         daoBien.create(bien1);
         //daoBien.create(bien2);
         
         int nbLogements = daoBat.countBiens(batiment);
         
         assertEquals("Probleme de compte des biens locatifs",2, nbLogements);
         supprimerBien(bien1);
         supprimerBien(bien2);
         supprimerBatiment(batiment);
         daoAdresse.delete(adresse);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void testUpdateBatimentNotSupported() throws SQLException, IOException {
    	daoBat.update(null);
    }
}