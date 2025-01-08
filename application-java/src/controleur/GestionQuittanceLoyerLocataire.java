package controleur;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import modele.BienLocatif;
import modele.Locataire;
import vue.AfficherQuittance;
import vue.QuittanceLoyerPrincipal;

public class GestionQuittanceLoyerLocataire  implements ActionListener{
	
	
	private QuittanceLoyerPrincipal fenQuittanceLoyer;
	
	private JTable tableLocataires;
	private JTable tableBiensActuels;
	private JTable tableBiensAnciens;
	
	public GestionQuittanceLoyerLocataire(QuittanceLoyerPrincipal fenQuittanceLoyer) {
		this.fenQuittanceLoyer = fenQuittanceLoyer;
		this.tableLocataires = fenQuittanceLoyer.getTableLocataires();
		this.tableBiensActuels = fenQuittanceLoyer.getTableBiensActuels();
		this.tableBiensAnciens = fenQuittanceLoyer.getTableBiensAnciens();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btnActif = (JButton) e.getSource();
			String btnLibelle = btnActif.getText();
			
			
			switch (btnLibelle) {
			case "Voir Quittance de Loyer" : 
				
				if (tableLocataires.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner un locataire.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
	            if (fenQuittanceLoyer.getEstActuel() && tableBiensActuels.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner un bien actuel.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
	            if (!fenQuittanceLoyer.getEstActuel() && tableBiensAnciens.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner un bien ancien.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
				String idBien;
				int ligneSelectLoc = tableLocataires.getSelectedRow();
				int ligneSelectBien = tableBiensActuels.getSelectedRow();
				String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
				if (fenQuittanceLoyer.getEstActuel()) {
					idBien = (String) tableBiensActuels.getValueAt(ligneSelectBien, 0);
				}else {
					idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBien, 0);
				}
				AfficherQuittance aq = new AfficherQuittance( idBien,idLoc);
				JLayeredPane layeredPaneAfficherQuittance= this.fenQuittanceLoyer.getLayeredPane();
				layeredPaneAfficherQuittance.add(aq, JLayeredPane.PALETTE_LAYER);
				aq.setVisible(true);
				break;
			case "Retour" : 
				fenQuittanceLoyer.dispose();
				break;
			
			}
		} else {
			JRadioButton btnActifRadio = (JRadioButton) e.getSource();
			String btnRadioLibelle = btnActifRadio.getText();
			
			switch (btnRadioLibelle) {
			case "Biens Actuels" : 
				
		        CardLayout cl = (CardLayout) (fenQuittanceLoyer.getCardPanel().getLayout());
		        cl.show(fenQuittanceLoyer.getCardPanel(), "Biens Actuels");
			    if (tableBiensAnciens !=null) {
			    	tableBiensAnciens.clearSelection();
			    }
			    fenQuittanceLoyer.setEstActuel(true);
				break;
			case "Biens Anciens" : 
				CardLayout cls = (CardLayout) (fenQuittanceLoyer.getCardPanel().getLayout());
		        cls.show(fenQuittanceLoyer.getCardPanel(), "Biens Anciens");
		        if (tableBiensActuels !=null) {
		        	tableBiensActuels.clearSelection();
			    }
		        fenQuittanceLoyer.setEstActuel(false);
				
				break;
			
			}
		}
		
		
		
	}
	

}
