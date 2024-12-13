-- Adresses utilisées par les locataires
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR001', '10 rue des Lilas', 31000, 'Toulouse', NULL);

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR002', '20 avenue de Paris', 75000, 'Paris', 'Appartement 3B');

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR003', '5 impasse des Érables', 69000, 'Lyon', NULL);

-- Adresse non utilisée (pour test d'intégrité)
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR004', '7 chemin des Pins', 34000, 'Montpellier', NULL);

select * from SAE_Adresse;

INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT001', 'ADDR001');

INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT002', 'ADDR002');

INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT003', 'ADDR003');

select * from SAE_batiment;

INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG001', 650.00, 'FISCAL001', 'garage cote rue', 45, 2, 'garage', 'BAT001');

INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG002', 1200.00, 'FISCAL002', 'apt 112', 30, 1, 'logement', 'BAT002');

INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG003', 950.00, 'FISCAL003', 'logement 2eme etage', 70, 3, 'logement', 'BAT003');

select * from SAE_Bien_locatif;

-- Insertion de baux valides, avec identifiant_logement existant
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI01', TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI02', TO_DATE('2023-02-01', 'YYYY-MM-DD'), NULL, 'LOG002');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI03', TO_DATE('2023-03-01', 'YYYY-MM-DD'), NULL, 'LOG003');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI04', TO_DATE('2023-04-01', 'YYYY-MM-DD'), TO_DATE('2024-04-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI05', TO_DATE('2023-05-01', 'YYYY-MM-DD'), NULL, 'LOG002');

select * from SAE_Bail;




-- -----------------

-- 1. Insertion dans SAE_Adresse
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR001', '10 rue des Lilas', 31000, 'Toulouse', NULL);

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR002', '20 avenue de Paris', 75000, 'Paris', 'Appartement 3B');

INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR003', '5 impasse des Érables', 69000, 'Lyon', NULL);

-- Adresse non utilisée
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) 
VALUES ('ADDR004', '7 chemin des Pins', 34000, 'Montpellier', NULL);


-- 2. Insertion dans SAE_batiment
INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT001', 'ADDR001');

INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT002', 'ADDR002');

INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
VALUES ('BAT003', 'ADDR003');


-- 3. Insertion dans SAE_Bien_locatif
INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG001', 650.00, 'FISCAL001', 'garage cote rue', 45, 2, 'garage', 'BAT001');

INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG002', 1200.00, 'FISCAL002', 'apt 112', 30, 1, 'logement', 'BAT002');

INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
VALUES ('LOG003', 950.00, 'FISCAL003', 'logement 2eme etage', 70, 3, 'logement', 'BAT003');


-- 4. Insertion dans SAE_Bail
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI01', TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI02', TO_DATE('2023-02-01', 'YYYY-MM-DD'), NULL, 'LOG002');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI03', TO_DATE('2023-03-01', 'YYYY-MM-DD'), NULL, 'LOG003');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI04', TO_DATE('2023-04-01', 'YYYY-MM-DD'), TO_DATE('2024-04-01', 'YYYY-MM-DD'), 'LOG001');

INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
VALUES ('BAI05', TO_DATE('2023-05-01', 'YYYY-MM-DD'), NULL, 'LOG002');


-- 5. Insertion dans SAE_Locataire
INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC001', 'Durand', 'Alice', 'alice.durand@example.com', '0612345678', TO_DATE('1990-04-15', 'YYYY-MM-DD'), 'Toulouse', 'Acte_123.pdf', 'ADDR001');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC002', 'Martin', 'Bob', NULL, '0712345678', TO_DATE('1985-07-20', 'YYYY-MM-DD'), 'Paris', 'Acte_456.pdf', 'ADDR002');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC003', 'Lemoine', 'Claire', 'claire.lemoine@example.com', NULL, TO_DATE('1995-12-01', 'YYYY-MM-DD'), 'Lyon', NULL, 'ADDR003');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC004', 'Petit', 'Denis', 'denis.petit@example.com', '0812345678', TO_DATE('1980-02-28', 'YYYY-MM-DD'), 'Marseille', NULL, NULL);

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Acte_de_caution, Id_SAE_Adresse)
VALUES ('LOC005', 'Roche', 'Eve', NULL, NULL, TO_DATE('2000-06-15', 'YYYY-MM-DD'), 'Bordeaux', NULL, 'ADDR001');


-- 6. Insertion dans SAE_assurance
INSERT INTO SAE_assurance (
  numero_de_contrat, annee_du_contrat, Type_de_contrat, prime,
  quotite_jursiprudence, montant_quoitite
) VALUES ('ASSUR001', 2023, 'Habitation', 1200.00, 1.00, 1200.00);

INSERT INTO SAE_assurance (
  numero_de_contrat, annee_du_contrat, Type_de_contrat, prime,
  quotite_jursiprudence, montant_quoitite
) VALUES ('ASSUR001', 2024, 'Habitation', 1250.00, 1.05, 1312.50);


-- 7. Insertion dans SAE_ICC
INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice)
VALUES ('2023', '1', 1785);

INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice)
VALUES ('2023', '2', 1790);

INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice)
VALUES ('2023', '3', 1795);

INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice)
VALUES ('2023', '4', 1800);


-- 8. Insertion dans SAE_Cautionnaire
INSERT INTO SAE_Cautionnaire (Id_Caution, Nom_ou_organisme, Prenom, Description_du_cautionnaire, Id_SAE_Adresse)
VALUES (1, 'GarantiePlus', NULL, 'Organisme cautionnaire', 'ADDR003');

INSERT INTO SAE_Cautionnaire (Id_Caution, Nom_ou_organisme, Prenom, Description_du_cautionnaire, Id_SAE_Adresse)
VALUES (2, 'Dupont', 'Francis', 'Caution personnelle', 'ADDR001');


-- 9. Insertion dans SAE_entreprise
INSERT INTO SAE_entreprise (SIRET, secteur_d_activite, nom, Id_SAE_Adresse)
VALUES ('ENTR001', 'Plomberie', 'PlombierDuCoin', 'ADDR002');

INSERT INTO SAE_entreprise (SIRET, secteur_d_activite, nom, Id_SAE_Adresse)
VALUES ('ENTR002', 'Électricité', 'ElecService', 'ADDR003');


-- 10. Insertion dans SAE_document_comptable
INSERT INTO SAE_document_comptable (
    numero_document, Date_document, Type_de_document, montant, fichier_document,
    montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment,
    SIRET, numero_de_contrat, annee_du_contrat
) VALUES (
    'DOC001', TO_DATE('2023-07-05', 'YYYY-MM-DD'), 'quittance', '650.00', 'quittance_juil_2023.pdf',
    NULL, 1, 'LOC001', 'BAT001', NULL, 'ASSUR001', 2023
);

INSERT INTO SAE_document_comptable (
    numero_document, Date_document, Type_de_document, montant, fichier_document,
    montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment,
    SIRET, numero_de_contrat, annee_du_contrat
) VALUES (
    'DOC002', TO_DATE('2023-08-10', 'YYYY-MM-DD'), 'facture', '300.00', 'facture_reparation_082023.pdf',
    '350.00', 0, 'LOC002', 'BAT002', 'ENTR001', 'ASSUR001', 2023
);

INSERT INTO SAE_document_comptable (
    numero_document, Date_document, Type_de_document, montant, fichier_document,
    montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment,
    SIRET, numero_de_contrat, annee_du_contrat
) VALUES (
    'DOC003', TO_DATE('2023-09-05', 'YYYY-MM-DD'), 'quittance', '1200.00', 'quittance_sep_2023.pdf',
    NULL, 1, 'LOC002', 'BAT002', NULL, 'ASSUR001', 2023
);

INSERT INTO SAE_document_comptable (
    numero_document, Date_document, Type_de_document, montant, fichier_document,
    montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment,
    SIRET, numero_de_contrat, annee_du_contrat
) VALUES (
    'DOC004', TO_DATE('2023-10-01', 'YYYY-MM-DD'), 'devis', '0.00', 'devis_electricite_102023.pdf',
    '500.00', 0, 'LOC003', 'BAT003', 'ENTR002', 'ASSUR001', 2024
);


-- 11. Insertion dans sae_charge_index
INSERT INTO sae_charge_index (
    Date_de_releve, Type, valeur, Date_releve_precedent, Cout_variable, Cout_fixe,
    numero_document, Date_document
) VALUES (
    TO_DATE('2023-08-15','YYYY-MM-DD'), 'Eau', 120.50, TO_DATE('2023-07-15','YYYY-MM-DD'), 100.00, 'Forfait mensuel',
    'DOC002', TO_DATE('2023-08-10','YYYY-MM-DD')
);


-- 12. Insertion dans SAE_Charge_cf
INSERT INTO SAE_Charge_cf (
    Date_de_charge, Type, montant, numero_document, Date_document
) VALUES (
    TO_DATE('2023-09-10','YYYY-MM-DD'), 'Forfait internet', '50.00',
    'DOC003', TO_DATE('2023-09-05','YYYY-MM-DD')
);


-- 13. Insertion dans SAE_Document (liés aux baux)
INSERT INTO SAE_Document (
    Id_bail, Date_document, type_de_document, url_document
) VALUES (
    'BAI01', TO_DATE('2023-01-01','YYYY-MM-DD'), 'Etat_des_lieux_entree', 'http://example.com/etat_lieux_bai01.pdf'
);

INSERT INTO SAE_Document (
    Id_bail, Date_document, type_de_document, url_document
) VALUES (
    'BAI02', TO_DATE('2023-02-01','YYYY-MM-DD'), 'Contrat_location', 'http://example.com/contrat_bai02.pdf'
);


-- 14. Insertion dans SAE_Regularisation
INSERT INTO SAE_Regularisation (
    Id_bail, Date_regu, montant
) VALUES (
    'BAI01', TO_DATE('2024-01-15','YYYY-MM-DD'), '100.00'
);


-- 15. Insertion dans SAE_diagnostiques
INSERT INTO SAE_diagnostiques (
    Date_diagnostique, identifiant, Resultats_des_diagnostiques, fichier_diagnostique,
    identifiant_batiment, identifiant_logement
) VALUES (
    TO_DATE('2023-06-01','YYYY-MM-DD'), 'DIAG001', 'DPE: C', 'dpe_log001_2023.pdf', 'BAT001', 'LOG001'
);

INSERT INTO SAE_diagnostiques (
    Date_diagnostique, identifiant, Resultats_des_diagnostiques, fichier_diagnostique,
    identifiant_batiment, identifiant_logement
) VALUES (
    TO_DATE('2023-07-15','YYYY-MM-DD'), 'DIAG002', 'Amiante: OK', 'amiante_log002_2023.pdf', 'BAT002', 'LOG002'
);


-- 16. Insertion dans sae_loyer
INSERT INTO sae_loyer (identifiant_logement, date_de_changement, montant_loyer)
VALUES ('LOG001', TO_DATE('2023-02-01','YYYY-MM-DD'), 660.00);

INSERT INTO sae_loyer (identifiant_logement, date_de_changement, montant_loyer)
VALUES ('LOG002', TO_DATE('2023-05-01','YYYY-MM-DD'), 1200.00);

INSERT INTO sae_loyer (identifiant_logement, date_de_changement, montant_loyer)
VALUES ('LOG003', TO_DATE('2023-03-01','YYYY-MM-DD'), 950.00);


-- 17. Insertion dans SAE_Provision_charge
INSERT INTO SAE_Provision_charge (
    Id_bail, date_changement, provision_pour_charge
) VALUES (
    'BAI01', TO_DATE('2023-01-01','YYYY-MM-DD'), 100.00
);

INSERT INTO SAE_Provision_charge (
    Id_bail, date_changement, provision_pour_charge
) VALUES (
    'BAI02', TO_DATE('2023-02-01','YYYY-MM-DD'), 120.00
);


-- 18. Insertion dans sae_etre_lie
INSERT INTO sae_etre_lie (
    identifiant_locataire1, identifiant_locataire2, Lien_familiale, Colocataire
) VALUES (
    'LOC001', 'LOC005', 'Aucune', 1
);


-- 19. Insertion dans sae_Cautionner
INSERT INTO sae_Cautionner (
    Id_Caution, Id_bail, Montant, Fichier_caution
) VALUES (
    1, 'BAI02', 1200.00, 'caution_bai02.pdf'
);

INSERT INTO sae_Cautionner (
    Id_Caution, Id_bail, Montant, Fichier_caution
) VALUES (
    2, 'BAI03', 950.00, 'caution_bai03.pdf'
);


-- 20. Insertion dans sae_contracter
INSERT INTO sae_contracter (
    identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer
) VALUES (
    'LOC001', 'BAI01', TO_DATE('2024-01-01','YYYY-MM-DD'), TO_DATE('2023-01-01','YYYY-MM-DD'), 1.00
);

INSERT INTO sae_contracter (
    identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer
) VALUES (
    'LOC002', 'BAI02', NULL, TO_DATE('2023-02-01','YYYY-MM-DD'), 1.00
);

INSERT INTO sae_contracter (
    identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer
) VALUES (
    'LOC001', 'BAI04', NULL, TO_DATE('2023-04-01','YYYY-MM-DD'), 0.50
);

INSERT INTO sae_contracter (
    identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer
) VALUES (
    'LOC005', 'BAI04', NULL, TO_DATE('2023-04-01','YYYY-MM-DD'), 0.50
);


-- 21. Insertion dans sae_facture_du_bien
INSERT INTO sae_facture_du_bien (
    identifiant_logement, numero_document, Date_document, part_des_charges
) VALUES (
    'LOG002', 'DOC002', TO_DATE('2023-08-10','YYYY-MM-DD'), 1.00
);

INSERT INTO sae_facture_du_bien (
    identifiant_logement, numero_document, Date_document, part_des_charges
) VALUES (
    'LOG001', 'DOC001', TO_DATE('2023-07-05','YYYY-MM-DD'), 1.00
);