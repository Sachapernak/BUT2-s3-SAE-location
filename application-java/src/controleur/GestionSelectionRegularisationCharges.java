package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import vue.FenetrePrincipale;
import vue.SelectionRegularisationCharges;
import vue.VoirRegularisationCharges;

/**
 * Contrôleur pour la sélection de la régularisation des charges.
 * Gère les interactions entre la vue {@link SelectionRegularisationCharges} et les opérations métier associées.
 */
public class GestionSelectionRegularisationCharges {
	
	private SelectionRegularisationCharges fen;
	private FenetrePrincipale fenPrincipale;
	
	/**
	 * Constructeur qui initialise le contrôleur avec les vues nécessaires.
	 * @param selectionRegularisationCharges la vue de sélection pour la régularisation des charges.
	 * @param fp la fenêtre principale de l'application.
	 */
	public GestionSelectionRegularisationCharges(SelectionRegularisationCharges selectionRegularisationCharges, FenetrePrincipale fp) {
		this.fen = selectionRegularisationCharges;
		this.fenPrincipale = fp;
	}
	
    /**
     * Charge la liste des locataires pour le bien sélectionné et met à jour la ComboBox correspondante.
     * Utilise un {@link SwingWorker} pour ne pas bloquer l'interface utilisateur.
     */
    public void chargerComboBoxLoc() {
        String idBien = fen.getSelectedBien();
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupère la liste des identifiants de locataires associés au bien sélectionné
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
     * Charge la liste des identifiants de logements et met à jour la ComboBox correspondante.
     * Utilise un {@link SwingWorker} pour récupérer les données en arrière-plan.
     */
    public void chargerComboBoxLogement() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupère la liste des identifiants de tous les logements
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
     * Ajoute un écouteur pour gérer les changements de sélection dans la ComboBox des locataires.
     * @param combo la ComboBox des locataires.
     */
    public void gestionComboLoc(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerList();
            }
        });
    }
    
    /**
     * Ajoute un écouteur pour gérer les changements de sélection dans la ComboBox des logements.
     * @param combo la ComboBox des logements.
     */
    public void gestionComboBien(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerComboBoxLoc();
            }
        });
    }
    
    /**
     * Charge la liste des baux pour le logement sélectionné et met à jour la table correspondante.
     * Utilise un {@link SwingWorker} pour exécuter la tâche en arrière-plan.
     */
    public void chargerList() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupère les identifiants des baux liés au logement sélectionné
                return new DaoBail().findByIdLogement(fen.getSelectedBien()).stream()
                        .map(Bail::getIdBail)
                        .toList();
            }

            @Override
            protected void done() {
                try {
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

    /**
     * Configure le bouton "Générer" pour afficher la fenêtre de visualisation des régularisations charges.
     * @param btnGenerer le bouton à configurer.
     */
	public void gestionBtnGenerer(JButton btnGenerer) {
		btnGenerer.addActionListener(e -> {
			if (checkInput()) {
				String dateFin = this.getDateFin();
				String dateDebut = this.getDateDebut(dateFin);
				// Crée et affiche la fenêtre de visualisation des régularisations charges
				JDialog dialog = new VoirRegularisationCharges(
                        fen.getSelectedLoc(), 
                        fen.getSelectedBail(), 
                        dateDebut, 
                        dateFin, 
                        this.fenPrincipale
                );
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else {
				fen.afficherMessageErreur("Les dates doivent etre saisies au format yyyy-MM-dd."
						+ "\nExemple : 2024-12-24");
			}
		});
	}
	
	/**
	 * Vérifie si la date entrée est valide (soit vide, soit au format yyyy-MM-dd).
	 * @return vrai si la date est valide, faux sinon.
	 */
	private boolean checkInput() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    try {
	        String dateFin = fen.getDate();

	        if (!dateFin.isEmpty()) { 
                LocalDate.parse(dateFin, formatter);
            }
	        return true;
	    } catch (DateTimeParseException e) {
	        return false;
	    }
	}
	
	/**
	 * Calcule la date de début correspondant à une période d'un an avant la date de fin donnée.
	 * @param dateFin la date de fin au format yyyy-MM-dd.
	 * @return la date de début calculée.
	 */
	private String getDateDebut(String dateFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fin = LocalDate.parse(dateFin, formatter);
		return String.valueOf(fin.minusDays(365));
	}
	
	/**
	 * Récupère la date de fin à utiliser. Si aucune date n'est spécifiée, renvoie la date actuelle.
	 * @return la date de fin.
	 */
	private String getDateFin() {
		String date = this.fen.getDate();
		if (date.isEmpty()) {
			return String.valueOf(LocalDate.now());
		}
		return date;
	}
}
