-- Type de donner comme la table
CREATE OR REPLACE TYPE contract_type AS OBJECT (
    identifiant_locataire varchar2(50),
    Id_bail varchar2(50),
    date_de_sortie DATE,
    date_d_entree DATE,
    part_de_loyer NUMBER(3,2));
/
    

CREATE OR REPLACE TYPE contract_array IS TABLE OF contract_type;
/

CREATE OR REPLACE PROCEDURE test_array(v_array in contract_array, v_sortie out varchar2) as

begin
    v_sortie := '';
    
    for str in (select id_bail from TABLE(v_array)) loop
        v_sortie := v_sortie || ' ' || str.id_bail;
    end loop;
    
end;
/


CREATE OR REPLACE PACKAGE pkg_locataire AS
    PROCEDURE supprimer_locataire(p_id_locataire IN SAE_LOCATAIRE.identifiant_locataire%type);
    
    PROCEDURE update_locataire(
        p_identifiant_locataire IN SAE_LOCATAIRE.identifiant_locataire%type,
        p_nom_locataire IN SAE_LOCATAIRE.nom_locataire%type,
        p_prenom_locataire IN SAE_LOCATAIRE.prenom_locataire%type,
        p_email_locataire IN SAE_LOCATAIRE.email_locataire%type,
        p_telephone_locataire IN SAE_LOCATAIRE.telephone_locataire%type,
        p_date_naissance IN SAE_LOCATAIRE.date_naissance%type,
        p_lieu_de_naissance IN SAE_LOCATAIRE.lieu_de_naissance%type,
        p_acte_de_caution IN SAE_LOCATAIRE.acte_de_caution%type,
        p_id_sae_adresse IN SAE_LOCATAIRE.id_sae_adresse%type,
        p_contrats in contract_array);
        
END pkg_locataire;
/

CREATE OR REPLACE PACKAGE BODY pkg_locataire AS

    -- update_contrats_bail pour update les contrats du "point de vue" du bail ?
    PROCEDURE update_contrats_loc(p_contrats in contract_array,
                                  p_identifiant_locataire IN SAE_LOCATAIRE.identifiant_locataire%type)
    AS
    
    Begin

        -- Suppression des contrats qui n'existent plus
        DELETE FROM sae_contracter
        WHERE identifiant_locataire = p_identifiant_locataire
          AND Id_bail NOT IN (
              SELECT c.Id_bail 
              FROM TABLE(p_contrats) c
          );
          
        -- Mise à jour ou insertion des contrats (via MERGE)
        MERGE INTO sae_contracter target --Table destination
        USING (SELECT * FROM TABLE(p_contrats)) source --Table source pour la maj
        
        ON ( 
            target.identifiant_locataire = p_identifiant_locataire
            AND target.Id_bail = source.Id_bail ) -- Les conditions
        
        WHEN MATCHED THEN
            UPDATE SET
                date_de_sortie = source.date_de_sortie,
                date_d_entree = source.date_d_entree,
                part_de_loyer = source.part_de_loyer
        
        WHEN NOT MATCHED THEN
            INSERT (
                identifiant_locataire, Id_bail, date_de_sortie, date_d_entree, part_de_loyer)
            VALUES (
                p_identifiant_locataire, source.Id_bail, source.date_de_sortie, 
                source.date_d_entree, source.part_de_loyer);
    
    END update_contrats_loc;

    PROCEDURE update_locataire(
        p_identifiant_locataire IN SAE_LOCATAIRE.identifiant_locataire%type,
        p_nom_locataire IN SAE_LOCATAIRE.nom_locataire%type,
        p_prenom_locataire IN SAE_LOCATAIRE.prenom_locataire%type,
        p_email_locataire IN SAE_LOCATAIRE.email_locataire%type,
        p_telephone_locataire IN SAE_LOCATAIRE.telephone_locataire%type,
        p_date_naissance IN SAE_LOCATAIRE.date_naissance%type,
        p_lieu_de_naissance IN SAE_LOCATAIRE.lieu_de_naissance%type,
        p_acte_de_caution IN SAE_LOCATAIRE.acte_de_caution%type,
        p_id_sae_adresse IN SAE_LOCATAIRE.id_sae_adresse%type,
        p_contrats in contract_array) 
    AS
    BEGIN
        -- Mise à jour des informations du locataire dans la table SAE_Locataire
        UPDATE SAE_Locataire
        SET 
            Nom_locataire = p_nom_locataire,
            Prenom_locataire = p_prenom_locataire,
            email_locataire = p_email_locataire,
            telephone_locataire = p_telephone_locataire,
            date_naissance = p_date_naissance,
            Lieu_de_naissance = p_lieu_de_naissance,
            Acte_de_caution = p_acte_de_caution,
            Id_SAE_Adresse = p_id_sae_adresse
        WHERE identifiant_locataire = p_identifiant_locataire;
        
        update_contrats_loc(p_contrats, p_identifiant_locataire);
    
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20003,
                'Le Locataire '|| p_identifiant_locataire || ' n''existe pas !');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20001, 
                'Erreur lors de la suppression du locataire : ' || SQLERRM);
    END update_locataire;

    PROCEDURE supprimer_locataire(p_id_locataire IN SAE_LOCATAIRE.identifiant_locataire%type) IS
        v_nb_documents NUMBER;
    BEGIN
        -- Vérifier l'existence de documents comptables liés au locataire
        SELECT COUNT(*)
        INTO v_nb_documents
        FROM sae_document_comptable
        WHERE identifiant_locataire = p_id_locataire;

        -- Si des documents existent, lever une erreur
        IF v_nb_documents > 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 
                'Impossible de supprimer le locataire ' || p_id_locataire ||
                '. Des paiements ou documents comptables sont liés à ce locataire.');
        END IF;

        -- Suppression des relations dans la table sae_etre_lie
        DELETE FROM sae_etre_lie
        WHERE identifiant_locataire = p_id_locataire
           OR identifiant_locataire_1 = p_id_locataire;

        -- Suppression des relations dans la table sae_contracter
        DELETE FROM sae_contracter
        WHERE identifiant_locataire = p_id_locataire;

        -- Suppression du locataire dans la table SAE_Locataire
        DELETE FROM sae_locataire
        WHERE identifiant_locataire = p_id_locataire;

        -- Validation de la transaction
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20001, 
                'Erreur lors de la suppression du locataire : ' || SQLERRM);
    END supprimer_locataire;

    
END pkg_locataire;
/
