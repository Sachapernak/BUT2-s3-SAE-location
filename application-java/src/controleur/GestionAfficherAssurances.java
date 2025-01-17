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
import modele.Entreprise;
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
				confirmerSuppression();
				break;
			default : 
				break;
        }
    }
    
    /**
     * Récupère une liste d'assurances filtrées en fonction d'un type de contrat et d'une année.
     * 
     * @param valeurComboBox la valeur sélectionnée dans la comboBox (peut être "Tous" ou un type spécifique).
     * @param annee l'année pour laquelle on souhaite récupérer les assurances.
     * @return une liste d'assurances correspondant aux critères spécifiés.
     */
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
    
    
    /**
     * Génère un modèle de table contenant les données des assurances filtrées.
     * 
     * @param valeurComboBox Le type de contrat sélectionné dans la comboBox ("Tous" ou un type spécifique).
     * @param annee L'année pour laquelle les assurances doivent être affichées.
     * @return Un objet DefaultTableModel contenant les données formatées pour affichage dans une JTable.
     */
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
    
    /**
     * Remplit la comboBox avec les différents types de contrats disponibles.
     * Ajoute une option "Tous" pour permettre la sélection de tous les types.
     */
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

    /**
     * Initialise la table des assurances en affichant toutes les assurances de l'année 2025.
     * Utilise un modèle de table généré à partir des données récupérées.
     */
	public void initialiserTable() {
		DefaultTableModel model = getTableModelAssurances("Tous", 2025);
		this.fenAfficherAssurances.setTableAssurances(model);
		
	}
	
	/**
	 * Cette méthode affiche une boîte de dialogue pour confirmer la suppression d'une entreprise.
	 * Si l'utilisateur confirme, la méthode de suppression de l'assurance est appelée.
	 */
	private void confirmerSuppression() {
        int response = javax.swing.JOptionPane.showConfirmDialog(
            this.fenAfficherAssurances,
            "Êtes-vous sûr de vouloir supprimer cette entreprise ?",
            "Confirmation de suppression",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        if (response == javax.swing.JOptionPane.YES_OPTION) {
            supprimerAssurance();
        }
    }
	
	/**
     * Effectue la suppression de l'assurance
     */
    private void supprimerAssurance() {
        int ligneSelect = this.fenAfficherAssurances.getSelectedRow();   
        if (ligneSelect != -1) {
            String numeroContrat = this.fenAfficherAssurances.getValueAt(ligneSelect, 0).toString();
            String annee = this.fenAfficherAssurances.getValueAt(ligneSelect, 1).toString();
            
            try {
            	Assurance assurance = daoAssurance.findById(numeroContrat, annee);
                daoAssurance.delete(assurance);
                initialiserTable(); 
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	
}
