package vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
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
import rapport.RapportRegularisation;

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
    private BigDecimal nouvelleValeur;
    private GestionRevalorisationCharges gest;
    private JButton btnHistorique;
    private JLabel lblDate;
    private JTextField textFieldDate;
    private JTextField textFieldValeurConseillee;
    private JLabel lblValeurConseillee;
    private JLabel lblEuro2;
    private JLabel lblBienLoc;


    public RevalorisationCharge() {
        this("", null, null);
    }
    
    /**
     * Crée la fenêtre de revalorisation avec un identifiant de logement initial.
     * @param idLog l'identifiant du logement initialement sélectionné
     */
    public RevalorisationCharge(String idBail, BigDecimal nouvelleValeur, RapportRegularisation rap) {
    	
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
        GridBagConstraints gbcLblCharges = new GridBagConstraints();
        gbcLblCharges.gridwidth = 6;
        gbcLblCharges.insets = new Insets(0, 0, 5, 0);
        gbcLblCharges.gridx = 0;
        gbcLblCharges.gridy = 0;
        getContentPane().add(lblCharges, gbcLblCharges);
        
        lblBienLoc = new JLabel("Bail :");
        GridBagConstraints gbcLblBienLoc = new GridBagConstraints();
        gbcLblBienLoc.insets = new Insets(0, 0, 5, 5);
        gbcLblBienLoc.anchor = GridBagConstraints.EAST;
        gbcLblBienLoc.gridx = 1;
        gbcLblBienLoc.gridy = 1;
        getContentPane().add(lblBienLoc, gbcLblBienLoc);
        
        
        comboBoxBail = new JComboBox<>();
        GridBagConstraints gbc_comboBoxBail = new GridBagConstraints();
        gbc_comboBoxBail.gridwidth = 3;
        gbc_comboBoxBail.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxBail.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxBail.gridx = 2;
        gbc_comboBoxBail.gridy = 1;
        getContentPane().add(comboBoxBail, gbc_comboBoxBail);

        lblAnciennesCharges = new JLabel("Anciennes provisions :");
        GridBagConstraints gbcLblAnciennesCharges = new GridBagConstraints();
        gbcLblAnciennesCharges.anchor = GridBagConstraints.EAST;
        gbcLblAnciennesCharges.insets = new Insets(0, 0, 5, 5);
        gbcLblAnciennesCharges.gridx = 1;
        gbcLblAnciennesCharges.gridy = 2;
        getContentPane().add(lblAnciennesCharges, gbcLblAnciennesCharges);
        
        textFieldChargeAnciennes = new JTextField();
        textFieldChargeAnciennes.setEditable(false);
        GridBagConstraints gbcTextFieldChargeAnciennes = new GridBagConstraints();
        gbcTextFieldChargeAnciennes.gridwidth = 2;
        gbcTextFieldChargeAnciennes.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldChargeAnciennes.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldChargeAnciennes.gridx = 2;
        gbcTextFieldChargeAnciennes.gridy = 2;
        getContentPane().add(textFieldChargeAnciennes, gbcTextFieldChargeAnciennes);
        textFieldChargeAnciennes.setColumns(10);
        
        JLabel lblEuro = new JLabel("€");
        GridBagConstraints gbcLblEuro = new GridBagConstraints();
        gbcLblEuro.anchor = GridBagConstraints.WEST;
        gbcLblEuro.insets = new Insets(0, 0, 5, 5);
        gbcLblEuro.gridx = 4;
        gbcLblEuro.gridy = 2;
        getContentPane().add(lblEuro, gbcLblEuro);
        
        lblValeurConseillee = new JLabel("Valeur conseillée : ");
        GridBagConstraints gbcLblValeurConseillee = new GridBagConstraints();
        gbcLblValeurConseillee.insets = new Insets(0, 0, 5, 5);
        gbcLblValeurConseillee.anchor = GridBagConstraints.EAST;
        gbcLblValeurConseillee.gridx = 1;
        gbcLblValeurConseillee.gridy = 3;
        getContentPane().add(lblValeurConseillee, gbcLblValeurConseillee);
        
        textFieldValeurConseillee = new JTextField();
        textFieldValeurConseillee.setEnabled(false);
        textFieldValeurConseillee.setEditable(false);
        GridBagConstraints gbcTextFieldValeurConseillee = new GridBagConstraints();
        gbcTextFieldValeurConseillee.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldValeurConseillee.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldValeurConseillee.gridx = 2;
        gbcTextFieldValeurConseillee.gridy = 3;
        getContentPane().add(textFieldValeurConseillee, gbcTextFieldValeurConseillee);
        textFieldValeurConseillee.setColumns(10);
        
        lblEuro2 = new JLabel("€");
        GridBagConstraints gbclblEuro2 = new GridBagConstraints();
        gbclblEuro2.insets = new Insets(0, 0, 5, 5);
        gbclblEuro2.gridx = 3;
        gbclblEuro2.gridy = 3;
        getContentPane().add(lblEuro2, gbclblEuro2);
        
        JLabel lblNouvCharges = new JLabel("Nouvelles provisions :");
        GridBagConstraints gbcLblNouvCharges = new GridBagConstraints();
        gbcLblNouvCharges.anchor = GridBagConstraints.EAST;
        gbcLblNouvCharges.insets = new Insets(0, 0, 5, 5);
        gbcLblNouvCharges.gridx = 1;
        gbcLblNouvCharges.gridy = 5;
        getContentPane().add(lblNouvCharges, gbcLblNouvCharges);
        
        spinnerNouvellesCharges = new JSpinner();
        spinnerNouvellesCharges.setModel(new SpinnerNumberModel(0.0, 0.0, 9999999.0, 50.0));
        GridBagConstraints gbcSpinnerNouvellesCharges = new GridBagConstraints();
        gbcSpinnerNouvellesCharges.gridwidth = 2;
        gbcSpinnerNouvellesCharges.fill = GridBagConstraints.HORIZONTAL;
        gbcSpinnerNouvellesCharges.insets = new Insets(0, 0, 5, 5);
        gbcSpinnerNouvellesCharges.gridx = 2;
        gbcSpinnerNouvellesCharges.gridy = 5;
        getContentPane().add(spinnerNouvellesCharges, gbcSpinnerNouvellesCharges);
        
        lblDate = new JLabel("Date d'effet :");
        GridBagConstraints gbcLblDate = new GridBagConstraints();
        gbcLblDate.anchor = GridBagConstraints.NORTHEAST;
        gbcLblDate.insets = new Insets(0, 0, 5, 5);
        gbcLblDate.gridx = 1;
        gbcLblDate.gridy = 6;
        getContentPane().add(lblDate, gbcLblDate);
        
        textFieldDate = new JTextField();
        GridBagConstraints gbcTextFieldDate = new GridBagConstraints();
        gbcTextFieldDate.anchor = GridBagConstraints.NORTH;
        gbcTextFieldDate.gridwidth = 2;
        gbcTextFieldDate.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldDate.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldDate.gridx = 2;
        gbcTextFieldDate.gridy = 6;
        getContentPane().add(textFieldDate, gbcTextFieldDate);
        textFieldDate.setColumns(10);
        
        btnHistorique = new JButton("Voir l'historique");
        GridBagConstraints gbcBtnHistorique = new GridBagConstraints();
        gbcBtnHistorique.anchor = GridBagConstraints.WEST;
        gbcBtnHistorique.insets = new Insets(0, 0, 0, 5);
        gbcBtnHistorique.gridx = 1;
        gbcBtnHistorique.gridy = 7;
        getContentPane().add(btnHistorique, gbcBtnHistorique);

        
        btnModifier = new JButton("Modifier");
        GridBagConstraints gbcBtnModifier = new GridBagConstraints();
        gbcBtnModifier.anchor = GridBagConstraints.WEST;
        gbcBtnModifier.gridwidth = 2;
        gbcBtnModifier.insets = new Insets(0, 0, 0, 5);
        gbcBtnModifier.gridx = 3;
        gbcBtnModifier.gridy = 7;
        getContentPane().add(btnModifier, gbcBtnModifier);
        
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
    public BigDecimal getNouvelleValeur() {
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
    
    public void setVisibleComboBoxBail(boolean bool) {
    	this.comboBoxBail.setVisible(bool);
    	this.lblBienLoc.setVisible(bool);
    }
    
    public void setVisibleChampsValeurConseillee(boolean bool) {
    	this.lblValeurConseillee.setVisible(bool);
    	this.textFieldValeurConseillee.setVisible(bool);
    	this.lblEuro2.setVisible(bool);
    }

}
