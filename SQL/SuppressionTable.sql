-- Désactiver les FK
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

-- Vider les tables avec DELETE pour permettre les relations
BEGIN
    FOR rec IN (SELECT table_name FROM user_tables WHERE table_name LIKE 'SAE_%')
    LOOP
        EXECUTE IMMEDIATE 'DELETE FROM ' || rec.table_name;
    END LOOP;
END;
/

-- Réactiver les FK
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

-- Afficher les contraintes des tables
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

-- Supprimer toutes les tables
BEGIN
    FOR rec IN (SELECT table_name FROM user_tables WHERE table_name LIKE 'SAE_%')
    LOOP
        EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS';
    END LOOP;
END;
/