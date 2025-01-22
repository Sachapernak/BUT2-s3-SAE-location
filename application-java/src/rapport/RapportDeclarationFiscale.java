package rapport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Classe permettant de générer un rapport de déclaration fiscale au format .docx
 * en utilisant la bibliothèque Apache POI.
 */
public class RapportDeclarationFiscale {

    /**
     * Constructeur par défaut.
     */
    public RapportDeclarationFiscale() {
    }

    /**
     * Génère un document Word (.docx) contenant un tableau de déclarations fiscales.
     * 
     * @param data          Liste de lignes. Chaque élément est un tableau de String
     *                      correspondant aux colonnes :
     *                      [ "Batiment", "Loyers encaissé", "Frais et charges", "Bénéfice(+) ou pertes(-)" ]
     * @param annee         Année à afficher dans le document.
     * @param total         Total des bénéfices ou pertes.
     * @param cheminFichier Chemin complet du fichier de sortie (.docx).
     * @throws IOException Si une erreur d'écriture survient.
     */
    public void genererRapportDeclarationFiscale(List<String[]> data,
                                                 String annee,
                                                 String total,
                                                 String cheminFichier) throws IOException {

        // Création d'un document Word vierge
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream fos = new FileOutputStream(cheminFichier)) {

            // 1) Titre du document
            XWPFParagraph paragraphTitre = document.createParagraph();
            paragraphTitre.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runTitre = paragraphTitre.createRun();
            runTitre.setText("Fiche Déclarations Fiscales");
            runTitre.setBold(true);
            runTitre.setFontSize(16);

            // 2) Paragraphe pour l'année
            XWPFParagraph paragraphAnnee = document.createParagraph();
            paragraphAnnee.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun runAnnee = paragraphAnnee.createRun();
            runAnnee.setText("Année : " + annee);
            runAnnee.setFontSize(12);
            runAnnee.addBreak(); // Saut de ligne

            // 3) Création d'un tableau : 4 colonnes
            XWPFTable table = document.createTable();

            // 3.1) Première ligne = en-tête du tableau
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("Bâtiment");
            headerRow.addNewTableCell().setText("Loyers encaissés");
            headerRow.addNewTableCell().setText("Frais et charges");
            headerRow.addNewTableCell().setText("Bénéfice(+) ou pertes(-)");

            // 3.2) Remplissage des données (chaque String[] représente une ligne)
            for (String[] rowData : data) {
                XWPFTableRow row = table.createRow();
                // On s'assure d'avoir 4 colonnes dans rowData
                // rowData[0] -> Bâtiment
                // rowData[1] -> Loyers encaissés
                // rowData[2] -> Frais et charges
                // rowData[3] -> Bénéfice / Pertes
                row.getCell(0).setText(rowData[0]);
                row.getCell(1).setText(rowData[1]);
                row.getCell(2).setText(rowData[2]);
                row.getCell(3).setText(rowData[3]);
            }

            // 4) Paragraphe pour le total
            XWPFParagraph paragraphTotal = document.createParagraph();
            paragraphTotal.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun runTotal = paragraphTotal.createRun();
            runTotal.setText("Total : " + total);
            runTotal.setBold(true);
            runTotal.setFontSize(12);

            // 5) Écriture du document dans le fichier
            document.write(fos);
        }
    }
    


    public static void main(String[] args) {
        // Création d'une instance du rapport
        RapportDeclarationFiscale rapport = new RapportDeclarationFiscale();

        // Préparation des données fictives pour trois bâtiments
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Batiment A", "12000", "2000", "10000"});
        data.add(new String[]{"Batiment B", "15000", "3000", "12000"});
        data.add(new String[]{"Batiment C", "10000", "1500", "8500"});

        // Définition des autres paramètres
        String annee = "2023";
        String total = "30500";  // Exemple de total calculé pour tous les bâtiments combinés
        String cheminFichier = "rapport_declaration_2023.docx";

        // Génération du rapport
        try {
            rapport.genererRapportDeclarationFiscale(data, annee, total, cheminFichier);
            System.out.println("Rapport généré avec succès dans le fichier : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du rapport : " + e.getMessage());
            e.printStackTrace();
        }
    }
	
}
