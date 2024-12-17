package controleur;

import java.io.IOException;
import java.math.BigDecimal;
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

	private FenetrePrincipale fen_principale;
	private DaoBatiment daoBatiment;
	private DaoBienLocatif daoBienLocatif;
	
	public GestionTablesFenetrePrincipale(FenetrePrincipale fp) {
		this.fen_principale = fp;
		this.daoBatiment = new DaoBatiment();
		this.daoBienLocatif = new DaoBienLocatif();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JTable tableBatiments = this.fen_principale.getTableBatiment();
		JTable tableBiensLoc = this.fen_principale.getTableBiensLoc();
		
		int ligneSelect = tableBatiments.getSelectedRow();
		if (ligneSelect > -1) {
			String idBat = (String) tableBatiments.getValueAt(ligneSelect, 0);
			remplirBiensLoc(tableBiensLoc,idBat);
		}
		
	}
	
	public static void viderTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                model.setValueAt(null, row, col); 
            }
        }
    }
    
    public void remplirBatiments (JTable tableBatiments) {
    	viderTable(tableBatiments);
    	int nbLogements = 0;
    		try {
				List<Batiment> batiments = daoBatiment.findAll();
				DefaultTableModel model = (DefaultTableModel) tableBatiments.getModel();
				model.setRowCount(0);
		        for (Batiment batiment : batiments) {
		        	nbLogements = daoBienLocatif.countBiens(batiment.getIdBat());
		            model.addRow(new Object[] { batiment.getIdBat(), batiment.getAdresse().getAdressePostale(),nbLogements});
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    
    public void remplirBiensLoc (JTable tableBiensLoc, String idBatiment) {
    	viderTable(tableBiensLoc);
    	try {
			List<BienLocatif> biens = daoBienLocatif.findByIdBatiment(idBatiment);
			DefaultTableModel model = (DefaultTableModel) tableBiensLoc.getModel();
			model.setRowCount(0);
	        for (BienLocatif bien : biens) {
	        	List<Loyer> loyers = bien.getLoyers();
	        	BigDecimal dernierLoyer = loyers.get(loyers.size()-1).getMontantLoyer();
	            model.addRow(new Object[] { bien.getIdentifiantLogement(),bien.getNbPiece(), bien.getSurface(), bien.getType(), 
	            		bien.getComplementAdresse(), bien.getLoyerBase(), dernierLoyer});
	        }
						
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}
