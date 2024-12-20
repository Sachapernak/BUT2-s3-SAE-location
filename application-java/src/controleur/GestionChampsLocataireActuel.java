package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
	private DaoLocataire daoLocataire;
	
	public GestionChampsLocataireActuel(AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.daoLocataire = new DaoLocataire();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JTable tableLoc = this.fen_afficher_locataires_actuels.getTableLocatairesActuels();
		JTextField dateNaissance = this.fen_afficher_locataires_actuels.getTextFieldDateDeNaissance();
		JTextField adresse = this.fen_afficher_locataires_actuels.getTextFieldAdressePerso();
		JTextField tel = this.fen_afficher_locataires_actuels.getTextFieldTel();
		JTextField mail = this.fen_afficher_locataires_actuels.getTextFieldMail();
		
		int index = tableLoc.getSelectedRow();
		
		if (index == -1) { 
	        viderChamps();
	        viderTable(this.fen_afficher_locataires_actuels.getTableBiensLoues());
	        return; 
	    }
		
		Locataire locSelect = lireLigneTable(this.fen_afficher_locataires_actuels.getTableLocatairesActuels());
		dateNaissance.setText(locSelect.getDateNaissance().toString());
		Adresse adr = locSelect.getAdresse();
		if (adr != null) {
			adresse.setText(adr.getAdressePostale());
		}
		tel.setText(locSelect.getTelephone());
		mail.setText(locSelect.getEmail());
		
		remplirTableLocation(this.fen_afficher_locataires_actuels.getTableBiensLoues(), locSelect);
	}
	
	public void remplirTableLocation(JTable tableLocations, Locataire locSelect) {
		viderTable(tableLocations);
		
		try {
			List<Contracter> contrats = locSelect.getContrats();
			String loyerActuel = "";
			String dateDerniereRegularisation = "";
			
			for (Contracter contrat : contrats) {
				String dateEntree = contrat.getDateEntree();
				String partLoyer = String.valueOf(contrat.getPartLoyer());
				Bail bail = contrat.getBail();
				
				List<Regularisation> regularisations = bail.getRegularisation();
				int tailleListeRegu = regularisations.size();
				if (tailleListeRegu>0) {
					dateDerniereRegularisation = regularisations.get(tailleListeRegu-1).getDateRegu();
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
				model.addRow(new Object[] {dateEntree, type, batiment, complementAdr, loyerActuel, partLoyer, dateDerniereRegularisation});
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
    
	public void remplirTableLocatairesActuels() {
	    JTable tableLocataires = this.fen_afficher_locataires_actuels.getTableLocatairesActuels();
	    
	    viderTable(tableLocataires);
	    try {
			List<Locataire> locataires = daoLocataire.findAll();
			DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
			model.setRowCount(0);
	        for (Locataire locataire : locataires) {
	            model.addRow(new String[] { locataire.getIdLocataire(), locataire.getNom(), locataire.getPrenom()});
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Locataire lireLigneTable(JTable tableLocatairesActuels) {
		Locataire loc = null;
		JTable tableLocataires = this.fen_afficher_locataires_actuels.getTableLocatairesActuels();
		int index = tableLocataires.getSelectedRow();
		 
		String idLoc = (String) tableLocataires.getValueAt(index, 0);
		
		try {
			loc = daoLocataire.findById(idLoc);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loc;		 
		 
	}
	
	
}
