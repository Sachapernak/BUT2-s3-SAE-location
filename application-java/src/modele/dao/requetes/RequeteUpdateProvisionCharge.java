package modele.dao.requetes;

import java.sql.Date;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.ProvisionCharge;

public class RequeteUpdateProvisionCharge extends Requete<ProvisionCharge>{

	@Override
	public String requete() {
		return "UPDATE SAE_PROVISION_CHARGE "
				+ "SET PROVISION_POUR_CHARGE = ? "
				+ "WHERE ID_BAIL = ? "
				+ "and DATE_CHANGEMENT = ? ";
	}
	
	
	@Override
	public void parametres(PreparedStatement prSt, ProvisionCharge donnees) throws SQLException {
		
			
			
        // Définir le formatter pour le format DD/MM/YY
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(donnees.getDateChangement(), formatter);
        prSt.setBigDecimal(1, donnees.getProvisionPourCharge());
		prSt.setString(2, donnees.getIdBail());
		prSt.setDate(3, Date.valueOf(localDateDebut));
		
	
		
	}
	
	
}
