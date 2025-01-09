CREATE TABLE SAE_assurance(
   numero_de_contrat VARCHAR2(50),
   annee_du_contrat NUMBER(10),
   Type_de_contrat VARCHAR2(50),
   CONSTRAINT pk_SAE_assurance PRIMARY KEY (numero_de_contrat, annee_du_contrat)
);

CREATE TABLE SAE_ICC(
   Annee_ICC CHAR(4),
   trimestre_ICC CHAR(1),
   indice NUMBER(10) NOT NULL,
   CONSTRAINT pk_SAE_ICC PRIMARY KEY (Annee_ICC, trimestre_ICC)
);

CREATE TABLE SAE_Adresse(
   Id_SAE_Adresse VARCHAR2(20),
   adresse VARCHAR2(50) NOT NULL,
   Code_postal NUMBER(10) NOT NULL,
   ville VARCHAR2(50) NOT NULL,
   Complement_adresse VARCHAR2(50),
   CONSTRAINT pk_SAE_Adresse PRIMARY KEY (Id_SAE_Adresse)
);

CREATE TABLE SAE_Locataire(
   identifiant_locataire VARCHAR2(50),
   Nom_locataire VARCHAR2(50) NOT NULL,
   Prenom_locataire VARCHAR2(50) NOT NULL,
   email_locataire VARCHAR2(50),
   telephone_locataire VARCHAR2(50),
   date_naissance DATE NOT NULL,
   Lieu_de_naissance VARCHAR2(50),
   Id_SAE_Adresse VARCHAR2(20),
   CONSTRAINT pk_SAE_Locataire PRIMARY KEY (identifiant_locataire),
   CONSTRAINT fk_SAE_Loc_Id_SAE_Adr FOREIGN KEY (Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_batiment(
   identifiant_batiment VARCHAR2(50),
   Id_SAE_Adresse VARCHAR2(20) NOT NULL,
   CONSTRAINT pk_SAE_batiment PRIMARY KEY (identifiant_batiment),
   CONSTRAINT fk_SAE_bat_Id_SAE_Adr FOREIGN KEY (Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_Cautionnaire(
   Id_Caution NUMBER(10),
   Nom_ou_organisme VARCHAR2(70) NOT NULL,
   Prenom VARCHAR2(50),
   Description_du_cautionnaire VARCHAR2(255),
   Id_SAE_Adresse VARCHAR2(20),
   CONSTRAINT pk_SAE_Cautionnaire PRIMARY KEY (Id_Caution),
   CONSTRAINT fk_SAE_Cautio_Id_SAE_Adr FOREIGN KEY (Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_entreprise(
   SIRET VARCHAR2(50),
   secteur_d_activite VARCHAR2(50) NOT NULL,
   nom VARCHAR2(50),
   Id_SAE_Adresse VARCHAR2(20) NOT NULL,
   CONSTRAINT pk_SAE_entreprise PRIMARY KEY (SIRET),
   CONSTRAINT fk_SAE_entr_Id_SAE_Adr FOREIGN KEY (Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_Bien_locatif(
   identifiant_logement VARCHAR2(50),
   Loyer_de_base NUMBER(15,2) NOT NULL,
   identifiant_fiscal VARCHAR2(50),
   complement_d_adresse VARCHAR2(50),
   surface NUMBER(10),
   nb_piece NUMBER(10),
   Type_de_bien VARCHAR2(50) NOT NULL,
   identifiant_batiment VARCHAR2(50) NOT NULL,
   CONSTRAINT pk_SAE_Bien_locatif PRIMARY KEY (identifiant_logement),
   CONSTRAINT fk_SAE_Bien_loc_id_bat FOREIGN KEY (identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment)
);

CREATE TABLE SAE_document_comptable(
   numero_document VARCHAR2(50),
   Date_document DATE,
   Type_de_document VARCHAR2(50) NOT NULL,
   montant NUMBER(10,2),
   fichier_document VARCHAR2(255),
   montant_devis NUMBER(10,2),
   recuperable_locataire NUMBER(1),
   identifiant_locataire VARCHAR2(50),
   identifiant_batiment VARCHAR2(50),
   SIRET VARCHAR2(50),
   numero_de_contrat VARCHAR2(50),
   annee_du_contrat NUMBER(10),
   CONSTRAINT pk_SAE_document_comptable PRIMARY KEY (numero_document, Date_document),
   CONSTRAINT fk_SAE_doc_compta_id_loc FOREIGN KEY (identifiant_locataire) REFERENCES SAE_Locataire(identifiant_locataire),
   CONSTRAINT fk_SAE_doc_compta_id_bat FOREIGN KEY (identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment),
   CONSTRAINT fk_SAE_doc_compta_SIRET FOREIGN KEY (SIRET) REFERENCES SAE_entreprise(SIRET),
   CONSTRAINT fk_SAE_doc_compta_contrat FOREIGN KEY (numero_de_contrat, annee_du_contrat) REFERENCES SAE_assurance(numero_de_contrat, annee_du_contrat)
);


CREATE TABLE sae_charge_index (
   id_charge_index VARCHAR2(50),
   Date_de_releve DATE,
   
   Type VARCHAR2(50),
   Valeur_compteur NUMBER(15,3) NOT NULL,
   Cout_variable_unitaire NUMBER(15,5) NOT NULL,
   Cout_fixe NUMBER(10,2),

   numero_document VARCHAR2(50) NOT NULL,
   Date_document DATE NOT NULL,

   Date_releve_precedent DATE,
   id_charge_index_preced VARCHAR2(50),

   CONSTRAINT pk_sae_charge_index PRIMARY KEY (id_charge_index, date_de_releve),
   CONSTRAINT uq_sae_charge_i_num_date UNIQUE (numero_document, Date_document),
   CONSTRAINT uq_sae_charge_preced UNIQUE (id_charge_index_preced, Date_releve_precedent),
   CONSTRAINT fk_sae_charge_i_num_date FOREIGN KEY (numero_document, Date_document) REFERENCES SAE_document_comptable(numero_document, Date_document),
   CONSTRAINT fk_SAE_charge_preced FOREIGN KEY (id_charge_index_preced, Date_releve_precedent) REFERENCES sae_charge_index(id_charge_index, Date_de_releve)
);




CREATE TABLE SAE_Charge_cf(
   id_charge_cf VARCHAR2(50) ,
   Date_de_charge DATE NOT NULL,
   Type VARCHAR2(50)  NOT NULL,
   montant NUMBER(10,2)   NOT NULL,
   numero_document VARCHAR2(50) NOT NULL,
   Date_document DATE NOT NULL,
   CONSTRAINT pk_SAE_Charge_cf PRIMARY KEY (id_charge_cf),
   CONSTRAINT uq_SAE_Charge_cf_num_date UNIQUE (numero_document, Date_document),
   CONSTRAINT fk_SAE_Charge_cf_num_date FOREIGN KEY (numero_document, Date_document) REFERENCES SAE_document_comptable(numero_document, Date_document)
);

CREATE TABLE SAE_Bail(
   Id_bail VARCHAR2(50),
   Date_de_debut DATE NOT NULL,
   date_de_fin DATE,
   identifiant_logement VARCHAR2(50),
   CONSTRAINT pk_SAE_Bail PRIMARY KEY (Id_bail),
   CONSTRAINT fk_SAE_Bail_id_log FOREIGN KEY (identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE SAE_Document(
   Id_bail VARCHAR2(50),
   Date_document DATE,
   type_de_document VARCHAR2(50) NOT NULL,
   url_document VARCHAR2(200) NOT NULL,
   CONSTRAINT pk_SAE_Document PRIMARY KEY (Id_bail, Date_document),
   CONSTRAINT fk_SAE_Doc_Id_bail FOREIGN KEY (Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE SAE_Regularisation(
   Id_bail VARCHAR2(50),
   Date_regu DATE,
   montant NUMBER(10,2) NOT NULL,
   CONSTRAINT pk_SAE_Regularisation PRIMARY KEY (Id_bail, Date_regu),
   CONSTRAINT fk_SAE_Regu_Id_bail FOREIGN KEY (Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE SAE_diagnostiques(
   Date_diagnostique DATE,
   identifiant VARCHAR2(50),
   Resultats_des_diagnostiques VARCHAR2(50) NOT NULL,
   fichier_diagnostique VARCHAR2(255),
   identifiant_batiment VARCHAR2(50),
   identifiant_logement VARCHAR2(50),
   CONSTRAINT pk_SAE_diagnostiques PRIMARY KEY (Date_diagnostique, identifiant),
   CONSTRAINT fk_SAE_diag_id_bat FOREIGN KEY (identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment),
   CONSTRAINT fk_SAE_diag_id_loge FOREIGN KEY (identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE sae_loyer(
   identifiant_logement VARCHAR2(50),
   date_de_changement DATE,
   montant_loyer NUMBER(10,2)  NOT NULL,
   CONSTRAINT pk_sae_loyer PRIMARY KEY (identifiant_logement, date_de_changement),
   CONSTRAINT fk_sae_loyer_id_loge FOREIGN KEY (identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE SAE_Provision_charge(
   Id_bail VARCHAR2(50),
   date_changement DATE,
   provision_pour_charge NUMBER(10,2)  NOT NULL,
   CONSTRAINT pk_SAE_Provision_charge PRIMARY KEY (Id_bail, date_changement),
   CONSTRAINT fk_SAE_Prov_charge_Id_bail FOREIGN KEY (Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_etre_lie(
   identifiant_locataire1 VARCHAR2(50),
   identifiant_locataire2 VARCHAR2(50),
   Lien_familiale VARCHAR2(50),
   Colocataire NUMBER(1) NOT NULL,
   CONSTRAINT pk_sae_etre_lie PRIMARY KEY (identifiant_locataire1, identifiant_locataire2),
   CONSTRAINT fk_sae_etre_lie_id_loc1 FOREIGN KEY (identifiant_locataire1) REFERENCES SAE_Locataire(identifiant_locataire),
   CONSTRAINT fk_sae_etre_lie_id_loc2 FOREIGN KEY (identifiant_locataire2) REFERENCES SAE_Locataire(identifiant_locataire)
);

CREATE TABLE sae_Cautionner(
   Id_Caution NUMBER(10),
   Id_bail VARCHAR2(50),
   Montant NUMBER(10,2) ,
   Fichier_caution VARCHAR2(255),
   CONSTRAINT pk_sae_Cautionner PRIMARY KEY (Id_Caution, Id_bail),
   CONSTRAINT fk_sae_Cautionner_Id_Cau FOREIGN KEY (Id_Caution) REFERENCES SAE_Cautionnaire(Id_Caution),
   CONSTRAINT fk_sae_Cautionner_Id_bail FOREIGN KEY (Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_contracter(
   identifiant_locataire VARCHAR2(50),
   Id_bail VARCHAR2(50),
   date_de_sortie DATE,
   date_d_entree DATE NOT NULL,
   part_de_loyer NUMBER(3,2),
   CONSTRAINT pk_sae_contracter PRIMARY KEY (identifiant_locataire, Id_bail),
   CONSTRAINT fk_sae_contracter_id_loc FOREIGN KEY (identifiant_locataire) REFERENCES SAE_Locataire(identifiant_locataire),
   CONSTRAINT fk_sae_contracter_Id_bail FOREIGN KEY (Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_facture_du_bien(
   identifiant_logement VARCHAR2(50),
   numero_document VARCHAR2(50),
   Date_document DATE,
   part_des_charges NUMBER(3,2),
   CONSTRAINT pk_sae_facture_du_bien PRIMARY KEY (identifiant_logement, numero_document, Date_document),
   CONSTRAINT fk_sae_facture_id_loc FOREIGN KEY (identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement),
   CONSTRAINT fk_sae_facture_num_date FOREIGN KEY (numero_document, Date_document) REFERENCES SAE_document_comptable(numero_document, Date_document)
);

ALTER TABLE SAE_document_comptable
ADD CONSTRAINT chk_SAE_doc_compta_type
CHECK (
    (lower(Type_de_document) in ('quittance','loyer') AND SIRET IS NULL)
    OR
    (lower(Type_de_document) not in ('quittance','loyer') AND SIRET IS NOT NULL)
);

ALTER TABLE SAE_document_comptable
ADD CONSTRAINT chk_Loyer_doc_compta
CHECK (
    lower(type_de_document) = 'loyer' AND RECUPERABLE_LOCATAIRE = 1
    AND NUMERO_DE_CONTRAT IS NULL
    AND ANNEE_DU_CONTRAT IS NULL
    AND (MONTANT_DEVIS = 0 OR MONTANT_DEVIS IS NULL)
);
