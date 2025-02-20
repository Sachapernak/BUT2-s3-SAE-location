package controleur;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;

import vue.AfficherAnciensLocataires;

import vue.AfficherBaux;
import vue.AfficherAssurances;
import vue.AfficherCharges;
import vue.AfficherEntreprises;
import vue.AfficherLocatairesActuels;
import vue.AfficherLoyers;
import vue.ChargerLoyers;
import vue.DeclarationFiscale;
import vue.FenetrePrincipale;
import vue.PageConnexion;
import vue.RevalorisationLoyer;
import vue.SelectionQuittance;
import vue.SelectionRegularisationCharges;
import vue.SetICC;
import vue.SelectionSoldeToutCompte;

public class GestionMenu implements ActionListener{

	private FenetrePrincipale fenPrincipale;
	
	public GestionMenu(FenetrePrincipale fp) {
		this.fenPrincipale = fp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem itemActif = (JMenuItem) e.getSource();
        String itemLibelle = itemActif.getText();
        
		JLayeredPane fenLayerPane = this.fenPrincipale.getLayeredPane();
		
		switch (itemLibelle) {
		
			case "Quitter" :
				this.fenPrincipale.dispose();
				break;
			case "Liste des anciens locataires" :
				AfficherAnciensLocataires afl = new AfficherAnciensLocataires();
				fenLayerPane.add(afl, JLayeredPane.PALETTE_LAYER);
				afl.setVisible(true);
				break;
			case "Liste des locataires actuels" :
				AfficherLocatairesActuels al = new AfficherLocatairesActuels();
				fenLayerPane.add(al, JLayeredPane.PALETTE_LAYER);
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
		        fenLayerPane.add(jInternalFrame, JLayeredPane.PALETTE_LAYER);
				jInternalFrame.setVisible(true);
				break;
			case "Consulter les charges" : 
				AfficherCharges ad = new AfficherCharges();
				ad.setVisible(true);
				break;
			case "Consulter les loyers" : 
				AfficherLoyers afloyers = new AfficherLoyers();
				fenLayerPane.add(afloyers, JLayeredPane.PALETTE_LAYER);
				afloyers.setVisible(true);
				break;	
			case "Charger les loyers" : 
				ChargerLoyers chLoyer = new ChargerLoyers();
				chLoyer.setVisible(true);
				break;
			case "Quittances de loyer" :
				SelectionQuittance slQ = new SelectionQuittance();
				fenLayerPane.add(slQ, JLayeredPane.PALETTE_LAYER);
				slQ.setVisible(true);
				break;
			case "Solde de tout comptes":
				SelectionSoldeToutCompte sdTc = new SelectionSoldeToutCompte();
				fenLayerPane.add(sdTc, JLayeredPane.PALETTE_LAYER);
				sdTc.setVisible(true);
				break;
			case "Afficher la déclaration fiscale" : 
				DeclarationFiscale df = new DeclarationFiscale();
				JLayeredPane layeredPaneDecla = this.fenPrincipale.getLayeredPane();
				layeredPaneDecla.add(df, JLayeredPane.PALETTE_LAYER);
				df.setVisible(true);
				break;
			case "Consulter ICC" : 
				SetICC stIcc= new SetICC();
				fenLayerPane.add(stIcc, JLayeredPane.PALETTE_LAYER);
				stIcc.setVisible(true);
				break;
			case "Afficher les entreprises":
				AfficherEntreprises ae = new AfficherEntreprises();
				fenLayerPane.add(ae, JLayeredPane.PALETTE_LAYER);
				ae.setVisible(true);
				break;
			case "Liste des baux":
				AfficherBaux afBaux = new AfficherBaux();
				fenLayerPane.add(afBaux, JLayeredPane.PALETTE_LAYER);
				afBaux.setVisible(true);
				break;
			case "Afficher les assurances" : 
				AfficherAssurances aa = new AfficherAssurances();
				fenLayerPane.add(aa, JLayeredPane.PALETTE_LAYER);
				aa.setVisible(true);
				break;
			case "Augmenter les loyers" : 
				RevalorisationLoyer reval = new RevalorisationLoyer();
				fenLayerPane.add(reval, JLayeredPane.PALETTE_LAYER);
				reval.setVisible(true);
				break;
			case "Régulariser les charges" : 
				SelectionRegularisationCharges regul = new SelectionRegularisationCharges(this.fenPrincipale);
				fenLayerPane.add(regul, JLayeredPane.PALETTE_LAYER);
				regul.setVisible(true);
				break;
			default:
				break;
				
		}
		
	}

}
