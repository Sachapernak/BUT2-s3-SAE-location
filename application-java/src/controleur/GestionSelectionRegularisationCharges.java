package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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
import vue.SelectionRegularisationCharges;
import vue.VoirRegularisationCharges;
import vue.VoirSoldeToutCompte;

public class GestionSelectionRegularisationCharges {
	
	private SelectionRegularisationCharges fen;
	
	public GestionSelectionRegularisationCharges(SelectionRegularisationCharges selectionRegularisationCharges) {
		this.fen = selectionRegularisationCharges;
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
    
    /**
     * Charge la liste des baux pour le logement sélectionné, en utilisant un SwingWorker.
     * La table est mise à jour avec les résultats.
     */
    public void chargerList() {
        // Change le curseur pour indiquer un chargement en cours
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupère la liste des identifiants de baux associés au logement sélectionné
                return new DaoBail().findByIdLogement(fen.getSelectedBien()).stream()
                        .map(Bail::getIdBail)
                        .toList();
            }

            @Override
            protected void done() {
                try {
                    // Met à jour la table avec les résultats obtenus
                    List<String> baux = get();
                    fen.setTableBail(baux);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fen.afficherMessageErreur("Opération interrompue.");
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    fen.afficherMessageErreur(cause != null ? cause.getMessage() : "Erreur inconnue.");
                } finally {
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }


	public void gestionBtnGenerer(JButton btnGenerer) {
		btnGenerer.addActionListener(e -> 
		{
			if (checkInput()) {
				String dateFin = this.fen.getDate();
				String dateDebut = getDateDebut(dateFin);
				JDialog dialog = new VoirRegularisationCharges(fen.getSelectedLoc(), fen.getSelectedBail(), 
						dateDebut, dateFin);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else {
				fen.afficherMessageErreur("Les dates doivent etre  saisies au format yyyy-MM-dd."
						+ "\nExemple : 2024-12-24");
			}

			
		});
		
	}
	
	/**
	 * Vérifie si les dates sont soit nulles, soit du format yyyy-MM-dd
	 * @return vrai si les deux dates sont nulles ou du bon format et dateDebut < dateFin.
	 */
	private boolean checkInput() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    try {
	        String dateFin = fen.getDate();

	        LocalDate fin = null;

	        if (dateFin.isEmpty()) {
	            return false;
	        }

	        fin = LocalDate.parse(dateFin, formatter);
	 
	        if (fin != null) {
	            return true;
	        }

	        return false;
	    } catch (DateTimeParseException e) {
	        return false;
	    }
	}
	
	private String getDateDebut(String dateFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fin = LocalDate.parse(dateFin, formatter);
		return String.valueOf(fin.minusDays(365));
	}

}
