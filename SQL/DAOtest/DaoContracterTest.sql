insert into SAE_BAIL values('bail_1', TO_DATE('01-02-2022','DD-MM-YYYY'),TO_DATE('01-02-2024','DD-MM-YYYY'));
insert into SAE_BAIL values('bail_2', TO_DATE('05-02-2019','DD-MM-YYYY'),TO_DATE('24-08-2022','DD-MM-YYYY'));
insert into SAE_BAIL values('bail_3', TO_DATE('20-05-2023','DD-MM-YYYY'),TO_DATE('21-05-2028','DD-MM-YYYY'));



INSERT INTO sae_LOCATAIRE VALUES (
    'LOC001',
    'Dupont',
    'Jean',
    'jean.dupont@example.com',
    '0600000001',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    'Paris',
    'Acte de caution signé',
    NULL
);
INSERT INTO sae_LOCATAIRE VALUES (
    'LOC002',
    'Dubois',
    'Jean',
    'jean.dupont@example.com',
    '0600001501',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    'Paris',
    'Acte de caution signé',
    NULL
);
INSERT INTO sae_LOCATAIRE VALUES (
    'LOC002',
    'Lefrancois',
    'Axel',
    'axel.lefrancois@example.com',
    '0600008901',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    'Paris',
    'Acte de caution signé',
    NULL
);


-- Create

BEGIN
    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 50
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/
BEGIN
    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => NULL,
        p_id_locataire => 'LOC002',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 60
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => NULL,
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 60
    );
    DBMS_OUTPUT.PUT_LINE('Test 3: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/
BEGIN
    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 60
    );
    DBMS_OUTPUT.PUT_LINE('Test 4: Ce contrat a deja etait place dans la base de donnees. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

-- Update

BEGIN
    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 55
    );
     DBMS_OUTPUT.PUT_LINE('Test 1 : Mise a jour complete reussie');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1 : Erreur - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => NULL,
        p_id_locataire => 'LOC002',
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 60
    );
     DBMS_OUTPUT.PUT_LINE('Test 2: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_1',
        p_id_locataire => NULL,
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 60
    );
     DBMS_OUTPUT.PUT_LINE('Test 3: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/


BEGIN
    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => NULL,
        p_date_d_entree => NULL,
        p_part_loyer => NULL
    );
     DBMS_OUTPUT.PUT_LINE('Test 4 : Mise a jour complete reussie');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4 : Erreur - ' || SQLERRM);
END;
/