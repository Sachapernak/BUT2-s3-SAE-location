package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * La classe Bail représente un contrat de location avec un identifiant unique, une date de début et une date de fin.
 * Elle permet de valider la relation temporelle entre ces dates et de manipuler les informations du bail.
 */
public class Bail {

    /**
     * Identifiant unique du bail.
     */
    private String idBail;

    /**
     * Date de début du bail au format "yyyy-dd-MM".
     */
    private String dateDeDebut;

    /**
     * Date de fin du bail au format "yyyy-dd-MM".
     */
    private String dateDeFin;

    /**
     * Constructeur de la classe Bail.
     *
     * @param idBail      Identifiant unique du bail.
     * @param dateDeDebut Date de début du bail au format "yyyy-dd-MM".
     */
    public Bail(String idBail, String dateDeDebut) {
        this.idBail = idBail;
        this.dateDeDebut = dateDeDebut;
    }

    /**
     * Constructeur de la classe Bail avec validation des dates.
     *
     * Ce constructeur initialise un objet Bail avec un identifiant unique, une date de début et une date de fin.
     * Il valide que la date de début est strictement antérieure à la date de fin, et que les dates respectent le format "yyyy-dd-MM".
     *
     * @param idBail      Identifiant unique du bail.
     * @param dateDeDebut Date de début du bail au format "yyyy-dd-MM".
     * @param dateDeFin   Date de fin du bail au format "yyyy-dd-MM".
     * @throws IllegalArgumentException Si la date de début n'est pas strictement antérieure à la date de fin
     *                                  ou si le format des dates est incorrect.
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

    /**
     * Retourne une représentation textuelle du bail.
     *
     * @return Une chaîne de caractères représentant l'id du bail, sa date de début et de fin.
     */
    @Override
    public String toString() {
        return "Bail " + idBail + ", " + dateDeDebut + ", " + dateDeFin;
    }

    /**
     * Retourne l'identifiant unique du bail.
     *
     * @return L'identifiant du bail.
     */
    public String getIdBail() {
        return idBail;
    }

    /**
     * Retourne la date de début du bail.
     *
     * @return La date de début du bail.
     */
    public String getDateDeDebut() {
        return dateDeDebut;
    }

    /**
     * Modifie la date de début du bail.
     *
     * @param date_de_debut La nouvelle date de début du bail.
     */
    public void setDateDeDebut(String date_de_debut) {
        this.dateDeDebut = date_de_debut;
    }

    /**
     * Retourne la date de fin du bail.
     *
     * @return La date de fin du bail.
     */
    public String getDateDeFin() {
        return dateDeFin;
    }

    /**
     * Modifie la date de fin du bail.
     * Cette méthode assure que la nouvelle date de fin est postérieure à la date de début.
     *
     * @param dateDeFin La nouvelle date de fin, au format "yyyy-dd-MM".
     * @throws IllegalArgumentException Si la date de fin est antérieure ou égale à la date de début, ou si le format est invalide.
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

    /**
     * Calcule et retourne le code de hachage basé sur l'identifiant du bail.
     *
     * @return Le code de hachage du bail.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idBail);
    }

    /**
     * Compare deux objets Bail pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les baux ont le même identifiant, false sinon.
     */
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
