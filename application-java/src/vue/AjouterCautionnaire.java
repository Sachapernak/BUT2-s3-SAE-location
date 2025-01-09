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

public class AjouterCautionnaire extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private GestionAjouterCautionnaire gestionFen;
    
    private AjouterBail fenPrecedente;

	private JTextField textFieldMontant;
    private JTextField textFieldLienActeCaution;
    private JTextField textFieldPrenom;
    public JTextField textFieldNomOuOrga;
    public JTextField textFieldDateDebut;
    private JTextField textFieldAdr;
    private JTextField textFieldComplement;
    private JTextField textFieldCodePostal;
    private JTextField textFieldVille;
    private JTextPane textPaneDescription;
    private JTextField textFieldIdentifiantCautionnaire;
    private JTextField textFieldIdentifiantAdresse;
    private JLabel lblMontantLoyer;
    
    
    public String getTextFieldMontant() {
		return textFieldMontant.getText();
	}
    public String getLblMontantLoyer() {
		return lblMontantLoyer.getText();
	}
	

	public String getTextFieldLienActeCaution() {
		return textFieldLienActeCaution.getText();
	}

	public String getTextFieldIdentifiantCautionnaire() {
		return textFieldIdentifiantCautionnaire.getText();
	}

	public String getTextFieldPrenom() {
		return textFieldPrenom.getText();
	}

	public String getTextFieldNomOuOrga() {
		return textFieldNomOuOrga.getText();
	}
	
    public String getTextFieldIdAdr() {
		return textFieldIdentifiantAdresse.getText();
	}
    
    public String getTextFieldAdr() {
		return textFieldAdr.getText();
	}

	public String getTextFieldComplement() {
		return textFieldComplement.getText();
	}

	public String getTextFieldCodePostal() {
		return textFieldCodePostal.getText();
	}

	public String getTextFieldVille() {
		return textFieldVille.getText();
	}
	
	 public List<String> getChampsObligatoires(){
	    List<String> res = new ArrayList<>();
	    res.add(getTextFieldIdentifiantCautionnaire());
	    res.add(getTextFieldNomOuOrga());
	    res.add(getTextFieldMontant());
	    return res;
	}
	 
	 public List<String> getChampsObligatoiresAdresse(){
		 List<String> res = new ArrayList<>(); 
		 res.add(getTextFieldIdAdr());
		 res.add(getTextFieldAdr());
		 res.add(getTextFieldCodePostal());
		 res.add(getTextFieldVille());
		 return res;
	 }
	
	public void setTextFieldMontant(String texte) {
		this.textFieldMontant.setText(texte);
	}

	public void setTextFieldLienActeCaution(String texte) {
		this.textFieldLienActeCaution.setText(texte);
	}

	public void setTextFieldPrenom(String texte) {
		this.textFieldPrenom.setText(texte);;
	}

	public void setTextFieldNomOuOrga(String texte) {
		this.textFieldNomOuOrga.setText(texte);
	}

	public void setTextFieldDateDebut(String texte) {
		this.textFieldDateDebut.setText(texte);
	}

	public void setTextFieldAdr(String texte) {
		this.textFieldAdr.setText(texte);
	}

	public void setTextFieldComplement(String texte) {
		this.textFieldComplement.setText(texte);
	}

	public void setTextFieldCodePostal(String texte) {
		this.textFieldCodePostal.setText(texte);
	}

	public void setTextFieldVille(String texte) {
		this.textFieldVille.setText(texte);
	}

	public void setTextPaneDescription(JTextPane textPaneDescription) {
		this.textPaneDescription = textPaneDescription;
	}

	public void setTextFieldIdentifiantCautionnaire(JTextField textFieldIdentifiantCautionnaire) {
		this.textFieldIdentifiantCautionnaire = textFieldIdentifiantCautionnaire;
	}

	public String getTextFieldDescription() {
		return textPaneDescription.getText();
	}
	
	

	/**
     * Create the frame.
     */
    public AjouterCautionnaire(AjouterBail ab, AjouterLocataire al, AfficherLocatairesActuels afl) {
    	
    	this.gestionFen = new GestionAjouterCautionnaire(this,al,afl,ab);
    	this.fenPrecedente = ab;
    	
        setBounds(0, 0, 670, 460);
        //721, 489
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

        JLabel lblSousTitre = new JLabel("Ajouter un cautionnaire");
        lblSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblSousTitre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSousTitre.setBounds(10, 40, 648, 20);
        contentPane.add(lblSousTitre);

        // Panel Encadré Nouveau Bail
        JPanel panelCautionnaire = new JPanel();
        panelCautionnaire.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Informations Cautionnaire", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelCautionnaire.setBounds(31, 70, 592, 223);
        contentPane.add(panelCautionnaire);
        panelCautionnaire.setLayout(null);

        // Champs de saisie pour Nouveau Bail
        JLabel lblNomOuOrga = new JLabel("Nom ou organisme : *");
        lblNomOuOrga.setBounds(10, 58, 111, 20);
        panelCautionnaire.add(lblNomOuOrga);

        textFieldNomOuOrga = new JTextField();
        textFieldNomOuOrga.setBounds(120, 58, 100, 20);
        panelCautionnaire.add(textFieldNomOuOrga);
        textFieldNomOuOrga.setColumns(10);

        JLabel lblPrenom = new JLabel("Prénom : ");
        lblPrenom.setBounds(10, 88, 100, 20);
        panelCautionnaire.add(lblPrenom);


        JLabel lblDescription = new JLabel("Description :");
        lblDescription.setBounds(10, 118, 100, 20);
        panelCautionnaire.add(lblDescription);
        
        JButton btnLocataireEstCautionnaire = new JButton("Remplir avec le locataire");
        btnLocataireEstCautionnaire.addActionListener(this.gestionFen);
        btnLocataireEstCautionnaire.setBounds(9, 191, 193, 22);
        panelCautionnaire.add(btnLocataireEstCautionnaire);
        
        textFieldPrenom = new JTextField();
        textFieldPrenom.setBounds(120, 88, 100, 19);
        panelCautionnaire.add(textFieldPrenom);
        textFieldPrenom.setColumns(10);
        
        
        JPanel panelAdresse = new JPanel();
        panelAdresse.setBorder(new TitledBorder(null, "Adresse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAdresse.setBounds(323, 16, 229, 179);
        panelCautionnaire.add(panelAdresse);
        panelAdresse.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lblIdAdresse = new JLabel("Identifiant :");
        panelAdresse.add(lblIdAdresse);
        
        JPanel panelTxtIdAdr = new JPanel();
        panelAdresse.add(panelTxtIdAdr);
        
        textFieldIdentifiantAdresse = new JTextField();
        panelTxtIdAdr.add(textFieldIdentifiantAdresse);
        textFieldIdentifiantAdresse.setColumns(10);
        
        JLabel lblAdr = new JLabel("Adresse : ");
        panelAdresse.add(lblAdr);
        
        JPanel panelTxtAdr = new JPanel();
        panelAdresse.add(panelTxtAdr);
        
        textFieldAdr = new JTextField();
        panelTxtAdr.add(textFieldAdr);
        textFieldAdr.setColumns(10);
        
        JLabel lblComplement = new JLabel("Complément : ");
        panelAdresse.add(lblComplement);
        
        JPanel panelTxtComplement = new JPanel();
        panelAdresse.add(panelTxtComplement);
        
        textFieldComplement = new JTextField();
        panelTxtComplement.add(textFieldComplement);
        textFieldComplement.setColumns(10);
        
        JLabel lblCodePostal = new JLabel("Code postal : ");
        panelAdresse.add(lblCodePostal);
        
        JPanel panelTxtCodePostal = new JPanel();
        panelAdresse.add(panelTxtCodePostal);
        
        textFieldCodePostal = new JTextField();
        panelTxtCodePostal.add(textFieldCodePostal);
        textFieldCodePostal.setColumns(10);
        
        JLabel lblVille = new JLabel("Ville : ");
        panelAdresse.add(lblVille);
        
        JPanel panelTxtVille = new JPanel();
        panelAdresse.add(panelTxtVille);
        
        textFieldVille = new JTextField();
        panelTxtVille.add(textFieldVille);
        textFieldVille.setColumns(10);
        
        JLabel lblIdCautionnaire = new JLabel("Identifiant : *");
        lblIdCautionnaire.setBounds(10, 35, 100, 13);
        panelCautionnaire.add(lblIdCautionnaire);
        
        textFieldIdentifiantCautionnaire = new JTextField();
        textFieldIdentifiantCautionnaire.setBounds(120, 29, 100, 19);
        panelCautionnaire.add(textFieldIdentifiantCautionnaire);
        textFieldIdentifiantCautionnaire.setColumns(10);
        
        JScrollPane scrollPaneDescription = new JScrollPane();
        scrollPaneDescription.setBounds(10, 140, 199, 36);
        panelCautionnaire.add(scrollPaneDescription);
        
        textPaneDescription = new JTextPane();
        scrollPaneDescription.setViewportView(textPaneDescription);
        
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(330, 370, 260, 38);
        contentPane.add(panelBoutons);
        
        // Boutons en bas au centre
        JButton btnValider = new JButton("Valider");
        panelBoutons.add(btnValider);
              
        JButton btnAnnuler = new JButton("Annuler");
        panelBoutons.add(btnAnnuler);
        
        JPanel panel_acteCaution = new JPanel();
        panel_acteCaution.setBorder(new TitledBorder(null, "Caution", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_acteCaution.setBounds(30, 299, 236, 115);
        contentPane.add(panel_acteCaution);
        panel_acteCaution.setLayout(null);
        
        textFieldMontant = new JTextField();
        textFieldMontant.setBounds(113, 20, 96, 19);
        panel_acteCaution.add(textFieldMontant);
        textFieldMontant.setColumns(10);
        
        JLabel lblMontant = new JLabel("Montant : *");
        lblMontant.setLabelFor(textFieldMontant);
        lblMontant.setBounds(10, 23, 69, 13);
        panel_acteCaution.add(lblMontant);
        
        textFieldLienActeCaution = new JTextField();
        textFieldLienActeCaution.setBounds(10, 86, 216, 19);
        panel_acteCaution.add(textFieldLienActeCaution);
        textFieldLienActeCaution.setColumns(10);
        
        JLabel lblLienActeCaution = new JLabel("Lien vers l'acte de caution : ");
        lblLienActeCaution.setBounds(10, 63, 169, 13);
        panel_acteCaution.add(lblLienActeCaution);
        
        JLabel lblLoyer = new JLabel("Loyer actuel :");
        lblLoyer.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblLoyer.setForeground(Color.GRAY);
        lblLoyer.setBounds(9, 43, 79, 13);
        panel_acteCaution.add(lblLoyer);
        
        JLabel lblEuros = new JLabel("€");
        lblEuros.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblEuros.setForeground(Color.GRAY);
        lblEuros.setBounds(123, 43, 12, 13);
        panel_acteCaution.add(lblEuros);
        
        lblMontantLoyer = new JLabel("");
        lblMontantLoyer.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblMontantLoyer.setForeground(Color.GRAY);
        lblMontantLoyer.setBounds(77, 43, 45, 13);
        lblMontantLoyer.setText(this.gestionFen.recupererMontantLoyer());
        panel_acteCaution.add(lblMontantLoyer);
        
        btnAnnuler.addActionListener(this.gestionFen);
        btnValider.addActionListener(this.gestionFen);
    }

	public AjouterBail getFenPrecedente() {
		return fenPrecedente;
	}

	public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message,"Erreur", JOptionPane.ERROR_MESSAGE); 
	}
}
