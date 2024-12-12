package utilitaires;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testmodele.AllTestsModele;
import testmodeledao.AllTestsModeleDao;


@RunWith(Suite.class)
@SuiteClasses({
    AllTestsModele.class,    // Configurations d'abord
    AllTestsModeleDao.class,      // Connexion à la BD 
})

public class AllTests {
}
