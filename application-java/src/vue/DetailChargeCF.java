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
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{130, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);

        // Ligne 0 : ID
        JLabel lblId = new JLabel("ID :");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.insets = new Insets(0, 0, 5, 5);
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        contentPanel.add(lblId, gbc_lblId);

        lblTxtId = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtId = new GridBagConstraints();
        gbc_lblTxtId.anchor = GridBagConstraints.WEST;
        gbc_lblTxtId.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtId.gridx = 1;
        gbc_lblTxtId.gridy = 0;
        contentPanel.add(lblTxtId, gbc_lblTxtId);

        // Ligne 1 : Date de charge
        JLabel lblDateDeCharge = new JLabel("Date de charge :");
        GridBagConstraints gbc_lblDateDeCharge = new GridBagConstraints();
        gbc_lblDateDeCharge.anchor = GridBagConstraints.EAST;
        gbc_lblDateDeCharge.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateDeCharge.gridx = 0;
        gbc_lblDateDeCharge.gridy = 1;
        contentPanel.add(lblDateDeCharge, gbc_lblDateDeCharge);

        lblTxtDateDeCharge = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtDateDeCharge = new GridBagConstraints();
        gbc_lblTxtDateDeCharge.anchor = GridBagConstraints.WEST;
        gbc_lblTxtDateDeCharge.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtDateDeCharge.gridx = 1;
        gbc_lblTxtDateDeCharge.gridy = 1;
        contentPanel.add(lblTxtDateDeCharge, gbc_lblTxtDateDeCharge);

        // Ligne 2 : Type
        JLabel lblType = new JLabel("Type :");
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.anchor = GridBagConstraints.EAST;
        gbc_lblType.insets = new Insets(0, 0, 5, 5);
        gbc_lblType.gridx = 0;
        gbc_lblType.gridy = 2;
        contentPanel.add(lblType, gbc_lblType);

        lblTxtType = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtType = new GridBagConstraints();
        gbc_lblTxtType.anchor = GridBagConstraints.WEST;
        gbc_lblTxtType.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtType.gridx = 1;
        gbc_lblTxtType.gridy = 2;
        contentPanel.add(lblTxtType, gbc_lblTxtType);

        // Ligne 3 : Montant
        JLabel lblMontant = new JLabel("Montant :");
        GridBagConstraints gbc_lblMontant = new GridBagConstraints();
        gbc_lblMontant.anchor = GridBagConstraints.EAST;
        gbc_lblMontant.insets = new Insets(0, 0, 5, 5);
        gbc_lblMontant.gridx = 0;
        gbc_lblMontant.gridy = 3;
        contentPanel.add(lblMontant, gbc_lblMontant);

        lblTxtMontant = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtMontant = new GridBagConstraints();
        gbc_lblTxtMontant.anchor = GridBagConstraints.WEST;
        gbc_lblTxtMontant.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtMontant.gridx = 1;
        gbc_lblTxtMontant.gridy = 3;
        contentPanel.add(lblTxtMontant, gbc_lblTxtMontant);

        // Ligne 4 : Numéro de document
        JLabel lblNumDoc = new JLabel("Numéro de document :");
        GridBagConstraints gbc_lblNumDoc = new GridBagConstraints();
        gbc_lblNumDoc.anchor = GridBagConstraints.EAST;
        gbc_lblNumDoc.insets = new Insets(0, 0, 0, 5);
        gbc_lblNumDoc.gridx = 0;
        gbc_lblNumDoc.gridy = 4;
        contentPanel.add(lblNumDoc, gbc_lblNumDoc);

        lblTxtNumDoc = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtNumDoc = new GridBagConstraints();
        gbc_lblTxtNumDoc.anchor = GridBagConstraints.WEST;
        gbc_lblTxtNumDoc.gridx = 1;
        gbc_lblTxtNumDoc.gridy = 4;
        contentPanel.add(lblTxtNumDoc, gbc_lblTxtNumDoc);

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
