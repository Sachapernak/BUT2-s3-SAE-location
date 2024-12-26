package utilitaires;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import vue.PageConnexion;

public class ConfigConnexion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigConnexion frame = new ConfigConnexion();
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
	public ConfigConnexion() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
        this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JInternalFrame jInternalFrame = new PageConnexion();
        jInternalFrame.setLocation(100, 100);
        jInternalFrame.setBounds(100,100,500, 350);
        jInternalFrame.setSize(450, 350);
        jInternalFrame.setVisible(true);
        jInternalFrame.setClosable(false);
        this.add(jInternalFrame);
        this.repaint();
        
        

	}

}
