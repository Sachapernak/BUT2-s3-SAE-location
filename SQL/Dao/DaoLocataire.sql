set serveroutput on 
--Creation du paquetage (l'entite) d'une liste d'adresse
create or replace package SAE_DAO_LOCATAIRE as 

    procedure SAE_CREATE(
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type);

    procedure SAE_DELETE(p_id_locataire sae_locataire.identifiant_locataire%type);

    procedure SAE_UPDATE(
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
    

    procedure SAE_CREATE(
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
            raise_application_error(-20021, 'L''identifiant du locataire, le nom locataire, le prenom locataire et la date de naissance doivent etre renseignes');
        end if;
        

        insert into sae_LOCATAIRE values (p_id_locataire, p_nom_locataire, p_prenom_locataire, p_email_locataire, p_telephone_locataire,p_date_naissance, p_lieu_de_naissance , p_acte_de_caution, p_adresse_locataire);
            exception 
                when dup_val_on_index then raise_application_error(-20023, 'Le locataire est deja dans la base de donnees');

    end SAE_CREATE;    


    procedure SAE_DELETE(p_id_locataire sae_locataire.identifiant_locataire%type)
    as

    begin

        delete from sae_locataire where identifiant_locataire = p_id_locataire;
    end SAE_DELETE;

    procedure SAE_UPDATE(
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
            raise_application_error(-20013, 'Vous devez renseigner l''identifiant, sans cela, on ne pourra pas cibler la ligne a modifier.');
        end if;
        if (p_nom_locataire is null and p_prenom_locataire is null and p_email_locataire is null and p_telephone_locataire is null and p_date_naissance is null and p_lieu_de_naissance is null and p_acte_de_caution is null and p_adresse_locataire is null) then
            raise_application_error(-20014, 'Vous devez renseigner des valeurs a modifier');

        end if;

        UPDATE sae_locataire 
        set Nom_locataire = p_nom_locataire, Prenom_locataire = p_prenom_locataire, email_locataire = p_email_locataire, telephone_locataire = p_telephone_locataire, date_naissance = p_date_naissance, Lieu_de_naissance = p_lieu_de_naissance, Acte_de_caution = p_acte_de_caution, Id_SAE_Adresse = p_adresse_locataire
        where identifiant_locataire = p_id_locataire;
            
    end SAE_UPDATE;

END SAE_DAO_LOCATAIRE;
/

-- Create

BEGIN
    SAE_DAO_LOCATAIRE.SAE_CREATE(
        p_id_locataire => 'LOC001',
        p_nom_locataire => 'Dupont',
        p_prenom_locataire => 'Jean',
        p_email_locataire => 'jean.dupont@example.com',
        p_telephone_locataire => '0600000001',
        p_date_naissance => TO_DATE('1990-01-01', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Paris',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => null
    );
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_LOCATAIRE.SAE_CREATE(
        p_id_locataire => NULL,  
        p_nom_locataire => 'Dupont',
        p_prenom_locataire => 'Jean',
        p_email_locataire => 'jean.dupont@example.com',
        p_telephone_locataire => '0600000001',
        p_date_naissance => TO_DATE('1990-01-01', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Paris',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => null
    );
    DBMS_OUTPUT.PUT_LINE('Test 2: Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_LOCATAIRE.SAE_CREATE(
        p_id_locataire => 'LOC006',
        p_nom_locataire => 'Bernard',
        p_prenom_locataire => 'Sophie',
        p_email_locataire => 'sophie.bernard@example.com',
        p_telephone_locataire => '0600000006',
        p_date_naissance => TO_DATE('1988-09-15', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Bordeaux',
        p_acte_de_caution => 'Acte de caution signé',
        p_adresse_locataire => null
    );
    DBMS_OUTPUT.PUT_LINE('Nouvelle insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur - ' || SQLERRM);
END;
/

-- Update


BEGIN
    SAE_DAO_LOCATAIRE.SAE_UPDATE(
        p_id_locataire => 'LOC006',            
        p_nom_locataire => 'Martin',          
        p_prenom_locataire => 'Claire',        
        p_email_locataire => 'claire.martin@example.com', 
        p_telephone_locataire => '0600000008',
        p_date_naissance => TO_DATE('1990-04-20', 'YYYY-MM-DD'), 
        p_lieu_de_naissance => 'Lille',       
        p_acte_de_caution => 'Acte mis à jour', 
        p_adresse_locataire => null         
    );
    DBMS_OUTPUT.PUT_LINE('Test 1 : Mise a jour complete reussie pour LOC006.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1 : Erreur - ' || SQLERRM);
END;
/

BEGIN
    SAE_DAO_LOCATAIRE.SAE_UPDATE(
        p_id_locataire => NULL,               
        p_nom_locataire => 'Dubois',         
        p_prenom_locataire => 'Marc',         
        p_email_locataire => 'marc.dubois@example.com', 
        p_telephone_locataire => '0600000010', 
        p_date_naissance => TO_DATE('1985-05-05', 'YYYY-MM-DD'),
        p_lieu_de_naissance => 'Paris',       
        p_acte_de_caution => 'Acte mis à jour', 
        p_adresse_locataire => null        
    );
    DBMS_OUTPUT.PUT_LINE('Test 3 : Veuillez renseigner l''identifiant, l''insertion n''est pas cense passer');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3 : Le test est passe - ' || SQLERRM);
END;
/

-- Delete

call SAE_DAO_LOCATAIRE.SAE_DELETE('LOC006');

DBMS_OUTPUT.PUT_LINE('Test Delete : Ceci n''est rien cense afficher');
SELECT * FROM SAE_LOCATAIRE where identifiant_locataire = 'LOC006';

ROLLBACK;