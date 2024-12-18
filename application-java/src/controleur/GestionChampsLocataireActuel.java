package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Bail;
import modele.Contracter;
import modele.Locataire;
import modele.Loyer;
import modele.Regularisation;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;

public class GestionChampsLocataireActuel implements ListSelectionListener {
	
	private AfficherLocatairesActuels fen_afficher_locataires_actuels;
	private GestionAfficherLocataire gestionAfficherLoc;
	private DaoLocataire daoLocataire;
	
	public GestionChampsLocataireActuel(AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.daoLocataire = new DaoLocataire();
		this.gestionAfficherLoc = new GestionAfficherLocataire(afl);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<String> listeLoc = this.fen_afficher_locataires_actuels.getListLocatairesActuels();
		JTextField dateNaissance = this.fen_afficher_locataires_actuels.getTextFieldDateDeNaissance();
		JTextField adresse = this.fen_afficher_locataires_actuels.getTextFieldAdressePerso();
		JTextField tel = this.fen_afficher_locataires_actuels.getTextFieldTel();
		JTextField mail = this.fen_afficher_locataires_actuels.getTextFieldMail();
		
		int index = listeLoc.getSelectedIndex();
		
		if (index == -1) { 
	        viderChamps();
	        viderTable(this.fen_afficher_locataires_actuels.getTableBiensLoues());
	        return; 
	    }
		
		try {
			Locataire locSelect = this.gestionAfficherLoc.lireLigneListe(index);
			dateNaissance.setText(locSelect.getDateNaissance().toString());
			Adresse adr = locSelect.getAdresse();
			if (adr != null) {
				adresse.setText(adr.getAdressePostale());
			}
			tel.setText(locSelect.getTelephone());
			mail.setText(locSelect.getEmail());
			
			remplirTableLocation(this.fen_afficher_locataires_actuels.getTableBiensLoues(), locSelect);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void remplirTableLocation(JTable tableLocations, Locataire locSelect) {
		viderTable(tableLocations);
		
		try {
			List<Contracter> contrats = locSelect.getContrats();
			String loyerActuel = "";
			String dateRegularisation = "";
			
			for (Contracter contrat : contrats) {
				String dateEntree = contrat.getDateEntree();
				String partLoyer = String.valueOf(contrat.getPartLoyer());
				Bail bail = contrat.getBail();
				
				List<Regularisation> regularisations = bail.getRegularisation();
				int tailleListeRegu = regularisations.size();
				if (tailleListeRegu>0) {
					String dateDerniereRegu = regularisations.get(tailleListeRegu-1).getDateRegu();
				}
				
				String batiment = bail.getBien().getBat().getIdBat();
				String complementAdr = bail.getBien().getComplementAdresse();
				
				List<Loyer> loyers = bail.getBien().getLoyers();
				int tailleListeLoyer = loyers.size();
				if (tailleListeLoyer>0) {
					loyerActuel = String.valueOf(loyers.get(tailleListeLoyer-1).getMontantLoyer());
				}
				String type = bail.getBien().getType().getValeur();
				
				DefaultTableModel model = (DefaultTableModel) tableLocations.getModel();
				model.addRow(new Object[] {dateEntree, type, batiment, complementAdr, loyerActuel, partLoyer, dateRegularisation});
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    }
	
	private void viderChamps() {
	    JTextField dateNaissance = this.fen_afficher_locataires_actuels.getTextFieldDateDeNaissance();
	    JTextField adresse = this.fen_afficher_locataires_actuels.getTextFieldAdressePerso();
	    JTextField tel = this.fen_afficher_locataires_actuels.getTextFieldTel();
	    JTextField mail = this.fen_afficher_locataires_actuels.getTextFieldMail();

	    dateNaissance.setText("");
	    adresse.setText("");
	    tel.setText("");
	    mail.setText("");
	}
    
    public void viderTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }


	
	
}
