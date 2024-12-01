package modele;

import java.sql.Date;

public class Contracter {

	private String dateSortie;
	private String dateEntree;
	private float partLoyer;
	public Bail bail;
	public Locataire locataire;
	
	public Contracter(Locataire locataire, Bail bail, 
			String dateEntree, float partLoyer) throws IllegalArgumentException {
		
		modifierPartLoyer(partLoyer);
				
		this.bail = bail;
		this.locataire = locataire;
		this.dateEntree = dateEntree;
		
		
	}

	public void modifierPartLoyer(float partLoyer) {
		if (partLoyer > 1.0f ) {
			throw new IllegalArgumentException("La part de loyer ne peut etre supérieur a 100% !");
		}
		this.partLoyer = partLoyer;
	}
	
	public void ajouterDateSortie(String dateSortie) {
		if ( Date.valueOf(dateSortie).before(Date.valueOf(dateEntree))) {
			throw new IllegalArgumentException("La date de fin ne peut etre inferieure a la date de début !");
		}
		
		this.dateSortie = dateSortie;
	}
	
	public void ajouterDateEntree(String dateEntree) {
		if (dateSortie != null && Date.valueOf(dateSortie).before(Date.valueOf(dateEntree))) {
			throw new IllegalArgumentException("La date de fin ne peut etre inferieure a la date de début !");
		}
		
		this.dateEntree = dateEntree;
	}
	

	public String getDateSortie() {
		return dateSortie;
	}

	public String getDateEntree() {
		return dateEntree;
	}


	public float getPartLoyer() {
		return partLoyer;
	}


	public Bail getBail() {
		return bail;
	}

	public Locataire getLocataire() {
		return locataire;
	}
	
}
