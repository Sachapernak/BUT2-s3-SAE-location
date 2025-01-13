package vue;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(25, 25, 650, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_charges = new JPanel();
		panel_charges.setBounds(10, 29, 618, 173);
		contentPane.add(panel_charges);
		panel_charges.setLayout(null);
		
		
		JButton btnRegulariser = new JButton("Régulariser");
		btnRegulariser.setBounds(416, 443, 100, 21);
		contentPane.add(btnRegulariser);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(538, 443, 85, 21);
		contentPane.add(btnRetour);
		

	}
}
