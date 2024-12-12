package modele.dao.requetes;

import modele.Loyer;

public class RequeteSelectLoyer extends Requete<Loyer> {

    @Override
    public String requete() {
        return "SELECT * FROM SAE_LOYER";
    }
}