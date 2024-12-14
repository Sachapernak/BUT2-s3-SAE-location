package modele.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.Assurance;
import modele.Batiment;
import modele.DocumentComptable;
import modele.Entreprise;
import modele.Locataire;
import modele.TypeDoc;
import modele.dao.requetes.RequeteDeleteDocumentComptable;
import modele.dao.requetes.RequeteSelectDocumentComptable;
import modele.dao.requetes.RequeteSelectDocumentComptableById;
import modele.dao.requetes.RequeteUpdateDocumentComptable;
import modele.dao.requetes.RequeteCreateDocumentComptable;

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

	@Override
	public List<DocumentComptable> findAll() throws SQLException, IOException {
		return find(new RequeteSelectDocumentComptable());
	}

	@Override
	protected DocumentComptable createInstance(ResultSet curseur) throws SQLException, IOException {
		String numDoc = curseur.getString("numero_document");
		String dateDoc = curseur.getDate("date_document").toString();
		
		TypeDoc type = TypeDoc.valueOf( curseur.getString("type_de_document").toUpperCase());
		
		BigDecimal montant = curseur.getBigDecimal("montant");
		String fichier = curseur.getString("fichier_document");
		
		DocumentComptable nouveau = new DocumentComptable(numDoc, dateDoc, type, montant, fichier);
		
		boolean recupLoc = curseur.getBoolean("recuperable_locataire");
		
		String idloc = curseur.getString("identifiant_locataire");
		if (!(idloc == null || idloc.isEmpty())) {
			Locataire loc = new DaoLocataire().findById(idloc);
			nouveau.setLocataire(loc);
		}
		
		String idBat = curseur.getString("identifiant_batiment");
		if (!(idBat == null || idBat.isEmpty())) {
			Batiment bat = new DaoBatiment().findById(idBat);
			nouveau.setBatiment(bat);
		}
		
		
		String siret = curseur.getString("siret");
		if (!(siret == null || siret.isEmpty())){
			Entreprise entr  = new DaoEntreprise().findById(siret);
			nouveau.setEntreprise(entr);
		}
		
		
		String nCtr = curseur.getString("numero_de_contrat");
		String annee = String.valueOf(curseur.getInt("annee_du_contrat"));
		if (!(nCtr == null || nCtr.isEmpty())) {
			Assurance asr  = new DaoAssurance().findById(nCtr, annee);
			nouveau.setAssurance(asr);
		}
		
		// TODO : Les charges CF et charges CV
		
		return nouveau;
	}



}
