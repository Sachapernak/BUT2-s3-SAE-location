package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.JDialog;

import modele.Bail;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoLocataire;
import vue.SelectionSoldeToutCompte;
import vue.VoirSoldeToutCompte;

public class GestionSoldeToutCompteSelection {
	
	private SelectionSoldeToutCompte fen;
	
	public GestionSoldeToutCompteSelection(SelectionSoldeToutCompte fen) {
		this.fen = fen;
	}
	
    /**
     * Charge la liste des logements pour le bâtiment sélectionné dans la ComboBox.
     */
    public void chargerComboBoxLoc() {
        String idBien = fen.getSelectedBien();
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return new DaoLocataire().findByIdBien(idBien).stream()
                                                   .map(e -> e.getIdLocataire())
                                                   .toList();
            }

            @Override
            protected void done() {
                try {
                    List<String> noms = get();
                    fen.setComboBoxLocataire(noms);
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
                    chargerList();
                }
            }
        }.execute();
    }
    
    /**
     * Charge la liste des logements pour le bâtiment sélectionné dans la ComboBox.
     */
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
                    fen.setComboBoxBien(noms);
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
                    chargerComboBoxLoc();
                }
            }
        }.execute();
    }
    
    /**
     * Gère l'action de changement de sélection sur la ComboBox des locataires
     */
    public void gestionComboLoc(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerList();
            }
        });
    }
    
    /**
     * Gère l'action de changement de sélection sur la ComboBox des bien.
     */
    public void gestionComboBien(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerComboBoxLoc();
            }
        });
    }
    
	public void chargerList() {
		try {
			fen.setTableBail(new DaoBail().findByIdLogement(fen.getSelectedBien()).stream()
					.map(Bail::getIdBail)
					.toList());
			
		} catch (Exception e) {
			fen.afficherMessageErreur(e.getMessage());
		}

	}

	public void gestionBtnGenerer(JButton btnGenerer) {
		btnGenerer.addActionListener(e -> 
		{
			JDialog dialog = new VoirSoldeToutCompte(fen.getSelectedLoc(), fen.getSelectedBail());
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		});
		
	}


}
