
-- Create

SELECT * FROM SAE_LOCATAIRE;
call DBMS_OUTPUT.PUT_LINE('rien n''est cense etre affiche');

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion doit reussir.');

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
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 2: L''identifiant est null. Il doit y avoir une erreur normalement.');

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
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 3: Nouvelle insertion doit reussir.');

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
    DBMS_OUTPUT.PUT_LINE('Insertion reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur - ' || SQLERRM);
END;
/

SELECT * FROM SAE_LOCATAIRE;
call DBMS_OUTPUT.PUT_LINE('Deux lignes sont censees etre affichees');

-- Update


BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 4: Mise a jour complete doit reussir pour LOC006.');

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
    DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4: Erreur - ' || SQLERRM);
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 5: Veuillez renseigner l''identifiant, la modification n''est pas cense passer');

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
    DBMS_OUTPUT.PUT_LINE('Modification reussie.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 5: Le test est passe - ' || SQLERRM);
END;
/
SELECT * FROM SAE_LOCATAIRE;
call DBMS_OUTPUT.PUT_LINE('Deux lignes dont une modifie sont censees etre affichees');


-- Delete
begin
    SAE_DAO_LOCATAIRE.SAE_DELETE('LOC006');

end;
/
SELECT * FROM SAE_LOCATAIRE;

call DBMS_OUTPUT.PUT_LINE('Test Delete : Ceci n''est cense afficher qu''une ligne');

ROLLBACK;