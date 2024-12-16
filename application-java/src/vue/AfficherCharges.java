package vue;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAfficherCharges;

public class AfficherCharges extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GestionAfficherCharges gestionClic;
	private JTable tableChargesIndex;
	private JTable tableIntituleLigneChargesIndex;
	private JTable tableChargesFixes;
	private JTable tableIntituleLigneChargesFixes;
	private JTextField textFieldTotalChIndex;
	private JTextField textFieldTotalChFixes;
	private JTextField textFieldTotal;

	

	/**
	 * Create the frame.
	 */
	public AfficherCharges() {
		this.gestionClic = new GestionAfficherCharges(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 650, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_charges = new JPanel();
		panel_charges.setBounds(10, 27, 618, 272);
		contentPane.add(panel_charges);
		panel_charges.setLayout(null);
		
		JLabel lblCharges = new JLabel("Les charges associées au bien :");
		lblCharges.setForeground(new Color(0, 0, 0));
		lblCharges.setBounds(0, 10, 205, 13);
		panel_charges.add(lblCharges);
		
		JScrollPane scrollPaneChargesIndex = new JScrollPane();
		scrollPaneChargesIndex.setBounds(0, 47, 600, 86);
		panel_charges.add(scrollPaneChargesIndex);
		
		tableChargesIndex = new JTable();
		tableChargesIndex.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Date1", "Date2", "Date3", "Date4", "Date5", "Date6", "Date3", "Date5"
			}
		));
		 for (int i = 0; i < tableChargesIndex.getColumnCount(); i++) {
	            tableChargesIndex.getColumnModel().getColumn(i).setPreferredWidth(80); 
	        }
		tableChargesIndex.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneChargesIndex.setViewportView(tableChargesIndex);
		
		tableIntituleLigneChargesIndex = new JTable();
		tableIntituleLigneChargesIndex.setBorder(new LineBorder(UIManager.getColor("Table.dropLineColor")));
		tableIntituleLigneChargesIndex.setBackground(UIManager.getColor("TableHeader.background"));
		tableIntituleLigneChargesIndex.setRowSelectionAllowed(false);
		tableIntituleLigneChargesIndex.setEnabled(false);
		tableIntituleLigneChargesIndex.setModel(new DefaultTableModel(
			new Object[][] {
				{"Charges \u00E0 index"},
				{"Parts"},
				{"Total"},
			},
			new String[] {
				" "
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableIntituleLigneChargesIndex.getColumnModel().getColumn(0).setResizable(false);
		tableIntituleLigneChargesIndex.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableIntituleLigneChargesIndex.getColumnModel().getColumn(0).setMaxWidth(100);
		tableIntituleLigneChargesIndex.setPreferredScrollableViewportSize(new Dimension(100, 0));
		scrollPaneChargesIndex.setRowHeaderView(tableIntituleLigneChargesIndex);
		
		JScrollPane scrollPaneChargesFixes = new JScrollPane();
		scrollPaneChargesFixes.setBounds(0, 165, 600, 86);
		panel_charges.add(scrollPaneChargesFixes);
		
		tableChargesFixes = new JTable();
		tableChargesFixes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Date1", "Date2", "Date3", "Date4", "Date5", "Date6", "Date3", "Date5"
			}
		));
		tableChargesFixes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneChargesFixes.setViewportView(tableChargesFixes);
		
		tableIntituleLigneChargesFixes = new JTable();
		tableIntituleLigneChargesFixes.setModel(new DefaultTableModel(
			new Object[][] {
				{"Charges fixes"},
				{"Parts"},
				{"Total"},
			},
			new String[] {
				" "
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableIntituleLigneChargesFixes.getColumnModel().getColumn(0).setResizable(false);
		tableIntituleLigneChargesFixes.setRowSelectionAllowed(false);
		tableIntituleLigneChargesFixes.setPreferredScrollableViewportSize(new Dimension(100, 0));
		tableIntituleLigneChargesFixes.setEnabled(false);
		tableIntituleLigneChargesFixes.setBorder(new LineBorder(UIManager.getColor("Table.dropLineColor")));
		tableIntituleLigneChargesFixes.setBackground(UIManager.getColor("TableHeader.background"));
		scrollPaneChargesFixes.setRowHeaderView(tableIntituleLigneChargesFixes);
		
		JPanel panel_total = new JPanel();
		panel_total.setBounds(10, 309, 244, 155);
		contentPane.add(panel_total);
		panel_total.setBorder(new TitledBorder(null, "Total des charges", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_total.setLayout(null);
		
		JLabel lblIntituleTotalChIndex = new JLabel("Charges à index :");
		lblIntituleTotalChIndex.setBounds(10, 36, 135, 13);
		panel_total.add(lblIntituleTotalChIndex);
		
		textFieldTotalChIndex = new JTextField();
		textFieldTotalChIndex.setEditable(false);
		textFieldTotalChIndex.setBounds(142, 33, 86, 19);
		panel_total.add(textFieldTotalChIndex);
		textFieldTotalChIndex.setColumns(10);
		
		JLabel lblIntituleTotalChFixes = new JLabel("Charges fixes :");
		lblIntituleTotalChFixes.setBounds(10, 70, 119, 13);
		panel_total.add(lblIntituleTotalChFixes);
		
		textFieldTotalChFixes = new JTextField();
		textFieldTotalChFixes.setEditable(false);
		textFieldTotalChFixes.setColumns(10);
		textFieldTotalChFixes.setBounds(142, 67, 86, 19);
		panel_total.add(textFieldTotalChFixes);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 100, 218, 2);
		panel_total.add(separator);
		
		JLabel lblTotalCharges = new JLabel("Total charges : ");
		lblTotalCharges.setBounds(10, 118, 98, 13);
		panel_total.add(lblTotalCharges);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setColumns(10);
		textFieldTotal.setBounds(142, 115, 86, 19);
		panel_total.add(textFieldTotal);
		
		JButton btnRegulariser = new JButton("Régulariser");
		btnRegulariser.setBounds(416, 443, 100, 21);
		contentPane.add(btnRegulariser);
		btnRegulariser.addActionListener(this.gestionClic);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(538, 443, 85, 21);
		contentPane.add(btnRetour);
		btnRetour.addActionListener(this.gestionClic);
		

	}
}
