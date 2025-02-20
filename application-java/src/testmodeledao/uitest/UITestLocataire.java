package testmodeledao.uitest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Locataire;
import modele.dao.DaoLocataire;

public class UITestLocataire {

	private JFrame frame;
	private JTable tableLocataire;
	private JPanel panel;
	private JLabel lblTitreAdresse;
	private JTextField textAdresse;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UITestLocataire window = new UITestLocataire();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UITestLocataire() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 200);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		tableLocataire = new JTable();


		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Nom", "Prenom", "Email", "Telephone", "Date Naissance", "Lieu"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class, String.class, String.class
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		
		tableLocataire.setModel(model);
		tableLocataire.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableLocataire.getColumnModel().getColumn(5).setPreferredWidth(90);
		scrollPane.setViewportView(tableLocataire);
		
		label = new JLabel("");
		frame.getContentPane().add(label);
		
		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblTitreAdresse = new JLabel(" Adresse:");
		lblTitreAdresse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblTitreAdresse, BorderLayout.NORTH);
		
		textAdresse = new JTextField();
		panel.add(textAdresse, BorderLayout.CENTER);
		textAdresse.setColumns(10);
		
		// Ajout de l'action listener pour afficher l'adresse
		ligneTableSelectionne(textAdresse);
		
		// Ajout des données locataire a partir de la BD
		ajoutDonneesLocataire(model);
		
		
		
		
	}

	private void ligneTableSelectionne(JTextField textfield) {
		tableLocataire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
			    int row = target.getSelectedRow();
			    
			    String id = (String) target.getModel().getValueAt(row, 0);
			    
			    try {
					String adresseComplete = recupererStringAdresseLocataire(id);
					
					textfield.setText(adresseComplete);
					
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			    
	
			    
			}
		});
	}

	private void ajoutDonneesLocataire(DefaultTableModel model) {
		// Cette partie n'est normalement pas intégrée a la vue, cependant,
		// dans le cadre du test, le modele-vue-controleur ne sera pas respecté
		
		DaoLocataire daoLoc = new DaoLocataire();
		List<Locataire> locataires = null;
		
		try {
			locataires = daoLoc.findAll();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		
		for (Locataire l : locataires) {
			Object[] nouvelleLigne = {l.getIdLocataire(), l.getNom(), l.getPrenom(), l.getEmail(), l.getTelephone(),
									l.getDateNaissance(), l.getLieuDeNaissance()};
			model.addRow(nouvelleLigne);
		}
	}

	/* Permet de récuperer le string de l'adresse complete
	 	l'adresse en string complet
	*/       
	private String recupererStringAdresseLocataire(String id) throws SQLException, IOException {
		Locataire loc = new DaoLocataire().findById(id);
		
		String adresseComplete;
		if (loc.getAdresse() != null) {
		
			Adresse adr = loc.getAdresse();
			
			
			String adresse = adr.getAdressePostale();
			String complementAdresse = "";
			String codePostal = Integer.toString(adr.getCodePostal());
			String ville = adr.getVille();
			
			
			if (adr.getComplementAdresse() != null) {
				complementAdresse = adr.getComplementAdresse() + ", ";
			}
			
			adresseComplete = adresse + ", " + complementAdresse + codePostal + " " + ville;
		} else {
			adresseComplete = "";
		}
		return adresseComplete;
	}

	

}
