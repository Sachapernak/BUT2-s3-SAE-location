package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.Adresse;
import modele.Bail;
import modele.BienLocatif;
import modele.Cautionnaire;
import modele.Cautionner;
import modele.Contracter;
import modele.Locataire;
import modele.dao.DaoAdresse;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoCautionnaire;
import modele.dao.DaoCautionner;
import modele.dao.DaoLocataire;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

public class GestionAjouterCautionnaire implements ActionListener{
	
	private AjouterCautionnaire fenAjouterCautionnaire;
	private AfficherLocatairesActuels fenAfficherLocataires;
	private AjouterLocataire fenAjouterLocataire;
	private AjouterBail fenAjouterBail;
	
	private DaoLocataire daoLocataire;
	private DaoCautionnaire daoCautionnaire;
	private DaoCautionner daoCautionner;
	private DaoAdresse daoAdresse;
	private DaoBail daoBail;
	private DaoBienLocatif daoBien;
	
	private VerificationFormatChamps formatCorrect;
	
	public GestionAjouterCautionnaire(AjouterCautionnaire ac, AjouterLocataire al, AfficherLocatairesActuels afl, AjouterBail ab) {
		this.fenAjouterCautionnaire = ac;
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		this.fenAjouterBail = ab;
		
		this.daoBail = new DaoBail();
		this.daoBien = new DaoBienLocatif();
		this.daoCautionnaire = new DaoCautionnaire();
		this.daoCautionner = new DaoCautionner();
		this.daoLocataire = new DaoLocataire();
		this.daoAdresse = new DaoAdresse();
		
		this.formatCorrect= new VerificationFormatChamps();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    	JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();
		
		switch (btnLibelle) {
			case "Annuler" :
				this.fenAjouterCautionnaire.dispose();
				break;
			case "Remplir avec le locataire": 
				
				remplirAdresseAvecLocataire();
				
				break;
			case "Valider" : 
				if(!champsRemplis(this.fenAjouterCautionnaire.getChampsObligatoires())) {
					this.fenAjouterCautionnaire.afficherMessageErreur("Tous les champs obligatoires ne sont pas remplis. \n Les champs obligatoires sont indiqués par *");
				} else {
					//verifier champs cautionnaire
					String idCautionnaire = this.fenAjouterCautionnaire.getTextFieldIdentifiantCautionnaire();
					String montantStr = this.fenAjouterCautionnaire.getTextFieldMontant();
					String codePostalStr = this.fenAjouterCautionnaire.getTextFieldCodePostal();
					if (!this.formatCorrect.validerInteger(idCautionnaire)) {
						this.fenAjouterCautionnaire.afficherMessageErreur("L'identifiant du cuationnaire doit être un entier");
						break;
					}
					if (!this.formatCorrect.validerMontant(montantStr)) {
						this.fenAjouterCautionnaire.afficherMessageErreur("Le montant de la caution doit être positif");
						break;
					}
					if (!codePostalStr.equals("")) {
						if (!this.formatCorrect.validerCodePostal(codePostalStr)){
							this.fenAjouterCautionnaire.afficherMessageErreur("Le code postal doit être composé de 5 entiers");
							break;
						}
					}
					
					JTable tableLoc = this.fenAfficherLocataires.getTableLocatairesActuels();
					Locataire nouveauLocataire;
					Cautionnaire cautionnaire;
					Cautionner cautionner;
					Adresse adresseLocataire;
					Adresse adresseCautionnaire;
					Bail bail = null; 
					String dateDebut = null;
					float partLoyer = 1F;
					
					
					//Recup infos locataire
					nouveauLocataire = recupererInfosLocataire();
					adresseLocataire = nouveauLocataire.getAdresse();
						
					//Recup bail
				    if (this.fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
			
					        String idBail = this.fenAjouterBail.getTextFieldIdBail();
					        dateDebut = this.fenAjouterBail.getTextFieldDateDebut();
					        String dateFin = this.fenAjouterBail.getTextFieldDateFin();
					        
					        if (!formatCorrect.validerDate(dateDebut)||!formatCorrect.validerDate(dateFin)) {
					        	this.fenAjouterCautionnaire.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
					        }
					        
					        BienLocatif bien;
						try {
							bien = daoBien.findById((String) this.fenAjouterBail.getComboBoxBiensLoc().getSelectedItem());
							bail = new Bail(idBail, dateDebut, bien);
						    if (dateFin.length()>= 1) {
						    	bail.setDateDeFin(dateFin);
						    }
							daoBail.create(bail);
						} catch (SQLException | IOException e1) {
							e1.printStackTrace();
						}      
				        
				    } 
				    else if (this.fenAjouterBail.getRdbtnBailExistant().isSelected()) {
				        // Récupérez la sélection du tableau des baux existants
				        int selectedRow = this.fenAjouterBail.getTableBauxActuels().getSelectedRow();
				        if (selectedRow != -1) {
				            // Récupérez les informations à partir de la ligne sélectionnée
				            String bailSelectionne = (String) this.fenAjouterBail.getTableBauxActuels().getValueAt(selectedRow, 0);
				            dateDebut = this.fenAjouterBail.getTextFieldDateArrivee();

				            if (!formatCorrect.validerDate(dateDebut)) {
					        	this.fenAjouterCautionnaire.afficherMessageErreur("Les dates doivent être au format YYYY-MM-dd");
					        }
				            
				            JTable tableParts = this.fenAjouterBail.getTablePartsLoyer();
				            partLoyer = (float) tableParts.getValueAt(tableParts.getRowCount()-2,1);				            		            			            
							try {
								bail = daoBail.findById(bailSelectionne);
							} catch (SQLException | IOException e1) {
								e1.printStackTrace();
							}
							actualiserPartsLoyer(this.fenAjouterBail.getTablePartsLoyer(),bail);
				        } 
				    }
					   
				    //Recup cautionnaire
				    cautionnaire = recupInfosCautionnaire();
					adresseCautionnaire = cautionnaire.getAdresse();
					
					//Recup cautionner 
					cautionner = recupererInfosCautionner(bail,cautionnaire);
					
					Contracter ctr = new Contracter(nouveauLocataire, bail, dateDebut, partLoyer);
					nouveauLocataire.getContrats().add(ctr);
					
					try {
						if (adresseLocataire != null) {
							if (!estAdresseExistante(nouveauLocataire.getAdresse())) {
								daoAdresse.create(adresseLocataire);
							}
						}
						if (adresseCautionnaire != null) {
							if (!estAdresseExistante(adresseCautionnaire)) {
								daoAdresse.create(adresseCautionnaire);
							}		
						}				
						daoLocataire.create(nouveauLocataire);
						daoCautionnaire.create(cautionnaire);
						daoCautionner.create(cautionner);
						
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}					
					DefaultTableModel modelTableLoc = (DefaultTableModel) tableLoc.getModel();
					modelTableLoc.addRow(new String[] { nouveauLocataire.getIdLocataire(), nouveauLocataire.getNom(), nouveauLocataire.getPrenom()});
										
					this.fenAjouterCautionnaire.dispose();
					this.fenAjouterCautionnaire.getFenPrecedente().dispose();	
					this.fenAjouterBail.getFenPrecedente().dispose();
	
				}
					
				break;
				
		}
	}

	
	
	
	public boolean champsRemplis(List<String> champs) {
		boolean champsNonVides = true;
		for (String champ : champs) {
			if (champ.equals("")) {
				return false;
			}
		}
		return champsNonVides;
	}
	
	
	/*---------------------------------------------------------------------
		Recuperer les infos du locataire
	-----------------------------------------------------------------------*/

	private Locataire recupererInfosLocataire() {
		Locataire nouveauLocataire = null;
		
		String idLoc = this.fenAjouterLocataire.getTextFieldIdLocataire();
		String nom = this.fenAjouterLocataire.getTextFieldNom();
		String prenom = this.fenAjouterLocataire.getTextFieldPrenom();
		
		String email = this.fenAjouterLocataire.getTextFieldEmail();
		String tel = this.fenAjouterLocataire.getTextFieldTel();
				
		String date_naissance = this.fenAjouterLocataire.getTextFieldDateNaissance();
		String lieu_naissance = this.fenAjouterLocataire.getTextFieldLieuNaissance();
		
		nouveauLocataire = new Locataire(idLoc, nom, prenom, date_naissance);
		if (!email.equals("")) {
			nouveauLocataire.setEmail(email);
		}
		if (!tel.equals("")) {
			nouveauLocataire.setTelephone(tel);
		}
		nouveauLocataire.setLieuDeNaissance(lieu_naissance);
		
		Adresse adresseLoc = recupererInfosAdresseLocataire();
		if (adresseLoc!=null) {
			nouveauLocataire.setAdresse(adresseLoc);
		}
		
		return nouveauLocataire;
		
	}
	
	private Adresse recupererInfosAdresseLocataire() {
		Adresse adresseComplete = null;
		if (champsRemplis(this.fenAjouterLocataire.getChampsObligatoiresAdresse())) {
			String idAdresse = this.fenAjouterLocataire.getTextFieldAdr();
			String adresse = this.fenAjouterLocataire.getTextFieldAdr();
			String complement = this.fenAjouterLocataire.getTextComplement();
			String codePostalStr = this.fenAjouterLocataire.getTextFieldCodePostal(); 
			int codePostal = convertirStrToInt(codePostalStr);
			String ville = this.fenAjouterLocataire.getTextFieldVille();
			
			adresseComplete = new Adresse(idAdresse, adresse, codePostal, ville, complement);
		}
		return adresseComplete;
		
	}
	
	/*---------------------------------------------------------------------
		Recuperer les infos du cautionnaire
	-----------------------------------------------------------------------*/
	
	private Cautionnaire recupInfosCautionnaire() {
		
		int idC = convertirStrToInt(this.fenAjouterCautionnaire.getTextFieldIdentifiantCautionnaire());
	    String nomOuOrgaC = this.fenAjouterCautionnaire.getTextFieldNomOuOrga();
		String prenomC = this.fenAjouterCautionnaire.getTextFieldPrenom();
		String descriptionC = this.fenAjouterCautionnaire.getTextFieldDescription();
		Cautionnaire cautionnaire = new Cautionnaire(idC, nomOuOrgaC,prenomC, descriptionC);
		
		Adresse adresseCautionnaire = recupererInfosAdresseCautionnaire();
		if (adresseCautionnaire!=null) {
			cautionnaire.setAdresse(adresseCautionnaire);
		}
		
		cautionnaire.setAdresse(adresseCautionnaire);
		
		return cautionnaire;
	}
	
	
	private Adresse recupererInfosAdresseCautionnaire() {
		Adresse adresseComplete = null;
		if (champsRemplis(this.fenAjouterCautionnaire.getChampsObligatoiresAdresse())) {
			String idAdrC = this.fenAjouterCautionnaire.getTextFieldIdAdr();
			String adrC = this.fenAjouterCautionnaire.getTextFieldAdr();
			String complementC = this.fenAjouterCautionnaire.getTextFieldComplement();
			String codePostalStrC = this.fenAjouterCautionnaire.getTextFieldCodePostal();
			
			//ICI
		
			
			int codePostalC = convertirStrToInt(codePostalStrC);
			String villeC = this.fenAjouterCautionnaire.getTextFieldVille();	
			
			adresseComplete = new Adresse(idAdrC, adrC, codePostalC, villeC, complementC);
		}
		return adresseComplete;
	}
	
	private Cautionner recupererInfosCautionner(Bail bail, Cautionnaire cautionnaire) {
		String montantStr = this.fenAjouterCautionnaire.getTextFieldMontant();
		//ICI
		BigDecimal montant = new BigDecimal(montantStr);
		String lienActeCaution = this.fenAjouterCautionnaire.getTextFieldLienActeCaution();
		
		return new Cautionner(montant, lienActeCaution, bail, cautionnaire);
		
	}

	
	/*---------------------------------------------------------------------
								Autre
	-----------------------------------------------------------------------*/
	
	
	private void remplirAdresseAvecLocataire() {
		String nomL = this.fenAjouterLocataire.getTextFieldNom();
		String prenomL = this.fenAjouterLocataire.getTextFieldPrenom();
		String adresseL = this.fenAjouterLocataire.getTextFieldAdr();
		String complementL = this.fenAjouterLocataire.getTextComplement();
		String codePostalL = this.fenAjouterLocataire.getTextFieldCodePostal();
		String villeL = this.fenAjouterLocataire.getTextFieldVille();
		
		this.fenAjouterCautionnaire.setTextFieldNomOuOrga(nomL);
		this.fenAjouterCautionnaire.setTextFieldPrenom(prenomL);
		this.fenAjouterCautionnaire.setTextFieldAdr(adresseL);
		this.fenAjouterCautionnaire.setTextFieldComplement(complementL);
		this.fenAjouterCautionnaire.setTextFieldCodePostal(codePostalL);
		this.fenAjouterCautionnaire.setTextFieldVille(villeL);
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
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
