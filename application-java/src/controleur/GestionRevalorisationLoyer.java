package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
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

/**
 * Classe de gestion pour la revalorisation des loyers.
 * Gère les interactions entre la vue {@link RevalorisationLoyer} et les opérations sur la base de données via DAO.
 */
public class GestionRevalorisationLoyer {

    private RevalorisationLoyer fen;

    public GestionRevalorisationLoyer(RevalorisationLoyer rl)  {
        this.fen = rl;
    }
    
    /**
     * Charge de manière asynchrone la liste des identifiants de logements dans la ComboBox.
     * Utilise un SwingWorker pour éviter de bloquer l'interface utilisateur.
     */
    public void chargerComboBoxLogement() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                // Récupération des identifiants de logements en arrière-plan
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
                    // Sélectionne l'élément courant dans la ComboBox
                    fen.setItemInCombo(fen.getIdLog() == null ? "" : fen.getIdLog());
                    // Charger les informations de loyer pour le logement sélectionné
                    chargerInfoLoyer();
                }
            }
        }.execute();
    }
    
    /**
     * Charge de manière asynchrone les informations du loyer pour le logement sélectionné.
     * Utilise un SwingWorker pour éviter de bloquer l'interface utilisateur.
     */
    public void chargerInfoLoyer() {
        String idLog = fen.getSelectedIdLog();
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<LoyerInfo, Void>() {
            @Override
            protected LoyerInfo doInBackground() throws Exception {
                return loadLoyerInfo(idLog);
            }
            
            @Override
            protected void done() {
                try {
                	
                    LoyerInfo info = get();
                    fen.setAncienLoyer(info.ancienLoyer);
                    fen.setLoyerMax(info.loyerMax);
                    
                // Gestion des exception    
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
                    e.printStackTrace();
                } finally {
                    fen.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }
    
    /**
     * Extrait la logique de chargement des informations de loyer pour un logement donné.
     * @param idLog Identifiant du logement
     * @return Un objet LoyerInfo contenant l'ancien loyer et le loyer maximum autorisé
     * @throws SQLException Si une erreur SQL survient
     * @throws IOException  Si une erreur d'entrée/sortie survient
     */
    private LoyerInfo loadLoyerInfo(String idLog) throws SQLException, IOException {
        DaoLoyer dao = new DaoLoyer();
        LoyerInfo info = new LoyerInfo();
        
        BigDecimal dernierLoyer = dao.getDernierLoyer(idLog);
        info.ancienLoyer = (dernierLoyer != null) ? dernierLoyer.toString() : "N/A";
        
        // Vérifie si le loyer est augmentable et obtient le loyer maximum selon le cas
        if (!dao.estLoyerAugmentable(idLog)) {
            info.loyerMax = info.ancienLoyer;
        } else {
            BigDecimal maxLoyerBD = dao.getMaxLoyer(idLog);
            info.loyerMax = (maxLoyerBD != null) ? maxLoyerBD.toString() : "N/A";
        }
        
        return info;
    }
    
    /**
     * Classe interne pour stocker temporairement les informations de loyer.
     */
    private static class LoyerInfo {
        String ancienLoyer;
        String loyerMax;
    }

    /**
     * Configure l'élément actuellement sélectionné dans la ComboBox.
     */
    public void setIdLog() {
        fen.setItemInCombo(fen.getIdLog());
    }

    /**
     * Gère l'action du bouton "Voir ICC" pour afficher la fenêtre SetICC.
     * Ajoute SetICC à la couche palette de la JFrame parente du JInternalFrame.
     */
    public void gestionBtnVoirICC(JButton btnICC) {
        btnICC.addActionListener(e -> {
            // Obtient la JFrame parent de l'InternalFrame
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(fen);
            if (parentFrame != null) {
                SetICC stIcc = new SetICC();
                JLayeredPane fenLayerPane = parentFrame.getLayeredPane();
                fenLayerPane.add(stIcc, JLayeredPane.PALETTE_LAYER);
                stIcc.setVisible(true);
            } else {
                fen.afficherMessageErreur("La fenêtre parente est introuvable !");
            }
        });
    }
    
    /**
     * Gère l'action du bouton "Quitter" pour fermer la fenêtre.
     */
    public void gestionBtnQuitter(JButton btnQuitter) {
        btnQuitter.addActionListener(e -> fen.dispose());
    }
    
    /**
     * Gère l'action du bouton "Revaloriser" pour enregistrer une nouvelle valeur de loyer.
     */
    public void gestionBtnRevaloriser(JButton btnReval) {
        btnReval.addActionListener(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateActuelle = LocalDate.now().format(formatter);

            Loyer loyer = new Loyer(fen.getSelectedIdLog(), dateActuelle, 
                                   new BigDecimal(fen.getValeurNouveauLoyer()));

            try {
                fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                new DaoLoyer().create(loyer);
                fen.dispose();
            } catch (SQLException | IOException e1) {
                fen.afficherMessageErreur(e1.getMessage());
                e1.printStackTrace();
            } finally {
                fen.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * Configure la réaction lors de la modification de l'élément sélectionné dans la ComboBox.
     * Recharge les informations du loyer pour le nouveau logement sélectionné.
     */
    public void gestionActionComboLog(JComboBox<String> combo) {
        combo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                chargerInfoLoyer();
            }
        });
    }
}
