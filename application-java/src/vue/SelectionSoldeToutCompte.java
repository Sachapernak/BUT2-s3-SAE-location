package vue;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JScrollPane;
import controleur.GestionSoldeToutCompteSelection;

import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextField;

public class SelectionSoldeToutCompte extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBoxBien;
	private JList<String> listBail;
	private JComboBox<String> comboBoxLoc;
	private JButton btnGenerer;
	private JButton btnQuitter;
	
	private GestionSoldeToutCompteSelection gest;
	private JTextField textFieldDeb;
	private JTextField textFieldFin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectionSoldeToutCompte frame = new SelectionSoldeToutCompte();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelectionSoldeToutCompte() {
		
		gest = new GestionSoldeToutCompteSelection(this);
		setBounds(100, 100, 500, 220);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 105, 70, 62, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 100, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTitre = new JLabel("Solde de tout compte");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.gridwidth = 5;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 1;
		gbc_lblTitre.gridy = 0;
		getContentPane().add(lblTitre, gbc_lblTitre);
		
		JLabel lblBienLocatif = new JLabel("Bien locatif :");
		GridBagConstraints gbc_lblBienLocatif = new GridBagConstraints();
		gbc_lblBienLocatif.insets = new Insets(0, 0, 5, 5);
		gbc_lblBienLocatif.anchor = GridBagConstraints.WEST;
		gbc_lblBienLocatif.gridx = 1;
		gbc_lblBienLocatif.gridy = 1;
		getContentPane().add(lblBienLocatif, gbc_lblBienLocatif);
		
		comboBoxBien = new JComboBox<>();
		comboBoxBien.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		GridBagConstraints gbc_comboBoxBien = new GridBagConstraints();
		gbc_comboBoxBien.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxBien.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBien.gridx = 2;
		gbc_comboBoxBien.gridy = 1;
		getContentPane().add(comboBoxBien, gbc_comboBoxBien);
		
		JLabel lbBail = new JLabel("Locataire");
		GridBagConstraints gbc_lbBail = new GridBagConstraints();
		gbc_lbBail.anchor = GridBagConstraints.EAST;
		gbc_lbBail.insets = new Insets(0, 0, 5, 5);
		gbc_lbBail.gridx = 3;
		gbc_lbBail.gridy = 1;
		getContentPane().add(lbBail, gbc_lbBail);
		
		comboBoxLoc = new JComboBox<String>();

		GridBagConstraints gbc_comboBoxLoc = new GridBagConstraints();
		gbc_comboBoxLoc.gridwidth = 2;
		gbc_comboBoxLoc.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxLoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxLoc.gridx = 4;
		gbc_comboBoxLoc.gridy = 1;
		getContentPane().add(comboBoxLoc, gbc_comboBoxLoc);
		
		JLabel lblDebut = new JLabel("Du :");
		GridBagConstraints gbc_lblDebut = new GridBagConstraints();
		gbc_lblDebut.anchor = GridBagConstraints.EAST;
		gbc_lblDebut.insets = new Insets(0, 0, 5, 5);
		gbc_lblDebut.gridx = 1;
		gbc_lblDebut.gridy = 2;
		getContentPane().add(lblDebut, gbc_lblDebut);
		
		textFieldDeb = new JTextField();
		GridBagConstraints gbc_textFieldDeb = new GridBagConstraints();
		gbc_textFieldDeb.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDeb.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDeb.gridx = 2;
		gbc_textFieldDeb.gridy = 2;
		getContentPane().add(textFieldDeb, gbc_textFieldDeb);
		textFieldDeb.setColumns(10);
		
		JLabel lblFin = new JLabel("au :");
		GridBagConstraints gbc_lblFin = new GridBagConstraints();
		gbc_lblFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFin.anchor = GridBagConstraints.EAST;
		gbc_lblFin.gridx = 3;
		gbc_lblFin.gridy = 2;
		getContentPane().add(lblFin, gbc_lblFin);
		
		textFieldFin = new JTextField();
		GridBagConstraints gbc_textFieldFin = new GridBagConstraints();
		gbc_textFieldFin.gridwidth = 2;
		gbc_textFieldFin.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFin.gridx = 4;
		gbc_textFieldFin.gridy = 2;
		getContentPane().add(textFieldFin, gbc_textFieldFin);
		textFieldFin.setColumns(10);
		
		JLabel lblListBail = new JLabel("Bail :");
		GridBagConstraints gbc_lblListBail = new GridBagConstraints();
		gbc_lblListBail.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblListBail.insets = new Insets(0, 0, 5, 5);
		gbc_lblListBail.gridx = 1;
		gbc_lblListBail.gridy = 4;
		getContentPane().add(lblListBail, gbc_lblListBail);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 300));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 4;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		listBail = new JList<>();
		scrollPane.setViewportView(listBail);
		
		btnGenerer = new JButton("Generer");
		GridBagConstraints gbc_btnGenerer = new GridBagConstraints();
		gbc_btnGenerer.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerer.gridx = 4;
		gbc_btnGenerer.gridy = 5;
		getContentPane().add(btnGenerer, gbc_btnGenerer);
		
		btnQuitter = new JButton("Quitter");
		GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
		gbc_btnQuitter.anchor = GridBagConstraints.EAST;
		gbc_btnQuitter.insets = new Insets(0, 0, 0, 5);
		gbc_btnQuitter.gridx = 5;
		gbc_btnQuitter.gridy = 5;
		getContentPane().add(btnQuitter, gbc_btnQuitter);
		

		btnQuitter.addActionListener(e -> dispose());
		
		gest.chargerComboBoxLogement();
		gest.chargerComboBoxLoc();

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
