create or replace PACKAGE Quittance AS

    -- Récupérer les documents comptables de type 'loyer'
    PROCEDURE GetDocumentsLoyer (
        p_identifiant_locataire IN sae_document_comptable.identifiant_locataire%TYPE,
        p_identifiant_logement IN sae_facture_du_bien.identifiant_logement%TYPE,
        p_date_document IN VARCHAR2,
        p_result OUT SYS_REFCURSOR
    );

    -- Récupérer un bien en fonction d'un document comptable
    PROCEDURE GetBienByDocument (
        p_date_document IN VARCHAR2,
        p_numero_document IN sae_document_comptable.numero_document%TYPE,
        p_result OUT SYS_REFCURSOR
    );
    -- Récupérer un bail en fonction d'un document comptable
    PROCEDURE GetBailByDocument (
        p_date_document IN VARCHAR2,
        p_numero_document IN sae_document_comptable.numero_document%TYPE,
        p_result OUT SYS_REFCURSOR
    );

    -- Récupérer les provisions pour charge en fonction d'un document comptable
    PROCEDURE GetPPCDocCompt (
        p_identifiant_locataire IN sae_document_comptable.identifiant_locataire%TYPE,
        p_identifiant_logement IN sae_facture_du_bien.identifiant_logement%TYPE,
        p_date_document_1 IN VARCHAR2,
        p_date_document_2 IN VARCHAR2,
        p_result OUT SYS_REFCURSOR
    );

    -- Récupérer un bail en fonction d'un locataire et d'un bien
    PROCEDURE GetPPCLocBien (
        p_identifiant_locataire IN sae_contracter.identifiant_locataire%TYPE,
        p_identifiant_logement IN sae_bail.identifiant_logement%TYPE,
        p_result OUT SYS_REFCURSOR
    );


END Quittance;

/
create or replace PACKAGE BODY Quittance AS

    PROCEDURE GetDocumentsLoyer (
    p_identifiant_locataire IN sae_document_comptable.identifiant_locataire%TYPE,
    p_identifiant_logement IN sae_facture_du_bien.identifiant_logement%TYPE,
    p_date_document IN VARCHAR2,
    p_result OUT SYS_REFCURSOR
) IS
BEGIN
    -- Vérifier que la première date est inférieure à la deuxième
    
        OPEN p_result FOR
        SELECT sdc.*
        FROM SAE_DOCUMENT_COMPTABLE sdc, SAE_FACTURE_DU_BIEN sfb
        WHERE sdc.identifiant_locataire = p_identifiant_locataire
          AND sdc.numero_document = sfb.numero_document
          AND sdc.date_document = sfb.date_document
          AND sfb.identifiant_logement = p_identifiant_logement
          AND sdc.type_de_document = 'loyer'
          AND TO_CHAR(sdc.date_document, 'MM-YYYY') = TO_CHAR(TO_DATE(p_date_document, 'DD-MM-YYYY'), 'MM-YYYY');
    
END GetDocumentsLoyer;


    -- Implémentation pour récupérer un bien en fonction d'un document comptable
    PROCEDURE GetBienByDocument (
        p_date_document IN VARCHAR2,
        p_numero_document IN sae_document_comptable.numero_document%TYPE,
        p_result OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_result FOR
        SELECT sbl.*
        FROM SAE_BIEN_LOCATIF sbl, SAE_LOCATAIRE sl, SAE_CONTRACTER sc, SAE_BAIL sb, 
             SAE_FACTURE_DU_BIEN sfdb, SAE_DOCUMENT_COMPTABLE sdc
        WHERE sbl.identifiant_logement = sb.identifiant_logement
          AND sb.id_bail = sc.id_bail
          AND sc.identifiant_locataire = sl.identifiant_locataire
          AND sbl.identifiant_logement = sfdb.identifiant_logement
          AND sfdb.numero_document = sdc.numero_document
          AND sfdb.date_document = sdc.date_document
          AND sdc.identifiant_locataire = sl.identifiant_locataire
          AND TO_CHAR(sdc.date_document, 'MM-YYYY') = TO_CHAR(TO_DATE(p_date_document, 'DD-MM-YYYY'), 'MM-YYYY')
          AND sdc.numero_document = p_numero_document;
    END GetBienByDocument;

    PROCEDURE GetBailByDocument (
        p_date_document IN VARCHAR2,
        p_numero_document IN sae_document_comptable.numero_document%TYPE,
        p_result OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_result FOR
        SELECT sb.* 
	        FROM sae_bien_locatif sbl, sae_locataire sl, sae_contracter sc, sae_bail sb, 
	             sae_facture_du_bien sfdb, sae_document_comptable sdc
	        WHERE sbl.identifiant_logement = sb.identifiant_logement
	          AND sb.id_bail = sc.id_bail
	          AND sc.identifiant_locataire = sl.identifiant_locataire
	          AND sbl.identifiant_logement = sfdb.identifiant_logement
	          AND sfdb.numero_document = sdc.numero_document
	          AND sfdb.date_document = sdc.date_document
	          AND sdc.identifiant_locataire = sl.identifiant_locataire
	          AND TO_CHAR(sdc.date_document, 'MM-YYYY') = TO_CHAR(TO_DATE(p_date_document, 'DD-MM-YYYY'), 'MM-YYYY')
	          AND sdc.numero_document = p_numero_document;
    END GetBailByDocument;

    PROCEDURE GetPPCDocCompt (
        p_identifiant_locataire IN sae_document_comptable.identifiant_locataire%TYPE,
        p_identifiant_logement IN sae_facture_du_bien.identifiant_logement%TYPE,
        p_date_document_1 IN VARCHAR2,
        p_date_document_2 IN VARCHAR2,
        p_result OUT SYS_REFCURSOR
    )IS
    BEGIN
    IF TO_DATE(p_date_document_1, 'DD-MM-YYYY') < TO_DATE(p_date_document_2, 'DD-MM-YYYY') THEN
        OPEN p_result FOR
        SELECT spc.id_bail, spc.date_changement, (spc.PROVISION_POUR_CHARGE * sc.part_de_loyer) AS PROVISION_POUR_CHARGE 
	        FROM SAE_PROVISION_CHARGE spc, SAE_BAIL sb, SAE_CONTRACTER sc, 
	             SAE_BIEN_LOCATIF sbl, SAE_LOCATAIRE sloc
	        WHERE sbl.identifiant_logement = sb.identifiant_logement
	          AND sb.id_bail = spc.id_bail
	          AND sb.id_bail = sc.id_bail
	          AND sc.identifiant_locataire = sloc.identifiant_locataire
	          AND spc.date_changement BETWEEN TO_DATE(p_date_document_1, 'DD-MM-YYYY') AND TO_DATE(p_date_document_2, 'DD-MM-YYYY')
	          AND sloc.identifiant_locataire = p_identifiant_locataire
	          AND sbl.identifiant_logement = p_identifiant_logement;
    END IF;
    END GetPPCDocCompt;

    PROCEDURE GetPPCLocBien (
    p_identifiant_locataire IN sae_contracter.identifiant_locataire%TYPE,
    p_identifiant_logement IN sae_bail.identifiant_logement%TYPE,
    p_result OUT SYS_REFCURSOR
) IS
BEGIN
    -- Cas où les deux paramètres sont NULL
    IF p_identifiant_locataire IS NULL AND p_identifiant_logement IS NULL THEN
        OPEN p_result FOR
        SELECT spc.*
        FROM sae_contracter sc, sae_bail sb, sae_provision_charge spc
        WHERE sb.id_bail = sc.id_bail
          AND sb.id_bail = spc.id_bail;

    -- Cas où les deux paramètres sont non NULL
    ELSIF p_identifiant_locataire IS NOT NULL AND p_identifiant_logement IS NOT NULL THEN
        OPEN p_result FOR
        SELECT spc.*
        FROM sae_contracter sc, sae_bail sb, sae_provision_charge spc
        WHERE sb.id_bail = sc.id_bail
          AND sb.id_bail = spc.id_bail
          AND sc.identifiant_locataire = p_identifiant_locataire
          AND sb.identifiant_logement = p_identifiant_logement;

    -- Cas non pris en charge (optionnel)
    ELSE
        RAISE_APPLICATION_ERROR(-20001, 'Paramètres invalides ou incomplets.');
    END IF;
END GetPPCLocBien;


END Quittance;