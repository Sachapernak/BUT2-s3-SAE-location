package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;

import modele.Assurance;
import modele.dao.DaoAssurance;
import vue.AjouterAssurance;


public class GestionAjouterAssurance implements ActionListener{

    private final DaoAssurance daoAssurance;
    private final AjouterAssurance fenAjouterAssurance;
    private VerificationChamps verifChamps;
   
    /**
     * Constructeur principal : injecte la vue et instancie les DAO.
     *
     * @param ae  la vue pour ajouter une assurance
     */
    public GestionAjouterAssurance(AjouterAssurance aa) {
        this.fenAjouterAssurance = aa;
        this.daoAssurance = new DaoAssurance();
        this.verifChamps = new VerificationChamps();
        
    }

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
    @Override
	public void actionPerformed(ActionEvent e) {

		JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Valider" : 
				if (!gererErreurs()) {
                    creerAssurance();
    				this.fenAjouterAssurance.dispose();
                }
				break;
			case "Annuler" : 
				this.fenAjouterAssurance.dispose();
				break;
			default:
				break;
		}
		
	}

    
    /**
     * Vérifie les erreurs de saisie dans la vue AjouterBail.
     *
     * @return true si une erreur est trouvée, false sinon
     */
    private boolean gererErreurs() {
        boolean erreurTrouvee = false;

        if (!verifChamps.champsRemplis(this.fenAjouterAssurance.getChampsObligatoiresAssurance())) {
        	this.fenAjouterAssurance.afficherMessageErreur(
                    "Les champs obligatoires (dotés d'une étoile) doivent être renseignés"
                );
                erreurTrouvee = true;            
        }else if (!verifChamps.validerAnnee(this.fenAjouterAssurance.getTextAnnee())) {
                this.fenAjouterAssurance.afficherMessageErreur("L'année doit être un entier composé de 4 chiffres");
                erreurTrouvee = true;
        }
        return erreurTrouvee;
    }
    
    private Assurance recupererAssurance() {
    	String numContrat = this.fenAjouterAssurance.getTextNumeroContrat();
    	String anneeStr = this.fenAjouterAssurance.getTextAnnee(); 
    	int annee = Integer.parseInt(anneeStr);
    	String type = this.fenAjouterAssurance.getTextTypeContrat();
        	
    	return new Assurance(numContrat, annee, type);
    }
    
    private void creerAssurance() {
    	Assurance newAssurance = recupererAssurance();
    	try {
			daoAssurance.create(newAssurance);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    }
        
	
}
