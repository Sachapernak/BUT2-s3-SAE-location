create or replace procedure SAE_LocataireBail( 
    p_id_locataire sae_locataire.identifiant_locataire%type, 
    p_nom_locataire sae_locataire.Nom_locataire%type, 
    p_prenom_locataire sae_locataire.Prenom_locataire%type, 
    p_email_locataire sae_locataire.email_locataire%type, 
    p_telephone_locataire sae_locataire.telephone_locataire%type,
    p_date_naissance sae_locataire.date_naissance%type,
    p_lieu_de_naissance sae_locataire.Lieu_de_naissance%type,
    p_acte_de_caution sae_locataire.Acte_de_caution%type,
    p_adresse_locataire sae_locataire.Id_SAE_Adresse%type,
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type)
    as
    begin
        if (p_id_locataire is null or p_nom_locataire is null or p_prenom_locataire is null or p_date_naissance is null) then
            raise_application_error(-20020, 'L''identifiant du locataire, le nom locataire, le prenom locataire et la date de naissance doivent etre renseignes');
        end if;
        

        insert into sae_LOCATAIRE values (p_id_locataire, p_nom_locataire, p_prenom_locataire, p_email_locataire, p_telephone_locataire,p_date_naissance, p_lieu_de_naissance , p_acte_de_caution, p_adresse_locataire);
            exception 
                when dup_val_on_index then raise_application_error(-20021, 'Le locataire est deja dans la base de donnees');

        if (P_ID_ADRESSE is null or p_adresse is null or p_code_postal is null or p_ville is null) then
            raise_application_error(-20010, 'L''identifiant de l''adresse, l''adresse mail, le code postal et la ville doivent etre renseignes');
        end if;
        

        insert into sae_ADRESSE values (P_ID_ADRESSE, p_adresse, p_code_postal, p_ville, p_complement_adresse);
            exception 
                when dup_val_on_index then raise_application_error(-20011, 'L''adresse est deja dans la base de donnees');



    as
    begin
        
