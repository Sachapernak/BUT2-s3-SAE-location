


-- Calculer la somme des provisions pour charges d'un bail, entre deux dates
CREATE OR REPLACE PROCEDURE calculer_somme_provision(
    p_id_bail  IN SAE_Provision_charge.Id_bail%TYPE,
    p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin IN DATE DEFAULT NULL,  -- Date de fin optionnelle
    p_total OUT NUMBER,
    p_calc OUT VARCHAR
) AS
    v_total_somme   NUMBER := 0;
    v_calcul        VARCHAR(512) := '';
      
BEGIN

    IF (p_date_debut is null) then
        p_date_debut := TO_DATE('1900-01-01', 'yyyy-MM-dd');
    END IF;
    
    FOR vProvision in (SELECT 
                            id_bail,
                            date_changement,
                            provision_pour_charge, 
                            TRUNC(ABS(MONTHS_BETWEEN(
                                NVL( LEAD(date_changement) OVER (PARTITION BY id_bail ORDER BY date_changement),
                                     NVL(p_date_fin, SYSDATE)),
                                date_changement ))) AS "DIFFERENCE_EN_MOIS"
                        FROM sae_provision_charge
                        WHERE ID_BAIL = p_id_bail
                        AND date_changement > p_date_debut)  LOOP           
        v_total_somme := v_total_somme 
                         + (vProvision.provision_pour_charge * vProvision.difference_en_mois);
        v_calcul := ' ' || v_calcul || vProvision.provision_pour_charge || ' * ' || vProvision.difference_en_mois || ' +';
    END LOOP;
    v_calcul := trim(trailing '+' from v_calcul);
    v_calcul := v_calcul || ' = ' || v_total_somme;
    
    p_total := v_total_somme;
    p_calc := v_calcul;
                         
END;
/




-- TEST
SET SERVEROUTPUT ON;
DECLARE
    v_total NUMBER;
    v_calc  VARCHAR2(512);
BEGIN
    -- Appel de la procédure avec le bail BAI01 et date de fin NULL
    pkg_solde_compte.calculer_somme_provision(
        p_id_bail  => 'BAI01',
        p_date_fin => NULL,
        p_total    => v_total,
        p_calc     => v_calc
    );
    
    -- Affichage des résultats
    DBMS_OUTPUT.PUT_LINE('Somme totale : ' || v_total);
    DBMS_OUTPUT.PUT_LINE('Détail du calcul : ' || v_calc);
END;
/

    




