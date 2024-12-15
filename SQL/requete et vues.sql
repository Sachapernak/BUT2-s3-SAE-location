-- debut de reflexion


-- Trouver les truc qui ne sont pas des quittances
select bien.identifiant_logement, sum(doc.montant), count(doc.montant) from
sae_document_comptable doc,
sae_facture_du_bien fac,
sae_bien_locatif bien
where doc.numero_document = fac.numero_document
and doc.date_document = fac.date_document
and fac.identifiant_logement = bien.identifiant_logement
and doc.type_de_document <> 'quittance'
group by bien.identifiant_logement;

-- Trouver les documents (non quittances) avec la part des charges prise en compte
SELECT bien.identifiant_logement, 
       SUM(doc.montant) AS montant_avant_part,
       SUM(doc.montant * fac.part_des_charges) AS total_montant, 
       COUNT(doc.montant) AS nb_documents
FROM sae_document_comptable doc
JOIN sae_facture_du_bien fac ON doc.numero_document = fac.numero_document
                            AND doc.date_document = fac.date_document
JOIN sae_bien_locatif bien ON fac.identifiant_logement = bien.identifiant_logement
WHERE doc.type_de_document <> 'quittance'
GROUP BY bien.identifiant_logement;


SELECT EXTRACT(YEAR FROM doc.date_document) AS annee,
       bien.identifiant_logement, 
       SUM(doc.montant) AS montant_avant_part,
       SUM(doc.montant * fac.part_des_charges) AS total_montant, 
       COUNT(doc.montant) AS nb_documents
FROM sae_document_comptable doc
JOIN sae_facture_du_bien fac ON doc.numero_document = fac.numero_document
                           AND doc.date_document = fac.date_document
JOIN sae_bien_locatif bien ON fac.identifiant_logement = bien.identifiant_logement
WHERE doc.type_de_document <> 'quittance'
GROUP BY EXTRACT(YEAR FROM doc.date_document), bien.identifiant_logement
ORDER BY 1 DESC, 2;


-- trouver les quittances
select bien.identifiant_logement, sum(doc.montant), count(doc.montant) from
sae_document_comptable doc,
sae_facture_du_bien fac,
sae_bien_locatif bien
where doc.numero_document = fac.numero_document
and doc.date_document = fac.date_document
and fac.identifiant_logement = bien.identifiant_logement
and doc.type_de_document = 'quittance'
group by bien.identifiant_logement;


-- Trouver les charges par mois, par logement
CREATE OR REPLACE VIEW vue_charge_par_logement AS
SELECT 
    EXTRACT(YEAR FROM doc.date_document) AS annee,
    EXTRACT(MONTH FROM doc.date_document) AS mois,
    bien.identifiant_logement, 
    SUM(doc.montant) AS montant_avant_part,
    SUM(doc.montant * fac.part_des_charges) AS total_montant, 
    COUNT(doc.montant) AS nb_documents
FROM 
    sae_document_comptable doc
JOIN 
    sae_facture_du_bien fac 
    ON doc.numero_document = fac.numero_document
    AND doc.date_document = fac.date_document
JOIN 
    sae_bien_locatif bien 
    ON fac.identifiant_logement = bien.identifiant_logement
WHERE 
    doc.type_de_document <> 'quittance'
GROUP BY 
    EXTRACT(YEAR FROM doc.date_document), 
    EXTRACT(MONTH FROM doc.date_document),
    bien.identifiant_logement;
    
CREATE OR REPLACE VIEW vue_charge_par_logement_annee AS
SELECT 
    annee,
    identifiant_logement,
    SUM(montant_avant_part) AS montant_avant_part_annuel,
    SUM(total_montant) AS total_montant_annuel,
    SUM(nb_documents) AS nb_documents_annuels
FROM 
    vue_charge_par_logement
GROUP BY 
    annee,
    identifiant_logement;

select * from vue_charge_par_logement order by 1 desc, 3, 2 desc;

select * from vue_charge_par_logement_annee order by 1 desc, 2;


CREATE OR REPLACE VIEW vue_charges_par_bail_et_annee AS
SELECT
    bail.Id_bail,
    EXTRACT(YEAR FROM doc.Date_document) AS annee,
    SUM(CASE WHEN doc.recuperable_locataire = 1 THEN doc.montant ELSE 0 END) AS total_charges_recuperables,
    SUM(CASE WHEN doc.recuperable_locataire = 0 THEN doc.montant ELSE 0 END) AS total_charges_non_recuperables,
    COUNT(*) AS nb_documents
FROM sae_document_comptable doc
JOIN sae_facture_du_bien fac
    ON doc.numero_document = fac.numero_document
    AND doc.date_document = fac.date_document
JOIN SAE_Bien_locatif bl
    ON fac.identifiant_logement = bl.identifiant_logement
JOIN SAE_Bail bail
    ON bl.identifiant_logement = bail.identifiant_logement
    AND doc.Date_document >= bail.Date_de_debut
    AND (bail.date_de_fin IS NULL OR doc.Date_document <= bail.date_de_fin)
WHERE doc.Type_de_document <> 'quittance'
GROUP BY 
    bail.Id_bail, 
    EXTRACT(YEAR FROM doc.Date_document);



select * from vue_charges_par_bail_et_annee order by 1, 2 desc;









