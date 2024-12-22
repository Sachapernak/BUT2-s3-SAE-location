package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Bail;
import modele.Batiment;
import modele.BienLocatif;
import modele.Cautionnaire;
import modele.Cautionner;
import modele.Contracter;
import modele.Locataire;
import modele.Loyer;
import modele.TypeDeBien;
import modele.dao.DaoAdresse;
import modele.dao.DaoBail;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoCautionnaire;
import modele.dao.DaoCautionner;
import modele.dao.DaoContracter;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

public class GestionAjouterCautionnaire implements ActionListener{
	
	private AjouterCautionnaire fen_ajouter_cautionnaire;
	private AfficherLocatairesActuels fen_afficher_locataires;
	private AjouterLocataire fen_ajouter_locataire;
	private AjouterBail fen_ajouter_bail;
	
	private DaoLocataire daoLocataire;
	private DaoCautionnaire daoCautionnaire;
	private DaoCautionner daoCautionner;
	private DaoAdresse daoAdresse;
	private DaoBail daoBail;
	private DaoBienLocatif daoBien;
	private DaoContracter daoContracter;
	
	public GestionAjouterCautionnaire(AjouterCautionnaire ac, AjouterLocataire al, AfficherLocatairesActuels afl, AjouterBail ab) {
		this.fen_ajouter_cautionnaire = ac;
		this.fen_ajouter_locataire = al;
		this.fen_afficher_locataires = afl;
		this.fen_ajouter_bail = ab;
		
		this.daoBail = new DaoBail();
		this.daoBien = new DaoBienLocatif();
		this.daoCautionnaire = new DaoCautionnaire();
		this.daoCautionner = new DaoCautionner();
		this.daoLocataire = new DaoLocataire();
		this.daoAdresse = new DaoAdresse();
		this.daoContracter = new DaoContracter();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fen_ajouter_cautionnaire.dispose();
				break;
			case "Remplir avec le locataire": 
				String nomL = this.fen_ajouter_locataire.getTextFieldNom().getText();
				String prenomL = this.fen_ajouter_locataire.getTextFieldPrenom().getText();
				String adresseL = this.fen_ajouter_locataire.getTextFieldAdr().getText();
				String complementL = this.fen_ajouter_locataire.getTextFieldComplement().getText();
				String codePostalL = this.fen_ajouter_locataire.getTextFieldCodePostal().getText();
				String villeL = this.fen_ajouter_locataire.getTextFieldVille().getText();
				
				this.fen_ajouter_cautionnaire.getTextFieldNomOuOrga().setText(nomL);
				this.fen_ajouter_cautionnaire.getTextFieldPrenom().setText(prenomL);
				this.fen_ajouter_cautionnaire.getTextFieldAdr().setText(adresseL);
				this.fen_ajouter_cautionnaire.getTextFieldComplement().setText(complementL);
				this.fen_ajouter_cautionnaire.getTextFieldCodePostal().setText(codePostalL);
				this.fen_ajouter_cautionnaire.getTextFieldVille().setText(villeL);
				
				break;
			case "Valider" : 
					JTable tableLoc = this.fen_afficher_locataires.getTableLocatairesActuels();
					Locataire nouveauLocataire;
					Cautionnaire cautionnaire;
					Cautionner cautionner;
					Adresse adresseLocataire;
					Adresse adresseCautionnaire;
					Bail bail = null; 
					String dateDebut = null;
					float partLoyer = 1F;
					
					//Recup infos locataire
					String idLoc = this.fen_ajouter_locataire.getTextFieldId().getText();
					String nom = this.fen_ajouter_locataire.getTextFieldNom().getText();
					String prenom = this.fen_ajouter_locataire.getTextFieldPrenom().getText();
					
					String email = this.fen_ajouter_locataire.getTextFieldEmail().getText();
					String tel = this.fen_ajouter_locataire.getTextFieldTel().getText();
					email = email.isEmpty() ? null : email;
					tel = tel.isEmpty() ? null : tel;					
					String date_naissance = this.fen_ajouter_locataire.getTextFieldDateNaissance().getText();
					String lieu_naissance = this.fen_ajouter_locataire.getTextFieldLieuNaissance().getText();
					String acte_caution = "a supprimer";
					
					//Recup adresse
					String adresse = this.fen_ajouter_locataire.getTextFieldAdr().getText();
					String complement = this.fen_ajouter_locataire.getTextFieldComplement().getText();
					String codePostalStr = this.fen_ajouter_locataire.getTextFieldCodePostal().getText();
					String ville = this.fen_ajouter_locataire.getTextFieldVille().getText();
					
					adresse = (adresse.isEmpty()) ? null : adresse;
					complement = (complement.isEmpty()) ? null : complement;
					ville = (ville.isEmpty()) ? null : ville;
					int codePostal = (codePostalStr.isEmpty()) ? 0 : convertirStrToInt(codePostalStr);
										
					nouveauLocataire = new Locataire(idLoc, nom, prenom, date_naissance);
					nouveauLocataire.setActeDeCaution(acte_caution);
					nouveauLocataire.setEmail(email);
					nouveauLocataire.setTelephone(tel);
					nouveauLocataire.setLieuDeNaissance(lieu_naissance);
					adresseLocataire = null;
					if (adresse != null) {	
						adresseLocataire = new Adresse(adresse+codePostal+ville, adresse, codePostal, ville, complement);
						nouveauLocataire.setAdresse(adresseLocataire);
					}	
						
					//Recup bail
				    if (this.fen_ajouter_bail.getRdbtnNouveauBail().isSelected()) {
			
				        String idBail = this.fen_ajouter_bail.getTextFieldIdBail().getText();
				        dateDebut = this.fen_ajouter_bail.getTextFieldDateDebut().getText();
				        String dateFin = this.fen_ajouter_bail.getTextFieldDateFin().getText();
				        BienLocatif bien;
					try {
						bien = daoBien.findById((String) this.fen_ajouter_bail.getComboBoxBiensLoc().getSelectedItem());
						bail = new Bail(idBail, dateDebut, bien);
					    if (dateFin.length()>= 1) {
					    	bail.setDateDeFin(dateFin);
					    }
						daoBail.create(bail);
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}      
				        
				    } 
				    else if (this.fen_ajouter_bail.getRdbtnBailExistant().isSelected()) {
				        // Récupérez la sélection du tableau des baux existants
				        int selectedRow = this.fen_ajouter_bail.getTableBauxActuels().getSelectedRow();
				        if (selectedRow != -1) {
				            // Récupérez les informations à partir de la ligne sélectionnée
				            String bailSelectionne = (String) this.fen_ajouter_bail.getTableBauxActuels().getValueAt(selectedRow, 0);
				            dateDebut = this.fen_ajouter_bail.getTextFieldDateArrivee().getText();
				            JTable tableParts = this.fen_ajouter_bail.getTablePartsLoyer();
				            partLoyer = (float) tableParts.getValueAt(tableParts.getRowCount()-2,1);				            		            			            
							try {
								bail = daoBail.findById(bailSelectionne);
							} catch (SQLException | IOException e1) {
								e1.printStackTrace();
							}
							actualiserPartsLoyer(this.fen_ajouter_bail.getTablePartsLoyer(),bail);
				        } 
				    }
					   
				    //Recup cautionnaire
				    int idC = convertirStrToInt(this.fen_ajouter_cautionnaire.getTextFieldIdentifiantCautionnaire().getText());
				    String nomOuOrgaC = this.fen_ajouter_cautionnaire.getTextFieldNomOuOrga().getText();
					String prenomC = this.fen_ajouter_cautionnaire.getTextFieldPrenom().getText();
					String descriptionC = this.fen_ajouter_cautionnaire.getTextFieldDescription().getText();
					String adrC = this.fen_ajouter_cautionnaire.getTextFieldAdr().getText();
					String complementC = this.fen_ajouter_cautionnaire.getTextFieldComplement().getText();
					String codePostalStrC = this.fen_ajouter_cautionnaire.getTextFieldCodePostal().getText();
					String villeC = this.fen_ajouter_cautionnaire.getTextFieldVille().getText();
					
					adrC = (adrC.isEmpty()) ? null : adrC;
					complementC = (complementC.isEmpty()) ? null : complementC;
					villeC = (villeC.isEmpty()) ? null : villeC;
					int codePostalC = (codePostalStrC.isEmpty()) ? 0 : convertirStrToInt(codePostalStr);
					
					adresseCautionnaire = new Adresse(adrC+codePostalC+villeC, adrC, codePostalC, villeC, complementC);
					cautionnaire = new Cautionnaire(idC, nomOuOrgaC,prenomC, descriptionC, adresseCautionnaire);
					
					//Recup cautionner 
					String montantStr = this.fen_ajouter_cautionnaire.getTextFieldMontant().getText();
					BigDecimal montant = new BigDecimal(montantStr);
					String lienActeCaution = this.fen_ajouter_cautionnaire.getTextFieldLienActeCaution().getText();
					
					cautionner = new Cautionner(montant, lienActeCaution, bail, cautionnaire);
					
					Contracter ctr = new Contracter(nouveauLocataire, bail, dateDebut, partLoyer);
					nouveauLocataire.getContrats().add(ctr);
					
					try {
						if (adresse != null) {
							if (!estAdresseExistante(adresseLocataire)) {
								daoAdresse.create(adresseLocataire);
							}
						}
						if (!estAdresseExistante(adresseCautionnaire)) {
							daoAdresse.create(adresseCautionnaire);
						}
						
						daoLocataire.create(nouveauLocataire);
						daoCautionnaire.create(cautionnaire);
						daoCautionner.create(cautionner);
						
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}					
					DefaultTableModel modelTableLoc = (DefaultTableModel) tableLoc.getModel();
					modelTableLoc.addRow(new String[] { nouveauLocataire.getIdLocataire(), nouveauLocataire.getNom(), nouveauLocataire.getPrenom()});
										
					this.fen_ajouter_cautionnaire.dispose();
					this.fen_ajouter_cautionnaire.getFenPrecedente().dispose();	
					this.fen_ajouter_bail.getFenPrecedente().dispose();
	
					break;
				
		}
	}
	
	private int convertirStrToInt(String str) {
		if (!str.isEmpty() && !str.isBlank() && str!=null) {
			return Integer.parseInt(str); 
		}
		return 0;
	}
	
	private boolean estAdresseExistante(Adresse adresse) {
		boolean res = false;
		try {
			res = daoAdresse.findById(adresse.getIdAdresse())!= null;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void actualiserPartsLoyer(JTable tablePartsLoyer, Bail bail) {
		
		for (int i = 0; i < tablePartsLoyer.getRowCount()-2; i++) {
			try {
				Locataire loc = daoLocataire.findById((String) tablePartsLoyer.getValueAt(i, 0));
				List<Contracter> contrats = loc.getContrats(); 
				for (Contracter contrat : contrats) {
					if (contrat.getBail().getIdBail().equals(bail.getIdBail())) {
						contrat.modifierPartLoyer((float) tablePartsLoyer.getValueAt(i, 1));
						daoLocataire.update(loc);
					}
				}
				System.out.println(loc.getContrats());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
