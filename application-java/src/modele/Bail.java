package modele;

import java.util.Objects;

public class Bail {
	
	private String id_bail;
	private String date_de_debut;
	private String date_de_fin;
	
	public Bail(String id_bail, String date_de_debut, String date_de_fin) {
		this.date_de_debut = date_de_debut;
		this.date_de_fin = date_de_fin;
	}
	
	
	/**
	 * @return l'identifiant du bail
	 */
	public String getId_bail() {
		return id_bail;
	}

	/**
	 * @return la date de debut
	 */
	public String getDate_de_debut() {
		return date_de_debut;
	}

	/**
	 * @param date de debut la date de debut au bail
	 */
	public void setDate_de_debut(String date_de_debut) {
		this.date_de_debut = date_de_debut;
	}

	/**
	 * @return la date de fin
	 */
	public String getDate_de_fin() {
		return date_de_fin;
	}

	/**
	 * @param date de fin la date de fin au bail
	 */
	public void setDate_de_fin(String date_de_fin) {
		this.date_de_fin = date_de_fin;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id_bail);
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
		return Objects.equals(id_bail, other.id_bail);
	}

	
	
}
