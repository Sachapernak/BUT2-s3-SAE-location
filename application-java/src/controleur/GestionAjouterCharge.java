package controleur;

import java.awt.Cursor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingWorker;
import modele.Batiment;
import modele.dao.DaoAssurance;
import modele.dao.DaoBatiment;
import modele.dao.DaoChargeIndex;
import modele.dao.DaoEntreprise;
import modele.dao.DaoLocataire;
import vue.AjouterCharge;

public class GestionAjouterCharge {
    
    private AjouterCharge fen;
    
    public GestionAjouterCharge(AjouterCharge fen) {
        this.fen = fen;
    }
    
    public void chargerComboBox()  {
        try {
            chargerComboType();
            chargerComboEntreprise();
            chargerComboBat();
            chargerComboLoc();
            chargerComboAssurance();
            chargerComboIDCharge();
        } catch (Exception e) {
            fen.afficherMessageErreur(e.getMessage());
        }
    }
    
    private void chargerComboType() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxTypes(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerType = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Chargement des types
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxTypes(Arrays.asList("FACTURE", "FACTURE_CV", "FACTURE_CF", "DEVIS", "QUITTANCE"));
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerType.execute();
    }
    
    private void chargerComboEntreprise() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxEntreprise(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerEntr = new SwingWorker<Void, Void>() {
            private List<String> nomsEntr;
            @Override
            protected Void doInBackground() throws Exception {
                nomsEntr = new DaoEntreprise().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getSiret())
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxEntreprise(nomsEntr);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerEntr.execute();
    }
    
    private void chargerComboBat() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxBat(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerBat = new SwingWorker<Void, Void>() {
            private List<String> bats;
            @Override
            protected Void doInBackground() throws Exception {
                bats = new DaoBatiment().findAll().stream()
                    .map(Batiment::getIdBat)
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxBat(bats);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerBat.execute();
    }
    
    private void chargerComboLoc() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxLocataire(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerLoc = new SwingWorker<Void, Void>() {
            private List<String> locs;
            @Override
            protected Void doInBackground() throws Exception {
                locs = new DaoLocataire().findAll().stream()
                    .map(e -> e.getNom() + " " + e.getIdLocataire())
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxLocataire(locs);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerLoc.execute();
    }
    
    private void chargerComboAssurance() {
        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboBoxAssu(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerAssu = new SwingWorker<Void, Void>() {
            private List<String> assus;
            @Override
            protected Void doInBackground() throws Exception {
                assus = new DaoAssurance().findAll().stream()
                    .map(a -> a.getNumeroContrat() + " " + a.getAnneeContrat())
                    .collect(Collectors.toList());
                return null;
            }
            
            @Override
            protected void done() {
                fen.setComboBoxAssu(assus);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerAssu.execute();
    }
    
    private void chargerComboIDCharge() throws SQLException, IOException {

        fen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        fen.setComboIDCharge(Arrays.asList("Chargement..."));
        
        SwingWorker<Void, Void> workerAssu = new SwingWorker<Void, Void>() {
            private List<String> res;
            @Override
            protected Void doInBackground() throws Exception {
                res = new DaoChargeIndex().findAllDistinctId();
                return null;
            }
            
            @Override
            protected void done() {
            	fen.setComboIDCharge(res);
                fen.setCursor(Cursor.getDefaultCursor());
            }
        };
        workerAssu.execute();

    }
}
