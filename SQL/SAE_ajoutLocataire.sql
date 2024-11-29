create or replace procedure SAE_ajoutcontracter(
    P_ID_Locataire sae_locataire.identifiant_locataire%type, 
    p_id_bail sae_bail.id_bail%type, 
    p_date_sortie sae_contracter.date_de_sortie%type, 
    p_date_entree sae_contracter.date_d_entree%type, 
    p_part_loyer sae_contracter.part_de_loyer%type)
as
begin
    if (p_id_locataire is null or p_id_bail is null) then
        raise_application_error(-20011, 'L''identifiant du locataire et du bail doivent etre renseignes');
    end if;
    if (p_date_sortie < p_date_entree) then
        raise_application_error(-20012, 'La date d''entree ne peut pas etre superieur a celui de sortie');
    end if;

    insert into sae_contracter values (P_ID_Locataire, p_id_bail, p_date_sortie, p_date_entree, p_part_loyer);
        exception 
            when dup_val_on_index then raise_application_error(-20016, 'Le bail est déjà dans la base de données');

end;
/

create or replace procedure SAE_ajoutBail(P_ID_BAIL varchar2, P_PROV_POUR_CHARGE number, P_DATE_DEB_BAIL date, P_DATE_FIN_BAIL date)
as
begin

    if (p_date_deb_bail > p_date_fin_bail) then
        raise_application_error(-20018,'La date date de debut du bail ne peut pas etre posterieur a sa fin');
    end if;
    insert into SAE_BAIL values (P_ID_BAIL, P_PROV_POUR_CHARGE, P_DATE_DEB_BAIL, P_DATE_FIN_BAIL);
    exception 
        when dup_val_on_index then raise_application_error(-20010, 'Le bail est dÃ©jÃ  dans la base de donnÃ©es');
end;
/



create or replace procedure SAE_ajoutLocataire(
    P_ID_LOC sae_locataire.identifiant_locataire%TYPE, 
    P_NOM sae_locataire.nom_locataire%TYPE, 
    P_PRENOM sae_locataire.prenom_locataire%TYPE, 
    P_EMAIL sae_locataire.email_locataire%TYPE,
    P_TEL1 sae_locataire.telephone_1_locataire%TYPE,
    P_TEL2 sae_locataire.telephone_2_locataire%TYPE,
    P_CP sae_locataire.code_postal_locataire%TYPE,
    P_DDN sae_locataire.date_naissance%TYPE,
    P_ACTECAUTION  sae_locataire.acte_de_caution%TYPE,
    P_LIEUNAISSANCE sae_locataire.lieu_de_naissance%TYPE,
    P_ADR_OPT sae_locataire.adresse_optionnelle%TYPE, 
    P_CP_OPT sae_locataire.code_postal_optionnel%TYPE,
    P_VILLE_OPT sae_locataire.ville_optionnel%TYPE,
    P_ID_BAIL sae_bail.id_bail%TYPE, 
    P_PROV_POUR_CHARGE sae_bail.provision_pour_charge%TYPE, 
    P_DATE_DEB_BAIL sae_bail.date_de_debut%TYPE, 
    P_DATE_FIN_BAIL sae_bail.date_de_fin%TYPE,
    P_DATE_entre_contrat sae_contracter.date_d_entree%TYPE,
    p_date_sortie_contrat sae_contracter.date_de_sortie%TYPE,
    p_part_loyer_contrat sae_contracter.part_de_loyer%TYPE)
as
begin

    if (p_id_loc is null or p_nom is null or p_cp is null or p_ddn is null) then
        raise_application_error(-20017, 'L''identifiant du locataire, son nom, son code postal et sa date de naissance doivent etre renseignes');
    end if;

    -- Insert into sae_locataire
    begin
        insert into SAE_LOCATAIRE 
        values (
            P_ID_LOC, P_NOM, P_PRENOM, P_EMAIL, P_TEL1, null,
            P_CP, P_DDN, P_ACTECAUTION, P_LIEUNAISSANCE, null, null, null
        );
    exception
        when dup_val_on_index then
            raise_application_error(-20013, 'Le locataire est deja dans la base de donn?es');
        when others then
            raise_application_error(-20014, 'Erreur lors de l''ajout du locataire');
    end;

    -- Insert into sae_bail (assuming SAE_ajoutBail is properly defined)
    begin
        SAE_ajoutBail(P_ID_BAIL, P_PROV_POUR_CHARGE, P_DATE_DEB_BAIL, P_DATE_FIN_BAIL);
    exception
        when others then
            raise_application_error(-20015, 'Erreur lors de l''ajout du bail');
    end;

    begin
        SAE_ajoutcontracter(p_id_loc, p_id_bail,p_date_sortie_contrat,P_DATE_entre_contrat,p_part_loyer_contrat);
    exception
        when others then
            raise_application_error(-20018, 'Erreur lors de l''ajout du contrat');
    end; 

end;
/




-- Test 1: Ajout d'un locataire avec un bail
call SAE_ajoutLocataire('LOC747', 'Dupont', 'Jean', 'jdm', '0678',null, 75001,TO_DATE('1980-05-20', 'YYYY-MM-DD'),'jdaoiejdiaeojdoj','Paris',null,null,null,'B001', 100.00, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'),TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'),3.22);


-- Test 2: Ajout d'un autre locataire avec un bail

call SAE_ajoutLocataire('LOC002', 'Martin', 'Paul', 'paul.martin@example.com', '0623456789', '0654321987', 69001, TO_DATE('1985-03-15', 'YYYY-MM-DD'),'Caution 67890', 'Lyon', NULL, NULL, NULL,'B002', 200.00, TO_DATE('2024-02-01', 'YYYY-MM-DD'), TO_DATE('2026-02-01', 'YYYY-MM-DD'),TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'),3.22);
/
-- V?rification des insertions dans la table SAE_LOCATAIRE
SELECT * FROM sae_locataire WHERE identifiant_locataire = 'LOC747';
SELECT * FROM sae_locataire WHERE identifiant_locataire = 'LOC002';

-- V?rification des insertions dans la table SAE_BAIL
SELECT * FROM sae_bail WHERE id_bail = 'B001';
SELECT * FROM sae_bail WHERE id_bail = 'B002';

-- V?rification des insertions dans la table SAE_Contracter
SELECT * FROM sae_contracter WHERE id_bail = 'B001' and identifiant_locataire = 'LOC747';
SELECT * FROM sae_contracter WHERE id_bail = 'B002' and identifiant_locataire = 'LOC747';

-- Annuler les transactions pour ?viter d'alt?rer la base de donn?es
ROLLBACK;

