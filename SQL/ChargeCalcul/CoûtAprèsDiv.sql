CREATE OR REPLACE FUNCTION cout_total_apres_division(
    p_identifiant_locataire IN VARCHAR2,
    p_type_releve IN VARCHAR2
) RETURN NUMBER AS
    v_diff_mois NUMBER;
    v_total_avant_division NUMBER;
    v_total_apres_division NUMBER;
BEGIN
    -- Appeler les fonctions pour récupérer les données nécessaires
    v_diff_mois := calculer_difference_mois(p_identifiant_locataire, p_type_releve);
    v_total_avant_division := cout_total_avant_division(p_identifiant_locataire, p_type_releve);

    -- Calculer le coût total après division
    IF v_diff_mois = 0 THEN
        v_diff_mois := 1; -- Éviter division par zéro
    END IF;

    v_total_apres_division := ROUND(v_total_avant_division / v_diff_mois, 2);

    RETURN v_total_apres_division;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;
