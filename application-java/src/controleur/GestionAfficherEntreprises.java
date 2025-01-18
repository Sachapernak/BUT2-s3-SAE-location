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
import javax.swing.table.DefaultTableModel;

import modele.Entreprise;
import modele.dao.DaoEntreprise;
import vue.AfficherEntreprises;
import vue.AjouterEntreprise;

public class GestionAfficherEntreprises implements ActionListener{

    private final DaoEntreprise daoEntreprise;
    private final AfficherEntreprises fenAfficherEntreprises;


    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param ae  la vue pour ajouter une entreprise
     */
    public GestionAfficherEntreprises(AfficherEntreprises ae) {
        this.fenAfficherEntreprises = ae;
        this.daoEntreprise = new DaoEntreprise();
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

    
    
    // --------------------------------------------------------------------
   	//                 Méthodes privées de gestion d'actions
   	// --------------------------------------------------------------------
    
    
    /**
     * Gère l'événement de sélection dans la JComboBox pour filtrer les entreprises par secteur.
     */
    private void handleSecteurSelection() {
        String secteurSelectionne =  this.fenAfficherEntreprises.getTypeSecteurActivitetCombo();
        remplirTable(secteurSelectionne);
    }

    /**
     * Gère les clics sur les boutons (Quitter, Ajouter, Supprimer, etc.).
     */
    private void handleButtonClick(JButton btnActif) {
        String btnLibelle = btnActif.getText();
        switch (btnLibelle) {
            case "Quitter":
                this.fenAfficherEntreprises.dispose();
                break;
            case "Ajouter":
                ouvrirFenetreAjout();
                break;
            case "Supprimer":
                confirmerSuppression();
                break;
            default:
                break;
        }
    }
    
    private void confirmerSuppression() {
        int response = javax.swing.JOptionPane.showConfirmDialog(
            this.fenAfficherEntreprises,
            "Êtes-vous sûr de vouloir supprimer cette entreprise ?",
            "Confirmation de suppression",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        if (response == javax.swing.JOptionPane.YES_OPTION) {
            supprimerEntreprise();
        }
    }

    /**
     * Effectue la suppression de l'entreprise.
     */
    private void supprimerEntreprise() {
        int ligneSelect = fenAfficherEntreprises.getSelectedRow();   
        if (ligneSelect != -1) {
            String siret = (String) fenAfficherEntreprises.getValueAt(ligneSelect, 0);
            try {
            	Entreprise entreprise = daoEntreprise.findById(siret);
                daoEntreprise.delete(entreprise);
                remplirTable(this.fenAfficherEntreprises.getTypeSecteurActivitetCombo());
                
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ouvre la fenêtre d'ajout d'entreprise.
     */
    private void ouvrirFenetreAjout() {
        JLayeredPane fenLayerPane = this.fenAfficherEntreprises.getLayeredPane();
        AjouterEntreprise ae = new AjouterEntreprise();
        fenLayerPane.add(ae, JLayeredPane.PALETTE_LAYER);
        ae.setVisible(true);
    }

    
    
    /**
     * Récupère une liste d'assurances filtrées en fonction d'un secteur d'activité.
     * 
     * @param valeurComboBox la valeur sélectionnée dans la comboBox (peut être "Tous" ou un secteur spécifique).
     * @return une liste d'assurances correspondant aux critères spécifiés.
     */
    public List<Entreprise> recupererEntreprises(String valeurComboBox) {
    	List<Entreprise> entreprisesFiltre = new ArrayList<>();
    	try {
			List<Entreprise> entreprises = daoEntreprise.findAll();
			for (Entreprise entreprise : entreprises) {
				if (valeurComboBox.equals("Tous") || entreprise.getSecteur().equals(valeurComboBox)) {
					entreprisesFiltre.add(entreprise);
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    	return entreprisesFiltre;	
    }
    
    /**
     * Génère un modèle de table contenant les données des entreprises filtrées.
     * 
     * @param valeurComboBox Le secteur d'activité sélectionné dans la comboBox ("Tous" ou un secteur spécifique).
     * @return Un objet DefaultTableModel contenant les données formatées pour affichage dans une JTable.
     */
    public DefaultTableModel getTableModelEntreprises(String valeurComboBox) {
    	List<Entreprise> entreprises = recupererEntreprises(valeurComboBox);
    	
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] { "Siret", "Nom", "Adresse" });

        for (Entreprise entreprise : entreprises) {
            tableModel.addRow(new Object[] {
                entreprise.getSiret(),
                entreprise.getNom(), 
                entreprise.getSecteur()
            });
        }
        return tableModel;
    }
    
    /**
     * Initialise la table des assurances en affichant toutes les assurances de l'année 2025.
     * Utilise un modèle de table généré à partir des données récupérées.
     */
	public void initialiserTable() {
		DefaultTableModel model = getTableModelEntreprises("Tous");
		this.fenAfficherEntreprises.setTableEntreprises(model);
	}
	
	public void remplirTable(String valeurComboBox) {
		DefaultTableModel model = getTableModelEntreprises(valeurComboBox);
		this.fenAfficherEntreprises.setTableEntreprises(model);
	}
	   
    
    /**
     * Remplit la comboBox avec les différents secteurs  d'activité des entreprises.
     * Ajoute une option "Tous" pour permettre la sélection de tous les secteurs.
     */
    public void remplirComboBox() {
    	List<Entreprise> entreprises;
		try {
			entreprises = daoEntreprise.findAll();
	    	List<String> secteur = new ArrayList<>();
	    	secteur.add("Tous");
	    	for (Entreprise entreprise : entreprises) {
	    		secteur.add(entreprise.getSecteur());
	    	}
	    	this.fenAfficherEntreprises.setComboBoxSecteursActivite(secteur);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
    }
    
    
    
    
  
}
