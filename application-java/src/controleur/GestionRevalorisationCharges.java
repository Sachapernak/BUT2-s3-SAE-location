package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import modele.Bail;
import modele.ProvisionCharge;
import modele.dao.DaoBail;
import modele.dao.DaoProvisionCharge;
import vue.DetailProvParBail;
import vue.RevalorisationCharge;

public class GestionRevalorisationCharges {
	
	private RevalorisationCharge fen;

	public GestionRevalorisationCharges(RevalorisationCharge fen) {
		this.fen = fen;
	}

	public void gestionBtnHistorique(JButton btnHistorique) {
		btnHistorique.addActionListener(e -> {
            DetailProvParBail dialog = new DetailProvParBail(fen.getSelectedIdBail());
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
		});
		
	}

	public void gestionBtnQuitter(JButton btnQuitter) {
		btnQuitter.addActionListener(e -> {
			fen.dispose();
		});
		
	}

	public void gestionBtnRevaloriser(JButton btnModifier) {
		btnModifier.addActionListener(e -> {
			try {
				
				System.out.println(fen.getValeurNouvelleCharges());
				
				ProvisionCharge prov = new ProvisionCharge(fen.getSelectedIdBail(), 
						fen.getDate().isEmpty() ? LocalDate.now().toString() : fen.getDate(), 
								new BigDecimal(fen.getValeurNouvelleCharges()));
				
				new DaoProvisionCharge().create(prov);
				
			} catch (SQLException | IOException ex) {
				ex.printStackTrace();
				fen.afficherMessageErreur(ex.getMessage());
			}

			
		});
		
	}

    /**
     * Charge de manière asynchrone la liste bails actifs dans la ComboBox.
     * Utilise un SwingWorker pour éviter de bloquer l'interface utilisateur.
     */
    public void chargerComboBoxBail() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupération des identifiants de logements en arrière-plan
                return new DaoBail().findAll().stream()
                							.filter(e -> {
                								String date = e.getDateDeFin();
                								return (date == null || date.compareTo(LocalDate.now().toString()) > 1);
                							})
                						   .map(Bail::getIdBail)
                                           .toList();
            }

            @Override
            protected void done() {
                try {
                	
                    List<String> noms = get();
                    fen.setListBail(noms);
                    
                } catch (InterruptedException e) {
                	
                	e.printStackTrace();
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur("Opération interrompue.");
                    
                } catch (ExecutionException e) {
                	
                	e.printStackTrace();
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
                    }
                    
                } finally {
                    fen.setItemInCombo(fen.getIdLog() == null ? "" : fen.getIdLog());
                    chargerInfoCharges();
                    fen.setCursor(Cursor.getDefaultCursor());
                    
                }
            }
        }.execute();
    }

	public void gestionActionComboLog(JComboBox<String> combo) {
		
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerInfoCharges();
            }
        });
	}
	
	public void chargerInfoCharges() {
  		try {
			Bail bai = new DaoBail().findById(fen.getSelectedIdBail());
			
			if (bai != null) {
				List<ProvisionCharge> provs = bai.getProvisionCharge();

    			if( !provs.isEmpty() ) {
    				
    				BigDecimal val = bai.getProvisionCharge().get(0).getProvisionPourCharge();
    				fen.setAnciennesCharges(val.toString());
    			} else {
    				fen.setAnciennesCharges("Aucune");
    			}
			}		    

		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void gestionAffichageChamps() {
		if (this.fen.getNouvelleValeur() == null || this.fen.getNouvelleValeur().isEmpty()) {
			this.fen.setVisibleComboBoxBail(true);
			this.fen.setVisibleChampsValeurConseillee(false);
		} else {
			this.fen.setVisibleComboBoxBail(false);
			this.fen.setVisibleChampsValeurConseillee(true);
		}
	}

}
