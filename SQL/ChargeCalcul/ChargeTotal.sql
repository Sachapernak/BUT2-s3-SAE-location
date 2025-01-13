CREATE OR REPLACE FUNCTION total_cout_locataire(
    p_identifiant_locataire IN VARCHAR2
) RETURN NUMBER AS
    v_cout_eau NUMBER;
    v_cout_electricite NUMBER;
    v_charges_cf_total NUMBER := 0;
    v_total_cout NUMBER;
    v_diff_mois NUMBER;
BEGIN
    -- Calculer le co�t pour l'eau
    v_cout_eau := cout_total_avant_division(p_identifiant_locataire, 'Eau');

    -- Calculer le co�t pour l'�lectricit�
    v_cout_electricite := cout_total_avant_division(p_identifiant_locataire, 'Electricit�');

    -- Calculer la diff�rence en mois pour les charges_cf (Eau et Electricit�)
    v_diff_mois := calculer_difference_mois(p_identifiant_locataire, 'Eau');
    IF v_diff_mois = 0 THEN
        v_diff_mois := 1; -- �viter division par z�ro
    END IF;

    -- R�cup�rer les charges_cf et les diviser par la diff�rence en mois
    SELECT NVL(SUM(chf.montant / v_diff_mois), 0)
    INTO v_charges_cf_total
    FROM SAE_Charge_cf chf
    JOIN sae_charge_index ch_idx
    ON chf.numero_document = ch_idx.numero_document
    WHERE ch_idx.Type IN ('Eau', 'Electricit�')
      AND EXISTS (
          SELECT 1 FROM SAE_document_comptable dc
          WHERE dc.numero_document = chf.numero_document
            AND dc.identifiant_locataire = p_identifiant_locataire
      );

    -- Calculer le co�t total
    v_total_cout := v_cout_eau + v_cout_electricite + v_charges_cf_total;

    RETURN v_total_cout;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;
