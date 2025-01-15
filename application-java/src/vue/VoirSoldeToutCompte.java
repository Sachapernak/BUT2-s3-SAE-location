package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class VoirSoldeToutCompte extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnQuitter;
	private JLabel lblNonLoc;
	private JTextField textFieldNomLoc;
	private JTextField textFieldPrenom;
	private JTextField textDateDebut;
	private JTextField textDateFin;
	private JTextField textFieldSousTotCharge;
	private JList listCharges;
	private JList listDeductions;
	private JTextField textSousTotDEduc;
	private JTextField textFieldTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VoirSoldeToutCompte dialog = new VoirSoldeToutCompte(null, null);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VoirSoldeToutCompte(String IdLoc, String IdBail) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{10, 0, 0, 0, 0, 10, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 130, 0, 130, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTitre = new JLabel("Solde de tout comtpes");
			GridBagConstraints gbc_lblTitre = new GridBagConstraints();
			gbc_lblTitre.gridwidth = 6;
			gbc_lblTitre.insets = new Insets(0, 0, 5, 0);
			gbc_lblTitre.gridx = 0;
			gbc_lblTitre.gridy = 0;
			contentPanel.add(lblTitre, gbc_lblTitre);
		}
		{
			lblNonLoc = new JLabel("Nom :");
			GridBagConstraints gbc_lblNonLoc = new GridBagConstraints();
			gbc_lblNonLoc.insets = new Insets(0, 0, 5, 5);
			gbc_lblNonLoc.anchor = GridBagConstraints.EAST;
			gbc_lblNonLoc.gridx = 1;
			gbc_lblNonLoc.gridy = 1;
			contentPanel.add(lblNonLoc, gbc_lblNonLoc);
		}
		{
			textFieldNomLoc = new JTextField();
			textFieldNomLoc.setEditable(false);
			GridBagConstraints gbc_textFieldNomLoc = new GridBagConstraints();
			gbc_textFieldNomLoc.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldNomLoc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldNomLoc.gridx = 2;
			gbc_textFieldNomLoc.gridy = 1;
			contentPanel.add(textFieldNomLoc, gbc_textFieldNomLoc);
			textFieldNomLoc.setColumns(10);
		}
		{
			JLabel lblPrenom = new JLabel("Prénom :");
			GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
			gbc_lblPrenom.anchor = GridBagConstraints.EAST;
			gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
			gbc_lblPrenom.gridx = 3;
			gbc_lblPrenom.gridy = 1;
			contentPanel.add(lblPrenom, gbc_lblPrenom);
		}
		{
			textFieldPrenom = new JTextField();
			textFieldPrenom.setEditable(false);
			GridBagConstraints gbc_textFieldPrenom = new GridBagConstraints();
			gbc_textFieldPrenom.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldPrenom.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldPrenom.gridx = 4;
			gbc_textFieldPrenom.gridy = 1;
			contentPanel.add(textFieldPrenom, gbc_textFieldPrenom);
			textFieldPrenom.setColumns(10);
		}
		{
			JLabel lblDate = new JLabel("Date du :");
			GridBagConstraints gbc_lblDate = new GridBagConstraints();
			gbc_lblDate.anchor = GridBagConstraints.EAST;
			gbc_lblDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblDate.gridx = 1;
			gbc_lblDate.gridy = 2;
			contentPanel.add(lblDate, gbc_lblDate);
		}
		{
			textDateDebut = new JTextField();
			textDateDebut.setEditable(false);
			GridBagConstraints gbc_textDateDebut = new GridBagConstraints();
			gbc_textDateDebut.insets = new Insets(0, 0, 5, 5);
			gbc_textDateDebut.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDateDebut.gridx = 2;
			gbc_textDateDebut.gridy = 2;
			contentPanel.add(textDateDebut, gbc_textDateDebut);
			textDateDebut.setColumns(10);
		}
		{
			JLabel lblAU = new JLabel("au :");
			GridBagConstraints gbc_lblAU = new GridBagConstraints();
			gbc_lblAU.insets = new Insets(0, 0, 5, 5);
			gbc_lblAU.anchor = GridBagConstraints.EAST;
			gbc_lblAU.gridx = 3;
			gbc_lblAU.gridy = 2;
			contentPanel.add(lblAU, gbc_lblAU);
		}
		{
			textDateFin = new JTextField();
			textDateFin.setEditable(false);
			GridBagConstraints gbc_textDateFin = new GridBagConstraints();
			gbc_textDateFin.insets = new Insets(0, 0, 5, 5);
			gbc_textDateFin.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDateFin.gridx = 4;
			gbc_textDateFin.gridy = 2;
			contentPanel.add(textDateFin, gbc_textDateFin);
			textDateFin.setColumns(10);
		}
		{
			JLabel lblCharge = new JLabel("Charges :");
			GridBagConstraints gbc_lblCharge = new GridBagConstraints();
			gbc_lblCharge.anchor = GridBagConstraints.NORTH;
			gbc_lblCharge.insets = new Insets(0, 0, 5, 5);
			gbc_lblCharge.gridx = 1;
			gbc_lblCharge.gridy = 3;
			contentPanel.add(lblCharge, gbc_lblCharge);
		}
		{
			JScrollPane scrollPaneCharges = new JScrollPane();
			GridBagConstraints gbc_scrollPaneCharges = new GridBagConstraints();
			gbc_scrollPaneCharges.gridwidth = 3;
			gbc_scrollPaneCharges.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPaneCharges.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneCharges.gridx = 2;
			gbc_scrollPaneCharges.gridy = 3;
			contentPanel.add(scrollPaneCharges, gbc_scrollPaneCharges);
			{
				listCharges = new JList();
				scrollPaneCharges.setViewportView(listCharges);
			}
		}
		{
			JLabel lblSousTotCharge = new JLabel("Sous-total :");
			GridBagConstraints gbc_lblSousTotCharge = new GridBagConstraints();
			gbc_lblSousTotCharge.anchor = GridBagConstraints.EAST;
			gbc_lblSousTotCharge.insets = new Insets(0, 0, 5, 5);
			gbc_lblSousTotCharge.gridx = 3;
			gbc_lblSousTotCharge.gridy = 4;
			contentPanel.add(lblSousTotCharge, gbc_lblSousTotCharge);
		}
		{
			textFieldSousTotCharge = new JTextField();
			textFieldSousTotCharge.setEditable(false);
			GridBagConstraints gbc_textFieldSousTotCharge = new GridBagConstraints();
			gbc_textFieldSousTotCharge.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldSousTotCharge.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldSousTotCharge.gridx = 4;
			gbc_textFieldSousTotCharge.gridy = 4;
			contentPanel.add(textFieldSousTotCharge, gbc_textFieldSousTotCharge);
			textFieldSousTotCharge.setColumns(10);
		}
		{
			JLabel lblDeduire = new JLabel("A déduire :");
			GridBagConstraints gbc_lblDeduire = new GridBagConstraints();
			gbc_lblDeduire.anchor = GridBagConstraints.NORTH;
			gbc_lblDeduire.insets = new Insets(0, 0, 5, 5);
			gbc_lblDeduire.gridx = 1;
			gbc_lblDeduire.gridy = 5;
			contentPanel.add(lblDeduire, gbc_lblDeduire);
		}
		{
			JScrollPane scrollPaneDeductions = new JScrollPane();
			GridBagConstraints gbc_scrollPaneDeductions = new GridBagConstraints();
			gbc_scrollPaneDeductions.gridwidth = 3;
			gbc_scrollPaneDeductions.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPaneDeductions.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneDeductions.gridx = 2;
			gbc_scrollPaneDeductions.gridy = 5;
			contentPanel.add(scrollPaneDeductions, gbc_scrollPaneDeductions);
			{
				listDeductions = new JList();
				scrollPaneDeductions.setViewportView(listDeductions);
			}
		}
		{
			JLabel lblSousTotDeduc = new JLabel("Sous-total :");
			GridBagConstraints gbc_lblSousTotDeduc = new GridBagConstraints();
			gbc_lblSousTotDeduc.anchor = GridBagConstraints.EAST;
			gbc_lblSousTotDeduc.insets = new Insets(0, 0, 5, 5);
			gbc_lblSousTotDeduc.gridx = 3;
			gbc_lblSousTotDeduc.gridy = 6;
			contentPanel.add(lblSousTotDeduc, gbc_lblSousTotDeduc);
		}
		{
			textSousTotDEduc = new JTextField();
			textSousTotDEduc.setEditable(false);
			GridBagConstraints gbc_textSousTotDEduc = new GridBagConstraints();
			gbc_textSousTotDEduc.insets = new Insets(0, 0, 5, 5);
			gbc_textSousTotDEduc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textSousTotDEduc.gridx = 4;
			gbc_textSousTotDEduc.gridy = 6;
			contentPanel.add(textSousTotDEduc, gbc_textSousTotDEduc);
			textSousTotDEduc.setColumns(10);
		}
		{
			JLabel lblTotal = new JLabel("Total :");
			GridBagConstraints gbc_lblTotal = new GridBagConstraints();
			gbc_lblTotal.anchor = GridBagConstraints.EAST;
			gbc_lblTotal.insets = new Insets(0, 0, 0, 5);
			gbc_lblTotal.gridx = 1;
			gbc_lblTotal.gridy = 7;
			contentPanel.add(lblTotal, gbc_lblTotal);
		}
		{
			textFieldTotal = new JTextField();
			textFieldTotal.setEditable(false);
			GridBagConstraints gbc_textFieldTotal = new GridBagConstraints();
			gbc_textFieldTotal.gridwidth = 3;
			gbc_textFieldTotal.insets = new Insets(0, 0, 0, 5);
			gbc_textFieldTotal.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldTotal.gridx = 2;
			gbc_textFieldTotal.gridy = 7;
			contentPanel.add(textFieldTotal, gbc_textFieldTotal);
			textFieldTotal.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGenerer = new JButton("Générer ");
				btnGenerer.setActionCommand("OK");
				buttonPane.add(btnGenerer);
				getRootPane().setDefaultButton(btnGenerer);
			}
			{
				btnQuitter = new JButton("Quitter");
				btnQuitter.setActionCommand("Cancel");
				buttonPane.add(btnQuitter);
			}
		}
	}

}
