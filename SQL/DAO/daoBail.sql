-- Package specification
Create or replace package SAE_DAO_BAIL as 
    
    procedure SAE_CREATE(
        p_id_bail sae_bail.id_bail%type, 
        p_date_de_debut sae_bail.date_de_debut%type, 
        p_date_de_fin sae_bail.date_de_fin%type
    );

    procedure SAE_DELETE(p_id_bail sae_bail.id_bail%TYPE);

    procedure SAE_UPDATE(
        p_id_bail sae_bail.id_bail%type, 
        p_date_de_debut sae_bail.date_de_debut%type, 
        p_date_de_fin sae_bail.date_de_fin%type
    );

end SAE_DAO_BAIL;
/
-- Package body
CREATE OR REPLACE PACKAGE BODY SAE_DAO_BAIL AS

    procedure SAE_CREATE(
        p_id_bail sae_bail.id_bail%type, 
        p_date_de_debut sae_bail.date_de_debut%type, 
        p_date_de_fin sae_bail.date_de_fin%type
    ) AS
    BEGIN
        IF (p_id_bail IS NULL OR p_date_de_debut IS NULL OR p_date_de_fin IS NULL) THEN
            raise_application_error(-20040, 'L''identifiant du bail, la date de debut et la date de fin doivent etre renseignes');
        END IF;
        
        INSERT INTO sae_bail (id_bail, date_de_debut, date_de_fin) 
        VALUES (p_id_bail, p_date_de_debut, p_date_de_fin);
    EXCEPTION 
        WHEN dup_val_on_index THEN 
            raise_application_error(-20041, 'Le bail est deja dans la base de donnees');
    END SAE_CREATE;    

    procedure SAE_DELETE(p_id_bail sae_bail.id_bail%type) AS
    BEGIN
        DELETE FROM sae_bail WHERE id_bail = p_id_bail;
    END SAE_DELETE;

    procedure SAE_UPDATE(
        p_id_bail sae_bail.id_bail%type, 
        p_date_de_debut sae_bail.date_de_debut%type, 
        p_date_de_fin sae_bail.date_de_fin%type
    ) AS
        vCount number;
    BEGIN
        IF (p_id_bail IS NULL) THEN
            raise_application_error(-20042, 'Sans l''identifiant, on ne peut pas cibler la ligne a modifier.');
        END IF;
        
        -- V�rifier l'existence de l'enregistrement
        SELECT COUNT(*) INTO vCount 
        FROM sae_bail 
        WHERE id_bail = p_id_bail;

        IF vCount = 0 THEN
            -- Lever une erreur si le bail n'existe pas
            RAISE_APPLICATION_ERROR(-20043, 'Le bail avec l''ID ' || p_id_bail || ' n''existe pas dans la base de donn�es.');
        END IF;

        UPDATE sae_bail 
        SET date_de_debut = p_date_de_debut, date_de_fin = p_date_de_fin
        WHERE id_bail = p_id_bail;
    END SAE_UPDATE;

END SAE_DAO_BAIL;
/
