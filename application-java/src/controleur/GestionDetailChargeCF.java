package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import modele.ChargeFixe;
import vue.DetailChargeCF;

/**
 * Gestionnaire pour la fenêtre de détail d'une charge fixe.
 */
public class GestionDetailChargeCF {

    private DetailChargeCF fen;
    private ChargeFixe chargeFixe;

    /**
     * Constructeur du gestionnaire.
     * @param fen Fenêtre de détail de la charge fixe.
     * @param chargeFixe L'objet ChargeFixe dont les détails sont affichés.
     */
    public GestionDetailChargeCF(DetailChargeCF fen, ChargeFixe chargeFixe) {
        this.fen = fen;
        this.chargeFixe = chargeFixe;
    }

    /**
     * Charge les données de la charge fixe dans la fenêtre.
     */
    public void chargeDonnee() {
        fen.setId(chargeFixe.getId());
        fen.setDateDeCharge(chargeFixe.getDateDeCharge());
        fen.setType(chargeFixe.getType());
        fen.setMontant(chargeFixe.getMontant().toString());
        fen.setNumDoc(chargeFixe.getNumDoc());
    }

    /**
     * Ajoute un écouteur pour le bouton Annuler qui ferme la fenêtre.
     * @param cancelButton Le bouton Annuler.
     */
    public void gestionAnnuler(JButton cancelButton) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fen.dispose();
            }
        });
    }
}
