set serveroutput on 
--Création du paquetage (l'entête) d'une liste d'adresse
create or replace package SAE_DAO_ADRESSE as 

    TYPE T_ADRESSE_RECORD IS RECORD (
        id_sae_adresse VARCHAR2(20),
        adresse VARCHAR2(100),
        code_postal VARCHAR2(10),
        ville VARCHAR2(50),
        complement_adresse VARCHAR2(100)
    );
    type T_ADRESSES is table of T_ADRESSE_RECORD;

   
    function GET_ADRESSES return T_ADRESSES;
    function GET_ADRESSE(p_id_adresse sae_adresse.id_sae_adresse%TYPE) return T_ADRESSE_RECORD;
    procedure SAE_INSERT_ADRESSE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type);

    procedure sae_delete_adresse(p_id_adresse sae_adresse.id_sae_adresse%type);

    procedure SAE_UPDATE_ADRESSE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type);
end SAE_DAO_ADRESSE;
/
CREATE OR REPLACE PACKAGE BODY SAE_DAO_ADRESSE AS
    FUNCTION GET_ADRESSES RETURN T_ADRESSES AS
    
        CURSOR curADRESSES IS 
            SELECT id_sae_adresse, adresse, code_postal_, ville, complement_adresse
            FROM SAE_ADRESSE
            ORDER BY id_sae_adresse;

        res T_ADRESSES := T_ADRESSES();  -- Initialisation de la collection principale
        vADRESSERECORD T_ADRESSE_RECORD; 
    BEGIN
    
        FOR vADRESSE IN curADRESSES LOOP
            res.EXTEND;  -- Étendre la collection `res` pour ajouter une nouvelle ligne
            vADRESSERECORD.id_sae_adresse := vADRESSE.id_sae_adresse;
            vADRESSERECORD.adresse := vADRESSE.adresse;
            vADRESSERECORD.code_postal := vADRESSE.code_postal_;
            vADRESSERECORD.ville := vADRESSE.ville;
            vADRESSERECORD.complement_adresse := vADRESSE.complement_adresse;

            res(res.count) := vADRESSERECORD;
        END LOOP;

        RETURN res;
    END GET_ADRESSES;

    function GET_ADRESSE(p_id_adresse sae_adresse.id_sae_adresse%TYPE) return T_ADRESSE_RECORD AS
    
        CURSOR curADRESSES IS 
            SELECT id_sae_adresse, adresse, code_postal_, ville, complement_adresse
            FROM SAE_ADRESSE
            where id_sae_adresse = p_id_adresse
            ORDER BY id_sae_adresse;

        vADRESSERECORD T_ADRESSE_RECORD; 
    BEGIN
    
        FOR vADRESSE IN curADRESSES LOOP
            vADRESSERECORD.id_sae_adresse := vADRESSE.id_sae_adresse;
            vADRESSERECORD.adresse := vADRESSE.adresse;
            vADRESSERECORD.code_postal := vADRESSE.code_postal_;
            vADRESSERECORD.ville := vADRESSE.ville;
            vADRESSERECORD.complement_adresse := vADRESSE.complement_adresse;

        END LOOP;

        RETURN vADRESSERECORD;
    END GET_ADRESSE;


    procedure SAE_INSERT_ADRESSE(
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
                when dup_val_on_index then raise_application_error(-20012, 'L''adresse est deja dans la base de donnees');

    end;    


    procedure sae_delete_adresse(p_id_adresse sae_adresse.id_sae_adresse%type) as

    begin

        delete from sae_adresse where id_sae_adresse = p_id_adresse;
    end;

    procedure SAE_UPDATE_ADRESSE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type)
    as
    begin
        if (P_ID_ADRESSE is null) then
            raise_application_error(-20013, 'Sans l''identifiant on ne pourra pas cibler la ligne que tu souhaties modifier.');
        end if;
        if (p_adresse is null and p_code_postal is null and p_ville is null and p_complement_adresse is null) then
            raise_application_error(-20014, 'Qu''est-ce que tu veux faire avec Adresse si tu ne met aucun paramètre a modifier');

        end if;

        UPDATE sae_ADRESSE 
        set adresse = p_adresse, code_postal_ = p_code_postal, ville = p_ville, complement_adresse = p_complement_adresse
        where id_sae_adresse = p_id_adresse;
            
    end;

END SAE_DAO_ADRESSE;
/


-- TEST FindAll and FindbyId

INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0001', 'michel.martin@example.com', '80002', 'Toulouse');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0002', 'henry.martin@example.com', '40002', 'Paris');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0003', 'louise.martin@example.com', '54002', 'Bordeaux');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0004', 'maji.martin@example.com', '23002', 'Lyon');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0005', 'gouil.martin@example.com', '21002', 'Lavaur');

DECLARE
    -- Variable pour stocker le tableau retourn� par la fonction
    res SAE_DAO_ADRESSE.T_ADRESSES;
    res2 SAE_DAO_ADRESSE.T_ADRESSE_RECORD;
    -- Index pour parcourir le tableau
    
BEGIN
    -- Appel de la fonction pour r�cup�rer les baux
    res := SAE_DAO_ADRESSE.GET_ADRESSES();
    res2 := SAE_DAO_ADRESSE.GET_ADRESSE('AL0005');
    -- Boucle pour parcourir et afficher chaque element du tableau retourne
    
    FOR i IN 1 .. res.COUNT LOOP
        DBMS_OUTPUT.PUT_LINE('ID: ' || res(i).id_sae_adresse || 
                             ', Adresse: ' || res(i).adresse ||
                             ', Code Postal: ' || res(i).code_postal ||
                             ', Ville: ' || res(i).ville ||
                             ', Complement adresse: ' || res(i).complement_adresse);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('----------------------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('ID: ' || res2.id_sae_adresse || 
                             ', Adresse: ' || res2.adresse ||
                             ', Code Postal: ' || res2.code_postal ||
                             ', Ville: ' || res2.ville ||
                             ', Complement adresse: ' || res2.complement_adresse);
    
END;
/


-- TEST Insert

-- Toutes les paramètres sont valides (doit passé)
BEGIN
    SAE_DAO_ADRESSE.SAE_INSERT_ADRESSE(
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
    SAE_DAO_ADRESSE.SAE_INSERT_ADRESSE(
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
   
    SAE_DAO_ADRESSE.SAE_INSERT_ADRESSE(
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

-- TEST DELETE 


call SAE_DAO_ADRESSE.sae_delete_adresse('AL0001');

-- n'est rien censé afficher.
SELECT * FROM SAE_ADRESSE where id_sae_adresse = 'AL0001';

/


-- TEST UPDATING


-- Toutes les paramètres sont valides (doit passer)
BEGIN
    SAE_UPDATE_ADRESSE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => '10 rue de la Liberté',
        p_code_postal => '75001',
        p_ville => 'Paris',
        p_complement_adresse => '4e étage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Mise à jour complète réussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/



-- Une valeur NULL pour l'ID (ne doit pas passer)
BEGIN
    SAE_UPDATE_ADRESSE(
        P_ID_ADRESSE => NULL,  -- Identifiant manquant
        p_adresse => '20 avenue des Champs-Élysées',
        p_code_postal => '75008',
        p_ville => 'Paris',
        p_complement_adresse => '2e étage'
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il y a un null');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Réussi - ' || SQLERRM);
END;
/

-- Tentative de modification sans parametre a modifier (ne doit pas passer)

BEGIN
   
    SAE_UPDATE_ADRESSE(
        P_ID_ADRESSE => 'AL0001',
        p_adresse => NULL,
        p_code_postal => NULL,
        p_ville => NULL,
        p_complement_adresse => NULL
    );

    DBMS_OUTPUT.PUT_LINE('Test 3: aucun parametre a modifier et a quoi ca sert ?');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: réussie' || SQLERRM);
END;
/

ROLLBACK;