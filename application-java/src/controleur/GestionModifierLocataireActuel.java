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
				
	             try {
	                    //modifierLocataire();
	                    JOptionPane.showMessageDialog(this.fen_modifier_locataire, 
	                        "Modification effectuée avec succès.", 
	                        "Modifier le locataire", 
	                        JOptionPane.INFORMATION_MESSAGE);
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(this.fen_modifier_locataire, 
	                        "Erreur lors de la modification : " + ex.getMessage(), 
	                        "Erreur", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	                this.fen_modifier_locataire.dispose();
	                break;
		}
	}
	
    public void modifierLocataire() throws SQLException, IOException {
        // Récupérer le locataire sélectionné
        JList<String> listeLoc = this.fen_afficher_locataires_actuels.getListLocatairesActuels();
        int ligneSelect = listeLoc.getSelectedIndex();
        if (ligneSelect < 0) {
            throw new IllegalStateException("Aucun locataire sélectionné.");
        }
        
        // Obtenir l'ID du locataire
        String[] infos = GestionAfficherLocataire.separerInfosLoc(listeLoc, ligneSelect);
        String locataireId = infos[0];
        Locataire locataire = daoLocataire.findById(locataireId);

        // Mettre à jour les informations à partir des champs de l'interface
        locataire.setNom(this.fen_modifier_locataire.getTextFieldNom().getText());
        locataire.setPrenom(this.fen_modifier_locataire.getTextFieldPrenom().getText());
        locataire.setEmail(this.fen_modifier_locataire.getTextFieldEmail().getText());
        locataire.setTelephone(this.fen_modifier_locataire.getTextFieldTel().getText());
        locataire.setDateNaissance(this.fen_modifier_locataire.getTextFieldDateNaissance().getText());
        locataire.setLieuDeNaissance(this.fen_modifier_locataire.getTextFieldLieuNaissance().getText());
        
        // Mise à jour de l'adresse
        if (locataire.getAdresse() != null) {
            locataire.getAdresse().setAdresse(this.fen_modifier_locataire.getTextFieldAdr().getText());
            locataire.getAdresse().setComplementAdresse(this.fen_modifier_locataire.getTextFieldComplement().getText());
            locataire.getAdresse().setCodePostal(Integer.parseInt(this.fen_modifier_locataire.getTextFieldCodePostal().getText()));
            locataire.getAdresse().setVille(this.fen_modifier_locataire.getTextFieldVille().getText());
        }
        
        // Enregistrer les modifications dans la base de données
        daoLocataire.update(locataire);
        
        // Mettre à jour la liste des locataires affichés
        GestionAfficherLocataire gestionAfficher = new GestionAfficherLocataire(fen_afficher_locataires_actuels);
        gestionAfficher.remplirListeLoc();
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
