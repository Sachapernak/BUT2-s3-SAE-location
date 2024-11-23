package modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Locataire {
	private String Idlocataire;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String dateNaissance;
	private String lieuDeNaissance;
	private String acteDeCaution;
	private Adresse adresse;
	private Collection<Bail> baux;
	
	
	public Locataire(String IdLocataire, String nom, String prenom, String dateNaissance) {
		this.Idlocataire = IdLocataire;
		this.nom = nom;
		this.prenom = prenom;
		//this.email = email;
		//this.telephone = telephone;
		this.dateNaissance = dateNaissance;
		//this.acteDeCaution = acte_de_caution;
		//this.lieuDeNaissance = lieu_de_naissance;
		//this.adresse = adresse;
		this.baux = new HashSet<Bail>();
		
	}


	/**
	 * @return L'identifiant du locataire
	 */
	public String getIdLocataire() {
		return Idlocataire;
	}


	/**
	 * @return Le nom du locataire
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * @return Le prenom du locataire
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * @return L'email du locataire
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return Le numero de telephone du locataire
	 */
	public String getTelephone() {
		return telephone;
	}


	/**
	 * @return La date de naissance du locataire
	 */
	public String getDateNaissance() {
		return dateNaissance;
	}


	/**
	 * @return Le lieu de naissance du locataire
	 */
	public String getLieuDeNaissance() {
		return lieuDeNaissance;
	}


	/**
	 * @return Le lien vers l'acte de caution du locataire
	 */
	public String getActeDeCaution() {
		return acteDeCaution;
	}


	/**
	 * @return L'adresse de contact du locataire
	 */
	public Adresse getAdresse() {
		return adresse;
	}


	/**
	 * @return La liste des bail attachée au locataire
	 */
	public Collection<Bail> getBaux() {
		return baux;
	}


	/**
	 * @param nom Le nom du locataire
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * @param prenom Le prenom du locataire
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	/**
	 * @param email L'email du locataire
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @param telephone Le numero de telephone du locataire
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 * @param date_naissance La date de naissance du locataire
	 */
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	/**
	 * @param lieu_de_naissance Le lieu de naissance du locataire
	 */
	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance = lieuDeNaissance;
	}

	/**
	 * @param adresse L'adresse de contact du locataire
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @param acteDeCaution Le lien vers l'acte de caution
	 */
	public void setActeDeCaution(String acteDeCaution) {
		this.acteDeCaution = acteDeCaution;
	}



	@Override
	public String toString() {		
		return "Locataire "+ Idlocataire + ": " + nom + " " + prenom 
				+ "\n    Email, Telephone : " + email + ", " + telephone
				+ "\n    Naissance : " + dateNaissance + " à " + lieuDeNaissance
				+ "\n    Lien acte de caution : " + acteDeCaution
				+ "\n    Adresse : " + adresse
				+ "\n    Baux : " + baux + "\n";
	}


	@Override
	public int hashCode() {
		return Objects.hash(adresse, baux, Idlocataire, acteDeCaution, dateNaissance, email,
				lieuDeNaissance, nom, prenom, telephone);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Locataire)) {
			return false;
		}
		Locataire other = (Locataire) obj;
		return this.Idlocataire == other.Idlocataire;
	}
	
	
	

}
