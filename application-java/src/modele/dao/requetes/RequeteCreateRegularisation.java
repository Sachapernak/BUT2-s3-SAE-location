package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Regularisation;


public class RequeteCreateRegularisation extends Requete<Regularisation>{

	
	
	
	@Override
	public String requete() {
		return "INSERT INTO SAE_REGULARISATION(ID_BAIL,DATE_REGU, MONTANT) "
				+ "VALUES ( ? , ? , ? )";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Regularisation donnees) throws SQLException {
		
		
		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateRegu(), formatter);
        
        prSt.setString(1, donnees.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut) );
		prSt.setBigDecimal(3, donnees.getMontantRegu());
        
	}
	
}
