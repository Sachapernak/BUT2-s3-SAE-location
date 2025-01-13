package controleur;

import java.awt.CardLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import modele.BienLocatif;
import modele.ChargeFixe;
import modele.ChargeIndex;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.Locataire;
import modele.Loyer;
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
	private JTable tableChargeIndex;
	private JTable tableChargeFixe;
	private JTable tableLoyer;
	private DaoLocataire dl;
	private DaoBienLocatif dbl;
	private DaoFactureBien dfb; 
	private DaoDocumentComptable ddc;
	
	public GestionQuittanceLoyerLocataire(QuittanceLoyerPrincipal fenQuittanceLoyer) {
		this.fenQuittanceLoyer = fenQuittanceLoyer;
		this.tableLocataires = fenQuittanceLoyer.getTableLocataires();
		this.tableBiensActuels = fenQuittanceLoyer.getTableBiensActuels();
		this.tableBiensAnciens = fenQuittanceLoyer.getTableBiensAnciens();
		this.tableChargeIndex = fenQuittanceLoyer.getChargeIndexTable();
		this.tableChargeFixe = fenQuittanceLoyer.getChargeCFTable();
		this.tableLoyer = fenQuittanceLoyer.getLoyerTable();
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
				if (tableLoyer.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner un loyer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
	            if (tableChargeIndex.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner une charge index.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
	            if (tableChargeFixe.getSelectedRow() == -1) {
	                JOptionPane.showMessageDialog(fenQuittanceLoyer, "Veuillez sélectionner une charge CF.", "Avertissement", JOptionPane.WARNING_MESSAGE);
	                break;
	            }
				
				String idBien;
				int ligneSelectLoc = tableLocataires.getSelectedRow();
				int ligneSelectBien = tableBiensActuels.getSelectedRow();
				int ligneSelectLoyer = tableLoyer.getSelectedRow();
				int ligneSelectChargeIndex = tableChargeIndex.getSelectedRow();
				int ligneSelectChargeFixe = tableChargeFixe.getSelectedRow();
				String idLoc = (String) tableLocataires.getValueAt(ligneSelectLoc, 0);
				
				if (fenQuittanceLoyer.getEstActuel()) {
					idBien = (String) tableBiensActuels.getValueAt(ligneSelectBien, 0);
				}else {
					idBien = (String) tableBiensAnciens.getValueAt(ligneSelectBien, 0);
				}
				ChargeIndex ci = createChargeIndex(ligneSelectChargeIndex);
				ChargeFixe cf = createChargeFixe(ligneSelectChargeFixe);
				Loyer loyer = createLoyer(ligneSelectLoyer);
				String numDoc = fenQuittanceLoyer.getNumeroDoc();
				
				Date currentDate = new Date();

		        // Définir un formatteur
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		        // Formater la date
		        String dateDoc = formatter.format(currentDate);
				TypeDoc type = TypeDoc.QUITTANCE;
				BigDecimal montant = new BigDecimal((String) tableLoyer.getValueAt(ligneSelectLoyer,2));
				String fichierDoc = "Quittance de Loyer "+numDoc+".word";
				DocumentComptable dc = new DocumentComptable(numDoc,dateDoc,type,montant,fichierDoc);
				float partDeCharge = Float.parseFloat(fenQuittanceLoyer.getPartCharges());
				try {
					Locataire locataire = dl.findById(idLoc);
					dc.setLocataire(locataire);
					BienLocatif bien = dbl.findById(idBien);
					FactureBien facturebien = new FactureBien(bien,dc,partDeCharge);
					ddc.create(dc);
					dfb.create(facturebien);
					GenereQuittance.generateQuittanceWord(dc,loyer,ci,cf);
					
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
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
	
	public ChargeIndex createChargeIndex(int ligneSelectChargeIndex) {
		String id = (String) tableChargeIndex.getValueAt(ligneSelectChargeIndex, 0);
		String dateDeReleve = (String) tableChargeIndex.getValueAt(ligneSelectChargeIndex, 1);
		String type = (String) tableChargeIndex.getValueAt(ligneSelectChargeIndex, 2);
		BigDecimal valeurCompteur = new BigDecimal((String) tableChargeIndex.getValueAt(ligneSelectChargeIndex,3));
		BigDecimal coutVariable = new BigDecimal((String) tableChargeIndex.getValueAt(ligneSelectChargeIndex,4));
		BigDecimal coutFixe = new BigDecimal((String) tableChargeIndex.getValueAt(ligneSelectChargeIndex,5));
		
		
		return new ChargeIndex(id,dateDeReleve,type,valeurCompteur,coutVariable,coutFixe,null,null);
	}
	
	public ChargeFixe createChargeFixe(int ligneSelectChargeFixe) {
		String id = (String) tableChargeFixe.getValueAt(ligneSelectChargeFixe, 0);
		String dateDeCharge = (String) tableChargeFixe.getValueAt(ligneSelectChargeFixe, 1);
		String type = (String) tableChargeFixe.getValueAt(ligneSelectChargeFixe, 2);
		BigDecimal montant = new BigDecimal((String) tableChargeFixe.getValueAt(ligneSelectChargeFixe,3));
		

		return new ChargeFixe(id,dateDeCharge,type,montant,null,null);
	}
	
	public Loyer createLoyer(int ligneSelectLoyer) {
		String idBien = (String) tableLoyer.getValueAt(ligneSelectLoyer, 0);
		String dateDeChangement = (String) tableLoyer.getValueAt(ligneSelectLoyer, 1);
		BigDecimal montantLoyer = new BigDecimal((String) tableLoyer.getValueAt(ligneSelectLoyer,2));

		return new Loyer(idBien,dateDeChangement,montantLoyer);
	}
//	
//public Loyer(String idBien, String dateDeChangement, BigDecimal montantLoyer) {
//        
//    	if (montantLoyer.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Un loyer ne peut etre negatif");}
//    	
//    	this.idBien = idBien;
//        this.dateDeChangement = dateDeChangement;
//        this.montantLoyer = montantLoyer;
//    }
}
