package controleur;


import java.awt.CardLayout;



import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import modele.BienLocatif;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.Locataire;
import modele.TypeDoc;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoFactureBien;
import modele.dao.DaoLocataire;
import vue.GenereQuittance;
import vue.QuittanceLoyerPrincipal;

public class GestionQuittanceLoyerLocataire  implements ActionListener{
	
	
	private QuittanceLoyerPrincipal fenQuittanceLoyer;
	
	private JTable tableLocataires;
	private JTable tableBiensActuels;
	private JTable tableBiensAnciens;
	private JTable tableCharge;
	private JTable tableDocLoyer;
	private DaoLocataire dl;
	private DaoBienLocatif dbl;
	private DaoFactureBien dfb; 
	private DaoDocumentComptable ddc;
	
	public GestionQuittanceLoyerLocataire(QuittanceLoyerPrincipal fenQuittanceLoyer) {
		this.fenQuittanceLoyer = fenQuittanceLoyer;
		this.tableLocataires = fenQuittanceLoyer.getTableLocataires();
		this.tableBiensActuels = fenQuittanceLoyer.getTableBiensActuels();
		this.tableBiensAnciens = fenQuittanceLoyer.getTableBiensAnciens();
		this.tableCharge = fenQuittanceLoyer.getChargeTable();
		this.tableDocLoyer =  fenQuittanceLoyer.getDocComptLoyerTable();
		this.dl = new DaoLocataire();
		this.dbl = new DaoBienLocatif();
		this.dfb = new DaoFactureBien();
		this.ddc = new DaoDocumentComptable();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btnActif = (JButton) e.getSource();
			String btnLibelle = btnActif.getText();
			
			
			switch (btnLibelle) {
			case "Voir Quittance de Loyer" : 
	            if (tableCharge.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner une charge.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
	            if (tableDocLoyer.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner un document comptable.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
				String idBien;
				int ligneSelectLoc = tableLocataires.getSelectedRow();
				int ligneSelectBienActuel = tableBiensActuels.getSelectedRow();
				int ligneSelectBienAncien = tableBiensAnciens.getSelectedRow();
				int ligneSelectDocLoyer = tableDocLoyer.getSelectedRow();
				
				String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
				
				if (fenQuittanceLoyer.getEstActuel()) {
					idBien = (String) tableBiensActuels.getValueAt(ligneSelectBienActuel, 0);
				}else {
					idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBienAncien, 0);
				}
				String numDoc = fenQuittanceLoyer.getNumeroDoc();
				if (numDoc == null || numDoc.trim().isEmpty()) {
				    JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez écrire un numéro de document.", "Avertissement", JOptionPane.WARNING_MESSAGE);
				    break;
				}
				
				
		        
		        String dateDocQuittance = (String) tableDocLoyer.getValueAt(ligneSelectDocLoyer, 1);
		        BigDecimal montant = new BigDecimal(tableDocLoyer.getValueAt(ligneSelectDocLoyer, 2).toString());
		        // Formater la date
				String fichierDoc = "Quittance de Loyer "+numDoc+".word";
				
				try {
					Locataire locataire = dl.findById(idLoc);
					
					DocumentComptable dc = new DocumentComptable(numDoc,dateDocQuittance,TypeDoc.QUITTANCE,montant,fichierDoc);
					dc.setLocataire(locataire);
					BienLocatif bien = dbl.findById(idBien);
					FactureBien facturebien = new FactureBien(bien,dc,1);
					
					ddc.create(dc);
					dfb.create(facturebien);
					GenereQuittance.generateQuittanceWord(dc);
					
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(fenQuittanceLoyer, "La quittance a bien été créé.", "Validé", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "Retour" : 
				fenQuittanceLoyer.dispose();
				break;
			case "Precedent" : 
				CardLayout cl = (CardLayout) fenQuittanceLoyer.getmainPanel().getLayout();
                cl.show(fenQuittanceLoyer.getmainPanel(), "Home");
				break;
			case "Suivant" : 
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
	            
	            int ligneSelectLocSuiv = tableLocataires.getSelectedRow();
				int ligneSelectBienActuelSuiv = tableBiensActuels.getSelectedRow();
				int ligneSelectBienAncienSuiv = tableBiensAnciens.getSelectedRow();
				String idLocSuiv = (String) tableLocataires.getValueAt(ligneSelectLocSuiv, 0);
				if (fenQuittanceLoyer.getEstActuel()) {
					idBien = (String) tableBiensActuels.getValueAt(ligneSelectBienActuelSuiv, 0);
				}else {
					idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBienAncienSuiv, 0);
				}
				
				fenQuittanceLoyer.getGestionTableQuittance().remplirTableCharge(null,idLocSuiv,idBien);
				CardLayout cll = (CardLayout) fenQuittanceLoyer.getmainPanel().getLayout();
                cll.show(fenQuittanceLoyer.getmainPanel(), "Quittance");
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
