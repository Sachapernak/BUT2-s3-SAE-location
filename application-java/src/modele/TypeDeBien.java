package modele;

public enum TypeDeBien {
	LOGEMENT("logement"),
	GARAGE("garage");
	
	public final String valeur;
	
	private TypeDeBien(String valeur) {
		this.valeur = valeur;
	}
	
    public String getValeur() {
        return this.valeur;
    }
    
    //Méthode permettant de récupérer l'énum à partir d'un string
    public static TypeDeBien fromString(String text) {
        for (TypeDeBien type : TypeDeBien.values()) {
            if (type.valeur.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
