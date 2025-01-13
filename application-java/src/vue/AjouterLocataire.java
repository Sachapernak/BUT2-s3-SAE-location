package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controleur.GestionAjouterLocataire;

/**
 * Fenêtre permettant l'ajout d'un nouveau locataire (étape 1/3). <br/>
 * Cette vue recueille toutes les informations personnelles et l'adresse
 * du futur locataire. <br/>
 * Les interactions sont gérées par le contrôleur {@link GestionAjouterLocataire}.
 */
public class AjouterLocataire extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // --- Panneau principal
    private JPanel contentPane;

    // --- Champs de texte pour les informations du locataire
    private JTextField textFieldIdLocataire;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldEmail;
    private JTextField textFieldDateNaissance;
    private JTextField textFieldLieuNaissance;
    private JTextField textFieldVille;
    private JTextField textFieldCodePostal;
    private JTextField textFieldComplement;
    private JTextField textFieldAdresse;
    private JTextField textFieldTel;
    private JTextField textFieldIdAdresse;

    // --- Contrôleur
    private final GestionAjouterLocataire gestionClic;

    /**
     * Constructeur principal de la fenêtre d'ajout de locataire.
     *
     * @param vueAfficherLocataires la vue {@link AfficherLocatairesActuels} 
     *                              dans laquelle ce formulaire d'ajout est intégré.
     */
    public AjouterLocataire(AfficherLocatairesActuels vueAfficherLocataires) {
        
        this.gestionClic = new GestionAjouterLocataire(this, vueAfficherLocataires);

        // Configuration de la fenêtre
        setBounds(0, 0, 620, 420);
        setClosable(true); // Permettre de fermer la fenêtre interne

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // --- Panneau nord avec le titre
        JPanel panelNord = new JPanel();
        contentPane.add(panelNord, BorderLayout.NORTH);

        JLabel labelTitreAjoutLoc = new JLabel("Ajouter un nouveau locataire 1/3");
        labelTitreAjoutLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        panelNord.add(labelTitreAjoutLoc);

        // --- Panneau central divisé en deux colonnes (gauche/droite)
        JPanel panelCentre = new JPanel();
        contentPane.add(panelCentre, BorderLayout.CENTER);
        panelCentre.setLayout(new GridLayout(1, 2, 0, 0));

        // --- Panneau gauche (informations locataire)
        JPanel panelGauche = new JPanel();
        panelCentre.add(panelGauche, BorderLayout.WEST);
        panelGauche.setLayout(new GridLayout(8, 2, 0, 0));

        // Lignes vides pour espacement
        JPanel panelVideHautGauche = new JPanel();
        panelGauche.add(panelVideHautGauche);

        JPanel panelVideHautGauche2 = new JPanel();
        panelGauche.add(panelVideHautGauche2);

        // ---------------------
        // Ligne : Identifiant locataire
        // ---------------------
        JPanel panelLabelId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelId);

        JLabel labelIdLoc = new JLabel("Identifiant* :");
        labelIdLoc.setHorizontalAlignment(SwingConstants.RIGHT);
        panelLabelId.add(labelIdLoc);

        JPanel panelTextId = new JPanel();
        panelGauche.add(panelTextId);

        textFieldIdLocataire = new JTextField();
        textFieldIdLocataire.setColumns(10);
        panelTextId.add(textFieldIdLocataire);

        // ---------------------
        // Ligne : Nom
        // ---------------------
        JPanel panelLabelNom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelNom);

        JLabel labelNom = new JLabel("Nom* : ");
        labelNom.setHorizontalAlignment(SwingConstants.RIGHT);
        panelLabelNom.add(labelNom);

        JPanel panelTextNom = new JPanel();
        panelGauche.add(panelTextNom);

        textFieldNom = new JTextField();
        textFieldNom.setColumns(10);
        panelTextNom.add(textFieldNom);

        // ---------------------
        // Ligne : Prénom
        // ---------------------
        JPanel panelLabelPrenom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelPrenom);

        JLabel labelPrenom = new JLabel("Prénom* :");
        labelPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
        panelLabelPrenom.add(labelPrenom);

        JPanel panelTextPrenom = new JPanel();
        panelGauche.add(panelTextPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setColumns(10);
        panelTextPrenom.add(textFieldPrenom);

        // ---------------------
        // Ligne : Téléphone
        // ---------------------
        JPanel panelLabelTel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelTel);

        JLabel labelTel = new JLabel("Téléphone :");
        labelTel.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabelTel.add(labelTel);

        JPanel panelTextTel = new JPanel();
        panelGauche.add(panelTextTel);

        textFieldTel = new JTextField();
        textFieldTel.setColumns(10);
        panelTextTel.add(textFieldTel);

        // ---------------------
        // Ligne : Email
        // ---------------------
        JPanel panelLabelEmail = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelEmail);

        JLabel labelEmail = new JLabel("E-mail :");
        panelLabelEmail.add(labelEmail);

        JPanel panelTextEmail = new JPanel();
        panelGauche.add(panelTextEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        panelTextEmail.add(textFieldEmail);

        // ---------------------
        // Ligne : Date de naissance
        // ---------------------
        JPanel panelLabelDateNaissance = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelDateNaissance);

        JLabel labelDateNaissance = new JLabel("Date de naissance* :");
        panelLabelDateNaissance.add(labelDateNaissance);

        JPanel panelTextDateNaissance = new JPanel();
        panelGauche.add(panelTextDateNaissance);

        textFieldDateNaissance = new JTextField();
        textFieldDateNaissance.setColumns(10);
        panelTextDateNaissance.add(textFieldDateNaissance);

        // ---------------------
        // Ligne : Lieu de naissance
        // ---------------------
        JPanel panelLabelLieuNaissance = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelGauche.add(panelLabelLieuNaissance);

        JLabel labelLieuNaissance = new JLabel("Lieu de naissance  :");
        panelLabelLieuNaissance.add(labelLieuNaissance);

        JPanel panelTextLieuNaissance = new JPanel();
        panelGauche.add(panelTextLieuNaissance);

        textFieldLieuNaissance = new JTextField();
        textFieldLieuNaissance.setColumns(10);
        panelTextLieuNaissance.add(textFieldLieuNaissance);

        // --- Panneau droite (adresse)
        JPanel panelDroite = new JPanel(new BorderLayout(0, 0));
        panelCentre.add(panelDroite, BorderLayout.EAST);

        JPanel panelAdresse = new JPanel();
        panelAdresse.setBorder(new TitledBorder(null, "Adresse : ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelDroite.add(panelAdresse, BorderLayout.CENTER);
        panelAdresse.setLayout(new GridLayout(7, 2, 0, 0));

        // Lignes vides en haut
        JPanel panelVideHautGauche2Droite = new JPanel();
        panelAdresse.add(panelVideHautGauche2Droite);
        JPanel panelVideHautDroite2Droite = new JPanel();
        panelAdresse.add(panelVideHautDroite2Droite);

        // ---------------------
        // Ligne : Identifiant d'adresse
        // ---------------------
        JPanel panelLabelIdAdresse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAdresse.add(panelLabelIdAdresse);

        JLabel labelIdAdresse = new JLabel("Identifiant :");
        labelIdAdresse.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabelIdAdresse.add(labelIdAdresse);

        JPanel panelTextIdAdresse = new JPanel();
        panelAdresse.add(panelTextIdAdresse);

        textFieldIdAdresse = new JTextField();
        textFieldIdAdresse.setColumns(10);
        panelTextIdAdresse.add(textFieldIdAdresse);

        // ---------------------
        // Ligne : Adresse (num, voie, etc.)
        // ---------------------
        JPanel panelLabelAdresse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAdresse.add(panelLabelAdresse);

        JLabel labelAdresse = new JLabel("Adresse : ");
        panelLabelAdresse.add(labelAdresse);

        JPanel panelTextAdresse = new JPanel();
        panelAdresse.add(panelTextAdresse);

        textFieldAdresse = new JTextField();
        textFieldAdresse.setColumns(10);
        panelTextAdresse.add(textFieldAdresse);

        // ---------------------
        // Ligne : Complément d'adresse
        // ---------------------
        JPanel panelLabelComplement = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAdresse.add(panelLabelComplement);

        JLabel labelComplement = new JLabel("Complément :");
        labelComplement.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabelComplement.add(labelComplement);

        JPanel panelTextComplement = new JPanel();
        panelAdresse.add(panelTextComplement);

        textFieldComplement = new JTextField();
        textFieldComplement.setColumns(10);
        panelTextComplement.add(textFieldComplement);

        // ---------------------
        // Ligne : Code postal
        // ---------------------
        JPanel panelLabelCodePostal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAdresse.add(panelLabelCodePostal);

        JLabel labelCodePostal = new JLabel("Code postal : ");
        panelLabelCodePostal.add(labelCodePostal);

        JPanel panelTextCodePostal = new JPanel();
        panelAdresse.add(panelTextCodePostal);

        textFieldCodePostal = new JTextField();
        textFieldCodePostal.setColumns(10);
        panelTextCodePostal.add(textFieldCodePostal);

        // ---------------------
        // Ligne : Ville
        // ---------------------
        JPanel panelLabelVille = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAdresse.add(panelLabelVille);

        JLabel labelVille = new JLabel("Ville :");
        panelLabelVille.add(labelVille);

        JPanel panelTextVille = new JPanel();
        panelAdresse.add(panelTextVille);

        textFieldVille = new JTextField();
        textFieldVille.setColumns(10);
        panelTextVille.add(textFieldVille);

        // Lignes vides en bas
        JPanel panelVideBas = new JPanel();
        panelDroite.add(panelVideBas, BorderLayout.SOUTH);
        panelVideBas.setLayout(new GridLayout(4, 0, 0, 0));
        panelVideBas.add(new JPanel());

        JPanel panelVideHaut = new JPanel(new GridLayout(4, 0, 0, 0));
        panelDroite.add(panelVideHaut, BorderLayout.NORTH);
        panelVideHaut.add(new JPanel());

        // --- Panneau sud avec les boutons
        JPanel panelSud = new JPanel();
        contentPane.add(panelSud, BorderLayout.SOUTH);

        JButton buttonContinuer = new JButton("Continuer");
        buttonContinuer.addActionListener(this.gestionClic);
        panelSud.add(buttonContinuer);

        JButton buttonAnnuler = new JButton("Annuler");
        buttonAnnuler.addActionListener(this.gestionClic);
        panelSud.add(buttonAnnuler);
    }

    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     *
     * @param message le message à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * @return la liste des champs obligatoires (identifiant, nom, prénom, date de naissance).
     */
    public List<String> getChampsObligatoires() {
        List<String> res = new ArrayList<>();
        res.add(getTextFieldIdLocataire());
        res.add(getTextFieldNom());
        res.add(getTextFieldPrenom());
        res.add(getTextFieldDateNaissance());
        return res;
    }

    /**
     * @return la liste des champs obligatoires pour l'adresse (idAdresse, adresse, codePostal, ville).
     */
    public List<String> getChampsObligatoiresAdresse() {
        List<String> res = new ArrayList<>();
        res.add(getTextFieldIdAdresse());
        res.add(getTextFieldAdresse());
        res.add(getTextFieldCodePostal());
        res.add(getTextFieldVille());
        return res;
    }

    // -----------------------------------------------------------------------
    // Getters pour le contrôleur
    // -----------------------------------------------------------------------

    public String getTextFieldIdLocataire() {
        return textFieldIdLocataire.getText();
    }

    public String getTextFieldNom() {
        return textFieldNom.getText();
    }

    public String getTextFieldPrenom() {
        return textFieldPrenom.getText();
    }

    public String getTextFieldEmail() {
        return textFieldEmail.getText();
    }

    public String getTextFieldDateNaissance() {
        return textFieldDateNaissance.getText();
    }

    public String getTextFieldLieuNaissance() {
        return textFieldLieuNaissance.getText();
    }

    public String getTextFieldVille() {
        return textFieldVille.getText();
    }

    public String getTextFieldCodePostal() {
        return textFieldCodePostal.getText();
    }

    public String getTextComplement() {
        return textFieldComplement.getText();
    }

    public String getTextFieldAdresse() {
        return textFieldAdresse.getText();
    }

    public String getTextFieldTel() {
        return textFieldTel.getText();
    }

    public String getTextFieldIdAdresse() {
        return textFieldIdAdresse.getText();
    }

    // -----------------------------------------------------------------------
    // Setters pour le contrôleur
    // -----------------------------------------------------------------------

    public void setTextFieldIdLocataire(String texte) {
        this.textFieldIdLocataire.setText(texte);
    }

    public void setTextFieldNom(String texte) {
        this.textFieldNom.setText(texte);
    }

    public void setTextFieldPrenom(String texte) {
        this.textFieldPrenom.setText(texte);
    }

    public void setTextFieldEmail(String texte) {
        this.textFieldEmail.setText(texte);
    }

    public void setTextFieldDateNaissance(String texte) {
        this.textFieldDateNaissance.setText(texte);
    }

    public void setTextFieldLieuNaissance(String texte) {
        this.textFieldLieuNaissance.setText(texte);
    }

    public void setTextFieldVille(String texte) {
        this.textFieldVille.setText(texte);
    }

    public void setTextFieldCodePostal(String texte) {
        this.textFieldCodePostal.setText(texte);
    }

    public void setTextComplement(String texte) {
        this.textFieldComplement.setText(texte);
    }

    public void setTextFieldAdresse(String texte) {
        this.textFieldAdresse.setText(texte);
    }

    public void setTextFieldTel(String texte) {
        this.textFieldTel.setText(texte);
    }

    public void setTextFieldIdAdresse(String texte) {
        this.textFieldIdAdresse.setText(texte);
    }
}
