
package utilitaires;

import java.io.IOException;
import java.util.Scanner;

import modele.FichierConfig;

public class ConfigCLI {

    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {

            // Demander les informations à l'utilisateur
        	System.out.println("Veuillez entrer vos informations pour les mettre à jour");
        	System.out.println("Laissez vide pour eviter la modification");
            System.out.print("Entrez le login de la base de données : ");
            String login = scanner.nextLine();

            System.out.print("Entrez le mot de passe de la base de données : ");
            String mdp = scanner.nextLine();

            System.out.print("Entrez l'URL de la base de données : ");
            String url = scanner.nextLine();

            // Enregistrer les informations dans le fichier de configuration
            FichierConfig fichierConfig = FichierConfig.getInstance();
            if (!"".equals(login)) {
            	fichierConfig.enregistrer("DB_USER", login);
            }
            if (!"".equals(mdp)) {
            	// Mot de passe est chiffré avant d'être enregistré
            	fichierConfig.enregistrerMdp(mdp);
            }
            
            if (!"".equals(url)) {
            	fichierConfig.enregistrer("DB_LINK", url);
            }

            System.out.println("Les informations ont été enregistrées avec succès dans config.properties.");
        
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'enregistrement des informations : " + e.getMessage());
        }
    }
}
