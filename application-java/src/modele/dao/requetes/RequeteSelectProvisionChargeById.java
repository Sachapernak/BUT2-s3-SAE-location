package modele.dao.requetes;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modele.ProvisionCharge;

public class RequeteSelectProvisionChargeById extends Requete<ProvisionCharge> {

	@Override
	public String requete() {
		return "select * from SAE_PROVISION_CHARGE "
				+ "where ID_Bail = ? "
				+ "and DATE_CHANGEMENT = ? ";
	}
	
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		
		// Définir le formatter pour le format DD/MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        
        // Convertir la chaîne en LocalDate
        LocalDate localDateDebut = LocalDate.parse(id[1], formatter);
        
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(localDateDebut));
	}
}
