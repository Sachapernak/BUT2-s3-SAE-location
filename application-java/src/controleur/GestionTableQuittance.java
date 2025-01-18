	package controleur;
	
	
import java.io.IOException;

import java.sql.SQLException;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.table.DefaultTableModel;
	
	import modele.Bail;
import modele.Contracter;
import modele.DocumentComptable;
import modele.Locataire;
	import modele.Loyer;
import modele.ProvisionCharge;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoLocataire;
import modele.dao.DaoProvisionCharge;
	
	import vue.QuittanceLoyerPrincipal;
	
	
	public class GestionTableQuittance implements ListSelectionListener, ChangeListener {
		
		private QuittanceLoyerPrincipal fenQuittanceLoyerPrincipal;
		private DaoLocataire daoLocataire;
		private DaoProvisionCharge dpc;
		private DaoDocumentComptable ddc;
		private JTable tableLocataires;
		private JTable tableBiensActuels;
		private JTable tableBiensAnciens;
		private JTable tableCharge;
		private JTable tableDocLoyer;
		
		public GestionTableQuittance(QuittanceLoyerPrincipal qlp)  {
			this.fenQuittanceLoyerPrincipal = qlp;
			this.tableLocataires = fenQuittanceLoyerPrincipal.getTableLocataires();
			this.tableBiensActuels = fenQuittanceLoyerPrincipal.getTableBiensActuels();
			this.tableBiensAnciens = fenQuittanceLoyerPrincipal.getTableBiensAnciens();
			this.tableCharge = fenQuittanceLoyerPrincipal.getChargeTable();
			this.tableDocLoyer =  fenQuittanceLoyerPrincipal.getDocComptLoyerTable();
			this.daoLocataire = new DaoLocataire();
			this.dpc = new DaoProvisionCharge();
			this.ddc = new DaoDocumentComptable();
			
		}
	
		@Override
		public void valueChanged(ListSelectionEvent e) {
		    // Récupérer le modèle de sélection source
		    ListSelectionModel selectionModel = (ListSelectionModel) e.getSource();

		    // Vérifiez si l'événement provient de la table des locataires
		    JTable tableLoc = this.fenQuittanceLoyerPrincipal.getTableLocataires();
		    JTable tableCharge = this.fenQuittanceLoyerPrincipal.getChargeTable(); // Si tableCharge est disponible

		    if (tableLoc.getSelectionModel() == selectionModel) {
		        // Action pour la table des locataires
		        Locataire locSelect = lireLigneTable(tableLoc);
		        remplirTableLocation(
		            this.fenQuittanceLoyerPrincipal.getTableBiensActuels(),
		            this.fenQuittanceLoyerPrincipal.getTableBiensAnciens(),
		            locSelect
		        );
		    }
		    if (tableCharge.getSelectionModel() == selectionModel) {
		        // Action pour la table des charges
		        remplirTableChargeLoyer(tableDocLoyer);
		    }
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
		
		public void remplirTableChargeLoyer(JTable tableDocComptLoyer) {
			DefaultTableModel modelDocLoyer = (DefaultTableModel) tableDocComptLoyer.getModel();
			UtilitaireTable.viderTable(tableDocComptLoyer);
		    String idBien;
			int ligneSelectLoc = tableLocataires.getSelectedRow();
			int ligneSelectBienActuel = tableBiensActuels.getSelectedRow();
			int ligneSelectBienAncien = tableBiensAnciens.getSelectedRow();
			String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
			if (fenQuittanceLoyerPrincipal.getEstActuel()) {
				idBien = (String) tableBiensActuels.getValueAt(ligneSelectBienActuel, 0);
			}else {
				idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBienAncien, 0);
			}
			if (tableCharge.getSelectedRow() != -1) {
				int ligneSelectPPC = tableCharge.getSelectedRow();
				String dateCharge = (String) tableCharge.getValueAt(ligneSelectPPC, 1);
				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
		        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		        String formattedDate = LocalDate.parse(dateCharge, inputFormatter).format(outputFormatter);
				try {
			    	List<DocumentComptable> dcLoyer = ddc.findByIdtypeloyer(idLoc,idBien,formattedDate );
			    	for (DocumentComptable docLoyer : dcLoyer) {
			    		modelDocLoyer.addRow(new Object[] {docLoyer.getNumeroDoc(),formattedDate,docLoyer.getMontant()});
			    	}
			    	
					
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
	
	public void remplirTableCharge(List<ProvisionCharge> listProvisionCharge, String idLoc, String idLog) {
		if (tableCharge.getRowCount() != 0) {
			UtilitaireTable.viderTable(tableCharge);
		}		
	    DefaultTableModel model = (DefaultTableModel) tableCharge.getModel();
	    model.setRowCount(0);
	    List<ProvisionCharge> listeProvisionCharge;
		try {
			if (listProvisionCharge == null) {
				listeProvisionCharge = dpc.findByIdLocLog(idLoc,idLog);
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
			
			int dateAnnee1 = (int)fenQuittanceLoyerPrincipal.getYearSpinner().getValue();
			int dateMois1 = (int)fenQuittanceLoyerPrincipal.getMonthSpinner().getValue();
			int dateDay1 = (int)fenQuittanceLoyerPrincipal.getDaySpinner1().getValue();
			
			int dateAnnee2 = (int)fenQuittanceLoyerPrincipal.getYearSpinner2().getValue();
			int dateMois2 = (int)fenQuittanceLoyerPrincipal.getMonthSpinner2().getValue();
			int dateDay2 = (int)fenQuittanceLoyerPrincipal.getDaySpinner2().getValue();
		    
			String dateDocCharge1 = String.format("%02d-%02d-%04d", dateDay1,dateMois1,dateAnnee1);
			String dateDocCharge2 = String.format("%02d-%02d-%04d", dateDay2,dateMois2,dateAnnee2);
			
		    String idBien;
			int ligneSelectLoc = tableLocataires.getSelectedRow();
			int ligneSelectBienActuel = tableBiensActuels.getSelectedRow();
			int ligneSelectBienAncien = tableBiensAnciens.getSelectedRow();
			String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
			if (fenQuittanceLoyerPrincipal.getEstActuel()) {
				idBien = (String) tableBiensActuels.getValueAt(ligneSelectBienActuel, 0);
			}else {
				idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBienAncien, 0);
			}
		    try {
		    	List<ProvisionCharge> listeProvisionCharge = dpc.findByIdPPC(idLoc,idBien,dateDocCharge1,dateDocCharge2);
		    	remplirTableCharge(listeProvisionCharge,null,null);
		    	
				
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
