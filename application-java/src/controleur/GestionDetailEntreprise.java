package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.Entreprise;
import vue.DetailEntreprise;

public class GestionDetailEntreprise {
	
	private DetailEntreprise fen;
	private Entreprise ent;
	
	public GestionDetailEntreprise(DetailEntreprise fen, Entreprise ent) {
		this.fen = fen;
		this.ent = ent;
	}
	
	public void chargeDonnee() {
		
		fen.setSiret(ent.getSiret());
		fen.setNom(ent.getNom());
		fen.setSecteur(ent.getSecteur());
		
		fen.setTextAdresse(ent.getAdresse().toString());
	}
	
	public void gestionAnnuler(JButton cancelButton) {

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fen.dispose();
			}
		});
	}
}
