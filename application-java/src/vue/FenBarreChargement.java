package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import controleur.GestionChargerLoyer;

/**
 * Fenêtre de chargement qui affiche la progression de l'insertion
 * des loyers en base de données via un SwingWorker.
 */
public class FenBarreChargement extends JDialog {

    private static final long serialVersionUID = 1L;

    private JProgressBar progressBar;
    private JLabel lblProgress;
    private JButton btnOk;

    // --- MVC : on stocke ici le controleur ---
    private GestionChargerLoyer gestionnaireCharger;

    // Données à insérer
    private List<List<String>> data;
    private String lienFichier; 
    private ChargerLoyers fenParent; // la fenêtre appelante (vue principale)

    public FenBarreChargement(ChargerLoyers fenParent,
                              GestionChargerLoyer gestionnaireCharger,
                              List<List<String>> data,
                              String lienFichier) {
        
        // fenParent = vue parent, 
        // true = modal (bloque la parent tant que c'est pas fini)
        super(fenParent, "Chargement en cours", true);
        
        this.fenParent = fenParent;
        this.gestionnaireCharger = gestionnaireCharger; 
        this.data = data;
        this.lienFichier = lienFichier;

        initUI();

        // Lance le SwingWorker juste après la construction
        lancerInsertionEnBase();
    }

    private void initUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(fenParent);
        setLayout(new BorderLayout(10,10));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        lblProgress = new JLabel("Préparation...", SwingConstants.CENTER);
        lblProgress.setFont(lblProgress.getFont().deriveFont(Font.BOLD, 14f));

        btnOk = new JButton("OK");
        btnOk.setEnabled(false);
        btnOk.addActionListener(e -> {
            // Quand on clique sur OK, on ferme la fenêtre
            dispose();
        });

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCenter.add(lblProgress);
        panelCenter.add(progressBar);

        add(panelCenter, BorderLayout.CENTER);

        JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSouth.add(btnOk);
        add(panelSouth, BorderLayout.SOUTH);
    }

    private void lancerInsertionEnBase() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {

            private String errorMessage = null;

            @Override
            protected Void doInBackground() {
                try {
                    // Appel à la méthode du contrôleur
                    gestionnaireCharger.insertDonneesEnBD(
                        data,
                        lienFichier,
                        // consumerPublish => on fait publish(i+1)
                        val -> publish(val), 
                        // consumerSetProgress => on fait setProgress(%)
                        percent -> setProgress(percent)
                    );
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                    errorMessage = ex.getMessage();
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                // On récupère la dernière valeur publiée
                int current = chunks.get(chunks.size() - 1);
                int total = data.size();
                lblProgress.setText("Insertion des loyers : " + current + " / " + total);
                int progressPercent = (int) (((float) current / (float) total) * 100);
                progressBar.setValue(Math.min(progressPercent, 100));

            }

            @Override
            protected void done() {
                if (errorMessage == null) {
                    // Pas d’erreur => terminé
                    lblProgress.setText("Terminé ! (" + data.size() + " loyers insérés)");
                    progressBar.setValue(100);
                } else {
                    lblProgress.setText("Erreur : \n" + errorMessage);
                    progressBar.setValue(-1);
                    afficherMessageErreur(errorMessage);
                }
                
                btnOk.setEnabled(true);
            }
        };

        // Lancement du SwingWorker
        worker.execute();
    }
    
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : \n" + message, 
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
