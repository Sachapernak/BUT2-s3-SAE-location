package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionVoirSoldeToutCompte;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class VoirSoldeToutCompte extends JDialog {

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
    
    private GestionVoirSoldeToutCompte gest;
    private JTable tableCharges;
    private JTable tableDeduc;

    /**
     * Create the dialog.
     */
    public VoirSoldeToutCompte(String idLoc, String idBail, String dateDebut, String dateFin) {
    	
    	
    	this.idLoc = idLoc;
    	this.idBail = idBail;
    	this.dateDebut = dateDebut;
    	this.dateFin = dateFin;
    	
    	this.gest = new GestionVoirSoldeToutCompte(this);
    	
        setBounds(100, 100, 810, 600);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{10, 0, 0, 0, 0, 10, 0};
        gblContentPanel.rowHeights = new int[]{0, 0, 0, 170, 0, 170, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);

        JLabel lblTitre = new JLabel("Solde de tout comtpes");
        GridBagConstraints gbcLblTitre = new GridBagConstraints();
        gbcLblTitre.gridwidth = 6;
        gbcLblTitre.insets = new Insets(0, 0, 5, 0);
        gbcLblTitre.gridx = 0;
        gbcLblTitre.gridy = 0;
        contentPanel.add(lblTitre, gbcLblTitre);

        lblNonLoc = new JLabel("Nom :");
        GridBagConstraints gbcLblNonLoc = new GridBagConstraints();
        gbcLblNonLoc.insets = new Insets(0, 0, 5, 5);
        gbcLblNonLoc.anchor = GridBagConstraints.EAST;
        gbcLblNonLoc.gridx = 1;
        gbcLblNonLoc.gridy = 1;
        contentPanel.add(lblNonLoc, gbcLblNonLoc);

        textFieldNomLoc = new JTextField();
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
        textFieldPrenom.setEditable(false);
        GridBagConstraints gbcTextFieldPrenom = new GridBagConstraints();
        gbcTextFieldPrenom.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldPrenom.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldPrenom.gridx = 4;
        gbcTextFieldPrenom.gridy = 1;
        contentPanel.add(textFieldPrenom, gbcTextFieldPrenom);
        textFieldPrenom.setColumns(10);

        JLabel lblDate = new JLabel("Date du :");
        GridBagConstraints gbcLblDate = new GridBagConstraints();
        gbcLblDate.anchor = GridBagConstraints.EAST;
        gbcLblDate.insets = new Insets(0, 0, 5, 5);
        gbcLblDate.gridx = 1;
        gbcLblDate.gridy = 2;
        contentPanel.add(lblDate, gbcLblDate);

        textDateDebut = new JTextField();
        textDateDebut.setEditable(false);
        GridBagConstraints gbcTextDateDebut = new GridBagConstraints();
        gbcTextDateDebut.insets = new Insets(0, 0, 5, 5);
        gbcTextDateDebut.fill = GridBagConstraints.HORIZONTAL;
        gbcTextDateDebut.gridx = 2;
        gbcTextDateDebut.gridy = 2;
        contentPanel.add(textDateDebut, gbcTextDateDebut);
        textDateDebut.setColumns(10);

        JLabel lblAU = new JLabel("au :");
        GridBagConstraints gbcLblAU = new GridBagConstraints();
        gbcLblAU.insets = new Insets(0, 0, 5, 5);
        gbcLblAU.anchor = GridBagConstraints.EAST;
        gbcLblAU.gridx = 3;
        gbcLblAU.gridy = 2;
        contentPanel.add(lblAU, gbcLblAU);

        textDateFin = new JTextField();
        textDateFin.setEditable(false);
        GridBagConstraints gbcTextDateFin = new GridBagConstraints();
        gbcTextDateFin.insets = new Insets(0, 0, 5, 5);
        gbcTextDateFin.fill = GridBagConstraints.HORIZONTAL;
        gbcTextDateFin.gridx = 4;
        gbcTextDateFin.gridy = 2;
        contentPanel.add(textDateFin, gbcTextDateFin);
        textDateFin.setColumns(10);

        JLabel lblCharge = new JLabel("Charges :");
        GridBagConstraints gbcLblCharge = new GridBagConstraints();
        gbcLblCharge.anchor = GridBagConstraints.NORTH;
        gbcLblCharge.insets = new Insets(0, 0, 5, 5);
        gbcLblCharge.gridx = 1;
        gbcLblCharge.gridy = 3;
        contentPanel.add(lblCharge, gbcLblCharge);

        JScrollPane scrollPaneCharges = new JScrollPane();
        GridBagConstraints gbcScrollPaneCharges = new GridBagConstraints();
        gbcScrollPaneCharges.gridwidth = 3;
        gbcScrollPaneCharges.insets = new Insets(0, 0, 5, 5);
        gbcScrollPaneCharges.fill = GridBagConstraints.BOTH;
        gbcScrollPaneCharges.gridx = 2;
        gbcScrollPaneCharges.gridy = 3;
        contentPanel.add(scrollPaneCharges, gbcScrollPaneCharges);
        
        tableCharges = new JTable();
        tableCharges.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
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
        gbcLblSousTotCharge.gridx = 3;
        gbcLblSousTotCharge.gridy = 4;
        contentPanel.add(lblSousTotCharge, gbcLblSousTotCharge);

        textFieldSousTotCharge = new JTextField();
        textFieldSousTotCharge.setEditable(false);
        GridBagConstraints gbcTextFieldSousTotCharge = new GridBagConstraints();
        gbcTextFieldSousTotCharge.insets = new Insets(0, 0, 5, 5);
        gbcTextFieldSousTotCharge.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldSousTotCharge.gridx = 4;
        gbcTextFieldSousTotCharge.gridy = 4;
        contentPanel.add(textFieldSousTotCharge, gbcTextFieldSousTotCharge);
        textFieldSousTotCharge.setColumns(10);

        JLabel lblDeduire = new JLabel("A déduire :");
        GridBagConstraints gbcLblDeduire = new GridBagConstraints();
        gbcLblDeduire.anchor = GridBagConstraints.NORTH;
        gbcLblDeduire.insets = new Insets(0, 0, 5, 5);
        gbcLblDeduire.gridx = 1;
        gbcLblDeduire.gridy = 5;
        contentPanel.add(lblDeduire, gbcLblDeduire);

        JScrollPane scrollPaneDeductions = new JScrollPane();
        GridBagConstraints gbcScrollPaneDeductions = new GridBagConstraints();
        gbcScrollPaneDeductions.gridwidth = 3;
        gbcScrollPaneDeductions.insets = new Insets(0, 0, 5, 5);
        gbcScrollPaneDeductions.fill = GridBagConstraints.BOTH;
        gbcScrollPaneDeductions.gridx = 2;
        gbcScrollPaneDeductions.gridy = 5;
        contentPanel.add(scrollPaneDeductions, gbcScrollPaneDeductions);
        
        tableDeduc = new JTable();
        tableDeduc.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Nom", "Calcul (si applicable)", "Montant"
        	}
        ));
        tableDeduc.setPreferredScrollableViewportSize(new Dimension(450, 170));
        scrollPaneDeductions.setViewportView(tableDeduc);

        JLabel lblSousTotDeduc = new JLabel("Sous-total :");
        GridBagConstraints gbcLblSousTotDeduc = new GridBagConstraints();
        gbcLblSousTotDeduc.anchor = GridBagConstraints.EAST;
        gbcLblSousTotDeduc.insets = new Insets(0, 0, 5, 5);
        gbcLblSousTotDeduc.gridx = 3;
        gbcLblSousTotDeduc.gridy = 6;
        contentPanel.add(lblSousTotDeduc, gbcLblSousTotDeduc);

        textSousTotDEduc = new JTextField();
        textSousTotDEduc.setEditable(false);
        GridBagConstraints gbcTextSousTotDEduc = new GridBagConstraints();
        gbcTextSousTotDEduc.insets = new Insets(0, 0, 5, 5);
        gbcTextSousTotDEduc.fill = GridBagConstraints.HORIZONTAL;
        gbcTextSousTotDEduc.gridx = 4;
        gbcTextSousTotDEduc.gridy = 6;
        contentPanel.add(textSousTotDEduc, gbcTextSousTotDEduc);
        textSousTotDEduc.setColumns(10);

        JLabel lblTotal = new JLabel("Total :");
        GridBagConstraints gbcLblTotal = new GridBagConstraints();
        gbcLblTotal.anchor = GridBagConstraints.EAST;
        gbcLblTotal.insets = new Insets(0, 0, 0, 5);
        gbcLblTotal.gridx = 1;
        gbcLblTotal.gridy = 7;
        contentPanel.add(lblTotal, gbcLblTotal);

        textFieldTotal = new JTextField();
        textFieldTotal.setEditable(false);
        GridBagConstraints gbcTextFieldTotal = new GridBagConstraints();
        gbcTextFieldTotal.gridwidth = 3;
        gbcTextFieldTotal.insets = new Insets(0, 0, 0, 5);
        gbcTextFieldTotal.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldTotal.gridx = 2;
        gbcTextFieldTotal.gridy = 7;
        contentPanel.add(textFieldTotal, gbcTextFieldTotal);
        textFieldTotal.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnGenerer = new JButton("Générer ");
        btnGenerer.setActionCommand("OK");
        buttonPane.add(btnGenerer);
        getRootPane().setDefaultButton(btnGenerer);

        btnQuitter = new JButton("Quitter");
        btnQuitter.setActionCommand("Cancel");
        buttonPane.add(btnQuitter);
        
        gest.setInfoLoc();
        gest.setDates();
        gest.loadCharges();
        gest.loadDeduc();
        gest.loadSousTotaux();
    }

    // Getters et setters selon le modèle fourni

    // Pour les champs texte

    public void setNomLoc(String nom) {
        textFieldNomLoc.setText(nom);
    }

    public void setPrenom(String prenom) {
        textFieldPrenom.setText(prenom);
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
     * @param liste liste des lignes à afficher dans la table
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
     * Met à jour la table des charges avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table
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
     * Met à jour la table des charges avec de nouvelles données.
     * 
     * @param liste liste des lignes à afficher dans la table
     * @param nomsColonnes le nom des colonnes
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

	
    // Pour afficher un message d'erreur
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }


}
