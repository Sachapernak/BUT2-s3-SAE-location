package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionVoirRegularisationCharges;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.Box;

/**
 * Fenêtre de dialogue pour visualiser le solde de tous les comptes d'un locataire.
 * Cette classe gère l'interface utilisateur et délègue le chargement des données au contrôleur associé.
 */
public class VoirRegularisationCharges extends JDialog {

    private static final String CHARGEMENT = "Chargement...";
    
	private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JButton btnQuitter;
    private JLabel lblNonLoc;
    private JTextField textFieldNomLoc;
    private JTextField textFieldPrenom;
    private JTextField textDateDebut;
    private JTextField textDateFin;
    private JTextField textFieldSousTotCharge;
    private JTextField textSousTotDEduc;
    private JTextField textFieldTotal;
    
    private String idLoc;
    private String idBail;
    private String dateDebut;
    private String dateFin;
    
    private GestionVoirRegularisationCharges gest;
    private JTable tableCharges;
    private JTable tableDeduc;
    private JTextField textFieldAdresse;

    private FenetrePrincipale fenPrincipale;
    
    /**
     * Constructeur de la fenêtre de dialogue.
     * 
     * @param idLoc identifiant du locataire
     * @param idBail identifiant du bail
     * @param dateDebut date de début de la période
     * @param dateFin date de fin de la période
     */
    public VoirRegularisationCharges(String idLoc, String idBail, String dateDebut, String dateFin, FenetrePrincipale fp) {
        this.idLoc = idLoc;
        this.idBail = idBail;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.fenPrincipale = fp;
        
        // Initialisation du contrôleur associé à cette vue
        this.gest = new GestionVoirRegularisationCharges(this);
        
        setBounds(100, 100, 810, 600);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{10, 0, 0, 100, 50, 48, 10, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 182, 0, 0, 50, 0, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);

        JLabel lblTitre = new JLabel("Régulariser les charges");
        GridBagConstraints gbcLblTitre = new GridBagConstraints();
        gbcLblTitre.gridwidth = 7;
        gbcLblTitre.insets = new Insets(0, 0, 5, 0);
        gbcLblTitre.gridx = 0;
        gbcLblTitre.gridy = 0;
        contentPanel.add(lblTitre, gbcLblTitre);

        // ─── Partie Locataire ───────────────────────────────────────────────────
        lblNonLoc = new JLabel("Nom :");
        GridBagConstraints gbcLblNonLoc = new GridBagConstraints();
        gbcLblNonLoc.insets = new Insets(0, 0, 5, 5);
        gbcLblNonLoc.anchor = GridBagConstraints.EAST;
        gbcLblNonLoc.gridx = 1;
        gbcLblNonLoc.gridy = 1;
        contentPanel.add(lblNonLoc, gbcLblNonLoc);

        textFieldNomLoc = new JTextField();
        textFieldNomLoc.setText(CHARGEMENT);
        textFieldNomLoc.setEditable(false);
        GridBagConstraints gbcTextFieldNomLoc = new GridBagConstraints();
        gbcTextFieldNomLoc.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldNomLoc.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldNomLoc.gridx = 2;
        gbcTextFieldNomLoc.gridy = 1;
        contentPanel.add(textFieldNomLoc, gbcTextFieldNomLoc);
        textFieldNomLoc.setColumns(10);

        JLabel lblPrenom = new JLabel("Prénom :");
        GridBagConstraints gbcLblPrenom = new GridBagConstraints();
        gbcLblPrenom.anchor = GridBagConstraints.EAST;
        gbcLblPrenom.insets = new Insets(0, 0, 5, 5);
        gbcLblPrenom.gridx = 3;
        gbcLblPrenom.gridy = 1;
        contentPanel.add(lblPrenom, gbcLblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setText(CHARGEMENT);
        textFieldPrenom.setEditable(false);
        GridBagConstraints gbcTextFieldPrenom = new GridBagConstraints();
        gbcTextFieldPrenom.gridwidth = 2;
        gbcTextFieldPrenom.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldPrenom.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldPrenom.gridx = 4;
        gbcTextFieldPrenom.gridy = 1;
        contentPanel.add(textFieldPrenom, gbcTextFieldPrenom);
        textFieldPrenom.setColumns(10);
        
        JLabel lblAdresse = new JLabel("Adresse : ");
        GridBagConstraints gbcLblAdresse = new GridBagConstraints();
        gbcLblAdresse.anchor = GridBagConstraints.EAST;
        gbcLblAdresse.insets = new Insets(0, 0, 5, 5);
        gbcLblAdresse.gridx = 1;
        gbcLblAdresse.gridy = 2;
        contentPanel.add(lblAdresse, gbcLblAdresse);
        
        textFieldAdresse = new JTextField();
        textFieldAdresse.setText(CHARGEMENT);
        textFieldAdresse.setEditable(false);
        GridBagConstraints gbcTextFieldAdresse = new GridBagConstraints();
        gbcTextFieldAdresse.gridwidth = 4;
        gbcTextFieldAdresse.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldAdresse.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldAdresse.gridx = 2;
        gbcTextFieldAdresse.gridy = 2;
        contentPanel.add(textFieldAdresse, gbcTextFieldAdresse);
        textFieldAdresse.setColumns(10);
        // ────────────────────────────────────────────────────────────────────────

        Component verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbcVerticalStrut = new GridBagConstraints();
        gbcVerticalStrut.insets = new Insets(0, 0, 5, 5);
        gbcVerticalStrut.gridx = 5;
        gbcVerticalStrut.gridy = 3;
        contentPanel.add(verticalStrut, gbcVerticalStrut);

        // ─── Partie Date ────────────────────────────────────────────────────────
        JLabel lblDate = new JLabel("Date :");
        GridBagConstraints gbcLblDate = new GridBagConstraints();
        gbcLblDate.anchor = GridBagConstraints.EAST;
        gbcLblDate.insets = new Insets(0, 0, 5, 5);
        gbcLblDate.gridx = 1;
        gbcLblDate.gridy = 4;
        contentPanel.add(lblDate, gbcLblDate);

        textDateDebut = new JTextField();
        textDateDebut.setText(CHARGEMENT);
        textDateDebut.setEditable(false);
        GridBagConstraints gbcTextDateDebut = new GridBagConstraints();
        gbcTextDateDebut.insets = new Insets(0, 0, 5, 5);
        gbcTextDateDebut.fill = GridBagConstraints.HORIZONTAL;
        gbcTextDateDebut.gridx = 2;
        gbcTextDateDebut.gridy = 4;
        contentPanel.add(textDateDebut, gbcTextDateDebut);
        textDateDebut.setColumns(10);

        JLabel lblAU = new JLabel("jusqu'au :");
        GridBagConstraints gbcLblAU = new GridBagConstraints();
        gbcLblAU.insets = new Insets(0, 0, 5, 5);
        gbcLblAU.anchor = GridBagConstraints.EAST;
        gbcLblAU.gridx = 3;
        gbcLblAU.gridy = 4;
        contentPanel.add(lblAU, gbcLblAU);

        textDateFin = new JTextField();
        textDateFin.setText(CHARGEMENT);
        textDateFin.setEditable(false);
        GridBagConstraints gbcTextDateFin = new GridBagConstraints();
        gbcTextDateFin.gridwidth = 2;
        gbcTextDateFin.fill = GridBagConstraints.HORIZONTAL;
        gbcTextDateFin.insets = new Insets(0, 0, 5, 5);
        gbcTextDateFin.gridx = 4;
        gbcTextDateFin.gridy = 4;
        contentPanel.add(textDateFin, gbcTextDateFin);
        textDateFin.setColumns(10);
        // ────────────────────────────────────────────────────────────────────────

        // ─── Partie Charges ─────────────────────────────────────────────────────
        JLabel lblCharge = new JLabel("Charges :");
        GridBagConstraints gbcLblCharge = new GridBagConstraints();
        gbcLblCharge.anchor = GridBagConstraints.NORTH;
        gbcLblCharge.insets = new Insets(0, 0, 5, 5);
        gbcLblCharge.gridx = 1;
        gbcLblCharge.gridy = 6;
        contentPanel.add(lblCharge, gbcLblCharge);

        JScrollPane scrollPaneCharges = new JScrollPane();
        scrollPaneCharges.setPreferredSize(new Dimension(2, 1));
        GridBagConstraints gbcScrollPaneCharges = new GridBagConstraints();
        gbcScrollPaneCharges.gridwidth = 4;
        gbcScrollPaneCharges.insets = new Insets(0, 0, 5, 5);
        gbcScrollPaneCharges.fill = GridBagConstraints.BOTH;
        gbcScrollPaneCharges.gridx = 2;
        gbcScrollPaneCharges.gridy = 6;
        contentPanel.add(scrollPaneCharges, gbcScrollPaneCharges);
        
        tableCharges = new JTable();
        tableCharges.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Date", "Nom", "Calcul (si applicable)", "montant"
            }
        ));
        tableCharges.getColumnModel().getColumn(0).setPreferredWidth(35);
        tableCharges.getColumnModel().getColumn(0).setMaxWidth(70);
        tableCharges.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableCharges.getColumnModel().getColumn(2).setPreferredWidth(180);
        tableCharges.getColumnModel().getColumn(3).setPreferredWidth(40);
        tableCharges.getColumnModel().getColumn(3).setMaxWidth(60);
        tableCharges.setPreferredScrollableViewportSize(new Dimension(500, 170));
        scrollPaneCharges.setViewportView(tableCharges);
                
        JLabel lblSousTotCharge = new JLabel("Sous-total :");
        GridBagConstraints gbcLblSousTotCharge = new GridBagConstraints();
        gbcLblSousTotCharge.anchor = GridBagConstraints.EAST;
        gbcLblSousTotCharge.insets = new Insets(0, 0, 5, 5);
        gbcLblSousTotCharge.gridx = 4;
        gbcLblSousTotCharge.gridy = 7;
        contentPanel.add(lblSousTotCharge, gbcLblSousTotCharge);
        
        textFieldSousTotCharge = new JTextField();
        textFieldSousTotCharge.setText(CHARGEMENT);
        textFieldSousTotCharge.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldSousTotCharge.setEditable(false);
        GridBagConstraints gbcTextFieldSousTotCharge = new GridBagConstraints();
        gbcTextFieldSousTotCharge.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldSousTotCharge.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldSousTotCharge.gridx = 5;
        gbcTextFieldSousTotCharge.gridy = 7;
        contentPanel.add(textFieldSousTotCharge, gbcTextFieldSousTotCharge);
        textFieldSousTotCharge.setColumns(10);
        // ────────────────────────────────────────────────────────────────────────

        Component verticalStrut2 = Box.createVerticalStrut(20);
        GridBagConstraints gbcVerticalStrut2 = new GridBagConstraints();
        gbcVerticalStrut2.insets = new Insets(0, 0, 5, 5);
        gbcVerticalStrut2.gridx = 5;
        gbcVerticalStrut2.gridy = 8;
        contentPanel.add(verticalStrut2, gbcVerticalStrut2);

        // ─── Partie Déductions ───────────────────────────────────────────────────
        JLabel lblDeduire = new JLabel("A déduire :");
        GridBagConstraints gbcLblDeduire = new GridBagConstraints();
        gbcLblDeduire.anchor = GridBagConstraints.NORTH;
        gbcLblDeduire.insets = new Insets(0, 0, 5, 5);
        gbcLblDeduire.gridx = 1;
        gbcLblDeduire.gridy = 9;
        contentPanel.add(lblDeduire, gbcLblDeduire);

        JScrollPane scrollPaneDeductions = new JScrollPane();
        GridBagConstraints gbcScrollPaneDeductions = new GridBagConstraints();
        gbcScrollPaneDeductions.gridwidth = 4;
        gbcScrollPaneDeductions.insets = new Insets(0, 0, 5, 5);
        gbcScrollPaneDeductions.fill = GridBagConstraints.BOTH;
        gbcScrollPaneDeductions.gridx = 2;
        gbcScrollPaneDeductions.gridy = 9;
        contentPanel.add(scrollPaneDeductions, gbcScrollPaneDeductions);
        
        tableDeduc = new JTable();
        tableDeduc.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Nom", "Calcul (si applicable)", "Montant"
            }
        ));
        tableDeduc.setPreferredScrollableViewportSize(new Dimension(450, 60));
        scrollPaneDeductions.setViewportView(tableDeduc);
                
        JLabel lblSousTotDeduc = new JLabel("Sous-total :");
        GridBagConstraints gbcLblSousTotDeduc = new GridBagConstraints();
        gbcLblSousTotDeduc.anchor = GridBagConstraints.EAST;
        gbcLblSousTotDeduc.insets = new Insets(0, 0, 5, 5);
        gbcLblSousTotDeduc.gridx = 4;
        gbcLblSousTotDeduc.gridy = 10;
        contentPanel.add(lblSousTotDeduc, gbcLblSousTotDeduc);
        
        textSousTotDEduc = new JTextField();
        textSousTotDEduc.setText(CHARGEMENT);
        textSousTotDEduc.setHorizontalAlignment(SwingConstants.RIGHT);
        textSousTotDEduc.setEditable(false);
        GridBagConstraints gbcTextSousTotDEduc = new GridBagConstraints();
        gbcTextSousTotDEduc.insets = new Insets(0, 0, 5, 5);
        gbcTextSousTotDEduc.fill = GridBagConstraints.HORIZONTAL;
        gbcTextSousTotDEduc.gridx = 5;
        gbcTextSousTotDEduc.gridy = 10;
        contentPanel.add(textSousTotDEduc, gbcTextSousTotDEduc);
        textSousTotDEduc.setColumns(10);
        // ────────────────────────────────────────────────────────────────────────

        Component verticalStrut1 = Box.createVerticalStrut(20);
        verticalStrut1.setPreferredSize(new Dimension(0, 10));
        verticalStrut1.setMinimumSize(new Dimension(0, 10));
        GridBagConstraints gbcVerticalStrut1 = new GridBagConstraints();
        gbcVerticalStrut1.insets = new Insets(0, 0, 5, 5);
        gbcVerticalStrut1.gridx = 5;
        gbcVerticalStrut1.gridy = 11;
        contentPanel.add(verticalStrut1, gbcVerticalStrut1);

        // ─── Partie Total ───────────────────────────────────────────────────────
        JLabel lblTotal = new JLabel("Total :");
        GridBagConstraints gbcLblTotal = new GridBagConstraints();
        gbcLblTotal.anchor = GridBagConstraints.EAST;
        gbcLblTotal.insets = new Insets(0, 0, 0, 5);
        gbcLblTotal.gridx = 4;
        gbcLblTotal.gridy = 12;
        contentPanel.add(lblTotal, gbcLblTotal);
                        
        textFieldTotal = new JTextField();
        textFieldTotal.setText(CHARGEMENT);
        textFieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldTotal.setEditable(false);
        GridBagConstraints gbcTextFieldTotal = new GridBagConstraints();
        gbcTextFieldTotal.insets = new Insets(0, 0, 0, 5);
        gbcTextFieldTotal.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldTotal.gridx = 5;
        gbcTextFieldTotal.gridy = 12;
        contentPanel.add(textFieldTotal, gbcTextFieldTotal);
        textFieldTotal.setColumns(10);
        // ────────────────────────────────────────────────────────────────────────

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnGenerer = new JButton("Générer ");

        buttonPane.add(btnGenerer);
        getRootPane().setDefaultButton(btnGenerer);

        btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> dispose());
        buttonPane.add(btnQuitter);
        
        // Initialisation des données de la vue via le contrôleur
        gest.gestionBtnGenerer(btnGenerer);
        gest.setInfoLoc();
        gest.setDates();
        gest.loadCharges();
        gest.loadDeduc();
        gest.loadSousTotaux();
    }



    // Getters et setters pour mettre à jour l'interface depuis le contrôleur

    public void setNomLoc(String nom) {
        textFieldNomLoc.setText(nom);
    }

    public void setPrenom(String prenom) {
        textFieldPrenom.setText(prenom);
    }

    public void setAdresse(String adresse) {
        textFieldAdresse.setText(adresse);
    }

    public void setDateDebut(String dateDebut) {
        textDateDebut.setText(dateDebut);
    }

    public void setDateFin(String dateFin) {
        textDateFin.setText(dateFin);
    }

    public void setSousTotCharge(String sousTotCharge) {
        textFieldSousTotCharge.setText(sousTotCharge);
    }

    public void setSousTotDeduc(String sousTotDeduc) {
        textSousTotDEduc.setText(sousTotDeduc);
    }

    public void setTotal(String total) {
        textFieldTotal.setText(total);
    }

    /**
     * Met à jour la table des charges avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table des charges
     */
    public void chargerTableCharges(List<String[]> liste) {
        String[] nomsColonnes = {"Date", "Nom", "Calcul (si applicable)", "montant"};
        chargerTable(liste, nomsColonnes, tableCharges);

        tableCharges.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableCharges.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableCharges.getColumnModel().getColumn(2).setPreferredWidth(180);
        tableCharges.getColumnModel().getColumn(3).setPreferredWidth(50);

        tableCharges.revalidate();
        tableCharges.repaint();
    }
    
    /**
     * Met à jour la table des déductions avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table des déductions
     */
    public void chargerTableDeduc(List<String[]> liste) {
        String[] nomsColonnes = {"Nom", "Calcul (si applicable)", "Montant"};
        chargerTable(liste, nomsColonnes, tableDeduc);

        tableDeduc.getColumnModel().getColumn(0).setPreferredWidth(200);
        tableDeduc.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableDeduc.getColumnModel().getColumn(2).setPreferredWidth(50);

        tableDeduc.revalidate();
        tableDeduc.repaint();
    }
    
    /**
     * Méthode utilitaire pour charger des données dans une JTable.
     * 
     * @param liste les données à afficher
     * @param nomsColonnes noms des colonnes de la table
     * @param table JTable à mettre à jour
     */
    private void chargerTable(List<String[]> liste, String[] nomsColonnes, JTable table) {
        DefaultTableModel model = new DefaultTableModel(nomsColonnes, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (String[] ligne : liste) {
            model.addRow(ligne);
        }
        
        table.setModel(model);
    }

    public String getIdLoc() {
        return idLoc;
    }

    public String getIdBail() {
        return idBail;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    /**
     * Met à jour le curseur de la fenêtre pour indiquer un état d'attente ou non.
     * 
     * @param enAttente vrai pour afficher le curseur d'attente, faux pour le curseur par défaut
     */
    public void setCursorAttente(boolean enAttente) {
        setCursor(enAttente ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) 
                            : Cursor.getDefaultCursor());
    }
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * 
     * @param message le message d'erreur à afficher
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
