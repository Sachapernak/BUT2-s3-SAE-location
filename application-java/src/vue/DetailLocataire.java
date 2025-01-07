package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionDetailLocataire;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import modele.Locataire;
import modele.dao.DaoLocataire;

/**
 * Fenêtre de détail d'un locataire.
 */
public class DetailLocataire extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    // --- Ajouts : Attributs texte + locataire ---
    private JLabel lbltxtID;
    private JLabel lbltxtNom;
    private JLabel lbltxtPrenom;
    private JLabel lbltxtDateNaissance;
    private JLabel lbltxtLieu;
    private JLabel lbltxtTelephone;
    private JLabel lbltxtEmail;
    private JTextArea txtrAdresse;   // Champ texte pour l'adresse

    // Ajout : on garde le locataire en attribut pour pouvoir manipuler ses infos
    private Locataire locataire;
    
    private GestionDetailLocataire gest;

    private JList<String> list;
    
    DefaultListModel listModel;

    /**
     * Launch the application (pour test).
     */
    public static void main(String[] args) {
        try {
        	System.out.println("Chargement de la fenetre...");
            Locataire l = new DaoLocataire().findById("LOC001");
            DetailLocataire dialog = new DetailLocataire(l);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée la boîte de dialogue.
     */
    public DetailLocataire(Locataire locataire) {
        this.locataire = locataire;
        gest = new GestionDetailLocataire();
        
        setPreferredSize(new Dimension(500, 0));
        setBounds(100, 100, 530, 342);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[] {130, 0, 180, 0, 190, 0, 2};
        gblContentPanel.rowHeights = new int[]{35, 230, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);
        
        // -- Panel Titre + ID ---
        
        JPanel topPanel = new JPanel();
        GridBagConstraints gbcTopPanel = new GridBagConstraints();
        gbcTopPanel.gridwidth = 5;
        gbcTopPanel.insets = new Insets(0, 0, 5, 5);
        gbcTopPanel.fill = GridBagConstraints.BOTH;
        gbcTopPanel.gridx = 0;
        gbcTopPanel.gridy = 0;
        contentPanel.add(topPanel, gbcTopPanel);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblTitre = new JLabel("Locataire");
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 14));
        topPanel.add(lblTitre);
        
        lbltxtID = new JLabel("[Identifiant]");
        lbltxtID.setFont(new Font("Tahoma", Font.BOLD, 14));
        topPanel.add(lbltxtID);
            
        // -- Panel Libellés (Nom, Prenom, etc.) ---
        
        JPanel leftMidPanel = new JPanel();
        GridBagConstraints gbcLeftMidPanel = new GridBagConstraints();
        gbcLeftMidPanel.anchor = GridBagConstraints.NORTH;
        gbcLeftMidPanel.insets = new Insets(0, 0, 0, 5);
        gbcLeftMidPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcLeftMidPanel.gridx = 0;
        gbcLeftMidPanel.gridy = 1;
        contentPanel.add(leftMidPanel, gbcLeftMidPanel);
        GridBagLayout gblLeftMidPanel = new GridBagLayout();
        gblLeftMidPanel.columnWidths = new int[]{0, 0};
        gblLeftMidPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblLeftMidPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblLeftMidPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        leftMidPanel.setLayout(gblLeftMidPanel);
            
        // just for spacing
        GridBagConstraints gbcVerticalStrut1 = new GridBagConstraints();
        gbcVerticalStrut1.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut1.gridx = 0;
        gbcVerticalStrut1.gridy = 0;
        leftMidPanel.add(Box.createVerticalStrut(10), gbcVerticalStrut1);
    
        JLabel lblNom = new JLabel("Nom :");
        lblNom.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblNom = new GridBagConstraints();
        gbcLblNom.anchor = GridBagConstraints.EAST;
        gbcLblNom.insets = new Insets(0, 0, 5, 0);
        gbcLblNom.gridx = 0;
        gbcLblNom.gridy = 1;
        leftMidPanel.add(lblNom, gbcLblNom);
            
        JLabel lblPrenom = new JLabel("Prénom :");
        lblPrenom.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblPrenom = new GridBagConstraints();
        gbcLblPrenom.insets = new Insets(0, 0, 5, 0);
        gbcLblPrenom.anchor = GridBagConstraints.EAST;
        gbcLblPrenom.gridx = 0;
        gbcLblPrenom.gridy = 2;
        leftMidPanel.add(lblPrenom, gbcLblPrenom);
            
        JLabel lblDateNaissance = new JLabel("Date de naissance :");
        lblDateNaissance.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblDateNaissance = new GridBagConstraints();
        gbcLblDateNaissance.insets = new Insets(0, 0, 5, 0);
        gbcLblDateNaissance.anchor = GridBagConstraints.EAST;
        gbcLblDateNaissance.gridx = 0;
        gbcLblDateNaissance.gridy = 3;
        leftMidPanel.add(lblDateNaissance, gbcLblDateNaissance);
            
        JLabel lblLieu = new JLabel("Lieu :");
        lblLieu.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblLieu = new GridBagConstraints();
        gbcLblLieu.anchor = GridBagConstraints.EAST;
        gbcLblLieu.insets = new Insets(0, 0, 5, 0);
        gbcLblLieu.gridx = 0;
        gbcLblLieu.gridy = 4;
        leftMidPanel.add(lblLieu, gbcLblLieu);
            
        // spacing
        GridBagConstraints gbcVerticalStrut2 = new GridBagConstraints();
        gbcVerticalStrut2.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut2.gridx = 0;
        gbcVerticalStrut2.gridy = 5;
        leftMidPanel.add(Box.createVerticalStrut(10), gbcVerticalStrut2);
            
        JLabel lblTel = new JLabel("Téléphone :");
        lblTel.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblTel = new GridBagConstraints();
        gbcLblTel.insets = new Insets(0, 0, 5, 0);
        gbcLblTel.anchor = GridBagConstraints.EAST;
        gbcLblTel.gridx = 0;
        gbcLblTel.gridy = 6;
        leftMidPanel.add(lblTel, gbcLblTel);
    
        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblEmail = new GridBagConstraints();
        gbcLblEmail.insets = new Insets(0, 0, 5, 0);
        gbcLblEmail.anchor = GridBagConstraints.EAST;
        gbcLblEmail.gridx = 0;
        gbcLblEmail.gridy = 7;
        leftMidPanel.add(lblEmail, gbcLblEmail);
    
        // spacing
        GridBagConstraints gbcVerticalStrut3 = new GridBagConstraints();
        gbcVerticalStrut3.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut3.gridx = 0;
        gbcVerticalStrut3.gridy = 8;
        leftMidPanel.add(Box.createVerticalStrut(5), gbcVerticalStrut3);
            
        JLabel lblAdresse = new JLabel("Adresse :");
        lblAdresse.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbcLblAdresse = new GridBagConstraints();
        gbcLblAdresse.anchor = GridBagConstraints.EAST;
        gbcLblAdresse.gridx = 0;
        gbcLblAdresse.gridy = 9;
        leftMidPanel.add(lblAdresse, gbcLblAdresse);
        
        // -- Petit strut horizontal --
        
        GridBagConstraints gbcHorizontalStrut = new GridBagConstraints();
        gbcHorizontalStrut.insets = new Insets(0, 0, 0, 5);
        gbcHorizontalStrut.gridx = 1;
        gbcHorizontalStrut.gridy = 1;
        contentPanel.add(Box.createHorizontalStrut(3), gbcHorizontalStrut);
        
        // -- Panel Valeurs (chargement...) ---
        
        JPanel midPanel = new JPanel();
        GridBagConstraints gbcMidPanel = new GridBagConstraints();
        gbcMidPanel.anchor = GridBagConstraints.NORTHWEST;
        gbcMidPanel.insets = new Insets(0, 0, 0, 5);
        gbcMidPanel.gridx = 2;
        gbcMidPanel.gridy = 1;
        contentPanel.add(midPanel, gbcMidPanel);
        
        GridBagLayout gblMidPanel = new GridBagLayout();
        gblMidPanel.columnWidths = new int[]{201, 0};
        gblMidPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0};
        gblMidPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gblMidPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        midPanel.setLayout(gblMidPanel);
    
        GridBagConstraints gbcVerticalStrut4 = new GridBagConstraints();
        gbcVerticalStrut4.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut4.gridx = 0;
        gbcVerticalStrut4.gridy = 0;
        midPanel.add(Box.createVerticalStrut(10), gbcVerticalStrut4);
    
        lbltxtNom = new JLabel("Chargement nom...");
        GridBagConstraints gbcLbltxtNom = new GridBagConstraints();
        gbcLbltxtNom.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtNom.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtNom.gridx = 0;
        gbcLbltxtNom.gridy = 1;
        midPanel.add(lbltxtNom, gbcLbltxtNom);
            
        lbltxtPrenom = new JLabel("Chargement prénom...");
        GridBagConstraints gbcLbltxtPrenom = new GridBagConstraints();
        gbcLbltxtPrenom.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtPrenom.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtPrenom.gridx = 0;
        gbcLbltxtPrenom.gridy = 2;
        midPanel.add(lbltxtPrenom, gbcLbltxtPrenom);
    
        lbltxtDateNaissance = new JLabel("Chargement date...");
        GridBagConstraints gbcLbltxtDateNaissance = new GridBagConstraints();
        gbcLbltxtDateNaissance.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtDateNaissance.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtDateNaissance.gridx = 0;
        gbcLbltxtDateNaissance.gridy = 3;
        midPanel.add(lbltxtDateNaissance, gbcLbltxtDateNaissance);
    
        // On saute l'indice 4 (attention) pour éviter l'écrasement
        // s'il y a un usage d'élément invisible. Sinon, c'est possible
        // de mettre le label lieu juste ici.
    
        lbltxtLieu = new JLabel("Chargement lieu...");
        GridBagConstraints gbcLbltxtLieu = new GridBagConstraints();
        gbcLbltxtLieu.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtLieu.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtLieu.gridx = 0;
        gbcLbltxtLieu.gridy = 5;
        midPanel.add(lbltxtLieu, gbcLbltxtLieu);
    
        GridBagConstraints gbcVerticalStrut5 = new GridBagConstraints();
        gbcVerticalStrut5.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut5.gridx = 0;
        gbcVerticalStrut5.gridy = 6;
        midPanel.add(Box.createVerticalStrut(10), gbcVerticalStrut5);
    
        lbltxtTelephone = new JLabel("Chargement téléphone...");
        GridBagConstraints gbcLbltxtTelephone = new GridBagConstraints();
        gbcLbltxtTelephone.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtTelephone.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtTelephone.gridx = 0;
        gbcLbltxtTelephone.gridy = 7;
        midPanel.add(lbltxtTelephone, gbcLbltxtTelephone);
    
        lbltxtEmail = new JLabel("Chargement email...");
        GridBagConstraints gbcLbltxtEmail = new GridBagConstraints();
        gbcLbltxtEmail.fill = GridBagConstraints.HORIZONTAL;
        gbcLbltxtEmail.insets = new Insets(0, 0, 5, 0);
        gbcLbltxtEmail.gridx = 0;
        gbcLbltxtEmail.gridy = 8;
        midPanel.add(lbltxtEmail, gbcLbltxtEmail);
    
        GridBagConstraints gbcVerticalStrut6 = new GridBagConstraints();
        gbcVerticalStrut6.insets = new Insets(0, 0, 5, 0);
        gbcVerticalStrut6.gridx = 0;
        gbcVerticalStrut6.gridy = 9;
        midPanel.add(Box.createVerticalStrut(5), gbcVerticalStrut6);
    
        // On réutilise le JTextArea existant comme champ d'adresse
        txtrAdresse = new JTextArea();
        txtrAdresse.setWrapStyleWord(true);
        txtrAdresse.setEditable(false);
        txtrAdresse.setText("Chargement de l'adresse...");
        txtrAdresse.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtrAdresse.setLineWrap(true);
        txtrAdresse.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        txtrAdresse.setRows(3);
        txtrAdresse.setMaximumSize(new Dimension(200, 2147483647));
        GridBagConstraints gbcTxtrAdresse = new GridBagConstraints();
        gbcTxtrAdresse.fill = GridBagConstraints.BOTH;
        gbcTxtrAdresse.gridx = 0;
        gbcTxtrAdresse.gridy = 10;
        midPanel.add(txtrAdresse, gbcTxtrAdresse);
            
        GridBagConstraints gbcHorizontalStrut2 = new GridBagConstraints();
        gbcHorizontalStrut2.insets = new Insets(0, 0, 0, 5);
        gbcHorizontalStrut2.gridx = 3;
        gbcHorizontalStrut2.gridy = 1;
        contentPanel.add(Box.createHorizontalStrut(10), gbcHorizontalStrut2);
    
        // -- Panel Baux associés ---
    
        JPanel rightMidPanel = new JPanel();
        GridBagConstraints gbcRightMidPanel = new GridBagConstraints();
        gbcRightMidPanel.insets = new Insets(0, 0, 0, 5);
        gbcRightMidPanel.fill = GridBagConstraints.BOTH;
        gbcRightMidPanel.gridx = 4;
        gbcRightMidPanel.gridy = 1;
        contentPanel.add(rightMidPanel, gbcRightMidPanel);
    
        GridBagLayout gblRightMidPanel = new GridBagLayout();
        gblRightMidPanel.columnWidths = new int[]{170, 0};
        gblRightMidPanel.rowHeights = new int[]{0, 190, 0};
        gblRightMidPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblRightMidPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        rightMidPanel.setLayout(gblRightMidPanel);
            
        JLabel lblBaux = new JLabel("Baux associés :");
        GridBagConstraints gbcLblBaux = new GridBagConstraints();
        gbcLblBaux.ipady = 2;
        gbcLblBaux.insets = new Insets(0, 0, 5, 0);
        gbcLblBaux.gridx = 0;
        gbcLblBaux.gridy = 0;
        rightMidPanel.add(lblBaux, gbcLblBaux);
    
        list = new JList<>();
        
        listModel = new DefaultListModel();
        
        listModel.addElement("Chargement...");
        
        list.setModel(listModel);
        GridBagConstraints gbcList = new GridBagConstraints();
        gbcList.fill = GridBagConstraints.BOTH;
        gbcList.gridx = 0;
        gbcList.gridy = 1;
        rightMidPanel.add(list, gbcList);
            
        GridBagConstraints gbcHorizontalStrut3 = new GridBagConstraints();
        gbcHorizontalStrut3.gridx = 5;
        gbcHorizontalStrut3.gridy = 1;
        contentPanel.add(Box.createHorizontalStrut(5), gbcHorizontalStrut3);
        
        // -- Panel du bas : Bouton Cancel par ex. ---
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
                    
        gest.chargerLocataireDansFenetre(this, locataire);
    }
    
    // Méthode pour charger une liste de baux dans le JList
    public void chargerBaux(List<String> baux) {
        listModel.clear();
        if(baux != null) {
            for(String bail : baux) {
                listModel.addElement(bail);
            }
        }
    }
    

    // 2. Getters et Setters pour le locataire + champs
    public Locataire getLocataire() {
        return locataire;
    }

    public void setTxtID(String text) {
        this.lbltxtID.setText(text);
    }

    public void setTxtNom(String text) {
        this.lbltxtNom.setText(text);
    }

    public void setTxtPrenom(String text) {
        this.lbltxtPrenom.setText(text);
    }

    public void setTxtDateNaissance(String text) {
        this.lbltxtDateNaissance.setText(text);
    }

    public void setTxtLieuNaissance(String text) {
        this.lbltxtLieu.setText(text);
    }

    public void setTxtTelephone(String text) {
        this.lbltxtTelephone.setText(text);
    }

    public void setTxtEmail(String text) {
        this.lbltxtEmail.setText(text);
    }

    public void setTxtAdresse(String text) {
        this.txtrAdresse.setText(text);
    }
}
