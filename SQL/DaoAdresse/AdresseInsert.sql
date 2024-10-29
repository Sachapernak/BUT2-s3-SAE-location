
create or replace procedure SAE_INSERT_ADRESSE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type)
as
begin
    if (P_ID_ADRESSE is null or p_adresse is null or p_code_postal is null or p_ville is null) then
        raise_application_error(-20011, 'L''identifiant de l''adresse, l''adresse mail, le code postal et la ville  doivent etre renseignes');
    end if;
    

    insert into sae_ADRESSE values (P_ID_ADRESSE, p_adresse, p_code_postal, p_ville, p_complement_adresse);
        exception 
            when dup_val_on_index then raise_application_error(-20016, 'L''adresse est deja dans la base de donnees');

end;
/

-- TEST

-- Toutes les paramètres sont valides (doit passé)
BEGIN
    SAE_INSERT_ADRESSE(
        P_ID_ADRESSE => 'AD001',
        p_adresse => '1 rue de la Paix',
        p_code_postal => '75001',
        p_ville => 'Paris',
        p_complement_adresse => '2e étage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion réussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/


-- Une valeur NULL pour un champ obligatoire (ne doit pas passé)
BEGIN
    SAE_INSERT_ADRESSE(
        P_ID_ADRESSE => 'AD002',
        p_adresse => NULL,
        p_code_postal => '69001',
        p_ville => 'Lyon',
        p_complement_adresse => '3e étage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il y a un null refait gro');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Réussi - ' || SQLERRM);
END;
/

-- Tentative d'insertion avec le même ID pour tester le doublon (ne doit pas passé)

BEGIN
   
    SAE_INSERT_ADRESSE(
        P_ID_ADRESSE => 'AD001',
        p_adresse => '10 boulevard Saint-Germain',
        p_code_postal => '75005',
        p_ville => 'Paris',
        p_complement_adresse => NULL
    );

    DBMS_OUTPUT.PUT_LINE('Test 3: l''ID est en double, refait gro');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: réussie' || SQLERRM);
END;
/

rollback;