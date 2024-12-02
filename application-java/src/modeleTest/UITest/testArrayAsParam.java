package modeleTest.UITest;

import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import modele.Bail;
import modele.ConnexionBD;
import modele.Contracter;
import modele.Locataire;
import oracle.jdbc.OracleConnection;

// Classe de test permettant de mieux comprendre la transmission de tableau dans PL/SQL
public class testArrayAsParam {
	
	public static void main(String[] args) throws SQLException, IOException {
		
		Connection cn = ConnexionBD.getInstance().getConnexion();
		
		deleteTestData();
		
		
		Bail bail = new Bail("BAI01");
		Bail bail2 = new Bail("BAI02");
		Bail bail3 = new Bail("BAI03");
		
		Locataire loca = new Locataire("idLocTest1000", "Didier", "Jean", "1999-10-11");

		Contracter contrat = new Contracter(loca, bail, "2001-01-01", 0.3f);
		contrat.ajouterDateSortie("2008-01-01");
		
		Contracter contrat2 = new Contracter(loca, bail2, "2002-01-01", 0.8f);
		contrat.ajouterDateSortie("2009-01-01");
		
		Contracter contrat3 = new Contracter(loca, bail3, "2003-01-01", 0.9f);
		contrat.ajouterDateSortie("2010-01-01");
		
		loca.getContrats().add(contrat);
		loca.getContrats().add(contrat2);
		loca.getContrats().add(contrat3);
		
		ArrayList<Object[]> tabContrat = new ArrayList<>();
		
		for (Contracter c : loca.getContrats()) {
			tabContrat.add(new Object[] {
					c.getLocataire().getIdLocataire(),
					c.getBail().getIdBail(),
					Date.valueOf(c.getDateEntree()),
					c.getDateSortie() == null ? null : Date.valueOf(c.getDateSortie()),
					c.getPartLoyer()
					}
			);
			
		}
		
	    
	    OracleConnection oracleCon = cn.unwrap(OracleConnection.class);
		Array array = oracleCon.createOracleArray("CONTRACT_ARRAY", tabContrat.toArray());

	    CallableStatement st = cn.prepareCall("{call test_array(?, ?) }");
	    
	    st.setArray(1, array);
	    st.registerOutParameter(2, Types.VARCHAR);
	    
        st.execute();

        // Récupérer la valeur de v_sortie
        String vSortie = st.getString(2);
        
        System.out.println(vSortie);
        
        CallableStatement st2 = cn.prepareCall("{call pkg_locataire.create_locataire("
        		+ " p_identifiant_locataire => ?, "
        		+ " p_nom_locataire => ?, "
        		+ " p_prenom_locataire => ?, "
        		+ " p_email_locataire => ?, "
        		+ " p_telephone_locataire => ?, "
        		+ " p_date_naissance => TO_DATE(?, 'YYYY-MM-DD'), "
        		+ " p_lieu_de_naissance => ?, "
        		+ " p_acte_de_caution => ?, "
        		+ "   p_id_sae_adresse => ?, "
        		+ "        p_contrats => ? )}");
	    
        st2.setString(1, loca.getIdLocataire());
        st2.setString(2, loca.getNom());
        st2.setString(3, loca.getPrenom());
        st2.setString(4, loca.getEmail());
        st2.setString(5, loca.getTelephone());
        st2.setString(6, loca.getDateNaissance());
        st2.setString(7, loca.getLieuDeNaissance());
        st2.setString(8, loca.getActeDeCaution());
        st2.setString(9, loca.getAdresse() == null ? null : loca.getAdresse().getIdAdresse());
        st2.setArray(10, array);
        
        st2.executeUpdate();
        
        ResultSet rs = cn.prepareStatement("Select * from sae_locataire").executeQuery();
        while(rs.next()) {
        	System.out.println(rs.getString("IDENTIFIANT_LOCATAIRE"));
        }
        
        

	}
	
    public static void deleteTestData() throws SQLException, IOException {
        String deleteContracts = "DELETE FROM SAE_contracter WHERE lower(identifiant_locataire) LIKE '%test%'";
        String deleteLocataires = "DELETE FROM SAE_LOCATAIRE WHERE lower(identifiant_locataire) LIKE '%test%'";
        String deleteAddresses = "DELETE FROM SAE_ADRESSE WHERE lower(ID_SAE_ADRESSE) LIKE '%test%'";

        Connection cn = ConnexionBD.getInstance().getConnexion();

        	Statement stmt = cn.createStatement();
        
            // Commencer une transaction
            cn.setAutoCommit(false);

            // Exécuter les suppressions
            stmt.executeUpdate(deleteContracts);
            stmt.executeUpdate(deleteLocataires);
            stmt.executeUpdate(deleteAddresses);

            // Valider les modifications
            cn.commit();
            System.out.println("Les données contenant 'test' ont été supprimées avec succès.");

    }
	
}
