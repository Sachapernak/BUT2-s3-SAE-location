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

import controleur.GestionRevalorisationCharges;
import controleur.GestionRevalorisationLoyer;
import java.awt.Color;

/**
 * Fenêtre interne pour la revalorisation des loyers.
 * Permet de sélectionner un bien locatif, d'afficher son ancien loyer,
 * son loyer maximum autorisé et de proposer une nouvelle valeur.
 */
public class RevalorisationCharge extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private JLabel lblAnciennesCharges;
    private JTextField textFieldChargeAnciennes;
    private JButton btnModifier;
    private JButton btnQuitter;
    private JSpinner spinnerNouvellesCharges;
    private JComboBox<String> comboBoxBail;
    
    private String idBail;
    private String nouvelleValeur;
    private GestionRevalorisationCharges gest;
    private JButton btnHistorique;
    private JLabel lblDate;
    private JTextField textFieldDate;
    private JTextField textFieldValeurConseillee;
    private JLabel lblValeurConseillee;
    private JLabel lblEuro_1;
    private JLabel lblBienLoc;


    public RevalorisationCharge() {
        this("", "");
    }
    
    /**
     * Crée la fenêtre de revalorisation avec un identifiant de logement initial.
     * @param idLog l'identifiant du logement initialement sélectionné
     */
    public RevalorisationCharge(String idBail, String nouvelleValeur) {
    	
        this.gest = new GestionRevalorisationCharges(this);
        this.idBail= idBail;
        this.nouvelleValeur = nouvelleValeur;
        
        setBounds(100, 100, 490, 230);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{30, 90, 97, 55, 50, 80, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 9, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
        // Titre de la fenêtre
        JLabel lblCharges = new JLabel("Modifier les provisions pour charges");
        GridBagConstraints gbc_lblCharges = new GridBagConstraints();
        gbc_lblCharges.gridwidth = 6;
        gbc_lblCharges.insets = new Insets(0, 0, 5, 0);
        gbc_lblCharges.gridx = 0;
        gbc_lblCharges.gridy = 0;
        getContentPane().add(lblCharges, gbc_lblCharges);
        
        lblBienLoc = new JLabel("Bail :");
        GridBagConstraints gbc_lblBienLoc = new GridBagConstraints();
        gbc_lblBienLoc.insets = new Insets(0, 0, 5, 5);
        gbc_lblBienLoc.anchor = GridBagConstraints.EAST;
        gbc_lblBienLoc.gridx = 1;
        gbc_lblBienLoc.gridy = 1;
        getContentPane().add(lblBienLoc, gbc_lblBienLoc);
        
        
        comboBoxBail = new JComboBox<>();
        GridBagConstraints gbc_comboBoxBail = new GridBagConstraints();
        gbc_comboBoxBail.gridwidth = 3;
        gbc_comboBoxBail.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxBail.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxBail.gridx = 2;
        gbc_comboBoxBail.gridy = 1;
        getContentPane().add(comboBoxBail, gbc_comboBoxBail);

        lblAnciennesCharges = new JLabel("Anciennes provisions :");
        GridBagConstraints gbc_lblAnciennesCharges = new GridBagConstraints();
        gbc_lblAnciennesCharges.anchor = GridBagConstraints.EAST;
        gbc_lblAnciennesCharges.insets = new Insets(0, 0, 5, 5);
        gbc_lblAnciennesCharges.gridx = 1;
        gbc_lblAnciennesCharges.gridy = 2;
        getContentPane().add(lblAnciennesCharges, gbc_lblAnciennesCharges);
        
        textFieldChargeAnciennes = new JTextField();
        textFieldChargeAnciennes.setEditable(false);
        GridBagConstraints gbc_textFieldChargeAnciennes = new GridBagConstraints();
        gbc_textFieldChargeAnciennes.gridwidth = 2;
        gbc_textFieldChargeAnciennes.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldChargeAnciennes.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldChargeAnciennes.gridx = 2;
        gbc_textFieldChargeAnciennes.gridy = 2;
        getContentPane().add(textFieldChargeAnciennes, gbc_textFieldChargeAnciennes);
        textFieldChargeAnciennes.setColumns(10);
        
        JLabel lblEuro = new JLabel("€");
        GridBagConstraints gbcLblEuro = new GridBagConstraints();
        gbcLblEuro.anchor = GridBagConstraints.WEST;
        gbcLblEuro.insets = new Insets(0, 0, 5, 5);
        gbcLblEuro.gridx = 4;
        gbcLblEuro.gridy = 2;
        getContentPane().add(lblEuro, gbcLblEuro);
        
        lblValeurConseillee = new JLabel("Valeur conseillée : ");
        lblValeurConseillee.setEnabled(false);
        GridBagConstraints gbc_lblValeurConseillee = new GridBagConstraints();
        gbc_lblValeurConseillee.insets = new Insets(0, 0, 5, 5);
        gbc_lblValeurConseillee.anchor = GridBagConstraints.EAST;
        gbc_lblValeurConseillee.gridx = 1;
        gbc_lblValeurConseillee.gridy = 3;
        getContentPane().add(lblValeurConseillee, gbc_lblValeurConseillee);
        
        textFieldValeurConseillee = new JTextField();
        textFieldValeurConseillee.setEnabled(false);
        textFieldValeurConseillee.setEditable(false);
        GridBagConstraints gbc_textFieldValeurConseillee = new GridBagConstraints();
        gbc_textFieldValeurConseillee.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValeurConseillee.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldValeurConseillee.gridx = 2;
        gbc_textFieldValeurConseillee.gridy = 3;
        getContentPane().add(textFieldValeurConseillee, gbc_textFieldValeurConseillee);
        textFieldValeurConseillee.setColumns(10);
        
        lblEuro_1 = new JLabel("€");
        lblEuro_1.setEnabled(false);
        GridBagConstraints gbc_lblEuro_1 = new GridBagConstraints();
        gbc_lblEuro_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblEuro_1.gridx = 3;
        gbc_lblEuro_1.gridy = 3;
        getContentPane().add(lblEuro_1, gbc_lblEuro_1);
        
        JLabel lblNouvCharges = new JLabel("Nouvelles provisions :");
        GridBagConstraints gbc_lblNouvCharges = new GridBagConstraints();
        gbc_lblNouvCharges.anchor = GridBagConstraints.EAST;
        gbc_lblNouvCharges.insets = new Insets(0, 0, 5, 5);
        gbc_lblNouvCharges.gridx = 1;
        gbc_lblNouvCharges.gridy = 5;
        getContentPane().add(lblNouvCharges, gbc_lblNouvCharges);
        
        spinnerNouvellesCharges = new JSpinner();
        spinnerNouvellesCharges.setModel(new SpinnerNumberModel(0.0, 0.0, 9999999.0, 50.0));
        GridBagConstraints gbc_spinnerNouvellesCharges = new GridBagConstraints();
        gbc_spinnerNouvellesCharges.gridwidth = 2;
        gbc_spinnerNouvellesCharges.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerNouvellesCharges.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerNouvellesCharges.gridx = 2;
        gbc_spinnerNouvellesCharges.gridy = 5;
        getContentPane().add(spinnerNouvellesCharges, gbc_spinnerNouvellesCharges);
        
        lblDate = new JLabel("Date d'effet :");
        GridBagConstraints gbc_lblDate = new GridBagConstraints();
        gbc_lblDate.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblDate.insets = new Insets(0, 0, 5, 5);
        gbc_lblDate.gridx = 1;
        gbc_lblDate.gridy = 6;
        getContentPane().add(lblDate, gbc_lblDate);
        
        textFieldDate = new JTextField();
        GridBagConstraints gbc_textFieldDate = new GridBagConstraints();
        gbc_textFieldDate.anchor = GridBagConstraints.NORTH;
        gbc_textFieldDate.gridwidth = 2;
        gbc_textFieldDate.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldDate.gridx = 2;
        gbc_textFieldDate.gridy = 6;
        getContentPane().add(textFieldDate, gbc_textFieldDate);
        textFieldDate.setColumns(10);
        
        btnHistorique = new JButton("Voir l'historique");
        GridBagConstraints gbcBtnHistorique = new GridBagConstraints();
        gbcBtnHistorique.anchor = GridBagConstraints.WEST;
        gbcBtnHistorique.insets = new Insets(0, 0, 0, 5);
        gbcBtnHistorique.gridx = 1;
        gbcBtnHistorique.gridy = 7;
        getContentPane().add(btnHistorique, gbcBtnHistorique);

        
        btnModifier = new JButton("Modifier");
        GridBagConstraints gbc_btnModifier = new GridBagConstraints();
        gbc_btnModifier.anchor = GridBagConstraints.WEST;
        gbc_btnModifier.gridwidth = 2;
        gbc_btnModifier.insets = new Insets(0, 0, 0, 5);
        gbc_btnModifier.gridx = 3;
        gbc_btnModifier.gridy = 7;
        getContentPane().add(btnModifier, gbc_btnModifier);
        
        btnQuitter = new JButton("Quitter");
        GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
        gbcBtnQuitter.anchor = GridBagConstraints.WEST;
        gbcBtnQuitter.gridx = 5;
        gbcBtnQuitter.gridy = 7;
        getContentPane().add(btnQuitter, gbcBtnQuitter);
        
        gest.gestionBtnHistorique(btnHistorique);
        gest.gestionBtnQuitter(btnQuitter);
        gest.gestionBtnRevaloriser(btnModifier);
        
        // Charger la liste des biens locatifs dans la ComboBox de manière asynchrone
        gest.chargerComboBoxBail();
        
        // Ajout du listener pour recharger 
        // les informations de loyer lors du changement de sélection
        // (après le chargement pour pas charger deux fois les infos
        gest.gestionActionComboLog(comboBoxBail);
        
        gest.gestionAffichageChamps();

    }
    
    /**
     * Retourne l'identifiant du logement courant.
     * @return idLog
     */
    public String getIdLog() {
        return this.idBail;
    }
    
    /**
     * Retourne la nouvelle valeur passée en paramètres.
     * @return nouvelleValeur
     */
    public String getNouvelleValeur() {
        return this.nouvelleValeur;
    }
    
    
    /**
     * Met à jour la ComboBox avec la liste des identifiants de biens locatifs.
     * @param listIDLog liste des identifiants des biens locatifs
     */
    public void setListBail(List<String> listIDLog) {
        comboBoxBail.setModel(new DefaultComboBoxModel<>(listIDLog.toArray(new String[0])));
    }
    
    /**
     * Sélectionne dans la ComboBox l'élément correspondant à la valeur fournie.
     * @param val la valeur à sélectionner dans la ComboBox
     */
    public void setItemInCombo(String val) {
        for (int i = 0; i < comboBoxBail.getItemCount(); i++) {
            String item = comboBoxBail.getItemAt(i);
            if (item.equalsIgnoreCase(val)) {
                comboBoxBail.setSelectedIndex(i);
                break;
            }
        }
    }
    
    /**
     * Retourne l'identifiant du bien locatif actuellement sélectionné dans la ComboBox.
     * @return l'identifiant sélectionné
     */
    public String getSelectedIdBail() {
        return String.valueOf(comboBoxBail.getSelectedItem());
    }
    
    /**
     * Met à jour le champ de texte pour afficher l'ancien loyer.
     * @param val valeur de l'ancien loyer
     */
    public void setAnciennesCharges(String val) {
        textFieldChargeAnciennes.setText(val);
    }
    
    /**
     * Retourne la valeur saisie pour le nouveau loyer dans le spinner.
     * @return la valeur du nouveau loyer
     */
    public String getValeurNouvelleCharges() {
        return String.valueOf(spinnerNouvellesCharges.getValue());
    }
    
    public String getDate() {
    	return textFieldDate.getText();
    }
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * @param message message d'erreur à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    public void setEnabledComboBoxBail(boolean bool) {
    	this.comboBoxBail.setEnabled(bool);
    	this.lblBienLoc.setEnabled(bool);
    }
    
    public void setEnabledChampsValeurConseillee(boolean bool) {
    	this.lblValeurConseillee.setEnabled(bool);
    	this.textFieldValeurConseillee.setEnabled(bool);
    	this.lblEuro_1.setEnabled(bool);
    }

}
