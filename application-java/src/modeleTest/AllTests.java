package modeleTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestConnexionBD.class, TestFichierConfig.class, TestDaoAdresse.class, TestDaoLocataire.class})
public class AllTests {

}
