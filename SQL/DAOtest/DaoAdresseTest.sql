




INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0001', 'michel.martin@example.com', '80002', 'Toulouse');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0002', 'henry.martin@example.com', '40002', 'Paris');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0003', 'louise.martin@example.com', '54002', 'Bordeaux');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0004', 'maji.martin@example.com', '23002', 'Lyon');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0005', 'gouil.martin@example.com', '21002', 'Lavaur');
/

-- TEST Insert

-- Toutes les paramï¿½tres sont valides (doit passer)
BEGIN
    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AD001',
        p_adresse => '1 rue de la Paix',
        p_code_postal => '75001',
        p_ville => 'Paris',
        p_complement_adresse => '2e etage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/


-- Une valeur NULL pour un champ obligatoire (ne doit pas passer)
BEGIN
    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AD002',
        p_adresse => NULL,
        p_code_postal => '69001',
        p_ville => 'Lyon',
        p_complement_adresse => '3e etage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il y a un null sur un champ obligatoire, l''insertion n''est pas cense passer');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Reussi - ' || SQLERRM);
END;
/

-- Tentative d'insertion avec le meme ID pour tester le doublon (ne doit pas passer)

BEGIN
   
    SAE_DAO_ADRESSE.SAE_CREATE(
        P_ID_ADRESSE => 'AD001',
        p_adresse => '10 boulevard Saint-Germain',
        p_code_postal => '75005',
        p_ville => 'Paris',
        p_complement_adresse => NULL
    );

    DBMS_OUTPUT.PUT_LINE('Test 3: l''ID est en double, l''insertion n''est pas cense passer');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Reussi' || SQLERRM);
END;
/

-- TEST DELETE 


call SAE_DAO_ADRESSE.SAE_DELETE('AL0001');

-- n'est rien cense afficher.
SELECT * FROM SAE_ADRESSE where id_sae_adresse = 'AL0001';

/


-- TEST UPDATING


-- Toutes les parametres sont valides (doit passer)
BEGIN
    sae_dao_adresse.SAE_UPDATE('AL0001','10 rue de la Liberte','75001','Paris', '4e etage');
    DBMS_OUTPUT.PUT_LINE('Test 1: Mise a jour complete reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/



-- Une valeur NULL pour l'ID (ne doit pas passer)
BEGIN
    SAE_DAO_ADRESSE.SAE_UPDATE(
        P_ID_ADRESSE => NULL,  -- Identifiant manquant
        p_adresse => '20 avenue des Champs-elysees',
        p_code_postal => '75008',
        p_ville => 'Paris',
        p_complement_adresse => '2e etage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il y a un null, la modification n''est pas cense passer');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Reussi - ' || SQLERRM);
END;
/

-- Tentative de modification sans parametre a modifier (ne doit pas passer)

BEGIN
   
    SAE_DAO_ADRESSE.SAE_UPDATE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => NULL,
        p_code_postal => NULL,
        p_ville => NULL,
        p_complement_adresse => NULL
    );

    DBMS_OUTPUT.PUT_LINE('Test 3:Il n''y a aucun parametre a modifier, la modification n''est pas cense fonctionner');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Reussi' || SQLERRM);
END;
/

ROLLBACK;