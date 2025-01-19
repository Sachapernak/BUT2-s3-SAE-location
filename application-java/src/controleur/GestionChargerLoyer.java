package controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import modele.BienLocatif;
import modele.ConnexionBD;
import modele.DocumentComptable;
import modele.FactureBien;
import modele.TypeDoc;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoFactureBien;
import modele.dao.DaoLocataire;
import vue.ChargerLoyers;
import vue.FenBarreChargement;



/**
 * Contrôleur chargé de gérer le parsing d'un fichier CSV de loyers,
 * et de retourner une structure de données contenant :
 *  - identifiant logement
 *  - identifiant locataire
 *  - date au format yyyy-mm-dd
 *  - montant total (loyer + charges)
 *  - identifiant (numDoc) pseudo-unique
 */
public class GestionChargerLoyer {

	private ChargerLoyers fen;
	
	public GestionChargerLoyer(ChargerLoyers fen) {
		this.fen = fen;
	}

	
	
    /**
     * Lit le fichier CSV et renvoie une liste de lignes contenant les informations
     * suivantes dans l'ordre :
     *    [id_logement, id_locataire, date (yyyy-mm-dd), montant_total, numDoc]
     * 
     * numDoc est généré automatiquement via la ligne selectionnée et est du format :
     * [id_loc] (sur max 15 lignes) + Hash MD5 de la ligne. Taille max : 50 varchar2
     * 
     * La probabilité de collission pour le pire cas :  
     * 14000 loyers (3 paiement / mois pour 5 bien différents, pendant 80 ans),
     * payés par le meme locataire 
     * = 3.3*10E−31 soit approximativement 0
     * 
     * 
     * @param csvFile Fichier CSV devant contenir :
     *                - idLogement
     *                - idLocataire
     *                - dateStr (au format MM/AA)
     *                - loyer
     *                - provisionCharge
     * @return liste de listes de chaînes de caractères, chaque sous-liste correspond
     *         à une ligne de données [idLog, idLoc, date, montantTotal, numDoc].
     * @throws IOException en cas d'erreur de lecture du fichier
     */
    public List<List<String>> parseCSV(File csvFile) throws IOException {
        List<List<String>> resultat = new ArrayList<>();

        // Map pour détecter les doublons et incrémenter la date si nécessaire
        Map<String, Integer> dayTracker = new HashMap<>();

        // Try with ressource pour fermer le buffer directemetn a la fin
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
            	
                // Format attendu : idLogement;idLocataire;MM/AA;loyer;charge
                String[] tokens = ligne.split(";");
                if (tokens.length < 5) continue;

                String idLogement = tokens[0].trim();
                String idLocataire = tokens[1].trim();
                String dateStr = tokens[2].trim();    // ex: "12/23"
                String loyerStr = tokens[3].trim();
                String chargeStr = tokens[4].trim();

                BigDecimal loyer = parseMontant(loyerStr);
                BigDecimal charge = parseMontant(chargeStr);
                BigDecimal montantTotal = loyer.add(charge);

                // Conversion de la date au format yyyy-mm-dd
                String[] dateTokens = dateStr.split("/");
                int mois = Integer.parseInt(dateTokens[0]);
                int annee = Integer.parseInt(dateTokens[1]);
                int fullYear = 2000 + annee;  // année 2000

                // Préparation pour la gestion des doublons
                String truncatedLocataire = idLocataire.length() > 15 
                                            ? idLocataire.substring(0, 15) 
                                            : idLocataire;

                // Calcul du hash MD5 pour la ligne CSV
                String hashValue = computeMD5(ligne);

                // Clé pour détecter les doublons
                String clefDoublon = idLogement + "-" + truncatedLocataire + "-" 
                                    + hashValue + "-" + mois + "-" + fullYear;

                int day = dayTracker.getOrDefault(clefDoublon, 10);
                dayTracker.put(clefDoublon, day + 1);

                // Formatage de la date
                String dateAAAAMMJJ = String.format("%04d-%02d-%02d", fullYear, mois, day);

                // Construction du numDoc selon la nouvelle logique : locataire max 15 char + hash
                String numDoc = truncatedLocataire + hashValue;
                
                // S'assurer que le numDoc ne dépasse pas 50 caractères
                if (numDoc.length() > 50) {
                    numDoc = numDoc.substring(0, 50);
                }

                List<String> ligneDonnees = Arrays.asList(
                        idLogement,
                        idLocataire,
                        dateAAAAMMJJ,
                        montantTotal.toPlainString(),
                        numDoc
                );
                resultat.add(ligneDonnees);
            }
        }

        return resultat;
    }

    /**
     * Tente de parser un montant au format décimal, renvoie BigDecimal(0) si échec.
     *
     * @param montantStr chaîne représentant le montant
     * @return un BigDecimal représentant la valeur parsée (ou 0 en cas d'erreur)
     */
    private BigDecimal parseMontant(String montantStr) {
        try {
            return new BigDecimal(montantStr);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcule une empreinte MD5 pour une chaîne donnée.
     * Utilisé pour generer un identifiant unique.
     * @param input chaîne à hasher
     * @return une représentation hexadécimale de la valeur MD5
     */
    private String computeMD5(String input) {
        try {
        	
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            
            // Convertir le digest en hexadécimal
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
        	
            // Si MD5 n'est pas trouvé, renvoie le hash basique de java
            return Integer.toHexString(input.hashCode());
        }
    }
    /**
     * Ajoute la gestion d'evenement sur le bouton annuler.
     * 
     * Action : fermer la fenetre
     * 
     * @param btnAnnuler Le bouton
     */
	public void gestionAnnuler(JButton btnAnnuler) {
		btnAnnuler.addActionListener(e -> fen.dispose());
	}
	
	public void gestionBtnSupprimer(JButton btnSupprimer) {
		btnSupprimer.addActionListener(e -> {
        		if (fen.nbLigneTable() > 0) {
            		fen.ajouterNbLoyers(-1);
        		}
        		fen.supprimerLigne(fen.getSelectLineIndex());
		});
	}

	public void gestionBtnAjouter(JButton btnAjouter) {
		btnAjouter.addActionListener(e ->{
        		fen.ajouterLigneVide();
        		fen.scrollToBottom();
        		fen.ajouterNbLoyers(1);
        	});
	}


	public void gestionCharger(JButton btnCharger) {
	    btnCharger.addActionListener(evt -> {
	        List<List<String>> data = fen.getListsTable();
	        if (data.isEmpty()) {
	            fen.afficherMessageErreur("Aucun loyer à charger !");
	            return;
	        }
	        // Ouvre la vue FenBarreChargement (modale)
	        FenBarreChargement fenBarre = new FenBarreChargement(
	            fen, // la fenêtre parent
	            this, // le gestionnaire
	            data, // la liste de loyers
	            fen.getLienFichier() // le CSV
	        );
	        fenBarre.setVisible(true);
	        fen.fenClear();
	    });
	}

	/**
	 * Ajoute la gestion d'evenement sur le bouton parcourir
	 * 
	 * Action : ouvre le un JFileChooser, puis charge les données 
	 * du CSV vers une liste.
	 * 
	 * Charge ensuite les données dans la table.
	 * 
	 * Affiche un message d'erreur en cas d'erreur.
	 * 
	 * @param btnParcourirFichier
	 */
	public void gestionParcourirFichier(JButton btnParcourirFichier) {
		btnParcourirFichier.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(fen);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                fen.setTextLienFichier(selectedFile.getAbsolutePath());
                
                // Tente de parser le CSV et de l'afficher dans le tableau
                try {
                    List<List<String>> data = parseCSV(selectedFile);
                    
                   
                    fen.afficherDonneesLoyer(data);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
	}
	
    /**
     * Insertion en base de données, appelée depuis le SwingWorker de la vue FenBarreChargement.
     * 
     * @param data Liste de loyers
     * @param lienFichier chemin du CSV
     * @param consumerPublish callback pour 'publish(i+1)' ou tout autre indice d'avancement
     * @param consumerSetProgress callback pour 'setProgress(pourcentage)'
     * @throws SQLException, IOException
     */
    public void insertDonneesEnBD(List<List<String>> data,
                                  String lienFichier,
                                  IntConsumer consumerPublish,
                                  IntConsumer consumerSetProgress) 
                                  throws SQLException, IOException 
    {
        // On peut reprendre votre logique existante ici
        DaoDocumentComptable daoDoc = new DaoDocumentComptable();
        DaoBienLocatif daoBien = new DaoBienLocatif();
        DaoLocataire daoLoc = new DaoLocataire();
        DaoFactureBien daoFactureBien = new DaoFactureBien();
        ConnexionBD bd = ConnexionBD.getInstance();

        TypeDoc type = TypeDoc.LOYER;
        
        int total = data.size();
        
        try {
            bd.setAutoCommit(false);

            for (int i = 0; i < total; i++) {
                List<String> ligne = data.get(i);
                
                String idLog = ligne.get(0);
                String idLoc = ligne.get(1);
                String date = ligne.get(2);
                BigDecimal montant = new BigDecimal(ligne.get(3));
                String numDoc = ligne.get(4);

                BienLocatif bien = daoBien.findById(idLog);

                DocumentComptable doc = new DocumentComptable(numDoc, date, type, montant, lienFichier);
                System.out.println(doc.toString());
                doc.setBatiment(bien.getBat());
                doc.setRecuperableLoc(true);
                doc.setLocataire(daoLoc.findById(idLoc));
                
                daoDoc.create(doc);
                
                FactureBien fb = new FactureBien(bien, doc, 1);
                daoFactureBien.create(fb);

                // --- Mise à jour de la progression ---
                if (consumerPublish != null) {
                    consumerPublish.accept(i + 1); 
                    // par ex., "publish(i + 1)" dans la vue
                }
                if (consumerSetProgress != null) {
                    int pourcentage = (int) ((i + 1) * 100.0 / total);
                    consumerSetProgress.accept(pourcentage); 
                    // par ex., "setProgress(pourcentage)" dans la vue
                }
            }

            bd.valider();

        } catch (SQLException | IOException e) {
            bd.anuler();
            throw e; // on relance l’exception pour que la vue l’affiche
        } finally {
            bd.setAutoCommit(true);
        }
    }
}