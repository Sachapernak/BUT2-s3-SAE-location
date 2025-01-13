CREATE OR REPLACE FUNCTION calculer_difference_mois(
    p_identifiant_locataire IN VARCHAR2,
    p_type_releve IN VARCHAR2
) RETURN NUMBER AS
    v_diff_mois NUMBER;
    v_date_plus_recent DATE;
    v_date_plus_ancien DATE;
BEGIN
    -- Récupérer les deux dernières dates
    WITH DerniersReleves AS (
        SELECT 
            l.identifiant_locataire,
            ci.Date_de_releve,
            ci.Type,
            ROW_NUMBER() OVER (PARTITION BY l.identifiant_locataire, ci.Type ORDER BY ci.Date_de_releve DESC) AS rang
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
        r1.Date_de_releve,
        r2.Date_de_releve
    INTO 
        v_date_plus_recent, v_date_plus_ancien
    FROM 
        DerniersReleves r1
    JOIN 
        DerniersReleves r2
        ON r1.identifiant_locataire = r2.identifiant_locataire
    WHERE 
        r1.rang = 1 -- Dernier relevé
        AND r2.rang = 2; -- Avant-dernier relevé

    -- Calculer la différence en mois
    v_diff_mois := MONTHS_BETWEEN(v_date_plus_recent, v_date_plus_ancien);
    IF v_diff_mois = 0 THEN
        v_diff_mois := 1; -- Éviter division par zéro
    END IF;

    RETURN v_diff_mois;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;
