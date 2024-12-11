package modele;

public enum TypeDeBien {
	LOGEMENT("logement"),
	GARAGE("garage");
	
	public final String valeur;
	
	private TypeDeBien(String valeur) {
		this.valeur = valeur;
	}
	
}
