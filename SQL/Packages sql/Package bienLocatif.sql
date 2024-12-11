CREATE OR REPLACE PACKAGE pkg_Bien_Locatif AS
  PROCEDURE supprimer_Bien_Locatif(
    p_identifiant_logement IN SAE_Bien_locatif.identifiant_logement%TYPE
  );
END pkg_Bien_Locatif;
/

CREATE OR REPLACE PACKAGE BODY pkg_Bien_Locatif AS
  PROCEDURE supprimer_Bien_Locatif(
    p_identifiant_logement IN SAE_Bien_locatif.identifiant_logement%TYPE
  ) IS
    v_bail_count NUMBER;
    v_doc_comptable_count NUMBER;
  BEGIN
    -- V�rifier s'il existe des baux li�s au bien locatif
    SELECT COUNT(*)
    INTO v_bail_count
    FROM sae_mettre_en_location
    WHERE identifiant_logement = p_identifiant_logement;

    IF v_bail_count > 0 THEN
      RAISE_APPLICATION_ERROR(-20101, 'Le bien locatif poss�de des baux li�s et ne peut pas �tre supprim�.');
    END IF;

    -- V�rifier s'il existe des documents comptables li�s au bien locatif
    SELECT COUNT(*)
    INTO v_doc_comptable_count
    FROM SAE_document_comptable
    WHERE identifiant_logement = p_identifiant_logement;

    IF v_doc_comptable_count > 0 THEN
      RAISE_APPLICATION_ERROR(-20102, 'Le bien locatif poss�de des documents comptables li�s et ne peut pas �tre supprim�.');
    END IF;

    -- Supprimer les loyers associ�s au bien locatif
    DELETE FROM sae_loyer
    WHERE identifiant_logement = p_identifiant_logement;

    -- Supprimer les ICC associ�s au bien locatif
    DELETE FROM SAE_ICC
    WHERE identifiant_logement = p_identifiant_logement;

    -- Supprimer le bien locatif
    DELETE FROM SAE_Bien_locatif
    WHERE identifiant_logement = p_identifiant_logement;

  END supprimer_Bien_Locatif;
END pkg_Bien_Locatif;
