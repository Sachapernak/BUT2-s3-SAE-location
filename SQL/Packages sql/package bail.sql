CREATE OR REPLACE PACKAGE pkg_Bail AS
  PROCEDURE supprimer_Bail(
    p_id_bail IN SAE_Bail.id_bail%TYPE
  );
  
  FUNCTION nombre_bail_actif(
    p_id_logement IN sae_bail.identifiant_logement%TYPE
    ) RETURN NUMBER;
    
END pkg_Bail;
/

CREATE OR REPLACE PACKAGE BODY pkg_Bail AS

  PROCEDURE supprimer_Bail(
    p_id_bail IN SAE_Bail.id_bail%TYPE
  ) IS
  BEGIN
    -- Supprimer les regularisations associes au bail
    DELETE FROM sae_regularisation
    WHERE id_bail = p_id_bail;

    -- Supprimer le bail
    DELETE FROM SAE_Bail
    WHERE id_bail = p_id_bail;

  END supprimer_Bail;
  
    FUNCTION nombre_bail_actif(
    p_id_logement IN sae_bail.identifiant_logement%TYPE
    ) RETURN NUMBER AS
        v_nb_bail NUMBER;
    BEGIN
    
        SELECT COUNT(*) INTO v_nb_bail
        FROM SAE_BAIL
        WHERE identifiant_logement = p_id_logement
        AND (DATE_DE_FIN IS NULL OR DATE_DE_FIN < SYSDATE);
        
        return v_nb_bail;
    
    END nombre_bail_actif;
    
END pkg_Bail;
/



