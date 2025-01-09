package vue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArchiverDocuments extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldDateArchivage;
	private JTextField textFieldBatimentConcerne;


	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public ArchiverDocuments() {
		setBounds(25, 25, 670, 342);
		getContentPane().setLayout(null);
		
		JPanel panel_FiltreDocuments = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_FiltreDocuments.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_FiltreDocuments.setBounds(35, 30, 379, 29);
		getContentPane().add(panel_FiltreDocuments);
		
		JLabel lblBatiment = new JLabel("Batiment");
		panel_FiltreDocuments.add(lblBatiment);
		
		JComboBox comboBoxBatiment = new JComboBox();
		comboBoxBatiment.setModel(new DefaultComboBoxModel(new String[] {"Tous", "batiment1", "batiment2"}));
		panel_FiltreDocuments.add(comboBoxBatiment);
		
		JPanel panel = new JPanel();
		panel_FiltreDocuments.add(panel);
		
		JLabel lblTypeDoc = new JLabel("Type : ");
		panel_FiltreDocuments.add(lblTypeDoc);
		
		JComboBox comboBoxTypeDoc = new JComboBox();
		comboBoxTypeDoc.setModel(new DefaultComboBoxModel(new String[] {"Tous", "Baux", "Etats des lieux", "Diagnostics", "Factures"}));
		panel_FiltreDocuments.add(comboBoxTypeDoc);
		
		JPanel panel_ListeDoc = new JPanel();
		panel_ListeDoc.setBorder(new TitledBorder(null, "Liste des documents archiv\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_ListeDoc.setBounds(35, 80, 270, 171);
		getContentPane().add(panel_ListeDoc);
		panel_ListeDoc.setLayout(null);
		
		JScrollPane scrollPaneListeDoc = new JScrollPane();
		scrollPaneListeDoc.setBounds(20, 26, 225, 122);
		panel_ListeDoc.add(scrollPaneListeDoc);
		
		JList listDocuments = new JList();
		listDocuments.setModel(new AbstractListModel() {
			String[] values = new String[] {"lien 1 ...................................................", "lien 1  ...................................................", "lien 1 ..................................................."};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneListeDoc.setViewportView(listDocuments);
		
		JPanel panel_DetailsDoc = new JPanel();
		panel_DetailsDoc.setBorder(new TitledBorder(null, "D\u00E9tails", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_DetailsDoc.setBounds(337, 80, 270, 124);
		getContentPane().add(panel_DetailsDoc);
		panel_DetailsDoc.setLayout(null);
		
		JLabel lblDateArchivage = new JLabel("Date d'archivage :");
		lblDateArchivage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateArchivage.setBounds(10, 30, 123, 19);
		panel_DetailsDoc.add(lblDateArchivage);
		
		textFieldDateArchivage = new JTextField();
		lblDateArchivage.setLabelFor(textFieldDateArchivage);
		textFieldDateArchivage.setBounds(143, 30, 96, 19);
		panel_DetailsDoc.add(textFieldDateArchivage);
		textFieldDateArchivage.setEditable(false);
		textFieldDateArchivage.setColumns(10);
		
		textFieldBatimentConcerne = new JTextField();
		textFieldBatimentConcerne.setBounds(143, 74, 96, 19);
		panel_DetailsDoc.add(textFieldBatimentConcerne);
		textFieldBatimentConcerne.setEditable(false);
		textFieldBatimentConcerne.setColumns(10);
		
		JLabel lblBatimentConcerne = new JLabel("Batiment concerné :");
		lblBatimentConcerne.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBatimentConcerne.setLabelFor(textFieldBatimentConcerne);
		lblBatimentConcerne.setBounds(10, 77, 123, 13);
		panel_DetailsDoc.add(lblBatimentConcerne);
		
		JPanel panel_Boutons = new JPanel();
		panel_Boutons.setBounds(35, 261, 232, 42);
		getContentPane().add(panel_Boutons);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(this);
		panel_Boutons.add(btnRetour);
		
		JButton btnArchiver = new JButton("Archiver");
		panel_Boutons.add(btnArchiver);

	}
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
