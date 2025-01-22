CREATE OR REPLACE PROCEDURE setTriggerEnable(p_state NUMBER) AS
  CURSOR trigger_cursor IS
    SELECT trigger_name FROM user_triggers;
  sql_command VARCHAR2(1000);
BEGIN
  -- Vérification du paramètre
  IF p_state NOT IN (0, 1) THEN
    RAISE_APPLICATION_ERROR(-20001, 'Le paramètre doit être 0 (désactiver) ou 1 (activer).');
  END IF;

  FOR rec IN trigger_cursor LOOP
    BEGIN
      -- Construction de la commande SQL en fonction du paramètre
      IF p_state = 1 THEN
        sql_command := 'ALTER TRIGGER ' || rec.trigger_name || ' ENABLE';
      ELSE
        sql_command := 'ALTER TRIGGER ' || rec.trigger_name || ' DISABLE';
      END IF;

      -- Exécution de la commande
      EXECUTE IMMEDIATE sql_command;
      DBMS_OUTPUT.PUT_LINE('Trigger ' || rec.trigger_name || 
                           (CASE WHEN p_state = 1 THEN ' activé.' ELSE ' désactivé.' END));
    EXCEPTION
      WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur sur le trigger ' || rec.trigger_name || ' : ' || SQLERRM);
        -- Continuer avec le prochain trigger en cas d'erreur
    END;
  END LOOP;
END setTriggerEnable;
/

BEGIN
  setTriggerEnable(0);
END;
/

BEGIN
  setTriggerEnable(1);
END;
/


-- verifie que la date de releve precedente correspond bien a une charge precedente
CREATE OR REPLACE TRIGGER TBIU_ch_indx_releve_preced
BEFORE INSERT OR UPDATE ON sae_charge_index
FOR EACH ROW
WHEN (NEW.id_charge_index_preced IS NOT NULL AND NEW.date_releve_precedent IS NOT NULL)
DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM sae_charge_index
    WHERE id_charge_index = :NEW.id_charge_index_preced
      AND date_de_releve = :NEW.date_releve_precedent;

    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'La date de relève précédente ne correspond à aucun relevé existant pour le même ID.');
    END IF;
END;
/



-- verifie que l'augementation du loyer est cohérente avec l'icc
CREATE OR REPLACE TRIGGER TBIU_reva_loyer_ICC
BEFORE INSERT OR UPDATE ON sae_loyer
FOR EACH ROW
DECLARE
    v_max_loyer NUMBER;
BEGIN
    -- Appel à la procédure pour calculer le loyer maximal autorisé
    pkg_icc.calculer_nouveau_loyer(:NEW.IDENTIFIANT_LOGEMENT, v_max_loyer);

    -- Vérification si le montant du nouveau loyer dépasse le maximum autorisé
    -- verifie si le loyer max n'est pas 0 (loyer augmentable sans maximum)
    IF v_max_loyer > 1 AND :NEW.montant_loyer > v_max_loyer THEN
        RAISE_APPLICATION_ERROR(-20002, 
            'Le nouveau loyer (' || :NEW.montant_loyer || ') ne peut pas être légalement supérieur à : ' || v_max_loyer);
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20003, 'Erreur lors du calcul du nouveau loyer : ' || SQLERRM);
END;
/

-- verifie que l'augementation du loyer est compatible avec la date du jour.
CREATE OR REPLACE TRIGGER TBIU_reva_loyer_date
BEFORE INSERT OR UPDATE ON sae_loyer
FOR EACH ROW
DECLARE
    v_loyer_augmentable NUMBER;
BEGIN
    -- Vérification si le loyer est augmentable à la date du jour
    v_loyer_augmentable := pkg_icc.loyer_augmentable(:NEW.IDENTIFIANT_LOGEMENT);

    IF v_loyer_augmentable = 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 
            'Le loyer ne peut pas être augmenté à cette date. Vérifiez les règles d''augmentation.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20005, 'Erreur lors de la vérification de l''augmentation du loyer : ' || SQLERRM);
END;
/


/**
  Verifie si tous les locataires ont une date de sortie.
  Si c'est le cas, met fin au bail a la dernière date de sortie.
    
  Si un nouveau locataire arrive, sans date de fin,
  met la date de fin a null
**/
CREATE OR REPLACE TRIGGER TAU_date_fin_contrat
FOR UPDATE ON SAE_CONTRACTER
COMPOUND TRIGGER

  -- Utiliser une table associative pour stocker les ID_BAIL modifiés comme clés
  TYPE BailIdAssocArray IS TABLE OF BOOLEAN INDEX BY SAE_CONTRACTER.ID_BAIL%TYPE;
  g_bail_ids BailIdAssocArray;

  BEFORE EACH ROW IS
  BEGIN
    -- Ajouter l'ID_BAIL à la collection s'il n'est pas déjà présent
    IF NOT g_bail_ids.EXISTS(:NEW.ID_BAIL) THEN
      g_bail_ids(:NEW.ID_BAIL) := TRUE;
    END IF;
  END BEFORE EACH ROW;

  AFTER STATEMENT IS
    v_nb_loc_actif       NUMBER;
    v_nb_loc_total       NUMBER;
    v_date_dernier_depart DATE;
    bail_key             SAE_CONTRACTER.ID_BAIL%TYPE;
  BEGIN
    -- Parcourir chaque clé (ID_BAIL) présente dans la table associative
    bail_key := g_bail_ids.FIRST;
    WHILE bail_key IS NOT NULL LOOP
      -- Compter les locataires actifs pour cet ID_BAIL
      SELECT COUNT(*)
        INTO v_nb_loc_actif
        FROM SAE_CONTRACTER
       WHERE ID_BAIL = bail_key
         AND date_de_sortie IS NULL;

      -- Compter le nombre total de locataires pour cet ID_BAIL
      SELECT COUNT(*)
        INTO v_nb_loc_total
        FROM SAE_CONTRACTER
       WHERE ID_BAIL = bail_key;

      IF (v_nb_loc_actif = 0 AND v_nb_loc_total > 0) THEN
        -- Récupérer la dernière date de sortie si aucun locataire actif
        SELECT MAX(date_de_sortie)
          INTO v_date_dernier_depart
          FROM SAE_CONTRACTER
         WHERE ID_BAIL = bail_key;

        UPDATE SAE_BAIL
           SET DATE_DE_FIN = v_date_dernier_depart
         WHERE ID_BAIL = bail_key;
      ELSE
        UPDATE SAE_BAIL
           SET DATE_DE_FIN = NULL
         WHERE ID_BAIL = bail_key;
      END IF;

      -- Passer à la clé suivante
      bail_key := g_bail_ids.NEXT(bail_key);
    END LOOP;
    -- Réinitialiser la collection après traitement
    g_bail_ids.DELETE;
  END AFTER STATEMENT;

END TAU_date_fin_contrat;
/


