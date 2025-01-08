
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

CREATE OR REPLACE TRIGGER TBI_fact_bien_somme
FOR INSERT OR UPDATE ON sae_facture_du_bien
COMPOUND TRIGGER

 -- Collection pour stocker les valeurs
  TYPE t_row IS RECORD (
      date_document    sae_facture_du_bien.date_document%TYPE,
      numero_document  sae_facture_du_bien.numero_document%TYPE
  );
  
  TYPE t_tab IS TABLE OF t_row;
  g_tab t_tab := t_tab();  -- Var globale


  -- AFTER EACH ROW: on empile les infos dans g_tab
  AFTER EACH ROW IS
  BEGIN
    g_tab.EXTEND;
    g_tab(g_tab.LAST).date_document   := :NEW.date_document;
    g_tab(g_tab.LAST).numero_document := :NEW.numero_document;
  END AFTER EACH ROW;

  --AFTER STATEMENT : contrôle sur la table
  AFTER STATEMENT IS
    v_somme NUMBER;
  BEGIN
    -- Parcourir la collection g_tab et contrôler 
    -- l’ensemble des (date_document, numero_document) impactés
    FOR i IN 1 .. g_tab.COUNT LOOP
      SELECT SUM(part_des_charges)
        INTO v_somme
        FROM sae_facture_du_bien
       WHERE date_document    = g_tab(i).date_document
         AND numero_document  = g_tab(i).numero_document;

      IF v_somme < 1 THEN
        RAISE_APPLICATION_ERROR(-20301,
          'La somme des parts des charges ne peut être inférieure à 1.0');
      ELSIF v_somme > 1 THEN
        RAISE_APPLICATION_ERROR(-20302,
          'La somme des parts des charges ne peut être supérieure à 1.0');
      END IF;
    END LOOP;
  END AFTER STATEMENT;

END TBI_fact_bien_somme;
/


-- TEST
select * from sae_facture_du_bien
order by numero_document;

UPDATE SAE_FACTURE_DU_BIEN SET PART_DES_CHARGES = 0.4
WHERE NUMERO_DOCUMENT = 'DOC007';

UPDATE SAE_FACTURE_DU_BIEN SET PART_DES_CHARGES = 0.5
WHERE NUMERO_DOCUMENT = 'DOC007';

UPDATE SAE_FACTURE_DU_BIEN SET PART_DES_CHARGES = 0.6
WHERE NUMERO_DOCUMENT = 'DOC007';

select sum(part_des_charges)
from sae_facture_du_bien
where numero_document = 'DOC007';