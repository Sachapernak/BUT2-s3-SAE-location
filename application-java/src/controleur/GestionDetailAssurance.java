package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import modele.Assurance;
import vue.DetailAssurance;

/**
 * Gestionnaire pour la fenêtre de détail d'une assurance.
 */
public class GestionDetailAssurance {

    private DetailAssurance fen;
    private Assurance assu;

    /**
     * Constructeur du gestionnaire.
     * @param fen Fenêtre de détail de l'assurance.
     * @param assu L'assurance dont les détails sont affichés.
     */
    public GestionDetailAssurance(DetailAssurance fen, Assurance assu) {
        this.fen = fen;
        this.assu = assu;
    }

    /**
     * Charge les données de l'assurance dans la fenêtre de détail.
     */
    public void chargeDonnee() {
        fen.setNumeroContrat(assu.getNumeroContrat());
        fen.setAnneeContrat(String.valueOf(assu.getAnneeContrat()));
        fen.setTypeContrat(assu.getTypeContrat());
    }

    /**
     * Ajoute un écouteur pour le bouton Annuler qui ferme la fenêtre.
     * @param cancelButton Le bouton Annuler.
     */
    public void gestionAnnuler(JButton cancelButton) {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fen.dispose();
            }
        });
    }
}
