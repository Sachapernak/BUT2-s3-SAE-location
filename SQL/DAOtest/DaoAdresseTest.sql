


SELECT * FROM SAE_ADRESSE;



/

-- TEST Insert

-- Toutes les parametres sont valides (doit passer)
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion doit reussir.');

    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => '1 rue de la Paix',
        p_code_postal => '75001',
        p_ville => 'Paris',
        p_complement_adresse => '2e etage'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1(2): Insertion doit reussir.');

    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AL0002',
        p_adresse => '10 boulevard Saint-Germain',
        p_code_postal => '75001',
        p_ville => 'Paris',
        p_complement_adresse => '2e etage'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1(2): Erreur - ' || SQLERRM);
END;
/


-- Une valeur NULL pour un champ obligatoire (ne doit pas passer)
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 2: Il y a un null sur un champ obligatoire, l''insertion n''est pas cense passer');

    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => NULL,
        p_code_postal => '69001',
        p_ville => 'Lyon',
        p_complement_adresse => '3e etage'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Reussi - ' || SQLERRM);
END;
/

-- Tentative d'insertion avec le meme ID pour tester le doublon (ne doit pas passer)

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 3: l''ID est en double, l''insertion n''est pas cense passer');
   
    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => '10 boulevard Saint-Germain',
        p_code_postal => '75005',
        p_ville => 'Paris',
        p_complement_adresse => NULL
    );

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Reussi' || SQLERRM);
END;
/
SELECT * FROM SAE_ADRESSE;

-- TEST UPDATING


-- Toutes les parametres sont valides (doit passer)
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 4: Mise a jour complete doit reussir.');

    sae_dao_adresse.SAE_UPDATE('AL0001','10 rue de la Liberte','75001','Paris', '4e etage');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4: Erreur - ' || SQLERRM);
END;
/



-- Une valeur NULL pour l'ID (ne doit pas passer)
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 5: Il y a un null, la modification n''est pas cense passer');

    SAE_DAO_ADRESSE.SAE_UPDATE(
        P_ID_ADRESSE => NULL,  -- Identifiant manquant
        p_adresse => '20 avenue des Champs-elysees',
        p_code_postal => '75008',
        p_ville => 'Paris',
        p_complement_adresse => '2e etage'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 5: Reussi - ' || SQLERRM);
END;
/
SELECT * FROM SAE_ADRESSE;





-- Delete
begin

    SAE_DAO_ADRESSE.SAE_DELETE('AL0001');

end;
/
SELECT * FROM SAE_ADRESSE;

call DBMS_OUTPUT.PUT_LINE('Test Delete : Ceci n''est cense afficher qu''une ligne');

ROLLBACK;