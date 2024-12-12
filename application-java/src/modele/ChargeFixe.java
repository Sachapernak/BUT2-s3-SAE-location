package modele;


public class ChargeFixe {
    String idBien;
    String dateDeCharge;
    String type;
    String montant;

    public ChargeFixe(String idBien, String dateDeCharge, String type, String montant) {
        this.idBien = idBien;
        this.dateDeCharge = dateDeCharge;
        this.type = type;
        this.montant = montant;
    }

    public String getIdBien() {
        return idBien;
    }

    public String getDateDeCharge() {
        return dateDeCharge;
    }

    public String getType() {
        return type;
    }

    public String getMontant() {
        return montant;
    }
}
