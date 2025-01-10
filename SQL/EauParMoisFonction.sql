create or replace FUNCTION calculer_cout_total_locataire(
    p_identifiant_locataire IN VARCHAR2
) RETURN NUMBER AS
    v_cout_total NUMBER; -- Variable pour stocker le résultat final
BEGIN
    -- Logique pour calculer le coût total
    WITH DerniersReleves AS (
        SELECT 
            l.identifiant_locataire,
            ci.Date_de_releve,
            ci.Valeur_compteur,
            ci.Cout_variable_unitaire,
            ci.Cout_fixe,
            ROW_NUMBER() OVER (PARTITION BY l.identifiant_locataire ORDER BY ci.Date_de_releve DESC) AS rang
        FROM 
            sae_charge_index ci
        JOIN 
            SAE_document_comptable dc 
            ON ci.numero_document = dc.numero_document
        JOIN 
            SAE_Locataire l 
            ON dc.identifiant_locataire = l.identifiant_locataire
        WHERE 
            l.identifiant_locataire = p_identifiant_locataire -- Filtrer sur le locataire fourni
    )
    SELECT 
        ROUND(
            ((r1.Valeur_compteur - r2.Valeur_compteur) * r1.Cout_variable_unitaire + r1.Cout_fixe) /
            MONTHS_BETWEEN(r1.Date_de_releve, r2.Date_de_releve),
            2
        )
    INTO 
        v_cout_total
    FROM 
        DerniersReleves r1
    JOIN 
        DerniersReleves r2 
        ON r1.identifiant_locataire = r2.identifiant_locataire
    WHERE 
        r1.rang = 1 -- Dernier relevé
        AND r2.rang = 2; -- Avant-dernier relevé

    -- Retourner le coût total calculé
    RETURN v_cout_total;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Si aucune donnée trouvée, retourner 0
        RETURN 0;
    WHEN OTHERS THEN
        -- Gestion des autres erreurs
        RETURN NULL; -- Retourne NULL pour toute autre erreur
END;


-- SELECT calculer_cout_total_locataire('MATLOC001') AS cout_total FROM DUAL;
