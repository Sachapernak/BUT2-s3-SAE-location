package rapport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Exemple complet de génération d'un solde de tout compte
 * avec insertion de charges CV/CF depuis un modèle Word (modeleToutCompte2.docx).
 */
public class RapportSoldeToutCompte {

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

    public RapportSoldeToutCompte() {
        this.chargesCV = new ArrayList<>();
        this.chargesCF = new ArrayList<>();
    }

    /**
     * Génère le solde de tout compte.
     * @param nomFichierSortie (sans l'extension .docx)
     */
    public void genererSoldeToutCompte(String nomFichierSortie) throws IOException {
        // 1) Construction de la map de placeholders
        Map<String, String> placeholders = construirePlaceholderMap();
        System.out.println("Map placeholders: " + placeholders);

        // 2) Charger le modèle depuis les ressources
        try (InputStream modele = getClass().getResourceAsStream("/rapport/modeleToutCompte2.docx")) {
            if (modele == null) {
                throw new FileNotFoundException(
                        "Impossible de trouver /rapport/modeleToutCompte2.docx dans le classpath !");
            }

            // 3) Ouvrir le document
            try (XWPFDocument document = new XWPFDocument(modele)) {

                // --- On va relever les indices des paragraphes qui contiennent [CHARGES_CV] ou [CHARGES_CF].
                List<Integer> cvIndices = new ArrayList<>();
                List<Integer> cfIndices = new ArrayList<>();

                // Récupère la liste des paragraphes
                List<XWPFParagraph> paragraphs = document.getParagraphs();

                // -------------------------------------------------------------------------
                // 3.1) Première boucle : 
                //      - Pour chaque paragraphe, si on voit [CHARGES_CV], on enregistre l'index
                //      - Si [CHARGES_CF], on enregistre l'index
                //      - Sinon, on fait les remplacements standards
                // -------------------------------------------------------------------------
                for (int i = 0; i < paragraphs.size(); i++) {
                    XWPFParagraph paragraph = paragraphs.get(i);
                    String paraText = paragraph.getText();

                    System.out.println("Paragraphe " + i + " (AVANT) : " + paraText);

                    if (paraText.contains("[CHARGES_CV]")) {
                        cvIndices.add(i);
                        // On supprime le placeholder dans ce paragraphe
                        retirerPlaceholder(paragraph, "[CHARGES_CV]");
                        continue;
                    }
                    if (paraText.contains("[CHARGES_CF]")) {
                        cfIndices.add(i);
                        // On supprime le placeholder dans ce paragraphe
                        retirerPlaceholder(paragraph, "[CHARGES_CF]");
                        continue;
                    }

                    // Sinon, remplacer les placeholders standard
                    remplacerPlaceholdersDansParagraphe(paragraph, placeholders);
                }

                // -------------------------------------------------------------------------
                // 3.2) On supprime les paragraphes (en partant de la fin)
                //      pour ceux qui contenaient [CHARGES_CV] et [CHARGES_CF].
                // -------------------------------------------------------------------------
                // On commence par les indices CV
                for (int i = cvIndices.size() - 1; i >= 0; i--) {
                    int idx = cvIndices.get(i);
                    document.removeBodyElement(document.getPosOfParagraph(paragraphs.get(idx)));
                }
                // Puis CF
                for (int i = cfIndices.size() - 1; i >= 0; i--) {
                    int idx = cfIndices.get(i);
                    document.removeBodyElement(document.getPosOfParagraph(paragraphs.get(idx)));
                }

                // -------------------------------------------------------------------------
                // 3.3) On réinsère nos charges CV et CF à ces mêmes emplacements
                //
                // Note : comme on supprime d'abord, puis qu'on réinsère dans l'ordre
                //       (du plus petit index au plus grand), on retrouve le bon emplacement.
                // -------------------------------------------------------------------------

                // Si tu tiens à réinsérer EXACTEMENT au même index, tu peux faire quelque chose comme :
                //  - Récupérer la BodyElements pour manipuler plus précisément
                //  - Insérer de nouveaux <w:p> (paragraphes) via body.insertNewP(pos).
                //
                // Ci-dessous, un exemple minimaliste où on recrée un paragraphe
                // à l'index voulu, puis on y injecte les pNom/pCalc. 
                // (Selon la version de POI, tu peux ajuster.)

                // Réinsertion charges CV
                for (int i = 0; i < cvIndices.size(); i++) {
                    int idx = cvIndices.get(i);

                    // On réinsère un paragraphe "fictif" à l'index exact
                    XWPFParagraph basePar = insertParagraphAt(document, idx);
                    // Puis on insère les détails
                    insererChargesCV(document, basePar);
                }

                // Réinsertion charges CF
                // (même principe, sauf qu'il faut faire attention aux indices si
                //  un CFIndex < CVIndex. On part du plus petit -> plus grand.)
                for (int i = 0; i < cfIndices.size(); i++) {
                    int idx = cfIndices.get(i);

                    XWPFParagraph basePar = insertParagraphAt(document, idx);
                    insererChargesCF(document, basePar);
                }

                // -------------------------------------------------------------------------
                // 4) Écriture du document final
                // -------------------------------------------------------------------------
                try (OutputStream fileOut = new FileOutputStream(nomFichierSortie + ".docx")) {
                    document.write(fileOut);
                    System.out.println("Fichier généré: " + nomFichierSortie + ".docx");
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Méthodes utilitaires
    // ------------------------------------------------------------------------

    /**
     * Insère "à l'index bodyIdx" un nouveau paragraphe vide dans le body du document.
     * Retourne le XWPFParagraph créé.
     */
    private XWPFParagraph insertParagraphAt(XWPFDocument doc, int bodyIdx) {
        // On va manipuler la liste IBodyElement pour insérer un <w:p> (nouveau paragraphe) à l'endroit voulu.
        // NB : si bodyIdx est trop grand, on peut le clipser à la taille max, ou alors on le place en fin.
        if (bodyIdx < 0) bodyIdx = 0;
        int max = doc.getBodyElements().size();
        if (bodyIdx > max) bodyIdx = max;

        // On insère un <w:p> XML au sein du document
        doc.getDocument().getBody().insertNewP(bodyIdx);

        // On relit la liste pour récupérer notre nouveau paragraphe
        // car insertNewP n'est pas super direct pour renvoyer le XWPFParagraph
        List<IBodyElement> bodyElems = doc.getBodyElements();
        IBodyElement insertedElem = bodyElems.get(bodyIdx);
        if (insertedElem instanceof XWPFParagraph) {
            return (XWPFParagraph) insertedElem;
        }
        // Si ça n'est pas un paragraphe (cas improbable), on crée un paragraphe en fin
        return doc.createParagraph();
    }

    /**
     * Supprime un placeholder donné (ex: "[CHARGES_CV]") dans un paragraphe.  
     * Remplace la chaîne par "" dans tous les runs.
     */
    private void retirerPlaceholder(XWPFParagraph paragraph, String placeholder) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null && text.contains(placeholder)) {
                run.setText(text.replace(placeholder, ""), 0);
            }
        }
    }

    /**
     * Remplace tous les placeholders standard ([NOM], [PRENOM], etc.) dans un paragraphe donné.
     */
    private void remplacerPlaceholdersDansParagraphe(XWPFParagraph paragraph, Map<String, String> placeholders) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null) return;
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text == null) continue;
            for (Map.Entry<String, String> e : placeholders.entrySet()) {
                if (text.contains(e.getKey())) {
                    text = text.replace(e.getKey(), e.getValue());
                }
            }
            run.setText(text, 0);
        }
    }

    /**
     * Insère les chargesCV juste après le paragraphe basePar.
     * On crée 2 paragraphes par charge : (Nom/gauche), (Calcul/droite).
     */
    private void insererChargesCV(XWPFDocument document, XWPFParagraph basePar) {
        for (String[] charge : chargesCV) {
            if (charge.length < 4) continue;
            String nomCharge = charge[1];
            String calcul = charge[2];

            // 1) Paragraphe (nom), aligné à gauche
            XWPFParagraph pNom = document.insertNewParagraph(basePar.getCTP().newCursor());
            pNom.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun runNom = pNom.createRun();
            runNom.setText(nomCharge);

            // 2) Paragraphe (calcul), aligné à droite
            XWPFParagraph pCalc = document.insertNewParagraph(pNom.getCTP().newCursor());
            pCalc.setAlignment(ParagraphAlignment.RIGHT);
            pCalc.createRun().setText(calcul);

            // NB : Selon la version d'Apache POI, l'ordre final peut parfois s'inverser.
            //      Si tu vois "15 m³ x 3€..." avant "Eau", tu peux inverser l'ordre
            //      ou insérer toujours par rapport à basePar (et non pNom).
        }
    }

    /**
     * Insère les chargesCF juste après le paragraphe basePar.
     */
    private void insererChargesCF(XWPFDocument document, XWPFParagraph basePar) {
        for (String[] charge : chargesCF) {
            if (charge.length < 4) continue;
            String nomCharge = charge[1];
            String montant = charge[3];

            // 1) Paragraphe (nom), aligné à gauche
            XWPFParagraph pNom = document.insertNewParagraph(basePar.getCTP().newCursor());
            pNom.setAlignment(ParagraphAlignment.LEFT);
            pNom.createRun().setText(nomCharge);

            // 2) Paragraphe (montant), aligné à droite
            XWPFParagraph pMontant = document.insertNewParagraph(pNom.getCTP().newCursor());
            pMontant.setAlignment(ParagraphAlignment.RIGHT);
            pMontant.createRun().setText(montant);
        }
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
        // Si ton modèle contient [PROVISIONS], par ex.
        map.put("[PROVISIONS]",     "Détail des provisions...");

        return map;
    }

    private static String safeStr(String val) {
        return val != null ? val : "";
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

    public void setChargesCV(List<String[]> chargesCV) { this.chargesCV = chargesCV; }
    public void setChargesCF(List<String[]> chargesCF) { this.chargesCF = chargesCF; }

    // ------------------------------------------------------------------------
    // Méthode main de test
    // ------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            RapportSoldeToutCompte rapport = new RapportSoldeToutCompte();
            // Champs simples :
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

            // Charges variables (CV) :
            List<String[]> listCV = new ArrayList<>();
            listCV.add(new String[]{"", "Eau", "15 m³ x 3€ = 45€", ""});
            listCV.add(new String[]{"", "Électricité", "80 kWh x 0.15€ = 12€", ""});
            rapport.setChargesCV(listCV);

            // Charges fixes (CF) :
            List<String[]> listCF = new ArrayList<>();
            listCF.add(new String[]{"", "Taxe ordures ménagères", "", "80€"});
            listCF.add(new String[]{"", "Frais d'entretien immeuble", "", "70€"});
            rapport.setChargesCF(listCF);

            // Génération
            System.out.println("Début de la génération...");
            rapport.genererSoldeToutCompte("testRap");
            System.out.println("Document généré avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
