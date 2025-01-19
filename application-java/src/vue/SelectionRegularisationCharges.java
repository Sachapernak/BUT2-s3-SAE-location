package vue;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JScrollPane;
import controleur.GestionSelectionRegularisationCharges;

import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractListModel;

public class SelectionRegularisationCharges extends JInternalFrame {

	private static final String CHARGEMENT = "Chargement...";
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBoxBien;
	private JList<String> listBail;
	private JComboBox<String> comboBoxLoc;
	private JButton btnGenerer;
	private JButton btnQuitter;
	
	private GestionSelectionRegularisationCharges gest;
	private JTextField textFieldDeb;
	private JTextField textFieldFin;



	/**
	 * Create the frame.
	 */
	public SelectionRegularisationCharges() {
		
		gest = new GestionSelectionRegularisationCharges(this);
		setBounds(100, 100, 500, 220);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 105, 70, 62, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 100, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTitre = new JLabel("Régularisation des charges");
		GridBagConstraints gbcLblTitre = new GridBagConstraints();
		gbcLblTitre.gridwidth = 5;
		gbcLblTitre.insets = new Insets(0, 0, 5, 5);
		gbcLblTitre.gridx = 1;
		gbcLblTitre.gridy = 0;
		getContentPane().add(lblTitre, gbcLblTitre);
		
		JLabel lblBienLocatif = new JLabel("Bien locatif :");
		GridBagConstraints gbcLblBienLocatif = new GridBagConstraints();
		gbcLblBienLocatif.insets = new Insets(0, 0, 5, 5);
		gbcLblBienLocatif.anchor = GridBagConstraints.WEST;
		gbcLblBienLocatif.gridx = 1;
		gbcLblBienLocatif.gridy = 1;
		getContentPane().add(lblBienLocatif, gbcLblBienLocatif);
		
		comboBoxBien = new JComboBox<>();
		comboBoxBien.setModel(new DefaultComboBoxModel<>(new String[] {CHARGEMENT}));

		GridBagConstraints gbcComboBoxBien = new GridBagConstraints();
		gbcComboBoxBien.insets = new Insets(0, 0, 5, 5);
		gbcComboBoxBien.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxBien.gridx = 2;
		gbcComboBoxBien.gridy = 1;
		getContentPane().add(comboBoxBien, gbcComboBoxBien);
		
		JLabel lbBail = new JLabel("Locataire");
		GridBagConstraints gbcLbBail = new GridBagConstraints();
		gbcLbBail.anchor = GridBagConstraints.EAST;
		gbcLbBail.insets = new Insets(0, 0, 5, 5);
		gbcLbBail.gridx = 3;
		gbcLbBail.gridy = 1;
		getContentPane().add(lbBail, gbcLbBail);
		
		comboBoxLoc = new JComboBox<>();
		comboBoxLoc.setModel(new DefaultComboBoxModel<>(new String[] {CHARGEMENT}));

		GridBagConstraints gbcComboBoxLoc = new GridBagConstraints();
		gbcComboBoxLoc.gridwidth = 2;
		gbcComboBoxLoc.insets = new Insets(0, 0, 5, 5);
		gbcComboBoxLoc.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxLoc.gridx = 4;
		gbcComboBoxLoc.gridy = 1;
		getContentPane().add(comboBoxLoc, gbcComboBoxLoc);
		
		JLabel lblDebut = new JLabel("Du :");
		GridBagConstraints gbcLblDebut = new GridBagConstraints();
		gbcLblDebut.anchor = GridBagConstraints.EAST;
		gbcLblDebut.insets = new Insets(0, 0, 5, 5);
		gbcLblDebut.gridx = 1;
		gbcLblDebut.gridy = 2;
		getContentPane().add(lblDebut, gbcLblDebut);
		
		textFieldDeb = new JTextField();
		GridBagConstraints gbcTextFieldDeb = new GridBagConstraints();
		gbcTextFieldDeb.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldDeb.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDeb.gridx = 2;
		gbcTextFieldDeb.gridy = 2;
		getContentPane().add(textFieldDeb, gbcTextFieldDeb);
		textFieldDeb.setColumns(10);
		
		JLabel lblFin = new JLabel("au :");
		GridBagConstraints gbcLblFin = new GridBagConstraints();
		gbcLblFin.insets = new Insets(0, 0, 5, 5);
		gbcLblFin.anchor = GridBagConstraints.EAST;
		gbcLblFin.gridx = 3;
		gbcLblFin.gridy = 2;
		getContentPane().add(lblFin, gbcLblFin);
		
		textFieldFin = new JTextField();
		GridBagConstraints gbcTextFieldFin = new GridBagConstraints();
		gbcTextFieldFin.gridwidth = 2;
		gbcTextFieldFin.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldFin.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldFin.gridx = 4;
		gbcTextFieldFin.gridy = 2;
		getContentPane().add(textFieldFin, gbcTextFieldFin);
		textFieldFin.setColumns(10);
		
		JLabel lblListBail = new JLabel("Bail :");
		GridBagConstraints gbcLblListBail = new GridBagConstraints();
		gbcLblListBail.anchor = GridBagConstraints.NORTHEAST;
		gbcLblListBail.insets = new Insets(0, 0, 5, 5);
		gbcLblListBail.gridx = 1;
		gbcLblListBail.gridy = 4;
		getContentPane().add(lblListBail, gbcLblListBail);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 300));
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 4;
		gbcScrollPane.insets = new Insets(0, 0, 5, 5);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 2;
		gbcScrollPane.gridy = 4;
		getContentPane().add(scrollPane, gbcScrollPane);
		
		listBail = new JList<>();
		listBail.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] {CHARGEMENT};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(listBail);
		
		btnGenerer = new JButton("Generer");
		GridBagConstraints gbcRbtnGenerer = new GridBagConstraints();
		gbcRbtnGenerer.insets = new Insets(0, 0, 0, 5);
		gbcRbtnGenerer.gridx = 4;
		gbcRbtnGenerer.gridy = 5;
		getContentPane().add(btnGenerer, gbcRbtnGenerer);
		
		btnQuitter = new JButton("Quitter");
		GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.anchor = GridBagConstraints.EAST;
		gbcBtnQuitter.insets = new Insets(0, 0, 0, 5);
		gbcBtnQuitter.gridx = 5;
		gbcBtnQuitter.gridy = 5;
		getContentPane().add(btnQuitter, gbcBtnQuitter);
		

		btnQuitter.addActionListener(e -> dispose());
		
		gest.chargerComboBoxLogement();

		gest.gestionComboLoc(comboBoxLoc);
		gest.gestionComboBien(comboBoxBien);
		gest.gestionBtnGenerer(btnGenerer);

	}

	public String getSelectedLoc() {
		return String.valueOf(comboBoxLoc.getSelectedItem());
	}
	
	public String getSelectedBien() {
		return String.valueOf(comboBoxBien.getSelectedItem());
	}
	
	public String getSelectedBail() {
		System.out.println("Vue selection regu charge l.199 : " + String.valueOf(listBail.getSelectedValue()));
		return String.valueOf(listBail.getSelectedValue());
	}
	
	public String getDateDebut() {
		return String.valueOf(textFieldDeb.getText());
	}
	
	public String getDateFin() {
		return String.valueOf(textFieldFin.getText());
	}
	
	
	
    /**
     * Remplit la combo des locataires.
     * @param locataires la liste d'IDs locataires
     */
    public void setComboBoxLocataire(List<String> locataires) {
        comboBoxLoc.removeAllItems();
        for(String locataire : locataires) {
            comboBoxLoc.addItem(locataire);
        }
    }
    
    /**
     * Remplit la combo des bien.
     * @param locataires la liste d'IDs de biens
     */
    public void setComboBoxBien(List<String> biens) {
        comboBoxBien.removeAllItems();
        for(String bien : biens) {
            comboBoxBien.addItem(bien);
        }
    }
    
	public void setTableBail(List<String> bails) {
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		
		for (String bail : bails) {
			listModel.addElement(bail);
		}
		

		listBail.setModel(listModel);
	}
    
    /**
     * Affiche un message d'erreur dans une boîte de dialogue.
     * @param message le message d'erreur
     */
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(
            this,
            "Erreur : \n" + message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    






}
