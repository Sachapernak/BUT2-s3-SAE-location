package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingWorker;

import modele.Batiment;
import modele.BienLocatif;
import modele.DocumentComptable;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoLoyer;
import rapport.RapportDeclarationFiscale;
import vue.DeclarationFiscale;

/**
 * Contrôleur pour la vue DeclarationFiscale.
 * Gère les interactions (événements boutons, comboBox, etc.) 
 * et le remplissage de la table correspondant à chaque année sélectionnée.
 */
public class GestionDeclarationFiscale implements ActionListener {

    private static final int ANNEEMIN = 1980;
	private final DeclarationFiscale view;

    /**
     * Constructeur qui associe ce contrôleur à la vue DeclarationFiscale.
     * @param df La vue (fenêtre) de déclaration fiscale.
     */
    public GestionDeclarationFiscale(DeclarationFiscale df)  {
        this.view = df;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();

        switch (btnLibelle) {
            case "Retour":
                view.dispose();
                break;
            case "Générer":
                genererRapport();
                break;
            default:
                break;
        }
    }

    /**
     * Configure le JComboBox pour réagir lorsque l'utilisateur sélectionne une année.
     * Lorsque l'utilisateur change d'année, on recharge la table via {@link #remplirTableBatiments()}.
     *
     * @param comboBoxAnnee Le JComboBox de sélection des années.
     */
    public void gestionActionComboBoxAnnee(JComboBox<String> comboBoxAnnee) {
        comboBoxAnnee.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                remplirTableBatiments(); 
            }
        });
    }
    
    /**
     * Remplit la comboBox des années en utilisant un SwingWorker pour ne pas bloquer l'IU.
     * Une fois les années chargées, on peut (si on le souhaite) déclencher 
     * un premier appel à {@link #remplirTableBatiments()} pour afficher 
     * directement les données de l'année sélectionnée.
     */
    public void remplirComboBoxAnnee() {
        view.setWaitCursor();

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() {
                List<String> annees = new ArrayList<>();
                int anneeActuelle = LocalDate.now().getYear();
                for (int i = anneeActuelle; i >= ANNEEMIN; i--) {
                    annees.add(String.valueOf(i));
                }
                return annees;
            }

            @Override
            protected void done() {
                try {
                    List<String> annees = get();
                    view.setComboBoxAnnee(annees);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    view.afficherMessageErreur(e.getMessage());
                    
                } finally {
                    view.setDefaultCursor();
                    remplirTableBatiments(); 

                }
            }
        }.execute();
    }

    /**
     * Remplit la table des bâtiments (loyers encaissés, frais, bénéfices) 
     * en fonction de l'année actuellement sélectionnée dans la vue.
     * Utilise un SwingWorker pour ne pas bloquer l'IU durant les calculs.
     */
    public void remplirTableBatiments() {
        view.setWaitCursor();

        new SwingWorker<BatimentDataResult, Void>() {
            @Override
            protected BatimentDataResult doInBackground() {
                List<String[]> rows = new ArrayList<>();
                BigDecimal totalBenefices = BigDecimal.ZERO;

                try {
                    List<Batiment> batiments = new DaoBatiment().findAll();
                    int annee = Integer.parseInt(view.getAnnee());

                    for (Batiment batiment : batiments) {
                        BigDecimal totalLoyersBat = calculerTotalLoyers(batiment, annee);
                        BigDecimal totalFraisBat  = calculerTotalFrais(batiment, annee);
                        BigDecimal benefice = totalLoyersBat.subtract(totalFraisBat);

                        totalBenefices = totalBenefices.add(benefice);

                        rows.add(new String[]{
                            batiment.getIdBat(),
                            totalLoyersBat.toString(),
                            totalFraisBat.toString(),
                            benefice.toString()
                        });
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    view.afficherMessageErreur(e.getMessage());
                }

                return new BatimentDataResult(rows, totalBenefices);
            }

            @Override
            protected void done() {
                try {
                    BatimentDataResult result = get();
                    view.remplirTable(result.getRows());
                    view.setTextTotal(result.getTotalBenefices().toString());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    view.afficherMessageErreur(
                        "Erreur lors du remplissage des données : " + e.getMessage()
                    );
                } finally {
                    view.setDefaultCursor();
                }
            }
        }.execute();
    }

    /**
     * Génère le rapport de déclaration fiscale en créant un fichier docx.
     * Récupère les données de la table, l'année sélectionnée et le total depuis la vue.
     */
    private void genererRapport() {
        try {
            new RapportDeclarationFiscale().genererRapportDeclarationFiscale(
                recupererDonnees(), 
                view.getAnnee(),
                view.getTextTotal(),
                view.getAnnee() + "RECAPFISCAL.docx"
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            view.afficherMessageErreur(ex.getMessage());
        }
    }

    /**
     * Récupère les données de la table sous forme de liste de tableaux de chaînes de caractères.
     */
    public List<String[]> recupererDonnees() {
        int nbLignes    = view.getNbLignesTable();
        int nbColonnes  = view.getColumnCount();
        List<String[]> ensembleData = new ArrayList<>(nbLignes);

        for (int i = 0; i < nbLignes; i++) {
            String[] ligneData = new String[nbColonnes];
            for (int j = 0; j < nbColonnes; j++) {
                Object value = view.getValueAt(i, j);
                ligneData[j] = (value != null) ? value.toString() : "";
            }
            ensembleData.add(ligneData);
        }
        return ensembleData;
    }

    // -------------------------------------------------------------------------
    // Calculs spécifiques (loyers, frais...) 
    // -------------------------------------------------------------------------

    private BigDecimal calculerTotalLoyers(Batiment batiment, int annee) throws SQLException {
        BigDecimal totalLoyersBat = BigDecimal.ZERO;

        try {
            List<BienLocatif> logements = new DaoBienLocatif().findByIdBat(batiment.getIdBat());
            for (BienLocatif logement : logements) {
                totalLoyersBat = totalLoyersBat.add(calculerLoyersLogement(logement, annee));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return totalLoyersBat;
    }

    private BigDecimal calculerLoyersLogement(BienLocatif logement, int annee) throws SQLException {
        BigDecimal totalLoyersLog = BigDecimal.ZERO;
        String idLog = logement.getIdentifiantLogement();

        // Parcourir 12 mois (du 1er mai N-1 au 30 avril N)
        LocalDate date = LocalDate.of(annee - 1, 5, 1);
        for (int i = 0; i < 12; i++) {
            LocalDate finMois = date.plusMonths(1).minusDays(1);

            try {
                BigDecimal estLoue = new DaoBienLocatif().estLoueEntre(
                    idLog, date.toString(), finMois.toString()
                );
                if (!estLoue.equals(BigDecimal.ZERO)) {
                    BigDecimal dernierLoyer = new DaoLoyer().getDernierLoyer(idLog, finMois.toString());
                    totalLoyersLog = totalLoyersLog.add(dernierLoyer);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            date = date.plusMonths(1);
        }
        return totalLoyersLog;
    }

    private BigDecimal calculerTotalFrais(Batiment batiment, int annee) throws SQLException, IOException {
        BigDecimal totalFraisBat = BigDecimal.ZERO;
        String debutAnnee = LocalDate.of(annee - 1, 5, 1).toString();
        String finAnnee   = LocalDate.of(annee, 4, 30).toString();

        List<BienLocatif> logements = new DaoBienLocatif().findByIdBat(batiment.getIdBat());
        for (BienLocatif logement : logements) {
            List<DocumentComptable> documents = 
                new DaoDocumentComptable().findByIdLogement(logement.getIdentifiantLogement());

            for (DocumentComptable document : documents) {
                String dateDoc = document.getDateDoc();
                if (!document.isRecuperableLoc()
                    && dateDoc.compareTo(finAnnee) < 0
                    && dateDoc.compareTo(debutAnnee) > 0) {

                    BigDecimal prorata = new DaoDocumentComptable().findMontantProrata(
                        document, logement.getIdentifiantLogement()
                    );
                    totalFraisBat = totalFraisBat.add(prorata);
                }
            }
        }
        return totalFraisBat;
    }

    // -------------------------------------------------------------------------
    // Classe interne de résultat pour le SwingWorker
    // -------------------------------------------------------------------------
    private static class BatimentDataResult {
        private final List<String[]> rows;
        private final BigDecimal totalBenefices;

        public BatimentDataResult(List<String[]> rows, BigDecimal totalBenefices) {
            this.rows = rows;
            this.totalBenefices = totalBenefices;
        }

        public List<String[]> getRows() {
            return rows;
        }

        public BigDecimal getTotalBenefices() {
            return totalBenefices;
        }
    }
}
