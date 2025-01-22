# BUT2-s3-SAE-Location

Projet universitaire de gestion de locations

Groupe 7
---
- Pernak Sacha
- Draux Erine
- Allasia Lévi
- Bourdon-Novellas Eliott
- Montagard Matéo



Objectifs du Projet
---
- **Développement d'une application Java** pour gérer la location de divers biens locatifs.
- **Intégration d'une base de données Oracle** avec l'application Java.
- **Création de triggers, fonctions PL/SQL** et manipulation des données dans la base de données.



Prérequis
---

### **Logiciels**
- **Java Development Kit (JDK)** - Version 17 ou supérieure recommandée
- **Oracle SQL Developer**
- **IDE Java** de votre choix (Nous avons utilisé Eclipse)

### **JARs Requis**
1. **Oracle JDBC Driver (OJDBC)**
   - Version recommandée : `ojdbc11.jar`
2. **Apache POI** (pour manipuler les fichiers Excel et Word)
   - Version recommandée : `poi-5.2.3.jar`
3. **JUnit 4** (pour les tests unitaires)



Étapes d'Installation de l'Application
---

### **1. Configuration de la Base de Données Oracle**
1. **Télécharger Oracle SQL Developer** si ce n'est pas déjà fait.
2. **Récupérer les fichiers SQL** situés dans le répertoire `SQL` du projet.
3. **Exécuter les scripts SQL** dans l'ordre suivant :
   - `Generation_tables.sql`
   - `JeuTest.sql`
   - `triggers.sql`
4. **Exécuter tous les fichiers** contenus dans le répertoire `Packages_sql`.

### **2. Configuration de l'Environnement de Développement**
1. **Importer les répertoires `src` et `documents`** dans votre IDE Java préféré.
2. **Ajouter les JARs requis** au classpath de votre projet :
   - `ojdbc11.jar`
   - `poi-5.2.3.jar`
   - `junit-4.x.jar` (assurez-vous d'avoir la dernière version de JUnit 4)
   
   *Instructions pour ajouter des JARs dans votre IDE :*
   - **IntelliJ IDEA** : `File > Project Structure > Libraries > + > Java` et sélectionnez les JARs.
   - **Eclipse** : Clic droit sur le projet > `Build Path > Add External Archives` et sélectionnez les JARs.
   - **NetBeans** : Clic droit sur le projet > `Properties > Libraries > Add JAR/Folder` et sélectionnez les JARs.

### **3. Exécution de l'Application**
1. **Lancer le fichier `FenetrePrincipale.java`** depuis votre IDE.
   - **Note :** Une erreur "URL Oracle invalide" peut s'afficher initialement. C'est normal car les identifiants de connexion n'ont pas encore été saisis.
2. **Saisir vos identifiants Oracle** via l'interface utilisateur lorsqu'invité.
3. **L'application est prête à être utilisée !**



Améliorations Possibles
--
Par manque de temps, nous n'avons pas pu parfaire notre projet. Ce que nous auriont voulu faire :

- **Refactorisation complète du code** pour améliorer la lisibilité et la maintenabilité.
- **Implémentation de nouvelles fenêtres** (par exemple, diagnostics, documents des locataires, etc.).
- **Optimisation des requêtes SQL** pour de meilleures performances.
- **Application de design patterns** tels que le Template Method pour structurer le code de manière plus efficace.
- **Utilisation d'interfaces et de méthodes abstraites** pour une architecture plus modulaire et extensible.


Remarques
---
- Assurez-vous que votre environnement Java est correctement configuré avant de commencer.
- Vérifiez les versions des JARs pour assurer la compatibilité avec votre JDK et IDE.
- Documentez toute modification ou amélioration apportée au projet pour faciliter la collaboration future.
