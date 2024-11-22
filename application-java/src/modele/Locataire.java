package modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Locataire {
	private String Id_locataire;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String date_naissance;
	private String lieu_de_naissance;
	private String acte_de_caution;
	private String Id_adresse;
	private Collection<Bail> Id_bail;
	
	
	public Locataire(String Id_locataire, String nom, String prenom, String email, String telephone, String date_naissance, String lieu_de_naissance, String acte_de_caution, String Id_adresse) {
		this.Id_locataire = Id_locataire;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.date_naissance = date_naissance;
		this.acte_de_caution = acte_de_caution;
		this.Id_adresse = Id_adresse;
		this.Id_bail = new HashSet<Bail>();
		
	}


	/**
	 * @return the id_locataire
	 */
	public String getId_locataire() {
		return Id_locataire;
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
	public String getDate_naissance() {
		return date_naissance;
	}


	/**
	 * @return the lieu_de_naissance
	 */
	public String getLieu_de_naissance() {
		return lieu_de_naissance;
	}


	/**
	 * @return the acte_de_caution
	 */
	public String getActe_de_caution() {
		return acte_de_caution;
	}


	/**
	 * @return the id_adresse
	 */
	public String getId_adresse() {
		return Id_adresse;
	}


	/**
	 * @return the id_bail
	 */
	public Collection<Bail> getId_bail() {
		return Id_bail;
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
	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}


	/**
	 * @param lieu_de_naissance the lieu_de_naissance to set
	 */
	public void setLieu_de_naissance(String lieu_de_naissance) {
		this.lieu_de_naissance = lieu_de_naissance;
	}


	/**
	 * @param acte_de_caution the acte_de_caution to set
	 */
	public void setActe_de_caution(String acte_de_caution) {
		this.acte_de_caution = acte_de_caution;
	}


	/**
	 * @param id_bail the id_bail to set
	 */
	public void setId_bail(Collection<Bail> id_bail) {
		Id_bail = id_bail;
	}


	@Override
	public String toString() {
		return "Locataire [Id_locataire=" + Id_locataire + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
				+ ", telephone=" + telephone + ", date_naissance=" + date_naissance + ", lieu_de_naissance="
				+ lieu_de_naissance + ", acte_de_caution=" + acte_de_caution + ", Id_adresse=" + Id_adresse
				+ ", Id_bail=" + Id_bail + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(Id_adresse, Id_bail, Id_locataire, acte_de_caution, date_naissance, email,
				lieu_de_naissance, nom, prenom, telephone);
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
		return this.Id_locataire == other.Id_locataire;
	}
	
	
	

}
