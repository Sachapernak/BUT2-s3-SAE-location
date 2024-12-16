package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.Batiment;
import modele.BienLocatif;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import vue.AfficherCharges;
import vue.FenetrePrincipale;
import vue.RevalorisationLoyer;

public class GestionFenetrePrincipale implements ActionListener{

	private FenetrePrincipale fen_principale;
	private DaoBatiment daoBatiment;
	private DaoBienLocatif daoBienLocatif;
	
	public GestionFenetrePrincipale(FenetrePrincipale fp) {
		this.fen_principale = fp;
		this.daoBatiment = new DaoBatiment();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Augmenter le loyer" : 
				RevalorisationLoyer rl = new RevalorisationLoyer();
				JLayeredPane layeredPaneRevalorisationLoyer= this.fen_principale.getLayeredPane();
				layeredPaneRevalorisationLoyer.add(rl, JLayeredPane.PALETTE_LAYER);
				rl.setVisible(true);
				break;
			case "Afficher les charges" : 
				AfficherCharges a1 = new AfficherCharges();
				JLayeredPane layeredPaneAfficherCharges= this.fen_principale.getLayeredPane();
				layeredPaneAfficherCharges.add(a1, JLayeredPane.PALETTE_LAYER);
				a1.setVisible(true);
				break;
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
    	int nbLogements = 0;
    		try {
				List<Batiment> batiments = daoBatiment.findAll();
				DefaultTableModel model = (DefaultTableModel) tableBatiments.getModel();
				model.setRowCount(0);
		        for (Batiment batiment : batiments) {
		        	nbLogements = daoBatiment.countBiens(batiment);
		            model.addRow(new Object[] { batiment.getIdBat(), batiment.getAdresse().getAdressePostale(),nbLogements});
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    
    public void remplirBiensLoc (JTable tableBiensLoc) {
    	try {
			List<BienLocatif> biens = daoBienLocatif.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }




}
