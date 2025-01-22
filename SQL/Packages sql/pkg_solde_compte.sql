--------------------------------------
--               VUES              ---
--------------------------------------

select * from sae_cf_par_loc;

-- Recuperer les charges fixes des locataires
CREATE OR REPLACE VIEW SAE_CF_PAR_LOC AS
SELECT DISTINCT
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
SELECT DISTINCT
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
      || '€'
      AS detail_calcul

FROM       sae_charge_index cv

-- Jointure pour retrouver l’index précédent
LEFT JOIN sae_charge_index cvpreced
       ON  cvpreced.id_charge_index = cv.id_charge_index_preced
       AND cvpreced.date_de_releve = cv.date_releve_precedent

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
    cau.id_bail AS idBail, 
    CASE 
      WHEN (SELECT COUNT(*) 
            FROM sae_contracter co 
            WHERE co.id_bail = cau.id_bail) = 0 
      THEN 0
      ELSE ROUND(
             SUM(cau.montant) / 
             (SELECT COUNT(*) 
              FROM sae_contracter co 
              WHERE co.id_bail = cau.id_bail
             ), 2)
    END AS montantLoc
FROM 
    sae_cautionner cau
GROUP BY 
    cau.id_bail;


CREATE OR REPLACE PACKAGE pkg_solde_compte AS
  -- Déclaration de la procédure calculer_somme_provision
  PROCEDURE calculer_somme_provision(
    p_id_bail  IN SAE_Provision_charge.Id_bail%TYPE,
    p_id_loc     IN SAE_Locataire.identifiant_locataire%TYPE,
    p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
    p_date_fin IN DATE DEFAULT NULL,  -- Date de fin optionnelle
    p_total OUT NUMBER,
    p_calc OUT VARCHAR2
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
    v_cf        NUMBER(10,3);
    v_cv        NUMBER(10,3);
    v_prov      NUMBER(10,3);
    v_caution   NUMBER(10,3) := 0;  -- Initialisation par défaut
    v_temp_calc VARCHAR2(512);
    v_date_debut DATE;
    v_date_fin   DATE;
BEGIN
    -- Gestion des dates par défaut
    v_date_debut := NVL(p_date_debut, TO_DATE('1900-01-01', 'yyyy-MM-dd'));
    v_date_fin   := NVL(p_date_fin, SYSDATE);

    -- Calcul des sommes pour les charges CF et CV
    SELECT NVL(SUM(montant), 0) 
      INTO v_cf
      FROM sae_cf_par_loc
     WHERE idBai = p_id_bail
       AND idLoc = p_id_loc
       AND dateDoc BETWEEN v_date_debut AND v_date_fin;
    
    SELECT NVL(SUM(montant), 0) 
      INTO v_cv
      FROM sae_cv_par_loc
     WHERE idBai = p_id_bail
       AND idLoc = p_id_loc
       AND dateDoc BETWEEN v_date_debut AND v_date_fin;
    
    p_total_charge := ROUND(v_cf + v_cv, 2);
        
    -- Appel à la procédure pour le calcul des provisions
    calculer_somme_provision(
        p_id_bail    => p_id_bail,
        p_id_loc     => p_id_loc,
        p_date_debut => p_date_debut,
        p_date_fin   => p_date_fin,
        p_total      => v_prov,
        p_calc       => v_temp_calc  -- récupère le détail du calcul
    );
    
    -- Récupération du montant de la caution, en gérant l'absence de donnée
    BEGIN
      SELECT montantLoc 
        INTO v_caution
        FROM SAE_CAUTION_BAIL_PAR_PERSON
       WHERE idBail = p_id_bail;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_caution := 0;
    END;
    
    p_total_deduc := ROUND(v_prov + v_caution, 2);
END sous_total;

    PROCEDURE calculer_somme_provision(
      p_id_bail    IN SAE_Provision_charge.Id_bail%TYPE,
      p_id_loc     IN SAE_Locataire.identifiant_locataire%TYPE,
      p_date_debut IN DATE DEFAULT TO_DATE('1900-01-01', 'yyyy-MM-dd'),
      p_date_fin   IN DATE DEFAULT NULL,  -- Date de fin optionnelle
      p_total      OUT NUMBER,
      p_calc       OUT VARCHAR2
    ) AS
      v_mult           NUMBER;
      v_total_somme    NUMBER := 0;
      v_calcul         VARCHAR2(512) := '';
      v_date_debut     DATE;
      v_date_fin       DATE;
      v_last_prov_date DATE;
      v_last_prov_val  NUMBER;
    BEGIN
      -- Gestion des dates par défaut si null
      v_date_debut := NVL(p_date_debut, TO_DATE('1900-01-01', 'yyyy-MM-dd'));
      v_date_fin   := NVL(p_date_fin, SYSDATE);
    
      /*
        Étape 1 : Récupérer la dernière provision avant ou égale à la date de début.
        Cette provision servira de base pour le calcul à partir de la date de début.
      */
      BEGIN
        SELECT MAX(date_changement), 
               MAX(provision_pour_charge) KEEP (DENSE_RANK LAST ORDER BY date_changement)
          INTO v_last_prov_date, v_last_prov_val
          FROM sae_provision_charge
         WHERE id_bail = p_id_bail
           AND date_changement <= v_date_debut;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          -- Aucun enregistrement trouvé avant la date de début
          v_last_prov_date := NULL;
          v_last_prov_val  := 0;
      END;
    
      /*
        Étape 2 : Construire un curseur pour sélectionner toutes les provisions 
                 à partir de la dernière trouvée (s'il existe) jusqu'à la date de fin.
      */
      FOR vProvision IN (
          SELECT 
              id_bail,
              date_changement,
              provision_pour_charge,
              TRUNC(ABS(MONTHS_BETWEEN(
                NVL(LEAD(date_changement) OVER (PARTITION BY id_bail ORDER BY date_changement), v_date_fin),
                date_changement
              ))) AS difference_en_mois
            FROM sae_provision_charge
           WHERE id_bail = p_id_bail
             AND date_changement >= NVL(v_last_prov_date, v_date_debut)
             AND date_changement <= v_date_fin
           ORDER BY date_changement
      ) LOOP
          DECLARE
            v_start_date DATE;
            v_end_date   DATE;
            v_effective_months NUMBER;
          BEGIN
            /*
              Déterminer la période effective pour le calcul :
              - La date de début est soit la date de début de calcul (pour la première itération),
                soit la date de changement actuelle.
              - La date de fin est le minimum entre la date de changement suivante et v_date_fin.
            */
            IF vProvision.date_changement < v_date_debut THEN
              v_start_date := v_date_debut;
            ELSE
              v_start_date := vProvision.date_changement;
            END IF;
    
            -- Calculer la date de fin pour la période en cours
            IF vProvision.difference_en_mois IS NULL OR vProvision.difference_en_mois = 0 THEN
              v_end_date := v_date_fin;
            ELSE
              -- Utilisation de LEAD via difference_en_mois pourrait être simplifiée par un calcul de date, 
              -- mais ici on utilise la logique déjà établie.
              SELECT NVL(MIN(date_changement), v_date_fin)
                INTO v_end_date
                FROM sae_provision_charge
               WHERE id_bail = p_id_bail
                 AND date_changement > vProvision.date_changement;
              IF v_end_date > v_date_fin THEN
                v_end_date := v_date_fin;
              END IF;
            END IF;
    
            -- Calculer le nombre de mois effectifs pour la période
            v_effective_months := TRUNC(ABS(MONTHS_BETWEEN(v_end_date, v_start_date)));
    
            -- Récupérer la part_de_loyer pour le locataire et le bail
            BEGIN
              SELECT NVL(part_de_loyer, 1)
                INTO v_mult
                FROM sae_contracter
               WHERE identifiant_locataire = p_id_loc
                 AND id_bail = p_id_bail;
            EXCEPTION
              WHEN NO_DATA_FOUND THEN
                v_mult := 1;
            END;
    
            -- Si c'est la première itération et qu'il y avait une provision avant la date de début
            IF v_last_prov_date IS NOT NULL AND v_last_prov_date <= v_date_debut THEN
              -- Utiliser la provision antérieure pour la période entre la date_debut et 
              -- la prochaine date_changement (ou date_fin)
              v_total_somme := v_total_somme 
                             + (v_last_prov_val * v_effective_months * v_mult);
              v_calcul := v_calcul || ' ' || (v_last_prov_val * v_mult) 
                          || ' * ' || v_effective_months || ' +';
              -- Réinitialiser v_last_prov_date pour n'utiliser la provision initiale qu'une seule fois
              v_last_prov_date := NULL;
            ELSE
              -- Pour les itérations suivantes, utiliser la provision actuelle
              v_total_somme := v_total_somme 
                             + (vProvision.provision_pour_charge * v_effective_months * v_mult);
              v_calcul := v_calcul || ' ' || (vProvision.provision_pour_charge * v_mult) 
                          || ' * ' || v_effective_months || ' +';
            END IF;
          END;
      END LOOP;
    
      -- Supprimer le dernier '+' ajouté s'il existe
      IF v_calcul IS NOT NULL THEN
        v_calcul := RTRIM(v_calcul, '+');
      END IF;
      v_calcul := v_calcul || ' = ' || v_total_somme || '€';
    
      p_total := v_total_somme;
      p_calc  := v_calcul;
    END calculer_somme_provision;



END pkg_solde_compte;
/


