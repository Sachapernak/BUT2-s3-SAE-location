package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;

public class RequeteDeleteAssurance extends Requete<Assurance> {

    @Override
    public String requete() {
        return "DELETE FROM SAE_Assurance WHERE numero_de_Contrat = ? AND annee_du_Contrat = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        prSt.setString(1, donnee.getNumeroContrat());
        prSt.setInt(2, donnee.getAnneeContrat());
    }
}
