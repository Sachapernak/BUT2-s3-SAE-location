CREATE OR REPLACE PACKAGE pkg_icc AS
    PROCEDURE calculer_nouveau_loyer(
        p_logement      IN  VARCHAR2,
        p_nouveau_loyer OUT NUMBER
    );
END pkg_icc;
/

CREATE OR REPLACE PACKAGE BODY pkg_icc AS

    PROCEDURE calculer_nouveau_loyer(
        p_logement      IN  VARCHAR2,
        p_nouveau_loyer OUT NUMBER
    ) AS
        v_date_de_debut   DATE;
        v_dernier_loyer   NUMBER;
        v_base_loyer      NUMBER;
        v_loyer_actuel    NUMBER;
        v_annee           VARCHAR2(4);
        v_trimestre       VARCHAR2(1);
        v_indice_new      NUMBER;
        v_indice_old      NUMBER;
    BEGIN
        ----------------------------------------------------------------------
        -- 1) Dernière date de début de bail pour ce logement
        ----------------------------------------------------------------------
        BEGIN
            SELECT date_de_debut
              INTO v_date_de_debut
              FROM (
                   SELECT date_de_debut
                     FROM sae_bail
                    WHERE identifiant_logement = p_logement
                 ORDER BY date_de_debut DESC
              )
             WHERE ROWNUM = 1;
         EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20501, 'Pas de bail pour le logement sélectionné');
        END;

        ----------------------------------------------------------------------
        -- 2) Dernier loyer (si déjà un) sinon on utilisera le loyer de base
        ----------------------------------------------------------------------
        BEGIN
            SELECT montant_loyer
              INTO v_dernier_loyer
              FROM (
                   SELECT montant_loyer
                     FROM sae_loyer
                    WHERE identifiant_logement = p_logement
                 ORDER BY date_de_changement DESC
              )
             WHERE ROWNUM = 1;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                v_dernier_loyer := NULL;
        END;
        
        ----------------------------------------------------------------------
        -- 3) Récupération du loyer de base
        ----------------------------------------------------------------------
        BEGIN
            SELECT loyer_de_base
              INTO v_base_loyer
              FROM sae_bien_locatif
             WHERE identifiant_logement = p_logement;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20502, 'Logement introuvable');
        END;

        ----------------------------------------------------------------------
        -- 4) Loyer actuel = dernier loyer s'il existe, sinon loyer de base
        ----------------------------------------------------------------------
        IF v_dernier_loyer IS NOT NULL THEN
            v_loyer_actuel := v_dernier_loyer;
        ELSE
            v_loyer_actuel := v_base_loyer;
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('loyer actuel = ' || TO_CHAR(v_loyer_actuel));

        ----------------------------------------------------------------------
        -- 5) Déterminer l'année en cours et extraire le trimestre du bail
        ----------------------------------------------------------------------
        v_annee     := TO_CHAR(SYSDATE, 'YYYY');       -- Année actuelle
        v_trimestre := TO_CHAR(v_date_de_debut, 'Q');  -- Trimestre basé sur la date du bail
        
        DBMS_OUTPUT.PUT_LINE('Année = ' || v_annee || ', Trimestre = ' || v_trimestre);

        ----------------------------------------------------------------------
        -- 6) Récupérer l'indice ICC pour (v_annee, v_trimestre)
        ----------------------------------------------------------------------
        BEGIN
            SELECT indice
              INTO v_indice_new
              FROM sae_icc
             WHERE annee_icc     = v_annee
               AND trimestre_icc = v_trimestre;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20503, 'ICC Introuvable pour : ' || v_annee || ' T' || v_trimestre);
        END;

        ----------------------------------------------------------------------
        -- 7) Récupérer l'indice ICC de l'année précédente (v_annee - 1), même trimestre
        ----------------------------------------------------------------------
        BEGIN
            SELECT indice
              INTO v_indice_old
              FROM sae_icc
             WHERE annee_icc     = TO_CHAR(TO_NUMBER(v_annee) - 1)
               AND trimestre_icc = v_trimestre;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20503, 'ICC Introuvable pour : ' || TO_CHAR(TO_NUMBER(v_annee) - 1) 
                                        || ' T' || v_trimestre);
        END;

        ----------------------------------------------------------------------
        -- 8) Calcul du nouveau loyer via la formule
        ----------------------------------------------------------------------
        p_nouveau_loyer := ROUND(v_loyer_actuel * (v_indice_new / v_indice_old), 2);

    END calculer_nouveau_loyer;

END pkg_icc;
/




