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

    -- Supprimer le bail
    DELETE FROM SAE_Bail
    WHERE id_bail = p_id_bail;

  END supprimer_Bail;
END pkg_Bail;




SET SERVEROUTPUT ON;

DECLARE
    -- Déclaration des variables avec des types explicites
    v_id_bail VARCHAR2(20) := 'B001';
    v_id_bien VARCHAR2(20) := 'LOG001';

    -- Compteurs pour vérifier les enregistrements
    v_count_regularisation INTEGER;
    v_count_bail INTEGER;
    v_count_bien INTEGER;
BEGIN
    
      


    -- 2. Insérer un bail de test lié au bien locatif
    INSERT INTO SAE_BAIL (ID_BAIL, DATE_DE_DEBUT, DATE_DE_FIN,IDENTIFIANT_LOGEMENT)
    VALUES (v_id_bail,  TO_DATE('2024-01-01', 'YYYY-MM-DD'), TO_DATE('2025-01-01', 'YYYY-MM-DD'),v_id_bien);

    -- 3. Insérer des régularisations associées au bail de test
    INSERT INTO SAE_REGULARISATION (ID_BAIL, DATE_REGU, MONTANT)
    VALUES (v_id_bail, TO_DATE('2024-06-01', 'YYYY-MM-DD'), 100.00);
    INSERT INTO SAE_REGULARISATION (ID_BAIL, DATE_REGU, MONTANT)
    VALUES (v_id_bail, TO_DATE('2024-09-01', 'YYYY-MM-DD'), 150.00);

    COMMIT;

    -- Vérifier que les enregistrements existent avant suppression
    SELECT COUNT(*) INTO v_count_bien FROM SAE_BIEN_LOCATIF WHERE IDENTIFIANT_LOGEMENT = v_id_bien;
    DBMS_OUTPUT.PUT_LINE('Nombre de biens locatifs avant suppression : ' || v_count_bien);

    SELECT COUNT(*) INTO v_count_bail FROM SAE_BAIL WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de baux avant suppression : ' || v_count_bail);

    SELECT COUNT(*) INTO v_count_regularisation FROM SAE_REGULARISATION WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de régularisations avant suppression : ' || v_count_regularisation);

    -- 4. Appeler la procédure pour supprimer le bail
    pkg_Bail.supprimer_Bail(v_id_bail);

    -- Vérifier que le bail a été supprimé
    SELECT COUNT(*) INTO v_count_bail FROM SAE_BAIL WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de baux après suppression : ' || v_count_bail);

    -- Vérifier que les régularisations associées ont été supprimées
    SELECT COUNT(*) INTO v_count_regularisation FROM SAE_REGULARISATION WHERE ID_BAIL = v_id_bail;
    DBMS_OUTPUT.PUT_LINE('Nombre de régularisations après suppression : ' || v_count_regularisation);

    -- 5. Assertions pour vérifier que le bail et les régularisations ont été supprimés
    IF v_count_bail = 0 AND v_count_regularisation = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Test réussi : le bail et les régularisations ont été supprimés.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Test échoué : le bail ou les régularisations n’ont pas été supprimés correctement.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
        ROLLBACK;
END;
/
