package testmodeledao;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.BienLocatif;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.dao.DaoBienLocatif;

public class TestDaoFactureBien {
	FactureBien fbien;
	BienLocatif bien;
	DocumentComptable docCompta;

	@Before
	public void setUp() throws Exception {
		bien = new DaoBienLocatif().findById("LOG001");
		docCompta = new DocumentComptable("DOC0001", null, null, null, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		
	}

}
