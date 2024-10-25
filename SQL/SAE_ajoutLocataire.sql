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
            raise_application_error(-20013, 'Le locataire est d?j? dans la base de donn?es');
        when others then
            raise_application_error(-20014, 'Erreur lors de l''ajout du locataire');
    end;

    -- Insert into sae_bail (assuming SAE_ajoutBail is properly defined)
    begin
        SAE_ajoutBail(P_ID_BAIL, P_PROV_POUR_CHARGE, P_DATE_DEB_BAIL, P_DATE_FIN_BAIL);
    exception
        when others then
            raise_application_error(-20015, 'Erreur lors de l?ajout du bail');
    end;

end;
/




-- Test 1: Ajout d'un locataire avec un bail
call SAE_ajoutLocataire('LOC747', 'Dupont', 'Jean', 'jdm', '0678',null, 75001,TO_DATE('1980-05-20', 'YYYY-MM-DD'),'jdaoiejdiaeojdoj','Paris',null,null,null,'B001', 100.00, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));


-- Test 2: Ajout d'un autre locataire avec un bail

call SAE_ajoutLocataire('LOC002', 'Martin', 'Paul', 'paul.martin@example.com', '0623456789', '0654321987', 69001, TO_DATE('1985-03-15', 'YYYY-MM-DD'),'Caution 67890', 'Lyon', NULL, NULL, NULL,'B002', 200.00, TO_DATE('2024-02-01', 'YYYY-MM-DD'), TO_DATE('2026-02-01', 'YYYY-MM-DD'));
/
-- V?rification des insertions dans la table SAE_LOCATAIRE
SELECT * FROM sae_locataire WHERE identifiant_locataire = 'LOC747';
SELECT * FROM sae_locataire WHERE identifiant_locataire = 'LOC002';

-- V?rification des insertions dans la table SAE_BAIL
SELECT * FROM sae_bail WHERE id_bail = 'B001';
SELECT * FROM sae_bail WHERE id_bail = 'B002';

-- Annuler les transactions pour ?viter d'alt?rer la base de donn?es
ROLLBACK;
