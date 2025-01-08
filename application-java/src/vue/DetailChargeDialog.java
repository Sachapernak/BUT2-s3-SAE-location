package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
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
import modele.Assurance;
import modele.Entreprise;
import modele.Locataire;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoEntreprise;
import modele.dao.DaoAssurance;
import modele.dao.DaoLocataire;

/**
 * Boîte de dialogue pour afficher les détails d'une charge documentaire.
 * Présente les informations du DocumentComptable dans une interface en grille.
 */
public class DetailChargeDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private int currentRow;

    /**
     * Constructeur de la boîte de dialogue.
     * @param parent Fenêtre parent.
     * @param doc DocumentComptable à afficher.
     * @param log Identifiant du logement pour calcul du prorata (peut être null).
     * @throws SQLException en cas d'erreur base.
     * @throws IOException en cas d'erreur d'E/S.
     */
    public DetailChargeDialog(java.awt.Frame parent, DocumentComptable doc, String log) 
            throws SQLException, IOException {
        
        super(parent, "Détail du document " + doc.getNumeroDoc(), true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagLayout gbl = (GridBagLayout) contentPanel.getLayout();
        gbl.columnWidths = new int[] {150, 300};
        gbl.columnWeights = new double[] {0.0, 1.0};

        currentRow = 0;
        Adder adder = new Adder(contentPanel);

        // SECTION : infos principales
        adder.addRow("Numéro de document :", new JLabel(doc.getNumeroDoc()));
        adder.addRow("Date :", new JLabel(String.valueOf(doc.getDateDoc())));
        adder.addRow("Type :", new JLabel(String.valueOf(doc.getTypeDoc())));

        addEmptyLine(contentPanel);

        // SECTION : montants
        adder.addRow("Montant total :", new JLabel(String.valueOf(doc.getMontant())));
        if (log != null) {
            String prorata = String.valueOf(new DaoDocumentComptable().findMontantProrata(doc, log));
            adder.addRow("Dont " + log + " :", new JLabel(prorata));
        }
        if (doc.getMontantDevis() != null) {
            adder.addRow("Montant devis :", new JLabel(String.valueOf(doc.getMontantDevis())));
        }

        addEmptyLine(contentPanel);

        // SECTION : autres infos
        if (doc.isRecuperableLoc()) {
            JButton btnVoirLoc = new JButton(doc.getIdLocataire());
            gestionBoutonLocataire(doc, btnVoirLoc);
            JPanel panelLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panelLoc.add(btnVoirLoc);
            adder.addRow("Locataire :", panelLoc);
        }

        if (!"quittance".equalsIgnoreCase(doc.getTypeDoc().toString())) {
            JButton btnVoirEntreprise = new JButton(doc.getIdEntreprise());
            gestionBoutonEntreprise(doc, btnVoirEntreprise);
            JPanel panelEntreprise = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panelEntreprise.add(btnVoirEntreprise);
            adder.addRow("Entreprise :", panelEntreprise);

            if (doc.getNumeroContrat() != null) {
                JButton btnVoirAssu = new JButton(doc.getNumeroContrat());
                gestionBoutonAssurance(doc, btnVoirAssu);
                JPanel panelAssu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                panelAssu.add(btnVoirAssu);
                adder.addRow("Assurance :", panelAssu);
            }

            if (doc.getChargeFixe() != null) {
                JButton btnVoirCF = new JButton("Voir détails...");
                gestionBoutonCF(doc, btnVoirCF);
                JPanel panelCF = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                panelCF.add(btnVoirCF);
                adder.addRow("Charge fixe :", panelCF);
            } else if (doc.getChargeIndex() != null) {
                JButton btnVoirCI = new JButton("Voir détails...");
                gestionBoutonIndex(doc, btnVoirCI);
                JPanel panelCI = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                panelCI.add(btnVoirCI);
                adder.addRow("Charge index :", panelCI);
            }
        }

        addEmptyLine(contentPanel);
        addLienSection(contentPanel, doc.getFichierDoc());

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        pack(); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void gestionBoutonLocataire(DocumentComptable doc, JButton btnVoirLoc) {
        btnVoirLoc.addActionListener(e -> {
            try {
                Locataire loc = new DaoLocataire().findById(doc.getIdLocataire());
                DetailLocataire dialog = new DetailLocataire(loc);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                this.dispose();
            } catch(Exception ex) { 
                JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                    "Erreur lors de l'affichage du locataire : " + ex.getMessage());
            }
        });
    }

    private void gestionBoutonEntreprise(DocumentComptable doc, JButton btnEntr) {
        btnEntr.addActionListener(e -> {
            try {
                Entreprise ent = new DaoEntreprise().findById(doc.getIdEntreprise());
                DetailEntreprise dialog = new DetailEntreprise(ent);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                this.dispose();
            } catch(Exception ex) { 
                JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                    "Erreur lors de l'affichage de l'entreprise : " + ex.getMessage());
            }
        });
    }

    private void gestionBoutonAssurance(DocumentComptable doc, JButton btnVoirAssu) {
        btnVoirAssu.addActionListener(e -> {
            try {
                Assurance assu = new DaoAssurance().findById(doc.getNumeroContrat(), doc.getAnneeContrat());
                DetailAssurance dialog = new DetailAssurance(assu);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                this.dispose();
            } catch(Exception ex) { 
                JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                    "Erreur lors de l'affichage de l'assurance : " + ex.getMessage());
            }
        });
    }

    private void gestionBoutonCF(DocumentComptable doc, JButton btnVoirCF) {
        btnVoirCF.addActionListener(e -> {
            try {
                DetailChargeCF dialog = new DetailChargeCF(doc.getChargeFixe());
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                this.dispose();
            } catch(Exception ex) { 
                JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                    "Erreur lors de l'affichage de l'assurance : " + ex.getMessage());
            }
        });
    }

    private void gestionBoutonIndex(DocumentComptable doc, JButton btnVoirCF) {
        btnVoirCF.addActionListener(e -> {
            try {
                DetailChargeIndex dialog = new DetailChargeIndex(doc.getChargeIndex());
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                this.dispose();
            } catch(Exception ex) { 
                JOptionPane.showMessageDialog(DetailChargeDialog.this, 
                    "Erreur lors de l'affichage de l'assurance : " + ex.getMessage());
            }
        });
    }

    private class Adder {
        private JPanel panel;
        Adder(JPanel panel) { this.panel = panel; }
        void addRow(String labelText, java.awt.Component comp) {
            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.gridx = 0;
            gbcLabel.gridy = currentRow;
            gbcLabel.anchor = GridBagConstraints.WEST;
            gbcLabel.insets = new Insets(5, 5, 5, 5);
            panel.add(new JLabel(labelText), gbcLabel);

            GridBagConstraints gbcValue = new GridBagConstraints();
            gbcValue.gridx = 1;
            gbcValue.gridy = currentRow;
            gbcValue.fill = GridBagConstraints.HORIZONTAL;
            gbcValue.anchor = GridBagConstraints.WEST;
            gbcValue.insets = new Insets(5, 5, 5, 5);
            panel.add(comp, gbcValue);

            currentRow++;
        }
    }

    private void addEmptyLine(JPanel panel) {
        GridBagConstraints gbcSpace = new GridBagConstraints();
        gbcSpace.gridx = 0; 
        gbcSpace.gridy = currentRow++;
        gbcSpace.gridwidth = 2; 
        gbcSpace.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel(" "), gbcSpace);
    }

    private void addLienSection(JPanel panel, String chemin) {
        GridBagConstraints gbcLabelLink = new GridBagConstraints();
        gbcLabelLink.gridx = 0;
        gbcLabelLink.gridy = currentRow;
        gbcLabelLink.insets = new Insets(5, 5, 5, 5);
        gbcLabelLink.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Lien :"), gbcLabelLink);

        String displayText = (chemin != null) ? chemin : "";
        if (chemin != null && chemin.contains("\\")) {
            displayText = chemin.substring(chemin.lastIndexOf("\\") + 1);
        }

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
        scrollPaneLink.setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbcValueLink = new GridBagConstraints();
        gbcValueLink.gridx = 1;
        gbcValueLink.gridy = currentRow;
        gbcValueLink.fill = GridBagConstraints.BOTH;
        gbcValueLink.weightx = 1.0;
        gbcValueLink.insets = new Insets(5, 5, 5, 5);
        panel.add(scrollPaneLink, gbcValueLink);

        currentRow++;
    }

    private void gestionCliqueFichier(String chemin, JTextArea textAreaLink) {
        textAreaLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
}
