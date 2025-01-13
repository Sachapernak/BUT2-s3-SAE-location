package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import controleur.GestionDetailAssurance;
import modele.Assurance;

/**
 * Fenêtre de détail pour une Assurance.
 * Affiche les informations d'une assurance dans une interface graphique.
 */
public class DetailAssurance extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JLabel lblTxtNumeroContrat;
    private JLabel lblTxtAnneeContrat;
    private JLabel lblTxtTypeContrat;
    private GestionDetailAssurance gest;

    /**
     * Crée la boîte de dialogue pour afficher les détails d'une assurance.
     * @param assurance L'objet Assurance dont les détails seront affichés.
     */
    public DetailAssurance(Assurance assurance) {
        this.gest = new GestionDetailAssurance(this, assurance);
        
        setTitle("Détail de l'assurance");
        setBounds(100, 100, 320, 140);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{130, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        
        // Ligne 0 : Numéro de contrat
        JLabel lblNumeroContrat = new JLabel("Numéro de contrat :");
        GridBagConstraints gbc_lblNumeroContrat = new GridBagConstraints();
        gbc_lblNumeroContrat.anchor = GridBagConstraints.EAST;
        gbc_lblNumeroContrat.insets = new Insets(0, 0, 5, 5);
        gbc_lblNumeroContrat.gridx = 0;
        gbc_lblNumeroContrat.gridy = 0;
        contentPanel.add(lblNumeroContrat, gbc_lblNumeroContrat);

        lblTxtNumeroContrat = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtNumeroContrat = new GridBagConstraints();
        gbc_lblTxtNumeroContrat.anchor = GridBagConstraints.WEST;
        gbc_lblTxtNumeroContrat.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtNumeroContrat.gridx = 1;
        gbc_lblTxtNumeroContrat.gridy = 0;
        contentPanel.add(lblTxtNumeroContrat, gbc_lblTxtNumeroContrat);

        // Ligne 1 : Année de contrat
        JLabel lblAnneeContrat = new JLabel("Année de contrat :");
        GridBagConstraints gbc_lblAnneeContrat = new GridBagConstraints();
        gbc_lblAnneeContrat.anchor = GridBagConstraints.EAST;
        gbc_lblAnneeContrat.insets = new Insets(0, 0, 5, 5);
        gbc_lblAnneeContrat.gridx = 0;
        gbc_lblAnneeContrat.gridy = 1;
        contentPanel.add(lblAnneeContrat, gbc_lblAnneeContrat);

        lblTxtAnneeContrat = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtAnneeContrat = new GridBagConstraints();
        gbc_lblTxtAnneeContrat.anchor = GridBagConstraints.WEST;
        gbc_lblTxtAnneeContrat.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtAnneeContrat.gridx = 1;
        gbc_lblTxtAnneeContrat.gridy = 1;
        contentPanel.add(lblTxtAnneeContrat, gbc_lblTxtAnneeContrat);

        // Ligne 2 : Type de contrat
        JLabel lblTypeContrat = new JLabel("Type de contrat :");
        GridBagConstraints gbc_lblTypeContrat = new GridBagConstraints();
        gbc_lblTypeContrat.anchor = GridBagConstraints.EAST;
        gbc_lblTypeContrat.insets = new Insets(0, 0, 0, 5);
        gbc_lblTypeContrat.gridx = 0;
        gbc_lblTypeContrat.gridy = 2;
        contentPanel.add(lblTypeContrat, gbc_lblTypeContrat);

        lblTxtTypeContrat = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtTypeContrat = new GridBagConstraints();
        gbc_lblTxtTypeContrat.anchor = GridBagConstraints.WEST;
        gbc_lblTxtTypeContrat.gridx = 1;
        gbc_lblTxtTypeContrat.gridy = 2;
        contentPanel.add(lblTxtTypeContrat, gbc_lblTxtTypeContrat);

        // Bouton Annuler en bas
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton cancelButton = new JButton("Annuler");
        buttonPane.add(cancelButton);

        gest.gestionAnnuler(cancelButton);
        gest.chargeDonnee();
    }

    // Setters pour afficher les informations de l'assurance
    public void setNumeroContrat(String numContrat) { lblTxtNumeroContrat.setText(numContrat); }
    public void setAnneeContrat(String annee) { lblTxtAnneeContrat.setText(annee); }
    public void setTypeContrat(String type) { lblTxtTypeContrat.setText(type); }
}
