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

import modele.ICC;
import modele.dao.DaoICC;

public class TestDaoICC {

    private DaoICC daoICC;
    private ICC iccRecup;

    @Before
    public void setUp() {
        this.daoICC = new DaoICC();
    }

    private void ajouterICC(ICC icc) throws SQLException, IOException {
        daoICC.create(icc);
    }

    private void supprimerICC(ICC icc) throws SQLException, IOException {
        daoICC.delete(icc);
    }

    @Test
    public void testDaoICCCreate() throws SQLException, IOException {
        ICC iccCreate = new ICC("2024", "1", 100);

        ajouterICC(iccCreate);
        iccRecup = daoICC.findById("2024", "1");

        assertNotNull("ICC non trouvée après insertion.", iccRecup);
        assertEquals("L'année est incorrecte.", "2024", iccRecup.getAnnee());
        assertEquals("Le trimestre est incorrect.", "1", iccRecup.getTrimestre());
        assertEquals("L'indice est incorrect.", 100, iccRecup.getIndiceICC());

        supprimerICC(iccCreate);
    }

    @Test
    public void testDaoICCUpdate() throws SQLException, IOException {
        ICC iccUpdate = new ICC("2022", "2", 200);
        ajouterICC(iccUpdate);

        iccRecup = daoICC.findById("2022", "2");
        assertNotNull("ICC non trouvée après insertion.", iccRecup);
        assertEquals("L'indice initial est incorrect.", 200, iccRecup.getIndiceICC());

        // Mise à jour de l'indice
        iccUpdate = new ICC("2022", "2", 250);
        daoICC.update(iccUpdate);

        iccRecup = daoICC.findById("2022", "2");
        assertEquals("L'indice mis à jour est incorrect.", 250, iccRecup.getIndiceICC());

        supprimerICC(iccUpdate);
    }

    @Test
    public void testDaoICCSuppressionDeuxFois() throws SQLException, IOException {
        ICC iccTest = new ICC("2025", "3", 300);
        ajouterICC(iccTest);

        iccRecup = daoICC.findById("2025", "3");
        assertNotNull("ICC non trouvée après insertion.", iccRecup);

        supprimerICC(iccTest);
        iccRecup = daoICC.findById("2025", "3");
        assertNull("L'ICC n'a pas été correctement supprimée.", iccRecup);

        // Deuxième suppression pour vérifier qu'il n'y a pas d'erreur
        supprimerICC(iccTest);
    }

    @Test
    public void testDaoICCFindAll() throws SQLException, IOException {
        ICC iccFindAll = new ICC("2026", "4", 400);
        ajouterICC(iccFindAll);

        List<ICC> list = daoICC.findAll();

        boolean contientICC = list.stream()
                                  .anyMatch(icc -> "2026".equals(icc.getAnnee()) && "4".equals(icc.getTrimestre()));

        assertTrue("L'ICC insérée n'est pas présente dans la liste récupérée.", contientICC);

        supprimerICC(iccFindAll);
    }

    @Test
    public void testFindByIdInvalid() throws SQLException, IOException {
        ICC icc = daoICC.findById("9999", "9");
        assertNull("La méthode findById devrait retourner null pour un ID inexistant.", icc);
    }

    @Test
    public void testDeleteNonExistent() throws SQLException, IOException {
        ICC fakeICC = new ICC("0000", "0", 0);
        daoICC.delete(fakeICC);
        ICC icc = daoICC.findById("0000", "0");
        assertNull("La suppression d'une ICC inexistante ne devrait pas lever une erreur.", icc);
    }
}
