package vue;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import modele.Adresse;
import modele.ChargeFixe;
import modele.ChargeIndex;
import modele.DocumentComptable;
import modele.Loyer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class GenereQuittance {

    public GenereQuittance() {
    }

    public static void generateQuittanceWord(DocumentComptable dc, Loyer loyer, ChargeIndex chargeIndex, ChargeFixe chargeFixe) throws IOException {
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
        detailsRun.setText("Quittance de loyer n° " + dc.getNumeroDoc());
        detailsRun.addBreak();
        detailsRun.setText("Reçu de : " + dc.getLocataire().getNom());
        detailsRun.addBreak();
        detailsRun.setText("Le : " + dc.getDateDoc());
        detailsRun.addBreak();
        detailsRun.setText("Pour loyer et accessoires des locaux sis :");
        detailsRun.addBreak();
        detailsRun.setText(adresse != null ? adresse.getAdressePostale() + ", " + adresse.getCodePostal() + " " + adresse.getVille() : "Non spécifié");
        detailsRun.addBreak();

        // Ajouter un tableau pour les loyers
        XWPFTable loyerTable = document.createTable();
        XWPFTableRow loyerHeaderRow = loyerTable.getRow(0);
        loyerHeaderRow.getCell(0).setText("Identifiant Logement");
        loyerHeaderRow.addNewTableCell().setText("Date de Changement");
        loyerHeaderRow.addNewTableCell().setText("Montant Loyer (€)");

        XWPFTableRow loyerRow = loyerTable.createRow();
        loyerRow.getCell(0).setText(loyer.getIdBien());
        loyerRow.getCell(1).setText(loyer.getDateDeChangement());
        loyerRow.getCell(2).setText(loyer.getMontantLoyer().toString());

        // Ajouter un tableau pour toutes les charges (fusion des charges fixes et indexées)
        XWPFParagraph separationParagraph = document.createParagraph();
        separationParagraph.createRun().addBreak();

        XWPFTable chargeTable = document.createTable();
        XWPFTableRow chargeHeaderRow = chargeTable.getRow(0);
        chargeHeaderRow.getCell(0).setText("Type de Charge");
        chargeHeaderRow.addNewTableCell().setText("Description");
        chargeHeaderRow.addNewTableCell().setText("Montant (€)");

        // Charges indexées
        XWPFTableRow chargeIndexRow = chargeTable.createRow();
        chargeIndexRow.getCell(0).setText("Charge Indexée");
        chargeIndexRow.getCell(1).setText(chargeIndex.getType());
        chargeIndexRow.getCell(2).setText(chargeIndex.getCoutFixe().add(chargeIndex.getCoutVariable().multiply(chargeIndex.getValeurCompteur())).toString());

        // Charges fixes
        XWPFTableRow chargeFixeRow = chargeTable.createRow();
        chargeFixeRow.getCell(0).setText("Charge Fixe");
        chargeFixeRow.getCell(1).setText(chargeFixe.getType());
        chargeFixeRow.getCell(2).setText(chargeFixe.getMontant().toString());
        
        // Ajouter la somme des charges
        BigDecimal totalCharges = chargeFixe.getMontant().add(chargeIndex.getCoutFixe().add(chargeIndex.getCoutVariable().multiply(chargeIndex.getValeurCompteur())));
        XWPFTableRow totalChargesRow = chargeTable.createRow();
        totalChargesRow.getCell(0).setText("");
        totalChargesRow.getCell(1).setText("");
        totalChargesRow.getCell(2).setText("Total Charges: " + totalCharges);

        // Ajouter le total général (loyer + charges)
        BigDecimal totalGeneral = loyer.getMontantLoyer().add(totalCharges);
        XWPFParagraph totalGeneralParagraph = document.createParagraph();
        XWPFRun totalGeneralRun = totalGeneralParagraph.createRun();
        totalGeneralRun.setText("Montant Total (Loyer + Charges): " + totalGeneral + " €");
        totalGeneralRun.setBold(true);
        totalGeneralParagraph.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.RIGHT);

        // Ajouter les mentions légales
        XWPFParagraph legalParagraph = document.createParagraph();
        XWPFRun legalRun = legalParagraph.createRun();
        legalRun.setText("Le paiement de la présente n'emporte pas présomption de paiement des termes antérieurs.");
        legalRun.addBreak();
        legalRun.setText("Cette quittance annule tous les reçus qui auraient pu être donnés pour acompte versé sur le présent terme.");
        legalRun.addBreak();
        legalRun.setText("Sous réserve d'encaissement.");

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
