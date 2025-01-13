	package controleur;
	
	import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.Date;
	import java.util.List;
	
	
	import javax.swing.JOptionPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.table.DefaultTableModel;
	
	import modele.Adresse;
	import modele.Bail;
import modele.ChargeFixe;
import modele.ChargeIndex;
import modele.Contracter;
	import modele.Locataire;
	import modele.Loyer;
import modele.dao.DaoChargeFixe;
import modele.dao.DaoChargeIndex;
import modele.dao.DaoLocataire;
import modele.dao.DaoLoyer;
import vue.AfficherLocatairesActuels;
	
	import vue.QuittanceLoyerPrincipal;
	
	
	public class GestionTableQuittance implements ListSelectionListener, ChangeListener {
		
		private QuittanceLoyerPrincipal fenQuittanceLoyerPrincipal;
		private DaoLocataire daoLocataire;
		private DaoChargeFixe dcf;
		private DaoChargeIndex dci;
		private DaoLoyer dl;
		private JTable tableLocataires;
		private JTable tableBiensActuels;
		private JTable tableBiensAnciens;
		private JTable tableLoyer;
		private JTable tableChargeIndex;
		private JTable tableChargeFixe;
		
		public GestionTableQuittance(QuittanceLoyerPrincipal qlp)  {
			this.fenQuittanceLoyerPrincipal = qlp;
			this.tableLocataires = fenQuittanceLoyerPrincipal.getTableLocataires();
			this.tableBiensActuels = fenQuittanceLoyerPrincipal.getTableBiensActuels();
			this.tableBiensAnciens = fenQuittanceLoyerPrincipal.getTableBiensAnciens();
			this.tableLoyer = fenQuittanceLoyerPrincipal.getLoyerTable();
			this.tableChargeIndex = fenQuittanceLoyerPrincipal.getChargeIndexTable();
			this.tableChargeFixe = fenQuittanceLoyerPrincipal.getChargeCFTable();
			this.daoLocataire = new DaoLocataire();
			this.dcf = new DaoChargeFixe();
			this.dci = new DaoChargeIndex();
			this.dl = new DaoLoyer();
			
			
		}
	
		@Override
		public void valueChanged(ListSelectionEvent e) {
			JTable tableLoc = this.fenQuittanceLoyerPrincipal.getTableLocataires();
			Locataire locSelect = lireLigneTable(tableLoc);
			remplirTableLocation(this.fenQuittanceLoyerPrincipal.getTableBiensActuels(),this.fenQuittanceLoyerPrincipal.getTableBiensAnciens(),locSelect);
			
			
		}
		
	
	public void remplirTableLocation(JTable tableBienActuel, JTable tableBienAncien,Locataire locSelect) {
		DefaultTableModel modelBienActuel = (DefaultTableModel) tableBienActuel.getModel();
		DefaultTableModel modelBienAncien = (DefaultTableModel) tableBienAncien.getModel();
		UtilitaireTable.viderTable(tableBienActuel);
		UtilitaireTable.viderTable(tableBienAncien);
		try {
		List<Contracter> contrats = locSelect.getContrats();
		String loyerActuel = "";
		String dateRegularisation = "";
		for (Contracter contrat : contrats) {
			String dateSortie = contrat.getDateSortie();
			
			String dateEntree = contrat.getDateEntree();
			String partLoyer = String.valueOf(contrat.getPartLoyer());
			Bail bail = contrat.getBail();
			
			
			
			String batiment = bail.getBien().getBat().getIdBat();
			String complementAdr = bail.getBien().getComplementAdresse();
			
			List<Loyer> loyers;
			
				loyers = bail.getBien().getLoyers();
			
			int tailleListeLoyer = loyers.size();
			if (tailleListeLoyer>0) {
				loyerActuel = String.valueOf(loyers.get(tailleListeLoyer-1).getMontantLoyer());
			}
			String type = bail.getBien().getType().getValeur();
			String idBien = bail.getBien().getIdentifiantLogement();
			
			if (dateSortie != null ) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate dateSortieConverted = LocalDate.parse(dateSortie, formatter);
	            
	            if (dateSortieConverted.isBefore(LocalDate.now())) {
	            	modelBienAncien.addRow(new Object[] {idBien,dateEntree, type, batiment, complementAdr, loyerActuel, partLoyer, dateRegularisation});
				}}else {
					modelBienActuel.addRow(new Object[] {idBien,dateEntree, type, batiment, complementAdr, loyerActuel, partLoyer, dateRegularisation});
	
			}
		
			
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void remplirTableChargeIndex(List<ChargeIndex> listChargeIndex) {
		
	    UtilitaireTable.viderTable(tableChargeIndex);
	    DefaultTableModel model = (DefaultTableModel) tableChargeIndex.getModel();
	    model.setRowCount(0);
	    List<ChargeIndex> listeChargeIndex;
		try {
			if (listChargeIndex == null) {
				listeChargeIndex = dci.findAll();
			}else {
				listeChargeIndex =listChargeIndex;
			}
			
			
	        for (ChargeIndex chargeIndex:listeChargeIndex) {
	        	
	            
	        	model.addRow(new String[] { chargeIndex.getId(), chargeIndex.getDateDeReleve(), chargeIndex.getType(),
	        			chargeIndex.getValeurCompteur().toString(),chargeIndex.getCoutVariable().toString(),
	        			chargeIndex.getCoutFixe().toString()});
	            
	        }
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	public void remplirTableChargeFixe(List<ChargeFixe> listChargeFixe) {
		
	    UtilitaireTable.viderTable(tableChargeFixe);
	    DefaultTableModel model = (DefaultTableModel) tableChargeFixe.getModel();
	    model.setRowCount(0);
	    List<ChargeFixe> listeChargeFixe;
		try {
			if (listChargeFixe == null) {
				listeChargeFixe = dcf.findAll();
			}else {
				listeChargeFixe =listChargeFixe;
			}
			
			
	        for (ChargeFixe chargeFixe:listeChargeFixe) {
	        	
	            
	        	model.addRow(new String[] { chargeFixe.getId(), chargeFixe.getDateDeCharge(), chargeFixe.getType(),
	        			chargeFixe.getMontant().toString()});

	        }
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}
	
	public void remplirTableLoyer(List<Loyer> listLoyer) {
		
	    UtilitaireTable.viderTable(tableLoyer);
	    DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
	    model.setRowCount(0);
	    List<Loyer> listeLoyer;
		try {
			if (listLoyer == null) {
				listeLoyer = dl.findAll();
			}else {
				listeLoyer =listLoyer;
			}
	        for (Loyer loyer:listeLoyer) {
	        	model.addRow(new String[] { loyer.getIdBien(), loyer.getDateDeChangement(),	loyer.getMontantLoyer().toString()});
	        }
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}
	
	
	
	
	public void remplirTableLocataires() {
	    JTable tableLocataires = fenQuittanceLoyerPrincipal.getTableLocataires();
	    UtilitaireTable.viderTable(tableLocataires);
	    DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
	    model.setRowCount(0);
	    List<Locataire> listeLoc;
		try {
			listeLoc = daoLocataire.findAll();
	        for (Locataire loc:listeLoc) {
	        	
	            // Écrire une ligne dans le tableau
	        	model.addRow(new String[] { loc.getIdLocataire(), loc.getNom(), loc.getPrenom()});
	
	            // Calculer et publier la progression
	            
	        }
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
	    }
	
		
		public Locataire lireLigneTable(JTable tableLocatairesActuels) {
			Locataire loc = null;
			JTable tableLocataires = this.fenQuittanceLoyerPrincipal.getTableLocataires();
			int index = tableLocataires.getSelectedRow();
			 
			String idLoc = (String) tableLocataires.getValueAt(index, 0);
			try {
				loc = daoLocataire.findById(idLoc);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return loc;		 
			 
		}

		
		
		private void updateTable() {
			
			int dateAnnee = (int)fenQuittanceLoyerPrincipal.getYearSpinner().getValue();
			int dateMois = (int)fenQuittanceLoyerPrincipal.getMonthSpinner().getValue();
		    
		    String dateDoc = String.format("%02d-%04d", dateMois,dateAnnee);
		    String idBien;
			int ligneSelectLoc = tableLocataires.getSelectedRow();
			int ligneSelectBien = tableBiensActuels.getSelectedRow();
			String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
			if (fenQuittanceLoyerPrincipal.getEstActuel()) {
				idBien = (String) tableBiensActuels.getValueAt(ligneSelectBien, 0);
			}else {
				idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBien, 0);
			}
		    try {
				List<ChargeIndex> listeChargeIndex = dci.findByIdLocBienDocComptable(idBien,dateDoc,idLoc);
				remplirTableChargeIndex(listeChargeIndex);
				
				List<ChargeFixe> listeChargeFixe = dcf.findByIdLocBienDocComptable(idBien,dateDoc,idLoc);
				remplirTableChargeFixe(listeChargeFixe);
				
				List<Loyer> listeLoyer = dl.findByIdLocBienDocComptable(idBien,idLoc);
				remplirTableLoyer(listeLoyer);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		

		@Override
		public void stateChanged(ChangeEvent e) {
			updateTable();			
		}

		
	
		
		
		
	}
