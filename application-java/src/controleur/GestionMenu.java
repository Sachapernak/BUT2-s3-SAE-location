package controleur;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;

import vue.AfficherAnciensLocataires;
import vue.AfficherLocatairesActuels;
import vue.AjouterEntreprise;
import vue.ArchiverDocuments;
import vue.FenetrePrincipale;
import vue.PageConnexion;
import vue.ReglesMetier;

public class GestionMenu implements ActionListener{

	private FenetrePrincipale fenPrincipale;
	
	public GestionMenu(FenetrePrincipale fp) {
		this.fenPrincipale = fp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem itemActif = (JMenuItem) e.getSource();
        String itemLibelle = itemActif.getText();
		
		switch (itemLibelle) {
			case "Quitter" :
				this.fenPrincipale.dispose();
				break;
			case "Liste des anciens locataires" :
				AfficherAnciensLocataires afl = new AfficherAnciensLocataires();
				JLayeredPane layeredPaneAfficherAnciensLoc = this.fenPrincipale.getLayeredPane();
				layeredPaneAfficherAnciensLoc.add(afl, JLayeredPane.PALETTE_LAYER);
				afl.setVisible(true);
				break;
			case "Liste des locataires actuels" :
				AfficherLocatairesActuels al = new AfficherLocatairesActuels();
				JLayeredPane layeredPaneAfficherLocActuels = this.fenPrincipale.getLayeredPane();
				layeredPaneAfficherLocActuels.add(al, JLayeredPane.PALETTE_LAYER);
				al.setVisible(true);
				break;
			case "Configurer la connexion" :
				JInternalFrame jInternalFrame = new PageConnexion();
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
				break;
			case "Afficher les règles métier" : 
				ReglesMetier rm = new ReglesMetier() ;
				JLayeredPane layeredPaneReglesMetiers = this.fenPrincipale.getLayeredPane();
				layeredPaneReglesMetiers.add(rm, JLayeredPane.PALETTE_LAYER);
				rm.setVisible(true);
				break;
			case "Consulter les documents" : 
				ArchiverDocuments ad = new ArchiverDocuments();
				JLayeredPane layeredPaneArchiverDoc = this.fenPrincipale.getLayeredPane();
				layeredPaneArchiverDoc.add(ad, JLayeredPane.PALETTE_LAYER);
				ad.setVisible(true);
				break;
			case "Ajouter une entreprise": 
				AjouterEntreprise ae = new AjouterEntreprise();
				JLayeredPane layeredPaneAjouterEntreprise = this.fenPrincipale.getLayeredPane();
				layeredPaneAjouterEntreprise.add(ae, JLayeredPane.PALETTE_LAYER);
				ae.setVisible(true);
				break; 
			default: 
				break;
				
		}
		
	}

}
