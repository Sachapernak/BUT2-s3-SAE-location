package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Document;

public class RequeteDeleteDocument extends Requete<Document>{
	
	@Override
	public String requete() {
		return "Delete from SAE_DOCUMENT where ID_BAIL = ? "
				+ "and DATE_DOCUMENT = ? ";
				
	}
		
	@Override
	public void parametres(PreparedStatement prSt, Document donnees) throws SQLException {

		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateDocument(), formatter);
        
		
		prSt.setString(1, donnees.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut));
	}

}
