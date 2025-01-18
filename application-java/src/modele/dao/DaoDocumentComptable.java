package modele.dao;

import java.io.IOException;



import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modele.ConnexionBD;
import modele.DocumentComptable;
import modele.TypeDoc;
import modele.dao.requetes.RequeteCreateDocumentComptable;
import modele.dao.requetes.RequeteDeleteDocumentComptable;
import modele.dao.requetes.RequeteSelectDocumentComptable;
import modele.dao.requetes.RequeteSelectDocumentComptableById;
import modele.dao.requetes.RequeteSelectDocumentComptableByIdLog;
import modele.dao.requetes.RequeteSelectDocumentComptableLoyersParLocataire;
import modele.dao.requetes.RequeteUpdateDocumentComptable;

public class DaoDocumentComptable extends DaoModele<DocumentComptable> {

	@Override
	public void create(DocumentComptable donnees) throws SQLException, IOException {
		miseAJour(new RequeteCreateDocumentComptable(), donnees);
		
	}

	@Override
	public void update(DocumentComptable donnees) throws SQLException, IOException {
		miseAJour(new RequeteUpdateDocumentComptable(), donnees);
		
	}

	@Override
	public void delete(DocumentComptable donnees) throws SQLException, IOException {
		miseAJour(new RequeteDeleteDocumentComptable(), donnees);
		
	}

	@Override
	public DocumentComptable findById(String... id) throws SQLException, IOException {
		return findById(new RequeteSelectDocumentComptableById(), id);
	}
	
	public List<DocumentComptable> findByIdLogement(String id) throws SQLException, IOException {
		return find(new RequeteSelectDocumentComptableByIdLog(), id);
	}
	
	
	public BigDecimal findMontantProrata(DocumentComptable doc, String id) throws SQLException, IOException {
	    String requete = "SELECT part_des_charges FROM sae_facture_du_bien "
	                   + "WHERE identifiant_logement = ? "
	                   + "AND date_document = ? "
	                   + "AND numero_document = ?";

	    try (
	        PreparedStatement prSt = ConnexionBD.getInstance().getConnexion().prepareStatement(requete)
	    ) {
	        // Paramétrage de la requête
	        prSt.setString(1, id);
	        prSt.setDate(2, Date.valueOf(doc.getDateDoc()));
	        prSt.setString(3, doc.getNumeroDoc());

	        // Exécution de la requête et récupération du résultat
	        try (ResultSet rs = prSt.executeQuery()) {
	            if (rs.next()) {
	                BigDecimal taux = rs.getBigDecimal("part_des_charges");
	                return doc.getMontant().multiply(taux);
	            } else {
	                throw new SQLException("Aucune donnée trouvée pour les critères spécifiés.");
	            }
	        }
	    }
	}

	
	public List<DocumentComptable> findLoyersByIdLocataire (String idLocataire) throws SQLException, IOException {
		
		return find(new RequeteSelectDocumentComptableLoyersParLocataire(), idLocataire);
	}
	
	public List<DocumentComptable> findAllLoyers() throws SQLException, IOException {
		
		return find(new RequeteSelectDocumentComptableLoyersParLocataire());
	}
	

	public List<DocumentComptable> findByIdtypeloyer(String... id) throws SQLException, IOException {
		
		String plsqlProcedure = "{ call Quittance.GetDocumentsLoyer(?, ?, ?, ?) }";
		CallableStatement stmt = ConnexionBD.getInstance().getConnexion().prepareCall(plsqlProcedure);
		 
        // Définir les paramètres d'entrée
        stmt.setString(1, id[0]);
        stmt.setString(2, id[1]);
        stmt.setString(3, id[2]);
        // Définir le paramètre de sortie (SYS_REFCURSOR)
        stmt.registerOutParameter(4, Types.REF_CURSOR);

        // Exécuter la procédure
        stmt.execute();
        ResultSet rs = (ResultSet) stmt.getObject(4);
        
        List<DocumentComptable> list = new ArrayList<>();
        while(rs.next()) {
        	list.add(createInstance(rs));
        }
		return list;
	}
	
	
	@Override
	public List<DocumentComptable> findAll() throws SQLException, IOException {
		return find(new RequeteSelectDocumentComptable());
	}

	@Override
	protected DocumentComptable createInstance(ResultSet curseur) throws SQLException, IOException {
	    String numDoc = curseur.getString("numero_document");
	    String dateDoc = curseur.getDate("date_document").toString();

	    TypeDoc type = TypeDoc.valueOf(curseur.getString("type_de_document").toUpperCase());

	    BigDecimal montant = curseur.getBigDecimal("montant");
	    String fichier = curseur.getString("fichier_document");

	    // Instanciation de base
	    DocumentComptable nouveau = new DocumentComptable(numDoc, dateDoc, type, montant, fichier);

	    boolean recupLoc = curseur.getBoolean("recuperable_locataire");
	    nouveau.setRecuperableLoc(recupLoc);

	    // --- Récupération des IDs pour lazy loading ---

	    String idloc = curseur.getString("identifiant_locataire");
	    if (idloc != null && !idloc.isEmpty()) {
	        // On NE charge plus le locataire tout de suite,
	        // on se contente de stocker son ID.
	        nouveau.setIdLocataire(idloc);
	    }

	    String idBat = curseur.getString("identifiant_batiment");
	    if (idBat != null && !idBat.isEmpty()) {
	        nouveau.setIdBatiment(idBat);
	    }

	    String siret = curseur.getString("siret");
	    if (siret != null && !siret.isEmpty()) {
	        nouveau.setIdEntreprise(siret);
	    }

	    String nCtr = curseur.getString("numero_de_contrat");
	    String annee = String.valueOf(curseur.getInt("annee_du_contrat"));
	    if (nCtr != null && !nCtr.isEmpty()) {
	        nouveau.setNumeroContrat(nCtr);
	        nouveau.setAnneeContrat(annee);
	    }

	    // ChargeFixe et ChargeIndex : on ne les charge plus ici,
	    // on les laisse en lazy loading dans la classe (via getChargeFixe / getChargeIndex).

	    return nouveau;
	}




}
