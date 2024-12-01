package modeleTest.UITest;

import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
		
		Bail bail = new Bail("IdBail1");
		Bail bail2 = new Bail("IdBail2");
		Bail bail3 = new Bail("IdBail3");
		
		Locataire loca = new Locataire("idLoc1", "Didier", "Jean", "1999-10-11");

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
	    

	}
	
}
