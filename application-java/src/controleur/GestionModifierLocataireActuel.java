package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.ModifierLocataireActuel;

public class GestionModifierLocataireActuel implements ActionListener{
	
	private ModifierLocataireActuel fen_modifier_locataire;
	private AfficherLocatairesActuels fen_afficher_locataires_actuels ;
	private DaoLocataire daoLocataire;
	
	public GestionModifierLocataireActuel(ModifierLocataireActuel mla, AfficherLocatairesActuels al) {
		this.fen_modifier_locataire = mla;
		this.fen_afficher_locataires_actuels = al;
		this.daoLocataire = new DaoLocataire();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fen_modifier_locataire.dispose();
				break;
			case "Valider" : 
				this.fen_modifier_locataire.dispose();
				JOptionPane.showMessageDialog(this.fen_modifier_locataire,"Modification effectuée","Modifier le locataire", JOptionPane.INFORMATION_MESSAGE);
				break;
		}
	}
	
	public Locataire recupererInfosLocataire() {
		JList<String> listeLoc = this.fen_afficher_locataires_actuels.getListLocatairesActuels();
		String[] parts;
	    int ligneSelect = listeLoc.getSelectedIndex();
	    if (ligneSelect > -1) {
	    	parts = GestionAfficherLocataire.separerInfosLoc(listeLoc,ligneSelect);
	    	try {
				return daoLocataire.findById(parts[0]);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return null;
	}
	
	public void remplirChampsModification() {
        Locataire loc = recupererInfosLocataire();
        this.fen_modifier_locataire.getTextFieldNom().setText(loc.getNom());
        this.fen_modifier_locataire.getTextFieldPrenom().setText(loc.getPrenom());
        this.fen_modifier_locataire.getTextFieldEmail().setText(loc.getEmail() != null ? loc.getEmail() : "");
        this.fen_modifier_locataire.getTextFieldTel().setText(loc.getTelephone() != null ? loc.getTelephone() : "");
        this.fen_modifier_locataire.getTextFieldDateNaissance().setText(loc.getDateNaissance());
        this.fen_modifier_locataire.getTextFieldLieuNaissance().setText(loc.getLieuDeNaissance());
        
        if (loc.getAdresse() != null) {
            this.fen_modifier_locataire.getTextFieldAdr().setText(loc.getAdresse().getAdressePostale() != null ? loc.getAdresse().getAdressePostale() : "");
            this.fen_modifier_locataire.getTextFieldComplement().setText(loc.getAdresse().getComplementAdresse() != null ? loc.getAdresse().getComplementAdresse() : "");
            this.fen_modifier_locataire.getTextFieldCodePostal().setText(String.valueOf(loc.getAdresse().getCodePostal()) != null ? String.valueOf(loc.getAdresse().getCodePostal()) : "");
            this.fen_modifier_locataire.getTextFieldVille().setText(loc.getAdresse().getVille() != null ? loc.getAdresse().getVille() : "");
        } else {
            this.fen_modifier_locataire.getTextFieldAdr().setText("");
            this.fen_modifier_locataire.getTextFieldComplement().setText("");
            this.fen_modifier_locataire.getTextFieldCodePostal().setText("");
            this.fen_modifier_locataire.getTextFieldVille().setText("");
        }
	}
	
}
