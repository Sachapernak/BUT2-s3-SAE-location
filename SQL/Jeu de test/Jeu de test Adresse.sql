-- Adresses utilis�es par les locataires
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR001', '10 rue des Lilas', 31000, 'Toulouse', NULL);

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR002', '20 avenue de Paris', 75000, 'Paris', 'Appartement 3B');

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR003', '5 impasse des �rables', 69000, 'Lyon', NULL);

-- Adresse non utilis�e (pour test d'int�grit�)
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR004', '7 chemin des Pins', 34000, 'Montpellier', NULL);
