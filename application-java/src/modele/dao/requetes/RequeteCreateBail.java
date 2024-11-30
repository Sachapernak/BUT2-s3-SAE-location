package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import modele.Bail;

public class RequeteCreateBail extends Requete<Bail>{

	
	
	
	@Override
	public String requete() {
		return "INSERT INTO SAE_BAIL(ID_BAIL,DATE_DE_DEBUT, DATE_DE_FIN) "
				+ "VALUES ( ? , ? , ? )";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Bail donnee) throws SQLException {
		
		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnee.getDateDeDebut(), formatter);
        
        prSt.setString(1, donnee.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut) );
		prSt.setDate(3, null);
        if (donnee.getDateDeFin() != null) {
        	LocalDate localDateFin = LocalDate.parse(donnee.getDateDeFin(), formatter);
        	prSt.setDate(3, Date.valueOf(localDateFin));
        }
        

		
		
		
		
	}
	
}
