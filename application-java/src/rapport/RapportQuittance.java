package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Classe pour générer un rapport de solde de tout compte avec insertion
 * de charges fixes (CF) et variables (CV) dans un document Word.
 */
public class RapportQuittance {

    private static final String FORMATDOC = ".docx";
	// --- Champs simples pour le locataire / bail ---
    // Champs correspondant aux placeholders
    private String nom;
    private String prenom;
    private String adresse;
    private String complement;
    private String codePostal;
    private String ville;
    private String dateCourante;
    private String datePaiement;
    private String totalPaiement;
    private String mois;
    private String totalLoyer;
    private String totalCharges;
    private String excedent;
    
    public RapportQuittance() {
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
        map.put("[DATEPAIEMENT]",   safeStr(datePaiement));
        map.put("[TOTALPAIEMENT]",  safeStr(totalPaiement));
        map.put("[MOIS]",           safeStr(mois));
        map.put("[TOTALL]",     safeStr(totalLoyer));
        map.put("[TOTALC]",   safeStr(totalCharges));
        map.put("[EXCED]",       safeStr(excedent));
        return map;
    }

    /**
     * Génère le document final en remplaçant les placeholders et en insérant les charges.
     *
     * @param nomFichierSortie Nom du fichier de sortie sans extension.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    public String genererSoldeToutCompte(String nomFichierSortie) throws IOException {
        // 1) Construction de la map de placeholders
        Map<String, String> placeholders = construirePlaceholderMap();

        // 2) Charger le modèle Word
        try (InputStream modele = new FileInputStream("./src/rapport/modeleQuittance.docx");
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
                text = remplacePlaceholder(placeholders, text);
                run.setText(text, 0);

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


    private static String safeStr(String val) {
        return val != null ? val : "";
    }

    // Setters correspondant aux placeholders utilisés dans la map
    public void setNom(String nom)              { this.nom = nom; }
    public void setPrenom(String prenom)        { this.prenom = prenom; }
    public void setAdresse(String adresse)      { this.adresse = adresse; }
    public void setComplement(String complement){ this.complement = complement; }
    public void setCodePostal(String codePostal){ this.codePostal = codePostal; }
    public void setVille(String ville)          { this.ville = ville; }
    public void setDateCourante(String dateCourante) { this.dateCourante = dateCourante; }
    public void setDatePaiement(String datePaiement) { this.datePaiement = datePaiement; }
    public void setTotalPaiement(String totalPaiement) { this.totalPaiement = totalPaiement; }
    public void setMois(String mois)            { this.mois = mois; }
    public void setTotalLoyer(String totalLoyer){ this.totalLoyer = totalLoyer; }
    public void setTotalCharges(String totalCharges){ this.totalCharges = totalCharges; }
    public void setExcedent(String excedent)    { this.excedent = excedent; }


    /**
     * Méthode principale pour tester la génération du document.
     */
    public static void main(String[] args) {
        try {
            RapportQuittance rapport = new RapportQuittance();

            // Initialisation des champs en fonction du document modèle
            rapport.setNom("Didier");
            rapport.setPrenom("Machin");
            rapport.setAdresse("18, rue des Lilas");
            rapport.setComplement("3eme etage");  // Exemple : aucun complément
            rapport.setCodePostal("31000");
            rapport.setVille("Toulouse");
            rapport.setDateCourante("16/08/2025");    // Date du document
            rapport.setDatePaiement("16/01/2025");    // Date du paiement
            rapport.setTotalPaiement("1234,56");      // Exemple de total payé
            rapport.setMois("Janvier 2025");
            rapport.setTotalLoyer("1000,00");         // Exemple de loyer
            rapport.setTotalCharges("150,00");        // Exemple de charges
            rapport.setExcedent("84,56");             // Exemple d'excédent

            // Générer le solde de tout compte
            String fichierGenere = rapport.genererSoldeToutCompte("RapportTestQuittance");

            System.out.println("Document généré : " + fichierGenere);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

