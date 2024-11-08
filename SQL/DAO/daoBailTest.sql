set serveroutput on 

insert into SAE_BAIL values('bail_1', TO_DATE('01-02-2022','DD-MM-YYYY'),TO_DATE('01-02-2024','DD-MM-YYYY'));
insert into SAE_BAIL values('bail_2', TO_DATE('05-02-2019','DD-MM-YYYY'),TO_DATE('24-08-2022','DD-MM-YYYY'));
insert into SAE_BAIL values('bail_3', TO_DATE('20-05-2023','DD-MM-YYYY'),TO_DATE('21-05-2028','DD-MM-YYYY'));


--------- Test 1 CREATE non-existant------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 1 create nouveau bail');
    SAE_DAO_BAIL.SAE_CREATE('bail_4', TO_DATE('15-06-2023', 'DD-MM-YYYY'), TO_DATE('15-06-2025', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Bail correctement ajouté');
END;
/
--------- Test 2 CREATE deja existant------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 2 create un bail deja existant');
    SAE_DAO_BAIL.SAE_CREATE('bail_4', TO_DATE('15-06-2023', 'DD-MM-YYYY'), TO_DATE('15-06-2025', 'DD-MM-YYYY'));
END;
/

--------- Test 3 UPDATE nouvelles dates ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 3 update modification de table');
    SAE_DAO_BAIL.SAE_UPDATE('bail_1', TO_DATE('10-10-2022', 'DD-MM-YYYY'), TO_DATE('10-10-2024', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Mise à jour réussie pour bail_1.');
END;
/

--------- Test 4 UPDATE bail inexistant ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 4 update bail inexistant');
    SAE_DAO_BAIL.SAE_UPDATE('bail_inexistant', TO_DATE('01-01-2023', 'DD-MM-YYYY'), TO_DATE('01-01-2025', 'DD-MM-YYYY'));   
END;
/

--------- Test 6 UPDATE bail null ------------
BEGIN
    DBMS_OUTPUT.PUT_LINE('Test 5 update bail null');
    SAE_DAO_BAIL.SAE_UPDATE(null, TO_DATE('01-01-2023', 'DD-MM-YYYY'), TO_DATE('01-01-2025', 'DD-MM-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Mise à jour échouée, bail_inexistant.');
END;
/

Rollback;
