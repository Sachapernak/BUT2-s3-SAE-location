set serveroutput on 
--Creation du paquetage (l'entite) d'une liste d'adresse
create or replace package SAE_DAO_ADRESSE as 

    procedure SAE_CREATE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type);

    procedure SAE_DELETE(p_id_adresse sae_adresse.id_sae_adresse%type);

    procedure SAE_UPDATE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type);
    
    
end SAE_DAO_ADRESSE;
/
CREATE OR REPLACE PACKAGE BODY SAE_DAO_ADRESSE AS
    
    procedure SAE_CREATE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type)
    as
    begin
        if (P_ID_ADRESSE is null or p_adresse is null or p_code_postal is null or p_ville is null) then
            raise_application_error(-20011, 'L''identifiant de l''adresse, l''adresse mail, le code postal et la ville doivent etre renseignes');
        end if;
        

        insert into sae_ADRESSE values (P_ID_ADRESSE, p_adresse, p_code_postal, p_ville, p_complement_adresse);
            exception 
                when dup_val_on_index then raise_application_error(-20012, 'L''adresse est deja dans la base de donnees');

    end SAE_CREATE;    


    procedure SAE_DELETE(p_id_adresse sae_adresse.id_sae_adresse%type) as

    begin

        delete from sae_adresse where id_sae_adresse = p_id_adresse;
    end SAE_DELETE;

    procedure SAE_UPDATE(
    P_ID_ADRESSE sae_ADRESSE.id_sae_adresse%type, 
    p_adresse sae_ADRESSE.adresse%type, 
    p_code_postal sae_ADRESSE.code_postal_%type, 
    p_ville sae_ADRESSE.ville%type, 
    p_complement_adresse sae_ADRESSE.complement_adresse%type)
    as
    begin
        if (P_ID_ADRESSE is null) then
            raise_application_error(-20013, 'Vous devez renseigner l''identifiant, sans cela, on ne pourra pas cibler la ligne a modifier.');
        end if;
        if (p_adresse is null and p_code_postal is null and p_ville is null and p_complement_adresse is null) then
            raise_application_error(-20014, 'Vous devez renseigner des valeurs a modifier');

        end if;

        UPDATE sae_ADRESSE 
        set adresse = p_adresse, code_postal_ = p_code_postal, ville = p_ville, complement_adresse = p_complement_adresse
        where id_sae_adresse = p_id_adresse;
            
    end SAE_UPDATE;

END SAE_DAO_ADRESSE;
