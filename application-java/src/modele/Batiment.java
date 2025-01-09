package modele;


public class Batiment {
	
	
	private String idBat;
	private Adresse adresse;
	
	public Batiment(String idBat, Adresse adresse) {
		
		this.idBat = idBat;
		this.adresse = adresse;
		
	}
	
	public String getIdBat() {
		return idBat;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	
}
