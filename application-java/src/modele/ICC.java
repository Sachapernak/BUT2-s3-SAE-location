package modele;

public class ICC {
    String annee;
    String trimestre;
    int indiceICC;

    public ICC(String annee, String trimestre, int indiceICC) {
        this.annee = annee;
        this.trimestre = trimestre;
        this.indiceICC = indiceICC;
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
