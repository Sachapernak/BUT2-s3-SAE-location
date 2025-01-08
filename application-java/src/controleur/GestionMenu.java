package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;

import vue.AfficherAnciensLocataires;
import vue.AfficherLocatairesActuels;
import vue.ArchiverDocuments;
import vue.ConfigurationConnexion;
import vue.FenetrePrincipale;
import vue.QuittanceLoyerPrincipal;
import vue.ReglesMetier;

public class GestionMenu implements ActionListener{

	private FenetrePrincipale fen_principale;
	
	public GestionMenu(FenetrePrincipale fp) {
		this.fen_principale = fp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem itemActif = (JMenuItem) e.getSource();
        String itemLibelle = itemActif.getText();
		
		switch (itemLibelle) {
			case "Quitter" :
				this.fen_principale.dispose();
				break;
			case "Liste des anciens locataires" :
				AfficherAnciensLocataires afl = new AfficherAnciensLocataires();
				JLayeredPane layeredPaneAfficherAnciensLoc = this.fen_principale.getLayeredPane();
				layeredPaneAfficherAnciensLoc.add(afl, JLayeredPane.PALETTE_LAYER);
				afl.setVisible(true);
				break;
			case "Liste des locataires actuels" :
				AfficherLocatairesActuels al = new AfficherLocatairesActuels();
				JLayeredPane layeredPaneAfficherLocActuels = this.fen_principale.getLayeredPane();
				layeredPaneAfficherLocActuels.add(al, JLayeredPane.PALETTE_LAYER);
				al.setVisible(true);
				break;
			case "Quittance de loyer des locataires" :
				QuittanceLoyerPrincipal QLP = new QuittanceLoyerPrincipal();
				JLayeredPane layeredPaneAfficherQuittance = this.fen_principale.getLayeredPane();
				layeredPaneAfficherQuittance.add(QLP, JLayeredPane.PALETTE_LAYER);
				QLP.setVisible(true);
				break;
			case "Configurer la connexion" :
				ConfigurationConnexion cc = new ConfigurationConnexion() ;
				JLayeredPane layeredPaneConfigConnexion = this.fen_principale.getLayeredPane();
				layeredPaneConfigConnexion.add(cc, JLayeredPane.PALETTE_LAYER);
				cc.setVisible(true);
				break;
			case "Afficher les règles métier" : 
				ReglesMetier rm = new ReglesMetier() ;
				JLayeredPane layeredPaneReglesMetiers = this.fen_principale.getLayeredPane();
				layeredPaneReglesMetiers.add(rm, JLayeredPane.PALETTE_LAYER);
				rm.setVisible(true);
				break;
			case "Consulter les documents" : 
				ArchiverDocuments ad = new ArchiverDocuments();
				JLayeredPane layeredPaneArchiverDoc = this.fen_principale.getLayeredPane();
				layeredPaneArchiverDoc.add(ad, JLayeredPane.PALETTE_LAYER);
				ad.setVisible(true);
				
		}
		
	}

}
