package modele;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChargeIndex {
    String id;
    String dateDeReleve;
    String type;
    
    String dateRelevePrecedent;
    
    BigDecimal valeur;
    BigDecimal coutVariable;
    BigDecimal coutFixe;
    
    String numDoc;
    String dateDoc;
    

    public ChargeIndex(String id, String dateDeReleve, String type,
    					BigDecimal valeur, BigDecimal coutVariable, BigDecimal coutFixe, 
                        String numDoc, String dateDoc) {
        this.id = id;
        this.dateDeReleve = dateDeReleve;
        this.type = type;
        
        this.valeur = valeur;
        this.coutVariable = coutVariable;
        this.coutFixe = coutFixe;
        
        this.numDoc = numDoc;
        this.dateDoc = dateDoc;
    }


	public String getDateDeReleve() {
		return dateDeReleve;
	}


	public void setDateDeReleve(String dateDeReleve) {
		this.dateDeReleve = dateDeReleve;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDateRelevePrecedent() {
		return dateRelevePrecedent;
	}


	public void setDateRelevePrecedent(String dateRelevePrecedent) {
		this.dateRelevePrecedent = dateRelevePrecedent;
	}


	public BigDecimal getValeur() {
		return valeur;
	}


	public void setValeur(BigDecimal valeur) {
		this.valeur = valeur;
	}


	public BigDecimal getCoutVariable() {
		return coutVariable;
	}


	public void setCoutVariable(BigDecimal coutVariable) {
		this.coutVariable = coutVariable;
	}


	public BigDecimal getCoutFixe() {
		return coutFixe;
	}


	public void setCoutFixe(BigDecimal coutFixe) {
		this.coutFixe = coutFixe;
	}


	public String getId() {
		return id;
	}


	public String getNumDoc() {
		return numDoc;
	}


	public String getDateDoc() {
		return dateDoc;
	}
    
    
    
    

    
   
}
