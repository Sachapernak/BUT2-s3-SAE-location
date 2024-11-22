package modele;

import java.util.List;
import java.util.Objects;

public class Locataire {
	private String id;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String dateNaissance; // est une date ?
	private String lieuNaissance;
	private String fichierCaution;
	
	private List<EtreLie> liens;
	
	private Adresse adresse;
	
	public Locataire(String id, String nom, String prenom, String dateNaissance) {
		
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locataire other = (Locataire) obj;
		return Objects.equals(id, other.id);
	}


	// Getter / Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getFichierCaution() {
		return fichierCaution;
	}

	public void setFichierCaution(String fichierCaution) {
		this.fichierCaution = fichierCaution;
	}

	public List<EtreLie> getLiens() {
		return liens;
	}

	public void setLiens(List<EtreLie> liens) {
		this.liens = liens;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getId() {
		return id;
	}
	
	
	
}

