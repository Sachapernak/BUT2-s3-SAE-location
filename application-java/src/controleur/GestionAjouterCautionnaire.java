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
import modele.Locataire;
import modele.Loyer;
import modele.TypeDeBien;
import modele.dao.DaoAdresse;
import modele.dao.DaoBail;
import modele.dao.DaoBatiment;
import modele.dao.DaoCautionnaire;
import modele.dao.DaoCautionner;
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
	
	public GestionAjouterCautionnaire(AjouterCautionnaire ac, AjouterLocataire al, AfficherLocatairesActuels afl, AjouterBail ab) {
		this.fen_ajouter_cautionnaire = ac;
		this.fen_ajouter_locataire = al;
		this.fen_afficher_locataires = afl;
		this.fen_ajouter_bail = ab;
		
		this.daoBail = new DaoBail();
		this.daoCautionnaire = new DaoCautionnaire();
		this.daoCautionner = new DaoCautionner();
		this.daoLocataire = new DaoLocataire();
		this.daoAdresse = new DaoAdresse();
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
				String codePosalL = this.fen_ajouter_locataire.getTextFieldCodePostal().getText();
				String villeL = this.fen_ajouter_locataire.getTextFieldVille().getText();
				
				this.fen_ajouter_cautionnaire.getTextFieldNomOuOrga().setText(nomL);
				this.fen_ajouter_cautionnaire.getTextFieldPrenom().setText(prenomL);
				this.fen_ajouter_cautionnaire.getTextFieldAdr().setText(adresseL);
				this.fen_ajouter_cautionnaire.getTextFieldComplement().setText(complementL);
				this.fen_ajouter_cautionnaire.getTextFieldCodePostal().setText(codePosalL);
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
					
					
					//Recup infos locataire
					String idLoc = this.fen_ajouter_locataire.getTextFieldId().getText();
					String nom = this.fen_ajouter_locataire.getTextFieldNom().getText();
					String prenom = this.fen_ajouter_locataire.getTextFieldPrenom().getText();
					String email = this.fen_ajouter_locataire.getTextFieldEmail().getText();
					String tel = this.fen_ajouter_locataire.getTextFieldTel().getText();
					String date_naissance = this.fen_ajouter_locataire.getTextFieldDateNaissance().getText();
					String lieu_naissance = this.fen_ajouter_locataire.getTextFieldLieuNaissance().getText();
					String acte_caution = "a supprimer";
					
					//Recup adresse
					String adresse = this.fen_ajouter_locataire.getTextFieldAdr().getText();
					String complement = this.fen_ajouter_locataire.getTextFieldComplement().getText();
					int codePostal = convertirStrToInt(this.fen_ajouter_locataire.getTextFieldCodePostal().getText());
					String ville = this.fen_ajouter_locataire.getTextFieldVille().getText();
					
					adresseLocataire = new Adresse(adresse+codePostal+ville, adresse, codePostal, ville, complement);										
					nouveauLocataire = new Locataire(idLoc, nom, prenom, date_naissance);
					nouveauLocataire.setActeDeCaution(acte_caution);
					nouveauLocataire.setEmail(email);
					nouveauLocataire.setTelephone(tel);
					nouveauLocataire.setLieuDeNaissance(lieu_naissance);
					nouveauLocataire.setAdresse(adresseLocataire);
					
					//Recup bail
				    if (this.fen_ajouter_bail.getRdbtnNouveauBail().isSelected()) {
				    	System.out.println("je passe bien ici l18 GestionAjouterLoc");
				        String idBail = this.fen_ajouter_bail.getTextFieldIdBail().getText();
				        String dateDebut = this.fen_ajouter_bail.getTextFieldDateDebut().getText();
				        String dateFin = this.fen_ajouter_bail.getTextFieldDateFin().getText();
				        
				       Batiment bat=  new Batiment("Test", adresseLocataire);
				       BienLocatif bien = new BienLocatif("Test",TypeDeBien.LOGEMENT,20,2,new BigDecimal(200), bat);
				       bail = new Bail(idBail, dateDebut, bien);
				       if (dateFin.length()>= 1) {
				    	   bail.setDateDeFin(dateFin);
				       }
				       
				        
				    } 
				    else if (this.fen_ajouter_bail.getRdbtnBailExistant().isSelected()) {
				        // Récupérez la sélection du tableau des baux existants
				        int selectedRow = this.fen_ajouter_bail.getTableBauxActuels().getSelectedRow();
				        if (selectedRow != -1) {
				            // Récupérez les informations à partir de la ligne sélectionnée
				            String bailSelectionne = (String) this.fen_ajouter_bail.getTableBauxActuels().getValueAt(selectedRow, 0);
				            String dateArrivee = this.fen_ajouter_bail.getTextFieldDateArrivee().getText();  
				            
				            /* A GERER / 
				             * ...
				             */
							try {
								bail = daoBail.findById(bailSelectionne);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
				        } 
				    }
									    
				    //Recup cautionnaire
				    int idC = convertirStrToInt(this.fen_ajouter_cautionnaire.getTextFieldIdentifiantCautionnaire().getText());
				    String nomOuOrgaC = this.fen_ajouter_cautionnaire.getTextFieldNomOuOrga().getText();
					String prenomC = this.fen_ajouter_cautionnaire.getTextFieldPrenom().getText();
					String descriptionC = this.fen_ajouter_cautionnaire.getTextFieldDescription().getText();
					String adrC = this.fen_ajouter_cautionnaire.getTextFieldAdr().getText();
					String complementC = this.fen_ajouter_cautionnaire.getTextFieldComplement().getText();
					int codePostalC = convertirStrToInt(this.fen_ajouter_cautionnaire.getTextFieldCodePostal().getText());
					String villeC = this.fen_ajouter_cautionnaire.getTextFieldVille().getText();
					
					adresseCautionnaire = new Adresse(adrC+codePostalC+villeC, adrC, codePostalC, villeC, complementC);
					cautionnaire = new Cautionnaire(idC, nomOuOrgaC,prenomC, descriptionC, adresseCautionnaire);
					
					//Recup cautionner 
					String montantStr = this.fen_ajouter_cautionnaire.getTextFieldMontant().getText();
					BigDecimal montant = new BigDecimal(montantStr);
					String lienActeCaution = this.fen_ajouter_cautionnaire.getTextFieldLienActeCaution().getText();
					
					cautionner = new Cautionner(montant, lienActeCaution, bail, cautionnaire);
					
					try {
						
						daoAdresse.create(adresseLocataire);
						daoBail.create(bail);
						
						if (daoAdresse.findById(adresseLocataire.getIdAdresse())==null) {
							daoAdresse.create(adresseCautionnaire);
						}
						
						daoLocataire.create(nouveauLocataire);
						daoCautionnaire.create(cautionnaire);
						
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
		return Integer.parseInt(str); 
	}
	
}
