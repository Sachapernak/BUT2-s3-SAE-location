package controleur;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.io.File;
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
import rapport.RapportRegularisation;
import vue.DetailProvParBail;
import vue.RevalorisationCharge;

/**
 * Contrôleur pour la gestion de la revalorisation des charges.
 * Gère les interactions entre la vue {@link RevalorisationCharge} et les opérations métier.
 */
public class GestionRevalorisationCharges {
	
	private RevalorisationCharge fen;

	/**
	 * Constructeur qui initialise le contrôleur avec la vue correspondante.
	 * @param fen la fenêtre de revalorisation des charges.
	 */
	public GestionRevalorisationCharges(RevalorisationCharge fen) {
		this.fen = fen;
	}

	/**
	 * Configure le bouton "Historique" pour afficher le détail de l'historique des provisions pour un bail sélectionné.
	 * @param btnHistorique le bouton historique à configurer.
	 */
	public void gestionBtnHistorique(JButton btnHistorique) {
		btnHistorique.addActionListener(e -> {
            DetailProvParBail dialog = new DetailProvParBail(fen.getSelectedIdBail());
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
		});
	}

	/**
	 * Configure le bouton "Quitter" pour fermer la fenêtre.
	 * @param btnQuitter le bouton quitter à configurer.
	 */
	public void gestionBtnQuitter(JButton btnQuitter) {
		btnQuitter.addActionListener(e -> fen.dispose());
	}

	/**
	 * Configure le bouton "Revaloriser" pour créer une nouvelle provision charge et, si on viens d'une régularisation,
	 * générer un rapport.
	 * @param btnModifier le bouton revaloriser à configurer.
	 */
	public void gestionBtnRevaloriser(JButton btnModifier) {
		btnModifier.addActionListener(e -> {
			try {
				// Création d'une nouvelle provision de charge
				ProvisionCharge prov = new ProvisionCharge(
                    fen.getSelectedIdBail(), 
					fen.getDate().isEmpty() ? LocalDate.now().toString() : fen.getDate(), 
					new BigDecimal(fen.getValeurNouvelleCharges())
                );
				
				new DaoProvisionCharge().create(prov);
				
				// Génération du rapport si nécessaire
				if (fen.getRap() != null) {
					genererRapport();
				}
				
			} catch (SQLException | IOException ex) {
				ex.printStackTrace();
				fen.afficherMessageErreur(ex.getMessage());
			}
		});
	}
	
	/**
	 * Génère et ouvre un document de régularisation des charges.
	 * Affiche un message d'erreur si le fichier du rapport ne peut être généré ou ouvert.
	 */
	public void genererRapport() {
		RapportRegularisation rap = this.fen.getRap();
		rap.setNouvProv(fen.getValeurNouvelleCharges());
		
        String cheminFichier;
		try {
			cheminFichier = rap.genererSoldeToutCompte(rap.getNomFichier());
			
			// Ouvrir le fichier une fois créé
	        File fichier = new File(cheminFichier);
	        
	        if (fichier.exists()) {
	            Desktop.getDesktop().open(fichier);
	            fen.dispose();
	        } else {
	            fen.afficherMessageErreur("Le fichier n'a pas été trouvé : " + cheminFichier);
	        }
			
		} catch (IOException e) {
            fen.afficherMessageErreur("Erreur lors de la génération ou de l'ouverture du fichier : " + e.getMessage());
            e.printStackTrace();
		}
	}

    /**
     * Charge de manière asynchrone la liste des baux actifs dans la ComboBox de la vue.
     * Utilise un {@link SwingWorker} pour ne pas bloquer l'interface utilisateur pendant la récupération.
     */
    public void chargerComboBoxBail() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupération des identifiants de baux actifs en arrière-plan
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

	/**
	 * Gère l'action sur la sélection d'un élément dans la ComboBox des baux.
	 * @param combo la ComboBox à laquelle ajouter l'écouteur d'événements.
	 */
	public void gestionActionComboLog(JComboBox<String> combo) {	
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
            	chargerInfoCharges();
            }
        });
	}
	
	/**
	 * Charge et affiche les informations de charges pour le bail sélectionné.
	 */
	public void chargerInfoCharges() {
  		try {
			Bail bai = new DaoBail().findById(fen.getSelectedIdBail());
			
			if (bai != null) {
				List<ProvisionCharge> provs = bai.getProvisionCharge();

    			if(!provs.isEmpty()) {
    				BigDecimal val = provs.get(0).getProvisionPourCharge();
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
	
	/**
	 * Ajuste l'affichage des champs dans la vue en fonction de si on a la nouvelle valeur de charge suggerée.
	 */
	public void gestionAffichageChamps() {
		if (this.fen.getNouvelleValeur() == null) {
			this.fen.setVisibleComboBoxBail(true);
			this.fen.setVisibleChampsValeurConseillee(false);
		} else {
			fen.setDate(LocalDate.now().toString());
			this.fen.setVisibleComboBoxBail(false);
			this.fen.setVisibleChampsValeurConseillee(true);
			initialiserMontantConseille();
		}
	}
	
	/**
	 * Initialise le montant conseillé dans la vue en utilisant la nouvelle valeur fournie.
	 */
	public void initialiserMontantConseille() {
		BigDecimal newValeur = this.fen.getNouvelleValeur();
		String newValStr = String.valueOf(newValeur);
		this.fen.setTextValConseille(newValStr);
	}
}
