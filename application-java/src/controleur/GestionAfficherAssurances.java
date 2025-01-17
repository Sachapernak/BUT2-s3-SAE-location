package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import modele.Assurance;
import modele.dao.DaoAssurance;
import vue.AfficherAssurances;
import vue.RevalorisationLoyer;
import vue.AjouterAssurance;

public class GestionAfficherAssurances implements ActionListener, ChangeListener{

    private final DaoAssurance daoAssurance;
    private final AfficherAssurances fenAfficherAssurances;


    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param ae  la vue pour ajouter une assurance
     */
    public GestionAfficherAssurances(AfficherAssurances aa) {
        this.fenAfficherAssurances = aa;
        this.daoAssurance = new DaoAssurance();
    }

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            handleSecteurSelection();
        } else if (e.getSource() instanceof JButton btnActif) {
            handleButtonClick(btnActif);
        }

    }
    
    @Override
	public void stateChanged(ChangeEvent e) {
    	handleSecteurSelection();
	}
    
    // --------------------------------------------------------------------
   	//                 Méthodes privées de gestion d'actions
   	// --------------------------------------------------------------------
    
    private void handleSecteurSelection() {
        String type = this.fenAfficherAssurances.getTypeContratCombo();
        int annee = this.fenAfficherAssurances.getSpinnerAnnee();
        
        DefaultTableModel defaultTableModel = getTableModelAssurances(type, annee);
        this.fenAfficherAssurances.setTableAssurances(defaultTableModel);
    }
    
    /**
     * Gère les clics sur les boutons (Quitter, Ajouter, Supprimer, etc.).
     */
    private void handleButtonClick(JButton btnActif) {
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" : 
				this.fenAfficherAssurances.dispose();
				break;
			case "Ajouter" : 
				AjouterAssurance aa = new AjouterAssurance();
				aa.setVisible(true);
				break;
			case "Supprimer" : 
				break;
			default : 
				break;
        }
    }
    
    public List<Assurance> recupererAssurances(String valeurComboBox, int annee) {
    	List<Assurance> assurancesType = new ArrayList<>();
    	try {
			List<Assurance> assurances = daoAssurance.findAll();
			for (Assurance assurance : assurances) {
				if ((valeurComboBox.equals("Tous") || assurance.getTypeContrat().equals(valeurComboBox)) 
						&& annee == assurance.getAnneeContrat() ) {
					assurancesType.add(assurance);
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    	return assurancesType;	
    }
    
    
    public DefaultTableModel getTableModelAssurances(String valeurComboBox, int annee) {
    	List<Assurance> assurances = recupererAssurances(valeurComboBox, annee);
    	
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] { "Numéro de contrat", "Année", "Type" });

        for (Assurance assurance : assurances) {
            tableModel.addRow(new Object[] {
                assurance.getNumeroContrat(),
        		assurance.getAnneeContrat(),
                assurance.getTypeContrat()
            });
        }
        return tableModel;
    }
    
    public void remplirComboBoxAssurances() {
    	List<Assurance> assurances;
		try {
			assurances = daoAssurance.findAll();
	    	List<String> typeContrat = new ArrayList<>();
	    	typeContrat.add("Tous");
	    	for (Assurance assurance : assurances) {
	    		typeContrat.add(assurance.getTypeContrat());
	    	}
	    	this.fenAfficherAssurances.setComboBoxTypeContrat(typeContrat);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
    	
    }

	public void initialiserTable() {
		DefaultTableModel model = getTableModelAssurances("Tous", 2023);
		this.fenAfficherAssurances.setTableAssurances(model);
		
	}
	
	
}
