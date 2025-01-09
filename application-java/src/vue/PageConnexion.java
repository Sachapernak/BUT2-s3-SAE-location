package vue;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import controleur.GestionPageConnexion;
import java.awt.Dimension;

public class PageConnexion extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Champs graphiques 
    private JLabel lblSatutText;
    private JLabel lblms1;
    private JLabel lblms2;
    private JLabel lblLatReqVal;
    private JLabel lblLatCoVal;
    
    private JTextField textFieldLien;
    private JTextField textFieldID;
    private JPasswordField passwordField;

    //  Contrôleur 
    private GestionPageConnexion gest;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PageConnexion frame = new PageConnexion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructeur de la fenêtre (Vue).
     */
    public PageConnexion() {
    	getContentPane().setPreferredSize(new Dimension(400, 300));
    	setMinimumSize(new Dimension(430, 330));
        
        // Instanciation du contrôleur
        this.gest = new GestionPageConnexion(this);

        getContentPane().setBounds(new Rectangle(0, 0, 800, 500));
        setBounds(100, 100, 400, 300);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        // ---- Panel du haut (vide) -------------------------------------------
        JPanel panelTop = new JPanel();
        getContentPane().add(panelTop, BorderLayout.NORTH);
        
        // ---- Panel du bas (boutons) -----------------------------------------
        JPanel panelBot = new JPanel();
        getContentPane().add(panelBot, BorderLayout.SOUTH);
        panelBot.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnQuitter = new JButton("Quitter");
        panelBot.add(btnQuitter);
        
        JButton btnRefresh = new JButton("Recharger");
        panelBot.add(btnRefresh);
        
        JButton btnConfirmer = new JButton("Confirmer");
        panelBot.add(btnConfirmer);
        
        // ---- Panel central (toute la grille) --------------------------------
        JPanel panelMid = new JPanel();
        getContentPane().add(panelMid, BorderLayout.CENTER);
        panelMid.setLayout(new GridLayout(3, 0, 0, 0));
        
        // ----------------------- G1 : Statut + Latences -----------------------
        JPanel panelG1 = new JPanel();
        panelMid.add(panelG1);
        panelG1.setLayout(new GridLayout(3, 0, 0, 0));
        
        // Statut
        JPanel panelG11 = new JPanel();
        panelG1.add(panelG11);
        panelG11.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblStatutTitre = new JLabel("Statut :");
        lblStatutTitre.setHorizontalAlignment(SwingConstants.RIGHT);
        panelG11.add(lblStatutTitre);
        
        lblSatutText = new JLabel("Tentative de connexion...");
        lblSatutText.setForeground(new Color(255, 128, 0));
        panelG11.add(lblSatutText);
        
        // Latence connexion
        JPanel panelG12 = new JPanel();
        panelG1.add(panelG12);
        panelG12.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblLatCoTitre = new JLabel("Latence connexion :");
        lblLatCoTitre.setHorizontalAlignment(SwingConstants.TRAILING);
        panelG12.add(lblLatCoTitre);
        
        JPanel panelLatCo = new JPanel();
        panelG12.add(panelLatCo);
        panelLatCo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 6));
        
        lblLatCoVal = new JLabel("Calcul...");
        lblLatCoVal.setForeground(new Color(255, 128, 0));
        panelLatCo.add(lblLatCoVal);
        
        lblms1 = new JLabel("ms");
        lblms1.setForeground(Color.BLACK);
        panelLatCo.add(lblms1);
        
        // Latence requête
        JPanel panelG13 = new JPanel();
        panelG1.add(panelG13);
        panelG13.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblLatReqTitre = new JLabel("Latence requete :");
        lblLatReqTitre.setHorizontalAlignment(SwingConstants.TRAILING);
        panelG13.add(lblLatReqTitre);
        
        JPanel panelLatReq = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelLatReq.getLayout();
        flowLayout.setVgap(6);
        flowLayout.setAlignment(FlowLayout.LEFT);
        panelG13.add(panelLatReq);
        
        lblLatReqVal = new JLabel("Calcul...");
        lblLatReqVal.setForeground(new Color(255, 128, 0));
        panelLatReq.add(lblLatReqVal);
        
        lblms2 = new JLabel("ms");
        lblms2.setForeground(Color.BLACK);
        panelLatReq.add(lblms2);
        
        // ----------------------- G2 : Titre + Lien ----------------------------
        JPanel panelG2 = new JPanel();
        panelMid.add(panelG2);
        panelG2.setLayout(new GridLayout(2, 0, 0, 0));
        
        //  Titre
        JPanel panelG21 = new JPanel();
        panelG2.add(panelG21);
        panelG21.setLayout(new BorderLayout(0, 0));
        
        JLabel lblBD = new JLabel("Modification des identifiants :");
        lblBD.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblBD.setHorizontalAlignment(SwingConstants.CENTER);
        panelG21.add(lblBD);
        
        //  Saisie lien
        JPanel panelG22 = new JPanel();
        panelG2.add(panelG22);
        panelG22.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblLien = new JLabel("Lien :              ");
        panelG22.add(lblLien);
        
        textFieldLien = new JTextField();
        textFieldLien.setColumns(30);
        panelG22.add(textFieldLien);
        
        // ----------------------- G3 : Saisie ID + MDP -------------------------
        JPanel panelG3 = new JPanel();
        panelMid.add(panelG3);
        panelG3.setLayout(new GridLayout(2, 0, 0, 0));
        
        // Saisie identifiant
        JPanel panelG31 = new JPanel();
        panelG3.add(panelG31);
        panelG31.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblID = new JLabel("Identifiant :     ");
        panelG31.add(lblID);
        
        textFieldID = new JTextField();
        textFieldID.setColumns(30);
        panelG31.add(textFieldID);
        
        // Saisie mot de passe
        JPanel panelG32 = new JPanel();
        panelG3.add(panelG32);
        panelG32.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblMDP = new JLabel("Mot de passe :");
        panelG32.add(lblMDP);
        
        passwordField = new JPasswordField();
        passwordField.setColumns(30);
        panelG32.add(passwordField);
        
        // On cache les "ms" par défaut
        lblms1.setVisible(false);
        lblms2.setVisible(false);
        
        // Actions du contrôleur 
        gest.gestionSourisQuitter(btnQuitter);
        gest.chargerInfoConnexion();
        gest.gestionSourisRecharger(btnRefresh);
        gest.gestionSourisConfirmer(btnConfirmer);

        gest.getInformationBD();
    }


    
    // Méthodes pour récupérer les valeurs saisies 
    public String getLien() {
        return textFieldLien.getText();
    }

    public String getIdentifiant() {
        return textFieldID.getText();
    }

    public char[] getMotDePasse() {
        return passwordField.getPassword();
    }

    // Méthodes pour mettre à jour l’affichage (labels) 
    public void setStatut(String statut, Color couleur) {
        lblSatutText.setText(statut);
        lblSatutText.setForeground(couleur);
    }

    public void setLatenceConnexion(String latence, Color couleur) {
        lblLatCoVal.setText(latence);
        lblLatCoVal.setForeground(couleur);
        lblms1.setVisible(true);
    }

    public void setLatenceRequete(String latence, Color couleur) {
        lblLatReqVal.setText(latence);
        lblLatReqVal.setForeground(couleur);
        lblms2.setVisible(true);
    }
    
    public void setLien(String lien) {
        textFieldLien.setText(lien);
    }

    public void setIdentifiant(String identifiant) {
        textFieldID.setText(identifiant);
    }


    // Méthodes utilitaires 
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur de lecture : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    
}
