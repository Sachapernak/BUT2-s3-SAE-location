package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import modele.DocumentComptable;
import modele.dao.DaoDocumentComptable;

public class DetailChargeDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    // Compteur de ligne dans le GridBagLayout
    int currentRow;

    public DetailChargeDialog(java.awt.Frame parent, DocumentComptable doc, String log) 
            throws SQLException, IOException {
        
        super(parent, "Détail du document " + doc.getNumeroDoc(), true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // --- Panel principal avec un GridBagLayout ---
        JPanel contentPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();

        // Première colonne (labels) = 150px, seconde colonne (valeurs) flexible
        gbl.columnWidths = new int[] {150, 300};
        gbl.columnWeights = new double[] {0.0, 1.0};
        contentPanel.setLayout(gbl);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        currentRow = 0;

        // Classe utilitaire pour ajouter une ligne (Label à gauche / Composant à droite)
        class Adder {
            void addRow(String labelText, java.awt.Component comp) {
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = currentRow;
                gbcLabel.anchor = GridBagConstraints.WEST;
                gbcLabel.insets = new Insets(5, 5, 5, 5);
                
                contentPanel.add(new JLabel(labelText), gbcLabel);

                GridBagConstraints gbcValue = new GridBagConstraints();
                gbcValue.gridx = 1;
                gbcValue.gridy = currentRow;
                gbcValue.fill = GridBagConstraints.HORIZONTAL;
                gbcValue.anchor = GridBagConstraints.WEST;
                gbcValue.insets = new Insets(5, 5, 5, 5);

                contentPanel.add(comp, gbcValue);

                currentRow++;
            }
        }
        Adder adder = new Adder();

        // --- SECTION : infos principales ---
        adder.addRow("Numéro de document :", new JLabel(doc.getNumeroDoc()));
        adder.addRow("Date :", new JLabel(String.valueOf(doc.getDateDoc())));
        adder.addRow("Type :", new JLabel(String.valueOf(doc.getTypeDoc())));

        // Espace vertical
        addEmptyLine(contentPanel);

        // --- SECTION : montants ---
        adder.addRow("Montant total :", new JLabel(String.valueOf(doc.getMontant())));
        if (log != null) {
            String prorata = String.valueOf(new DaoDocumentComptable().findMontantProrata(doc, log));
            adder.addRow("Dont " + log + " :", new JLabel(prorata));
        }
        if (doc.getMontantDevis() != null) {
            adder.addRow("Montant devis :", new JLabel(String.valueOf(doc.getMontantDevis())));
        }

        // Espace vertical
        addEmptyLine(contentPanel);

        // --- SECTION : autres infos (locataire, entreprise, etc.) ---
        if (doc.isRecuperableLoc()) {
            // On place le bouton dans un panel FlowLayout aligné à gauche
            JPanel panelLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JButton btnVoirLoc = new JButton(doc.getIdLocataire());
            
            panelLoc.add(btnVoirLoc);
            adder.addRow("Locataire :", panelLoc);
        }

        if (!"quittance".equalsIgnoreCase(doc.getTypeDoc().toString())) {
        	
            JPanel panelEntreprise = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JButton btnVoirEntreprise = new JButton(doc.getIdEntreprise());
            panelEntreprise.add(btnVoirEntreprise);
            adder.addRow("Entreprise :", panelEntreprise);

            if (doc.getNumeroContrat() != null) {
                JPanel panelAssu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                JButton btnVoirAssu = new JButton(doc.getNumeroContrat());
                panelAssu.add(btnVoirAssu);
                adder.addRow("Assurance :", panelAssu);
            }

            if (doc.getChargeFixe() != null) {
                JPanel panelCF = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                JButton btnVoirCF = new JButton("Voir détails...");
                panelCF.add(btnVoirCF);
                adder.addRow("Charge fixe :", panelCF);
            } else if (doc.getChargeIndex() != null) {
                JPanel panelCI = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                JButton btnVoirCI = new JButton("Voir détails...");
                panelCI.add(btnVoirCI);
                adder.addRow("Charge index :", panelCI);
            }
        }

        // Espace vertical
        addEmptyLine(contentPanel);

        // --- SECTION : lien cliquable ---
        GridBagConstraints gbcLabelLink = new GridBagConstraints();
        gbcLabelLink.gridx = 0;
        gbcLabelLink.gridy = currentRow;
        gbcLabelLink.insets = new Insets(5, 5, 5, 5);
        gbcLabelLink.anchor = GridBagConstraints.WEST;
        contentPanel.add(new JLabel("Lien :"), gbcLabelLink);

        // Chemin du fichier
        String chemin = doc.getFichierDoc();
        String displayText = (chemin != null) ? chemin : "";
        if (chemin != null && chemin.contains("\\")) {
            // N'afficher que la dernière partie après le "\"
            displayText = chemin.substring(chemin.lastIndexOf("\\") + 1);
        }

        // Zone de texte non éditable, scrollable si besoin (horizontal / vertical)
        JTextArea textAreaLink = new JTextArea(displayText, 2, 20);
        textAreaLink.setWrapStyleWord(false);
        textAreaLink.setLineWrap(false);
        textAreaLink.setEditable(false);
        textAreaLink.setOpaque(false);
        textAreaLink.setForeground(Color.BLUE);
        textAreaLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gestionCliqueFichier(chemin, textAreaLink);

        JScrollPane scrollPaneLink = new JScrollPane(
            textAreaLink,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPaneLink.setBorder(null);

        GridBagConstraints gbcValueLink = new GridBagConstraints();
        gbcValueLink.gridx = 1;
        gbcValueLink.gridy = currentRow;
        gbcValueLink.fill = GridBagConstraints.BOTH;
        gbcValueLink.weightx = 1.0;
        gbcValueLink.insets = new Insets(5, 5, 5, 5);
        contentPanel.add(scrollPaneLink, gbcValueLink);

        currentRow++;

        // --- Ajout du panel au dialog ---
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Configuration de la fenêtre
        pack(); 
        setLocationRelativeTo(parent);
        setVisible(true);
    }

	private void gestionCliqueFichier(String chemin, JTextArea textAreaLink) {
		textAreaLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (Desktop.isDesktopSupported() && chemin != null) {
                    try {
                        Desktop.getDesktop().open(new File(chemin));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                            "Impossible d'ouvrir le fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
	}

    /**
     * Ajoute une ligne vide pour séparer visuellement les sections.
     */
    private void addEmptyLine(JPanel panel) {
        GridBagConstraints gbcSpace = new GridBagConstraints();
        gbcSpace.gridx = 0; 
        gbcSpace.gridy = currentRow++;
        gbcSpace.gridwidth = 2; 
        gbcSpace.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel(" "), gbcSpace);
    }
}
