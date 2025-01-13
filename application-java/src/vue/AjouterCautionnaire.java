package vue;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controleur.GestionAjouterCautionnaire;

import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.SystemColor;
import javax.swing.JSeparator;

/**
 * Vue permettant l'ajout d'un cautionnaire lors de la création d'un nouveau
 * locataire et de son bail. Possibilité de poursuivre sans cautionnaire.
 */
public class AjouterCautionnaire extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private GestionAjouterCautionnaire gestionFen;
    private AjouterBail fenPrecedente;

    // Champs de formulaire
    private JTextField textFieldMontant;
    private JTextField textFieldLienActeCaution;
    private JTextField textFieldPrenom;
    private  JTextField textFieldNomOuOrga;
    private  JTextField textFieldDateDebut;
    private JTextField textFieldAdr;
    private JTextField textFieldComplement;
    private JTextField textFieldCodePostal;
    private JTextField textFieldVille;
    private JTextPane   textPaneDescription;
    private JTextField  textFieldIdentifiantCautionnaire;
    private JTextField  textFieldIdentifiantAdresse;
    
    // Label affichant le montant du loyer
    private JLabel lblMontantLoyer;

    /**
     * Récupère le texte saisi dans le champ "Montant".
     */
    public String getTextFieldMontant() {
        return textFieldMontant.getText();
    }

    /**
     * Récupère la valeur du label indiquant le loyer actuel.
     */
    public String getLblMontantLoyer() {
        return lblMontantLoyer.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Lien vers l'acte de caution".
     */
    public String getTextFieldLienActeCaution() {
        return textFieldLienActeCaution.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Identifiant du cautionnaire".
     */
    public String getTextFieldIdentifiantCautionnaire() {
        return textFieldIdentifiantCautionnaire.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Prénom du cautionnaire".
     */
    public String getTextFieldPrenom() {
        return textFieldPrenom.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Nom ou Organisme".
     */
    public String getTextFieldNomOuOrga() {
        return textFieldNomOuOrga.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Identifiant de l'adresse".
     */
    public String getTextFieldIdAdr() {
        return textFieldIdentifiantAdresse.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Adresse".
     */
    public String getTextFieldAdr() {
        return textFieldAdr.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Complément".
     */
    public String getTextFieldComplement() {
        return textFieldComplement.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Code Postal".
     */
    public String getTextFieldCodePostal() {
        return textFieldCodePostal.getText();
    }

    /**
     * Récupère le texte saisi dans le champ "Ville".
     */
    public String getTextFieldVille() {
        return textFieldVille.getText();
    }

    /**
     * Récupère la liste des champs obligatoires pour un cautionnaire.
     */
    public List<String> getChampsObligatoires() {
        List<String> res = new ArrayList<>();
        res.add(getTextFieldIdentifiantCautionnaire());
        res.add(getTextFieldNomOuOrga());
        res.add(getTextFieldMontant());
        return res;
    }

    /**
     * Récupère la liste des champs obligatoires pour l'adresse du cautionnaire.
     */
    public List<String> getChampsObligatoiresAdresse() {
        List<String> res = new ArrayList<>();
        res.add(getTextFieldIdAdr());
        res.add(getTextFieldAdr());
        res.add(getTextFieldCodePostal());
        res.add(getTextFieldVille());
        return res;
    }

    /**
     * Assigne une valeur au champ "Montant".
     */
    public void setTextFieldMontant(String texte) {
        this.textFieldMontant.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Lien Acte de Caution".
     */
    public void setTextFieldLienActeCaution(String texte) {
        this.textFieldLienActeCaution.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Prénom".
     */
    public void setTextFieldPrenom(String texte) {
        this.textFieldPrenom.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Nom ou Organisme".
     */
    public void setTextFieldNomOuOrga(String texte) {
        this.textFieldNomOuOrga.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Date de début".
     */
    public void setTextFieldDateDebut(String texte) {
        this.textFieldDateDebut.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Adresse".
     */
    public void setTextFieldAdr(String texte) {
        this.textFieldAdr.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Complément".
     */
    public void setTextFieldComplement(String texte) {
        this.textFieldComplement.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Code Postal".
     */
    public void setTextFieldCodePostal(String texte) {
        this.textFieldCodePostal.setText(texte);
    }

    /**
     * Assigne une valeur au champ "Ville".
     */
    public void setTextFieldVille(String texte) {
        this.textFieldVille.setText(texte);
    }

    /**
     * Récupère le texte de la zone "Description".
     */
    public String getTextFieldDescription() {
        return textPaneDescription.getText();
    }

    /**
     * Constructeur. Initialise la fenêtre d'ajout de cautionnaire et y associe
     * le contrôleur GestionAjouterCautionnaire.
     * 
     * @param ab  la vue AjouterBail précédente
     * @param al  la vue AjouterLocataire
     * @param afl la vue listant les locataires actuels
     */
    public AjouterCautionnaire(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
    	
    	this.gestionFen = new GestionAjouterCautionnaire(this, al, afl, ab);
    	this.fenPrecedente = ab;
    	
        setBounds(0, 0, 670, 460);
        setClosable(true);
        setTitle("Ajouter un cautionnaire");
        
        // Panel principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Titre principal
        JLabel lblTitreAjoutLoc = new JLabel("Ajouter un nouveau locataire 3/3");
        lblTitreAjoutLoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitreAjoutLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitreAjoutLoc.setBounds(0, 10, 658, 30);
        contentPane.add(lblTitreAjoutLoc);

        // Sous-titre
        JLabel lblSousTitre = new JLabel("Ajouter un cautionnaire");
        lblSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSousTitre.setBounds(10, 40, 648, 20);
        contentPane.add(lblSousTitre);

        // Panel encadré "Informations Cautionnaire"
        JPanel panelCautionnaire = new JPanel();
        panelCautionnaire.setBorder(new TitledBorder(
        		new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, Color.GRAY), 
        		"Informations Cautionnaire", 
        		TitledBorder.LEADING, 
        		TitledBorder.TOP, 
        		null, 
        		Color.BLACK
        ));
        panelCautionnaire.setBounds(31, 70, 592, 223);
        contentPane.add(panelCautionnaire);
        panelCautionnaire.setLayout(null);

        // Label et champ "Nom ou organisme"
        JLabel lblNomOuOrga = new JLabel("Nom ou organisme : *");
        lblNomOuOrga.setBounds(10, 58, 111, 20);
        panelCautionnaire.add(lblNomOuOrga);

        textFieldNomOuOrga = new JTextField();
        textFieldNomOuOrga.setBounds(120, 58, 100, 20);
        panelCautionnaire.add(textFieldNomOuOrga);
        textFieldNomOuOrga.setColumns(10);

        // Label et champ "Prénom"
        JLabel lblPrenom = new JLabel("Prénom : ");
        lblPrenom.setBounds(10, 88, 100, 20);
        panelCautionnaire.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setBounds(120, 88, 100, 19);
        textFieldPrenom.setColumns(10);
        panelCautionnaire.add(textFieldPrenom);

        // Label et zone "Description"
        JLabel lblDescription = new JLabel("Description :");
        lblDescription.setBounds(10, 118, 100, 20);
        panelCautionnaire.add(lblDescription);

        JScrollPane scrollPaneDescription = new JScrollPane();
        scrollPaneDescription.setBounds(10, 140, 199, 36);
        panelCautionnaire.add(scrollPaneDescription);

        textPaneDescription = new JTextPane();
        scrollPaneDescription.setViewportView(textPaneDescription);

        // Bouton "Remplir avec le locataire"
        JButton btnLocataireEstCautionnaire = new JButton("Remplir avec le locataire");
        btnLocataireEstCautionnaire.addActionListener(this.gestionFen);
        btnLocataireEstCautionnaire.setBounds(9, 191, 193, 22);
        panelCautionnaire.add(btnLocataireEstCautionnaire);

        // Panel "Adresse"
        JPanel panelAdresse = new JPanel();
        panelAdresse.setBorder(new TitledBorder(null, "Adresse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAdresse.setBounds(323, 16, 229, 179);
        panelCautionnaire.add(panelAdresse);
        panelAdresse.setLayout(new GridLayout(0, 2, 0, 0));

        // Identifiant de l'adresse
        JLabel lblIdAdresse = new JLabel("Identifiant :");
        panelAdresse.add(lblIdAdresse);

        JPanel panelTxtIdAdr = new JPanel();
        panelAdresse.add(panelTxtIdAdr);

        textFieldIdentifiantAdresse = new JTextField();
        textFieldIdentifiantAdresse.setColumns(10);
        panelTxtIdAdr.add(textFieldIdentifiantAdresse);

        // Adresse
        JLabel lblAdr = new JLabel("Adresse : ");
        panelAdresse.add(lblAdr);

        JPanel panelTxtAdr = new JPanel();
        panelAdresse.add(panelTxtAdr);

        textFieldAdr = new JTextField();
        textFieldAdr.setColumns(10);
        panelTxtAdr.add(textFieldAdr);

        // Complément
        JLabel lblComplement = new JLabel("Complément : ");
        panelAdresse.add(lblComplement);

        JPanel panelTxtComplement = new JPanel();
        panelAdresse.add(panelTxtComplement);

        textFieldComplement = new JTextField();
        textFieldComplement.setColumns(10);
        panelTxtComplement.add(textFieldComplement);

        // Code postal
        JLabel lblCodePostal = new JLabel("Code postal : ");
        panelAdresse.add(lblCodePostal);

        JPanel panelTxtCodePostal = new JPanel();
        panelAdresse.add(panelTxtCodePostal);

        textFieldCodePostal = new JTextField();
        textFieldCodePostal.setColumns(10);
        panelTxtCodePostal.add(textFieldCodePostal);

        // Ville
        JLabel lblVille = new JLabel("Ville : ");
        panelAdresse.add(lblVille);

        JPanel panelTxtVille = new JPanel();
        panelAdresse.add(panelTxtVille);

        textFieldVille = new JTextField();
        textFieldVille.setColumns(10);
        panelTxtVille.add(textFieldVille);

        // Identifiant cautionnaire
        JLabel lblIdCautionnaire = new JLabel("Identifiant : *");
        lblIdCautionnaire.setBounds(10, 35, 100, 13);
        panelCautionnaire.add(lblIdCautionnaire);

        textFieldIdentifiantCautionnaire = new JTextField();
        textFieldIdentifiantCautionnaire.setBounds(120, 29, 100, 19);
        textFieldIdentifiantCautionnaire.setColumns(10);
        panelCautionnaire.add(textFieldIdentifiantCautionnaire);

        // Panel de boutons en bas
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(296, 368, 339, 38);
        contentPane.add(panelBoutons);

        JButton btnValider = new JButton("Valider");
        panelBoutons.add(btnValider);

        JButton btnAnnuler = new JButton("Annuler");
        panelBoutons.add(btnAnnuler);

        JSeparator separator = new JSeparator();
        panelBoutons.add(separator);

        JButton btnPoursuivreSsCautionnaire = new JButton("Poursuivre sans cautionnaire →");
        btnPoursuivreSsCautionnaire.setBorder(null);
        btnPoursuivreSsCautionnaire.setBackground(SystemColor.menu);
        btnPoursuivreSsCautionnaire.setForeground(Color.BLUE);
        btnPoursuivreSsCautionnaire.setFont(new Font("Tahoma", Font.PLAIN, 9));
        panelBoutons.add(btnPoursuivreSsCautionnaire);

        // Panel acte de caution
        JPanel panelActeCaution = new JPanel();
        panelActeCaution.setBorder(new TitledBorder(null, "Caution", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelActeCaution.setBounds(30, 299, 236, 115);
        contentPane.add(panelActeCaution);
        panelActeCaution.setLayout(null);

        // Montant
        textFieldMontant = new JTextField();
        textFieldMontant.setToolTipText("Le montant de la caution ne peut excéder le triple du loyer actuel.");
        textFieldMontant.setBounds(113, 20, 96, 19);
        textFieldMontant.setColumns(10);
        panelActeCaution.add(textFieldMontant);

        JLabel lblMontant = new JLabel("Montant : *");
        lblMontant.setLabelFor(textFieldMontant);
        lblMontant.setBounds(10, 23, 69, 13);
        panelActeCaution.add(lblMontant);

        // Lien acte de caution
        textFieldLienActeCaution = new JTextField();
        textFieldLienActeCaution.setBounds(10, 86, 216, 19);
        textFieldLienActeCaution.setColumns(10);
        panelActeCaution.add(textFieldLienActeCaution);

        JLabel lblLienActeCaution = new JLabel("Lien vers l'acte de caution : ");
        lblLienActeCaution.setBounds(10, 63, 169, 13);
        panelActeCaution.add(lblLienActeCaution);

        // Label du loyer actuel (informatif)
        JLabel lblLoyer = new JLabel("Loyer actuel :");
        lblLoyer.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblLoyer.setForeground(Color.GRAY);
        lblLoyer.setBounds(9, 43, 79, 13);
        panelActeCaution.add(lblLoyer);

        JLabel lblEuros = new JLabel("€");
        lblEuros.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblEuros.setForeground(Color.GRAY);
        lblEuros.setBounds(123, 43, 12, 13);
        panelActeCaution.add(lblEuros);

        lblMontantLoyer = new JLabel("");
        lblMontantLoyer.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblMontantLoyer.setForeground(Color.GRAY);
        lblMontantLoyer.setBounds(77, 43, 45, 13);
        // On récupère le loyer actuel depuis le contrôleur
        lblMontantLoyer.setText(this.gestionFen.recupererMontantLoyer());
        panelActeCaution.add(lblMontantLoyer);

        // Listeners sur les boutons
        btnAnnuler.addActionListener(this.gestionFen);
        btnValider.addActionListener(this.gestionFen);
        btnPoursuivreSsCautionnaire.addActionListener(this.gestionFen);
    }

    /**
     * Retourne la fenêtre précédente (AjouterBail).
     */
    public AjouterBail getFenPrecedente() {
        return fenPrecedente;
    }

    /**
     * Affiche une boîte de dialogue d'erreur.
     * @param message Le message à afficher.
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
        		this, 
        		message, 
        		"Erreur", 
        		JOptionPane.ERROR_MESSAGE
        ); 
    }
}
