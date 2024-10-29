set serveroutput on 
--Création du paquetage (l'entête) d'une liste d'adresse
create or replace package SAE_LISTEADRESSE as 

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
end SAE_LISTEADRESSE;
/
CREATE OR REPLACE PACKAGE BODY SAE_LISTEADRESSE AS
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


END SAE_LISTEADRESSE;
/


-- TEST 

INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0001', 'michel.martin@example.com', '80002', 'Toulouse');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0002', 'henry.martin@example.com', '40002', 'Paris');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0003', 'louise.martin@example.com', '54002', 'Bordeaux');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0004', 'maji.martin@example.com', '23002', 'Lyon');
INSERT INTO SAE_ADRESSE(id_sae_adresse, adresse, code_postal_, ville) values ('AL0005', 'gouil.martin@example.com', '21002', 'Lavaur');

DECLARE
    -- Variable pour stocker le tableau retourn� par la fonction
    res SAE_LISTEADRESSE.T_ADRESSES;
    res2 SAE_LISTEADRESSE.T_ADRESSE_RECORD;
    -- Index pour parcourir le tableau
    
BEGIN
    -- Appel de la fonction pour r�cup�rer les baux
    res := SAE_LISTEADRESSE.GET_ADRESSES();
    res2 := SAE_LISTEADRESSE.GET_ADRESSE('AL0005');
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

ROLLBACK;