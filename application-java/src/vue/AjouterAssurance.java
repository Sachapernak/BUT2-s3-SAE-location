package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.GestionAjouterAssurance;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.Font;

public class AjouterAssurance extends JDialog{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNumContrat;
	private JTextField textFieldAnneeContrat;
	private JTextField textFieldTypeContrat;
	
	private GestionAjouterAssurance gestionFen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AjouterAssurance dialog = new AjouterAssurance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AjouterAssurance() {
		this.gestionFen = new GestionAjouterAssurance(this);
		setBounds(150, 130, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTitre = new JPanel();
			contentPanel.add(panelTitre, BorderLayout.NORTH);
			{
				JLabel lblTitre = new JLabel("Ajouter une assurance");
				lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
				panelTitre.add(lblTitre);
			}
		}
		{
			JPanel panelCentre = new JPanel();
			contentPanel.add(panelCentre, BorderLayout.CENTER);
			GridBagLayout gblPanelCentre = new GridBagLayout();
			gblPanelCentre.columnWidths = new int[]{0, 183, 183, 0, 0};
			gblPanelCentre.rowHeights = new int[]{30, 0, 10, 0, 10, 0, 0, 0};
			gblPanelCentre.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gblPanelCentre.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			panelCentre.setLayout(gblPanelCentre);
			{
				JLabel lblNumContrat = new JLabel("Numéro de contrat* :");
				lblNumContrat.setHorizontalAlignment(SwingConstants.RIGHT);
				GridBagConstraints gbcLblNumContrat = new GridBagConstraints();
				gbcLblNumContrat.insets = new Insets(0, 0, 5, 5);
				gbcLblNumContrat.anchor = GridBagConstraints.EAST;
				gbcLblNumContrat.gridx = 1;
				gbcLblNumContrat.gridy = 1;
				panelCentre.add(lblNumContrat, gbcLblNumContrat);
			}
			{
				textFieldNumContrat = new JTextField();
				GridBagConstraints gbcTextFieldNumContrat = new GridBagConstraints();
				gbcTextFieldNumContrat.insets = new Insets(0, 0, 5, 5);
				gbcTextFieldNumContrat.anchor = GridBagConstraints.WEST;
				gbcTextFieldNumContrat.gridx = 2;
				gbcTextFieldNumContrat.gridy = 1;
				panelCentre.add(textFieldNumContrat, gbcTextFieldNumContrat);
				textFieldNumContrat.setColumns(10);
			}
			{
				JLabel lblAnneeDuContrat = new JLabel("Annee du contrat* :");
				lblAnneeDuContrat.setHorizontalAlignment(SwingConstants.RIGHT);
				GridBagConstraints gbcLblAnneeDuContrat = new GridBagConstraints();
				gbcLblAnneeDuContrat.anchor = GridBagConstraints.EAST;
				gbcLblAnneeDuContrat.insets = new Insets(0, 0, 5, 5);
				gbcLblAnneeDuContrat.gridx = 1;
				gbcLblAnneeDuContrat.gridy = 3;
				panelCentre.add(lblAnneeDuContrat, gbcLblAnneeDuContrat);
			}
			{
				textFieldAnneeContrat = new JTextField();
				GridBagConstraints gbcTextFieldAnneeContrat = new GridBagConstraints();
				gbcTextFieldAnneeContrat.insets = new Insets(0, 0, 5, 5);
				gbcTextFieldAnneeContrat.anchor = GridBagConstraints.WEST;
				gbcTextFieldAnneeContrat.gridx = 2;
				gbcTextFieldAnneeContrat.gridy = 3;
				panelCentre.add(textFieldAnneeContrat, gbcTextFieldAnneeContrat);
				textFieldAnneeContrat.setColumns(10);
			}
			{
				JLabel lblTypeContrat = new JLabel("Type de contrat :");
				lblTypeContrat.setHorizontalAlignment(SwingConstants.RIGHT);
				GridBagConstraints gbcLblTypeContrat = new GridBagConstraints();
				gbcLblTypeContrat.anchor = GridBagConstraints.EAST;
				gbcLblTypeContrat.insets = new Insets(0, 0, 5, 5);
				gbcLblTypeContrat.gridx = 1;
				gbcLblTypeContrat.gridy = 5;
				panelCentre.add(lblTypeContrat, gbcLblTypeContrat);
			}
			{
				textFieldTypeContrat = new JTextField();
				GridBagConstraints gbcTextFieldTypeContrat = new GridBagConstraints();
				gbcTextFieldTypeContrat.insets = new Insets(0, 0, 5, 5);
				gbcTextFieldTypeContrat.anchor = GridBagConstraints.WEST;
				gbcTextFieldTypeContrat.gridx = 2;
				gbcTextFieldTypeContrat.gridy = 5;
				panelCentre.add(textFieldTypeContrat, gbcTextFieldTypeContrat);
				textFieldTypeContrat.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Valider");
				okButton.addActionListener(this.gestionFen);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addActionListener(this.gestionFen);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String getTextNumeroContrat() {
		return this.textFieldNumContrat.getText();
	}
	
	public String getTextAnnee() {
		return this.textFieldAnneeContrat.getText();
	}
	
	public String getTextTypeContrat() {
		return this.textFieldTypeContrat.getText();
	}
	
	// -------------------------------------------------------------------------
    // Méthodes d'aide pour le contrôleur
    // -------------------------------------------------------------------------

    /**
     * Retourne la liste des champs obligatoires pour une assurance.
     * @return liste de chaînes représentant les valeurs des champs obligatoires
     */
    public List<String> getChampsObligatoiresAssurance() {
        List<String> res = new ArrayList<>();
        res.add(getTextNumeroContrat());
        res.add(getTextAnnee());
        return res;
    }
    
    /**
     * fonction permettant d'afficher les messages d'erreurs
     */
	public void afficherMessageErreur(String message) {
	    JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}
