package controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modele.Bail;
import modele.Cautionner;
import modele.Locataire;
import modele.ProvisionCharge;
import modele.dao.DaoBail;
import modele.dao.DaoLocataire;
import modele.dao.DaoCautionner;
import modele.dao.DaoProvisionCharge;
import vue.AfficherLocatairesActuels;

public class GestionChampsMontantAfficherLocataire implements ListSelectionListener {

	private AfficherLocatairesActuels fen_afficher_locataires_actuels;
	private DaoBail daoBail;
	private DaoProvisionCharge daoProvision;
	private DaoCautionner daoCautionner;
	
	public GestionChampsMontantAfficherLocataire (AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.daoBail = new DaoBail();
		this.daoProvision = new DaoProvisionCharge();
		this.daoCautionner = new DaoCautionner();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JTable tableBiens = this.fen_afficher_locataires_actuels.getTableBiensLoues();
		int index = tableBiens.getSelectedRow();
		if (index != -1) {
			Bail bailSelectionne = recupererBail(tableBiens, index);
			BigDecimal provision = null;
			BigDecimal caution = null;
			try {
				List<ProvisionCharge> provisions = this.daoProvision.findByIdBail(bailSelectionne.getIdBail());
				if (provisions.size() > 0) {
					provisions.get(0).getProvisionPourCharge();
				}
				Cautionner cautionner = this.daoCautionner.findByIdBail(bailSelectionne.getIdBail());
				if (cautionner != null) {
					cautionner.getMontant();
				}
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
			this.fen_afficher_locataires_actuels.getTextFieldProvisionPourCharge().setText(String.valueOf(provision));
			this.fen_afficher_locataires_actuels.getTextFieldCaution().setText(String.valueOf(caution));
			
		}		
	}
	
	public Bail recupererBail(JTable tableBiens, int index) {
		Bail bail = null;
		String idBail = (String) tableBiens.getValueAt(index, 0);
		try {
			bail = this.daoBail.findById(idBail);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return bail;
		
	}
	
}
