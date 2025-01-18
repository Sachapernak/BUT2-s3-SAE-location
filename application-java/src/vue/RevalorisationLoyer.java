package vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import controleur.GestionRevalorisationLoyer;
import java.awt.Color;

/**
 * Fenêtre interne pour la revalorisation des loyers.
 * Permet de sélectionner un bien locatif, d'afficher son ancien loyer,
 * son loyer maximum autorisé et de proposer une nouvelle valeur.
 */
public class RevalorisationLoyer extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private JLabel lblAncienLoyer;
    private JTextField textFieldLoyerActuel;
    private JTextField textFieldLoyerMax;
    private JButton btnICC;
    private JButton btnRevaloriser;
    private JButton btnQuitter;
    private JSpinner spinnerNouveauLoyer;
    private JComboBox<String> comboBoxBienLoc;
    
    private String idLog;
    private GestionRevalorisationLoyer gest;
    private JButton btnHistorique;
    private JLabel lblLoyerAugmentable;


    public RevalorisationLoyer() {
        this("");
    }
    
    /**
     * Crée la fenêtre de revalorisation avec un identifiant de logement initial.
     * @param idLog l'identifiant du logement initialement sélectionné
     */
    public RevalorisationLoyer(String idLog) {
        this.gest = new GestionRevalorisationLoyer(this);
        this.idLog = idLog;
        
        setBounds(100, 100, 490, 230);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{30, 90, 97, 55, 50, 80, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
        // Titre de la fenêtre
        JLabel lblRevaloriserLoyers = new JLabel("Revaloriser un loyer");
        GridBagConstraints gbcLblRevaloriserLoyers = new GridBagConstraints();
        gbcLblRevaloriserLoyers.gridwidth = 6;
        gbcLblRevaloriserLoyers.insets = new Insets(0, 0, 5, 0);
        gbcLblRevaloriserLoyers.gridx = 0;
        gbcLblRevaloriserLoyers.gridy = 0;
        getContentPane().add(lblRevaloriserLoyers, gbcLblRevaloriserLoyers);
        
        JLabel lblBienLoc = new JLabel("Bien locatif :");
        GridBagConstraints gbcLblBienLoc = new GridBagConstraints();
        gbcLblBienLoc.insets = new Insets(0, 0, 5, 5);
        gbcLblBienLoc.anchor = GridBagConstraints.EAST;
        gbcLblBienLoc.gridx = 1;
        gbcLblBienLoc.gridy = 1;
        getContentPane().add(lblBienLoc, gbcLblBienLoc);
        
        comboBoxBienLoc = new JComboBox<>();
        GridBagConstraints gbcComboBoxBienLoc = new GridBagConstraints();
        gbcComboBoxBienLoc.gridwidth = 3;
        gbcComboBoxBienLoc.insets = new Insets(0, 0, 5, 5);
        gbcComboBoxBienLoc.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxBienLoc.gridx = 2;
        gbcComboBoxBienLoc.gridy = 1;
        getContentPane().add(comboBoxBienLoc, gbcComboBoxBienLoc);
        
        lblAncienLoyer = new JLabel("Ancien loyer :");
        GridBagConstraints gbcLblAncienLoyer = new GridBagConstraints();
        gbcLblAncienLoyer.anchor = GridBagConstraints.EAST;
        gbcLblAncienLoyer.insets = new Insets(0, 0, 5, 5);
        gbcLblAncienLoyer.gridx = 1;
        gbcLblAncienLoyer.gridy = 2;
        getContentPane().add(lblAncienLoyer, gbcLblAncienLoyer);
        
        textFieldLoyerActuel = new JTextField();
        textFieldLoyerActuel.setEditable(false);
        GridBagConstraints gbcTxtFieldLoyerActuel = new GridBagConstraints();
        gbcTxtFieldLoyerActuel.gridwidth = 2;
        gbcTxtFieldLoyerActuel.insets = new Insets(0, 0, 5, 5);
        gbcTxtFieldLoyerActuel.fill = GridBagConstraints.HORIZONTAL;
        gbcTxtFieldLoyerActuel.gridx = 2;
        gbcTxtFieldLoyerActuel.gridy = 2;
        getContentPane().add(textFieldLoyerActuel, gbcTxtFieldLoyerActuel);
        textFieldLoyerActuel.setColumns(10);
        
        JLabel lblEuro = new JLabel("€");
        GridBagConstraints gbcLblEuro = new GridBagConstraints();
        gbcLblEuro.anchor = GridBagConstraints.WEST;
        gbcLblEuro.insets = new Insets(0, 0, 5, 5);
        gbcLblEuro.gridx = 4;
        gbcLblEuro.gridy = 2;
        getContentPane().add(lblEuro, gbcLblEuro);
        
        JLabel lblMax = new JLabel("Loyer maximum :");
        GridBagConstraints gbcLblMax = new GridBagConstraints();
        gbcLblMax.anchor = GridBagConstraints.EAST;
        gbcLblMax.insets = new Insets(0, 0, 5, 5);
        gbcLblMax.gridx = 1;
        gbcLblMax.gridy = 3;
        getContentPane().add(lblMax, gbcLblMax);
        
        textFieldLoyerMax = new JTextField();
        textFieldLoyerMax.setEditable(false);
        GridBagConstraints gbcTxtFieldLoyerMax = new GridBagConstraints();
        gbcTxtFieldLoyerMax.gridwidth = 2;
        gbcTxtFieldLoyerMax.insets = new Insets(0, 0, 5, 5);
        gbcTxtFieldLoyerMax.fill = GridBagConstraints.HORIZONTAL;
        gbcTxtFieldLoyerMax.gridx = 2;
        gbcTxtFieldLoyerMax.gridy = 3;
        getContentPane().add(textFieldLoyerMax, gbcTxtFieldLoyerMax);
        textFieldLoyerMax.setColumns(10);
        
        JLabel lblEuro2 = new JLabel("€");
        GridBagConstraints gbcLblEuro2 = new GridBagConstraints();
        gbcLblEuro2.anchor = GridBagConstraints.WEST;
        gbcLblEuro2.insets = new Insets(0, 0, 5, 5);
        gbcLblEuro2.gridx = 4;
        gbcLblEuro2.gridy = 3;
        getContentPane().add(lblEuro2, gbcLblEuro2);
        
        JLabel lblNouvLoyer = new JLabel("Nouveau Loyer :");
        GridBagConstraints gbcLblNouvLoyer = new GridBagConstraints();
        gbcLblNouvLoyer.insets = new Insets(0, 0, 5, 5);
        gbcLblNouvLoyer.gridx = 1;
        gbcLblNouvLoyer.gridy = 5;
        getContentPane().add(lblNouvLoyer, gbcLblNouvLoyer);
        
        spinnerNouveauLoyer = new JSpinner();
        spinnerNouveauLoyer.setModel(new SpinnerNumberModel(0.0, 0.0, 0.0, 50.0));
        GridBagConstraints gbcSpinnerNouveauLoyer = new GridBagConstraints();
        gbcSpinnerNouveauLoyer.gridwidth = 2;
        gbcSpinnerNouveauLoyer.fill = GridBagConstraints.HORIZONTAL;
        gbcSpinnerNouveauLoyer.insets = new Insets(0, 0, 5, 5);
        gbcSpinnerNouveauLoyer.gridx = 2;
        gbcSpinnerNouveauLoyer.gridy = 5;
        getContentPane().add(spinnerNouveauLoyer, gbcSpinnerNouveauLoyer);
        
        lblLoyerAugmentable = new JLabel("Attention, le loyer n'est actuellement pas augmentable");
        lblLoyerAugmentable.setForeground(new Color(255, 0, 0));
        GridBagConstraints gbc_lblLoyerAugmentable = new GridBagConstraints();
        gbc_lblLoyerAugmentable.gridwidth = 6;
        gbc_lblLoyerAugmentable.insets = new Insets(0, 0, 5, 5);
        gbc_lblLoyerAugmentable.gridx = 0;
        gbc_lblLoyerAugmentable.gridy = 6;
        getContentPane().add(lblLoyerAugmentable, gbc_lblLoyerAugmentable);
        
        btnICC = new JButton("Voir ICC");
        GridBagConstraints gbcBtnICC = new GridBagConstraints();
        gbcBtnICC.gridwidth = 2;
        gbcBtnICC.insets = new Insets(0, 0, 0, 5);
        gbcBtnICC.gridx = 0;
        gbcBtnICC.gridy = 7;
        getContentPane().add(btnICC, gbcBtnICC);
        
        btnHistorique = new JButton("Voir l'historique");
        GridBagConstraints gbcBtnHistorique = new GridBagConstraints();
        gbcBtnHistorique.anchor = GridBagConstraints.WEST;
        gbcBtnHistorique.insets = new Insets(0, 0, 0, 5);
        gbcBtnHistorique.gridx = 2;
        gbcBtnHistorique.gridy = 7;
        getContentPane().add(btnHistorique, gbcBtnHistorique);
        
        btnRevaloriser = new JButton("Revaloriser");
        GridBagConstraints gbcBtnRevaloriser = new GridBagConstraints();
        gbcBtnRevaloriser.anchor = GridBagConstraints.WEST;
        gbcBtnRevaloriser.gridwidth = 2;
        gbcBtnRevaloriser.insets = new Insets(0, 0, 0, 5);
        gbcBtnRevaloriser.gridx = 3;
        gbcBtnRevaloriser.gridy = 7;
        getContentPane().add(btnRevaloriser, gbcBtnRevaloriser);
        
        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.anchor = GridBagConstraints.WEST;
        gbcBtnQuitter.gridx = 5;
        gbcBtnQuitter.gridy = 7;
        getContentPane().add(btnQuitter, gbcBtnQuitter);
        
        setAugmentInterdite(false);
        
        // Initialiser la gestion des boutons et autres interactions
        gest.gestionBtnVoirICC(btnICC);
        gest.gestionBtnQuitter(btnQuitter);
        gest.gestionBtnRevaloriser(btnRevaloriser);
        gest.gestionBtnHistorique(btnHistorique);
        
        // Charger la liste des biens locatifs dans la ComboBox de manière asynchrone
        gest.chargerComboBoxLogement();
        
        // Ajout du listener pour recharger 
        // les informations de loyer lors du changement de sélection
        // (après le chargement pour pas charger deux fois les infos
        gest.gestionActionComboLog(comboBoxBienLoc);
    }
    
    /**
     * Retourne l'identifiant du logement courant.
     * @return idLog
     */
    public String getIdLog() {
        return this.idLog;
    }
    
    public void setAugmentInterdite(boolean interdit) {
    	lblLoyerAugmentable.setVisible(interdit);
    }
    
    /**
     * Met à jour la ComboBox avec la liste des identifiants de biens locatifs.
     * @param listIDLog liste des identifiants des biens locatifs
     */
    public void setListLog(List<String> listIDLog) {
        comboBoxBienLoc.setModel(new DefaultComboBoxModel<>(listIDLog.toArray(new String[0])));
    }
    
    /**
     * Sélectionne dans la ComboBox l'élément correspondant à la valeur fournie.
     * @param val la valeur à sélectionner dans la ComboBox
     */
    public void setItemInCombo(String val) {
        for (int i = 0; i < comboBoxBienLoc.getItemCount(); i++) {
            String item = comboBoxBienLoc.getItemAt(i);
            if (item.equalsIgnoreCase(val)) {
                comboBoxBienLoc.setSelectedIndex(i);
                break;
            }
        }
    }
    
    /**
     * Retourne l'identifiant du bien locatif actuellement sélectionné dans la ComboBox.
     * @return l'identifiant sélectionné
     */
    public String getSelectedIdLog() {
        return String.valueOf(comboBoxBienLoc.getSelectedItem());
    }
    
    /**
     * Met à jour le champ de texte pour afficher l'ancien loyer.
     * @param val valeur de l'ancien loyer
     */
    public void setAncienLoyer(String val) {
        textFieldLoyerActuel.setText(val);
    }
    
    /**
     * Met à jour l'affichage du loyer maximum et configure le spinner pour la nouvelle valeur de loyer.
     * @param loyerMax le loyer maximum autorisé
     */
    public void setLoyerMax(String loyerMax) {
        textFieldLoyerMax.setText(loyerMax);
        
        double loyerMaxDouble = Double.parseDouble(loyerMax);
        if (loyerMaxDouble == 0.0) {
        	spinnerNouveauLoyer.setModel(new SpinnerNumberModel(0, 0.0, 999999.99, 50.0));
        	
        } else {
            spinnerNouveauLoyer.setModel(new SpinnerNumberModel(loyerMaxDouble, 0.0, loyerMaxDouble, 50.0));
        }
        

    }
    
    /**
     * Retourne la valeur saisie pour le nouveau loyer dans le spinner.
     * @return la valeur du nouveau loyer
     */
    public String getValeurNouveauLoyer() {
        return String.valueOf(spinnerNouveauLoyer.getValue());
    }
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * @param message message d'erreur à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
