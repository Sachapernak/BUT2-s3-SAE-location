package modele;

public class FactureBien {



	private final BienLocatif bien;
	private final DocumentComptable document;
	
	float partDesCharges;
	
	public FactureBien(BienLocatif bien, DocumentComptable document, 
					   float partDesCharges) {
		
		if (partDesCharges <= 0 || partDesCharges > 1) {
			throw new IllegalArgumentException("La part des charges doit etre entre 0 et 1 !");
		}
		
		this.bien = bien;
		this.document = document;
		this.partDesCharges = partDesCharges;
	}
	
	public float getPartDesCharges() {
		return partDesCharges;
	}

	public void setPartDesCharges(float partDesCharges) {
		
		if (partDesCharges <= 0 || partDesCharges > 1) {
			throw new IllegalArgumentException("La part des charges doit etre entre 0 et 1 !");
		}
		
		this.partDesCharges = partDesCharges;
	}

	public BienLocatif getBien() {
		return bien;
	}

	public DocumentComptable getDocument() {
		return document;
	}
	
	
}
