package modele.dao.requetes;

import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import oracle.jdbc.OracleConnection;

public class ProcedureCreateLocataire extends Procedure<Locataire> {

    @Override
    public String procedure() {
        return "{call pkg_locataire.create_locataire("
        		+ " p_identifiant_locataire => ?, "
        		+ " p_nom_locataire => ?, "
        		+ " p_prenom_locataire => ?, "
        		+ " p_email_locataire => ?, "
        		+ " p_telephone_locataire => ?, "
        		+ " p_date_naissance => ?, "
        		+ " p_lieu_de_naissance => ?, "
        		+ " p_acte_de_caution => ?, "
        		+ "   p_id_sae_adresse => ?, "
        		+ "        p_contrats => ? )}";
    }
    
    
    

    @Override
    public void parametres(CallableStatement prSt, Locataire donnee) throws SQLException {
        prSt.setString(1, donnee.getIdLocataire());
        prSt.setString(2, donnee.getNom());
        prSt.setString(3, donnee.getPrenom());
        prSt.setString(4, donnee.getEmail());
        prSt.setString(5, donnee.getTelephone());
        prSt.setDate(6,   Date.valueOf(donnee.getDateNaissance())); 
        prSt.setString(7, donnee.getLieuDeNaissance());
        prSt.setString(8, donnee.getActeDeCaution());
        prSt.setString(9, donnee.getAdresse() == null ? null : donnee.getAdresse().getIdAdresse());
         
        // Ajout des contrats
        ArrayList<Object[]> tabContrat = new ArrayList<>();
        
        
		for (Contracter c : donnee.getContrats()) {
			tabContrat.add(new Object[] {
					c.getLocataire().getIdLocataire(),
					c.getBail().getIdBail(),
					Date.valueOf(c.getDateEntree()),
					c.getDateSortie() == null ? null : Date.valueOf(c.getDateSortie()),
					c.getPartLoyer()
					}
			);
			
		}
		
	    Connection cn;
		
	    try {
			cn = ConnexionBD.getInstance().getConnexion();
			OracleConnection oracleCon = cn.unwrap(OracleConnection.class);
			Array array = oracleCon.createOracleArray("CONTRACT_ARRAY", tabContrat.toArray());
			prSt.setArray(10, array);
		} catch (IOException e) {
			e.printStackTrace();
			prSt.setArray(10, null);
		} 
    }
    
    
}
