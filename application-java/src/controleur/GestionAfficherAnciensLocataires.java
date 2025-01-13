package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ItemEvent;
import modele.Bail;
import modele.BienLocatif;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoContracter;
import modele.dao.DaoLocataire;
import vue.AfficherAnciensLocataires;

/**
 * Contrôleur gérant l'affichage des anciens locataires. <br/>
 * Il écoute les interactions sur la vue {@link AfficherAnciensLocataires} :
 * <ul>
 *     <li>Sélection d'un bâtiment</li>
 *     <li>Selection d'un bien locatif</li>
 *     <li>Bouton de retour</li>
 * </ul>
 * En fonction des actions, le contrôleur remplit les combos (bâtiments, biens locatifs)
 * et la table des anciens locataires.
 */
public class GestionAfficherAnciensLocataires implements ActionListener {

    /** Vue associée à ce contrôleur. */
    private final AfficherAnciensLocataires fenAfficherAnciensLocataires;
    /**
     * Constructeur principal.
     *
     * @param aal la vue {@link AfficherAnciensLocataires}
     */
    public GestionAfficherAnciensLocataires(AfficherAnciensLocataires aal) {
        this.fenAfficherAnciensLocataires = aal;
        new DaoLocataire();
    }

    /**
     * Initialise le listener sur la comboBox "Bâtiment". <br/>
     * Lorsqu'un bâtiment est sélectionné, on remplit la comboBox des biens locatifs
     * correspondants et on met à jour la table des anciens locataires pour ce bâtiment.
     */
    public void gestionActionComboBatiment() {
        JComboBox<String> comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();
        comboBoxBatiment.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String batimentSelectionne = (String) comboBoxBatiment.getSelectedItem();
                remplirComboBoxBienLocatif(batimentSelectionne);
                remplirTableAnciensLocatairesParBatiment(batimentSelectionne);
            }
        });
    }

    /**
     * Initialise le listener sur la comboBox "Bien Locatif". <br/>
     * Lorsqu'un bien locatif est sélectionné, on met à jour la table des anciens locataires
     * pour ce bien précis. Si "Tous" est sélectionné, on affiche tous les anciens locataires
     * du bâtiment.
     */
    public void gestionActionComboLogement() {
        JComboBox<String> comboBoxBienLocatif = this.fenAfficherAnciensLocataires.getComboBoxBienLocatif();
        JComboBox<String> comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();

        comboBoxBienLocatif.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String bienSelectionne = (String) comboBoxBienLocatif.getSelectedItem();
                String batimentSelectionne = (String) comboBoxBatiment.getSelectedItem();

                // Affiche les anciens locataires d'un bien spécifique
                remplirTableAnciensLocataires(bienSelectionne);

                // Si "Tous" est sélectionné, on affiche tous les anciens locataires du bâtiment
                if ("Tous".equals(bienSelectionne)) {
                    remplirTableAnciensLocatairesParBatiment(batimentSelectionne);
                }
            }
        });
    }


    /**
     * Gère les actions sur les boutons de la vue {@link AfficherAnciensLocataires}.
     *
     * @param e l'événement d'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();

        if ("Retour".equals(btnLibelle)) {
                this.fenAfficherAnciensLocataires.dispose();
        }
               
        
    }

    /**
     * Remplit la comboBox des bâtiments en allant chercher les données en base de données. <br/>
     * Les éléments sont les identifiants des bâtiments.
     */
    public void remplirComboBoxBatiment() {
        JComboBox<String> comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();

        try {
            // Récupération des bâtiments et conversion en tableau
            List<String> batiments = new DaoBatiment().findAll().stream()
                    .map(b -> b.getIdBat())
                    .toList();

            comboBoxBatiment.setModel(new DefaultComboBoxModel<>(batiments.toArray(new String[0])));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remplit la comboBox des biens locatifs (d'un bâtiment) en base de données. <br/>
     * Ajoute l'élément "Tous" en première position.
     *
     * @param batiment l'identifiant du bâtiment sélectionné
     */
    public void remplirComboBoxBienLocatif(String batiment) {
        JComboBox<String> comboBoxBienLocatif = this.fenAfficherAnciensLocataires.getComboBoxBienLocatif();

        try {
            // Récupération des biens locatifs pour ce bâtiment
            List<String> logements = new DaoBienLocatif().findByIdBat(batiment).stream()
                    .map(b -> b.getIdentifiantLogement())
                    .collect(Collectors.toList());

            // Ajout de "Tous" au début de la liste
            logements.add(0, "Tous");

            comboBoxBienLocatif.setModel(new DefaultComboBoxModel<>(logements.toArray(new String[0])));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remplit la table des anciens locataires pour un bien donné. <br/>
     * Un locataire est considéré ancien s'il possède un {@link Contracter} dont la date de sortie n'est pas nulle.
     *
     * @param bienLocatif l'identifiant du bien locatif sélectionné
     */
    public void remplirTableAnciensLocataires(String bienLocatif) {
        JTable tableLocataires = this.fenAfficherAnciensLocataires.getTableAnciensLocataires();
        UtilitaireTable.viderTable(tableLocataires);

        try {
            DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
            model.setRowCount(0);

            // Recherche des contrats pour ce bien
            List<Contracter> contrats = new DaoContracter().findByIdLogement(bienLocatif);

            // On n'affiche que les locataires dont la date de sortie est renseignée
            for (Contracter contrat : contrats) {
                if (contrat.getDateSortie() != null) {
                    Locataire locataire = contrat.getLocataire();
                    model.addRow(new String[] {
                            locataire.getIdLocataire(),
                            locataire.getNom(),
                            locataire.getPrenom(),
                            contrat.getDateEntree(),
                            contrat.getDateSortie()
                    });
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remplit la table des anciens locataires en fonction d'un bâtiment. <br/>
     * Récupère tous les biens locatifs du bâtiment, tous les baux associés à ces biens,
     * et les contrats associés à chaque bail. Pour chaque contrat, si la date de sortie
     * n'est pas nulle, le locataire est affiché en ancien locataire.
     *
     * @param batiment l'identifiant du bâtiment sélectionné
     */
    public void remplirTableAnciensLocatairesParBatiment(String batiment) {
        JTable tableLocataires = this.fenAfficherAnciensLocataires.getTableAnciensLocataires();
        UtilitaireTable.viderTable(tableLocataires);

        try {
            DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
            model.setRowCount(0);

            // Récupération de tous les logements pour ce bâtiment
            List<BienLocatif> logements = new DaoBienLocatif().findByIdBat(batiment);

            // Pour chaque logement, on récupère la liste des baux
            for (BienLocatif logement : logements) {
                List<Bail> baux = new DaoBail().findByIdLogement(logement.getIdentifiantLogement());

                // Pour chaque bail, on récupère tous les contrats
                for (Bail bail : baux) {
                    List<Contracter> contrats = new DaoContracter().getContrats(bail);

                    // On n'affiche que les locataires dont la date de sortie est renseignée
                    for (Contracter contrat : contrats) {
                        if (contrat.getDateSortie() != null) {
                            Locataire locataire = contrat.getLocataire();
                            model.addRow(new String[] {
                                    locataire.getIdLocataire(),
                                    locataire.getNom(),
                                    locataire.getPrenom(),
                                    contrat.getDateEntree(),
                                    contrat.getDateSortie()
                            });
                        }
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
