package controleur;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import modele.ConnexionBD;
import modele.FichierConfig;
import vue.PageConnexion;

public class GestionPageConnexion {
    
    private PageConnexion pageConnexion;
    
    public GestionPageConnexion(PageConnexion pageConnexion) {
        this.pageConnexion = pageConnexion;
    }

    // -------------------------------------------------------------------------
    // Gestions d'événements (souris, clics...)
    // -------------------------------------------------------------------------

    public void gestionSourisQuitter(JButton btnQuitter) {
        btnQuitter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageConnexion.dispose();
            }
        });
    }

    public void gestionSourisRecharger(JButton btnRefresh) {
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recharger();
            }
        });
    }

    public void gestionSourisConfirmer(JButton btnConfirmer) {
        btnConfirmer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // On récupère les valeurs directement via la Vue
                String lien = pageConnexion.getLien();
                String user = pageConnexion.getIdentifiant();
                char[] pwd = pageConnexion.getMotDePasse();
                
                try {
                    FichierConfig fc = FichierConfig.getInstance();
                    
                    if (!lien.isEmpty()) {
                        fc.enregistrer("DB_LINK", lien);
                    }
                    
                    if (!user.isEmpty()) {
                        fc.enregistrer("DB_USER", user);
                    }
                    
                    if (pwd.length > 0) {
                        try {
                            fc.enregistrerMdp(new String(pwd));
                        } finally {
                            Arrays.fill(pwd, '\0');  // Nettoyer le tableau
                        }
                    }
                    
                    ConnexionBD bd = ConnexionBD.getInstance();
                    bd.updateBDLink();
                    
                    recharger();
        
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
               
            }
        });
    }

    // -------------------------------------------------------------------------
    // Chargement / Rechargement des infos
    // -------------------------------------------------------------------------

    public void chargerInfoConnexion() {
        FichierConfig fc = FichierConfig.getInstance();
        try {
            String link = fc.lire("DB_LINK");
            String user = fc.lire("DB_USER");
            
            // On met à jour la Vue pour afficher le lien et l'ID
            pageConnexion.setLien(link);
            pageConnexion.setIdentifiant(user);

        } catch (IOException e) {
            pageConnexion.afficherMessageErreur(e.getMessage());
        }
    }

    private void recharger() {
        // Mise à jour du statut et des latences
        pageConnexion.setStatut("Chargement...", new Color(255, 128, 0));
        pageConnexion.setLatenceConnexion("Chargement...", new Color(255, 128, 0));
        pageConnexion.setLatenceRequete("Chargement...", new Color(255, 128, 0));

        getInformationBD();
        chargerInfoConnexion();
    }
    
    private boolean setConnexion() {
        try {
            if (ConnexionBD.getInstance().isConnexionOk()) {
                pageConnexion.setStatut("Connexion OK", new Color(0, 128, 0));
                return true;
                
            } else {
                pageConnexion.setStatut("Pas de connexion", Color.RED);
                pageConnexion.setLatenceConnexion("N/A", Color.RED);
                pageConnexion.setLatenceRequete("N/A", Color.RED);
                return false;
            }
        } catch (SQLException | IOException e) {
            pageConnexion.setStatut("Pas de connexion", Color.RED);
            pageConnexion.setLatenceConnexion("N/A", Color.RED);
            pageConnexion.setLatenceRequete("N/A", Color.RED);
            return false;
        }
    }
    
    private void setValLatCo() {
        final long GOOD_PING = 500;
        final long BAD_PING = 1000;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceConnexionBD();
            if (val.isPresent()) {
                long ping = val.get();
                // Couleur en fonction du ping
                Color c;
                if (ping < GOOD_PING) {
                    c = new Color(0, 128, 0);
                } else if (ping < BAD_PING) {
                    c = new Color(255, 128, 0);
                } else {
                    c = Color.RED;
                }
                pageConnexion.setLatenceConnexion(String.valueOf(ping), c);
                
            } else {
                pageConnexion.setStatut("Pas de connexion", Color.RED);
                pageConnexion.setLatenceConnexion("N/A", Color.RED);

            }
        } catch (SQLException | IOException e) {
            pageConnexion.setStatut("Pas de connexion", Color.RED);
            pageConnexion.setLatenceConnexion("N/A", Color.RED);

        }
    }
    
    private void setValLatReq() {
        final long GOOD_PING = 50;
        final long BAD_PING = 100;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceRequeteBD();
            if (val.isPresent()) {
                long ping = val.get();
                Color c;
                if (ping < GOOD_PING) {
                    c = new Color(0, 128, 0);
                } else if (ping < BAD_PING) {
                    c = new Color(255, 128, 0);
                } else {
                    c = Color.RED;
                }
                pageConnexion.setLatenceRequete(String.valueOf(ping), c);
                
            } else {
                pageConnexion.setStatut("Pas de connexion", Color.RED);
                pageConnexion.setLatenceRequete("N/A", Color.RED);
            }
        } catch (SQLException | IOException e) {
            pageConnexion.setStatut("Pas de connexion", Color.RED);
            pageConnexion.setLatenceRequete("N/A", Color.RED);
        }
    }

    // -------------------------------------------------------------------------
    // SwingWorker pour récupérer infos BD sans bloquer la vue
    // -------------------------------------------------------------------------

    public void getInformationBD() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (setConnexion()) {
	                setValLatCo();
	                setValLatReq();
                }
                return null;
            }
        }.execute();
    }

    
    

}
