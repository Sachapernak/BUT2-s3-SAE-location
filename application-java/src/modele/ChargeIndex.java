package modele;

import java.time.LocalDate;

public class ChargeIndex {
    String idBien;
    LocalDate dateDeReleve;
    String type;
    double valeur;
    LocalDate dateRelevePrecedent;
    double coutVariable;
    String coutFixe;

    public ChargeIndex(String idBien, LocalDate dateDeReleve, String type, double valeur, LocalDate dateRelevePrecedent,
                          double coutVariable, String coutFixe) {
        this.idBien = idBien;
        this.dateDeReleve = dateDeReleve;
        this.type = type;
        this.valeur = valeur;
        this.dateRelevePrecedent = dateRelevePrecedent;
        this.coutVariable = coutVariable;
        this.coutFixe = coutFixe;
    }

    public String getIdBien() {
        return idBien;
    }

    public LocalDate getDateDeReleve() {
        return dateDeReleve;
    }

    public String getType() {
        return type;
    }

    public double getValeur() {
        return valeur;
    }

    public LocalDate getDateRelevePrecedent() {
        return dateRelevePrecedent;
    }

    public double getCoutVariable() {
        return coutVariable;
    }

    public String getCoutFixe() {
        return coutFixe;
    }
}
