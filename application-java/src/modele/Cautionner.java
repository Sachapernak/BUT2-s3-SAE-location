package modele;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * La classe Cautionner représente la relation entre un cautionnaire et un bail,
 * avec des informations sur le montant et le lien vers le document,
 */
public class Cautionner {

    /**
     * Montant de la caution.
     */
	private BigDecimal montant; 
    /**
     * Lien vers le fichier de caution
     */
	private String fichierCaution;
    /**
     * Instance de bail représentant le bail associé.
     */
	private Bail bail;
    /**
     * Instance de la classe cautionnaire représentant le ou les cautionnaires associés
     */
	private List<Cautionnaire> cautionnaires;
	
	 /**
     * Constructeur de la classe Cautionner.
     *
     * @param montant Le montant de la caution.
     * @param fichierCaution Le lien vers le fichier de caution.
     * @param bail   Le bail associé au contrat.
     * @param cautionnaires  La liste des cautionnaires dans le cas où il y en a plusieurs.
     */
	public Cautionner(BigDecimal montant, String fichierCaution, Bail bail, Cautionnaire... cautionnaires) {
		this.montant = montant;
		this.fichierCaution = fichierCaution;
		this.bail = bail;
		this.cautionnaires = Arrays.asList(cautionnaires);
	}

	/**
     * Retourne le montant de la caution.
     *
     * @return Le montant.
     */
	public BigDecimal getMontant() {
		return montant;
	}
	
    /**
     * Retourne le bail associé au contrat.
     *
     * @return L'instance de la classe Bail.
     */
	public String getFichierCaution() {
		return fichierCaution;
	}
	
    /**
     * Retourne le bail associé au cautionnaire.
     *
     * @return L'instance de la classe Bail.
     */
	public Bail getBail() {
		return bail;
	}

    /**
     * Retourne lla liste du ou des cautionnaires associé à la caution.
     *
     * @return La liste contenant les instances de la classe Cautionnaire.
     */
	public List<Cautionnaire> getCautionnaire() {
		return cautionnaires;
	}
	
    /**
     * Retourne lla liste du ou des cautionnaires associé à la caution.
     *
     * @return La liste contenant les instances de la classe Cautionnaire.
     */
	public Cautionnaire getLastCautionnaire() {
		return cautionnaires.get(cautionnaires.size()-1);
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
		
	}
	
	public void setFichierCaution(String fichier) {
		this.fichierCaution = fichier;
	}
	
	
	
}
