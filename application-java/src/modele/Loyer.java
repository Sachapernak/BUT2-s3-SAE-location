package modele;


public class Loyer {
    String idBien;
    String dateDeChangement;
    double montantLoyer;

    public Loyer(String idBien, String dateDeChangement, double montantLoyer) {
        this.idBien = idBien;
        this.dateDeChangement = dateDeChangement;
        this.montantLoyer = montantLoyer;
    }

    public String getIdBien() {
        return idBien;
    }

    public String getDateDeChangement() {
        return dateDeChangement;
    }

    public double getMontantLoyer() {
        return montantLoyer;
    }
}
