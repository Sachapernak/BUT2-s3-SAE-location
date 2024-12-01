package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Contracter;

public class RequeteSelectContratByIdBail extends Requete<Contracter> {


		@Override
		public String requete() {
			return "select * from sae_contracter "
					+ "where id_bail = ?";
		}


		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			// TODO Auto-generated method stub
			prSt.setString(1, id[0]);
		}
		
	
}
