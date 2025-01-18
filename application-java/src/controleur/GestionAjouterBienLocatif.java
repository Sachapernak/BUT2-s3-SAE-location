package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modele.Batiment;
import modele.BienLocatif;
import modele.Loyer;
import modele.TypeDeBien;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoLoyer;
import vue.AjouterBienLocatif;

/**
 * Contrôleur gérant l'ajout d'un batiment 
 * 
 * Cette classe interagit avec la vue AjouterBatiment et met à jour le
 * modèle (DAOs, entités) en fonction des actions de l'utilisateur.
 */

public class GestionAjouterBienLocatif implements ActionListener {

    private final DaoBatiment daoBatiment;
    private final DaoBienLocatif daoBienLoc;
    private final AjouterBienLocatif fenAjoutBienLocatif;
    private final VerificationChamps verifChamps;

    
    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param aj  la vue pour ajouter un batiment
     */
    public GestionAjouterBienLocatif(AjouterBienLocatif aj) {
        this.fenAjoutBienLocatif = aj;
        this.verifChamps = new VerificationChamps();
        this.daoBatiment = new DaoBatiment();
        this.daoBienLoc = new DaoBienLocatif();
    }

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        String btnLibelle = ((JButton) e.getSource()).getText();
        switch (btnLibelle) {
            case "Annuler":
            	this.fenAjoutBienLocatif.dispose();
                break;
            case "Ajouter":
            	if (!gererErreur()) {
            		handleValider();
                	this.fenAjoutBienLocatif.dispose();
            	}
                break;
            default:
                break;
        }
    }

    
    // --------------------------------------------------------------------
 	//                 Méthodes privées de gestion d'actions
 	// --------------------------------------------------------------------

 	/**
 	 * Gère l’action de validation dans la vue AjouterBatiment.
 	 */
    private void handleValider() {
        try {
            BienLocatif bienLoc = creerBien();
            if (bienLoc != null) {
                this.fenAjoutBienLocatif.afficherMessage("Bien locatif ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (Exception ex) {
            this.fenAjoutBienLocatif.afficherMessage("Erreur lors de la création du bâtiment : " + ex.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        this.fenAjoutBienLocatif.dispose();
    }

    /**
     * Vérifie si tous les champs obligatoires sont remplis et si le code postal est valide.
     * Affiche un message d'erreur si une condition n'est pas respectée.
     *
     * @return true si tous les champs sont valides, false sinon.
     */
    private boolean gererErreur() {
    	boolean erreurTrouvee = false;
        List<String> champs = this.fenAjoutBienLocatif.getChampsObligatoiresBienLocatif();

        if (!this.verifChamps.champsRemplis(champs)) {
            this.fenAjoutBienLocatif.afficherMessage("Tous les champs obligatoires doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            erreurTrouvee = true;
        }
        
        if (!this.verifChamps.validerBigDecimal(this.fenAjoutBienLocatif.getTextLoyerBase())) {
        	this.fenAjoutBienLocatif.afficherMessage("Le loyer doit être un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);
        	erreurTrouvee = true;
        }
        
        //Verification des  entiers
        String nbPiecesStr = this.fenAjoutBienLocatif.getTextNbPieces();
        if (!nbPiecesStr.isEmpty() &&!this.verifChamps.validerInteger(nbPiecesStr)) {
            this.fenAjoutBienLocatif.afficherMessage("Le nombre de pieces doit être un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
            erreurTrouvee = true;
        }
        String surfaceStr = this.fenAjoutBienLocatif.getTextSurface();
        if (!surfaceStr.isEmpty() &&!this.verifChamps.validerInteger(surfaceStr)) {
            this.fenAjoutBienLocatif.afficherMessage("La surface doit être un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
            erreurTrouvee = true;
        }

        return erreurTrouvee;
    }



    private Batiment recupererBatiment() {
    	String idBat = this.fenAjoutBienLocatif.getIdBatimentCombo();
    	Batiment batiment = null;
    	try {
			batiment = this.daoBatiment.findById(idBat);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    	return batiment;
    }
    
    /**
     * Crée un bien locatif en fonction des informations saisies par l'utilisateur.
     * Vérifie si un bien avec le même ID existe déjà en base de données avant de le créer.
     * Récupère également les informations du bâtiment associé.
     *
     * @return le bien locatif créé, ou null si une erreur survient (par ex. bien déjà existant).
     * @throws SQLException en cas de problème d'accès à la base de données.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    private BienLocatif recupererBienLocatif() {
    	String idBien = this.fenAjoutBienLocatif.getTextIdBien();
        try {
			if (daoBienLoc.findById(idBien) != null) {
			    this.fenAjoutBienLocatif.afficherMessage("Le bâtiment existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
			    return null;
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		    return null;
		}
        Batiment batiment = recupererBatiment();
        String loyerBaseStr = this.fenAjoutBienLocatif.getTextLoyerBase();
        BigDecimal loyerBase = convertStrToBigDecimal(loyerBaseStr);
        String idFiscal = this.fenAjoutBienLocatif.getTextIdFiscal();
        String complementAdresse = this.fenAjoutBienLocatif.getTextComplementAdr();
        String surfaceStr = this.fenAjoutBienLocatif.getTextSurface();
        int surface = convertStrToInt(surfaceStr);
        String nbPiecesStr = this.fenAjoutBienLocatif.getTextNbPieces();
        int nbPieces = convertStrToInt(nbPiecesStr);
        String typeStr = this.fenAjoutBienLocatif.getTypeBienCombo();
        TypeDeBien type = TypeDeBien.fromString(typeStr);  
             
        BienLocatif bienLoc = new BienLocatif(idBien,type,surface,nbPieces, loyerBase ,batiment);
        
        if(!idFiscal.isEmpty()) {
        	bienLoc.setIdFiscal(idFiscal);
        }
        if(!complementAdresse.isEmpty()) {
        	bienLoc.setComplementAdresse(complementAdresse);
        }
        return bienLoc;
	}
    

    private BienLocatif creerBien() throws SQLException, IOException {    
        BienLocatif bien = recupererBienLocatif(); 
        daoBienLoc.create(bien);
        return bien;
    }
    
    /**
     * Convertit une chaîne de caractères en un objet BigDecimal.
     * Si la chaîne ne peut pas être convertie (par exemple, si elle n'est pas un nombre valide), la méthode retourne null.
     *
     * @param valeur la chaîne de caractères à convertir.
     * @return un objet BigDecimal représentant la valeur, ou null si la conversion échoue.
     */
    private BigDecimal convertStrToBigDecimal(String valeur) {
    	BigDecimal newValeur;
    	try {
    	    newValeur = new BigDecimal(valeur);
    	} catch (NumberFormatException e) {
    	    return null;
    	}
    	return newValeur;
    }
    
    /**
     * Convertit une chaîne de caractères en un entier.
     * Si la chaîne ne peut pas être convertie (par exemple, si elle n'est pas un nombre valide), la méthode retourne -1.
     *
     * @param valeur la chaîne de caractères à convertir.
     * @return un entier représentant la valeur, ou -1 si la conversion échoue.
     */
    private int convertStrToInt(String valeur) {
    	try {
	        int entier = Integer.parseInt(valeur);
	        return entier; 
	    } catch (NumberFormatException e) {
	        return -1;
	    }
    }
    
    // --------------------------------------------------------------------
  	//                 Méthodes privées pour remplir les comboBox
  	// --------------------------------------------------------------------
    
    /**
     * Remplit la comboBox avec les différents id de batiment.
     */
    public void remplirComboBoxIdBat() {
    	List<Batiment> batiments;
		try {
			batiments = daoBatiment.findAll();
	    	List<String> idBat = new ArrayList<>();
	    	for (Batiment batiment : batiments) {
	    		idBat.add(batiment.getIdBat());
	    	}
	    	this.fenAjoutBienLocatif.setComboBoxBatiment(idBat);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * Remplit la comboBox avec les différents type de location.
     */
    public void remplirComboBoxTypeBienLoc() {
    	List<String> types = Arrays.stream(TypeDeBien.values())
                .map(TypeDeBien::getValeur) 
                .collect(Collectors.toList());
    	this.fenAjoutBienLocatif.setComboBoxTypeBien(types);

    }

    
    
}
