package controleur;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;

import modele.ConnexionBD;
import utilitaires.ConfigConnexion;
import vue.AfficherCharges;
import vue.FenetrePrincipale;
import vue.RevalorisationLoyer;
import vue.PageConnexion;

public class GestionFenetrePrincipale implements ActionListener{

	private FenetrePrincipale fenPrincipale;
	
	public GestionFenetrePrincipale(FenetrePrincipale fp) {
		this.fenPrincipale = fp;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Augmenter le loyer" : 
				RevalorisationLoyer rl = new RevalorisationLoyer();
				JLayeredPane layeredPaneRevalorisationLoyer= this.fenPrincipale.getLayeredPane();
				layeredPaneRevalorisationLoyer.add(rl, JLayeredPane.PALETTE_LAYER);
				rl.setVisible(true);
				break;
			case "Afficher les charges" : 
				AfficherCharges a1 = new AfficherCharges();
				JLayeredPane layeredPaneAfficherCharges= this.fenPrincipale.getLayeredPane();
				layeredPaneAfficherCharges.add(a1, JLayeredPane.PALETTE_LAYER);
				a1.setVisible(true);
				break;
			case "Charger" : 
				GestionTablesFenetrePrincipale gestionTables = new GestionTablesFenetrePrincipale(this.fenPrincipale);
				gestionTables.remplirBatiments(this.fenPrincipale.getTableBatiment());
		}
		
	}
	
	public void afficherPageConnexion() {
		JInternalFrame jInternalFrame = new PageConnexion();
		try {
			if (!ConnexionBD.getInstance().isConnexionOk()) {	    
				jInternalFrame.setNormalBounds(new Rectangle(100, 100, 400, 400));
				jInternalFrame.setSize(new Dimension(400, 400));
				jInternalFrame.getContentPane().setSize(new Dimension(400, 400));
				jInternalFrame.getContentPane().setPreferredSize(new Dimension(400, 400));
				jInternalFrame.setPreferredSize(new Dimension(400, 400));
				jInternalFrame.setMinimumSize(new Dimension(300, 300));
		        jInternalFrame.setLocation(100, 100);
		        jInternalFrame.setBounds(100,100,500, 350);
		        jInternalFrame.setSize(450, 350);
		        jInternalFrame.setVisible(true);
		        jInternalFrame.setClosable(false);
				JLayeredPane layeredPaneConnexion= this.fenPrincipale.getLayeredPane();
				layeredPaneConnexion.add(jInternalFrame, JLayeredPane.PALETTE_LAYER);
				jInternalFrame.setVisible(true);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
