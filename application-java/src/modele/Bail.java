package modele;

import java.util.Objects;

public class Bail {
	
	private String idBail;
	private String dateDeDebut;

	private String dateDeFin;
	
	
	
	/**
	 * Constructeur de la classe Bail. 
	 *
	 * @param idBail      l'identifiant unique du bail
	 * @param dateDeDebut la date du debut du bail
	 */
	public Bail(String idBail, String dateDeDebut) {
		this.idBail = idBail;
		this.dateDeDebut = dateDeDebut;
		
	}
	
	/**
	 * Constructeur de la classe Bail. 
	 *
	 * @param idBail      l'identifiant unique du bail
	 * @param dateDeDebut la date de debut du bail
	 * @param dateDeFin   la date de fin du bail
	 */
	public Bail(String idBail, String dateDeDebut, String dateDeFin) {
		this.idBail = idBail;
		this.dateDeDebut = dateDeDebut;
		this.dateDeFin = dateDeFin;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Bail " + idBail + ", " + dateDeDebut + ", " + dateDeFin;
	}

	/**
	 * @return l'identifiant du bail
	 */
	public String getIdBail() {
		return idBail;
	}

	/**
	 * @return la date de debut
	 */
	public String getDateDeDebut() {
		return dateDeDebut;
	}

	/**
	 * @param date de debut la date de debut au bail
	 */
	public void setDateDeDebut(String date_de_debut) {
		this.dateDeDebut = date_de_debut;
	}

	/**
	 * @return la date de fin
	 */
	public String getDateDeFin() {
		return dateDeFin;
	}

	/**
	 * @param date de fin la date de fin au bail
	 */
	public void setDateDeFin(String date_de_fin) {
		this.dateDeFin = date_de_fin;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idBail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Bail)) {
			return false;
		}
		Bail other = (Bail) obj;
		return Objects.equals(idBail, other.idBail);
	}

	
	
}
