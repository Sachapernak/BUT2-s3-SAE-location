package controleur;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modele.ConnexionBD;
import vue.AfficherCharges;
import vue.AjouterBien;
import vue.FenetrePrincipale;
import vue.RevalorisationLoyer;
import vue.PageConnexion;

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
			case "Ajouter un bien":
                if (tableBatiment.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(fenPrincipale, "Veuillez sélectionner une ligne du tableau avant d'ajouter un bien.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
                } else {
                	
                    AjouterBien ab1 = new AjouterBien(this.fenPrincipale.getTableBatiment(), gtfp);
                    JLayeredPane layeredPaneAjouterBien = this.fenPrincipale.getLayeredPane();
                    layeredPaneAjouterBien.add(ab1, JLayeredPane.PALETTE_LAYER);
                    ab1.setVisible(true);
                }
                break;
		}
		
	}
	
	public void afficherEtaConnexion() {
		try {
			if (!ConnexionBD.getInstance().isConnexionOk()) {
			    this.fenPrincipale.setEtatConnexion(new Color(0, 128, 0));			    
			}else {
				this.fenPrincipale.setEtatConnexion(Color.red);
				PageConnexion pageConnexion = new PageConnexion();
				JLayeredPane layeredPaneConnexion= this.fenPrincipale.getLayeredPane();
				layeredPaneConnexion.add(pageConnexion, JLayeredPane.PALETTE_LAYER);
				pageConnexion.setVisible(true);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
