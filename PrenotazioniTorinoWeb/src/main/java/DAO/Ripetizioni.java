package DAO;

public class Ripetizioni {
    String nome_docente;
    String cognome_docente;
    int id_docente;
    String nome_corso;
    int id_corso;

    public Ripetizioni(String nome_docente, String cognome_docente, int id_docente, String nome_corso, int id_corso) {
        this.nome_docente = nome_docente;
        this.cognome_docente = cognome_docente;
        this.id_docente = id_docente;
        this.nome_corso = nome_corso;
        this.id_corso = id_corso;
    }

    public String getNome_docente() {
        return nome_docente;
    }

    public String getCognome_docente() {
        return cognome_docente;
    }

    public int getId_docente() {
        return id_docente;
    }

    public String getNome_corso() {
        return nome_corso;
    }

    public int getId_corso() {
        return id_corso;
    }
}
