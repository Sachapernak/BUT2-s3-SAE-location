package vue;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;

public class AfficherCharges extends JFrame {

    private static final long serialVersionUID = 1L;

    // Composants
    private JComboBox<String> comboBatiment;
    private JComboBox<String> comboLogement;
    private JComboBox<String> comboTypeCharge;
    private JTable tableCharges;
    private JButton btnQuitter;
    private JTextField txtRecherche;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AfficherCharges::new);
    }

    public AfficherCharges() {
        super("Gestion des Charges");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panneau principal en GridBagLayout
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        gbl_mainPanel.columnWidths = new int[]{90, 99, 0, 100, 0, 110, 0};
        JPanel mainPanel = new JPanel(gbl_mainPanel);

        // ---------------------------------------------------------------------
        // LIGNE 0 : Label + Combo Batiment, Label + Combo Logement, Label + Combo Type
        // ---------------------------------------------------------------------

        // Label "Bâtiment :"
        JLabel lblBatiment = new JLabel("Bâtiment :");
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0; 
        gbc1.gridy = 0; 
        gbc1.insets = new Insets(5, 5, 5, 5);
        gbc1.anchor = GridBagConstraints.LINE_END; 
        mainPanel.add(lblBatiment, gbc1);

        // Combo Bâtiment
        comboBatiment = new JComboBox<>(new String[]{"Bâtiment A", "Bâtiment B", "Bâtiment C"});
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(comboBatiment, gbc2);

        // Label "Logement :"
        JLabel lblLogement = new JLabel("Logement :");
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.insets = new Insets(5, 5, 5, 5);
        gbc3.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(lblLogement, gbc3);

        // Combo Logement
        comboLogement = new JComboBox<>(new String[]{});
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 3;
        gbc4.gridy = 0;
        gbc4.insets = new Insets(5, 5, 5, 5);
        gbc4.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(comboLogement, gbc4);

        // Label "Type de charge :"
        JLabel lblCharge = new JLabel("Type de charge :");
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 4;
        gbc5.gridy = 0;
        gbc5.insets = new Insets(5, 5, 5, 5);
        gbc5.anchor = GridBagConstraints.EAST;
        mainPanel.add(lblCharge, gbc5);

        // Combo Type de Charge
        comboTypeCharge = new JComboBox<>(new String[]{});
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.anchor = GridBagConstraints.WEST;
        gbc6.gridx = 5;
        gbc6.gridy = 0;
        gbc6.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(comboTypeCharge, gbc6);

        // ---------------------------------------------------------------------
        // LIGNE 2 : Table avec la liste des Charges (dans un JScrollPane)
        // ---------------------------------------------------------------------
        String[] columnNames = {"ID", "Description", "Montant", "Date"};
        Object[][] data = {
            {1, "Loyer",        700.0, "2025-01-01"},
            {2, "Eau",          30.0,  "2025-01-02"},
            {3, "Électricité",  60.0,  "2025-01-03"}
        };
                
                        // ---------------------------------------------------------------------
                        // LIGNE 1 : Barre de recherche
                        // ---------------------------------------------------------------------
                        JLabel lblRecherche = new JLabel("Recherche :");
                        GridBagConstraints gbc7 = new GridBagConstraints();
                        gbc7.gridx = 0;
                        gbc7.gridy = 1;
                        gbc7.insets = new Insets(5, 5, 5, 5);
                        gbc7.anchor = GridBagConstraints.LINE_END;
                        mainPanel.add(lblRecherche, gbc7);
        
                // Champ de texte pour la recherche
                txtRecherche = new JTextField(15);
                GridBagConstraints gbc8 = new GridBagConstraints();
                gbc8.gridx = 1;
                gbc8.gridy = 1;
                gbc8.gridwidth = 5;   // s'étend sur 5 colonnes (de 1 à 5)
                gbc8.fill = GridBagConstraints.HORIZONTAL;
                gbc8.weightx = 1.0;
                gbc8.insets = new Insets(5, 5, 5, 5);
                mainPanel.add(txtRecherche, gbc8);
        tableCharges = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(tableCharges);
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.gridx = 0;
        gbc9.gridy = 2;
        gbc9.gridwidth = 7;   // étend sur 6 colonnes
        gbc9.fill = GridBagConstraints.BOTH;
        gbc9.weightx = 1.0;   // On laisse de l'extension horizontale
        gbc9.weighty = 1.0;   // La table doit pouvoir s'étendre verticalement
        gbc9.insets = new Insets(5, 5, 5, 0);
        mainPanel.add(scrollPane, gbc9);

        // ---------------------------------------------------------------------
        // Paramétrage final de la fenêtre
        // ---------------------------------------------------------------------
        setContentPane(mainPanel);
                                        
                                                // ---------------------------------------------------------------------
                                                // LIGNE 3 : Bouton Quitter
                                                // ---------------------------------------------------------------------
                                                btnQuitter = new JButton("Quitter");
                                                GridBagConstraints gbc10 = new GridBagConstraints();
                                                gbc10.gridx = 5;
                                                gbc10.gridy = 3;
                                                gbc10.fill = GridBagConstraints.NONE;
                                                gbc10.weightx = 0;
                                                gbc10.weighty = 0;
                                                gbc10.anchor = GridBagConstraints.EAST;
                                                gbc10.insets = new Insets(5, 5, 10, 5);
                                                mainPanel.add(btnQuitter, gbc10);
        setSize(630, 572);
        setLocationRelativeTo(null); // Centre la fenêtre
        setVisible(true);

        this.comboLogement.setEnabled(false);
        
        setListBatiment(new ArrayList<>(Arrays.asList("test1", "test2")));
        
    }

    public void setListBatiment(List<String> batiments) {
    	this.comboBatiment.setModel(new DefaultComboBoxModel(batiments.toArray()));

    }
    
    public String getSelectBatiment() {
    	return (String) this.comboBatiment.getSelectedItem();
    }
    
    public void setListLogement(List<String> logements) {
    	this.comboLogement.setModel(new DefaultComboBoxModel(logements.toArray()));
    	
    }
    
    public String getSelectLogement() {
    	return (String) this.comboLogement.getSelectedItem();
    }
    
    public void setListCharges(List<String> charges) {
    	this.comboTypeCharge.setModel(new DefaultComboBoxModel(charges.toArray()));
    }
    
    public String getSelectCharge() {
    	return (String) this.comboTypeCharge.getSelectedItem();
    }
    
    public String getValRecherche() {
    	return this.txtRecherche.getText();
    }
    
    
}
