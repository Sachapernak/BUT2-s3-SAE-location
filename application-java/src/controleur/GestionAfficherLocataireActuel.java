package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import modele.Bail;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoBail;
import modele.dao.DaoContracter;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterLocataire;
import vue.VoirSoldeToutCompte;

/**
 * Contrôleur gérant les actions sur la vue {@link AfficherLocatairesActuels}.
 * <p>
 * Actions possibles :
 * <ul>
 *     <li>Ajouter un locataire</li>
 *     <li>Modifier un locataire</li>
 *     <li>Résilier le bail</li>
 *     <li>Retour</li>
 * </ul>
 */
public class GestionAfficherLocataireActuel implements ActionListener {

    private final AfficherLocatairesActuels fenAfficherLocatairesActuels;
    private final GestionChampsLocataireActuel gestionChampsLoc;
    private final DaoLocataire daoLocataire;
    private final DaoBail daoBail;

    /**
     * Constructeur principal.
     *
     * @param afl la vue {@link AfficherLocatairesActuels}
     */
    public GestionAfficherLocataireActuel(AfficherLocatairesActuels afl) {
        this.fenAfficherLocatairesActuels = afl;
        this.gestionChampsLoc = new GestionChampsLocataireActuel(afl);
        this.daoLocataire = new DaoLocataire();
        this.daoBail = new DaoBail();
    }

    /**
     * Gère les actions sur les boutons de la vue.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();

        switch (btnLibelle) {
            case "Retour":
                fenAfficherLocatairesActuels.dispose();
                break;

            case "Modifier":
                modifierLocataireSelectionne();
                break;

            case "Ajouter":
                afficherFenetreAjouterLocataire();
                break;

            case "Résilier le bail":

				resilierBailSelection();
                break;

            default:
                // Aucune autre action
                break;
        }
    }

    /**
     * Lance l'action de modification du locataire sélectionné dans la table.
     */
    private void modifierLocataireSelectionne() {
        JTable tableLoc = fenAfficherLocatairesActuels.getTableLocatairesActuels();
        int ligneSelect = tableLoc.getSelectedRow();

        if (ligneSelect > -1) {
            fenAfficherLocatairesActuels.setCursorAttente(true);

            new SwingWorker<Void, Void>() {
                Locataire loc;

                @Override
                protected Void doInBackground() throws Exception {
                    loc = lireLocataireDepuisTable(tableLoc, ligneSelect);
                    if (loc != null) {
                        loc.setNom((String) tableLoc.getValueAt(ligneSelect, 1));
                        loc.setPrenom((String) tableLoc.getValueAt(ligneSelect, 2));
                        loc.setDateNaissance(fenAfficherLocatairesActuels.getTextFieldDateDeNaissance().getText());
                        loc.setEmail(fenAfficherLocatairesActuels.getTextFieldMail().getText());
                        loc.setTelephone(fenAfficherLocatairesActuels.getTextFieldTel().getText());

                        // Mise à jour en base
                        daoLocataire.update(loc);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get(); // Déclenche l'exception si une erreur est survenue
                        if (loc != null) {
                            JOptionPane.showMessageDialog(
                                fenAfficherLocatairesActuels,
                                "Modification effectuée",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        fenAfficherLocatairesActuels.afficherMessageErreur("Modification interrompue.");
                    } catch (ExecutionException ex) {
                        fenAfficherLocatairesActuels.afficherMessageErreur(
                                "Erreur : " + ex.getCause().getMessage()
                        );
                    } finally {
                        fenAfficherLocatairesActuels.setCursorAttente(false);
                    }
                }
            }.execute();
        }
    }

    /**
     * Affiche la fenêtre permettant d'ajouter un nouveau locataire.
     */
    private void afficherFenetreAjouterLocataire() {
        AjouterLocataire al = new AjouterLocataire(fenAfficherLocatairesActuels);
        JLayeredPane layeredPane = fenAfficherLocatairesActuels.getLayeredPane();
        layeredPane.add(al, JLayeredPane.PALETTE_LAYER);
        al.setVisible(true);
    }

    /**
     * Résilie le bail pour le locataire et le bien sélectionnés.
     * @throws IOException 
     * @throws SQLException 
     */
    private void resilierBailSelection(){
    	
        fenAfficherLocatairesActuels.setCursorAttente(true);
        
        String idLoc = fenAfficherLocatairesActuels.getSelectedIdLoc();
        String idBail = fenAfficherLocatairesActuels.getSelectedIdBail();

        
        if (!idLoc.isEmpty()
        		&& !idBail.isEmpty()) {

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                		
                		try {
                			
                			DaoContracter dao = new DaoContracter();
                			String dateDebut = dao.findDate(idLoc, idBail);
                            VoirSoldeToutCompte fenSoldeCompte = new VoirSoldeToutCompte(idLoc, 
                            		                                  idBail,  dateDebut,  LocalDate.now().toString());
                			fenSoldeCompte.setVisible(true);
                			
                		} catch (SQLException | IOException e) {
                			e.printStackTrace();
                			fenAfficherLocatairesActuels.afficherMessageErreur(e.getMessage());
                		}

 
                		
                    return null;
                }

                @Override
                protected void done() {
                    fenAfficherLocatairesActuels.setCursorAttente(false);

                }
            }.execute();
            
        } else {
            JOptionPane.showMessageDialog(
                    fenAfficherLocatairesActuels,
                    "Vous devez sélectionner un locataire et un bien",
                    "Impossible de résilier le bail",
                    JOptionPane.ERROR_MESSAGE
            );
            fenAfficherLocatairesActuels.setCursorAttente(false);
        }
    }

    /**
     * Lit le locataire correspondant à la ligne sélectionnée dans la table.
     *
     * @param tableLoc la table contenant la liste des locataires
     * @param rowIndex l'indice de la ligne sélectionnée
     * @return le locataire si trouvé, null sinon
     * @throws SQLException si erreur en base
     * @throws IOException si erreur d'E/S
     */
    public Locataire lireLocataireDepuisTable(JTable tableLoc, int rowIndex) { 
        return gestionChampsLocLireLigneTable(tableLoc, rowIndex);
    }

    /**
     * Méthode déléguant à {@link GestionChampsLocataireActuel#lireLigneTable(JTable, int)}.
     */
    public Locataire gestionChampsLocLireLigneTable(JTable tableLoc, int rowIndex) {
        try {
			return this.gestionChampsLoc
			           .getClass()
			           .getDeclaredMethod("lireLigneTable", JTable.class, int.class)
			           .invoke(this.gestionChampsLoc, tableLoc, rowIndex) 
			           == null ? null 
			                   : (Locataire) this.gestionChampsLoc
			                                    .getClass()
			                                    .getDeclaredMethod("lireLigneTable", JTable.class, int.class)
			                                    .invoke(this.gestionChampsLoc, tableLoc, rowIndex);
		} catch (Exception e) {
			fenAfficherLocatairesActuels.afficherMessageErreur(e.getMessage());
			e.printStackTrace();
		}
		return null;
    }
    
    public Bail lireBailDepuisTable() {
    	try {
    		return new DaoBail().findById(fenAfficherLocatairesActuels.getSelectedIdBail());
    	} catch (SQLException | IOException e) {
    		e.printStackTrace();
    		fenAfficherLocatairesActuels.afficherMessageErreur(e.getMessage());
    		return null;
    	}
    	
    }
}
