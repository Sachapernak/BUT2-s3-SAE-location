package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterLocataire;
import vue.ModifierLocataireActuel;

public class GestionAfficherLocataire implements ActionListener {
	
	private AfficherLocatairesActuels fen_afficher_locataires_actuels;
	private DaoLocataire daoLocataire;
	
	public GestionAfficherLocataire(AfficherLocatairesActuels afl)  {
		this.fen_afficher_locataires_actuels = afl;
		this.daoLocataire = new DaoLocataire();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Retour" :
				this.fen_afficher_locataires_actuels.dispose();
				break;
			case "Modifier" :
				ModifierLocataireActuel mla = new ModifierLocataireActuel(this.fen_afficher_locataires_actuels);
				JLayeredPane layeredPaneModifLoc = this.fen_afficher_locataires_actuels.getLayeredPane();
				layeredPaneModifLoc.add(mla, JLayeredPane.PALETTE_LAYER);
				mla.setVisible(true);
				/*
				JList<String> listeLoc = this.fen_afficher_locataires_actuels.getListLocatairesActuels();
			    int ligneSelect = listeLoc.getSelectedIndex();
			    
			    if (ligneSelect > -1) {
			    	Locataire loc;
					try {
						loc = lireLigneListe(ligneSelect);
						String[] separerLigne = separerInfosLoc(listeLoc, ligneSelect);
						loc.setNom(separerLigne[1]);
					    loc.setPrenom(separerLigne[2]);
					    loc.setDateNaissance(this.fen_afficher_locataires_actuels.getTextFieldDateDeNaissance().getText());
					    loc.setEmail(this.fen_afficher_locataires_actuels.getTextFieldMail().getText());
					    loc.setTelephone(this.fen_afficher_locataires_actuels.getTextFieldTel().getText());
					    
					    JOptionPane.showMessageDialog(null, "Modification effectuée");
					    
						try {
							this.daoLocataire.update(loc);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
			    }  */ 
				break;
				
			case "Ajouter" : 
				AjouterLocataire al = new AjouterLocataire(this.fen_afficher_locataires_actuels);
				JLayeredPane layeredPaneAjoutLoc = this.fen_afficher_locataires_actuels.getLayeredPane();
				layeredPaneAjoutLoc.add(al, JLayeredPane.PALETTE_LAYER);
				al.setVisible(true);
				break;
			case "Résilier le bail" : 
				System.out.println("resiliation");
				break;
		}
	}
	
	public void remplirListeLoc() {
	    JList<String> listeLoc = this.fen_afficher_locataires_actuels.getListLocatairesActuels();
	    List<Locataire> locataires;
		try {
			locataires = this.daoLocataire.findAll();
			if (locataires == null || locataires.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Aucun locataire trouvé.");
		        return;
		    }

		    DefaultListModel<String> listModel = new DefaultListModel<>();
		    for (Locataire locataire : locataires) {
		        listModel.addElement(locataire.getIdLocataire().toString() + "   " + locataire.getNom().toString() + "   " + locataire.getPrenom()); 
		    }

		    listeLoc.setModel(listModel);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Locataire lireLigneListe(int indexLigne) throws SQLException {
		Locataire resultat = null;
	    JList<String> listLocataires = this.fen_afficher_locataires_actuels.getListLocatairesActuels();

	    if (indexLigne < 0 || indexLigne >= listLocataires.getModel().getSize()) {
	        throw new IllegalArgumentException("Index invalide : " + indexLigne);
	    }

	    String ligneSelec = listLocataires.getModel().getElementAt(indexLigne);

	    String[] ligneSeparee = separerInfosLoc(listLocataires, indexLigne); 
	    String locataireId = ligneSeparee[0]; 
	    
	     try {
			resultat = this.daoLocataire.findById(locataireId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     
	    return resultat;
	}
	
    public static String[] separerInfosLoc(JList<String> listLocataires, int indexLigne) {
        if (indexLigne < 0 || indexLigne >= listLocataires.getModel().getSize()) {
            throw new IndexOutOfBoundsException("Index invalide : " + indexLigne);
        }
        
        String ligneSelec = listLocataires.getModel().getElementAt(indexLigne);
        
        String[] parts = ligneSelec.split("   ");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Impossible de séparer : " + ligneSelec);
        }
        return parts; // Retourne les parties séparées
    }

	
	
}
