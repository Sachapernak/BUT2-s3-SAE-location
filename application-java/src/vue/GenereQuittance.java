package vue;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import modele.Adresse;
import modele.DocumentComptable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class GenereQuittance {

    public GenereQuittance()  {
        
    }

    public static void generateQuittanceWord(DocumentComptable dc) throws IOException {
        // Création du document Word
        XWPFDocument document = new XWPFDocument();

        // Ajouter le texte "Locataire" en haut à droite
        XWPFParagraph locataireParagraph = document.createParagraph();
        XWPFRun locataireRun = locataireParagraph.createRun();
        locataireRun.setText("Locataire: " + dc.getLocataire().getNom());
        locataireRun.setBold(true);
        locataireParagraph.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.RIGHT);

        // Ajouter le titre "Quittance de loyer"
        XWPFParagraph title = document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Quittance de loyer");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        title.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.CENTER);

        // Ajouter les détails de la quittance
        XWPFParagraph detailsParagraph = document.createParagraph();
        XWPFRun detailsRun = detailsParagraph.createRun();
        Adresse adresse = dc.getBatiment() != null ? dc.getBatiment().getAdresse() : null;
        detailsRun.setText(
            "Quittance de loyer n° " + dc.getNumeroDoc() + "\n" +
            "Reçu de : " + dc.getLocataire().getNom() + "\n" +
            "La somme de : " + dc.getMontant() + "€\n" +
            "Le : " + dc.getDateDoc() + "\n" +
            "Pour loyer et accessoires des locaux sis :\n" +
            (adresse != null ? adresse.getAdressePostale() + ", " + adresse.getCodePostal() + " " + adresse.getVille() : "Non spécifié") + "\n"
        );

        // Ajouter un tableau pour le loyer et les charges
        XWPFTable table = document.createTable();

        // Ligne d'en-tête
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Description");
        headerRow.addNewTableCell().setText("Montant (€)");

        // Ajouter les données au tableau
        String[][] data = {
            {"Loyer nu", dc.getMontant().toString()},
            {"Charges", dc.getMontantDevis() != null ? dc.getMontantDevis().toString() : "0"},
            {"Montant total du terme", dc.getMontant().add(dc.getMontantDevis() != null ? dc.getMontantDevis() : BigDecimal.ZERO).toString()}
        };
        for (String[] row : data) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(row[0]);
            tableRow.getCell(1).setText(row[1]);
        }

        // Ajouter les mentions légales
        XWPFParagraph legalParagraph = document.createParagraph();
        XWPFRun legalRun = legalParagraph.createRun();
        legalRun.setText("Le paiement de la présente n'emporte pas présomption de paiement des termes antérieurs.\n" +
                "Cette quittance annule tous les reçus qui auraient pu être donnés pour acompte versé sur le présent terme.\n" +
                "Sous réserve d'encaissement.");

        // Ajouter une signature
        XWPFParagraph signatureParagraph = document.createParagraph();
        XWPFRun signatureRun = signatureParagraph.createRun();
        signatureRun.setText("Fait à " + (adresse != null ? adresse.getVille() : "Non spécifié") + " le " + dc.getDateDoc());
        signatureParagraph.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.RIGHT);

        // Sauvegarder le document Word
        try (FileOutputStream out = new FileOutputStream("QuittanceLoyer_" + dc.getNumeroDoc() + ".docx")) {
            document.write(out);
        }

        // Fermer le document
        document.close();
    }
}
