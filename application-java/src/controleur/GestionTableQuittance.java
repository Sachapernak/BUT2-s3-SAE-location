	package controleur;
	
	
import java.io.IOException;
import java.sql.SQLException;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.List;
	
	
	import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.table.DefaultTableModel;
	
	import modele.Bail;
import modele.Contracter;
	import modele.Locataire;
	import modele.Loyer;
import modele.ProvisionCharge;
import modele.dao.DaoLocataire;
import modele.dao.DaoProvisionCharge;
	
	import vue.QuittanceLoyerPrincipal;
	
	
	public class GestionTableQuittance implements ListSelectionListener, ChangeListener {
		
		private QuittanceLoyerPrincipal fenQuittanceLoyerPrincipal;
		private DaoLocataire daoLocataire;
		private DaoProvisionCharge dpc;
		private JTable tableLocataires;
		private JTable tableBiensActuels;
		private JTable tableBiensAnciens;
		private JTable tableChargeIndex;
		
		public GestionTableQuittance(QuittanceLoyerPrincipal qlp)  {
			this.fenQuittanceLoyerPrincipal = qlp;
			this.tableLocataires = fenQuittanceLoyerPrincipal.getTableLocataires();
			this.tableBiensActuels = fenQuittanceLoyerPrincipal.getTableBiensActuels();
			this.tableBiensAnciens = fenQuittanceLoyerPrincipal.getTableBiensAnciens();
			this.tableChargeIndex = fenQuittanceLoyerPrincipal.getChargeTable();
			this.daoLocataire = new DaoLocataire();
			this.dpc = new DaoProvisionCharge();
			
			
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
	
	public void remplirTableCharge(List<ProvisionCharge> listProvisionCharge) {
	    UtilitaireTable.viderTable(tableChargeIndex);
	    DefaultTableModel model = (DefaultTableModel) tableChargeIndex.getModel();
	    model.setRowCount(0);
	    List<ProvisionCharge> listeProvisionCharge;
		try {
			if (listProvisionCharge == null) {
				listeProvisionCharge = dpc.findAll();
			}else {
				listeProvisionCharge =listProvisionCharge;
			}
	        for (ProvisionCharge provisionCharge:listeProvisionCharge) {
	        	model.addRow(new String[] { provisionCharge.getIdBail(), provisionCharge.getDateChangement(), provisionCharge.getProvisionPourCharge().toString()});
	        }
		} catch (SQLException | IOException e) {
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
		    
		    String dateDocCharge = String.format("%02d-%04d", dateMois,dateAnnee);
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
		    	List<ProvisionCharge> listeProvisionCharge = dpc.findByIdPPC(dateDocCharge,idLoc,idBien);
		    	remplirTableCharge(listeProvisionCharge);
		    	
				
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
