package modele.dao.requetes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.Bail;

public class RequeteUpdateBail extends Requete<Bail>{

	@Override
	public String requete() {
		return "UPDATE SAE_Bail "
				+ "SET DATE_DE_DEBUT = ? , DATE_DE_FIN = ? , IDENTIFIANT_LOGEMENT = ? "
				+ "WHERE ID_BAIL = ? ";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, Bail donnee) throws SQLException {
		

        // Définir le formatter pour le format DD/MM/YY
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnee.getDateDeDebut(), formatter);
        
        prSt.setDate(1,Date.valueOf(localDateDebut) );
        prSt.setDate(2, null);
        if (donnee.getDateDeFin() != null) {
        	LocalDate localDateFin = LocalDate.parse(donnee.getDateDeFin(), formatter);
        	prSt.setDate(2, Date.valueOf(localDateFin));
        }
		
        prSt.setString(3, donnee.getBien().getIdentifiantLogement());
		prSt.setString(4, donnee.getIdBail());
		
	}
}
