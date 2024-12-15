package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Regularisation;

public class RequeteDeleteRegularisation extends Requete<Regularisation>{
	
	@Override
	public String requete() {
		return "Delete from SAE_REGULARISATION where ID_BAIL = ? "
				+ "and DATE_REGU = ? ";
				
	}
		
	@Override
	public void parametres(PreparedStatement prSt, Regularisation donnees) throws SQLException {

		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateRegu(), formatter);
        
		
		prSt.setString(1, donnees.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut));
	}

}
