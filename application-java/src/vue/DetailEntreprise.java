package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionDetailEntreprise;
import modele.Entreprise;
import modele.dao.DaoEntreprise;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class DetailEntreprise extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblTxtSiret;
	private JLabel lblTxtNom;
	private JLabel lblTxtSecteur;
	private JTextArea textAreaAdresse;
	
	private GestionDetailEntreprise gest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			DetailEntreprise dialog = new DetailEntreprise(new DaoEntreprise().findById("ENTR001"));
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetailEntreprise(Entreprise entreprise) {
		
		this.gest = new GestionDetailEntreprise(this, entreprise);
		setBounds(100, 100, 450, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{130, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 55, 25, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblSiret = new JLabel("SIRET :");
		GridBagConstraints gbc_lblSiret = new GridBagConstraints();
		gbc_lblSiret.insets = new Insets(0, 0, 5, 5);
		gbc_lblSiret.anchor = GridBagConstraints.EAST;
		gbc_lblSiret.gridx = 0;
		gbc_lblSiret.gridy = 0;
		contentPanel.add(lblSiret, gbc_lblSiret);
	
	
		lblTxtSiret = new JLabel("Chargement...");
		GridBagConstraints gbc_lblTxtSiret = new GridBagConstraints();
		gbc_lblTxtSiret.insets = new Insets(0, 0, 5, 0);
		gbc_lblTxtSiret.anchor = GridBagConstraints.WEST;
		gbc_lblTxtSiret.gridx = 1;
		gbc_lblTxtSiret.gridy = 0;
		contentPanel.add(lblTxtSiret, gbc_lblTxtSiret);
	
	
		JLabel lblNom = new JLabel("Nom :");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		contentPanel.add(lblNom, gbc_lblNom);
	
	
		lblTxtNom = new JLabel("Chargement...");
		GridBagConstraints gbc_lblTxtNom = new GridBagConstraints();
		gbc_lblTxtNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblTxtNom.anchor = GridBagConstraints.WEST;
		gbc_lblTxtNom.gridx = 1;
		gbc_lblTxtNom.gridy = 1;
		contentPanel.add(lblTxtNom, gbc_lblTxtNom);
	
	
		JLabel lblSecteur = new JLabel("Secteur d'activité :");
		GridBagConstraints gbc_lblSecteur = new GridBagConstraints();
		gbc_lblSecteur.anchor = GridBagConstraints.EAST;
		gbc_lblSecteur.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecteur.gridx = 0;
		gbc_lblSecteur.gridy = 2;
		contentPanel.add(lblSecteur, gbc_lblSecteur);
	
	
		lblTxtSecteur = new JLabel("Chargement...");
		GridBagConstraints gbc_lblTxtSecteur = new GridBagConstraints();
		gbc_lblTxtSecteur.insets = new Insets(0, 0, 5, 0);
		gbc_lblTxtSecteur.anchor = GridBagConstraints.WEST;
		gbc_lblTxtSecteur.gridx = 1;
		gbc_lblTxtSecteur.gridy = 2;
		contentPanel.add(lblTxtSecteur, gbc_lblTxtSecteur);
	
	
		JLabel lblAdresse = new JLabel("Adresse :");
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.anchor = GridBagConstraints.EAST;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 3;
		contentPanel.add(lblAdresse, gbc_lblAdresse);
	
	
		textAreaAdresse = new JTextArea();
		textAreaAdresse.setLineWrap(true);
		textAreaAdresse.setWrapStyleWord(true);
		textAreaAdresse.setEditable(false);
		GridBagConstraints gbc_textAreaAdresse = new GridBagConstraints();
		gbc_textAreaAdresse.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaAdresse.fill = GridBagConstraints.BOTH;
		gbc_textAreaAdresse.gridx = 1;
		gbc_textAreaAdresse.gridy = 3;
		contentPanel.add(textAreaAdresse, gbc_textAreaAdresse);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		JButton cancelButton = new JButton("Annuler");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		gest.gestionAnnuler(cancelButton);
		gest.chargeDonnee();
	}


	
	public void setSiret(String siret) {
		this.lblTxtSiret.setText(siret);
	}
	
	public void setNom(String nom) {
		this.lblTxtNom.setText(nom);
	}
	
	public void setSecteur(String secteur) {
		this.lblTxtSecteur.setText(secteur);
	}
	
	public void setTextAdresse(String adresse) {
		this.textAreaAdresse.setText(adresse);
	}

}
