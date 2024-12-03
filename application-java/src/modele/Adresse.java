package modele;

import java.util.Objects;

/**
 * La classe Adresse représente une adresse physique, comprenant des informations
 * telles que l'identifiant, l'adresse proprement dite, le code postal, la ville et un complément d'adresse.
 */
public class Adresse {

    /**
     * Identifiant unique de l'adresse.
     */
    private String idAdresse;

    /**
     * Description textuelle de l'adresse (ex. rue et numéro).
     */
    private String adressePostale;

    /**
     * Code postal de l'adresse.
     */
    private int codePostal;

    /**
     * Ville associée à l'adresse.
     */
    private String ville;

    /**
     * Complément d'adresse, optionnel.
     */
    private String complementAdresse;

    /**
     * Constructeur de la classe Adresse.
     *
     * @param idAdresse   Identifiant unique de l'adresse.
     * @param adresse     Description textuelle de l'adresse (ex. rue et numéro).
     * @param codePostal  Code postal de l'adresse.
     * @param ville       Ville associée à l'adresse.
     */
    public Adresse(String idAdresse, String adresse, int codePostal, String ville) {
        this.idAdresse = idAdresse;
        this.adressePostale = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    /**
     * Constructeur de la classe Adresse avec un complément d'adresse.
     *
     * @param idAdresse   Identifiant unique de l'adresse.
     * @param adresse     Description textuelle de l'adresse (ex. rue et numéro).
     * @param codePostal  Code postal de l'adresse.
     * @param ville       Ville associée à l'adresse.
     * @param complement  Complément d'adresse (facultatif).
     */
    public Adresse(String idAdresse, String adresse, int codePostal, String ville, String complement) {
        this(idAdresse, adresse, codePostal, ville);
        this.complementAdresse = complement;
    }

    /**
     * Retourne une représentation textuelle de l'adresse.
     *
     * @return Une chaîne de caractères décrivant l'adresse.
     */
    @Override
    public String toString() {
        return "(adresse " + idAdresse + ") " + adressePostale + ", " + complementAdresse + ", " + codePostal + " " + ville;
    }

    /**
     * Retourne l'identifiant unique de l'adresse.
     *
     * @return L'identifiant de l'adresse.
     */
    public String getIdAdresse() {
        return idAdresse;
    }

    /**
     * Retourne la description textuelle de l'adresse (ex. rue et numéro).
     *
     * @return La description de l'adresse.
     */
    public String getAdressePostale() {
        return adressePostale;
    }

    /**
     * Retourne le code postal de l'adresse.
     *
     * @return Le code postal de l'adresse.
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * Retourne la ville associée à l'adresse.
     *
     * @return La ville de l'adresse.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Retourne le complément d'adresse (si disponible).
     *
     * @return Le complément d'adresse.
     */
    public String getComplementAdresse() {
        return complementAdresse;
    }

    /**
     * Modifie la description textuelle de l'adresse.
     *
     * @param adresse La nouvelle description de l'adresse.
     */
    public void setAdresse(String adresse) {
        this.adressePostale = adresse;
    }

    /**
     * Modifie le code postal de l'adresse.
     *
     * @param codePostal Le nouveau code postal.
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Modifie la ville associée à l'adresse.
     *
     * @param ville La nouvelle ville.
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Modifie le complément d'adresse.
     *
     * @param complementAdresse Le nouveau complément d'adresse.
     */
    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    /**
     * Calcule et retourne le code de hachage basé sur l'identifiant de l'adresse.
     *
     * @return Le code de hachage de l'adresse.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdresse);
    }

    /**
     * Compare deux objets Adresse pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les adresses ont le même identifiant, false sinon.
     */
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
