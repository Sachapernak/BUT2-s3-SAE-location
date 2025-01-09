package testmodeledao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Cautionner;
import modele.ConnexionBD;
import modele.TypeDeBien;
import modele.Adresse;
import modele.Bail;
import modele.Batiment;
import modele.BienLocatif;
import modele.Cautionnaire;
import modele.dao.DaoAdresse;
import modele.dao.DaoBail;
import modele.dao.DaoCautionnaire;
import modele.dao.DaoCautionner;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;

public class TestDaoCautionner {

    private DaoCautionner daoC;
    private Cautionner cautionnerRecup;
    private Cautionnaire cautionnaireTest;
    private Adresse adresseTest;
    private BienLocatif bienTest;
    private Batiment batimentTest;
    private Bail bailTest;

    @Before
    public void setUp() throws SQLException, IOException {
        this.daoC = new DaoCautionner();  
        
        this.adresseTest = new Adresse("TESTADDR", "10 rue du Test", 12345, "TestVille");
        DaoAdresse daoA = new DaoAdresse();
        
        this.cautionnaireTest = new Cautionnaire(9, "NomTest", "PrenomTest", "DescriptionTest");
        this.cautionnaireTest.setAdresse(adresseTest);
        DaoCautionnaire daoCautionnaire = new DaoCautionnaire();
                
        this.batimentTest = new Batiment("TESTBAT", adresseTest);
        DaoBatiment daoBat = new DaoBatiment();

        this.bienTest = new BienLocatif(
            "TESTBIEN",                  // identifiantLogement
            TypeDeBien.LOGEMENT,       // type (Enum TypeDeBien)
            45,                        // surface en m²
            2,                         // nombre de pièces
            new BigDecimal("650.00"),                    // loyer de base
            batimentTest                   // bâtiment associé
        );
        DaoBienLocatif daoBien = new DaoBienLocatif();
        
        this.bailTest = new Bail("TEST01", "2024-01-01", bienTest);
        DaoBail daoBail = new DaoBail();
        
        deleteTestData();
        
        daoA.create(adresseTest);
        daoCautionnaire.create(cautionnaireTest);
        daoBat.create(batimentTest);
        daoBien.create(bienTest);
        daoBail.create(bailTest);
        
 
    }
    
    private void ajouterCautionner(Cautionner cautionner) throws SQLException, IOException {
        daoC.create(cautionner);
    }

    private void supprimerCautionner(Cautionner cautionner) throws SQLException, IOException {
        daoC.delete(cautionner);
    }
    
    public void deleteTestData() throws SQLException, IOException {
        String deleteCautionner = "DELETE FROM SAE_cautionner WHERE lower(id_caution) LIKE '%test%'";
        String deleteAddresses = "DELETE FROM SAE_ADRESSE WHERE lower(ID_SAE_ADRESSE) LIKE '%test%'";
        String deleteCautionnaire = "DELETE FROM SAE_CAUTIONNAIRE WHERE lower(ID_CAUTION) = 9";
        String deleteBien = "DELETE FROM SAE_bien_locatif WHERE lower(IDENTIFIANT_LOGEMENT) LIKE '%test%'";
        String deleteBatiment = "DELETE FROM SAE_batiment WHERE lower(IDENTIFIANT_BATIMENT) LIKE '%test%'";
        String deleteBail = "DELETE FROM SAE_bail WHERE lower(IDENTIFIANT_LOGEMENT) LIKE '%test%'";

        Connection cn = ConnexionBD.getInstance().getConnexion();

        try (Statement stmt = cn.createStatement()) {
        	// Commencer une transaction
        	cn.setAutoCommit(false);

            // Exécuter les suppressions
        	stmt.executeUpdate(deleteCautionnaire);  // Delete Cautionnaire records
            stmt.executeUpdate(deleteBail);          // Delete Bail records
            stmt.executeUpdate(deleteBien);          // Delete BienLocatif records
            stmt.executeUpdate(deleteBatiment);      // Delete Batiment records

            // Now delete the Adresse records
            stmt.executeUpdate(deleteAddresses);
            
            // Valider les modifications
            cn.commit();
        }
        

        System.out.println("Les données contenant 'test' ont été supprimées avec succès.");

    }
    
    @Test
    public void testDaoCautionnerCreate() throws SQLException, IOException {
        Cautionner createCautionner = new Cautionner(new BigDecimal(150),"TEST_fichier_acte_caution",this.bailTest,this.cautionnaireTest);
        
        ajouterCautionner(createCautionner);
        cautionnerRecup = daoC.findById(String.valueOf( createCautionner.getLastCautionnaire().getIdCautionnaire()), createCautionner.getBail().getIdBail());

        assertNotNull("Cautionner non trouvé après insertion.", cautionnerRecup);
        assertEquals("L'ID du cautionnaire dans cautionner est incorrect.", "TEST01" , cautionnerRecup.getBail().getIdBail());
        assertEquals("L'ID du bail dans cautionner est incorrect.", 9 , cautionnerRecup.getLastCautionnaire().getIdCautionnaire());
        assertEquals("Le montant de la caution est incorrect.", new BigDecimal(150), cautionnerRecup.getMontant());
        assertEquals("Le fichier de la caution est incorrect.", "TEST_fichier_acte_caution", cautionnerRecup.getFichierCaution());
        supprimerCautionner(createCautionner);
    }
    
    @Test
    public void testDaoCautionnerModify() throws SQLException, IOException {
        Cautionner majCautionner = new Cautionner(new BigDecimal(150),"TEST_fichier_acte_caution",this.bailTest,this.cautionnaireTest);
        BigDecimal newMontant = new BigDecimal(200);
        
        ajouterCautionner(majCautionner);
        cautionnerRecup = daoC.findById(String.valueOf( majCautionner.getLastCautionnaire().getIdCautionnaire()), majCautionner.getBail().getIdBail());

        assertNotNull("Cautionnaire non trouvé après insertion.", cautionnerRecup);
        assertEquals("Le montant de la caution est incorrect.", new BigDecimal(150), cautionnerRecup.getMontant());
        assertEquals("Le fichier de caution est incorrect", "TEST_fichier_acte_caution",  cautionnerRecup.getFichierCaution() );
        majCautionner.setMontant(newMontant);
        daoC.update(majCautionner);

        cautionnerRecup = daoC.findById(String.valueOf( majCautionner.getLastCautionnaire().getIdCautionnaire()), majCautionner.getBail().getIdBail());
        assertEquals("Le nom modifié est incorrect.", newMontant, cautionnerRecup.getMontant());
        assertEquals("Le fichier de caution est incorrect", "TEST_fichier_acte_caution",  cautionnerRecup.getFichierCaution() );
        
        supprimerCautionner(majCautionner);
    }
    
    @Test
    public void testDaoSuppressionDeuxFois() throws SQLException, IOException {
        // Création du Cautionner avec les bons paramètres en utilisant le constructeur correct
        Cautionner testCautionner = new Cautionner(new BigDecimal(150), "TEST_fichier_acte_caution", bailTest, cautionnaireTest);
        ajouterCautionner(testCautionner);

        // Récupération et vérification de l'existence du Cautionner
        cautionnerRecup = daoC.findById(String.valueOf(testCautionner.getLastCautionnaire().getIdCautionnaire()), testCautionner.getBail().getIdBail());
        assertNotNull("Cautionnaire non trouvé après insertion.", cautionnerRecup);

        // Suppression du Cautionner et vérification de sa suppression
        supprimerCautionner(testCautionner);
        cautionnerRecup = daoC.findById(String.valueOf(testCautionner.getLastCautionnaire().getIdCautionnaire()), testCautionner.getBail().getIdBail());
        assertNull("Le cautionnaire n'a pas été correctement supprimé.", cautionnerRecup);

        // Essai de suppression une seconde fois sans erreur
        supprimerCautionner(testCautionner);  // Cela ne doit pas lever d'exception
    }
    
    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        
        Cautionner cautionner = daoC.findById("100", "TEST01");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", cautionner);
    }
    
    @Test
    public void testDeleteNonExistent() throws SQLException, IOException, IllegalArgumentException {
    	Cautionner fakeCautionner = new Cautionner(new BigDecimal(9999), "Faux_fichier_acte_caution", bailTest, cautionnaireTest);
    	daoC.delete(fakeCautionner);
    	Cautionner cautionner = daoC.findById(String.valueOf(fakeCautionner.getLastCautionnaire().getIdCautionnaire()), fakeCautionner.getBail().getIdBail());
        assertNull("La suppression d'un cautionnaire inexistant ne devrait pas lever une erreur.", cautionner);
    }




    
    
    
    
}
