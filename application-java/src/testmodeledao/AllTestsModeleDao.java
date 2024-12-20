package testmodeledao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    TestDaoAdresse.class, 
    TestDaoBatiment.class,      // Entités principales
    TestDaoLocataire.class,
    TestDaoBienLocatif.class,
    
    TestDaoBail.class,          
    TestDaoLoyer.class,
    TestDaoDocument.class,
    TestDaoRegularisation.class,
    TestDaoProvisionCharge.class,
    TestDaoICC.class,
    TestDaoFactureBien.class,
    TestDaoDocumentComptable.class,
    TestDaoEntreprise.class,
    TestDaoAssurance.class,
    TestDaoChargeIndex.class,
    TestDaoChargeFixe.class
})

public class AllTestsModeleDao {
}
