package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import modele.Adresse;
import modele.Batiment;
import modele.dao.DaoAdresse;
import modele.dao.DaoBatiment;
import vue.AjouterBatiment;

public class GestionAjouterBatiment implements ActionListener {

    private final DaoAdresse daoAdresse; 
    private final DaoBatiment daoBatiment;
    private final AjouterBatiment fenAjoutBatiment;
    private final VerificationChamps verifChamps;

    public GestionAjouterBatiment(AjouterBatiment aj) {
        this.fenAjoutBatiment = aj;
        this.verifChamps = new VerificationChamps();
        this.daoAdresse = new DaoAdresse();
        this.daoBatiment = new DaoBatiment();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnLibelle = ((JButton) e.getSource()).getText();
        switch (btnLibelle) {
            case "Annuler":
            	this.fenAjoutBatiment.dispose();
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
            Batiment batiment = creerBatiment();
            if (batiment != null) {
                this.fenAjoutBatiment.afficherMessage("Bâtiment ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (Exception ex) {
            this.fenAjoutBatiment.afficherMessage("Erreur lors de la création du bâtiment : " + ex.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);;
            ex.printStackTrace();
        }

        this.fenAjoutBatiment.dispose();
    }

    private boolean verifierChamps() {
        List<String> champs = this.fenAjoutBatiment.getChampsObligatoires();

        if (!this.verifChamps.champsRemplis(champs)) {
            this.fenAjoutBatiment.afficherMessage("Tous les champs obligatoires doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!this.verifChamps.validerCodePostal(this.fenAjoutBatiment.getStringTextFieldCodePostal())) {
            this.fenAjoutBatiment.afficherMessage("Le code postal doit être composé de 5 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Batiment creerBatiment() throws SQLException, IOException {
        String idBat = this.fenAjoutBatiment.getStringTextFieldIdentifiant();
        Adresse adresse = creerAdresse();
        if (daoBatiment.findById(idBat) != null) {
            this.fenAjoutBatiment.afficherMessage("Le bâtiment existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (daoAdresse.findById(adresse.getIdAdresse()) == null) {
            daoAdresse.create(adresse);
        }
        Batiment batiment = new Batiment(idBat, adresse);
        daoBatiment.create(batiment);
        return batiment;
    }

    private Adresse creerAdresse() {
        String id = this.fenAjoutBatiment.getStringTextFieldIdAdr();
        String adr = this.fenAjoutBatiment.getStringTextFieldAdresse();
        int cp = Integer.parseInt(this.fenAjoutBatiment.getStringTextFieldCodePostal());
        String ville = this.fenAjoutBatiment.getStringTextFieldVille();
        String complement = this.fenAjoutBatiment.getStringTextFieldComplement();

        Adresse adresse = new Adresse(id, adr, cp, ville);
        if (!complement.isEmpty()) {
            adresse.setComplementAdresse(complement);
        }
        return adresse;
    }
}
