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
	
	protected Contracter createInstance(ResultSet curseur, Locataire locataire) throws SQLException, IllegalArgumentException, IOException {
		String idloc= curseur.getString("identifiant_locataire");
		String idBail = curseur.getString("id_bail");
		String dateEntre = curseur.getDate("Date_d_entree").toString();
		String dateSortie;
		dateSortie = curseur.getDate("Date_de_sortie") == null ?  null : curseur.getDate("Date_de_sortie").toString();
		float partLoyer = curseur.getFloat("PART_DE_LOYER");
		
		Contracter contrat = new Contracter(locataire,
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

		
		List<Contracter> res = new ArrayList<>();
		
		Connection cn = ConnexionBD.getInstance().getConnexion();
		
		PreparedStatement prSt = cn.prepareStatement(req.requete());
		
		req.parametres(prSt, id);
		
		
		ResultSet rs = prSt.executeQuery();
		
		while(rs.next()) {
			res.add(createInstance(rs, donnees));
		}
		
		rs.close();
		prSt.close();
		return res;


	}
	
	
}
