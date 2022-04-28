package DAO;

public class associazioni {
    int idDocente;
    int idCorso;

    public associazioni(int idDocente, int idCorso) {

        this.idDocente = idDocente;
        this.idCorso = idCorso;
    }



    public int getIdDocente() {
        return idDocente;
    }

    public int getIdCorso() {
        return idCorso;
    }
}
