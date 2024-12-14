package modele;

import java.math.BigDecimal;

public class ChargeFixe {
    private String dateDeCharge;
    private String type;
    private BigDecimal montant;
    
    // en String et pas en classe, car il ne devrait pas etre nécessaire de récuperer
    // la classe
    private String numDoc;
    private String dateDoc;
	private String id;
    

    public ChargeFixe(String id, String dateDeCharge, String type, BigDecimal montant, String numDoc, String dateDoc) {
        this.id = id;
    	
    	this.dateDeCharge = dateDeCharge;
        this.type = type;
        this.montant = montant;
        
        this.numDoc = numDoc;
        this.dateDoc = dateDoc;
    }


    public String getDateDeCharge() {
        return dateDeCharge;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getMontant() {
        return montant;
    }
    
    public String getNumDoc() {
    	return numDoc;
    }
    
    public String getDateDoc() {
    	return dateDoc;
    }


	public String getId() {
		return id;
	}
}
