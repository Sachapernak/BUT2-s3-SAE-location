package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modele.Batiment;
import modele.BienLocatif;
import modele.Loyer;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import vue.FenetrePrincipale;

public class GestionTablesFenetrePrincipale implements ListSelectionListener{

	private FenetrePrincipale fenPrincipale;
	private DaoBatiment daoBatiment;
	private DaoBienLocatif daoBienLocatif;
	
	public GestionTablesFenetrePrincipale(FenetrePrincipale fp) {
		this.fenPrincipale = fp;
		this.daoBatiment = new DaoBatiment();
		this.daoBienLocatif = new DaoBienLocatif();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JTable tableBatiments = this.fenPrincipale.getTableBatiment();
		JTable tableBiensLoc = this.fenPrincipale.getTableBiensLoc();
		
		int ligneSelect = tableBatiments.getSelectedRow();
		if (ligneSelect > -1) {
			String idBat = (String) tableBatiments.getValueAt(ligneSelect, 0);
			
			remplirBiensLoc(tableBiensLoc,idBat);
			
		}
		
	}
	
    
    public void remplirBatiments (JTable tableBatiments) {
    	UtilitaireTable.viderTable(tableBatiments);
    	int nbLogements = 0;
    		try {
				List<Batiment> batiments = daoBatiment.findAll();
				DefaultTableModel model = (DefaultTableModel) tableBatiments.getModel();
				model.setRowCount(0);
		        for (Batiment batiment : batiments) {
		        	nbLogements = daoBienLocatif.countBiens(batiment.getIdBat());
		            model.addRow(new Object[] { batiment.getIdBat(), batiment.getAdresse().getAdressePostale(),nbLogements});
		        }
			} catch (SQLException | IOException e) {
				fenPrincipale.afficherMessageErreur(e.getMessage());
				e.printStackTrace();
			} 
    }
    
    public void remplirBiensLoc(JTable tableBiensLoc, String idBatiment) {
    	UtilitaireTable.viderTable(tableBiensLoc);
    	try {
			List<BienLocatif> biens = daoBienLocatif.findByIdBat(idBatiment);
			DefaultTableModel model = (DefaultTableModel) tableBiensLoc.getModel();
			model.setRowCount(0);
	        for (BienLocatif bien : biens) {
	        	List<Loyer> loyers = bien.getLoyers();
	        	
	        	String dernierLoyer;
	        	
	        	if (!loyers.isEmpty()){
		        	dernierLoyer = loyers.get(loyers.size()-1).getMontantLoyer().toString();
		            
	        	} else {
	        		dernierLoyer = bien.getLoyerBase().toString();
	        	}
	        	
	        	model.addRow(new Object[] { bien.getIdentifiantLogement(),bien.getNbPiece(), bien.getSurface(), bien.getType(), 
	            		bien.getComplementAdresse(), bien.getLoyerBase(), dernierLoyer});
	        	

	        }
						
		} catch (SQLException | IOException e) {
			fenPrincipale.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		} 
    }

}
