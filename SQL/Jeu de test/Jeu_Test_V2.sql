-- Table SAE_assurance
INSERT INTO SAE_assurance (numero_de_contrat, annee_du_contrat, Type_de_contrat) VALUES
('CONTRAT001', 2023, 'Habitation');
INSERT INTO SAE_assurance (numero_de_contrat, annee_du_contrat, Type_de_contrat) VALUES
('CONTRAT002', 2024, 'Responsabilité Civile');

-- Table SAE_ICC
INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice) VALUES
('2023', '1', 1250);
INSERT INTO SAE_ICC (Annee_ICC, trimestre_ICC, indice) VALUES
('2023', '2', 1260);

-- Table SAE_Adresse
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) VALUES
('MATADR001', '12 Rue des Lilas', 31000, 'Toulouse', NULL);
INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse) VALUES
('MATADR002', '45 Avenue des Pins', 33000, 'Bordeaux', 'Proche du parc');

-- Table SAE_batiment
INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse) VALUES
('MATBAT001', 'MATADR001');
INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse) VALUES
('MATBAT002', 'MATADR002');

-- Table SAE_Bien_locatif
INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment) VALUES
('MATLOG001', 800.00, 'FISC001', NULL, 75, 3, 'Appartement', 'MATBAT001');
INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment) VALUES
('MATLOG002', 1200.00, 'FISC002', '2ème étage', 100, 5, 'Maison', 'MATBAT002');

-- Table SAE_Locataire
INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Id_SAE_Adresse) VALUES
('MATLOC001', 'Dupont', 'Jean', 'jean.dupont@example.com', '0601234567', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Toulouse', 'MATADR001');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Id_SAE_Adresse) VALUES
('MATLOC002', 'Martin', 'Sophie', 'sophie.martin@example.com', '0602345678', TO_DATE('1985-09-20', 'YYYY-MM-DD'), 'Bordeaux', 'MATADR002');

INSERT INTO SAE_Locataire (identifiant_locataire, Nom_locataire, Prenom_locataire, email_locataire, telephone_locataire, date_naissance, Lieu_de_naissance, Id_SAE_Adresse) VALUES
('MATLOC003', 'Durand', 'Paul', 'paul.durand@example.com', '0603456789', TO_DATE('1992-12-10', 'YYYY-MM-DD'), 'Toulouse', 'MATADR001');

-- Table SAE_Cautionnaire
INSERT INTO SAE_Cautionnaire (Id_Caution, Nom_ou_organisme, Prenom, Description_du_cautionnaire, Id_SAE_Adresse) VALUES
(1, 'Banque Nationale', NULL, 'Institution financière garantissant le paiement des loyers', 'MATADR001');
INSERT INTO SAE_Cautionnaire (Id_Caution, Nom_ou_organisme, Prenom, Description_du_cautionnaire, Id_SAE_Adresse) VALUES
(2, 'Dupont', 'Marie', 'Caution personnelle', 'MATADR002');

-- Table SAE_entreprise
INSERT INTO SAE_entreprise (SIRET, secteur_d_activite, nom, Id_SAE_Adresse) VALUES
('ENTR001', 'Construction', 'Entreprise A', 'MATADR001');
INSERT INTO SAE_entreprise (SIRET, secteur_d_activite, nom, Id_SAE_Adresse) VALUES
('ENTR002', 'Plomberie', 'Entreprise B', 'MATADR002');

-- Table SAE_Bail
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement) VALUES
('MATBAI001', TO_DATE('2023-01-01', 'YYYY-MM-DD'), NULL, 'MATLOG001');
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement) VALUES
('MATBAI002', TO_DATE('2023-03-01', 'YYYY-MM-DD'), TO_DATE('2023-12-31', 'YYYY-MM-DD'), 'MATLOG002');
INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement) VALUES
('MATBAI003', TO_DATE('2024-02-01', 'YYYY-MM-DD'), NULL, 'MATLOG002');

-- Table SAE_Provision_charge
INSERT INTO SAE_Provision_charge (Id_bail, date_changement, provision_pour_charge) VALUES
('MATBAI001', TO_DATE('2023-02-01', 'YYYY-MM-DD'), 50.00);
INSERT INTO SAE_Provision_charge (Id_bail, date_changement, provision_pour_charge) VALUES
('MATBAI001', TO_DATE('2023-06-01', 'YYYY-MM-DD'), 60.00);

-- Table SAE_Regularisation
INSERT INTO SAE_Regularisation (Id_bail, Date_regu, montant) VALUES
('MATBAI001', TO_DATE('2023-12-01', 'YYYY-MM-DD'), 110.00);

-- Table SAE_diagnostiques
INSERT INTO SAE_diagnostiques (Date_diagnostique, identifiant, Resultats_des_diagnostiques, fichier_diagnostique, identifiant_batiment, identifiant_logement) VALUES
(TO_DATE('2023-01-10', 'YYYY-MM-DD'), 'DIAG001', 'Conforme', 'diagnostic_batiment1.pdf', 'MATBAT001', NULL);
INSERT INTO SAE_diagnostiques (Date_diagnostique, identifiant, Resultats_des_diagnostiques, fichier_diagnostique, identifiant_batiment, identifiant_logement) VALUES
(TO_DATE('2023-02-15', 'YYYY-MM-DD'), 'DIAG002', 'Non Conforme', 'diagnostic_logement1.pdf', 'MATBAT001', 'MATLOG001');
INSERT INTO SAE_diagnostiques (Date_diagnostique, identifiant, Resultats_des_diagnostiques, fichier_diagnostique, identifiant_batiment, identifiant_logement) VALUES
(TO_DATE('2024-03-05', 'YYYY-MM-DD'), 'DIAG003', 'Conforme', 'diagnostic_logement2.pdf', 'MATBAT002', 'MATLOG002');

-- Table sae_loyer
INSERT INTO sae_loyer (identifiant_logement, date_de_changement, montant_loyer) VALUES
('MATLOG001', TO_DATE('2023-01-01', 'YYYY-MM-DD'), 800.00);
INSERT INTO sae_loyer (identifiant_logement, date_de_changement, montant_loyer) VALUES
('MATLOG002', TO_DATE('2023-03-01', 'YYYY-MM-DD'), 1200.00);

-- Table SAE_document_comptable
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC001', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 'charge', 100.00, 'doc001.pdf', NULL, 1, 'MATLOC001', 'MATBAT001', 'ENTR001');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC002', TO_DATE('2023-06-15', 'YYYY-MM-DD'), 'charge', 120.00, 'doc002.pdf', NULL, 1, 'MATLOC001', 'MATBAT001', 'ENTR002');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC003', TO_DATE('2023-11-20', 'YYYY-MM-DD'), 'charge_non_recu', 80.00, 'doc003.pdf', NULL, 1, 'MATLOC001', 'MATBAT001', 'ENTR002');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC004', TO_DATE('2023-06-15', 'YYYY-MM-DD'), 'charge', 350, 'doc004.pdf', NULL, 1, 'MATLOC001','MATBAT001', 'ENTR002');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC005', TO_DATE('2023-09-01', 'YYYY-MM-DD'), 'charge', 110, 'doc005.pdf', NULL, 1, 'MATLOC001','MATBAT001', 'ENTR002');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC006', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 'charge_Entretiens', 250, 'doc006.pdf', NULL, 1, 'MATLOC001','MATBAT001', 'ENTR002');
INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, SIRET) VALUES
('DOC007', TO_DATE('2023-06-15', 'YYYY-MM-DD'), 'charge', 180, 'doc007.pdf', NULL, 1, 'MATLOC001','MATBAT001', 'ENTR002');

-- Table sae_charge_index
INSERT INTO sae_charge_index (id_charge_index, Date_de_releve, Type, Valeur_compteur, Cout_variable_unitaire, Cout_fixe, numero_document, Date_document) VALUES
('CHIDX001', TO_DATE('2023-02-01', 'YYYY-MM-DD'), 'Eau', 150.000, 1.25000, 20.00, 'DOC001', TO_DATE('2023-01-15', 'YYYY-MM-DD'));
INSERT INTO sae_charge_index (id_charge_index, Date_de_releve, Type, Valeur_compteur, Cout_variable_unitaire, Cout_fixe, numero_document, Date_document) VALUES
('CHIDX002', TO_DATE('2023-06-01', 'YYYY-MM-DD'), 'Eau', 300.500, 1.25000, 15.00, 'DOC002', TO_DATE('2023-06-15', 'YYYY-MM-DD'));
INSERT INTO sae_charge_index (id_charge_index, Date_de_releve, Type, Valeur_compteur, Cout_variable_unitaire, Cout_fixe, numero_document, Date_document) VALUES
('CHIDX003', TO_DATE('2023-07-01', 'YYYY-MM-DD'), 'Electricité', 250.300, 0.12000, 10.00, 'DOC003', TO_DATE('2023-11-20', 'YYYY-MM-DD'));
INSERT INTO sae_charge_index (id_charge_index, Date_de_releve, Type, Valeur_compteur, Cout_variable_unitaire, Cout_fixe, numero_document, Date_document) VALUES
('CHIDX004', TO_DATE('2023-08-01', 'YYYY-MM-DD'), 'Electricité', 275.000, 0.13000, 12.00, 'DOC004', TO_DATE('2023-06-15', 'YYYY-MM-DD'));
INSERT INTO sae_charge_index (id_charge_index, Date_de_releve, Type, Valeur_compteur, Cout_variable_unitaire, Cout_fixe, numero_document, Date_document) VALUES
('CHIDX005', TO_DATE('2023-09-01', 'YYYY-MM-DD'), 'Eau', 310.000, 1.30000, 25.00, 'DOC005', TO_DATE('2023-09-01', 'YYYY-MM-DD'));

-- Table SAE_Charge_cf
INSERT INTO SAE_Charge_cf (id_charge_cf, Date_de_charge, Type, montant, numero_document, Date_document) VALUES
('CHCF001', TO_DATE('2023-01-20', 'YYYY-MM-DD'), 'Entretien', 250.00, 'DOC006', TO_DATE('2023-01-15', 'YYYY-MM-DD'));
INSERT INTO SAE_Charge_cf (id_charge_cf, Date_de_charge, Type, montant, numero_document, Date_document) VALUES
('CHCF002', TO_DATE('2023-06-20', 'YYYY-MM-DD'), 'OrdureMénagère', 180.00, 'DOC007', TO_DATE('2023-06-15', 'YYYY-MM-DD'));

-- Table SAE_Document
INSERT INTO SAE_Document (Id_bail, Date_document, type_de_document, url_document) VALUES
('MATBAI001', TO_DATE('2023-02-15', 'YYYY-MM-DD'), 'Etat des lieux', 'etat_lieux_bail1.pdf');
INSERT INTO SAE_Document (Id_bail, Date_document, type_de_document, url_document) VALUES
('MATBAI002', TO_DATE('2023-03-15', 'YYYY-MM-DD'), 'Contrat de bail', 'contrat_bail2.pdf');

-- Table sae_etre_lie
INSERT INTO sae_etre_lie (identifiant_locataire1, identifiant_locataire2, Lien_familiale, Colocataire) VALUES
('MATLOC001', 'MATLOC003', 'Frère', 1);

-- Table sae_Cautionner
INSERT INTO sae_Cautionner (Id_Caution, Id_bail, Montant, Fichier_caution) VALUES
(1, 'MATBAI001', 500.00, 'caution1.pdf');

-- Table sae_contracter
INSERT INTO sae_contracter (identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer) VALUES
('MATLOC001', 'MATBAI001', NULL, TO_DATE('2023-01-01', 'YYYY-MM-DD'), 1.00);

-- Table sae_facture_du_bien
INSERT INTO sae_facture_du_bien (identifiant_logement, numero_document, Date_document, part_des_charges) VALUES
('MATLOG002', 'DOC002', TO_DATE('2023-06-15', 'YYYY-MM-DD'), 0.50);
INSERT INTO sae_facture_du_bien (identifiant_logement, numero_document, Date_document, part_des_charges) VALUES
('MATLOG002', 'DOC003', TO_DATE('2023-11-20', 'YYYY-MM-DD'), 0.75);
