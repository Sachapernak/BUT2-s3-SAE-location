package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modele.Adresse;
import modele.Entreprise;
import modele.dao.DaoAdresse;
import modele.dao.DaoEntreprise;
import vue.AjouterEntreprise;

public class GestionAjouterEntreprise implements ActionListener{

    private final DaoAdresse daoAdresse; 
    private final DaoEntreprise daoEntreprise;
    private final AjouterEntreprise fenAjoutEntreprise;
    private final VerificationChamps verifChamps;

    public GestionAjouterEntreprise(AjouterEntreprise ae) {
        this.fenAjoutEntreprise = ae;
        this.verifChamps = new VerificationChamps();
        this.daoAdresse = new DaoAdresse();
        this.daoEntreprise = new DaoEntreprise();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnLibelle = ((JButton) e.getSource()).getText();
        switch (btnLibelle) {
            case "Annuler":
            	this.fenAjoutEntreprise.dispose();
                break;
            case "Ajouter":
                handleValider();
                break;
            default:
                break;
        }
    }

    private void handleValider() {
        if (!verifierChamps()) {
            return;
        }

        try {
            Entreprise entreprise = creerEntreprise();
            if (entreprise != null) {
                this.fenAjoutEntreprise.afficherMessage("Entreprise créé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            this.fenAjoutEntreprise.afficherMessage("Erreur lors de la création de l'entreprise : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        this.fenAjoutEntreprise.dispose();
    }

    private boolean verifierChamps() {
        List<String> champs = this.fenAjoutEntreprise.getChampsObligatoires();

        if (!this.verifChamps.champsRemplis(champs)) {
            this.fenAjoutEntreprise.afficherMessage("Tous les champs obligatoires doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!this.verifChamps.validerCodePostal(this.fenAjoutEntreprise.getStringTextFieldCodePostal())) {
            this.fenAjoutEntreprise.afficherMessage("Le code postal doit être composé de 5 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Entreprise creerEntreprise() throws SQLException, IOException {
        String siret = this.fenAjoutEntreprise.getStringTextFieldSiret();
        Adresse adresse = creerAdresse();
        if (daoEntreprise.findById(siret) != null) {
            this.fenAjoutEntreprise.afficherMessage("L'entreprise existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (daoAdresse.findById(adresse.getIdAdresse()) == null) {
            daoAdresse.create(adresse);
        }
        String secteurActivite = this.fenAjoutEntreprise.getStringTextFieldSecteurActivite();
        
        Entreprise entreprise = new Entreprise(siret, null, secteurActivite,  adresse);
        
        String nom = this.fenAjoutEntreprise.getStringTextFieldNom();
        if (!nom.isEmpty()) {
        	entreprise.setNom(nom);
        }
                
        daoEntreprise.create(entreprise);
        return entreprise;
    }

    private Adresse creerAdresse() {
        String id = this.fenAjoutEntreprise.getStringTextFieldIdAdr();
        String adr = this.fenAjoutEntreprise.getStringTextFieldAdresse();
        int cp = Integer.parseInt(this.fenAjoutEntreprise.getStringTextFieldCodePostal());
        String ville = this.fenAjoutEntreprise.getStringTextFieldVille();
        String complement = this.fenAjoutEntreprise.getStringTextFieldComplement();

        Adresse adresse = new Adresse(id, adr, cp, ville);
        if (!complement.isEmpty()) {
            adresse.setComplementAdresse(complement);
        }
        return adresse;
    }
}
