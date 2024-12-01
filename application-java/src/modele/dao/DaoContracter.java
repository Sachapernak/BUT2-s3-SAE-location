package modele.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Bail;
import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import modele.dao.requetes.Requete;
import modele.dao.requetes.RequeteSelectContratByIdBail;
import modele.dao.requetes.RequeteSelectContratByIdLocataire;

public class DaoContracter {

	public DaoContracter() {
		
	}
	
	protected Contracter createInstance(ResultSet curseur) throws SQLException, IllegalArgumentException, IOException {
		String idloc= curseur.getString("identifiant_locataire");
		String idBail = curseur.getString("id_bail");
		String dateEntre = curseur.getDate("Date_d_entree").toString();
		String dateSortie = curseur.getDate("Date_de_sortie").toString();
		float partLoyer = curseur.getFloat("PART_DE_LOYER");
		
		Contracter contrat = new Contracter(new DaoLocataire().findById(idloc),
											new DaoBail().findById(idBail),
											dateEntre,
											partLoyer);
		
		if (dateSortie != null) {
			contrat.ajouterDateSortie(dateSortie);
		}
		
		return contrat;
	
	}
	
	protected List<Contracter> getContrats(Locataire donnees) throws SQLException, IOException {

		if(donnees == null) {
			return new ArrayList<Contracter>();
		}
		
		Requete<Contracter> req = new RequeteSelectContratByIdLocataire();
		
		String id = donnees.getIdLocataire();

		return getContrats(id, req);


	}
	
	protected List<Contracter> getContrats(Bail donnees) throws SQLException, IOException {

		if(donnees == null) {
			return new ArrayList<Contracter>();
		}
		
		Requete<Contracter> req = new RequeteSelectContratByIdBail();
		
		String id = donnees.getIdBail();

		return getContrats(id, req);


	}
	
	private List<Contracter> getContrats(String id, Requete<Contracter> req) throws SQLException, IOException{
		
		List<Contracter> res = new ArrayList<>();
		
		Connection cn = ConnexionBD.getInstance().getConnexion();
		PreparedStatement prSt = cn.prepareStatement(req.requete());
		
		req.parametres(prSt, id);
		
		
		ResultSet rs = prSt.executeQuery();
		
		while(rs.next()) {
			res.add(new DaoContracter().createInstance(rs));
		}
		
		rs.close();
		prSt.close();
		return res;
	}
}
