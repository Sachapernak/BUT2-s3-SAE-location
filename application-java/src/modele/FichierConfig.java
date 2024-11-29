package modele;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class FichierConfig {
    
    private static String chemin = "src/config.properties";
    private static FichierConfig instance;
    
    private FichierConfig() {
    }
    
    // pas obligé 
    public static synchronized FichierConfig getInstance() {
        if (instance == null) {
            instance = new FichierConfig();
        }
        return instance;
    }
    
    public static void changerChemin(String nouveauChemin) {
    	
    	if( nouveauChemin == null) {
    		chemin = "src/config.properties";
    	} else {
    		chemin = nouveauChemin;
    	}
    }
    
    public static String getChemin() {
    	return chemin;
    }
    
    public String lire(String propriete) throws IOException  {
    	FileInputStream propsInput = new FileInputStream(chemin);
            Properties prop = new Properties();
            prop.load(propsInput);
            return prop.getProperty(propriete);
    }

        
    
}
