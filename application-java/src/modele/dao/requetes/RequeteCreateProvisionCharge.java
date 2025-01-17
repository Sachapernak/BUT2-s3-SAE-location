package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.ProvisionCharge;


public class RequeteCreateProvisionCharge extends Requete<ProvisionCharge>{

	
	
	
	@Override
	public String requete() {
		return "INSERT INTO SAE_PROVISION_CHARGE(ID_BAIL,DATE_CHANGEMENT, PROVISION_POUR_CHARGE) "
				+ "VALUES ( ? , ? , ? )";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, ProvisionCharge donnees) throws SQLException {
		
		
		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateChangement(), formatter);
        
        prSt.setString(1, donnees.getIdBail());
		prSt.setDate(2, Date.valueOf(localDateDebut) );
		prSt.setBigDecimal(3, donnees.getProvisionPourCharge());
        
	}
	
}
