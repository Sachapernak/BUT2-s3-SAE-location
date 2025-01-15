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

/**
 * Contrôleur gérant l'ajout d'un batiment 
 * 
 * Cette classe interagit avec la vue AjouterBatiment et met à jour le
 * modèle (DAOs, entités) en fonction des actions de l'utilisateur.
 */

public class GestionAjouterBatiment implements ActionListener {

    private final DaoAdresse daoAdresse; 
    private final DaoBatiment daoBatiment;
    private final AjouterBatiment fenAjoutBatiment;
    private final VerificationChamps verifChamps;

    
    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param aj  la vue pour ajouter un batiment
     */
    public GestionAjouterBatiment(AjouterBatiment aj) {
        this.fenAjoutBatiment = aj;
        this.verifChamps = new VerificationChamps();
        this.daoAdresse = new DaoAdresse();
        this.daoBatiment = new DaoBatiment();
    }

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
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

    
    // --------------------------------------------------------------------
 	//                 Méthodes privées de gestion d'actions
 	// --------------------------------------------------------------------

 	/**
 	 * Gère l’action de validation dans la vue AjouterBatiment.
 	 */
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

    /**
     * Vérifie si tous les champs obligatoires sont remplis et si le code postal est valide.
     * Affiche un message d'erreur si une condition n'est pas respectée.
     *
     * @return true si tous les champs sont valides, false sinon.
     */
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

    /**
     * Crée un bâtiment en fonction des informations saisies par l'utilisateur.
     * Vérifie si le bâtiment ou l'adresse existe déjà en base de données avant de les ajouter.
     *
     * @return le bâtiment créé, ou null si une erreur survient (par ex. bâtiment déjà existant).
     * @throws SQLException en cas de problème d'accès à la base de données.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
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
    
    /**
     * Crée une instance de l'adresse en fonction des informations saisies par l'utilisateur.
     * Vérifie et ajoute un complément d'adresse si celui-ci est renseigné.
     *
     * @return une instance de l'objet Adresse créée à partir des champs de l'interface.
     */
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
