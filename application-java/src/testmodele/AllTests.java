package testmodele;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    TestFichierConfig.class,    // Configurations d'abord
    TestConnexionBD.class,      // Connexion à la BD
    
    TestDaoAdresse.class, 
    TestDaoBatiment.class,      // Entités principales
    TestDaoLocataire.class,
    TestDaoBienLocatif.class,
    
    TestDaoBail.class,          
    TestDaoLoyer.class,
          
    TestDaoICC.class            
})

public class AllTests {
}
