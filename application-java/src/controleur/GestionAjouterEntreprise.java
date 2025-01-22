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

/**
 * Contrôleur gérant l'ajout d'une entreprise
 * 
 * Cette classe interagit avec la vue AjouterEntreprise et met à jour le
 * modèle (DAOs, entités) en fonction des actions de l'utilisateur.
 */

public class GestionAjouterEntreprise implements ActionListener{

    private static final String ERREUR = "Erreur";
	private final DaoAdresse daoAdresse; 
    private final DaoEntreprise daoEntreprise;
    private final AjouterEntreprise fenAjoutEntreprise;
    private final VerificationChamps verifChamps;

    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param ae  la vue pour ajouter une entreprise
     */
    public GestionAjouterEntreprise(AjouterEntreprise ae) {
        this.fenAjoutEntreprise = ae;
        this.verifChamps = new VerificationChamps();
        this.daoAdresse = new DaoAdresse();
        this.daoEntreprise = new DaoEntreprise();
    }

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
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
            Entreprise entreprise = creerEntreprise();
            if (entreprise != null) {
                this.fenAjoutEntreprise.afficherMessage("Entreprise créé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            this.fenAjoutEntreprise.afficherMessage("Erreur lors de la création de l'entreprise : " + ex.getMessage(), ERREUR, JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        this.fenAjoutEntreprise.dispose();
    }

    /**
     * Vérifie si tous les champs obligatoires sont remplis et si le code postal est valide.
     * Affiche un message d'erreur si une condition n'est pas respectée.
     *
     * @return true si tous les champs sont valides, false sinon.
     */
    private boolean verifierChamps() {
        List<String> champs = this.fenAjoutEntreprise.getChampsObligatoires();

        if (!this.verifChamps.champsRemplis(champs)) {
            this.fenAjoutEntreprise.afficherMessage("Tous les champs obligatoires doivent être remplis.", ERREUR, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!this.verifChamps.validerCodePostal(this.fenAjoutEntreprise.getStringTextFieldCodePostal())) {
            this.fenAjoutEntreprise.afficherMessage("Le code postal doit être composé de 5 chiffres.", ERREUR, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    
    /**
     * Crée une instance d'entreprise en fonction des informations saisies par l'utilisateur.
     * Vérifie si l'entreprise ou l'adresse existe déjà en base de données avant de les ajouter.
     *
     * @return une instance de l'objet Entreprise créée à partir des champs de l'interface,
     *         ou null si une erreur survient (par exemple, l'entreprise existe déjà).
     * @throws SQLException en cas de problème d'accès à la base de données.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    private Entreprise creerEntreprise() throws SQLException, IOException {
        String siret = this.fenAjoutEntreprise.getStringTextFieldSiret().replaceAll("\\s","");
        Adresse adresse = creerAdresse();
        if (daoEntreprise.findById(siret) != null) {
            this.fenAjoutEntreprise.afficherMessage("L'entreprise existe déjà.", ERREUR, JOptionPane.ERROR_MESSAGE);
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

    /**
     * Crée une instance de l'adresse en fonction des informations saisies par l'utilisateur.
     * Vérifie et ajoute un complément d'adresse si celui-ci est renseigné.
     *
     * @return une instance de l'objet Adresse créée à partir des champs de l'interface.
     */
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
