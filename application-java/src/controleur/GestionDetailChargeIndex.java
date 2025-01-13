package controleur;

import javax.swing.JButton;

import modele.ChargeIndex;
import vue.DetailChargeIndex;

/**
 * Gestionnaire pour la fenêtre de détail d'une charge indexée.
 */
public class GestionDetailChargeIndex {

    private DetailChargeIndex fen;
    private ChargeIndex chargeIndex;

    /**
     * Constructeur du gestionnaire.
     * @param fen Fenêtre de détail de la charge indexée.
     * @param chargeIndex L'objet ChargeIndex dont les détails sont affichés.
     */
    public GestionDetailChargeIndex(DetailChargeIndex fen, ChargeIndex chargeIndex) {
        this.fen = fen;
        this.chargeIndex = chargeIndex;
    }

    /**
     * Charge les données de la charge indexée dans la fenêtre.
     */
    public void chargeDonnee() {
        fen.setId(chargeIndex.getId());
        fen.setDateDeReleve(chargeIndex.getDateDeReleve());
        fen.setDateRelevePrecedent(chargeIndex.getDateRelevePrecedent());
        fen.setType(chargeIndex.getType());
        fen.setValeurCompteur(chargeIndex.getValeurCompteur().toString());
        fen.setCoutFixe(chargeIndex.getCoutFixe().toString());
        fen.setNumDoc(chargeIndex.getNumDoc());
        fen.setValUnitaire(chargeIndex.getCoutVariable().toString());
    }

    /**
     * Ajoute un écouteur pour le bouton Annuler qui ferme la fenêtre.
     * @param cancelButton Le bouton Annuler.
     */
    public void gestionAnnuler(JButton cancelButton) {
        cancelButton.addActionListener(e -> fen.dispose());
    }
}
