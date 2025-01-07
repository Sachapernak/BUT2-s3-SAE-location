package controleur;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.SwingWorker;

import modele.DocumentComptable;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLocatif;
import modele.dao.DaoDocumentComptable;
import vue.AfficherCharges;


public class GestionAfficherCharge {
	
	private AfficherCharges frame;
	
	public GestionAfficherCharge(AfficherCharges frame) {
		this.frame = frame;
	}
	
	public void chargerComboBoxBatiment() {
	    // Afficher un curseur d'attente
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    
	    new SwingWorker<List<String>, Void>() {
	        @Override
	        protected List<String> doInBackground() throws Exception {
	            
	        	return new DaoBatiment().findAll().stream()
									              .map(e -> e.getIdBat())
									              .collect(Collectors.toList());
	        }

	        @Override
	        protected void done() {
	            try {
	                List<String> noms = get();
	                frame.setListBatiment(noms);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                frame.afficherMessageErreur("Opération interrompue.");
	            } catch (ExecutionException e) {
	                Throwable cause = e.getCause();
	                if (cause instanceof SQLException || cause instanceof IOException) {
	                    frame.afficherMessageErreur(cause.getMessage());
	                } else {
	                    frame.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
	                }
	            } finally {
	                // Réinitialiser le curseur
	                frame.setCursor(Cursor.getDefaultCursor());
	                chargerComboBoxLogement();
	            }
	        }
	    }.execute();
	    
	    
	}
	
	public void chargerComboBoxLogement() {
		String idBat = frame.getSelectBatiment();
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    
	    new SwingWorker<List<String>, Void>() {
	        @Override
	        protected List<String> doInBackground() throws Exception {
	        	return new DaoBienLocatif().findByIdBat(idBat).stream()
										        			.map(e -> e.getIdentifiantLogement())
										        			.collect(Collectors.toList());
	        }

	        @Override
	        protected void done() {
	            try {
	                List<String> noms = get();
	                frame.setListLogement(noms);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                frame.afficherMessageErreur("Opération interrompue.");
	            } catch (ExecutionException e) {
	                Throwable cause = e.getCause();
	                if (cause instanceof SQLException || cause instanceof IOException) {
	                    frame.afficherMessageErreur(cause.getMessage());
	                } else {
	                    frame.afficherMessageErreur("Erreur inattendue : " + cause.getMessage());
	                }
	            } finally {
	                // Réinitialiser le curseur
	                frame.setCursor(Cursor.getDefaultCursor());
	                chargerTable();
	            }
	        }
	    }.execute();
	}

	public void gestionActionComboBat(JComboBox<String> combo) {
	    combo.addItemListener(new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent evt) {
	            if (evt.getStateChange() == ItemEvent.SELECTED) {
	                chargerComboBoxLogement();
	                chargerTable();
	            }
	        }
	    });
	}
	
	public void gestionActionComboLog(JComboBox<String> combo) {
	    combo.addItemListener(new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent evt) {
	            if (evt.getStateChange() == ItemEvent.SELECTED) {
	            	System.out.println("ok");
	                chargerTable();
	            }
	        }
	    });
	}
	
	// TODO finir
	public void chargerTable() {
		try {
			List<DocumentComptable> docs = new DaoDocumentComptable().findByIdLogement(frame.getSelectLogement());
			
			System.out.println(frame.getSelectLogement());
			
			frame.chargerTable(docs.stream()
							.peek(doc -> doc.toString())
				    		.map(doc -> new Object[] {
				            doc.getNumeroDoc(),
				            doc.getTypeDoc(),
				            doc.getMontant(),
				            doc.getDateDoc()
				        })
				        .collect(Collectors.toList()));
		} catch (SQLException | IOException e) {
			frame.afficherMessageErreur("Erreur : " + e.getMessage());
		}
	}

}
