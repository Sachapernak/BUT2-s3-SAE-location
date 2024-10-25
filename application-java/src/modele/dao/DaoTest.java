/*select * from TM_ENSEIGNER
where    (DEBSEMC, JOURC, HEUREDC, GRPC) in 
        (('05/06/17', 'mardi', '08:00', 'InS2E2'),
         ('27/02/17', 'mardi', '09:30', 'InS2A2'),
         ('17/04/17', 'mardi', '09:30', 'InS2B2'),
         ('20/03/17', 'mardi', '11:00', 'InS2C2'),
         ('05/06/17', 'mardi', '11:00', 'InS1D1'),
         ('24/04/17', 'mardi', '14:00', 'InS2C1'),
         ('01/05/17', 'mardi', '14:00', 'InS2C1'),
         ('17/04/17', 'mardi', '14:00', 'InS2A'),
         ('15/05/17', 'mardi', '14:00', 'InS2A'),
         ('27/03/17', 'mardi', '15:30', 'InS2E2')) ;
         
select * from TM_FORMATION
where    GRPC in 
        ('InS2E2','InS2A2', 'InS2B2', 'InS2C2', 'InS1D1', 'InS2C1', 'InS2A', 'InS2E2') ;      
        
SELECT  FROM TM_ENSEIGNER WHERE rownum <=10; 

select 'Formation f'||IDFOR||' = new Formation ("' || IDFOR || '", "' || LIBF || '", ' || NBGRC || ', ' || NBGRTD || ', '|| NBGRTP || ') ;'
from TM_FORMATION ;

select 'Formation f1 = new Formation ("' || IDFOR || '", "' || LIBF || '", ' || NBGRC || ', ' || NBGRTD || ', '|| NBGRTP || ') ;'
from TM_FORMATION ;

select * from TM_ENSEIGNER
where rownum < 11 ;

create or replace view VueS3C01 as select * from TM_ENSEIGNER where rownum < 11 ;

select 'Creneau c'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC||' = new Creneau ("' || DEBSEMC || '", "' || JOURC || '", "' || HEUREDC || '", "' || TYPEC || '", "'|| 
HEUREFC || '", m' || MATC || ', g' || GRPC || ' ) ;'
from TM_CRENEAU
where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
and rownum < 11 ;

select 'Mat m' || MATC ||' = new Mat ("' || Matc || '", "' || Intc || '") ;'
from TM_MAT
where MATC in 
    (select distinct MATC
    from TM_CRENEAU
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 );



select DEBSEMC, JOURC, HEUREDC, GRPC
from TM_CRENEAU
where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
and rownum < 11 ;

select 'Formation f'||IDFOR||' = new Formation ("' || IDFOR || '", "' || LIBF || '", ' || NBGRC || ', ' || NBGRTD || ', '|| NBGRTP || ') ;'
from TM_FORMATION ;
         
         
select 'Enseignant e' || IDENSEIGN ||' = new Enseignant ("' || IDENSEIGN || '", "' || NOM ||'", "' || PRENOM ||'", ' || nvl(NBHDISP, 0) || 
        ', s' || GRADE || ') ;'
from TM_ENSEIGNANT
where IDENSEIGN in 
    (select distinct ENSC
    from TM_ENSEIGNER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 );
    
 select 'Statut s'||GRADE||' = new Statut ("' || GRADE || '", ' || NBHEURST || ') ;'
from TM_STATUT ;   



select distinct 'java.util.Collection<Enseignant> en' || to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || 
        to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC  ||' = new HashSet<>() ;'
    from TM_ENSEIGNER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 
union         
select  'en'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC ||
        '.add(' || 'e' || ensc || ') ;' 
    from TM_ENSEIGNER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 
union 
 select distinct 'c'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC ||
        '.setEnsC(en'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC || ') ;'
    from TM_ENSEIGNER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11   ;
    
    
    
Select 'Salle sa'||NSALLE||' = new Salle ("' || NSALLE || '", "' || TSAL || '", ' || CAPACITE ||') ;'
from TM_SALLE 
where NSALLE in
    (select distinct SALC
    from TM_AFFECTER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 );
    
    
select distinct 'java.util.Collection<Salle> salle' || to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || 
        to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC  ||' = new HashSet<>() ;'
    from TM_AFFECTER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 
union     
select  'salle'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC ||
        '.add(' || 'sa' || SALC || ') ;' 
    from TM_AFFECTER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11 
union 
 select distinct 'c'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC ||
        '.setSalC(salle'||to_char(to_date(DEBSEMC, 'DD/MM/YY'), 'DDMMYY') || JOURC || to_char(to_date(HEUREDC, 'HH24:MI'), 'HHMI') || GRPC || ') ;'
    from TM_AFFECTER
    where (DEBSEMC, JOURC, HEUREDC, GRPC) in (select DEBSEMC, JOURC, HEUREDC, GRPC from VueS3C01) 
    and rownum < 11   ;   */ 

package modele.dao;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import modele.Creneau;
import modele.Enseignant;
import modele.Formation;
import modele.Groupe;
import modele.Mat;
import modele.Salle;
import modele.Statut;

public class DaoTest 
{
	public static final Collection<Creneau> CRENEAUX = new ArrayList<> () ;
	public static final Collection<Enseignant> ENSEIGNANTS = new ArrayList<> () ;
	public static final Collection<Formation> FORMATIONS = new ArrayList<> () ;
	public static final Collection<Groupe> GROUPES = new ArrayList<> () ;
	public static final Collection<Mat> MATS = new ArrayList<> () ;
	public static final Collection<Salle> SALLES = new ArrayList<> () ;
	public static final Collection<Statut> STATUTS = new ArrayList<> () ;

	static
	{	
		Formation fS1   = new Formation ("S1  ", "DUT Premiere Annee - Semestre 1", 1, 6, 12) ;
		Formation fAS   = new Formation ("AS  ", "DUT Annee Speciale", 1, 1, 1) ;
		Formation fS3   = new Formation ("S3  ", "DUT Seconde Annee - Semestre 1", 1, 5, 5) ;
		Formation fS2   = new Formation ("S2  ", "DUT Premiere Annee - Semestre 2", 1, 6, 6) ;
		Formation fDQL  = new Formation ("DQL ", "Licence Developpement Qualite Logiciel", 1, 1, 1) ;
		Formation fS4   = new Formation ("S4  ", "DUT Seconde Annee - Semestre 2", 1, 4, 4) ;
		Formation fGT   = new Formation ("GT  ", "Licence Bases de Donnees Avancees", 1, 2, 2) ;
		
		DaoTest.FORMATIONS.add(fS1) ;
		DaoTest.FORMATIONS.add(fAS) ;
		DaoTest.FORMATIONS.add(fS3) ;
		DaoTest.FORMATIONS.add(fS2) ;
		DaoTest.FORMATIONS.add(fDQL) ;
		DaoTest.FORMATIONS.add(fS4) ;
		DaoTest.FORMATIONS.add(fGT) ;

		Groupe gInAS = new Groupe ("InAS", 23, fAS ) ;
		Groupe gInS1A1 = new Groupe ("InS1A1", 15, fS1 ) ;
		Groupe gInS1A2 = new Groupe ("InS1A2", 14, fS1 ) ;
		Groupe gInS1B1 = new Groupe ("InS1B1", 14, fS1 ) ;
		Groupe gInS1B2 = new Groupe ("InS1B2", 14, fS1 ) ;
		Groupe gInS1C = new Groupe ("InS1C", 28, fS1 ) ;
		Groupe gInS1D = new Groupe ("InS1D", 30, fS1 ) ;
		Groupe gInS1E1 = new Groupe ("InS1E1", 14, fS1 ) ;
		Groupe gInS1E2 = new Groupe ("InS1E2", 15, fS1 ) ;
		Groupe gInS1F1 = new Groupe ("InS1F1", 15, fS1 ) ;			

		DaoTest.GROUPES.add(gInAS) ;
		DaoTest.GROUPES.add(gInS1A1) ;
		DaoTest.GROUPES.add(gInS1A2) ;
		DaoTest.GROUPES.add(gInS1B1) ;
		DaoTest.GROUPES.add(gInS1B2) ;
		DaoTest.GROUPES.add(gInS1C) ;
		DaoTest.GROUPES.add(gInS1D) ;
		DaoTest.GROUPES.add(gInS1E1) ;
		DaoTest.GROUPES.add(gInS1E2) ;
		DaoTest.GROUPES.add(gInS1F1) ;
		
		Mat mInM1101 = new Mat ("InM1101", "") ;
		Mat mInM1202 = new Mat ("InM1202", "Alg�bre lin�aire") ;
		Mat mInM1205 = new Mat ("InM1205", "Expression-Communication - Fondamentaux de la communication") ;
		Mat mInM1206 = new Mat ("InM1206", "Anglais et Informatique") ;
		Mat minA1109 = new Mat ("inA1109", "") ;
		
		DaoTest.MATS.add(mInM1101) ;
		DaoTest.MATS.add(mInM1202) ;
		DaoTest.MATS.add(mInM1205) ;
		DaoTest.MATS.add(mInM1206) ;
		DaoTest.MATS.add(minA1109) ;
				
		Creneau c190916jeudi0800InS1A1 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1206, gInS1A1 ) ;
		Creneau c190916jeudi0800InS1B2 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1205, gInS1B2 ) ;
		Creneau c190916jeudi0800InS1A2 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1206, gInS1A2 ) ;
		Creneau c190916jeudi0800InS1B1 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1205, gInS1B1 ) ;
		Creneau c190916jeudi0800InAS = new Creneau ("19/09/16", "jeudi", "08:00", "TD", "09:30", minA1109, gInAS ) ;
		Creneau c190916jeudi0800InS1E1 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1101, gInS1E1 ) ;
		Creneau c190916jeudi0800InS1C = new Creneau ("19/09/16", "jeudi", "08:00", "TD", "09:30", mInM1202, gInS1C ) ;
		Creneau c190916jeudi0800InS1D = new Creneau ("19/09/16", "jeudi", "08:00", "TD", "09:30", mInM1202, gInS1D ) ;
		Creneau c190916jeudi0800InS1E2 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1101, gInS1E2 ) ;
		Creneau c190916jeudi0800InS1F1 = new Creneau ("19/09/16", "jeudi", "08:00", "TP", "09:30", mInM1101, gInS1F1 ) ;

	
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1A1) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1B2) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1A2) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1B1) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InAS) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1E1) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1C) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1D) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1E2) ;
		DaoTest.CRENEAUX.add(c190916jeudi0800InS1F1) ;
		
		Statut sPRCE = new Statut ("PRCE", 384) ;
		Statut sVACA = new Statut ("VACA", 184) ;
		Statut sCDOC = new Statut ("CDOC", 64) ;
		Statut sATER = new Statut ("ATER", 96) ;
		Statut sPROF = new Statut ("PROF", 192) ;
		Statut sPRAG = new Statut ("PRAG", 384) ;
		Statut sMCF  = new Statut ("MCF ", 192) ;
		
		DaoTest.STATUTS.add(sPRCE) ;
		DaoTest.STATUTS.add(sVACA) ;
		DaoTest.STATUTS.add(sCDOC) ;
		DaoTest.STATUTS.add(sATER) ;
		DaoTest.STATUTS.add(sPROF) ;
		DaoTest.STATUTS.add(sPRAG) ;
		DaoTest.STATUTS.add(sMCF) ;

		Enseignant eADM = new Enseignant ("ADM", "AUDOUX", "Marguerite", 0, sMCF ) ;
		Enseignant eBZH = new Enseignant ("BZH", "BAZIN", "Herv�", 0, sMCF ) ;
		Enseignant eCMA = new Enseignant ("CMA", "CAMUS", "Albert", 0, sMCF ) ;
		Enseignant eDBJ = new Enseignant ("DBJ", "DU BELLAY", "Joachim", 0, sPRCE) ;
		Enseignant eDUA = new Enseignant ("DUA", "DAUDET", "Alphonse", 0, sMCF ) ;
		Enseignant eEUP = new Enseignant ("EUP", "ELUARD", "Paul", 0, sPRCE) ;
		Enseignant eFUA = new Enseignant ("FUA", "FOURNIER", "Alain", 0, sPRCE) ;
		Enseignant ePNF = new Enseignant ("PNF", "PONGE", "Francis", 0, sMCF ) ;
		Enseignant eSAA = new Enseignant ("SAA", "STAEL", "Anne Louise", 0, sPRCE) ;
		Enseignant eSRN = new Enseignant ("SRN", "SARRAUTE", "Nathalie", 0, sMCF ) ;
		
		DaoTest.ENSEIGNANTS.add(eADM) ;
		DaoTest.ENSEIGNANTS.add(eBZH) ;
		DaoTest.ENSEIGNANTS.add(eCMA) ;
		DaoTest.ENSEIGNANTS.add(eDBJ) ;
		DaoTest.ENSEIGNANTS.add(eDUA) ;
		DaoTest.ENSEIGNANTS.add(eEUP) ;
		DaoTest.ENSEIGNANTS.add(eFUA) ;		
		DaoTest.ENSEIGNANTS.add(ePNF) ;
		DaoTest.ENSEIGNANTS.add(eSAA) ;
		DaoTest.ENSEIGNANTS.add(eSRN) ;
		
		java.util.Collection<Enseignant> en190916jeudi0800InAS = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1A1 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1A2 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1B1 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1B2 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1C = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1D = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1E1 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1E2 = new HashSet<>() ;
		java.util.Collection<Enseignant> en190916jeudi0800InS1F1 = new HashSet<>() ;		
		
		c190916jeudi0800InAS.setEnsC(en190916jeudi0800InAS) ;
		c190916jeudi0800InS1A1.setEnsC(en190916jeudi0800InS1A1) ;
		c190916jeudi0800InS1A2.setEnsC(en190916jeudi0800InS1A2) ;
		c190916jeudi0800InS1B1.setEnsC(en190916jeudi0800InS1B1) ;
		c190916jeudi0800InS1B2.setEnsC(en190916jeudi0800InS1B2) ;
		c190916jeudi0800InS1C.setEnsC(en190916jeudi0800InS1C) ;
		c190916jeudi0800InS1D.setEnsC(en190916jeudi0800InS1D) ;
		c190916jeudi0800InS1E1.setEnsC(en190916jeudi0800InS1E1) ;
		c190916jeudi0800InS1E2.setEnsC(en190916jeudi0800InS1E2) ;
		c190916jeudi0800InS1F1.setEnsC(en190916jeudi0800InS1F1) ;
		
		en190916jeudi0800InAS.add(eADM) ;
		en190916jeudi0800InS1A1.add(eEUP) ;
		en190916jeudi0800InS1A2.add(eSRN) ;
		en190916jeudi0800InS1B1.add(eSAA) ;
		en190916jeudi0800InS1B2.add(eDBJ) ;
		en190916jeudi0800InS1C.add(ePNF) ;
		en190916jeudi0800InS1D.add(eBZH) ;
		en190916jeudi0800InS1E1.add(eCMA) ;
		en190916jeudi0800InS1E2.add(eDUA) ;
		en190916jeudi0800InS1F1.add(eFUA) ;

		
		Salle saIN116 = new Salle ("IN116", "TD", 30) ;
		Salle saIN204 = new Salle ("IN204", "TD", 30) ;
		Salle saIN205 = new Salle ("IN205", "TD", 30) ;
		Salle saIN210 = new Salle ("IN210", "TD", 30) ;
		Salle saIN212 = new Salle ("IN212", "TD", 30) ;
		Salle saInAmbre = new Salle ("InAmbre", "TP", 30) ;
		Salle saInDiamant = new Salle ("InDiamant", "TP", 30) ;
		Salle saInJade = new Salle ("InJade", "TP", 28) ;
		Salle saInOpale = new Salle ("InOpale", "TP", 30) ;
		
		DaoTest.SALLES.add(saIN116) ;
		DaoTest.SALLES.add(saIN204) ;
		DaoTest.SALLES.add(saIN205) ;
		DaoTest.SALLES.add(saIN210) ;
		DaoTest.SALLES.add(saIN212) ;
		DaoTest.SALLES.add(saInAmbre) ;
		DaoTest.SALLES.add(saInDiamant) ;		
		DaoTest.SALLES.add(saInJade) ;
		DaoTest.SALLES.add(saInOpale) ;
		
		java.util.Collection<Salle> salle190916jeudi0800InAS = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1A1 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1A2 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1B1 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1B2 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1C = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1D = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1E1 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1E2 = new HashSet<>() ;
		java.util.Collection<Salle> salle190916jeudi0800InS1F1 = new HashSet<>() ;
		
		c190916jeudi0800InAS.setSalC(salle190916jeudi0800InAS) ;
		c190916jeudi0800InS1A1.setSalC(salle190916jeudi0800InS1A1) ;
		c190916jeudi0800InS1A2.setSalC(salle190916jeudi0800InS1A2) ;
		c190916jeudi0800InS1B1.setSalC(salle190916jeudi0800InS1B1) ;
		c190916jeudi0800InS1B2.setSalC(salle190916jeudi0800InS1B2) ;
		c190916jeudi0800InS1C.setSalC(salle190916jeudi0800InS1C) ;
		c190916jeudi0800InS1D.setSalC(salle190916jeudi0800InS1D) ;
		c190916jeudi0800InS1E1.setSalC(salle190916jeudi0800InS1E1) ;
		c190916jeudi0800InS1E2.setSalC(salle190916jeudi0800InS1E2) ;
		c190916jeudi0800InS1F1.setSalC(salle190916jeudi0800InS1F1) ;

		salle190916jeudi0800InAS.add(saInDiamant) ;
		salle190916jeudi0800InS1A1.add(saIN116) ;
		salle190916jeudi0800InS1A2.add(saIN116) ;
		salle190916jeudi0800InS1B1.add(saIN210) ;
		salle190916jeudi0800InS1B2.add(saIN212) ;
		salle190916jeudi0800InS1C.add(saIN204) ;
		salle190916jeudi0800InS1D.add(saIN205) ;
		salle190916jeudi0800InS1E1.add(saInAmbre) ;
		salle190916jeudi0800InS1E2.add(saInOpale) ;
		salle190916jeudi0800InS1F1.add(saInJade) ;	
	}
	
// -------------------------- Creneau --------------------------------------------
	
	public static List<Creneau> selectCreneau (String... id)
	{

		List<Creneau> liste ;
		
		if (id.length != 0)
		{
			liste =  DaoTest.CRENEAUX.stream().filter(c -> 
					c.getDebsemC().equals(id[0]) && 
					c.getJourC().equals(id[1]) && 
					c.getHeureDC().equals(id[2]) &&
					c.getGrpC().getGrpC().equals(id[3])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.CRENEAUX) ;
		}
		return liste ;	
	}
	
	public static void insertCreneau(Creneau c)
	{
		DaoTest.CRENEAUX.add(c) ;
	}
	
	public static void updateCreneau(Creneau c)
	{
		List<Creneau> liste = DaoTest.selectCreneau(c.getDebsemC(), c.getJourC(), c.getHeureDC(), c.getGrpC().getGrpC()) ;
		Creneau d = liste.get(0) ;
		d.setHeureFC(c.getHeureFC());
		d.setTypeC(c.getTypeC());
		d.setMatC(c.getMatC());
		d.setEnsC(c.getEnsC());
		d.setGrpC(c.getGrpC());
		d.setSalC(c.getSalC());
	}	

	public static void deleteCreneau(Creneau c)
	{
		DaoTest.CRENEAUX.remove(c) ;
	}
	
// -------------------------- Enseignant ----------------------------------------	
	
	public static List<Enseignant> selectEnseignant (String... id)
	{
		List<Enseignant> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.ENSEIGNANTS.stream().filter(e -> e.getIdEnseign().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.ENSEIGNANTS) ;
		}
		return liste ;	
	}	
	
	public static void insertEnseignant(Enseignant e)
	{
		DaoTest.ENSEIGNANTS.add(e) ;
	}
	
	public static void updateEnseignant(Enseignant e)
	{
		List<Enseignant> liste = DaoTest.selectEnseignant(e.getIdEnseign()) ;
		Enseignant d = liste.get(0) ;
		d.setNbHDisp(e.getNbHDisp());
		d.setNom(e.getNom());
		d.setPrenom(e.getPrenom());
		d.setStatut(e.getStatut());
	}	
	
	public static void deleteEnseignant(Enseignant e)
	{
		DaoTest.ENSEIGNANTS.remove(e) ;
	}	
	
// -------------------------- Formation -----------------------------------------
	
	public static List<Formation> selectFormation (String... id)
	{
		List<Formation> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.FORMATIONS.stream().filter(f -> f.getIdFor().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.FORMATIONS) ;
		}
		return liste ;	
	}
	
	public static void insertFormation(Formation f)
	{
		DaoTest.FORMATIONS.add(f) ;
	}
	
	public static void updateFormation(Formation f)
	{
		List<Formation> liste = DaoTest.selectFormation(f.getIdFor()) ;
		Formation d = liste.get(0) ;
		d.setIdFor(f.getIdFor());
		d.setLibF(f.getLibF());
		d.setNbGrC(f.getNbGrC());
		d.setNbGrTD(f.getNbGrTD());
		d.setNbGrTP(f.getNbGrTP());
	}		
	
	public static void deleteFormation(Formation f)
	{
		DaoTest.FORMATIONS.remove(f) ;
	}
	
// -------------------------- Groupe --------------------------------------------	
	
	public static List<Groupe> selectGroupe (String... id)
	{
		List<Groupe> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.GROUPES.stream().filter(g -> g.getGrpC().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.GROUPES) ;
		}
		return liste ;	
	}
	
	public static void insertGroupe(Groupe g)
	{
		DaoTest.GROUPES.add(g) ;
	}
	
	public static void updateGoupe(Groupe g)
	{
		List<Groupe> liste = DaoTest.selectGroupe(g.getGrpC()) ;
		Groupe d = liste.get(0) ;
		d.setGrpC(g.getGrpC());
		d.setAnnee(g.getAnnee());
		d.setEff(g.getEff());
	}
	
	public static void deleteGroupe(Groupe g)
	{
		DaoTest.GROUPES.remove(g) ;
	}	
	
// -------------------------- Mat -----------------------------------------------
	
	public static List<Mat> selectMat (String... id)
	{
		List<Mat> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.MATS.stream().filter(m -> m.getMatC().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.MATS) ;
		}
		return liste ;	
	}
	
	public static void insertMat(Mat m)
	{
		DaoTest.MATS.add(m) ;
	}
	
	public static void updateMat(Mat m)
	{
		List<Mat> liste = DaoTest.selectMat(m.getMatC()) ;
		Mat d = liste.get(0) ;
		d.setMatC(m.getMatC());
		d.setIntC(m.getIntC());
	}
	
	public static void deleteMat(Mat m)
	{
		DaoTest.MATS.remove(m) ;
	}
	
// -------------------------- Salle ---------------------------------------------
	
	public static List<Salle> selectSalle (String... id)
	{
		List<Salle> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.SALLES.stream().filter(s -> s.getnSalle().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.SALLES) ;
		}
		return liste ;	
	}	
	
	public static void insertSalle (Salle s)
	{
		DaoTest.SALLES.add(s) ;
	}	
	
	public static void updateSalle(Salle s)
	{
		List<Salle> liste = DaoTest.selectSalle(s.getnSalle()) ;
		Salle d = liste.get(0) ;
		d.setCapacite(s.getCapacite());
		d.setnSalle(s.getnSalle());
		d.settSal(s.gettSal());
	}
	
	public static void deleteSalle(Salle s)
	{
		DaoTest.SALLES.remove(s) ;
	}	
	
// -------------------------- Statut---------------------------------------------
	
	public static List<Statut> selectStatut (String... id)
	{
		List<Statut> liste ;
		if (id.length != 0)
		{
			liste =  DaoTest.STATUTS.stream().filter(s -> s.getGrade().equals(id[0])).collect(Collectors.toList()) ;
		}
		else
		{
			liste = new ArrayList<>(DaoTest.STATUTS) ;
		}
		return liste ;	
	}
	
	public static void insertStatut (Statut s)
	{
		DaoTest.STATUTS.add(s) ;
	}
	
	public static void updateStatut(Statut s)
	{
		List<Statut> liste = DaoTest.selectStatut(s.getGrade()) ;
		Statut d = liste.get(0) ;
		d.setGrade(s.getGrade());
		d.setNbHeurSt(s.getNbHeurSt());
	}		
	
	public static void deleteStatut(Statut s)
	{
		DaoTest.STATUTS.remove(s) ;
	}
}