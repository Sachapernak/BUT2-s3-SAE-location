package modele;

import java.util.Objects;

public class Assurance {
	private String numeroContrat;
	private int anneeContrat;
	private String typeContrat;
	
	
	public Assurance(String numeroContrat, int anneeContrat, String typeContrat) {
		super();
		this.numeroContrat = numeroContrat;
		this.anneeContrat = anneeContrat;
		this.typeContrat = typeContrat;
	}


	@Override
	public int hashCode() {
		return Objects.hash(anneeContrat, numeroContrat, typeContrat);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assurance other = (Assurance) obj;
		return anneeContrat == other.anneeContrat && Objects.equals(numeroContrat, other.numeroContrat)
				&& Objects.equals(typeContrat, other.typeContrat);
	}


	@Override
	public String toString() {
		return "Assurance [numContrat=" + numeroContrat + ", annéContrat=" + anneeContrat + ", typeContrat=" + typeContrat
				+ "]";
	}


	public String getNumeroContrat() {
		return numeroContrat;
	}


	public int getAnneeContrat() {
		return anneeContrat;
	}


	public String getTypeContrat() {
		return typeContrat;
	}


	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}
	
	
	
}

