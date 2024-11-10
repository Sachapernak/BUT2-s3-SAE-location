

select * from sae_bail;
call DBMS_OUTPUT.PUT_LINE('rien n''est cense etre affiche');

--------- Test 1 CREATE non-existant------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1 create nouveau bail, est cense passer');
    SAE_DAO_BAIL.SAE_CREATE('bail_1', TO_DATE('15-06-2023', 'DD-MM-YYYY'), TO_DATE('15-06-2025', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Test 1: Insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/
--------- Test 1(2) CREATE non-existant------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1(2) create nouveau bail, est cense passer');
    SAE_DAO_BAIL.SAE_CREATE('bail_2',TO_DATE('05-02-2019','DD-MM-YYYY'), TO_DATE('24-08-2022','DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Test 1(2): Insertion reussie.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1: Erreur - ' || SQLERRM);
END;
/
--------- Test 2 CREATE deja existant------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 2 create un bail deja existant, une erreur est attendue');
    SAE_DAO_BAIL.SAE_CREATE('bail_1', TO_DATE('15-06-2023', 'DD-MM-YYYY'), TO_DATE('15-06-2025', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Test 2: Ce contrat a deja etait place dans la base de donnees. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

--------- Test 3 CREATE identifiant null------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 3 create un bail avec identifiant null, une erreur est attendue');
    SAE_DAO_BAIL.SAE_CREATE(null, TO_DATE('15-06-2023', 'DD-MM-YYYY'), TO_DATE('15-06-2025', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Test 3: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

select * from sae_bail;
call DBMS_OUTPUT.PUT_LINE('Deux lignes sont censees etre affichees');



--------- Test 4 UPDATE nouvelles dates ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 4 update modification de table, est cense passer');
    SAE_DAO_BAIL.SAE_UPDATE('bail_1', TO_DATE('10-10-2022', 'DD-MM-YYYY'), TO_DATE('10-10-2024', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Test 4 : Mise a jour complete reussie');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4 : Erreur - ' || SQLERRM);
END;
/

--------- Test 5 UPDATE bail inexistant ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 5 update bail inexistant, une erreur est attendue');
    SAE_DAO_BAIL.SAE_UPDATE('bail_inexistant', TO_DATE('01-01-2023', 'DD-MM-YYYY'), TO_DATE('01-01-2025', 'DD-MM-YYYY'));   
    DBMS_OUTPUT.PUT_LINE('Test 5: L''identifiant n''existe pas. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 5: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/

--------- Test 6 UPDATE bail null ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 6 update bail null, une erreur est attendue');
    SAE_DAO_BAIL.SAE_UPDATE(null, TO_DATE('01-01-2023', 'DD-MM-YYYY'), TO_DATE('01-01-2025', 'DD-MM-YYYY'));
     DBMS_OUTPUT.PUT_LINE('Test 6: L''identifiant est null. Il doit y avoir une erreur normalement.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 6: Erreur obtenu, le test est réussi - ' || SQLERRM);
END;
/
select * from sae_bail;

call DBMS_OUTPUT.PUT_LINE('Deux lignes sont censees etre affichees');


-- Delete
begin 
    SAE_DAO_BAIL.SAE_DELETE('bail_1');

end;
/
select * from sae_bail;
call DBMS_OUTPUT.PUT_LINE('Test Delete : Ceci n''est cense afficher qu''une ligne');



Rollback;
