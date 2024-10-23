
-- desactiver les FK
BEGIN
    FOR rec IN (SELECT uc.constraint_name, ucc.table_name
                FROM user_constraints uc
                JOIN user_cons_columns ucc
                ON uc.constraint_name = ucc.constraint_name
                WHERE uc.constraint_type = 'R'  -- 'R' pour FOREIGN KEY
                AND uc.constraint_name LIKE 'FK_SAE%')
    LOOP
        EXECUTE IMMEDIATE 'ALTER TABLE ' || rec.table_name || ' DISABLE CONSTRAINT ' || rec.constraint_name;
    END LOOP;
END;
/


-- Vider les tables avec TRUNCATE
TRUNCATE TABLE SAE_comprendre_cf;
TRUNCATE TABLE SAE_comprendre_charge_variable;
TRUNCATE TABLE SAE_mettre_en_location;
TRUNCATE TABLE SAE_contracter;
TRUNCATE TABLE SAE_appartenir;
TRUNCATE TABLE SAE_Cautionner;
TRUNCATE TABLE SAE_etre_lie;
TRUNCATE TABLE SAE_ICC;
TRUNCATE TABLE SAE_diagnostiques;
TRUNCATE TABLE SAE_Regularisation;
TRUNCATE TABLE SAE_Document;

TRUNCATE TABLE SAE_Bail;
TRUNCATE TABLE SAE_Cautionnaire;
TRUNCATE TABLE SAE_batiment;
TRUNCATE TABLE SAE_Bien_locatif;
TRUNCATE TABLE SAE_LOYER;
TRUNCATE TABLE SAE_document_comptable;
TRUNCATE TABLE SAE_entreprise;
TRUNCATE TABLE SAE_assurance;
TRUNCATE TABLE SAE_Locataire;

TRUNCATE TABLE SAE_Charge_cf;
TRUNCATE TABLE SAE_charge_index;
/

BEGIN
    FOR rec IN (SELECT uc.constraint_name, ucc.table_name
                FROM user_constraints uc
                JOIN user_cons_columns ucc
                ON uc.constraint_name = ucc.constraint_name
                WHERE uc.constraint_type = 'R'  -- 'R' pour FOREIGN KEY
                AND uc.constraint_name LIKE 'FK_SAE%')
    LOOP
        EXECUTE IMMEDIATE 'ALTER TABLE ' || rec.table_name || ' ENABLE CONSTRAINT ' || rec.constraint_name;
    END LOOP;
END;
/

SELECT table_name,
          CASE constraint_type
       WHEN 'C' THEN 'Check Constraint'
       WHEN 'P' THEN 'Primary Key'
       WHEN 'U' THEN 'Unique Constraint'
       WHEN 'R' THEN 'Foreign Key'
       WHEN 'V' THEN 'View Check Option'
       ELSE 'Other'
    END AS constraint_type_name,
    constraint_name,
    status AS is_active

FROM user_constraints
WHERE table_name LIKE '%SAE%'
ORDER BY table_name, constraint_type, constraint_name;
/


--Supprimer table
DROP TABLE SAE_comprendre_cf CASCADE CONSTRAINTS;
DROP TABLE SAE_comprendre_charge_variable CASCADE CONSTRAINTS;
DROP TABLE SAE_mettre_en_location CASCADE CONSTRAINTS;
DROP TABLE SAE_contracter CASCADE CONSTRAINTS;
DROP TABLE SAE_appartenir CASCADE CONSTRAINTS;
DROP TABLE SAE_Cautionner CASCADE CONSTRAINTS;
DROP TABLE SAE_etre_lie CASCADE CONSTRAINTS;
DROP TABLE SAE_Charge_cf CASCADE CONSTRAINTS;
DROP TABLE SAE_charge_index CASCADE CONSTRAINTS;
DROP TABLE SAE_document_comptable CASCADE CONSTRAINTS;
DROP TABLE SAE_ICC CASCADE CONSTRAINTS;
DROP TABLE SAE_diagnostiques CASCADE CONSTRAINTS;
DROP TABLE SAE_Regularisation CASCADE CONSTRAINTS;
DROP TABLE SAE_Document CASCADE CONSTRAINTS;
DROP TABLE SAE_assurance CASCADE CONSTRAINTS;
DROP TABLE SAE_entreprise CASCADE CONSTRAINTS;
DROP TABLE SAE_Bail CASCADE CONSTRAINTS;
DROP TABLE SAE_Cautionnaire CASCADE CONSTRAINTS;
DROP TABLE SAE_batiment CASCADE CONSTRAINTS;
DROP TABLE SAE_Bien_locatif CASCADE CONSTRAINTS;
DROP TABLE SAE_Locataire CASCADE CONSTRAINTS;
DROP TABLE SAE_Loyer CASCADE CONSTRAINTS;