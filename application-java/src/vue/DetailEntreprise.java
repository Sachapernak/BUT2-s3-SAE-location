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
		GridBagLayout gblContentPanel = new GridBagLayout();
		gblContentPanel.columnWidths = new int[]{130, 0, 0};
		gblContentPanel.rowHeights = new int[]{0, 0, 0, 55, 25, 0};
		gblContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gblContentPanel);
		
		JLabel lblSiret = new JLabel("SIRET :");
		GridBagConstraints gbcLblSiret = new GridBagConstraints();
		gbcLblSiret.insets = new Insets(0, 0, 5, 5);
		gbcLblSiret.anchor = GridBagConstraints.EAST;
		gbcLblSiret.gridx = 0;
		gbcLblSiret.gridy = 0;
		contentPanel.add(lblSiret, gbcLblSiret);
	
	
		lblTxtSiret = new JLabel("Chargement...");
		GridBagConstraints gbcLblTxtSiret = new GridBagConstraints();
		gbcLblTxtSiret.insets = new Insets(0, 0, 5, 0);
		gbcLblTxtSiret.anchor = GridBagConstraints.WEST;
		gbcLblTxtSiret.gridx = 1;
		gbcLblTxtSiret.gridy = 0;
		contentPanel.add(lblTxtSiret, gbcLblTxtSiret);
	
	
		JLabel lblNom = new JLabel("Nom :");
		GridBagConstraints gbcLblNom = new GridBagConstraints();
		gbcLblNom.anchor = GridBagConstraints.EAST;
		gbcLblNom.insets = new Insets(0, 0, 5, 5);
		gbcLblNom.gridx = 0;
		gbcLblNom.gridy = 1;
		contentPanel.add(lblNom, gbcLblNom);
	
	
		lblTxtNom = new JLabel("Chargement...");
		GridBagConstraints gbcLblTxtNom = new GridBagConstraints();
		gbcLblTxtNom.insets = new Insets(0, 0, 5, 0);
		gbcLblTxtNom.anchor = GridBagConstraints.WEST;
		gbcLblTxtNom.gridx = 1;
		gbcLblTxtNom.gridy = 1;
		contentPanel.add(lblTxtNom, gbcLblTxtNom);
	
	
		JLabel lblSecteur = new JLabel("Secteur d'activité :");
		GridBagConstraints gbcLblSecteur = new GridBagConstraints();
		gbcLblSecteur.anchor = GridBagConstraints.EAST;
		gbcLblSecteur.insets = new Insets(0, 0, 5, 5);
		gbcLblSecteur.gridx = 0;
		gbcLblSecteur.gridy = 2;
		contentPanel.add(lblSecteur, gbcLblSecteur);
	
	
		lblTxtSecteur = new JLabel("Chargement...");
		GridBagConstraints gbcLblTxtSecteur = new GridBagConstraints();
		gbcLblTxtSecteur.insets = new Insets(0, 0, 5, 0);
		gbcLblTxtSecteur.anchor = GridBagConstraints.WEST;
		gbcLblTxtSecteur.gridx = 1;
		gbcLblTxtSecteur.gridy = 2;
		contentPanel.add(lblTxtSecteur, gbcLblTxtSecteur);
	
	
		JLabel lblAdresse = new JLabel("Adresse :");
		GridBagConstraints gbcLblAdresse = new GridBagConstraints();
		gbcLblAdresse.anchor = GridBagConstraints.EAST;
		gbcLblAdresse.insets = new Insets(0, 0, 5, 5);
		gbcLblAdresse.gridx = 0;
		gbcLblAdresse.gridy = 3;
		contentPanel.add(lblAdresse, gbcLblAdresse);
	
	
		textAreaAdresse = new JTextArea();
		textAreaAdresse.setLineWrap(true);
		textAreaAdresse.setWrapStyleWord(true);
		textAreaAdresse.setEditable(false);
		GridBagConstraints gbcTextAreaAdresse = new GridBagConstraints();
		gbcTextAreaAdresse.insets = new Insets(0, 0, 5, 0);
		gbcTextAreaAdresse.fill = GridBagConstraints.BOTH;
		gbcTextAreaAdresse.gridx = 1;
		gbcTextAreaAdresse.gridy = 3;
		contentPanel.add(textAreaAdresse, gbcTextAreaAdresse);
		
		
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
