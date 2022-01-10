package DAO;

public class Ripetizioni {
    String nome_docente;
    String cognome_docente;
    String nome_corso;

    public Ripetizioni(String nome_docente, String cognome_docente, String nome_corso) {
        this.nome_docente = nome_docente;
        this.cognome_docente = cognome_docente;
        this.nome_corso = nome_corso;
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


}
