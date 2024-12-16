package testmodeledao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Adresse;
import modele.Entreprise;
import modele.dao.DaoAdresse;
import modele.dao.DaoEntreprise;

public class TestDaoEntreprise {

    private DaoEntreprise daoEntreprise;
    private DaoAdresse daoAdresse;

    @Before
    public void setUp() {
        daoEntreprise = new DaoEntreprise();
        daoAdresse = new DaoAdresse();
    }

    private void ajouterEntreprise(Entreprise entreprise) throws SQLException, IOException {
        daoEntreprise.create(entreprise);
    }

    private void supprimerEntreprise(Entreprise entreprise) throws SQLException, IOException {
        daoEntreprise.delete(entreprise);
    }

    @Test
    public void testDaoEntrepriseCreate() throws SQLException, IOException {
        Adresse adresse = daoAdresse.findById("ADDR001");

        Entreprise entreprise = new Entreprise("SIRETTEST01", "TestEntreprise", "Technologie", adresse);
        ajouterEntreprise(entreprise);

        Entreprise entrepriseRecup = daoEntreprise.findById("SIRETTEST01");

        assertNotNull("L'entreprise n'a pas été trouvée après insertion.", entrepriseRecup);
        assertEquals("Le SIRET est incorrect.", "SIRETTEST01", entrepriseRecup.getSiret());
        assertEquals("Le nom est incorrect.", "TestEntreprise", entrepriseRecup.getNom());
        assertEquals("Le secteur est incorrect.", "Technologie", entrepriseRecup.getSecteur());
        assertEquals("L'adresse associée est incorrecte.", "ADDR001", entrepriseRecup.getAdresse().getIdAdresse());

        supprimerEntreprise(entreprise);
    }

    @Test
    public void testDaoEntrepriseUpdate() throws SQLException, IOException {
        Adresse adresse = daoAdresse.findById("ADDR002");

        Entreprise entreprise = new Entreprise("SIRETTEST02", "OldName", "Industrie", adresse);
        ajouterEntreprise(entreprise);

        entreprise.setNom("NewName");
        entreprise.setSecteur("Innovation");
        daoEntreprise.update(entreprise);

        Entreprise entrepriseRecup = daoEntreprise.findById("SIRETTEST02");

        assertNotNull("L'entreprise mise à jour n'a pas été trouvée.", entrepriseRecup);
        assertEquals("Le nom mis à jour est incorrect.", "NewName", entrepriseRecup.getNom());
        assertEquals("Le secteur mis à jour est incorrect.", "Innovation", entrepriseRecup.getSecteur());

        supprimerEntreprise(entreprise);
    }

    @Test
    public void testDaoEntrepriseDelete() throws SQLException, IOException {
        Adresse adresse = daoAdresse.findById("ADDR003");

        Entreprise entreprise = new Entreprise("SIRETTEST03", "TestDelete", "Commerce", adresse);
        ajouterEntreprise(entreprise);

        supprimerEntreprise(entreprise);

        Entreprise entrepriseRecup = daoEntreprise.findById("SIRETTEST03");
        assertNull("L'entreprise n'a pas été correctement supprimée.", entrepriseRecup);
    }

    @Test
    public void testDaoEntrepriseFindAll() throws SQLException, IOException {
        Adresse adresse = daoAdresse.findById("ADDR004");

        Entreprise entreprise1 = new Entreprise("SIRETTEST04", "Entreprise1", "Services", adresse);
        Entreprise entreprise2 = new Entreprise("SIRETTEST05", "Entreprise2", "Construction", adresse);

        ajouterEntreprise(entreprise1);
        ajouterEntreprise(entreprise2);

        List<Entreprise> entreprises = daoEntreprise.findAll();

        assertTrue("L'entreprise 1 n'est pas présente dans la liste récupérée.",
                entreprises.stream().anyMatch(e -> "SIRETTEST04".equals(e.getSiret())));
        assertTrue("L'entreprise 2 n'est pas présente dans la liste récupérée.",
                entreprises.stream().anyMatch(e -> "SIRETTEST05".equals(e.getSiret())));

        supprimerEntreprise(entreprise1);
        supprimerEntreprise(entreprise2);
    }

    @Test
    public void testDaoEntrepriseFindByIdInvalid() throws SQLException, IOException {
        Entreprise entreprise = daoEntreprise.findById("INVALID_SIRET");
        assertNull("La méthode findById devrait retourner null pour un SIRET inexistant.", entreprise);
    }
}
