package vue;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
    
    
    public JTextField getTextFieldMontant() {
		return textFieldMontant;
	}

	public JTextField getTextFieldLienActeCaution() {
		return textFieldLienActeCaution;
	}

	public JTextField getTextFieldIdentifiantCautionnaire() {
		return textFieldIdentifiantCautionnaire;
	}

	public JTextField getTextFieldPrenom() {
		return textFieldPrenom;
	}

	public JTextField getTextFieldNomOuOrga() {
		return textFieldNomOuOrga;
	}
	
    public JTextField getTextFieldAdr() {
		return textFieldAdr;
	}

	public JTextField getTextFieldComplement() {
		return textFieldComplement;
	}

	public JTextField getTextFieldCodePostal() {
		return textFieldCodePostal;
	}

	public JTextField getTextFieldVille() {
		return textFieldVille;
	}
	
	public JTextPane getTextFieldDescription() {
		return textPaneDescription;
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
        panelCautionnaire.setBounds(31, 70, 592, 213);
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
        btnLocataireEstCautionnaire.setBounds(359, 181, 193, 22);
        panelCautionnaire.add(btnLocataireEstCautionnaire);
        
        textFieldPrenom = new JTextField();
        textFieldPrenom.setBounds(120, 88, 100, 19);
        panelCautionnaire.add(textFieldPrenom);
        textFieldPrenom.setColumns(10);
        
        
        JPanel panelAdresse = new JPanel();
        panelAdresse.setBounds(323, 25, 229, 134);
        panelCautionnaire.add(panelAdresse);
        panelAdresse.setLayout(null);
        
        JLabel lblAdr = new JLabel("Adresse : *");
        lblAdr.setBounds(0, 10, 75, 13);
        panelAdresse.add(lblAdr);
        
        textFieldAdr = new JTextField();
        textFieldAdr.setBounds(112, 7, 96, 19);
        panelAdresse.add(textFieldAdr);
        textFieldAdr.setColumns(10);
        
        JLabel lblComplement = new JLabel("Complément : *");
        lblComplement.setBounds(0, 39, 96, 13);
        panelAdresse.add(lblComplement);
        
        textFieldComplement = new JTextField();
        textFieldComplement.setBounds(112, 36, 96, 19);
        panelAdresse.add(textFieldComplement);
        textFieldComplement.setColumns(10);
        
        JLabel lblCodePostal = new JLabel("Code postal : *");
        lblCodePostal.setBounds(0, 68, 96, 13);
        panelAdresse.add(lblCodePostal);
        
        textFieldCodePostal = new JTextField();
        textFieldCodePostal.setBounds(112, 65, 96, 19);
        panelAdresse.add(textFieldCodePostal);
        textFieldCodePostal.setColumns(10);
        
        JLabel lblVille = new JLabel("Ville : *");
        lblVille.setBounds(0, 95, 45, 13);
        panelAdresse.add(lblVille);
        
        textFieldVille = new JTextField();
        textFieldVille.setBounds(112, 94, 96, 19);
        panelAdresse.add(textFieldVille);
        textFieldVille.setColumns(10);
        
        JLabel lblIdCautionnaire = new JLabel("Identifiant : *");
        lblIdCautionnaire.setBounds(10, 35, 100, 13);
        panelCautionnaire.add(lblIdCautionnaire);
        
        textFieldIdentifiantCautionnaire = new JTextField();
        textFieldIdentifiantCautionnaire.setBounds(120, 29, 100, 19);
        panelCautionnaire.add(textFieldIdentifiantCautionnaire);
        textFieldIdentifiantCautionnaire.setColumns(10);
        
        JScrollPane scrollPaneDescription = new JScrollPane();
        scrollPaneDescription.setBounds(10, 140, 199, 46);
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
        panel_acteCaution.setBounds(31, 293, 236, 115);
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
        
        JLabel lblLienActeCaution = new JLabel("Lien vers l'acte de caution : *");
        lblLienActeCaution.setBounds(10, 63, 169, 13);
        panel_acteCaution.add(lblLienActeCaution);
        
        btnAnnuler.addActionListener(this.gestionFen);
        btnValider.addActionListener(this.gestionFen);
    }

	public AjouterBail getFenPrecedente() {
		return fenPrecedente;
	}
}
