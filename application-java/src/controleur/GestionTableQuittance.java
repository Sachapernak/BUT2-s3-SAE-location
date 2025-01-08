	package controleur;
	
	import java.io.IOException;
	
	
	
	import java.sql.SQLException;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.Date;
	import java.util.List;
	
	
	import javax.swing.JOptionPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	
	import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.table.DefaultTableModel;
	
	import modele.Adresse;
	import modele.Bail;
	import modele.Contracter;
	import modele.Locataire;
	import modele.Loyer;
	
	import modele.dao.DaoLocataire;
	
	import vue.AfficherLocatairesActuels;
	
	import vue.QuittanceLoyerPrincipal;
	
	
	public class GestionTableQuittance implements ListSelectionListener {
		
		private QuittanceLoyerPrincipal fenQuittanceLoyerPrincipal;
		private DaoLocataire daoLocataire;
		
		
		
		public GestionTableQuittance(QuittanceLoyerPrincipal qlp)  {
			this.fenQuittanceLoyerPrincipal = qlp;
			this.daoLocataire = new DaoLocataire();
			
			
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
	
	
	
	public void ecrireLigneTable(int numeroLigne, Locataire locataire) {
		JTable tableLocataires = this.fenQuittanceLoyerPrincipal.getTableLocataires();
		DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
		
	    
	    model.addRow(new String[] { locataire.getIdLocataire(), locataire.getNom(), locataire.getPrenom()});
	    
	}
	
	public void remplirTableLocataires() {
	    JTable tableLocataires = fenQuittanceLoyerPrincipal.getTableLocataires();
	    UtilitaireTable.viderTable(tableLocataires);
	    DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
	    model.setRowCount(0);
	    List<Locataire> listeLoc;
		try {
			listeLoc = daoLocataire.findAll();
			int nombre = 0;
	        for (Locataire loc:listeLoc) {
	        	
	            // Écrire une ligne dans le tableau
	            ecrireLigneTable(nombre, loc);
	            nombre++;
	
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
	
		
		
		
	}
