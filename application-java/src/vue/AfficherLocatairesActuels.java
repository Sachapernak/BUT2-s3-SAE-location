package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherLocataireActuel;
import controleur.GestionChampsLocataireActuel;
import controleur.GestionChampsMontantAfficherLocataire; // Si nécessaire
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Cursor;

/**
 * Vue permettant d'afficher et de gérer les locataires actuels. <br/>
 * Comporte :
 * <ul>
 *     <li>Une table listant les locataires actuels</li>
 *     <li>Une table listant les biens loués</li>
 *     <li>Des champs d'informations personnelles</li>
 *     <li>Des champs pour provisions/caution</li>
 *     <li>Des boutons pour ajouter, modifier, résilier, etc.</li>
 * </ul>
 * Les contrôleurs associés gèrent la logique d'accès aux données :
 * <ul>
 *     <li>{@link GestionAfficherLocataireActuel}</li>
 *     <li>{@link GestionChampsLocataireActuel}</li>
 *     <li>{@link GestionChampsMontantAfficherLocataire}</li>
 * </ul>
 */
public class AfficherLocatairesActuels extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Contrôleurs
    private final transient GestionAfficherLocataireActuel gestionClic;
    private final transient GestionChampsLocataireActuel gestionChampsLoc;
    private final transient GestionChampsMontantAfficherLocataire gestionChampsMontant;

    // Composants UI
    private JTable tableLocatairesActuels;
    private JTable tableBiensLoues;

    private JTextField textFieldDateDeNaissance;
    private JTextField textFieldAdressePerso;
    private JTextField textFieldTel;
    private JTextField textFieldMail;
    private JTextField textFieldProvisionPourCharge;
    private JTextField textFieldCaution;

    /**
     * Constructeur. Initialise la fenêtre et configure les contrôleurs.
     */
    public AfficherLocatairesActuels() {

        // Instanciation des contrôleurs
        this.gestionClic = new GestionAfficherLocataireActuel(this);
        this.gestionChampsLoc = new GestionChampsLocataireActuel(this);
        this.gestionChampsMontant = new GestionChampsMontantAfficherLocataire(this);

        // Configuration de la fenêtre
        setBounds(25, 25, 670, 490);
        getContentPane().setLayout(null);
        setClosable(true);
        setTitle("Locataires actuels");

        // Titre principal
        JLabel lblTitre = new JLabel("Les locataires actuels");
        lblTitre.setForeground(new Color(70, 130, 180));
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitre.setBounds(0, 10, 658, 39);
        getContentPane().add(lblTitre);

        // Panneau des locataires (tableau)
        JPanel panelLocataires = new JPanel();
        panelLocataires.setBorder(new TitledBorder(
                null,
                "Les locataires",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                null,
                null
        ));
        panelLocataires.setBounds(10, 67, 302, 155);
        panelLocataires.setLayout(new BorderLayout(0, 0));
        getContentPane().add(panelLocataires);

        JScrollPane scrollPaneLocatairesActuels = new JScrollPane();
        panelLocataires.add(scrollPaneLocatairesActuels, BorderLayout.CENTER);

        // Table des locataires actuels
        tableLocatairesActuels = new JTable();
        tableLocatairesActuels.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null},
        		{null, null, null},
        		{null, null, null},
        	},
        	new String[] {
        		"Identifiant", "Nom", "Pr\u00E9nom"
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false, true, true
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        scrollPaneLocatairesActuels.setViewportView(tableLocatairesActuels);

        // Écouteur pour mise à jour des champs lorsqu'on sélectionne un locataire
        tableLocatairesActuels.getSelectionModel().addListSelectionListener(this.gestionChampsLoc);

        // Boutons : Modifier, Résilier, Ajouter
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(8, 226, 304, 31);
        getContentPane().add(panelBoutons);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(this.gestionClic);
        panelBoutons.add(btnModifier);

        JButton btnResilierBail = new JButton("Résilier le bail");
        btnResilierBail.addActionListener(this.gestionClic);
        panelBoutons.add(btnResilierBail);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(this.gestionClic);
        panelBoutons.add(btnAjouter);

        // Bouton Retour
        JPanel panelRetour = new JPanel();
        panelRetour.setBounds(559, 420, 89, 31);
        getContentPane().add(panelRetour);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(this.gestionClic);
        panelRetour.add(btnRetour);

        // Panneau d'informations personnelles
        JPanel panelInfosPerso = new JPanel();
        panelInfosPerso.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE, Color.GRAY),
                "Informations Personnelles",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                null,
                Color.BLACK
        ));
        panelInfosPerso.setBounds(328, 67, 320, 155);
        panelInfosPerso.setLayout(null);
        getContentPane().add(panelInfosPerso);

        JLabel lblDateDeNaissance = new JLabel("Date de naissance :");
        lblDateDeNaissance.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDateDeNaissance.setBounds(-4, 29, 136, 13);
        panelInfosPerso.add(lblDateDeNaissance);

        textFieldDateDeNaissance = new JTextField();
        lblDateDeNaissance.setLabelFor(textFieldDateDeNaissance);
        textFieldDateDeNaissance.setBounds(145, 26, 96, 19);
        panelInfosPerso.add(textFieldDateDeNaissance);

        JLabel lblAdressePerso = new JLabel("Adresse personnelle :");
        lblAdressePerso.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAdressePerso.setBounds(-14, 61, 146, 13);
        panelInfosPerso.add(lblAdressePerso);

        textFieldAdressePerso = new JTextField();
        lblAdressePerso.setLabelFor(textFieldAdressePerso);
        textFieldAdressePerso.setBounds(145, 58, 159, 19);
        panelInfosPerso.add(textFieldAdressePerso);

        JLabel lblTel = new JLabel("Téléphone :");
        lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTel.setBounds(-27, 90, 159, 13);
        panelInfosPerso.add(lblTel);

        textFieldTel = new JTextField();
        textFieldTel.setBounds(145, 87, 96, 19);
        panelInfosPerso.add(textFieldTel);

        JLabel lblMail = new JLabel("E-mail : ");
        lblMail.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMail.setBounds(87, 119, 45, 13);
        panelInfosPerso.add(lblMail);

        textFieldMail = new JTextField();
        textFieldMail.setBounds(145, 116, 159, 19);
        panelInfosPerso.add(textFieldMail);

        // Panneau d'informations des biens loués
        JPanel panelInfosBatiments = new JPanel();
        panelInfosBatiments.setBorder(new TitledBorder(
                null,
                "Location",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                null,
                null
        ));
        panelInfosBatiments.setBounds(10, 267, 638, 90);
        panelInfosBatiments.setLayout(new BorderLayout(0, 0));
        getContentPane().add(panelInfosBatiments);

        JScrollPane scrollPaneBiensLoues = new JScrollPane();
        panelInfosBatiments.add(scrollPaneBiensLoues, BorderLayout.CENTER);

        tableBiensLoues = new JTable();
        tableBiensLoues.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBiensLoues.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null, null, null, null, null, null},
            },
            new String[] {
                "Bail", "Date d'entrée", "Type", "Bâtiment", "Adresse complète", "Loyer", "Part de loyer", "Dernière régularisation"
            }
        ) {
            private static final long serialVersionUID = 1L;
            Class<?>[] columnTypes = new Class<?>[] {
                String.class, Object.class, String.class, String.class, 
                String.class, Object.class, Object.class, Object.class
            };
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPaneBiensLoues.setViewportView(tableBiensLoues);

        // Écouteur pour mise à jour de la provision/caution lorsqu'on sélectionne un bien
        tableBiensLoues.getSelectionModel().addListSelectionListener(this.gestionChampsMontant);

        // Panneau d'infos de paiement (provision, caution)
        JPanel panelInfosPaiement = new JPanel();
        panelInfosPaiement.setBounds(10, 367, 521, 39);
        panelInfosPaiement.setLayout(null);
        getContentPane().add(panelInfosPaiement);

        JLabel lblProvisionPourCharge = new JLabel("Provisions pour charges : ");
        lblProvisionPourCharge.setHorizontalAlignment(SwingConstants.RIGHT);
        lblProvisionPourCharge.setBounds(-11, 8, 178, 13);
        panelInfosPaiement.add(lblProvisionPourCharge);

        textFieldProvisionPourCharge = new JTextField();
        textFieldProvisionPourCharge.setBounds(181, 5, 96, 19);
        panelInfosPaiement.add(textFieldProvisionPourCharge);

        JLabel lblCaution = new JLabel("Montant caution :");
        lblCaution.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCaution.setBounds(278, 8, 127, 13);
        panelInfosPaiement.add(lblCaution);

        textFieldCaution = new JTextField();
        textFieldCaution.setBounds(415, 5, 96, 19);
        panelInfosPaiement.add(textFieldCaution);

        // Remplissage initial de la table des locataires
        this.gestionChampsLoc.remplirTableLocatairesActuels();
    }

    // -----------------------------------------------------------------------
    // Getters et Setters (pour le contrôleur)
    // -----------------------------------------------------------------------

    public JTable getTableLocatairesActuels() {
        return tableLocatairesActuels;
    }

    public void setTableLocatairesActuels(JTable tableLocatairesActuels) {
        this.tableLocatairesActuels = tableLocatairesActuels;
    }

    public JTable getTableBiensLoues() {
        return tableBiensLoues;
    }

    public void setTableBiensLoues(JTable tableBiensLoues) {
        this.tableBiensLoues = tableBiensLoues;
    }

    public JTextField getTextFieldDateDeNaissance() {
        return textFieldDateDeNaissance;
    }

    public void setTextFieldDateDeNaissance(JTextField textFieldDateDeNaissance) {
        this.textFieldDateDeNaissance = textFieldDateDeNaissance;
    }

    public JTextField getTextFieldAdressePerso() {
        return textFieldAdressePerso;
    }

    public void setTextFieldAdressePerso(JTextField textFieldAdressePerso) {
        this.textFieldAdressePerso = textFieldAdressePerso;
    }

    public JTextField getTextFieldTel() {
        return textFieldTel;
    }

    public void setTextFieldTel(JTextField textFieldTel) {
        this.textFieldTel = textFieldTel;
    }

    public JTextField getTextFieldMail() {
        return textFieldMail;
    }

    public void setTextFieldMail(JTextField textFieldMail) {
        this.textFieldMail = textFieldMail;
    }

    public JTextField getTextFieldProvisionPourCharge() {
        return textFieldProvisionPourCharge;
    }

    public void setTextFieldProvisionPourCharge(JTextField textFieldProvisionPourCharge) {
        this.textFieldProvisionPourCharge = textFieldProvisionPourCharge;
    }

    public JTextField getTextFieldCaution() {
        return textFieldCaution;
    }

    public void setTextFieldCaution(JTextField textFieldCaution) {
        this.textFieldCaution = textFieldCaution;
    }
    
    public String getSelectedIdBail() {
    	int row = tableBiensLoues.getSelectedRow();
    	if (row >= 0) {
    		return String.valueOf(tableBiensLoues.getModel().getValueAt(row, 0));
    	} else {
    		return "";
    	}
    }
    
    public String getSelectedIdLoc() {
    	int row = tableLocatairesActuels.getSelectedRow();
    	if (row >= 0) {
    		return String.valueOf(tableLocatairesActuels.getModel().getValueAt(row, 0));
    	} else {
    		return "";
    	}
    }

    // -----------------------------------------------------------------------
    // Méthodes utilitaires
    // -----------------------------------------------------------------------

    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     *
     * @param message le message d'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
                this,
                "Erreur : \n" + message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Permet d'afficher ou non un curseur d'attente pour signifier
     * qu'une tâche lourde (accès BD, calcul, etc.) est en cours.
     *
     * @param enAttente true pour un curseur d'attente, false pour le curseur par défaut
     */
    public void setCursorAttente(boolean enAttente) {
        setCursor(enAttente ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                            : Cursor.getDefaultCursor());
    }
}
