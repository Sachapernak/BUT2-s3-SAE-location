package modele;

import java.util.ArrayList;
import java.util.List;
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
	private List<Contracter> contrats;
	
	
	public Locataire(String IdLocataire, String nom, String prenom, String dateNaissance) {
		this.Idlocataire = IdLocataire;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.contrats = new ArrayList<Contracter>();		
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
	 * @return La liste des contrats attaché au locataires
	 */
	public List<Contracter> getContrats() {
		return contrats;
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
	    StringBuilder res = new StringBuilder();

	    res.append("Locataire ").append(Idlocataire).append(": ")
	       .append(nom).append(" ").append(prenom)
	       .append("\n    Email, Telephone : ").append(email != null ? email : "non renseigné")
	       .append(", ").append(telephone != null ? telephone : "non renseigné")
	       .append("\n    Naissance : ").append(dateNaissance)
	       .append(" à ").append(lieuDeNaissance != null ? lieuDeNaissance : "non renseigné")
	       .append("\n    Lien acte de caution : ").append(acteDeCaution != null ? acteDeCaution : "non renseigné")
	       .append("\n    Adresse : ").append(adresse != null ? adresse.toString() : "non renseignée")
	       .append("\n    Baux : [");

	    if (contrats != null && !contrats.isEmpty()) {
	        for (Contracter contrat : contrats) {
	            res.append(contrat.getBail() != null ? contrat.getBail().toString() : "Bail non renseigné").append(", ");
	        }
	        res.setLength(res.length() - 2); // Supprimer la dernière virgule et l'espace
	    } else {
	        res.append("Aucun bail associé");
	    }

	    res.append("]\n");
	    return res.toString();
	}


	// Que les truc du equals
	@Override
	public int hashCode() {
		return Objects.hash(Idlocataire);
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
