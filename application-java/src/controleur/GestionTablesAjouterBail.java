package controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modele.Bail;
import modele.Contracter;
import modele.dao.DaoBail;
import modele.dao.DaoContracter;
import vue.AjouterBail;
import vue.AjouterLocataire;

public class GestionTablesAjouterBail implements ListSelectionListener, KeyListener{

	private AjouterBail fen_ajouter_bail;
	private AjouterLocataire fenAjouterLocataire;
	private DaoBail daoBail;
	private DaoContracter daoContracter;
	
	public GestionTablesAjouterBail(AjouterBail fen_ajouter_bail, AjouterLocataire fenAjouterLocataire)  {
		this.fen_ajouter_bail = fen_ajouter_bail;
		this.fenAjouterLocataire = fenAjouterLocataire;
		this.daoBail= new DaoBail();
		this.daoContracter= new DaoContracter();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		JTable tableBauxActuels = this.fen_ajouter_bail.getTableBauxActuels(); 
		int index = tableBauxActuels.getSelectedRow();
		if (index != -1) {
			try {
				Bail bail = daoBail.findById((String) tableBauxActuels.getValueAt(index, 0));
				remplirPartsLoyer(bail);
				
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public void remplirTableBauxExistant(JTable tableBauxExistant) {
		List<Bail> baux;
		try {
			DaoBail daoBail = new DaoBail();
			baux = daoBail.findAll();
		
		DefaultTableModel model = (DefaultTableModel) tableBauxExistant.getModel();
		model.setRowCount(0);
        for (Bail bail : baux) {
        	String complement = bail.getBien().getBat().getAdresse().getComplementAdresse();
        	String adresse = bail.getBien().getBat().getAdresse().getAdressePostale();
            model.addRow(new Object[] {bail.getIdBail(), complement, adresse, bail.getDateDeDebut(), bail.getDateDeFin()});
        }
        
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	private void remplirPartsLoyer(Bail bail) {
		JTable tablePartsLoyer = this.fen_ajouter_bail.getTablePartsLoyer();
		JTable tableTotal = this.fen_ajouter_bail.getTableTotal();
		DefaultTableModel modelTableParts = (DefaultTableModel) tablePartsLoyer.getModel();
		DefaultTableModel modelTotal = (DefaultTableModel) tableTotal.getModel();
				
		UtilitaireTable.viderTable(tablePartsLoyer);
		try {
			List<Contracter> contrats = this.daoContracter.getContrats(bail);
			for (Contracter contrat : contrats) {
				modelTableParts.addRow(new Object[] {contrat.getLocataire().getIdLocataire(), contrat.getPartLoyer()});
			}
			String id_new_loc = this.fenAjouterLocataire.getTextFieldIdLocataire();
			modelTableParts.addRow(new Object[] {id_new_loc, 0F});
						
			modelTableParts.addRow(new Object[] {" ",calculerPartsTotal(tablePartsLoyer)});
			modelTotal.setRowCount(contrats.size()+1);
			modelTotal.addRow(new String[] {"Total"}); 
					        
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
	}
	
	public float calculerPartsTotal(JTable tablePartsLoyer) {
		float somme = 0;
		for (int i = 0; i < tablePartsLoyer.getRowCount()-1; i ++) {
			float valeur = (float) tablePartsLoyer.getValueAt(i, 1);
			somme += valeur;
		}
		return somme;
	}
	
	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			JTable tablePartsLoyer = this.fen_ajouter_bail.getTablePartsLoyer();
			float valeur = calculerPartsTotal(tablePartsLoyer);
			tablePartsLoyer.setValueAt(valeur, tablePartsLoyer.getRowCount()-1, 1);
			if (valeur > 1F) {
				this.fen_ajouter_bail.getLblMessageErreur().setText("Attention le total des parts de loyer ne doit pas dépasser 1");
			}
		}
	}
	public void keyTyped(KeyEvent e) {
	
	}
	

	
}
