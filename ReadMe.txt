Groupe 7 : Pernak Sacha, Draux Erine, Allasia Lévi, Bourdon--Novellas Eliott, Montagard Matéo


JARs requis pour l'application : 
---------------------------------
1. OJDBC (Oracle JDBC Driver)  
   - Version recommandée : ojdbc11.jar  

2. Apache POI (pour manipuler les fichiers Excel et Word)  
   - Version recommandée :  poi-5.2.3.jar  

3. JUnit 4 (pour les tests unitaires)  



Etapes d'installation de l'application : 
-----------------------------------------

1. Récupérer les fichiers contenu dans le répertoire 'SQL' sur Oracle SQL Developer

2. Executer les fichiers dans l'ordre suivant : 
	--> 'Generation tables.sql'
	--> 'JeuTest.sql' 
	--> 'triggers.sql'
Puis executer l'ensemble des fichiers contenus dans le répertoires 'Packages sql'

3. Importer le répertoire 'src' et 'documents' sur l'IDE de votre choix.

4. Installez les jar requis.

5. Executer le fichier 'FenetrePrincipale.java'
   -> Une première erreur "URL Oracle invalide" devrait s'afficher. 
	C'est normal : vous n'avez pas encore entré vos identifiants de connexion.
	Vous pouvez l'ignorer.

6. Saisissez vos identifiants de votre base de données Oracle via l'interface

Vous pouvez désormais utiliser l'application !


