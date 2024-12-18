package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterLocataire;

public class GestionAfficherLocataire implements ActionListener {
	
	private AfficherLocatairesActuels fen_afficher_locataires_actuels;
	private GestionChampsLocataireActuel gestion_champs_loc;
	private DaoLocataire daoLocataire;
	
	
	public GestionAfficherLocataire(AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.gestion_champs_loc = new GestionChampsLocataireActuel(afl);
		this.daoLocataire = new DaoLocataire();
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
				break;
				
			case "Ajouter" : 
				AjouterLocataire al = new AjouterLocataire(this.fen_afficher_locataires_actuels);
				JLayeredPane layeredPaneAjoutLoc = this.fen_afficher_locataires_actuels.getLayeredPane();
				layeredPaneAjoutLoc.add(al, JLayeredPane.PALETTE_LAYER);
				al.setVisible(true);
				break;
			case "Résilier le bail" : 
				System.out.println("resiliation");
				break;
		}
	}
	      
	
}
