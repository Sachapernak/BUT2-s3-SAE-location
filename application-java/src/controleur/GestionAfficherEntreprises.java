package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
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
            handleSecteurSelection((JComboBox<String>) e.getSource());
        } else if (e.getSource() instanceof JButton) {
            handleButtonClick((JButton) e.getSource());
        }
    }

    
    
    // --------------------------------------------------------------------
   	//                 Méthodes privées de gestion d'actions
   	// --------------------------------------------------------------------
    
    
    /**
     * Gère l'événement de sélection dans la JComboBox pour filtrer les entreprises par secteur.
     */
    private void handleSecteurSelection(JComboBox<String> comboBoxSecteur) {
        String secteurSelectionne = (String) comboBoxSecteur.getSelectedItem();
        remplirTable(fenAfficherEntreprises.getTableEntreprises(), secteurSelectionne);
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
        int ligneSelect = fenAfficherEntreprises.getTableEntreprises().getSelectedRow();   
        if (ligneSelect != -1) {
            String siret = (String) fenAfficherEntreprises.getTableEntreprises().getValueAt(ligneSelect, 0);
            try {
            	Entreprise entreprise = daoEntreprise.findById(siret);
                daoEntreprise.delete(entreprise);
                remplirTable(fenAfficherEntreprises.getTableEntreprises(), "Tous");  
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
     * Remplit une table Swing (JTable) avec les données des entreprises.
     * Les données sont récupérées depuis la base de données via le DAO et affichées dans le tableau.
     * 
     * @param tableEntreprises la JTable à remplir avec les données des entreprises.
     */
    
    public void remplirTable(JTable tableEntreprises, String secteurSelectionne) {
    	UtilitaireTable.viderTable(tableEntreprises);
    	try{
			List<Entreprise> entreprises = daoEntreprise.findAll();
			DefaultTableModel model = (DefaultTableModel) tableEntreprises.getModel();
			model.setRowCount(0);
		    for (Entreprise entreprise : entreprises) {
		    	if (secteurSelectionne.equals("Tous") || entreprise.getSecteur().equals(secteurSelectionne))
		        model.addRow(new Object[] { entreprise.getSiret(), entreprise.getNom(), entreprise.getAdresse().getAdressePostale()});
		    }
    	} catch (SQLException | IOException e) {
			e.printStackTrace();
		} 
    }
    
    public void remplirComboBox(JComboBox<String> comboBoxSecteur) {
    	 comboBoxSecteur.addItem("Tous");
    	try {
            List<Entreprise> entreprises = daoEntreprise.findAll();
            for (Entreprise entreprise : entreprises) {
                comboBoxSecteur.addItem(entreprise.getSecteur());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
  
}
