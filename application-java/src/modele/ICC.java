package modele;

public class ICC {
    String idBien;
    String annee;
    String trimestre;
    int indiceICC;

    public ICC(String idBien, String annee, String trimestre, int indiceICC) {
        this.idBien = idBien;
        this.annee = annee;
        this.trimestre = trimestre;
        this.indiceICC = indiceICC;
    }

    public String getIdBien() {
        return idBien;
    }

    public String getAnnee() {
        return annee;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public int getIndiceICC() {
        return indiceICC;
    }
}
