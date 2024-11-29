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
    'Acte de caution signe',
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
    'Acte de caution signe',
    NULL
);
INSERT INTO sae_LOCATAIRE VALUES (
    'LOC003',
    'Lefrancois',
    'Axel',
    'axel.lefrancois@example.com',
    '0600008901',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    'Paris',
    'Acte de caution signe',
    NULL
);
SELECT * FROM SAE_ADRESSE;
call DBMS_OUTPUT.PUT_LINE('rien n''est cense etre affiche');


-- Create

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1: l''insertion doit reussir.');

    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6 -- la part du loyer est sur 10, pas sur 100.
    );
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1(2): l''insertion doit reussir.');

    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_2',
        p_id_locataire => 'LOC002',
        p_date_de_sortie => TO_DATE('2028-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2027-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 9 -- la part du loyer est sur 10, pas sur 100.
    );
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1(2): Erreur - ' || SQLERRM);
END;
/
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 2: L''identifiant est null. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => NULL,
        p_id_locataire => 'LOC002',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 3: L''identifiant est null. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => NULL,
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 4: Ce contrat a deja etait place dans la base de donnees. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_CREATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2024-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2023-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/
call DBMS_OUTPUT.PUT_LINE('Deux lignes sont censees etre affichees');

select * from SAE_CONTRACTER;
-- Update

BEGIN
     DBMS_OUTPUT.PUT_LINE('Test 5 : Mise a jour complete doit reussir');

    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_1',
        p_id_locataire => 'LOC001',
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 5
    );
        DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 5 : Erreur - ' || SQLERRM);
END;
    

/

BEGIN
     DBMS_OUTPUT.PUT_LINE('Test 6: L''identifiant est null. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => NULL,
        p_id_locataire => 'LOC002',
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
        DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 6: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 7: L''identifiant est null. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_1',
        p_id_locataire => NULL,
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
        DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 7: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/

BEGIN
     DBMS_OUTPUT.PUT_LINE('Test 8: L''identifiant n''existe pas. Il doit y avoir une erreur normalement.');

    SAE_DAO_CONTRACTER.SAE_UPDATE(
        p_id_bail => 'bail_',
        p_id_locataire => 'Locjda',
        p_date_de_sortie => TO_DATE('2025-12-31', 'YYYY-MM-DD'),
        p_date_d_entree => TO_DATE('2024-01-01', 'YYYY-MM-DD'),
        p_part_loyer => 6
    );
        DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 8: Erreur obtenu, le test est reussi - ' || SQLERRM);
END;
/

select * from SAE_CONTRACTER;
call DBMS_OUTPUT.PUT_LINE('Deux lignes dont une modifie sont censees etre affichees');

-- Delete
begin

    SAE_DAO_CONTRACTER.SAE_DELETE('bail_1','LOC001');

end;
/
select * from SAE_CONTRACTER;

call DBMS_OUTPUT.PUT_LINE('Test Delete : Ceci n''est cense afficher qu''une ligne');


Rollback;
