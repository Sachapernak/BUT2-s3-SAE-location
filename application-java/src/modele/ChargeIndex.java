package modele;

import java.math.BigDecimal;

public class ChargeIndex {
    private String id;
    private String dateDeReleve;
    private String type;
    
    // L'id du releve precedent est le meme.
    private String dateRelevePrecedent;
    
    private BigDecimal valeurCompteur;
    private BigDecimal coutVariable;
    private BigDecimal coutFixe;
    
    private String numDoc;
    private String dateDoc;
    

    public ChargeIndex(String id, String dateDeReleve, String type,
    					BigDecimal valeurCompteur, BigDecimal coutVariable, BigDecimal coutFixe, 
    					String numDoc, String dateDoc) {
        this.id = id;
        this.dateDeReleve = dateDeReleve;
        this.type = type;
        
        this.valeurCompteur = valeurCompteur;
        this.coutVariable = coutVariable;
        this.coutFixe = coutFixe;
        
        this.numDoc = numDoc;
        this.dateDoc = dateDoc;
    }


	public String getDateDeReleve() {
		return dateDeReleve;
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


	public BigDecimal getValeurCompteur() {
		return valeurCompteur;
	}


	public void setValeurCompteur(BigDecimal valeur) {
		this.valeurCompteur = valeur;
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
