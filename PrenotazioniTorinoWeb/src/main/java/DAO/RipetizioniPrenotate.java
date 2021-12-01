package DAO;

public class RipetizioniPrenotate {
    String docente;
    String corso;
    String utente;
    String data;
    int ora;


    public RipetizioniPrenotate(String docente, String corso, String utente, String data, int ora) {
        this.docente = docente;
        this.corso = corso;
        this.utente = utente;
        this.data = data;
        this.ora = ora;
    }

    public String getDocente() {
        return docente;
    }

    public String getCorso() {
        return corso;
    }

    public String getUtente() {
        return utente;
    }

    public String getData() {
        return data;
    }

    public int getOra() {
        return ora;
    }
}
