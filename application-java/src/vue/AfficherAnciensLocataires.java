package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;

import controleur.GestionAfficherAnciensLocataires;

/**
 * Fenêtre interne pour afficher les anciens locataires.
 * Cette vue est associée à un contrôleur qui gère la logique et les interactions.
 */
public class AfficherAnciensLocataires extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Composants de l'interface utilisateur
    private final JComboBox<String> comboBoxBatiment;
    private final JComboBox<String> comboBoxBienLocatif;
    private final JTable tableAnciensLocataires;
    private final transient GestionAfficherAnciensLocataires gestionTab;

    /**
     * Constructeur de la fenêtre interne.
     * Initialise les composants et configure les actions via le contrôleur.
     */
    public AfficherAnciensLocataires() {
        // Initialisation du contrôleur avec cette vue
        this.gestionTab = new GestionAfficherAnciensLocataires(this);

        setBounds(25, 25, 670, 490);
        getContentPane().setLayout(null);

        // Titre de la fenêtre
        JLabel lblTitre = new JLabel("Anciens locataires");
        lblTitre.setForeground(new Color(70, 130, 180));
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitre.setBounds(0, 10, 658, 44);
        getContentPane().add(lblTitre);

        // Label et ComboBox pour le bâtiment
        JLabel lblBatiment = new JLabel("Batiment :");
        lblBatiment.setBounds(65, 68, 66, 13);
        getContentPane().add(lblBatiment);

        comboBoxBatiment = new JComboBox<>();
        lblBatiment.setLabelFor(comboBoxBatiment);
        comboBoxBatiment.setBounds(141, 64, 136, 21);
        comboBoxBatiment.setModel(new DefaultComboBoxModel<>(new String[] {"Chargement..."}));
        getContentPane().add(comboBoxBatiment);

        // Label et ComboBox pour le bien locatif
        JLabel lblBienLocatif = new JLabel("Bien locatif :");
        lblBienLocatif.setBounds(368, 68, 86, 13);
        getContentPane().add(lblBienLocatif);

        comboBoxBienLocatif = new JComboBox<>();
        lblBienLocatif.setLabelFor(comboBoxBienLocatif);
        comboBoxBienLocatif.setBounds(450, 64, 136, 21);
        comboBoxBienLocatif.setModel(new DefaultComboBoxModel<>(new String[] {"Chargement..."}));
        getContentPane().add(comboBoxBienLocatif);

        // Panneau pour la table des locataires
        JPanel panelLocataires = new JPanel();
        panelLocataires.setBorder(new TitledBorder(null, "Les locataires", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelLocataires.setBounds(10, 103, 638, 242);
        panelLocataires.setLayout(new BorderLayout(0, 0));
        getContentPane().add(panelLocataires);

        // Zone de défilement contenant la table
        JScrollPane scrollPaneLocataires = new JScrollPane();
        scrollPaneLocataires.setBounds(21, 10, 581, 170);

        // Initialisation de la table avec un modèle par défaut
        tableAnciensLocataires = new JTable();
        tableAnciensLocataires.setModel(new DefaultTableModel(
            new Object[][] {
                // Données initiales vides ou exemples
                {null, null, null, null, null},
                // ...
            },
            new String[] {
                "Identifiant", "Nom", "Prénom", "Date d'entrée", "Date de sortie"
            }
        ));
        scrollPaneLocataires.setViewportView(tableAnciensLocataires);

        // Ajout de la zone de défilement au centre du panneau des locataires
        JPanel panelCentre = new JPanel(null);
        panelCentre.add(scrollPaneLocataires);
        panelLocataires.add(panelCentre, BorderLayout.CENTER);

        // Panneau et bouton de retour
        JPanel panelRetour = new JPanel();
        panelRetour.setBounds(559, 380, 89, 31);
        getContentPane().add(panelRetour);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(gestionTab);
        panelRetour.add(btnRetour);

        // Initialisations complémentaires via le contrôleur
        gestionTab.remplirComboBoxBatiment();
        gestionTab.gestionActionComboBatiment();
        gestionTab.remplirComboBoxBienLocatif(String.valueOf(comboBoxBatiment.getSelectedItem()));
        gestionTab.gestionActionComboLogement();
        gestionTab.remplirTableAnciensLocatairesParBatiment(String.valueOf(comboBoxBatiment.getSelectedItem()));
    }

    // Accesseurs pour le contrôleur

    public JComboBox<String> getComboBoxBatiment() {
        return comboBoxBatiment;
    }

    public JComboBox<String> getComboBoxBienLocatif() {
        return comboBoxBienLocatif;
    }

    public JTable getTableAnciensLocataires() {
        return tableAnciensLocataires;
    }
}
