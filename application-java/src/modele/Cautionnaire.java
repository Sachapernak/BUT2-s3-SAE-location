package modele;

import java.util.Objects;

/**
 * La classe Cautionnaire représente le cautionnaire du locataire avec un identifiant unique, un nomOuOrganisme et une prenom.
 * Elle permet de manipuler les informations du cautionnaire.
 */
public class Cautionnaire {
	
    /**
     * Identifiant unique du cautionnaire.
     */
	private int idCautionnaire;
    /**
     * nom ou le nom de l'organisme de la personne ou de l'organiseme
     */
	private String nomOuOrganisme;
    /**
     * prenom du cautionnaire
     */
	private String prenom; 
    /**
     * Description du cautionnaire
     */
	private String description;
	/**
     * Adresse du cautionnaire.
     */
    private Adresse adresse;
	
	/**
     * Constructeur de la classe Cautionnaire.
     *
     * @param idCautionnaire Identifiant unique du cautionnaire.
     * @param nomOuOrganisme nom ou Organisme du cautionnaire
     * @param prenom prenom de la personne
     * @param description
     */
	public Cautionnaire(int idCautionnaire, String nomOuOrganisme, String prenom, String description, Adresse adresse) {
		this.idCautionnaire = idCautionnaire;
		this.nomOuOrganisme = nomOuOrganisme;
		this.prenom = prenom;
		this.description = description;
		this.adresse = adresse;
	}

	/**
     * Retourne l'identifiant unique du cautionnaire.
     *
     * @return L'identifiant du cautionnaire.
     */
	public int getIdCautionnaire() {
		return idCautionnaire;
	}

	/**
     * Retourne le nomOuOrganisme du cautionnaire.
     *
     * @return Le nomOuOrganisme du cautionnaire.
     */
	public String getNomOuOrganisme() {
		return nomOuOrganisme;
	}

	/**
     * Retourne le prenom du cautionnaire.
     *
     * @return Le prenom du cautionnaire.
     */
	public String getPrenom() {
		return prenom;
	}

	/**
     * Retourne la description du bail.
     *
     * @return La description du bail.
     */
	public String getDescription() {
		return description;
	}
	
	/**
     * Retourne l'adresse du cautionnaire.
     *
     * @return L'adresse du cautionnaire.
     */
	public Adresse getAdresse() {
		return adresse;
	}	
	
	

	public void setIdCautionnaire(int idCautionnaire) {
		this.idCautionnaire = idCautionnaire;
	}

	public void setNomOuOrganisme(String nomOuOrganisme) {
		this.nomOuOrganisme = nomOuOrganisme;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
     * Calcule et retourne le code de hachage basé sur l'identifiant du bail.
     *
     * @return Le code de hachage du bail.
     */
	@Override
	public int hashCode() {
		return Objects.hash(idCautionnaire);
	}
	
	
	 /**
     * Compare deux objets Cautionnaire pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les cautionnaires ont le même identifiant, false sinon.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Cautionnaire)) {
			return false;
		}
		Cautionnaire other = (Cautionnaire) obj;
		return idCautionnaire == other.idCautionnaire;
	} 
	
	
	
	

}
