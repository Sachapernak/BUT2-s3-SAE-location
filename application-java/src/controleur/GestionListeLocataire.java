package controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;

public class GestionListeLocataire {
	
	private AfficherLocatairesActuels fen_afficher_loc;
	
	public GestionListeLocataire(AfficherLocatairesActuels al) {
		this.afficherLocatairesActuels = al;
	}
	
	public void remplirListeLoc(AfficherLocatairesActuels fen_afficher_loc) {
			
		    JList<String> listeLoc = fen_afficher_loc.getListLocatairesActuels();
		    List<Locataire> locataires;
		    
		    viderJList(listeLoc);
		    
			try {
				locataires = new DaoLocataire().findAll();
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
		    JList<String> listLocataires = this.fen_afficher_loc.getListLocatairesActuels();
	
		    if (indexLigne < 0 || indexLigne >= listLocataires.getModel().getSize()) {
		        throw new IllegalArgumentException("Index invalide : " + indexLigne);
		    }
	
		    String[] ligneSeparee = separerInfosLoc(listLocataires, indexLigne); 
		    String locataireId = ligneSeparee[0]; 
		    
		     try {
				resultat = new DaoLocataire().findById(locataireId);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		     
		    return resultat;
		}
		
	    public String[] separerInfosLoc(JList<String> listLocataires, int indexLigne) {
	        if (indexLigne < 0 || indexLigne >= listLocataires.getModel().getSize()) {
	            throw new IndexOutOfBoundsException("Index invalide : " + indexLigne);
	        }
	        
	        String ligneSelec = listLocataires.getModel().getElementAt(indexLigne);
	        
	        String[] parts = ligneSelec.split("   ");
	        if (parts.length < 2) {
	            throw new IllegalArgumentException("Impossible de séparer : " + ligneSelec);
	        }
	        return parts;
	    }
	    
	    public static void viderJList(JList liste) {
	    	DefaultListModel model = (DefaultListModel) liste.getModel(); 
	        model.clear();
	    }


}
