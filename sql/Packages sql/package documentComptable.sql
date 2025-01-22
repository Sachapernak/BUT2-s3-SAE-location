CREATE OR REPLACE PACKAGE pkg_document_comptable AS
  PROCEDURE supprimer_document_comptable(p_numero_document IN VARCHAR2, p_date_document IN DATE);
END pkg_document_comptable;
/

CREATE OR REPLACE PACKAGE BODY pkg_document_comptable AS
  PROCEDURE supprimer_document_comptable(p_numero_document IN VARCHAR2, p_date_document IN DATE) IS
  BEGIN
    -- Suppression des charges fixes associées
    DELETE FROM SAE_Charge_cf
    WHERE numero_document = p_numero_document
      AND Date_document = p_date_document;

    -- Suppression des charges variables associées (si elles existent)
    DELETE FROM sae_charge_index
    WHERE numero_document = p_numero_document
      AND Date_document = p_date_document;

    -- Suppression des factures associées au bien
    DELETE FROM sae_facture_du_bien
    WHERE numero_document = p_numero_document
      AND Date_document = p_date_document;

    -- Suppression du document comptable lui-même
    DELETE FROM SAE_document_comptable
    WHERE numero_document = p_numero_document
      AND Date_document = p_date_document;

    -- Validation des changements
    COMMIT;
  END supprimer_document_comptable;
END pkg_document_comptable;
/

select * from sae_entreprise;
select *  from sae_locataire;


-- Ajout d'un document comptable et des données associées
BEGIN
  -- Insertion dans SAE_document_comptable
  INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, fichier_document, montant_devis, recuperable_locataire, identifiant_locataire, identifiant_batiment, siret)
  VALUES ('DOC_TEST', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'facture', '1500', 'facture_test.pdf', null, 1, 'LOC001', null, 'ENTR001');

  -- Insertion dans SAE_Charge_cf
  INSERT INTO SAE_Charge_cf (id_charge_cf,Date_de_charge, Type, montant, numero_document, Date_document)
  VALUES ('TESTCF',TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'Eau', '300', 'DOC_TEST', TO_DATE('2024-01-01', 'YYYY-MM-DD'));


  -- Insertion dans sae_facture_du_bien
  INSERT INTO sae_facture_du_bien (identifiant_logement, numero_document, Date_document, part_des_charges)
  VALUES ('LOG001', 'DOC_TEST', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 0.5);
  
  COMMIT;
END;
/

-- Vérification des insertions
SELECT * FROM SAE_document_comptable WHERE numero_document = 'DOC_TEST';
SELECT * FROM SAE_Charge_cf WHERE numero_document = 'DOC_TEST';
SELECT * FROM sae_facture_du_bien WHERE numero_document = 'DOC_TEST';

-- Test de la suppression via le package
BEGIN
  pkg_document_comptable.supprimer_document_comptable('DOC_TEST', TO_DATE('2024-01-01', 'YYYY-MM-DD'));
END;
/

-- Vérification de la suppression
SELECT * FROM SAE_document_comptable WHERE numero_document = 'DOC_TEST';
SELECT * FROM SAE_Charge_cf WHERE numero_document = 'DOC_TEST';
SELECT * FROM sae_facture_du_bien WHERE numero_document = 'DOC_TEST';


