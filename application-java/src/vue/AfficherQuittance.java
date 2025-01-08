package vue;

import javax.swing.*;

import controleur.GestionAfficherQuittance;

import java.awt.*;

public class AfficherQuittance extends JInternalFrame {

    private JTextField numeroDocField;
    private JTextField dateDocField;
    private JTextField montantField;
    private JTextField partChargesField;
    private String bien;
    private String locataire;
    private GestionAfficherQuittance gestionClic;

    public String getBien() {
        return bien;
    }

    public String getLocataire() {
        return locataire;
    }

    public String getNumeroDoc() {
        return numeroDocField.getText();
    }

    public String getDateDoc() {
        return dateDocField.getText();
    }

    public String getMontant() {
        return montantField.getText();
    }

    public String getPartCharges() {
        return partChargesField.getText();
    }

    public AfficherQuittance(String bien, String locataire) {
        super("Quittance de Loyer", true, true, true, true);
        setBounds(50, 50, 400, 300);
        setLayout(new GridBagLayout());
        this.bien = bien;
        this.locataire = locataire;
        
        this.gestionClic = new GestionAfficherQuittance(this);
        // Champ pour le numéro de document
        JLabel numeroDocLabel = new JLabel("Numéro de Document:");
        numeroDocField = new JTextField(20);

        GridBagConstraints gbcNumeroDocLabel = new GridBagConstraints();
        gbcNumeroDocLabel.gridx = 0;
        gbcNumeroDocLabel.gridy = 0;
        gbcNumeroDocLabel.insets = new Insets(5, 5, 5, 5);
        gbcNumeroDocLabel.anchor = GridBagConstraints.WEST;
        add(numeroDocLabel, gbcNumeroDocLabel);

        GridBagConstraints gbcNumeroDocField = new GridBagConstraints();
        gbcNumeroDocField.gridx = 1;
        gbcNumeroDocField.gridy = 0;
        gbcNumeroDocField.weightx = 1;
        gbcNumeroDocField.insets = new Insets(5, 5, 5, 5);
        gbcNumeroDocField.fill = GridBagConstraints.HORIZONTAL;
        add(numeroDocField, gbcNumeroDocField);

        // Champ pour la date du document
        JLabel dateDocLabel = new JLabel("Date du Document:");
        dateDocField = new JTextField(20);

        GridBagConstraints gbcDateDocLabel = new GridBagConstraints();
        gbcDateDocLabel.gridx = 0;
        gbcDateDocLabel.gridy = 1;
        gbcDateDocLabel.insets = new Insets(5, 5, 5, 5);
        gbcDateDocLabel.anchor = GridBagConstraints.WEST;
        add(dateDocLabel, gbcDateDocLabel);

        GridBagConstraints gbcDateDocField = new GridBagConstraints();
        gbcDateDocField.gridx = 1;
        gbcDateDocField.gridy = 1;
        gbcDateDocField.weightx = 1;
        gbcDateDocField.insets = new Insets(5, 5, 5, 5);
        gbcDateDocField.fill = GridBagConstraints.HORIZONTAL;
        add(dateDocField, gbcDateDocField);

        // Champ pour le montant
        JLabel montantLabel = new JLabel("Montant:");
        montantField = new JTextField(20);

        GridBagConstraints gbcMontantLabel = new GridBagConstraints();
        gbcMontantLabel.gridx = 0;
        gbcMontantLabel.gridy = 2;
        gbcMontantLabel.insets = new Insets(5, 5, 5, 5);
        gbcMontantLabel.anchor = GridBagConstraints.WEST;
        add(montantLabel, gbcMontantLabel);

        GridBagConstraints gbcMontantField = new GridBagConstraints();
        gbcMontantField.gridx = 1;
        gbcMontantField.gridy = 2;
        gbcMontantField.weightx = 1;
        gbcMontantField.insets = new Insets(5, 5, 5, 5);
        gbcMontantField.fill = GridBagConstraints.HORIZONTAL;
        add(montantField, gbcMontantField);

        // Champ pour la part des charges
        JLabel partChargesLabel = new JLabel("Part des Charges:");
        partChargesField = new JTextField(20);

        GridBagConstraints gbcPartChargesLabel = new GridBagConstraints();
        gbcPartChargesLabel.gridx = 0;
        gbcPartChargesLabel.gridy = 3;
        gbcPartChargesLabel.insets = new Insets(5, 5, 5, 5);
        gbcPartChargesLabel.anchor = GridBagConstraints.WEST;
        add(partChargesLabel, gbcPartChargesLabel);

        GridBagConstraints gbcPartChargesField = new GridBagConstraints();
        gbcPartChargesField.gridx = 1;
        gbcPartChargesField.gridy = 3;
        gbcPartChargesField.weightx = 1;
        gbcPartChargesField.insets = new Insets(5, 5, 5, 5);
        gbcPartChargesField.fill = GridBagConstraints.HORIZONTAL;
        add(partChargesField, gbcPartChargesField);

        // Champ pour le type de document (prérempli à "Quittance")
        JLabel typeDocLabel = new JLabel("Type de Document:");
        JTextField typeDocField = new JTextField("Quittance", 20);
        typeDocField.setEditable(false);

        GridBagConstraints gbcTypeDocLabel = new GridBagConstraints();
        gbcTypeDocLabel.gridx = 0;
        gbcTypeDocLabel.gridy = 4;
        gbcTypeDocLabel.insets = new Insets(5, 5, 5, 5);
        gbcTypeDocLabel.anchor = GridBagConstraints.WEST;
        add(typeDocLabel, gbcTypeDocLabel);

        GridBagConstraints gbcTypeDocField = new GridBagConstraints();
        gbcTypeDocField.gridx = 1;
        gbcTypeDocField.gridy = 4;
        gbcTypeDocField.weightx = 1;
        gbcTypeDocField.insets = new Insets(5, 5, 5, 5);
        gbcTypeDocField.fill = GridBagConstraints.HORIZONTAL;
        add(typeDocField, gbcTypeDocField);
        
        // Bouton pour retour
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(gestionClic);
        GridBagConstraints gbcRetourButton = new GridBagConstraints();
        gbcRetourButton.gridx = 1;
        gbcRetourButton.gridy = 5;
        gbcRetourButton.insets = new Insets(10, 5, 5, 5);
        gbcRetourButton.anchor = GridBagConstraints.CENTER;
        add(retourButton, gbcRetourButton);
        
        // Bouton pour sauvegarder
        JButton saveButton = new JButton("Sauvegarder");
        saveButton.addActionListener(gestionClic);
        GridBagConstraints gbcSaveButton = new GridBagConstraints();
        gbcSaveButton.gridx = 0;
        gbcSaveButton.gridy = 5;
        gbcSaveButton.insets = new Insets(10, 5, 5, 5);
        gbcSaveButton.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbcSaveButton);

       
    }
}
