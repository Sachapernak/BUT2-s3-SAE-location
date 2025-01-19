package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Classe pour générer un rapport de solde de tout compte avec insertion
 * de charges fixes (CF) et variables (CV) dans un document Word.
 */
public class RapportRegularisation {

    private static final String FORMATDOC = ".docx";
	private static final String STYLECHARGE = "charge";
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
    private String calcProv;
    private String totalProv;
    
    private String prochaineDate;
    private String loyer;
    private String nouvProv;
    private String totalLoyerProv;

    /**
     * Liste de charges variables (CV).
     * Chaque String[] = { date, nomCharge, calcul, montantTotal }.
     */
    private List<String[]> charges;
    
    /**
     * Nom du fichier
     */
    private String nomFichier;


    public RapportRegularisation() {
        this.charges = new ArrayList<>();
        this.nomFichier = "";
    }
    
    public String getNomFichier() {
    	return this.nomFichier;
    }
    
    public void setNomFichier(String nomFichier) {
    	this.nomFichier = nomFichier;
    }
    
    private void calculerNouvLoyer() {
    	
    	BigDecimal loyer = new BigDecimal(this.loyer == null ? "0" : this.loyer);
    	
    	BigDecimal provCharge = new BigDecimal(this.nouvProv == null ? "0" : this.nouvProv);
    	
    	BigDecimal total = loyer.add(provCharge);
    	
    	this.totalLoyerProv = total.toString();
    	
    }

    /**
     * Construit la map de tous les placeholders de base à utiliser pour le remplacement.
     *
     * @return une map contenant les placeholders et leurs valeurs associées.
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
        map.put("[DATEDEBUT]",      safeStr(dateDebut));
        map.put("[DATEFIN]",        safeStr(dateFin));
        map.put("[TOTALCHARGE]",    safeStr(totalCharge));
        map.put("[TOTALDEDUC]",     safeStr(totalDeduc));
        map.put("[TOTALPROV]",      safeStr(totalProv));
        map.put("[TOTAL]",          safeStr(total));
        map.put("[PROVISIONS]",     safeStr(calcProv));
        map.put("[LOYER]",          safeStr(loyer));
        map.put("[NOUVPROV]",       safeStr(nouvProv));
        map.put("[TOTALLP]",        safeStr(totalLoyerProv));
        map.put("[PRODATE]",        safeStr(prochaineDate));
        
        
        // Placeholders pour insertion de charges : remplacés par vide.
        map.put("[CHARGES]", "");
        return map;
    }

    /**
     * Génère le document final en remplaçant les placeholders et en insérant les charges.
     *
     * @param nomFichierSortie Nom du fichier de sortie sans extension.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    public String genererSoldeToutCompte(String nomFichierSortie) throws IOException {
    	
    	if (totalLoyerProv == null || totalLoyerProv.isEmpty()) {
    		calculerNouvLoyer();
    	}
    	
        // 1) Construction de la map de placeholders
        Map<String, String> placeholders = construirePlaceholderMap();

        // 2) Charger le modèle Word
        try (InputStream modele = new FileInputStream("./src/rapport/modeleRegu.docx");
             XWPFDocument document = new XWPFDocument(modele)) {
            // Récupérer la liste des paragraphes du document
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                // Obtenir le texte complet du paragraphe malgré la segmentation en runs
                // Remplacer les placeholders dans ce paragraphe
                remplacerPlaceholdersDansParagraphe(document, paragraph, placeholders);
            }

            // 4) Écriture du document final
            try (OutputStream fileOut = new FileOutputStream(nomFichierSortie + FORMATDOC)) {
                document.write(fileOut);
                System.out.println("Fichier généré: " + nomFichierSortie + FORMATDOC);
                
                return nomFichierSortie + FORMATDOC;
            }
        }
    }


    /**
     * Remplace tous les placeholders standard dans un paragraphe donné et insère 
     * les charges variables ou fixes si détectées.
     *
     * @param document Le document Word.
     * @param paragraph Le paragraphe à traiter.
     * @param placeholders La map des placeholders et valeurs.
     */
    private void remplacerPlaceholdersDansParagraphe(XWPFDocument document, XWPFParagraph paragraph, Map<String, String> placeholders) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null) {
            return;
        }

        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                // Détection des placeholders de charges
                boolean cg = text.contains("[CHARGES]");

                text = remplacePlaceholder(placeholders, text);
                run.setText(text, 0);

                // Insertion des charges si les marqueurs ont été détectés
                if (cg) {
                    insertionCharge(document, paragraph);
                }

            }
        }
    }

	public String remplacePlaceholder(Map<String, String> placeholders, String text) {
		// Remplacer les placeholders standards dans le texte courant
		for (Map.Entry<String, String> e : placeholders.entrySet()) {
		    if (text.contains(e.getKey())) {
		        text = text.replace(e.getKey(), e.getValue());
		    }
		}
		return text;
	}


    /**
     * Insère les paragraphes pour les charges variables dans le document.
     * Applique le style "charge" sur les paragraphes insérés.
     *
     * @param document Le document Word.
     * @param paragraph Le paragraphe de référence pour l'insertion.
     */
    public void insertionCharge(XWPFDocument document, XWPFParagraph paragraph) {
        for (String[] charge : charges) {
        	
        	String montant;
        	
        	montant = charge[2].isEmpty() ? charge[3] + "€" : charge[2];
        	
            // Nom de la charge (à gauche)
            XWPFParagraph pNom = document.insertNewParagraph(paragraph.getCTP().newCursor());
            pNom.setAlignment(ParagraphAlignment.LEFT);
            pNom.setStyle(STYLECHARGE);  // Application du style "charge"
            XWPFRun runNom = pNom.createRun();
            runNom.setText(charge[1]);

            // Calcul (à droite)
            XWPFParagraph pCalc = document.insertNewParagraph(paragraph.getCTP().newCursor());
            pCalc.setAlignment(ParagraphAlignment.RIGHT);
            pCalc.setStyle(STYLECHARGE);  // Application du style "charge"
            XWPFRun runCalc = pCalc.createRun();
            runCalc.setText(montant);
        }
    }

    private static String safeStr(String val) {
        return val != null ? val : "";
    }

    // --- Setters ---

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

    public void setTotalProv(String totalProv)         { this.totalProv = totalProv; }
    public void setCalcProv(String calcProv)           { this.calcProv = calcProv; }
    public void setCharges(List<String[]> charges)     { this.charges = charges; }
    
    public void setProchaineDate(String prochaineDate) { this.prochaineDate = prochaineDate; }
    public void setLoyer(String loyer)                 { this.loyer = loyer; }
    public void setNouvProv(String nouvProv)           { this.nouvProv = nouvProv; }
    public void setTotalLoyerProv(String totalLP)      { this.totalLoyerProv = totalLP; }
    

    /**
     * Méthode principale pour tester la génération du document.
     */
    public static void main(String[] args) {
        try {
            RapportRegularisation rapport = new RapportRegularisation();

            // Initialisation des champs pour les placeholders
            rapport.setNom("MILLAN");
            rapport.setPrenom("Thierry");
            rapport.setAdresse("18, rue des Lilas");
            rapport.setComplement("");  // Par exemple, champ complément vide
            rapport.setCodePostal("31000");
            rapport.setVille("Toulouse");
            rapport.setDateCourante("16/01/2025");
            rapport.setDateDebut("01/01/2025");
            rapport.setDateFin("31/01/2025");
            rapport.setTotalCharge("150");
            rapport.setTotalDeduc("600");
            rapport.setTotalProv("200");
            rapport.setTotal("-450");
            rapport.setCalcProv("4*30 = 1234");
            
            // Initialisation des nouveaux champs pour le document
            rapport.setProchaineDate("01/02/2025");
            rapport.setLoyer("800");
            rapport.setNouvProv("220");
            rapport.setTotalLoyerProv("1020");

            // Préparation des charges (variables et fixes) pour insérer [CHARGES]
            List<String[]> listCV = new ArrayList<>();
            listCV.add(new String[]{"", "Eau", "15 m³ x 3€ = 45€", ""});
            listCV.add(new String[]{"", "Électricité", "80 kWh x 0.15€ = 12€", ""});

            List<String[]> listCF = new ArrayList<>();
            listCF.add(new String[]{"", "Taxe ordures ménagères", "", "80€"});
            listCF.add(new String[]{"", "Frais d'entretien immeuble", "", "70€"});

            // Combiner les listes CV et CF
            listCV.addAll(listCF);
            rapport.setCharges(listCV);

            // Génération du document
            String nomFichierGenere = rapport.genererSoldeToutCompte("testRapRegu");
            System.out.println("Document généré : " + nomFichierGenere);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
