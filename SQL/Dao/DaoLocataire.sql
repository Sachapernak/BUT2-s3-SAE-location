set serveroutput on 
--Crï¿½ation du paquetage (l'entï¿½te) d'une liste d'adresse
create or replace package SAE_DAO_LOCATAIRE as 

    TYPE T_LOCATAIRE_RECORD IS RECORD (
        identifiant_locataire VARCHAR2(20),
        Nom_locataire VARCHAR2(50),
        Prenom_locataire VARCHAR2(50),
        email_locataire VARCHAR2(50),
        telephone_locataire VARCHAR2(50),
        date_naissance VARCHAR2(50),
        Lieu_de_naissance VARCHAR2(50),
        Acte_de_caution VARCHAR2(50),
        Id_SAE_Adresse VARCHAR2(20)
    );
    type T_LOCATAIRES is table of T_LOCATAIRE_RECORD;

--    SAE_Locataire = (identifiant_locataire VARCHAR2(50) , Nom_locataire VARCHAR2(50) , Prenom_locataire VARCHAR2(50) , email_locataire VARCHAR2(50) , telephone_locataire VARCHAR2(50) , date_naissance DATE, Lieu_de_naissance VARCHAR2(50) , Acte_de_caution CLOB, #Id_SAE_Adresse*);

    function GET_LOCATAIRES return T_LOCATAIRES;
    function GET_LOCATAIRE(p_id_locataire sae_locataire.identifiant_locataire%TYPE) return T_LOCATAIRE_RECORD;
    procedure SAE_INSERT_LOCATAIRE(
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type);

    procedure sae_delete_locataire(p_id_locataire sae_locataire.identifiant_locataire%type);

    procedure SAE_UPDATE_LOCATAIRE(
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type);
    
    
end SAE_DAO_LOCATAIRE;
/
CREATE OR REPLACE PACKAGE BODY SAE_DAO_LOCATAIRE AS
    function GET_LOCATAIRES return T_LOCATAIRES AS
    
        CURSOR curLOCATAIRES IS 
            SELECT *
            FROM sae_locataire
            ORDER BY identifiant_locataire;

        res T_LOCATAIRES := T_LOCATAIRES();  -- Initialisation de la collection principale
        vLOCATAIRERECORD T_LOCATAIRE_RECORD; 
    BEGIN
    
        FOR vLOCATAIRE IN curLOCATAIRES LOOP
            res.EXTEND;  -- ï¿½tendre la collection `res` pour ajouter une nouvelle ligne
            vLOCATAIRERECORD.identifiant_locataire := vLOCATAIRE.identifiant_locataire;
            vLOCATAIRERECORD.Nom_locataire := vLOCATAIRE.Nom_locataire;
            vLOCATAIRERECORD.Prenom_locataire := vLOCATAIRE.Prenom_locataire;
            vLOCATAIRERECORD.email_locataire := vLOCATAIRE.email_locataire;
            vLOCATAIRERECORD.telephone_locataire := vLOCATAIRE.telephone_locataire;
            vLOCATAIRERECORD.date_naissance := vLOCATAIRE.date_naissance;
            vLOCATAIRERECORD.Lieu_de_naissance := vLOCATAIRE.Lieu_de_naissance;
            vLOCATAIRERECORD.Acte_de_caution := vLOCATAIRE.Acte_de_caution;
            vLOCATAIRERECORD.Id_SAE_Adresse := vLOCATAIRE.Id_SAE_Adresse;

            res(res.count) := vLOCATAIRERECORD;
        END LOOP;

        RETURN res;
    END GET_LOCATAIRES;

    function GET_LOCATAIRE(p_id_locataire sae_locataire.identifiant_locataire%TYPE) return T_LOCATAIRE_RECORD AS
    
        CURSOR curLOCATAIRES IS 
            SELECT *
            FROM sae_locataire
            
            ORDER BY identifiant_locataire;

        vLOCATAIRERECORD T_LOCATAIRE_RECORD; 

    BEGIN
    
        FOR vLOCATAIRE IN curLOCATAIRES LOOP
            if (vLOCATAIRE.id_sae_adresse = p_id_adresse) then
                vLOCATAIRERECORD.identifiant_locataire := vLOCATAIRE.identifiant_locataire;
                vLOCATAIRERECORD.Nom_locataire := vLOCATAIRE.Nom_locataire;
                vLOCATAIRERECORD.Prenom_locataire := vLOCATAIRE.Prenom_locataire;
                vLOCATAIRERECORD.email_locataire := vLOCATAIRE.email_locataire;
                vLOCATAIRERECORD.telephone_locataire := vLOCATAIRE.telephone_locataire;
                vLOCATAIRERECORD.date_naissance := vLOCATAIRE.date_naissance;
                vLOCATAIRERECORD.Lieu_de_naissance := vLOCATAIRE.Lieu_de_naissance;
                vLOCATAIRERECORD.Acte_de_caution := vLOCATAIRE.Acte_de_caution;
                vLOCATAIRERECORD.Id_SAE_Adresse := vLOCATAIRE.Id_SAE_Adresse;
            end if;
        END LOOP;

        RETURN vLOCATAIRERECORD;
    END GET_LOCATAIRE;


    procedure SAE_INSERT_LOCATAIRE(
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type)
    as
    begin
        if (p_id_locataire is null or p_nom_locataire is null or p_prenom_locataire is null or p_date_naissance is null) then
            raise_application_error(-20021, 'L''identifiant du locataire, le nom locataire,le prenom locataire et la date de naissance doivent etre renseignes');
        end if;
        if ((p_date_naissance - sysdate)/365.25 < 18 ) then
            raise_application_error(-20022, 'Le locataire doit etre majeur');
        end if;

        insert into sae_LOCATAIRE values (p_id_locataire, p_nom_locataire, p_prenom_locataire, p_email_locataire, p_telephone_locataire,p_date_naissance, p_lieu_de_naissance , p_acte_de_caution, p_adresse_locataire);
            exception 
                when dup_val_on_index then raise_application_error(-20023, 'Le locataire est deja dans la base de donnees');

    end SAE_INSERT_LOCATAIRE;    


    procedure sae_delete_locataire(p_id_locataire sae_locataire.identifiant_locataire%type)
    as

    begin

        delete from sae_locataire where identifiant_locataire = p_id_locataire;
    end sae_delete_locataire;

    procedure SAE_UPDATE_LOCATAIRE(
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type)
    as
    begin
        if (p_id_locataire is null) then
            raise_application_error(-20013, 'Sans l''identifiant on ne pourra pas cibler la ligne que tu souhaties modifier.');
        end if;
        if (p_nom_locataire is null and p_prenom_locataire is null and p_email_locataire is null and p_telephone_locataire is null and p_date_naissance is null and p_lieu_de_naissance is null and p_acte_de_caution is null and p_adresse_locataire is null) then
            raise_application_error(-20014, 'Qu''est-ce que tu veux faire avec Locataire si tu ne mets aucun parametre a modifier');

        end if;

        UPDATE sae_locataire 
        set Nom_locataire = p_nom_locataire, Prenom_locataire = p_prenom_locataire, email_locataire = p_email_locataire, telephone_locataire = p_telephone_locataire, date_naissance = p_date_naissance, Lieu_de_naissance = p_lieu_de_naissance, Acte_de_caution = p_acte_de_caution, Id_SAE_Adresse = p_adresse_locataire
        where identifiant_locataire = p_id_locataire;
            
    end SAE_UPDATE_LOCATAIRE;

END SAE_DAO_LOCATAIRE;
/

BEGIN
    SAE_INSERT_LOCATAIRE(
        p_id_locataire => 'LOC001',
        p_nom_locataire => 'Dupont',
        p_prenom_locataire => 'Jean',
        p_email_locataire => 'jean.dupont@example.com',
        p_telephone_locataire => '0600000001',
        p_date_naissance => TO_DATE('1990-01-01', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Paris',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => 'AD001'
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion réussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/

BEGIN
    SAE_INSERT_LOCATAIRE(
        p_id_locataire => NULL,  -- Identifiant manquant
        p_nom_locataire => 'Dupont',
        p_prenom_locataire => 'Jean',
        p_email_locataire => 'jean.dupont@example.com',
        p_telephone_locataire => '0600000001',
        p_date_naissance => TO_DATE('1990-01-01', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Paris',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => 'AD001'
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, tu as réussi - ' || SQLERRM);
END;
/

BEGIN
    SAE_INSERT_LOCATAIRE(
        p_id_locataire => 'LOC002',
        p_nom_locataire => 'Martin',
        p_prenom_locataire => 'Marie',
        p_email_locataire => 'marie.martin@example.com',
        p_telephone_locataire => '0600000002',
        p_date_naissance => NULL,  -- Date de naissance manquante
        p_lieu_de_naissance => 'Lyon',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => 'AD002'
    );
    DBMS_OUTPUT.PUT_LINE('Test 3: Insertion réussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur - ' || SQLERRM);
END;
/


ROLLBACK;