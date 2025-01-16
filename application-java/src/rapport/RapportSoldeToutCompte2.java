package rapport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class RapportSoldeToutCompte2 {
	
    // --- Champs simples pour le locataire / bail ---
    private String nom;
    private String prenom;
    private String adresse;
    private String complement;
    private String codePostal;
    private String ville;
    private String dateCourante;
    private String dateDebut;
    private String dateFin;

    // --- Champs montants / calculs divers ---
    private String totalCharge;
    private String totalDeduc;
    private String total;
    private String caution;
    private String calcProv;
    private String totalProv;
   
    
    /**
     * Liste de charges variables (CV).  
     * Chaque String[] = { date, nomCharge, calcul, montantTotal }.  
     * On utilise surtout charge[1] = nomCharge, charge[2] = calcul.
     */
    private List<String[]> chargesCV;

    /**
     * Liste de charges fixes (CF).  
     * Chaque String[] = { date, nomCharge, "", montantTotal }.  
     * On utilise surtout charge[1] = nomCharge, charge[3] = montantTotal.
     */
    private List<String[]> chargesCF;

    public RapportSoldeToutCompte2() {
        this.chargesCV = new ArrayList<>();
        this.chargesCF = new ArrayList<>();
    }
    
    /**
     * Construit la map de tous les placeholders de base.
     */
    private Map<String, String> construirePlaceholderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("[NOM]",            safeStr(nom));
        map.put("[PRENOM]",         safeStr(prenom));
        map.put("[ADRESSE]",        safeStr(adresse));
        map.put("[COMPLEMENT]",     safeStr(complement));
        map.put("[CODEP]",          safeStr(codePostal));
        map.put("[VILLE]",          safeStr(ville));
        map.put("[DATE]",           safeStr(dateCourante));
        map.put("[DATE DEBUT]",     safeStr(dateDebut));
        map.put("[DATE FIN]",       safeStr(dateFin));
        map.put("[TOTAL CHARGE]",   safeStr(totalCharge));
        map.put("[TOTAL DEDUC]",    safeStr(totalDeduc));
        map.put("[TOTAL PROV]",     safeStr(totalProv));
        map.put("[CAUTION]",        safeStr(caution));
        map.put("[TOTAL]",          safeStr(total));
        map.put("[PROVISIONS]",     safeStr(calcProv));
        
        map.put("[CHARGESV]", "");
        map.put("[CHARGESF]", "");

        return map;
    }
    
    public void genererSoldeToutCompte(String nomFichierSortie) throws IOException {
        // 1) Construction de la map de placeholders
        Map<String, String> placeholders = construirePlaceholderMap();
        System.out.println("Map placeholders: " + placeholders);

        // 2) Charger le modèle depuis les ressources
        try (InputStream modele = new FileInputStream("./src/rapport/modeleToutCompte2.docx")) {
            // 3) Ouvrir le document
            try (XWPFDocument document = new XWPFDocument(modele)) {
                // Récupère la liste des paragraphes
                List<XWPFParagraph> paragraphs = document.getParagraphs();

                for (int i = 0; i < paragraphs.size(); i++) {
                    XWPFParagraph paragraph = paragraphs.get(i);
                    String paraText = paragraph.getText();

                    System.out.println("Paragraphe " + i + ": " + paraText);
                   
                    // Remplacer les placeholders standards pour ce paragraphe
                    remplacerPlaceholdersDansParagraphe(document, paragraph, placeholders);

                   
                }

                // 4) Écriture du document final
                try (OutputStream fileOut = new FileOutputStream(nomFichierSortie + ".docx")) {
                    document.write(fileOut);
                    System.out.println("Fichier généré: " + nomFichierSortie + ".docx");
                }
            }
        }
    }
    
	private static String safeStr(String val) {
        return val != null ? val : "";
    }
    
    /**
     * Remplace tous les placeholders standard ([NOM], [PRENOM], etc.) dans un paragraphe donné.
     */
    private void remplacerPlaceholdersDansParagraphe(XWPFDocument document, XWPFParagraph paragraph, Map<String, String> placeholders) {
    	
        boolean cv = false;
        boolean cf = false;
    	
        List<XWPFRun> runs = paragraph.getRuns();
        
        if (runs == null) {
            return;
        }
        
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            
            if (text != null) {
            	System.out.println(text);
            	cv = text.contains("[CHARGESV]");
            	cf = text.contains("[CHARGESF]");
            	
                for (Map.Entry<String, String> e : placeholders.entrySet()) {
                    if (text.contains(e.getKey())) {
                        text = text.replace(e.getKey(), e.getValue());
                    }
                }
                run.setText(text, 0);
                
                if (cv) {
                    insertionCV(document, paragraph);
                }
                if (cf) {
                    insertionCF(document, paragraph);
                }
            }
        }
    }

	public void insertionCF(XWPFDocument document, XWPFParagraph paragraph) {
		for (String[] charge : chargesCF) {
		    // Nom de charge (à gauche)
		    XWPFParagraph pNom = document.insertNewParagraph(paragraph.getCTP().newCursor());
		    pNom.setAlignment(ParagraphAlignment.LEFT);
		    XWPFRun runNom = pNom.createRun();
		    runNom.setText(charge[1]); // ex. "Eau"

		    // Calcul (à droite)
		    XWPFParagraph pCalc = document.insertNewParagraph(paragraph.getCTP().newCursor());
		    pCalc.setAlignment(ParagraphAlignment.RIGHT);
		    XWPFRun runCalc = pCalc.createRun();
		    runCalc.setText(charge[3]);  // le montant total
		}
	}

	public void insertionCV(XWPFDocument document, XWPFParagraph paragraph) {
		for (String[] charge : chargesCV) {
		    // Nom de charge (à gauche)
		    XWPFParagraph pNom = document.insertNewParagraph(paragraph.getCTP().newCursor());
		    pNom.setAlignment(ParagraphAlignment.LEFT);
		    XWPFRun runNom = pNom.createRun();
		    runNom.setText(charge[1]); // ex. "Eau"

		    // Calcul (à droite)
		    XWPFParagraph pCalc = document.insertNewParagraph(paragraph.getCTP().newCursor());
		    pCalc.setAlignment(ParagraphAlignment.RIGHT);
		    XWPFRun runCalc = pCalc.createRun();
		    runCalc.setText(charge[2]); // ex. "15 m³ x 3€ = 45€"
		}
	}
    
    // ------------------------------------------------------------------------
    // Setters
    // ------------------------------------------------------------------------
    public void setNom(String nom)                     { this.nom = nom; }
    public void setPrenom(String prenom)               { this.prenom = prenom; }
    public void setAdresse(String adresse)             { this.adresse = adresse; }
    public void setComplement(String complement)       { this.complement = complement; }
    public void setCodePostal(String codePostal)       { this.codePostal = codePostal; }
    public void setVille(String ville)                 { this.ville = ville; }
    public void setDateCourante(String dateCourante)   { this.dateCourante = dateCourante; }
    public void setDateDebut(String dateDebut)         { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin)             { this.dateFin = dateFin; }
    public void setTotalCharge(String totalCharge)     { this.totalCharge = totalCharge; }
    public void setTotalDeduc(String totalDeduc)       { this.totalDeduc = totalDeduc; }
    public void setTotal(String total)                 { this.total = total; }
    public void setCaution(String caution)             { this.caution = caution; }
    public void setTotalProv(String totalProv)         { this.totalProv = totalProv; }
    public void setCalcProv(String calcProv)           { this.calcProv = calcProv; }

    public void setChargesCV(List<String[]> chargesCV) { this.chargesCV = chargesCV; }
    public void setChargesCF(List<String[]> chargesCF) { this.chargesCF = chargesCF; }

    // ------------------------------------------------------------------------
    // Classe main de test
    // ------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            RapportSoldeToutCompte2 rapport = new RapportSoldeToutCompte2();

            // Initialisation des champs
            rapport.setNom("Dupont");
            rapport.setPrenom("Jean");
            rapport.setAdresse("10 rue des Fleurs");
            rapport.setComplement("Rés. Les Rosiers");
            rapport.setCodePostal("75001");
            rapport.setVille("Paris");
            rapport.setDateCourante("16/01/2025");
            rapport.setDateDebut("01/01/2025");
            rapport.setDateFin("31/01/2025");
            rapport.setTotalCharge("150");
            rapport.setTotalProv("200");
            rapport.setCaution("400");
            rapport.setTotalDeduc("600");
            rapport.setTotal("-450");
            rapport.setCalcProv("4*30 = 1234");

            // Charges variables (CV)
            List<String[]> listCV = new ArrayList<>();
            listCV.add(new String[]{"", "Eau", "15 m³ x 3€ = 45€", ""});
            listCV.add(new String[]{"", "Électricité", "80 kWh x 0.15€ = 12€", ""});
            rapport.setChargesCV(listCV);

            // Charges fixes (CF)
            List<String[]> listCF = new ArrayList<>();
            listCF.add(new String[]{"", "Taxe ordures ménagères", "", "80€"});
            listCF.add(new String[]{"", "Frais d'entretien immeuble", "", "70€"});
            rapport.setChargesCF(listCF);

            System.out.println("Début de la génération...");
            rapport.genererSoldeToutCompte("testRap");
            System.out.println("Document généré avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
