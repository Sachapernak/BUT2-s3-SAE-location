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


/**
 * GestionTablesAjouterBail gère les interactions entre les tables d'affichage dans les vues
 * {@link AjouterBail} et {@link AjouterLocataire} liées à l'ajout d'un bail.
 * Elle écoute les sélections de lignes de tables et les saisies clavier pour effectuer 
 * des mises à jour correspondantes.
 */
public class GestionTablesAjouterBail implements ListSelectionListener, KeyListener{

	private AjouterBail fenAjouterBail;
	private AjouterLocataire fenAjouterLocataire;
	private DaoBail daoBail;
	private DaoContracter daoContracter;
	
	
    /**
     * Constructeur pour initialiser le gestionnaire avec les vues et les DAO nécessaires.
     * @param fenAjouterBail la vue pour ajouter un bail.
     * @param fenAjouterLocataire la vue pour ajouter un locataire.
     */
	public GestionTablesAjouterBail(AjouterBail fenAjouterBail, AjouterLocataire fenAjouterLocataire)  {
		this.fenAjouterBail = fenAjouterBail;
		this.fenAjouterLocataire = fenAjouterLocataire;
		this.daoBail= new DaoBail();
		this.daoContracter= new DaoContracter();
	}
	
    /**
     * Méthode appelée lors d'un changement de sélection dans une table.
     * Elle récupère le bail sélectionné et remplit les parts de loyer associées.
     * @param e l'événement de sélection de liste.
     */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		JTable tableBauxActuels = this.fenAjouterBail.getTableBauxActuels(); 
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
	
    /**
     * Remplit la table des baux existants avec les données récupérées depuis la base.
     * @param tableBauxExistant la table à remplir.
     */
	public void remplirTableBauxExistant(JTable tableBauxExistant) {
		List<Bail> baux;
		try {
			baux = daoBail.findAll();
		
		DefaultTableModel model = (DefaultTableModel) tableBauxExistant.getModel();
		model.setRowCount(0);
        for (Bail bail : baux) {
        	String complement = bail.getBien().getBat().getAdresse().getComplementAdresse();
        	String adresse = bail.getBien().getBat().getAdresse().getAdressePostale();
            model.addRow(new Object[] {bail.getIdBail(), complement, adresse, bail.getDateDeDebut(), bail.getDateDeFin()});
        }
        
		} catch (SQLException | IOException e) {
			fenAjouterBail.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		} 
					
	}
	
    /**
     * Remplit la table des parts de loyer pour un bail donné.
     * @param bail le bail pour lequel remplir les parts de loyer.
     */
	private void remplirPartsLoyer(Bail bail) {
		JTable tablePartsLoyer = this.fenAjouterBail.getTablePartsLoyer();
		JTable tableTotal = this.fenAjouterBail.getTableTotal();
		DefaultTableModel modelTableParts = (DefaultTableModel) tablePartsLoyer.getModel();
		DefaultTableModel modelTotal = (DefaultTableModel) tableTotal.getModel();
				
		// Vider la table avant de la remplir
		UtilitaireTable.viderTable(tablePartsLoyer);
		try {
			// Remplit la table avec les parts de loyer existantes pour chaque contrat
			List<Contracter> contrats = this.daoContracter.getContrats(bail);
			for (Contracter contrat : contrats) {
				modelTableParts.addRow(new Object[] {contrat.getLocataire().getIdLocataire(), contrat.getPartLoyer()});
			}
			
            // Ajoute une nouvelle ligne pour un locataire à ajouter
			String idNewLoc = this.fenAjouterLocataire.getTextFieldIdLocataire();
			modelTableParts.addRow(new Object[] {idNewLoc, 0F});
			
            // Ajoute une ligne pour afficher le total des parts calculées
			modelTableParts.addRow(new Object[] {" ",calculerPartsTotal(tablePartsLoyer)});
			modelTotal.setRowCount(contrats.size()+1);
			
			modelTotal.addRow(new String[] {"Total"}); 
					        
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
	}
	
	
    /**
     * Calcule la somme des parts de loyer présentes dans la table.
     * @param tablePartsLoyer la table contenant les parts de loyer.
     * @return la somme des parts de loyer.
     */
	public float calculerPartsTotal(JTable tablePartsLoyer) {
		float somme = 0;
		for (int i = 0; i < tablePartsLoyer.getRowCount()-1; i ++) {
			float valeur = Float.parseFloat(String.valueOf(tablePartsLoyer.getValueAt(i, 1)));
			somme += valeur;
		}
		return somme;
	}
	
	

	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			JTable tablePartsLoyer = this.fenAjouterBail.getTablePartsLoyer();
			float valeur = calculerPartsTotal(tablePartsLoyer);
			tablePartsLoyer.setValueAt(valeur, tablePartsLoyer.getRowCount()-1, 1);
			if (valeur != 1F) {
				this.fenAjouterBail.getLblMessageErreur().setText("Attention le total des parts de loyer doit être égal à 1");
			}
		}
	}
	public void keyTyped(KeyEvent e) {
		// Uniquement pour keyReleased
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Uniquement pour keyRelease aussi
		
	}
	

	
}
