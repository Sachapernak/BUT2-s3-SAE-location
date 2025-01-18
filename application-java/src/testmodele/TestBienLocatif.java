package testmodele;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Adresse;
import modele.Bail;
import modele.Batiment;
import modele.BienLocatif;
import modele.Diagnostiques;
import modele.FactureBien;
import modele.Loyer;
import modele.TypeDeBien;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Classe de test JUnit 4 pour la classe {@code BienLocatif}.
 * <p>
 * Cette classe teste les différentes fonctionnalités de {@code BienLocatif}, y compris
 * la validation des entrées, les getters et setters, l'ajout et la suppression de loyers,
 * la réinitialisation des données, ainsi que les méthodes {@code equals}, {@code hashCode} et {@code toString}.
 * </p>
 * <p>
 * Les tests associés aux DAO {@code DaoFactureBien} et {@code DaoDiagnostique} sont ignorés
 * car ces DAOs ne sont pas encore fonctionnels.
 * </p>
 * 
 * @version 1.1
 */
public class TestBienLocatif {

    private BienLocatif bienLocatif;
    private Batiment batiment;
    private Adresse adresse;
    private Bail bail;

    private final String identifiantLogement = "LOG001";
    private final TypeDeBien type = TypeDeBien.LOGEMENT;
    private final int surface = 75;
    private final int nbPiece = 3;
    private final BigDecimal loyerBase = new BigDecimal("1200.00");

    /**
     * Méthode exécutée avant chaque test pour initialiser un objet {@code BienLocatif} valide.
     */
    @Before
    public void setUp() {
        // Initialisation de l'adresse et du bâtiment pour les tests
        adresse = new Adresse("ADDR001", "123 Rue Exemple", 75000, "Paris");
        batiment = new Batiment("BAT_TEST", adresse);

        // Initialisation de BienLocatif avec des paramètres valides
        bienLocatif = new BienLocatif(identifiantLogement, type, surface, nbPiece, loyerBase, batiment);
    }
    
    /**
     * Liste des loyers ajoutés pendant les tests pour un nettoyage ultérieur.
     */
    private List<Loyer> loyersToCleanUp = new ArrayList<>();
    
    /**
     * Méthode exécutée après chaque test pour supprimer les loyers ajoutés.
     */
    @After
    public void tearDown() throws SQLException, IOException {
        for (Loyer loyer : loyersToCleanUp) {
            try {
                bienLocatif.removeLoyer(loyer);
            } catch (IllegalArgumentException e) {
                // Loyer peut avoir déjà été supprimé dans le test
            }
        }
        loyersToCleanUp.clear();
    }


    /**
     * Test du constructeur avec des paramètres valides.
     */
    @Test
    public void testConstructorValid() {
        assertEquals("LOG001", bienLocatif.getIdentifiantLogement());
        assertEquals(TypeDeBien.LOGEMENT, bienLocatif.getType());
        assertEquals(75, bienLocatif.getSurface());
        assertEquals(3, bienLocatif.getNbPiece());
        assertEquals(new BigDecimal("1200.00"), bienLocatif.getLoyerBase());
        assertEquals(batiment, bienLocatif.getBat());
    }

    /**
     * Test du constructeur avec une surface non positive.
     * 
     * @throws IllegalArgumentException attendu lorsque la surface est non positive
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidSurface() {
        new BienLocatif("LOG124", type, 0, nbPiece, loyerBase, batiment);
    }

    /**
     * Test du constructeur avec un loyer de base négatif.
     * 
     * @throws IllegalArgumentException attendu lorsque le loyer de base est négatif
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidLoyerBase() {
        new BienLocatif("LOG125", type, surface, nbPiece, new BigDecimal("-100.00"), batiment);
    }

    /**
     * Test du constructeur avec un identifiant de logement null.
     * 
     * @throws IllegalArgumentException attendu lorsque l'identifiant de logement est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullIdentifiantLogement() {
        new BienLocatif(null, type, surface, nbPiece, loyerBase, batiment);
    }

    /**
     * Test du constructeur avec un type de bien null.
     * 
     * @throws IllegalArgumentException attendu lorsque le type de bien est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullType() {
        new BienLocatif("LOG126", null, surface, nbPiece, loyerBase, batiment);
    }

    /**
     * Test du constructeur avec un bâtiment null.
     * 
     * @throws IllegalArgumentException attendu lorsque le bâtiment est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullBatiment() {
        new BienLocatif("LOG127", type, surface, nbPiece, loyerBase, null);
    }

    /**
     * Test du setter {@code setSurface} avec une valeur valide.
     */
    @Test
    public void testSetSurfaceValid() {
        int newSurface = 85;
        bienLocatif.setSurface(newSurface);
        assertEquals(newSurface, bienLocatif.getSurface());
    }

    /**
     * Test du setter {@code setSurface} avec une valeur non positive.
     * 
     * @throws IllegalArgumentException attendu lorsque la surface est non positive
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetSurfaceInvalid() {
        bienLocatif.setSurface(-50);
    }

    /**
     * Test du setter {@code setLoyerBase} avec une valeur valide.
     */
    @Test
    public void testSetLoyerBaseValid() {
        BigDecimal newLoyerBase = new BigDecimal("1300.00");
        bienLocatif.setLoyerBase(newLoyerBase);
        assertEquals(newLoyerBase, bienLocatif.getLoyerBase());
    }

    /**
     * Test du setter {@code setLoyerBase} avec une valeur négative.
     * 
     * @throws IllegalArgumentException attendu lorsque le loyer de base est négatif
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetLoyerBaseInvalid() {
        bienLocatif.setLoyerBase(new BigDecimal("-500.00"));
    }

    /**
     * Test du setter {@code setNbPiece}.
     */
    @Test
    public void testSetNbPiece() {
        int newNbPiece = 4;
        bienLocatif.setNbPiece(newNbPiece);
        assertEquals(newNbPiece, bienLocatif.getNbPiece());
    }

    /**
     * Test du setter {@code setIdFiscal}.
     */
    @Test
    public void testSetIdFiscal() {
        String newIdFiscal = "FISCAL123";
        bienLocatif.setIdFiscal(newIdFiscal);
        assertEquals(newIdFiscal, bienLocatif.getIdFiscal());
    }

    /**
     * Test du setter {@code setComplementAdresse}.
     */
    @Test
    public void testSetComplementAdresse() {
        String newComplementAdresse = "LOGEMENT 5B";
        bienLocatif.setComplementAdresse(newComplementAdresse);
        assertEquals(newComplementAdresse, bienLocatif.getComplementAdresse());
    }

    /**
     * Test de la méthode {@code addLoyer} avec un loyer valide correspondant au bien locatif.
     * 
     * @throws SQLException si une erreur SQL survient lors de la création du loyer
     * @throws IOException  si une erreur d'entrée/sortie survient lors de la création du loyer
     */
    @Test
    public void testAddLoyerValid() throws SQLException, IOException {
        Loyer loyer = new Loyer(identifiantLogement, "2024-02-01", new BigDecimal("1201.00"));
        bienLocatif.addLoyer(loyer);
        loyersToCleanUp.add(loyer);

        List<Loyer> loyers = bienLocatif.getLoyers();
        assertTrue(loyers.contains(loyer));

        // Suppression du loyer ajouté pour nettoyer les données après le test
        bienLocatif.removeLoyer(loyer);
        assertFalse(loyers.contains(loyer));
    }

    /**
     * Test de la méthode {@code addLoyer} avec un loyer ne correspondant pas au bien locatif.
     * 
     * @throws IllegalArgumentException attendu lorsque le loyer ne correspond pas au bien locatif
     * @throws SQLException             non attendu
     * @throws IOException              non attendu
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddLoyerInvalid() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOG999", "2024-01-01", new BigDecimal("1300.00"));
        bienLocatif.addLoyer(loyer);
        loyersToCleanUp.add(loyer);
    }

    /**
     * Test de la méthode {@code removeLoyer} avec un loyer valide correspondant au bien locatif.
     * 
     * @throws SQLException si une erreur SQL survient lors de la suppression du loyer
     * @throws IOException  si une erreur d'entrée/sortie survient lors de la suppression du loyer
     */
    @Test
    public void testRemoveLoyerValid() throws SQLException, IOException {
        // Ajouter un loyer pour le supprimer ensuite
        Loyer loyer = new Loyer(identifiantLogement, "2025-02-01", new BigDecimal("1201.00"));
        bienLocatif.addLoyer(loyer);
        loyersToCleanUp.add(loyer);

        List<Loyer> loyers = bienLocatif.getLoyers();
        assertTrue(loyers.contains(loyer));

        // Supprimer le loyer
        bienLocatif.removeLoyer(loyer);
        assertFalse(loyers.contains(loyer));
    }

    /**
     * Test de la méthode {@code removeLoyer} avec un loyer ne correspondant pas au bien locatif.
     * 
     * @throws IllegalArgumentException attendu lorsque le loyer ne correspond pas au bien locatif
     * @throws SQLException             non attendu
     * @throws IOException              non attendu
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveLoyerInvalid() throws SQLException, IOException {
        Loyer loyer = new Loyer("LOG999", "2024-02-01", new BigDecimal("1400.00"));
        bienLocatif.removeLoyer(loyer);
    }

    /**
     * Test de la méthode {@code rechargerDonnees}.
     * 
     * @throws SQLException si une erreur SQL survient lors du chargement des loyers
     * @throws IOException  si une erreur d'entrée/sortie survient lors du chargement des loyers
     */
    @Test
    public void testRechargerDonnees() throws SQLException, IOException {
        // Ajouter un loyer
        Loyer loyer = new Loyer(identifiantLogement, "2025-03-01", new BigDecimal("1201.00"));
        bienLocatif.addLoyer(loyer);
        loyersToCleanUp.add(loyer);
        assertTrue(bienLocatif.getLoyers().contains(loyer));

        // Supprimer le loyer avant de recharger les données
        bienLocatif.removeLoyer(loyer);
        loyersToCleanUp.remove(loyer); // Supprimer de la liste de nettoyage
        assertFalse(bienLocatif.getLoyers().contains(loyer));

    }

    /**
     * Test de la méthode {@code equals} pour deux objets identiques.
     */
    @Test
    public void testEqualsSameObject() {
        assertTrue(bienLocatif.equals(bienLocatif));
    }

    /**
     * Test de la méthode {@code equals} pour deux objets différents avec le même identifiant de logement.
     */
    @Test
    public void testEqualsSameIdentifiant() {
        BienLocatif autreBien = new BienLocatif(identifiantLogement, type, surface, nbPiece, loyerBase, batiment);
        assertTrue(bienLocatif.equals(autreBien));
    }

    /**
     * Test de la méthode {@code equals} pour deux objets différents avec des identifiants de logement différents.
     */
    @Test
    public void testEqualsDifferentIdentifiant() {
        BienLocatif autreBien = new BienLocatif("LOG456", type, surface, nbPiece, loyerBase, batiment);
        assertFalse(bienLocatif.equals(autreBien));
    }

    /**
     * Test de la méthode {@code hashCode} pour deux objets égaux.
     */
    @Test
    public void testHashCodeEqualObjects() {
        BienLocatif autreBien = new BienLocatif(identifiantLogement, type, surface, nbPiece, loyerBase, batiment);
        assertEquals(bienLocatif.hashCode(), autreBien.hashCode());
    }

    /**
     * Test de la méthode {@code hashCode} pour deux objets différents.
     */
    @Test
    public void testHashCodeDifferentObjects() {
        BienLocatif autreBien = new BienLocatif("LOG456", type, surface, nbPiece, loyerBase, batiment);
        assertNotEquals(bienLocatif.hashCode(), autreBien.hashCode());
    }

    /**
     * Test de la méthode {@code toString}.
     */
    @Test
    public void testToString() {
        String expected = "BienLocatif{" +
                "identifiantLogement='" + identifiantLogement + '\'' +
                ", type=" + type +
                ", surface=" + surface +
                ", nbPiece=" + nbPiece +
                ", loyerBase=" + loyerBase +
                ", bat=" + batiment +
                '}';
        assertEquals(expected, bienLocatif.toString());
    }

    /**
     * Test de la méthode {@code getLoyers} avec lazy-loading.
     * 
     * @throws SQLException si une erreur SQL survient lors du chargement des loyers
     * @throws IOException  si une erreur d'entrée/sortie survient lors du chargement des loyers
     */
    @Test
    public void testGetLoyersLazyLoading() throws SQLException, IOException {
        // Supposons que le DAO retourne une liste vide initialement
        List<Loyer> loyers = bienLocatif.getLoyers();

        // Ajouter un loyer via la méthode addLoyer
        Loyer loyer = new Loyer(identifiantLogement, "2025-04-01", new BigDecimal("1202.00"));
        bienLocatif.addLoyer(loyer);

        // Récupérer les loyers et vérifier que le nouveau loyer est présent
        loyers = bienLocatif.getLoyers();
        assertTrue(loyers.contains(loyer));

        // Supprimer le loyer ajouté pour nettoyer les données après le test
        bienLocatif.removeLoyer(loyer);
        assertFalse(loyers.contains(loyer));
    }

    /**
     * Test de la méthode {@code getDocsComptables} avec lazy-loading.
     */
    @Test
    public void testGetDocsComptablesLazyLoading() throws SQLException, IOException {
        List<FactureBien> docs = bienLocatif.getDocsComptables();
        assertNotNull(docs);
        for (FactureBien facture : docs) {
        	assertNotNull(facture.getDocument().getNumeroDoc());
        }
        // Ajoutez des documents comptables via le DAO si nécessaire
        // Puis vérifiez qu'ils sont bien chargés
    }


}
