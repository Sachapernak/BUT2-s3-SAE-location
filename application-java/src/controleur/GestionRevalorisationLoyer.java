package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import modele.Loyer;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoLoyer;
import vue.RevalorisationLoyer;
import vue.SetICC;

public class GestionRevalorisationLoyer {

	private RevalorisationLoyer fen;
	
	public GestionRevalorisationLoyer(RevalorisationLoyer rl)  {
		this.fen = rl;
	}
	
    public void chargerComboBoxLogement() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoBienLocatif().findAll().stream()
                                                   .map(e -> e.getIdentifiantLogement())
                                                   .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> noms = get();
                    fen.setListLog(noms);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur("Opération interrompue.");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof SQLException || cause instanceof IOException) {
                        fen.afficherMessageErreur(cause.getMessage());
                    } else {
                        fen.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
                    }
                } finally {
                    fen.setCursor(Cursor.getDefaultCursor());
                    
                    fen.setItemInCombo(fen.getIdLog() == null ? "" : fen.getIdLog());
                    
                    chargerInfoLoyer();
                }
            }
        }.execute();
    }
    
    public void chargerInfoLoyer() {
    	String idLog = fen.getSelectedIdLog();
    	DaoLoyer dao = new DaoLoyer();
    	String ancienLoyer;
		try {
			ancienLoyer = dao.getDernierLoyer(idLog).toString();

	    	fen.setAncienLoyer(ancienLoyer);
	    	
	    	if (!dao.estLoyerAugmentable(idLog)) {
	    		fen.setLoyerMax(ancienLoyer);
	    	} else {
	    		fen.setLoyerMax(dao.getMaxLoyer(idLog).toString());
	    	}
	    	
		} catch (SQLException | IOException e) {
			fen.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
    }
	
	public void setIdLog() {
		fen.setItemInCombo(fen.getIdLog());
	}

	public void gestionBtnVoirICC(JButton btnICC) {
		btnICC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(fen);

	            if (parentFrame != null) {
	                // Ajouter SetICC à la fenêtre principale
	                SetICC stIcc = new SetICC();
	                JLayeredPane fenLayerPane = parentFrame.getLayeredPane();
	                fenLayerPane.add(stIcc, JLayeredPane.PALETTE_LAYER);
	                stIcc.setVisible(true);
	            } else {
	               fen.afficherMessageErreur("La fenetre parente est introuvable !");;
	            }
			}
		});
	}
	
	public void gestionBtnQuitter(JButton btnQuitter) {
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fen.dispose();
			}
		});
	}
	
	public void gestionBtnRevaloriser(JButton btnReval) {
		btnReval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String dateActuelle = LocalDate.now().format(formatter);
				
				Loyer loyer = new Loyer(fen.getSelectedIdLog(), dateActuelle ,new BigDecimal(fen.getValeurNouveauLoyer()));

				try {
					new DaoLoyer().create(loyer);
					fen.dispose();
				} catch (SQLException | IOException e1) {
					fen.afficherMessageErreur(e1.getMessage());
					e1.printStackTrace();
				}

			}
		});
	}
	
    public void gestionActionComboLog(JComboBox<String> combo) {
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                	chargerInfoLoyer();
                }
            }
        });
    }
	
}
