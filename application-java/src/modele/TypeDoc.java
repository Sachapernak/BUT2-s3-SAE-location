package modele;

public enum TypeDoc {
	QUITTANCE("quittance"),
	FACTURE_CHARGE_FIXE("facture cf"),
	FACTURE_CHARGE_VARIABLE("facture cv"),
	FACTURE("facture");

	private String type;
	
	private TypeDoc(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	
}
