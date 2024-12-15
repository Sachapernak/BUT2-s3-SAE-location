package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Document;


public class RequeteCreateDocument extends Requete<Document>{

	
	
	
	@Override
	public String requete() {
		return "INSERT INTO SAE_DOCUMENT(ID_BAIL,DATE_DOCUMENT, TYPE_DE_DOCUMENT,URL_DOCUMENT) "
				+ "VALUES ( ? , ? , ? , ? )";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Document donnees) throws SQLException {
		
		
		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateDocument(), formatter);
        
        prSt.setString(1, donnees.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut) );
		prSt.setString(3, donnees.getTypeDocument());
		prSt.setString(4, donnees.getUrlDocument());
        
	}
	
}
