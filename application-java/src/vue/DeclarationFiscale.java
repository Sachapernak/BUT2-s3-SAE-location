package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionDeclarationFiscale;

public class DeclarationFiscale extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------
    // Composants UI
    // -------------------------------------------------------------------------
    private JTable tableBatiments;
    private final JComboBox<String> comboBoxAnnee;
    private JTextField textFieldTotal;
    
    // -------------------------------------------------------------------------
    // Contrôleur associé
    // -------------------------------------------------------------------------
    private GestionDeclarationFiscale gestionDecla;

    /**
     * Constructeur : Initialise la fenêtre et configure les composants.
     */
    public DeclarationFiscale() {
        
        this.gestionDecla = new GestionDeclarationFiscale(this);
        
        setBounds(25, 25, 670, 434);
        getContentPane().setLayout(null);
        
        // Titre principal
        JLabel lblTitre = new JLabel("Déclaration Fiscale");
        lblTitre.setForeground(new Color(70, 130, 180));
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitre.setBounds(0, 10, 658, 44);
        getContentPane().add(lblTitre);
        
        // Label et ComboBox pour l'année
        JLabel lblAnnee = new JLabel("Année :");
        lblAnnee.setBounds(26, 81, 66, 13);
        getContentPane().add(lblAnnee);

        comboBoxAnnee = new JComboBox<>();
        lblAnnee.setLabelFor(comboBoxAnnee);
        comboBoxAnnee.setBounds(88, 77, 136, 21);
        comboBoxAnnee.setModel(new DefaultComboBoxModel<>(new String[] {"Chargement..."}));
        getContentPane().add(comboBoxAnnee);
        
        // Panneau récapitulatif des bâtiments
        JPanel panelRecapBatiment = new JPanel();
        panelRecapBatiment.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, 
                new Color(255, 255, 255), new Color(160, 160, 160)),
                "R\u00E9capitulatif par b\u00E2timent", 
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelRecapBatiment.setBounds(26, 116, 580, 155);
        getContentPane().add(panelRecapBatiment);
        panelRecapBatiment.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPanelBatiment = new JScrollPane();
        panelRecapBatiment.add(scrollPanelBatiment, BorderLayout.CENTER);

        tableBatiments = new JTable();
        tableBatiments.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
            },
            new String[] {
                "Bâtiment", "Loyers encaissés", "Frais et charges", "Bénéfice(+) ou déficit(-)"
            }
        ));
        scrollPanelBatiment.setViewportView(tableBatiments);
        
        // Panneau bas : total bénéfices
        JPanel panelBas = new JPanel();
        panelBas.setBounds(26, 293, 580, 39);
        panelBas.setLayout(null);
        getContentPane().add(panelBas);
        
        JLabel lblTotal = new JLabel("Total Bénéfices :");
        lblTotal.setBounds(-24, 13, 127, 13);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        panelBas.add(lblTotal);
        
        textFieldTotal = new JTextField();
        textFieldTotal.setBounds(113, 10, 96, 19);
        panelBas.add(textFieldTotal);

        // Panneau bas : boutons
        JPanel panel = new JPanel();
        panel.setBounds(339, 362, 267, 31);
        getContentPane().add(panel);
        
        JButton btnGenerer = new JButton("Générer");
        btnGenerer.addActionListener(this.gestionDecla);
        panel.add(btnGenerer);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(this.gestionDecla);
        panel.add(btnRetour);
        
        // Configuration du contrôleur : actions initiales
        gestionDecla.gestionActionComboBoxAnnee(this.comboBoxAnnee);
        gestionDecla.remplirComboBoxAnnee();

    }
    
    // -------------------------------------------------------------------------
    // Méthodes d'aide pour le contrôleur et manipulation de la vue
    // -------------------------------------------------------------------------

    /**
     * @return Le texte du champ total des bénéfices.
     */
    public String getTextTotal() {
        return textFieldTotal.getText();
    }

    /**
     * Met à jour le champ "Total Bénéfices".
     * @param text La nouvelle valeur à afficher.
     */
    public void setTextTotal(String text) {
        this.textFieldTotal.setText(text);
    }

    /**
     * @return La table affichant les informations des bâtiments.
     */
    public JTable getTableBatiments() {
        return tableBatiments;
    }

    /**
     * Permet de remplacer la table de bâtiments (si besoin).
     * @param tableBatiment nouvelle table à associer.
     */
    public void setTableBatiment(JTable tableBatiment) {
        this.tableBatiments = tableBatiment;
    }

    /**
     * Remplit la ComboBox d'année avec la liste fournie.
     * @param annees la liste des années disponibles.
     */
    public void setComboBoxAnnee(List<String> annees) {
        comboBoxAnnee.removeAllItems();
        for(String annee : annees) {
            comboBoxAnnee.addItem(annee);
        }
    }

    /**
     * Récupère l'indice de la ligne sélectionnée dans la table des bâtiments.
     * @return L'indice de la ligne sélectionnée (ou -1 si aucune sélection).
     */
    public int getSelectedRow() {
        return tableBatiments.getSelectedRow();
    }

    /**
     * @return Le nombre de lignes présent dans la table des bâtiments.
     */
    public int getNbLignesTable() {
        return tableBatiments.getRowCount();
    }

    /**
     * @return Le nombre de colonnes présent dans la table des bâtiments.
     */
    public int getColumnCount() {
        return tableBatiments.getColumnCount();
    }

    /**
     * Charge (ou remplace) les données de la table des bâtiments.
     * <p>
     * Cette fonction vide d'abord la table, puis ajoute les nouvelles lignes
     * contenues dans la liste passée en paramètre.
     * </p>
     * @param list liste de lignes. Chaque ligne est un tableau de 4 colonnes :
     *             [ "Bâtiment", "Loyers encaissés", "Frais et charges", "Bénéfice ou déficit" ].
     */
    public void remplirTable(List<String[]> list) {
        DefaultTableModel model = (DefaultTableModel) this.tableBatiments.getModel();
        // Vider la table
        model.setRowCount(0);
        // Insérer les nouvelles données
        for (String[] row : list) {
            model.addRow(row);
        }
    }

    /**
     * Récupère la valeur située à la position spécifiée (ligne, colonne) dans la table des bâtiments.
     * @param ligne   L'indice de la ligne dans la table.
     * @param colonne L'indice de la colonne dans la table.
     * @return La valeur située à la position spécifiée.
     */
    public Object getValueAt(int ligne, int colonne) {
        return this.tableBatiments.getValueAt(ligne, colonne);
    }

    /**
     * Récupère l'élément actuellement sélectionné dans le JComboBox des années.
     * @return L'élément sélectionné (typiquement, une chaîne représentant l'année).
     */
    public Object getSelectedItemComboAnnee() {
        return comboBoxAnnee.getSelectedItem();
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec le message fourni.
     * @param message Message à afficher.
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Met la fenêtre en mode sablier (curseur d'attente).
     */
    public void setWaitCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    /**
     * Rétablit le curseur par défaut.
     */
    public void setDefaultCursor() {
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Récupère l'année sélectionnée sous forme de chaîne.
     * @return L'année sélectionnée.
     */
    public String getAnnee() {
        return String.valueOf(comboBoxAnnee.getSelectedItem());
    }
}
