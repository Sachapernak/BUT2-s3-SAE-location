package vue;



import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import controleur.GestionAjouterBien;
import controleur.GestionTablesFenetrePrincipale;

public class AjouterBien extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    private JTable tableBat;
    private JTextField textFieldIdBienLog;
    private JTextField textFieldSurfaceLog;
    private JTextField textFieldNbPiecesLog;
    private JTextField textFieldLoyerBaseLog;
    private JTextField textFieldIdentifiantFiscalLog;
    private JTextField textFieldComplementAdresseLog;
    
    private JTextField textFieldIdBienGar;
    private JTextField textFieldSurfaceGar;
    private JTextField textFieldLoyerBaseGar;
    private JTextField textFieldIdentifiantFiscalGar;
    private JTextField textFieldComplementAdresseGar;

    
	private JRadioButton rdbtnLogement;
    private JRadioButton rdbtnGarage;
    private ButtonGroup groupTypeBien;

    
    
    
    /**
	 * @return the groupTypeBien
	 */
	public ButtonGroup getGroupTypeBien() {
		return groupTypeBien;
	}

	

	/**
	 * @return the tableBat
	 */
	public JTable getTableBat() {
		return tableBat;
	}

	/**
	 * @return the textFieldIdBienLog
	 */
	public JTextField getTextFieldIdBienLog() {
		return textFieldIdBienLog;
	}

	/**
	 * @return the textFieldSurfaceLog
	 */
	public JTextField getTextFieldSurfaceLog() {
		return textFieldSurfaceLog;
	}

	/**
	 * @return the textFieldNbPiecesLog
	 */
	public JTextField getTextFieldNbPiecesLog() {
		return textFieldNbPiecesLog;
	}

	/**
	 * @return the textFieldLoyerBaseLog
	 */
	public JTextField getTextFieldLoyerBaseLog() {
		return textFieldLoyerBaseLog;
	}

	/**
	 * @return the textFieldIdentifiantFiscalLog
	 */
	public JTextField getTextFieldIdentifiantFiscalLog() {
		return textFieldIdentifiantFiscalLog;
	}

	/**
	 * @return the textFieldComplementAdresseLog
	 */
	public JTextField getTextFieldComplementAdresseLog() {
		return textFieldComplementAdresseLog;
	}

	/**
	 * @return the textFieldIdBienGar
	 */
	public JTextField getTextFieldIdBienGar() {
		return textFieldIdBienGar;
	}

	/**
	 * @return the textFieldSurfaceGar
	 */
	public JTextField getTextFieldSurfaceGar() {
		return textFieldSurfaceGar;
	}

	/**
	 * @return the textFieldLoyerBaseGar
	 */
	public JTextField getTextFieldLoyerBaseGar() {
		return textFieldLoyerBaseGar;
	}

	/**
	 * @return the textFieldIdentifiantFiscalGar
	 */
	public JTextField getTextFieldIdentifiantFiscalGar() {
		return textFieldIdentifiantFiscalGar;
	}

	/**
	 * @return the textFieldComplementAdresseGar
	 */
	public JTextField getTextFieldComplementAdresseGar() {
		return textFieldComplementAdresseGar;
	}

	/**
	 * @return the rdbtnLogement
	 */
	public JRadioButton getRdbtnLogement() {
		return rdbtnLogement;
	}

	/**
	 * @return the rdbtnGarage
	 */
	public JRadioButton getRdbtnGarage() {
		return rdbtnGarage;
	}

	private GestionAjouterBien gestionClic;

    public AjouterBien(JTable tableBat, GestionTablesFenetrePrincipale gtfp) {
        this.gestionClic = new GestionAjouterBien(this,gtfp);
        
        setBounds(0, 0, 620, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitreAjoutBien = new JLabel("Ajouter un nouveau bien locatif");
        lblTitreAjoutBien.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitreAjoutBien.setBounds(180, 10, 300, 30);
        contentPane.add(lblTitreAjoutBien);

        rdbtnLogement = new JRadioButton("Logement");
        rdbtnLogement.setBounds(150, 50, 100, 20);
        rdbtnGarage = new JRadioButton("Garage");
        rdbtnGarage.setBounds(300, 50, 100, 20);
        groupTypeBien = new ButtonGroup();
        groupTypeBien.add(rdbtnLogement);
        groupTypeBien.add(rdbtnGarage);
        contentPane.add(rdbtnLogement);
        contentPane.add(rdbtnGarage);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBounds(20, 80, 560, 270);
        contentPane.add(cardPanel);
        


		// Champs pour le Logement
		textFieldIdBienLog = new JTextField();
		textFieldSurfaceLog = new JTextField();
		textFieldNbPiecesLog = new JTextField();
		textFieldLoyerBaseLog = new JTextField();
		textFieldIdentifiantFiscalLog = new JTextField();
		textFieldComplementAdresseLog = new JTextField();
		
		
        this.tableBat = tableBat;
        
        // Panel for Logement
        JPanel panelLogement = new JPanel(null);
        addLabelAndTextField(panelLogement, "Identifiant du Bien* :", 20, 10, textFieldIdBienLog);
        addLabelAndTextField(panelLogement, "Surface (m²)* :", 20, 50, textFieldSurfaceLog);
        addLabelAndTextField(panelLogement, "Nombre de Pièces* :", 20, 90, textFieldNbPiecesLog);
        addLabelAndTextField(panelLogement, "Complément d'Adresse :", 20, 130, textFieldComplementAdresseLog);
        addLabelAndTextField(panelLogement, "Identifiant Fiscal :", 20, 170, textFieldIdentifiantFiscalLog);
        addLabelAndTextField(panelLogement, "Loyer de Base* :", 20, 210, textFieldLoyerBaseLog);
	    
        // Champs pour le Logement
 		textFieldIdBienGar = new JTextField();
 		textFieldSurfaceGar = new JTextField();
 		textFieldLoyerBaseGar = new JTextField();
 		textFieldIdentifiantFiscalGar = new JTextField();
 		textFieldComplementAdresseGar = new JTextField();
        
        // Panel for Garage
        JPanel panelGarage = new JPanel(null);
        addLabelAndTextField(panelGarage, "Identifiant du Bien* :", 20, 10, textFieldIdBienGar);
        addLabelAndTextField(panelGarage, "Surface (m²) :", 20, 50, textFieldSurfaceGar);
        addLabelAndTextField(panelGarage, "Complément d'Adresse :", 20, 90, textFieldComplementAdresseGar);
        addLabelAndTextField(panelGarage, "Identifiant Fiscal :", 20, 130, textFieldIdentifiantFiscalGar);
        addLabelAndTextField(panelGarage, "Loyer de Base* :", 20, 170, textFieldLoyerBaseGar);
        
        
        cardPanel.add(panelLogement, "Logement");
        cardPanel.add(panelGarage, "Garage");

        rdbtnLogement.addActionListener(e -> cardLayout.show(cardPanel, "Logement"));
        rdbtnGarage.addActionListener(e -> cardLayout.show(cardPanel, "Garage"));

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(180, 370, 100, 30);
        btnAjouter.addActionListener(this.gestionClic);
        contentPane.add(btnAjouter);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(320, 370, 100, 30);
        btnAnnuler.addActionListener(this.gestionClic);
        contentPane.add(btnAnnuler);

        rdbtnLogement.setSelected(true);
        cardLayout.show(cardPanel, "Logement");
    }

    private void addLabelAndTextField(JPanel panel, String labelText, int x, int y, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 150, 25);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(label);
        textField.setBounds(x + 160, y, 200, 25);
        panel.add(textField);
        textField.setColumns(10);
    }
}