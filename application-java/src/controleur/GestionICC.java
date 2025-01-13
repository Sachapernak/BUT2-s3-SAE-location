package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
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
					chargerDonnee();
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
					List<String> identifiants = extraireIdIcc(fen.getSelectedLine());
					ICC icc = new ICC(identifiants.get(0), identifiants.get(1), 0);
					new DaoICC().delete(icc);
					chargerDonnee();
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
	
	public List<String> extraireIdIcc(String ligne){
		List<String> res = new ArrayList<>();
        // Expression régulière pour capturer l'année et le numéro de trimestre
        Pattern pattern = Pattern.compile("(\\d{4})\\sT(\\d)");
        java.util.regex.Matcher matcher = pattern.matcher(ligne);

        if (matcher.find()) {
            String year = matcher.group(1);      
            String trim = matcher.group(2);    
            res.add(year);
            res.add(trim);
            
            return res;
            
        } else {
            return Collections.emptyList();
        }
	}
	
}
