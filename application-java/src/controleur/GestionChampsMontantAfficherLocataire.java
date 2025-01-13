package controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Bail;
import modele.Cautionner;
import modele.ProvisionCharge;
import modele.dao.DaoBail;
import modele.dao.DaoCautionner;
import modele.dao.DaoProvisionCharge;
import vue.AfficherLocatairesActuels;

/**
 * Contrôleur gérant l'affichage des montants liés à un locataire sélectionné,
 * notamment les provisions pour charges et le montant de la caution.
 * <p>
 * Implémente {@link ListSelectionListener} pour réagir aux changements de sélection
 * dans la table des biens loués.
 * </p>
 */
public class GestionChampsMontantAfficherLocataire implements ListSelectionListener {

    /** Référence à la vue des locataires actuels. */
    private final AfficherLocatairesActuels fenLoc;
    private final DaoBail daoBail;
    private final DaoProvisionCharge daoProvision;
    private final DaoCautionner daoCautionner;

    /**
     * Constructeur principal.
     *
     * @param afl la vue des locataires actuels
     */
    public GestionChampsMontantAfficherLocataire(AfficherLocatairesActuels afl) {
        this.fenLoc = afl;
        this.daoBail = new DaoBail();
        this.daoProvision = new DaoProvisionCharge();
        this.daoCautionner = new DaoCautionner();
    }

    /**
     * Méthode appelée lorsque la sélection change dans la table des biens loués.
     * Elle met à jour les champs de provisions pour charges et caution dans la vue.
     *
     * @param e l'événement de changement de sélection
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
    	
        JTable tableBiens = fenLoc.getTableBiensLoues();
        int index = tableBiens.getSelectedRow();
        
        if (index != -1) {
            // Récupération du bail sélectionné à partir de la table
            Bail bailSelectionne = recupererBail(daoBail, tableBiens, index);

            // Vérification que le bail n'est pas null pour éviter NullPointerException
            if (bailSelectionne == null) {
                fenLoc.afficherMessageErreur("Aucun bail sélectionné ou bail introuvable.");
                return;
            }

            BigDecimal provision = null;
            BigDecimal caution = null;

            try {
                // Récupération de la provision associée au bail
                List<ProvisionCharge> provisions = daoProvision.findByIdBail(bailSelectionne.getIdBail());
                if (!provisions.isEmpty()) {
                    provision = provisions.get(0).getProvisionPourCharge();
                }

                // Récupération de la caution associée au bail
                Cautionner cautionner = daoCautionner.findByIdBail(bailSelectionne.getIdBail());
                if (cautionner != null) {
                    caution = cautionner.getMontant();
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
                fenLoc.afficherMessageErreur("Erreur lors de la récupération : " + ex.getMessage());
            }

            // Mise à jour des champs dans la vue
            fenLoc.getTextFieldProvisionPourCharge().setText(String.valueOf(provision));
            fenLoc.getTextFieldCaution().setText(String.valueOf(caution));
        }
    }

    /**
     * Récupère un {@link Bail} à partir d'une ligne sélectionnée dans une table.
     *
     * @param daoBail     l'accès aux données pour les baux
     * @param tableBiens  la table contenant les biens loués
     * @param index       l'indice de la ligne sélectionnée
     * @return le {@link Bail} correspondant ou null en cas d'erreur
     */
    public static Bail recupererBail(DaoBail daoBail, JTable tableBiens, int index) {
        Bail bail = null;
        String idBail = (String) tableBiens.getValueAt(index, 0);
        try {
            bail = daoBail.findById(idBail);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return bail;
    }
}
