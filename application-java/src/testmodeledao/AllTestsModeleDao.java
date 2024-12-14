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
          
    TestDaoICC.class,
    TestDaoFactureBien.class
})

public class AllTestsModeleDao {
}
