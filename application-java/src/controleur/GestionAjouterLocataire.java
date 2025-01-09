package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AjouterBail;
import vue.AjouterLocataire;
import vue.AfficherLocatairesActuels;

public class GestionAjouterLocataire implements ActionListener {

    private final AjouterLocataire fenAjouterLocataire;
    private final AfficherLocatairesActuels fenAfficherLocataires;
    private final VerificationChamps verifChamps;

    public GestionAjouterLocataire(AjouterLocataire al, AfficherLocatairesActuels afl) {
        this.fenAjouterLocataire = al;
        this.fenAfficherLocataires = afl;
        this.verifChamps = new VerificationChamps();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        String buttonLabel = sourceButton.getText();

        if ("Annuler".equals(buttonLabel)) {
            actionAnnuler();
        } else if ("Continuer".equals(buttonLabel)) {
            handleContinuer();
        }
    }

    /**
     * Traite la logique lorsque l'utilisateur clique sur "Continuer".
     */
    private void handleContinuer() {
        // Vérification des champs obligatoires
        if (!verifChamps.champsRemplis(fenAjouterLocataire.getChampsObligatoires())) {
            fenAjouterLocataire.afficherMessageErreur("Il est nécessaire de remplir les champs obligatoires");
            return;
        }

        // Validation de la date
        String dateNaissance = fenAjouterLocataire.getTextFieldDateNaissance();
        if (!verifChamps.validerDate(dateNaissance)) {
            fenAjouterLocataire.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
            return;
        }

        // Vérification et validation de l'adresse si au moins un champ est rempli
        if (verifChamps.auMoinsUnChampRempli(fenAjouterLocataire.getChampsObligatoiresAdresse()) && !validerAdresse()) {
                return;
            }
        

        // Vérification de l'existence du locataire et ouverture de la fenêtre correspondante
        verifierEtOuvrirAjouterBail();
    }

    /**
     * Valide les champs d'adresse et affiche les messages d'erreur si nécessaire.
     * 
     * @return true si l'adresse est valide ou non nécessaire, false sinon.
     */
    private boolean validerAdresse() {
        String codePostal = fenAjouterLocataire.getTextFieldCodePostal();
        if (!codePostal.isEmpty() && !verifChamps.validerCodePostal(codePostal)) {
            fenAjouterLocataire.afficherMessageErreur("Le code postal doit etre composé de 5 entiers");
            return false;
        }
        if (!verifChamps.champsRemplis(fenAjouterLocataire.getChampsObligatoiresAdresse())) {
            fenAjouterLocataire.afficherMessageErreur(
                "Si vous voulez saisir une adresse, vous devez impérativement saisir l'ensemble des champs obligatoires à l'exception du complément"
            );
            return false;
        }
        return true;
    }

    /**
     * Vérifie si le locataire existe déjà et, dans le cas contraire, ouvre la fenêtre d'ajout de bail.
     */
    private void verifierEtOuvrirAjouterBail() {
        DaoLocataire daoLocataire = new DaoLocataire();
        try {
            String idLocataire = fenAjouterLocataire.getTextFieldIdLocataire();
            Locataire locataireExist = daoLocataire.findById(idLocataire);
            if (locataireExist == null) {
                AjouterBail ajoutBail = new AjouterBail(fenAjouterLocataire, fenAfficherLocataires);
                JLayeredPane layeredPane = fenAfficherLocataires.getLayeredPane();
                layeredPane.add(ajoutBail, JLayeredPane.PALETTE_LAYER);
                ajoutBail.setVisible(true);
            } else {
                fenAjouterLocataire.afficherMessageErreur("Le locataire existe déjà");
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionAnnuler() {
        fenAjouterLocataire.dispose();
    }
}
