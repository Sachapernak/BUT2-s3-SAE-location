package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe Locataire représente un locataire avec ses informations personnelles,
 * son adresse de contact et la liste des contrats (baux) associés.
 */
public class Locataire {
    
    /**
     * Identifiant unique du locataire.
     */
    private String Idlocataire;
    
    /**
     * Nom du locataire.
     */
    private String nom;
    
    /**
     * Prénom du locataire.
     */
    private String prenom;
    
    /**
     * Adresse email du locataire.
     */
    private String email;
    
    /**
     * Numéro de téléphone du locataire.
     */
    private String telephone;
    
    /**
     * Date de naissance du locataire.
     */
    private String dateNaissance;
    
    /**
     * Lieu de naissance du locataire.
     */
    private String lieuDeNaissance;
    
    /**
     * Adresse de contact du locataire.
     */
    private Adresse adresse;
    
    /**
     * Liste des contrats associés au locataire.
     */
    private List<Contracter> contrats;

    /**
     * Constructeur de la classe Locataire.
     *
     * @param idLocataire Identifiant unique du locataire.
     * @param nom Le nom du locataire.
     * @param prenom Le prénom du locataire.
     * @param dateNaissance La date de naissance du locataire.
     */
    public Locataire(String idLocataire, String nom, String prenom, String dateNaissance) {
        this.Idlocataire = idLocataire;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.contrats = new ArrayList<>();        
    }

    /**
     * Retourne l'identifiant unique du locataire.
     *
     * @return L'identifiant du locataire.
     */
    public String getIdLocataire() {
        return Idlocataire;
    }

    /**
     * Retourne le nom du locataire.
     *
     * @return Le nom du locataire.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le prénom du locataire.
     *
     * @return Le prénom du locataire.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Retourne l'email du locataire.
     *
     * @return L'email du locataire.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le numéro de téléphone du locataire.
     *
     * @return Le numéro de téléphone du locataire.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Retourne la date de naissance du locataire.
     *
     * @return La date de naissance du locataire.
     */
    public String getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Retourne le lieu de naissance du locataire.
     *
     * @return Le lieu de naissance du locataire.
     */
    public String getLieuDeNaissance() {
        return lieuDeNaissance;
    }


    /**
     * Retourne l'adresse de contact du locataire.
     *
     * @return L'adresse du locataire.
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * Retourne la liste des contrats associés au locataire.
     *
     * @return La liste des contrats du locataire.
     */
    public List<Contracter> getContrats() {
        return contrats;
    }

    /**
     * Modifie le nom du locataire.
     *
     * @param nom Le nouveau nom du locataire.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie le prénom du locataire.
     *
     * @param prenom Le nouveau prénom du locataire.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Modifie l'email du locataire.
     *
     * @param email Le nouvel email du locataire.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Modifie le numéro de téléphone du locataire.
     *
     * @param telephone Le nouveau numéro de téléphone du locataire.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Modifie la date de naissance du locataire.
     *
     * @param dateNaissance La nouvelle date de naissance du locataire.
     */
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Modifie le lieu de naissance du locataire.
     *
     * @param lieuDeNaissance Le nouveau lieu de naissance du locataire.
     */
    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }

    /**
     * Modifie l'adresse de contact du locataire.
     *
     * @param adresse La nouvelle adresse de contact du locataire.
     */
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }


    /**
     * Retourne une représentation textuelle du locataire.
     *
     * @return Une chaîne de caractères décrivant le locataire.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        
        String absent = "non renseigné";
        res.append("Locataire ").append(Idlocataire).append(": ")
           .append(nom).append(" ").append(prenom)
           .append("\n    Email, Telephone : ").append(email != null ? email : absent)
           .append(", ").append(telephone != null ? telephone : absent)
           .append("\n    Naissance : ").append(dateNaissance)
           .append(" à ").append(lieuDeNaissance != null ? lieuDeNaissance : absent)
           .append("\n    Adresse : ").append(adresse != null ? adresse.toString() : absent)
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

    /**
     * Calcule et retourne le code de hachage basé sur l'identifiant du locataire.
     *
     * @return Le code de hachage du locataire.
     */
    @Override
    public int hashCode() {
        return Objects.hash(Idlocataire);
    }

    /**
     * Compare deux objets Locataire pour vérifier s'ils sont égaux.
     *
     * @param obj L'objet à comparer.
     * @return true si les locataires ont le même identifiant, false sinon.
     */
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
