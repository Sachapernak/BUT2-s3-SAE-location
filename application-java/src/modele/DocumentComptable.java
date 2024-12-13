package modele;

import java.math.BigDecimal;

public class DocumentComptable {
	
	private String numeroDoc;
	private String dateDoc;
	
	private TypeDoc typeDoc;
	
	BigDecimal montant;
	
	String fichierDoc;
	
	BigDecimal montantDevis;
	
	boolean recuperableLoc = false;
	
	ChargeIndex chargeIndex;
	ChargeFixe chargeFixe;
	Locataire locataire;
	Entreprise entreprise;
	Assurance assurance;
	
	
	

	public DocumentComptable(String numDoc, String dateDoc, TypeDoc type,
			BigDecimal montant, String fichierDoc) {
		
		this.numeroDoc = numDoc;
		this.dateDoc = dateDoc;
		this.typeDoc = type;
		this.montant = montant;
		this.fichierDoc = fichierDoc;
		
	}


	public BigDecimal getMontant() {
		return montant;
	}


	public void setMontant(BigDecimal montant) {
		if (this.montant.compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("Le montant ne peut etre negatif !");
		}
		this.montant = montant;
	}


	public BigDecimal getMontantDevis() {

		return montantDevis;
	}


	public void setMontantDevis(BigDecimal montantDevis) {
		if (this.montant.compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("Le montant ne peut etre negatif !");
		}
		this.montantDevis = montantDevis;
	}


	public boolean isRecuperableLoc() {
		return recuperableLoc;
	}


	public void setRecuperableLoc(boolean recuperableLoc) {
		this.recuperableLoc = recuperableLoc;
	}


	public String getNumeroDoc() {
		return numeroDoc;
	}


	public String getDateDoc() {
		return dateDoc;
	}


	public TypeDoc getTypeDoc() {
		return typeDoc;
	}


	public String getFichierDoc() {
		return fichierDoc;
	}

	
	
	
	// Les types

	public ChargeIndex getChargeIndex() {
		return chargeIndex;
	}


	public void setChargeIndex(ChargeIndex chargeIndex) {
		this.chargeIndex = chargeIndex;
	}

	

	public ChargeFixe getChargeFixe() {
		return chargeFixe;
	}


	public void setChargeFixe(ChargeFixe chargeFixe) {
		this.chargeFixe = chargeFixe;
	}

	

	public Locataire getLocataire() {
		return locataire;
	}


	public void setLocataire(Locataire locataire) {
		this.locataire = locataire;
	}

	

	public Entreprise getEntreprise() {
		return entreprise;
	}


	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}


	
	public Assurance getAssurance() {
		return assurance;
	}


	public void setAssurance(Assurance assurance) {
		this.assurance = assurance;
	}
	
	
	


}
