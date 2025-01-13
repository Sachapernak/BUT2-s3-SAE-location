package vue;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import modele.Adresse;
import modele.BienLocatif;
import modele.DocumentComptable;
import modele.Loyer;
import modele.ProvisionCharge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class GenereQuittance {

    public GenereQuittance() {
    }

    public static void generateQuittanceWord(DocumentComptable dcQuittance, BigDecimal montant, BienLocatif bienLocatif, Loyer loyer, ProvisionCharge pc) throws SQLException, IOException {
        // Création du document Word
        XWPFDocument document = new XWPFDocument();

        // Ajouter le texte "Locataire" en haut à droite
        XWPFParagraph locataireParagraph = document.createParagraph();
        XWPFRun locataireRun = locataireParagraph.createRun();
        locataireRun.setText("Locataire: " + dcQuittance.getLocataire().getNom());
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
        Adresse adresse = bienLocatif.getBat() != null ? bienLocatif.getBat().getAdresse() : null;
        detailsRun.setText("Quittance de loyer n° " + dcQuittance.getNumeroDoc());
        detailsRun.addBreak();
        detailsRun.setText("Reçu de : " + dcQuittance.getLocataire().getNom());
        detailsRun.addBreak();
        detailsRun.setText("Le : " + dcQuittance.getDateDoc());
        detailsRun.addBreak();
        detailsRun.setText("Pour loyer et accessoires des locaux sis :");
        detailsRun.addBreak();
        detailsRun.setText(adresse != null ? adresse.getAdressePostale() + ", " + adresse.getCodePostal() + " " + adresse.getVille() : "Non spécifié");
        detailsRun.addBreak();

        // Tableau des montants à payer
        XWPFParagraph amountsToPayTitle = document.createParagraph();
        XWPFRun amountsToPayRun = amountsToPayTitle.createRun();
        amountsToPayRun.setText("Montants à payer :");
        amountsToPayRun.setBold(true);
        
        XWPFTable amountsToPayTable = document.createTable();
        XWPFTableRow loyerRow = amountsToPayTable.getRow(0);
        loyerRow.getCell(0).setText("Loyer dû :");
        loyerRow.addNewTableCell().setText(loyer.getMontantLoyer() + " €");

        XWPFTableRow chargeRow = amountsToPayTable.createRow();
        chargeRow.getCell(0).setText("Provision pour charges :");
        chargeRow.getCell(1).setText(pc.getProvisionPourCharge() + " €");

        BigDecimal totalDues = loyer.getMontantLoyer().add(pc.getProvisionPourCharge());
        XWPFTableRow totalDueRow = amountsToPayTable.createRow();
        totalDueRow.getCell(0).setText("Total dû :");
        totalDueRow.getCell(1).setText(totalDues + " €");

        // Tableau des montants payés
        XWPFParagraph amountsPaidTitle = document.createParagraph();
        XWPFRun amountsPaidRun = amountsPaidTitle.createRun();
        amountsPaidRun.setText("Montants payés :");
        amountsPaidRun.setBold(true);

        XWPFTable amountsPaidTable = document.createTable();
        XWPFTableRow paidRow = amountsPaidTable.getRow(0);
        paidRow.getCell(0).setText("Montant payé par le locataire :");
        paidRow.addNewTableCell().setText(montant + " €");

        BigDecimal differenceLoyer = montant.subtract(loyer.getMontantLoyer());
        XWPFTableRow differenceLoyerRow = amountsPaidTable.createRow();
        differenceLoyerRow.getCell(0).setText("Montant - Loyer :");
        differenceLoyerRow.getCell(1).setText(differenceLoyer + " €");

        BigDecimal differenceTotal = montant.subtract(totalDues);
        XWPFTableRow differenceTotalRow = amountsPaidTable.createRow();
        differenceTotalRow.getCell(0).setText("Montant - (Loyer + Charges) :");
        differenceTotalRow.getCell(1).setText(differenceTotal + " €");

        // Observation selon les cas
        String observation;
        if (differenceTotal.compareTo(BigDecimal.ZERO) >= 0) {
            observation = "Tout a été payé pour ce mois.";
        } else if (montant.compareTo(loyer.getMontantLoyer()) < 0) {
            observation = "Le locataire n'a pas terminé de payer le loyer.";
        } else {
            observation = "Il manque des charges à payer.";
        }

        XWPFTableRow observationRow = amountsPaidTable.createRow();
        observationRow.getCell(0).setText("Observation :");
        observationRow.getCell(1).setText(observation);

        // Ajouter la date du paiement et signature
        XWPFParagraph signatureParagraph = document.createParagraph();
        XWPFRun signatureRun = signatureParagraph.createRun();
        signatureRun.setText("Fait à " + (adresse != null ? adresse.getVille() : "Non spécifié") + " le " + dcQuittance.getDateDoc());
        signatureParagraph.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.RIGHT);

        // Sauvegarder le document Word
        try (FileOutputStream out = new FileOutputStream("QuittanceLoyer_" + dcQuittance.getNumeroDoc() + ".docx")) {
            document.write(out);
        }

        // Fermer le document
        document.close();
    }
}
