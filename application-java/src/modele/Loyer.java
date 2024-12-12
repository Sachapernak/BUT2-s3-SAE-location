package modele;

import java.math.BigDecimal;

public class Loyer {
    String idBien;
    String dateDeChangement;
    BigDecimal montantLoyer;

    public Loyer(String idBien, String dateDeChangement, BigDecimal montantLoyer) {
        
    	if (montantLoyer.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Un loyer ne peut etre negatif");}
    	
    	this.idBien = idBien;
        this.dateDeChangement = dateDeChangement;
        this.montantLoyer = montantLoyer;
    }

    public String getIdBien() {
        return idBien;
    }

    public String getDateDeChangement() {
        return dateDeChangement;
    }

    public BigDecimal getMontantLoyer() {
        return montantLoyer;
    }
    
    public void setMontantLoyer(BigDecimal montantLoyer) {
    	
    	if (montantLoyer.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Un loyer ne peut etre negatif");}
    	
    	this.montantLoyer = montantLoyer;
    }
}
