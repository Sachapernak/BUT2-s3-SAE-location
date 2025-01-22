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
import modele.Loyer;
import modele.ProvisionCharge;
import modele.dao.DaoAdresse;
import modele.dao.DaoBail;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoCautionnaire;
import modele.dao.DaoCautionner;
import modele.dao.DaoLocataire;
import modele.dao.DaoProvisionCharge;
import vue.AfficherLocatairesActuels;
import vue.AjouterBail;
import vue.AjouterCautionnaire;
import vue.AjouterLocataire;

/**
 * Contrôleur gérant l'ajout d'un cautionnaire dans le processus d'ajout
 * ou de modification d'un bail/locataire. 
 * 
 * Cette classe interagit avec la vue AjouterCautionnaire et met à jour le
 * modèle (DAOs, entités) en fonction des actions de l'utilisateur.
 */
public class GestionAjouterCautionnaire implements ActionListener {

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
	private DaoProvisionCharge daoProvision;

	private VerificationChamps verifChamps;

	private float partLoyer;
	private String dateDebut;

	/**
	 * Constructeur principal du contrôleur, injecte les vues et instancie les DAO.
	 */
	public GestionAjouterCautionnaire(
			AjouterCautionnaire ac, 
			AjouterLocataire al, 
			AfficherLocatairesActuels afl,
			AjouterBail ab
	) {
		this.fenAjouterCautionnaire = ac;
		this.fenAjouterLocataire = al;
		this.fenAfficherLocataires = afl;
		this.fenAjouterBail = ab;

		// Instanciation des DAO
		this.daoBail = new DaoBail();
		this.daoBien = new DaoBienLocatif();
		this.daoCautionnaire = new DaoCautionnaire();
		this.daoCautionner = new DaoCautionner();
		this.daoLocataire = new DaoLocataire();
		this.daoAdresse = new DaoAdresse();
		this.daoProvision = new DaoProvisionCharge();

		// Service de vérification des champs
		this.verifChamps = new VerificationChamps();
	}

	public void setPartLoyer(float partLoyer) {
		this.partLoyer = partLoyer;
	}

	/**
	 * Méthode déclenchée par les actions sur la vue. On décompose selon le bouton cliqué.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnActif = (JButton) e.getSource();
		String btnLibelle = btnActif.getText();

		switch (btnLibelle) {
			case "Annuler":
				handleAnnuler();
				break;
				
			case "Remplir avec le locataire":
				handleRemplirAvecLocataire();
				break;
				
			case "Poursuivre sans cautionnaire →":
				handlePoursuivreSansCautionnaire();
				break;
				
			case "Valider":
				handleValider();
				break;
				
			default:
				// Par défaut, aucune action supplémentaire
				break;
		}
	}

	// --------------------------------------------------------------------
	//                 Méthodes privées de gestion d'actions
	// --------------------------------------------------------------------

	/**
	 * Gère l’action d’annulation dans la vue AjouterCautionnaire.
	 */
	private void handleAnnuler() {
		this.fenAjouterCautionnaire.dispose();
	}

	/**
	 * Remplit les champs du cautionnaire avec ceux du locataire actuel.
	 */
	private void handleRemplirAvecLocataire() {
		remplirAdresseAvecLocataire();
	}

	/**
	 * Gère la poursuite du processus sans ajout de cautionnaire.
	 * On crée le locataire, son bail (le cas échéant) et on met à jour l’affichage.
	 */
	private void handlePoursuivreSansCautionnaire() {
		JTable tableLocataire = this.fenAfficherLocataires.getTableLocatairesActuels();
		Locataire newLocataire;
		Adresse adresseLoc;
		Bail bailLoc;

		// La part de loyer est de 1 pour un locataire sans cautionnaire
		this.partLoyer = 1F;
		
		newLocataire = recupererInfosLocataire();
		adresseLoc = newLocataire.getAdresse();
		bailLoc = recupererBail();
		
		if (bailLoc == null) {
			return;
		}

		// Contrat
		Contracter ctrLoc = new Contracter(newLocataire, bailLoc, bailLoc.getDateDeDebut(), this.partLoyer);
		newLocataire.getContrats().add(ctrLoc);

		// Création en base du bail s’il est nouveau
		creationBail(bailLoc);

		// Persistance du locataire et de son adresse
		try {
			if (adresseLoc != null && !estAdresseExistante(adresseLoc)) {
				daoAdresse.create(adresseLoc);
			}
			daoLocataire.create(newLocataire);
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}

		// Mise à jour de la table
		DefaultTableModel modelTableLocataire = (DefaultTableModel) tableLocataire.getModel();
		modelTableLocataire.addRow(new String[] { 
				newLocataire.getIdLocataire(), 
				newLocataire.getNom(),
				newLocataire.getPrenom() 
		});

		// Fermetures successives des fenêtres
		this.fenAjouterCautionnaire.dispose();
		this.fenAjouterCautionnaire.getFenPrecedente().dispose();
		this.fenAjouterBail.getFenPrecedente().dispose();
	}

	/**
	 * Gère la validation lorsqu’un cautionnaire est saisi.
	 * Effectue les contrôles sur les champs, la création des entités 
	 * (bail, locataire, cautionnaire) et leur insertion en base.
	 */
	private void handleValider() {
		// Vérifie la complétude des champs obligatoires
		if (!this.verifChamps.champsRemplis(this.fenAjouterCautionnaire.getChampsObligatoires())) {
			this.fenAjouterCautionnaire.afficherMessageErreur(
				"Tous les champs obligatoires pour saisir un cautionnaire ne sont pas remplis.\n"
				+ "Les champs obligatoires sont indiqués par *"
			);
			return;
		}

		// Vérifie la cohérence des données (formats, montants, etc.)
		if (gererErreur()) {
			// Si gererErreur() renvoie true, on a rencontré un problème
			return;
		}

		// Récupération table et entités
		JTable tableLoc = this.fenAfficherLocataires.getTableLocatairesActuels();
		Locataire nouveauLocataire = recupererInfosLocataire();
		Adresse adresseLocataire = nouveauLocataire.getAdresse();
		Bail bail = recupererBail();
		
		// Récupération du cautionnaire et de la relation cautionner
		Cautionnaire cautionnaire = recupInfosCautionnaire();
		Adresse adresseCautionnaire = cautionnaire.getAdresse();
		Cautionner cautionner = recupererInfosCautionner(bail, cautionnaire);

		// Création du contrat
		Contracter ctr = new Contracter(nouveauLocataire, bail, dateDebut, this.partLoyer);
		nouveauLocataire.getContrats().add(ctr);

		// Création en base du bail s’il est nouveau
		creationBail(bail);
		
		//Recupération et creation de la provision pour charge lors de l'ajout d'un nouveau bail

		// Persistance locataire, cautionnaire, etc.
		try {
			if (adresseLocataire != null && !estAdresseExistante(adresseLocataire)) {
				daoAdresse.create(adresseLocataire);
			}
			if (adresseCautionnaire != null && !estAdresseExistante(adresseCautionnaire)) {
				daoAdresse.create(adresseCautionnaire);
			}
			daoLocataire.create(nouveauLocataire);
			daoCautionnaire.create(cautionnaire);
			daoCautionner.create(cautionner);

			
			// Mise à jour de la vue
			DefaultTableModel modelTableLoc = (DefaultTableModel) tableLoc.getModel();
			modelTableLoc.addRow(new String[] { 
					nouveauLocataire.getIdLocataire(), 
					nouveauLocataire.getNom(),
					nouveauLocataire.getPrenom() 
			});

			// Fermeture des fenêtres
			this.fenAjouterCautionnaire.dispose();
			this.fenAjouterCautionnaire.getFenPrecedente().dispose();
			this.fenAjouterBail.getFenPrecedente().dispose();
		
		
		} catch (SQLException | IOException e1) {
			fenAjouterCautionnaire.afficherMessageErreur(e1.getMessage());
			e1.printStackTrace();
		}
		
	}

	// --------------------------------------------------------------------
	//                    Méthodes privées utilitaires
	// --------------------------------------------------------------------

	/**
	 * Gère les éventuelles erreurs de format, de champ obligatoire, etc.
	 * @return true si une erreur est trouvée, false sinon
	 */
	private boolean gererErreur() {
		boolean erreurTrouvee = false;

		String idCautionnaire = this.fenAjouterCautionnaire.getTextFieldIdentifiantCautionnaire();
		String montantStr = this.fenAjouterCautionnaire.getTextFieldMontant();

		// Vérification format de l’identifiant
		if (!this.verifChamps.validerInteger(idCautionnaire)) {
			this.fenAjouterCautionnaire.afficherMessageErreur(
					"L'identifiant du cautionnaire doit être un entier");
			erreurTrouvee = true;
		}

		// Vérification montant de la caution
		if (!this.verifChamps.validerMontant(montantStr, recupererMontantLoyer())) {
			this.fenAjouterCautionnaire.afficherMessageErreur(
					"Le montant de la caution doit être positif et ne peut excéder le triple du loyer");
			erreurTrouvee = true;
		}

		// Vérification adresse si au moins un champ est rempli
		List<String> champsObligatoiresAdr = this.fenAjouterCautionnaire.getChampsObligatoiresAdresse();
		if (this.verifChamps.auMoinsUnChampRempli(champsObligatoiresAdr)) {
			// Vérification format codePostal
			String codePostal = this.fenAjouterCautionnaire.getTextFieldCodePostal();
			if (!codePostal.isEmpty() && !this.verifChamps.validerCodePostal(codePostal)) {
				this.fenAjouterLocataire.afficherMessageErreur(
						"Le code postal doit être composé de 5 entiers");
				erreurTrouvee = true;
			}
			// Vérification que tous les champs obligatoires sont saisis
			if (!this.verifChamps.champsRemplis(champsObligatoiresAdr)) {
				this.fenAjouterCautionnaire.afficherMessageErreur("""
						Si vous voulez saisir une adresse,
						vous devez impérativement saisir l'ensemble des champs obligatoires
						à l'exception du complément.
						""");
				erreurTrouvee = true;
			}
		}

		return erreurTrouvee;
	}

	/**
	 * Récupère le bail saisi ou sélectionné dans la vue AjouterBail.
	 * @return un objet Bail correspondant, ou null si problème
	 */
	private Bail recupererBail() {
		Bail bail = null;
		if (this.fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
			// Création d'un nouveau bail
			String idBail = this.fenAjouterBail.getTextIdBail();
			dateDebut = this.fenAjouterBail.getTextDateDebut();
			String dateFin = this.fenAjouterBail.getTextDateFin();
			try {
				BienLocatif bien = daoBien.findById(
						(String) this.fenAjouterBail.getComboBoxBiensLoc().getSelectedItem()
				);
				bail = new Bail(idBail, dateDebut, bien);
				if (dateFin.length() >= 1) {
					bail.setDateDeFin(dateFin);
				}
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}

		} else if (this.fenAjouterBail.getRdbtnBailExistant().isSelected()) {
			// Bail existant
			int selectedRow = this.fenAjouterBail.getTableBauxActuels().getSelectedRow();
			if (selectedRow != -1) {
				String bailSelectionne = (String) this.fenAjouterBail
						.getTableBauxActuels()
						.getValueAt(selectedRow, 0);
				dateDebut = this.fenAjouterBail.getTextDateDebut();

				JTable tableParts = this.fenAjouterBail.getTablePartsLoyer();
				this.setPartLoyer((float) tableParts.getValueAt(
						tableParts.getRowCount() - 2, 
						1
				));

				try {
					bail = daoBail.findById(bailSelectionne);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return bail;
	}
	

	/**
	 * Crée le bail en base de données si c'est un nouveau bail.
	 * Sinon, met à jour les parts de loyer des locataires déjà existants.
	 */
	public void creationBail(Bail bail) {
		
		if (this.fenAjouterBail.getRdbtnNouveauBail().isSelected()) {
			ProvisionCharge provisionCharge = recupererMontantProvisionPourCharges(bail.getIdBail());
			try {
				daoBail.create(bail);
				creationProvision(provisionCharge);
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		} else if (this.fenAjouterBail.getRdbtnBailExistant().isSelected()) {
			actualiserPartsLoyer(this.fenAjouterBail.getTablePartsLoyer(), bail);
		}
	}

	/**
	 * Recupere a partir des informations saisies la provision pour charge.
	 * @return provisionCharge : la provision pour charge associé au bail et au locataire
	 */
	public ProvisionCharge recupererMontantProvisionPourCharges(String idBail) {
		String montantProvisionStr =  this.fenAjouterBail.getTextCharges();
		BigDecimal montantProvision = new BigDecimal(montantProvisionStr);
		
		String dateProvisionStr = fenAjouterBail.getTextDateDebut();

		
		
		return new ProvisionCharge(idBail,dateProvisionStr, montantProvision );
	}
	
	/**
	 * Ajoute la provision pour charge à la base de données
	 */
	public void creationProvision(ProvisionCharge provision) {
		try {
			daoProvision.create(provision);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Récupère le montant du loyer actuel du bien.
	 * @return montant du loyer sous forme de String
	 */
	public String recupererMontantLoyer() {
		Bail bail = recupererBail();
		BigDecimal montant = new BigDecimal(0);
		List<Loyer> loyers;
		try {
			if (bail == null) {
				return String.valueOf(montant);
			}
			loyers = bail.getBien().getLoyers();
			if (loyers != null && !loyers.isEmpty()) {
				Loyer dernierLoyer = loyers.get(loyers.size() - 1);
				montant = dernierLoyer.getMontantLoyer();
			}else {
				montant = bail.getBien().getLoyerBase();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return String.valueOf(montant);
	}

	/**
	 * Récupère les informations du locataire depuis la vue AjouterLocataire.
	 * @return un objet Locataire initialisé
	 */
	private Locataire recupererInfosLocataire() {
		String idLoc = this.fenAjouterLocataire.getTextFieldIdLocataire();
		String nom = this.fenAjouterLocataire.getTextFieldNom();
		String prenom = this.fenAjouterLocataire.getTextFieldPrenom();

		String email = this.fenAjouterLocataire.getTextFieldEmail();
		String tel = this.fenAjouterLocataire.getTextFieldTel();

		String dateNaissance = this.fenAjouterLocataire.getTextFieldDateNaissance();
		String lieuNaissance = this.fenAjouterLocataire.getTextFieldLieuNaissance();

		Locataire nouveauLocataire = new Locataire(idLoc, nom, prenom, dateNaissance);
		if (!email.isEmpty()) {
			nouveauLocataire.setEmail(email);
		}
		if (!tel.isEmpty()) {
			nouveauLocataire.setTelephone(tel);
		}
		nouveauLocataire.setLieuDeNaissance(lieuNaissance);

		Adresse adresseLoc = recupererInfosAdresseLocataire();
		if (adresseLoc != null) {
			nouveauLocataire.setAdresse(adresseLoc);
		}
		return nouveauLocataire;
	}

	/**
	 * Récupère les informations d'adresse du locataire depuis la vue AjouterLocataire.
	 * @return un objet Adresse initialisé ou null si champs non remplis
	 */
	private Adresse recupererInfosAdresseLocataire() {
		if (this.verifChamps.champsRemplis(this.fenAjouterLocataire.getChampsObligatoiresAdresse())) {
			String idAdresse = this.fenAjouterLocataire.getTextFieldIdAdresse();
			String adresse = this.fenAjouterLocataire.getTextFieldAdresse();
			String complement = this.fenAjouterLocataire.getTextComplement();
			String codePostalStr = this.fenAjouterLocataire.getTextFieldCodePostal();
			int codePostal = convertirStrToInt(codePostalStr);
			String ville = this.fenAjouterLocataire.getTextFieldVille();

			return new Adresse(idAdresse, adresse, codePostal, ville, complement);
		}
		return null;
	}

	/**
	 * Récupère les informations du cautionnaire depuis la vue AjouterCautionnaire.
	 * @return un objet Cautionnaire initialisé
	 */
	private Cautionnaire recupInfosCautionnaire() {
		int idC = convertirStrToInt(this.fenAjouterCautionnaire.getTextFieldIdentifiantCautionnaire());
		String nomOuOrgaC = this.fenAjouterCautionnaire.getTextFieldNomOuOrga();
		String prenomC = this.fenAjouterCautionnaire.getTextFieldPrenom();
		String descriptionC = this.fenAjouterCautionnaire.getTextFieldDescription();

		Cautionnaire cautionnaire = new Cautionnaire(idC, nomOuOrgaC, prenomC, descriptionC);

		Adresse adresseCautionnaire = recupererInfosAdresseCautionnaire();
		if (adresseCautionnaire != null) {
			cautionnaire.setAdresse(adresseCautionnaire);
		}

		return cautionnaire;
	}

	/**
	 * Récupère les informations d'adresse du cautionnaire depuis la vue AjouterCautionnaire.
	 * @return un objet Adresse ou null si pas de champs obligatoires remplis
	 */
	private Adresse recupererInfosAdresseCautionnaire() {
		if (this.verifChamps.champsRemplis(this.fenAjouterCautionnaire.getChampsObligatoiresAdresse())) {
			String idAdrC = this.fenAjouterCautionnaire.getTextFieldIdAdr();
			String adrC = this.fenAjouterCautionnaire.getTextFieldAdr();
			String complementC = this.fenAjouterCautionnaire.getTextFieldComplement();
			String codePostalStrC = this.fenAjouterCautionnaire.getTextFieldCodePostal();
			int codePostalC = convertirStrToInt(codePostalStrC);
			String villeC = this.fenAjouterCautionnaire.getTextFieldVille();

			return new Adresse(idAdrC, adrC, codePostalC, villeC, complementC);
		}
		return null;
	}

	/**
	 * Récupère les informations pour instancier un objet Cautionner (relation).
	 * @param bail le bail concerné
	 * @param cautionnaire la personne/organisme caution
	 * @return un objet Cautionner initialisé
	 */
	private Cautionner recupererInfosCautionner(Bail bail, Cautionnaire cautionnaire) {
		String montantStr = this.fenAjouterCautionnaire.getTextFieldMontant();
		BigDecimal montant = new BigDecimal(montantStr);
		String lienActeCaution = this.fenAjouterCautionnaire.getTextFieldLienActeCaution();

		return new Cautionner(montant, lienActeCaution, bail, cautionnaire);
	}

	/**
	 * Remplit les champs du cautionnaire avec ceux du locataire déjà saisis.
	 */
	private void remplirAdresseAvecLocataire() {
		String nomL = this.fenAjouterLocataire.getTextFieldNom();
		String prenomL = this.fenAjouterLocataire.getTextFieldPrenom();
		String adresseL = this.fenAjouterLocataire.getTextFieldAdresse();
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

	/**
	 * Convertit une chaîne en entier, renvoie 0 si vide.
	 */
	private int convertirStrToInt(String str) {
		if (str == null || str.isBlank()) {
			return 0;
		}
		return Integer.parseInt(str);
	}

	/**
	 * Vérifie si l'adresse existe déjà en base de données.
	 */
	private boolean estAdresseExistante(Adresse adresse) {
		boolean res = false;
		try {
			res = daoAdresse.findById(adresse.getIdAdresse()) != null;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Actualise les parts de loyer en base pour les locataires d’un bail existant.
	 */
	private void actualiserPartsLoyer(JTable tablePartsLoyer, Bail bail) {
		// On exclut les deux dernières lignes du tableau (souvent destinées aux totaux, etc.)
		for (int i = 0; i < tablePartsLoyer.getRowCount() - 2; i++) {
			try {
				Locataire loc = daoLocataire.findById(
						(String) tablePartsLoyer.getValueAt(i, 0)
				);
				List<Contracter> contrats = loc.getContrats();
				for (Contracter contrat : contrats) {
					if (contrat.getBail().getIdBail().equals(bail.getIdBail())) {
						contrat.modifierPartLoyer(
								(float) tablePartsLoyer.getValueAt(i, 1)
						);
						daoLocataire.update(loc);
					}
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
