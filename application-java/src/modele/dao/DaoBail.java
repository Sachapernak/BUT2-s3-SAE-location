package modele.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import modele.Bail;
import modele.BienLocatif;
import modele.ConnexionBD;
import modele.dao.requetes.RequeteCreateBail;
import modele.dao.requetes.RequeteDeleteBail;
import modele.dao.requetes.RequeteFindNbBailActif;
import modele.dao.requetes.RequeteSelectAllCfBaiLoc;
import modele.dao.requetes.RequeteSelectAllCIBaiLoc;
import modele.dao.requetes.RequeteSelectBail;
import modele.dao.requetes.RequeteSelectBailById;
import modele.dao.requetes.RequeteSelectBailByIdLogement;
import modele.dao.requetes.RequeteSelectCautionParLocBai;
import modele.dao.requetes.RequeteSelectSommeProvBaiLoc;
import modele.dao.requetes.RequeteSelectTotalChargeDeduc;
import modele.dao.requetes.RequeteUpdateBail;

/**
 * Classe DaoBail qui implémente les opérations CRUD pour l'entité Bail.
 * 
 * Cette classe permet de gérer les opérations de création, mise à jour, suppression, et recherche des baux dans la base de données.
 * Elle étend la classe générique DaoModele et utilise des requêtes SQL spécifiques pour interagir avec la base de données.
 */
public class DaoBail extends DaoModele<Bail> implements Dao<Bail> {

    /**
     * Crée un nouveau bail dans la base de données.
     * 
     * Cette méthode insère un nouveau bail dans la base de données en utilisant une requête de type `RequeteCreateBail`.
     * 
     * @param donnees Le bail à insérer dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void create(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteCreateBail(), donnees);
    }

    /**
     * Met à jour un bail existant dans la base de données.
     * 
     * Cette méthode met à jour les informations d'un bail dans la base de données en utilisant une requête de type `RequeteUpdateBail`.
     * 
     * @param donnees Le bail à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void update(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteUpdateBail(), donnees);
    }

    /**
     * Supprime un bail de la base de données.
     * 
     * Cette méthode supprime un bail de la base de données en utilisant une requête de type `RequeteDeleteBail`.
     * 
     * @param donnees Le bail à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void delete(Bail donnees) throws SQLException, IOException {
        miseAJour(new RequeteDeleteBail(), donnees);
    }

    /**
     * Recherche un bail par son identifiant dans la base de données.
     * 
     * Cette méthode retourne le bail correspondant à l'identifiant passé en paramètre en utilisant une requête de type `RequeteSelectBailById`.
     * 
     * @param id L'identifiant du bail à rechercher.
     * @return Le bail correspondant à l'identifiant fourni.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public Bail findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectBailById(), id);
    }

    /**
     * Recherche tous les baux dans la base de données.
     * 
     * Cette méthode retourne une liste de tous les baux présents dans la base de données en utilisant la requête `RequeteSelectBail`.
     * 
     * @return La liste des baux trouvés dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'exécution de la requête.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public List<Bail> findAll() throws SQLException, IOException {
        return find(new RequeteSelectBail());
    }

    /**
     * Crée une instance d'un bail à partir des données récupérées d'un ResultSet.
     * 
     * Cette méthode extrait les informations d'un bail à partir d'un `ResultSet` et retourne une instance de l'objet `Bail` correspondant.
     * Elle gère la conversion des dates en objets `LocalDate` et formate les dates pour correspondre au format attendu par la classe `Bail`.
     * 
     * @param curseur Le `ResultSet` contenant les données d'un bail.
     * @return L'instance de Bail correspondant aux données extraites.
     * @throws SQLException Si une erreur survient lors de l'accès aux données du `ResultSet`.
     * @throws IOException 
     */
    @Override
    protected Bail createInstance(ResultSet curseur) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir la chaîne en LocalDateTime
        LocalDateTime localDateTimeDebut = LocalDateTime.parse(curseur.getString("DATE_DE_DEBUT"), formatter);

        // Extraire uniquement la date si nécessaire
        LocalDate localDateDebut = localDateTimeDebut.toLocalDate();
        
        String idBail = curseur.getString("ID_BAIL");
        
        String idBien = curseur.getString("IDENTIFIANT_LOGEMENT");
        BienLocatif bien = new DaoBienLocatif().findById(idBien);
        


        // Formatter la date de début
        String dateDeDebut = localDateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Bail bail = new Bail(idBail, dateDeDebut, bien);
        String dateDeFin = curseur.getString("DATE_DE_FIN");
        if (dateDeFin != null) {
            LocalDateTime localDateTimeFin = LocalDateTime.parse(dateDeFin, formatter);
            LocalDate localDateFin = localDateTimeFin.toLocalDate();
            bail.setDateDeFin(localDateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return bail;
    }
    
    public List<Bail> findByIdLogement(String idLogement) throws SQLException, IOException{
    	if (idLogement == null) {
            return new ArrayList<>();
        }
    	List<Bail> res = new ArrayList<>();
		Connection cn = ConnexionBD.getInstance().getConnexion();
		RequeteSelectBailByIdLogement req = new RequeteSelectBailByIdLogement();
        PreparedStatement prSt = cn.prepareStatement(req.requete());
        req.parametres(prSt, idLogement);
        ResultSet rs = prSt.executeQuery();
        while (rs.next()) {
            res.add(createInstance(rs));
        }
        rs.close();
        prSt.close();
        return res;
	}
    
    public List<String[]> findAllChargesBaiLoc(String idBail, String idLoc, String dateDeb, String dateFin) 
    		throws SQLException, IOException{
    	
    	
    	List<String[]> res = new ArrayList<>();
		Connection cn = ConnexionBD.getInstance().getConnexion();
		PreparedStatement prSt;
        ResultSet rs;
    	
		// Charges Index
		RequeteSelectAllCIBaiLoc reqCI = new RequeteSelectAllCIBaiLoc();
        
		prSt = cn.prepareStatement(reqCI.requete());
        reqCI.parametres(prSt, idBail, idLoc, dateDeb, dateFin);
        rs = prSt.executeQuery();
        
        while (rs.next()) {
        	// " " en 3eme pos car on a pas de calcul, pour harmoniser avec les charges a index
        	String[] str = {rs.getDate("dateDoc").toString(), rs.getString("typeDoc"), rs.getString("Detail_Calcul"), rs.getBigDecimal("montant").toString()};
            res.add(str);
        }
        rs.close();
        prSt.close();
        
    	
		RequeteSelectAllCfBaiLoc reqCf = new RequeteSelectAllCfBaiLoc();
        prSt = cn.prepareStatement(reqCf.requete());
        reqCf.parametres(prSt, idBail, idLoc, dateDeb, dateFin);
        rs = prSt.executeQuery();
        
        while (rs.next()) {
        	// " " en 3eme pos car on a pas de calcul, pour harmoniser avec les charges a index
        	String[] str = {rs.getDate("dateDoc").toString(), rs.getString("typeDoc"), "", rs.getBigDecimal("montant").toString()};
            res.add(str);
        }
        rs.close();
        prSt.close();

        return res;
    }
    

    
    public String[] findAllDeducBaiLoc(String idBail, String idLoc, String dateDeb, String dateFin) 
    		throws SQLException, IOException{
    	
    	String calc = "";
    	String totalProv = ""; 
    	String caution = "";
    	

		Connection cn = ConnexionBD.getInstance().getConnexion();
		CallableStatement prCl;
        ResultSet rs;
    	
		// Provisions pour charges
		RequeteSelectSommeProvBaiLoc reqProv = new RequeteSelectSommeProvBaiLoc();
        
		prCl = cn.prepareCall(reqProv.requete());
        reqProv.parametres(prCl, idBail, idLoc, dateDeb, dateFin);
        prCl.execute();
        
        totalProv = prCl.getBigDecimal(5).toString();
        calc = prCl.getString(6);
        
        prCl.close();
        
    	// Caution
        
		RequeteSelectCautionParLocBai reqCau = new RequeteSelectCautionParLocBai();
        
		PreparedStatement prSt = cn.prepareStatement(reqCau.requete());
		reqCau.parametres(prSt, idBail, idLoc, dateDeb, dateFin);
        rs = prSt.executeQuery();
        
        if (rs.next()) {
        	caution = rs.getBigDecimal("MONTANTLOC").toString();
        }
        rs.close();
        prSt.close();
        

        
        String[] res = {totalProv, calc, caution};
        
        
    	return res;
    }
    
    public BigDecimal[] findTotalChargeDeduc(String idBail, String idLoc, String dateDeb, String dateFin) throws SQLException, IOException {
    	
    	BigDecimal totalCharge;
    	BigDecimal totalDeduc;
    	
    	BigDecimal total;
    	

		Connection cn = ConnexionBD.getInstance().getConnexion();
		CallableStatement prCl;
    	
		// Provisions pour charges
		RequeteSelectTotalChargeDeduc reqTot= new RequeteSelectTotalChargeDeduc();
        
		prCl = cn.prepareCall(reqTot.requete());
		reqTot.parametres(prCl, idBail, idLoc, dateDeb, dateFin);
        prCl.execute();
        
        totalCharge = prCl.getBigDecimal(5);
        totalDeduc = prCl.getBigDecimal(6);
        
        total = totalCharge.subtract(totalDeduc);
        
        prCl.close();
        
        BigDecimal[] resultat = {totalCharge, totalDeduc, total};
        
        return resultat;
    }
    
    public int findNbBailActifParLogement(String idLog) throws SQLException, IOException {
    	
		Connection cn = ConnexionBD.getInstance().getConnexion();
		CallableStatement prCl;

		RequeteFindNbBailActif reqTot= new RequeteFindNbBailActif();
        
		prCl = cn.prepareCall(reqTot.requete());
		reqTot.parametres(prCl, idLog);
        prCl.execute();
               
        return  prCl.getInt(1);
    	
    }
    
    public BigDecimal findChargeDuMois(String idBail, String date) throws SQLException, IOException {
		String req = 
					"""
					SELECT 
					    p.id_bail,
					    p.provision_pour_charge
					FROM sae_provision_charge p
					WHERE p.date_changement = (
					    SELECT MAX(p2.date_changement)
					    FROM sae_provision_charge p2
					    WHERE p2.id_bail = p.id_bail
					      AND p2.date_changement <= ?
					)
					AND p.id_bail = ?;
					""";


	
	try (PreparedStatement prSt = ConnexionBD.getInstance().getConnexion().prepareStatement(req)) {
		        // Paramétrage de la requête
	
	        prSt.setDate(1, Date.valueOf(date));
	        prSt.setString(2, idBail);
	
	        // Exécution de la requête et récupération du résultat
	        try (ResultSet rs = prSt.executeQuery()) {
	            if (rs.next()) {
	
	                return rs.getBigDecimal(2);
	            } else {
	                return BigDecimal.ZERO;
	            }
	        }
	    }
    }
    
    public BigDecimal findPartDesCharges(String idBail, String idLoc) throws SQLException, IOException {
		String req = """
					Select part_de_loyer
					from sae_contracter
					where identifiant_locataire = ?
					and id_bail = ?
					""";
		
		try (PreparedStatement prSt = ConnexionBD.getInstance().getConnexion().prepareStatement(req)) {
			        // Paramétrage de la requête
		
		        prSt.setString(1, idLoc);
		        prSt.setString(2, idBail);
		
		        // Exécution de la requête et récupération du résultat
		        try (ResultSet rs = prSt.executeQuery()) {
		            if (rs.next()) {
		
		                return rs.getBigDecimal(1);
		            } else {
		                return BigDecimal.ZERO;
		            }
		        }
		    }
		
		}
}
