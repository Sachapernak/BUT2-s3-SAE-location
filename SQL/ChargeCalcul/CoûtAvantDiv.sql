CREATE OR REPLACE FUNCTION cout_total_avant_division(
    p_identifiant_locataire IN VARCHAR2,
    p_type_releve IN VARCHAR2
) RETURN NUMBER AS
    v_cout_total NUMBER;
    v_valeur_plus_recent NUMBER;
    v_valeur_plus_ancien NUMBER;
    v_cout_variable_unitaire NUMBER;
    v_cout_fixe NUMBER;
BEGIN
    -- Récupérer les données nécessaires
    WITH DerniersReleves AS (
        SELECT 
            l.identifiant_locataire,
            ci.Valeur_compteur,
            ci.Cout_variable_unitaire,
            ci.Cout_fixe,
            ci.Type,
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
            l.identifiant_locataire = p_identifiant_locataire
            AND ci.Type = p_type_releve -- Filtrer par type
    )
    SELECT 
        r1.Valeur_compteur,
        r2.Valeur_compteur,
        r1.Cout_variable_unitaire,
        r1.Cout_fixe
    INTO 
        v_valeur_plus_recent, v_valeur_plus_ancien, v_cout_variable_unitaire, v_cout_fixe
    FROM 
        DerniersReleves r1
    JOIN 
        DerniersReleves r2
        ON r1.identifiant_locataire = r2.identifiant_locataire
    WHERE 
        r1.rang = 1 -- Dernier relevé
        AND r2.rang = 2; -- Avant-dernier relevé

    -- Calculer le coût total avant division
    v_cout_total := ((v_valeur_plus_recent - v_valeur_plus_ancien) * v_cout_variable_unitaire) + v_cout_fixe;

    RETURN v_cout_total;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;
