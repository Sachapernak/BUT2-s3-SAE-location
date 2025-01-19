package controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import modele.Bail;
import modele.ProvisionCharge;
import modele.dao.DaoBail;
import vue.DetailProvParBail;

public class GestionDetailProv {
	
	DetailProvParBail fen;
	
	public GestionDetailProv(DetailProvParBail fen) {
		this.fen = fen;
	}
	
	public void chargerFen() {
		chargerTitre();
		chargerTableProvs();
	}
	
	public void chargerTitre() {
		fen.setTitreBail(fen.getIdBail());
	}
	
	
	public void chargerTableProvs() {
		
        String bail = fen.getIdBail();
        if (bail == null) return;

        fen.setWaitCursor(true);

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                // Récup du bail
                Bail bai = new DaoBail().findById(bail);
                
                // Renvoie de la liste de date, valeur
                return bai.getProvisionCharge().stream()
                		.map(e ->  new Object[] {e.getDateChangement(), e.getProvisionPourCharge().toString()})
                		.toList();
            }

            @Override
            protected void done() {
                try {
                    // Résultat : liste des provs 
                	List<Object[]> data = get();
                    fen.chargerTableCharges(data);

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
				new DaoBail().findById(fen.getIdBail()).removeProvisionCharge(
						new ProvisionCharge(fen.getIdBail(), fen.getSelectedDate(), BigDecimal.ZERO));
			} catch (SQLException | IOException e1) {
				fen.afficherMessageErreur(e1.getMessage());
				e1.printStackTrace();
			}
			
			chargerTableProvs();
		});
    }

}
