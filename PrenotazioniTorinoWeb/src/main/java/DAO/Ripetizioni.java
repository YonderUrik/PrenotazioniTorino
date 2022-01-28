package DAO;

public class Ripetizioni {
    String nome_docente;
    String cognome_docente;
    String nome_corso;
    String giorno;
    int ora;
    int idCorso;
    int idDocente;

    public Ripetizioni(String nome_docente, String cognome_docente, String nome_corso, String giorno, int ora, int idCorso, int idDocente) {
        this.nome_docente = nome_docente;
        this.cognome_docente = cognome_docente;
        this.nome_corso = nome_corso;
        this.giorno = giorno;
        this.ora = ora;
        this.idCorso = idCorso;
        this.idDocente = idDocente;
    }

    public String getNome_docente() {
        return nome_docente;
    }

    public String getCognome_docente() {
        return cognome_docente;
    }

    public String getNome_corso() {
        return nome_corso;
    }

    public String getGiorno() {
        return giorno;
    }

    public int getOra() {
        return ora;
    }

    public int getIdCorso() {
        return idCorso;
    }

    public int getIdDocente() {
        return idDocente;
    }
}
