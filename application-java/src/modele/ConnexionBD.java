package modele;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Classe singleton pour gérer la connexion à la base de données Oracle.
 * Cette classe fournit une gestion centralisée de la connexion à la base de données 
 * en utilisant une instance unique (Design Pattern Singleton).
 * 
 * Elle assure la récupération d'une connexion valide à la base de données, 
 * ainsi que la mise à jour de la configuration de la connexion si nécessaire.
 * 
 * Les informations de connexion (utilisateur, mot de passe, URL) sont lues 
 * depuis un fichier de configuration via la classe {@link FichierConfig}.
 */
public class ConnexionBD {

    /**
     * Instance unique de la classe ConnexionBD (Singleton).
     */
    private static ConnexionBD instance;

    /**
     * Connexion à la base de données.
     */
    private static Connection cn;

    /**
     * Source de données Oracle utilisée pour obtenir des connexions à la base de données.
     */
    private static OracleDataSource bd;

    /**
     * Instance de la classe de configuration pour lire les paramètres de connexion.
     */
    private static FichierConfig conf;

    /**
     * Initialisation de la configuration via la classe {@link FichierConfig}.
     * Cette initialisation se produit au moment du chargement de la classe.
     */
    static {
        conf = FichierConfig.getInstance();
    }

    /**
     * Constructeur privé de la classe {@link ConnexionBD}. Initialise la connexion
     * à la base de données en utilisant la configuration fournie.
     * 
     * @throws SQLException Si une erreur SQL se produit lors de la configuration de la source de données.
     * @throws IOException Si une erreur de lecture du fichier de configuration survient.
     */
    private ConnexionBD() throws SQLException, IOException {
        initierBD();
    }

    /**
     * Initialise la source de données Oracle avec les paramètres de connexion 
     * extraits du fichier de configuration.
     * 
     * @throws SQLException Si une erreur SQL se produit lors de l'initialisation de la source de données.
     * @throws IOException Si une erreur de lecture du fichier de configuration survient.
     */
    private static void initierBD() throws SQLException, IOException {
        bd = new OracleDataSource();
        bd.setUser(conf.lire("DB_USER"));
  
		bd.setPassword(conf.recupererMdp());

        bd.setURL(conf.lire("DB_LINK"));
    }

    /**
     * Récupère l'instance unique de la classe {@link ConnexionBD} (Singleton).
     * Si l'instance n'existe pas encore, elle est créée.
     * 
     * @return L'instance unique de la classe {@link ConnexionBD}.
     * @throws SQLException Si une erreur SQL se produit lors de l'obtention de la connexion.
     * @throws IOException Si une erreur de lecture du fichier de configuration survient.
     */
    public static synchronized ConnexionBD getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new ConnexionBD();
        }
        return instance;
    }

    /**
     * Récupère une connexion valide à la base de données. Si la connexion actuelle est fermée,
     * une nouvelle connexion est établie.
     * 
     * @return Une connexion à la base de données.
     * @throws SQLException Si une erreur SQL se produit lors de l'établissement de la connexion.
     */
    public Connection getConnexion() throws SQLException {
        if (cn == null || cn.isClosed()) {
            cn = bd.getConnection();
        }
        return cn;
    }

    /**
     * Récupère la source de données Oracle utilisée pour établir les connexions.
     * 
     * @return La source de données Oracle.
     */
    public OracleDataSource getBD() {
        return bd;
    }

    /**
     * Ferme la connexion actuelle à la base de données si elle est ouverte.
     * 
     * @throws SQLException Si une erreur SQL se produit lors de la fermeture de la connexion.
     */
    public void fermerConnexion() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            cn.close();
        }
    }

    /**
     * Met à jour la configuration de la base de données et réinitialise la source de données.
     * La connexion actuelle est fermée avant l'initialisation de la nouvelle configuration.
     * 
     * @throws SQLException Si une erreur SQL se produit lors de la mise à jour de la connexion.
     * @throws IOException Si une erreur de lecture du fichier de configuration survient.
     */
    public void updateBDLink() throws SQLException, IOException {
        fermerConnexion();
        initierBD();
    }
    
    public Optional<Long> latenceRequeteBD() {
    		
            String requeteTest = "SELECT 1 FROM DUAL";
            long finRequete;
            long debutRequete;

            try (PreparedStatement prst = getConnexion().prepareStatement(requeteTest)) {
            	
            	debutRequete = System.currentTimeMillis();
            	ResultSet rs = prst.executeQuery();
            	finRequete = System.currentTimeMillis();

                if (rs.next()) {
                	return Optional.of(finRequete - debutRequete);
                }
                rs.close();
            } catch (SQLException e) {
            	return Optional.empty();

            }
    	return Optional.empty();
        
    	
    }
        
    public Optional<Long> latenceConnexionBD() {

        long finRequete;
        long debutRequete;
       
        try {
        	this.fermerConnexion();
        	
        	debutRequete = System.currentTimeMillis();
        	this.getConnexion();
        	finRequete = System.currentTimeMillis();
        	
        	return Optional.of(finRequete - debutRequete);
        	
        } catch (SQLException e) {
        	return Optional.empty();
        }
        
    }
    
    public boolean isConnexionOk() {
    	return (latenceConnexionBD().isPresent() && latenceRequeteBD().isPresent());
    }

}
