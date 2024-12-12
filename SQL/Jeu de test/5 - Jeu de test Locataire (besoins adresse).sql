-- Locataires avec des adresses valides
INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC001', 'Durand', 'Alice', 'alice.durand@example.com', '0612345678', TO_DATE('1990-04-15', 'YYYY-MM-DD'), 'Toulouse', 'Acte_123.pdf', 'ADDR001');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC002', 'Martin', 'Bob', NULL, '0712345678', TO_DATE('1985-07-20', 'YYYY-MM-DD'), 'Paris', 'Acte_456.pdf', 'ADDR002');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC003', 'Lemoine', 'Claire', 'claire.lemoine@example.com', NULL, TO_DATE('1995-12-01', 'YYYY-MM-DD'), 'Lyon', NULL, 'ADDR003');

-- Locataire sans adresse (pour tester les valeurs NULL dans la clé étrangère)
INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC004', 'Petit', 'Denis', 'denis.petit@example.com', '0812345678', TO_DATE('1980-02-28', 'YYYY-MM-DD'), 'Marseille', NULL, NULL);

-- Locataire avec des informations minimales
INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC005', 'Roche', 'Eve', NULL, NULL, TO_DATE('2000-06-15', 'YYYY-MM-DD'), 'Bordeaux', NULL, 'ADDR001');

select * from sae_locataire;