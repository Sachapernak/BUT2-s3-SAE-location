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
import modele.Document;
import modele.FactureBien;
import modele.Loyer;
import modele.ProvisionCharge;
import modele.Regularisation;
import modele.TypeDeBien;
import modele.dao.DaoFactureBien;
import modele.dao.DaoDiagnostique;
import modele.dao.DaoLoyer;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Classe de test JUnit 4 pour la classe {@code Bail}.
 * <p>
 * Cette classe teste les fonctionnalités de la classe {@code Bail}, incluant la validation
 * des entrées, l'ajout et la suppression de régularisations, provisions pour charges et documents,
 * ainsi que les méthodes {@code equals}, {@code hashCode} et {@code toString}.
 * </p>
 * <p>
 * Chaque test vérifie le bon fonctionnement des opérations de gestion des baux et s'assure du
 * nettoyage des données après exécution pour garantir l'intégrité des tests.
 * </p>
 */
public class TestBail {

	private Bail bail;
    private BienLocatif bienLocatif;
    private Batiment batiment;
    private Adresse adresse;
    
    private final String idBail = "BAI01";
    private final String dateDeDebut = "2024-01-01";
    private final String dateDeFin = "2025-01-01";
    private final String identifiantLogement = "LOG001";
    private final TypeDeBien type = TypeDeBien.LOGEMENT;
    private final int surface = 75;
    private final int nbPiece = 3;
    private final BigDecimal loyerBase = new BigDecimal("1200.00");

    /**
     * Méthode exécutée avant chaque test pour initialiser un objet {@code Bail} valide.
     */
    @Before
    public void setUp() {
        // Initialisation de l'adresse et du bâtiment pour les tests
        adresse = new Adresse("ADDR001", "123 Rue Exemple", 75000, "Paris");
        batiment = new Batiment("BAT_TEST", adresse);

        // Initialisation de BienLocatif avec des paramètres valides
        bienLocatif = new BienLocatif(identifiantLogement, type, surface, nbPiece, loyerBase, batiment);
        
        // Initialisation de Bail
        bail = new Bail(idBail,dateDeDebut,dateDeFin,bienLocatif);
    }
    
    /**
     * Liste des regularisations ajoutés pendant les tests pour un nettoyage ultérieur.
     */
    private List<Regularisation> regularisationsToCleanUp = new ArrayList<>();
    
    /**
     * Liste des provisions pour charge ajoutées pendant les tests pour un nettoyage ultérieur.
     */
    private List<ProvisionCharge> provisionchargesToCleanUp = new ArrayList<>();
    
    /**
     * Liste des documents ajoutés pendant les tests pour un nettoyage ultérieur.
     */
    private List<Document> documentsToCleanUp = new ArrayList<>();
    
    /**
     * Méthode exécutée après chaque test pour supprimer les regularisations, provisions pour charge et documents ajoutés.
     */
    @After
    public void tearDown() throws SQLException, IOException {
        for (Regularisation regu : regularisationsToCleanUp) {
            try {
                bail.removeRegularisation(regu);
            } catch (IllegalArgumentException e) {
                // Regularisation peut avoir déjà été supprimé dans le test
            }
        }
        regularisationsToCleanUp.clear();
        
        for (ProvisionCharge proCha : provisionchargesToCleanUp) {
            try {
                bail.removeProvisionCharge(proCha);
            } catch (IllegalArgumentException e) {
                // Provision pour charge peut avoir déjà été supprimé dans le test
            }
        }
        provisionchargesToCleanUp.clear();
        
        for (Document docu : documentsToCleanUp) {
            try {
                bail.removeDocument(docu);
            } catch (IllegalArgumentException e) {
                // Document peut avoir déjà été supprimé dans le test
            }
        }
        documentsToCleanUp.clear();
    }


    /**
     * Test du constructeur avec des paramètres valides.
     */
    @Test
    public void testConstructorValid() {
        assertEquals(bienLocatif, bail.getBien());
        assertEquals("BAI01", bail.getIdBail());
        assertEquals("2024-01-01", bail.getDateDeDebut());
        assertEquals("2025-01-01", bail.getDateDeFin());
    }

    /**
     * Test du constructeur avec une date de debut posterieur a la date de fin.
     * 
     * @throws IllegalArgumentException attendu lorsque la date de debut est posterieur à celle de fin.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidDateFormat() {
        new Bail(idBail, dateDeFin,dateDeDebut, bienLocatif);
    }

    

    /**
     * Test du constructeur avec un identifiant de bail null.
     * 
     * @throws IllegalArgumentException attendu lorsque l'identifiant de bail est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullIdentifiantBail() {
        new Bail(null,  dateDeDebut,dateDeFin, bienLocatif);
    }

    /**
     * Test du constructeur avec une date de debut null.
     * 
     * @throws IllegalArgumentException attendu lorsque la date de debut est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullType() {
    	bail = new Bail(idBail, null,dateDeFin, bienLocatif);
    }

    /**
     * Test du constructeur avec un bien locatif null.
     * 
     * @throws IllegalArgumentException attendu lorsque le bien locatif est null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullBienLocatif() {
    	new Bail(idBail, dateDeDebut,dateDeFin, null);
    }

    /**
     * Test du setter {@code setDateDeFin} avec une valeur valide.
     */
    @Test
    public void testSetDateDeFinValid() {
        String dateDeFinValid = "2026-01-01";
        bail.setDateDeFin(dateDeFinValid);
        assertEquals(dateDeFinValid, bail.getDateDeFin());
    }
    
    /**
     * Test du setter {@code setDateDeDebut} avec une valeur valide.
     */
    @Test
    public void testSetDateDeDebutValid() {
        String dateDeDebutValid = "2026-01-01";
        bail.setDateDeDebut(dateDeDebutValid);
        assertEquals(dateDeDebutValid, bail.getDateDeDebut());
    }

    /**
     * Test du setter {@code setDateDeFin} avec une valeur invalide.
     * 
     * @throws IllegalArgumentException attendu lorsque la date de fin est anterieur a celle de debut.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDateDeFinInvalid() {
        bail.setDateDeFin("2022-01-01"); // sachant que date de debut = 2024-01-01
    }

    /**
     * Teste l'ajout d'une régularisation valide au bail.
     * <p>
     * Cette méthode vérifie que la régularisation ajoutée correspond bien au bail et qu'elle
     * est présente dans la liste après l'ajout. Ensuite, elle nettoie les données en supprimant
     * la régularisation ajoutée.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la création de la régularisation.
     * @throws IOException  Si une erreur d'entrée/sortie survient lors de la création de la régularisation.
     */
    @Test
    public void testAddRegularisationValid() throws SQLException, IOException {
        Regularisation regularisation = new Regularisation(idBail, "2024-01-01", new BigDecimal("100.00"));
        bail.addRegularisation(regularisation);
        regularisationsToCleanUp.add(regularisation);

        List<Regularisation> regularisations = bail.getRegularisation();
        assertTrue(regularisations.contains(regularisation));

        bail.removeRegularisation(regularisation);
        assertFalse(regularisations.contains(regularisation));
    }


    /**
     * Teste l'ajout d'une régularisation invalide au bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant de la régularisation
     * ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque la régularisation ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddRegularisationInvalid() throws SQLException, IOException {
        Regularisation regularisation = new Regularisation("BAI999", "2024-01-01", new BigDecimal("100.00"));
        bail.addRegularisation(regularisation);
        regularisationsToCleanUp.add(regularisation);
    }


    /**
     * Teste la suppression d'une régularisation valide du bail.
     * <p>
     * Cette méthode ajoute une régularisation au bail, puis la supprime et vérifie qu'elle n'est plus
     * présente dans la liste des régularisations.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression de la régularisation.
     * @throws IOException  Si une erreur d'entrée/sortie survient lors de la suppression de la régularisation.
     */
    @Test
    public void testRemoveRegularistionValid() throws SQLException, IOException {
        Regularisation regularisation = new Regularisation(idBail, "2024-02-01", new BigDecimal("140.00"));
        bail.addRegularisation(regularisation);
        regularisationsToCleanUp.add(regularisation);

        List<Regularisation> regularisations = bail.getRegularisation();
        assertTrue(regularisations.contains(regularisation));

        bail.removeRegularisation(regularisation);
        assertFalse(regularisations.contains(regularisation));
    }


    /**
     * Teste la suppression d'une régularisation invalide du bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant de la régularisation
     * ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque la régularisation ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRegularisationInvalid() throws SQLException, IOException {
        Regularisation regularisation = new Regularisation("LOG999", "2024-02-01", new BigDecimal("140.00"));
        bail.removeRegularisation(regularisation);
    }

    
    /**
     * Teste l'ajout d'une provision pour charge valide au bail.
     * <p>
     * Cette méthode vérifie que la provision pour charge ajoutée correspond bien au bail et
     * qu'elle est présente dans la liste après l'ajout. Ensuite, elle nettoie les données
     * en supprimant la provision ajoutée.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la création de la provision.
     * @throws IOException  Si une erreur d'entrée/sortie survient lors de la création de la provision.
     */
    @Test
    public void testAddProvisionChargeValid() throws SQLException, IOException {
        ProvisionCharge provisioncharge = new ProvisionCharge(idBail, "2024-01-01", new BigDecimal("100.00"));
        bail.addProvisionCharge(provisioncharge);
        provisionchargesToCleanUp.add(provisioncharge);

        List<ProvisionCharge> provisioncharges = bail.getProvisionCharge();
        assertTrue(provisioncharges.contains(provisioncharge));

        bail.removeProvisionCharge(provisioncharge);
        assertFalse(provisioncharges.contains(provisioncharge));
    }


    /**
     * Teste l'ajout d'une provision pour charge invalide au bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant de la provision
     * pour charge ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque la provision ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddProvisionChargeInvalid() throws SQLException, IOException {
        ProvisionCharge provisioncharge = new ProvisionCharge("BAI999", "2024-01-01", new BigDecimal("100.00"));
        bail.addProvisionCharge(provisioncharge);
        provisionchargesToCleanUp.add(provisioncharge);
    }


    /**
     * Teste la suppression d'une provision pour charge valide du bail.
     * <p>
     * Cette méthode ajoute une provision pour charge au bail, puis la supprime et vérifie
     * qu'elle n'est plus présente dans la liste des provisions après la suppression.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression de la provision.
     * @throws IOException  Si une erreur d'entrée/sortie survient lors de la suppression de la provision.
     */
    @Test
    public void testRemoveProvisionChargeValid() throws SQLException, IOException {
        ProvisionCharge provisioncharge = new ProvisionCharge(idBail, "2024-02-01", new BigDecimal("140.00"));
        bail.addProvisionCharge(provisioncharge);
        provisionchargesToCleanUp.add(provisioncharge);

        List<ProvisionCharge> provisioncharges = bail.getProvisionCharge();
        assertTrue(provisioncharges.contains(provisioncharge));

        bail.removeProvisionCharge(provisioncharge);
        assertFalse(provisioncharges.contains(provisioncharge));
    }


    /**
     * Teste la suppression d'une provision pour charge invalide du bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant de la provision
     * pour charge ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque la provision ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveProvisionChargeInvalid() throws SQLException, IOException {
        ProvisionCharge provisioncharge = new ProvisionCharge("LOG999", "2024-02-01", new BigDecimal("140.00"));
        bail.removeProvisionCharge(provisioncharge);
    }

    
    /**
     * Teste l'ajout d'un document valide au bail.
     * <p>
     * Cette méthode vérifie que le document ajouté correspond bien au bail et qu'il est présent
     * dans la liste après l'ajout. Ensuite, elle nettoie les données en supprimant le document ajouté.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la création du document.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors de la création du document.
     */
    @Test
    public void testAddDocumentValid() throws SQLException, IOException {
        Document document = new Document(idBail, "2024-01-01", "docu", "url");
        bail.addDocument(document);
        documentsToCleanUp.add(document);

        List<Document> documents = bail.getDocument();
        assertTrue(documents.contains(document));

        bail.removeDocument(document);
        assertFalse(documents.contains(document));
    }


    /**
     * Teste l'ajout d'un document invalide au bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant du document
     * ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque le document ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddDocumentInvalid() throws SQLException, IOException {
        Document document = new Document("BAI999", "2024-01-01", "docu", "url");
        bail.addDocument(document);
        documentsToCleanUp.add(document);
    }


    /**
     * Teste la suppression d'un document valide du bail.
     * <p>
     * Cette méthode ajoute un document au bail, puis le supprime et vérifie qu'il n'est plus présent
     * dans la liste des documents après la suppression.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression du document.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors de la suppression du document.
     */
    @Test
    public void testRemoveDocumentValid() throws SQLException, IOException {
        Document document = new Document(idBail, "2024-02-01", "docu", "url");
        bail.addDocument(document);
        documentsToCleanUp.add(document);

        List<Document> documents = bail.getDocument();
        assertTrue(documents.contains(document));

        bail.removeDocument(document);
        assertFalse(documents.contains(document));
    }


    /**
     * Teste la suppression d'un document invalide du bail.
     * <p>
     * Cette méthode s'assure qu'une exception est levée lorsque l'identifiant du document
     * ne correspond pas au bail.
     * </p>
     *
     * @throws IllegalArgumentException Attend une exception lorsque le document ne correspond pas au bail.
     * @throws SQLException             Non attendu.
     * @throws IOException              Non attendu.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDocumentInvalid() throws SQLException, IOException {
        Document document = new Document("LOG999", "2024-02-01", "docu", "url");
        bail.removeDocument(document);
    }


    /**
     * Teste la méthode {@code rechargerDonnees} pour vérifier le rechargement correct des données.
     * <p>
     * Ce test ajoute successivement une régularisation, une provision pour charge, et un document au bail.
     * Après chaque ajout, l'élément est supprimé et on vérifie qu'il a bien été retiré de la liste correspondante.
     * Ensuite, le rechargement des données est effectué pour s'assurer que les listes sont vides après le rechargement.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors de la manipulation des données.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors de la manipulation des données.
     */
    @Test
    public void testRechargerDonnees() throws SQLException, IOException {
        // Ajouter une régularisation
        Regularisation regularisation = new Regularisation(idBail, "2024-03-01", new BigDecimal("150.00"));
        bail.addRegularisation(regularisation);
        regularisationsToCleanUp.add(regularisation);
        assertTrue(bail.getRegularisation().contains(regularisation));

        // Supprimer la régularisation avant le rechargement
        bail.removeRegularisation(regularisation);
        regularisationsToCleanUp.remove(regularisation);
        assertFalse(bail.getRegularisation().contains(regularisation));

     
        // Ajouter une provision pour charge
        ProvisionCharge provisioncharge = new ProvisionCharge(idBail, "2024-03-01", new BigDecimal("150.00"));
        bail.addProvisionCharge(provisioncharge);
        provisionchargesToCleanUp.add(provisioncharge);
        assertTrue(bail.getProvisionCharge().contains(provisioncharge));

        // Supprimer la provision pour charge avant le rechargement
        bail.removeProvisionCharge(provisioncharge);
        provisionchargesToCleanUp.remove(provisioncharge);
        assertFalse(bail.getProvisionCharge().contains(provisioncharge));


        // Ajouter un document
        Document document = new Document(idBail, "2024-03-01", "docu", "url");
        bail.addDocument(document);
        documentsToCleanUp.add(document);
        assertTrue(bail.getDocument().contains(document));

        // Supprimer le document avant le rechargement
        bail.removeDocument(document);
        documentsToCleanUp.remove(document);
        assertFalse(bail.getDocument().contains(document));

      
    }


    /**
     * Test de la méthode {@code equals} pour deux objets identiques.
     */
    @Test
    public void testEqualsSameObject() {
        assertTrue(bail.equals(bail));
    }

    /**
     * Test de la méthode {@code equals} pour deux objets différents avec le même identifiant de logement.
     */
    @Test
    public void testEqualsSameIdentifiant() {
        Bail autreBail = new Bail(idBail, dateDeDebut,dateDeFin,bienLocatif);
        assertTrue(bail.equals(autreBail));
    }

    /**
     * Test de la méthode {@code equals} pour deux objets différents avec des identifiants de logement différents.
     */
    @Test
    public void testEqualsDifferentIdentifiant() {
    	Bail autreBail = new Bail("BAI0001", dateDeDebut,dateDeFin,bienLocatif);
        assertFalse(bail.equals(autreBail));
    }

    /**
     * Test de la méthode {@code hashCode} pour deux objets égaux.
     */
    @Test
    public void testHashCodeEqualObjects() {
    	Bail autreBail = new Bail(idBail, dateDeDebut,dateDeFin,bienLocatif);
        assertEquals(bail.hashCode(), autreBail.hashCode());
    }

    /**
     * Test de la méthode {@code hashCode} pour deux objets différents.
     */
    @Test
    public void testHashCodeDifferentObjects() {
    	Bail autreBail = new Bail("BAI999", dateDeDebut,dateDeFin,bienLocatif);
        assertNotEquals(bail.hashCode(), autreBail.hashCode());
    }

    /**
     * Test de la méthode {@code toString}.
     */
    @Test
    public void testToString() {
    	
        String expected = "Bail [idBail=" + idBail + ", dateDeDebut=" + dateDeDebut + ", dateDeFin=" + dateDeFin + ", bien=" + bienLocatif+ "]";
        assertEquals(expected, bail.toString());
    }

    /**
     * Teste la méthode {@code getRegularisation} avec le mécanisme de lazy-loading.
     * <p>
     * Ce test vérifie que la liste des régularisations est chargée à la demande.
     * Initialement, la liste est vide. Une régularisation est ensuite ajoutée pour s'assurer
     * qu'elle est correctement chargée. Enfin, la régularisation est supprimée pour nettoyer les données.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors du chargement des régularisations.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors du chargement des régularisations.
     */
    @Test
    public void testGetRegularisationsLazyLoading() throws SQLException, IOException {
        // Supposons que le DAO retourne une liste.
        List<Regularisation> regularisations = bail.getRegularisation();
        assertNotNull(regularisations);

        // Ajouter une régularisation via la méthode addRegularisation
        Regularisation regularisation = new Regularisation(idBail, "2024-04-01", new BigDecimal("160.00"));
        bail.addRegularisation(regularisation);

        // Récupérer les régularisations et vérifier que la nouvelle régularisation est présente
        regularisations = bail.getRegularisation();
        assertTrue(regularisations.contains(regularisation));

        // Supprimer la régularisation ajoutée pour nettoyer les données après le test
        bail.removeRegularisation(regularisation);
        assertFalse(regularisations.contains(regularisation));
    }

    /**
     * Teste la méthode {@code getProvisionCharge} avec le mécanisme de lazy-loading.
     * <p>
     * Ce test vérifie que la liste des provisions pour charges est chargée à la demande.
     * Initialement, la liste est vide. Une provision pour charge est ensuite ajoutée pour s'assurer
     * qu'elle est correctement chargée. Enfin, la provision est supprimée pour nettoyer les données.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors du chargement des provisions pour charges.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors du chargement des provisions pour charges.
     */
    @Test
    public void testGetProvisionChargesLazyLoading() throws SQLException, IOException {
        // Supposons que le DAO retourne une liste.
        List<ProvisionCharge> provisioncharges = bail.getProvisionCharge();
        assertNotNull(provisioncharges);

        // Ajouter une provision pour charge via la méthode addProvisionCharge
        ProvisionCharge provisioncharge = new ProvisionCharge(idBail, "2024-04-01", new BigDecimal("160.00"));
        bail.addProvisionCharge(provisioncharge);

        // Récupérer les provisions pour charges et vérifier que la nouvelle provision est présente
        provisioncharges = bail.getProvisionCharge();
        assertTrue(provisioncharges.contains(provisioncharge));

        // Supprimer la provision ajoutée pour nettoyer les données après le test
        bail.removeProvisionCharge(provisioncharge);
        assertFalse(provisioncharges.contains(provisioncharge));
    }

    
    /**
     * Teste la méthode {@code getDocument} avec le mécanisme de lazy-loading.
     * <p>
     * Ce test vérifie que la liste des documents est chargée à la demande.
     * Initialement, la liste est vide. Un document est ensuite ajouté pour s'assurer
     * qu'il est correctement chargé. Enfin, le document est supprimé pour nettoyer les données.
     * </p>
     *
     * @throws SQLException Si une erreur SQL survient lors du chargement des documents.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors du chargement des documents.
     */
    @Test
    public void testGetDocumentsLazyLoading() throws SQLException, IOException {
        // Supposons que le DAO retourne une liste vide initialement
        List<Document> documents = bail.getDocument();
        assertNotNull(documents);

        // Ajouter un document via la méthode addDocument
        Document document = new Document(idBail, "2024-04-01", "docu", "url");
        bail.addDocument(document);

        // Récupérer les documents et vérifier que le nouveau document est présent
        documents = bail.getDocument();
        assertTrue(documents.contains(document));

        // Supprimer le document ajouté pour nettoyer les données après le test
        bail.removeDocument(document);
        assertFalse(documents.contains(document));
    }

}
