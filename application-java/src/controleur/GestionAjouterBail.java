package controleur;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import modele.BienLocatif;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

/**
 * Contrôleur pour la gestion de l'ajout d'un bail.
 * <p>
 * Il gère les interactions utilisateur dans la vue {@link AjouterBail}
 * et interagit avec les DAO pour accéder aux données.
 */
public class GestionAjouterBail implements ActionListener {

    private final AjouterBail fenAjouterBail;
    private final AfficherLocatairesActuels fenAfficherLocataires;
    private final AjouterLocataire fenAjouterLocataire;
    private final DaoBienLocatif daoBien;
    private final VerificationChamps verifChamps;

    /**
     * Constructeur principal.
     *
     * @param ab  la vue pour ajouter un bail
     * @param al  la vue pour ajouter un locataire
     * @param afl la vue pour afficher les locataires actuels
     */
    public GestionAjouterBail(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
    	
    	this.fenAjouterBail = ab;
        this.fenAjouterLocataire = al;
        this.fenAfficherLocataires = afl;
        
        this.daoBien = new DaoBienLocatif();
        this.verifChamps = new VerificationChamps();
    }

    /**
     * Méthode invoquée lors d'une action sur un composant (bouton, radio).
     *
     * @param e l'événement d'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Gestion des boutons radio
        if (source instanceof JRadioButton) {
            actionPerformedRadio(e);
        }
        // Gestion des boutons (Annuler, Continuer, Vider, etc.)
        else if (source instanceof JButton btnActif) {
            String btnLibelle = btnActif.getText();
            switch (btnLibelle) {
                case "Annuler":
                    fenAjouterBail.dispose();
                    break;
                case "Continuer":
                    if (!gererErreurs()) {
                        ouvrirFenAjoutCautionnaire();
                    }
                    break;
                case "Vider":
                    viderTextFields();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Vérifie les erreurs de saisie dans la vue AjouterBail.
     *
     * @return true si une erreur est trouvée, false sinon
     */
    private boolean gererErreurs() {
        boolean erreurTrouvee = false;

        // Vérification du format des dates
        if (!verifChamps.verifierDates(fenAjouterBail.getChampsDate())) {
            fenAjouterBail.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
            erreurTrouvee = true;
        }
        // Cas d'un bail existant
        else if (fenAjouterBail.getRdbtnBailExistant().isSelected()) {
            // Vérifie la date d'arrivée (champ obligatoire)
            if (fenAjouterBail.getTextDateArrivee().isEmpty()) {
                fenAjouterBail.afficherMessageErreur(
                    "Les champs obligatoires (dotés d'une étoile) doivent être renseignés"
                );
                erreurTrouvee = true;
            }
            
            // Vérifie la somme des parts de loyer (doit être égale à 1)
            if (!totalPartEgalAUn()) {
                fenAjouterBail.afficherMessageErreur("Le total des parts n'est pas égal à 1");
                erreurTrouvee = true;
            }
        }
        // Cas d'un nouveau bail
        else if (fenAjouterBail.getRdbtnNouveauBail().isSelected()
                && !verifChamps.champsRemplis(fenAjouterBail.getChampsObligatoiresNouveauBail())) {
            fenAjouterBail.afficherMessageErreur(
                "Les champs obligatoires (dotés d'une étoile) doivent être renseignés"
            );
            erreurTrouvee = true;
        }
        
        else if (fenAjouterBail.getRdbtnNouveauBail().isSelected()
                && !verifChamps.validerBigDecimal(fenAjouterBail.getTextCharges())) {
        	fenAjouterBail.afficherMessageErreur(
                    "Le montant des charges doit etre un nombre"
                );
            erreurTrouvee = true;
        }

        return erreurTrouvee;
    }

    /**
     * Ouvre la fenêtre pour ajouter un cautionnaire.
     */
    private void ouvrirFenAjoutCautionnaire() {
        AjouterCautionnaire ac = new AjouterCautionnaire(
            fenAjouterBail,
            fenAjouterLocataire,
            fenAfficherLocataires
        );
        JLayeredPane layeredPaneAjoutCautionnaire = fenAfficherLocataires.getLayeredPane();
        layeredPaneAjoutCautionnaire.add(ac, JLayeredPane.PALETTE_LAYER);
        ac.setVisible(true);
    }

    /**
     * Initialise la date de début avec la date actuelle.
     *
     * @param textFieldDateDebut le champ de texte pour la date de début
     */
    public void initialiserDateDebut(JTextField textFieldDateDebut) {
        LocalDate dateActuelle = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        textFieldDateDebut.setText(dateActuelle.format(formatter));
    }

    /**
     * Remplit une JComboBox avec les identifiants des biens locatifs.
     *
     * @param comboBoxBiens la JComboBox à remplir
     */
    public void remplirJComboBoxBiens(JComboBox<String> comboBoxBiens) {
        try {
            List<BienLocatif> biens = daoBien.findAll();
            for (BienLocatif bien : biens) {
                comboBoxBiens.addItem(bien.getIdentifiantLogement());
            }
            verifierDispoNouveauBail();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie que la somme des parts de loyer est égale à 1
     * dans le tableau des parts.
     *
     * @return true si la somme est égale à 1, false sinon
     */
    public boolean totalPartEgalAUn() {
        JTable tableParts = fenAjouterBail.getTablePartsLoyer();
        int indexLastLigne = tableParts.getRowCount() - 1;
        float totalParts = (float) tableParts.getValueAt(indexLastLigne, 1);
        return totalParts == 1F;
    }

    /**
     * Vide les champs de saisie dans la vue AjouterBail (ID Bail, Date Début, Date Fin).
     */
    private void viderTextFields() {
        fenAjouterBail.setTextIdBail("");
        fenAjouterBail.setTextDateDebut("");
        fenAjouterBail.setTextDateFin("");
    }
    
    public void verifierDispoNouveauBail() {
    	
    	
    	try {
    		DaoBail dao = new DaoBail();
			int nbBail = dao.findNbBailActifParLogement(String.valueOf(fenAjouterBail.getComboBoxBiensLoc().getSelectedItem()));
			fenAjouterBail.setNouveauBailEnable(nbBail == 0);
			
		} catch (SQLException | IOException e) {
			fenAjouterBail.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		} 
    	
    	
    }

    /**
     * Gère l'action des boutons radio pour choisir entre
     * la création d'un nouveau bail et le rattachement à un bail existant.
     *
     * @param e l'événement d'action provenant du JRadioButton
     */
    private void actionPerformedRadio(ActionEvent e) {
        JRadioButton rdBtnActif = (JRadioButton) e.getSource();
        String rdBtnLibelle = rdBtnActif.getText();

        CardLayout cd = fenAjouterBail.getCardLayout();
        JPanel panelPrincipal = fenAjouterBail.getPanelAssocierBail();

        switch (rdBtnLibelle) {
            case "Créer un nouveau bail":
                verifierDispoNouveauBail();
                cd.show(panelPrincipal, "nouveauBail");
                break;
            case "Rattacher à un bail existant":
                cd.show(panelPrincipal, "bauxExistants");
            	fenAjouterBail.enableContinuer(true);
                break;
            default:
                break;
        }
    }

	public void gestionChangementLog(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED && combo.isVisible()) {
                verifierDispoNouveauBail();
            }
    });
	}
}
