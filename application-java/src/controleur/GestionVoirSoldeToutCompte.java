package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import vue.VoirSoldeToutCompte;

public class GestionVoirSoldeToutCompte {
	
	Locataire loc;
	VoirSoldeToutCompte fen;
	
	public GestionVoirSoldeToutCompte(VoirSoldeToutCompte fen) {
		this.fen = fen;
	}
	
	public void loadLocataire() {
		
		try {
			this.loc = new DaoLocataire().findById(fen.getIdLoc());
			
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void setInfoLoc() {
		if(this.loc == null) {
			loadLocataire();
		}
		
		fen.setNomLoc(loc.getNom());
		fen.setPrenom(loc.getPrenom());
	}
	
	public void setDates() {
		if(fen.getDateDebut() == null || fen.getDateDebut().isEmpty()) {
			fen.setDateDebut("Début");
		} else {
			fen.setDateDebut(fen.getDateDebut());
		}
		
		if(fen.getDateFin() == null || fen.getDateDebut().isEmpty()) {
			fen.setDateFin("Aujourd'hui");
		} else {
			fen.setDateDebut(fen.getDateFin());
		}
	}
	
	public void loadCharges() {		
		try {
			List<String[]> lignes = new DaoBail().findAllChargesBaiLoc(fen.getIdBail(), fen.getIdLoc(), 
													fen.getDateDebut(), fen.getDateFin());
			
			fen.chargerTableCharges(lignes);
			
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public void loadDeduc() {
		try {
			String[] res = new DaoBail().findAllDeducBaiLoc(fen.getIdBail(), fen.getIdLoc(), 
														fen.getDateDebut(), fen.getDateFin());
			
			List<String[]> lignes = new ArrayList<>();
			
			String[] provisions = {"Provisions pour charge", res[1], res[0]};
			String[] caution = {"Caution de l'appartement", "", res[2]};
			
			lignes.add(provisions);
			lignes.add(caution);
			
			fen.chargerTableDeduc(lignes);
			
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
		
	}
	

}
