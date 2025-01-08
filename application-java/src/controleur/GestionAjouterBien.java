package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import vue.AjouterBien;
import modele.BienLocatif;
import modele.TypeDeBien;
import modele.Batiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoBatiment;

public class GestionAjouterBien implements ActionListener {

    private AjouterBien fen_ajouter_bien;
    private DaoBatiment db;
    private GestionTablesFenetrePrincipale gtfp;
    
    private String idBien;
    private String surfaceStr;
    private String nbPiecesStr;
    private String loyerBaseStr;
    private String identifiantFiscal;
    private String complementAdresse;

    public GestionAjouterBien(AjouterBien ab1, GestionTablesFenetrePrincipale gtfp) {
        this.fen_ajouter_bien = ab1;
        this.db = new DaoBatiment();
        this.gtfp = gtfp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btn = e.getSource();

        if (btn instanceof JButton) {
            JButton btnActif = (JButton) e.getSource();
            String btnLibelle = btnActif.getText();

            switch (btnLibelle) {
                case "Annuler":
                    this.fen_ajouter_bien.dispose();
                    break;
                case "Ajouter":
                    ajouterBien();
                    break;
            }
        }
    }

    private void ajouterBien() {
        try {
        	TypeDeBien typeBien = fen_ajouter_bien.getRdbtnLogement().isSelected() ? TypeDeBien.LOGEMENT : TypeDeBien.GARAGE;
        	
        	 
        	if (typeBien.getValeur() == "logement") {
        		// Récupération des valeurs des champs de texte
                idBien = fen_ajouter_bien.getTextFieldIdBienLog().getText();
                surfaceStr = fen_ajouter_bien.getTextFieldSurfaceLog().getText();
                nbPiecesStr = fen_ajouter_bien.getTextFieldNbPiecesLog().getText();
                loyerBaseStr = fen_ajouter_bien.getTextFieldLoyerBaseLog().getText();
                identifiantFiscal = fen_ajouter_bien.getTextFieldIdentifiantFiscalLog().getText();
                complementAdresse = fen_ajouter_bien.getTextFieldComplementAdresseLog().getText();
        	}else {
        		 idBien = fen_ajouter_bien.getTextFieldIdBienGar().getText();
                 surfaceStr = fen_ajouter_bien.getTextFieldSurfaceGar().getText();
                 loyerBaseStr = fen_ajouter_bien.getTextFieldLoyerBaseGar().getText();
                 identifiantFiscal = fen_ajouter_bien.getTextFieldIdentifiantFiscalGar().getText();
                 complementAdresse = fen_ajouter_bien.getTextFieldComplementAdresseGar().getText();
                 nbPiecesStr = "1";
        	}
            
            JTable tableBatiment = fen_ajouter_bien.getTableBat();
            int ligneSelect = tableBatiment.getSelectedRow();
    		
			String idBat = (String) tableBatiment.getValueAt(ligneSelect, 0);
            
            
            // Conversion des valeurs en types appropriés
            int surface = Integer.parseInt(surfaceStr);
            int nbPieces = nbPiecesStr.isEmpty() ? 0 : Integer.parseInt(nbPiecesStr);
            BigDecimal loyerBase = new BigDecimal(loyerBaseStr);

            // Détermination du type de bien
            
            Batiment bat = db.findById(idBat);

            // Création de l'objet BienLocatif
            BienLocatif bien = new BienLocatif(idBien, typeBien, surface, nbPieces, loyerBase, bat);
            bien.setIdFiscal(identifiantFiscal);
            bien.setComplementAdresse(complementAdresse);

            // Enregistrement du bien dans la base de données
            DaoBienLocatif daoBien = new DaoBienLocatif();
            daoBien.create(bien);
            
            gtfp.remplirBatiments(tableBatiment);
            JOptionPane.showMessageDialog(fen_ajouter_bien, "Bien ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            fen_ajouter_bien.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fen_ajouter_bien, "Erreur lors de l'ajout du bien : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
