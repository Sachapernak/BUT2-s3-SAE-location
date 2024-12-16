package vue;


import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.GestionReglesMetier;

import java.awt.Font;

public class ReglesMetier extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private GestionReglesMetier gestionClic;

	/**
	 * Create the frame.
	 */
	public ReglesMetier() {
		
		this.gestionClic = new GestionReglesMetier(this);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JPanel panel_boutons = new JPanel();
		panel_boutons.setBounds(213, 230, 215, 31);
		getContentPane().add(panel_boutons);
		
		JButton btnTelecharger = new JButton("Télécharger");
		panel_boutons.add(btnTelecharger);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this.gestionClic);
		panel_boutons.add(btnRetour);
		
		JLabel lblTitre = new JLabel("Les règles métier");
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(0, 10, 438, 31);
		getContentPane().add(lblTitre);

	}
}
