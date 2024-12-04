CREATE TABLE SAE_Bail(
   Id_bail VARCHAR2(50) ,
   Date_de_debut DATE NOT NULL,
   date_de_fin DATE,
   PRIMARY KEY(Id_bail)
);

CREATE TABLE SAE_assurance(
   numero_de_contrat VARCHAR2(50) ,
   annee_du_contrat NUMBER(10),
   Type_de_contrat VARCHAR2(50) ,
   prime NUMBER(15,2)  ,
   quotite_jursiprudence NUMBER(3,2)  ,
   montant_quoitite NUMBER(15,2)  ,
   PRIMARY KEY(numero_de_contrat, annee_du_contrat)
);

CREATE TABLE SAE_Document(
   Id_bail VARCHAR2(50) ,
   Date_document DATE,
   type_de_document VARCHAR2(50)  NOT NULL,
   url_document VARCHAR(200)  NOT NULL,
   PRIMARY KEY(Id_bail, Date_document),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE SAE_Regularisation(
   Id_bail VARCHAR2(50) ,
   Date_regu DATE,
   montant VARCHAR2(50)  NOT NULL,
   PRIMARY KEY(Id_bail, Date_regu),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE SAE_Adresse(
   Id_SAE_Adresse VARCHAR2(20) ,
   adresse VARCHAR2(50)  NOT NULL,
   Code_postal NUMBER(10) NOT NULL,
   ville VARCHAR2(50)  NOT NULL,
   Complement_adresse VARCHAR2(50) ,
   PRIMARY KEY(Id_SAE_Adresse)
);

CREATE TABLE SAE_Provision_charge(
   Id_bail VARCHAR2(50) ,
   date_changement DATE,
   provision_pour_charge NUMBER(15,2)   NOT NULL,
   PRIMARY KEY(Id_bail, date_changement),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE SAE_Locataire(
   identifiant_locataire VARCHAR2(50) ,
   Nom_locataire VARCHAR2(50)  NOT NULL,
   Prenom_locataire VARCHAR2(50)  NOT NULL,
   email_locataire VARCHAR2(50) ,
   telephone_locataire VARCHAR2(50) ,
   date_naissance DATE NOT NULL,
   Lieu_de_naissance VARCHAR2(50) ,
   Acte_de_caution VARCHAR2(200) ,
   Id_SAE_Adresse VARCHAR2(20) ,
   PRIMARY KEY(identifiant_locataire),
   FOREIGN KEY(Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_batiment(
   identifiant_batiment VARCHAR2(50) ,
   Id_SAE_Adresse VARCHAR2(20)  NOT NULL,
   PRIMARY KEY(identifiant_batiment),
   FOREIGN KEY(Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_Cautionnaire(
   Id_Caution NUMBER(10),
   Nom_ou_organisme VARCHAR2(70)  NOT NULL,
   Prenom VARCHAR2(50) ,
   Description_du_cautionnaire VARCHAR2(200) ,
   Id_SAE_Adresse VARCHAR2(20) ,
   PRIMARY KEY(Id_Caution),
   FOREIGN KEY(Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_entreprise(
   SIRET VARCHAR2(50) ,
   secteur_d_activite VARCHAR2(50)  NOT NULL,
   nom VARCHAR2(50) ,
   Id_SAE_Adresse VARCHAR2(20)  NOT NULL,
   PRIMARY KEY(SIRET),
   FOREIGN KEY(Id_SAE_Adresse) REFERENCES SAE_Adresse(Id_SAE_Adresse)
);

CREATE TABLE SAE_Bien_locatif(
   identifiant_logement VARCHAR2(50) ,
   Loyer_de_base NUMBER(15,2)   NOT NULL,
   identifiant_fiscal VARCHAR2(50) ,
   complement_d_adresse VARCHAR2(50) ,
   regularisation_des_charges VARCHAR2(50) ,
   surface NUMBER(10),
   nb_piece NUMBER(10),
   Type_de_bien VARCHAR2(50)  NOT NULL,
   part_des_charges NUMBER(3,2)  ,
   identifiant_batiment VARCHAR2(50)  NOT NULL,
   PRIMARY KEY(identifiant_logement),
   FOREIGN KEY(identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment)
);

CREATE TABLE SAE_document_comptable(
   numero_document VARCHAR2(50) ,
   Date_document DATE,
   Type_de_document VARCHAR2(50)  NOT NULL,
   montant VARCHAR2(50) ,
   fichier_document VARCHAR2(200) ,
   montant_devis VARCHAR2(50) ,
   recuperable_locataire NUMBER(1),
   identifiant_locataire VARCHAR2(50) ,
   identifiant_batiment VARCHAR2(50) ,
   SIRET VARCHAR2(50) ,
   numero_de_contrat VARCHAR2(50) ,
   annee_du_contrat NUMBER(10),
   identifiant_logement VARCHAR2(50) ,
   PRIMARY KEY(numero_document, Date_document),
   FOREIGN KEY(identifiant_locataire) REFERENCES SAE_Locataire(identifiant_locataire),
   FOREIGN KEY(identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment),
   FOREIGN KEY(SIRET) REFERENCES SAE_entreprise(SIRET),
   FOREIGN KEY(numero_de_contrat, annee_du_contrat) REFERENCES SAE_assurance(numero_de_contrat, annee_du_contrat),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE sae_charge_index(
   Date_de_releve DATE,
   Type VARCHAR2(50) ,
   valeur NUMBER(15,2)   NOT NULL,
   Date_releve_precedent DATE,
   Cout_variable NUMBER(15,2)   NOT NULL,
   Cout_fixe VARCHAR2(50) ,
   numero_document VARCHAR2(50)  NOT NULL,
   Date_document DATE NOT NULL,
   PRIMARY KEY(Date_de_releve, Type),
   UNIQUE(numero_document, Date_document),
   FOREIGN KEY(numero_document, Date_document) REFERENCES SAE_document_comptable(numero_document, Date_document)
);

CREATE TABLE SAE_Charge_cf(
   Date_de_charge DATE,
   Type VARCHAR2(50) ,
   montant VARCHAR2(50)  NOT NULL,
   numero_document VARCHAR2(50)  NOT NULL,
   Date_document DATE NOT NULL,
   PRIMARY KEY(Date_de_charge, Type),
   UNIQUE(numero_document, Date_document),
   FOREIGN KEY(numero_document, Date_document) REFERENCES SAE_document_comptable(numero_document, Date_document)
);

CREATE TABLE SAE_diagnostiques(
   Date_diagnostique DATE,
   identifiant VARCHAR2(50) ,
   Resultats_des_diagnostiques VARCHAR2(50)  NOT NULL,
   fichier_diagnostique VARCHAR2(200) ,
   identifiant_batiment VARCHAR2(50) ,
   identifiant_logement VARCHAR2(50) ,
   PRIMARY KEY(Date_diagnostique, identifiant),
   FOREIGN KEY(identifiant_batiment) REFERENCES SAE_batiment(identifiant_batiment),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE SAE_ICC(
   identifiant_logement VARCHAR2(50) ,
   Annee_ICC CHAR(4) ,
   trimestre_ICC CHAR(1) ,
   indice NUMBER(10) NOT NULL,
   PRIMARY KEY(identifiant_logement, Annee_ICC, trimestre_ICC),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE sae_loyer(
   identifiant_logement VARCHAR2(50) ,
   date_de_changement DATE,
   montant_loyer NUMBER(15,2)   NOT NULL,
   PRIMARY KEY(identifiant_logement, date_de_changement),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement)
);

CREATE TABLE sae_etre_lie(
   identifiant_locataire VARCHAR2(50) ,
   identifiant_locataire_1 VARCHAR2(50) ,
   Lien_familiale VARCHAR2(50) ,
   Colocataire NUMBER(1) NOT NULL,
   PRIMARY KEY(identifiant_locataire, identifiant_locataire_1),
   FOREIGN KEY(identifiant_locataire) REFERENCES SAE_Locataire(identifiant_locataire),
   FOREIGN KEY(identifiant_locataire_1) REFERENCES SAE_Locataire(identifiant_locataire)
);

CREATE TABLE sae_Cautionner(
   Id_Caution NUMBER(10),
   Id_bail VARCHAR2(50) ,
   Montant NUMBER(15,2)  ,
   Fichier_caution VARCHAR2(200) ,
   PRIMARY KEY(Id_Caution, Id_bail),
   FOREIGN KEY(Id_Caution) REFERENCES SAE_Cautionnaire(Id_Caution),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_contracter(
   identifiant_locataire VARCHAR2(50) ,
   Id_bail VARCHAR2(50) ,
   date_de_sortie DATE,
   date_d_entree DATE NOT NULL,
   part_de_loyer NUMBER(3,2)  ,
   PRIMARY KEY(identifiant_locataire, Id_bail),
   FOREIGN KEY(identifiant_locataire) REFERENCES SAE_Locataire(identifiant_locataire),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_mettre_en_location(
   identifiant_logement VARCHAR2(50) ,
   Id_bail VARCHAR2(50) ,
   PRIMARY KEY(identifiant_logement, Id_bail),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement),
   FOREIGN KEY(Id_bail) REFERENCES SAE_Bail(Id_bail)
);

CREATE TABLE sae_comprendre_charge_variable(
   identifiant_logement VARCHAR2(50) ,
   Date_de_releve DATE,
   Type VARCHAR2(50) ,
   part_des_charges NUMBER(3,2)  ,
   PRIMARY KEY(identifiant_logement, Date_de_releve, Type),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement),
   FOREIGN KEY(Date_de_releve, Type) REFERENCES sae_charge_index(Date_de_releve, Type)
);

CREATE TABLE sae_comprendre_cf(
   identifiant_logement VARCHAR2(50) ,
   Date_de_charge DATE,
   Type VARCHAR2(50) ,
   part_des_charges NUMBER(3,2)  ,
   PRIMARY KEY(identifiant_logement, Date_de_charge, Type),
   FOREIGN KEY(identifiant_logement) REFERENCES SAE_Bien_locatif(identifiant_logement),
   FOREIGN KEY(Date_de_charge, Type) REFERENCES SAE_Charge_cf(Date_de_charge, Type)
);




ALTER TABLE SAE_document_comptable
ADD CONSTRAINT chk_SAE_doc_compta_type
CHECK (
    (Type_de_document!= 'quittance' OR SIRET IS NULL) AND
    (Type_de_document = 'quittance' OR SIRET IS NOT NULL)
);

