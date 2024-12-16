package controleur;

import java.sql.SQLException;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Locataire;
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
		try {
			Locataire locSelect = this.gestionAfficherLoc.lireLigneListe(index);
			dateNaissance.setText(locSelect.getDateNaissance().toString());
			Adresse adr = locSelect.getAdresse();
			if (adr != null) {
				adresse.setText(adr.getAdressePostale());
			}
			tel.setText(locSelect.getTelephone());
			mail.setText(locSelect.getEmail());
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
    
    public static void viderTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                model.setValueAt(null, row, col); 
            }
        }
    }


	
	
}
