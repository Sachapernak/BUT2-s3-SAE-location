--------------------------------------
--               VUES              ---
--------------------------------------

-- Recuperer les charges fixes a partir d'un bail
CREATE OR REPLACE VIEW SAE_CF_PAR_BAIL AS
SELECT DISTINCT
    doc.date_document as dateDoc, 
    b.identifiant_logement  as idLog,
    bai.id_bail as idBai,
    cf.Type as typeDoc, 
    (doc.montant * fdb.part_des_charges) as montant


from sae_bien_locatif b, sae_document_comptable doc, 
sae_charge_cf cf, sae_facture_du_bien fdb, sae_bail bai,
sae_contracter sc

where doc.recuperable_locataire = 1
and cf.numero_document = doc.numero_document
and cf.date_document = doc.date_document
and fdb.numero_document = doc.numero_document
and fdb.date_document = doc.date_document
and fdb.identifiant_logement = b.identifiant_logement
and bai.identifiant_logement = b.identifiant_logement
and bai.date_de_fin is null
order by doc.date_document DESC;
--

-- Recup charge variables
CREATE OR REPLACE VIEW SAE_CV_PAR_BAIL AS
SELECT DISTINCT
    doc.date_document as dateDoc,  
    b.identifiant_logement  as idLog,
    bai.id_bail as idBai,
    cv.Type as typeDoc, 
    doc.montant * fdb.part_des_charges AS montant,

    '('
      || TO_CHAR(cv.valeur_compteur)
      || ' - '
      || TO_CHAR(NVL(cvpreced.valeur_compteur, 0))
      || ') x '
      || TO_CHAR(cv.cout_variable_unitaire * fdb.part_des_charges, 'FM9999990.00')
      || ' + '
      || TO_CHAR(cv.cout_fixe * fdb.part_des_charges, 'FM9999990.00') 
      || ' = '
      || TO_CHAR(doc.montant * fdb.part_des_charges, 'FM9999990.00')
      || '€'
      AS detail_calcul

FROM       sae_charge_index cv

-- Jointure pour retrouver l?index pr?c?dent
LEFT JOIN sae_charge_index cvpreced
       ON  cvpreced.id_charge_index = cv.id_charge_index_preced
       AND cvpreced.date_de_releve < cv.date_de_releve

-- Jointure pour acc?der ? la facture/document
JOIN       sae_document_comptable doc
       ON  cv.numero_document = doc.numero_document
       AND cv.date_document   = doc.date_document       

JOIN       sae_facture_du_bien fdb
       ON  fdb.numero_document = doc.numero_document
       AND fdb.date_document   = doc.date_document

-- Jointure sur le logement
JOIN       sae_bien_locatif b
       ON  fdb.identifiant_logement = b.identifiant_logement

JOIN       sae_bail bai
       ON  bai.identifiant_logement = b.identifiant_logement
       AND bai.date_de_fin IS NULL

-- Condition sur le document r?cup?rable
WHERE      doc.recuperable_locataire = 1
ORDER BY doc.date_document DESC;
/


CREATE OR REPLACE PACKAGE pkg_regularisation_charge AS
  -- D?claration de la proc?dure calculer_somme_provision
  PROCEDURE calculer_somme_provision_bail(
    p_id_bail  IN SAE_Provision_charge.Id_bail%TYPE,
    p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin IN DATE DEFAULT NULL,  -- Date de fin optionnelle
    p_total OUT NUMBER,
    p_calc OUT VARCHAR2
  );
  
    PROCEDURE sous_total(
        p_id_bail    IN SAE_Provision_charge.Id_bail%TYPE,
        p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
        p_date_fin   IN DATE DEFAULT SYSDATE,  -- Date de fin optionnelle
        p_total_charge OUT NUMBER,
        p_total_deduc  OUT NUMBER);
    
END pkg_regularisation_charge;
/

CREATE OR REPLACE PACKAGE BODY pkg_regularisation_charge AS

    PROCEDURE sous_total(
        p_id_bail    IN SAE_Provision_charge.Id_bail%TYPE,
        p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
        p_date_fin   IN DATE DEFAULT SYSDATE,  -- Date de fin optionnelle
        p_total_charge OUT NUMBER,
        p_total_deduc  OUT NUMBER
    ) AS
        v_cf      NUMBER(10,3);
        v_cv      NUMBER(10,3);
        v_prov    NUMBER(10,3);
        v_temp_calc VARCHAR2(512);
        v_date_debut DATE;
        v_date_fin DATE;
        
    BEGIN
    
        v_date_debut := p_date_debut;
        IF p_date_debut IS NULL THEN
          v_date_debut := TO_DATE('1900-01-01', 'yyyy-MM-dd');
        END IF;
        
        v_date_fin := p_date_fin;
        IF p_date_fin IS NULL THEN
          v_date_fin := SYSDATE;
        END IF;
    
        SELECT NVL(SUM(montant), 0) INTO v_cf
        FROM sae_cf_par_bail
        WHERE idBai = p_id_bail
          AND dateDoc >= v_date_debut
          AND dateDoc <= v_date_fin;
        
        SELECT NVL(SUM(montant), 0) INTO v_cv
        FROM sae_cv_par_bail
        WHERE idBai = p_id_bail
          AND dateDoc >= v_date_debut
          AND dateDoc <= v_date_fin;
        
        p_total_charge := ROUND(v_cf + v_cv, 2);
            
        calculer_somme_provision_bail(
            p_id_bail  => p_id_bail,
            p_date_debut => p_date_debut,
            p_date_fin => p_date_fin,
            p_total    => v_prov,
            p_calc     => v_temp_calc  -- utiliser une variable pour r?cup?rer ce param?tre OUT
        );
                
        p_total_deduc := v_prov;
    END sous_total;

  PROCEDURE calculer_somme_provision_bail (
    p_id_bail    IN  SAE_Provision_charge.Id_bail%TYPE,
    p_date_debut IN  DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin   IN  DATE DEFAULT NULL,
    p_total      OUT NUMBER,
    p_calc       OUT VARCHAR2
  ) AS
    v_total_somme    NUMBER := 0;
    v_calcul         VARCHAR2(512) := '';
    v_date_debut     DATE;
    v_date_fin       DATE;
    v_last_prov_date DATE;
    v_last_prov_val  NUMBER;
  BEGIN
    v_date_debut := NVL(p_date_debut, TO_DATE('1900-01-01', 'yyyy-MM-dd'));
    v_date_fin   := NVL(p_date_fin, SYSDATE);

    BEGIN
      SELECT MAX(date_changement),
             MAX(provision_pour_charge) KEEP (DENSE_RANK LAST ORDER BY date_changement)
        INTO v_last_prov_date, v_last_prov_val
        FROM sae_provision_charge
       WHERE id_bail = p_id_bail
         AND date_changement <= v_date_debut;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_last_prov_date := NULL;
        v_last_prov_val  := 0;
    END;

    FOR vProvision IN (
      SELECT
        id_bail,
        date_changement,
        provision_pour_charge,
        TRUNC(
          ABS(
            MONTHS_BETWEEN(
              NVL(
                LEAD(date_changement) OVER (PARTITION BY id_bail ORDER BY date_changement),
                v_date_fin
              ),
              date_changement
            )
          )
        ) AS difference_en_mois
      FROM sae_provision_charge
      WHERE id_bail         = p_id_bail
        AND date_changement >= NVL(v_last_prov_date, v_date_debut)
        AND date_changement <= v_date_fin
      ORDER BY date_changement
    ) LOOP
      DECLARE
        v_start_date       DATE;
        v_end_date         DATE;
        v_effective_months NUMBER;
        tmp_months         NUMBER;
      BEGIN
        IF vProvision.date_changement < v_date_debut THEN
          v_start_date := v_date_debut;
        ELSE
          v_start_date := vProvision.date_changement;
        END IF;

        IF vProvision.difference_en_mois IS NULL OR vProvision.difference_en_mois = 0 THEN
          v_end_date := v_date_fin;
        ELSE
          SELECT NVL(MIN(date_changement), v_date_fin)
            INTO v_end_date
          FROM sae_provision_charge
          WHERE id_bail = p_id_bail
            AND date_changement > vProvision.date_changement;

          IF v_end_date > v_date_fin THEN
            v_end_date := v_date_fin;
          END IF;
        END IF;

        tmp_months := MONTHS_BETWEEN(v_end_date, v_start_date);
        IF tmp_months < 0 THEN
          tmp_months := -tmp_months;
        END IF;
        v_effective_months := TRUNC(tmp_months);

        -- Si cette itération atteint la fin de la période globale, 
        -- on soustrait 1 pour ignorer le dernier mois partiel.
        IF v_end_date = v_date_fin AND v_effective_months > 0 THEN
          v_effective_months := v_effective_months - 1;
        END IF;

        IF v_last_prov_date IS NOT NULL 
           AND v_last_prov_date <= v_date_debut
        THEN
          v_total_somme := v_total_somme + (v_last_prov_val * v_effective_months);
          v_calcul := v_calcul 
                    || ' ' || v_last_prov_val
                    || ' * ' || v_effective_months 
                    || ' +';
          v_last_prov_date := NULL;
        ELSE
          v_total_somme := v_total_somme 
                           + (vProvision.provision_pour_charge * v_effective_months);
          v_calcul := v_calcul 
                    || ' ' || vProvision.provision_pour_charge
                    || ' * ' || v_effective_months 
                    || ' +';
        END IF;
      END;
    END LOOP;

    IF v_calcul IS NOT NULL THEN
      v_calcul := RTRIM(v_calcul, '+');
    END IF;
    v_calcul := v_calcul || ' = ' || v_total_somme || '€';

    p_total := v_total_somme;
    p_calc  := v_calcul;
  END calculer_somme_provision_bail;

END pkg_regularisation_charge;
/



