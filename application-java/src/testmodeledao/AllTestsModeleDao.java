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
    TestDaoCautionnaire.class,
    
    TestDaoBail.class,          
    TestDaoLoyer.class,
    TestDaoDocument.class,
    TestDaoRegularisation.class,
    TestDaoProvisionCharge.class,
    TestDaoICC.class,
    TestDaoFactureBien.class,
    TestDaoDocumentComptable.class
})

public class AllTestsModeleDao {
}
