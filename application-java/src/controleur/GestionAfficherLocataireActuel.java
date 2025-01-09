package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import modele.Bail;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterLocataire;

public class GestionAfficherLocataireActuel implements ActionListener {
	
	private AfficherLocatairesActuels fen_afficher_locataires_actuels;
	private GestionChampsLocataireActuel gestion_champs_loc;
	private DaoLocataire daoLocataire;
	private DaoBail daoBail;
	
	
	public GestionAfficherLocataireActuel(AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.gestion_champs_loc = new GestionChampsLocataireActuel(afl);
		this.daoLocataire = new DaoLocataire();
		this.daoBail = new DaoBail();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" :
				this.fen_afficher_locataires_actuels.dispose();
				break;
			case "Modifier" :
				
				JTable tableLoc = this.fen_afficher_locataires_actuels.getTableLocatairesActuels();
			    int ligneSelect = tableLoc.getSelectedRow();
			    
			    if (ligneSelect > -1) {
			    	modifierLocataire(tableLoc, ligneSelect);	
			    }  
				break;
				
			case "Ajouter" : 
				AjouterLocataire al = new AjouterLocataire(this.fen_afficher_locataires_actuels);
				JLayeredPane layeredPaneAjoutLoc = this.fen_afficher_locataires_actuels.getLayeredPane();
				layeredPaneAjoutLoc.add(al, JLayeredPane.PALETTE_LAYER);
				al.setVisible(true);
				break;
			case "Résilier le bail" : 
				System.out.println("resiliation");
				
				JTable tableLoc1 = this.fen_afficher_locataires_actuels.getTableLocatairesActuels();
			    int ligneSelectLoc = tableLoc1.getSelectedRow();
			    
			    JTable tableBiens = this.fen_afficher_locataires_actuels.getTableBiensLoues();
			    int ligneSelectBiens = tableBiens.getSelectedRow();
				
				Locataire loc = this.gestion_champs_loc.lireLigneTable(tableLoc1);
				Bail bail = GestionChampsMontantAfficherLocataire.recupererBail(this.daoBail, tableLoc1, ligneSelectLoc);
				
				if (ligneSelectLoc > -1 && ligneSelectBiens > -1) {
					List<Contracter> contrats = loc.getContrats();
					for (Contracter contrat : contrats) {
						if (contrat.getBail().getIdBail().equals(bail.getIdBail())) {
							contrat.setDateSortie(String.valueOf(LocalDate.now()));
							System.out.println("l.79 gestion afficherlocatataireactuel : " + contrat.getDateSortie());
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez séléctionner un locataire et un bien", "Impossible de résilier le bail", JOptionPane.ERROR_MESSAGE);
				}

				break;
		}
	}

	
	private void modifierLocataire(JTable tableLoc, int ligneSelect) {
		Locataire loc;
		try {
			loc = this.gestion_champs_loc.lireLigneTable(tableLoc);
			
			loc.setNom((String) tableLoc.getValueAt(ligneSelect, 1));
		    loc.setPrenom((String) tableLoc.getValueAt(ligneSelect, 2));
		    loc.setDateNaissance(this.fen_afficher_locataires_actuels.getTextFieldDateDeNaissance().getText());
		    loc.setEmail(this.fen_afficher_locataires_actuels.getTextFieldMail().getText());
		    loc.setTelephone(this.fen_afficher_locataires_actuels.getTextFieldTel().getText());
		    
		    JOptionPane.showMessageDialog(null, "Modification effectuée");
		    
			try {
				this.daoLocataire.update(loc);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	      
	
}
