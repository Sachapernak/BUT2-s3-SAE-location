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
    private JLabel lblTxtValUnitaire;


    /**
     * Crée la boîte de dialogue pour afficher les détails d'une charge indexée.
     * @param chargeIndex L'objet ChargeIndex dont les détails seront affichés.
     */
    public DetailChargeIndex(ChargeIndex chargeIndex) {
        this.gest = new GestionDetailChargeIndex(this, chargeIndex);

        setTitle("Détail de la charge indexée");
        setBounds(100, 100, 290, 250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{150, 0, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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

        // Ligne 1 : Date de relevé
        JLabel lblDateDeReleve = new JLabel("Date de relevé :");
        GridBagConstraints gbcLblDateDeReleve = new GridBagConstraints();
        gbcLblDateDeReleve.anchor = GridBagConstraints.EAST;
        gbcLblDateDeReleve.insets = new Insets(0, 0, 5, 5);
        gbcLblDateDeReleve.gridx = 0;
        gbcLblDateDeReleve.gridy = 1;
        contentPanel.add(lblDateDeReleve, gbcLblDateDeReleve);

        lblTxtDateDeReleve = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtDateDeReleve = new GridBagConstraints();
        gbcLblTxtDateDeReleve.anchor = GridBagConstraints.WEST;
        gbcLblTxtDateDeReleve.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtDateDeReleve.gridx = 1;
        gbcLblTxtDateDeReleve.gridy = 1;
        contentPanel.add(lblTxtDateDeReleve, gbcLblTxtDateDeReleve);

        // Ligne 2 : Date de relevé précédent
        JLabel lblDateRelevePrecedent = new JLabel("Date relevé précédent :");
        GridBagConstraints gbcLblDateRelevePrecedent = new GridBagConstraints();
        gbcLblDateRelevePrecedent.anchor = GridBagConstraints.EAST;
        gbcLblDateRelevePrecedent.insets = new Insets(0, 0, 5, 5);
        gbcLblDateRelevePrecedent.gridx = 0;
        gbcLblDateRelevePrecedent.gridy = 2;
        contentPanel.add(lblDateRelevePrecedent, gbcLblDateRelevePrecedent);

        lblTxtDateRelevePrecedent = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtDateRelevePrecedent = new GridBagConstraints();
        gbcLblTxtDateRelevePrecedent.anchor = GridBagConstraints.WEST;
        gbcLblTxtDateRelevePrecedent.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtDateRelevePrecedent.gridx = 1;
        gbcLblTxtDateRelevePrecedent.gridy = 2;
        contentPanel.add(lblTxtDateRelevePrecedent, gbcLblTxtDateRelevePrecedent);

        // Ligne 3 : Type
        JLabel lblType = new JLabel("Type :");
        GridBagConstraints gbcLblType = new GridBagConstraints();
        gbcLblType.anchor = GridBagConstraints.EAST;
        gbcLblType.insets = new Insets(0, 0, 5, 5);
        gbcLblType.gridx = 0;
        gbcLblType.gridy = 3;
        contentPanel.add(lblType, gbcLblType);

        lblTxtType = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtType = new GridBagConstraints();
        gbcLblTxtType.anchor = GridBagConstraints.WEST;
        gbcLblTxtType.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtType.gridx = 1;
        gbcLblTxtType.gridy = 3;
        contentPanel.add(lblTxtType, gbcLblTxtType);

        // Ligne 4 : Valeur compteur
        JLabel lblValeurCompteur = new JLabel("Valeur compteur :");
        GridBagConstraints gbcLblValeurCompteur = new GridBagConstraints();
        gbcLblValeurCompteur.anchor = GridBagConstraints.EAST;
        gbcLblValeurCompteur.insets = new Insets(0, 0, 5, 5);
        gbcLblValeurCompteur.gridx = 0;
        gbcLblValeurCompteur.gridy = 4;
        contentPanel.add(lblValeurCompteur, gbcLblValeurCompteur);

        lblTxtValeurCompteur = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtValeurCompteur = new GridBagConstraints();
        gbcLblTxtValeurCompteur.anchor = GridBagConstraints.WEST;
        gbcLblTxtValeurCompteur.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtValeurCompteur.gridx = 1;
        gbcLblTxtValeurCompteur.gridy = 4;
        contentPanel.add(lblTxtValeurCompteur, gbcLblTxtValeurCompteur);
        
        JLabel lblCoutUnitaire = new JLabel("Coût unitaire :");
        GridBagConstraints gbcLblCoutUnitaire = new GridBagConstraints();
        gbcLblCoutUnitaire.anchor = GridBagConstraints.EAST;
        gbcLblCoutUnitaire.insets = new Insets(0, 0, 5, 5);
        gbcLblCoutUnitaire.gridx = 0;
        gbcLblCoutUnitaire.gridy = 5;
        contentPanel.add(lblCoutUnitaire, gbcLblCoutUnitaire);
        
        lblTxtValUnitaire = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtValUnitaire = new GridBagConstraints();
        gbcLblTxtValUnitaire.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtValUnitaire.anchor = GridBagConstraints.WEST;
        gbcLblTxtValUnitaire.gridx = 1;
        gbcLblTxtValUnitaire.gridy = 5;
        contentPanel.add(lblTxtValUnitaire, gbcLblTxtValUnitaire);

        // Ligne 5 : Coût fixe
        JLabel lblCoutFixe = new JLabel("Coût fixe :");
        GridBagConstraints gbcLblCoutFixe = new GridBagConstraints();
        gbcLblCoutFixe.anchor = GridBagConstraints.EAST;
        gbcLblCoutFixe.insets = new Insets(0, 0, 5, 5);
        gbcLblCoutFixe.gridx = 0;
        gbcLblCoutFixe.gridy = 6;
        contentPanel.add(lblCoutFixe, gbcLblCoutFixe);

        lblTxtCoutFixe = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtCoutFixe = new GridBagConstraints();
        gbcLblTxtCoutFixe.anchor = GridBagConstraints.WEST;
        gbcLblTxtCoutFixe.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtCoutFixe.gridx = 1;
        gbcLblTxtCoutFixe.gridy = 6;
        contentPanel.add(lblTxtCoutFixe, gbcLblTxtCoutFixe);

        // Ligne 6 : Numéro de document
        JLabel lblNumDoc = new JLabel("Numéro de document :");
        GridBagConstraints gbcLblNumDoc = new GridBagConstraints();
        gbcLblNumDoc.anchor = GridBagConstraints.EAST;
        gbcLblNumDoc.insets = new Insets(0, 0, 5, 5);
        gbcLblNumDoc.gridx = 0;
        gbcLblNumDoc.gridy = 7;
        contentPanel.add(lblNumDoc, gbcLblNumDoc);

        lblTxtNumDoc = new JLabel("Chargement...");
        GridBagConstraints gbcLblTxtNumDoc = new GridBagConstraints();
        gbcLblTxtNumDoc.insets = new Insets(0, 0, 5, 0);
        gbcLblTxtNumDoc.anchor = GridBagConstraints.WEST;
        gbcLblTxtNumDoc.gridx = 1;
        gbcLblTxtNumDoc.gridy = 7;
        contentPanel.add(lblTxtNumDoc, gbcLblTxtNumDoc);

        // Bouton Annuler
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton cancelButton = new JButton("Annuler");
        buttonPane.add(cancelButton);
        gest.gestionAnnuler(cancelButton);
        gest.chargeDonnee();
    }
    
    
    //
    //   Getters et Setters
    //
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
    	lblTxtValUnitaire.setText(val); 	
    }
}
