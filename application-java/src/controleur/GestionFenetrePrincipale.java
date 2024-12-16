package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vue.AfficherCharges;
import vue.FenetrePrincipale;
import vue.RevalorisationLoyer;

public class GestionFenetrePrincipale implements ActionListener{

	private FenetrePrincipale fen_principale;
	
	public GestionFenetrePrincipale(FenetrePrincipale fp) {
		this.fen_principale = fp;
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




}
