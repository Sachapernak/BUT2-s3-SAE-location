package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controleur.GestionDetailChargeCF;
import modele.ChargeFixe;

/**
 * Fenêtre de détail pour une Charge Fixe.
 */
public class DetailChargeCF extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JLabel lblTxtId;
    private JLabel lblTxtDateDeCharge;
    private JLabel lblTxtType;
    private JLabel lblTxtMontant;
    private JLabel lblTxtNumDoc;
    private GestionDetailChargeCF gest;

    /**
     * Crée la boîte de dialogue pour afficher les détails d'une charge fixe.
     * @param chargeFixe L'objet ChargeFixe dont les détails seront affichés.
     */
    public DetailChargeCF(ChargeFixe chargeFixe) {
        this.gest = new GestionDetailChargeCF(this, chargeFixe);

        setTitle("Détail de la charge fixe");
        setBounds(100, 100, 400, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{130, 0, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);

        // Ligne 0 : ID
        JLabel lblId = new JLabel("ID :");
        GridBagConstraints gbcLblId = new GridBagConstraints();
        gbcLblId.anchor = GridBagConstraints.EAST;
        gbcLblId.insets = new Insets(0, 0, 5, 5);
        gbcLblId.gridx = 0;
        gbcLblId.gridy = 0;
        contentPanel.add(lblId, gbcLblId);

        lblTxtId = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtId = new GridBagConstraints();
        gbcLblTxtId.anchor = GridBagConstraints.WEST;
        gbcLblTxtId.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtId.gridx = 1;
        gbcLblTxtId.gridy = 0;
        contentPanel.add(lblTxtId, gbcLblTxtId);

        // Ligne 1 : Date de charge
        JLabel lblDateDeCharge = new JLabel("Date de charge :");
        GridBagConstraints gbcLblDateDeCharge = new GridBagConstraints();
        gbcLblDateDeCharge.anchor = GridBagConstraints.EAST;
        gbcLblDateDeCharge.insets = new Insets(0, 0, 5, 5);
        gbcLblDateDeCharge.gridx = 0;
        gbcLblDateDeCharge.gridy = 1;
        contentPanel.add(lblDateDeCharge, gbcLblDateDeCharge);

        lblTxtDateDeCharge = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtDateDeCharge = new GridBagConstraints();
        gbcLblTxtDateDeCharge.anchor = GridBagConstraints.WEST;
        gbcLblTxtDateDeCharge.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtDateDeCharge.gridx = 1;
        gbcLblTxtDateDeCharge.gridy = 1;
        contentPanel.add(lblTxtDateDeCharge, gbcLblTxtDateDeCharge);

        // Ligne 2 : Type
        JLabel lblType = new JLabel("Type :");
        GridBagConstraints gbcLblType = new GridBagConstraints();
        gbcLblType.anchor = GridBagConstraints.EAST;
        gbcLblType.insets = new Insets(0, 0, 5, 5);
        gbcLblType.gridx = 0;
        gbcLblType.gridy = 2;
        contentPanel.add(lblType, gbcLblType);

        lblTxtType = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtType = new GridBagConstraints();
        gbcLblTxtType.anchor = GridBagConstraints.WEST;
        gbcLblTxtType.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtType.gridx = 1;
        gbcLblTxtType.gridy = 2;
        contentPanel.add(lblTxtType, gbcLblTxtType);

        // Ligne 3 : Montant
        JLabel lblMontant = new JLabel("Montant :");
        GridBagConstraints gbcLblMontant = new GridBagConstraints();
        gbcLblMontant.anchor = GridBagConstraints.EAST;
        gbcLblMontant.insets = new Insets(0, 0, 5, 5);
        gbcLblMontant.gridx = 0;
        gbcLblMontant.gridy = 3;
        contentPanel.add(lblMontant, gbcLblMontant);

        lblTxtMontant = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtMontant = new GridBagConstraints();
        gbcLblTxtMontant.anchor = GridBagConstraints.WEST;
        gbcLblTxtMontant.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtMontant.gridx = 1;
        gbcLblTxtMontant.gridy = 3;
        contentPanel.add(lblTxtMontant, gbcLblTxtMontant);

        // Ligne 4 : Numéro de document
        JLabel lblNumDoc = new JLabel("Numéro de document :");
        GridBagConstraints gbcLblNumDoc = new GridBagConstraints();
        gbcLblNumDoc.anchor = GridBagConstraints.EAST;
        gbcLblNumDoc.insets = new Insets(0, 0, 0, 5);
        gbcLblNumDoc.gridx = 0;
        gbcLblNumDoc.gridy = 4;
        contentPanel.add(lblNumDoc, gbcLblNumDoc);

        lblTxtNumDoc = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtNumDoc = new GridBagConstraints();
        gbcLblTxtNumDoc.anchor = GridBagConstraints.WEST;
        gbcLblTxtNumDoc.gridx = 1;
        gbcLblTxtNumDoc.gridy = 4;
        contentPanel.add(lblTxtNumDoc, gbcLblTxtNumDoc);

        // Boutons en bas
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton cancelButton = new JButton("Annuler");
        buttonPane.add(cancelButton);

        gest.gestionAnnuler(cancelButton);
        gest.chargeDonnee();
    }

    // Setters pour afficher les informations de la charge fixe
    public void setId(String id) { lblTxtId.setText(id); }
    public void setDateDeCharge(String date) { lblTxtDateDeCharge.setText(date); }
    public void setType(String type) { lblTxtType.setText(type); }
    public void setMontant(String montant) { lblTxtMontant.setText(montant); }
    public void setNumDoc(String numDoc) { lblTxtNumDoc.setText(numDoc); }
}
