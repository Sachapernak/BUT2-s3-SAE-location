package modele;

import java.util.Objects;

public class Adresse {
	
	private String Id_adresse;
	private String adresse;
	private int Code_postal;
	private String ville;
	private String Complement_adresse;
	
	public Adresse(String Id_adresse, String adresse, int Code_postal, String ville, String Complement_adresse) {
		this.Id_adresse = Id_adresse;
		this.adresse =adresse;
		this.Code_postal = Code_postal;
		this.ville = ville;
		this.Complement_adresse = Complement_adresse;
	}


	@Override
	public String toString() {
		return "Adresse [Id_adresse=" + Id_adresse + ", adresse=" + adresse + ", Code_postal=" + Code_postal
				+ ", ville=" + ville + ", Complement_adresse=" + Complement_adresse + "]";
	}


	/**
	 * @return the id_adresse
	 */
	public String getId_adresse() {
		return Id_adresse;
	}


	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}


	/**
	 * @return the code_postal
	 */
	public int getCode_postal() {
		return Code_postal;
	}


	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}


	/**
	 * @return the complement_adresse
	 */
	public String getComplement_adresse() {
		return Complement_adresse;
	}


	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	/**
	 * @param code_postal the code_postal to set
	 */
	public void setCode_postal(int code_postal) {
		Code_postal = code_postal;
	}


	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}


	/**
	 * @param complement_adresse the complement_adresse to set
	 */
	public void setComplement_adresse(String complement_adresse) {
		Complement_adresse = complement_adresse;
	}


	@Override
	public int hashCode() {
		return Objects.hash(Code_postal, Complement_adresse, Id_adresse, adresse, ville);
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
		return this.Id_adresse == other.Id_adresse;
	}
	
	
	
	

}