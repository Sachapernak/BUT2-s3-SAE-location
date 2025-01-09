package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.swing.JButton;

import modele.ICC;
import modele.dao.DaoICC;
import vue.SetICC;

public class GestionICC {

	SetICC fen;
	public GestionICC(SetICC fen) {
		this.fen = fen;
	}
	
	public void chargerDonnee() {
		try {
			fen.setTableICC(new DaoICC().findAll().stream()
					.map(e -> e.getAnnee() + " T" + e.getTrimestre() + ": " + e.getIndiceICC())
					.collect(Collectors.toList()));
			
		} catch (Exception e) {
			fen.afficherMessageErreur(e.getMessage());
		}

	}
	
	public void gestionConfirmer(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					new DaoICC().create(new ICC(fen.getAnnee(), fen.getTrimestre(), Integer.valueOf(fen.getIndice())));
					fen.dispose();
				} catch (NumberFormatException | SQLException | IOException e1) {
					fen.afficherMessageErreur(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
	}

	public void gestionSupprimer(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new DaoICC().delete(new ICC(fen.getAnnee(), fen.getTrimestre(), 0));
					fen.dispose();
				} catch (NumberFormatException | SQLException | IOException e1) {
					fen.afficherMessageErreur(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
	}

	public void gestionAnnuler(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fen.dispose();
			}
		});
	}
	
}
