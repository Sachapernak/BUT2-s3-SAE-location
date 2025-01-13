-- Fonction pour calculer la différence entre les mois
CREATE OR REPLACE FUNCTION calculer_difference_mois(
    p_identifiant_locataire IN VARCHAR2
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


-- Fonction pour calculer le coût total avant division
CREATE OR REPLACE FUNCTION cout_avant_div(
    p_identifiant_locataire IN VARCHAR2
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


-- Fonction pour calculer le coût total après division
CREATE OR REPLACE FUNCTION cout_apres_div(
    p_identifiant_locataire IN VARCHAR2
) RETURN NUMBER AS
    v_diff_mois NUMBER;
    v_cout_avant_div NUMBER;
    v_cout_total_apres_division NUMBER;
BEGIN
    -- Appeler les fonctions pour récupérer les données nécessaires
    v_diff_mois := calculer_difference_mois(p_identifiant_locataire);
    v_cout_avant_div := cout_avant_div(p_identifiant_locataire);

    -- Calculer le coût total après division
    IF v_diff_mois = 0 THEN
        v_diff_mois := 1; -- Éviter division par zéro
    END IF;

    v_cout_total_apres_division := ROUND(v_cout_avant_div / v_diff_mois, 2);

    RETURN v_cout_total_apres_division;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;


--SELECT calculer_difference_mois('MATLOC001') AS difference_mois FROM DUAL;
--SELECT cout_avant_div('MATLOC001') AS cout_total_avant FROM DUAL;
--SELECT cout_apres_div('MATLOC001') AS cout_total_apres FROM DUAL;

