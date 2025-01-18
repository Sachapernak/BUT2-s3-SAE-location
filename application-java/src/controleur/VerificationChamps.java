package controleur;

import java.math.BigDecimal;
import java.util.List;

public class VerificationChamps {
	
	private static final int TAILLE_CODE_POSTAL = 5;
	
	// Vérifie si le montant de la caution est bien un nombre valide (entier ou décimal), supérieur à 0
	// et qu'il n'excède pas le triple du loyer
	protected boolean validerMontant(String montantStr, String loyerStr) {
	    
		try {
	        BigDecimal montant = new BigDecimal(montantStr);
	        BigDecimal loyer = new BigDecimal(loyerStr);
	        BigDecimal loyerMax = loyer.multiply(BigDecimal.valueOf(3));
	        
	        return montant.compareTo(BigDecimal.ZERO) >= 0 && montant.compareTo(loyerMax) <= 0;
	    } catch (NumberFormatException e) {
	        return false; 
	    }
	}
	
	// Vérifie si un montant est bien un nombre valide (entier ou décimal) et supérieur à 0
	protected boolean validerBigDecimal(String montantStr) {
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
	        int entier = Integer.parseInt(valeur);
	        return entier > 0; 
	    } catch (NumberFormatException e) {
	        return false; 
	    }
	}
	
	protected boolean validerAnnee(String valeur) {
		if (valeur.length()!=4) {
			return false;
		}
		try {
	        int annee = Integer.parseInt(valeur);
	        return annee>0;
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
	
	public boolean verifierDates(List<String> champsDate) {
		for (String champ : champsDate) {
			if (!champ.equals("")&&!validerDate(champ)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean champsRemplis(List<String> champs) {
		for (String champ : champs) {
			if (champ.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean auMoinsUnChampRempli(List<String> champs) {
		for (String champ : champs) {
			if (!champ.isEmpty()) {
				 return true;
			}
		}
		return false;
	}
	
}
