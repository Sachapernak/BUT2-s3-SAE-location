package modele;

import java.util.List;

public class Batiment {
	
	// TODO finir : ajouter les factures du bat
	
	private String idBat;
	private Adresse adresse;
	private List<DocumentComptable> documents;
	
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
