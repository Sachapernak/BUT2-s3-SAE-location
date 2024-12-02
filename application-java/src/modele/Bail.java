package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	 * Ce constructeur initialise un objet de type Bail avec un identifiant unique, 
	 * une date de début et une date de fin. Il effectue une validation des dates 
	 * pour s'assurer que la date de début est strictement antérieure à la date de fin.
	 *
	 * @param idBail      l'identifiant unique du bail, sous forme de chaîne de caractères.
	 * @param dateDeDebut la date de début du bail, au format "yyyy-dd-MM".
	 * @param dateDeFin   la date de fin du bail, au format "yyyy-dd-MM".
	 * 
	 * @throws IllegalArgumentException si la date de début n'est pas strictement antérieure à la date de fin 
	 *                                  ou si les dates ne respectent pas le format attendu.
	 * 
	 */
	public Bail(String idBail, String dateDeDebut, String dateDeFin) throws IllegalArgumentException {
	    // Définir le format attendu pour les dates
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

	    // Convertir les chaînes en objets LocalDate
	    LocalDate date1 = LocalDate.parse(dateDeDebut, formatter);
	    LocalDate date2 = LocalDate.parse(dateDeFin, formatter);

	    // Vérifier que la date de début est strictement antérieure à la date de fin
	    if (date1.isAfter(date2) || date1.isEqual(date2)) {
	        throw new IllegalArgumentException("La date de début doit être avant celle de la fin");
	    }

	    // Initialiser les attributs de l'objet
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
	 * Définit la date de fin du bail.
	 * 
	 * Cette méthode permet de modifier la date de fin du bail en s'assurant 
	 * que la nouvelle date de fin est strictement postérieure à la date de début déjà définie.
	 *
	 * @param dateDeFin la nouvelle date de fin, au format "yyyy-dd-MM".
	 * 
	 * @throws IllegalArgumentException si la nouvelle date de fin est antérieure 
	 *                                  ou égale à la date de début actuelle, ou si le format est invalide.
	 * 
	 *
	 */
	public void setDateDeFin(String dateDeFin) throws IllegalArgumentException {
	    // Définir le format attendu pour les dates
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

	    // Convertir les dates en objets LocalDate
	    LocalDate date1 = LocalDate.parse(this.dateDeDebut, formatter); // Date de début actuelle
	    LocalDate date2 = LocalDate.parse(dateDeFin, formatter);       // Nouvelle date de fin

	    // Vérifier que la nouvelle date de fin est postérieure à la date de début
	    if (date1.isAfter(date2) || date1.isEqual(date2)) {
	        throw new IllegalArgumentException("La date de début doit être avant celle de la fin");
	    }

	    this.dateDeFin = dateDeFin;
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
