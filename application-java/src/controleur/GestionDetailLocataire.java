package controleur;

import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingWorker;

import modele.Locataire;
import vue.DetailLocataire;

public class GestionDetailLocataire {
	
	DetailLocataire fen;
	
	public GestionDetailLocataire(DetailLocataire fen) {
		this.fen = fen;
	}
    
    /**
     * Charge les informations du locataire dans la fenêtre DetailLocataire.
     * 
     * @param fen    La fenêtre de détail dans laquelle on injecte les données.
     * @param loc    Le locataire dont on veut afficher les informations.
     */
    public void chargerLocataireDansFenetre(Locataire loc) {
    	
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                // Remplit tous les champs de la fenêtre
                fen.setTxtID(loc.getIdLocataire());
                fen.setTxtNom(loc.getNom());
                fen.setTxtPrenom(loc.getPrenom());
                fen.setTxtDateNaissance(loc.getDateNaissance());
                fen.setTxtLieuNaissance(loc.getLieuDeNaissance());
                fen.setTxtTelephone(loc.getTelephone());
                fen.setTxtEmail(loc.getEmail());
                
                // Pour l'adresse
                if (loc.getAdresse() != null) {
                    // On suppose que loc.getAdresse().toString() nous suffit
                    fen.setTxtAdresse(loc.getAdresse().toString());
                } else {
                    fen.setTxtAdresse("Pas d'adresse renseignée");
                }
				return null;


            }
        }.execute();
        
        chargerBaux(loc);
        
        
    }

	private void chargerBaux(Locataire loc) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                List<String> baux = loc.getContrats().stream()
                		.map(ctr -> ctr.getBail().getIdBail())
                		.toList();
                
                fen.chargerBaux(baux);
                
                return null;
            }
        }.execute();
		
	}
	
	public void gestionBtnFermer(JButton cancelButton) {
		cancelButton.addActionListener(e ->fen.dispose());
	}
}
