package controleur;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SwingWorker;

import modele.ConnexionBD;
import modele.FichierConfig;
import vue.PageConnexion;

public class GestionPageConnexion {
	
	
	private PageConnexion pageConnexion;
	

	public GestionPageConnexion(PageConnexion pageConnexion) {
		
		this.pageConnexion = pageConnexion;
		
		
	}

	public void gestionSourisQuitter(JButton btnQuitter) {
			btnQuitter.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		pageConnexion.dispose();
	        	}
	        });
		
	
	}

	public void chargerInfoConnexion() {
		FichierConfig fc = FichierConfig.getInstance();
        
        try {
			String link = fc.lire("DB_LINK");
			String user = fc.lire("DB_USER");
			
			pageConnexion.getTextFieldLien().setText(link);
			pageConnexion.getTextFieldID().setText(user);
			
			
		} catch (IOException e) {			
			pageConnexion.afficherMessageErreur(e.getMessage());
			
		}
	}

	public void gestionSourisRecharger(JButton btnRefresh) {
		btnRefresh.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
                recharger();
        	}


        });
		
	}
	
	private void recharger() {
		pageConnexion.getLblSatutText().setText("Chargement...");
		pageConnexion.getLblSatutText().setForeground(new Color(255, 128, 0));
        
		pageConnexion.getLblLatCoVal().setText("Chargement...");
		pageConnexion.getLblLatCoVal().setForeground(new Color(255, 128, 0));
        
		pageConnexion.getLblLatReqVal().setText("Chargement...");
		pageConnexion.getLblLatReqVal().setForeground(new Color(255, 128, 0));
        
        getInformationBD();
        chargerInfoConnexion();
	}
	
	public void getInformationBD() {
		new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                setConnexion();
                setValLatCo();
                setValLatReq();
                return null;
            }
        }.execute();
	}
	
    private void setValLatCo() {
        
        final long GOOD_PING = 500;
        final long BAD_PING = 1000;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceConnexionBD();
            
            if (val.isPresent()) {
            	pageConnexion.getLblLatCoVal().setText(val.get().toString());
            	pageConnexion.getLblms1().setVisible(true);
                
                if (val.get() < GOOD_PING) {
                	pageConnexion.getLblLatCoVal().setForeground(new Color(0, 128, 0));
                } else if (val.get() < BAD_PING){
                	pageConnexion.getLblLatCoVal().setForeground(new Color(255, 128, 0));
                } else {
                	pageConnexion.getLblLatCoVal().setForeground(Color.RED);
                }
                
            } else {
            	pageConnexion.getLblSatutText().setText("Pas de connexion");
            	pageConnexion.getLblSatutText().setForeground(Color.RED);
            	
            	pageConnexion.getLblLatCoVal().setText("N/A");
            	pageConnexion.getLblLatCoVal().setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
        	pageConnexion.getLblSatutText().setText("Pas de connexion");
        	pageConnexion.getLblSatutText().setForeground(Color.RED);
            
        	pageConnexion.getLblLatCoVal().setText("N/A");
        	pageConnexion.getLblLatCoVal().setForeground(Color.RED);
            
        }
    }
    
    private void setValLatReq() {
        
        final long GOOD_PING = 50;
        final long BAD_PING = 100;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceRequeteBD();
            
            if (val.isPresent()) {
            	pageConnexion.getLblLatReqVal().setText(val.get().toString());
            	pageConnexion.getLblms2().setVisible(true);
                
                if (val.get() < GOOD_PING) {
                	pageConnexion.getLblLatReqVal().setForeground(new Color(0, 128, 0));
                } else if (val.get() < BAD_PING){
                	pageConnexion.getLblLatReqVal().setForeground(new Color(255, 128, 0));
                } else {
                	pageConnexion.getLblLatReqVal().setForeground(Color.RED);
                }
            } else {
            	pageConnexion.getLblSatutText().setText("Pas de connexion");
            	pageConnexion.getLblSatutText().setForeground(Color.RED);
                
            	pageConnexion.getLblLatReqVal().setText("N/A");
            	pageConnexion.getLblLatReqVal().setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
        	pageConnexion.getLblSatutText().setText("Pas de connexion");
        	pageConnexion.getLblSatutText().setForeground(Color.RED);
            
        	pageConnexion.getLblLatReqVal().setText("N/A");
        	pageConnexion.getLblLatReqVal().setForeground(Color.RED);
        }
    }

	public void gestionSourisConfirmer(JButton btnConfirmer, JPasswordField passwordField) {
		btnConfirmer.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String lien = pageConnexion.getTextFieldLien().getText();
        		String user = pageConnexion.getTextFieldID().getText();
        		

        		
        		try {
        			FichierConfig fc = FichierConfig.getInstance();
        			if (lien.length() > 0) {
        				fc.enregistrer("DB_LINK", lien);
        			}
        			
        			if (user.length() > 0) {
        				fc.enregistrer("DB_USER", user);
        			}
        			
        			if (passwordField.getPassword().length > 0) {
        				try {
        				fc.enregistrerMdp(new String(passwordField.getPassword()));
        				} finally {
        					Arrays.fill(passwordField.getPassword() , '\0');
        				}
        				
        			}
        			
            		ConnexionBD bd = ConnexionBD.getInstance();
            		bd.updateBDLink();
            		
            		recharger();
            		
        			
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		}
        	}
        });
		
	}
	
    private void setConnexion() {
        try {
            if (ConnexionBD.getInstance().isConnexionOk()) {
            	pageConnexion.getLblSatutText().setText("Connexion OK");
            	pageConnexion.getLblSatutText().setForeground(new Color(0, 128, 0));
            } else {
            	pageConnexion.getLblSatutText().setText("Pas de connexion");
            	pageConnexion.getLblSatutText().setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
        	pageConnexion.getLblSatutText().setText("Pas de connexion");
        	pageConnexion.getLblSatutText().setForeground(Color.RED);
        }
    }
}
