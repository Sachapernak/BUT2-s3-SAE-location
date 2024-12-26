package utilitaires;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import vue.PageConnexion;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

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
		setBounds(100, 100, 500, 500);
        this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JInternalFrame jInternalFrame = new PageConnexion();
		gestionFemertureInternalFrame(jInternalFrame);
		jInternalFrame.setNormalBounds(new Rectangle(100, 100, 400, 400));
		jInternalFrame.setSize(new Dimension(400, 400));
		jInternalFrame.getContentPane().setSize(new Dimension(400, 400));
		jInternalFrame.getContentPane().setPreferredSize(new Dimension(400, 400));
		jInternalFrame.setPreferredSize(new Dimension(400, 400));
		jInternalFrame.setMinimumSize(new Dimension(300, 300));
        jInternalFrame.setLocation(100, 100);
        jInternalFrame.setBounds(100,100,500, 350);
        jInternalFrame.setSize(450, 350);
        jInternalFrame.setVisible(true);
        jInternalFrame.setClosable(false);
        getContentPane().add(jInternalFrame);
        this.repaint();
        
        

	}

	private void gestionFemertureInternalFrame(JInternalFrame jInternalFrame) {
		jInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				dispose();
			}
		});
	}

}
