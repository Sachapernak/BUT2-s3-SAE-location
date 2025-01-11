package vue;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import controleur.GestionChargerLoyer;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;

/**
 * Vue permettant de charger des loyers depuis un fichier CSV.
 * Affiche un bouton "Parcourir" pour sélectionner le fichier, 
 * puis charge les données et les affiche dans la JTable.
 */
public class ChargerLoyers extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanelPrincipal = new JPanel();
    private JTextField textFieldLienFichier;
    private JTable tableLoyer;
    private JLabel lblNbLoyers;
    private JLabel lblFichier;
    private JButton btnParcourirFichier;
    private JButton btnCharger;
    private JButton btnAnnuler;
    
    // Contrôleur pour la logique de parsing CSV
    private final GestionChargerLoyer gestionnaireCharger;
    private JButton btnAjouter;
    private JButton btnSupprimer;
    private JScrollPane scrollPaneLoyers;

    /**
     * Lance l'application en mode autonome (pour test).
     */
    public static void main(String[] args) {
        try {
            ChargerLoyers dialog = new ChargerLoyers();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur : création de la fenêtre et positionnement des composants.
     */
    public ChargerLoyers() {
        // Instancie le contrôleur
        this.gestionnaireCharger = new GestionChargerLoyer(this);

        setBounds(100, 100, 530, 381);
        getContentPane().setLayout(new BorderLayout());
        contentPanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanelPrincipal, BorderLayout.CENTER);

        // Configuration du layout principal
        GridBagLayout gbl_contentPanelPrincipal = new GridBagLayout();
        gbl_contentPanelPrincipal.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPanelPrincipal.rowHeights = new int[]{0, 0, 0, 100, 0, 0};
        gbl_contentPanelPrincipal.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanelPrincipal.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPanelPrincipal.setLayout(gbl_contentPanelPrincipal);

        // Label principal
        JLabel lblCharger = new JLabel("Charger Loyers");
        GridBagConstraints gbc_lblCharger = new GridBagConstraints();
        gbc_lblCharger.gridwidth = 3;
        gbc_lblCharger.insets = new Insets(0, 0, 5, 0);
        gbc_lblCharger.gridx = 0;
        gbc_lblCharger.gridy = 0;
        contentPanelPrincipal.add(lblCharger, gbc_lblCharger);

        // Label "Fichier"
        lblFichier = new JLabel("Fichier : ");
        GridBagConstraints gbc_lblFichier = new GridBagConstraints();
        gbc_lblFichier.insets = new Insets(0, 0, 5, 5);
        gbc_lblFichier.anchor = GridBagConstraints.EAST;
        gbc_lblFichier.gridx = 0;
        gbc_lblFichier.gridy = 1;
        contentPanelPrincipal.add(lblFichier, gbc_lblFichier);

        // Champ texte pour le lien du fichier
        textFieldLienFichier = new JTextField();
        GridBagConstraints gbc_textFieldLienFichier = new GridBagConstraints();
        gbc_textFieldLienFichier.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldLienFichier.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldLienFichier.gridx = 1;
        gbc_textFieldLienFichier.gridy = 1;
        contentPanelPrincipal.add(textFieldLienFichier, gbc_textFieldLienFichier);
        textFieldLienFichier.setColumns(10);

        // Bouton "Parcourir"
        btnParcourirFichier = new JButton("Parcourir");
        GridBagConstraints gbc_btnParcourirFichier = new GridBagConstraints();
        gbc_btnParcourirFichier.insets = new Insets(0, 0, 5, 0);
        gbc_btnParcourirFichier.gridx = 2;
        gbc_btnParcourirFichier.gridy = 1;
        contentPanelPrincipal.add(btnParcourirFichier, gbc_btnParcourirFichier);

        // Label "Nb loyers"
        lblNbLoyers = new JLabel("0");
        GridBagConstraints gbc_lblNbLoyers = new GridBagConstraints();
        gbc_lblNbLoyers.anchor = GridBagConstraints.EAST;
        gbc_lblNbLoyers.insets = new Insets(0, 0, 5, 5);
        gbc_lblNbLoyers.gridx = 0;
        gbc_lblNbLoyers.gridy = 2;
        contentPanelPrincipal.add(lblNbLoyers, gbc_lblNbLoyers);

        JLabel lblLoyersAAjouter = new JLabel("loyer(s) à ajouter : ");
        GridBagConstraints gbc_lblLoyersAAjouter = new GridBagConstraints();
        gbc_lblLoyersAAjouter.anchor = GridBagConstraints.WEST;
        gbc_lblLoyersAAjouter.insets = new Insets(0, 0, 5, 5);
        gbc_lblLoyersAAjouter.gridx = 1;
        gbc_lblLoyersAAjouter.gridy = 2;
        contentPanelPrincipal.add(lblLoyersAAjouter, gbc_lblLoyersAAjouter);

        // Tableau pour afficher les loyers chargés
        scrollPaneLoyers = new JScrollPane();
        scrollPaneLoyers.setPreferredSize(new Dimension(100, 200));
        GridBagConstraints gbc_scrollPaneLoyers = new GridBagConstraints();
        gbc_scrollPaneLoyers.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPaneLoyers.gridwidth = 3;
        gbc_scrollPaneLoyers.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneLoyers.gridx = 0;
        gbc_scrollPaneLoyers.gridy = 3;
        contentPanelPrincipal.add(scrollPaneLoyers, gbc_scrollPaneLoyers);

        // Configuration du modèle de table : 5 colonnes
        tableLoyer = new JTable();
        tableLoyer.setModel(new DefaultTableModel(
        	null,
        	new String[] {
        		"ID Logement", "ID Locataire", "Date", "Montant total", "NumDoc"
        	}
        ));
        scrollPaneLoyers.setViewportView(tableLoyer);

        // Panel bas : Boutons "Charger" et "Annuler"
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        btnSupprimer = new JButton("Supprimer ligne");

        buttonPane.add(btnSupprimer);
        
        btnAjouter = new JButton("Ajouter ligne");

        buttonPane.add(btnAjouter);

        btnCharger = new JButton("Charger");
        btnCharger.setActionCommand("OK");
        buttonPane.add(btnCharger);
        getRootPane().setDefaultButton(btnCharger);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setActionCommand("Cancel");
        buttonPane.add(btnAnnuler);

        // --- Ajout des action listeners ---
        
        
        gestionnaireCharger.gestionParcourirFichier(btnParcourirFichier);
        gestionnaireCharger.gestionCharger(btnCharger);
        gestionnaireCharger.gestionAnnuler(btnAnnuler);
        gestionnaireCharger.gestionBtnAjouter(btnAjouter);
        gestionnaireCharger.gestionBtnSupprimer(btnSupprimer);
    }



	
	public void setTextLienFichier(String text) {
		textFieldLienFichier.setText(text);
	}

    /**
     * Met à jour la JTable en affichant la liste de loyers.
     * Met également à jour le label lblNbLoyers avec la taille de la liste.
     *
     * @param data liste de données [idLog, idLoc, date, montantTotal, numDoc]
     */
    public void afficherDonneesLoyer(List<List<String>> data) {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        model.setRowCount(0); // Nettoyage des lignes existantes

        for (List<String> row : data) {
            model.addRow(row.toArray());
        }
        
        // Mise à jour du label indiquant le nombre de loyers
        setTxtNbLoyers(String.valueOf(data.size()));
    }
    
    public void setTxtNbLoyers(String valeur) {
    	lblNbLoyers.setText(valeur);
    }
    
    public void ajouterNbLoyers(int valeur) {
    	lblNbLoyers.setText(String.valueOf(Integer.valueOf(lblNbLoyers.getText()) + valeur));
    }
    
    /**
     * @return Le chemin du fichier indiqué dans le champ texte (si besoin)
     */
    public String getLienFichier() {
        return textFieldLienFichier.getText();
    }
    
    /**
     * Récupère les données de la JTable et les retourne sous forme de List<List<String>>.
     * Ignore les lignes vides ou celles qui ne sont pas complètement remplies.
     *
     * @return List<List<String>> contenant les données valides de la JTable
     */
    public List<List<String>> getListsTable() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        int rowCount = model.getRowCount();


        List<List<String>> validRows = new ArrayList<>();

        // Parcours des lignes du tableau
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {

        	List<String> rowData;
        	
        	rowData = getLigneIfValid(rowIndex);
        	
        	if (!rowData.isEmpty()) {
        		validRows.add(rowData);
        	}


        }

        return validRows;
    }
    
    public List<String> getLigneIfValid(int rowIndex) {
    	
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        boolean skipRow = false;  
        int colCount = model.getColumnCount();
        List<String> rowData = new ArrayList<>(colCount);
        
        // Parcours des colonnes de la ligne
        for (int colIndex = 0; colIndex < colCount; colIndex++) {
            Object cellValue = model.getValueAt(rowIndex, colIndex);
            if (cellValue == null || cellValue.toString().trim().isEmpty()) {
                skipRow = true;  // On ignore cette ligne si une cellule est vide
                break;
            }
            rowData.add(cellValue.toString().trim());
        }

        // Ajoute la ligne seulement si elle est complète
        if (!skipRow) {
            return rowData;
        }
    	return Collections.emptyList();
    }
    
    // Pour afficher un message d'erreur
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    public int getSelectLineIndex() {

    	return tableLoyer.getSelectedRow();
    }
    
    public void supprimerLigne(int ligne) {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
    	
    	if (ligne >= 0) {
    		model.removeRow(ligne);
    	} else if (nbLigneTable() > 0) {
    		model.removeRow(nbLigneTable() - 1);
    	}
        

    }
    
    public void ajouterLigneVide() {
        DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
        model.addRow(new String[] {null, null, null, null});

    }
    
    public int nbLigneTable() {
    	DefaultTableModel model = (DefaultTableModel) tableLoyer.getModel();
    	return model.getRowCount();
    }
    
    // Scroll a la toute fin de la scrollPane
    public void scrollToBottom() {
    	
        JScrollBar verticalBar = scrollPaneLoyers.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }
}
