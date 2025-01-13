package controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import modele.BienLocatif;
import modele.Loyer;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoLoyer;
import vue.DetailLoyersParLogement;

public class GestionDetailLoyers {
	
	DetailLoyersParLogement fen;
	
	public GestionDetailLoyers(DetailLoyersParLogement fen) {
		this.fen = fen;
	}
	
	public void chargerFen() {
		chargerTitre();
		chargerLoyerDeBase();
		chargerTableLoyer();
	}
	
	public void chargerTitre() {
		fen.setTitreLogement(fen.getIdLog());
	}
	
	public void chargerLoyerDeBase() {
		
		BienLocatif bien;
		try {
			bien = new DaoBienLocatif().findById(fen.getIdLog());
			fen.setLoyerDeBase(bien.getLoyerBase().toString());
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void chargerTableLoyer() {
		
        String bien = fen.getIdLog();
        if (bien == null) return;

        fen.setWaitCursor(true);

        new SwingWorker<List<Loyer>, Void>() {
            @Override
            protected List<Loyer> doInBackground() throws Exception {
                // Récupérer tous les loyers du locataire via la DAO
                // (Méthode demandée : DaoDocumentComptable.findLoyersByIdLocataire(String idLocataire))
                return new DaoLoyer().findByIdLogement(bien);
            }

            @Override
            protected void done() {
                try {
                    // Résultat : liste des loyers
                    List<Loyer> loyers = get();
                    
                    // Transformation de la liste en structure exploitable par la table
                    List<Object[]> data = loyers.stream().map(l -> 
                    	new Object[] {l.getDateDeChangement(), l.getMontantLoyer()}
                    ).toList();

                    fen.chargerTableLoyers(data);


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur("Opération interrompue (chargement loyers du bien).");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    fen.afficherMessageErreur("Erreur chargement : " + cause.getMessage());
                } finally {
                    fen.setWaitCursor(false);
                }
            }
        }.execute();
	}
	
    /**
     * Gère l'action du bouton "Quitter" pour fermer la fenêtre.
     */
    public void gestionBtnQuitter(JButton btnQuitter) {
        btnQuitter.addActionListener(e -> fen.dispose());
    }
    
    public void gestionBtnSupprimer(JButton btnSupprimer) {
        btnSupprimer.addActionListener(e -> 
        		{
					try {
						new DaoLoyer().delete(new Loyer(fen.getIdLog(), fen.getSelectedDate(), BigDecimal.ZERO));
						chargerTableLoyer();
					} catch (SQLException | IOException e1) {
						fen.afficherMessageErreur(e1.getMessage());
						e1.printStackTrace();
					}
				}
        		
        		);
    }
    

}
