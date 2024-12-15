package modele.dao.requetes;

import java.sql.Date;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Document;

public class RequeteUpdateDocument extends Requete<Document>{

	@Override
	public String requete() {
		return "UPDATE SAE_DOCUMENT "
				+ "SET TYPE_DE_DOCUMENT = ? , URL_DOCUMENT = ? "
				+ "WHERE ID_BAIL = ? "
				+ "and DATE_DOCUMENT = ? ";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Document donnees) throws SQLException {
		
			
			
        // Définir le formatter pour le format DD/MM/YY
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateDocument(), formatter);
        prSt.setString(1, donnees.getTypeDocument());
        prSt.setString(2, donnees.getUrlDocument());
		prSt.setString(3, donnees.getIdBail());
		prSt.setDate(4, Date.valueOf(localDateDebut));
		
	
		
	}
	
	
}
