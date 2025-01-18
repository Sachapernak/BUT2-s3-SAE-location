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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modele.ConnexionBD;
import vue.AfficherCharges;
import vue.AjouterBien;
import vue.FenetrePrincipale;
import vue.AjouterBatiment;
import vue.RevalorisationLoyer;
import vue.PageConnexion;
import vue.RevalorisationLoyer;

public class GestionFenetrePrincipale implements ActionListener{

	private FenetrePrincipale fenPrincipale;
	private JTable tableBatiment;
    private JButton btnAjouterBien;
    private GestionTablesFenetrePrincipale gtfp;
	
	public GestionFenetrePrincipale(FenetrePrincipale fp, JButton btnAjouterBien, GestionTablesFenetrePrincipale gtfp) {
		this.fenPrincipale = fp;
		this.tableBatiment = fp.getTableBatiment();
        this.btnAjouterBien = btnAjouterBien;
        this.gtfp = gtfp;
        
     
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JLayeredPane layeredPane= this.fenPrincipale.getLayeredPane();
		JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Augmenter le loyer" : 
				RevalorisationLoyer rl = new RevalorisationLoyer(fenPrincipale.getValeurIdTableLogement());
				layeredPane.add(rl, JLayeredPane.PALETTE_LAYER);
				rl.setVisible(true);
				break;
			case "Charger" : 
				GestionTablesFenetrePrincipale gestionTables = new GestionTablesFenetrePrincipale(this.fenPrincipale);
				gestionTables.remplirBatiments(this.fenPrincipale.getTableBatiment());
				break;
			case "Consulter charges" :
				AfficherCharges a1 = new AfficherCharges(fenPrincipale.getValeurIdTableBat(), fenPrincipale.getValeurIdTableLogement() );
				a1.setVisible(true);
				break;

			case "Ajouter un bien":
                    JOptionPane.showMessageDialog(fenPrincipale, "Veuillez sélectionner une ligne du tableau avant d'ajouter un bien.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
                } else {
                	
                    AjouterBien ab1 = new AjouterBien(this.fenPrincipale.getTableBatiment(), gtfp);
                    JLayeredPane layeredPaneAjouterBien = this.fenPrincipale.getLayeredPane();
                    layeredPaneAjouterBien.add(ab1, JLayeredPane.PALETTE_LAYER);
                    ab1.setVisible(true);
                }
                break;

			case "Ajouter un batiment" : 
				AjouterBatiment ab = new AjouterBatiment();
				ab.setVisible(true);
				break;
			default:
				break;
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
