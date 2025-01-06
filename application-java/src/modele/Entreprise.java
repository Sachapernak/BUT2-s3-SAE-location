package modele;

public class Entreprise {

	private String siret;
	private String nom;
	private String secteur;
	
	
	private Adresse adresse;
	
	public Entreprise(String siret, String nom, String secteur, 
			Adresse adresse) {
		
		this.siret = siret;
		this.nom = nom;
		this.secteur = secteur;
		this.adresse = adresse;
		
	}
	
	public String getSiret() {

		return this.siret;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getSecteur() {
		return this.secteur;
	}
	
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}
	
	public Adresse getAdresse() {
		return this.adresse;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

}
