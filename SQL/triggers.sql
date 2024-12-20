
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
