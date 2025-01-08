package controleur;

import java.math.BigDecimal;

public class VerificationFormatChamps {
	
	private static final int TAILLE_CODE_POSTAL = 5;
	
	// Vérifie si un montant est bien un nombre valide (entier ou décimal) et supérieur à 0
	protected boolean validerMontant(String montantStr) {
	    try {
	        BigDecimal montant = new BigDecimal(montantStr);
	        return montant.compareTo(BigDecimal.ZERO) >= 0;
	    } catch (NumberFormatException e) {
	        return false; 
	    }
	}

	// Vérifie si l'identifiant du cautionnaire est bien un entier positif
	protected boolean validerInteger(String valeur) {
	    try {
	        int idCautionnaire = Integer.parseInt(valeur);
	        return idCautionnaire > 0; 
	    } catch (NumberFormatException e) {
	        return false; 
	    }
	}
	
	protected boolean validerCodePostal(String codePostalStrC) {
		return validerInteger(codePostalStrC) && codePostalStrC.length() == TAILLE_CODE_POSTAL;
	}

	// Vérifie si une date est bien au format yyyy-MM-dd
	protected boolean validerDate(String dateStr) {
	    try {
	        java.time.LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        return true;
	    } catch (java.time.format.DateTimeParseException e) {
	        return false; 
	    }
	}
}
