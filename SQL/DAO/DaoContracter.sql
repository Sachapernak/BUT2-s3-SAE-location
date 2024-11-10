-- Package specification
Create or replace package SAE_DAO_CONTRACTER as 
    
    procedure SAE_CREATE(
        p_id_bail sae_bail.id_bail%type, 
        p_id_locataire sae_locataire.identifiant_locataire%type, 
        p_date_de_sortie sae_contracter.date_de_sortie%type, 
        p_date_d_entree sae_contracter.date_d_entree%type,
        p_part_loyer sae_contracter.part_de_loyer%type
    );

    procedure SAE_DELETE(p_id_bail sae_bail.id_bail%TYPE, p_id_locataire sae_locataire.identifiant_locataire%type);

    procedure SAE_UPDATE(
        p_id_bail sae_bail.id_bail%type, 
        p_id_locataire sae_locataire.identifiant_locataire%type, 
        p_date_de_sortie sae_contracter.date_de_sortie%type, 
        p_date_d_entree sae_contracter.date_d_entree%type,
        p_part_loyer sae_contracter.part_de_loyer%type
    );

end SAE_DAO_CONTRACTER;
/
-- Package body
CREATE OR REPLACE PACKAGE BODY SAE_DAO_CONTRACTER AS

    procedure SAE_CREATE(
        p_id_bail sae_bail.id_bail%type, 
        p_id_locataire sae_locataire.identifiant_locataire%type, 
        p_date_de_sortie sae_contracter.date_de_sortie%type, 
        p_date_d_entree sae_contracter.date_d_entree%type,
        p_part_loyer sae_contracter.part_de_loyer%type
    ) AS
    BEGIN
        IF (p_id_bail IS NULL OR p_id_locataire IS NULL OR p_date_d_entree is null) THEN
            raise_application_error(-20020, 'L''identifiant du bail, l''identifiant du locataire et la date d''entree doivent etre renseignes');
        END IF;
        
        INSERT INTO sae_contracter (identifiant_locataire,id_bail, date_de_sortie, date_d_entree,part_de_loyer) 
        VALUES (p_id_locataire, p_id_bail, p_date_de_sortie, p_date_d_entree, p_part_loyer);
    EXCEPTION 
        WHEN dup_val_on_index THEN 
            raise_application_error(-20022, 'Le contrat est deja dans la base de donnees');
    END SAE_CREATE;    

    procedure SAE_DELETE(p_id_bail sae_bail.id_bail%TYPE, p_id_locataire sae_locataire.identifiant_locataire%type) AS
    BEGIN
        DELETE FROM sae_contracter WHERE id_bail = p_id_bail and p_id_locataire = identifiant_locataire;
    END SAE_DELETE;

    procedure SAE_UPDATE(
        p_id_bail sae_bail.id_bail%type, 
        p_id_locataire sae_locataire.identifiant_locataire%type, 
        p_date_de_sortie sae_contracter.date_de_sortie%type, 
        p_date_d_entree sae_contracter.date_d_entree%type,
        p_part_loyer sae_contracter.part_de_loyer%type
        
    ) AS
        vCount number;
    BEGIN
        IF (p_id_bail IS NULL or p_id_locataire is NULL) THEN
            raise_application_error(-20023, 'Sans l''identifiant, on ne peut pas cibler la ligne a modifier.');
        END IF;
        
        -- Verifier l'existence de l'enregistrement
        SELECT COUNT(*) INTO vCount 
        FROM sae_contracter 
        WHERE id_bail = p_id_bail
        AND identifiant_locataire = p_id_locataire;

        IF vCount = 0 THEN
            -- Lever une erreur si le contrat n'existe pas
            RAISE_APPLICATION_ERROR(-20024, 'Il n''existe aucun contrat avec les deux identifiants donnes');
        END IF;

        UPDATE sae_contracter
        SET date_d_entree = p_date_d_entree, date_de_sortie = p_date_de_sortie, part_de_loyer = p_part_loyer
        WHERE id_bail = p_id_bail
        AND identifiant_locataire = p_id_locataire;
    END SAE_UPDATE;

END SAE_DAO_CONTRACTER;
/
