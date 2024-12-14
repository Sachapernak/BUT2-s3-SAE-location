package testmodeledao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import modele.BienLocatif;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.TypeDoc;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoFactureBien;

public class TestDaoFactureBien {
	FactureBien fbien;
	BienLocatif bien;
	DocumentComptable docCompta;
	DaoFactureBien dao;

	@Before
	public void setUp() throws Exception {
		
		dao = new DaoFactureBien();
		bien = new DaoBienLocatif().findById("LOG003");
		docCompta = new DocumentComptable("DOC001", "2023-07-05", 
				TypeDoc.QUITTANCE, new BigDecimal("650.00"),
				"quittance_juil_2023.pdf");
		

	}


	@Test
	public void testCreate() throws SQLException, IOException {
		
		fbien = new FactureBien(bien, docCompta, 1);
		dao.create(fbien);
		
		FactureBien recup = new DaoFactureBien().findById(
				fbien.getBien().getIdentifiantLogement(),
				fbien.getDocument().getNumeroDoc(),
				fbien.getDocument().getDateDoc());
		
		dao.delete(fbien);
		
		assertNotNull(recup);
		assertEquals(fbien.getBien(), recup.getBien());
		assertEquals(fbien.getDocument(), recup.getDocument());
		assertEquals(fbien.getPartDesCharges(), recup.getPartDesCharges(), 0.01f);
		
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException, IOException {
		fbien = new FactureBien(bien, docCompta, 1);
		dao.create(fbien);
		fbien.setPartDesCharges(0.5f);
		dao.update(fbien);
		
		FactureBien recup = new DaoFactureBien().findById(
				fbien.getBien().getIdentifiantLogement(),
				fbien.getDocument().getNumeroDoc(),
				fbien.getDocument().getDateDoc());
		
		assertEquals(0.5f, recup.getPartDesCharges(), 0.01f);
		dao.delete(fbien);
		
	}
	
	@Test
	public void testDelete() throws SQLException, IOException {
		fbien = new FactureBien(bien, docCompta, 1);
		dao.create(fbien);
		
		dao.delete(fbien);
		
		FactureBien recup = new DaoFactureBien().findById(
				fbien.getBien().getIdentifiantLogement(),
				fbien.getDocument().getNumeroDoc(),
				fbien.getDocument().getDateDoc());
		
		assertNull(recup);
		
	}
	
	@Test
	public void testFindByIdLogement() throws SQLException, IOException {
		
		fbien = new FactureBien(bien, docCompta, 1);
		
		
		DocumentComptable docCompta2 = new DocumentComptable("DOC002", "2023-08-10", 
				TypeDoc.FACTURE, new BigDecimal("350.00"),
				"facture_reparation_082023.pdf");
		FactureBien fbien2 = new FactureBien(bien, docCompta2, 1);
		
		
		BienLocatif bien2 = new DaoBienLocatif().findById("LOG001");
		DocumentComptable docCompta3 = new DocumentComptable("DOC004", "2023-10-01", 
				TypeDoc.FACTURE, new BigDecimal("0.00"),
				"devis.pdf");
		
		FactureBien fbien3 = new FactureBien(bien2, docCompta3, 1);
		
		dao.create(fbien);
		dao.create(fbien2);
		dao.create(fbien3);
		
		List<FactureBien> flist = dao.findByIdLogement(bien.getIdentifiantLogement());
		
		 boolean contientTest1 = flist.stream()
                 .anyMatch(facB -> facB.getBien().equals(bien) && docCompta.getNumeroDoc().equals(facB.getDocument().getNumeroDoc()));
		 boolean contientTest2 = flist.stream()
                 .anyMatch(facB -> facB.getBien().equals(bien) && docCompta2.getNumeroDoc().equals(facB.getDocument().getNumeroDoc()));
		 boolean contientTest3 = flist.stream()
                 .anyMatch(facB -> facB.getBien().equals(bien) && docCompta3.getNumeroDoc().equals(facB.getDocument().getNumeroDoc()));
	
		 
		 assertTrue(contientTest1);
		 assertTrue(contientTest2);
		 assertFalse(contientTest3);
		 
		 dao.delete(fbien);
		 dao.delete(fbien2);
		 dao.delete(fbien3);
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testExceptionFindAll() throws SQLException, IOException {
		dao.findAll();
		
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testExceptionCreateInstance() throws SQLException, IOException {
		ResultSet rs = null;
		dao.createInstance(rs);
		
	}
	
	

}
