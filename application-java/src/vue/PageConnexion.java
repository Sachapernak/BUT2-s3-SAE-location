package vue;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.SwingWorker;

import modele.ConnexionBD;

public class PageConnexion extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblSatutText;
    private JLabel lblLatCoVal;
    private JLabel lblLatReqVal;
    private JLabel lblms1;
    private JLabel lblms2;

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

        setBounds(100, 100, 400, 300);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panelTop = new JPanel();
        getContentPane().add(panelTop, BorderLayout.NORTH);
        
        JPanel panelBot = new JPanel();
        getContentPane().add(panelBot, BorderLayout.SOUTH);
        
        JPanel panelMid = new JPanel();
        getContentPane().add(panelMid, BorderLayout.CENTER);
        panelMid.setLayout(new GridLayout(4, 0, 0, 0));
        
        JPanel panelG1 = new JPanel();
        panelMid.add(panelG1);
        panelG1.setLayout(new GridLayout(3, 0, 0, 0));
        
        JPanel panelG11 = new JPanel();
        panelG1.add(panelG11);
        panelG11.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblStatutTitre = new JLabel("Statut :");
        panelG11.add(lblStatutTitre);
        
        lblSatutText = new JLabel("Tentative de connexion...");
        lblSatutText.setForeground(new Color(255, 128, 0));
        panelG11.add(lblSatutText);
        
        JPanel panelG12 = new JPanel();
        panelG1.add(panelG12);
        panelG12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblLatCoTitre = new JLabel("Latence connexion :");
        panelG12.add(lblLatCoTitre);
        
        lblLatCoVal = new JLabel("Calcul...");
        lblLatCoVal.setForeground(new Color(255, 128, 0));
        panelG12.add(lblLatCoVal);
        
        lblms1 = new JLabel("ms");
        panelG12.add(lblms1);
        
        
        JPanel panelG13 = new JPanel();
        panelG1.add(panelG13);
        panelG13.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblLatReqTitre = new JLabel("Latence requete :");
        panelG13.add(lblLatReqTitre);
        
        lblLatReqVal = new JLabel("Calcul...");
        lblLatReqVal.setForeground(new Color(255, 128, 0));
        panelG13.add(lblLatReqVal);
        
        lblms2 = new JLabel("ms");
        panelG13.add(lblms2);

        lblms1.setVisible(false);
        lblms2.setVisible(false);

        // Utiliser SwingWorker pour exécuter les tâches en arrière-plan
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