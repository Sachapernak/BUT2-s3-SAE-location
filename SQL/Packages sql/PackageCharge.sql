CREATE OR REPLACE PACKAGE pkg_charge_calculations AS

    -- Fonction pour calculer la différence en mois
    FUNCTION diff_mois(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER;

    -- Fonction pour calculer le coût total avant division
    FUNCTION cout_avant_division(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER;

    -- Fonction pour calculer le coût total après division
    FUNCTION cout_apres_division(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER;

    -- Fonction pour calculer le coût total pour un locataire
    FUNCTION total_cout_locataire(
        p_identifiant_locataire IN VARCHAR2
    ) RETURN NUMBER;

END pkg_charge_calculations;
/

CREATE OR REPLACE PACKAGE BODY pkg_charge_calculations AS

    -- Fonction pour calculer la différence en mois
    FUNCTION diff_mois(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER AS
        v_diff_mois NUMBER;
        v_date_plus_recent DATE;
        v_date_plus_ancien DATE;
    BEGIN
        WITH DerniersReleves AS (
            SELECT 
                dc.identifiant_locataire,
                ci.Date_de_releve,
                ci.Type,
                ROW_NUMBER() OVER (PARTITION BY dc.identifiant_locataire, ci.Type ORDER BY ci.Date_de_releve DESC) AS rang
            FROM 
                sae_charge_index ci
            JOIN 
                SAE_document_comptable dc 
                ON ci.numero_document = dc.numero_document
            WHERE 
                dc.identifiant_locataire = p_identifiant_locataire
                AND ci.Type = p_type_releve
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
            r1.rang = 1
            AND r2.rang = 2;

        v_diff_mois := MONTHS_BETWEEN(v_date_plus_recent, v_date_plus_ancien);
        IF v_diff_mois = 0 THEN
            v_diff_mois := 1;
        END IF;

        RETURN v_diff_mois;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN NULL;
    END;

    -- Fonction pour calculer le coût total avant division
    FUNCTION cout_avant_division(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER AS
        v_cout_total NUMBER;
        v_valeur_plus_recent NUMBER;
        v_valeur_plus_ancien NUMBER;
        v_cout_variable_unitaire NUMBER;
        v_cout_fixe NUMBER;
    BEGIN
        WITH DerniersReleves AS (
            SELECT 
                dc.identifiant_locataire,
                ci.Valeur_compteur,
                ci.Cout_variable_unitaire,
                ci.Cout_fixe,
                ci.Type,
                ROW_NUMBER() OVER (PARTITION BY dc.identifiant_locataire, ci.Type ORDER BY ci.Date_de_releve DESC) AS rang
            FROM 
                sae_charge_index ci
            JOIN 
                SAE_document_comptable dc 
                ON ci.numero_document = dc.numero_document
            WHERE 
                dc.identifiant_locataire = p_identifiant_locataire
                AND ci.Type = p_type_releve
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
            r1.rang = 1
            AND r2.rang = 2;

        v_cout_total := ((v_valeur_plus_recent - v_valeur_plus_ancien) * v_cout_variable_unitaire) + v_cout_fixe;

        RETURN v_cout_total;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN NULL;
    END;

    -- Fonction pour calculer le coût total après division
    FUNCTION cout_apres_division(
        p_identifiant_locataire IN VARCHAR2,
        p_type_releve IN VARCHAR2
    ) RETURN NUMBER AS
        v_diff_mois NUMBER;
        v_cout_total_avant_division NUMBER;
    BEGIN
        v_diff_mois := diff_mois(p_identifiant_locataire, p_type_releve);
        v_cout_total_avant_division := cout_avant_division(p_identifiant_locataire, p_type_releve);

        IF v_diff_mois = 0 THEN
            v_diff_mois := 1;
        END IF;

        RETURN ROUND(v_cout_total_avant_division / v_diff_mois, 2);
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN NULL;
    END;

    -- Fonction pour calculer le coût total pour un locataire
    FUNCTION total_cout_locataire(
        p_identifiant_locataire IN VARCHAR2
    ) RETURN NUMBER AS
        v_cout_eau NUMBER;
        v_cout_electricite NUMBER;
        v_charges_cf_total NUMBER := 0;
        v_total_cout NUMBER;
        v_diff_mois NUMBER;
    BEGIN
        -- Calculer le coût après division pour l'eau
        v_cout_eau := cout_avant_division(p_identifiant_locataire, 'Eau');

        -- Calculer le coût après division pour l'électricité
        v_cout_electricite := cout_avant_division(p_identifiant_locataire, 'Electricité');

        -- Calculer la différence en mois pour les charges fixes (basé sur l'eau)
        v_diff_mois := diff_mois(p_identifiant_locataire, 'Eau');
        IF v_diff_mois = 0 THEN
            v_diff_mois := 1; -- Éviter division par zéro
        END IF;

        -- Récupérer toutes les charges fixes pour le locataire et les diviser par la période calculée
        SELECT NVL(SUM(chf.montant / v_diff_mois), 0)
        INTO v_charges_cf_total
        FROM SAE_Charge_cf chf
        JOIN SAE_document_comptable dc
        ON chf.numero_document = dc.numero_document
        WHERE dc.identifiant_locataire = p_identifiant_locataire;

        -- Calculer le coût total
        v_total_cout := v_cout_eau + v_cout_electricite + v_charges_cf_total;

        RETURN v_total_cout;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN NULL;
    END;

END pkg_charge_calculations;
/
