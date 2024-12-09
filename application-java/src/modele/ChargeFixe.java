package modele;

import java.time.LocalDate;

public class ChargeFixe {
    String idBien;
    LocalDate dateDeCharge;
    String type;
    String montant;

    public ChargeFixe(String idBien, LocalDate dateDeCharge, String type, String montant) {
        this.idBien = idBien;
        this.dateDeCharge = dateDeCharge;
        this.type = type;
        this.montant = montant;
    }

    public String getIdBien() {
        return idBien;
    }

    public LocalDate getDateDeCharge() {
        return dateDeCharge;
    }

    public String getType() {
        return type;
    }

    public String getMontant() {
        return montant;
    }
}
