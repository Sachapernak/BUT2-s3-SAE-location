CREATE OR REPLACE PACKAGE pkg_Bail AS
  PROCEDURE supprimer_Bail(
    p_id_bail IN SAE_Bail.id_bail%TYPE
  );
END pkg_Bail;
/

CREATE OR REPLACE PACKAGE BODY pkg_Bail AS
  PROCEDURE supprimer_Bail(
    p_id_bail IN SAE_Bail.id_bail%TYPE
  ) IS
   
    
  BEGIN
   
    -- Supprimer les regularisations associes au bail
    DELETE FROM sae_regularisation
    WHERE id_bail = p_id_bail;

    -- Supprimer les regularisations associes au bail
    DELETE FROM sae_provision_charge
    WHERE id_bail = p_id_bail;

    -- Supprimer les regularisations associes au bail
    DELETE FROM sae_document
    WHERE id_bail = p_id_bail;

    -- Supprimer le bail
    DELETE FROM SAE_Bail
    WHERE id_bail = p_id_bail;

  END supprimer_Bail;
END pkg_Bail;


SET SERVEROUTPUT ON;

DECLARE
    -- Déclaration des variables avec des types explicites
    v_id_bail VARCHAR2(20) := 'TESTBAI01';
   
    

    -- Compteurs pour vérifier les enregistrements
    v_count_regularisation INTEGER;
    v_count_provision_charge INTEGER;
    v_count_document INTEGER;
    v_count_bail INTEGER;

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

    -- 2. Insérer un bail de test lié au bien locatif
    INSERT INTO SAE_BAIL (ID_BAIL, DATE_DE_DEBUT, DATE_DE_FIN, IDENTIFIANT_LOGEMENT)
    VALUES (v_id_bail, TO_DATE('2024-01-01', 'YYYY-MM-DD'), TO_DATE('2025-01-01', 'YYYY-MM-DD'), 'TESTLOG01');
    -- 3. Insérer des régularisations associées au bail de test
    INSERT INTO SAE_REGULARISATION (ID_BAIL, DATE_REGU, MONTANT)
    VALUES (v_id_bail, TO_DATE('2024-06-01', 'YYYY-MM-DD'), 100.00);

    -- 4. Insérer des provisions pour charges associées au bail de test
    INSERT INTO SAE_PROVISION_CHARGE (ID_BAIL, DATE_CHANGEMENT, PROVISION_POUR_CHARGE)
    VALUES (v_id_bail, TO_DATE('2024-07-01', 'YYYY-MM-DD'), 200.00);

    -- 5. Insérer des documents associés au bail de test
    INSERT INTO SAE_DOCUMENT (ID_BAIL, DATE_DOCUMENT, TYPE_DE_DOCUMENT, URL_DOCUMENT)
    VALUES (v_id_bail, TO_DATE('2024-08-01', 'YYYY-MM-DD'), 'Contrat', 'http://example.com/contrat.pdf');

    COMMIT;

    -- Vérifier que les enregistrements existent avant suppression
    SELECT COUNT(*) INTO v_count_bail FROM SAE_BAIL WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de baux avant suppression : ' || v_count_bail);

    SELECT COUNT(*) INTO v_count_regularisation FROM SAE_REGULARISATION WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de régularisations avant suppression : ' || v_count_regularisation);

    SELECT COUNT(*) INTO v_count_provision_charge FROM SAE_PROVISION_CHARGE WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de provisions pour charges avant suppression : ' || v_count_provision_charge);

    SELECT COUNT(*) INTO v_count_document FROM SAE_DOCUMENT WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de documents avant suppression : ' || v_count_document);

    -- 6. Appeler la procédure pour supprimer le bail
    pkg_Bail.supprimer_Bail(v_id_bail);

    -- Vérifier que le bail et les enregistrements associés ont été supprimés
    SELECT COUNT(*) INTO v_count_bail FROM SAE_BAIL WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de baux après suppression : ' || v_count_bail);

    SELECT COUNT(*) INTO v_count_regularisation FROM SAE_REGULARISATION WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de régularisations après suppression : ' || v_count_regularisation);

    SELECT COUNT(*) INTO v_count_provision_charge FROM SAE_PROVISION_CHARGE WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de provisions pour charges après suppression : ' || v_count_provision_charge);

    SELECT COUNT(*) INTO v_count_document FROM SAE_DOCUMENT WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de documents après suppression : ' || v_count_document);

    -- 7. Assertions pour vérifier que tout a été supprimé
    IF v_count_bail = 0 AND v_count_regularisation = 0 AND v_count_provision_charge = 0 AND v_count_document = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Test réussi : le bail et les enregistrements associés ont été supprimés.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Test échoué : le bail ou les enregistrements associés n’ont pas été supprimés correctement.');
    END IF;
    
    -- Nettoyage des donn�es pour �viter des conflits sur d'autres tests

  DELETE FROM sae_facture_du_bien WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_Bail WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_Bien_locatif WHERE identifiant_logement LIKE 'TEST%';
  DELETE FROM SAE_batiment WHERE identifiant_batiment LIKE 'TEST%';
  DELETE FROM SAE_Adresse WHERE Id_SAE_Adresse LIKE 'TEST%';
  


EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
        ROLLBACK;
END;
/
