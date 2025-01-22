CREATE OR REPLACE PACKAGE pkg_Bien_Locatif AS
  PROCEDURE supprimer_Bien_Locatif(
    p_identifiant_logement IN SAE_Bien_locatif.identifiant_logement%TYPE
  );
  
  PROCEDURE est_loue_entre(
    p_identifiant_logement IN SAE_Bien_locatif.identifiant_logement%TYPE,
    p_debut_periode IN DATE,
    p_fin_periode IN DATE,
    p_result OUT NUMBER
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
    FROM sae_bail
    WHERE identifiant_logement = p_identifiant_logement;

    IF v_bail_count > 0 THEN
      RAISE_APPLICATION_ERROR(-20101, 'Le bien locatif poss�de des baux li�s et ne peut pas �tre supprim�.');
    END IF;

    -- V�rifier s'il existe des documents comptables li�s au bien locatif
    SELECT COUNT(*)
    INTO v_doc_comptable_count
    FROM sae_facture_du_bien
    WHERE identifiant_logement = p_identifiant_logement;

    IF v_doc_comptable_count > 0 THEN
      RAISE_APPLICATION_ERROR(-20102, 'Le bien locatif poss�de des documents comptables li�s et ne peut pas �tre supprim�.');
    END IF;

    -- Supprimer les loyers associ�s au bien locatif
    DELETE FROM sae_loyer
    WHERE identifiant_logement = p_identifiant_logement;

    -- Supprimer le bien locatif
    DELETE FROM SAE_Bien_locatif
    WHERE identifiant_logement = p_identifiant_logement;

  END supprimer_Bien_Locatif;
  
  
  PROCEDURE est_loue_entre(
    p_identifiant_logement IN SAE_Bien_locatif.identifiant_logement%TYPE,
    p_debut_periode IN DATE,
    p_fin_periode IN DATE,
    p_result OUT NUMBER
  ) IS
    v_bail_count NUMBER;
  BEGIN
    -- V�rifier s'il existe des baux li�s au bien locatif
    SELECT COUNT(*)
    INTO v_bail_count
    FROM sae_bail
    WHERE identifiant_logement = p_identifiant_logement
    AND date_de_debut <= p_fin_periode 
    AND (date_de_fin >= p_debut_periode OR date_de_fin is NULL);

    IF v_bail_count = 0 THEN
      p_result := 0;
    ELSE
      p_result := 1;
    END IF;

  END est_loue_entre;

  
END pkg_Bien_Locatif;





/
-- TESTS
SET SERVEROUTPUT ON;
-- Pr�paration des donn�es
BEGIN
  -- Adresse pour le b�timent et les entit�s li�es
  INSERT INTO SAE_Adresse (Id_SAE_Adresse, adresse, Code_postal, ville, Complement_adresse)
  VALUES ('TESTADDR01', '1 rue du Test', 75001, 'Paris', NULL);

  -- Cr�ation d'un b�timent
  INSERT INTO SAE_batiment (identifiant_batiment, Id_SAE_Adresse)
  VALUES ('TESTBAT01', 'TESTADDR01');

  -- Bien locatif sans d�pendances
  INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
  VALUES ('TESTLOG01', 1000, 'FISCAL01', 'Appartement A', 50, 2, 'Appartement', 'TESTBAT01');

  -- Bien locatif avec des baux li�s
  INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
  VALUES ('TESTLOG02', 1200, 'FISCAL02', 'Appartement B', 60, 3, 'Appartement', 'TESTBAT01');
  INSERT INTO SAE_Bail (Id_bail, Date_de_debut, date_de_fin, identifiant_logement)
  VALUES ('TESTBAIL01', TO_DATE('2023-01-01', 'YYYY-MM-DD'), NULL, 'TESTLOG02');

  -- Bien locatif avec des documents comptables li�s
  INSERT INTO SAE_Bien_locatif (identifiant_logement, Loyer_de_base, identifiant_fiscal, complement_d_adresse, surface, nb_piece, Type_de_bien, identifiant_batiment)
  VALUES ('TESTLOG03', 1500, 'FISCAL03', 'Appartement C', 80, 4, 'Appartement', 'TESTBAT01');
  INSERT INTO SAE_document_comptable (numero_document, Date_document, Type_de_document, montant, identifiant_batiment, identifiant_locataire)
  VALUES ('TESTDOC01', TO_DATE('2023-02-01', 'YYYY-MM-DD'), 'quittance', '500', 'TESTBAT01', NULL);
  INSERT INTO sae_facture_du_bien (identifiant_logement, numero_document, Date_document, part_des_charges)
  VALUES ('TESTLOG03', 'TESTDOC01', TO_DATE('2023-02-01', 'YYYY-MM-DD'), 0.5);
END;
/

-- Test1
BEGIN
  pkg_Bien_Locatif.supprimer_Bien_Locatif('TESTLOG01');
  DBMS_OUTPUT.PUT_LINE('Test 1 : Suppression r�ussie pour le bien sans d�pendances.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Test 1 : �chec - ' || SQLERRM);
END;
/
-- Test2
BEGIN
  pkg_Bien_Locatif.supprimer_Bien_Locatif('TESTLOG02');
  DBMS_OUTPUT.PUT_LINE('Test 2 : Suppression r�ussie (ceci n�est pas attendu).');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Test 2 : �chec attendu - ' || SQLERRM);
END;
/
-- Test3
BEGIN
  pkg_Bien_Locatif.supprimer_Bien_Locatif('TESTLOG03');
  DBMS_OUTPUT.PUT_LINE('Test 3 : Suppression r�ussie (ceci n�est pas attendu).');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Test 3 : �chec attendu - ' || SQLERRM);
END;
/
-- TEST4
BEGIN
  -- Suppression des d�pendances
  DELETE FROM sae_facture_du_bien WHERE identifiant_logement = 'TESTLOG03';
  DELETE FROM SAE_document_comptable WHERE numero_document = 'TESTDOC01';

  -- Tentative de suppression
  pkg_Bien_Locatif.supprimer_Bien_Locatif('TESTLOG03');
  DBMS_OUTPUT.PUT_LINE('Test 4 : Suppression r�ussie apr�s suppression des d�pendances.');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Test 4 : �chec - ' || SQLERRM);
END;
/


-- Nettoyage des donn�es pour �viter des conflits sur d'autres tests
BEGIN
  DELETE FROM sae_facture_du_bien WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_Bail WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_Bien_locatif WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_batiment WHERE identifiant_batiment LIKE 'TEST%';
  DELETE FROM SAE_Adresse WHERE Id_SAE_Adresse LIKE 'TEST%';
END;
/



