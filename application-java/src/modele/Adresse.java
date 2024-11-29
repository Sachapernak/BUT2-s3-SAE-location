package modele;

import java.util.Objects;

public class Adresse {
	
	private String idAdresse;
	private String adresse;
	private int codePostal;
	private String ville;
	private String complementAdresse;
	
	/**
	 * Constructeur de la classe Adresse. 
	 *
	 * @param idAdresse   l'identifiant unique de l'adresse
	 * @param adresse     la description textuelle de l'adresse (ex. rue et numéro)
	 * @param codePostal  le code postal de l'adresse
	 * @param ville       la ville associée à l'adresse
	 */
	public Adresse(String idAdresse, String adresse, int codePostal, String ville) {
		this.idAdresse = idAdresse;
		this.adresse =adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		
	}
	
	/**
	 * Constructeur de la classe Adresse. 
	 *
	 * @param idAdresse   l'identifiant unique de l'adresse
	 * @param adresse     la description textuelle de l'adresse (ex. rue et numéro)
	 * @param codePostal  le code postal de l'adresse
	 * @param ville       la ville associée à l'adresse
	 * @param complement  le complement d'adresse
	 */
	public Adresse(String idAdresse, String adresse, int codePostal, String ville, String complement) {
		this(idAdresse, adresse, codePostal, ville);
		this.complementAdresse = complement;
		
	}


	@Override
	public String toString() {
		return "(adresse " + idAdresse + ") " + adresse + ", " + complementAdresse + ", " + codePostal + " " + ville;
	}


	/**
	 * @return l'identifiant de l'adresse
	 */
	public String getIdAdresse() {
		return idAdresse;
	}


	/**
	 * @return l'adresse
	 */
	public String getAdresse() {
		return adresse;
	}


	/**
	 * @return le code postal
	 */
	public int getCodePostal() {
		return codePostal;
	}


	/**
	 * @return la ville
	 */
	public String getVille() {
		return ville;
	}


	/**
	 * @return le complement d'adresse
	 */
	public String getComplementAdresse() {
		return complementAdresse;
	}


	/**
	 * @param adresse La premiere ligne d'adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	/**
	 * @param code_postal Le code postal
	 */
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}


	/**
	 * @param ville La ville
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}


	/**
	 * @param complement_adresse Le complement d'adresse
	 */
	public void setComplementAdresse(String complementAdresse) {
		this.complementAdresse = complementAdresse;
	}

	
	// modifier
	@Override
	public int hashCode() {
		return Objects.hash(idAdresse);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Adresse)) {
			return false;
		}
		Adresse other = (Adresse) obj;
		return this.idAdresse == other.idAdresse;
	}
	
	
	
	

}