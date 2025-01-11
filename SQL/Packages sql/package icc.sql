CREATE OR REPLACE PACKAGE pkg_icc AS
    PROCEDURE calculer_nouveau_loyer(
        p_logement      IN  VARCHAR2,
        p_nouveau_loyer OUT NUMBER
    );
    
    PROCEDURE dernier_loyer(
        p_logement      IN  VARCHAR2,
        p_dernier_loyer OUT NUMBER
    );
END pkg_icc;
/

CREATE OR REPLACE PACKAGE BODY pkg_icc AS

    PROCEDURE calculer_nouveau_loyer(
        p_logement      IN  VARCHAR2,
        p_nouveau_loyer OUT NUMBER
    ) AS
        v_date_de_debut   DATE;
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
        -- 2) Obtention du loyer actuel via la procédure dernier_loyer
        ----------------------------------------------------------------------
        BEGIN
            -- Appel à la procédure interne du package pour obtenir le loyer actuel
            dernier_loyer(p_logement, v_loyer_actuel);
        EXCEPTION
            WHEN OTHERS THEN
                RAISE;
        END;

        ----------------------------------------------------------------------
        -- 3) Déterminer l'année en cours et extraire le trimestre du bail
        ----------------------------------------------------------------------
        v_annee     := TO_CHAR(SYSDATE, 'YYYY');       -- Année actuelle
        v_trimestre := TO_CHAR(v_date_de_debut, 'Q');  -- Trimestre basé sur la date du bail

        ----------------------------------------------------------------------
        -- 4) Récupérer l'indice ICC pour (v_annee, v_trimestre)
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
        -- 5) Récupérer l'indice ICC de l'année précédente (v_annee - 1), même trimestre
        ----------------------------------------------------------------------
        BEGIN
            SELECT indice
              INTO v_indice_old
              FROM sae_icc
             WHERE annee_icc     = TO_CHAR(TO_NUMBER(v_annee) - 1)
               AND trimestre_icc = v_trimestre;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20503, 'ICC Introuvable pour : ' 
                                        || TO_CHAR(TO_NUMBER(v_annee) - 1) 
                                        || ' T' || v_trimestre);
        END;

        ----------------------------------------------------------------------
        -- 6) Calcul du nouveau loyer via la formule
        ----------------------------------------------------------------------
        p_nouveau_loyer := ROUND(v_loyer_actuel * (v_indice_new / v_indice_old), 2);

    END calculer_nouveau_loyer;
    
    -- Définition de la procédure dernier_loyer reste inchangée
    PROCEDURE dernier_loyer(
        p_logement      IN  VARCHAR2,
        p_dernier_loyer OUT NUMBER
    ) AS
        v_dernier_loyer   NUMBER;
        v_base_loyer      NUMBER;
    BEGIN
        ----------------------------------------------------------------------
        -- 1) Dernier loyer (si déjà un) sinon on utilisera le loyer de base
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
        -- 2) Récupération du loyer de base
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
        -- 3) Loyer actuel = dernier loyer s'il existe, sinon loyer de base
        ----------------------------------------------------------------------
        IF v_dernier_loyer IS NOT NULL THEN
            p_dernier_loyer := v_dernier_loyer;
        ELSE
            p_dernier_loyer := v_base_loyer;
        END IF;
    
    END dernier_loyer;

END pkg_icc;
/





