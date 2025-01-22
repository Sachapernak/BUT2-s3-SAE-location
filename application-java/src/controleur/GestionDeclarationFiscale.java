package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import modele.Batiment;
import modele.BienLocatif;
import modele.DocumentComptable;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import modele.dao.DaoLoyer;
import rapport.RapportDeclarationFiscale;
import vue.DeclarationFiscale;

public class GestionDeclarationFiscale implements  ActionListener {
	private DeclarationFiscale fenDeclarationFiscale;
	
	
	public GestionDeclarationFiscale(DeclarationFiscale df)  {
		this.fenDeclarationFiscale = df;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        JButton btnActif = (JButton) e.getSource();
        String btnLibelle = btnActif.getText();

        switch(btnLibelle) {
	        case "Retour" : 
	        	fenDeclarationFiscale.dispose();
	        	break;
	        case "Générer" : 
	        	try {
		        	new RapportDeclarationFiscale().genererRapportDeclarationFiscale(recupererDonnees(), 
		        			fenDeclarationFiscale.getAnnee(), fenDeclarationFiscale.getTextTotal(), 
		        			fenDeclarationFiscale.getAnnee() + "RECAPFISCAL.docx");
	        	} catch (IOException ex) {
	        		ex.printStackTrace();
	        		fenDeclarationFiscale.afficherMessageErreur(ex.getMessage());
	        	}

	        	break;
	        default : 
	        	break;
        	
        }
            	
        
    }
	
	/**
	 * Cette méthode gère l'action lorsque l'utilisateur sélectionne une année dans le JComboBox.
	 * Elle réagit à l'événement de sélection et remplit la table des bâtiments avec les données correspondantes.
	 * 
	 * @param comboBoxAnnee Le JComboBox de sélection des années.
	 */
	public void gestionActionComboBoxAnnee(JComboBox<String> comboBoxAnnee) {
        comboBoxAnnee.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                remplirTableBatiments();
            }
        });
    }
		
	/**
	 * Cette méthode remplit le JComboBox avec les années disponibles, de l'année actuelle jusqu'à 1950,
	 * en utilisant un SwingWorker pour ne pas bloquer l'interface utilisateur.
	 * Elle met le curseur en sablier pendant le traitement.
	 */
	public void remplirComboBoxAnnee() {
	    // Met la fenêtre en mode sablier avant de commencer l'opération
	    fenDeclarationFiscale.setWaitCursor();
	    
	    new SwingWorker<List<String>, Void>() {
	        @Override
	        protected List<String> doInBackground() {
	            List<String> annees = new ArrayList<>();
	            int anneeActuelle = LocalDate.now().getYear();
	            for (int i = anneeActuelle; i >= 1950; i--) {
	                annees.add(String.valueOf(i));
	            }
	            return annees;
	        }

	        @Override
	        protected void done() {
	            try {
	                List<String> annees = get();
	                fenDeclarationFiscale.setComboBoxAnnee(annees);
	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	                fenDeclarationFiscale.afficherMessageErreur(e.getMessage());
	            } finally {
	                fenDeclarationFiscale.setDefaultCursor();
	            }
	        }
	    }.execute();
	}

	
	/**
	 * Cette méthode remplit la table des bâtiments avec les données des bâtiments, des loyers encaissés, 
	 * des frais et des bénéfices en utilisant un SwingWorker pour ne pas bloquer l'interface utilisateur.
	 */
	public void remplirTableBatiments() {
	    // Met la fenêtre en mode sablier avant de commencer l'opération
	    fenDeclarationFiscale.setWaitCursor();

	    new SwingWorker<BatimentDataResult, Void>() {
	        @Override
	        protected BatimentDataResult doInBackground() {
	            List<String[]> rows = new ArrayList<>();
	            BigDecimal totalBenefices = BigDecimal.ZERO;

	            try {
	                List<Batiment> batiments = new DaoBatiment().findAll();
	                int annee = Integer.parseInt((String) fenDeclarationFiscale.getSelectedItemComboAnnee());

	                for (Batiment batiment : batiments) {
	                    BigDecimal totalLoyersBat = calculerTotalLoyers(batiment, annee);
	                    BigDecimal totalFraisBat  = calculerTotalFrais(batiment, annee);
	                    BigDecimal benefice = totalLoyersBat.subtract(totalFraisBat);

	                    totalBenefices = totalBenefices.add(benefice);

	                    rows.add(new String[]{
	                        batiment.getIdBat(),
	                        totalLoyersBat.toString(),
	                        totalFraisBat.toString(),
	                        benefice.toString()
	                    });
	                }
	            } catch (SQLException | IOException e) {
	                e.printStackTrace();
	                // Gestion des erreurs selon les besoins
	            }

	            return new BatimentDataResult(rows, totalBenefices);
	        }

	        @Override
	        protected void done() {
	            try {
	                BatimentDataResult result = get();

	                // Mise à jour de la table via la méthode de la vue
	                fenDeclarationFiscale.remplirTable(result.getRows());

	                // Mettre à jour le champ "Total Bénéfices"
	                fenDeclarationFiscale.setTextTotal(result.getTotalBenefices().toString());

	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	                fenDeclarationFiscale.afficherMessageErreur(
	                    "Erreur lors du remplissage des données : " + e.getMessage()
	                );
	            } finally {
	                fenDeclarationFiscale.setDefaultCursor();
	            }
	        }
	    }.execute();
	}

	
	/**
	 * Classe de résulat utilisé pour recuperer les resultat du swingWorker
	 */
	private static class BatimentDataResult {
	    private final List<String[]> rows;
	    private final BigDecimal totalBenefices;

	    public BatimentDataResult(List<String[]> rows, BigDecimal totalBenefices) {
	        this.rows = rows;
	        this.totalBenefices = totalBenefices;
	    }

	    public List<String[]> getRows() {
	        return rows;
	    }

	    public BigDecimal getTotalBenefices() {
	        return totalBenefices;
	    }
	}



	/**
	 * Cette méthode calcule le total des loyers pour un bâtiment donné pour une année spécifiée.
	 * Elle parcourt tous les logements du bâtiment et calcule les loyers pour chaque logement.
	 * 
	 * @param batiment Le bâtiment pour lequel calculer les loyers.
	 * @param annee    L'année pour laquelle les loyers doivent être calculés.
	 * @return Le total des loyers pour le bâtiment.
	 * @throws SQLException Si une erreur de base de données se produit.
	 */
    private BigDecimal calculerTotalLoyers(Batiment batiment, int annee) throws SQLException {
        BigDecimal totalLoyersBat = BigDecimal.ZERO;

        // Obtenir tous les logements d'un bâtiment
        List<BienLocatif> logements;
		try {
			logements = new DaoBienLocatif().findByIdBat(batiment.getIdBat());
		
	        for (BienLocatif logement : logements) {
	            totalLoyersBat = totalLoyersBat.add(calculerLoyersLogement(logement, annee));
	        }
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

        return totalLoyersBat;
    }

    /**
     * Cette méthode calcule les loyers totaux pour un logement donné sur une année spécifiée.
     * Elle parcourt les mois de l'année et calcule le loyer pour chaque mois.
     * 
     * @param logement Le logement pour lequel calculer les loyers.
     * @param annee    L'année pour laquelle les loyers doivent être calculés.
     * @return Le total des loyers pour le logement.
     * @throws SQLException Si une erreur de base de données se produit.
     */

    private BigDecimal calculerLoyersLogement(BienLocatif logement, int annee) throws SQLException {
        BigDecimal totalLoyersLog = BigDecimal.ZERO;
        String idLog = logement.getIdentifiantLogement();

        // Parcourir les 12 mois de l'année précédente
        LocalDate date = LocalDate.of(annee - 1, 5, 1);
        for (int i = 0; i < 12; i++) {
            LocalDate finMois = date.plusMonths(1).minusDays(1);

            // Vérifier si le logement est loué sur le mois
            try {
				if (!new DaoBienLocatif().estLoueEntre(idLog, date.toString(), finMois.toString()).equals(BigDecimal.ZERO)) {
				    totalLoyersLog = totalLoyersLog.add(new DaoLoyer().getDernierLoyer(idLog, finMois.toString()));
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}

            date = date.plusMonths(1);
        }

        return totalLoyersLog;
    }

    
    /**
     * Cette méthode calcule le total des frais pour un bâtiment donné.
     * Elle parcourt tous les logements du bâtiment et calcule les frais associés pour chaque logement.
     * 
     * @param batiment Le bâtiment pour lequel calculer les frais.
     * @return Le total des frais pour le bâtiment.
     * @throws SQLException Si une erreur de base de données se produit.
     * @throws IOException  Si une erreur d'entrée-sortie se produit.
     */
    private BigDecimal calculerTotalFrais(Batiment batiment, int annee) throws SQLException, IOException {
        BigDecimal totalFraisBat = BigDecimal.ZERO;
        String debutAnnee = LocalDate.of(annee - 1, 5, 1).toString();
        String finAnnee = LocalDate.of(annee, 4, 30).toString();

        // Obtenir tous les logements d'un bâtiment
        List<BienLocatif> logements = new DaoBienLocatif().findByIdBat(batiment.getIdBat());
        for (BienLocatif logement : logements) {
            // Calculer les frais pour chaque logement
            List<DocumentComptable> documents = new DaoDocumentComptable().findByIdLogement(logement.getIdentifiantLogement());
            for (DocumentComptable document : documents) {
                if (!document.isRecuperableLoc() && document.getDateDoc().compareTo(finAnnee) < 0 
                		&& document.getDateDoc().compareTo(debutAnnee) > 0) {
                    totalFraisBat = totalFraisBat.add(new DaoDocumentComptable().findMontantProrata(document, logement.getIdentifiantLogement()));
                }
            }
        }

        return totalFraisBat;
    }
    
    
    /**
     * Récupère les données de la table sous forme de liste de tableaux de chaînes de caractères.
     * 
     * Cette méthode parcourt chaque ligne et chaque colonne de la table, récupère les valeurs
     * de chaque cellule, les convertit en chaînes de caractères, et les stocke dans un tableau.
     * Chaque tableau est ensuite ajouté à une liste, qui est retournée en fin de méthode.
     *
     * @return List<String[]> Une liste contenant des tableaux de chaînes représentant les données de la table.
     */
    public List<String[]> recupererDonnees() {
        // Récupération du modèle et dimensions de la table une seule fois
        int nbLignes = this.fenDeclarationFiscale.getNbLignesTable();
        int nbColonnes = this.fenDeclarationFiscale.getColumnCount();
        List<String[]> ensembleData = new ArrayList<>(nbLignes);

        for (int i = 0; i < nbLignes; i++) {
            String[] ligneData = new String[nbColonnes];
            for (int j = 0; j < nbColonnes; j++) {
                Object value = this.fenDeclarationFiscale.getValueAt(i, j);
                ligneData[j] = (value != null) ? value.toString() : "";
            }
            ensembleData.add(ligneData);
        }

        return ensembleData;
    }



}
