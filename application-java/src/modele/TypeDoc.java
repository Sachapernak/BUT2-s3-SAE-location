package modele;

public enum TypeDoc {
	QUITTANCE("quittance"),
	FACTURE_CF("facture cf"),
	FACTURE_CV("facture cv"),
	FACTURE("facture"),
	DEVIS("devis"),
	LOYER("loyer"),
	REGULARISATION("regularisation");

	private String type;
	
	private TypeDoc(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
}
