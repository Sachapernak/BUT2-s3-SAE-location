package modeleTest;

import java.util.List;

import modele.Locataire;
import modele.dao.DaoLocataire;

public class TestLocataireApp {
	
	// Permet d'afficher les locataires depuis la BD
	// Utilisé a des fin de test
	public static void main(String[] args) {
		List<Locataire> locataires = null;
		try {
			locataires = new DaoLocataire().findAll();
		} catch (Exception e) {
			System.out.println("Erreur du test : " + e.getMessage());;
		}
		
		for (Locataire locataire : locataires) {
		   System.out.println(locataire.toString());
		}
	}
}