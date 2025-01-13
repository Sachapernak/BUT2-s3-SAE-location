package vue;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.BorderLayout;

/**
 * Fenêtre principale pour tester setICC.
 */
public class UtilitaireICC {

    private JFrame frame;

    /**
     * Lancement de l'application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	UtilitaireICC window = new UtilitaireICC();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructeur. Initialise l'application.
     */
    public UtilitaireICC() {
        initialize();
    }

    /**
     * Initialise les composants de la fenêtre.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Test setICC");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Desktop Pane pour gérer les JInternalFrame
        JDesktopPane desktopPane = new JDesktopPane();
        frame.getContentPane().add(desktopPane, BorderLayout.CENTER);

        // Bouton pour ajouter un JInternalFrame
        JButton btnAddInternalFrame = new JButton("Ajouter un JInternalFrame");
        btnAddInternalFrame.addActionListener(e -> {
            // Création et ajout de l'InternalFrame
            SetICC internalFrame = new SetICC();
            internalFrame.setClosable(true);
            internalFrame.setResizable(true);
            internalFrame.setIconifiable(true);
            internalFrame.setMaximizable(true);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);
            try {
                internalFrame.setSelected(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.getContentPane().add(btnAddInternalFrame, BorderLayout.NORTH);
    }
}
