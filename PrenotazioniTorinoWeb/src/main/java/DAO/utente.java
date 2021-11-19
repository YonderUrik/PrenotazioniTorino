package DAO;

public class utente {
    String cognome;
    String nome;
    int id;
    String email;
    String password;
    String ruolo;

    public utente(String cognome, String nome, int id, String email, String password, String ruolo) {
        this.cognome = cognome;
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }
}
