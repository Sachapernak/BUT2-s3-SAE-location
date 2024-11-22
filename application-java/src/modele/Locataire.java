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
	private Collection<Bail> IdBail;
	
	
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
		this.IdBail = new HashSet<Bail>();
		
	}


	/**
	 * @return the id_locataire
	 */
	public String getIdLocataire() {
		return Idlocataire;
	}


	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}


	/**
	 * @return the date_naissance
	 */
	public String getDateNaissance() {
		return dateNaissance;
	}


	/**
	 * @return the lieu_de_naissance
	 */
	public String getLieuDeNaissance() {
		return lieuDeNaissance;
	}


	/**
	 * @return the acte_de_caution
	 */
	public String getActeDeCaution() {
		return acteDeCaution;
	}


	/**
	 * @return the id_adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}


	/**
	 * @return the id_bail
	 */
	public Collection<Bail> getIdBail() {
		return IdBail;
	}


	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 * @param date_naissance the date_naissance to set
	 */
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	/**
	 * @param lieu_de_naissance the lieu_de_naissance to set
	 */
	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance = lieuDeNaissance;
	}


	/**
	 * @param acte_de_caution the acte_de_caution to set
	 */
	public void setActeDeCaution(String acteDeCaution) {
		this.acteDeCaution = acteDeCaution;
	}


	/**
	 * @param id_bail the id_bail to set
	 */
	public void setIdBail(Collection<Bail> idBail) {
		IdBail = idBail;
	}


	@Override
	public String toString() {
		return "Locataire [Id_locataire=" + Idlocataire + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
				+ ", telephone=" + telephone + ", date_naissance=" + dateNaissance + ", lieu_de_naissance="
				+ lieuDeNaissance + ", acte_de_caution=" + acteDeCaution + ", Id_adresse=" + adresse
				+ ", Id_bail=" + IdBail + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(adresse, IdBail, Idlocataire, acteDeCaution, dateNaissance, email,
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
