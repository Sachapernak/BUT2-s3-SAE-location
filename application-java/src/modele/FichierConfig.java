package modele;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * La classe FichierConfig gère la lecture, l'écriture et le chiffrement/déchiffrement des propriétés de configuration.
 * Elle permet de charger et d'enregistrer les propriétés d'un fichier de configuration, ainsi que de gérer le mot de passe 
 * chiffré pour la connexion à la base de données.
 * 
 * La classe utilise AES en mode GCM (avec NoPadding) pour chiffrer et déchiffrer le mot de passe de la base de données.
 * Elle gère également la génération et le stockage d'une clé secrète pour le chiffrement.
 * 
 * Les principales fonctionnalités incluent :
 * - Lecture et écriture des propriétés dans un fichier de configuration (config.properties).
 * - Génération et stockage d'une clé secrète dans un fichier de configuration (key.config).
 * - Chiffrement et déchiffrement du mot de passe en utilisant AES/GCM/NoPadding.
 * - Sauvegarde et récupération du mot de passe chiffré dans le fichier de configuration.
 * 
 * Cette classe suit le modèle singleton, garantissant qu'il n'existe qu'une seule instance de la configuration.
 * 
 * @author VotreNom
 * @version 1.0
 */
public class FichierConfig {
    
    // Chemin du fichier de clé secrète
    private static String cheminKeyConfig = "src/key.config"; 
    
    // Chemin du fichier de configuration
    private static String chemin = "src/config.properties";
    
    // Instance singleton de la classe
    private static FichierConfig instance;

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     */
    private FichierConfig() {
    }

    /**
     * Récupère l'instance singleton de FichierConfig.
     * 
     * @return L'instance unique de FichierConfig.
     */
    public static synchronized FichierConfig getInstance() {
        if (instance == null) {
            instance = new FichierConfig();
        }
        return instance;
    }

    /**
     * Change le chemin du fichier de configuration.
     * 
     * @param nouveauChemin Le nouveau chemin à utiliser.
     */
    public static void changerChemin(String nouveauChemin) {
        chemin = (nouveauChemin != null) ? nouveauChemin : "src/config.properties";
    }
    
    public static void setCheminKeyConfig(String nouveauChemin) {
    	cheminKeyConfig = (nouveauChemin != null) ? nouveauChemin : "src/key.config";
    }

    /**
     * Récupère le chemin actuel du fichier de configuration.
     * 
     * @return Le chemin du fichier de configuration.
     */
    public static String getChemin() {
        return chemin;
    }
    
    public static String getCheminKeyConfig() {
    	return cheminKeyConfig;
    }

    /**
     * Génère une clé secrète aléatoire et la sauvegarde dans le fichier key.config.
     * 
     * @throws IOException Si une erreur survient lors de l'écriture de la clé dans le fichier.
     */
    public void genererEtSauvegarderCle() throws IOException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] cleSecrete = new byte[16]; // AES nécessite une clé de 16 octets (128 bits)
        secureRandom.nextBytes(cleSecrete);
        String cleBase64 = Base64.getEncoder().encodeToString(cleSecrete);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminKeyConfig))) {
            writer.write(cleBase64);
        }
    }

    /**
     * Charge la clé secrète depuis le fichier key.config.
     * 
     * @return La clé secrète sous forme de chaîne encodée en Base64.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private String chargerCleSecrete() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminKeyConfig))) {
            return reader.readLine();
        }
    }

    /**
     * Lit une propriété depuis le fichier de configuration.
     * 
     * @param propriete Le nom de la propriété à lire.
     * @return La valeur de la propriété.
     * @throws IOException Si une erreur survient lors de la lecture du fichier de configuration.
     */
    public String lire(String propriete) throws IOException {
        Properties prop = new Properties();
        try (FileInputStream propsInput = new FileInputStream(chemin)) {
            prop.load(propsInput);
        }
        return prop.getProperty(propriete);
    }

    /**
     * Enregistre une propriété dans le fichier de configuration.
     * 
     * @param propriete Le nom de la propriété à enregistrer.
     * @param valeur La valeur à associer à la propriété.
     * @throws IOException Si une erreur survient lors de l'écriture dans le fichier de configuration.
     */
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
     * Chiffre un mot de passe en utilisant AES/GCM/NoPadding.
     * 
     * @param mdp Le mot de passe en clair à chiffrer.
     * @return Le mot de passe chiffré avec l'IV sous forme de chaîne encodée en Base64.
     * @throws Exception Si une erreur survient lors du chiffrement.
     */
    private String chiffrerMdp(String mdp) throws Exception {
        String cleSecrete = chargerCleSecrete();
        SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(cleSecrete), "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        byte[] iv = new byte[12]; // IV de 12 octets pour AES GCM
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(mdp.getBytes());

        String encryptedMdp = Base64.getEncoder().encodeToString(encrypted);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);

        return ivBase64 + ":" + encryptedMdp;
    }

    /**
     * Déchiffre un mot de passe chiffré en utilisant AES/GCM/NoPadding.
     * 
     * @param mdpChiffre Le mot de passe chiffré avec l'IV sous forme de chaîne encodée en Base64.
     * @return Le mot de passe déchiffré.
     * @throws Exception Si une erreur survient lors du déchiffrement.
     */
    private String dechiffrerMdp(String mdpChiffre) throws Exception {
        String cleSecrete = chargerCleSecrete();
        SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(cleSecrete), "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        String[] parts = mdpChiffre.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedMdp = Base64.getDecoder().decode(parts[1]);

        GCMParameterSpec spec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decrypted = cipher.doFinal(encryptedMdp);

        return new String(decrypted);
    }

    /**
     * Enregistre un mot de passe chiffré dans le fichier de configuration.
     * Si la clé secrète n'existe pas, elle est générée avant de chiffrer le mot de passe.
     * 
     * @param mdp Le mot de passe en clair à chiffrer et enregistrer.
     * @throws Exception Si une erreur survient lors du chiffrement ou de l'enregistrement.
     */
    public void enregistrerMdp(String mdp) throws Exception {
        File keyFile = new File(cheminKeyConfig);
        if (!keyFile.exists()) {
            genererEtSauvegarderCle();
        }
        String mdpChiffre = chiffrerMdp(mdp);
        enregistrer("DB_PASSWORD", mdpChiffre);
    }

    /**
     * Récupère et déchiffre le mot de passe depuis le fichier de configuration.
     * 
     * @return Le mot de passe déchiffré.
     * @throws Exception Si une erreur survient lors du déchiffrement ou de la lecture du mot de passe.
     */
    public String recupererMdp() throws IOException {
        String dechiffre = "";
        String mdpChiffre = lire("DB_PASSWORD");
        try {
        	dechiffre = dechiffrerMdp(mdpChiffre);
		} catch (Exception e) {
			throw new IOException("Erreur dans la lecture du mot de passe : " + e.getMessage());
		}

        return dechiffre;
    }

}
