package DAO;

public class RipetizioniPrenotate {
    String docente;
    String corso;
    String utente;
    String data;
    String stato;
    int ora;
    int idCorso;
    int idDocente;
    int idUtente;

    public RipetizioniPrenotate(String docente, String corso, String utente, String data, String stato, int ora, int idCorso, int idDocente, int idUtente) {
        this.docente = docente;
        this.corso = corso;
        this.utente = utente;
        this.data = data;
        this.stato = stato;
        this.ora = ora;
        this.idCorso = idCorso;
        this.idDocente = idDocente;
        this.idUtente = idUtente;
    }


    public String getStato() {
        return stato;
    }

    public int getIdCorso() {
        return idCorso;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public int getIdUtente() {
        return idUtente;
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
