package modele;

import java.sql.Date;

/**
 * La classe Contracter représente la relation entre un locataire et un bail,
 * avec des informations sur les dates d'entrée et de sortie, ainsi que la part
 * de loyer prise en charge.
 */
public class Contracter {

    /**
     * Date de sortie du contrat (format "YYYY-MM-DD").
     */
    private String dateSortie;

    /**
     * Date d'entrée dans le contrat (format "YYYY-MM-DD").
     */
    private String dateEntree;

    /**
     * Part du loyer attribuée au locataire (entre 0.0 et 1.0).
     */
    private float partLoyer;

    /**
     * Instance de la classe Bail représentant le bail associé.
     */
    public Bail bail;

    /**
     * Instance de la classe Locataire représentant le locataire associé.
     */
    public Locataire locataire;

    /**
     * Constructeur de la classe Contracter.
     *
     * @param locataire Le locataire associé au contrat.
     * @param bail      Le bail associé au contrat.
     * @param dateEntree La date d'entrée dans le contrat (format "YYYY-MM-DD").
     * @param partLoyer  La part du loyer attribuée au locataire (entre 0.0 et 1.0).
     * @throws IllegalArgumentException Si la part de loyer est supérieure à 1.0.
     */
    public Contracter(Locataire locataire, Bail bail, String dateEntree, float partLoyer) throws IllegalArgumentException {
        modifierPartLoyer(partLoyer);
        this.bail = bail;
        this.locataire = locataire;
        this.dateEntree = dateEntree;
    }

    /**
     * Modifie la part de loyer attribuée au locataire.
     *
     * @param partLoyer La nouvelle part de loyer (entre 0.0 et 1.0).
     * @throws IllegalArgumentException Si la part de loyer est supérieure à 1.0.
     */
    public void modifierPartLoyer(float partLoyer) {
        if (partLoyer > 1.0f) {
            throw new IllegalArgumentException("La part de loyer ne peut etre supérieur a 100% !");
        }
        this.partLoyer = partLoyer;
    }

    /**
     * Ajoute une date de sortie au contrat.
     *
     * @param dateSortie La date de sortie (format "YYYY-MM-DD").
     * @throws IllegalArgumentException Si la date de sortie est antérieure à la date d'entrée.
     */
    public void ajouterDateSortie(String dateSortie) {
        if (Date.valueOf(dateSortie).before(Date.valueOf(dateEntree))) {
            throw new IllegalArgumentException("La date de fin ne peut etre inferieure a la date de début !");
        }
        this.dateSortie = dateSortie;
    }

    /**
     * Retourne la date de sortie du contrat.
     *
     * @return La date de sortie (format "YYYY-MM-DD").
     */
    public String getDateSortie() {
        return dateSortie;
    }

    /**
     * Retourne la date d'entrée dans le contrat.
     *
     * @return La date d'entrée (format "YYYY-MM-DD").
     */
    public String getDateEntree() {
        return dateEntree;
    }

    /**
     * Retourne la part du loyer attribuée au locataire.
     *
     * @return La part du loyer (entre 0.0 et 1.0).
     */
    public float getPartLoyer() {
        return partLoyer;
    }

    /**
     * Retourne le bail associé au contrat.
     *
     * @return L'instance de la classe Bail.
     */
    public Bail getBail() {
        return bail;
    }

    /**
     * Retourne le locataire associé au contrat.
     *
     * @return L'instance de la classe Locataire.
     */
    public Locataire getLocataire() {
        return locataire;
    }

	public void setDateSortie(String dateSortie) {
		this.dateSortie = dateSortie;
	}

	public void setPartLoyer(float partLoyer) {
		this.partLoyer = partLoyer;
	}
    
    
}
