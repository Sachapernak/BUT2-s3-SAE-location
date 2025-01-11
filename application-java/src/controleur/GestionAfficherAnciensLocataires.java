package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.table.DefaultTableModel;

import modele.Bail;
import modele.BienLocatif;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoContracter;
import modele.dao.DaoLocataire;
import vue.AfficherAnciensLocataires;

public class GestionAfficherAnciensLocataires implements ActionListener {
	
	private AfficherAnciensLocataires fenAfficherAnciensLocataires;
	private DaoLocataire daoLocataire;
	
	public GestionAfficherAnciensLocataires(AfficherAnciensLocataires aal)  {
		this.fenAfficherAnciensLocataires = aal;
		this.daoLocataire = new DaoLocataire();
	}
	
	public void gestionActionComboBatiment() {
		JComboBox comboBoxBienLocatif = this.fenAfficherAnciensLocataires.getComboBoxBienLocatif();
		JComboBox comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();
		comboBoxBatiment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    remplirComboBoxBienLocatif(String.valueOf(comboBoxBatiment.getSelectedItem()));
                    remplirTableAnciensLocatairesParBatiment(String.valueOf(comboBoxBatiment.getSelectedItem()));
                }
            }
        });
    }
	
	public void gestionActionComboLogement() {
		JComboBox comboBoxBienLocatif = this.fenAfficherAnciensLocataires.getComboBoxBienLocatif();
		JComboBox comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();
		comboBoxBienLocatif.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                	System.out.println("a");
					remplirTableAnciensLocataires(String.valueOf(comboBoxBienLocatif.getSelectedItem()));
					if (comboBoxBienLocatif.getSelectedItem() == "Tous"){
						remplirTableAnciensLocatairesParBatiment(String.valueOf(comboBoxBatiment.getSelectedItem()));
					}
                }
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		switch (btnLibelle) {
			case "Retour" :
				this.fenAfficherAnciensLocataires.dispose();
				break;
		}
	}
	
	public void remplirComboBoxBatiment() {
	    JComboBox comboBoxBatiment = this.fenAfficherAnciensLocataires.getComboBoxBatiment();
	    try {
			comboBoxBatiment.setModel(new DefaultComboBoxModel(new DaoBatiment().findAll().stream()
					.map(e -> e.getIdBat())
					.collect(Collectors.toList()).toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void remplirComboBoxBienLocatif(String batiment) {
	    JComboBox comboBoxBienLocatif = this.fenAfficherAnciensLocataires.getComboBoxBienLocatif();
	    try {
	    	List<String> valeurs = new DaoBienLocatif().findByIdBat(batiment).stream()
					.map(e -> e.getIdentifiantLogement())
					.collect(Collectors.toList());
	    	valeurs.add(0, "Tous");
			comboBoxBienLocatif.setModel(new DefaultComboBoxModel(valeurs.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void remplirTableAnciensLocataires(String bienLocatif) {
	    JTable tableLocataires = this.fenAfficherAnciensLocataires.getTableAnciensLocataires();
	    UtilitaireTable.viderTable(tableLocataires);
	    try {
			DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
			model.setRowCount(0);
			List<Contracter> contrats = new DaoContracter().findByIdLogement(bienLocatif);
	        for (Contracter contrat : contrats) {
	        	if (contrat.getDateSortie() != null) {
		        	Locataire locataire = contrat.getLocataire();
		        	model.addRow(new String[] { locataire.getIdLocataire(), locataire.getNom(), locataire.getPrenom(), 
		        			contrat.getDateEntree(), contrat.getDateSortie()});
	        	}
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void remplirTableAnciensLocatairesParBatiment(String batiment) {
		JTable tableLocataires = this.fenAfficherAnciensLocataires.getTableAnciensLocataires();
	    UtilitaireTable.viderTable(tableLocataires);
	    try {
			DefaultTableModel model = (DefaultTableModel) tableLocataires.getModel();
			model.setRowCount(0);
			
			List<BienLocatif> logements = new DaoBienLocatif().findByIdBat(batiment);
			for (BienLocatif logement : logements) {
				List<Bail> bails = new DaoBail().findByIdLogement(logement.getIdentifiantLogement());
		        for (Bail bail : bails) {
		        	List<Contracter> contrats = new DaoContracter().getContrats(bail);
		        	for (Contracter contrat : contrats) {
		        		if (contrat.getDateSortie() != null) {
			        		Locataire locataire = contrat.getLocataire();
			        		model.addRow(new String[] { locataire.getIdLocataire(), locataire.getNom(), locataire.getPrenom(), 
			        		contrat.getDateEntree(), contrat.getDateSortie()});
		        		}
		        	}
		        }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
