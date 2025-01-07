package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class DetailLocataire extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lbltxtNom;
	private JLabel lbltxtPrenom;
	private JLabel lbltxtDateNaissance;
	private JLabel lbltxtLieu;
	private JLabel lbltxtTelephone;
	private JLabel lbltxtEmail;
	private JTextArea txtrChargementDeLadresse;
	private JLabel lbltxtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetailLocataire dialog = new DetailLocataire();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetailLocataire() {
		setPreferredSize(new Dimension(500, 0));
		setBounds(100, 100, 530, 342);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {130, 0, 180, 0, 190, 0, 2};
		gbl_contentPanel.rowHeights = new int[]{35, 230, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 5;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblTitre = new JLabel("Locataire");
				lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblTitre);
			}
			{
				lbltxtID = new JLabel("[Identifiant]");
				lbltxtID.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lbltxtID);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.NORTH;
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				Component verticalStrut = Box.createVerticalStrut(10);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 0;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				JLabel lblNom = new JLabel("Nom :");
				lblNom.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblNom = new GridBagConstraints();
				gbc_lblNom.anchor = GridBagConstraints.EAST;
				gbc_lblNom.insets = new Insets(0, 0, 5, 0);
				gbc_lblNom.gridx = 0;
				gbc_lblNom.gridy = 1;
				panel.add(lblNom, gbc_lblNom);
			}
			{
				JLabel lblPrnom = new JLabel("Prénom :");
				lblPrnom.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblPrnom = new GridBagConstraints();
				gbc_lblPrnom.insets = new Insets(0, 0, 5, 0);
				gbc_lblPrnom.anchor = GridBagConstraints.EAST;
				gbc_lblPrnom.gridx = 0;
				gbc_lblPrnom.gridy = 2;
				panel.add(lblPrnom, gbc_lblPrnom);
			}
			{
				JLabel lblDateNaissance = new JLabel("Date de naissance :");
				lblDateNaissance.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblDateNaissance = new GridBagConstraints();
				gbc_lblDateNaissance.insets = new Insets(0, 0, 5, 0);
				gbc_lblDateNaissance.anchor = GridBagConstraints.EAST;
				gbc_lblDateNaissance.gridx = 0;
				gbc_lblDateNaissance.gridy = 3;
				panel.add(lblDateNaissance, gbc_lblDateNaissance);
			}
			{
				JLabel lblLieu = new JLabel("Lieu :");
				lblLieu.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblLieu = new GridBagConstraints();
				gbc_lblLieu.anchor = GridBagConstraints.EAST;
				gbc_lblLieu.insets = new Insets(0, 0, 5, 0);
				gbc_lblLieu.gridx = 0;
				gbc_lblLieu.gridy = 4;
				panel.add(lblLieu, gbc_lblLieu);
			}
			{
				Component verticalStrut = Box.createVerticalStrut(10);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 5;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				JLabel lblTel = new JLabel("Téléphone :");
				lblTel.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblTel = new GridBagConstraints();
				gbc_lblTel.insets = new Insets(0, 0, 5, 0);
				gbc_lblTel.anchor = GridBagConstraints.EAST;
				gbc_lblTel.gridx = 0;
				gbc_lblTel.gridy = 6;
				panel.add(lblTel, gbc_lblTel);
			}
			{
				JLabel lblEmail = new JLabel("Email :");
				lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblEmail = new GridBagConstraints();
				gbc_lblEmail.insets = new Insets(0, 0, 5, 0);
				gbc_lblEmail.anchor = GridBagConstraints.EAST;
				gbc_lblEmail.gridx = 0;
				gbc_lblEmail.gridy = 7;
				panel.add(lblEmail, gbc_lblEmail);
			}
			{
				Component verticalStrut = Box.createVerticalStrut(5);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 8;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				JLabel lblAdresse = new JLabel("Adresse :");
				lblAdresse.setHorizontalAlignment(SwingConstants.TRAILING);
				GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
				gbc_lblAdresse.anchor = GridBagConstraints.EAST;
				gbc_lblAdresse.gridx = 0;
				gbc_lblAdresse.gridy = 9;
				panel.add(lblAdresse, gbc_lblAdresse);
			}
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(3);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
			gbc_horizontalStrut.gridx = 1;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.NORTHWEST;
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{201, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0};
			gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				Component verticalStrut = Box.createVerticalStrut(10);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 0;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				lbltxtNom = new JLabel("Chargement nom...");
				GridBagConstraints gbc_lbltxtNom = new GridBagConstraints();
				gbc_lbltxtNom.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtNom.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtNom.gridx = 0;
				gbc_lbltxtNom.gridy = 1;
				panel.add(lbltxtNom, gbc_lbltxtNom);
			}
			{
				lbltxtPrenom = new JLabel("Chargement prénom...");
				GridBagConstraints gbc_lbltxtPrenom = new GridBagConstraints();
				gbc_lbltxtPrenom.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtPrenom.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtPrenom.gridx = 0;
				gbc_lbltxtPrenom.gridy = 2;
				panel.add(lbltxtPrenom, gbc_lbltxtPrenom);
			}
			{
				lbltxtDateNaissance = new JLabel("Chargement date...");
				GridBagConstraints gbc_lbltxtDateNaissance = new GridBagConstraints();
				gbc_lbltxtDateNaissance.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtDateNaissance.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtDateNaissance.gridx = 0;
				gbc_lbltxtDateNaissance.gridy = 3;
				panel.add(lbltxtDateNaissance, gbc_lbltxtDateNaissance);
			}
			{
				lbltxtLieu = new JLabel("Chargement lieu...");
				GridBagConstraints gbc_lbltxtLieu = new GridBagConstraints();
				gbc_lbltxtLieu.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtLieu.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtLieu.gridx = 0;
				gbc_lbltxtLieu.gridy = 5;
				panel.add(lbltxtLieu, gbc_lbltxtLieu);
			}
			{
				Component verticalStrut = Box.createVerticalStrut(10);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 6;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				lbltxtTelephone = new JLabel("Chargement téléphone...");
				GridBagConstraints gbc_lbltxtTelephone = new GridBagConstraints();
				gbc_lbltxtTelephone.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtTelephone.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtTelephone.gridx = 0;
				gbc_lbltxtTelephone.gridy = 7;
				panel.add(lbltxtTelephone, gbc_lbltxtTelephone);
			}
			{
				lbltxtEmail = new JLabel("Chargement email...");
				GridBagConstraints gbc_lbltxtEmail = new GridBagConstraints();
				gbc_lbltxtEmail.fill = GridBagConstraints.HORIZONTAL;
				gbc_lbltxtEmail.insets = new Insets(0, 0, 5, 0);
				gbc_lbltxtEmail.gridx = 0;
				gbc_lbltxtEmail.gridy = 8;
				panel.add(lbltxtEmail, gbc_lbltxtEmail);
			}
			{
				Component verticalStrut = Box.createVerticalStrut(5);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 9;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				txtrChargementDeLadresse = new JTextArea();
				txtrChargementDeLadresse.setEditable(false);
				txtrChargementDeLadresse.setText("Chargement de l'adresse...");
				txtrChargementDeLadresse.setFont(new Font("Tahoma", Font.PLAIN, 11));
				txtrChargementDeLadresse.setLineWrap(true);
				txtrChargementDeLadresse.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				txtrChargementDeLadresse.setRows(3);
				txtrChargementDeLadresse.setMaximumSize(new Dimension(200, 2147483647));
				GridBagConstraints gbc_txtrChargementDeLadresse = new GridBagConstraints();
				gbc_txtrChargementDeLadresse.fill = GridBagConstraints.BOTH;
				gbc_txtrChargementDeLadresse.gridx = 0;
				gbc_txtrChargementDeLadresse.gridy = 10;
				panel.add(txtrChargementDeLadresse, gbc_txtrChargementDeLadresse);
			}
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(10);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
			gbc_horizontalStrut.gridx = 3;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 4;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{170, 0};
			gbl_panel.rowHeights = new int[]{0, 190, 0};
			gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblBaux = new JLabel("Baux associés :");
				GridBagConstraints gbc_lblBaux = new GridBagConstraints();
				gbc_lblBaux.ipady = 2;
				gbc_lblBaux.insets = new Insets(0, 0, 5, 0);
				gbc_lblBaux.gridx = 0;
				gbc_lblBaux.gridy = 0;
				panel.add(lblBaux, gbc_lblBaux);
			}
			{
				JList list = new JList();
				list.setModel(new AbstractListModel() {
					String[] values = new String[] {"Chargement..."};
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.fill = GridBagConstraints.BOTH;
				gbc_list.gridx = 0;
				gbc_list.gridy = 1;
				panel.add(list, gbc_list);
			}
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(5);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.gridx = 5;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
