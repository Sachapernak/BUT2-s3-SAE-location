package vue;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.SwingWorker;

import modele.ConnexionBD;
import modele.FichierConfig;

import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PageConnexion extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblSatutText;
    private JLabel lblms1;
    private JLabel lblms2;
    private JLabel lblLatReqVal;
    private JLabel lblLatCoVal;
    private JTextField textFieldLien;
    private JTextField textFieldID;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PageConnexion frame = new PageConnexion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PageConnexion() {
    	getContentPane().setBounds(new Rectangle(0, 0, 400, 400));

        setBounds(100, 100, 400, 300);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelTop = new JPanel();
        getContentPane().add(panelTop, BorderLayout.NORTH);
        
        JPanel panelBot = new JPanel();
        getContentPane().add(panelBot, BorderLayout.SOUTH);
        panelBot.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnQuitter = new JButton("Annuler");

        panelBot.add(btnQuitter);
        
        JButton btnRefresh = new JButton("Recharger");

        panelBot.add(btnRefresh);
        
        JButton btnConfirmer = new JButton("Confirmer");

        panelBot.add(btnConfirmer);
        
        JPanel panelMid = new JPanel();
        getContentPane().add(panelMid, BorderLayout.CENTER);
        panelMid.setLayout(new GridLayout(3, 0, 0, 0));
        
        JPanel panelG1 = new JPanel();
        panelMid.add(panelG1);
        panelG1.setLayout(new GridLayout(3, 0, 0, 0));
        
        JPanel panelG11 = new JPanel();
        panelG1.add(panelG11);
        panelG11.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblStatutTitre = new JLabel("Statut :");
        lblStatutTitre.setHorizontalAlignment(SwingConstants.RIGHT);
        panelG11.add(lblStatutTitre);
        
        lblSatutText = new JLabel("Tentative de connexion...");
        lblSatutText.setForeground(new Color(255, 128, 0));
        panelG11.add(lblSatutText);
        
        JPanel panelG12 = new JPanel();
        panelG1.add(panelG12);
        panelG12.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblLatCoTitre = new JLabel("Latence connexion :");
        lblLatCoTitre.setHorizontalAlignment(SwingConstants.TRAILING);
        panelG12.add(lblLatCoTitre);
        
        JPanel panel = new JPanel();
        panelG12.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 6));
        
        lblLatCoVal = new JLabel("Calcul...");
        lblLatCoVal.setForeground(new Color(255, 128, 0));
        panel.add(lblLatCoVal);
        
        lblms1 = new JLabel("ms");
        lblms1.setForeground(Color.BLACK);
        panel.add(lblms1);
        
        
        JPanel panelG13 = new JPanel();
        panelG1.add(panelG13);
        panelG13.setLayout(new GridLayout(0, 2, 5, 0));
        
        JLabel lblLatReqTitre = new JLabel("Latence requete :");
        lblLatReqTitre.setHorizontalAlignment(SwingConstants.TRAILING);
        panelG13.add(lblLatReqTitre);
        
        JPanel panelG132 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelG132.getLayout();
        flowLayout.setVgap(6);
        flowLayout.setAlignment(FlowLayout.LEFT);
        panelG13.add(panelG132);
        
        lblLatReqVal = new JLabel("Calcul...");
        lblLatReqVal.setForeground(new Color(255, 128, 0));
        panelG132.add(lblLatReqVal);
        
        lblms2 = new JLabel("ms");
        lblms2.setForeground(Color.BLACK);
        panelG132.add(lblms2);
        
        JPanel panelG2 = new JPanel();
        panelMid.add(panelG2);
        panelG2.setLayout(new GridLayout(2, 0, 0, 0));
        
        JPanel panelG21 = new JPanel();
        panelG2.add(panelG21);
        panelG21.setLayout(new BorderLayout(0, 0));
        
        JLabel lblBD = new JLabel("Modification des identifiants :");
        lblBD.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblBD.setHorizontalAlignment(SwingConstants.CENTER);
        panelG21.add(lblBD);
        
        JPanel panelG22 = new JPanel();
        panelG2.add(panelG22);
        panelG22.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblLien = new JLabel("Lien :              ");
        panelG22.add(lblLien);
        
        textFieldLien = new JTextField();
        panelG22.add(textFieldLien);
        textFieldLien.setColumns(30);
        
        JPanel panelG3 = new JPanel();
        panelMid.add(panelG3);
        panelG3.setLayout(new GridLayout(2, 0, 0, 0));
        
        JPanel panelG31 = new JPanel();
        panelG3.add(panelG31);
        panelG31.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblID = new JLabel("Identifiant :     ");
        panelG31.add(lblID);
        
        textFieldID = new JTextField();
        panelG31.add(textFieldID);
        textFieldID.setColumns(30);
        
        JPanel panelG32 = new JPanel();
        panelG3.add(panelG32);
        panelG32.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblMDP = new JLabel("Mot de passe :");
        panelG32.add(lblMDP);
        
        passwordField = new JPasswordField();
        passwordField.setColumns(30);
        panelG32.add(passwordField);
        
        lblms1.setVisible(false);
        lblms2.setVisible(false);
        
        gestionSourisQuitter(btnQuitter);
        
        chargerInfoConnexion();
        
        gestionSourisRecharger(btnRefresh);
        gestionSourisConfirmer(btnConfirmer);

      

        // Utiliser SwingWorker pour exécuter les tâches en arrière-plan
        getInformationBD();
    }

	private void gestionSourisQuitter(JButton btnQuitter) {
		btnQuitter.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
	}

	private void gestionSourisConfirmer(JButton btnConfirmer) {
		btnConfirmer.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String lien = textFieldLien.getText();
        		String user = textFieldID.getText();
        		

        		
        		try {
        			FichierConfig fc = FichierConfig.getInstance();
        			if (lien.length() > 0) {
        				fc.enregistrer("DB_LINK", lien);
        			}
        			
        			if (user.length() > 0) {
        				fc.enregistrer("DB_USER", user);
        			}
        			
        			if (passwordField.getPassword().length > 0) {
        				fc.enregistrerMdp(new String(passwordField.getPassword()));
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

	private void getInformationBD() {
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

	private void gestionSourisRecharger(JButton btnRefresh) {
		btnRefresh.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
                recharger();
        	}


        });
	}
	
	private void recharger() {
		lblSatutText.setText("Chargement...");
        lblSatutText.setForeground(new Color(255, 128, 0));
        
        lblLatCoVal.setText("Chargement...");
        lblLatCoVal.setForeground(new Color(255, 128, 0));
        
        lblLatReqVal.setText("Chargement...");
        lblLatReqVal.setForeground(new Color(255, 128, 0));
        
        getInformationBD();
        chargerInfoConnexion();
	}

	private void chargerInfoConnexion() {
		FichierConfig fc = FichierConfig.getInstance();
        
        try {
			String link = fc.lire("DB_LINK");
			String user = fc.lire("DB_USER");
			
			textFieldLien.setText(link);
			textFieldID.setText(user);
			
			
		} catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture de config.properties",
                    "Erreur de lecture", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
	}
    
    private void setConnexion() {
        try {
            if (ConnexionBD.getInstance().isConnexionOk()) {
                this.lblSatutText.setText("Connexion OK");
                this.lblSatutText.setForeground(new Color(0, 128, 0));
            } else {
                this.lblSatutText.setText("Pas de connexion");
                this.lblSatutText.setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
            this.lblSatutText.setText("Pas de connexion");
            this.lblSatutText.setForeground(Color.RED);
        }
    }
    
    private void setValLatCo() {
        
        final long GOOD_PING = 500;
        final long BAD_PING = 1000;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceConnexionBD();
            
            if (val.isPresent()) {
                this.lblLatCoVal.setText(val.get().toString());
                lblms1.setVisible(true);
                
                if (val.get() < GOOD_PING) {
                    this.lblLatCoVal.setForeground(new Color(0, 128, 0));
                } else if (val.get() < BAD_PING){
                    this.lblLatCoVal.setForeground(new Color(255, 128, 0));
                } else {
                    this.lblLatCoVal.setForeground(Color.RED);
                }
                
            } else {
            	this.lblSatutText.setText("Pas de connexion");
                this.lblSatutText.setForeground(Color.RED);
            	
                this.lblLatCoVal.setText("N/A");
                this.lblLatCoVal.setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
            this.lblSatutText.setText("Pas de connexion");
            this.lblSatutText.setForeground(Color.RED);
            
            this.lblLatCoVal.setText("N/A");
            this.lblLatCoVal.setForeground(Color.RED);
            
        }
    }
    
    private void setValLatReq() {
        
        final long GOOD_PING = 50;
        final long BAD_PING = 100;
        
        try {
            Optional<Long> val = ConnexionBD.getInstance().latenceRequeteBD();
            
            if (val.isPresent()) {
                this.lblLatReqVal.setText(val.get().toString());
                lblms2.setVisible(true);
                
                if (val.get() < GOOD_PING) {
                    this.lblLatReqVal.setForeground(new Color(0, 128, 0));
                } else if (val.get() < BAD_PING){
                    this.lblLatReqVal.setForeground(new Color(255, 128, 0));
                } else {
                    this.lblLatReqVal.setForeground(Color.RED);
                }
            } else {
            	this.lblSatutText.setText("Pas de connexion");
                this.lblSatutText.setForeground(Color.RED);
                
                this.lblLatReqVal.setText("N/A");
                this.lblLatReqVal.setForeground(Color.RED);
            }
        } catch (SQLException | IOException e) {
            this.lblSatutText.setText("Pas de connexion");
            this.lblSatutText.setForeground(Color.RED);
            
            this.lblLatReqVal.setText("N/A");
            this.lblLatReqVal.setForeground(Color.RED);
        }
    }
}