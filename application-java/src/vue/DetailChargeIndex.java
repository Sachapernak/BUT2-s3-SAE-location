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

import controleur.GestionDetailChargeIndex;
import modele.ChargeIndex;

/**
 * Fenêtre de détail pour une Charge Indexée.
 */
public class DetailChargeIndex extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JLabel lblTxtId;
    private JLabel lblTxtDateDeReleve;
    private JLabel lblTxtDateRelevePrecedent;
    private JLabel lblTxtType;
    private JLabel lblTxtValeurCompteur;
    private JLabel lblTxtCoutFixe;
    private JLabel lblTxtNumDoc;

    private GestionDetailChargeIndex gest;
    private JLabel lblCoutUnitaire;

    /**
     * Crée la boîte de dialogue pour afficher les détails d'une charge indexée.
     * @param chargeIndex L'objet ChargeIndex dont les détails seront affichés.
     */
    public DetailChargeIndex(ChargeIndex chargeIndex) {
        this.gest = new GestionDetailChargeIndex(this, chargeIndex);

        setTitle("Détail de la charge indexée");
        setBounds(100, 100, 290, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{150, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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

        // Ligne 1 : Date de relevé
        JLabel lblDateDeReleve = new JLabel("Date de relevé :");
        GridBagConstraints gbc_lblDateDeReleve = new GridBagConstraints();
        gbc_lblDateDeReleve.anchor = GridBagConstraints.EAST;
        gbc_lblDateDeReleve.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateDeReleve.gridx = 0;
        gbc_lblDateDeReleve.gridy = 1;
        contentPanel.add(lblDateDeReleve, gbc_lblDateDeReleve);

        lblTxtDateDeReleve = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtDateDeReleve = new GridBagConstraints();
        gbc_lblTxtDateDeReleve.anchor = GridBagConstraints.WEST;
        gbc_lblTxtDateDeReleve.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtDateDeReleve.gridx = 1;
        gbc_lblTxtDateDeReleve.gridy = 1;
        contentPanel.add(lblTxtDateDeReleve, gbc_lblTxtDateDeReleve);

        // Ligne 2 : Date de relevé précédent
        JLabel lblDateRelevePrecedent = new JLabel("Date relevé précédent :");
        GridBagConstraints gbc_lblDateRelevePrecedent = new GridBagConstraints();
        gbc_lblDateRelevePrecedent.anchor = GridBagConstraints.EAST;
        gbc_lblDateRelevePrecedent.insets = new Insets(0, 0, 5, 5);
        gbc_lblDateRelevePrecedent.gridx = 0;
        gbc_lblDateRelevePrecedent.gridy = 2;
        contentPanel.add(lblDateRelevePrecedent, gbc_lblDateRelevePrecedent);

        lblTxtDateRelevePrecedent = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtDateRelevePrecedent = new GridBagConstraints();
        gbc_lblTxtDateRelevePrecedent.anchor = GridBagConstraints.WEST;
        gbc_lblTxtDateRelevePrecedent.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtDateRelevePrecedent.gridx = 1;
        gbc_lblTxtDateRelevePrecedent.gridy = 2;
        contentPanel.add(lblTxtDateRelevePrecedent, gbc_lblTxtDateRelevePrecedent);

        // Ligne 3 : Type
        JLabel lblType = new JLabel("Type :");
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.anchor = GridBagConstraints.EAST;
        gbc_lblType.insets = new Insets(0, 0, 5, 5);
        gbc_lblType.gridx = 0;
        gbc_lblType.gridy = 3;
        contentPanel.add(lblType, gbc_lblType);

        lblTxtType = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtType = new GridBagConstraints();
        gbc_lblTxtType.anchor = GridBagConstraints.WEST;
        gbc_lblTxtType.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtType.gridx = 1;
        gbc_lblTxtType.gridy = 3;
        contentPanel.add(lblTxtType, gbc_lblTxtType);

        // Ligne 4 : Valeur compteur
        JLabel lblValeurCompteur = new JLabel("Valeur compteur :");
        GridBagConstraints gbc_lblValeurCompteur = new GridBagConstraints();
        gbc_lblValeurCompteur.anchor = GridBagConstraints.EAST;
        gbc_lblValeurCompteur.insets = new Insets(0, 0, 5, 5);
        gbc_lblValeurCompteur.gridx = 0;
        gbc_lblValeurCompteur.gridy = 4;
        contentPanel.add(lblValeurCompteur, gbc_lblValeurCompteur);

        lblTxtValeurCompteur = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtValeurCompteur = new GridBagConstraints();
        gbc_lblTxtValeurCompteur.anchor = GridBagConstraints.WEST;
        gbc_lblTxtValeurCompteur.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtValeurCompteur.gridx = 1;
        gbc_lblTxtValeurCompteur.gridy = 4;
        contentPanel.add(lblTxtValeurCompteur, gbc_lblTxtValeurCompteur);
        
        lblCoutUnitaire = new JLabel("Coût unitaire :");
        GridBagConstraints gbc_lblCoutUnitaire = new GridBagConstraints();
        gbc_lblCoutUnitaire.anchor = GridBagConstraints.EAST;
        gbc_lblCoutUnitaire.insets = new Insets(0, 0, 5, 5);
        gbc_lblCoutUnitaire.gridx = 0;
        gbc_lblCoutUnitaire.gridy = 5;
        contentPanel.add(lblCoutUnitaire, gbc_lblCoutUnitaire);
        
        JLabel lblTxtValUnitaire = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtValUnitaire = new GridBagConstraints();
        gbc_lblTxtValUnitaire.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtValUnitaire.anchor = GridBagConstraints.WEST;
        gbc_lblTxtValUnitaire.gridx = 1;
        gbc_lblTxtValUnitaire.gridy = 5;
        contentPanel.add(lblTxtValUnitaire, gbc_lblTxtValUnitaire);

        // Ligne 5 : Coût fixe
        JLabel lblCoutFixe = new JLabel("Coût fixe :");
        GridBagConstraints gbc_lblCoutFixe = new GridBagConstraints();
        gbc_lblCoutFixe.anchor = GridBagConstraints.EAST;
        gbc_lblCoutFixe.insets = new Insets(0, 0, 5, 5);
        gbc_lblCoutFixe.gridx = 0;
        gbc_lblCoutFixe.gridy = 6;
        contentPanel.add(lblCoutFixe, gbc_lblCoutFixe);

        lblTxtCoutFixe = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtCoutFixe = new GridBagConstraints();
        gbc_lblTxtCoutFixe.anchor = GridBagConstraints.WEST;
        gbc_lblTxtCoutFixe.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtCoutFixe.gridx = 1;
        gbc_lblTxtCoutFixe.gridy = 6;
        contentPanel.add(lblTxtCoutFixe, gbc_lblTxtCoutFixe);

        // Ligne 6 : Numéro de document
        JLabel lblNumDoc = new JLabel("Numéro de document :");
        GridBagConstraints gbc_lblNumDoc = new GridBagConstraints();
        gbc_lblNumDoc.anchor = GridBagConstraints.EAST;
        gbc_lblNumDoc.insets = new Insets(0, 0, 5, 5);
        gbc_lblNumDoc.gridx = 0;
        gbc_lblNumDoc.gridy = 7;
        contentPanel.add(lblNumDoc, gbc_lblNumDoc);

        lblTxtNumDoc = new JLabel("Chargement...");
        GridBagConstraints gbc_lblTxtNumDoc = new GridBagConstraints();
        gbc_lblTxtNumDoc.insets = new Insets(0, 0, 5, 0);
        gbc_lblTxtNumDoc.anchor = GridBagConstraints.WEST;
        gbc_lblTxtNumDoc.gridx = 1;
        gbc_lblTxtNumDoc.gridy = 7;
        contentPanel.add(lblTxtNumDoc, gbc_lblTxtNumDoc);

        // Bouton Annuler
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton cancelButton = new JButton("Annuler");
        buttonPane.add(cancelButton);
        gest.gestionAnnuler(cancelButton);
        gest.chargeDonnee();
    }

    public void setId(String id) { 
    	lblTxtId.setText(id); 	
    }
    
    public void setDateDeReleve(String date) { 
    	lblTxtDateDeReleve.setText(date);	 
   	}
    
    public void setDateRelevePrecedent(String date) { 
    	lblTxtDateRelevePrecedent.setText(date); 	
    }
    
    public void setType(String type) { 
    	lblTxtType.setText(type); 
    }
    
    public void setValeurCompteur(String valeur) { 
    	lblTxtValeurCompteur.setText(valeur); 	
    }
    
    public void setCoutFixe(String cout) { 
    	lblTxtCoutFixe.setText(cout); 	
    }
    
    public void setNumDoc(String numDoc) { 
    	lblTxtNumDoc.setText(numDoc);  	
    }
    
    public void setValUnitaire(String val) {
    	lblCoutUnitaire.setText(val); 	
    }
}
