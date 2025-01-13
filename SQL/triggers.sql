
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
        RAISE_APPLICATION_ERROR(-20001, 'La date de rel�ve pr�c�dente ne correspond � aucun relev� existant pour le m�me ID.');
    END IF;
END;
/



-- verifie que l'augementation du loyer est coh�rente avec l'icc
CREATE OR REPLACE TRIGGER TBIU_reva_loyer_ICC
BEFORE INSERT OR UPDATE ON sae_loyer
FOR EACH ROW
DECLARE
    v_max_loyer NUMBER;
BEGIN
    -- Appel � la proc�dure pour calculer le loyer maximal autoris�
    pkg_icc.calculer_nouveau_loyer(:NEW.IDENTIFIANT_LOGEMENT, v_max_loyer);

    -- V�rification si le montant du nouveau loyer d�passe le maximum autoris�
    IF :NEW.montant_loyer > v_max_loyer THEN
        RAISE_APPLICATION_ERROR(-20002, 
            'Le nouveau loyer (' || :NEW.montant_loyer || ') ne peut pas �tre l�galement sup�rieur � : ' || v_max_loyer);
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
    -- V�rification si le loyer est augmentable � la date du jour
    v_loyer_augmentable := pkg_icc.loyer_augmentable(:NEW.IDENTIFIANT_LOGEMENT);

    IF v_loyer_augmentable = 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 
            'Le loyer ne peut pas �tre augment� � cette date. V�rifiez les r�gles d''augmentation.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20005, 'Erreur lors de la v�rification de l''augmentation du loyer : ' || SQLERRM);
END;
/

/
