--------------------------------------
--               VUES              ---
--------------------------------------

select * from sae_cf_par_loc;

-- Recuperer les charges fixes des locataires
CREATE OR REPLACE VIEW SAE_CF_PAR_LOC AS
SELECT
    doc.date_document as dateDoc, 
    l.identifiant_locataire as idLoc, 
    b.identifiant_logement  as idLog,
    bai.id_bail as idBai,
    cf.Type as typeDoc, 
    (doc.montant * sc.part_de_loyer * fdb.part_des_charges) as montant


from sae_locataire l, sae_bien_locatif b, sae_document_comptable doc, 
sae_charge_cf cf, sae_facture_du_bien fdb, sae_bail bai,
sae_contracter sc

where  sc.identifiant_locataire = l.identifiant_locataire
and doc.recuperable_locataire = 1
and cf.numero_document = doc.numero_document
and cf.date_document = doc.date_document
and fdb.numero_document = doc.numero_document
and fdb.date_document = doc.date_document
and fdb.identifiant_logement = b.identifiant_logement
and bai.identifiant_logement = b.identifiant_logement
and bai.date_de_fin is null
and sc.id_bail = bai.id_bail
order by doc.date_document DESC;
--

-- Recup charge variables
CREATE OR REPLACE VIEW SAE_CV_PAR_LOC AS
SELECT
    doc.date_document as dateDoc, 
    l.identifiant_locataire as idLoc, 
    b.identifiant_logement  as idLog,
    bai.id_bail as idBai,
    cv.Type as typeDoc, 
    doc.montant * sc.part_de_loyer * fdb.part_des_charges AS montant,

    '('
      || TO_CHAR(cv.valeur_compteur)
      || ' - '
      || TO_CHAR(NVL(cvpreced.valeur_compteur, 0))
      || ') x '
      || TO_CHAR(cv.cout_variable_unitaire * sc.part_de_loyer * fdb.part_des_charges, 'FM9999990.00')
      || ' + '
      || TO_CHAR(cv.cout_fixe * sc.part_de_loyer * fdb.part_des_charges, 'FM9999990.00') 
      || ' = '
      || TO_CHAR(doc.montant * sc.part_de_loyer * fdb.part_des_charges, 'FM9999990.00')
      AS detail_calcul

FROM       sae_charge_index cv

-- Jointure pour retrouver l’index précédent
LEFT JOIN sae_charge_index cvpreced
       ON  cvpreced.id_charge_index = cv.id_charge_index_preced
       AND cvpreced.date_de_releve < cv.date_de_releve

-- Jointure pour accéder à la facture/document
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

-- Jointure sur le bail/locataire
JOIN       sae_contracter sc
       ON  sc.id_bail = bai.id_bail

JOIN       sae_locataire l
       ON  sc.identifiant_locataire = l.identifiant_locataire

-- Condition sur le document récupérable
WHERE      doc.recuperable_locataire = 1
ORDER BY doc.date_document DESC;
/


-- Selectionner le montant de caution / locataire
CREATE OR REPLACE VIEW SAE_CAUTION_BAIL_PAR_PERSON AS
SELECT 
    cau.id_bail as idBail, 
    sum(cau.montant) / count(co.identifiant_locataire) AS montantLoc
FROM 
    sae_cautionner cau,
    sae_bail bai,
    sae_contracter co
WHERE
    cau.id_bail = bai.id_bail
    AND bai.id_bail = co.id_bail
GROUP BY 
    cau.id_bail;
/


CREATE OR REPLACE PACKAGE pkg_solde_compte AS
  -- Déclaration de la procédure calculer_somme_provision
  PROCEDURE calculer_somme_provision(
    p_id_bail  IN SAE_Provision_charge.Id_bail%TYPE,
    p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin IN DATE DEFAULT NULL,  -- Date de fin optionnelle
    p_total OUT NUMBER,
    p_calc OUT VARCHAR
  );
  
    PROCEDURE sous_total(
        p_id_bail    IN SAE_Provision_charge.Id_bail%TYPE,
        p_id_loc     IN SAE_Locataire.identifiant_locataire%TYPE,
        p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
        p_date_fin   IN DATE DEFAULT SYSDATE,  -- Date de fin optionnelle
        p_total_charge OUT NUMBER,
        p_total_deduc  OUT NUMBER);
    
END pkg_solde_compte;
/

CREATE OR REPLACE PACKAGE BODY pkg_solde_compte AS

    PROCEDURE sous_total(
        p_id_bail    IN SAE_Provision_charge.Id_bail%TYPE,
        p_id_loc     IN SAE_Locataire.identifiant_locataire%TYPE,
        p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
        p_date_fin   IN DATE DEFAULT SYSDATE,  -- Date de fin optionnelle
        p_total_charge OUT NUMBER,
        p_total_deduc  OUT NUMBER
    ) AS
        v_cf      NUMBER(10,3);
        v_cv      NUMBER(10,3);
        v_prov    NUMBER(10,3);
        v_mult    NUMBER(3,2);
        v_caution NUMBER(10,3);
        v_temp_calc VARCHAR2(512);
        
    BEGIN
        SELECT NVL(SUM(montant), 0) INTO v_cf
        FROM sae_cf_par_loc
        WHERE idBai = p_id_bail
          AND idLoc = p_id_loc
          AND dateDoc >= p_date_debut
          AND dateDoc <= p_date_fin;
        
        SELECT NVL(SUM(montant), 0) INTO v_cv
        FROM sae_cv_par_loc
        WHERE idBai = p_id_bail
          AND idLoc = p_id_loc
          AND dateDoc >= p_date_debut
          AND dateDoc <= p_date_fin;
        
        p_total_charge := v_cf + v_cv;
            
        calculer_somme_provision(
            p_id_bail  => p_id_bail,
            p_date_debut => p_date_debut,
            p_date_fin => p_date_fin,
            p_total    => v_prov,
            p_calc     => v_temp_calc  -- utiliser une variable pour récupérer ce paramètre OUT
        );
        
        SELECT part_de_loyer INTO v_mult
        FROM sae_contracter
        WHERE identifiant_locataire = p_id_loc
          AND id_bail = p_id_bail;
        
        v_prov := v_prov * v_mult;
        
        SELECT montantLoc INTO v_caution
        FROM SAE_CAUTION_BAIL_PAR_PERSON
        WHERE idBail = p_id_bail;
        
        p_total_deduc := v_prov + v_caution;
    END sous_total;

  PROCEDURE calculer_somme_provision(
    p_id_bail  IN SAE_Provision_charge.Id_bail%TYPE,
    p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin IN DATE DEFAULT NULL,  -- Date de fin optionnelle
    p_total OUT NUMBER,
    p_calc OUT VARCHAR
  ) AS
  
    v_total_somme NUMBER := 0;
    v_calcul      VARCHAR2(512) := '';
    v_date_debut DATE;
    
  BEGIN
    -- Si p_date_debut est null, on le fixe à la date par défaut
    v_date_debut := p_date_debut;
    IF p_date_debut IS NULL THEN
      v_date_debut := TO_DATE('1900-01-01', 'yyyy-MM-dd');
    END IF;
    
    FOR vProvision IN (SELECT 
                            id_bail,
                            date_changement,
                            provision_pour_charge, 
                            TRUNC(ABS(MONTHS_BETWEEN(
                              NVL(LEAD(date_changement) OVER (PARTITION BY id_bail ORDER BY date_changement),
                                  NVL(p_date_fin, SYSDATE)),
                              date_changement
                            ))) AS difference_en_mois
                        FROM sae_provision_charge
                        WHERE ID_BAIL = p_id_bail
                            AND date_changement > v_date_debut) LOOP
                            
      v_total_somme := v_total_somme 
                       + (vProvision.provision_pour_charge * vProvision.difference_en_mois);
      v_calcul := v_calcul || ' ' || vProvision.provision_pour_charge || ' * ' 
                  || vProvision.difference_en_mois || ' +';
    END LOOP;
    
    -- Supprimer le dernier '+' ajouté
    v_calcul := RTRIM(v_calcul, '+');
    v_calcul := v_calcul || ' = ' || v_total_somme;
    
    p_total := v_total_somme;
    p_calc  := v_calcul;
  END calculer_somme_provision;

END pkg_solde_compte;
/


