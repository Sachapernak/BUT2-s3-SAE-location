package modele;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class FichierConfig {

    private static String chemin = "src/config.properties";
    private static FichierConfig instance;

    private FichierConfig() {
    }

    public static synchronized FichierConfig getInstance() {
        if (instance == null) {
            instance = new FichierConfig();
        }
        return instance;
    }

    public static void changerChemin(String nouveauChemin) {
        if (nouveauChemin == null) {
            chemin = "src/config.properties";
        } else {
            chemin = nouveauChemin;
        }
    }

    public static String getChemin() {
        return chemin;
    }

    public String lire(String propriete) throws IOException {
        Properties prop = new Properties();
        try (FileInputStream propsInput = new FileInputStream(chemin)) {
            prop.load(propsInput);
        }
        return prop.getProperty(propriete);
    }

    public void enregistrer(String propriete, String valeur) throws IOException {
        Properties prop = new Properties();
        try (FileInputStream propsInput = new FileInputStream(chemin)) {
            prop.load(propsInput);
        }
        prop.setProperty(propriete, valeur);
        try (FileOutputStream propsOutput = new FileOutputStream(chemin)) {
            prop.store(propsOutput, null);
        }
    }

    /**
     * Récupère et hache le mot de passe stocké dans le fichier de configuration.
     *
     * @return Le mot de passe haché.
     * @throws IOException            Si le fichier de configuration ne peut pas être lu.
     * @throws NoSuchAlgorithmException Si l'algorithme de hachage SHA-256 est introuvable.
     */
    public String recupererMdp() throws IOException, NoSuchAlgorithmException {
        String mdp = lire("DB_PASSWORD");  // Récupère le mot de passe depuis la config
        return hacherMdp(mdp);  // Hache le mot de passe
    }

    /**
     * Hache un mot de passe avec l'algorithme SHA-256.
     *
     * @param mdp Le mot de passe à hacher.
     * @return Le mot de passe haché.
     * @throws NoSuchAlgorithmException Si l'algorithme de hachage est introuvable.
     */
    private String hacherMdp(String mdp) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(mdp.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Enregistre un mot de passe sécurisé (haché) dans le fichier de configuration.
     *
     * @param mdp Le mot de passe en texte clair à hacher et enregistrer.
     * @throws IOException            Si le fichier de configuration ne peut pas être modifié.
     * @throws NoSuchAlgorithmException Si l'algorithme de hachage est introuvable.
     */
    public void enregistrerMdp(String mdp) throws IOException, NoSuchAlgorithmException {
        String mdpHache = hacherMdp(mdp);  // Hache le mot de passe
        enregistrer("DB_PASSWORD", mdpHache);  // Enregistre le mot de passe haché dans la config
    }
}

