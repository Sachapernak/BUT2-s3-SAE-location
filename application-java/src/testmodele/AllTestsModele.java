package testmodele;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    TestFichierConfig.class,    // Configurations d'abord
    TestConnexionBD.class,
    TestBienLocatif.class// Connexion à la BD 
})

public class AllTestsModele {
}
