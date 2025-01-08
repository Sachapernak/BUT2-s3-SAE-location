package controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JButton;

import modele.BienLocatif;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.Locataire;
import modele.TypeDoc;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoFactureBien;
import modele.dao.DaoLocataire;
import vue.AfficherQuittance;
import vue.GenereQuittance;

public class GestionAfficherQuittance  implements ActionListener{
	
	
	private AfficherQuittance fenAfficherQuittance;
	private DaoLocataire dl;
	private DaoBienLocatif dbl;
	private DaoFactureBien dfb; 
	private DaoDocumentComptable ddc;
	
	public GestionAfficherQuittance(AfficherQuittance fenAfficherQuittance) {
		this.fenAfficherQuittance = fenAfficherQuittance;
		this.dl = new DaoLocataire();
		this.dbl = new DaoBienLocatif();
		this.dfb = new DaoFactureBien();
		this.ddc = new DaoDocumentComptable();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnActif = (JButton) e.getSource();
		String btnLibelle = btnActif.getText();
		
		
		switch (btnLibelle) {
			case "Sauvegarder" : 
						
				String numDoc = fenAfficherQuittance.getNumeroDoc();
				String dateDoc = fenAfficherQuittance.getDateDoc();
				TypeDoc type = TypeDoc.QUITTANCE;
				BigDecimal montant = new BigDecimal(fenAfficherQuittance.getMontant());
				String fichierDoc = "Quittance de Loyer "+numDoc;
				DocumentComptable dc = new DocumentComptable(numDoc,dateDoc,type,montant,fichierDoc);
				float partDeCharge = Float.parseFloat(fenAfficherQuittance.getPartCharges());
				try {
					Locataire locataire = dl.findById(fenAfficherQuittance.getLocataire());
					dc.setLocataire(locataire);
					BienLocatif bien = dbl.findById(fenAfficherQuittance.getBien());
					FactureBien facturebien = new FactureBien(bien,dc,partDeCharge);
					ddc.create(dc);
					dfb.create(facturebien);
					GenereQuittance.generateQuittanceWord(dc);
					
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				
				}
				
				
				
					
				
				break;
			case "Retour" : 
				fenAfficherQuittance.dispose();
				break;
			
		}
	}
	
	
	public DocumentComptable creationDocumentComptable() {
		
		//TypeDoc type = TypeDoc.valueOf();
		return null;
		
	}

}
