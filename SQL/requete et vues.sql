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
       SUM(doc.montant * fac.part_des_charges) AS total_montant, 
       COUNT(doc.montant) AS nb_documents
FROM sae_document_comptable doc
JOIN sae_facture_du_bien fac ON doc.numero_document = fac.numero_document
                            AND doc.date_document = fac.date_document
JOIN sae_bien_locatif bien ON fac.identifiant_logement = bien.identifiant_logement
WHERE doc.type_de_document <> 'quittance'
GROUP BY bien.identifiant_logement;


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






